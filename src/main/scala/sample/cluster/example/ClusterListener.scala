package sample.cluster.example

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.cluster.ClusterEvent
import akka.cluster.ClusterEvent._
import akka.cluster.typed.Cluster
import akka.cluster.typed.Subscribe

object ClusterListener {
  def apply(): Behavior[ClusterEvent.ClusterDomainEvent] =
    Behaviors.setup { ctx =>
      ctx.log.info("starting up cluster listener...")
      Cluster(ctx.system).subscriptions ! Subscribe(ctx.self, classOf[ClusterEvent.ClusterDomainEvent])

      Behaviors.receiveMessagePartial {
        case MemberUp(member) =>
          ctx.log.info("Member is Up: {}", member.address)
          Behaviors.same
        case UnreachableMember(member) =>
          ctx.log.info("Member detected as unreachable: {}", member)
          Behaviors.same
        case MemberRemoved(member, previousStatus) =>
          ctx.log.info("Member is Removed: {} after {}",
            member.address, previousStatus)
          Behaviors.same
        case LeaderChanged(member) =>
          ctx.log.info("Leader changed: " + member)
          Behaviors.same
        case any: MemberEvent =>
          ctx.log.info("Member Event: " + any.toString)
          Behaviors.same
      }
    }
}
