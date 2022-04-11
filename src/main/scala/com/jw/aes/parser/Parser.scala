package com.jw.aes.parser
import com.jw.aes.tokenizer.Token
import com.jw.aes.tokenizer.Tokenizer
import com.jw.aes.parser.Expression

import scala.collection.mutable.ListBuffer

class Parser(tokens : List[Token]) {
  private var currTok : Int = 0
  private var exprs : ListBuffer[Expression] = new ListBuffer()

  def parse() : Unit = {
    while (!endParse) {
      exprs += parseAdditive
    }
  }

  def parseAdditive : Expression = {
    var expr = parseFactor
    while (matchOneOf(List("Plus", "Minus"))) {

    }
    expr
  }

  def parseFactor : Expression = {
    var expr = parseParenthesized
    while (matchOneOf(List("Star", "Slash"))) {

    }
    expr
  }

  def parseParenthesized : Expression = {
    if (matchToken("LeftParen")) {
      parseAdditive
    } else {
      parseVariable
    }
  }

  def parseVariable : Expression = {
    if (matchOneOf(List("Linear", "Quadratic", "Cubic", "Quartic"))) {

    }
    else {
      parseConstant
    }
  }

  def parseConstant : Expression = {

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
      if (matchToken(typ)) {
        consumeToken
        return true
      }
    }
    false
  }
  private def matchToken(typ : String) : Boolean = {
    if (!endParse) {
      if (peek.tokenIs(typ)) return true
    }
    false
  }
  private def matchOneOf(types : List[String]) : Boolean = {
    if (!endParse) {
      for (typ <- types) {
        if (matchToken(typ)) {
          return true
        }
      }
    }
    false
  }
}
