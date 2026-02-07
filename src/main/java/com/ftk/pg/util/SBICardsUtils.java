package com.ftk.pg.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.enstage.mlehelper.beans.EncryptedRequestData;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.requestvo.TokenHeader;
import com.ftk.pg.vo.sbiNb.SbiRequestHeader;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SBICardsUtils {
	static Logger logger = LogManager.getLogger(SBICardsUtils.class);
	
	//RUPAY Headers
	public static final String SBI_HEADER_XAPI_KEY="SBI_HEADER_XAPI_KEY";
	public static final String SBI_RUPAY_HEADER_PG_INSTANCE_ID="SBI_RUPAY_HEADER_PG_INSTANCE_ID";
	public static final String SBI_RUPAY_HEADER_MERCHANT_ID="SBI_RUPAY_HEADER_MERCHANT_ID";
	
	//URLS
	public static final String SBI_pVrq_VERSIONING_REQUEST="SBI_pVrq_VERSIONING_REQUEST";
	public static final String SBI_RUPAY_CHECKBIN_URL="SBI_RUPAY_CHECKBIN_URL";
    public static final String SBI_RUPAY_INITIATE_URL = "SBI_RUPAY_INITIATE_URL";
    public static final String SBI_RUPAY_REDIRECT_RETURN_URL="SBI_RUPAY_REDIRECT_RETURN_URL";
    public static final String SBI_RUPAY_REDIRECT_RETURN_URL_V2="SBI_RUPAY_REDIRECT_RETURN_URL_V2";
    public static final String SBI_RUPAY_VERIFY_OTP_URL="SBI_RUPAY_VERIFY_OTP_URL";
    public static final String SBI_RUPAY_RESEND_OTP_URL="SBI_RUPAY_RESEND_OTP_URL";
    public static final String SBI_RUPAY_GENERATE_OTP_URL="SBI_RUPAY_GENERATE_OTP_URL";
    public static final String SBI_RUPAY_AUTH_URL="SBI_RUPAY_AUTH_URL";
    public static final String SBI_REFUND_URL="SBI_REFUND_URL";
    public static final String SBI_SALE_AUTH_URL="SBI_SALE_AUTH_URL";
    public static final String CREATE_TOKEN="CREATE_TOKEN";
    public static final String SBI_CARD_RETURN_URL="SBI_CARD_RETURN_URL";
    public static final String SBI_CARD_RETURN_URL_V2="SBI_CARD_RETURN_URL_V2";
    
    //pvrq Details
    public static final String SBI_DEVICE_CHANNEL="SBI_DEVICE_CHANNEL";
    public static final String pVrq_MESSAGE_TYPE="pVrq_MESSAGE_TYPE";
    public static final String SBI_p_VERSIONING="SBI_p_VERSIONING";
    public static final String SBI_ACQUIRERBIN="SBI_ACQUIRERBIN";
    public static final String SBI_ACQUIRERID="SBI_ACQUIRERID";
    
    //parq Details
    
    public static final String SBI_ThreeDSRequestorID="SBI_ThreeDSRequestorID";
    public static final String SBI_ThreeDSRequestorName="SBI_ThreeDSRequestorName";
    
    //saleAuth
    
    public static final String SBI_ACQUIRING_BANK_ID="SBI_ACQUIRING_BANK_ID";
    
    //Token Header
    
    public static final String SBI_TOKEN_CLIENT_ID="SBI_TOKEN_CLIENT_ID";
    public static final String SBI_TOKEN_CLIENT_API_KEY="SBI_TOKEN_CLIENT_API_KEY";
    public static final String SBI_TOKEN_CLIENT_API_USER="SBI_TOKEN_CLIENT_API_USER";
    
    //
    public static final String SBI_ACQUIRER_BIN_MASTER="SBI_ACQUIRER_BIN_MASTER";
    public static final String SBI_ACQUIRER_BIN_VISA="SBI_ACQUIRER_BIN_VISA";
    
    //
    public static final String SBI_SECRET_KEY="SBI_SECRET_KEY";
    public static final String SBI_TOKEN_ENC_SECRET_KEY="SBI_TOKEN_ENC_SECRET_KEY";
    public static final String SBI_TOKEN_DEC_SECRET_KEY="SBI_TOKEN_DEC_SECRET_KEY";
    
    
    //
    public static final String SBI_CLIENT_REFERENCE_ID_LENGTH="SBI_CLIENT_REFERENCE_ID_LENGTH";
    
    //
    public static final String SBI_VAULT_ID="SBI_VAULT_ID";
    
    //Generate Token 
    
    public static final String SBI_WIBMO_MERCHANT_ID="SBI_WIBMO_MERCHANT_ID";
    
    
    //Sale Auth
    
    public static final String SBI_PG_INSTENCE_ID_AUTH_SALE="SBI_PG_INSTENCE_ID_AUTH_SALE";
    
    
    //token RequestorID
    public static final String SBI_TOKEN_REQUESTOR_ID_VISA="SBI_TOKEN_REQUESTOR_ID_VISA";
    public static final String SBI_TOKEN_REQUESTOR_ID_RUPAY="SBI_TOKEN_REQUESTOR_ID_RUPAY";
    public static final String SBI_TOKEN_REQUESTOR_ID_MASTER="SBI_TOKEN_REQUESTOR_ID_MASTER";
    
    //wimbo redirection Url
    
    public static final String SBI_WIMBO_RUPAY_REDIRECTION_URL="SBI_WIMBO_RUPAY_REDIRECTION_URL";
    
   
    
    
	public static String postapi(String url, String requestjson, SbiRequestHeader header) {
		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, requestjson);
			Request request = new Request.Builder().url(url).method("POST", body)
					.addHeader("x-api-key", header.getXapikey()).addHeader("pgInstanceId", header.getPgInstanceId())
					.addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();

			String responseString = response.body().string();
			logger.info("response===> : " + responseString);

			return responseString;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public static String authpost(String url, String requestjson, SbiRequestHeader header) {
		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, requestjson);
			Request request = new Request.Builder().url(url).method("POST", body)
					.addHeader("x-api-key", header.getXapikey()).addHeader("pgInstanceId", header.getPgInstanceId())

					.addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();

			String responseString = response.body().string();
			logger.info("response===> : " + responseString);

			return responseString;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public static String formpostapi(String url, EncryptedRequestData requestdata, SbiRequestHeader header) {
		Gson gson = new Gson();
		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("text/plain");
			RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
					.addFormDataPart("signedEncRequestPayload", requestdata.getSignedEncRequestPayload())
					.addFormDataPart("requestSymmetricEncKey", requestdata.getRequestSymmetricEncKey())
					.addFormDataPart("iv", requestdata.getIv()).build();
			Request request = new Request.Builder().url(url).method("POST", body)
					.addHeader("x-api-key", header.getXapikey()).addHeader("pgInstanceId", header.getPgInstanceId())
					.build();
			Response response = client.newCall(request).execute();
			String res = response.body().string();

			return res;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);

		}
		return null;
	}

	public static String rupayformdata(String url, EncryptedRequestData requestdata, SbiRequestHeader header) {
		Gson gson = new Gson();
		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("text/plain");
			RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
					.addFormDataPart("signedEncRequestPayload", requestdata.getSignedEncRequestPayload())
					.addFormDataPart("requestSymmetricEncKey", requestdata.getRequestSymmetricEncKey())
					.addFormDataPart("iv", requestdata.getIv()).build();
			Request request = new Request.Builder().url(url).method("POST", body)
					.addHeader("x-api-key", header.getXapikey()).addHeader("pg_instance_id", header.getPgInstanceId())
					.addHeader("merchant_id", header.getMerchantId()).addHeader("Content-Type", "application/json")
					.build();
			Response response = client.newCall(request).execute();
			String res = response.body().string();

			return res;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);

		}
		return null;
	}

	public static String rupaypostjson(String url, String requestdata, SbiRequestHeader header) {
		Gson gson = new Gson();
		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, requestdata);
			Request request = new Request.Builder().url(url).method("POST", body)
					.addHeader("x-api-key", header.getXapikey()).addHeader("pgInstanceId", header.getPgInstanceId())
					.addHeader("merchantId", header.getMerchantId()).addHeader("Content-Type", "application/json")
					.build();
			Response response = client.newCall(request).execute();

			String responseString = response.body().string();
			logger.info("response===> : " + responseString);

			return responseString;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}
	public static String posttokenapi(String url, String requestjson, TokenHeader tokenheader) {
		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, requestjson);
			Request request = new Request.Builder().url(url).method("POST", body)
					.addHeader("X-Auth-Token",tokenheader.getAuthToken() )
					.addHeader("clientId",tokenheader.getClientId())
					.addHeader("clientApiUser", tokenheader.getClientApiUser())
					.addHeader("clientApiKey", tokenheader.getClientApiKey())
					.addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();

			String responseString = response.body().string();
			logger.info("response===> : " + responseString);

			return responseString;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

}
