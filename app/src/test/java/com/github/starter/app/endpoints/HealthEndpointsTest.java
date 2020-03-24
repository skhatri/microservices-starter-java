package com.github.starter.app.endpoints;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Predicate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
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
                .build();
        verifyResult(uri, webTestClient, Map.class, m -> !m.isEmpty());
    }

    private <T> void verifyResult(String uri, WebTestClient webTestClient, Class<T> clz, Predicate<T> predicate) {
        FluxExchangeResult<T> result = webTestClient.get().uri(uri).exchange().expectStatus().isOk().returnResult(clz);
        StepVerifier.create(result.getResponseBody())
                .recordWith(ArrayList::new)
                .thenConsumeWhile(m -> predicate.test(m))
                .verifyComplete();
    }

    @Test
    @DisplayName("test readiness endpoint")
    public void testReadinessEndpoint() {
        String uri = "/readiness";
        WebTestClient webTestClient = WebTestClient.bindToController(HealthEndpoints.class).build();
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
        String uri = "/readiness-xyz";
        WebTestClient webTestClient = WebTestClient.bindToController(HealthEndpoints.class)
                .build();
        webTestClient.get().uri(uri).exchange().expectStatus().isNotFound();
    }
}
