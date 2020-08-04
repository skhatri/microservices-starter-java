
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
open http://localhost:8080/todo/default/search
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

### Running all engines in containers
To create fat jar for each server type, use the following command.
```
./scripts/pack/builder.sh
```
It will also create a docker container with all fat jars. You can pass environment variable ```ENGINE``` to pick
a server type to run in your container. The value of ENGINE can be undertow,jetty,reactor-netty or tomcat. 
Default value is reactor-netty.

```
docker run -p 8080:8080 -e ENGINE=undertow -d starter-java:latest
```


### Time Based Load Testing
```
brew install nghttp2
#http1.1
h2load --h1 -c50 -m20 --duration=120 --warm-up-time=5 http://localhost:8080/todo/default/search
h2load -c50 -m20 --duration=120 --warm-up-time=5 https://localhost:8080/todo/default/search
```

Unmonitored Time-based testing can be done using ```scripts/pack/time-based-test.sh```

### Request Load Testing
```
#http1.1
h2load --h1 -n400000 -c100 -m1 http://localhost:8080/todo/default/search
#http2
h2load -n400000 -c50 -m20 https://localhost:8080/todo/default/search
```

### Testing Grpc Endpoints

```
brew install grpcurl
#ad-hoc curl with grpcurl
grpcurl -import-path app/src/main/proto -proto todo.proto list 
grpcurl -import-path app/src/main/proto -proto todo.proto describe TodoService.getTodos 

#without reflection
grpcurl -import-path app/src/main/proto -proto todo.proto -plaintext localhost:8100 TodoService.getTodos
#with reflection
grpcurl -plaintext localhost:8100 TodoService.getTodos

#load testing with ghz
brew install ghz
ghz --insecure --proto app/src/main/proto/todo.proto --call TodoService.getTodos localhost:8100 \
-o build/reports/ghz.html -O html -n 400000 -c 20 

```

### Bridge gRPC with Envoy
Download GoogleApis to $GOOGLEAPIS_DIR
```
mkdir -p $HOME/dev/platform/envoy
export GOOGLEAPIS_DIR=$HOME/dev/platform/envoy/googleapis
git clone https://github.com/googleapis/googleapis.git  $GOOGLEAPIS_DIR

#generate proto description using todo.proto file
protoc -I${GOOGLEAPIS_DIR} -I. --include_imports --include_source_info \
  --descriptor_set_out=proto.pb app/src/main/proto/todo.proto

#build todo binary
gradle clean build -x test #for quick build
docker-compose build
docker-compose up -d

docker-compose ps
```

#### Testing REST endpoints using direct app host port
```
curl --http2 -H"Content-Type:application/json" http://todo:8080/todo/default/search -v
curl --http1.1 -H"Content-Type:application/json" http://todo:8080/todo/default/search -v
curl --http2 -H"Content-Type:application/json" http://todo:8080/todo/grpc/search -v
curl --http1.1 -H"Content-Type:application/json" http://todo:8080/todo/grpc/search -v
```

#### Testing gRPC endpoints using direct app host port
```
grpcurl -protoset proto.pb -plaintext -d '{"status":"DONE"}' localhost:8100 todo.TodoService.getTodos
grpcurl -plaintext -protoset proto.pb -d '{"status":"DONE", "action_by":"user1"}' todo:8100 todo.TodoService.getTodos
grpcurl -plaintext -import-path app/src/main/proto -import-path $HOME/dev/platform/envoy/googleapis -proto todo.proto todo:8100 todo.TodoService.getTodos
```

#### Testing gRPC endpoints via envoy proxy using grpcurl

```
grpcurl -plaintext -protoset proto.pb -d '{"status":"NEW", "action_by":"user1"}' todo:9090 todo.TodoService.getTodos
grpcurl -plaintext -import-path app/src/main/proto -import-path $HOME/dev/platform/envoy/googleapis -proto todo.proto todo:9090 todo.TodoService.getTodos
grpcurl -import-path app/src/main/proto -import-path $HOME/dev/platform/envoy/googleapis -proto todo.proto -plaintext localhost:9090 todo.TodoService.getTodos
grpcurl -plaintext -protoset proto.pb -d '{}' todo:9090 todo.TodoService.status


grpcurl -plaintext -import-path $HOME/dev/platform/envoy/googleapis -import-path app/src/main/proto -proto todo.proto \
-d '{
              "id": "9",
              "description": "Listen to Spotify",
              "created": "2020-03-20T13:00:00Z",
              "actionBy": "user1",
              "status": "NEW",
              "updated": "2020-03-20T13:00:00Z"
            }' \
todo:9090 todo.TodoService.update

grpcurl -plaintext -import-path $HOME/dev/platform/envoy/googleapis -import-path app/src/main/proto -proto todo.proto \
-d '{
              "id": "9",
              "description": "Listen to Songs",
              "created": "2020-03-20T13:00:00Z",
              "actionBy": "user1",
              "status": "NEW",
              "updated": "2020-03-20T13:00:00Z"
            }' \
todo:8100 todo.TodoService.update
```

#### Testing gRPC endpoints via envoy proxy using REST

```
curl --http1.1 \
-H"User-Agent:grpc-go/1.30.0" -H"Accept:" \
http://todo:9090/todo.search -v

curl --http1.1 \
-H"User-Agent:grpc-go/1.30.0" -H"Accept:" \
"http://todo:9090/todo.search?action_by=user1&status=DONE" -v

curl --http1.1 -H"Content-Type:application/json" \
-XPOST -H"Accept:" \
-d '{"id": "9", "description": "Listen to Spotify", "created": "2020-03-20T13:00:00Z", "actionBy": "user1", "status": "NEW", "updated": "2020-03-20T13:00:00Z"}' \
http://todo:9090/todo.update  -v

curl --http1.1 -H"Content-Type:application/json" \
-XPOST -H"Accept:" \
-d '{ "description": "Listen to Spotify", "created": "2020-03-20T13:00:00Z", "actionBy": "user1", "status": "NEW", "updated": "2020-03-20T13:00:00Z"}' \
http://todo:9090/todo.save  -v

curl --http1.1 -XGET -H"User-Agent:grpc-go/1.30.0" http://todo:9090/todo.status -v


```