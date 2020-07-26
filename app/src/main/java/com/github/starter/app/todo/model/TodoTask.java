package com.github.starter.app.todo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.ZonedDateTime;


public class TodoTask implements Serializable {
    private final String id;
    private final String description;

    @JsonProperty("action_by")
    private final String actionBy;

    private final ZonedDateTime created;

    private final String status;

    private final ZonedDateTime updated;

    @JsonCreator
    public TodoTask(@JsonProperty("id") String id, @JsonProperty("description") String description, @JsonProperty("action_by") String actionBy,
                    @JsonProperty("created") ZonedDateTime created, @JsonProperty("status") String status, @JsonProperty("updated") ZonedDateTime updated) {
        this.id = id;
        this.description = description;
        this.actionBy = actionBy;
        this.created = created;
        this.status = status;
        this.updated = updated;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getActionBy() {
        return actionBy;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public String getStatus() {
        return status;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }

    @Override
    public String toString() {
        return new StringBuilder("id:").append(id).append(", description: ").append(description).append(", action_by: ").append(actionBy)
            .append(", created: ").append(created).append(", status: ").append(status).append(", updated: ").append(updated).toString();
    }
}
