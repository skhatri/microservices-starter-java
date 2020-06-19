package com.github.starter.app.health.endpoints;

import com.github.starter.app.health.service.DownstreamStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController(value = "/")
public class HealthEndpoints {
    private final DownstreamStatus downstreamStatus;

    @Autowired
    HealthEndpoints(DownstreamStatus downstreamStatus) {
        this.downstreamStatus = downstreamStatus;
    }

    @GetMapping("/")
    public Mono<Map<String, Object>> index() {
        return createPayload("up", "Journey starts here!");
    }

    @GetMapping("/favicon.ico")
    public Mono<Void> favicon() {
        return Mono.empty();
    }

    @GetMapping("/liveness")
    public Mono<ResponseEntity<Map<String, Object>>> liveness() {
        Mono<ResponseEntity<Map<String, Object>>> response = downstreamStatus.status().map(v ->
                new ResponseEntity<>(Map.of("status", "live", "message", "is running!", "downstream", v ? "UP" : "DOWN"),
                        v ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR)
        );
        return response;
    }

    @GetMapping("/readiness")
    public Mono<Map<String, Object>> readiness() {
        return createPayload("ready", "can serve!");
    }

    private Mono<Map<String, Object>> createPayload(String status, String message) {
        return Mono.just(Map.of("status", status, "message", message));
    }

}
