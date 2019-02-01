package com.example.turtle.myriadmobilechallenge.model;

import com.google.gson.annotations.SerializedName;

public class TokenKey {
    @SerializedName("token")
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public TokenKey(String key) {
        this.key = key;
    }
}
