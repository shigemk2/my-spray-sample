package com.example

import akka.actor.Actor
import spray.routing._
import spray.http._
import MediaTypes._
import scala.concurrent.duration._

trait LongTimeService extends HttpService {
  val longTimeRoute =
    path("longTime") {
      extract(_.responder) { responder =>
        responder ! SetRequestTimeout(30 seconds)
        slowQuery()
        complete("complete")
      }
    }

  def slowQuery(): Unit = {
    Thread.sleep(50000)
  }
}
