package com.entertechsolutions.miruandroid.Utils;

import com.entertechsolutions.miruandroid.R;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Functions {

    public static String CheckNull(Object obj){
        return obj==null?"":obj.toString();
    }

    public static String getDatewithTimeFormeted(Object object){

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMMM yyyy hh:mm aa");//dd/MM/yyyy
        // input.setTimeZone(TimeZone.getTimeZone("GTM"));
        Date d = null;
        try {
            d = input.parse(object.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String formatted = sdfDate.format(d);

        return formatted;
    }

    public static String getDateFormeted(Object object){
        if (object == null) return "";

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMMM yyyy");//dd/MM/yyyy
        // input.setTimeZone(TimeZone.getTimeZone("GTM"));
        Date d = null;
        try {
            d = input.parse(object.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String formatted = sdfDate.format(d);
        return formatted;
    }

    public static String getDateFormetedSpray(Object object){
        if(object == null) return "";
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMMM yyyy");//dd/MM/yyyy
        // input.setTimeZone(TimeZone.getTimeZone("GTM"));
        Date d = null;
        try {
            d = input.parse(object.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String formatted = sdfDate.format(d);
        return formatted;
    }

    public static long textFieldtoLong(TextInputEditText textField){
        return Integer.parseInt(textField.getText().toString().isEmpty() ==true? "0" :textField.getText().toString());
    }


    public static String getResultString (int count){
        String nameresult  = "";

        switch (count) {
            case 0:
               nameresult = "Not Used";
                break;
            case 1:
                nameresult = "Excellent";
                break;
            case 2:
                nameresult = "Good";
                break;
            default:
                nameresult = "Bad";
                break;
        }
        return nameresult;
    }

    public static String getResultId (String count){

        String nameresult  ;

        switch (count) {
            case "Not Used":
                nameresult = "0";
                break;
            case "Excellent":
                nameresult = "1";
                break;
            case "Good":
                nameresult = "2";
                break;
            default:
                nameresult = "3";
                break;
        }
        return nameresult;
    }

    public static String getResultirrigation (String count){

        String nameresult  ;

        switch (count) {
            case "Not Applied":
                nameresult = "0";
                break;
            default:
                nameresult = "1";
                break;
        }
        return nameresult;
    }

    public static String getUpdateirrigation (String count){

        String nameresult  ;

        switch (count) {
            case "Not Apply":
                nameresult = "0";
                break;
            default:
                nameresult = "1";
                break;
        }
        return nameresult;
    }

    public static String getirriText (int count){

        String nameresult  ;

        switch (count) {
            case 0 :
                nameresult = "Not Apply";
                break;
            default:
                nameresult = "Apply";
                break;
        }
        return nameresult;
    }




    public static String gettaskName(int icon){
        String weatherIcon ;
        switch(icon) {
            case 5:
                weatherIcon = "Irrigation";
                break;
            case 4:
                weatherIcon = "Fertilize";
                break;
            default:
                weatherIcon = "Spray";
        }
        return weatherIcon;

    }


    public static int getPositionseedqu(String text){
        int nameresult  ;

        switch (text) {
            case "Good":
                nameresult = 1;
                break;
            case "Bad":
                nameresult = 2;
                break;
            default:
                nameresult = 0;
                break;
        }
        return nameresult;

    }


    public static int getPositionsowingM(String text){
        int nameresult  ;

        switch (text) {
            case "Manual":
                nameresult = 1;
                break;
            case "Machinery":
                nameresult = 2;
                break;
            default:
                nameresult = 0;
                break;
        }
        return nameresult;

    }


    public static String setDateServer(Object object){
        if(object == null) return null;

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

        // input.setTimeZone(TimeZone.getTimeZone("GTM"));
        Date d = null;
        try {
            d = input.parse(object.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String formatted = sdfDate.format(d);
        return formatted;
    }

    public static String getdateSpray(Object object){

        if(object == null) return "";


            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");//dd/MM/yyyy
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

            // input.setTimeZone(TimeZone.getTimeZone("GTM"));
            Date d = null;
            try {
                d = input.parse(object.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            String formatted = sdfDate.format(d);
            return formatted;

    }



    public static  int getProductResultColor (String count){
        int nameresult  ;

        switch (count) {
            case "Bad":
                nameresult = R.color.red ;
                break;
            case "Excellent":
                nameresult = R.color.excellent_color;
                break;
            case "Good":
                nameresult = R.color.colorAccent;
                break;
            default:
                nameresult = R.color.black;
                break;
        }
        return nameresult;
    }



    public static int getSoilTypeValue(String text){
        int nameresult  ;

        switch (text) {
            case "Alkaline الکلی":
                nameresult = 1;
                break;
            case "Clay مٹی":
                nameresult = 2;
                break;
            case "Sandy کرکرا":
                nameresult = 3;
                break;
            case "Loam ملحقہ":
                nameresult = 4;
                break;
            case "Dsert ریگستانی":
                nameresult = 5;
                break;
            case "Silt گاد":
                nameresult = 6;
                break;
            default:
                nameresult = 0;
                break;
        }
        return nameresult;

    }

}
