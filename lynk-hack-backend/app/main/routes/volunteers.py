from flask_restplus import Namespace, Resource
from flask import request

from app.main.services.volunteers import VolunteersService

api = Namespace('Volunteers', description="Endpoints to get volunteer details")


@api.route('volunteers')
class Volunteers(Resource):
    def post(self):
        volunteer = request.json
        volunteer_id = VolunteersService.add_new_volunteer(volunteer)
        return {'status': 'success', 'insertedId': volunteer_id}

    def get(self):
        volunteers = VolunteersService.get_all_volunteers()
        return {'volunteers': volunteers}


@api.route('volunteers/<volunteer_id>')
class GetVolunteers(Resource):
    def get(self, volunteer_id):
        volunteer = VolunteersService.get_volunteer(volunteer_id)
        return volunteer


@api.route('volunteers/<volunteer_id>/acknowledged-issues')
class GetAcknowledgedIssues(Resource):
    def get(self, volunteer_id):
        issues = VolunteersService.get_acknowledged_issues(volunteer_id)
        return {'issues': issues}


@api.route('volunteers/<volunteer_id>/non-acknowledged-issues')
class GetNonAcknowledgedIssues(Resource):
    def get(self, volunteer_id):
        issues = VolunteersService.get_non_acknowledged_issues(volunteer_id)
        return {'issues': issues}

