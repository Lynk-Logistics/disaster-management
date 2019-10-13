from app.main.mongo.issues_acknowledgements import IssuesAcknowledgementsService as IssuesAcknowledgementsDBService
from app.main.mongo.volunteers import VolunteersService


class IssuesAcknowledgementsService:
    @staticmethod
    def acknowledge(**kwargs):
        issue_id = kwargs['issue_id']
        phone_no = kwargs.get('phone_no')
        volunteer_id = None
        if phone_no:
            volunteer = VolunteersService.get_volunteer_id_by_phone_no(phone_no)
            if volunteer:
                volunteer_id = volunteer['id']
            else:
                volunteer_id = VolunteersService.add_volunteer({'phoneNo': phone_no})
                volunteer_id = str(volunteer_id)
        if not volunteer_id:
            volunteer_id = kwargs.get('volunteer_id')
        is_acknowledgement_present = IssuesAcknowledgementsDBService.is_acknowledgement_present(issue_id, volunteer_id)
        if not is_acknowledgement_present:
            acknowledgement_id = IssuesAcknowledgementsDBService.add_acknowledgement(
                {"volunteer_id": volunteer_id, "issue_id": issue_id})
            return str(acknowledgement_id)
        else:
            return is_acknowledgement_present['id']
