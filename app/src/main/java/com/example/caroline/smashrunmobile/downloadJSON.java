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
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Caroline on 3/28/2015.
 */
 class downloadJSON extends AsyncTask<String,String,String> {
    JSONObject json;
    DisplayRun obj;
    FirstPage firstobj;

    public downloadJSON(DisplayRun obj) {
        this.obj = obj;
        this.firstobj = null;

    }

    public downloadJSON(FirstPage obj) {
        this.firstobj = obj;
        this.obj = null;

    }
    @Override
    protected String doInBackground(String... url) {

        HttpResponse response = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet method = new HttpGet(url[0]);

        try
        {
            response = httpclient.execute(method);
            HttpEntity entity = response.getEntity();
            if(entity != null){
                return EntityUtils.toString(entity);
            }
            else{
                return "Empty String.";
            }
        }
        catch(Exception e){
            return "Network problem";
        }
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (this.obj != null) {
            //run by single run query
            try {

                JSONObject returnobj = new JSONObject(result);
                this.obj.setJSON(returnobj);
            }catch (Exception e) {
                e.printStackTrace();
            }
        } else if (this.firstobj != null) {
            try {
                JSONArray returnobj = new JSONArray(result);
                this.firstobj.setJSON(returnobj);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Log.d("Server message is ", result);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
