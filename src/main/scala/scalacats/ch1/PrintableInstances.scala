package scalacats.ch1

import PrintableSyntax._

object PrintableInstances {

  implicit val printableString: Printable[String] = (a: String) => a

  implicit val printableInt: Printable[Int] = (i: Int) => i.toString

  implicit val printableCat: Printable[Cat] = (cat: Cat) =>
    s"${cat.name.fmt} is a ${cat.age.fmt} year-old ${cat.color.fmt} cat."

}
