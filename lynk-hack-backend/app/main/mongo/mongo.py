from pymongo import MongoClient
from app.main.config import Config


class MongoConnection:
    __instance = None
    mongo_connection_options = {'ssl': False, 'serverSelectionTimeoutMS': 5000, 'connectTimeoutMS': 5000}

    @staticmethod
    def get_instance():
        if MongoConnection.__instance is None:
            MongoConnection.__instance = MongoClient(Config.DB_URL, **MongoConnection.mongo_connection_options)
        return MongoConnection.__instance

    @staticmethod
    def get_collection(collection_name, db_name=Config.DB_NAME):
        client = MongoConnection.get_instance()
        db = client[db_name]
        return db[collection_name]

    @staticmethod
    async def close():
        if MongoConnection.__instance is not None:
            await MongoConnection.__instance.close()
