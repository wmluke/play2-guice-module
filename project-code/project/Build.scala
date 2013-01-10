import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "play2-guice-module"
  val appVersion      = "0.1-SNAPSHOT"

  val appDependencies = Seq(
    javaCore,
    javaJdbc,
    javaEbean,
    javaCore,
    javaJdbc,
    javaEbean,
    "com.google.inject" % "guice" % "3.0",
    "com.google.inject.extensions" % "guice-servlet" % "3.0",
    "com.google.inject.extensions" % "guice-multibindings" % "3.0",
    "com.google.guava" % "guava" % "13.0"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    organization := "net.bunselmeyer",

    // disable publishing the main API jar to work around
    //   https://play.lighthouseapp.com/projects/82401/tickets/898-javadoc-error-invalid-flag-g-when-publishing-new-module-local
    //   https://play.lighthouseapp.com/projects/82401/tickets/710-publish-local-is-broken
    publishArtifact in(Compile, packageDoc) := false
  )

}