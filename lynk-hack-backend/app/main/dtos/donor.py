from app.main.dtos.base_dto import BaseDTO
from app.main.dtos.location import Location


class Donor(BaseDTO):
    def __init__(self, obj):
        self.name = obj.get('name')
        self.phone_no = obj.get('phoneNo')
        self.location = Location(obj.get('lat'), obj.get('long'))
