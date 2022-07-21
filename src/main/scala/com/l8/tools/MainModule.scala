package com.l8.tools

import cats.effect.IO
import com.l8.tools.stamper.StamperModule
import org.http4s.blaze.server.BlazeServerBuilder

class MainModule {

  def start(): IO[Unit] = {

    for {
      stamperModule <- StamperModule.create()
      hostname = "localhost"
      port = 8080
      api = stamperModule.controller.routes.orNotFound
      _ <- BlazeServerBuilder[IO].bindHttp(port, hostname).withHttpApp(api).resource.use { _ =>
        IO {
          println(s"Go to: http://$hostname:$port/")
          println("Press any key to exit ...")
          scala.io.StdIn.readLine()
        }
      }
    } yield ()
  }
}
