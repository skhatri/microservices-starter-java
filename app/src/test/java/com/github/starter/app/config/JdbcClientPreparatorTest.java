package com.github.starter.app.config;

import com.github.skhatri.mounted.MountedSecretsResolver;
import com.github.skhatri.mounted.NoOpSecretsResolver;
import com.github.starter.core.exception.ConfigurationException;
import com.github.starter.core.secrets.SecretsClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

@DisplayName("Jdbc Client Preparator")
public class JdbcClientPreparatorTest {
    private JdbcClientPreparator clientPreparator;

    @Test
    @DisplayName("test no jdbc present")
    public void testNoJdbcConnectionConfigPresent() {
        Assertions.assertThrows(ConfigurationException.class, () -> {
            MountedSecretsResolver noop = new NoOpSecretsResolver();
            clientPreparator = new JdbcClientPreparator(Map.of(), new SecretsClient(noop));
            clientPreparator.configure(null);
        });
    }

}
