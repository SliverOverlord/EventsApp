package com.example.turtle.myriadmobilechallenge.controller;

import android.app.Activity;

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

public class LoginClient {
    public static String connectToServer(Activity activity1, JSONObject request){
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


            //connection.setRequestProperty("Key","Value");

            //connection.addRequestProperty("Username", user);
            //connection.addRequestProperty("Password",pass);

            OutputStream oStream = connection.getOutputStream();
            //OutputStream oStream = new BufferedOutputStream(connection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(oStream, "UTF-8"));
            writer.write(request.toString());

            //writer.write(data);
            //writer.flush();
            writer.close();
            oStream.close();
            //writeStream(outputPost);


            //connection.setChunkedStreamingMode(0);
            //connection.connect();

            InputStream iStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(iStream, "UTF-8"));
            String line = "";
            StringBuilder builder = new StringBuilder();
            while((line = bufferedReader.readLine()) != null){

                builder.append(line);
            }
            bufferedReader.close();
            key = builder.toString();

            if(connection != null){
                connection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }


        return key;
    }
}
