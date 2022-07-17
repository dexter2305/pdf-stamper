package com.l8.tools.stamper.controller

import cats.effect.IO
import fs2.text._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.multipart.Multipart
import org.http4s.twirl._

class StamperController extends Http4sDsl[IO] {

  val routes = HttpRoutes.of[IO] {
    case GET -> Root                     => Ok(html.index())
    case req @ POST -> Root / "generate" =>
      req.decode[Multipart[IO]] { m =>
        m.parts.find(_.name == Some("data-file")) match {
          case None       => BadRequest(s"Not file")
          case Some(part) =>
            Ok(part.body.through(utf8.decode))
        }
      }
  }

}
