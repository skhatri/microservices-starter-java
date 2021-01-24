package com.github.starter.grpc.model;

import com.github.starter.app.todo.model.TodoTask;
import com.github.starter.proto.Todos;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class TodoTasks {

    public static TodoTask todoToTodoTask(Todos.Todo item) {
        ZonedDateTime dateTime = ZonedDateTime.parse(item.getCreated(), DateTimeFormatter.ISO_ZONED_DATE_TIME);
        return new TodoTask(item.getId(), item.getDescription(), item.getActionBy(), dateTime, item.getStatus(), dateTime);
    }

    public static Todos.Todo toTodo(TodoTask todoTask) {
        String id = todoTask.getId();
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        String created = todoTask.getCreated().format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
        return Todos.Todo.newBuilder()
            .setId(id)
            .setActionBy(todoTask.getActionBy())
            .setCreated(created)
            .setDescription(todoTask.getDescription())
            .setStatus(todoTask.getStatus())
            .setUpdated(todoTask.getUpdated() != null ? todoTask.getUpdated().format(DateTimeFormatter.ISO_ZONED_DATE_TIME) : created)
            .build();
    }

}
