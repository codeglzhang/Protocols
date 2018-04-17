package com.example.demo.protocol;

import com.example.demo.protocol.Handler.CommandHandler;
import com.example.demo.protocol.Handler.FrameSpiltInHandler;
import com.example.demo.protocol.command.CommandReceive;
import com.example.demo.protocol.command.Frame;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Initialization extends ChannelInitializer<SocketChannel>{

    @Autowired
    private Frame frame;
    @Autowired
    CommandReceive commandReceive;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ByteBuf delimite= Unpooled.copiedBuffer(frame.getTail());
        int length=frame.getMaxlength();
        if(length<=0){
            length=1024;
        }
        socketChannel.pipeline()
                .addLast(new DelimiterBasedFrameDecoder(1024,delimite))
                .addLast(new FrameSpiltInHandler(frame))
                .addLast(new CommandHandler(commandReceive));
    }

}
