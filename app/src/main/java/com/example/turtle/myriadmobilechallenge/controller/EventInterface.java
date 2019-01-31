package com.example.turtle.myriadmobilechallenge.controller;

import com.example.turtle.myriadmobilechallenge.model.Event;
import com.example.turtle.myriadmobilechallenge.model.EventInfo;
import com.example.turtle.myriadmobilechallenge.model.SpeakerInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface EventInterface {
    @GET("events")
   Call<ArrayList<Event>> getAllEvents(@Header("Authorization") String tokenKey);

    @GET("events/{id}")
    Call<EventInfo> getEventInfo(@Header("Authorization") String tokenKey, @Path("id") String id);

    @GET("speakers/{id}")
    Call<SpeakerInfo> getSpeakerInfo(@Header("Authorization") String tokenKey, @Path("id") String id);
}
