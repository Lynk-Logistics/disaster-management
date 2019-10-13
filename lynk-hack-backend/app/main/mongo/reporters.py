from app.main.mongo.base_mongo import BaseMongo
from app.main.mongo.mongo import MongoConnection
from bson import ObjectId


class ReportersService:
    collection_name = 'reporters'

    @staticmethod
    def get_collection():
        return MongoConnection.get_collection(ReportersService.collection_name)

    @staticmethod
    def add_reporter(victim):
        collection = ReportersService.get_collection()
        return BaseMongo.insert(collection, victim)

    @staticmethod
    def get_all():
        collection = ReportersService.get_collection()
        return BaseMongo.find_all(collection)

    @staticmethod
    def get_by_id(victim_id):
        collection = ReportersService.get_collection()
        return BaseMongo.find_one(collection, {'_id': ObjectId(victim_id)})

    @staticmethod
    def get_by_phone_no(phone_no):
        collection = ReportersService.get_collection()
        return BaseMongo.find_one(collection, {'phoneNo': phone_no})
