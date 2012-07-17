import com.bookish.config._
import com.bookish.config.SbtDependencies._

import sbt._
import Keys._

object Playpen extends Build {
  val Organization = "com.mslinn"

  configUrl := "https://raw.github.com/Bookish/config/master/scalaBuild/Build.conf"
  //configUrl := SbtDependencies.jarUrl
  println(SbtDependencies.settings)

  lazy val buildSettings = Defaults.defaultSettings ++ SbtDependencies.settings ++ Seq(
//    organization := V.Organization,
//    version      := V.bkshDomainBus,
//    scalaVersion := V.scala, // V.scala is set by the plug-in, if the config file defines it properly
    crossPaths   := false
  )

  lazy val defaultSettings = buildSettings ++ Seq(
    parallelExecution in Test := false,
    resolvers ++= Seq(
      "Typesafe Snapshots"   at "http://repo.typesafe.com/typesafe/snapshots"
    ),
    scalacOptions in (Compile, console) += "-Yrepl-sync",
    scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-unchecked"),
    javacOptions  ++= Seq("-Xlint:deprecation", "-Xcheckinit"),
    logLevel in compile := Level.Warn,
//    credentials += Credentials("Artifactory Realm", "ci-sb-1.obi.int", creds.userid, creds.password), // creds._ set by plug-in
//    publishTo <<= (version) { version: String =>
//      if (version.trim.endsWith("SNAPSHOT"))
//        Some("bookish" at servers.artifactory + "libs-snapshot-local/") // servers._ set by plug-in
//      else
//        Some("bookish" at servers.artifactory + "libs-release-local/")
//    },
    initialCommands +=
      """
        |println("Type :load repl")
      """.stripMargin
  )

  lazy val DomainBus = Project(
    id = "playpen",
    base = file("."),
    settings = defaultSettings ++ SbtDependencies.settings
  )
}
