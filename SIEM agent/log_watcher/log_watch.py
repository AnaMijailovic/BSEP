import datetime
import os
import re
import sys
import time
from threading import Thread, Lock

import win32con
import win32evtlog
import win32evtlogutil
from watchdog.events import PatternMatchingEventHandler
from watchdog.observers import Observer

from communication.communication import *
from configuration.configuration import *
from log_watcher.logEventHandler import LogEventHandler
from util.util import get_mac_address

URL = 'https://localhost:8082/siem/logs'


# TODO Send numbers instead of this?
EVENT_TYPES = {win32con.EVENTLOG_AUDIT_FAILURE: 'ERROR',
               win32con.EVENTLOG_AUDIT_SUCCESS: 'INFO',
               win32con.EVENTLOG_INFORMATION_TYPE: 'INFO',
               win32con.EVENTLOG_WARNING_TYPE: 'WARN',
               win32con.EVENTLOG_ERROR_TYPE: 'ERROR'}

LOCK = Lock()
LOGS = []


# not used right now
def create_event_handler():
    patterns = "*"
    ignore_patterns = ""
    ignore_directories = False
    case_sensitive = True
    handler = PatternMatchingEventHandler(patterns, ignore_patterns, ignore_directories, case_sensitive)

    return handler


def create_observers(paths_config):
    """
    Creates watchdog.observer for each directory_path specified in config.json.

    :param paths_config: configuration from config.json
    :return: list with created observers

    """
    all_observers = []

    for path_config in paths_config:
        new_observer = Observer()
        event_handler = LogEventHandler(path_config['source_name'], path_config['regex'], path_config['parse_log'],
                                        os.path.abspath(path_config['directory_path']),
                                        path_config['file_names'],
                                        path_config['recursive'] == "True", process_syslog)
        new_observer.schedule(event_handler, path_config['directory_path'],
                              recursive=path_config['recursive'] == "True")
        all_observers.append(new_observer)

    return all_observers


def read_windows_event_log():

    """
    Reads all windows event logs using win32evtlog library.

    :return: void
    """
    # TODO Multiple log types?
    server = windows_logs_config['server']  # name of the target computer to get event logs
    log_type = windows_logs_config['log_type']

    hand = win32evtlog.OpenEventLog(server, log_type)
    flags = win32evtlog.EVENTLOG_SEQUENTIAL_READ | win32evtlog.EVENTLOG_FORWARDS_READ

    now = datetime.datetime.now()

    # start infinite loop
    while True:
        events = win32evtlog.ReadEventLog(hand, flags, 0)
        if events:
            for event in events:
                if event.TimeGenerated > now:  # send only new messages

                    # get understandable text message about the error
                    message = win32evtlogutil.SafeFormatMessage(event, log_type)

                    # format syslog message
                    syslog = "{time} \t{mac_address} \t{source_name} --- \t{severity_level} [{id}] {message}".format(
                        time=event.TimeGenerated.Format("%d-%m-%Y %H:%M:%S"),
                        mac_address=get_mac_address(),
                        source_name=event.SourceName,  # the name of the software that logs the event
                        severity_level=EVENT_TYPES[event.EventType],
                        id=event.EventID,
                        message=message
                    )

                    process_syslog(syslog, windows_logs_config['regex'])


def process_syslog(syslog, regex_list):
    """
    Checks if log matches regexes defined in config file
    If mode is real_time sends log immediately to the siem center
    If mode is batch_processing appends new log to LOGS list

    :param syslog: string representing new log
    :param regex_list: list of regexes used to filter logs
    :return: void

    """

    # filter logs
    print(syslog)
    for regex in regex_list:
        pattern = re.compile(regex)
        if not pattern.match(syslog):
            return

    if mode == "real_time":
        post(URL, syslog)
    else:
        with LOCK:
            LOGS.append(syslog)


def batch_processing():
    """
    Used if mode is batch_processing.
    Starts infinite loop and sends all logs to siem center after each "interval" seconds

    :return: void
    """
    while True:
        time.sleep(interval)
        with LOCK:
            print("[INFO]  Sending ", str(len(LOGS)), " new  logs ")
            # TODO Send all logs in one request
            for log in LOGS:
                post(URL, log)
            LOGS.clear()


def start_threads():
    """
    Starts all necessary threads

    :return: void
    """
    # start thread for batch processing
    if mode == "batch_processing":
        print("[INFO] Starting batch_processing thread")
        batch_thread = Thread(target=batch_processing, args=())
        batch_thread.start()

    # start thread for windows logs
    if windows_logs_config['watch'] == "True":
        print("[INFO] Starting windows thread")
        win_thread = Thread(target=read_windows_event_log, args=())
        win_thread.start()

    # start threads for watching all other folders
    print("[INFO] Starting observer threads")
    for observer in observers:
        observer.start()
    try:
        while True:
            time.sleep(1)  # TODO is this needed?
    except KeyboardInterrupt:
        if mode == "batch_processing":
            batch_thread.stop()
            batch_thread.join()

        if windows_logs_config['watch'] == "True":
            win_thread.stop()
            win_thread.join()
        for observer in observers:
            observer.stop()
            observer.join()


if __name__ == "__main__":

    if len(sys.argv) == 2:
        interval = sys.argv[1]
    else:
        interval = 10

    mode = "real_time" if interval == 0 else "batch_processing"
    print("*** SIEM AGENT STARTED ***")
    print("MODE: " + mode)
    if mode == "batch_processing":
        print("INTERVAL: " + str(interval) + " s \n")

    # load config from json file
    paths_configs, windows_logs_config = load_paths_configuration("../configuration/config.json")
    observers = create_observers(paths_configs)

    start_threads()
