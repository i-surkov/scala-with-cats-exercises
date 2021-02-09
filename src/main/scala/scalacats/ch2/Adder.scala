package scalacats.ch2

import cats.{Order => _, _}
import cats.implicits._

object Adder {

  def reduceList[A: Monoid](items: List[A]): A = items.foldMap(identity)

  def add(items: List[Int]): Int = reduceList(items)

  def add(items: List[Option[Int]]): Option[Int] = reduceList(items)

  def add(items: List[Order]): Order = reduceList(items)

}
