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

        this.finishedDateStr = makeDateStr(startDateTime,endDateTime);
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

    /*
    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }
    */

    public boolean isFeatured() {

        return featured;
    }

    public void setFeatured(boolean featured) {

        this.featured = featured;
    }

    private Date makeDate(String dateStr){
        Date newDate = new Date();
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            newDate = dateFormat.parse(dateStr);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return newDate;
    }
/*
    private Date makeDate2(Date date1, Date date2){
        Date newDate = new Date();
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            //newDate = dateFormat.parse(dateStr);
            String tmp = "";
            tmp += date1.getYear()+"-"+date1.getTime()+"-"+date1.getDay();
            tmp += "'T'"+date2.get
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return newDate;
    }
    */

    private String makeDateStr(Date date1, Date date2){
        String dateStr = "";

        //dateStr = dateStr + date1.getMonth()+"/";
        //dateStr = dateStr + date1.getDay()+"/";
        //dateStr = dateStr + date1.getYear()+ " ";
        //dateStr = dateStr + date1.getHours()+"-";
        //dateStr = dateStr + date2.getHours();

        DateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yy hh a");
        DateFormat dateFormat2 = new SimpleDateFormat(" hh:mm a");

        String tmpStr1 = dateFormat1.format(date1);
        String tmpStr2 = dateFormat2.format(date2);

        dateStr = tmpStr1 + " -" + tmpStr2;

        return dateStr;
    }

}
