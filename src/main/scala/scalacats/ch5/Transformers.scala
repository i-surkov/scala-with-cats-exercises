package scalacats.ch5

import cats.data._
import cats.implicits._
import mouse.all._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration._

object Transformers {

  type Response[A] = EitherT[Future, String, A]

  val powerLevels = Map(
    "Jazz"      -> 6,
    "Bumblebee" -> 8,
    "Hot Rod"   -> 10
  )

  def getPowerLevel(autobot: String): Response[Int] =
    EitherT.fromEither(powerLevels.get(autobot).toRight(s"$autobot is unreachable"))

  def canSpecialMove(ally1: String, ally2: String): Response[Boolean] = for {
    first  <- getPowerLevel(ally1)
    second <- getPowerLevel(ally2)
  } yield first + second > 15

  def tacticalReport(ally1: String, ally2: String): String = {
    val report = canSpecialMove(ally1, ally2)
      .fold(err => s"Comms error: $err",
            _.fold(
              s"$ally1 and $ally2 are ready to roll out!",
              s"$ally1 and $ally2 need a recharge."
            )
      )
    Await.result(report, 5 seconds)
  }

}
