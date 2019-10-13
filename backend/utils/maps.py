import googlemaps
import configparser

import projectpaths

class Singleton(type):
    _instances = {}

    def __call__(cls, *args, **kwargs):
        if cls not in cls._instances:
            cls._instances[cls] = super(Singleton, cls).__call__(*args, **kwargs)
        return cls._instances[cls]


class Maps(metaclass=Singleton):
    def __init__(self):
        config = configparser.ConfigParser()
        config.read(projectpaths.config_path)
        token = config["google"]["api_token"]
        print(len(token))
        print(config["google"])
        self._gmaps = googlemaps.Client(key=token)


    def getCurrentLocation(self):
        loc = self._gmaps.geolocate()  # {'location': {'lat': 13.0477875, 'lng': 80.26557369999999}, 'accuracy': 7715.0}
        print(loc)

    def getGMaps(self):
        return self._gmaps
