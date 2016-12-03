package models

import javax.inject.Inject

import anorm.SqlParser._
import anorm._

import play.api.db.DBApi

case class Program(id: Option[Long] = None, name: String)

@javax.inject.Singleton
class ProgramService @Inject() (dbapi: DBApi) {

  private val db = dbapi.database("default")

  /**
   * Parse a Program from a ResultSet
   */
  val simple = {
    get[Option[Long]]("program.id") ~
      get[String]("program.name") map {
      case id~name => Program(id, name)
    }
  }

  /**
   * Construct the Map[String,String] needed to fill a select options set.
   */
  def options: Seq[(String,String)] = db.withConnection { implicit connection =>
    SQL("select * from program order by name").as(simple *).
      foldLeft[Seq[(String, String)]](Nil) { (cs, c) =>
      c.id.fold(cs) { id => cs :+ (id.toString -> c.name) }
    }
  }

}
