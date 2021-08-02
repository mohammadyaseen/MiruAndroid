/*
package com.entertechsolutions.miruandroid.Storage;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreffManager {

    private static final String SHARED_PREF_NAME = "my_shared_preff";

    private static SharedPreffManager mInstance;
    private Context mCtx;

    private SharedPreffManager(Context mCtx) {
        this.mCtx = mCtx;
    }


    public static synchronized SharedPreffManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new SharedPreffManager(mCtx);
        }
        return mInstance;
    }


    public void savePic (String pic){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("pic", pic);
        editor.apply();
    }

    public String getPic(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("pic","");
    }


    public void saveUser(Login_Data_Response user) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id",  user.getId()==null?0:user.getId());
        editor.putString("code", user.getCode());
        editor.putString("email", user.getEmail());
        editor.putString("name", user.getName());
        editor.putString("fatherName", user.getFatherName());
        editor.putString("pictureUrl", (String) user.getPictureUrl());
        editor.putString("cnic", user.getCnic());
        editor.putString("address", (String) user.getAddress());
        editor.putString("mobile", (String) user.getMobile());
        editor.putString("token", (String) user.getAuthToken());

        editor.apply();

    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id", -1) != -1;
    }

    public Login_Data_Response getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Login_Data_Response(
                sharedPreferences.getInt("id",-1),
                sharedPreferences.getString("code",null),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("name", null),
                sharedPreferences.getString("fatherName", null),
                sharedPreferences.getString("pictureUrl", null),
                sharedPreferences.getString("cnic", null),
                sharedPreferences.getString("address", null),
                sharedPreferences.getString("mobile", null),
                sharedPreferences.getString("token", null)
        );
    }

    public void clear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
*/
