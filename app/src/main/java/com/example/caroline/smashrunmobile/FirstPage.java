package com.example.caroline.smashrunmobile;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpEntity;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class FirstPage extends ActionBarActivity {

    public JSONObject activityData;
    public EditText userIdField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        userIdField = (EditText) findViewById(R.id.userid);

        EditText passwordField = (EditText) findViewById(R.id.password);
        String password = passwordField.getText().toString();

        Button confirmButton = (Button) findViewById(R.id.button);
        confirmButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                doSomething();
            }
        });


    }

    public void doSomething(){
        String userid = userIdField.getText().toString();

        Log.d("SmashRunMobile", "User Id: " + userid);
        TextView t = (TextView)findViewById(R.id.outTextView);

        t.setText("test :] ");

        configureJSON();
    }

    public void configureJSON() {
        DefaultHttpClient   httpclient = new DefaultHttpClient(new BasicHttpParams());

        //example json request
        String url = "https://api.smashrun.com/v1/my/activities/2088942?";
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
        nameValuePair.add(new BasicNameValuePair("client_id", "caroline_l097faff0"));
        nameValuePair.add(new BasicNameValuePair("access_token", "AL2LRz0jUgHhih9mjrLSYhbk5LTxVOxSFbA0PeFx5sAA"));

        String query = URLEncodedUtils.format(nameValuePair, "utf-8");
        url += query;

        Log.d("uri: ", url);

        new downloadJSON(this).execute(url);
        //use activityData JSONObject
    }

    public void setJSON(JSONObject j) {
        this.activityData = j;

        TextView t = (TextView)findViewById(R.id.outTextView);
        try {
            t.setText("Distance: " + activityData.getString("distance"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first_page, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
