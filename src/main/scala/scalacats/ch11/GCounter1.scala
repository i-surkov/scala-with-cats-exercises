package scalacats.ch11

import cats.kernel._
import cats.syntax.monoid._

final case class GCounter1(counters: Map[String, Int]) {

  def increment(machine: String, amount: Int): GCounter1 =
    GCounter1(counters |+| Map(machine -> amount))

  def merge(that: GCounter1): GCounter1 = {
    implicit val s: CommutativeSemigroup[Int] = CommutativeSemigroup.instance[Int](_ max _)
    GCounter1(counters |+| that.counters)
  }

  def total: Int = counters.values.sum

}
