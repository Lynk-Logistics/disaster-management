from app.main.mongo.base_mongo import BaseMongo
from app.main.mongo.mongo import MongoConnection
from bson import ObjectId


class VictimsService:
    collection_name = 'victims'

    @staticmethod
    def get_collection():
        return MongoConnection.get_collection(VictimsService.collection_name)

    @staticmethod
    def add_victim(victim):
        collection = VictimsService.get_collection()
        return BaseMongo.insert(collection, victim)

    @staticmethod
    def get_all():
        collection = VictimsService.get_collection()
        return BaseMongo.find_all(collection)

    @staticmethod
    def get_by_id(victim_id):
        collection = VictimsService.get_collection()
        return BaseMongo.find_one(collection, {'_id': ObjectId(victim_id)})

    @staticmethod
    def get_by_phone_no(phone_no):
        collection = VictimsService.get_collection()
        return BaseMongo.find_one(collection, {'phoneNo': phone_no})
