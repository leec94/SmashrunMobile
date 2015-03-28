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
    public JSONObject activityData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        //String message = intent.getStringExtra(FirstPage.EXTRA_MESSAGE);

        TextView textView = new TextView(this);

        textView.setTextSize(40);
       // textView.setText(message);

        setContentView(R.layout.activity_display_run);

        //each of these modify the TextView on the display_run form
        /*setVariables(message, R.id.Date);
        setVariables(message, R.id.Weather);
        setVariables(message, R.id.Time);
        setVariables(message, R.id.Distance);
        setVariables(message, R.id.Calories);
        setVariables(message, R.id.Pace);*/
        configureJSON();
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
    public void configureJSON() {
        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        //example json request
        //ADD BACK IN String url = "https://api.smashrun.com/v1/my/activities/2088942";
        String url = "https://api.smashrun.com/v1/my/activities/2088942?";
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
        nameValuePair.add(new BasicNameValuePair("client_id", "caroline_l097faff0"));
        nameValuePair.add(new BasicNameValuePair("access_token", "AL2LRz0jUgHhih9mjrLSYhbk5LTxVOxSFbA0PeFx5sAA"));
        String query = URLEncodedUtils.format(nameValuePair, "utf-8");
        url += query;
        new downloadJSON(this).execute(url);
    }
    public void setJSON(JSONObject j) {
        this.activityData = j;
        TextView t = (TextView)findViewById(R.id.Distance);
        try {
            t.setText("Distance: " + activityData.getString("distance"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
