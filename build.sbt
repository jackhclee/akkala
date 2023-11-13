scalaVersion := "2.13.12"

resolvers += "Akka library repository".at("https://repo.akka.io/maven")

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % "2.9.0",
  "org.slf4j"          % "slf4j-simple"     % "1.7.36"
)
