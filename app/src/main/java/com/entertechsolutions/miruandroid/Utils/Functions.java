package com.entertechsolutions.miruandroid.Utils;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.entertechsolutions.miruandroid.Activities.Login;
import com.entertechsolutions.miruandroid.Activities.MainActivity;
import com.entertechsolutions.miruandroid.Models.LoginModel;
import com.entertechsolutions.miruandroid.Models.LoginResponce;
import com.entertechsolutions.miruandroid.MyApplication;
import com.entertechsolutions.miruandroid.R;
import com.entertechsolutions.miruandroid.Storage.SharedPreffManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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



}
