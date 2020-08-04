package com.github.starter.app.todo.service;

import com.github.starter.app.todo.model.SearchRequest;
import com.github.starter.app.todo.model.TodoTask;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TodoService {
    Mono<List<TodoTask>> listItems(SearchRequest searchRequest);

    Mono<TodoTask> findById(String id);

    Mono<TodoTask> save(TodoTask task);

    Mono<TodoTask> update(String id, TodoTask task);

    Mono<Boolean> delete(String id);

    String getName();
}
