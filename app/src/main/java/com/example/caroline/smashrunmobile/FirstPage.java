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
import android.content.Intent;


public class FirstPage extends ActionBarActivity {

    public EditText userIdField;
    public final static  String EXTRA_MESSAGE = "com.example.caroline.smashrunmobile.MESSAGE";

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
//                doSomething();
                goHome(new View());
            }
        });


    }

//    public void doSomething(){
//        String userid = userIdField.getText().toString();
//
//        Log.d("SmashRunMobile", "User Id: " + userid);
//    }


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

    public void goHome(View view) {
        Log.d("SmashRunMobile", "we got so far");
        Intent intent = new Intent(this, DisplayRun.class);


        EditText userid = (EditText) findViewById(R.id.userid);
        String message = userid.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);

        startActivity(intent);
    }
}
