package com.github.starter.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClientTest {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("0.0.0.0", 8100)
                .usePlaintext()
                .build();


        channel.shutdown();
    }
}
