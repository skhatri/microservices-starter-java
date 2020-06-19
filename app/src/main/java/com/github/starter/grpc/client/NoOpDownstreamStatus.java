package com.github.starter.grpc.client;

import com.github.starter.app.health.service.DownstreamStatus;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@ConditionalOnProperty(name = "flags.use.grpc", havingValue = "false")
public class NoOpDownstreamStatus implements DownstreamStatus {

    @Override
    public Mono<Boolean> status() {
        return Mono.just(true);
    }
}
