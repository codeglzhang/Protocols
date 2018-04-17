package com.example.demo.protocol.command;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties
public class CommandSend {
    private List<Command> commands_send=new ArrayList<>();

    public CommandSend() {
    }

    public List<Command> getCommands_send() {
        return commands_send;
    }

    public void setCommands_send(List<Command> commands_send) {
        this.commands_send = commands_send;
    }
}
