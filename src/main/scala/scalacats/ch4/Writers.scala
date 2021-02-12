package scalacats.ch4

import cats._
import cats.data._
import cats.implicits._

object Writers {

  def slowly[A](body: => A): A =
    try body
    finally Thread.sleep(100)

  def factorial(n: Int): Int = {
    val ans = slowly(if (n == 0) 1 else n * factorial(n - 1))
    println(s"fact $n $ans")
    ans
  }

  type Logged[A] = Writer[Vector[String], A]

  def writerFactorial(n: Int): Logged[Int] = for {
    ans <- slowly(if (n == 0) 1.pure[Logged] else writerFactorial(n - 1).map(_ * n))
    _   <- Vector(s"fact $n $ans").tell
  } yield ans

}
