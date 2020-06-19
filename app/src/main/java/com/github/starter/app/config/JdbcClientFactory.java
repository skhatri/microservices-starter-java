package com.github.starter.app.config;

import com.github.starter.core.exception.ConfigurationException;

import java.util.Map;
import java.util.Optional;

public class JdbcClientFactory {
    private final Map<String, JdbcClient> clients;

    JdbcClientFactory(Map<String, JdbcClient> jdbcClients) {
        this.clients = jdbcClients;
    }

    public JdbcClient forName(String jdbcClientName) {
        return Optional.ofNullable(this.clients.get(jdbcClientName))
          .orElseThrow(() -> new ConfigurationException(String.format("no jdbc client of name [%s] found", jdbcClientName)));
    }

}
