FROM mozilla/sbt
COPY ./helloworld/build.sbt /http4s/build.sbt
COPY ./helloworld/src/main/scala/helloworld /http4s/src/main/scala/helloworld
WORKDIR /http4s
EXPOSE 8080
CMD ["sbt", "run"]
