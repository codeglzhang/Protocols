package com.example.demo.protocol.Handler;

import com.example.demo.protocol.Utils.Computer;
import com.example.demo.protocol.command.AnalyzeUtils;
import com.example.demo.protocol.command.Command;
import com.example.demo.protocol.command.CommandReceive;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.minidev.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;


import java.util.Iterator;
import java.util.List;


@Component
public class CommandHandler extends ChannelInboundHandlerAdapter {

    private CommandReceive commandReceive;

    public CommandHandler(CommandReceive commandReceive) {
        this.commandReceive = commandReceive;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message= (Message) msg;
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
    }

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
