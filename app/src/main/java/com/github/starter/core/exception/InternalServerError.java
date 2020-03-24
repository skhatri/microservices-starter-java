package com.github.starter.core.exception;

public class InternalServerError extends ApiException {
    public InternalServerError() {
        this("internal-error", "Internal Server Error");
    }

    private InternalServerError(String code, String message) {
        super(code, message, StatusCodes.INTERNAL_SERVER_ERROR, null);
    }

    public static InternalServerError fromCodeAndMessage(String code, String message) {
        return new InternalServerError(code, message);
    }
}
