package com.github.starter.app.todo.repository;

import com.github.starter.app.config.JdbcClientFactory;
import com.github.starter.core.exception.InternalServerError;
import com.github.starter.core.utils.DateUtils;
import com.github.starter.proto.Todos;
import io.r2dbc.spi.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
    public Mono<List<Todos.Todo>> listItems(Todos.SearchRequest searchRequest) {
        DatabaseClient.GenericSelectSpec select = databaseClient.select()
            .from("todo.tasks").project("*");

        if (searchRequest.getActionBy() != "") {
            select = select.matching(Criteria.where("action_by").is(searchRequest.getActionBy()));
        }
        if (searchRequest.getStatus() != "") {
            select = select.matching(Criteria.where("status").is(searchRequest.getStatus()));
        }
        return select.map(this::asTodo).all().collectList();
    }

    private Todos.Todo asTodo(Row row) {
        return Todos.Todo.newBuilder()
            .setId(row.get("id", String.class))
            .setActionBy(row.get("action_by", String.class))
            .setCreated(DateUtils.safeDateSerialise(row.get("created")))
            .setUpdated(DateUtils.safeDateSerialise(row.get("updated")))
            .setStatus(row.get("status", String.class))
            .setDescription(row.get("description", String.class))
            .build();
    }


    @Override
    public Mono<Todos.Todo> findById(String id) {
        return databaseClient
            .execute("select * from todo.tasks where id= $1").bind("$1", id)
            .map(this::asTodo)
            .first();
    }

    @Override
    public Mono<Todos.Todo> add(Todos.Todo todoTask) {
        String id = UUID.randomUUID().toString();
        ZonedDateTime updated = ZonedDateTime.now();

        return databaseClient
            .execute("insert into todo.tasks(id, description, action_by, created, status, updated) values($1, $2, $3, $4, $5, $6)")
            .bind("$1", id)
            .bind("$2", todoTask.getDescription())
            .bind("$3", todoTask.getActionBy())
            .bind("$4", ZonedDateTime.parse(todoTask.getCreated(), DateTimeFormatter.ISO_ZONED_DATE_TIME))
            .bind("$5", todoTask.getStatus())
            .bind("$6", updated)
            .fetch().rowsUpdated()
            .filter(i -> i == 1)
            .switchIfEmpty(Mono.error(InternalServerError.fromCodeAndMessage("add-error", "Could not add TODO record")))
            .then(Mono.just(Todos.Todo.newBuilder().setId(id).setDescription(todoTask.getDescription())
                .setActionBy(todoTask.getActionBy())
                .setCreated(todoTask.getCreated())
                .setStatus(todoTask.getStatus()).setUpdated(updated.format(DateTimeFormatter.ISO_ZONED_DATE_TIME)).build()));
    }

    @Override
    public Mono<Todos.Todo> update(Todos.Todo todoTask) {
        ZonedDateTime updatedTime = ZonedDateTime.now();
        return databaseClient.execute("update todo.tasks set description=$1, action_by=$2, created=$3, status=$4, updated=$5 where id=$6")
            .bind("$1", todoTask.getDescription())
            .bind("$2", todoTask.getActionBy())
            .bind("$3", ZonedDateTime.parse(todoTask.getCreated(), DateTimeFormatter.ISO_ZONED_DATE_TIME))
            .bind("$4", todoTask.getStatus())
            .bind("$5", updatedTime)
            .bind("$6", todoTask.getId()).fetch().rowsUpdated()
            .filter(i -> i == 1)
            .switchIfEmpty(Mono.error(InternalServerError.fromCodeAndMessage("update-error", "Could not update TODO record")))
            .then(Mono.just(
                Todos.Todo.newBuilder().setId(todoTask.getId()).setDescription(todoTask.getDescription())
                    .setActionBy(todoTask.getActionBy())
                    .setCreated(todoTask.getCreated())
                    .setStatus(todoTask.getStatus()).setUpdated(updatedTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME)).build()));
    }

    @Override
    public Mono<Boolean> delete(String id) {
        return databaseClient.delete().from("todo.tasks").matching(Criteria.where("id").is(id)).fetch().rowsUpdated()
            .filter(i -> i == 1)
            .switchIfEmpty(Mono.error(InternalServerError.fromCodeAndMessage("delete-error", String.format("Could not delete TODO record %s", id))))
            .then(Mono.just(true));
    }
}
