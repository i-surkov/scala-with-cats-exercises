package scalacats.ch7

import scalacats.ch7.Lists._
import scalacats.ch7.Traverses._

object App {

  def main(args: Array[String]): Unit = {

    val list = List(1, 2, 3, 4, 5)

    // Default list folds

    println(list.foldLeft(List[Int]())((acc, el) => el :: acc))
    println(list.foldRight(List[Int]())(_ :: _))

    // Custom list functions

    println(map(list)(_ * 2))
    println(flatMap(list)(a => List(a, a * 2)))
    println(filter(list)(_ % 2 == 0))
    println(sum(list))

    // Traversing with Applicatives

    println(listSequence(List(Vector(1, 2), Vector(3, 4))))
    println(listSequence(List(Vector(1, 2), Vector(3, 4), Vector(5, 6))))

    println(processOption(List(2, 4, 6)))
    println(processOption(List(1, 2, 3)))

    println(processValidated(List(2, 4, 6)))
    println(processValidated(List(1, 2, 3)))

  }

}
