package scalacats.ch1

import cats.implicits._
import scalacats.ch1.PrintableInstances._
import scalacats.ch1.PrintableSyntax._

object App {

  def main(args: Array[String]): Unit = {

    val cat = Cat("Fred", 11, "White")

    cat.print
    println(cat.show)

    val cat1 = Cat("Garfield", 38, "orange and black")
    val cat2 = Cat("Heathcliff", 33, "orange and black")
    val optionCat1 = Option(cat1)
    val optionCat2 = Option.empty[Cat]

    println("Same cats are equal: " + (cat1 === cat1))
    println("Different cats are not equal: " + (cat1 =!= cat2))
    println("Same optional cats are equal: " + (optionCat1 === optionCat1))
    println("Some cat is equal to None cat: " + (optionCat1 === optionCat2))

  }

}
