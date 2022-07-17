package com.l8.tools.stamper.controller

import cats.effect.IO

class StamperModule() {
  val controller = new StamperController()
}

object StamperModule {

  def create(): IO[StamperModule] = IO.delay(new StamperModule)
}
