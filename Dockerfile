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

#FROM openjdk:8 as stage0
#LABEL snp-multi-stage="intermediate"
#LABEL snp-multi-stage-id="be44da99-cd2e-485a-aa78-e40f809d21b4"
#WORKDIR /opt/docker
#COPY 1/opt /1/opt
#COPY 2/opt /2/opt
#USER root
#RUN ["chmod", "-R", "u=rX,g=rX", "/1/opt/docker"]
#RUN ["chmod", "-R", "u=rX,g=rX", "/2/opt/docker"]
#RUN ["chmod", "u+x,g+x", "/1/opt/docker/bin/app-one-master"]
#RUN ["chmod", "u+x,g+x", "/1/opt/docker/bin/sample_cluster_simple_app"]
#RUN ["chmod", "u+x,g+x", "/1/opt/docker/bin/sample_cluster_stats_app"]
#RUN ["chmod", "u+x,g+x", "/1/opt/docker/bin/sample_cluster_transformation_app"]
#
#FROM openjdk:8
#USER root
#RUN id -u demiourgos728 1>/dev/null 2>&1 || (( getent group 0 1>/dev/null 2>&1 || ( type groupadd 1>/dev/null 2>&1 && groupadd -g 0 root || addgroup -g 0 -S root )) && ( type useradd 1>/dev/null 2>&1 && useradd --system --create-home --uid 1001 --gid 0 demiourgos728 || adduser -S -u 1001 -G root demiourgos728 ))
#WORKDIR /opt/docker
#COPY --from=stage0 --chown=demiourgos728:root /1/opt/docker /opt/docker
#COPY --from=stage0 --chown=demiourgos728:root /2/opt/docker /opt/docker
#USER 1001:0
#ENTRYPOINT ["/opt/docker/bin/akka-sample-cluster-scala"]
#CMD []
