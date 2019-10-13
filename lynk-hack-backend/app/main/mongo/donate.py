from app.main.mongo.base_mongo import BaseMongo
from app.main.mongo.mongo import MongoConnection
from bson import ObjectId


class DonateService:
    collection_name = 'donate'

    @staticmethod
    def get_collection():
        return MongoConnection.get_collection(DonateService.collection_name)

    @staticmethod
    def donate(items):
        collection = DonateService.get_collection()
        donation = DonateService.get(items.get('donatedBy'))
        if not donation:
            return BaseMongo.insert(collection, items)
        filters = {'_id': ObjectId(donation.get('id'))}
        return BaseMongo.replace_one(collection, filters, items)

    @staticmethod
    def get(donar_id):
        collection = DonateService.get_collection()
        filters = {'donatedBy': donar_id}
        return BaseMongo.find_one(collection, filters)

    @staticmethod
    def get_donor_recommendations(essential_ids):
        collection = DonateService.get_collection()
        items_filter = [{"items.itemId": essential_id} for essential_id in essential_ids]
        filters = {}
        if items_filter:
            filters["$or"] = items_filter
        return BaseMongo.find_all(collection, filters)

