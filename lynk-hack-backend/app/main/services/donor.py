from app.main.dtos.donor import Donor
from app.main.dtos.volunteer import Volunteer
from app.main.mongo.donate import DonateService
from app.main.mongo.donor import DonarsService as DonarsDBService
from app.main.mongo.essentials import EssentialsService
from app.main.services.issues import IssuesService


class DonorsService:
    @staticmethod
    def add_new_donor(donor):
        donor = Donor(donor)
        is_donor_found = DonarsDBService.get_by_phone_no(donor.phone_no)
        if not is_donor_found:
            donor_id = DonarsDBService.add_donor(donor.to_json())
        else:
            donor_id = is_donor_found['id']
        return str(donor_id)

    @staticmethod
    def get_all_donors():
        donors = DonarsDBService.get_all()
        return donors

    @staticmethod
    def get_donor(donor_id):
        volunteer = DonarsDBService.get_by_id(donor_id)
        return volunteer

    @staticmethod
    def get_donor_recommendations(issue_id):
        essentials = IssuesService.get_essentials(issue_id)
        essential_ids = [essential['id'] for essential in essentials]
        donors = DonateService.get_donor_recommendations(essential_ids)
        donor_ids = [donor['donatedBy'] for donor in donors]
        donors = list()
        for donor_id in donor_ids:
            donors.append(DonarsDBService.get_by_id(donor_id))
        return donors

    @staticmethod
    def get_essential_requirements():
        issues = IssuesService.get_all_issues()
        essentials_count_maps = [
            {"essentials": issue.get('essentials', []), 'noOfPeople': issue.get('noOfPeople', 1)}
            for issue in issues
        ]

        essentials = {}
        for essentials_count_map in essentials_count_maps:
            for essential in essentials_count_map['essentials']:
                if not essentials.get(essential):
                    essentials[essential] = int(essentials_count_map["noOfPeople"])
                else:
                    essentials[essential] += int(essentials_count_map["noOfPeople"])

        essential_list = list()
        for essential_id, quantity in essentials.items():
            ess = EssentialsService.get_by_id(essential_id)
            ess['quantity'] = quantity
            essential_list.append(ess)

        return essential_list
