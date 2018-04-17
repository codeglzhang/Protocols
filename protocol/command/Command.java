package com.example.demo.protocol.command;

import java.util.ArrayList;
import java.util.List;

public class Command {
    protected String functioncode;
    protected int isVariable;
    protected List<DataFild> dataFields = new ArrayList<>();

    public Command() {
    }

    public String getFunctioncode() {
        return functioncode;
    }

    public void setFunctioncode(String functioncode) {
        this.functioncode = functioncode;
    }

    public int getIsVariable() {
        return isVariable;
    }

    public void setIsVariable(int isVariable) {
        this.isVariable = isVariable;
    }

    public List<DataFild> getDataFields() {
        return dataFields;
    }

    public void setDataFields(List<DataFild> dataFields) {
        this.dataFields = dataFields;
    }

}
