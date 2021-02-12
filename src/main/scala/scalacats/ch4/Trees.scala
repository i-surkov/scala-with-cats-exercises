package scalacats.ch4

import cats._
import scalacats.ch3.Tree
import scalacats.ch3.Tree._

object Trees {

  implicit val recursiveTreeMonad: Monad[Tree] = new Monad[Tree] {

    override def pure[A](x: A): Tree[A] = leaf(x)

    override def flatMap[A, B](fa: Tree[A])(f: A => Tree[B]): Tree[B] = fa match {
      case Leaf(a)             => f(a)
      case Branch(left, right) => Branch(flatMap(left)(f), flatMap(right)(f))
    }

    override def tailRecM[A, B](a: A)(f: A => Tree[Either[A, B]]): Tree[B] =
      flatMap(f(a)) {
        case Left(newA) => tailRecM(newA)(f)
        case Right(b)   => leaf(b)
      }

  }

}
