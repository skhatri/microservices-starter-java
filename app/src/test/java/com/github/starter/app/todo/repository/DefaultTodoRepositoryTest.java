package com.github.starter.app.todo.repository;

import com.github.starter.Application;
import org.junit.jupiter.api.BeforeEach;
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
    private TodoRepositoryUseCases todoRepositoryUseCases;

    @BeforeEach
    public void setup() {
        todoRepositoryUseCases = new TodoRepositoryUseCases(todoRepository);
    }

    @DisplayName("list todo")
    @Test
    public void testListTodo() {
        todoRepositoryUseCases.testListTodoTasks();
    }

    @DisplayName("add todo")
    @Test
    public void testAddTodoTask() {
        todoRepositoryUseCases.verifyAddTodoTask();
    }


    @DisplayName("delete todo")
    @Test
    public void testDeleteTodoTask() {
        todoRepositoryUseCases.verifyDeleteTodoTask();
    }


    @DisplayName("update todo")
    @Test
    public void testUpdateTodoTask() {
        todoRepositoryUseCases.verifyUpdateTodoTask();
    }

}
