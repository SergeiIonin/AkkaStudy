//#full-example
package com.example

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object TrafficLightsExample extends App {
  case object MessageFromRedLight
  case object MessageFromYellowLight
  case object MessageFromGreenLight
  case object Start

  object Light {
    def apply(duration: Int, colour: String): Light = new Light(duration, colour)
  }

  object RedLight extends Light(5000, "Red")
  object YellowLight extends Light(1000, "Yellow")
  object GreenLight extends Light(3000, "Green")

  val system: ActorSystem = ActorSystem("TrafficLights")
  val RedLightActor: ActorRef = TrafficLightsFactory.RedLightActor
  val YellowLightActor: ActorRef = TrafficLightsFactory.YellowLightActor
  val GreenLightActor: ActorRef = TrafficLightsFactory.GreenLightActor

  class Light(duration: Int, colour: String) extends Actor {
    def receive = {
      case Start if colour=="Red" => {
        println("RED")
        Thread.sleep(duration)
        YellowLightActor ! MessageFromRedLight
      }
      case MessageFromGreenLight if colour == "Red" => {
        println("RED")
        Thread.sleep(duration)
        YellowLightActor ! MessageFromRedLight
      }
      case MessageFromRedLight if colour == "Yellow" => {
        println("YELLOW")
        Thread.sleep(duration)
        GreenLightActor ! MessageFromYellowLight
      }
      case MessageFromYellowLight if colour == "Green" => {
        println("GREEN")
        Thread.sleep(duration)
        RedLightActor ! MessageFromGreenLight
      }
    }
    def props: Props = Props[Light]
  }

  RedLightActor ! Start


}
