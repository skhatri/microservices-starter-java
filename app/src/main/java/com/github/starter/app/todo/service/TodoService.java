package com.github.starter.app.todo.service;

import com.github.starter.app.todo.model.TodoTask;
import java.util.List;
import reactor.core.publisher.Mono;

public interface TodoService {
    Mono<List<TodoTask>> listItems();

    Mono<TodoTask> findById(String id);

    Mono<TodoTask> save(TodoTask task);

    Mono<TodoTask> update(String id, TodoTask task);

    Mono<Boolean> delete(String id);
}
