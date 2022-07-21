package com.l8.stamper.v2

import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfStamper
import com.l8.stamper.LoggingServiceComponent
import com.l8.stamper.v2.domain.DomainError
import com.l8.stamper.v2.domain.Record

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

trait StamperServiceComponent {

  def stamper: StamperService

  trait StamperService {
    def stamp(template: Array[Byte], data: Seq[Record]): Seq[Array[Byte]]
  }
}

trait StamperServiceComponentWithItext extends StamperServiceComponent { 

  class StamperServiceWithItext extends StamperService {

    private def stamp(reader: PdfReader, headers: Set[String], record: Record): Array[Byte] = {
      val baos    = new ByteArrayOutputStream
      val stamper = new PdfStamper(reader, baos)
      headers.forall(key => stamper.getAcroFields().setField(key, record.columns(key)))
      stamper.close()
      baos.toByteArray()
    }

    override def stamp(templateAsByteArray: Array[Byte], data: Seq[Record]): Seq[Array[Byte]] = {
      val reader: PdfReader           = new PdfReader(templateAsByteArray)
      val headers: Set[String]        = data.head.columns.keySet
      val byteArray: Seq[Array[Byte]] = data.map(record => stamp(reader, headers, record))
      reader.close()
      byteArray
    }
  }
}
