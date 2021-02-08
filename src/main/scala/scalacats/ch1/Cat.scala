package scalacats.ch1

import cats._
import cats.implicits._

final case class Cat(name: String, age: Int, color: String)

object Cat {

  implicit val catShow: Show[Cat] = Show.show { cat =>
    s"${cat.name.show} is a ${cat.age.show} year-old ${cat.color.show} cat."
  }

  implicit val catEq: Eq[Cat] = Eq.instance(_.show == _.show)

}
