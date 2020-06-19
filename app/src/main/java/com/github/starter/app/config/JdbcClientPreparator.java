package com.github.starter.app.config;

import com.github.starter.core.exception.ConfigurationException;
import com.github.starter.core.model.Tuple2;
import com.github.starter.core.secrets.SecretsClient;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public final class JdbcClientPreparator {
    private final Map<String, ConfigItem> configItemMap;
    private final SecretsClient secretsClient;

    JdbcClientPreparator(Map<String, ConfigItem> configItemMap, SecretsClient secretsClient) {
        this.configItemMap = configItemMap;
        this.secretsClient = secretsClient;
    }

    public Map<String, JdbcClient> configure(BiConsumer<ConfigItem, JdbcClient> setupHook) {
        Map<String, JdbcClient> clients = configItemMap.entrySet().stream()
                .filter(kv -> kv.getValue().isEnabled())
                .map(kv -> {
                    ConfigItem configItem = kv.getValue();
                    ConnectionFactoryOptions.Builder options = ConnectionFactoryOptions.builder();
                    options.option(ConnectionFactoryOptions.DRIVER, configItem.getDriver());
                    Optional.ofNullable(configItem.getDatabase()).ifPresent(db -> options.option(ConnectionFactoryOptions.DATABASE, db));
                    Optional.ofNullable(configItem.getHost()).ifPresent(host -> options.option(ConnectionFactoryOptions.HOST, host));
                    Optional.ofNullable(configItem.getPort()).ifPresent(port -> options.option(ConnectionFactoryOptions.PORT, port));
                    options.option(ConnectionFactoryOptions.PASSWORD, new String(secretsClient.resolve(configItem.getPassword())));
                    options.option(ConnectionFactoryOptions.USER, new String(secretsClient.resolve(configItem.getUsername())));
                    Optional.ofNullable(configItem.getProtocol()).ifPresent(protocol -> options.option(ConnectionFactoryOptions.PROTOCOL, protocol));
                    ConnectionFactory connFactory = ConnectionFactories.get(options.build());
                    JdbcClient jdbcClient = new JdbcClient(connFactory);
                    Optional.ofNullable(setupHook).ifPresent(hook -> hook.accept(configItem, jdbcClient));
                    return new Tuple2<>(kv.getKey(), jdbcClient);
                }).collect(Collectors.toMap(pair -> pair.first, pair -> pair.second));
        if (clients.isEmpty()) {
            throw new ConfigurationException("no jdbc clients are set. set datasource.jdbc.enabled=false if not using jdbc");
        }
        return clients;
    }
}
