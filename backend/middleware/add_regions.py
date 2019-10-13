import json
class AddRegions:
	def addGeoStr(self, geoData):
		if len(geoData["features"])>0 :
			file = open("static/html/data.geojson", "r+")
			oldData = file.read()
			if oldData!="":
				print("im=====")
				oldData = json.loads(oldData)
				oldData["features"].extend(geoData["features"])
				print(oldData)
				file.seek(0)
				file.write(json.dumps(oldData))
				file.truncate()
			else:
				print("-------")
				file.write(json.dumps(oldData))
			file.close()
