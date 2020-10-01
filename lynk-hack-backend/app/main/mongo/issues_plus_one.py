from app.main.mongo.base_mongo import BaseMongo
from app.main.mongo.mongo import MongoConnection


class IssuesPlusOneService:
    collection_name = 'issues_plus_one'

    @staticmethod
    def get_collection():
        return MongoConnection.get_collection(IssuesPlusOneService.collection_name)

    @staticmethod
    def add_plus_one(issue_victim_plus_one_mapping):
        collection = IssuesPlusOneService.get_collection()
        return BaseMongo.insert(collection, issue_victim_plus_one_mapping)

    @staticmethod
    def get_all_plus_ones_for_the_issue(issue_id):
        collection = IssuesPlusOneService.get_collection()
        filters = {'issue_id': issue_id}
        return BaseMongo.find_all(collection, filters)

    @staticmethod
    def is_plus_one_present(issue_id, victim_id):
        collection = IssuesPlusOneService.get_collection()
        filters = {'issue_id': issue_id, "victim_id": victim_id}
        return BaseMongo.find_one(collection, filters)

