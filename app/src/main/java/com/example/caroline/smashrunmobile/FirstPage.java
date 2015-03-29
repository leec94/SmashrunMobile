package com.example.caroline.smashrunmobile;

import android.app.AlertDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Intent;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.json.*;

import java.util.ArrayList;
import java.util.List;


public class FirstPage extends ActionBarActivity {

    public EditText userIdField;
    List<String> activityidlist;
    public boolean hasClicked = false;
    public final static  String EXTRA_MESSAGE = "com.example.caroline.smashrunmobile.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        userIdField = (EditText) findViewById(R.id.userid);



        Button latestButton = (Button) findViewById(R.id.getLatestButton);
        latestButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                getLatestRunAction();
            }
        });

        Button confirmButton = (Button) findViewById(R.id.button);
        confirmButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                doSomething();
            }
        });


    }

    public void getLatestRunAction() {
        if (hasClicked) {
            return;
        } else {
            hasClicked = true;
        }
        //make a list of the 10 latest runs with JSON
        //form request to use API
        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        String url = ("https://api.smashrun.com/v1/my/activities/search?");

        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
        nameValuePair.add(new BasicNameValuePair("count", "10"));
        nameValuePair.add(new BasicNameValuePair("client_id", "caroline_l097faff0"));
        nameValuePair.add(new BasicNameValuePair("access_token", "AL2LRz0jUgHhih9mjrLSYhbk5LTxVOxSFbA0PeFx5sAA"));
        String query = URLEncodedUtils.format(nameValuePair, "utf-8");
        url += query;
        new downloadJSON(this).execute(url);
    }

    public void setJSON(JSONArray j) {
        TextView blah = (TextView) findViewById(R.id.textView);
        blah.setText("Finished web request!");

        Spinner runspin = (Spinner) findViewById(R.id.spinnerLatest);
        List<String> list = new ArrayList<>();
        activityidlist = new ArrayList<String>();
        //lol how do you json
        try {
            for (int i = 0; i < j.length(); i++) {

                String activity_ID = j.getJSONObject(i).getString("activityId");
                activityidlist.add(i, activity_ID);
                String date = j.getJSONObject(i).getString("startDateTimeLocal");
                String distance = j.getJSONObject(i).getString("distance");
                list.add(i, date + " " + distance);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        runspin.setAdapter(dataAdapter);


    }


    public void doSomething(){
        if (!hasClicked || activityidlist.size() < 1) {
            return;
        }

        Spinner s = (Spinner) findViewById(R.id.spinnerLatest);
        int id = Integer.parseInt(activityidlist.get(s.getSelectedItemPosition()));

        if (id == 0) {
            return;
        }
        String userid = userIdField.getText().toString();

        Log.d("SmashRunMobile", "User Id: " + userid);



        Intent intent = new Intent(this, DisplayRun.class);

        Bundle activity = new Bundle();
        activity.putInt("activityid", id);
        intent.putExtras(activity);
        EditText username = (EditText) findViewById(R.id.userid);
        String message = username.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);

        startActivity(intent);
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
