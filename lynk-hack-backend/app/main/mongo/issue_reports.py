from app.main.mongo.base_mongo import BaseMongo
from app.main.mongo.mongo import MongoConnection


class IssueReportsService:
    collection_name = 'issues_reports'

    @staticmethod
    def get_collection():
        return MongoConnection.get_collection(IssueReportsService.collection_name)

    @staticmethod
    def add_report(issue_report_mapping):
        collection = IssueReportsService.get_collection()
        return BaseMongo.insert(collection, issue_report_mapping)

    @staticmethod
    def get_all_reports_for_the_issue(issue_id):
        collection = IssueReportsService.get_collection()
        filters = {'issue_id': issue_id}
        return BaseMongo.find_all(collection, filters)

    @staticmethod
    def is_report_present(issue_id, reporter_id):
        collection = IssueReportsService.get_collection()
        filters = {'issue_id': issue_id, "reporter_id": reporter_id}
        return BaseMongo.find_one(collection, filters)

