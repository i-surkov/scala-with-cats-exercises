package scalacats.ch8

import cats._
import cats.implicits._

class UptimeService[F[_]: Applicative](client: UptimeClient[F]) {

  def getTotalUptime(hostnames: List[String]): F[Int] =
    hostnames.traverse(client.getUptime).map(_.sum)

}
