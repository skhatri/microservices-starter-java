package com.github.starter.app.todo.service;

import com.github.starter.app.todo.model.SearchRequest;
import com.github.starter.app.todo.model.TodoTask;
import com.github.starter.app.todo.repository.TodoRepository;
import com.github.starter.core.exception.BadRequest;
import com.github.starter.grpc.model.TodoTasks;
import com.github.starter.proto.Todos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service("defaultTodoService")
@Primary
public class DefaultTodoService implements TodoService {

    private final TodoRepository todoRepository;

    @Autowired
    DefaultTodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public Mono<List<TodoTask>> listItems(SearchRequest searchRequest) {
        return todoRepository.listItems(
            Todos.SearchRequest.newBuilder()
                .setCreated(searchRequest.getCreated())
                .setStatus(searchRequest.getStatus())
                .setActionBy(searchRequest.getActionBy())
            .build()
        ).map(tasks -> tasks.stream().map(TodoTasks::todoToTodoTask).collect(Collectors.toList()));
    }

    @Override
    public Mono<TodoTask> findById(String id) {
        return todoRepository.findById(id).map(TodoTasks::todoToTodoTask);
    }

    @Override
    public Mono<TodoTask> save(TodoTask task) {
        return todoRepository.add(TodoTasks.toTodo(task)).map(TodoTasks::todoToTodoTask);
    }

    @Override
    public Mono<TodoTask> update(String id, TodoTask task) {
        if (!id.equals(task.getId())) {
            return Mono.error(BadRequest.forCodeAndMessage("invalid-id", "Provided Task ID does not match one in Payload"));
        }
        return todoRepository.update(TodoTasks.toTodo(task)).map(TodoTasks::todoToTodoTask);
    }

    @Override
    public Mono<Boolean> delete(String id) {
        return todoRepository.delete(id);
    }

    @Override
    public String getName() {
        return "default";
    }
}
