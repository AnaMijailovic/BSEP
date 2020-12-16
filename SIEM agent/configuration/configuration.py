import json


def load_paths_configuration(config_file_path):
    """
    Loads configuration from json file.

    :param config_file_path:
    :return:
    """
    with open(config_file_path) as json_file:
        data = json.load(json_file)
    return data['paths'], data['windows_logs']


if __name__ == "__main__":
    config = load_paths_configuration("config.json")
    print(config)
