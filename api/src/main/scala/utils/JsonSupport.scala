package utils

import java.sql.Timestamp
import java.util.UUID

import org.joda.time.format.ISODateTimeFormat
import org.joda.time.{DateTime, DateTimeZone}
import spray.json.{DefaultJsonProtocol, DeserializationException, JsString, JsValue, JsonFormat, RootJsonFormat}

trait JsonSupport extends DefaultJsonProtocol{

  implicit def uuidFormat = new JsonFormat[UUID] {
    def write(uuid: UUID) = JsString(uuid.toString)

    def read(value: JsValue) = value match {
      case JsString(x) => UUID.fromString(x)
      case _ => throw new DeserializationException("String expected")
    }
  }
  implicit object TimestampFormat extends RootJsonFormat[Timestamp] {
    val formatter = ISODateTimeFormat.dateTime.withZone(DateTimeZone.UTC)


    def write(obj: Timestamp): JsValue = {
      JsString(formatter.print(new DateTime(obj)))
    }

    def read(json: JsValue): Timestamp = json match {
      case JsString(s) => try {
        new Timestamp(s.toLong)
      } catch {
        case t: Throwable => error(s)
      }
      case _ =>
        error(json.toString())
    }

    def error(v: Any): Timestamp = {
      throw new DeserializationException("String expected")
    }
  }
}
