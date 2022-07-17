import sbt._

object Dependencies {

  lazy val itextVersion  = "5.5.6"
  lazy val http4sVersion = "0.23.7"

  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.2.11"

  //ce3
  lazy val cats_effect = "org.typelevel" %% "cats-effect" % "3.3.3"

  //http4s
  lazy val http4s_dsl          = "org.http4s" %% "http4s-dsl"          % http4sVersion
  lazy val http4s_blaze_server = "org.http4s" %% "http4s-blaze-server" % http4sVersion
  lazy val http4s_circe        = "org.http4s" %% "http4s-circe"        % http4sVersion
  lazy val http4s_twirl        = "org.http4s" %% "http4s-twirl"        % http4sVersion
  lazy val http4s_all          = Seq(http4s_blaze_server, http4s_dsl, http4s_circe, http4s_twirl)

  //fs2
  lazy val fs2_core = "co.fs2" %% "fs2-core" % "3.2.4"

  //pdf
  lazy val itext_xmlworker = "com.itextpdf.tool" % "xmlworker" % itextVersion
  lazy val itext_itextpdf  = "com.itextpdf"      % "itextpdf"  % itextVersion

  //loggers
  lazy val slf4j_api    = "org.slf4j" % "slf4j-api"    % "1.7.32"
  lazy val slf4j_scribe = "com.outr" %% "scribe-slf4j" % "3.0.2"
}
