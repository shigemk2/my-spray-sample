package com.example

import akka.actor.Actor
import spray.routing._
import spray.http._
import MediaTypes._

class MyServiceActor extends Actor with MyService {

  def actorRefFactory = context

  def receive = runRoute(myRoute)
}


trait MyService extends HttpService {
  val myRoute =
    path( "" ) {
      get {
        respondWithMediaType( `text/html` ) {
          complete {
            <html>
            <body>
            <h1>Say hello to <i>spray-routing</i> on <i>spray-can</i>!</h1>
            </body>
            </html>
          }
        }
      }
    } ~
  path( "add" ) {
    get {
      respondWithMediaType( `text/html` ) {
        complete {
          <html>
            <body>
              <h1>TODO管理</h1>
              <ul>
                <li><a href="/add" title="TODO登録">TODO登録</a></li>
              </ul>
            </body>
          </html>
        }
      }
    }
  }
}
