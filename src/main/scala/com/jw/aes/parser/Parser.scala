package com.jw.aes.parser
import com.jw.aes.tokenizer.Token
import com.jw.aes.tokenizer.Tokenizer

class Parser(tokens : List[Token]) {
  private var currTok : Int = 0


  def parse() : Unit = {

  }

  private def endParse : Boolean = currTok >= tokens.length
  private def peek : Token = {
    if (!endParse) {
      tokens(currTok)
    } else tokens.last // End
  }
  private def peekNext : Token = {
    if (currTok < tokens.length - 2) {
      tokens(currTok + 1)
    }
    else tokens.last
  }
  private def consumeToken : Token = {
    if (!endParse) {
      val tok = tokens(currTok)
      currTok += 1
      tok
    }
    else tokens.last
  }
  private def matchAndConsumeToken(typ : String) : Boolean = {
    if (!endParse) {
      if (tokens(currTok).tokenIs(typ)) {
        currTok += 1
        return true
      }
    }
    false
  }
  private def matchToken(typ : String) : Boolean = {
    if (!endParse) {
      if (tokens(currTok).tokenIs(typ)) return true
    }
    false
  }
}
