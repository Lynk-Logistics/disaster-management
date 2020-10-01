class BaseMongo:
    @staticmethod
    def insert(collection, document):
        insert_one_result = collection.insert_one(document)

        if insert_one_result.acknowledged:
            return insert_one_result.inserted_id

        raise Exception('MONGODB_ERROR')

    @staticmethod
    def find_all(collection, filters={}):
        cursor = collection.find(filters)

        documents = list()
        for document in cursor:
            document['id'] = str(document.pop('_id'))
            documents.append(document)

        return documents

    @staticmethod
    def find_one(collection, filters):
        document = collection.find_one(filters)
        if not document:
            return None
        document['id'] = str(document.pop('_id'))
        return document

    @staticmethod
    def aggregate(collection, filters):
        cursor = collection.aggregate(filters)
        return cursor

    @staticmethod
    def replace_one(collection, filters, replacement):
        update_result = collection.replace_one(filters, replacement, upsert=True)

        if update_result.acknowledged:
            return True

        raise Exception('MONGODB_ERROR')


