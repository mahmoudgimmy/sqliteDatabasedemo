package com.example.mahmo.sqlitedemo;

import java.io.Serializable;
// using Serializable to put you object in intent
public class Task implements Serializable {
    private String name;
    private String id;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
