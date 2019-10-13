package sql

import java.net.{Socket, URI}

import com.typesafe.config.ConfigValueFactory.fromAnyRef
import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.StrictLogging
import org.flywaydb.core.Flyway
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.JdbcProfile
import sql.DatabaseConfig._

case class SqlDatabase(
                        db: slick.jdbc.JdbcBackend.Database,
                        connectionString: JdbcConnectionString,
                        driver:           JdbcProfile

                      ) {

  def updateSchema() {
    val flyway = new Flyway()
    flyway.setOutOfOrder(true)
    flyway.setDataSource(connectionString.url, connectionString.username, connectionString.password)
    flyway.migrate()
  }

  def close() {
    db.close()
  }
}

case class JdbcConnectionString(url: String, username: String = "", password: String = "")

object SqlDatabase extends StrictLogging {
  def create(config: DatabaseConfig): SqlDatabase = {
    val envDatabaseUrl = System.getenv("DATABASE_URL")

    if (config.dbPostgresServerName.length > 0)
      createPostgresFromConfig(config)
    else if (envDatabaseUrl != null)
      createPostgresFromEnv(envDatabaseUrl)
    else
      throw new RuntimeException("Invalid or missing DB configurations")
  }

  def createPostgresFromEnv(envDatabaseUrl: String) = {
    val dbUri = new URI(envDatabaseUrl)
    val username = dbUri.getUserInfo.split(":")(0)
    val password = dbUri.getUserInfo.split(":")(1)
    val intermediaryConfig = new DatabaseConfig {
      override def rootConfig: Config =
        ConfigFactory
          .empty()
          .withValue(PostgresDSClass, fromAnyRef("org.postgresql.ds.PGSimpleDataSource"))
          .withValue(PostgresServerNameKey, fromAnyRef(dbUri.getHost))
          .withValue(PostgresPortKey, fromAnyRef(dbUri.getPort))
          .withValue(PostgresDbNameKey, fromAnyRef(dbUri.getPath.tail))
          .withValue(PostgresUsernameKey, fromAnyRef(username))
          .withValue(PostgresPasswordKey, fromAnyRef(password))
          .withFallback(ConfigFactory.load())
    }
    createPostgresFromConfig(intermediaryConfig)
  }

  def postgresUrl(host: String, port: Int, dbName: String) =
    s"jdbc:postgresql://$host:$port/$dbName"

  def postgresConnectionString(config: DatabaseConfig) = {
    val host = config.dbPostgresServerName
    val port = config.dbPostgresPort
    val dbName = config.dbPostgresDbName
    val username = config.dbPostgresUsername
    val password = config.dbPostgresPassword
    JdbcConnectionString(postgresUrl(host, port, dbName), username, password)
  }

  def createPostgresFromConfig(config: DatabaseConfig) = {
    waitForDatabaseToStartUp(config)
    val db = Database.forConfig("db.postgres", config.rootConfig)
    SqlDatabase(db, postgresConnectionString(config), slick.jdbc.PostgresProfile)
  }

  private def waitForDatabaseToStartUp(config: DatabaseConfig): Unit = {
    def isSocketOpen(host: String, port: Int) = {
      try {
        new Socket(host, port).close()
        true
      } catch {
        case _: Throwable => false
      }
    }

    while (!isSocketOpen(config.dbPostgresServerName, config.dbPostgresPort)) {
      logger.warn(
        s"Cannot connect to DB at ${config.dbPostgresServerName}:${config.dbPostgresPort}. Retrying in 10 seconds"
      )
      Thread.sleep(10000)
    }
  }
}
