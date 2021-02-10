package scalacats.ch3

trait Codec[A] {
  self =>

  def encode(value: A): String
  def decode(value: String): A
  def imap[B](dec: A => B, enc: B => A): Codec[B] = new Codec[B] {
    override def encode(value: B): String = self.encode(enc(value))
    override def decode(value: String): B = dec(self.decode(value))
  }
}

object Code {

  def encode[A](value: A)(implicit c: Codec[A]): String = c.encode(value)
  def decode[A](value: String)(implicit c: Codec[A]): A = c.decode(value)

  implicit val stringCodec: Codec[String] =
    new Codec[String] {
      def encode(value: String): String = value
      def decode(value: String): String = value
    }

  implicit val intCodec: Codec[Int] =
    stringCodec.imap(_.toInt, _.toString)

  implicit val booleanCodec: Codec[Boolean] =
    stringCodec.imap(_.toBoolean, _.toString)

  implicit val doubleCodec: Codec[Double] =
    stringCodec.imap(_.toDouble, _.toString)

  implicit def boxCodec[A](implicit c: Codec[A]): Codec[Box[A]] = new Codec[Box[A]] {
    override def encode(value: Box[A]): String = c.encode(value.value)
    override def decode(value: String): Box[A] = Box(c.decode(value))
  }

}
