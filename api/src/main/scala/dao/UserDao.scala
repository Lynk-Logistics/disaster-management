package dao

import java.util.UUID

import models.{User, UserSchema}
import service.UserInfo
import sql.SqlDatabase

import scala.concurrent.{ExecutionContext, Future}

case class UserDao(database: SqlDatabase)(implicit ec: ExecutionContext) extends UserSchema {
  import database.driver.api._

  def addUser(user: User) = {
    database.db.run(users += user)
  }

  def fetchUser(userInfo: UserInfo): Future[Option[UUID]] = {
    val query = users.filter(_.email === userInfo.email).filter(_.password === userInfo.password)
    database.db.run(query.result).map(_.headOption.map(_.volunteerId))
  }
}