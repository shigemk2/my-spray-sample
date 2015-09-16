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
          complete ( index )
        }
      }
    } ~
  path( "add" ) {
    get {
      respondWithMediaType( `text/html` ) {
        complete ( add )
      }
    } ~
    post {
      respondWithMediaType( `text/html` ) {
        complete ( add )
      }
    }
  }

  lazy val index =
    <html>
      <body>
        <h1>TODO管理</h1>
        <ul>
          <li><a href="/add" title="TODO登録">TODO登録</a></li>
        </ul>
      </body>
    </html>

  lazy val add =
    <html>
      <body>
        <h1>TODO管理</h1>
        <form name="form1" method="POST" action="add">
          <div>
            <span><input type="text" name="todo" value="" style="width:60%;" /></span>
            <span><input type="submit" name="submit" value="登録" /></span>
          </div>
        </form>
      </body>
    </html>
}

object Todos {
  private var items: Seq[String] = Seq()
  def add( item: String ): Unit = synchronized {
    items = items :+ item
  }

  def remove( item: String ): Unit = {
    val ( head, tail ) = items.span( _ != item )
    synchronized {
      if ( tail.size == 0 )
        head
      else
        head ++ tail.tail
    }
  }
  def all: Seq[String] = items
}
