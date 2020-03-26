package com.github.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
    "com.github.starter.core",
    "com.github.starter.app"
})
public class Application {
    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }
}
