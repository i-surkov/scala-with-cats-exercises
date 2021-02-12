package scalacats.ch4

import cats._
import cats.implicits._

object MonadError {

  def validateAdult[F[_]](age: Int)(implicit me: MonadError[F, Throwable]): F[Int] =
    age.pure[F].ensureOr { a =>
      new IllegalArgumentException(s"Age must be greater than or equal to 18, was given $a")
    }(_ >= 18)

}
