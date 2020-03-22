package com.github.starter.app.endpoints;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController("/")
public class HealthEndpoints {


    @GetMapping("/liveness")
    public Mono<Map<String, Object>> liveness() {
        return Mono.just(Map.of("status", "live"));
    }

    @GetMapping("/readiness")
    public Mono<Map<String, Object>> readiness() {
        return Mono.just(Map.of("status", "ready"));
    }
}
