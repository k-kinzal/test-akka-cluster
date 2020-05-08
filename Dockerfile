FROM hseeberger/scala-sbt:11.0.6_1.3.9_2.13.1 as builder

WORKDIR /scala
COPY build.sbt /scala/build.sbt
COPY project /scala/project
RUN sbt update

COPY . /scala
RUN sbt pack


FROM openjdk:8

WORKDIR /opt/docker

COPY --from=builder /scala/target/pack/bin /opt/docker/bin
COPY --from=builder /scala/target/pack/lib /opt/docker/lib

ENTRYPOINT ["/opt/docker/bin/akka-sample-cluster-scala"]
