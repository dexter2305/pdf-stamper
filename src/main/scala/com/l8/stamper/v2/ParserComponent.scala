package com.l8.stamper.v2

import com.l8.stamper.domain.Record

import java.io.File
import scala.io.Source

trait ParserComponent {

  val datafileParser: Parser

  trait Parser {
    def parse(data: File): Seq[Record]
  }
}

trait ParserComponentSimple extends ParserComponent {

  class SimpleParser extends Parser {
    private def parseDataRows(iterator: Iterator[String], headers: List[String], acc: Seq[Record]): Seq[Record] = {
      if (iterator.hasNext) {
        val line = iterator.next()
        val data = line.split(",").toList
        val record = Record(headers.zip(data).toMap)
        parseDataRows(iterator, headers, acc :+ record)
      } else acc
    }
    override def parse(data: File): Seq[Record] = {
      val source        = Source.fromFile(data)
      val linesIterator = source.getLines()
      val headers       = linesIterator.next().split(",").map(_.trim).toList
      parseDataRows(linesIterator, headers, List.empty[Record])
    }
  }
}
