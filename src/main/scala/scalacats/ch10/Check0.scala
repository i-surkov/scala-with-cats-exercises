package scalacats.ch10

import cats._
import cats.data._
import cats.implicits._

sealed trait Check0[E, A] {

  import Check0._

  def apply(value: A)(implicit sg: Semigroup[E]): Validated[E, A] = this match {
    case Pure(f)            => f(value)
    case And(first, second) => first(value) *> second(value)
    case Or(first, second)  => first(value) findValid second(value)
  }

  def and(that: Check0[E, A]): Check0[E, A] =
    And(this, that)

  def or(that: Check0[E, A]): Check0[E, A] =
    Or(this, that)

}

object Check0 {

  final case class Pure[E, A](f: A => Validated[E, A]) extends Check0[E, A]

  final case class And[E, A](first: Check0[E, A], second: Check0[E, A]) extends Check0[E, A]

  final case class Or[E, A](first: Check0[E, A], second: Check0[E, A]) extends Check0[E, A]

}
