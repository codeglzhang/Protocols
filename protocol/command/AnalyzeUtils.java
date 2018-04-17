package com.example.demo.protocol.command;


import com.example.demo.protocol.Utils.Computer;
import io.netty.buffer.ByteBuf;
import net.minidev.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 解析接收到的指令字节数组的工具类。
 */
public class AnalyzeUtils {
    /**
     * 根据指令字段的配置信息，将字节数据转换为json数据。
     * @param jsonObject
     * @param list
     * @param byteBuf
     * @return
     * @throws Exception
     */
    public  static JSONObject  getFild(JSONObject jsonObject, List<DataFild> list, ByteBuf byteBuf) throws Exception {
        Iterator<DataFild> filds = list.iterator();

        while (filds.hasNext()) {
            DataFild fild = filds.next();
            int unitnum=1;

            if (fild.getIsUnit() > 0) {
                String un = fild.getUnitNum();
                if (un.indexOf('=') > 0) {
                    unitnum = computrValue(un,jsonObject);
                } else {
                    unitnum = Integer.valueOf(un);
                }

                List<DataFild> unitlist = new ArrayList<>();
                List<Integer> lenList = new ArrayList<>();
                unitlist.add(fild);
                lenList.add(computelen(fild, jsonObject));
                while (true) {
                    if (!filds.hasNext()) {
                        break;
                    }
                    fild = filds.next();
                    if (fild.getIsUnit() <= 0) {
                        break;
                    }
                    unitlist.add(fild);
                    lenList.add(computelen(fild, jsonObject));
                }
                int tmp=0;
                for(int n=1;n<=unitnum;n++) {
                    for(int m=0;m<unitlist.size();m++) {
                        DataFild unitfields=unitlist.get(tmp);
                        byte[] data = getdata(fild, byteBuf,lenList.get(m));
                        jsonObject.appendField(unitfields.getJsonName()+n,byteArryToByteArry(data));
                        tmp++;
                    }
                }
            }
            if (!filds.hasNext()) {
                break;
            }
            int length=computelen(fild,jsonObject);
            jsonObject=getdata(fild,byteBuf,length,jsonObject);
        }
        return jsonObject;
    }

    /**
     * 调用计算器计算表达式的值
     * @param len
     * @param jsonObject
     * @return
     * @throws Exception
     */
    public static int computrValue(String len,JSONObject jsonObject) throws Exception {
        String excepssion=len.substring(len.indexOf('=')+1);
        StringBuffer tmp=new StringBuffer();
        Computer computer=new Computer();
        List<String> list=computer.spiltExpression(excepssion,"+-*/$()");

        Iterator<String> iterator=list.iterator();
        while (iterator.hasNext()){
            String s=iterator.next();
            if(computer.isNumber(s)||computer.isOperators(s)) {
                tmp.append(s);
            }else {
                Byte[] num= (Byte[]) jsonObject.get(s);
                if(num.length>62)
                {
                    throw new Exception(AnalyzeUtils.class+":value is to big");
                }
                else {
                    long number = 0;
                    for(int n=0;n<num.length;n++)
                    {
                        number=(number<<8)|(num[n]&0xff);
                    }
                    tmp.append(String.valueOf(number));
                }
            }
        }

        return computer.compute(tmp.toString());
    }

    /**
     * 将byte数组转换为Byte数组，便于存放在json数据中。因为json中只能读取写入Byte数组，无法读取写入byte数组。
     * @param a
     * @return
     */
    public static Byte[] byteArryToByteArry(byte[] a){
        Byte[] bytes=new Byte[a.length];
        for (int n=0;n<a.length;n++){
            bytes[n]=a[n];
        }
        return bytes;
    }

    /**
     * 将Byte数组，转换为byte数组，用于Byetbuf的写入，因为Byetbuf只能写入byte数组。
     * @param data
     * @return
     */
    public static byte[] ByteArryTobyteArry(Byte[] data){
        byte[] bytes=new byte[data.length];
        for (int n=0;n<data.length;n++) {
            bytes[n]=data[n];
        }
        return bytes;
    }

    /**
     * 根据配置信息获取指令的非单元字段的具体数据，并直接将其存入json中。
     * @param fild
     * @param byteBuf
     * @param length
     * @param jsonObject
     * @return
     */
    private static JSONObject getdata(DataFild fild,ByteBuf byteBuf,int length,JSONObject jsonObject){
        String datatype = fild.getTypeOfData();
        if (datatype.equals(DataType.BYTE)) {
            byte[] filddata = new byte[length];
            byteBuf.readBytes(filddata);
            jsonObject.appendField(fild.getJsonName(),byteArryToByteArry(filddata));
        } else if (datatype.equals(DataType.BIN)) {
            String[] scope = fild.getBitScope().split("-");
            int start = Integer.valueOf(scope[0]);
            int end = Integer.valueOf(scope[1]);
            String data = null;
            Byte filddata = 0;
            if (start == 0) {
                data = Integer.toBinaryString(byteBuf.readByte() & 0xff);
            }
            filddata = Byte.valueOf(data.substring(start, end + 1));
            jsonObject.appendField(fild.getJsonName(), filddata);
        }
        return jsonObject;
    }

    /**
     * 根据配置信息获取指令的单元字段的具体数据，并返回byte数组。这个函数是专门用于获取单元字段的数据的，
     * 因为单元字段的json键值是动态生成的(jsonname+序号)，所以这里只能返回一个字节数组，调用此方法的地方再根据具体的键值写入json。
     * @param fild
     * @param byteBuf
     * @param length
     * @return
     */
    private static byte[] getdata(DataFild fild,ByteBuf byteBuf,int length){
        String datatype = fild.getTypeOfData();
        if (datatype.equals(DataType.BYTE)) {
            byte[] filddata = new byte[length];
            byteBuf.readBytes(filddata);
            return filddata;
        } else if (datatype.equals(DataType.BIN)) {
            String[] scope = fild.getBitScope().split("-");
            int start = Integer.valueOf(scope[0]);
            int end = Integer.valueOf(scope[1]);
            String data = null;
            Byte filddata = 0;
            if (start == 0) {
                data = Integer.toBinaryString(byteBuf.readByte() & 0xff);
            }
            filddata = Byte.valueOf(data.substring(start, end + 1));
            return new byte[]{filddata};
        }else {
            return null;
        }
    }


    /**
     * 计算配置文件中length表达式的值。
     * @param fild
     * @param jsonObject
     * @return
     * @throws Exception
     */
    private static int computelen(DataFild fild,JSONObject jsonObject) throws Exception {
        int length=0;
        String datatype=fild.getTypeOfData();
        if (fild.getIsVariable() <= 0) {
            length = Integer.valueOf(fild.getLength());
        } else if (!datatype.equals(DataType.BIN)) {
            length = computrValue(fild.getLength(), jsonObject);
        }
        return length;
    }

}
