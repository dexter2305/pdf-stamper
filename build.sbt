import Dependencies._

Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / scalaVersion       := "2.13.8"
ThisBuild / version            := "0.1.0-SNAPSHOT"
ThisBuild / organization       := "com.l8.tools"
ThisBuild / organizationName   := "swag"
ThisBuild / watchBeforeCommand := Watch.clearScreen
ThisBuild / scalacOptions      := Seq("-deprecation", "-Xlint:unused", "-Xlint:infer-any")

lazy val root = (project in file("."))
  .settings(
    name := "pdf-stamper",
    libraryDependencies ++= Seq(
      cats_effect withJavadoc,
      http4s_blaze_server withJavadoc,
      http4s_circe withJavadoc,
      http4s_dsl withJavadoc,
      http4s_twirl withJavadoc,
      fs2_core withJavadoc,
      itext_xmlworker withJavadoc,
      itext_itextpdf withJavadoc,
      slf4j_api withJavadoc,
      slf4j_scribe,
      scalaTest % Test withJavadoc,
    ),
  )
  .enablePlugins(SbtTwirl)
Compile / mainClass := Some("com.l8.tools.Boot")
