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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class FirstPage extends ActionBarActivity {

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

        configureJSON();
        t.setText("test :] ");
    }

    public void configureJSON() {
        DefaultHttpClient   httpclient = new DefaultHttpClient(new BasicHttpParams());

        //example json request
        HttpPost httppost = new HttpPost("https://api.smashrun.com/v1/my/activities/2088942");

        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
        nameValuePair.add(new BasicNameValuePair("client_id", "caroline_l097faff0"));
        nameValuePair.add(new BasicNameValuePair("access_token", "AWYToBBf9ZnTQ2GK5vVwWjBI0xmwxHWOaQ0mBrnsqByY"));
        nameValuePair.add(new BasicNameValuePair("redirect_uri", "http://yoursite.com/callback#access_token=the_access_token&token_type=bearer&expires_in=large_number_of_seconds"));

// Depends on your web service
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;

        //Encoding POST data
        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));

        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        try {
            HttpResponse response = httpclient.execute(httppost);
            // write response to log but thats ugly
            Log.d("Http Post Response:", response.toString());
            /*
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            result = sb.toString();
            */
        } catch (Exception e) {
            // Oops
        }
        finally {
            try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
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
