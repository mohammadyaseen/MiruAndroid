
package com.entertechsolutions.miruandroid.Storage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import com.entertechsolutions.miruandroid.Models.LoginModel;

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




    public void saveUser(LoginModel user) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id",  user.getId()==null?0:user.getId());
        editor.putString("email", user.getEmail());
        editor.putString("name", user.getName());
        editor.putBoolean("isActive", user.getIsActive());
        editor.putBoolean("isVerified", user.getIsVerified());
        editor.putString("phoneNo", user.getPhoneNo());
        editor.putString("token",  user.getAuthToken());

        editor.apply();

    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id", -1) != -1;
    }

    public LoginModel getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new LoginModel(
                sharedPreferences.getInt("id",-1),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("name", null),
                sharedPreferences.getBoolean("isActive", true),
                sharedPreferences.getBoolean("isVerified", true),
                sharedPreferences.getString("phoneNo", null),
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

