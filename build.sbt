import sbt.Keys._

name := "dbinterview"

version := "1.0"

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test",
  "net.databinder.dispatch" %% "dispatch-core" % "0.11.2",
  "org.scalaj" %% "scalaj-http" % "0.3.16",
  "ch.qos.logback" % "logback-classic" % "1.0.3",
  "ch.qos.logback" % "logback-core" % "1.0.3",
  "org.json4s" %% "json4s-jackson" % "3.2.10",
  "com.github.nscala-time" %% "nscala-time" % "1.2.0"
)
