package routes

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.twitter.scalding.Args
import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.StrictLogging
import dao.{AssignedTicketDao, TicketDao, UserDao, VolunteerDao}
import service.{CatamaranService, TwilioClient}
import sql.{DatabaseConfig, SqlDatabase}

import scala.concurrent.ExecutionContext

object CatamaranApi extends App with StrictLogging with CatamaranRoutes {
  val cmdArgs = Args(args)
  val host = cmdArgs.optional("host").getOrElse("localhost")
  val port = cmdArgs.optional("port").map(_.toInt).getOrElse(4000)

  implicit lazy val system          = ActorSystem()
  implicit val ec: ExecutionContext = system.dispatcher
  implicit lazy val materializer    = ActorMaterializer()

  val databaseConfig = new DatabaseConfig {
    override def rootConfig: Config = ConfigFactory.load().getConfig("catamaran")
  }
  val twilioConfig = ConfigFactory.load().getConfig("catamaran")

  val sqlDatabase = SqlDatabase.create(databaseConfig)
  sqlDatabase.updateSchema()

  val catamaranDao = new TicketDao(sqlDatabase)
  val userDao = new UserDao(sqlDatabase)
  val volunteerDao = new VolunteerDao(sqlDatabase)
  val assignedTicketDao = new AssignedTicketDao(sqlDatabase)
  val twilioClient = TwilioClient(twilioConfig)
  val catamaranService = new CatamaranService(catamaranDao, userDao, volunteerDao, assignedTicketDao, twilioClient)

  val service = new ServiceRouter(catamaranRoutes)
  logger.info(s"Starting server on $host:$port")
  service.bind(host, port)
}
