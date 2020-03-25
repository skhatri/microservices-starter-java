package com.github.starter.app.config;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.data.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;

public final class JdbcClient {
    private final ConnectionFactory connectionFactory;

    public JdbcClient(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public Mono<Connection> create() {
        return Mono.from(connectionFactory.create());
    }

    public DatabaseClient client() {
        return DatabaseClient.create(connectionFactory);
    }
}
