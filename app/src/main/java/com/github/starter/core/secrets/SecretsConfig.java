package com.github.starter.core.secrets;

import com.github.skhatri.mounted.MountedSecretsFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecretsConfig {
    @ConditionalOnProperty(name = "secrets.enabled", havingValue = "true", matchIfMissing = false)
    @Autowired
    @Bean
    SecretsClient createSecretClient(SecretsProperties secretsProperties) {
        return new SecretsClient(new MountedSecretsFactory(secretsProperties.getConfig()).create());
    }

    @ConditionalOnProperty(name = "secrets.enabled", havingValue = "false", matchIfMissing = true)
    @Bean
    SecretsClient createNoOpSecretClient() {
        return new SecretsClient(MountedSecretsFactory.noOpResolver());
    }
}
