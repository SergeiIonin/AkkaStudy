package com.example

import akka.actor.{ActorRef, ActorSystem, Props}
import com.example.TrafficLightsExample.{GreenLight, RedLight, YellowLight}

class TrafficLightsFactory(RedLightActor: ActorRef, YellowLightActor: ActorRef, GreenLightActor: ActorRef, system: ActorSystem)

object TrafficLightsFactory {
  val system = ActorSystem("TrafficLights")
  val RedLightActor: ActorRef = system.actorOf(Props(RedLight), "redLight")
  val YellowLightActor: ActorRef = system.actorOf(Props(YellowLight), "yellowLight")
  val GreenLightActor: ActorRef = system.actorOf(Props(GreenLight), "greenLight")
}
