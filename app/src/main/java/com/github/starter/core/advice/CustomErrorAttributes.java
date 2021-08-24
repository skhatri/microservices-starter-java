package com.github.starter.core.advice;

import com.github.starter.core.container.MessageItem;
import com.github.starter.core.exception.ApiException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Component
public final class CustomErrorAttributes extends DefaultErrorAttributes {
    private static final String STATUS_KEY = "status";

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorMap = super.getErrorAttributes(request, options);
        Throwable exception = getError(request);
        MessageItem.Builder builder = new MessageItem.Builder();
        builder.withDetailItem("path", request.exchange().getRequest().getPath().toString());

        if (exception instanceof ApiException) {
            ApiException apiException = ApiException.class.cast(exception);
            errorMap.put(STATUS_KEY, apiException.getStatus());
            builder.withDetailItem(STATUS_KEY, apiException.getStatus())
                    .withCode(apiException.getCode()).withMessage(apiException.getSummary());
        } else {
            int defaultStatusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            errorMap.put(STATUS_KEY, defaultStatusCode);
            Throwable ex = exception.getCause() != null ? exception.getCause() : exception;
            builder.withDetailItem(STATUS_KEY, defaultStatusCode).withCode(ex.getClass().getSimpleName()).withMessage(ex.getMessage());
        }
        errorMap.put("error", builder.build());
        return errorMap;
    }
}
