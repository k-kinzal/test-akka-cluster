package sample.cluster.example

import akka.actor.typed.ActorSystem
import akka.management.cluster.bootstrap.ClusterBootstrap
import akka.management.scaladsl.AkkaManagement
import com.typesafe.config.ConfigFactory

object ClusteringApp extends App {
  val config = ConfigFactory.load()
  val system = ActorSystem(ClusterListener(), "Example")

  AkkaManagement(system).start()
  ClusterBootstrap(system).start()
}
