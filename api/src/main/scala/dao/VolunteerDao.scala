package dao

import java.util.UUID

import models.{Volunteer, VolunteerSchema}
import sql.SqlDatabase
import scala.concurrent.{ExecutionContext, Future}

case class VolunteerDao(database: SqlDatabase)(implicit val ec: ExecutionContext) extends VolunteerSchema {

  import database._
  import database.driver.api._

  def addVolunteer(volunteer: Volunteer) = {
    db.run(volunteers += volunteer)
  }

  def findVolunteerBy(firstName: String, lastName: String, email: String, gender: String, phoneNo: String) = {
    db.run(volunteers.filter {
      v =>
        v.firstName === firstName &&
          v.lastName === lastName &&
          v.email === email &&
          v.gender === gender &&
          v.phone === phoneNo
    }.result).map(_.headOption)
  }

  def findVolunteer(id: UUID): Future[Option[Volunteer]] = {
    val query = volunteers.filter(_.id === id)
    database.db.run(query.result).map(_.headOption)
  }
}

