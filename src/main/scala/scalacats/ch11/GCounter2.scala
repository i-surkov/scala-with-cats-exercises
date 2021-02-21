package scalacats.ch11

import cats.kernel._
import cats.syntax.monoid._
import cats.syntax.foldable._

final case class GCounter2[A](counters: Map[String, A]) {

  def increment(machine: String, value: A)(implicit ev: Monoid[A]): GCounter2[A] =
    GCounter2(counters |+| Map(machine -> value))

  def merge(that: GCounter2[A])(implicit ev: BoundedSemiLattice[A]): GCounter2[A] =
    GCounter2(counters |+| that.counters)

  def total(implicit ev: CommutativeMonoid[A]): A = counters.values.toList.combineAll

}
