package models

import java.sql.Timestamp
import java.util.UUID

import org.joda.time.DateTime
import service.TicketInput
import slick.lifted
import sql.SqlDatabase

case class Ticket(id: UUID, issueType: String, message: String, status: String, address: String, phone: String,
                  createdDate: Timestamp, dispatchedDate: Option[Timestamp], resolvedDate: Option[Timestamp])

object Ticket {
  def withRandomUUID(issueType: String, message: String, status: String, address: String, phone: String): Ticket = {
    Ticket(UUID.randomUUID(), issueType, message, status, address, phone, new Timestamp(DateTime.now().getMillis), None, None)
  }

  def fromTicketInput(ticketInput: TicketInput, status: String): Ticket = {
    withRandomUUID(ticketInput.issueType, ticketInput.message, status, ticketInput.address, ticketInput.phone)
  }
}

trait TicketSchema {
  protected val database: SqlDatabase

  import database.driver.api._

  val tickets = lifted.TableQuery[Tickets]

  protected class Tickets(tag: Tag) extends Table[Ticket](tag, "tickets") {
    def id = column[UUID]("id", O.PrimaryKey)

    def issueType = column[String]("issue_type")

    def message = column[String]("message")

    def status = column[String]("status")

    def address = column[String]("address")

    def phone = column[String]("phone_number")

    def createdDate = column[Timestamp]("created_timestamp")

    def dispatchedDate = column[Option[Timestamp]]("dispatched_timestamp")

    def resolvedDate = column[Option[Timestamp]]("resolved_timestamp")


    def * = (id, issueType, message, status, address, phone, createdDate, dispatchedDate, resolvedDate) <>
      ((Ticket.apply _).tupled, Ticket.unapply)
  }

}