package scalacats.ch3

trait Printable[A] {
  self =>

  def fmt(value: A): String

  def contramap[B](func: B => A): Printable[B] =
    value => self.fmt(func(value))

}

object Printable {

  def fmt[A](value: A)(implicit p: Printable[A]): String = p.fmt(value)

  implicit val stringPrintable: Printable[String] =
    value => s"'$value'"

  implicit val booleanPrintable: Printable[Boolean] =
    value => if (value) "yes" else "no"

  implicit def boxPrintable[A](implicit valuePrintable: Printable[A]): Printable[Box[A]] =
    valuePrintable.contramap(_.value)

}
