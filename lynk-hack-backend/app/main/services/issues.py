from app.main.dtos.coordinate import Coordinate
from app.main.dtos.issue import Issue
from app.main.mongo.essentials import EssentialsService
from app.main.mongo.issue_reports import IssueReportsService
from app.main.mongo.issues import IssuesService as IssuesDBService
from app.main.mongo.issues_acknowledgements import IssuesAcknowledgementsService
from app.main.mongo.issues_plus_one import IssuesPlusOneService
from app.main.mongo.reporters import ReportersService
from app.main.mongo.victims import VictimsService
from app.main.mongo.volunteers import VolunteersService


class IssuesService:
    @staticmethod
    def add_new_issue(issue):
        issue_dto = Issue(issue)
        issue_id = IssuesDBService.add_issue(issue_dto.to_json())
        return str(issue_id)

    @staticmethod
    def get_all_issues(coordinate=None):
        if not coordinate:
            issues = IssuesDBService.get_all()
        else:
            issues = IssuesService.get_issues_based_on_location(coordinate)
        for issue in issues:
            issue_id = issue['id']
            issue['acknowledgedVolunteers'] = IssuesService._get_volunteers_for_the_issue(issue_id)
            issue['plusOnes'] = IssuesService._get_plus_ones_for_the_issue(issue_id)
            issue['reports'] = IssuesService._get_reports_for_the_issue(issue_id)
        return issues

    @staticmethod
    def get_issue_by_id(issue_id):
        issue = IssuesDBService.get_by_id(issue_id)
        essentials = list()
        for essential_id in issue.get('essentials', []):
            essentials.append(EssentialsService.get_by_id(essential_id))
        issue['essentials'] = essentials
        issue['acknowledgedVolunteers'] = IssuesService._get_volunteers_for_the_issue(issue_id)
        issue['plusOnes'] = IssuesService._get_plus_ones_for_the_issue(issue_id)
        issue['reports'] = IssuesService._get_reports_for_the_issue(issue_id)
        return issue

    @staticmethod
    def get_essentials(issue_id):
        issue = IssuesDBService.get_by_id(issue_id)
        essentials = list()
        for essential_id in issue.get('essentials', []):
            essentials.append(EssentialsService.get_by_id(essential_id))
        return essentials

    @staticmethod
    def _get_reports_for_the_issue(issue_id):
        reports = IssueReportsService.get_all_reports_for_the_issue(issue_id)
        total_reports = len(reports)
        verified_reporter_ids = [
            report.get('reporter_id')
            for report in reports
            if report.get('reporter_id')
        ]
        reporters = list()
        for reporter_id in verified_reporter_ids:
            reporters.append(ReportersService.get_by_id(reporter_id))
        report_obj = {
            "totalReports": total_reports,
            "anonymousReports": total_reports - len(reporters),
            "verifiedReports": len(reporters),
            "verifiedReportReporters": reporters,
        }
        return report_obj

    @staticmethod
    def _get_plus_ones_for_the_issue(issue_id):
        plus_one_victims = IssuesPlusOneService.get_all_plus_ones_for_the_issue(issue_id)
        total_plus_ones = len(plus_one_victims)
        verified_plus_one_victim_ids = [
            plus_one_victim.get('victim_id')
            for plus_one_victim in plus_one_victims
            if plus_one_victim.get('victim_id')
        ]
        victims = list()
        for victim_id in verified_plus_one_victim_ids:
            victims.append(VictimsService.get_by_id(victim_id))
        plus_ones = {
            "totalPlusOnes": total_plus_ones,
            "anonymousPlusOnes": total_plus_ones - len(victims),
            "verifiedPlusOnes": len(victims),
            "verifiedPlusOneVictims": victims,
        }
        return plus_ones

    @staticmethod
    def _get_volunteers_for_the_issue(issue_id):
        acknowledged_volunteers = IssuesAcknowledgementsService.get_all_acknowledgements_for_the_issue(issue_id)
        volunteer_ids = [acknowledged_volunteer['volunteer_id'] for acknowledged_volunteer in acknowledged_volunteers]
        volunteers_list = list()
        for volunteer_id in volunteer_ids:
            volunteers_list.append(VolunteersService.get_by_id(volunteer_id))
        volunteers = {
            'totalAcknowledgements': len(volunteers_list),
            'verifiedAcknowledgedVolunteers': volunteers_list
        }
        return volunteers

    @staticmethod
    def get_issues_based_on_location(coordinate):
        coordinate_dto = Coordinate(coordinate)
        issues = IssuesDBService.get_all_issues_based_on_coordinates(coordinate_dto)
        for issue in issues:
            issue_id = issue['id']
            issue['acknowledgedVolunteers'] = IssuesService._get_volunteers_for_the_issue(issue_id)
            issue['plusOnes'] = IssuesService._get_plus_ones_for_the_issue(issue_id)
            issue['reports'] = IssuesService._get_reports_for_the_issue(issue_id)
        return issues

    @staticmethod
    def get_clustors_of_issues(coordinate):
        coordinate = Coordinate(coordinate)
        clustors_of_issues = IssuesDBService.get_clusters(coordinate)
        return clustors_of_issues
