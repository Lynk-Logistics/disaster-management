package service

import akka.stream.Materializer
import com.typesafe.config.Config
import scalaj.http._
import spray.json._

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

case class TwilioRequest(from: String, to: String, body: String)
trait TwilioJsonFormat extends DefaultJsonProtocol {
  implicit val twilioJsonFormat = jsonFormat3(TwilioRequest.apply)
}
object TwilioJsonFormat extends TwilioJsonFormat

case class TwilioClient(config: Config)(implicit val ec: ExecutionContext, val mat: Materializer) {

  def timeout: FiniteDuration = 120 seconds

  def sendMessage(request: TwilioRequest) = {
    val response = Http("https://api.twilio.com/2010-04-01/Accounts/ACa7c6316c3c162efdd614e685969ca6ab/Messages.json")
      .postForm(Seq("From" -> "whatsapp:+14155238886", "To" -> request.to, "Body" -> request.body))
      .auth("ACa7c6316c3c162efdd614e685969ca6ab", "3a9e41ce24b30c177cfad9780ce8f113")
      .asString
    println(response)
    Future.successful(response)
  }

}
