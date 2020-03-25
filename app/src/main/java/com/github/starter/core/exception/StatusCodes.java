package com.github.starter.core.exception;

import java.util.Arrays;

public enum StatusCodes {
    OK(200), BAD_REQUEST(400), NOT_FOUND(404), UNAUTHORIZED(401), FORBIDDEN(403), INTERNAL_SERVER_ERROR(500);
    private int code;

    private StatusCodes(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static StatusCodes fromValue(int value) {
        return Arrays.stream(StatusCodes.values()).filter(statusCode -> statusCode.code == value)
                .findAny().orElse(StatusCodes.INTERNAL_SERVER_ERROR);
    }
}
