package com.github.starter.grpc.client;

import com.github.starter.app.health.service.DownstreamStatus;
import com.github.starter.core.adapters.ListenableFutureToCompletableFutureAdapter;
import com.github.starter.proto.TodoServiceGrpc;
import com.github.starter.proto.Todos;
import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletionStage;

@Service
@ConditionalOnProperty(name = "flags.use.grpc", havingValue = "true")
public class GrpcStatusClient implements DownstreamStatus {
    private final ManagedChannel channel;

    @Autowired
    GrpcStatusClient(@Value("${grpc.port:8100}") int port) {
        this.channel = ManagedChannelBuilder.forAddress("0.0.0.0", port)
                .usePlaintext()
                .build();
    }

    public Mono<Boolean> status() {
        ListenableFuture<Todos.Status> listenableFuture = TodoServiceGrpc.newFutureStub(channel).status(Todos.Params.newBuilder().build());
        CompletionStage<Boolean> status = ListenableFutureToCompletableFutureAdapter.toCompletionStage(listenableFuture)
                .thenApply(s -> "UP".equalsIgnoreCase(s.getValue()));
        return Mono.fromCompletionStage(status);
    }
}
