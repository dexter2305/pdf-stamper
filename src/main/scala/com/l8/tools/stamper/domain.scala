package com.l8.tools.stamper

object domain {

  case class Record private (columns: Map[String, String])
  object Record {
    def apply(columns: Map[String, String]) = new Record(columns)
  }

  case class DomainError private (message: String)
  object DomainError {
    def apply(message: String) = new DomainError(message)
  }

}
