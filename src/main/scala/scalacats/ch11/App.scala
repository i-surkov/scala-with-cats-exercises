package scalacats.ch11

import cats.instances.map.catsKernelStdCommutativeMonoidForMap
import cats.kernel.CommutativeMonoid

object App {

  def main(args: Array[String]): Unit = {

    // GCounter1

    val g1_1 = GCounter1(Map("A" -> 1, "B" -> 2))
    val g1_2 = GCounter1(Map("A" -> 0, "B" -> 3))

    println(g1_1.increment("A", 2))
    println(g1_1.total)
    println(g1_1.merge(g1_2))
    println(g1_2.merge(g1_1))

    // GCounter2

    val g2_1 = GCounter2(Map("A" -> 1, "B" -> 2))
    val g2_2 = GCounter2(Map("A" -> 0, "B" -> 3))

    println(g2_1.increment("A", 2))
    println(g2_1.total)
    println(g2_1.merge(g2_2))
    println(g2_2.merge(g2_1))

    // GCounter map instance

    val g1 = Map("a" -> 7, "b" -> 3)
    val g2 = Map("a" -> 2, "b" -> 5)

    {
      import GCounter.mapGCounterInstance

      val counter = GCounter[Map, String, Int]
      val merged = counter.merge(g1, g2)
      println(merged)
      val total = counter.total(merged)
      println(total)
    }

    // GCounter generic key-value instance

    {
      import GCounter.gCounterInstance

      // Explicit map monoid to resolve different int monoid instances for different contexts
      implicit val mapMonoid: CommutativeMonoid[Map[String, Int]] =
        catsKernelStdCommutativeMonoidForMap(BoundedSemiLattice.intBoundedSemiLatticeInstance)

      val counter2 = GCounter[Map, String, Int]
      val merged2 = counter2.merge(g1, g2)
      println(merged2)
      val total2 = counter2.total(merged2)
      println(total2)
    }

  }

}
