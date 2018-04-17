package com.example.demo.byte_to_json;

import com.example.demo.protocol.command.Frame;
import com.example.demo.protocol.command.CommandReceive;
import com.example.demo.protocol.command.CommandSend;

/**
 * 一个协议配置文件的载体，它由帧结构、接受指令、发送指令三部分组成。
 *
 */
public class Protocol {
    private Frame frame;
    private CommandReceive commandSpilt;
    private CommandSend commandSend;

    public Protocol(Frame frame, CommandReceive commandSpilt,CommandSend commandSend) {
        this.frame = frame;
        this.commandSpilt = commandSpilt;
        this.commandSend=commandSend;
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public CommandReceive getCommandSpilt() {
        return commandSpilt;
    }

    public void setCommandSpilt(CommandReceive commandSpilt) {
        this.commandSpilt = commandSpilt;
    }

    public CommandSend getCommandSend() {
        return commandSend;
    }

    public void setCommandSend(CommandSend commandSend) {
        this.commandSend = commandSend;
    }
}
