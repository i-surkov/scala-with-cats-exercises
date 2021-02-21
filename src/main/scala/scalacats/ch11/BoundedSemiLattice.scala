package scalacats.ch11

import cats.kernel.CommutativeMonoid

trait BoundedSemiLattice[A] extends CommutativeMonoid[A] {
  def combine(a1: A, a2: A): A
  def empty: A
}

object BoundedSemiLattice {

  implicit val intBoundedSemiLatticeInstance: BoundedSemiLattice[Int] =
    new BoundedSemiLattice[Int] {
      override def combine(a1: Int, a2: Int): Int = a1 max a2
      override def empty: Int = 0
    }

  implicit def setBoundedSemiLatticeInstance[A]: BoundedSemiLattice[Set[A]] =
    new BoundedSemiLattice[Set[A]] {
      override def combine(a1: Set[A], a2: Set[A]): Set[A] = a1 union a2
      override def empty: Set[A] = Set.empty
    }

}
