.PHONY: build

build:
	./gradlew clean build -x test

up:
	@docker-compose up -d

down:
	@docker-compose down

ps:
	@docker-compose ps

gatling-envoy:
	./gradlew load-testing:runTest -Psimulation=ListSimulation
gatling-direct-grpc-json:
	PORT=8080 SERVICE_TYPE=grpc ./gradlew load-testing:runTest -Psimulation=ListSimulation

gatling-direct-json:
	PORT=8080 SERVICE_TYPE=default ./gradlew load-testing:runTest -Psimulation=ListSimulation

ghz-envoy:
	@ghz --insecure --protoset proto.pb --call todo.TodoService.getTodos localhost:9000 -o build/ghz-envoy.html -O html -n 200000 -c 10

ghz-direct:
	@ghz --insecure --protoset proto.pb --call todo.TodoService.getTodos localhost:8100 -o build/ghz-direct.html -O html -n 200000 -c 10


