package com.github.starter.app.todo.repository;

import com.github.starter.proto.Todos;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TodoRepository {
    Mono<List<Todos.Todo>> listItems();

    Mono<Todos.Todo> findById(String id);

    Mono<Todos.Todo> add(Todos.Todo todoTask);

    Mono<Todos.Todo> update(Todos.Todo todoTask);

    Mono<Boolean> delete(String id);
}
