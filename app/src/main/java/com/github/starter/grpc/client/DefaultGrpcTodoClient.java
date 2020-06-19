package com.github.starter.grpc.client;

import com.github.starter.app.todo.model.TodoTask;
import com.github.starter.app.todo.service.TodoService;
import com.github.starter.core.adapters.ListenableFutureToCompletableFutureAdapter;
import com.github.starter.grpc.model.TodoTasks;
import com.github.starter.proto.TodoServiceGrpc;
import com.github.starter.proto.Todos;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.protobuf.BoolValue;
import com.google.protobuf.StringValue;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

@Service("grpcTodoService")
@ConditionalOnProperty(name = "flags.use.grpc", havingValue = "true")
public class DefaultGrpcTodoClient implements TodoService {

    private final int grpcPort;
    private final ManagedChannel channel;

    @Autowired
    public DefaultGrpcTodoClient(@Value("${grpc.port:8100}") int port) {
        this.grpcPort = port;
        this.channel = ManagedChannelBuilder.forAddress("0.0.0.0", grpcPort)
                .usePlaintext()
                .build();
    }

    @Override
    public Mono<List<TodoTask>> listItems() {
        ListenableFuture<Todos.TodoList> items = TodoServiceGrpc.newFutureStub(channel).getTodos(Todos.Params.newBuilder().build());
        CompletionStage<List<TodoTask>> completionStage = ListenableFutureToCompletableFutureAdapter.toCompletionStage(items, MoreExecutors.directExecutor())
                .thenApply(todoList -> todoList.getDataList().stream().map(TodoTasks::todoToTodoTask).collect(Collectors.toList()));
        return Mono.fromCompletionStage(completionStage);
    }


    @Override
    public Mono<TodoTask> findById(String id) {
        ListenableFuture<Todos.Todo> item = TodoServiceGrpc.newFutureStub(channel).findById(StringValue.newBuilder().setValue(id).build());
        return Mono.fromCompletionStage(
                ListenableFutureToCompletableFutureAdapter.toCompletionStage(item)
                        .thenApply(TodoTasks::todoToTodoTask)
        );
    }

    @Override
    public Mono<TodoTask> save(TodoTask task) {
        return saveUpdate(task, false);
    }

    private Mono<TodoTask> saveUpdate(TodoTask task, boolean update) {
        Todos.Todo todo = Todos.Todo.newBuilder()
                .setId(task.getId())
                .setActionBy(task.getActionBy())
                .setCreated(task.getCreated().toString())
                .setDescription(task.getDescription())
                .setStatus(task.getStatus())
                .build();
        TodoServiceGrpc.TodoServiceFutureStub todoServiceFutureStub = TodoServiceGrpc.newFutureStub(channel);
        ListenableFuture<Todos.Todo> items;
        if (update) {
            items = todoServiceFutureStub.update(todo);
        } else {
            items = todoServiceFutureStub.save(todo);
        }
        return Mono.fromCompletionStage(ListenableFutureToCompletableFutureAdapter.toCompletionStage(items).thenApply(TodoTasks::todoToTodoTask));
    }


    @Override
    public Mono<TodoTask> update(String id, TodoTask task) {
        if (!id.equals(task.getId())) {
            return Mono.error(new RuntimeException("Task id is invalid"));
        }
        return saveUpdate(task, true);
    }

    @Override
    public Mono<Boolean> delete(String id) {
        ListenableFuture<BoolValue> delResponse = TodoServiceGrpc.newFutureStub(channel).delete(StringValue.newBuilder()
                .setValue(id)
                .build()
        );
        return Mono.fromCompletionStage(ListenableFutureToCompletableFutureAdapter.toCompletionStage(delResponse).thenApply(BoolValue::getValue));
    }
}
