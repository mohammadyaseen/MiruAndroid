package com.entertechsolutions.miruandroid;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    private static Context mContext;
    public static final int local  = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}