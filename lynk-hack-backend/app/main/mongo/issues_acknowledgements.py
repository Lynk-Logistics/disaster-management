from app.main.mongo.base_mongo import BaseMongo
from app.main.mongo.mongo import MongoConnection
from bson import ObjectId


class IssuesAcknowledgementsService:
    collection_name = 'issues_acknowledgements'

    @staticmethod
    def get_collection():
        return MongoConnection.get_collection(IssuesAcknowledgementsService.collection_name)

    @staticmethod
    def add_acknowledgement(issue_volunteer_acknowledgement_mapping):
        collection = IssuesAcknowledgementsService.get_collection()
        return BaseMongo.insert(collection, issue_volunteer_acknowledgement_mapping)

    @staticmethod
    def get_all_acknowledgements_for_the_issue(issue_id):
        collection = IssuesAcknowledgementsService.get_collection()
        filters = {'issue_id': issue_id}
        return BaseMongo.find_all(collection, filters)

    @staticmethod
    def is_acknowledgement_present(issue_id, volunteer_id):
        collection = IssuesAcknowledgementsService.get_collection()
        filters = {'issue_id': issue_id, "volunteer_id": volunteer_id}
        return BaseMongo.find_one(collection, filters)

    @staticmethod
    def get_all_acknowledgements_for_the_volunteer(volunteer_id):
        collection = IssuesAcknowledgementsService.get_collection()
        filters = {'volunteer_id': volunteer_id}
        return BaseMongo.find_all(collection, filters)

