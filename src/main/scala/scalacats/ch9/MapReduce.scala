package scalacats.ch9

import cats._
import cats.implicits._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object MapReduce {

  def foldMap[A, B: Monoid](v: Vector[A])(f: A => B): B =
    v.foldRight(Monoid[B].empty)(f(_) |+| _)

  def parallelFoldMap[A, B: Monoid](values: Vector[A])(func: A => B): Future[B] = {
    val processors = Runtime.getRuntime.availableProcessors
    val groupSize = (values.length.toDouble / processors).ceil.toInt
    values.grouped(groupSize)
      .map(subTask => Future(foldMap(subTask)(func)))
      .toVector
      .sequence
      .map(_.combineAll)
  }

  def parallelFoldMapCats[A, B: Monoid](values: Vector[A])(func: A => B): Future[B] = {
    val processors = Runtime.getRuntime.availableProcessors
    val groupSize = (values.length.toDouble / processors).ceil.toInt
    values.grouped(groupSize)
      .toVector
      .traverse(subTask => Future(foldMap(subTask)(func)))
      .map(_.combineAll)
  }

  def parallelFoldMapCats2[A, B: Monoid](values: Vector[A])(func: A => B): Future[B] = {
    val processors = Runtime.getRuntime.availableProcessors
    val groupSize = (values.length.toDouble / processors).ceil.toInt
    values.grouped(groupSize)
      .toVector
      .foldMapM(subTask => Future(foldMap(subTask)(func)))
  }

}
