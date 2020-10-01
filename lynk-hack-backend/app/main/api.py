from flask import Blueprint
from flask_restplus import Api

blueprint = Blueprint('api', __name__)


api = Api(blueprint, title='Lynk HAck')
