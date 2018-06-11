package com.example.android.quakereport;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EqData {
    private String magnitude;
    private String place1;
    private String place2;
    private String location;
    private long timeInMilliseconds;
    private String time;
    private String date;
    private String url;





    public EqData(Double magnitude, String location, long timeInMilliseconds, String url) {
        DecimalFormat formatter = new DecimalFormat("0.0");
        this.magnitude = formatter.format(magnitude);
        this.location = location;
        this.url=url;
        this.timeInMilliseconds = timeInMilliseconds;
        this.assignDateAndTime();
        this.assignPlace1and2();

    }

    public String getMagnitude() {
        return this.magnitude;
    }

    public String getLocation() {
        return location;
    }

    public String getPlace1() {
        return place1;
    }

    public String getPlace2() {
        return place2;
    }

    public String getDate(){
        return date;
    }
    public String getUrl(){return url;}

    private void assignPlace1and2() {

        //Split String
        String[] places = new String[2];
        if(location.contains("of")){
            places = location.split("of");
            place1 = places[0] + "of";
            place2 = places[1];
        }else{
            place1 = "Near the";
            place2 = location;
        }



    }


    public String getTime() {

        return time;
    }

    //Assigns Readable Date and Time from given time in Unix Epoch time format.
    private void assignDateAndTime(){
        Date javaDate = new Date(this.timeInMilliseconds);

         SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyy");
        this.date = dateFormat.format(javaDate);

         SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        this.time = timeFormat.format(javaDate);
    }

}
