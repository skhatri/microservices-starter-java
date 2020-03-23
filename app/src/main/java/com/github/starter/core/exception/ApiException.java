package com.github.starter.core.exception;

public class ApiException extends RuntimeException {
    private String code;
    private String summary;
    private int status;

    public ApiException(String code, String summary, int status, Throwable cause) {
        super(code, cause);
        this.code = code;
        this.summary = summary;
        this.status = status;
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
