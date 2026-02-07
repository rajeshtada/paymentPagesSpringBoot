package com.ftk.pg.util;

import javax.crypto.SecretKey;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.enstage.mlehelper.beans.AES;
import com.enstage.mlehelper.beans.EncryptedRequestData;
import com.enstage.mlehelper.beans.EncryptedResponseData;
import com.enstage.mlehelper.client.Client;
import com.enstage.mlehelper.server.Server;
import com.enstage.mlehelper.util.DecryptionUtil;
import com.enstage.mlehelper.util.EncryptionUtil;
import com.enstage.mlehelper.util.JsonUtil;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.vo.sbiNb.SbiRequestHeader;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SbiTransactionStatusUtil {
	
	private static Logger log = LogManager.getLogger(SbiTransactionStatusUtil.class);
	
	public static final String SBI_PG_INSTENCE_ID_AUTH_SALE="SBI_PG_INSTENCE_ID_AUTH_SALE";
	public static final String SBI_SALE_STATUS_URL="SBI_SALE_STATUS_URL";
	public static final Object SBI_HEADER_XAPI_KEY = "SBI_HEADER_XAPI_KEY";
	public static final String SBI_REFUND_URL = "SBI_REFUND_URL";
	
	
	
	
	
	public static String encrypt(String requestjson, AES aes) throws Exception {

		Gson gson = new Gson();
		try {

			String signedRequest = EncryptionUtil.digitalSignWithRSA(requestjson, Client.getPrivateKey());

			String encryptedRequest = EncryptionUtil.encrypt(signedRequest, aes);
			String encSymmetricKey = EncryptionUtil.encryptDEK(aes.getKey(), Server.getPublicKey());
			EncryptedRequestData encryptedRequestData = EncryptedRequestData.buildRequest(encryptedRequest,
					encSymmetricKey, aes);
			log.info("Encrypted request : " + JsonUtil.getJsonString(encryptedRequestData));
//			System.out.println("Encrypted request : " + JsonUtil.getJsonString(encryptedRequestData));

			String encrequestjson = gson.toJson(encryptedRequestData);
			return encrequestjson;

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;

	}

	public static String decypt(EncryptedResponseData encryptedResponse, AES aes) {
		String signedResponse = null;
		log.info("status of encrypted Response "+encryptedResponse.getStatusCode());
//		System.out.println("status of encrypted Response "+encryptedResponse.getStatusCode());
		try {
			if (encryptedResponse.getStatusCode().equals("PG99200")) {
				SecretKey decryptedSymmetricKey = DecryptionUtil
						.decryptDEK(encryptedResponse.getResponseSymmetricEncKey(), Client.getPrivateKey());
				signedResponse = DecryptionUtil.decrypt(encryptedResponse.getSignedEncResponsePayload(),
						encryptedResponse.getIv(), decryptedSymmetricKey);

				DecryptionUtil.verifySignature(signedResponse, Server.getPublicKey());
				log.info("Decrypted Data" + DecryptionUtil.getJsonFromJws(signedResponse));
//				System.out.println("Decrypted Data" + DecryptionUtil.getJsonFromJws(signedResponse));


			}
			return DecryptionUtil.getJsonFromJws(signedResponse);
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}

		return null;

	}

	public static String postapi(String url, String requestjson, SbiRequestHeader header) {
		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, requestjson);
			Request request = new Request.Builder().url(url).method("POST", body)
					.addHeader("x-api-key", header.getXapikey())
					.addHeader("pgInstanceId", header.getPgInstanceId())
//					.addHeader("merchantId", header.getMerchantId())
					.addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();

			String responseString = response.body().string();
			log.info("response===> : " + responseString);
//			System.out.println("response===> : " + responseString);

			return responseString;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

}
