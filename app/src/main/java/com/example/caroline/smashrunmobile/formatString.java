package com.example.caroline.smashrunmobile;

import java.lang.Object;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Caroline on 4/4/2015.
 */
public class formatString {

    public static String stringToDate(String dateTime) {
        try {
            dateTime = dateTime.substring(0, 22) + dateTime.substring(23);  // to get rid of the ":"
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
        sdf.setTimeZone(TimeZone.getDefault());

        Date date = null;
        try {
            date = (Date) sdf.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return DateFormat.getDateInstance().format(date);
    }

    public static String stringtoDateTime(String dateTime){
        try {
            dateTime = dateTime.substring(0, 22) + dateTime.substring(23);  // to get rid of the ":"
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
        sdf.setTimeZone(TimeZone.getDefault());

        Date date = null;
        try {
            date = (Date) sdf.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return DateFormat.getDateTimeInstance().format(date);
    }

    public static String celsiusToFahrenheit(String s) {
        double cel = Double.parseDouble(s);
        double fahr = cel *(9/5) + 32;

        return String.format("%.3g",fahr);
    }

    public static String kmToMiles(String s) {
        double km = Double.parseDouble(s);
        double miles = 0.62137*km;
        return String.format("%.3g", miles);
    }

}
