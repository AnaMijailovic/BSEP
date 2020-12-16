from log_watcher import parser
import calendar
import datetime
from util.util import generate_syslog


# Oct  7 00:25:47 unix_user-VirtualBox snapd[5511]: autorefresh.go:387: auto-refresh: all snaps are up-to-date
def parse_linux_log_date(line):
    """
    Extracts datetime form linux system log line and converts it into timestamp.
    Linux system log date format example:  Oct  7 00:25:47

    :param line: string representing linux system log
    :return: timestamp
    """
    month_abbr_regex = "([A-Za-z]{3}) "
    datetime_regex = "(\\d{1,2} \\d{2}:\\d{2}:\\d{2}) "
    datetime_format = "%Y %m %d %H:%M:%S"

    # get month number from abbreviation
    month_abbr = parser.match_regex(line, month_abbr_regex)
    mont_number = list(calendar.month_abbr).index(month_abbr)

    # get current year
    year = datetime.datetime.now().year
    datetime_str = parser.match_regex(line, datetime_regex)
    # make full datetime
    full_datetime = str(year) + " " + str(mont_number) + " " + datetime_str

    # convert to timestamp
    return int(datetime.datetime.strptime(full_datetime, datetime_format).timestamp())


def parse_linux_log_line(line):
    """
     Converts  linux system log line into syslog format.
     
    :param line:  string representing linux system log
    :return:  syslog
    """
    timestamp = parse_linux_log_date(line)
    tokens = line.split()
    source_name = tokens[4].split("[")[0]
    source_name = source_name.replace(":", "")

    event_id = "unknown"
    try:
        event_id = tokens[4].split("[")[1]
        event_id = event_id.replace("]:", "")
    except IndexError:
        pass
    message = ' '.join(tokens[5:])

    # TODO How to change this?
    severity_level = "UNKNOWN"

    # print("Source name: " + source_name)
    # print("Message: " + message)
    # print("Event id: " + event_id)

    syslog = generate_syslog(timestamp, source_name, severity_level, event_id, message)
    print("Linux sys log: " + syslog)
    return syslog


if __name__ == "__main__":

    parse_linux_log_line(
        "Jun  1 22:20:05 secserv kernel: Kernel logging (proc) stopped.")
