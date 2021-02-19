package scalacats.ch10

import cats._
import cats.data.Validated._
import cats.data._
import cats.implicits._

sealed trait Predicate[E, A] {

  import Predicate._

  def run(implicit s: Semigroup[E]): A => Either[E, A] = this(_).toEither

  def and(that: Predicate[E, A]): Predicate[E, A] =
    And(this, that)

  def or(that: Predicate[E, A]): Predicate[E, A] =
    Or(this, that)

  def apply(a: A)(implicit s: Semigroup[E]): Validated[E, A] = this match {
    case Pure(func) =>
      func(a)
    case And(left, right) =>
      (left(a), right(a)).mapN((_, _) => a)
    case Or(left, right) =>
      left(a) match {
        case Valid(_) => Valid(a)
        case Invalid(e1) =>
          right(a) match {
            case Valid(_)    => Valid(a)
            case Invalid(e2) => Invalid(e1 |+| e2)
          }
      }
  }
}

object Predicate {

  final case class And[E, A](left: Predicate[E, A], right: Predicate[E, A]) extends Predicate[E, A]

  final case class Or[E, A](left: Predicate[E, A], right: Predicate[E, A]) extends Predicate[E, A]

  final case class Pure[E, A](func: A => Validated[E, A]) extends Predicate[E, A]

  def apply[E, A](func: A => Validated[E, A]): Predicate[E, A] = Pure(func)

  def lift[E, A](e: => E, p: A => Boolean): Predicate[E, A] =
    Predicate(a => Validated.cond(p(a), a, e))

  def liftNel[E, A](e: => E, p: A => Boolean): Predicate[NonEmptyList[E], A] =
    Predicate(a => Validated.condNel(p(a), a, e))

  // Utils

  type Errors = NonEmptyList[String]

  def error(s: String): NonEmptyList[String] =
    NonEmptyList(s, Nil)

  def longerThan(n: Int): Predicate[Errors, String] =
    Predicate.lift(
      error(s"Must be longer than $n characters"),
      str => str.length > n
    )

  val alphanumeric: Predicate[Errors, String] =
    Predicate.lift(
      error(s"Must be all alphanumeric characters"),
      str => str.forall(_.isLetterOrDigit)
    )

  def contains(char: Char): Predicate[Errors, String] = Predicate.lift(
    error(s"Must contain the character $char"),
    str => str.contains(char)
  )

  def containsOnce(char: Char): Predicate[Errors, String] = Predicate.lift(
    error(s"Must contain the character $char only once"),
    str => str.count(_ == char) == 1
  )

}
