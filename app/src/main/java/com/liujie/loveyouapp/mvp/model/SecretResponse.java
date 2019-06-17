package com.liujie.loveyouapp.mvp.model;

public class SecretResponse {
    private int id;
    private String name;
    private String phoneNum;

    public SecretResponse(String name, String phoneNum) {
        this.name = name;
        this.phoneNum = phoneNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
