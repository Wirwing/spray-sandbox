package com.sprayed.chat

import akka.actor.{ Actor, Props }
import akka.event.Logging
import spray.http._
import spray.routing._
import MediaTypes._

class SprayApiDemoServiceActor extends Actor with SprayApiDemoService {

  def actorRefFactory = context
  def receive = runRoute(sprayApiDemoRoute)

}

trait SprayApiDemoService extends HttpService {

  val sprayApiDemoRoute = pathPrefix("api") {
    path("elevation-service" / DoubleNumber / DoubleNumber) { (long, lat) =>
      requestContext =>
        val elevationService = actorRefFactory.actorOf(Props(new ElevationService(requestContext)))
        elevationService ! ElevationService.Process(long, lat)
    }

  }

}