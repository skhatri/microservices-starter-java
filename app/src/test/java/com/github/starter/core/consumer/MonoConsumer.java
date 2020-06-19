package com.github.starter.core.consumer;

import reactor.core.publisher.Mono;

import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

public class MonoConsumer<T> {
    private Mono<T> mono;
    private boolean error;

    public MonoConsumer(Mono<T> mono, boolean error) {
        this.mono = mono;
        this.error = error;
    }

    public void drain(Consumer<T> consumer) {
        CountDownLatch latch = new CountDownLatch(1);
        mono.subscribe(res -> {
                if (consumer != null) {
                    consumer.accept(res);
                }
                if (!error) {
                    latch.countDown();
                }
            },
            err -> {
                if (error) {
                    latch.countDown();
                }
            }
        );

        try {
            latch.await();
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    public void drain() {
        drain(null);
    }
}
