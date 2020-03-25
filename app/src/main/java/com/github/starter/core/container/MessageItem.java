package com.github.starter.core.container;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.Map;

public final class MessageItem {
    private final String code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Map<String, Object> details;

    @JsonCreator
    public MessageItem(@JsonProperty("code") String code, @JsonProperty("message") String message, @JsonProperty("details") Map<String, Object> details) {
        this.code = code;
        this.message = message;
        this.details = details;
    }

    @JsonCreator
    public MessageItem(String code, String message) {
        this(code, message, null);
    }


    public Map<String, Object> getDetails() {
        return details;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


    public static final class Builder {
        private String code;
        private String message;
        private Map<String, Object> details = new HashMap<>();

        public Builder withCode(String code) {
            this.code = code;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withDetails(Map<String, Object> details) {
            this.details.putAll(details);
            return this;
        }

        public Builder withDetailItem(String item, Object value) {
            this.details.put(item, value);
            return this;
        }

        public MessageItem build() {
            return new MessageItem(code, message, details);
        }
    }
}
