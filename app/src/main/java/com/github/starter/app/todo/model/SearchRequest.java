package com.github.starter.app.todo.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class SearchRequest {
    private String actionBy;
    private String status;
    private String created;

    @JsonCreator
    public SearchRequest(String actionBy, String status, String created) {
        this.actionBy = actionBy;
        this.status = status;
        this.created = created;
    }

    public String getActionBy() {
        return actionBy;
    }

    public String getStatus() {
        return status;
    }

    public String getCreated() {
        return created;
    }
}
