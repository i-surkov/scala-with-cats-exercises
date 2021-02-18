package scalacats.ch9

import cats.implicits._
import scalacats.ch9.MapReduce._

import scala.concurrent._
import scala.concurrent.duration.DurationInt

object App {

  def main(args: Array[String]): Unit = {

    // foldMap

    println(foldMap(Vector(1, 2, 3))(identity))
    println(foldMap(Vector(1, 2, 3))(_.toString + "! "))

    // parallelFoldMap

    val result: Future[Int] =
      parallelFoldMap((1 to 1000000).toVector)(identity)
    println(Await.result(result, 1.second))

    // parallelFoldMapCats

    val future: Future[Int] =
      parallelFoldMapCats((1 to 1000).toVector)(_ * 1000)
    println(Await.result(future, 1.second))

    // parallelFoldMapCats2

    val future2: Future[Int] =
      parallelFoldMapCats2((1 to 1000).toVector)(_ * 1000)
    println(Await.result(future2, 1.second))

  }

}
