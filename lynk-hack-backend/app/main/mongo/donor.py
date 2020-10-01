from app.main.mongo.base_mongo import BaseMongo
from app.main.mongo.mongo import MongoConnection
from bson import ObjectId


class DonarsService:
    collection_name = 'donars'

    @staticmethod
    def get_collection():
        return MongoConnection.get_collection(DonarsService.collection_name)

    @staticmethod
    def add_donor(donor):
        collection = DonarsService.get_collection()
        return BaseMongo.insert(collection, donor)

    @staticmethod
    def get_all():
        collection = DonarsService.get_collection()
        return BaseMongo.find_all(collection)

    @staticmethod
    def get_by_id(donor_id):
        collection = DonarsService.get_collection()
        return BaseMongo.find_one(collection, {'_id': ObjectId(donor_id)})

    @staticmethod
    def get_by_phone_no(phone_no):
        collection = DonarsService.get_collection()
        return BaseMongo.find_one(collection, {'phoneNo': phone_no})
