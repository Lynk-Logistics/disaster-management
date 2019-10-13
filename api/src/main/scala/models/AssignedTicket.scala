package models

import java.util.UUID

import slick.lifted
import sql.SqlDatabase

case class AssignedTicket(ticketId: UUID, volunteerId: UUID)

trait AssignedTicketSchema {
  protected val database: SqlDatabase

  import database.driver.api._

  val assignedTickets = lifted.TableQuery[AssignedTickets]

  protected class AssignedTickets(tag: Tag) extends Table[AssignedTicket](tag, "assigned_tickets") {
    def ticketId = column[UUID]("ticket_id")

    def volunteerId = column[UUID]("volunteer_id")

    def * = (ticketId, volunteerId) <>
      ((AssignedTicket.apply _).tupled, AssignedTicket.unapply)
  }
}