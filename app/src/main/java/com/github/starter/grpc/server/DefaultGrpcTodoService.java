package com.github.starter.grpc.server;

import com.github.starter.app.todo.repository.TodoRepository;
import com.github.starter.proto.TodoServiceGrpc;
import com.github.starter.proto.Todos;
import com.google.protobuf.BoolValue;
import com.google.protobuf.StringValue;
import io.grpc.stub.StreamObserver;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
public class DefaultGrpcTodoService extends TodoServiceGrpc.TodoServiceImplBase {

    private TodoRepository todoRepository;

    @Autowired
    DefaultGrpcTodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public void getTodos(Todos.SearchRequest request, StreamObserver<Todos.TodoList> responseObserver) {
        StreamObserverAdapter.transform(
            this.todoRepository.listItems(request),
            responseObserver,
            tasks -> Todos.TodoList.newBuilder().addAllData(tasks).build()
        );
    }


    @Override
    public void save(Todos.Todo request, StreamObserver<Todos.Todo> responseObserver) {
        StreamObserverAdapter.transform(
            this.todoRepository.add(request),
            responseObserver,
            Function.identity()
        );
    }

    @Override
    public void update(Todos.Todo request, StreamObserver<Todos.Todo> responseObserver) {
        System.out.println("before update");
        Todos.Todo todo = this.todoRepository.update(request).block();
        System.out.println("updated todo");
        responseObserver.onNext(todo);
        responseObserver.onCompleted();
        System.out.println("update completed");
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
            Function.identity()
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
