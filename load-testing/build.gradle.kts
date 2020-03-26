plugins {
    id("scala")
}

dependencies {
    implementation("org.scala-lang:scala-library:2.12.10")
    implementation("org.scala-lang:scala-compiler:2.12.10")
    implementation("io.gatling:gatling-core:3.3.1")
    implementation("io.gatling:gatling-http:3.3.1")
    implementation("io.gatling:gatling-http-client:3.3.1")
    implementation("io.gatling:gatling-charts:3.3.1")
    implementation("io.gatling:gatling-app:3.3.1")
    implementation("io.gatling.highcharts:gatling-charts-highcharts:3.3.1")
}

task("runTest", JavaExec::class) {
    main = "io.gatling.app.Gatling"
    classpath = sourceSets["test"].runtimeClasspath
    args = listOf(
        "-bf", "${sourceSets["test"].output.dirs}",
        "-rsf", "${sourceSets["test"].resources}",
        "-rf", "$projectDir/build/reports/gatling",
        "-s", "com.github.starter.todo.TodoSimulation"
    )
}