package com.example.demo.protocol.Utils;

import org.apache.commons.io.FileUtils;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class JsonUtils {
    public static JSONObject readJsonConfig(String configFileName) throws JSONException, IOException {
        File file=new File(configFileName);
        String content= FileUtils.readFileToString(file,"UTF-8");
        JSONObject config=new JSONObject(content);
        return config;
    }
}
