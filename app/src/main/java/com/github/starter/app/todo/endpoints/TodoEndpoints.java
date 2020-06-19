package com.github.starter.app.todo.endpoints;

import com.github.starter.app.todo.model.TodoTask;
import com.github.starter.app.todo.service.TodoService;
import com.github.starter.core.container.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/todo")
@CrossOrigin(value = "*")
public class TodoEndpoints {

    private final TodoService todoService;

    @Autowired
    TodoEndpoints(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/search")
    public Mono<Container<List<TodoTask>>> list() {
        return todoService.listItems().map(Container::new);
    }

    @GetMapping("/{id}")
    public Mono<Container<TodoTask>> get(@PathVariable("id") String id) {
        return todoService.findById(id).map(Container::new);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Container<TodoTask>> add(@RequestBody TodoTask todoTask) {
        return todoService.save(todoTask).map(Container::new);
    }

    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Container<TodoTask>> update(@PathVariable("id") String id, @RequestBody TodoTask todoTask) {
        return todoService.update(id, todoTask).map(Container::new);
    }

    @DeleteMapping("/{id}")
    public Mono<Container<Map<String, Boolean>>> delete(@PathVariable("id") String id) {
        return todoService.delete(id).map(b -> Map.of("result", b)).map(Container::new);
    }
}
