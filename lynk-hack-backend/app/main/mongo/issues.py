from app.main.mongo.base_mongo import BaseMongo
from app.main.mongo.mongo import MongoConnection
from bson import ObjectId


class IssuesService:
    collection_name = 'issues'
    EARTH_EQUATORIAL_RADIUS = 6378.1

    @staticmethod
    def __build_location_filters(coordinate):
        distance_to_km = coordinate.radius / 1000
        distance_to_radians = distance_to_km / IssuesService.EARTH_EQUATORIAL_RADIUS
        filters = {
            "location": {"$geoWithin": {"$centerSphere": [[coordinate.long, coordinate.lat], distance_to_radians]}}}
        return filters

    @staticmethod
    def __build_aggregate_location_filters(coordinate):
        filters = [
            {
                "$geoNear": {
                    "near": {
                        "type": "Point",
                        "coordinates": [
                            coordinate.long,
                            coordinate.lat
                        ]
                    },
                    "spherical": True,
                    "distanceField": "distance",
                    "distanceMultiplier": 0.001
                }
            },
            {
                "$bucket": {
                    "groupBy": "$distance",
                    "boundaries": [
                        0, 5, 10, 20, 50, 100, 500
                    ],
                    "default": "greater than 500km",
                    "output": {
                        "count": {
                            "$sum": 1
                        },
                        "docs": {
                            "$push": "$$ROOT"
                        }
                    }
                }
            }
        ]
        return filters

    @staticmethod
    def get_collection():
        return MongoConnection.get_collection(IssuesService.collection_name)

    @staticmethod
    def add_issue(issue):
        essential_collection = IssuesService.get_collection()
        return BaseMongo.insert(essential_collection, issue)

    @staticmethod
    def get_all():
        essential_collection = IssuesService.get_collection()
        return BaseMongo.find_all(essential_collection)

    @staticmethod
    def get_all_issues_based_on_coordinates(coordinate):
        essential_collection = IssuesService.get_collection()
        filters = IssuesService.__build_location_filters(coordinate)
        return BaseMongo.find_all(essential_collection, filters)

    @staticmethod
    def get_by_id(issue_id):
        essential_collection = IssuesService.get_collection()
        essentials = BaseMongo.find_one(essential_collection, {'_id': ObjectId(issue_id)})
        return essentials

    @staticmethod
    def get_clusters(coordinate):
        issues_collection = IssuesService.get_collection()
        filters = IssuesService.__build_aggregate_location_filters(coordinate)

        cursor = BaseMongo.aggregate(issues_collection, filters)

        documents = list()
        for document in cursor:
            new_document = {}
            new_document['distance'] = document.pop("_id")
            new_document['count'] = document['count']
            docs = document.get('docs')
            for doc in docs:
                doc["id"] = str(doc.pop("_id"))
            new_document['issues'] = docs
            documents.append(new_document)

        return documents

