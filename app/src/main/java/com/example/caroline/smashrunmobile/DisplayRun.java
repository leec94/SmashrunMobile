package com.example.caroline.smashrunmobile;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.sql.Time;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class DisplayRun extends ActionBarActivity {
    private JSONObject activityData;
    DecimalFormat twoDecimals = new DecimalFormat("#.##");//used to print to 2 decimals


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

    public void setVariables(String message, int x) {

        TextView tempView = (TextView) findViewById(x);
        tempView.setText(message);
    }

    public void configureJSON(int activity_id) {
        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        //example json request
        //ADD BACK IN String url = "https://api.smashrun.com/v1/my/activities/2088942";
        String url = ("https://api.smashrun.com/v1/my/activities/" + String.valueOf(activity_id) + "?");

        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
        nameValuePair.add(new BasicNameValuePair("client_id", "caroline_l097faff0"));
        nameValuePair.add(new BasicNameValuePair("access_token", "AL2LRz0jUgHhih9mjrLSYhbk5LTxVOxSFbA0PeFx5sAA"));
        String query = URLEncodedUtils.format(nameValuePair, "utf-8");
        url += query;
        new downloadJSON(this).execute(url);
    }

    public void setJSON(JSONObject j) {
        this.activityData = j;

        TextView date = (TextView) findViewById(R.id.Date);
        TextView weather = (TextView) findViewById(R.id.Weather);
        TextView time = (TextView) findViewById(R.id.Time);

        TextView distance = (TextView) findViewById(R.id.Distance);
        TextView calories = (TextView) findViewById(R.id.Calories);
        TextView pace = (TextView) findViewById(R.id.Pace);

        GraphView graph = (GraphView) findViewById(R.id.graph);

        date.setText("Date: " + getTimeDate(activityData));
        weather.setText("Weather: " + getWeather(activityData) + " \u00b0F");
        time.setText("Duration: " + formatString.minToTime(getTime(activityData)) + " minutes");
        distance.setText("Distance: " + getDist(activityData) + " miles");
        //calculate this from something
        calories.setText("Calories: a lot.");

        pace.setText("Pace: " + formatString.minToTime(getPace(activityData)) + " min/mile\n\n\n ");

         //removes the graph if there is no elevation data
        if (!(getElevation(activityData)==null)) {
            graph.addSeries(getElevation(activityData));
            //this is ugly
            graph.getViewport().setScalable(true);
            graph.getViewport().setScrollable(true);
            //set appropriate min and max
            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setYAxisBoundsManual(true);
        }
        else
            graph.setVisibility(View.INVISIBLE);


    }

    /*
    Retrieves the date and time of the run
    @param data - the JSON object containing the required data
     */
    public String getTimeDate(JSONObject data) {
        try {
            if (data.has("startDateTimeLocal")) {
                String Timedate = activityData.getString("startDateTimeLocal");
                return formatString.stringtoDateTime(Timedate);
            } else
                return "n/a";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "n/a";
    }

    /*
    returns the temperature at time of the run
    @param data - the JSONObject containing the required data
     */
    public String getWeather(JSONObject data) {
        try {
            if (data.has("temperature")) {
                String temp = data.getString("temperature");
                return formatString.celsiusToFahrenheit(temp);
            } else return "n/a";

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "n/a";
    }

    /*
    Retrieves the duration of the run
    @param data - the JSON object containing the required data
  */
    public double getTime(JSONObject data) {
        try {
            if (data.has("duration") && Double.parseDouble(data.getString("duration")) > 0.0) {
                return Double.valueOf(twoDecimals.format(Double.parseDouble(data.getString("duration"))/60.0));
            } else
                return -1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /*
 Retrieves the distance of the run
 @param data - the JSON object containing the required data
  */
    public String getDist(JSONObject data){
        try {
            if(data.has("distance")) {
                String dist = data.getString("distance");
                return formatString.kmToMiles(dist);
            }
            else return "n/a";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "n/a";
    }
    /*
    Retrieves the average pace of the run
    @param data - the JSON object containing the required data
     */
    public double getPace(JSONObject data) {

        if (getTime(data) > 0 && Double.parseDouble(getDist(data)) > 0) {
            return Double.valueOf(twoDecimals.format(getTime(data) / Double.parseDouble(getDist(data))));
        }
        else return -1;

    }

    /*
    Retrieves datapoints for the elevation series
     */
    public LineGraphSeries<DataPoint> getElevation(JSONObject data){
        try {
            int index = -1;
            //finds where elevation data is located
            for(int i = 0; i< data.getJSONArray("recordingKeys").length(); i++){
                if (data.getJSONArray("recordingKeys").get(i).equals("elevation")){
                    index = i;
                    break;
                }
            }
            //if it exists, get all elevation points, if not return null
            if (index > 0) {
                LineGraphSeries<DataPoint> series;
                DataPoint[] elevateData = new DataPoint[data.getJSONArray("recordingValues").getJSONArray(index).length()];
                for (int i = 0; i < data.getJSONArray("recordingValues").getJSONArray(index).length(); i++) {
                    elevateData[i] = (new DataPoint(i, data.getJSONArray("recordingValues").getJSONArray(index).getDouble(i) * 3.2808));
                }
                //throw all elevations into graph
                series = new LineGraphSeries<DataPoint>(elevateData);
                return series;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
