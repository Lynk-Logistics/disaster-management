from app.main.dtos.base_dto import BaseDTO


class Volunteer(BaseDTO):
    def __init__(self, obj):
        self.name = obj.get('name')
        self.phone_no = obj.get('phoneNo')
        self.email_id = obj.get('emailId')
