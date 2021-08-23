package com.entertechsolutions.miruandroid.Skrill;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.entertechsolutions.miruandroid.Activities.ChildList;
import com.entertechsolutions.miruandroid.Activities.Login;
import com.entertechsolutions.miruandroid.Activities.PaymantWebView;
import com.entertechsolutions.miruandroid.MyApplication;
import com.entertechsolutions.miruandroid.R;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class HttpURLConnectionPayment {
	private final String USER_AGENT = "Chrome/4.0.249.0";
	URI SIDobj;

	WebView webView;

	/*public static void main(String[] args) throws Exception {

		HttpURLConnectionPayment http = new HttpURLConnectionPayment();
		System.out.println("\nTesting 2 - Send Http POST request");
		http.sendPostToMakePayment();

	}
*/
	public void sendPostToMakePayment() throws Exception {
		String url = "https://pay.skrill.com";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		// add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		StringBuilder urlParameters=new StringBuilder();
		urlParameters.append("pay_to_email=skrillpaymentwhb3@skrill.com&");
		urlParameters.append("amount=10&currency=EUR&");
		urlParameters.append("prepare_only=1&");
		urlParameters.append("status_url=http://www.skrill.com/payment_made.html");
		urlParameters.append("merchant_fields=field1,field2&");
		urlParameters.append("field1=dsadasd&");
		urlParameters.append("field2=tttttt&");
		urlParameters.append("detail1_description=productinformation&");
		urlParameters.append("detail1_text=dasddasd");
		urlParameters.append("payment_methods=ACC");
		urlParameters.append("return_url=http://www.skrill.com/payment_made.html");
		urlParameters.append("cancel_url=http://www.skrill.com/payment_cancelled.html");


		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters.toString());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());
		System.out.println(response.length());

		// display redirect quick checkout
		//after receiving the response with the session we only need to pass it as a parameter, no need for an action here as well
		StringBuilder urlSID = new StringBuilder("https://pay.skrill.com?sid=");
		urlSID.append(response);
		SIDobj = new URI(urlSID.toString());

		System.out.println();

		PaymantWebView.payurl(SIDobj);

		/*Desktop d = Desktop.getDesktop();
		d.browse(SIDobj);*/
	}



}
