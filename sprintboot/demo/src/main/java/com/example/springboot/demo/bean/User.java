package com.example.springboot.demo.bean;


public class User {
    private String name;
    private int id;

    public User(){

    }
    public User(int id,String name){
        this.id = id;
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

}
