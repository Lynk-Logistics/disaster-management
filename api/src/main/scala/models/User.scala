package models

import java.util.UUID

import slick.lifted
import sql.SqlDatabase

case class User(volunteerId: UUID, email: String, password: String)

trait UserSchema {
  protected val database: SqlDatabase

  import database.driver.api._

  val users = lifted.TableQuery[Users]

  protected class Users(tag: Tag) extends Table[User](tag, "users") {
    def volunteerId = column[UUID]("volunteer_id", O.PrimaryKey)

    def email = column[String]("email")

    def password = column[String]("password")

    def * = (volunteerId, email, password) <>
      ((User.apply _).tupled, User.unapply)
  }
}
