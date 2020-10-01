from bson import ObjectId

from app.main.mongo.base_mongo import BaseMongo
from app.main.mongo.mongo import MongoConnection


class EssentialsService:
    collection_name = 'essentials'

    @staticmethod
    def get_collection():
        return MongoConnection.get_collection(EssentialsService.collection_name)

    @staticmethod
    def add_essential(essential):
        essential_collection = EssentialsService.get_collection()
        return BaseMongo.insert(essential_collection, essential)

    @staticmethod
    def get_all():
        essential_collection = EssentialsService.get_collection()
        return BaseMongo.find_all(essential_collection)

    @staticmethod
    def get(name):
        essential_collection = EssentialsService.get_collection()
        essentials = BaseMongo.find_one(essential_collection, {'name': name.lower()})
        return essentials

    @staticmethod
    def get_by_id(essential_id):
        collection = EssentialsService.get_collection()
        return BaseMongo.find_one(collection, {'_id': ObjectId(essential_id)})
