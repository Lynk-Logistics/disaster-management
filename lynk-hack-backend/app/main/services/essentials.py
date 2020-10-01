from app.main.dtos.essential import Essential
from app.main.mongo.essentials import EssentialsService as EssentialsDBService


class EssentialsService:
    @staticmethod
    def add_new_essential(essential, priority):
        essential_model = Essential(essential, priority)
        is_essential_found = EssentialsDBService.get(essential)
        if not is_essential_found:
            inserted_id = EssentialsDBService.add_essential(essential_model.to_json())
        else:
            inserted_id = is_essential_found['_id']
        return str(inserted_id)

    @staticmethod
    def get_essentials():
        essentials = EssentialsDBService.get_all()
        return essentials
