package com.l8.tools

import cats.effect.IOApp
import cats.effect.{ExitCode, IO}

object Boot extends IOApp {

  lazy val keyPressToTerminate: IO[Unit] = for {
    _ <- IO.delay(println("Press ENTER to terminate .. "))
    _ <- IO.delay(scala.io.StdIn.readLine())
  } yield ()

  override def run(args: List[String]): IO[ExitCode] = //(new MainModule()).start().as(ExitCode.Success)
    IO.race((new MainModule().start()), keyPressToTerminate).as(ExitCode.Success)

}
