package scalacats.ch1

object PrintableSyntax {

  implicit class PrintableOps[A](value: A) {

    def fmt(implicit printable: Printable[A]): String = Printable.fmt(value)

    def print(implicit printable: Printable[A]): Unit = Printable.print(value)

  }

}
