//#full-example
package com.example

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object TrafficLightsExample extends App {

  case object Start
  case class Message(colour: String)
  case object Message

  object Light {
    def apply(duration: Int, colour: String): Light = new Light(duration, colour)
  }

  object RedLight extends Light(4000, "Red")
  object YellowLight extends Light(1000, "Yellow")
  object GreenLight extends Light(3000, "Green")

  val system: ActorSystem = ActorSystem("TrafficLights")
  val RedLightActor: ActorRef = TrafficLightsFactory.RedLightActor
  val YellowLightActor: ActorRef = TrafficLightsFactory.YellowLightActor
  val GreenLightActor: ActorRef = TrafficLightsFactory.GreenLightActor

  class Light(duration: Int, colour: String) extends Actor {

    def receive = {
      case Start if colour=="Red"  => {
        println("RED")
        Thread.sleep(duration)
        YellowLightActor ! Message("Yellow")
      }
        case Message("Red") => {
          println("RED")
          Thread.sleep(duration)
          YellowLightActor ! Message("Yellow") //MessageFromRedLight
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
    }
    def props: Props = Props[Light]

  RedLightActor ! Start

}
