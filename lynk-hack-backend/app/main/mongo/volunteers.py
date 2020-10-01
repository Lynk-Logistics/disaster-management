from app.main.mongo.base_mongo import BaseMongo
from app.main.mongo.mongo import MongoConnection
from bson import ObjectId


class VolunteersService:
    collection_name = 'volunteers'

    @staticmethod
    def get_collection():
        return MongoConnection.get_collection(VolunteersService.collection_name)

    @staticmethod
    def add_volunteer(volunteer):
        volunteer_collection = VolunteersService.get_collection()
        return BaseMongo.insert(volunteer_collection, volunteer)

    @staticmethod
    def get_all():
        collection = VolunteersService.get_collection()
        return BaseMongo.find_all(collection)

    @staticmethod
    def get_by_id(volunteer_id):
        volunteer_collection = VolunteersService.get_collection()
        return BaseMongo.find_one(volunteer_collection, {'_id': ObjectId(volunteer_id)})

    @staticmethod
    def get_by_email(email):
        volunteer_collection = VolunteersService.get_collection()
        return BaseMongo.find_one(volunteer_collection, {'emailId': email})

    @staticmethod
    def get_volunteer_id_by_phone_no(phone_no):
        volunteer_collection = VolunteersService.get_collection()
        return BaseMongo.find_one(volunteer_collection, {'phoneNo': phone_no})
