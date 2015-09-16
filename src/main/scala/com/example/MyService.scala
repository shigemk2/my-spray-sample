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
      addPage
    } ~
    post {
      formFields( 'todo ) { todo =>
        Todos.add( todo )
        addPage
      }
    }
  }

  def addPage: Route = {
    respondWithMediaType( `text/html` ) {
      complete ( add( Todos.all ) )
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

  def add( items: Seq[String] = Seq.empty[String] ) =
    <html>
      <body>
        <h2>TODO登録</h2>
        <form name="form1" method="POST" action="add">
          <div>
            <span><input type="text" name="todo" value="" style="width:60%;" /></span>
            <span><input type="submit" name="submit" value="登録" /></span>
          </div>
        </form>
        <div>
          <ul>
            { for { item <- items } yield <li>{item}</li> }
          </ul>
        </div>
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
