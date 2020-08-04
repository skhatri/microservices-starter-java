package com.github.starter.app.todo.repository;

import com.github.starter.app.todo.endpoints.Todos;
import com.github.starter.app.todo.model.TodoTask;
import com.github.starter.grpc.model.TodoTasks;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public class TodoRepositoryUseCases {

    private final TodoRepository todoRepository;

    TodoRepositoryUseCases(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }


    public void testListTodoTasks() {
        List<com.github.starter.proto.Todos.Todo> tasks = todoRepository.listItems(
            com.github.starter.proto.Todos.SearchRequest.newBuilder()
                .setActionBy("")
                .build()).block();
        Assertions.assertFalse(tasks.isEmpty(), "todo table should have some data");
    }

    public void verifyAddTodoTask() {
        TodoTask task = Todos.createOne(LocalDateTime.now());
        com.github.starter.proto.Todos.Todo savedTask = todoRepository.add(TodoTasks.toTodo(task)).block();
        Assertions.assertEquals(task.getDescription(), savedTask.getDescription());

        String taskId = savedTask.getId();
        com.github.starter.proto.Todos.Todo storedTask = todoRepository.findById(taskId).block();
        Assertions.assertEquals(savedTask.getDescription(), storedTask.getDescription());
    }

    public void verifyDeleteTodoTask() {
        TodoTask task = Todos.createOne(LocalDateTime.now());
        com.github.starter.proto.Todos.Todo savedTask = todoRepository.add(TodoTasks.toTodo(task)).block();
        Assertions.assertEquals(task.getDescription(), savedTask.getDescription());

        String taskId = savedTask.getId();
        Assertions.assertTrue(todoRepository.delete(taskId).block());

        Assertions.assertFalse(todoRepository.delete(taskId).onErrorReturn(false).block());
    }

    public void verifyUpdateTodoTask() {
        TodoTask task = Todos.createOne(LocalDateTime.now());
        com.github.starter.proto.Todos.Todo savedTask = todoRepository.add(TodoTasks.toTodo(task)).block();
        Assertions.assertEquals(task.getDescription(), savedTask.getDescription());

        String taskId = savedTask.getId();
        todoRepository.update(
            TodoTasks.toTodo(new TodoTask(taskId, savedTask.getDescription(), "user1", ZonedDateTime.now(),
                "DONE", ZonedDateTime.now()))
        ).block();

        com.github.starter.proto.Todos.Todo updatedTask = todoRepository.findById(taskId).block();
        Assertions.assertEquals("DONE", updatedTask.getStatus());
    }
}
