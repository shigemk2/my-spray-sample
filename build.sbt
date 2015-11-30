organization  := "com.example"

version       := "0.1"

scalaVersion  := "2.11.7"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers += "repo.typesafe.com" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= {
  val akkaV = "2.3.9"
  val sprayV = "1.3.3"
  Seq(
    "io.spray"            %%  "spray-can"     % sprayV,
    "io.spray"            %%  "spray-routing" % sprayV,
    "io.spray"            %%  "spray-testkit" % sprayV  % "test",
    "com.typesafe.akka"   %%  "akka-actor"    % akkaV,
    "com.typesafe.akka"   %%  "akka-testkit"  % akkaV   % "test",
    "org.specs2"          %%  "specs2-core"   % "2.3.11" % "test",
    "com.aerospike"       %  "aerospike-client" % "latest.integration",
    "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.1.5" % "test",
    "io.gatling"            % "gatling-test-framework"    % "2.1.5" % "test"
  )
}

enablePlugins(GatlingPlugin)

// assembly
// テストのスキップ
test in assembly := {}
// メインクラスの明示
mainClass in assembly := Some("com.example.Boot")
// どんなjarを作るか
assemblyJarName in assembly := "my-spray-sample.jar"

// 競合しているものの解消
packageOptions in assembly ~= { pos =>
  pos.filterNot { po =>
    po.isInstanceOf[Package.MainClass]
  }
}

Revolver.settings
