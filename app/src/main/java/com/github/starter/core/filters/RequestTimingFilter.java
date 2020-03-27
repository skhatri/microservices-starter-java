package com.github.starter.core.filters;

import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@ConditionalOnProperty(name = "flags.log.requests", havingValue = "true", matchIfMissing = true)
@Component
public class RequestTimingFilter implements WebFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestTimingFilter.class);
    private final boolean logParameters;

    @Autowired
    RequestTimingFilter(@Value("${flags.log.parameters:false}") boolean logParameters) {
        this.logParameters = logParameters;
    }

    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        long start = System.currentTimeMillis();
        String path = exchange.getRequest().getPath().toString();
        StringBuilder params = new StringBuilder();
        if (this.logParameters) {
            String pairs = exchange.getRequest().getQueryParams().toSingleValueMap()
                .entrySet()
                .stream()
                .map(em -> String.format("%s=%s", em.getKey(), em.getValue()))
                .collect(Collectors.joining(", "));
            params.append(pairs.isEmpty() ? "" : ", ").append(pairs);
        }

        return chain.filter(exchange)
            .doOnSuccess(v -> {
                long endTime = System.currentTimeMillis();
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info("tag={}, uri=\"{}\", time={}, unit=ms{}", "request-timing",
                        path, (endTime - start), params);
                }
            });

    }


}
