package com.entertechsolutions.miruandroid.Utils;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitCliant {

    public static String BASE_URL = "https://miru.cx/webapi/";
    ///private static final String BASE_URL = "http://192.168.100.14:90/api/";
    private static RetrofitCliant retrofit = null;


    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY))
            // .addInterceptor(new ChuckInterceptor(MyApplication.getContext()))
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

    public static RetrofitCliant getInstance() {
        if (retrofit==null) {
            retrofit = new RetrofitCliant();
        }
        return retrofit;
    }

    public synchronized Retrofit getClient() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

}
