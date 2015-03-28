package com.example.caroline.smashrunmobile;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.net.URL;

/**
 * Created by Caroline on 3/28/2015.
 */
 class downloadJSON extends AsyncTask<String,String,String> {
    @Override
    protected String doInBackground(String... url) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpResponse response = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet method = new HttpGet(url[0]);
        try {
            //method.setHeader("Accept", "application/json");
            response = httpclient.execute(method);
        } catch (Exception e) {
            return "stopped at HttpRespone";
        }

        try
        {
            HttpEntity entity = response.getEntity();
            if(entity != null){
                return EntityUtils.toString(entity);
            }
            else{
                return "stopped at HttpEntity";
            }
        }
        catch(Exception e){
            return "Network problem";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        // update textview here
        Log.d("Server message is ", result);
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        Log.d("Pre-execute started", "!");
    }
}
