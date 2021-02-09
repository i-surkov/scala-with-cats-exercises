package scalacats.ch2

import cats._
import cats.implicits._

case class Order(totalCost: Double, quantity: Double)

object Order {

  implicit val orderMonoid: Monoid[Order] = new Monoid[Order] {
    private val doubleMonoid = Monoid[Double]
    override def empty: Order = Order(doubleMonoid.empty, doubleMonoid.empty)
    override def combine(x: Order, y: Order): Order =
      Order(
        x.totalCost |+| y.totalCost,
        x.quantity |+| y.quantity
      )
  }

}
