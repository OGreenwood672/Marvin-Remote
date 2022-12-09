package com.ogreenwood.discord_music;

import android.util.Log;
import android.widget.Toast;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class Bot {

    public String NAME;
    public String AVATAR_URL = "https://i.ibb.co/cgN7sYJ/marvin.png";
    public String WEBHOOK = "None";

    public Bot(String name, String webhook) {
        NAME = name;
        WEBHOOK = webhook;
    }

    public void post(String content) {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                if (WEBHOOK == "None") {
                    return;
                }

                try  {
                    HttpClient httpclient = HttpClients.createDefault();
                    HttpPost httppost = new HttpPost(WEBHOOK);

                    ArrayList<NameValuePair> params = new ArrayList<>(2);
                    params.add(new BasicNameValuePair("username", NAME));
                    params.add(new BasicNameValuePair("avatar_url", AVATAR_URL));
                    params.add(new BasicNameValuePair("content", content));
                    httppost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));


                    try {
                        httpclient.execute(httppost);
                        Log.e("[BOT]", "POST REQUEST SENT");
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("[BOT]", "POST REQUEST FAILED");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }

}
