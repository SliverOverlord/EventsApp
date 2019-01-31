package com.example.turtle.myriadmobilechallenge.controller;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;
    public static final String BASE_URL = "https://challenge.myriadapps.com/api/v1/";


    public static Retrofit getClient(){
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;


    }


    //public  EventAdapter getApiInterface(Retrofit eventRetrofit){
    //    return eventRetrofit.create(EventAdapter.class);
    //}

}
