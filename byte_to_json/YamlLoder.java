package com.example.demo.byte_to_json;

import com.example.demo.protocol.command.Frame;
import com.example.demo.protocol.command.CommandReceive;
import com.example.demo.protocol.command.CommandSend;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class YamlLoder {
    public static <T> T loadResource(String Yamlpath,Class<T> target){
        Yaml yaml=new Yaml();
        FileInputStream yml= null;
        T result=null;
        try {
            yml = new FileInputStream(new File(Yamlpath));
            result= yaml.loadAs(yml,target);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                yml.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static List<Protocol> getAllProtocols(String Yamlpath){
        ArrayList<String> protocol_name=loadResource(Yamlpath,ArrayList.class);
        List<Protocol> ProtocolList=new ArrayList<>();
        protocol_name.forEach(name->{
            String FrameYaml=name+"Frame.yml";
            String CommandReceiveYaml=name+"CommandReceive.yml";
            String CommandSendYaml=name+"CommandSend.yml";

            Frame frame=loadResource(FrameYaml,Frame.class);
            CommandReceive commandReceive=loadResource(CommandReceiveYaml,CommandReceive.class);
            CommandSend commandSend=loadResource(CommandSendYaml,CommandSend.class);
            Protocol protocol=new Protocol(frame,commandReceive,commandSend);
            ProtocolList.add(protocol);
        });
        return ProtocolList;
    }
}
