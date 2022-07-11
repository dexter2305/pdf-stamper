package com.l8.stamper

object domain {

  case class DomainError private (message: String)
  object DomainError {
    def apply(message: String) = new DomainError(message)
  }

  case class Record private (columns: Map[String, String])
  object Record {
    def apply(columns: Map[String, String]) = new Record(columns)
  }

}
