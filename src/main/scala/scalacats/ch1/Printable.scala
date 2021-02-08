package scalacats.ch1

trait Printable[A] {
  def fmt(a: A): String
}

object Printable {

  def fmt[A](a: A)(implicit printable: Printable[A]): String =
    printable.fmt(a)

  def print[A: Printable](a: A): Unit = println(fmt(a))

}
