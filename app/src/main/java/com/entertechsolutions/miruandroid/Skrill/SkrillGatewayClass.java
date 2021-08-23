package com.entertechsolutions.miruandroid.Skrill;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class SkrillGatewayClass {

	/*public static void main(String[] args) {
		String url = "https://pay.skrill.com";
		SkrillGatewayClass api = new SkrillGatewayClass();
		HttpClient httpClient = new HttpClient(url);
		SkrillAPIConfig config = new SkrillAPIConfig("SkrillPaymentWHB3@skrill.com");
		SkrillAPI skrillrequest = new SkrillAPI(httpClient, config);
		SkrillAPICustomer customer = new SkrillAPICustomer();
			customer.setFirstName("Firs%tname");
			customer.setLastName("Lastname");
			customer.setAddress1("address1");
			customer.setAddress2("address2");
			customer.setZip("123");
			customer.setCity("NY");
			customer.setState("NY");
			customer.setCountry("USA");
			customer.setLanguage("EN");
			customer.setPhone("123456");
			customer.setEmail("test+Customer999@skrill.com");
			
			skrillrequest.customerData(customer);
		SkrillAPIDebit debit = new SkrillAPIDebit();
			debit.setNotificationURL("https://your.notification.url.com/notification.html?param1=value1");
			debit.setSuccessURL("https://your.success.url.com/success.html?param2=value2");
			debit.setCancelURL("https://your.cancel.url.com/error.html?param3=value3");
			debit.setLogoURL("https://your.logo.url.com/logo.png");
		
			skrillrequest.debitData(debit);
		SkrillAPITransaction transaction = new SkrillAPITransaction();
			transaction.setAmount("2");
			transaction.setCurrency("EUR");
			transaction.setDescriptor("Tes%2 tMerchant");
			//transaction.setTrnID("1");
			transaction.setConfirmationNote("Thank you for your payment");
			transaction.setDetail1Description("Product");
			transaction.setDetail1Text("43211234");
			
			skrillrequest.transactionData(transaction);
		String result = skrillrequest.debitTransactionData();
		System.out.println("https://pay.skrill.com?sid=" + result);
	}*/

	static class SkrillAPITransaction {
		private String amount;
		private String currency;
		private String descriptor;
		private String trnID;
		private String confirmationNote;
		private String detail1Description;
		private String detail1Text;

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public void setCurrency(String currency) {
			this.currency = currency;
		}

		public void setDescriptor(String descriptor) {
			this.descriptor = descriptor;
		}

		public void setTrnID(String trnID) {
			this.trnID = trnID;
		}

		public void setConfirmationNote(String confirmationNote) {
			this.confirmationNote = confirmationNote;
		}

		public void setDetail1Description(String detail1Description) {
			this.detail1Description = detail1Description;
		}

		public void setDetail1Text(String detail1Text) {
			this.detail1Text = detail1Text;
		}

		public String getAmount() {
			return this.amount;
		}

		public String getCurrency() {
			return this.currency;
		}

		public String getDescriptor() {
			return this.descriptor;
		}

		public String getTrnID() {
			return this.trnID;
		}

		public String getConfirmationNote() {
			return this.confirmationNote;
		}

		public String getDetail1Description() {
			return this.detail1Description;
		}

		public String getDetail1Text() {
			return this.detail1Text;
		}

	}

	static class SkrillAPIDebit {
		private String notificationURL;
		private String successURL;
		private String cancelURL;
		private String logo_url;

		public void setNotificationURL(String notificationURL) {
			this.notificationURL = notificationURL;
		}

		public void setSuccessURL(String successURL) {
			this.successURL = successURL;
		}

		public void setCancelURL(String cancelURL) {
			this.cancelURL = cancelURL;
		}

		public void setLogoURL(String logo_url) {
			this.logo_url = logo_url;
		}

		public String getNotificationURL() {
			return this.notificationURL;
		}

		public String getSuccessURL() {
			return this.successURL;
		}

		public String getCancelURL() {
			return this.cancelURL;
		}

		public String getLogoURL() {
			return this.logo_url;
		}
	}

	static class SkrillAPI {
		private HttpClient httpClient;
		private SkrillAPIConfig skrillConfig;
		private SkrillAPICustomer customer;
		private SkrillAPIDebit debit;
		private SkrillAPITransaction transaction;

		public SkrillAPI(HttpClient httpClient, SkrillAPIConfig config) {
			this.httpClient = httpClient;
			this.skrillConfig = config;
		}

		public void customerData(SkrillAPICustomer customer) {
			this.customer = customer;
		}

		public void debitData(SkrillAPIDebit debit) {
			this.debit = debit;
		}

		public void transactionData(SkrillAPITransaction transaction) {
			this.transaction = transaction;
		}

		public String debitTransactionData() {
			return this.httpClient.post(this.body());
		}

		public Map<String, String> body() {

			Map<String, String> writer = new HashMap<String, String>();

			writer.put("pay_to_email", this.skrillConfig.getMerchantEmail());
			writer.put("currency", this.transaction.getCurrency());
			writer.put("amount", this.transaction.getAmount());
			writer.put("prepare_only", "1");
			writer.put("recipient_description", this.transaction.getDescriptor());
			writer.put("detail1_description", this.transaction.getDetail1Description());
			writer.put("detail1_text", this.transaction.getDetail1Text());
			writer.put("return_url", this.debit.getSuccessURL());
			writer.put("cancel_url", this.debit.getCancelURL());
			writer.put("status_url", this.debit.getNotificationURL());
			writer.put("logo_url", this.debit.getLogoURL());
			writer.put("language", this.customer.getLanguage());
			writer.put("confirmation_note", this.transaction.getConfirmationNote());
			writer.put("transaction_id", this.transaction.getTrnID());
			writer.put("pay_from_email", this.customer.getEmail());
			writer.put("firstname", this.customer.getFirstName());
			writer.put("lastname", this.customer.getLastName());
			writer.put("address", this.customer.getAddress1());
			writer.put("address2", this.customer.getAddress2());
			writer.put("phone_number", this.customer.getPhone());
			writer.put("postal_code", this.customer.getZip());
			writer.put("city", this.customer.getCity());
			writer.put("state", this.customer.getState());
			writer.put("country", this.customer.getCountry());

			return writer;
		}

	}

	static class SkrillAPIConfig {
		private String merchantEmail;

		public SkrillAPIConfig(String merchantEmail) {
			this.merchantEmail = merchantEmail;
		}

		public String getMerchantEmail() {
			return this.merchantEmail;
		}

	}

	static class SkrillAPICustomer {
		private String firstName;
		private String lastName;
		private String address1;
		private String address2;
		private String zip;
		private String city;
		private String state;
		private String country;
		private String language;
		private String phone;
		private String email;

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public void setAddress1(String address1) {
			this.address1 = address1;
		}

		public void setAddress2(String address2) {
			this.address2 = address2;
		}

		public void setZip(String zip) {
			this.zip = zip;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public void setState(String state) {
			this.state = state;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public void setLanguage(String language) {
			this.language = language;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getFirstName() {
			return this.firstName;
		}

		public String getLastName() {
			return this.lastName;
		}

		public String getAddress1() {
			return this.address1;
		}

		public String getAddress2() {
			return this.address2;
		}

		public String getZip() {
			return this.zip;
		}

		public String getCity() {
			return this.city;
		}

		public String getState() {
			return this.state;
		}

		public String getCountry() {
			return this.country;
		}

		public String getLanguage() {
			return this.language;
		}

		public String getPhone() {
			return this.phone;
		}

		public String getEmail() {
			return this.email;
		}

	}

	static class HttpClient {
		private String url;

		public HttpClient(String url) {
			this.url = url;
		}

		public String post(Map<String, String> data) {

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
	}
}
