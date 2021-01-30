package com.github.starter.app.todo.endpoints;

import com.github.starter.app.todo.model.SearchRequest;
import com.github.starter.app.todo.model.TodoTask;
import com.github.starter.app.todo.service.TodoServiceFactory;
import com.github.starter.core.container.Container;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/todo")
@CrossOrigin(value = "*")
public class TodoEndpoints {

    private final TodoServiceFactory serviceFactory;

    @Autowired
    TodoEndpoints(TodoServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @GetMapping("/{mode}/")
    public Mono<Container<List<TodoTask>>> list(@PathVariable("mode") String mode,
                                                @RequestParam(value = "created", required = false, defaultValue = "") String created,
                                                @RequestParam(value = "action_by", required = false, defaultValue = "")String actionBy,
                                                @RequestParam(value = "status", required = false, defaultValue = "")String status) {
        SearchRequest searchRequest = new SearchRequest(actionBy, status, created);
        return this.serviceFactory.findHandler(mode).listItems(searchRequest).map(Container::new);
    }

    @GetMapping("/{mode}/{id}")
    @Counted(recordFailuresOnly = false, description = "find by id")
    public Mono<Container<TodoTask>> get(@PathVariable("mode") String mode, @PathVariable("id") String id) {
        return serviceFactory.findHandler(mode).findById(id).map(Container::new);
    }

    @PostMapping(value = "/{mode}/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Timed(description = "save time")
    public Mono<Container<TodoTask>> add(@PathVariable("mode") String mode, @RequestBody TodoTask todoTask) {
        return serviceFactory.findHandler(mode).save(todoTask).map(Container::new);
    }

    @PostMapping(value = "/{mode}/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Container<TodoTask>> update(@PathVariable("mode") String mode, @PathVariable("id") String id, @RequestBody TodoTask todoTask) {
        return serviceFactory.findHandler(mode).update(id, todoTask).map(Container::new);
    }

    @DeleteMapping("/{mode}/{id}")
    public Mono<Container<Map<String, Boolean>>> delete(@PathVariable("mode") String mode, @PathVariable("id") String id) {
        return serviceFactory.findHandler(mode).delete(id).map(b -> Map.of("result", b)).map(Container::new);
    }
}
