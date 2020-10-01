from app.main.dtos.volunteer import Volunteer
from app.main.mongo.issues import IssuesService as IssuesDBService
from app.main.mongo.issues_acknowledgements import IssuesAcknowledgementsService
from app.main.mongo.volunteers import VolunteersService as VolunteersDBService
from app.main.services.issues import IssuesService


class VolunteersService:
    @staticmethod
    def add_new_volunteer(volunteer):
        volunteer = Volunteer(volunteer)
        is_volunteer_found = VolunteersDBService.get_volunteer_id_by_phone_no(volunteer.phone_no)
        if not is_volunteer_found:
            volunteer_id = VolunteersDBService.add_volunteer(volunteer.to_json())
        else:
            volunteer_id = is_volunteer_found['id']
        return str(volunteer_id)

    @staticmethod
    def get_all_volunteers():
        volunteers = VolunteersDBService.get_all()
        return volunteers

    @staticmethod
    def get_volunteer(volunteer_id):
        volunteer = VolunteersDBService.get_by_id(volunteer_id)
        return volunteer

    @staticmethod
    def get_acknowledged_issues(volunteer_id):
        acknowledgements = IssuesAcknowledgementsService.get_all_acknowledgements_for_the_volunteer(volunteer_id)
        acknowledged_issue_ids = [acknowledgement['issue_id'] for acknowledgement in acknowledgements]
        issues = IssuesService.get_all_issues()
        issues = [issue for issue in issues if issue['id'] in acknowledged_issue_ids]
        return issues

    @staticmethod
    def get_non_acknowledged_issues(volunteer_id):
        acknowledgements = IssuesAcknowledgementsService.get_all_acknowledgements_for_the_volunteer(volunteer_id)
        acknowledged_issue_ids = [acknowledgement['issue_id'] for acknowledgement in acknowledgements]
        issues = IssuesService.get_all_issues()
        issues = [issue for issue in issues if issue['id'] not in acknowledged_issue_ids]
        return issues
