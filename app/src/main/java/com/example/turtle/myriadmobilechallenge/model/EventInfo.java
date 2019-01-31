package com.example.turtle.myriadmobilechallenge.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class EventInfo {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("image_url")
    private String image_url;
    @SerializedName("event_description")
    private String description;
    @SerializedName("start_date_time")
    private Date startDateTime;
    @SerializedName("end_date_time")
    private Date endDateTime;
    @SerializedName("location")
    private String location;
    @SerializedName("featured")
    private String featured;
    @SerializedName("speakers")
    private ArrayList<SpeakerId> speakerIdList = new ArrayList<>();

    public EventInfo(int id, String title, String image_url, String description, Date startDateTime, Date endDateTime, String location, String featured, ArrayList<SpeakerId> speakerIdList) {
        this.id = id;
        this.title = title;
        this.image_url = image_url;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.location = location;
        this.featured = featured;
        this.speakerIdList = speakerIdList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFeatured() {
        return featured;
    }

    public void setFeatured(String featured) {
        this.featured = featured;
    }

    public ArrayList<SpeakerId> getSpeakerIdList() {
        return speakerIdList;
    }

    public void setSpeakerIdList(ArrayList<SpeakerId> speakerIdList) {
        this.speakerIdList = speakerIdList;
    }
}
