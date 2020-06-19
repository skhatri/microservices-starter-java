package com.github.starter.app.health.service;

import reactor.core.publisher.Mono;

public interface DownstreamStatus {
    Mono<Boolean> status();
}
