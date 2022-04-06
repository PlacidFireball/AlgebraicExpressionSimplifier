package com.jw.aes.tokenizer


class TokenType extends Enumeration {
  type TokenType = Value
  val Constant, Linear, Quadratic, Cubic,
  Quartic,Plus, Minus, Star, Slash,
  Error, LeftParen, RightParen, Unit, Carrot = Value
  val End : Value = Value("<EOF>")
}

class Token(var stringData : String = "", var tokenType : TokenType#Value) {
  override def toString: String = {
    "[Token: "+stringData+" | Type: "+tokenType.toString+"]"
  }
  def tokenIs(typ : String) : Boolean = {
    typ.equals(tokenType.toString)
  }
}

