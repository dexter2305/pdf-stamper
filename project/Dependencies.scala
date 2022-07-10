import sbt._

object Dependencies {

  def itextVersion = "5.5.6"

  lazy val scalaTest       = "org.scalatest"    %% "scalatest"    % "3.2.11"
  lazy val itext_xmlworker = "com.itextpdf.tool" % "xmlworker"    % itextVersion
  lazy val itext_itextpdf  = "com.itextpdf"      % "itextpdf"     % itextVersion
  lazy val scribe          = "com.outr"         %% "scribe"       % "3.10.0"
  lazy val slf4j_api       = "org.slf4j"         % "slf4j-api"    % "1.7.32"
  lazy val slf4j_scribe    = "com.outr"         %% "scribe-slf4j" % "3.0.2"
}
