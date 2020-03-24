package com.github.starter.core.exception;

public class ApiException extends RuntimeException {
    private final String code;
    private final String summary;
    private final int status;

    public ApiException(String code, String summary, int status, Throwable cause) {
        super(code, cause);
        this.code = code;
        this.summary = summary;
        this.status = status;
    }


    public ApiException(String code, String summary, StatusCodes status, Throwable cause) {
        this(code, summary, status.getCode(), cause);
    }

    public String getCode() {
        return code;
    }

    public String getSummary() {
        return summary;
    }

    public int getStatus() {
        return status;
    }
}
