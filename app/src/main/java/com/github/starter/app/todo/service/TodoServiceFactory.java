package com.github.starter.app.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TodoServiceFactory {
    private final Map<String, TodoService> todoServices;
    private final TodoService defaultService;

    @Autowired
    public TodoServiceFactory(List<TodoService> todoServices) {
        this.todoServices = todoServices.stream()
                .collect(Collectors.toMap(km -> km.getName(), vm -> vm));
        this.defaultService = this.todoServices.get("default");
    }

    public TodoService findHandler(String intent) {
        return this.todoServices.getOrDefault(intent, this.defaultService);
    }


}
