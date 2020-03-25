package com.github.starter.app.todo.repository;

import com.github.starter.app.todo.model.TodoTask;
import java.util.List;
import reactor.core.publisher.Mono;

public interface TodoRepository {
    Mono<List<TodoTask>> listItems();

    Mono<TodoTask> findById(String id);

    Mono<TodoTask> add(TodoTask todoTask);

    Mono<TodoTask> update(TodoTask todoTask);

    Mono<Boolean> delete(String id);
}
