package com.github.starter.core.filters;

public class RequestTimingFilters {
    private RequestTimingFilters() {
    }

    public static RequestTimingFilter newInstance(boolean log) {
        return new RequestTimingFilter(log);
    }
}
