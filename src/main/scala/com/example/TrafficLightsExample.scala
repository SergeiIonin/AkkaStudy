//#full-example
package com.example

import akka.actor.{Actor, ActorRef, Props}

object TrafficLightsExample extends App {

  case object Start
  case class Message(colour: String)

  object TrafficLightActor {
    def apply(duration: Int, colour: String): TrafficLightActor = new TrafficLightActor(duration, colour)
  }

  val RedLightActor: ActorRef = TrafficLightsFactory.RedLightActor
  val YellowLightActor: ActorRef = TrafficLightsFactory.YellowLightActor
  val GreenLightActor: ActorRef = TrafficLightsFactory.GreenLightActor


  class TrafficLightActor(duration: Int, colour: String) extends Actor {

    def receive = {
      case Start if colour=="Red"  => {
        println("RED")
        Thread.sleep(duration)
        YellowLightActor ! Message("Yellow")
      }
        case Message("Red") => {
          println("RED")
          Thread.sleep(duration)
          YellowLightActor ! Message("Yellow")
        }
        case Message("Yellow") => {
          println("YELLOW")
          Thread.sleep(duration)
          GreenLightActor ! Message("Green")
        }
        case Message("Green") => {
          println("GREEN")
          Thread.sleep(duration)
          RedLightActor ! Message("Red")
        }
      }

    def props: Props = Props[TrafficLightActor]
    }

  RedLightActor ! Start

}
