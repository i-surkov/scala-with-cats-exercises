package scalacats.ch11

import cats.kernel.CommutativeMonoid
import cats.syntax.foldable._
import cats.syntax.monoid._
import scalacats.ch11.KeyValueStore._

trait GCounter[F[_, _], K, V] {

  def increment(f: F[K, V])(k: K, v: V)(implicit m: CommutativeMonoid[V]): F[K, V]

  def merge(f1: F[K, V], f2: F[K, V])(implicit b: BoundedSemiLattice[V]): F[K, V]

  def total(f: F[K, V])(implicit m: CommutativeMonoid[V]): V

}

object GCounter {

  def apply[F[_, _], K, V](implicit counter: GCounter[F, K, V]): GCounter[F, K, V] =
    counter

  implicit def mapGCounterInstance[K, V]: GCounter[Map, K, V] = new GCounter[Map, K, V] {
    override def increment(f: Map[K, V])(k: K, v: V)(
      implicit m: CommutativeMonoid[V]
    ): Map[K, V] = {
      val total = f.getOrElse(k, m.empty) |+| v
      f + (k -> total)
    }

    override def merge(f1: Map[K, V], f2: Map[K, V])(implicit b: BoundedSemiLattice[V]): Map[K, V] =
      f1 |+| f2

    override def total(f: Map[K, V])(implicit m: CommutativeMonoid[V]): V =
      f.values.toList.combineAll
  }

  implicit def gCounterInstance[F[_, _], K, V](implicit
    kvs: KeyValueStore[F],
    km: CommutativeMonoid[F[K, V]]
  ): GCounter[F, K, V] = new GCounter[F, K, V] {
    def increment(f: F[K, V])(key: K, value: V)(implicit m: CommutativeMonoid[V]): F[K, V] = {
      val total = f.getOrElse(key, m.empty) |+| value
      f.put(key, total)
    }

    def merge(f1: F[K, V], f2: F[K, V])(implicit b: BoundedSemiLattice[V]): F[K, V] =
      f1 |+| f2

    def total(f: F[K, V])(implicit m: CommutativeMonoid[V]): V = f.values.combineAll
  }

}
