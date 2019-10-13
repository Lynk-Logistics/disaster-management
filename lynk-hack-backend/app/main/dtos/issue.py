from app.main.dtos.base_dto import BaseDTO
from app.main.dtos.location import Location


class Issue(BaseDTO):
    def __init__(self, obj):
        self.name = obj.get('name')
        self.no_of_people = obj.get('noOfPeople')
        self.phone_no = obj.get('phoneNo')
        self.address = obj.get('address')
        self.location = Location(obj.get('lat'), obj.get('long'))
        self.essentials = obj.get('essentials')
