package tokenizer

import com.jw.aes.tokenizer.{Token, Tokenizer}
import org.scalatest._
import flatspec._
import matchers._

class TokenizerTest extends AnyFlatSpec with should.Matchers {

  def assertTokensAre(tokenTypes : List[String], tokens : List[Token]) : Unit = {
    var i : Int = 0
    for (str<-tokenTypes) {
      val boolean = tokens(i) tokenIs str
      assert(boolean)
      i += 1
    }
  }

  "A Tokenizer" should "tokenize an empty string" in {
    val tokenizer = new Tokenizer("")
    val tokenList = tokenizer.tokenize()
    assert(!tokenizer.hasErrors)
    assertTokensAre(List("<EOF>"), tokenList)
    assert(tokenList.size == 1)
  }
  it should "tokenize all polynomials up to x^4" in {
    val tokenizer = new Tokenizer("x x^2 x^3 x^4")
    val tokenList = tokenizer.tokenize()
    assert(!tokenizer.hasErrors)
    assertTokensAre(List("Linear", "Quadratic", "Cubic", "Quartic", "<EOF>"), tokenList)
    assert(tokenList.size == 5)
  }

  it should "tokenize all syntax" in {
    val tokenizer = new Tokenizer("( ) + - * /")
    val tokenList = tokenizer.tokenize()
    assert(!tokenizer.hasErrors)
    assertTokensAre(List("LeftParen", "RightParen", "Plus", "Minus",
      "Star", "Slash", "<EOF>"), tokenList)
    assert(tokenList.size == 7)
  }

  it should "tokenize an algebraic expression" in {
    val tokenizer = new Tokenizer("(x+1)/1+x^2-4")
    val tokenList = tokenizer.tokenize()
    assert(!tokenizer.hasErrors)
    assertTokensAre(List("LeftParen", "Linear", "Plus", "Constant", "RightParen", "Slash",
      "Constant", "Plus","Quadratic", "Minus", "Constant", "<EOF>"), tokenList)
  }
}
