package com.github.starter.grpc.model;

import com.github.starter.app.todo.model.TodoTask;
import com.github.starter.proto.Todos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TodoTasks {

    public static TodoTask todoToTodoTask(Todos.Todo item) {
        LocalDateTime dateTime = LocalDateTime.parse(item.getCreated(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return new TodoTask(item.getId(), item.getDescription(), item.getActionBy(), dateTime, item.getStatus(), dateTime);
    }

    public static Todos.Todo toTodo(TodoTask todoTask) {

        String created = todoTask.getCreated().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return Todos.Todo.newBuilder()
                .setId(todoTask.getId())
                .setActionBy(todoTask.getActionBy())
                .setCreated(created)
                .setDescription(todoTask.getDescription())
                .setStatus(todoTask.getStatus())
                .setUpdated(todoTask.getUpdated() != null ? todoTask.getUpdated().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : created)
                .build();
    }

}
