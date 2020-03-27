package com.github.starter.app.health.endpoints;

import com.github.starter.core.consumer.MonoConsumer;
import com.github.starter.core.filters.RequestTimingFilters;
import java.util.Map;
import java.util.function.Predicate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@DisplayName("Health Endpoints Test")
@ExtendWith(SpringExtension.class)
public class HealthEndpointsTest {

    private WebTestClient webTestClient;

    @Test
    @DisplayName("test liveness endpoint")
    public void testLivenessEndpoint() {
        String uri = "/liveness";

        WebTestClient webTestClient = WebTestClient.bindToController(HealthEndpoints.class)
            .webFilter(RequestTimingFilters.newInstance(true))
            .build();
        verifyResult(uri, webTestClient, Map.class, m -> !m.isEmpty());
    }

    private <T> void verifyResult(String uri, WebTestClient webTestClient, Class<T> clz, Predicate<T> predicate) {
        Mono<T> result = Mono.from(webTestClient.get().uri(uri).exchange().expectStatus().isOk().returnResult(clz).getResponseBody());
        new MonoConsumer<>(result, false).drain(res -> Assertions.assertTrue(predicate.test(res)));
        StepVerifier.create(result)
            .verifyComplete();
    }

    @Test
    @DisplayName("test readiness endpoint")
    public void testReadinessEndpoint() {
        String uri = "/readiness";
        WebTestClient webTestClient = WebTestClient.bindToController(HealthEndpoints.class)
            .webFilter(RequestTimingFilters.newInstance(false))
            .build();
        verifyResult(uri, webTestClient, Map.class, m -> !m.isEmpty());
    }

    @Test
    @DisplayName("test index endpoint")
    public void testIndexEndpoint() {
        String uri = "/";
        WebTestClient webTestClient = WebTestClient.bindToController(HealthEndpoints.class).build();
        verifyResult(uri, webTestClient, Map.class, m -> !m.isEmpty());
    }


    @Test
    @DisplayName("test non-existent endpoint")
    public void testNonExistent() {
        String uri = "/readiness-xyz?action=reload";
        WebTestClient webTestClient = WebTestClient.bindToController(HealthEndpoints.class)
            .webFilter(RequestTimingFilters.newInstance(true))
            .build();
        webTestClient.get().uri(uri).exchange().expectStatus().isNotFound();
    }
}
