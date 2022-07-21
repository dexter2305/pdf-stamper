package com.l8.tools.stamper

import cats.effect.std.UUIDGen
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfStamper

import java.io.ByteArrayOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

object Util {

  def process(template: Array[Byte], datalines: Seq[String]): Array[Byte] = {

    val baos      = new ByteArrayOutputStream()
    val zipStream = new ZipOutputStream(baos)

    val templateReader         = new PdfReader(template)
    val entryNameField: String = templateReader.getInfo().getOrDefault("Keywords", "")
    println(s"entry name field: '$entryNameField'")
    val parser                 = SimpleParser(datalines)
    val headers                = parser.headers
    for {
      record <- parser.parse()
      baos       = new ByteArrayOutputStream
      stamper    = new PdfStamper(templateReader, baos)
      _          = for (header <- headers) {
        stamper.getAcroFields().setField(header, record.columns(header))
      }
      _          = stamper.close
      _          = baos.close
      entryBytes = baos.toByteArray()
      entryname  = if (headers.contains(entryNameField)) record.columns(entryNameField) else "Anonymous"
      entryDn    = s"${entryname}_${System.nanoTime()}"
      zipEntry   = new ZipEntry(entryDn)
      _          = zipStream.putNextEntry(zipEntry)
      _          = zipStream.write(entryBytes)
      _          = println(s"$entryDn written of size ${entryBytes.size} bytes")
    } yield ()
    templateReader.close
    val zipBytes               = baos.toByteArray
    println(s"Zip stream size ${zipBytes.size} bytes")
    zipBytes
  }

}
