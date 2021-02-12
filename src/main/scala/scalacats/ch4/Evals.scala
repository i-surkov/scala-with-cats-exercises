package scalacats.ch4

import cats.Eval

object Evals {

  def unsafeFoldRight[A, B](as: List[A], acc: B)(fn: (A, B) => B): B = as match {
    case head :: tail =>
      fn(head, unsafeFoldRight(tail, acc)(fn))
    case Nil =>
      acc
  }

  def safeFoldRight[A, B](as: List[A], acc: B)(fn: (A, B) => B): B = {
    def helper(as: List[A], acc: B): Eval[B] = as match {
      case head :: tail =>
        Eval.defer(helper(tail, acc).map(fn(head, _)))
      case Nil =>
        Eval.now(acc)
    }
    helper(as, acc).value
  }

}
