package dao

import models.{AssignedTicket, AssignedTicketSchema, User, UserSchema}
import sql.SqlDatabase

case class AssignedTicketDao(database: SqlDatabase) extends AssignedTicketSchema {
  import database.driver.api._

  def assignUser(assignedTicket: AssignedTicket) = {
    database.db.run(assignedTickets += assignedTicket)
  }
}