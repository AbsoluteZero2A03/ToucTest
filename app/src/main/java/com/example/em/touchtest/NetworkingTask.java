package com.example.em.touchtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


/**
 * Created by em on 08/02/15.
 */
public class NetworkingTask extends AsyncTask<String,Void,HttpResponse> {
    private List<NameValuePair> params;
    public NetworkingTask(List<NameValuePair> param_list) {
        super();
        this.params = param_list;
    }
    protected HttpResponse doInBackground(String... urlStr) {
        HttpClient httpClient = new DefaultHttpClient();
/*
        httpClient.getParams().setParameter("Connection", "Keep-Alive");
        httpClient.getParams().setParameter("Content-Type", "multipart/form-data;");
        httpClient.getParams().setParameter("http.socket.timeout", Integer.valueOf(100000));
        httpClient.getParams().setParameter("http.connection.timeout", Integer.valueOf(100000));
*/
        HttpEntity httpEntity;
        HttpPost httpPost = new HttpPost(urlStr[0]);
        for (NameValuePair n : params) {
            if (n.getName() == "picture") {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize=4;
                Bitmap bm = BitmapFactory.decodeFile(n.getValue(),options);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG,40,baos);
                byte[] byteImage_photo = baos.toByteArray();
                params.add(new BasicNameValuePair("picture_data", Base64.encodeToString(byteImage_photo, Base64.DEFAULT)));
            }
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            return httpClient.execute(httpPost);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
