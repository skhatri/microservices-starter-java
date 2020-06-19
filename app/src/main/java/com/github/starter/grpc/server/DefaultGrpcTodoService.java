package com.github.starter.grpc.server;

import com.github.starter.app.todo.repository.TodoRepository;
import com.github.starter.grpc.model.TodoTasks;
import com.github.starter.proto.TodoServiceGrpc;
import com.github.starter.proto.Todos;
import com.google.protobuf.BoolValue;
import com.google.protobuf.StringValue;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DefaultGrpcTodoService extends TodoServiceGrpc.TodoServiceImplBase {

    private TodoRepository todoRepository;

    @Autowired
    DefaultGrpcTodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public void getTodos(Todos.Params request, StreamObserver<Todos.TodoList> responseObserver) {
        StreamObserverAdapter.transform(
                this.todoRepository.listItems(),
                responseObserver,
                task -> Todos.TodoList.newBuilder()
                        .addAllData(task.stream().map(TodoTasks::toTodo).collect(Collectors.toList()))
                        .build()
        );
    }


    @Override
    public void save(Todos.Todo request, StreamObserver<Todos.Todo> responseObserver) {
        StreamObserverAdapter.transform(
                this.todoRepository.add(TodoTasks.todoToTodoTask(request)),
                responseObserver,
                TodoTasks::toTodo
        );
    }

    @Override
    public void update(Todos.Todo request, StreamObserver<Todos.Todo> responseObserver) {
        StreamObserverAdapter.transform(
                this.todoRepository.update(TodoTasks.todoToTodoTask(request)),
                responseObserver,
                TodoTasks::toTodo
        );
    }

    @Override
    public void delete(StringValue request, StreamObserver<BoolValue> responseObserver) {
        StreamObserverAdapter.transform(
                this.todoRepository.delete(request.getValue()),
                responseObserver,
                b -> BoolValue.newBuilder().setValue(b).build()
        );
    }

    @Override
    public void findById(StringValue request, StreamObserver<Todos.Todo> responseObserver) {
        StreamObserverAdapter.transform(
                this.todoRepository.findById(request.getValue()),
                responseObserver,
                TodoTasks::toTodo
        );
    }

    @Override
    public void status(Todos.Params request, StreamObserver<Todos.Status> responseObserver) {
        StreamObserverAdapter.transform(
                Mono.just("UP"),
                responseObserver,
                status -> Todos.Status.newBuilder().setValue(status).build()
        );
    }
}
