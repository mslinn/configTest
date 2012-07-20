import com.bookish.config.{SbtProjectConfig, V, creds, servers}
import sbt._
import Keys._

object Playpen extends Build {
  val Organization = "com.micronautics"

  //SbtProjectConfig.fetchFromUrl     = "https://raw.github.com/Bookish/config/master/scalaBuild/Build.conf"
  //SbtProjectConfig.outerSectionName = "bookishDeps"
  SbtProjectConfig.fetchFromUrl     = "https://raw.github.com/Bookish/config/v3/src/main/resources/definitions.conf"
  SbtProjectConfig.quiet            = true

  lazy val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := "com.micronautics",
    version      := V("bkshDomainBus"),
    scalaVersion := V("scala"),
    crossPaths   := false
  )

  lazy val defaultSettings = buildSettings ++ Seq(
    parallelExecution in Test := false,
    scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-unchecked"),
    logLevel in compile := Level.Warn,
    credentials += Credentials("Artifactory Realm", "ci-sb-1.obi.int", creds("userid"), creds("password")),
    publishTo <<= (version) { version: String =>
      if (version.trim.endsWith("SNAPSHOT"))
        Some("art" at servers("artifactory") + "libs-snapshot-local/")
      else
        Some("art" at servers("artifactory") + "libs-release-local/")
    }
  )

  lazy val PlaypenProject = Project(
    id = "playpen",
    base = file("."),
    settings = defaultSettings
  )
}
