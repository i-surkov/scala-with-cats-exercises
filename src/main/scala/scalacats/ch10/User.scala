package scalacats.ch10

import cats.data._
import cats.implicits._

object User {

  import Predicate._

  final case class User(username: String, email: String)

  val checkUsername: Check[Errors, String, String] = Check(longerThan(4) and alphanumeric)

  val splitEmail: Check[Errors, String, (String, String)] = Check(_.split('@') match {
    case Array(name, domain) =>
      (name, domain).validNel[String]
    case _ =>
      "Must contain a single @ character".invalidNel[(String, String)]
  })

  val checkLeft: Check[Errors, String, String] =
    Check(longerThan(0))

  val checkRight: Check[Errors, String, String] =
    Check(longerThan(3) and contains('.'))

  val joinEmail: Check[Errors, (String, String), String] =
    Check[Errors, (String, String), String] { case (l, r) =>
      (checkLeft(l), checkRight(r)).mapN(_ + "@" + _)
    }

  val checkEmail: Check[Errors, String, String] =
    splitEmail andThen joinEmail

  def createUser(username: String, email: String): Validated[Errors, User] =
    (checkUsername(username), checkEmail(email)).mapN(User)

}
