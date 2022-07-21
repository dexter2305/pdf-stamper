package com.l8.tools.stamper

import cats.effect.IO
import fs2._
import fs2.text._
import org.http4s.Header
import org.http4s.Headers
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.multipart.Multipart
import org.http4s.twirl._
import org.slf4j.LoggerFactory
import org.typelevel.ci.CIString

class StamperController extends Http4sDsl[IO] {

  import domain._

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
                  line <- datapart.body.through(utf8.decode)
                } yield line

                Ok {
                  for {
                    templateBytes <- templateByteStream.compile.toList
                    datalines     <- dataLineStream.compile.toList
                    templateSize  = templateBytes.size
                    dataLineCount = datalines.size
                  } yield s"template size $templateSize && dataline count $dataLineCount"
                }
              case None => BadRequest("Missing data file")
            }
          }
        }
      }
  }

}
