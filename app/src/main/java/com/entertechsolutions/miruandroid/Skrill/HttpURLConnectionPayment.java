package com.entertechsolutions.miruandroid.Skrill;



import android.os.AsyncTask;

import com.entertechsolutions.miruandroid.Activities.PaymantWebView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class HttpURLConnectionPayment {
	String USER_AGENT = "Chrome/4.0.249.0";
	URI SIDobj;

	public void sendPostToMakePayment(Double amount ,String guId) {

		try {
			//String url = "https://pay.skrill.com/";
			URL myurl = new URL("https://pay.skrill.com/");
			HttpsURLConnection con = (HttpsURLConnection) myurl.openConnection();
			// add request header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			StringBuilder urlParameters = new StringBuilder();
			urlParameters.append("pay_to_email=billing@retlmtutors.com&");
			//urlParameters.append("pay_to_email=skrillpaymentwhb3@skrill.com&");
			urlParameters.append("amount=" + amount + "&currency=USD&");
			urlParameters.append("prepare_only=1&");
			urlParameters.append("merchant_fields=guId&");
			urlParameters.append("id=" + guId + "&");
			urlParameters.append("return_url=https://miru.cx/webapi/api/UserSubscription/Paid?id=" + guId + "&");
			urlParameters.append("cancel_url=https://miru.cx/webapi/api/UserSubscription/Cancel?id=" + guId);


			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters.toString());
			wr.flush();
			wr.close();


			int responseCode = con.getResponseCode();
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

			PaymantWebView.payurl(urlSID.toString());
		}
		catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}


	}
