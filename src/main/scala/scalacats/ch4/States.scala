package scalacats.ch4

import cats.data._
import cats.implicits._
import mouse.all._

object States {

  type CalcState[A] = State[List[Int], A]

  sealed trait CalcSymbol
  case class Number(value: Int) extends CalcSymbol
  case class Operation(op: (Int, Int) => Int) extends CalcSymbol

  def parseSymbol(sym: String): CalcSymbol = sym match {
    case "+" => Operation(_ + _)
    case "-" => Operation(_ - _)
    case "*" => Operation(_ * _)
    case "/" => Operation(_ / _)
    case _   => sym.parseInt.fold(throw _, Number)
  }

  def evalOne(sym: String): CalcState[Int] = State[List[Int], Int] { stack =>
    (sym |> parseSymbol, stack) match {
      case (Number(number), _) => (number :: stack, number)
      case (Operation(op), top :: prev :: stackTail) =>
        val result = op(prev, top)
        (result :: stackTail, op(prev, top))
      case (op, _) =>
        throw new IllegalStateException(s"Stack couldn't be processed: op: $op, stack: $stack")
    }
  }

  def evalAll(input: List[String]): CalcState[Int] = input.foldM(0)((_, str) => evalOne(str))

  def evalInput(input: String): Int = evalAll(input.split(' ').toList).runEmptyA.value

}
