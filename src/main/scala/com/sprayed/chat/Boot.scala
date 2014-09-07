package com.sprayed.chat

import akka.actor.ActorSystem
import akka.event.Logging
import spray.can.Http
import akka.actor.Props
import akka.io.IO

object Boot extends App {

  implicit val system = ActorSystem("spray-api-service")
  val log = Logging(system, getClass)

  val service = system.actorOf(Props[SprayApiDemoServiceActor], "spray-service")
  IO(Http) ! Http.Bind(service, interface = "localhost", port = 8080)

}