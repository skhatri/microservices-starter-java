package com.github.starter.grpc.server;

import io.grpc.stub.StreamObserver;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class StreamObserverAdapter<I, O> {

    public StreamObserverAdapter(Mono<I> input, StreamObserver<O> streamObserver, Function<I, O> fn) {
        input.subscribe(
                data -> streamObserver.onNext(fn.apply(data)),
                err -> streamObserver.onError(err),
                () -> streamObserver.onCompleted()
        );

    }

    public static final <I, O> void transform(Mono<I> input, StreamObserver<O> streamObserver, Function<I, O> fn) {
        new StreamObserverAdapter<>(input, streamObserver, fn);
    }

}
