package com.ftk.pg.homecontrollerTest;

import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftk.pg.dto.RequestWrapper;
import com.ftk.pg.encryption.GcmPgEncryption;
import com.ftk.pg.exception.EncDecException;
import com.ftk.pg.requestvo.API_PaymentRequest;
import com.ftk.pg.requestvo.ShowImageRequest;
import com.ftk.pg.util.EncryptionUtil;
import com.google.gson.Gson;

public class HomeControllerTest {

	static Gson gson = new Gson();

	public static void main(String[] args) throws Exception {

		String token = getToken();

		System.out.println("Token=====>" + token);
		RequestWrapper requestWrapper = new RequestWrapper();

		// String request = pgTimeOut(token, requestWrapper);
		String request = showImage(token, requestWrapper);
		System.out.println("Request=================>" + request);
	}

	public static <T> String encryptedData(String data, String token, Class<T> clazz) throws Exception {

		return encrypt(data, token);

	}

	public static String pgTimeOut(String token, RequestWrapper requestwrapper) throws Exception {

		API_PaymentRequest api_payment_request = new API_PaymentRequest();
		api_payment_request.setTransactionId(515758639l);

		System.out.println("API_PaymentRequest============>" + gson.toJson(api_payment_request));
		token = token.substring(7);
		String encrequest = encryptedData(gson.toJson(api_payment_request), token, API_PaymentRequest.class);

		requestwrapper.setData(encrequest);
		System.out.println("RequestWrapper==============>" + gson.toJson(requestwrapper));
		return gson.toJson(requestwrapper);

	}

	public static String showImage(String token, RequestWrapper requestwrapper) throws Exception {

		ShowImageRequest showImageRequest = new ShowImageRequest();
		showImageRequest.setImagePath(
				"3FFdhCF7lmxlGNy+X8TqPtcekEhVo0updaBtEdmOOS+w_FJ2SnYNzU3mUa9SgQw8vqlJy4AVFfpschkLYK0XdA==");

		System.out.println("API_PaymentRequest============>" + gson.toJson(showImageRequest));
		token = token.substring(7);
		String encrequest = encryptedData(gson.toJson(showImageRequest), token, API_PaymentRequest.class);

		requestwrapper.setData(encrequest);
		System.out.println("RequestWrapper==============>" + gson.toJson(requestwrapper));
		return gson.toJson(requestwrapper);

	}

	public static String getToken() {

		String token = UUID.randomUUID().toString();
		return token;
	}

	public static String encrypt(String data, String ivKey) throws Exception {
		GcmPgEncryption gcmPgEncryption = new GcmPgEncryption(ivKey);
		String encrypteddata = Optional.ofNullable(gcmPgEncryption.encryptWithMKeys(data))
				.orElseThrow(() -> new EncDecException("Unable to Encrypt the Response Data"));
		return encrypteddata;
	}

	public static <T> T decryptdata(String encryptedData, String ivKey, Class<T> clazz) throws Exception {
		GcmPgEncryption gcmPgEncryption = new GcmPgEncryption(ivKey);
		String decryptedData = Optional.ofNullable(gcmPgEncryption.decryptWithMKeys(encryptedData))
				.orElseThrow(() -> new EncDecException("Unable to Decrypt the Response Data"));
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(decryptedData, clazz);
	}

}
