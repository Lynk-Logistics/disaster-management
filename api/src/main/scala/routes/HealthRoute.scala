package routes

import akka.http.scaladsl.model.StatusCodes.OK
import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.http.scaladsl.server.Directives._

trait HealthRoute {
  val healthRoutes = path( "health") {
    get {
      complete(OK)
    }
  }
}