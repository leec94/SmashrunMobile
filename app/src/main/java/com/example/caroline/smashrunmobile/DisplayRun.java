package com.example.caroline.smashrunmobile;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class DisplayRun extends ActionBarActivity {
    private JSONObject activityData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();


        TextView textView = new TextView(this);

        textView.setTextSize(40);

        setContentView(R.layout.activity_display_run);

        Bundle bundle = getIntent().getExtras();
        int activity_id = bundle.getInt("activityid");
        configureJSON(activity_id);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_run, menu);
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

    public void setVariables(String message, int x){

        TextView tempView = (TextView) findViewById(x);
        tempView.setText(message);
    }
    public void configureJSON(int activity_id) {
        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        //example json request
        //ADD BACK IN String url = "https://api.smashrun.com/v1/my/activities/2088942";
        String url = ("https://api.smashrun.com/v1/my/activities/"+ String.valueOf(activity_id) + "?");

        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
        nameValuePair.add(new BasicNameValuePair("client_id", "caroline_l097faff0"));
        nameValuePair.add(new BasicNameValuePair("access_token", "AL2LRz0jUgHhih9mjrLSYhbk5LTxVOxSFbA0PeFx5sAA"));
        String query = URLEncodedUtils.format(nameValuePair, "utf-8");
        url += query;
        new downloadJSON(this).execute(url);
    }
    public void setJSON(JSONObject j) {
        this.activityData = j;

        TextView date = (TextView)findViewById(R.id.Date);
        TextView weather = (TextView)findViewById(R.id.Weather);
        TextView time = (TextView)findViewById(R.id.Time);

        TextView distance = (TextView)findViewById(R.id.Distance);
        TextView calories = (TextView)findViewById(R.id.Calories);
        TextView pace = (TextView)findViewById(R.id.Pace);
        try {
            String Timedate = activityData.getString("startDateTimeLocal");
            Timedate = formatString.stringtoDateTime(Timedate);
            date.setText("Date: " + Timedate);
            String temp = activityData.getString("temperature");
            temp = formatString.celsiusToFahrenheit(temp);
            System.out.println(temp);
            weather.setText("Weather: " + temp + "\u00b0F");
            //end time - start time, get rid of pauses
            time.setText("Time: in progress");
            distance.setText("Distance: " + activityData.getString("distance"));
            //calculate this from something
            calories.setText("Calories: a lot.");
            //also calculate this
            //temporarily changed to ID for testing
            pace.setText("ID: " + activityData.getString("activityId"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
