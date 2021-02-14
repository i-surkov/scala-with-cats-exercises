package scalacats.ch5

import scalacats.ch5.Transformers._

object App {

  def main(args: Array[String]): Unit = {

    println(tacticalReport("Jazz", "Bumblebee"))
    println(tacticalReport("Bumblebee", "Hot Rod"))
    println(tacticalReport("Jazz", "Ironhide"))

  }

}
