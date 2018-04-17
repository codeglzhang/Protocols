package com.example.demo.protocol.command;


/**
 * 对应指令中的每个字段，是字段配置文件的容器
 */
public class DataFild {
    private String jsonName;
    private String length;
    private String typeOfData;
    private int isVariable;
    private int isUnit;
    private  String unitNum;
    private String bitScope;
    public DataFild() {
    }

    public String getBitScope() {
        return bitScope;
    }

    public void setBitScope(String bitScope) {
        this.bitScope = bitScope;
    }

    public String getJsonName() {
        return jsonName;
    }

    public void setJsonName(String jsonName) {
        this.jsonName = jsonName;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getTypeOfData() {
        return typeOfData;
    }

    public void setTypeOfData(String typeOfData) {
        this.typeOfData = typeOfData;
    }

    public int getIsVariable() {
        return isVariable;
    }

    public void setIsVariable(int isVariable) {
        this.isVariable = isVariable;
    }

    public int getIsUnit() {
        return isUnit;
    }

    public void setIsUnit(int isUnit) {
        this.isUnit = isUnit;
    }

    public String getUnitNum() {
        return unitNum;
    }

    public void setUnitNum(String unitNum) {
        this.unitNum = unitNum;
    }

}
