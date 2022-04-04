package tokenizer

import collection.mutable.Stack
import com.jw.aes.tokenizer.Tokenizer
import org.scalatest._
import flatspec._
import matchers._

class TokenizerTest extends AnyFlatSpec with should.Matchers {

  "A Tokenizer" should "tokenize an empty string" in {
    val tokenizer = new Tokenizer("")
    assert(tokenizer.tokenize().isEmpty)
  }
  it should "tokenize all polynomials up to x^4" in {
    val tokenizer = new Tokenizer("x x^2 x^3 x^4")
    val tokenList = tokenizer.tokenize()
    assert(tokenList.size == 4)
  }
}
