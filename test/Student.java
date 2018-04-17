package com.example.demo.test;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


public class Student {

    private Name name;

    private String sex;

    public Student() {
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
