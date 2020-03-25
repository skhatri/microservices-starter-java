package com.github.starter.app.todo.service;

import com.github.starter.app.todo.model.TodoTask;
import com.github.starter.app.todo.repository.TodoRepository;
import com.github.starter.core.exception.BadRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DefaultTodoService implements TodoService {

    private final TodoRepository todoRepository;

    @Autowired
    DefaultTodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public Mono<List<TodoTask>> listItems() {
        return todoRepository.listItems();
    }

    @Override
    public Mono<TodoTask> findById(String id) {
        return todoRepository.findById(id);
    }

    @Override
    public Mono<TodoTask> save(TodoTask task) {
        return todoRepository.add(task);
    }

    @Override
    public Mono<TodoTask> update(String id, TodoTask task) {
        if (!id.equals(task.getId())) {
            return Mono.error(BadRequest.forCodeAndMessage("invalid-id", "Provided Task ID does not match one in Payload"));
        }
        return todoRepository.update(task);
    }

    @Override
    public Mono<Boolean> delete(String id) {
        return todoRepository.delete(id);
    }
}
