package com.l8.tools.stamper

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import com.l8.stamper.v2
import fs2._
import fs2.text._
import org.http4s.Header
import org.http4s.Headers
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.multipart.Multipart
import org.http4s.twirl._
import org.typelevel.ci.CIString

import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import org.slf4j.LoggerFactory

class StamperController extends Http4sDsl[IO] {

  import domain._

  val logger = LoggerFactory.getLogger(getClass())

  val routes = HttpRoutes.of[IO] {
    case GET -> Root                     => Ok(html.index())
    case req @ POST -> Root / "generate" => {

      req.decode[Multipart[IO]] { m =>
        val data: Either[DomainError, Seq[String]]     = m.parts.find(_.name == Some("data")) match {
          case Some(part) => Right(part.body.through(utf8.decode).compile.toList.unsafeRunSync())
          case None       => Left(DomainError("No data file found"))
        }
        val template: Either[DomainError, Array[Byte]] = m.parts.find(_.name == Some("template")) match {
          case Some(part) => Right(part.body.compile.toList.map(_.toArray).unsafeRunSync())
          case None       => Left(DomainError("No template data found"))
        }
        (template, data) match {
          case (Right(template), Right(data)) =>
            logger.info(s"template byte size: ${template.length}")
            logger.info(s"csv data length ${data.size}")
            val zip: Array[Byte] = Util.process(template, data)
            //Ok(zip, Header.Raw.apply(CIString("Content-Type"), "application/zip"))
            Ok()
          case _                              => BadRequest("Invalid request")
        }

      // m.parts.find(_.name == Some("template-file")) match {
      //   case None               => BadRequest(s"Not file")
      //   case Some(templatePart) =>
      //     val l             = templatePart.contentLength
      //     //Ok(templatePart.body.through(utf8.decode), Header.Raw.apply(CIString("Content-Type"), "application/zip"))
      //     val templateBytes = templatePart.body.compile.toList.map(_.toArray).unsafeRunSync()

      //     Ok(s"Template file Content-Type: ${templatePart.contentType} Content-Length: ${templateBytes.length}")
      // }

      }

    }
  }

}
