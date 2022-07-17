package com.l8.tools

import cats.effect.IOApp
import cats.effect.{ExitCode, IO}

object Boot extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = (new MainModule()).start().as(ExitCode.Success)

}
