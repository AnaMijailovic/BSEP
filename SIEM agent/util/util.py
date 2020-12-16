import os
import sys
import datetime


def generate_syslog(timestamp, source_name, severity_level, event_id, message):
    """
        Generates syslog

    :param timestamp:
    :param source_name:
    :param severity_level:
    :param event_id:
    :param message:
    :return:
    """
    syslog = "{time} \t{mac_address} \t{source_name} --- \t{severity_level}  [{id}] {message}".format(
        time=datetime.datetime.fromtimestamp(timestamp).strftime("%d-%m-%Y %H:%M:%S"),
        mac_address=get_mac_address(),
        source_name=source_name,
        severity_level=severity_level,
        id=event_id,
        message=message
    )

    return syslog


def get_mac_address():
    """
    Gets media access control address

    :return: mac address
    """

    if sys.platform == 'win32':
        for line in os.popen("ipconfig /all"):
            if line.lstrip().startswith('Physical Address'):
                mac = line.split(':')[1].strip().replace('-', ':')
                break

    return mac


def find_all_files(directory_path, recursive):
    """
    Finds all files in a directory.

    :param directory_path:
    :param recursive: if True looks in the subdirectories too
    :return: list with file paths
    """

    all_files = []

    for root, directories, files in os.walk(directory_path):
        for file in files:
            all_files.append(os.path.join(os.path.abspath(root), file))
        if not recursive:
            break   # prevent descending into subdirectories

    return all_files


def find_number_of_lines(path):
    """
    Finds the number of lines in the file.

    :param path: file path
    :return: number of lines in the file
    """
    i = 0
    with open(path, 'rb') as f:
        for i, l in enumerate(f):
            pass
    return i + 1


def create_file_lines_dict(directory_path, file_names, recursive):
    """
    Creates dictionary containing file paths as keys and number of file lines as values

    :param directory_path:
    :param file_names: list containing names of files of interest (in directory_path),
                       if empty -> look for all files in directory_path
    :param recursive: if True looks in the subdirectories too
                      ignored if file_names are specified
    :return: dictionary containing file paths as keys and number of lines as values
    """
    file_paths = []

    if file_names:
        for file_name in file_names:
            file_paths.append(os.path.join(os.path.abspath(directory_path), file_name))
    else:
        file_paths = find_all_files(directory_path, recursive)

    file_lines_dict = {}
    for path in file_paths:
        file_lines_dict[path] = find_number_of_lines(path)

    return file_lines_dict
