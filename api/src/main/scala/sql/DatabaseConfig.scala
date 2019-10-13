package sql

import DatabaseConfig._
import com.typesafe.config.Config

trait DatabaseConfig {
  lazy val dbPostgresServerName = if(rootConfig.hasPath(PostgresServerNameKey)) { rootConfig.getString(PostgresServerNameKey) } else { "" }
  lazy val dbPostgresPort       = rootConfig.getInt(PostgresPortKey)
  lazy val dbPostgresDbName     = rootConfig.getString(PostgresDbNameKey)
  lazy val dbPostgresUsername   = rootConfig.getString(PostgresUsernameKey)
  lazy val dbPostgresPassword   = rootConfig.getString(PostgresPasswordKey)

  lazy val jdbcUrl = s"jdbc:postgresql://${dbPostgresServerName}:${dbPostgresPort}/${dbPostgresDbName}?user=${dbPostgresUsername}&password=${dbPostgresPassword}"

  def rootConfig: Config
}

object DatabaseConfig {
  val PostgresDSClass       = "db.postgres.dataSourceClass"
  val PostgresServerNameKey = "db.postgres.properties.serverName"
  val PostgresPortKey       = "db.postgres.properties.portNumber"
  val PostgresDbNameKey     = "db.postgres.properties.databaseName"
  val PostgresUsernameKey   = "db.postgres.properties.user"
  val PostgresPasswordKey   = "db.postgres.properties.password"
}
