package com.example.demo.protocol.Handler;


import io.netty.buffer.ByteBuf;
import net.minidev.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Message {
    private ByteBuf command;
    private JSONObject jsonObject;
    private String functioncode;
    private List<JSONObject> commandlist=new ArrayList<>();
    public Message() {
    }

    public Message(ByteBuf command, JSONObject jsonObject,String functioncode) {
        this.command = command;
        this.jsonObject = jsonObject;
        this.functioncode=functioncode;
    }

    public String getFunctioncode() {
        return functioncode;
    }

    public void setFunctioncode(String functioncode) {
        this.functioncode = functioncode;
    }

    public ByteBuf getCommand() {
        return command;
    }

    public void setCommand(ByteBuf command) {
        this.command = command;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public List<JSONObject> getCommandlist() {
        return commandlist;
    }

    public void setCommandlist(List<JSONObject> commandlist) {
        this.commandlist = commandlist;
    }
}
