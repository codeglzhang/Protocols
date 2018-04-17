package com.example.demo.protocol.command;

import com.example.demo.test.Name;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties
public class CommandReceive {
    private List<Command> commands_receive= new ArrayList<>();

    public CommandReceive() {
    }

    public List<Command> getCommands_receive() {
        return commands_receive;
    }

    public void setCommands_receive(List<Command> commands_receive) {
        this.commands_receive = commands_receive;
    }
}
