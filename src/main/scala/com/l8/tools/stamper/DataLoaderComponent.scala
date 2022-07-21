package com.l8.stamper

import com.l8.stamper.v2.domain.Record

import java.io.File
import scala.io.Source
import cats.effect.IO

trait DataLoaderComponent {

  def parser: Loader

  trait Loader {
    def load(data: File): IO[Seq[Record]]

  }
}

trait CSVDataLoaderComponent extends DataLoaderComponent {

  class CSVDataLoader extends Loader {
    private def parseDataRows(iterator: Iterator[String], headers: List[String], acc: Seq[Record]): Seq[Record] = {
      if (iterator.hasNext) {
        val line   = iterator.next()
        val data   = line.split(",").toList
        val record = Record(headers.zip(data).toMap)
        parseDataRows(iterator, headers, acc :+ record)
      } else acc
    }
    override def load(data: File): IO[Seq[Record]] = IO {
      val source        = Source.fromFile(data)
      val linesIterator = source.getLines()
      val headers       = linesIterator.next().split(",").map(_.trim).toList
      parseDataRows(linesIterator, headers, List.empty[Record])
    }
  }
}
