package com.example.turtle.myriadmobilechallenge.model;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import com.google.gson.annotations.SerializedName;

public class SpeakerInfo {
    @SerializedName("id")
    private int id;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("bio")
    private String bio;
    @SerializedName("image_url")
    private String imageUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public SpeakerInfo(int id, String firstName, String lastName, String bio, String imageUrl) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.imageUrl = imageUrl;
    }


    public SpannableString getSpannable(){

        String tmpStr1 = "Speaker\n\n";

        int start = 0;
        int end = tmpStr1.length();

        String tmpStr2 = this.getFirstName() +" "+ this.getLastName() + "\n\n";
        String tmpStr3 = this.getBio() + "\n\n";
        String tmpStr4 = tmpStr1+tmpStr2+tmpStr3;

        SpannableString tmp = new SpannableString(tmpStr4);
        tmp.setSpan(new ForegroundColorSpan(Color.GRAY),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        start = end;
        end = end+tmpStr2.length();
        tmp.setSpan(new RelativeSizeSpan(1.2f),start,end,0);

        end = tmpStr4.length();
        tmp.setSpan(new ForegroundColorSpan(Color.BLACK),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return tmp;
    }
}
