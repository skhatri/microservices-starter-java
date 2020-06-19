package com.github.starter.app.todo.endpoints;

import com.github.starter.app.todo.model.TodoTask;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Todos {
    private Todos() {
    }

    public static TodoTask createOne(String id, LocalDateTime dateTime) {
        return new TodoTask(id, "Todo Task 1", "user",
            dateTime, "NEW", dateTime);
    }

    public static TodoTask createOne(LocalDateTime dateTime) {
        return new TodoTask("1", "Todo Task 1", "user",
            dateTime, "NEW", dateTime);
    }

    public static TodoTask createOneForToday() {
        return createOneForDate(LocalDate.now());
    }

    public static TodoTask createOneForDate(LocalDate date) {
        return createOne(date.atStartOfDay());
    }

}
