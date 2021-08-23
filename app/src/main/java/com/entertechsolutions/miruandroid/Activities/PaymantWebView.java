package com.entertechsolutions.miruandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.entertechsolutions.miruandroid.MyApplication;
import com.entertechsolutions.miruandroid.R;
import com.entertechsolutions.miruandroid.Skrill.HttpURLConnectionPayment;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import retrofit2.http.Url;

public class PaymantWebView extends AppCompatActivity  {

    public static Object pay;
    public static WebView myWebView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymant_web_view);

        try {
            pay();
        } catch (Exception e) {
            e.printStackTrace();
        }

        myWebView =  findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        myWebView = new WebView(this);
        setContentView(myWebView);

        myWebView.setWebViewClient(new WebViewClient() {
          /*  Dialog progressDialog = new Dialog (context);

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressDialog.setTitle("Loading...");
               // progressDialog.setMessage("Please wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                if (progressDialog != null){
                    progressDialog.dismiss();
                }
            }
*/


        });

      //  myWebView.loadUrl("http://google.com/");



    }




    public static void payurl (URI uri) throws URISyntaxException {

        URI url =  new  URI(uri.toString());

        String urlS = url.toString();


        if (!urlS.isEmpty()) {
            myWebView.loadUrl(urlS);
            Log.e("URL","    "+myWebView.getUrl());

            Toast.makeText(MyApplication.getContext(), ""+myWebView.getUrl(), Toast.LENGTH_LONG).show();
        }
        else {
            //myWebView.getUrl();
            Toast.makeText(MyApplication.getContext(), " Null", Toast.LENGTH_LONG).show();
        }
    }



    public void pay() throws Exception {
        HttpURLConnectionPayment http = new HttpURLConnectionPayment();
        System.out.println("\nTesting 2 - Send Http POST request");
        http.sendPostToMakePayment();
    }
}
