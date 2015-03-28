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
import org.json.JSONObject;

import java.net.URL;

/**
 * Created by Caroline on 3/28/2015.
 */
 class downloadJSON extends AsyncTask<String,String,String> {
    JSONObject json;
    FirstPage obj;

    public downloadJSON(FirstPage obj) {
        this.obj = obj;
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
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        // update textview here

        try {

            JSONObject returnobj = new JSONObject(result);
            this.obj.setJSON(returnobj);
        }catch (Exception e) {
            e.printStackTrace();
        }
        //Log.d("Server message is ", result);
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
    }
}
