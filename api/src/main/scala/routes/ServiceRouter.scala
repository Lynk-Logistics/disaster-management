package routes

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer

class ServiceRouter(catamaranRoutes: Route) extends CorsSupport with HealthRoute {
  implicit val system = ActorSystem("catamaran-api")
  implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer()

  val routes = healthRoutes ~ catamaranRoutes

  def bind(host: String, port: Int) = {
    Http().bindAndHandle(Route.handlerFlow(corsHandler(routes)), host, port)
  }
}
