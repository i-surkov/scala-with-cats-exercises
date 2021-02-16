package scalacats.ch7

import cats.implicits._
import mouse.all._

object Lists {

  def map[A, B](list: List[A])(f: A => B): List[B] =
    list.foldRight(List[B]())(f(_) :: _)

  def flatMap[A, B](list: List[A])(f: A => List[B]): List[B] =
    list.foldRight(List[B]())(f(_) ::: _)

  def filter[A](list: List[A])(p: A => Boolean): List[A] =
    list.foldRight(List[A]())((el, acc) => (p(el) ?? List(el)) ::: acc)

  def sum[A](list: List[A])(implicit num: Numeric[A]): A =
    list.foldRight(num.zero)(num.plus)

}
