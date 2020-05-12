import cats.effect._
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.syntax._
import org.http4s.dsl.io._
import org.http4s.server.blaze._
import org.mongodb.scala._
import java.time.Instant

object Main extends IOApp {

  val client:MongoClient=MongoClient("mongodb://mongo:27017")
  val database: MongoDatabase = client.getDatabase("scala")
  val collection: MongoCollection[Document] = database.getCollection("scalaLog")

  val helloWorldService = HttpRoutes.of[IO] {
    case GET -> Root / "hello" / name =>
      val unixTimestamp : Long = Instant.now.getEpochSecond()
      val doc: Document = Document( "endpoint" -> "hello",
                                    "path" -> s"$name",
                                    "timestamp" -> s"$unixTimestamp")
      val observable:  Observable[Completed] = collection.insertOne(doc)
      observable.subscribe(new Observer[Completed] {
        override def onNext(result: Completed): Unit = println("Inserted")
        override def onError(e: Throwable): Unit = println("Failed")
        override def onComplete(): Unit = println("Completed")
      })
      Ok(s"Hello, $name.")
  }.orNotFound

  def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO]
      .bindHttp(8080, "0.0.0.0")
      .withHttpApp(helloWorldService)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
}
