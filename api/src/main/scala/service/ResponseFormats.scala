package service

import models.{Ticket, Volunteer}
import spray.json.DefaultJsonProtocol
import utils.JsonSupport

case class VolunteerInfo(firstName: String, lastName: String, gender: String, email: String, password: String, phone: String)

case class TicketInput(issueType: String, message: String, address: String, phone: String)
case class UserInfo(email: String, password: String)

case class TicketPatch(id: String, status: String, volunteer: Option[VolunteerInfo])

trait ResponseFormats extends DefaultJsonProtocol with JsonSupport {

  implicit val volunteerInfoJsonFormat = jsonFormat6(VolunteerInfo.apply)
  implicit val volunteerJsonFormat = jsonFormat6(Volunteer.apply)
  implicit val ticketInputFormat = jsonFormat4(TicketInput.apply)
  implicit val ticketFormat = jsonFormat9(Ticket.apply)
  implicit val userInfoJsonFormat = jsonFormat2(UserInfo.apply)
  implicit val ticketPatchFormat = jsonFormat3(TicketPatch.apply)
}