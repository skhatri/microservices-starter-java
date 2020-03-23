package com.github.starter.core.advice;

import com.github.starter.core.exception.BadRequest;
import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.support.DefaultServerCodecConfigurer;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DisplayName("Global Error Handler Tests")
public class GlobalErrorHandlerTest {

    @DisplayName("Test Routing Function")
    @Test
    public void testRoutingFunction() throws Exception {
        ApplicationContext applicationContext = Mockito.mock(ApplicationContext.class);
        Mockito.when(applicationContext.getClassLoader()).thenReturn(GlobalErrorHandlerTest.class.getClassLoader());
        ServerCodecConfigurer codecConfigurer = new DefaultServerCodecConfigurer();
        CustomErrorAttributes attributes = new CustomErrorAttributes();
        GlobalErrorHandler errorHandler = new GlobalErrorHandler(attributes, applicationContext, codecConfigurer);
        errorHandler.afterPropertiesSet();

        ServerRequest request = createErrorServerRequest(new BadRequest());

        Mono<ServerResponse> serverResponse = errorHandler.getRoutingFunction(attributes).route(request).flatMap(fn -> fn.handle(request));
        CountDownLatch latch = new CountDownLatch(1);
        serverResponse.subscribe(res -> {
            Assertions.assertEquals(400, res.statusCode().value());
            latch.countDown();
        });
        latch.await();

        StepVerifier.create(serverResponse)
            .thenConsumeWhile(sr -> sr.statusCode().isError())
            .verifyComplete();
    }

    private ServerRequest createErrorServerRequest(Throwable throwable) {
        ServerWebExchange serverWebExchange = Mockito.mock(ServerWebExchange.class);
        ServerHttpRequest httpRequest = Mockito.mock(ServerHttpRequest.class);
        Mockito.when(httpRequest.getId()).thenReturn("request1");
        RequestPath requestPath = Mockito.mock(RequestPath.class);
        Mockito.when(requestPath.toString()).thenReturn("/some-uri");
        Mockito.when(httpRequest.getPath()).thenReturn(requestPath);
        Mockito.when(serverWebExchange.getRequest()).thenReturn(httpRequest);
        return MockServerRequest.builder()
                .exchange(serverWebExchange)
                .attribute(DefaultErrorAttributes.class.getName() + ".ERROR", throwable)
                .build();
    }
}
