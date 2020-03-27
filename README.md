# microservices-starter
Microservices Starter Project

[![Known Vulnerabilities](https://snyk.io/test/github/skhatri/microservices-starter-java/badge.svg?targetFile=build.gradle)](https://snyk.io/test/github/skhatri/microservices-starter-java?targetFile=build.gradle)


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