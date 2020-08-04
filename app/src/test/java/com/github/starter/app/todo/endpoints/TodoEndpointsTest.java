package com.github.starter.app.todo.endpoints;

import com.github.starter.app.todo.model.SearchRequest;
import com.github.starter.app.todo.model.TodoTask;
import com.github.starter.app.todo.service.TodoService;
import com.github.starter.app.todo.service.TodoServiceFactory;
import com.github.starter.core.advice.CustomErrorAttributes;
import com.github.starter.core.advice.GlobalErrorHandler;
import com.github.starter.core.consumer.MonoConsumer;
import com.github.starter.core.exception.InternalServerError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.codec.support.DefaultServerCodecConfigurer;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

@DisplayName("Todo Endpoints")
@ExtendWith(SpringExtension.class)
public class TodoEndpointsTest {
    private WebTestClient webTestClient;

    @Autowired
    private ApplicationContext applicationContext;

    @ParameterizedTest(name = "Error Scenario - [{index}] {0} - {4} {1}")
    @MethodSource("data")
    public void testTodosErrorService(String scenarioName, String uri, Consumer<TodoService> serviceHook, Class clz, HttpMethod method) {
        verifyInternalServiceErrorResponse(uri, serviceHook::accept, clz, method);
    }

    private <R> void verifyInternalServiceErrorResponse(String uri, Consumer<TodoService> serviceHook, Class<R> clz, HttpMethod method) {
        TodoService todoService = Mockito.mock(TodoService.class);
        serviceHook.accept(todoService);
        TodoServiceFactory serviceFactory = new TodoServiceFactory(List.of(todoService));
        TodoEndpoints todo = new TodoEndpoints(serviceFactory);
        CustomErrorAttributes errorAttributes = new CustomErrorAttributes();
        GlobalErrorHandler globalErrorHandler = new GlobalErrorHandler(errorAttributes, applicationContext, new DefaultServerCodecConfigurer());
        WebTestClient webTestClient = WebTestClient.bindToController(todo, globalErrorHandler).build();

        Mono<R> result = Mono.from(webTestClient.method(method).uri(uri).exchange().expectStatus().is5xxServerError().returnResult(clz).getResponseBody());
        new MonoConsumer<>(result, false).drain();
        StepVerifier.create(result).expectComplete().verify();
    }

    private static Stream<Arguments> data() {
        TodoTask todoTask = Todos.createOneForToday();
        String id = todoTask.getId();
        SearchRequest searchRequest = new SearchRequest("", "", "");
        return Stream.of(
            Arguments.of(
                "Find Todos test", "/todo/123",
                (Consumer<TodoService>) todoService -> Mockito.when(todoService.findById("123")).thenReturn(Mono.error(InternalServerError::new)), Map.class, HttpMethod.GET
            ),
            Arguments.of(
                "Delete Todos id", "/todos/123",
                (Consumer<TodoService>) todoService -> Mockito.when(todoService.delete("123")).thenReturn(Mono.error(InternalServerError::new)), Map.class, HttpMethod.DELETE
            ),
            Arguments.of(
                "Update Todos", String.format("/todos/%s", id),
                (Consumer<TodoService>) todoService -> Mockito.when(todoService.update(id, todoTask)).thenReturn(Mono.error(InternalServerError::new)), Map.class, HttpMethod.POST
            ),
            Arguments.of(
                "Add Todos", "/todos/",
                (Consumer<TodoService>) todoService -> Mockito.when(todoService.save(todoTask)).thenReturn(Mono.error(InternalServerError::new)), Map.class, HttpMethod.POST
            ),
            Arguments.of(
                "Search Todos", "/todos/search",
                (Consumer<TodoService>) todoService -> Mockito.when(todoService.listItems(searchRequest)).thenReturn(Mono.error(InternalServerError::new)), Map.class, HttpMethod.GET
            )
        );
    }

}
