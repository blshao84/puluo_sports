
name := "puluo"

version := "0.1"

organization := "com.puluo"

scalaVersion := "2.10.3"

resolvers ++= Seq(
             "snapshots"     at "http://repo1.maven.org/maven2/",
            "releases"        at "http://repo1.maven.org/maven2/"
        )

jetty(port=1234)

unmanagedResourceDirectories in Test <+= (baseDirectory) { _ / "src/main/webapp" }

scalacOptions ++= Seq("-deprecation", "-unchecked")

javacOptions ++= Seq("-encoding", "UTF-8")

conflictWarning in ThisBuild := ConflictWarning.disable

lazy val root = (project in file(".")).aggregate(lift_server, core)
lazy val core = project
lazy val lift_server = project.dependsOn(core)
