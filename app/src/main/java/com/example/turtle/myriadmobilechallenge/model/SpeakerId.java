package com.example.turtle.myriadmobilechallenge.model;

import com.google.gson.annotations.SerializedName;


public class SpeakerId {
    @SerializedName("id")
    private int id;

    public SpeakerId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String tmp;
        tmp = Integer.toString(this.getId());
        return tmp;
    }
}
