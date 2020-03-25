package com.github.starter.app.config;

import java.util.List;

public class ExecutionScript {
    private String fileName;
    private List<String> commands;

    public ExecutionScript(String fileName, List<String> commands) {
        this.fileName = fileName;
        this.commands = commands;
    }

    public String getFileName() {
        return fileName;
    }

    public List<String> getCommands() {
        return commands;
    }
}
