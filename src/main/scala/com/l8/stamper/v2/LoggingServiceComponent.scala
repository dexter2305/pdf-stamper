package com.l8.stamper

trait LoggingServiceComponent {

  val loggingService: LoggingService

  trait LoggingService {

    def info(message: String): Unit
    def error(message: String): Unit
    def error(message: String, trace: Exception): Unit

  }

}

trait LoggingServiceComponentWithSLF4j extends LoggingServiceComponent {
  
  import org.slf4j.LoggerFactory;

  class LoggingServiceWithSlf4j extends LoggingService {

    val logger = LoggerFactory.getLogger("")

    override def info(message: String): Unit = ???

    override def error(message: String): Unit = ???

    override def error(message: String, trace: Exception): Unit = ???


  }

}