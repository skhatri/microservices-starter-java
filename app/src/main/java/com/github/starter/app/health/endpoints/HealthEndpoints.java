package com.github.starter.app.health.endpoints;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController(value = "/")
public class HealthEndpoints {

    @GetMapping("/")
    public Mono<Map<String, Object>> index() {
        return createPayload("up", "Journey starts here!");
    }

    @GetMapping("/liveness")
    public Mono<Map<String, Object>> liveness() {
        return createPayload("live", "is running!");
    }

    @GetMapping("/readiness")
    public Mono<Map<String, Object>> readiness() {
        return createPayload("ready", "can serve!");
    }

    private Mono<Map<String, Object>> createPayload(String status, String message) {
        return Mono.just(Map.of("status", status, "message", message));
    }

}
