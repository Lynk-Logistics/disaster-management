from app.main.mongo.issue_reports import IssueReportsService as IssueReportsDBService
from app.main.mongo.reporters import ReportersService


class IssueReportsService:
    @staticmethod
    def report(**kwargs):
        issue_id = kwargs['issue_id']
        phone_no = kwargs.get('phone_no')
        name = kwargs.get('name')
        reporter_id = None
        if phone_no:
            reporter = ReportersService.get_by_phone_no(phone_no)
            if reporter:
                reporter_id = reporter['id']
            else:
                reporter_id = ReportersService.add_reporter({'phoneNo': phone_no, "name": name})
                reporter_id = str(reporter_id)
        if not reporter_id:
            reporter_id = kwargs.get('reporter_id')
        if not reporter_id:
            plus_one_id = IssueReportsDBService.add_report(
                {"reporter_id": reporter_id, "issue_id": issue_id})
            return str(plus_one_id)
        is_report_present = IssueReportsDBService.is_report_present(issue_id, reporter_id)
        if not is_report_present:
            plus_one_id = IssueReportsDBService.add_report(
                {"reporter_id": reporter_id, "issue_id": issue_id})
            return str(plus_one_id)
        else:
            return is_report_present['id']
