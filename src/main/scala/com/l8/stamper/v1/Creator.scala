package com.l8.stamper.v2


import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfStamper
import java.io.FileOutputStream
import org.slf4j.LoggerFactory



object Creator extends App {
  
  val l = LoggerFactory.getLogger(Creator.getClass())


  val reader = new PdfReader("./refs/sample-template-v3.pdf")
  val stamper = new PdfStamper(reader, new FileOutputStream("./target/instance.pdf"), '0', true)
  val map  = reader.getInfo()
  println(map)
  stamper.getAcroFields().setField("username", "Naveen Shyam Reuban")
  stamper.getAcroFields().setField("ideaname", "IceBox")
  stamper.getAcroFields().setField("ename", "tbnox-workd?")
  stamper.close()
  reader.close()

  l.info("pdf stamped !! ")

}
