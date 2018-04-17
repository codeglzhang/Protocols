package com.example.demo.byte_to_json;

import com.example.demo.protocol.Handler.CommandHandler;
import com.example.demo.protocol.Handler.Message;
import com.example.demo.protocol.Utils.Computer;
import com.example.demo.protocol.command.AnalyzeUtils;
import com.example.demo.protocol.command.Command;
import com.example.demo.protocol.command.CommandReceive;
import com.example.demo.protocol.command.CommandSend;
import io.netty.buffer.ByteBuf;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Iterator;
import java.util.List;


/**
 * 根据具体的协议的指令配置，对报文数据进行解析
 *
 */
public class CommandSpilt {
    /**
     * 接收类指令
     */
    private CommandReceive commandReceive;

    /**
     * 发送类指令
     */
    private CommandSend commandSend;

    public CommandSpilt(CommandSend commandSend) {
        this.commandSend = commandSend;
    }

    public CommandSpilt(CommandReceive  commandReceive) {
        this.commandReceive = commandReceive;
    }


    /**
     * 根据配置文件，将字节数据解析成json数据
     * 1.根据配置文件，计算出当前指令中所携带的功能码。
     * 2.根据计算出的功能码，找到指令对应的配置文件
     * 3.根据指令的配置文件，对指令的每一个字段进行一步一步的解析
     * 4.字段解析步骤：
     *     1.字段类型解析（单元、非单元）
     *     2.字段长度解析（变长或固定）
     *     3.字段数据类型解析（bin或者byte数组）
     *     4.根据解析结果获取数据。
     *
     * @param message 包含了要解析的数据，和配置文件
     * @return 解析完毕后的json对象
     * @throws Exception
     */
    public JSONObject read(Message message) throws Exception {
        if(message==null){
            return null;
        }
        String funtioncode=computeFuncCode(message);
        Iterator<Command> iterator=commandReceive.getCommands_receive().iterator();

        while (iterator.hasNext()){
            Command command=iterator.next();
            if (funtioncode.equals(command.getFunctioncode())){
                JSONObject com=new JSONObject();
                if(command.getDataFields().size()>0){
                    com=AnalyzeUtils.getFild(com,command.getDataFields(),message.getCommand());
                }
                message.getCommandlist().add(com);
            }
        }
        JSONObject jsondata= message.getJsonObject().appendField("CommandList",message.getCommandlist());
        Logger logger= LoggerFactory.getLogger(CommandHandler.class);
        logger.info(jsondata.toString());
        System.out.println(jsondata.toString());
        return jsondata;
    }

    /**
     * 根据配置文件计算当前指令的功能码
     * @param message
     * @return 计算完成的功能码
     */
    private String computeFuncCode(Message message){
        StringBuilder result=new StringBuilder();
        Computer computer=new Computer();
        List<String> tmp=computer.spiltExpression(message.getFunctioncode(),"+");
        Iterator<String> iterator=tmp.iterator();
        while (iterator.hasNext()) {
            String str=iterator.next();
            if (str.equals("+")) {
                result.append(str);
            }else if(str.indexOf('-')>0){
                String[] arry=str.split("-");
                int start= Integer.parseInt(arry[0]);
                int end=Integer.parseInt(arry[1]);
                byte[] func=new byte[end-start+1];
                ByteBuf byteBuf=message.getCommand();
                byteBuf.getBytes(byteBuf.readerIndex()+start,func);
                for(int n=0;n<func.length;n++) {
                    result.append(func[n]&0xff);
                }
            }
            else {
                int value=(int)message.getJsonObject().get(str);
                result.append(String.valueOf(value));
            }
        }
        return  result.toString();
    }

}
