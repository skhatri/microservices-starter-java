package com.github.starter.app.todo.repository;

import com.github.starter.Application;
import com.github.starter.app.todo.endpoints.Todos;
import com.github.starter.app.todo.model.TodoTask;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = {Application.class})
public class DefaultTodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @DisplayName("list todo")
    @Test
    public void testListTodo() {
        List<TodoTask> tasks = todoRepository.listItems().block();
        Assertions.assertFalse(tasks.isEmpty(), "todo table should have some data");
    }

    @DisplayName("add todo")
    @Test
    public void testAddTodoTask() {
        TodoTask task = Todos.createOne(LocalDateTime.now());
        TodoTask savedTask = todoRepository.add(task).block();
        Assertions.assertEquals(task.getDescription(), savedTask.getDescription());

        String taskId = savedTask.getId();
        TodoTask storedTask = todoRepository.findById(taskId).block();
        Assertions.assertEquals(savedTask.getDescription(), storedTask.getDescription());
    }


    @DisplayName("delete todo")
    @Test
    public void testDeleteTodoTask() {
        TodoTask task = Todos.createOne(LocalDateTime.now());
        TodoTask savedTask = todoRepository.add(task).block();
        Assertions.assertEquals(task.getDescription(), savedTask.getDescription());

        String taskId = savedTask.getId();
        Assertions.assertTrue(todoRepository.delete(taskId).block());

        Assertions.assertFalse(todoRepository.delete(taskId).onErrorReturn(false).block());
    }


    @DisplayName("update todo")
    @Test
    public void testUpdateTodoTask() {
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
