package com.example.demo.byte_to_json;

import com.example.demo.protocol.command.Frame;
import com.example.demo.protocol.Handler.Message;
import com.example.demo.protocol.command.AnalyzeUtils;
import io.netty.buffer.ByteBuf;
import net.minidev.json.JSONObject;
import sun.misc.CRC16;

import java.util.zip.CRC32;

public class FrameSpilt {
    private Frame frame;

    public FrameSpilt(Frame frame) {
        this.frame = frame;
    }

    public Message read(ByteBuf msg) {
        ByteBuf byteBuf =  msg;
        byte[] com = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(com);
        byteBuf.resetReaderIndex();
        byteBuf.readBytes(frame.getHead().length);
        Message message=null;
        try {
            if (CRCcheck(com)) {
                JSONObject jsonObject = new JSONObject();
                if (frame.getCommonFields().size() > 0) {
                    jsonObject=AnalyzeUtils.getFild(jsonObject,frame.getCommonFields(),byteBuf);
                }
                 message = new Message(byteBuf, jsonObject,frame.getFunctioncodepos());

            } else {
                throw new Exception(new String("CRC校验出错！！！".getBytes(), "UTF-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    private boolean CRCcheck(byte[] com) throws Exception {
        int start=frame.getCrc_startpos();
        int length=frame.getCrc_length();
        int datastart=frame.getCrcdata_start();
        int dataend=frame.getCrcdata_end();
        long crcvalue=0;

        if(start<0){
            start=com.length+start+frame.getTail().length;
        }

        if(dataend<0){
            dataend=com.length+dataend+frame.getTail().length;
        }

        if(frame.getCrc_isbig()>0){
            crcvalue=com[start]&0xff;
            for (int i=1;i<length;i++) {
                crcvalue =(crcvalue)<<8|(com[start+i]&0xff);
            }
        }else {
            crcvalue=com[start+length-1]&0xff;
            for (int i=start+length-2;i>=start;i--) {
                crcvalue =(crcvalue<<8)|(com[i]&0xff);
            }
        }

        if(frame.getCrc_type()==32)
        {
            CRC32 crc32=new CRC32();

            crc32.update(com,datastart,dataend-datastart+1);
            if(crcvalue==crc32.getValue()) {
                return true;
            }else
            {
                return false;
            }
        }
        else if(frame.getCrc_type()==16)
        {
            CRC16 crc16=new CRC16();
            for(int i=datastart;i<=dataend;i++){
                crc16.update(com[i]);
            }
            if(crcvalue==crc16.value) {
                return true;
            }else
            {
                return false;
            }

        }
        else {
            throw new Exception(new String("CRC配置信息有误！！！".getBytes(),"UTF-8"));
        }
    }

}
