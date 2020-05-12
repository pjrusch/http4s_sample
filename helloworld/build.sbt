scalaVersion := "2.12.7"

val http4sVersion = "0.19.0"

resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion,
  "org.mongodb.scala" %% "mongo-scala-driver" % "2.9.0"
)

scalacOptions ++= Seq("-Ypartial-unification")
