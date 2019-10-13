from flask_restplus import Namespace, Resource
from flask import request

from app.main.services.issues_acknowledgement import IssuesAcknowledgementsService

api = Namespace('Issues Acknowledgements')


@api.route('issues-acknowledgements')
class Volunteers(Resource):
    def post(self):
        volunteer = request.json
        phone_no = volunteer.get('phoneNo')
        volunteer_id = volunteer.get('volunteerId')
        IssuesAcknowledgementsService.acknowledge(volunteer_id=volunteer_id, phone_no=phone_no)
        return {'status': 'success', 'insertedId': volunteer_id}

    def get(self):
        volunteers = VolunteersService.get_all_volunteers()
        return {'volunteers': volunteers}


@api.route('volunteers/<volunteer_id>')
class GetVolunteers(Resource):
    def get(self, volunteer_id):
        volunteer = VolunteersService.get_volunteer(volunteer_id)
        return volunteer

