package routes

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.HttpMethods.{GET, OPTIONS, POST, PATCH, PUT}
import akka.http.scaladsl.model.headers.{HttpOriginRange, `Access-Control-Allow-Headers`, `Access-Control-Allow-Methods`, `Access-Control-Allow-Origin`}
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives.{complete, _}
import akka.http.scaladsl.server.{Directive0, Route}
import service._

import scala.concurrent.ExecutionContext
import scala.util.Success

trait CorsSupport {
  lazy val allowedOrigin = HttpOriginRange.*

  //this directive adds access control headers to normal responses
  private def addAccessControlHeaders: Directive0 = {
    respondWithHeaders(
      `Access-Control-Allow-Origin`(allowedOrigin),
      `Access-Control-Allow-Headers`("Accept", "Content-Type", "X-Requested-With", "Access-Control-Allow-Origin")
    )
  }

  //this handles preflight OPTIONS requests.
  private def preflightRequestHandler: Route = options {
    complete(HttpResponse(StatusCodes.OK).withHeaders(`Access-Control-Allow-Methods`(OPTIONS, POST, GET, PATCH, PUT)))
  }

  def corsHandler(r: Route) = addAccessControlHeaders {
    preflightRequestHandler ~ r
  }
}

trait CatamaranRoutes extends SprayJsonSupport with ResponseFormats {
  implicit val ec: ExecutionContext

  def catamaranService: CatamaranService

  def catamaranRoutes = path("issue") {
    post {
      entity(as[TicketInput]) { ticketInput =>
        onSuccess(catamaranService.createTicket(ticketInput)) {
          case TicketDuplicateFound(msg) => complete((StatusCodes.Conflict, msg))
          case TicketInsertSuccess(msg) => complete((StatusCodes.OK, msg))
        }
      }
    } ~ patch {
      entity(as[TicketPatch]) {
        ticketPatch => onSuccess(catamaranService.updateTicket(ticketPatch)) {
          case VolunteerNotFound(msg) => complete((StatusCodes.NotFound, msg))
          case TicketUpdateSuccess => complete((StatusCodes.OK))
        }
      }
    }
  } ~ path("register") {
    post {
      entity(as[VolunteerInfo]) { volunteerInfo =>
        complete(catamaranService.registerUser(volunteerInfo))
      }
    }
  } ~ path("show" / Segment) { issueId =>
    get {
      onSuccess(catamaranService.getTicket(issueId)) {
        case TicketNotFound(msg) => complete((StatusCodes.NotFound, msg))
        case TicketFound(ticket) => complete((StatusCodes.Found, ticket))
        case TicketFetchError(msg) => complete((StatusCodes.BadRequest, msg))
      }

    }
  } ~ path("login") {
    post {
      entity(as[UserInfo]) { userInfo =>
        onComplete(catamaranService.validateUser(userInfo)) {
          case Success(Some(volunteer)) => complete(StatusCodes.OK, volunteer)
          case Success(None) => complete(StatusCodes.Unauthorized)
          case _ => complete(StatusCodes.InternalServerError)
        }
      }
    }
  } ~ path("list" / "issues") {
    get {
      parameters('status.?, 'query.?) { (statusOpt, queryOpt) =>
         onComplete(catamaranService.listTickets(statusOpt, queryOpt)) {
           case Success(tickets) => complete(StatusCodes.OK, tickets)
           case _ => complete(StatusCodes.BadRequest)
         }
      }
    }
  }

}
