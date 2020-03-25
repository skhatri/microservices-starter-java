package com.github.starter.core.exception;

public class ConfigurationException extends RuntimeException {

    public ConfigurationException(String reason) {
        super(reason);
    }

    public ConfigurationException(String reason, Throwable cause) {
        super(reason, cause);
    }
}
