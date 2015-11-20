package com.example

import akka.actor.Actor
import spray.routing._
import spray.http._
import MediaTypes._

trait LongTimeService extends HttpService {
  val longTimeRoute =
    path("longTime") {
      get {
        respondWithMediaType( `text/html` ) {
          complete ( "hogehoge" )
        }
      }
      // extract(_.responder) { responder =>
      //   responder ! SetRequestTimeout(5 minutes)
      //   complete(slowQuery())
      // }
    }
}
