import re
import time
import datetime
from util.util import get_mac_address


def match_regex(line, regex):
    """
    Extracts part of the string line that matches regular expression.

    :param line: string representing log
    :param regex: regex used to match the part of the line
    :return: part of the line matching regex or None
    """

    matching_string = re.search(re.compile(regex), line)
    return matching_string.group(1) if matching_string is not None else None


def get_datetime(line, datetime_regex, datetime_format):
    """
    Converts datetime form log line into standard format.

    :param line: string representing whole log
    :param datetime_regex: regex used to extract datetime string from whole log line
    :param datetime_format: format in which datetime is represented in log line
    :return: datetime from log line in standard format or
             current datetime in standard format if datetime is missing in log line
    """
    parsed_time = match_regex(line, datetime_regex)

    if parsed_time is not None:
        timestamp = int(datetime.datetime.strptime(parsed_time, datetime_format).timestamp())
    else:
        timestamp = time.time()

    # TODO Remove datetime format to some config file
    return datetime.datetime.fromtimestamp(timestamp).strftime("%d-%m-%Y %H:%M:%S")


def parse_log_line(line, parse_regex, source):
    """
    Converts log line into syslog format.

    :param line: string representing whole log
    :param parse_regex: dictionary containing regex names as keys
                        and regular expressions as values
    :param source: the name of the software that logs the event
                   used if source name is missing in log line
    :return: log line in syslog format
    """

    source_name = match_regex(line, parse_regex['source_name_regex'])
    if source_name is None or source_name == "":
        source_name = source

    syslog = "{time} \t{mac_address} \t{source_name}  \t{severity_level} - [{id}] {message}".format(
        time=get_datetime(line, parse_regex['datetime_regex'], parse_regex['datetime_format']),
        mac_address=get_mac_address(),
        source_name=source_name,
        severity_level=match_regex(line, parse_regex['severity_level_regex']),
        id=match_regex(line, parse_regex['event_id_regex']),
        message=match_regex(line, parse_regex['message_regex'])
    )

    return syslog


if __name__ == '__main__':
    date_format = "%Y-%m-%d %H:%M"
    print("Get datetime: ", get_datetime("1991-09-21 22:22 source_name",
                                         "(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}) [a-z A-Z]*",
                                         date_format))

    print("Match name: " + match_regex("name ana is valid", "[a-z A-Z]* (.*) [a-z A-Z]* [a-z A-Z]*"))
    print("Match severity level: " + match_regex("INFO 1991-09-21 22:22 23456666 source_name",
                                                 "([A-Z]*) \\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2} [0-9]*" +
                                                 " [a-z A-Z]*"))
    print("Match valid date: " + match_regex("INFO 1991-09-21 22:22 23456666 source_name - Some info message",
                                             "[A-Z]* (\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}) [0-9]*" +
                                             " [a-z A-Z]*"))
    print("Match event id: " + match_regex("WARN 1991-09-21 22:22 23456666 source_name - Some warning",
                                           "[A-Z]* \\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2} ([0-9]*) [a-z A-Z]*"))
    print("Match source_name: " + match_regex("ERROR 1991-09-21 22:22 source_name - Some error happened!",
                                              "[A-Z]* \\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2} ([a-z _ A-Z]*)"))
    print("Match message: " + match_regex("ERROR 1991-09-21 22:22 7487345 source_name - Some error happened!",
                                          "[A-Z]* \\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2} [0-9]* [a-z _ A-Z]* - (.*)"))

    # Test if everything works fine
    # Expected output: 21-09-2019 22:22:00 	44:1C:A8:1B:C5:19 	agent   	ERROR - [23456666] Some error happened!
    test_line = "ERROR 2019-09-21 22:22 23456666 agent - Some error happened!"
    test_parse_regex = {
      "datetime_format": "%Y-%m-%d %H:%M",
      "datetime_regex": "[A-Z]* (\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}) [0-9]* [a-zA-Z1-9_ ]*",
      "source_name_regex": "[A-Z]* \\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2} [0-9]* ([a-zA-Z1-9_ ]*)",
      "severity_level_regex": "([A-Z]*) \\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2} [0-9]* [a-zA-Z1-9_ ]*",
      "event_id_regex": "[A-Z]* \\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2} ([0-9]*) [a-zA-Z1-9_ ]*",
      "message_regex": "[A-Z]* \\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2} [0-9]* [a-zA-Z1-9_ ]* - (.*)"
    }

    test_source_name = "siem agent"

    print("\nParse log line: " + parse_log_line(test_line, test_parse_regex, test_source_name))
