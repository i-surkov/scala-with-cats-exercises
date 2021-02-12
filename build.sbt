name := "scala-with-cats-exercises"

version := "0.1"

scalaVersion := "2.13.4"

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked",
  "-language:existentials",
  "-language:higherKinds",
  "-language:postfixOps",
  "-Ywarn-dead-code",
  "-Xcheckinit",
  "-Xlint"
)

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "2.4.1",
  "org.typelevel" %% "mouse" % "0.26.2"
)
