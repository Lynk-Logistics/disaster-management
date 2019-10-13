from flask import Flask, Blueprint, render_template, request, make_response, jsonify, send_from_directory, Response
from flask_api import status
import flask_restful
from flask_restful import abort
from flask import jsonify
import calendar
import time
import subprocess
import datetime
import os
import json
from utils.cloud_vision import *
from utils.maps import Maps
from utils.cloud_storage import *
from config.config import *
from middleware.add_regions import AddRegions

bees_blueprint = Blueprint('bees', __name__, url_prefix='/api')
bees_api = flask_restful.Api(bees_blueprint)

class BeesRoutesController(flask_restful.Resource):

    def get(self):
        maps = Maps()
        maps.getCurrentLocation()
        headers = {'Content-Type': 'text/html'}
        return {'task': 'Hello world - get'}, 201

    def post(self):
        try:
            headers = {'Content-Type': 'text/html'}
            return {'task': 'Hello world - post'}, 201
        except Exception as e:
            headers = {'Content-Type': 'text/html'}
            return {'task': 'Hello world'}, 201

bees_api.add_resource(BeesRoutesController, '/routes')


class GetPendingAreasForVerificationController(flask_restful.Resource):

    def get(self):
        try:
            request_data = request.get_json()
            data = {'response': 'success'}
        except Exception as e:
            data = {'response': 'error', "error_msg": str(e)}
        response = jsonify(data)
        return response

    def post(self):
        try:
            data = {'response': 'success'}
        except Exception as e:
            data = {'response': 'error', "error_msg": str(e)}
        response = jsonify(data)
        return response


bees_api.add_resource(GetPendingAreasForVerificationController, '/pending-verification')


class GetUnsafeAreasController(flask_restful.Resource):
    def get(self):
        try:
            print ("inside")
            data = {'response': get_unsafe_areas()}
        except Exception as e:
            data = {'response': 'error', "error_msg": str(e)}
        response = jsonify(data)
        return response

bees_api.add_resource(GetUnsafeAreasController, '/get-unsafe-areas')


class GetPendingVerificationsController(flask_restful.Resource):
    def get(self):
        try:
            request_data = request.get_json()
            data = {'response': get_pending_verification()}
            response = jsonify(data)
            return response
        except Exception as e:
            data = {'response': 'error', "error_msg": str(e)}
            response = jsonify(data)
            return response

bees_api.add_resource(GetPendingVerificationsController, '/get-pending-verifications')


class UpdatePendingVerificationsController(flask_restful.Resource):
    def post(self):
        try:
            request_data = request.get_json()
            update_marked_area_status(request_data["id"],request_data["status"])
            data = {'response': 'success', 'msg': 'Status updated!'}
            response = jsonify(data)
            return response
        except Exception as e:
            data = {'response': 'error', "error_msg": str(e)}
            response = jsonify(data)
            return response

bees_api.add_resource(UpdatePendingVerificationsController, '/update-pending-verifications')


class MarkUnsafeAreasController(flask_restful.Resource):

    def post(self):
        try:
            request_data = request.get_json()
            id = save_marked_area(request_data)
            if is_unsafe(id["id"], request.get_json())['unsafe']:
                update_marked_area_status(id["id"], VERIFIED_BY_CV)
                data = {'response': 'success', 'msg': 'Updated successfully'}
            else:
                update_marked_area_status(id["id"], PENDING)
                data = {'response': 'success', 'msg': 'Your request has been sent for validation'}
            response = jsonify(data)
            return response
        except Exception as e:
            data = {'response': 'error', 'msg': 'Something went wrong', 'err':str(e)}
            response = jsonify(data)
            return response

bees_api.add_resource(MarkUnsafeAreasController, '/mark-unsafe-areas')


class BeesGetHelpController(flask_restful.Resource):

    def get(self):
        headers = {'Content-Type': 'text/html'}
        return {'task': 'Hello world - get'}, 201

    def post(self):
        try:
            headers = {'Content-Type': 'text/html'}
            return {'task': 'Hello world - post'}, 201
        except Exception as e:
            headers = {'Content-Type': 'text/html'}
            return {'task': 'Hello world'}, 201
#            return make_response(
#                render_template(
#                    'test.html',
#                    msg="Could not be added"),
#                200,
#                headers)

bees_api.add_resource(BeesGetHelpController, '/get-help')

class BeesVolunteersController(flask_restful.Resource):

    def get(self):

        headers = {'Content-Type': 'text/html'}
        return {'task': 'Hello world - get'}, 201

    def post(self):
        try:
            headers = {'Content-Type': 'text/html'}
            return {'task': 'Hello world - post'}, 201
        except Exception as e:
            headers = {'Content-Type': 'text/html'}
            return {'task': 'Hello world'}, 201

bees_api.add_resource(BeesVolunteersController, '/volunteers')

class BeesDonationsController(flask_restful.Resource):

    def get(self):

        headers = {'Content-Type': 'text/html'}
        return {'task': 'Hello world - get'}, 201

    def post(self):
        try:
            headers = {'Content-Type': 'text/html'}
            return {'task': 'Hello world - post'}, 201
        except Exception as e:
            headers = {'Content-Type': 'text/html'}
            return {'task': 'Hello world'}, 201

bees_api.add_resource(BeesDonationsController, '/donations')


class BeesUpdateGeoDataController(flask_restful.Resource):
    def get(self):
        headers = {'Content-Type': 'text/html'}
        return make_response(render_template('flooded_regions.html'), 200, headers)

    def post(self):
        print(request.json)
        AddRegions().addGeoStr(request.json)
        headers = {'Content-Type': 'text/html'}
        return {'status': 'Successfully update the data'}, 201


bees_api.add_resource(BeesUpdateGeoDataController, '/update-geo-data')


class BeesDirectionsController(flask_restful.Resource):
    def get(self):
        headers = {'Content-Type': 'text/html'}
        data = request.get_json()
        return make_response(render_template('directions.html', from_lat=request.args["from_lat"], from_lng=request.args["from_lng"], to_lat=request.args["to_lat"], to_lng=request.args["to_lng"]), 200, headers)


bees_api.add_resource(BeesDirectionsController, '/directions')



class BeesHelpController(flask_restful.Resource):

    def get(self):

        headers = {'Content-Type': 'text/html'}
        return {'task': 'Hello world - get'}, 201

    def post(self):
        try:
            headers = {'Content-Type': 'text/html'}
            return {'task': 'Hello world - post'}, 201
        except Exception as e:
            headers = {'Content-Type': 'text/html'}
            return {'task': 'Hello world'}, 201

bees_api.add_resource(BeesHelpController, '/help')