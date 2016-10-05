name          := "reader-article"
organization  := "com.emarsys"
version       := "0.0.1"
scalaVersion  := "2.11.8"

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-unchecked",
  "-feature",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-Ywarn-dead-code",
  "-Xlint",
  "-Xfatal-warnings"
)

libraryDependencies ++= Seq(
  "org.scalatest"    %% "scalatest" % "3.0.0" % "test",
  "com.github.etaty" %% "rediscala" % "1.6.0",
  "org.typelevel"    %% "cats"      % "0.4.1"
)
