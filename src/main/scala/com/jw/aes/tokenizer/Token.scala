package com.jw.aes.tokenizer


class TokenType extends Enumeration {
  type TokenType = Value
  val Constant,
  Linear,
  Quadratic,
  Cubic,
  Quartic = Value // x terms
  val Plus, Minus, Star, Slash,
  Error = Value("Error")
  val LeftParen, RightParen, Unit, Carrot = Value         // parens & uninitialized value

}


class Token(var stringData : String = "", var tokenType : TokenType#Value) {

}

