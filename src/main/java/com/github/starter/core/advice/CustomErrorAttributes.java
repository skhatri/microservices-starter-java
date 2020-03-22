package com.github.starter.core.advice;

import com.github.starter.core.exception.ApiException;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        Map<String, Object> errorMap = super.getErrorAttributes(request, includeStackTrace);
        Throwable exception = getError(request);
        if (exception instanceof ApiException) {
            ApiException apiException = ApiException.class.cast(exception);
            errorMap.put("status", apiException.getStatus());
            errorMap.put("code", apiException.getCode());
            errorMap.put("summary", apiException.getSummary());
        }
        errorMap.put("cause", exception.getCause());
        errorMap.put("path", request.exchange().getRequest().getPath().toString());

        return errorMap;
    }
}
