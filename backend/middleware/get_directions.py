import configparser
from utils.maps import Maps
from datetime import datetime

class GetDirections:
	def __init__(self):
		pass

	def loadGeoData(self):
		maps = Maps()
		maps.getGMaps()


	def getDirections(self,from_loc="navalur",to_loc="Adyar",mode="driving", departure_time=datetime.now()):
		maps = Maps()
		maps.getGMaps()

		directions_result = gmaps.directions(from_loc,
		                                     to_loc,
		                                     mode=mode,
		                                     alternatives="true",
		                                     # waypoints=["perumbakkam","velachery"],
		                                     departure_time=departure_time)
		print(directions_result)

