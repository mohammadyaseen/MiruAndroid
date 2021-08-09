package com.entertechsolutions.miruandroid.Utils;


import com.entertechsolutions.miruandroid.Remote.RetrofitClient;

public class ServiceUtils {

    public static String BASE_URL = "https://miru.cx/webapi/";

    public static ApiInterface api = RetrofitClient.getInstance().getClient(BASE_URL)
            .create(ApiInterface.class);

}



