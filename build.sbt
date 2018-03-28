name := "lagom-assignment-01"
organization in ThisBuild := "com.knoldus"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.12.4"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % Test
/*
lagomUnmanagedServices in ThisBuild := Map("externalservice" -> "https://jsonplaceholder.typicode.com")*/

lazy val `emp-lagom` = (project in file("."))
  .aggregate(`emp-lagom-api`, `emp-lagom-impl`)

lazy val `emp-lagom-api` = (project in file("emp-lagom-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `emp-lagom-impl` = (project in file("emp-lagom-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`emp-lagom-api`)

