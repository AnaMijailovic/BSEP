import os
from watchdog.events import FileSystemEventHandler
from util import util
from log_watcher import parser
from log_watcher import linux_log_parser


class LogEventHandler(FileSystemEventHandler):

    def __init__(self, source_name, regex_list, parse_log, path, file_names, recursive, callback):
        super(LogEventHandler, self).__init__()

        self.source_name = source_name,
        self.path = path
        self.files = util.create_file_lines_dict(path, file_names, recursive)
        self.parse_log = parse_log
        self.regex_list = regex_list
        self.recursive = recursive
        self.callback = callback

    def on_modified(self, event):
        """
        This method is called when change in file is detected -> new log written.
        Finds new lines (logs) and calls callback function to process them.

        :param event: event.src_path: path to the file that has been modified
        :return: void
        """
        abs_path = os.path.abspath(event.src_path)
        print(f"[INFO] {abs_path} has been modified")
        try:
            with open(abs_path, 'r') as f:
                lines = f.readlines()
                offset = self.files[abs_path]
                for line in lines[offset:]:
                    self.files[abs_path] = len(lines)

                    if self.parse_log == "False":
                        syslog = line
                    elif self.parse_log == "Linux":
                        syslog = linux_log_parser.parse_linux_log_line(line)
                    else:
                        syslog = parser.parse_log_line(line, self.parse_log, self.source_name)

                    self.callback(syslog, self.regex_list)
        except KeyError:
            pass
