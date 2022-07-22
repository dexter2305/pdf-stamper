package com.l8.tools.stamper

import cats.effect.IO
import fs2._
import fs2.text._
import org.http4s.Headers
import org.http4s.HttpRoutes
import org.http4s.MediaType
import org.http4s.dsl.Http4sDsl
import org.http4s.headers.`Content-Disposition`
import org.http4s.headers.`Content-Type`
import org.http4s.multipart.Multipart
import org.http4s.twirl._
import org.slf4j.LoggerFactory
import org.typelevel.ci.CIString

class StamperController extends Http4sDsl[IO] {


  val logger = LoggerFactory.getLogger(getClass())

  val routes = HttpRoutes.of[IO] {
    case GET -> Root                     => Ok(html.index())
    case req @ POST -> Root / "generate" =>
      req.decode[Multipart[IO]] { m =>
        m.parts.find(_.name == Some("template")) match {
          case None               => BadRequest("Missing template file")
          case Some(templatePart) => {
            val templateByteStream = for {
              byte <- templatePart.body
            } yield byte

            m.parts.find(_.name == Some("data")) match {
              case Some(datapart) =>
                val dataLineStream = for {
                  line <- datapart.body.through(utf8.decode).through(text.lines)
                } yield line
                for {
                  templateBytes <- templateByteStream.compile.toList
                  datalines     <- dataLineStream.compile.toList
                  templateSize      = templateBytes.size
                  dataLineCount     = datalines.size
                  _                 = logger.info(s"template size:${templateSize} bytes && data lines received: $dataLineCount")
                  templateByteArray = templateBytes.toArray
                  _                 = logger.info(s"${templatePart}")
                  zipByteArray      = Util.process(templateByteArray, datalines)
                  outputfilename    = templatePart.filename match {
                    case None              => s"pdf-stamped-${System.nanoTime()}.zip"
                    case Some(pdfFileName) => pdfFileName.substring(0, pdfFileName.lastIndexOf(".")) + s".zip"
                  }
                  headers           = Headers(
                    `Content-Type`(MediaType.application.zip),
                    `Content-Disposition`("attachment", Map(CIString("filename") -> outputfilename)),
                  )
                  response <- Ok(zipByteArray).map(_.withHeaders(headers))
                } yield response
              case None           => BadRequest("Missing data file")
            }
          }
        }
      }
  }

}
