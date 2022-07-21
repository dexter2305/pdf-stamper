package com.l8.stamper.v1

import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfStamper
import java.io.FileOutputStream
import org.slf4j.LoggerFactory
import java.nio.file.Files
import java.nio.file.Paths

object Creator extends App {

  val l                = LoggerFactory.getLogger(Creator.getClass())
  val templateFileName = "./refs/sample-template-v3.pdf"
  //val templateFile     = Paths.get(templateFileName).toFile()
  val reader           = new PdfReader(templateFileName)
  val stamper          = new PdfStamper(reader, new FileOutputStream("./target/instance.pdf"), '0', true)
  val map              = reader.getInfo()

  println(s"pdf.getInfo() -> $map")

  val keywords = map.getOrDefault("Keywords", "").split(",").filterNot(_.isEmpty).map(_.trim)
  println(s"number of keywords: ${keywords.size}")
  println(s"keywords: ${keywords.mkString(" ")}")

  stamper.getAcroFields().getFields().keySet().forEach(println(_))

  stamper.getAcroFields().setField("username", "Naveen Shyam Reuban")
  stamper.getAcroFields().setField("ideaname", "IceBox")
  stamper.getAcroFields().setField("ename", "tbnox-workd?")
  stamper.close()
  reader.close()

  l.info("pdf stamped !! ")

}
