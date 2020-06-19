package com.github.starter.app.todo.repository;

import com.github.skhatri.mounted.MountedSecretsFactory;
import com.github.skhatri.mounted.MountedSecretsResolver;
import com.github.skhatri.mounted.model.ErrorDecision;
import com.github.skhatri.mounted.model.SecretConfiguration;
import com.github.skhatri.mounted.model.SecretProvider;
import com.github.starter.app.config.ConfigItem;
import com.github.starter.app.config.JdbcClientConfig;
import com.github.starter.app.config.JdbcClientFactory;
import com.github.starter.app.config.JdbcProperties;
import com.github.starter.core.secrets.SecretsClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@DisplayName("Todo Repository Integration Tests")
@Testcontainers
public class DefaultTodoRepositoryIntegrationTest {

    private JdbcProperties jdbcProperties;

    private TodoRepositoryUseCases todoRepositoryUseCases;

    @Container
    public GenericContainer postgres = new GenericContainer<>("skhatri/todo-postgres:11.5")
        .withEnv("POSTGRES_PASSWORD", "admin")
        .withExposedPorts(5432);

    @BeforeEach
    public void setUp() {


        ConfigItem cfg = new ConfigItem();
        cfg.setDriver("postgresql");
        cfg.setEnabled(true);
        cfg.setHost(postgres.getContainerIpAddress());
        cfg.setPort(postgres.getFirstMappedPort());
        cfg.setUsername("todo_admin");
        cfg.setPassword("password");
        String defaultJdbcName = "default-jdbc-client";
        cfg.setName(defaultJdbcName);
        cfg.setDatabase("todo");
        this.jdbcProperties = new JdbcProperties();
        this.jdbcProperties.setRef(List.of(cfg));

        SecretProvider secretProvider = new SecretProvider();
        secretProvider.setEntriesLocation("all.properties");
        secretProvider.setErrorDecision(ErrorDecision.EMPTY.toString().toLowerCase());
        secretProvider.setIgnoreResourceFailure(true);
        secretProvider.setName("vault");
        secretProvider.setMount("/doesnotexist");

        SecretConfiguration secretConfiguration = new SecretConfiguration();
        secretConfiguration.setKeyErrorDecision(ErrorDecision.IDENTITY.toString().toLowerCase());
        secretConfiguration.setProviders(Arrays.asList(secretProvider));
        MountedSecretsResolver mountedSecretsResolver = new MountedSecretsFactory(secretConfiguration).create();
        SecretsClient secretsClient = new SecretsClient(mountedSecretsResolver);
        JdbcClientConfig jdbcClientConfig = new JdbcClientConfig();
        Map<String, ConfigItem> config = jdbcClientConfig.databaseProperties(this.jdbcProperties);
        JdbcClientFactory factory = jdbcClientConfig.dataSources(config, secretsClient);

        this.todoRepositoryUseCases = new TodoRepositoryUseCases(new DefaultTodoRepository(factory, defaultJdbcName));
    }

    @DisplayName("list todo")
    @Test
    public void testListTodo() {
        todoRepositoryUseCases.testListTodoTasks();
    }

    @DisplayName("add todo")
    @Test
    public void testAddTodoTask() {
        todoRepositoryUseCases.verifyAddTodoTask();
    }


    @DisplayName("delete todo")
    @Test
    public void testDeleteTodoTask() {
        todoRepositoryUseCases.verifyDeleteTodoTask();
    }


    @DisplayName("update todo")
    @Test
    public void testUpdateTodoTask() {
        todoRepositoryUseCases.verifyUpdateTodoTask();
    }

    @AfterEach
    public void tearDown() {
        postgres.stop();
    }
}
