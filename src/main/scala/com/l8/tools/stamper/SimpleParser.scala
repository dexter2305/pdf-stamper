package com.l8.tools.stamper
import domain._

class SimpleParser(lines: Seq[String]) {

  val headers = lines.head.split(",").map(_.trim).toList

  def parse(): Seq[Record] = {
    for {
      line <- lines.tail if line.trim.length() > 0 && line.contains(",")
      values = line.split(",")
      record = Record(headers.zip(values).toMap)
    } yield record
  }
}

object SimpleParser {

  def apply(lines: Seq[String]) = new SimpleParser(lines)
}
