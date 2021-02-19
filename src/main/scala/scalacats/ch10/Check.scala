package scalacats.ch10

import cats._
import cats.data._

sealed trait Check[E, A, B] {

  import Check._

  def apply(a: A)(implicit s: Semigroup[E]): Validated[E, B]

  def map[C](func: B => C): Check[E, A, C] =
    Map(this, func)

  def flatMap[C](func: B => Check[E, A, C]): Check[E, A, C] =
    FlatMap(this, func)

  def andThen[C](that: Check[E, B, C]): Check[E, A, C] =
    AndThen(this, that)

}

object Check {

  final case class PurePred[E, A](predicate: Predicate[E, A]) extends Check[E, A, A] {
    override def apply(a: A)(implicit s: Semigroup[E]): Validated[E, A] =
      predicate(a)
  }

  final case class Pure[E, A, B](func: A => Validated[E, B]) extends Check[E, A, B] {
    def apply(a: A)(implicit s: Semigroup[E]): Validated[E, B] =
      func(a)
  }

  final case class Map[E, A, B, C](check: Check[E, A, B], f: B => C) extends Check[E, A, C] {
    override def apply(a: A)(implicit s: Semigroup[E]): Validated[E, C] =
      check(a).map(f)
  }

  final case class FlatMap[E, A, B, C](check: Check[E, A, B], f: B => Check[E, A, C])
      extends Check[E, A, C] {
    override def apply(a: A)(implicit s: Semigroup[E]): Validated[E, C] =
      check(a).withEither(_.flatMap(b => f(b)(a).toEither))
  }

  final case class AndThen[E, A, B, C](check: Check[E, A, B], that: Check[E, B, C])
      extends Check[E, A, C] {
    override def apply(a: A)(implicit s: Semigroup[E]): Validated[E, C] =
      check(a).withEither(_.flatMap(b => that(b).toEither))
  }

  def apply[E, A](predicate: Predicate[E, A]): Check[E, A, A] = PurePred(predicate)

  def apply[E, A, B](func: A => Validated[E, B]): Check[E, A, B] =
    Pure(func)

}
