package com.example.turtle.myriadmobilechallenge.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.turtle.myriadmobilechallenge.R;
import com.example.turtle.myriadmobilechallenge.controller.EventAdapter;
import com.example.turtle.myriadmobilechallenge.controller.EventInterface;
import com.example.turtle.myriadmobilechallenge.controller.RetrofitClient;
import com.example.turtle.myriadmobilechallenge.model.EventInfo;
import com.example.turtle.myriadmobilechallenge.model.SpeakerId;
import com.example.turtle.myriadmobilechallenge.model.SpeakerInfo;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventInfoActivity extends AppCompatActivity {

    EventInterface eventInterface;

    public ImageView detailsImage;
    public TextView detailsTitle;
    public TextView detailsDateInfo;
    public TextView detailInfo;
    public TextView detailsLocation;
    public TextView detailsSpeakersTxt;

    private String id;
    private SpannableStringBuilder spannableString = new SpannableStringBuilder();

    Toolbar toolbar;
    private String key;

    EventInfo  info;
    SpeakerInfo speakerInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        key = getToken();

        Intent idIntent = getIntent();
        id = idIntent.getStringExtra("_id");

        initViews();
        loadData();
    }
    private void initViews() {
        detailsImage = (ImageView) findViewById(R.id.detailsImage);
        detailsTitle = (TextView) findViewById(R.id.eventDetailTitle);
        detailsDateInfo = (TextView) findViewById(R.id.eventDetailDateT);
        detailInfo = (TextView) findViewById(R.id.eventDetailsInfo);
        detailsLocation = (TextView) findViewById(R.id.eventDetailsLocation);
        detailsSpeakersTxt = (TextView) findViewById(R.id.eventDetailsSpeakers);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent EventActivity = new Intent(getApplicationContext(), EventListActivity.class);
                startActivity(EventActivity);
            }
        });
    }

    private void loadData(){

        eventInterface = RetrofitClient.getClient().create(EventInterface.class);
        Call<EventInfo> call = eventInterface.getEventInfo(key,id);

        call.enqueue(new Callback<EventInfo>() {
            @Override
            public void onResponse(Call<EventInfo> call, Response<EventInfo> response) {

                info = response.body();

                try{

                    Picasso.get().load(info.getImage_url())
                            .fit()
                            .into(detailsImage);

                    detailsTitle.setText(info.getTitle());
                    detailInfo.setText(info.getDescription());

                    String dateStr = makeDateStr(info);
                    detailsDateInfo.setText(dateStr);

                    setLocationStr();
                    makeSpeakerStr();


                }
                catch(Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<EventInfo> call, Throwable t) {

            }
        });
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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

    private String makeDateStr(EventInfo eventInfo){
        String newDateStr = "";

        DateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yy ha");
        DateFormat dateFormat2 = new SimpleDateFormat(" h:mma");

        String tmpStr1 = dateFormat1.format(eventInfo.getStartDateTime());
        String tmpStr2 = dateFormat2.format(eventInfo.getEndDateTime());

        newDateStr = tmpStr1 + " -" + tmpStr2;

        return newDateStr;
    }

    private void setLocationStr(){
        //insert icon into location string

        String tmpStr1 = " "+ info.getLocation();

        SpannableStringBuilder tmp = new SpannableStringBuilder(" ");
        Drawable icon = ContextCompat.getDrawable(getApplicationContext(),R.drawable.location_icon);
        icon.setBounds(0,0, icon.getIntrinsicWidth(),icon.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(icon,ImageSpan.ALIGN_BOTTOM);
        tmp.setSpan(span,0,1,Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tmp.append(tmpStr1);

        detailsLocation.setText(tmp);
    }

    private void makeSpeakerStr(){

        ArrayList<SpeakerId> idList = info.getSpeakerIdList();

        eventInterface = RetrofitClient.getClient().create(EventInterface.class);


        for (int i = 0; i < idList.size();i++) {

            String tmpId = idList.get(i).toString();
            Call<SpeakerInfo> call = eventInterface.getSpeakerInfo(key,tmpId);

            call.enqueue(new Callback<SpeakerInfo>() {


                @Override
                public void onResponse(Call<SpeakerInfo> call, Response<SpeakerInfo> response) {
                    speakerInfo = response.body();
                    //Format speaker string
                    spannableString.append(speakerInfo.getSpannable());
                    //display speakers
                    detailsSpeakersTxt.setText(spannableString);
                }

                @Override
                public void onFailure(Call<SpeakerInfo> call, Throwable t) {

                }
            });


        }
    }
}