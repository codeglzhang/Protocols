package com.example.demo.protocol.command;

import com.example.demo.protocol.command.DataFild;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "frame")
/**
 * 帧的配置文件容器
 */
public class Frame {
    private int maxlength;

    private byte[] head;

    private byte[] tail;

    private int  crc_startpos;

    private int crc_length;

    private int crc_type;

    private int crc_isbig;

    private int crcdata_start;

    private int crcdata_end;

    private List<DataFild> commonFields;

    private String functioncodepos;

    public Frame() {
    }

    public byte[] getHead() {
        return head;
    }

    public void setHead(byte[] head) {
        this.head = head;
    }

    public byte[] getTail() {
        return tail;
    }

    public void setTail(byte[] tail) {
        this.tail = tail;
    }


    public int getCrc_startpos() {
        return crc_startpos;
    }

    public void setCrc_startpos(int crc_startpos) {
        this.crc_startpos = crc_startpos;
    }

    public int getCrc_length() {
        return crc_length;
    }

    public void setCrc_length(int crc_length) {
        this.crc_length = crc_length;
    }

    public int getCrc_type() {
        return crc_type;
    }

    public void setCrc_type(int crc_type) {
        this.crc_type = crc_type;
    }

    public int getCrc_isbig() {
        return crc_isbig;
    }

    public void setCrc_isbig(int crc_isbig) {
        this.crc_isbig = crc_isbig;
    }

    public int getCrcdata_start() {
        return crcdata_start;
    }

    public void setCrcdata_start(int crcdata_start) {
        this.crcdata_start = crcdata_start;
    }

    public int getCrcdata_end() {
        return crcdata_end;
    }

    public void setCrcdata_end(int crcdata_end) {
        this.crcdata_end = crcdata_end;
    }

    public int getMaxlength() {
        return maxlength;
    }

    public void setMaxlength(int maxlength) {
        this.maxlength = maxlength;
    }

    public List<DataFild> getCommonFields() {
        return commonFields;
    }

    public void setCommonFields(List<DataFild> commonFields) {
        this.commonFields = commonFields;
    }

    public String getFunctioncodepos() {
        return functioncodepos;
    }

    public void setFunctioncodepos(String functioncodepos) {
        this.functioncodepos = functioncodepos;
    }
    public int getFramelen(){
        int len=tail.length+head.length+crc_length;
        return len;
    }
}
