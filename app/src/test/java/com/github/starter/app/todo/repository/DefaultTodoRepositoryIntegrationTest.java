package com.github.starter.app.todo.repository;

import com.github.starter.app.config.ConfigItem;
import com.github.starter.app.config.JdbcClientConfig;
import com.github.starter.app.config.JdbcClientFactory;
import com.github.starter.app.config.JdbcProperties;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

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
        cfg.setUsername("postgres");
        cfg.setPassword("admin");
        cfg.setName("default-jdbc-client");
        cfg.setDatabase("postgres");
        this.jdbcProperties = new JdbcProperties();
        this.jdbcProperties.setRef(List.of(cfg));

        JdbcClientConfig jdbcClientConfig = new JdbcClientConfig();
        Map<String, ConfigItem> config = jdbcClientConfig.databaseProperties(this.jdbcProperties);
        JdbcClientFactory factory = jdbcClientConfig.dataSources(config);

        this.todoRepositoryUseCases = new TodoRepositoryUseCases(new DefaultTodoRepository(factory));
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
