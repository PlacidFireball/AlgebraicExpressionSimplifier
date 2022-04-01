package com.jw.aes.tokenizer

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
    while (idx < src.length) {
      consumeWhiteSpace()
      val varToken = tokenizeVar()
      val numToken = tokenizeNum()
      val syntaxToken = tokenizeSyntax()
      if (varToken.isDefined) {
        tokenList = varToken.get :: tokenList
      }
      else if (numToken.isDefined) {
        tokenList = numToken.get :: tokenList
      }
      else if (syntaxToken.isDefined) {
        tokenList = varToken.get :: tokenList
      }
      else {
        val tokenType = new TokenType().Error
        val syntaxErrorToken = new Token(src(idx).toString, tokenType)
        idx += 1
        tokenList = syntaxErrorToken :: tokenList
      }
    }
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
}
