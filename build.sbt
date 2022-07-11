import Dependencies._

Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / scalaVersion       := "2.13.8"
ThisBuild / version            := "0.1.0-SNAPSHOT"
ThisBuild / organization       := "com.swag.ti"
ThisBuild / organizationName   := "swag"
ThisBuild / watchBeforeCommand := Watch.clearScreen

lazy val root = (project in file("."))
  .settings(
    name := "pdf-stamper",
    libraryDependencies ++= Seq(
      itext_xmlworker withJavadoc,
      itext_itextpdf withJavadoc,
      slf4j_api withJavadoc,
      slf4j_scribe,
      scalaTest % Test withJavadoc,
    ),
  )
  .enablePlugins(SbtTwirl)
