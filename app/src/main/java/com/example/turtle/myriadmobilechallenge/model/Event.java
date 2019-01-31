package com.example.turtle.myriadmobilechallenge.model;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event {
    @SerializedName("id")
    private int eventId;
    @SerializedName("title")
    private String title;
    @SerializedName("image_url")
    private String imageUrl;

    @SerializedName("start_date_time")
    private Date startDateTime;
    @SerializedName("end_date_time")
    private Date endDateTime;

    private String finishedDateStr;

    @SerializedName("location")
    private String location;
    @SerializedName("featured")
    private boolean featured;

    public Event(int eventId, String title, String imageUrl, Date startDateTime,
                 Date endDateTime, String location, boolean featured) {
        this.eventId = eventId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.location = location;
        this.featured = featured;

        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;

        this.finishedDateStr = makeDateStr();
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {

        this.eventId = eventId;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getImageUrl() {

        return imageUrl;
    }

    public void setImageUrl(String imageId) {

        this.imageUrl = imageId;
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

    public String getDateStr() {
        return finishedDateStr;
    }

    public boolean isFeatured() {

        return featured;
    }

    public void setFeatured(boolean featured) {

        this.featured = featured;
    }

    public String makeDateStr(){

        DateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yy ha");
        DateFormat dateFormat2 = new SimpleDateFormat(" hh:mma");

        String tmpStr1 = dateFormat1.format(this.getStartDateTime());
        String tmpStr2 = dateFormat2.format(this.getEndDateTime());

        String formatedDateStr = tmpStr1+" -"+tmpStr2;
        formatedDateStr = formatedDateStr.toLowerCase();

        return formatedDateStr;
    }

}
