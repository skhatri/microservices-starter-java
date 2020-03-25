package com.github.starter.app.config;

import com.github.starter.core.exception.ConfigurationException;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Jdbc Client Preparator")
public class JdbcClientPreparatorTest {
    private JdbcClientPreparator clientPreparator;

    @Test
    @DisplayName("test no jdbc present")
    public void testNoJdbcConnectionConfigPresent() {
        Assertions.assertThrows(ConfigurationException.class, () -> {
            clientPreparator = new JdbcClientPreparator(Map.of());
            clientPreparator.configure(null);
        });
    }

}
