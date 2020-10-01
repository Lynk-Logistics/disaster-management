from flask_restplus import Namespace, Resource
from flask import request

api = Namespace('Victims', description="Endpoints to get victim details")


@api.route('victims')
class GetBrand(Resource):
    def post(self):
        name = request.args.get('name')
        return name
