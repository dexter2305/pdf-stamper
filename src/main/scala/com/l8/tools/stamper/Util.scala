package com.l8.tools.stamper

import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfStamper
import org.slf4j.LoggerFactory

import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

object Util extends App {

  val templatefilePath           = "./refs/sample-template-v4.pdf"
  val csvfilePath                = "./refs/data.csv"
  val templatebytes: Array[Byte] = Files.readAllBytes(Paths.get(templatefilePath))
  val datalines: Seq[String]     = scala.io.Source.fromFile(csvfilePath).getLines().toList
  val zipBytes                   = process(templatebytes, datalines)

  private def getBytesFromFile(filepath: String) = {
    val is    = new FileInputStream(filepath)
    val cnt   = is.available
    val bytes = Array.ofDim[Byte](cnt)
    is.read(bytes)
    is.close()
    bytes
  }

  def process(template: Array[Byte], datalines: Seq[String]): Array[Byte] = {
    val logger    = LoggerFactory.getLogger(getClass())
    val baos      = new ByteArrayOutputStream()
    //val baos      = new FileOutputStream("./target/stamped-unique.zip")
    val zipStream = new ZipOutputStream(baos)

    val templateReader         = new PdfReader(template)
    val entryNameField: String = templateReader.getInfo().getOrDefault("Keywords", "")
    logger.info(s"filename pattern set from: '$entryNameField'")
    val parser                 = SimpleParser(datalines)
    val headers                = parser.headers
    logger.info(s"number of data records :${parser.parse().size}")
    for {
      record <- parser.parse()
      _           = logger.info(s"processing for ${record.columns.mkString(" ")}")
      templateDoc = new PdfReader(template)
      baos        = new ByteArrayOutputStream
      stamper     = new PdfStamper(templateDoc, baos)
      _           = for (header <- headers) {
        stamper.getAcroFields().setField(header, record.columns(header.trim))
        logger.info(s"setting fields $header: ${record.columns(header)}")
      }
      _           = stamper.close
      _           = baos.close
      entryBytes  = baos.toByteArray()
      entryname   = if (headers.contains(entryNameField)) record.columns(entryNameField) else "Anonymous"
      entryDn     = s"${entryname}_${System.nanoTime()}.pdf"
      zipEntry    = new ZipEntry(entryDn)
      _           = zipStream.putNextEntry(zipEntry)
      _           = zipStream.write(entryBytes)
      _           = logger.info(s"$entryDn written of size ${entryBytes.size} bytes")
    } yield ()
    templateReader.close
    zipStream.close()
    val zipBytes               = baos.toByteArray
    logger.info(s"Zip stream size ${zipBytes.size} bytes")
    zipBytes
    //Array.empty[Byte]
  }

}
