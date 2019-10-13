from app.main.dtos.items import Items
from app.main.mongo.donate import DonateService as DonateDBService
from app.main.mongo.essentials import EssentialsService


class DonateService:
    @staticmethod
    def donate(item_maps, donor_id):
        items = Items(item_maps, donor_id)

        DonateDBService.donate(items.to_json())

    @staticmethod
    def get_donations(donar_id):
        donation = DonateDBService.get(donar_id)
        item_maps = donation['items']
        items = list()
        for item_map in item_maps:
            item = EssentialsService.get_by_id(item_map['itemId'])
            item['quantity'] = item_map['quantity']
            items.append(item)
        return items
