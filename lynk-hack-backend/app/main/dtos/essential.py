from app.main.dtos.base_dto import BaseDTO


class Essential(BaseDTO):
    def __init__(self, name, priority):
        self.name = name.lower()
        self.priority = int(priority or 1)
