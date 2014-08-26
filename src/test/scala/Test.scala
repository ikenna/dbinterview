import net.ikenna.Question
import org.scalatest.FunSuite

class Test extends FunSuite {

  test("How old is Tony Blair") {
    val answer = Question.ask("How old is Tony Blair?")
    assert("61" === answer)
  }

  test("What is the birth place of David Cameron") {
    val answer = Question.ask("What is the birth place of David Cameron?")
    assert("London, United Kingdom" === answer)
  }
}



