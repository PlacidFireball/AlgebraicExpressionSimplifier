package com.jw.aes.tokenizer

import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks._


class Tokenizer(src : String) {
  var idx : Int = 0
  var tokenList : List[Token] = List()
  lazy val hasErrors: Boolean = {
    var flag = false
    for (token <- tokenList) {
      if (token.tokenType.equals(new TokenType().Error)) {
        flag = true
      }
    }
    flag
  }

  def tokenize() : List[Token] = {
    val tokenBuff : ListBuffer[Token] = new ListBuffer()
    while (idx < src.length) {
      consumeWhiteSpace()
      val varToken = tokenizeVar()
      val numToken = tokenizeNum()
      val syntaxToken = tokenizeSyntax()
      if (varToken.isDefined) {
        tokenBuff += varToken.get
      }
      else if (numToken.isDefined) {
        tokenBuff += numToken.get
      }
      else if (syntaxToken.isDefined) {
        tokenBuff += syntaxToken.get
      }
      else {
        val tokenType = new TokenType().Error
        val syntaxErrorToken = new Token(src(idx).toString, tokenType)
        idx += 1
        tokenBuff += syntaxErrorToken
      }
    }
    tokenBuff += new Token("<EOF>", new TokenType().End)
    tokenList = tokenBuff.toList
    tokenList
  }

  ///////////////////////////////
  ///    Helper functions     ///
  ///////////////////////////////

  def tokenizeVar() : Option[Token] = {
    /// Tokenize x^n
    if (matchCharAndConsume('x')) {
      if (matchCharAndConsume('^')) {
        if (matchCharAndConsume('2')) {
          return newVarToken('2')
        }
        else if (matchCharAndConsume('3')) {
          return newVarToken('3')
        }
        else if (matchCharAndConsume('4')) {
          return newVarToken('4')
        }
        else return None
      }
      return Some(new Token("x", new TokenType().Linear))
    }
    None
  }

  // tokenize any c \in \Z
  def tokenizeNum() : Option[Token] = {
    if (idx >= src.length) return None
    if (src(idx).isDigit) {
      var digits = ""
      while (src(idx).isDigit) {
        digits += src(idx)
        idx += 1
      }
      var exponentDigits = ""
      if (matchCharAndConsume('^')) {
        while (src(idx).isDigit) {
          exponentDigits += src(idx)
          idx += 1
        }
      }
      if (exponentDigits.equals("")) {
        val strVal = digits
        val tokenType = new TokenType().Constant
        return Some(new Token(strVal, tokenType))
      }
      else {
        val strVal = digits+"^"+exponentDigits
        val tokenType = new TokenType().Constant
        return Some(new Token(strVal, tokenType))
      }
    }
    None

  }
  // tokenize () + - * /
  def tokenizeSyntax() : Option[Token] = {
    if (idx >= src.length) return None
    if (src(idx) == '(') {
      Some(new Token("(", new TokenType().LeftParen))
    }
    else if (src(idx) == ')') {
      Some(new Token(")", new TokenType().RightParen))
    }
    else if (src(idx) == '*') {
      Some(new Token("*", new TokenType().Star))
    }
    else if (src(idx) == '/') {
      Some(new Token(")", new TokenType().Slash))
    }
    else if (src(idx) == '+') {
      Some(new Token("+", new TokenType().Plus))
    }
    else if (src(idx) == '-') {
      Some(new Token("-", new TokenType().Minus))
    }
    None
  }

  def newVarToken(power : Char): Option[Token] = {
    val strVal = "x^"+power
    val tokenType = if (power == '2') {
      new TokenType().Quadratic
    } else if (power == '3') {
      new TokenType().Cubic
    } else if (power == '4') {
      new TokenType().Quartic
    } else {
      new TokenType().Error
    }
    Some(new Token(strVal, tokenType))
  }
  def consumeWhiteSpace() : Unit = {
    breakable {
      while (src(idx).isWhitespace) {
        idx += 1
        if (idx >= src.length) {
          break
        }
      }
    }
  }
  def matchChar(needle : Char) : Boolean = {
    if (idx >= src.length) return false
    if (src(idx) == needle) true
    else false
  }
  def matchCharAndConsume(needle : Char) : Boolean = {
    if (idx >= src.length) return false
    if (matchChar(needle)) {
      this.idx += 1
      return true
    }
    false
  }
  // TODO: This code feels really gronky
  def tokenIsError(tokenType : TokenType#Value) = tokenType.equals(new TokenType().Error)

  override def toString: String = {
    if (idx >= src.length) {
      src + "-->[]<--"
    } else {
      val end : String = if (idx == src.length - 1)
      { "" }
      else { src.substring(idx+1, src.length -1) }
      src.substring(0, idx) + "-->["+src(idx) +"]<--" + end
    }
  }
}