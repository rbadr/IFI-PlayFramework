package models

import java.util.Date
import javax.inject.Inject

import anorm.SqlParser._
import anorm._
import play.api.db.DBApi

case class Student(id: Option[Long] = None,
                    name: String,
                    birthdate: Option[Date],
                    entrydate: Option[Date],
                    programId: Option[Long])

/**
 * Helper for pagination.
 */
case class Page[A](items: Seq[A], page: Int, offset: Long, total: Long) {
  lazy val prev = Option(page - 1).filter(_ >= 0)
  lazy val next = Option(page + 1).filter(_ => (offset + items.size) < total)
}


@javax.inject.Singleton
class StudentService @Inject() (dbapi: DBApi, programService: ProgramService) {

  private val db = dbapi.database("default")

  // -- Parsers

  /**
   * Parse a student from a ResultSet
   */
  val simple = {
    get[Option[Long]]("student.id") ~
      get[String]("student.name") ~
      get[Option[Date]]("student.birthdate") ~
      get[Option[Date]]("student.entrydate") ~
      get[Option[Long]]("student.program_id") map {
      case id~name~birthdate~entrydate~programId =>
        Student(id, name, birthdate, entrydate, programId)
    }
  }

  /**
   * Parse a (Student,Program) from a ResultSet
   */
  val withProgram = simple ~ (programService.simple ?) map {
    case student~program => (student,program)
  }

  // -- Queries

  /**
   * Retrieve a student from the id.
   */
  def findById(id: Long): Option[Student] = {
    db.withConnection { implicit connection =>
      SQL("select * from student where id = {id}").on('id -> id).as(simple.singleOpt)
    }
  }

  /**
   * Return a page of (Student,Program).
   *
   * @param page Page to display
   * @param pageSize Number of students per page
   * @param orderBy Student property used for sorting
   * @param filter Filter applied on the name column
   */
  def list(page: Int = 0, pageSize: Int = 10, orderBy: Int = 1, filter: String = "%"): Page[(Student, Option[Program])] = {

    val offest = pageSize * page

    db.withConnection { implicit connection =>

      val students = SQL(
        """
          select * from student
          left join program on student.program_id = program.id
          where student.name like {filter}
          order by {orderBy} nulls last
          limit {pageSize} offset {offset}
        """
      ).on(
        'pageSize -> pageSize,
        'offset -> offest,
        'filter -> filter,
        'orderBy -> orderBy
      ).as(withProgram *)

      val totalRows = SQL(
        """
          select count(*) from student
          left join program on student.program_id = program.id
          where student.name like {filter}
        """
      ).on(
        'filter -> filter
      ).as(scalar[Long].single)

      Page(students, page, offest, totalRows)

    }

  }

  /**
   * Update a student.
   *
   * @param id The student id
   * @param student The student values.
   */
  def update(id: Long, student: Student) = {
    db.withConnection { implicit connection =>
      SQL(
        """
          update student
          set name = {name}, birthdate = {birthdate}, entrydate = {entrydate}, program_id = {program_id}
          where id = {id}
        """
      ).on(
        'id -> id,
        'name -> student.name,
        'birthdate -> student.birthdate,
        'entrydate -> student.entrydate,
        'program_id -> student.programId
      ).executeUpdate()
    }
  }

  /**
   * Insert a new student.
   *
   * @param student The student values.
   */
  def insert(student: Student) = {
    db.withConnection { implicit connection =>
      SQL(
        """
          insert into student values (
            (select next value for student_seq),
            {name}, {birthdate}, {entrydate}, {program_id}
          )
        """
      ).on(
        'name -> student.name,
        'birthdate -> student.birthdate,
        'entrydate -> student.entrydate,
        'program_id -> student.programId
      ).executeUpdate()
    }
  }

  /**
   * Delete a student.
   *
   * @param id Id of the student to delete.
   */
  def delete(id: Long) = {
    db.withConnection { implicit connection =>
      SQL("delete from student where id = {id}").on('id -> id).executeUpdate()
    }
  }

}
