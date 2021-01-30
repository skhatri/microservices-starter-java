package com.github.starter.todo

import io.gatling.core.Predef._
import io.gatling.core.structure.PopulationBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration._

object Todo {
  val server = Option(System.getenv("HOST")).getOrElse("localhost")
  val port = Option(System.getenv("PORT")).map(_.toInt).getOrElse(8080)
  val ssl = Option(System.getenv("SSL")).map(_.toBoolean).getOrElse(false)
  val http2Enabled = Option(System.getenv("HTTP2")).map(_.toBoolean).getOrElse(false)
  val serviceType = Option(System.getenv("SERVICE_TYPE")).getOrElse("default")

  val protocol = if (ssl) "https" else "http"
  val base = s"$protocol://$server:$port"
  val randomIndex = Iterator.continually(Map("index" -> (Math.random() * 2).toInt))

  val todoPath = s"/todo/${serviceType}"
  val searchPath = serviceType match {
    case "" => "/todo.search"
    case x => s"/todo/$x/"
  }

  def findByIdPath(id:String) = serviceType match {
    case "" => s"/todo.findById?value=$id"
    case x => s"/todo/$x/$id"
  }
  val addPath = serviceType match {
    case "" => "/todo.save"
    case x => s"/todo/$x/"
  }

  def updatePath(id:String) = serviceType match {
    case "" => "/todo.update"
    case x => s"/todo/$x/$id"
  }

  val warmupUrl = serviceType match {
    case "" => "/todo.liveness"
    case _ => "/liveness"
  }

  private val httpProtocolBase = http
    .baseUrl(base)
    .warmUp(s"$base$warmupUrl")
    .doNotTrackHeader("1")
    .acceptHeader("application/json;q=0.9,*/*;q=0.8")
    .contentTypeHeader("application/json")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36")

  val httpProtocol = if (http2Enabled) {
    httpProtocolBase.enableHttp2.http2PriorKnowledge(Map(s"$server:$port" -> true)).copy(useOpenSsl = true)
  } else {
    httpProtocolBase
  }


  val feeder = csv("tasks.csv").random

  val search = exec(http("Search - Home").get(warmupUrl))
    .pause(1)
    .exec(http("Search - List")
      .get(searchPath))
    .pause(2)
    .exec(http("Search - Find")
      .get(findByIdPath("1")))
    .pause(1)

  val add = exec(http("Add - Home").get(warmupUrl))
    .pause(1)
    .feed(feeder)
    .exec(http("Add Post")
      .post(addPath).body(StringBody(
      """{
        |      "description": "${task}",
        |      "action_by": "user1",
        |      "created": "2020-03-20T13:00:00Z",
        |      "status": "NEW",
        |      "updated": "2020-03-20T13:00:00Z"
        |}""".stripMargin)).check(status.is(200)))
    .pause(2)
    .exec(http("Add - Select")
      .get(findByIdPath("1")).check(status.is(200)))
    .pause(1)

  val edit = feed(randomIndex).exec(http("Edit - Search")
    .get(searchPath)
    .check(jsonPath("$.data[0].id").saveAs("taskId"))
  ).pause(1)
    .feed(feeder)
    .exec(http("Edit - Post")
      .post(updatePath("$taskId")).body(StringBody(
      """{
        |      "id": "${taskId}",
        |      "description": "${task}",
        |      "action_by": "user1",
        |      "created": "2020-03-20T13:00:00Z",
        |      "status": "NEW",
        |      "updated": "2020-03-20T13:00:00Z"
        |}""".stripMargin)).check(status.is(200)))

    .pause(2)

}

class TodoSimulation extends Simulation {

  val searchScenario = scenario("Todo Search").during(2 minutes) {
    exec(Todo.search)
      .pause(3 seconds, 5 seconds)
  }
  val addScenario = scenario("Todo Add").during(2 minutes) {
    exec(Todo.add)
      .pause(3 seconds, 7 seconds)
  }

  val editScenario = scenario("Todo Edit").during(1 minutes) {
    exec(Todo.edit)
      .pause(3 seconds, 7 seconds)
  }

  setUp(
    searchScenario.inject(constantConcurrentUsers(20) during (10 seconds)),
    addScenario.inject(constantConcurrentUsers(20) during (10 seconds)),
    editScenario.inject(constantConcurrentUsers(2) during (10 seconds))
  ).protocols(Todo.httpProtocol).maxDuration(2 minutes)
}


class ListSimulation extends Simulation {

  val searchScenarioClosed: PopulationBuilder = scenario("Todo Search").during(5 minutes) {
    exec(Todo.search)
  }.inject(
    constantConcurrentUsers(10) during (30 seconds),
    rampConcurrentUsers(10) to (20) during (10 seconds)
  )

  val searchScenarioOpen: PopulationBuilder = scenario("Todo Search").during(5 minutes) {
    exec(Todo.search)
  }.inject(
    heavisideUsers(2000) during (30 seconds)
  )


  setUp(searchScenarioOpen).protocols(Todo.httpProtocol).maxDuration(2 minutes)
}

