package com.ivanzomi.clientapp;

public class RequestModel {

    public String name;
    public String imagelink;

    public RequestModel(String name, String imagelink) {
        this.name = name;
        this.imagelink = imagelink;
    }

    public RequestModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagelink() {
        return imagelink;
    }

    public void setImagelink(String imagelink) {
        this.imagelink = imagelink;
    }
}
