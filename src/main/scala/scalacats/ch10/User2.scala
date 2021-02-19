package scalacats.ch10

import cats.data._
import cats.implicits._

object User2 {

  import Predicate._

  type Result[A] = Either[Errors, A]

  type Check[A, B] = Kleisli[Result, A, B]

  def check[A, B](func: A => Result[B]): Check[A, B] = Kleisli(func)

  def checkPred[A](pred: Predicate[Errors, A]): Check[A, A] = Kleisli[Result, A, A](pred.run)

  final case class User(username: String, email: String)

  val checkUsername: Check[String, String] = checkPred(longerThan(4) and alphanumeric)

  val splitEmail: Check[String, (String, String)] = check(_.split('@') match {
    case Array(name, domain) =>
      (name, domain).rightNel
    case _ =>
      "Must contain a single @ character".leftNel[(String, String)]
  })

  val checkLeft: Check[String, String] =
    checkPred(longerThan(0))

  val checkRight: Check[String, String] =
    checkPred(longerThan(3) and contains('.'))

  val joinEmail: Check[(String, String), String] = check { case (l, r) =>
    (checkLeft(l), checkRight(r)).mapN(_ + "@" + _)
  }

  val checkEmail: Check[String, String] =
    splitEmail andThen joinEmail

  def createUser(username: String, email: String): Result[User] =
    (checkUsername(username), checkEmail(email)).mapN(User)

}
