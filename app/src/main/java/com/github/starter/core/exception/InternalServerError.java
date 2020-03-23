package com.github.starter.core.exception;

public class InternalServerError extends ApiException {
    public InternalServerError() {
        super("internal-error", "Internal Server Error", 500, null);
    }
}
