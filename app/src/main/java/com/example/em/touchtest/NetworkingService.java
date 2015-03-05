package com.example.em.touchtest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.List;


public class NetworkingService extends Service {
    public NetworkingService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public HttpResponse sendPostData(String urlStr, List<NameValuePair> params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(urlStr);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            return httpClient.execute(httpPost);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
