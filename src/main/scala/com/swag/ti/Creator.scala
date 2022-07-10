package com.swag.ti
package v1
import com.itextpdf.tool.xml.XMLWorkerHelper
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.Document
import java.io.FileOutputStream
import java.io.File
import java.io.FileInputStream
import org.slf4j.LoggerFactory

object Creator extends App {

  val logger = LoggerFactory.getLogger(Creator.getClass())

  println("Hello, user")

  val document  = new Document()
  val pdf       = new File("./target/sample.pdf")
  val pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdf))
  document.open()
  document.addCreationDate()
  XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document, new FileInputStream("./sample/sample.html"))
  document.close()
  logger.info("document written !!")
}
