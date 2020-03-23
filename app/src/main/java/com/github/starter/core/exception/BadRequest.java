package com.github.starter.core.exception;

public class BadRequest extends ApiException {
    public BadRequest() {
        super("bad-request", "Invalid Request", 400, null);
    }
}
