package com.example.caroline.smashrunmobile;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;


public class DisplayRun extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        String message = intent.getStringExtra(FirstPage.EXTRA_MESSAGE);

        TextView textView = new TextView(this);

        textView.setTextSize(40);
        textView.setText(message);

        setContentView(R.layout.activity_display_run);

        //each of these modify the TextView on the display_run form
        setVariables(message, R.id.Date);
        setVariables(message, R.id.Weather);
        setVariables(message, R.id.Time);
        setVariables(message, R.id.Distance);
        setVariables(message, R.id.Calories);
        setVariables(message, R.id.Pace);
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

}
