name := "scala-frp-poc"

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-deprecation")

// grading libraries
libraryDependencies += "junit" % "junit" % "4.10" % "test"
// used for style-checking submissions
libraryDependencies += "org.scalastyle" %% "scalastyle" % "0.8.0"
// used for base64 encoding
libraryDependencies += "commons-codec" % "commons-codec" % "1.10"

// used to escape json for the submission
libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.4"
