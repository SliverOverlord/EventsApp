package com.example.turtle.myriadmobilechallenge.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

import com.example.turtle.myriadmobilechallenge.R;
import com.example.turtle.myriadmobilechallenge.controller.EventAdapter;
import com.example.turtle.myriadmobilechallenge.controller.EventInterface;
import com.example.turtle.myriadmobilechallenge.controller.RetrofitClient;
import com.example.turtle.myriadmobilechallenge.model.Event;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventListActivity extends AppCompatActivity {
    private String key = "";
    private ArrayList<Event> eventList = new ArrayList<>();

    RecyclerView eventRecycler;
    EventAdapter eventAdapter;
    EventInterface eventInterface;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        key = getToken();


        initViews();

    }

    private void initViews(){
        eventRecycler = (RecyclerView)findViewById(R.id.eventRecycler);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        eventRecycler.setLayoutManager(layoutManager);

        loadJSON();
    }

    private void loadJSON(){
        eventInterface = RetrofitClient.getClient().create(EventInterface.class);
        Call<ArrayList<Event>> call = eventInterface.getAllEvents(key);


        call.enqueue(new Callback<ArrayList<Event>>() {
            @Override
            public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {
                eventList = response.body();

                //Log.d("TAG","Response = " + eventList);

                try{
                    eventAdapter = new EventAdapter(getApplicationContext(), eventList);
                    eventRecycler.setAdapter(eventAdapter);
                }
                catch(Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Event>> call, Throwable t) {
                finish();
            }
        });
    }

    private String getToken(){

            String tmpKey = "";
            SharedPreferences sharedToken = getApplicationContext().getSharedPreferences("tokenInfo", Context.MODE_PRIVATE);
            tmpKey += sharedToken.getString("token", "");
            if(tmpKey == ""){
                //Send back to Login if key has been cleared
                Intent nextActivity = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(nextActivity);
                finish();
            }

        return tmpKey;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.logout){
            //Stuff to reset login and logout.
            SharedPreferences sharedToken = getSharedPreferences("tokenInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedToken.edit();
            editor.putString("token", "");
            editor.apply();

            Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginActivity);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        logout();
    }


    //Function to reset API token and logout.
    private void logout(){
        try{
            SharedPreferences sharedToken = getSharedPreferences("tokenInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedToken.edit();
            editor.putString("token", "");
            editor.apply();

            Intent logoutActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(logoutActivity);
            finish();
        }
        catch (Exception e){
            e.printStackTrace();
        }



    }

}
