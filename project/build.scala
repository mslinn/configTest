import com.bookish.config._
import com.bookish.config.SbtDependencies._

import sbt._
import Keys._

object Playpen extends Build {
  val Organization = "com.mslinn"

  configUrl := "https://raw.github.com/Bookish/config/master/scalaBuild/Build.conf"
  //configUrl := SbtDependencies.jarUrl
  println("Playpen: configUrl=" + configUrl) // how to display this?

  // Retrieve the Lookup object from versionsTask - does not work! What is the right way of doing this?
  print("Playpen: versionsTask value=");      configVersionsLookup    map { (lookup: Lookup) => println(lookup) }
  print("\nPlaypen: credentialsTask value="); configCredentialsLookup map { (lookup: Lookup) => println(lookup) }
  print("\nPlaypen: serversTask value=");     configServersLookup     map { (lookup: Lookup) => println(lookup) }
  println

  println("configVersionsLookup.get(\"bkshDomainBus\")" + configVersionsLookup.key.get("bkshDomainBus"))

  // How can I obtain the Lookup instances from the plug-in?!?!?!

  lazy val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := Organization,
    //version      := configVersionsLookup.key.get("bkshDomainBus"), // does not compile; I would prefer to write V.bkshDomainBus
    //scalaVersion := configVersionsLookup.key.get("scalaVersion"),  // does not compile; I would prefer to write V.scalaVersion
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
    //credentials += Credentials("Artifactory Realm", "ci-sb-1.obi.int", credentials.userid, credentials.password),
    //publishTo <<= (version) { version: String =>
      //if (version.trim.endsWith("SNAPSHOT"))
        //Some("bookish" at servers.artifactory + "libs-snapshot-local/")
      //else
        //Some("bookish" at servers.artifactory + "libs-release-local/")
    //},
    initialCommands +=
      """
        |println("Type :load repl")
      """.stripMargin
  )

  import com.micronautics.dependencyReport.DependencyReport._
  lazy val DomainBus = Project(
    id = "playpen",
    base = file("."),
    settings = defaultSettings ++ SbtDependencies.settings //++ /*dependencyReportSettings ++*/ /*SbtDependencies ++*/ Seq(libraryDependencies)
  )
}
