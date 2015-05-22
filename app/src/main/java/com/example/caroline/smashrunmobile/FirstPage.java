package com.example.caroline.smashrunmobile;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class FirstPage extends Fragment {

    public EditText userIdField;
    List<String> activityIdList;
    public boolean hasClicked = false;
    public final static  String EXTRA_MESSAGE = "com.example.caroline.smashrunmobile.MESSAGE";
    View rootView;

    public FirstPage() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_first_page, container, false);

        userIdField = (EditText) rootView.findViewById(R.id.userid);

        Button latestButton = (Button) rootView.findViewById(R.id.getLatestButton);
        latestButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getLatestRunAction();
            }
        });

        Button confirmButton = (Button) rootView.findViewById(R.id.button);
        confirmButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                doSomething();
            }
        });

        return rootView;

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
        TextView blah = (TextView) rootView.findViewById(R.id.textView);
        blah.setText("Finished web request!");


        Spinner runSpin = (Spinner) rootView.findViewById(R.id.spinnerLatest);
        List<String> list = new ArrayList<>();
        activityIdList = new ArrayList<String>();
        //lol how do you json
        try {
            for (int i = 0; i < j.length(); i++) {

                String activity_ID = j.getJSONObject(i).getString("activityId");
                activityIdList.add(i, activity_ID);
                String date = j.getJSONObject(i).getString("startDateTimeLocal");
                date = formatString.stringToDate(date);
                String distance = j.getJSONObject(i).getString("distance");
                distance = formatString.kmToMiles(distance);
                list.add(i, distance + " miles on " +  date);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        runSpin.setAdapter(dataAdapter);


    }


    public void doSomething(){
        if (!hasClicked || activityIdList.size() < 1) {
            return;
        }

        Spinner s = (Spinner) rootView.findViewById(R.id.spinnerLatest);
        int id = Integer.parseInt(activityIdList.get(s.getSelectedItemPosition()));

        if (id == 0) {
            return;
        }
        String userId = userIdField.getText().toString();

        Log.d("SmashRunMobile", "User Id: " + userId);



        Intent intent = new Intent(this.getActivity(), DisplayRun.class);

        Bundle activity = new Bundle();
        activity.putInt("activityid", id);
        intent.putExtras(activity);
        EditText username = (EditText) rootView.findViewById(R.id.userid);
        String message = username.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);

        startActivity(intent);
    }


}
