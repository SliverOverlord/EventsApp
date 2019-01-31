package com.example.turtle.myriadmobilechallenge.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.turtle.myriadmobilechallenge.activity.EventListActivity;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginService extends AsyncTask<String, Integer , String> {
    private final String userN;
    private final String passW;
    private String key = "";
    Context context;

    public LoginService(Context context1, String userStr, String passStr) {
        userN = userStr;
        passW = passStr;
        context = context1;
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            JSONObject request = new JSONObject();
            request.put("Username", userN);
            request.put("Password", passW);


            String keyJsonStr = connectToServer(context, request);
            JsonObject keyJson = new JsonParser().parse(keyJsonStr).getAsJsonObject();
            key = keyJson.get("token").getAsString();



        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        SharedPreferences sharedToken = context.getSharedPreferences("tokenInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedToken.edit();
        editor.putString("token", key);
        editor.apply();

        Intent secondActivity = new Intent(context, EventListActivity.class);
        secondActivity.putExtra("TokenKey", key);
        context.startActivity(secondActivity);
    }


    public static String connectToServer(Context context2, JSONObject request) {
        String key = "";
        try {
            URL url = new URL("https://challenge.myriadapps.com/api/v1/login");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("POST");
            connection.connect();
            connection.setConnectTimeout(100000);



            OutputStream oStream = connection.getOutputStream();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(oStream, "UTF-8"));
            writer.write(request.toString());

            writer.close();
            oStream.close();


            InputStream iStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(iStream, "UTF-8"));
            String line = "";
            StringBuilder builder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {

                builder.append(line);
            }
            bufferedReader.close();
            key = builder.toString();

            if (connection != null) {
                connection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return key;
    }
}