package com.example

import akka.actor.{ActorRef, ActorSystem, Props}
import com.example.TrafficLightsExample.{TrafficLightActor}

class TrafficLightsFactory(RedLightActor: ActorRef, YellowLightActor: ActorRef, GreenLightActor: ActorRef, system: ActorSystem)

object TrafficLightsFactory {
  val system = ActorSystem("TrafficLights")
  val RedLightActor: ActorRef = system.actorOf(Props(TrafficLightActor(4000, "Red")), "redLight")
  val YellowLightActor: ActorRef = system.actorOf(Props(TrafficLightActor(1000, "Yellow")), "yellowLight")
  val GreenLightActor: ActorRef = system.actorOf(Props(TrafficLightActor(3000, "Green")), "greenLight")
}
