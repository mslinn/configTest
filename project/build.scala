import com.bookish.config._
import com.bookish.config.SbtDependencies._

import sbt._
import Keys._

object Playpen extends Build {
  val Organization = "com.mslinn"

  fetchFromUrl = new java.net.URL("https://raw.github.com/Bookish/config/master/scalaBuild/Build.conf")
  //fetchFromUrl = SbtDependencies.jarUrl
  println("Playpen: fetchFromUrl=" + fetchFromUrl.toString) // this works

  // Retrieve the Lookup object from versionsTask - does not work!
  print("Playpen: versionsTask value=");      versionsTask    map { (lookup: Lookup) => println(lookup) }
  print("\nPlaypen: credentialsTask value="); credentialsTask map { (lookup: Lookup) => println(lookup) }
  print("\nPlaypen: serversTask value=");     serversTask     map { (lookup: Lookup) => println(lookup) }
  println

  // Want to set vals called creds, servers and V to the appropriate lookup instance, but this does not compile
//  val creds   = credentialsTask.flatMap { (lookup: Lookup) => lookup }
//  val servers = serversTask.flatMap     { (lookup: Lookup) => lookup }
//  val V       = versionsTask.flatMap    { (lookup: Lookup) => lookup }
//
//  println("creds=" + creds.toString)
//  println("servers=" + servers.toString)
//  println("V=" + V.toString)

  lazy val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := Organization,
    //version      := V.bkshDomainBus,
    //scalaVersion := V.scalaVersion,
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
    settings = defaultSettings //++ /*dependencyReportSettings ++*/ /*SbtDependencies ++*/ Seq(libraryDependencies)
  )
}
