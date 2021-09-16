package com.entertechsolutions.miruandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.entertechsolutions.miruandroid.MyApplication;
import com.entertechsolutions.miruandroid.R;
import com.entertechsolutions.miruandroid.Skrill.HttpURLConnectionPayment;
import com.entertechsolutions.miruandroid.Storage.SharedPreffManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import retrofit2.http.Url;

public class PaymantWebView extends AppCompatActivity  {

    public static Object pay;
    public static WebView myWebView ;

    static String  NewUrl = "https://pay.skrill.com/app/?sid=";
    static String Sid ;
    String guId;
    Double amount ;
    int systemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymant_web_view);

        Intent intent = getIntent();
        guId = intent.getStringExtra("guid");
        amount = intent.getDoubleExtra("amount",-1);
        systemId = intent.getIntExtra("systemId",-1);
        //pay();

        new MyTask().execute("ab");

        myWebView =  findViewById(R.id.webview);
        myWebView.setWebViewClient(new MyWebViewClient());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            webSettings.setSafeBrowsingEnabled(false);
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        myWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(myWebView, true);
        } else {
            CookieManager.getInstance().setAcceptCookie(true);
        }


        // Extras tried for Android 9.0, can be removed if want.
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setBlockNetworkImage(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

    }

    public static void  loadurl(String url){
        myWebView.loadUrl(url);
    }

    public static void payurl (String uri) {
        Sid = uri;
    }

    public void pay() {
        HttpURLConnectionPayment http = new HttpURLConnectionPayment();
        System.out.println("\nTesting 2 - Send Http POST request");
        http.sendPostToMakePayment(amount,guId);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

          //  view.loadUrl(url);

            Thread thread = new Thread(new Runnable(){
                @Override
                public void run() {

                   /* try {
                        URL aURL = null;
                        aURL = new URL(method(url.toString()));
                        URLConnection conn = aURL.openConnection();
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        BufferedReader in;

                        in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                        String inputLine;
                        StringBuffer response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();

                           *//* JSONObject jObject = new JSONObject(response.toString());
                            String aJsonString = jObject.getString("Message");
                            if (aJsonString.equals("Amount paid")){
                                Intent it = new Intent(MyApplication.getContext(), MainActivity.class);
                                //it.putExtra("guid", loginResponse.getData());
                                it.putExtra("systemId",systemId);
                                it.putExtra("text", "s");
                                startActivity(it);
                                finish();
                            }
                            else if (aJsonString.equals("Payment cancelled")){
                                Intent it = new Intent(MyApplication.getContext(), ChildList.class);
                                startActivity(it);
                                finish();
                            }
*//*
                       *//* if (response!=null){
                            Intent it = new Intent(MyApplication.getContext(), MainActivity.class);
                            //it.putExtra("guid", loginResponse.getData());
                            it.putExtra("systemId",systemId);
                            it.putExtra("text", "s");
                            startActivity(it);
                            finish();
                        }*//*
                       // Toast.makeText(MyApplication.getContext(), "Oh Yes!   " + response.toString(), Toast.LENGTH_SHORT).show();
                        // read inputstream to get the json..
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }
            });
            thread.start();

                return true;
        }
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(MyApplication.getContext(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
        }

    }

    public String method(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == '&') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    private class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try  {
                pay();
            } catch (Exception e) {
                e.printStackTrace();
            }
            String url = params[0];
            return url;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // do something with result
            loadurl(NewUrl+Sid);

        }
    }

}
