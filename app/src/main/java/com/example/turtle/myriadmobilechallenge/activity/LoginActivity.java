package com.example.turtle.myriadmobilechallenge.activity;
/*
Author Joshua DeNio
Project name: Fargo Events App
Date: 12/20/18

Description:
This app gathers a users login information and submits it to api for a security token.
It then uses the token to request a json from the api and uses the data to populate
a recyclerView with events.

Each item in the recyclerView will open to a page with the events details.
Clicking logout will reset the login credentials and open the login page.

 */
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.turtle.myriadmobilechallenge.R;
import com.example.turtle.myriadmobilechallenge.controller.EventInterface;
import com.example.turtle.myriadmobilechallenge.controller.LoginService;
import com.example.turtle.myriadmobilechallenge.controller.RetrofitClient;
import com.example.turtle.myriadmobilechallenge.model.TokenKey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText userNameTxt;
    private EditText passwordTxt;
    private Button loginBtn;

    private LoginService loginService;
    EventInterface eventInterface;
    TokenKey tokenKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameTxt = findViewById(R.id.userNameTxt);
        passwordTxt = findViewById(R.id.passwordTxt);
        loginBtn = findViewById(R.id.loginBtn);

        userNameTxt.requestFocus();

        //Check to see if there is a saved key
        checkForKey();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean validVar = validate();
                if (validVar == true){


                    //loginService = new LoginService(getApplicationContext(),userNameTxt.getText().toString(),passwordTxt.getText().toString());
                    //loginService.execute();
                    connect();



                    userNameTxt.setText("");
                    passwordTxt.setText("");
                    userNameTxt.requestFocus();


                }
                else {
                    invalidAlert();

                }
            }
        });
    }

    private void checkForKey(){
        try{
            String tmpKey = "";
            SharedPreferences sharedToken = getApplicationContext().getSharedPreferences("tokenInfo", Context.MODE_PRIVATE);
            tmpKey += sharedToken.getString("token", "");
            if(tmpKey != ""){
                //Start new activity and sen in the key
                Intent secondActivity = new Intent(getApplicationContext(), EventListActivity.class);
                secondActivity.putExtra("TokenKey", tmpKey);
                startActivity(secondActivity);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void connect() {

        String user = userNameTxt.getText().toString();
        String pass = passwordTxt.getText().toString();
        eventInterface = RetrofitClient.getClient().create(EventInterface.class);
        Call<TokenKey> call = eventInterface.login(user, pass);

        call.enqueue(new Callback<TokenKey>() {
            @Override
            public void onResponse(Call<TokenKey> call, Response<TokenKey> response) {
                tokenKey = response.body();
                String key = tokenKey.getKey();

                SharedPreferences sharedToken = getApplicationContext().getSharedPreferences("tokenInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedToken.edit();
                editor.putString("token", key);
                editor.apply();

                Intent secondActivity = new Intent(getApplicationContext(), EventListActivity.class);
                secondActivity.putExtra("TokenKey", key);
                getApplicationContext().startActivity(secondActivity);
            }

            @Override
            public void onFailure(Call<TokenKey> call, Throwable t) {

            }
        });
    }

    //Function to save API token
    private void saveToken(String tokenStr){
        SharedPreferences sharedToken = getSharedPreferences("tokenInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedToken.edit();
        editor.putString("token", tokenStr);
        editor.apply();
    }

    //Function to save username and password to sharedPreferences
    public void savelogin(View view){
        SharedPreferences sharedP = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedP.edit();
        editor.putString("userName", userNameTxt.getText().toString());
        editor.putString("password", passwordTxt.getText().toString());
        editor.apply();
    }

    //Function to validate input
    private Boolean validate(){

        Boolean validV = true;
        int str1 = userNameTxt.getText().toString().trim().length();
        int str2 = passwordTxt.getText().toString().trim().length();

        if (str1 == 0 || str2 == 0){
            validV = false;

        }

        return validV;
    }

    //Function alert and reset fields
    private void invalidAlert(){
        userNameTxt.setText("");
        passwordTxt.setText("");
        userNameTxt.requestFocus();

        AlertDialog.Builder alertbuilder = new AlertDialog.Builder(this);
        alertbuilder.setMessage("Enter a valid username and password");

        alertbuilder.setCancelable(true);
        alertbuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });


        AlertDialog alertDialog = alertbuilder.create();
        alertDialog.show();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }


}
