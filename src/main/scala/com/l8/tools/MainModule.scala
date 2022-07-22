package com.l8.tools

import cats.effect.IO
import com.l8.tools.stamper.StamperModule
import org.http4s.blaze.server.BlazeServerBuilder
import java.net.InetAddress

class MainModule {

  def start(): IO[Unit] = {

    for {
      stamperModule <- StamperModule.create()
      port = sys.env.get("HTTP_PORT").flatMap(_.toIntOption).getOrElse(8080)
      host = sys.env.getOrElse("HTTP_HOST", InetAddress.getLocalHost.getHostName())
      api  = stamperModule.controller.routes.orNotFound
      _ <- BlazeServerBuilder[IO].bindHttp(port, host).withHttpApp(api).resource.use { x => IO.never }
    } yield ()
  }
}
