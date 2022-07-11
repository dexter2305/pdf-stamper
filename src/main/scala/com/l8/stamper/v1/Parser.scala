package com.l8.stamper.v1

object Parser extends App {

  object domain {

    case class Record private (elements: List[(String, String)]) {
      def apply(key: String): Option[String] = {
        elements.find(_._1 == key) match {
          case None         => None
          case Some((x, y)) => Some(y)
        }
      }
    }

    object Record {
      def apply(elements: (String, String)*) = {
        new Record(elements.toList)
      }
    }
  }

  import domain._
  val r = Record(("b" -> "2"), ("a" -> "1"))
  println(r)
  println(r("b"))

}
