package com.github.starter.core.secrets;

import com.github.skhatri.mounted.model.SecretConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "secrets")
@Component
public class SecretsProperties {
    private SecretConfiguration config;

    public SecretConfiguration getConfig() {
        return config;
    }

    public void setConfig(SecretConfiguration config) {
        this.config = config;
    }
}
