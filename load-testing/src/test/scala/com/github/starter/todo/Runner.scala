package com.github.starter.todo

object Runner extends App {
    io.gatling.app.Gatling.main(
      Array(
        "-bf", "out/test/load-testing",
        "-rsf", "load-testing/src/test/resources",
        "-rf", "load-testing/build/reports",
        "-s", "com.github.starter.todo.TodoSimulation"
      )
    )
}
