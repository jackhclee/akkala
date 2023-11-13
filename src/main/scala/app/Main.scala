package app

import akka.actor.AbstractActor
import akka.actor.typed.receptionist.Receptionist
import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}

object Actor1 {
  def apply(): Behavior[String] = {
    Behaviors.setup(context =>
      {
        context.log.info("Actor1 setup")
        new Actor1(context)
      })
  }
}
class Actor1(context: ActorContext[String]) extends AbstractBehavior[String](context) {
  var count: Int = 0
  override def onMessage(msg: String): Behavior[String] = {
    msg match {
      case "SELF" => {
        count += 1
        context.log.info("SELF [{}] count: {}", msg, context.self)
        this
      }
      case "ADD" => {
        count += 1
        context.log.info("ADD [{}] count: {}", msg, count)
        this
      }
      case "SUBTRACT" => {
        context.log.info("SUBTRACT [{}] count: {}", msg, count)
        count -= 1
        this
      }
      case "SPAWN" => {
        context.log.info("SPWAN [{}] count: {}", msg, count)
        context.spawn(Actor1(),"Actor1-1")
        this
      }
      case "PRINT" => {
        context.log.info("PRINT [{}] count: {}", msg, count)
        this
      }

      case x : String => {
        context.log.info("NOOP [{}] count: {}", x, count)
        this
      }
    }
  }
}
object Main extends App {
    println("HAHH")
    implicit val as = ActorSystem.create(Actor1(),"AS1")

    as.ref ! "SELF"
    as.ref ! "ADD"
    as.ref ! "SUBTRACT"
    as.ref ! "X"
    as.ref ! "ADD"
    as.ref ! "X"
    as.ref ! "SPWAN"
    as.ref ! "ADD"
    as.ref ! "PRINT"
//    as.receptionist ! Receptionist.
}
