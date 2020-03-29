package com.github.starter.app.todo.repository;

import com.github.starter.app.todo.endpoints.Todos;
import com.github.starter.app.todo.model.TodoTask;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;

public class TodoRepositoryUseCases {

    private final TodoRepository todoRepository;

    TodoRepositoryUseCases(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }


    public void testListTodoTasks() {
        List<TodoTask> tasks = todoRepository.listItems().block();
        Assertions.assertFalse(tasks.isEmpty(), "todo table should have some data");
    }

    public void verifyAddTodoTask() {
        TodoTask task = Todos.createOne(LocalDateTime.now());
        TodoTask savedTask = todoRepository.add(task).block();
        Assertions.assertEquals(task.getDescription(), savedTask.getDescription());

        String taskId = savedTask.getId();
        TodoTask storedTask = todoRepository.findById(taskId).block();
        Assertions.assertEquals(savedTask.getDescription(), storedTask.getDescription());
    }

    public void verifyDeleteTodoTask() {
        TodoTask task = Todos.createOne(LocalDateTime.now());
        TodoTask savedTask = todoRepository.add(task).block();
        Assertions.assertEquals(task.getDescription(), savedTask.getDescription());

        String taskId = savedTask.getId();
        Assertions.assertTrue(todoRepository.delete(taskId).block());

        Assertions.assertFalse(todoRepository.delete(taskId).onErrorReturn(false).block());
    }

    public void verifyUpdateTodoTask() {
        TodoTask task = Todos.createOne(LocalDateTime.now());
        TodoTask savedTask = todoRepository.add(task).block();
        Assertions.assertEquals(task.getDescription(), savedTask.getDescription());

        String taskId = savedTask.getId();
        todoRepository.update(
            new TodoTask(taskId, savedTask.getDescription(), "user1", LocalDateTime.now(),
                "DONE", LocalDateTime.now())
        ).block();

        TodoTask updatedTask = todoRepository.findById(taskId).block();
        Assertions.assertEquals("DONE", updatedTask.getStatus());
    }
}
