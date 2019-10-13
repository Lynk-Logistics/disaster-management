from flask_restplus import Namespace, Resource
from flask import request

from app.main.services.essentials import EssentialsService

api = Namespace('Essentials')


@api.route('essentials')
class Essentials(Resource):
    def post(self):
        essential = request.args.get('essential')
        priority = request.args.get('priority')
        id = EssentialsService.add_new_essential(essential, priority)
        return {'status': 'success', 'insertedId': id}

    def get(self):
        essentials = EssentialsService.get_essentials()
        return {
            'essentials': essentials
        }
