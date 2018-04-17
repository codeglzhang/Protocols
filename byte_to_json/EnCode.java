package com.example.demo.byte_to_json;

import com.example.demo.protocol.command.DataFild;
import com.example.demo.protocol.command.Frame;
import com.example.demo.protocol.command.AnalyzeUtils;
import com.example.demo.protocol.command.CommandSend;
import com.example.demo.protocol.command.DataType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minidev.json.JSONObject;
import sun.misc.CRC16;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;

/**
 * 将Json数据进行编码，转换成byte数组。
 */
public class EnCode {

    public static byte[] encode(Frame frame, CommandSend commandSend, JSONObject data) {
        ByteBuf result = Unpooled.buffer();
        result.writeBytes(frame.getHead());
        List<DataFild> list = frame.getCommonFields();
        result = writeData(result, list, data);
        result=compute_crc(frame,result);
        byte[] res=new byte[result.readableBytes()];
        result.readBytes(res);
        return res;
    }

    /**
     * 将指令字段由Json格式转为二进制数据
     * @param result
     * @param list
     * @param data
     * @return
     */
    private static ByteBuf writeData(ByteBuf result, List<DataFild> list, JSONObject data) {
        Iterator<DataFild> fields = list.iterator();
        while (fields.hasNext()) {
            DataFild field = fields.next();
            if (field.getIsUnit() > 0) {
                try {
                    int unitnum = AnalyzeUtils.computrValue(field.getUnitNum(), data);
                    List<DataFild> unitlist = new ArrayList<>();
                    unitlist.add(field);
                    while (true) {
                        if (!fields.hasNext()) {
                            break;
                        }
                        field = fields.next();
                        if (field.getIsUnit() <= 0) {
                            break;
                        }
                        unitlist.add(field);
                    }
                    int tmp = 0;
                    for (int n = 1; n <= unitnum; n++) {
                        for (int m = 0; m < unitlist.size(); m++) {
                            result=write(data,field,result,field.getJsonName()+n);
                            tmp++;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (!fields.hasNext()) {
                break;
            }
            result=write(data,field,result,field.getJsonName());
        }
        return result;
    }

    /**
     * 按照数据格式写入数据，目前支持的是byte数组和bin位
     * @param data
     * @param fild
     * @param byteBuf
     * @param JsonName
     * @return
     */
    private static ByteBuf write(JSONObject data, DataFild fild, ByteBuf byteBuf, String JsonName){
        if(fild.getTypeOfData().equals(DataType.BYTE)){
            Byte[] fielddata = (Byte[]) data.get(JsonName);
            byteBuf.writeBytes(AnalyzeUtils.ByteArryTobyteArry(fielddata));
        }else if(fild.getTypeOfData().equals(DataType.BIN)){
            String[] scope = fild.getBitScope().split("-");
            int start = Integer.valueOf(scope[0]);
            int end = Integer.valueOf(scope[1]);
            byte da = (Byte)data.get(JsonName);
            if (start == 0) {
                byteBuf.writeByte(da&0xff);
            }else {
                byte fielddata = byteBuf.getByte(byteBuf.writerIndex() - 1);
                int t=fielddata<<(end-start+1)|(da&0xff);
                byteBuf.setByte(byteBuf.writerIndex()-1,t);
            }
        }

        return byteBuf;
    }

    /**
     * 计算CRC
     * @param frame
     * @param byteBuf
     * @return
     */
    private static ByteBuf compute_crc(Frame frame, ByteBuf byteBuf){
        byte[] data=new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(data);
        byteBuf.resetReaderIndex();

        int datastart=frame.getCrcdata_start();
        int dataend=frame.getCrcdata_end();

        long result=0;
        int type=frame.getCrc_type();

        if(dataend<0){
            dataend=data.length+dataend+frame.getTail().length;
        }
        if(type==16){
            CRC16 crc16=new CRC16();
            for(int i=datastart;i<=dataend;i++){
                crc16.update(data[i]);
            }
            if(frame.getCrc_isbig()>0) {
                byteBuf.writeInt(crc16.value);
            }else {
                byteBuf.writeIntLE(crc16.value);
            }
        }else if(type==32){
            CRC32 crc32=new CRC32();
            crc32.update(data,datastart,dataend-datastart+1);
            if(frame.getCrc_isbig()>0) {
                byteBuf.writeLong(crc32.getValue());
            }else {
                byteBuf.writeLongLE(crc32.getValue());
            }
        }
        return byteBuf;
    }
}