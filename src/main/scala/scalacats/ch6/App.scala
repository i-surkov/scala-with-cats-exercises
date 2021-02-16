package scalacats.ch6

import cats._
import cats.implicits._

object App {

  def main(args: Array[String]): Unit = {

    def product[F[_]: Monad, A, B](x: F[A], y: F[B]): F[(A, B)] =
      x.flatMap(a => y.map(b => (a, b)))

    val myProduct: List[(Int, Int)] = product(List(1, 2), List(3, 4))
    val cartesian: List[(Int, Int)] = (List(1, 2), List(3, 4)).tupled
    println(myProduct === cartesian)
    println(cartesian)

    val zipped: List[(Int, Int)] =
      (List(1, 2), List(3, 4)).parTupled
    println(zipped)

  }

}
