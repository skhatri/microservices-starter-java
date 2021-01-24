package com.github.starter.core.adapters;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

public class ListenableFutureToCompletableFutureAdapter<T> {
    private final Executor executor;
    private final CompletableFuture<T> completionStage;

    public ListenableFutureToCompletableFutureAdapter(ListenableFuture<T> listenableFuture, Executor executor) {
        this.executor = executor;
        this.completionStage = new CompletableFuture<>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                boolean cancelled = listenableFuture.cancel(mayInterruptIfRunning);
                super.cancel(cancelled);
                return cancelled;
            }
        };

        Futures.addCallback(listenableFuture, new FutureCallback<T>() {
            @Override
            public void onSuccess(T result) {
                completionStage.complete(result);
            }

            @Override
            public void onFailure(Throwable t) {
                completionStage.completeExceptionally(t);
            }
        }, this.executor);
    }

    public CompletionStage<T> getCompletionStage() {
        return this.completionStage;
    }

    public static final <T> CompletionStage<T> toCompletionStage(ListenableFuture<T> listenableFuture, Executor executor) {
        return new ListenableFutureToCompletableFutureAdapter<>(listenableFuture, executor).getCompletionStage();
    }

    public static final <T> CompletionStage<T> toCompletionStage(ListenableFuture<T> listenableFuture) {
        return new ListenableFutureToCompletableFutureAdapter<>(listenableFuture, MoreExecutors.directExecutor()).getCompletionStage();
    }

}
