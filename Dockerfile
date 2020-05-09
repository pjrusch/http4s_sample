FROM mozilla/sbt AS build
WORKDIR /http4s
RUN yes "" | sbt -sbt-version 1.2.8 new http4s/http4s.g8 -b 0.21
WORKDIR /http4s/quickstart
RUN sbt -sbt-version 1.2.8 update

FROM mozilla/sbt
COPY --from=build /http4s/quickstart /quickstart
COPY --from=build /root/.ivy2 /root/.ivy2
COPY --from=build /root/.sbt /root/.sbt
WORKDIR /quickstart
EXPOSE 8080
CMD ["sbt", "-sbt-version", "1.2.8", "run"]
