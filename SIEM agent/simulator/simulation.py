import os
import sys
import time
from util.util import generate_syslog
import datetime
from util.util import get_mac_address


def normal_state():
    return generate_syslog(time.time(), "siem agent", "INFO", "11111", "This is the message")


def app_crashes():
    return generate_syslog(time.time(), "siem agent", "ERROR", "2222", "App failed to start")


def unsuccessful_login():
    return generate_syslog(time.time(), "siem agent", "WARN", "3333", "Unsuccessful login for user: username")


def database_error():
    return generate_syslog(time.time(), "siem agent", "ERROR", "4444", "Database error")


def start_simulation(log_interval, file_path, state_num):
    print("\n Starting simulation ...")

    while True:
        time.sleep(log_interval)
        print("\n Starting simulation ...")

        if state_num == 1:
            log = normal_state()
        elif state_num == 2:
            log = app_crashes()
        elif state_num == 3:
            log = unsuccessful_login()
        elif state_num == 4:
            log = database_error()

        with open(file_path, "a") as file:
            file.write(log + "\n")


if __name__ == "__main__":

    if len(sys.argv) == 3:
        interval, path = sys.argv[1], sys.argv[2]
    else:
        interval, path = "", ""

    print("\n *** SIMULATOR STARTED ***\n")

    while not interval.isdigit():
        interval = input("Enter interval (s): ")

    while not os.path.isfile(path):
        path = input("Enter file path (absolute): ")

    state = ""
    while state not in ["1", "2", "3", "4"]:
        print("Choose state: ")
        print(" 1. NORMAL")
        print(" 2. APP CRASHES")
        print(" 3. UNSUCCESSFUL LOGIN")
        print(" 4. DATABASE ERROR")
        state = input(">> ")

    start_simulation(int(interval), path, int(state))
