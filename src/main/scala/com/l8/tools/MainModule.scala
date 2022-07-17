package com.l8.tools

import cats.effect.IO
import com.l8.tools.stamper.controller.StamperModule
import org.http4s.blaze.server.BlazeServerBuilder

class MainModule {

  def start(): IO[Unit] = {

    for {
      stamperModule <- StamperModule.create()
      api = stamperModule.controller.routes.orNotFound
      _ <- BlazeServerBuilder[IO].bindHttp(8080, "localhost").withHttpApp(api).serve.compile.drain

    } yield ()
  }
}
