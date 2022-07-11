package com.l8.stamper.v2

import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfStamper
import com.l8.stamper.LoggingServiceComponent
import com.l8.stamper.domain.DomainError

import java.io.File
import java.io.FileOutputStream
import com.l8.stamper.domain.Record

trait StamperServiceComponent {

  val stamper: StamperService

  trait StamperService {
    def stamp(template: File, data: Seq[Record]): Either[DomainError, File]
  }
}

trait StamperServiceComponentWithItext extends StamperServiceComponent { requires: LoggingServiceComponent =>

  class StamperServiceWithItext extends StamperService {
    override def stamp(template: File, data: Seq[Record]): Either[DomainError, File] = {
      val reader  = new PdfReader(template.getAbsolutePath())
      val headers = data.head.columns.keySet
      
      ???
    }
  }
}
