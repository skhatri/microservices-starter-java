package com.github.starter.app.config;

import com.github.starter.core.secrets.SecretsClient;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@ConditionalOnProperty(name = "datasource.jdbc.enabled", havingValue = "true")
public class JdbcClientConfig {

    @Autowired
    @Bean
    public Map<String, ConfigItem> databaseProperties(JdbcProperties jdbcProperties) {
        return jdbcProperties.getRef().stream()
            .collect(Collectors.toMap(ConfigItem::getName, cfg -> cfg));
    }

    @Autowired
    @Bean
    public JdbcClientFactory dataSources(Map<String, ConfigItem> jdbcConfigItems, SecretsClient secretsClient) {
        return new JdbcClientFactory(new JdbcClientPreparator(jdbcConfigItems, secretsClient).configure(this::runInitScripts));
    }

    private void runInitScripts(ConfigItem configItem, JdbcClient jdbcClient) {
        Optional.ofNullable(configItem.getLoad()).ifPresent(load -> {
            if ("h2".equals(configItem.getDriver())) {
                JdbcScriptProcessor scriptProcessor = new JdbcScriptProcessor();
                scriptProcessor.process(load, jdbcClient.create().block());
            }
        });
    }

}
