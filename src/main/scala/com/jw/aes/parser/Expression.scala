package com.jw.aes.parser

import com.jw.aes.tokenizer.Token

import scala.collection.mutable.ListBuffer

abstract class Expression {
  def evaluate() : Unit
}
