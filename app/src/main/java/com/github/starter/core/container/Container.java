package com.github.starter.core.container;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.Map;


public final class Container<T> {
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private final T data;
    private final Map<String, Object> metadata;
    private final List<MessageItem> errors;
    private final List<MessageItem> warnings;

    @JsonCreator
    public Container(T data, Map<String, Object> metadata, List<MessageItem> errors, List<MessageItem> warnings) {
        this.data = data;
        this.metadata = metadata;
        this.errors = errors;
        this.warnings = warnings;
    }

    @JsonCreator
    public Container(T data) {
        this(data, Map.of(), List.of(), List.of());
    }

    @JsonCreator
    public Container(List<MessageItem> errors) {
        this(null, Map.of(), errors, List.of());
    }


    public T getData() {
        return data;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public List<MessageItem> getErrors() {
        return errors;
    }

    public List<MessageItem> getWarnings() {
        return warnings;
    }
}
