from app.main.mongo.issues_plus_one import IssuesPlusOneService
from app.main.mongo.victims import VictimsService


class IssuesPlusOnesService:
    @staticmethod
    def plus_one(**kwargs):
        issue_id = kwargs['issue_id']
        phone_no = kwargs.get('phone_no')
        name = kwargs.get('name')
        victim_id = None
        if phone_no:
            victim = VictimsService.get_by_phone_no(phone_no)
            if victim:
                victim_id = victim['id']
            else:
                victim_id = VictimsService.add_victim({'phoneNo': phone_no, "name": name})
                victim_id = str(victim_id)
        if not victim_id:
            victim_id = kwargs.get('victim_id')
        if not victim_id:
            plus_one_id = IssuesPlusOneService.add_plus_one(
                {"victim_id": victim_id, "issue_id": issue_id})
            return str(plus_one_id)
        is_plus_one_present = IssuesPlusOneService.is_plus_one_present(issue_id, victim_id)
        if not is_plus_one_present:
            plus_one_id = IssuesPlusOneService.add_plus_one(
                {"victim_id": victim_id, "issue_id": issue_id})
            return str(plus_one_id)
        else:
            return is_plus_one_present['id']
