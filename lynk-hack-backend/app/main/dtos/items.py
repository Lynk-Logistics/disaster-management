from app.main.dtos.base_dto import BaseDTO


class Items(BaseDTO):
    def __init__(self, item_maps, donar_id):
        self.donated_by = donar_id
        self.items = item_maps