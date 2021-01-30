# microservices-starter-java
Microservices Starter Project with Reactive, Reactive DB Driver, Gradle Kotlin DSL, Checkstyle, Coverage, SonarQube, gRPC, EnvoyProxy and Grafana dashboards

[![Build](https://travis-ci.com/skhatri/microservices-starter-java.svg?branch=master)](https://travis-ci.com/github/skhatri/microservices-starter-java)
[![Code Coverage](https://img.shields.io/codecov/c/github/skhatri/microservices-starter-java/master.svg)](https://codecov.io/github/skhatri/microservices-starter-java?branch=master)
[![CII Best Practices](https://bestpractices.coreinfrastructure.org/projects/3827/badge)](https://bestpractices.coreinfrastructure.org/projects/3827)
[![Maintainability](https://api.codeclimate.com/v1/badges/587771b52b48dfc7c333/maintainability)](https://codeclimate.com/github/skhatri/microservices-starter-java/maintainability)
[![Known Vulnerabilities](https://snyk.io/test/github/skhatri/microservices-starter-java/badge.svg?targetFile=build.gradle.kts)](https://snyk.io/test/github/skhatri/microservices-starter-java?targetFile=build.gradle.kts)

### Development
[Developer Guide](DEV.md)

### logging
log4j2

### code analysis
sonar

### testing
junit 5

### code-coverage
Jacoco

### code-style
Google Checkstyle modified to be compatible with 8.30.
Method Length, File Length, Cyclomatic Complexity have been added.

### load-testing
Gatling

Load test can be run using one of the following two approaches
```
gradle load-testing:runTest
IDE - com.github.starter.todo.Runner
```

### vulnerability

Install snyk and authenticate for CLI session
```
npm install -g snyk
snyk auth
```

Publish results using

```
snyk monitor --all-sub-projects
```

### grpc
Grpc server for Todo runs on port 8100. Rest Endpoint /todo/ can be made to forward to gRPC using ```use.grpc```
The forwarding of REST request to gRPC is done for illustration purpose.


### build docker
A jar for each web engines is packed into the generated docker image. Pass environment variable ENGINE to activate one.

```
#build
./scripts/pack/builder.sh

#run
docker run -e ENGINE=reactor-netty --cpus 1.0 -m 756m -p 8080:8080 -it starter-java:latest
docker run -e ENGINE=undertow --cpus 1.0 -m 756m -p 8080:8080 -it starter-java:latest 
```

