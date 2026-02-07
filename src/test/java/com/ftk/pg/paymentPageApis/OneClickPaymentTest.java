package com.ftk.pg.paymentPageApis;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import com.ftk.pg.dto.ResponseWrapper;
import com.ftk.pg.encryption.GcmPgEncryption;
import com.ftk.pg.responsevo.API_PaymentResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OneClickPaymentTest {
	
	static String baseUrl="https://eovtg2gb67.execute-api.ap-south-1.amazonaws.com/UAT/pg/api/";

	public static void main(String[] args) {
		try {
			String token = "c3537406-25e4-41aa-a352-279df61ed095";
			String ivKey = token;
	        
			String deReq="{\n"
					+ "        \"token\": \""+token+"\"\n"
					+ "    }";
		
			System.out.println("deReq==>" + deReq);
			String enc = enc(deReq, ivKey);
			String url = baseUrl+"payment";
			
			String req = "{\n      \"data\": \"" + enc + "\"\n}";
			System.out.println("final Request==>" + req);
			String res = initiate(url, req, token);

			System.out.println(res);

			Gson gson = new Gson();
			ResponseWrapper decRes = gson.fromJson(res, ResponseWrapper.class);
			System.out.println("resData : " + decRes.getData());

			String dec = dec(String.valueOf(decRes.getData()), ivKey);
			
			Gson gson1 = new GsonBuilder()
					 .registerTypeAdapter(Date.class, new TimestampDateAdapter())
				    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
				    .create();
			
			API_PaymentResponse api_PaymentResponse=gson1.fromJson(dec,API_PaymentResponse.class);
			System.out.println(api_PaymentResponse);
			
			String dePayReq=dePayReq(api_PaymentResponse.getTransactionId());
			 
		        
				System.out.println("deReq Pay Request==>" + dePayReq);
				
				String encPay = enc(dePayReq, ivKey);

				String urlPay = baseUrl+"pay";
				String reqPay = "{\n      \"data\": \"" + encPay + "\"\n}";
				String resPay = initiate(urlPay, reqPay, token);

				System.out.println(res);
				ResponseWrapper decPayRes = gson.fromJson(resPay, ResponseWrapper.class);
				System.out.println("resData : " + decPayRes.getData());

				String decPay = dec(String.valueOf(decPayRes.getData()), ivKey);
				System.out.println(decPay);
		}catch(Exception e) {
			e.printStackTrace();
		}

		

	}

	private static String dePayReq(String transactionId) {
		// TODO Auto-generated method stub
		String req=  "{\n"
        		+ "    \"method\": \"ccPay\",\n"
        		+ "    \"requestParams\": {\n"
        		+ "        \"browserLanguage\": \"en-GB\",\n"
        		+ "        \"browserColorDepth\": \"24\",\n"
        		+ "        \"browserScreenHeight\": \"768\",\n"
        		+ "        \"browserScreenWidth\": \"1366\",\n"
        		+ "        \"browserTZ\": \"-330\",\n"
        		+ "        \"javaEnabled\": \"false\",\n"
        		+ "        \"jsEnabled\": \"true\",\n"
        		+ "        \"ipAddress\": \"null\",\n"
        		+ "        \"amt\": \"10\",\n"
        		+ "        \"txncurr\": \"INR\",\n"
        		+ "        \"paymentMode\": \"CC\",\n"
        		+ "        \"transactionId\": \""+transactionId+"\",\n"
        		+ "        \"login\": \"testmerchant\",\n"
        		+ "        \"number\": \"5100010000000049\",\n"
        		+ "        \"month\": \"02\",\n"
        		+ "        \"year\": \"2027\",\n"
        		+ "        \"cvc\": \"123\",\n"
        		+ "        \"name\": \"siddharth\",\n"
        		+ "        \"mobile\": \"6350043232\",\n"
        		+ "        \"udf3\": \"s@gmail.com\"\n"
        		+ "    }\n"
        		+ "}";
		
		return req;
	}

	private static String initiate(String url, String req, String token) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, req);
		Request request = new Request.Builder().url(url).method("POST", body)
				.addHeader("Authorization", "Bearer " + token).addHeader("Content-Type", "application/json").build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}

	private static String enc(String data, String ivKey) throws Exception {
		GcmPgEncryption gcmPgEncryption = new GcmPgEncryption(ivKey);
		String enc = gcmPgEncryption.encryptWithMKeys(data);
		System.out.println("enc => " + enc);
		return enc;
	}

	private static String dec(String enc, String ivKey) throws Exception {
		GcmPgEncryption gcmPgEncryption = new GcmPgEncryption(ivKey);
		String dec = gcmPgEncryption.decryptWithMKeys(enc);
		System.out.println("dec => " + dec);
		return dec;
	}


}
