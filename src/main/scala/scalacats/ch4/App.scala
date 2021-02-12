package scalacats.ch4

import cats.implicits._
import scalacats.ch3.Tree._
import scalacats.ch4.Evals._
import scalacats.ch4.MonadError._
import scalacats.ch4.Readers._
import scalacats.ch4.States._
import scalacats.ch4.Trees._
import scalacats.ch4.Writers._

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent._
import scala.concurrent.duration._
import scala.util.Try

object App {

  def main(args: Array[String]): Unit = {

    // MonadError

    println(validateAdult[Try](18))
    println(validateAdult[Try](8))

    type ExceptionOr[A] = Either[Throwable, A]
    println(validateAdult[ExceptionOr](-1))

    // Eval

    // println(unsafeFoldRight(1 to 1000000 toList, 0)(_ + _)) // throws StackOverflow
    println(safeFoldRight(1 to 1000000 toList, 0)(_ + _))

    // Writer

    println(factorial(5))

    val factorialWriters = Await.result(
      Future.sequence(Vector(
        Future(writerFactorial(5)),
        Future(writerFactorial(5))
      )),
      5.seconds
    )

    factorialWriters.zipWithIndex.foreach {
      case (writer, idx) =>
        println(s"Thread $idx:")
        writer.run._1.foreach(println)
        println()
    }

    // Reader

    val users = Map(
      1 -> "dade",
      2 -> "kate",
      3 -> "margo"
    )

    val passwords = Map(
      "dade"  -> "zerocool",
      "kate"  -> "acidburn",
      "margo" -> "secret"
    )

    val db = Db(users, passwords)
    println(checkLogin(1, "zerocool").run(db))
    println(checkLogin(4, "davinci").run(db))

    // State

    println(evalOne("42").runA(Nil).value)

    val program = for {
      _   <- evalOne("1")
      _   <- evalOne("2")
      ans <- evalOne("+")
    } yield ans

    println(program.runA(Nil).value)

    val multistageProgram = evalAll(List("1", "2", "+", "3", "*"))
    println(multistageProgram.runA(Nil).value)

    val biggerProgram = for {
      _   <- evalAll(List("1", "2", "+"))
      _   <- evalAll(List("3", "4", "+"))
      ans <- evalOne("*")
    } yield ans

    println(biggerProgram.runA(Nil).value)

    println(evalInput("1 2 + 3 4 + *"))

    // Tree

    println(branch(leaf(100), leaf(200))
      .flatMap(x => branch(leaf(x - 1), leaf(x + 1))))

    val tree = for {
      a <- branch(leaf(100), leaf(200))
      b <- branch(leaf(a - 10), leaf(a + 10))
      c <- branch(leaf(b - 1), leaf(b + 1))
    } yield c

    println(tree)

  }
}
