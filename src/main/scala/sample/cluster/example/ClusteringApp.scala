package sample.cluster.example

import akka.actor.typed.ActorSystem
import akka.discovery.{Discovery, Lookup, ServiceDiscovery}
import akka.management.cluster.bootstrap.ClusterBootstrap
import akka.management.scaladsl.AkkaManagement
import com.typesafe.config.ConfigFactory

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import scala.util.{Failure, Success}


object ClusteringApp extends App {
  val config = ConfigFactory.load()
  val system = ActorSystem(ClusterListener(), "Example")
//  val system = ActorSystem(ClusterListener)
//  val serviceDiscovery = Discovery(system).discovery
//  val lookup: Future[ServiceDiscovery.Resolved] =
//    serviceDiscovery.lookup(Lookup("akka-samples-cluster-scala"), 1.second)
//
//  import scala.concurrent.ExecutionContext.Implicits.global
//  lookup onComplete {
//    case Success(v) => {
//      system.log.info("###### DEBUG: {}", v.serviceName)
//      system.log.info("###### DEBUG: {}", v.addresses)
//    }
//    case Failure(t) =>
//      system.log.info("###### DEBUG ERR: {}", t.getMessage)
//  }
  AkkaManagement(system).start()
  ClusterBootstrap(system).start()
}
