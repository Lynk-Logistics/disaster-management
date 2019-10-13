package models

import java.util.UUID

import service.VolunteerInfo
import slick.lifted
import sql.SqlDatabase

case class Volunteer(id: UUID, firstName: String, lastName: String, gender: String, email: String, phone: String)

object Volunteer {
  def apply(volunteerInfo: VolunteerInfo): Volunteer = {
    Volunteer(UUID.randomUUID(), volunteerInfo.firstName, volunteerInfo.lastName, volunteerInfo.gender, volunteerInfo.email, volunteerInfo.phone)
  }
}


trait VolunteerSchema {
  protected val database: SqlDatabase

  import database.driver.api._

  val volunteers = lifted.TableQuery[Volunteers]

  protected class Volunteers(tag: Tag) extends Table[Volunteer](tag, "volunteers") {
    def id = column[UUID]("id", O.PrimaryKey)

    def firstName = column[String]("first_name")

    def lastName = column[String]("last_name")

    def gender = column[String]("gender")

    def email = column[String]("email")

    def phone = column[String]("phone_number")

    val volunteerApplyFn:(UUID, String, String, String, String, String) => Volunteer = Volunteer.apply

    def * = (id, firstName, lastName, gender, email, phone) <>
      (volunteerApplyFn.tupled, Volunteer.unapply)
  }

}