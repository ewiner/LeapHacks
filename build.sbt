scalaVersion := "2.10.2"

name := "LeapHacks"

libraryDependencies <+= (scalaVersion)(
  "org.scala-lang" % "scala-reflect" % _)
