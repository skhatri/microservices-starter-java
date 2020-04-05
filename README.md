# microservices-starter
Microservices Starter Project

[![Build](https://travis-ci.com/skhatri/microservices-starter-java.svg?branch=master)](https://travis-ci.com/github/skhatri/microservices-starter-java)
[![Code Coverage](https://img.shields.io/codecov/c/github/skhatri/microservices-starter-java/master.svg)](https://codecov.io/github/skhatri/microservices-starter-java?branch=master)
[![CII Best Practices](https://bestpractices.coreinfrastructure.org/projects/3827/badge)](https://bestpractices.coreinfrastructure.org/projects/3827)
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

### container
build image using
```
gradle jib 
```