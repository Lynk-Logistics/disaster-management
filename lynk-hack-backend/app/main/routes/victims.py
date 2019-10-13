from flask_restplus import Namespace, Resource
from flask import request

api = Namespace('Victims', description="Endpoints to get victim details")


@api.route('victims')
class GetVictims(Resource):
    def get(self):
        print('coming here')
        name = request.args.get('name')
        return {'name': name}
