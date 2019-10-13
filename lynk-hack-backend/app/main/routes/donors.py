from flask_restplus import Namespace, Resource
from flask import request

from app.main.services.donate import DonateService
from app.main.services.donor import DonorsService

api = Namespace('Donors')


@api.route('donors')
class Donors(Resource):
    def post(self):
        donor = request.json
        donor_id = DonorsService.add_new_donor(donor)
        return {'status': 'success', 'insertedId': donor_id}

    def get(self):
        donors = DonorsService.get_all_donors()
        return {'donors': donors}


@api.route('donors/<donor_id>')
class GetDonors(Resource):
    def get(self, donor_id):
        donor = DonorsService.get_donor(donor_id)
        return donor


@api.route('donors/<donor_id>/donate')
class GetDonors(Resource):
    def post(self, donor_id):
        items = request.json
        DonateService.donate(items['items'], donor_id)
        return {'status': 'success'}

    def get(self, donor_id):
        items = DonateService.get_donations(donor_id)
        return {'items': items}


@api.route('donors/essential-requirements')
class GetEssentialRequirements(Resource):
    def get(self):
        essentials = DonorsService.get_essential_requirements()
        return {
            "essentialRequirements": essentials
        }