import json

import firebase_admin
from firebase_admin import db
from firebase_admin import credentials

from datetime import timedelta, datetime

from config.config import *
from utils.datetime_encoder import DateTimeEncoder

cred = credentials.Certificate("hacks_cred.json")
firebase_admin.initialize_app(cred,{
	'databaseURL': 'https://hacks-255317.firebaseio.com'
})
marked_areas = db.reference('marked_areas')

def save_marked_area(request):
	area = marked_areas.push(request)
	return {'id': area.key}

def update_marked_area_status(id, status):
	data = marked_areas.child(id)
	data.update({
	    STATUS: status
	})
	return {'response': "success"}

def update_marked_area_cv(id, cv):
	data = marked_areas.child(id)
	data.update({
	    "cloud_vision_data": cv
	})
	return {'response': "success"}

def get_marked_areas():
	marked_areas_dict = marked_areas.get()
	return marked_areas_dict

def get_pending_verification():
	marked_areas_dict = get_marked_areas()
	now = datetime.now()
	pendingList = []
	for key, value in marked_areas_dict.items():
	    for i,j in value.items():
	        if (i==STATUS and j in [PENDING]):
	            pendingList.append({key:value})
	return pendingList

def get_unsafe_areas():
	now = datetime.now()
	marked_areas_dict = get_marked_areas()
	print (now)
	lat_lon_list = []
	for key, value in marked_areas_dict.items():
	    for i,j in value.items():
	        if (i==STATUS and j in [VERIFIED, VERIFIED_BY_CV]) and \
	        (datetime.now() - timedelta(hours=24) <= datetime.strptime(value["date"], "%Y-%m-%dT%H:%M:%S.%fZ") <= datetime.now()):
	            print(lat_lon_list)
	            lat_lon_list.append([value["latLng"]["lng"],value["latLng"]["lat"]])

	geo_json = {
    "features": [{
        "type": "Feature",
        "properties": {
            "class": "street",
            "oneway": 0,
            "osm_id": 384524249,
            "type": "unclassified",
            "is_flooded": True,
            "weight": 100,
            "timestamp": 1509696548406
        },
        "geometry": {
            "coordinates": lat_lon_list,
            "type": "LineString"
        },
        "id": "0054da0108c8bcb3185b917df2317cff"
    }],
    "type": "FeatureCollection"
	}
	
	return geo_json
