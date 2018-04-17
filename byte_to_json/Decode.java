package com.example.demo.byte_to_json;

import com.example.demo.protocol.command.Frame;
import com.example.demo.protocol.Handler.Message;
import com.example.demo.protocol.command.CommandReceive;
import io.netty.buffer.ByteBuf;
import net.minidev.json.JSONObject;

/**
 * 根据配置文件，对字节数据进行解码，转化为json数据
 */
public class Decode {
    public static JSONObject decode(Frame frame, CommandReceive commandReceive, ByteBuf msg) throws Exception {
        JSONObject result=null;
        Message message=new FrameSpilt(frame).read(msg);
        result=new CommandSpilt(commandReceive).read(message);
        return result;
    }
}
