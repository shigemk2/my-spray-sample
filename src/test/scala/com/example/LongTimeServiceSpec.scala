package com.example

import org.specs2.mutable.Specification
import spray.testkit.Specs2RouteTest
import spray.http._
import spray.http.StatusCodes._
import StatusCodes._

class LongTimeServiceSpec extends Specification with Specs2RouteTest with LongTimeService {
  def actorRefFactory = system

  "LongTimeService" should {

    "return complete" in {
      Get("longTime") ~> longTimeRoute ~> check {
        status.intValue should be equalTo 200
      }
    }
  }
}
