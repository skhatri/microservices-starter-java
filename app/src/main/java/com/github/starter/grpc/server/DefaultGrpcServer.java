package com.github.starter.grpc.server;

import com.github.starter.proto.TodoServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class DefaultGrpcServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGrpcServer.class);

    private final TodoServiceGrpc.TodoServiceImplBase grpcService;

    private final int grpcPort;
    private final Server server;

    @Autowired
    public DefaultGrpcServer(TodoServiceGrpc.TodoServiceImplBase grpcService, @Value("${grpc.port:8100}") int port) {
        this.grpcService = grpcService;
        this.grpcPort = verifyPort(port);
        this.server = ServerBuilder.forPort(this.grpcPort)
                .addService(this.grpcService)
                .addService(ProtoReflectionService.newInstance())
                .build();
    }

    private int verifyPort(int port) {
        if (port == -1) {
            return new SecureRandom().nextInt(55535) + 10000;
        }
        return port;
    }

    @PostConstruct
    public void run() {
        try {
            server.start();
            LOGGER.info("started grpc server on {}", this.grpcPort);
        } catch (Exception ioe) {
            throw new RuntimeException("grpc server error", ioe);
        }
    }

    @PreDestroy
    public void destroy() {
        try {
            server.shutdown();
        } catch (RuntimeException re) {

        }
    }
}
