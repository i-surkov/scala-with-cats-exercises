package scalacats.ch4

import cats.data._

object Readers {

  final case class Db(
    usernames: Map[Int, String],
    passwords: Map[String, String]
  )

  type DbReader[A] = Reader[Db, A]

  def findUsername(userId: Int): DbReader[Option[String]] =
    Reader(_.usernames.get(userId))

  def checkPassword(username: String, password: String): DbReader[Boolean] =
    Reader(_.passwords.get(username).contains(password))

  def checkLogin(userId: Int, password: String): DbReader[Boolean] = (for {
    username <- OptionT(findUsername(userId))
    checks   <- OptionT.liftF(checkPassword(username, password))
  } yield checks).getOrElse(false)

}
