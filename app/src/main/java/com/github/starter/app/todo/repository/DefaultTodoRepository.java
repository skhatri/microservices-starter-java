package com.github.starter.app.todo.repository;

import com.github.starter.app.config.JdbcClientFactory;
import com.github.starter.app.todo.model.TodoTask;
import com.github.starter.core.exception.InternalServerError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class DefaultTodoRepository implements TodoRepository {

    private final DatabaseClient databaseClient;

    @Autowired
    public DefaultTodoRepository(JdbcClientFactory clientFactory, @Value("${flags.default-jdbc-client}") String jdbcClientName) {
        this.databaseClient = clientFactory.forName(jdbcClientName).client();
    }

    @Override
    public Mono<List<TodoTask>> listItems() {
        return databaseClient
            .execute("select * from todo.tasks limit 20").as(TodoTask.class)
            .fetch().all().collectList();
    }

    @Override
    public Mono<TodoTask> findById(String id) {
        return databaseClient
            .execute("select * from todo.tasks where id= $1").bind("$1", id)
            .as(TodoTask.class)
            .fetch().first();
    }

    @Override
    public Mono<TodoTask> add(TodoTask todoTask) {
        String id = UUID.randomUUID().toString();
        LocalDateTime updated = LocalDateTime.now();
        return databaseClient
            .execute("insert into todo.tasks(id, description, action_by, created, status, updated) values($1, $2, $3, $4, $5, $6)")
            .bind("$1", id)
            .bind("$2", todoTask.getDescription())
            .bind("$3", todoTask.getActionBy())
            .bind("$4", todoTask.getCreated())
            .bind("$5", todoTask.getStatus())
            .bind("$6", updated)
            .fetch().rowsUpdated()
            .filter(i -> i == 1)
            .switchIfEmpty(Mono.error(InternalServerError.fromCodeAndMessage("add-error", "Could not add TODO record")))
            .then(Mono.just(new TodoTask(id, todoTask.getDescription(), todoTask.getActionBy(), todoTask.getCreated(), todoTask.getStatus(), updated)));
    }

    @Override
    public Mono<TodoTask> update(TodoTask todoTask) {
        LocalDateTime updatedTime = LocalDateTime.now();
        return databaseClient.execute("update todo.tasks set description=$1, action_by=$2, created=$3, status=$4, updated=$5 where id=$6")
            .bind("$1", todoTask.getDescription())
            .bind("$2", todoTask.getActionBy())
            .bind("$3", todoTask.getCreated())
            .bind("$4", todoTask.getStatus())
            .bind("$5", updatedTime)
            .bind("$6", todoTask.getId()).fetch().rowsUpdated()
            .filter(i -> i == 1)
            .switchIfEmpty(Mono.error(InternalServerError.fromCodeAndMessage("update-error", "Could not update TODO record")))
            .then(Mono.just(new TodoTask(todoTask.getId(), todoTask.getDescription(), todoTask.getActionBy(), todoTask.getCreated(), todoTask.getStatus(), updatedTime)));
    }

    @Override
    public Mono<Boolean> delete(String id) {
        return databaseClient.delete().from("todo.tasks").matching(Criteria.where("id").is(id)).fetch().rowsUpdated()
            .filter(i -> i == 1)
            .switchIfEmpty(Mono.error(InternalServerError.fromCodeAndMessage("delete-error", String.format("Could not delete TODO record %s", id))))
            .then(Mono.just(true));
    }
}
