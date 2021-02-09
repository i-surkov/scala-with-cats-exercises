package scalacats.ch2

object MonoidInstances {

  val booleanAnd: Monoid[Boolean] = new Monoid[Boolean] {
    override def empty: Boolean = true
    override def combine(x: Boolean, y: Boolean): Boolean = x && y
  }

  val booleanOr: Monoid[Boolean] = new Monoid[Boolean] {
    override def empty: Boolean = false
    override def combine(x: Boolean, y: Boolean): Boolean = x || y
  }

  val booleanXor: Monoid[Boolean] = new Monoid[Boolean] {
    override def empty: Boolean = false
    override def combine(x: Boolean, y: Boolean): Boolean = (x || y) && !(x && y)
  }

  val booleanXnor: Monoid[Boolean] = new Monoid[Boolean] {
    override def empty: Boolean = true
    override def combine(x: Boolean, y: Boolean): Boolean = (x && y) || !(x || y)
  }

  def setUnion[A]: Monoid[Set[A]] = new Monoid[Set[A]] {
    override def empty: Set[A] = Set()
    override def combine(x: Set[A], y: Set[A]): Set[A] = x union y
  }

  def setIntersection[A]: Semigroup[Set[A]] = new Semigroup[Set[A]] {
    override def combine(x: Set[A], y: Set[A]): Set[A] = x intersect y
  }

  def setXor[A]: Monoid[Set[A]] = new Monoid[Set[A]] {
    override def empty: Set[A] = Set()
    override def combine(x: Set[A], y: Set[A]): Set[A] = (x diff y) union (y diff x)
  }

}
