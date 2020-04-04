
### Importing App
```
./gradlew cleanIdea idea
```

### Build App
```
gradle clean build
```

### Running App
1. Run Application.kt from IDE
2. Run using command line \
    ```gradle runApp```
3. Run as fat jar

```
gradle clean build
java -jar app/build/libs/app.jar
```

### Accessing App
```
open http://localhost:8080/todo/search
```

#### Enable this flag when enabling SSL and HTTP/2
```
chrome://flags/#allow-insecure-localhost
```


### Performance test

Run the app with one of the web servers
```
gradle runApp -Pserver.type=tomcat
gradle runApp -Pserver.type=undertow
gradle runApp -Pserver.type=reactor-netty
gradle runApp -Pserver.type=jetty

or
./scripts/run-app.sh <server.type>
```

And Run simple performance with 
```
gradle load-testing:runTest -Psimulation=ListSimulation

or

./scripts/perf.sh
``` 

### Time Based Load Testing
```
brew install nghttp2
#http1.1
h2load --h1 -c50 -m20 --duration=120 --warm-up-time=5 http://localhost:8080/todo/search
h2load -c50 -m20 --duration=120 --warm-up-time=5 https://localhost:8080/todo/search
```

### Request Load Testing
```
#http1.1
h2load --h1 -n400000 -c100 -m1 http://localhost:8080/todo/search
#http2
h2load -n400000 -c50 -m20 https://localhost:8080/todo/search
```
