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
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
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
        MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        String boundary = "------bound------";
        httpPost.setHeader("Content-Type", "multipart/form-data; boundary="+boundary);
        for (NameValuePair n : params) {
            if (n.getName() == "picture") {
                File picFile = new File(n.getValue());
                /*FileInputStream fis;
                byte[] data = new byte[(int) picFile.length()];

                try {
                    fis = new FileInputStream(picFile);
                    fis.read(data);
                    fis.close();
                } catch (Exception e) {


                }*/
                //BitmapFactory.Options options = new BitmapFactory.Options();
                //options.inSampleSize=4;
                //Bitmap bm = BitmapFactory.decodeByteArray(data,0,(int) picFile.length());
                //ByteArrayOutputStream baos = new ByteArrayOutputStream();
                //bm.compress(Bitmap.CompressFormat.JPEG,40,baos);
                //byte[] byteImage_photo = baos.toByteArray();
                multipartEntity.addPart(n.getName(), new FileBody(picFile));
            } else {
                try {
                    multipartEntity.addPart(n.getName(), new StringBody(n.getValue()));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        httpPost.setEntity(multipartEntity);

        try {
            return httpClient.execute(httpPost);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
