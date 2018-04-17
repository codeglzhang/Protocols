package com.example.demo.byte_to_json;

import com.example.demo.protocol.command.Frame;
import io.netty.buffer.ByteBuf;

import java.util.LinkedList;
import java.util.List;

/**
 * 协议路由的实现类，通过枚举类实现了一个单例
 */
public enum  ProtocolRoute {
    PROTOCOLS("src\\main\\resources\\protocols.yml");

    /**
     * 全局的协议配置文件容器
     */
    private final List<Protocol> protocolList;

    /**
     *
     * @param path 配置文件的路径
     */
    ProtocolRoute(String path){
        this.protocolList=YamlLoder.getAllProtocols(path);
    }

    public List<Protocol> getProtocolMap(){
        return protocolList;
    }

    /**
     * 匹配头部报文数据是否符合
     * @param data 收到的一帧数据
     * @param frame 帧结构的配置文件
     * @return 符合返回true，不符合返回false
     */
    private boolean checkHeadData(ByteBuf data, Frame frame){
        byte[] head=new byte[frame.getHead().length];
        data.getBytes(0,head);
        return compareByteArry(head,frame.getHead());
    }


    /**
     * 比较两个byte数组是否相同（长度相同，元素相同）
     * @param a1
     * @param a2
     * @return ture为相同，false为不同。
     */
    private boolean compareByteArry(byte[] a1,byte[] a2){
        if(a1.length!=a2.length) {
            return false;
        }
        for (int n=0;n<=a1.length;n++)
        {
            if(a1[n]!=a2[n])
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 路由算法，找到与接收到的一帧数据数据相匹配的协议
     * @param data 接收到的一帧数据
     * @throws Exception 如果没有匹配的协议则，抛出异常
     */
    public void route(ByteBuf data) throws Exception {
        List<Protocol> list= new LinkedList<>();

        protocolList.forEach(protocol -> {
            if(checkHeadData(data,protocol.getFrame())){
                list.add(protocol);
            }
        });
        if(list.size()==0){
            throw new Exception("找不到对应的协议");
        }

        try{


        }catch (Exception e){

        }
    }

}
