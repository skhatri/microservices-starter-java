package com.github.starter.core.exception;

public class BadRequest extends ApiException {
    public BadRequest() {
        this("bad-request", "Invalid Request");
    }

    private BadRequest(String code, String message) {
        super(code, message, StatusCodes.BAD_REQUEST, null);
    }

    public static BadRequest forCodeAndMessage(String code, String message) {
        return new BadRequest(code, message);
    }
}
