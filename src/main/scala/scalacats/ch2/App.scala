package scalacats.ch2

import cats.implicits._

object App {

  def main(args: Array[String]): Unit = {

    import Adder._

    println(add(List(1, 2, 3)))

    println(add(List(1.some, 2.some, 3.some, none[Int])))

    println(add(List(
      Order(1.1, 2.2),
      Order(3.3, 4.5),
      Order(5.5, 6.6)
    )))

  }

}
