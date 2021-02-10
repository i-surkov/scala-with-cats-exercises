package scalacats.ch3

import cats.implicits._
import scalacats.ch3.Code._
import scalacats.ch3.Tree._
import scalacats.ch3.Printable._

object App {

  def main(args: Array[String]): Unit = {

    val tree = branch(
      branch(
        leaf(1),
        branch(
          leaf(2),
          leaf(3)
        )
      ),
      branch(
        branch(
          leaf(4),
          leaf(5)
        ),
        leaf(6)
      )
    )

    println(tree)
    println(tree.map(_ * 10))

    println(fmt("hello"))
    println(fmt(true))

    println(fmt(Box("hello world")))
    println(fmt(Box(true)))

    println(encode(123.4))
    println(decode[Double]("123.4"))
    println(encode(Box(123.4)))
    println(decode[Box[Double]]("123.4"))

  }
}
