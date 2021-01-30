package com.github.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
    "com.github.starter.core",
    "com.github.starter.app",
    "com.github.starter.grpc"
})
public class Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        LOGGER.info("task=app-lifecycle, phase=bootstrap");
        SpringApplication.run(Application.class, args);
        LOGGER.info("task=app-lifecycle, phase=started, time_taken={} ms", System.currentTimeMillis() - start);
    }
}
