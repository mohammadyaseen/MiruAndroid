package com.entertechsolutions.miruandroid.Skrill;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class SkrillApiClass {

	private String url = "https://www.skrill.com/app/pay.pl?";
	private String email;
	private String amount;
	private String currency;
	private String frn_trn_id;
	private String sid;
	private String password;
	private String bnf_email;
	private String subject;
	private String note;

	/*public static void main(String[] args) {
		SkrillApiClass payout = new SkrillApiClass("the_creech@abv.bg", "skrill123", "2", "EUR", "TRNy1",
				"testcustomer999@skrill.com", "subject1", "Note1");
		
		System.out.println(payout.SendMoney());

	}*/

	public SkrillApiClass(String email, String password, String amount, String currency, String frn_trn_id,
			String bnf_email, String subject, String note) {
		this.email = email;
		this.password = getMD5(password);
		this.amount = amount;
		this.currency = currency;
		this.bnf_email = bnf_email;
		this.subject = subject;
		this.note = note;
		this.frn_trn_id = frn_trn_id;
	}

	public static String getMD5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);

			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String post(Map<String, String> data)  {

		try {
			URL obj = new URL(this.url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Skrill-API-lib");

			con.setDoOutput(true);
			OutputStream os = con.getOutputStream();

			StringBuilder postData = new StringBuilder();

			for (Map.Entry<String, String> param : data.entrySet()) {
				if (postData.length() != 0)
					postData.append('&');
				postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
				postData.append('=');
				postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
			}
			byte[] postDataBytes = postData.toString().getBytes("UTF-8");

			os.write(postDataBytes);
			os.flush();
			os.close();

			int responseCode = con.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in;

				in = new BufferedReader(new InputStreamReader(con.getInputStream()));

				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				return response.toString();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String generateSession() {
		Map<String, String> data = new HashMap<String, String>();

		data.put("action", "prepare");
		data.put("email", this.email);
		data.put("password", this.password);
		data.put("amount", this.amount);
		data.put("currency", this.currency);
		data.put("frn_trn_id", this.frn_trn_id);
		data.put("bnf_email", this.bnf_email);
		data.put("subject", this.subject);
		data.put("note", this.note);
		
		String result = this.post(data);
		
		if(result.contains("<sid>")){
			this.sid = result.substring(result.indexOf("<sid>") +  "<sid>".length(), result.indexOf("</sid>"));
		}
		
		return result;
	}

	public String SendMoney(String sid) {

		Map<String, String> data = new HashMap<String, String>();

		data.put("action", "transfer");
		data.put("sid", sid);

		return this.post(data);
	}
	
	//Overload method
	public String SendMoney() {
		generateSession();
		
		if (this.sid != null) {
			Map<String, String> data = new HashMap<String, String>();

			data.put("action", "transfer");
			data.put("sid", this.sid);

			return this.post(data);
		}

		return null;
	}

}
