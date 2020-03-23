package com.github.starter.app.endpoints;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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
