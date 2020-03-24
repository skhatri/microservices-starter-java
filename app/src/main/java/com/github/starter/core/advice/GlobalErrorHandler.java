package com.github.starter.core.advice;

import com.github.starter.core.container.Container;
import com.github.starter.core.container.MessageItem;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
public final class GlobalErrorHandler extends AbstractErrorWebExceptionHandler {

    @Autowired
    public GlobalErrorHandler(CustomErrorAttributes errorAttributes,
                              ApplicationContext applicationContext,
                              ServerCodecConfigurer codecConfigurer) {
        super(errorAttributes, new ResourceProperties(), applicationContext);
        super.setMessageWriters(codecConfigurer.getWriters());
        super.setMessageReaders(codecConfigurer.getReaders());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::render);
    }

    private Mono<ServerResponse> render(ServerRequest request) {
        Map<String, Object> error = getErrorAttributes(request, false);
        int statusCode = Integer.parseInt(String.valueOf(error.getOrDefault("status", "500")));
        MessageItem messageItem = MessageItem.class.cast(error.get("error"));
        Container container = new Container(List.of(messageItem));
        return ServerResponse.status(HttpStatus.valueOf(statusCode)).contentType(MediaType.APPLICATION_JSON).bodyValue(container);
    }
}
