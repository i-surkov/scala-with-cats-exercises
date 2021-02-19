package scalacats.ch10

import cats.data._

object App {

  def main(args: Array[String]): Unit = {

    // Check0

    val positiveCheck = Check0.Pure[NonEmptyChain[String], Int] { value =>
      Validated.condNec(value > 0, value, "Value is negative")
    }

    val evenCheck = Check0.Pure[NonEmptyChain[String], Int] { value =>
      Validated.condNec(value % 2 == 0, value, "Value is odd")
    }

    println(positiveCheck.and(evenCheck).apply(56))
    println(positiveCheck.and(evenCheck).apply(-10))
    println(positiveCheck.and(evenCheck).apply(-11))

    println(positiveCheck.or(evenCheck).apply(56))
    println(positiveCheck.or(evenCheck).apply(-10))
    println(positiveCheck.or(evenCheck).apply(-11))

    // Predicate

    val positivePred = Predicate { value: Int =>
      Validated.condNec(value > 0, value, "Value is negative")
    }

    val evenPred = Predicate { value: Int =>
      Validated.condNec(value % 2 == 0, value, "Value is odd")
    }

    // Checks

    val mappedAndCheck = Check(positivePred.and(evenPred)).map(_ * 100)
    val mappedOrCheck = Check(positivePred.or(evenPred)).map(_ * 100)

    println(mappedAndCheck(56))
    println(mappedAndCheck(-10))
    println(mappedAndCheck(-11))

    println(mappedOrCheck(56))
    println(mappedOrCheck(-10))
    println(mappedOrCheck(-11))

    val mappedPositiveCheck = Check(positivePred).map(_ * 100)
    val mappedEvenCheck = Check(evenPred).map(_ + 10)

    println(mappedPositiveCheck andThen mappedEvenCheck apply 56)

    // More Checks

    println(User.createUser("a1df", "a1d@gmailcom"))
    println(User.createUser("a1df", "a1d@gmail.com"))
    println(User.createUser("a1dfg", "a1df@gmailcom"))
    println(User.createUser("a1dfg", "a1df@gmail.com"))

    // Kleisli

    println(User2.createUser("a1df", "a1d@gmailcom"))
    println(User2.createUser("a1df", "a1d@gmail.com"))
    println(User2.createUser("a1dfg", "a1df@gmailcom"))
    println(User2.createUser("a1dfg", "a1df@gmail.com"))

  }

}
