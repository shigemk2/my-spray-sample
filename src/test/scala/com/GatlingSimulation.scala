import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class GatlingSimulation extends Simulation {
  val req = http("Connection Test").get("http://127.0.0.1:8080").check(status.is(200))
  val scn = scenario("request").exec(req)

  // setUp(scn.inject(ramp(1000 users) over (50 seconds)))
  setUp(scn.inject(atOnceUsers(1)))
}
