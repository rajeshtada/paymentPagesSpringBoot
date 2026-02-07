package com.ftk.pg.util;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Map;
import java.util.Random;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.TransactionLog;
import com.ftk.pg.vo.nbbl.NbblRequestWrapper;
import com.ftk.pg.vo.nbbl.Signature;
import com.ftk.pg.vo.nbbl.reqTxnInit.Head;
import com.ftk.pg.vo.nbbl.reqTxnInit.PaymentRequestNBBL;
import com.ftk.pg.vo.nbbl.reqTxnInit.Txn;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class NbblUtil {

	static Logger logger = LogManager.getLogger(NbblUtil.class);
	
	public static final String NBBL_PA_ID = "NBBL_PA_ID";
	public static final String NBBL_PA_NAME = "NBBL_PA_NAME";
	
	public static final String NBBL_PUBLIC_KEY_CERT = "NBBL_PUBLIC_KEY_CERT";
	public static final String GTP_NBBL_PRIVATE_KEY_FILE = "GTP_NBBL_PRIVATE_KEY_FILE";
	public static final String NBBL_RETURN_URL = "NBBL_RETURN_URL";

	public static final String NBBL_REQ_TXN_INIT_URL = "NBBL_REQ_TXN_INIT_URL";
	public static final String NBBL_RESP_TXN_INIT_URL = "NBBL_RESP_TXN_INIT_URL";
	public static final String NBBL_REQ_TXN_INIT_API_KEY = "NBBL_REQ_TXN_INIT_API_KEY";

	public static final String NBBL_HEART_BEAT_URL = "NBBL_HEART_BEAT_URL";
	
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final String DIGITS = "0123456789";

	public static String generateTimeStamp() {
		ZonedDateTime zonedDateTime = ZonedDateTime.now();
//		ZoneId zone = ZoneId.of("Asia/Kolkata");
//		ZonedDateTime zonedDateTime = ZonedDateTime.now(zone);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
		String timestamp = zonedDateTime.format(formatter);
		return timestamp;

	}

	
	public static String generateMessageId(String txnId, LocalDateTime dateTime) {
//		long timeMillis = System.currentTimeMillis();

//		 StringBuilder sb = new StringBuilder(22);
//		 Random random = new Random();
//	        for (int i = 0; i < 22; i++) {
//	        	int randomIntBounded = random.nextInt(CHARACTERS.length());
//	            sb.append(CHARACTERS.charAt(randomIntBounded));
//	        }
//	        
//	        while(txnId.length() < 10) {
//	        	txnId = "0"+txnId;
//	        }
//	        return "MSG"+sb.toString()+txnId;

		StringBuilder sb = new StringBuilder(35);
		Random random = new Random();
		for (int i = 0; i < 27; i++) {
			sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
		}

		int year = dateTime.getYear() % 10;
		int dayOfYear = dateTime.getDayOfYear();
		String julianDate = String.format("%d%03d", year, dayOfYear);
		sb.append(julianDate);

		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
		sb.append(dateTime.format(timeFormatter));

		return sb.toString();
	}

	public static String generateRefId(String paID, String txnId, LocalDateTime dateTime) {

		StringBuilder sb = new StringBuilder(20);
		sb.append(paID);
		
		int year = dateTime.getYear() % 10;
		int dayOfYear = dateTime.getDayOfYear();
		String julianDate = String.format("%d%03d", year, dayOfYear);
		sb.append(julianDate);
		
		Random random = new Random();
		sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));

		while (txnId.length() < 10) {
			txnId = "0" + txnId;
		}
		return sb.toString() + txnId;
	}

	public static String generateTimeStamp(ZonedDateTime zonedDateTime) {

//		ZoneId zone = ZoneId.of("Asia/Kolkata");
//		ZonedDateTime zonedDateTime = ZonedDateTime.now(zone);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
        String timestamp = zonedDateTime.format(formatter);
		return timestamp;
		
	}

	public static String postapi2(String data, String url, String apikey) {
		try {

			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, data);
			Request request = new Request.Builder().url(url).method("POST", body)
					.addHeader("Content-Type", "application/json").addHeader("Accept", "application/json")
					.addHeader("X-API-AUTH", apikey).build();
			Response response = client.newCall(request).execute();
			String res = response.body().string();
			return res;

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}
	
	public static String postapi(String data, String url, String apikey) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(url);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Accept", "application/json");
            request.setHeader("X-API-AUTH", apikey);
            request.setEntity(new StringEntity(data));

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                String responseBody = EntityUtils.toString(response.getEntity());
                logger.info("Response Code: " + statusCode);
                logger.info("Response Body: " + responseBody);
                return responseBody;
            }
        } catch (Exception e) {
        	new GlobalExceptionHandler().customException(e);
        }

		return null;
	}

	public static void checkHeartBeatApi(TransactionLog transactionLog, Map<String, String> propMap) {
		// TODO Auto-generated method stub
		
		try {
			
			String nbblPrivateKeyFile = propMap.get(GTP_NBBL_PRIVATE_KEY_FILE);
			String nbblPublicKeyCert = propMap.get(NBBL_PUBLIC_KEY_CERT);
			String paId = propMap.get(NBBL_PA_ID);

			logger.info("nbblEncryption pvtKey : " + nbblPrivateKeyFile + " : publicCert : " + nbblPublicKeyCert);
			NbblEncryptionUtil nbblEncryptionUtil = new NbblEncryptionUtil(nbblPrivateKeyFile, nbblPublicKeyCert);

		LocalDateTime createdDate = transactionLog.getCreatedDate();
		ZonedDateTime zonedDateTime = createdDate.atZone(ZoneId.systemDefault());

		String timenow = NbblUtil.generateTimeStamp(zonedDateTime);
	    
		String refId = NbblUtil.generateRefId(paId, String.valueOf(transactionLog.getTransactionId()),createdDate);
		String messageId = NbblUtil.generateMessageId(String.valueOf(transactionLog.getTransactionId()),createdDate);
		
		PaymentRequestNBBL paymentRequestnbbl = new PaymentRequestNBBL();
		
		Head head = new Head();
		head.setVer("1.0");
		head.setTs(timenow);
		head.setMsgID(messageId);
		head.setOrgID(paId);

		paymentRequestnbbl.setHead(head);
		paymentRequestnbbl.setTxn(Txn.builder().refID(refId).ts(timenow).build());
		
		Gson gson = new Gson();
		String payload = gson.toJson(paymentRequestnbbl);
		
		logger.info("nbbl Heartbeat Api payload : " + payload);
		
		String base64Signature = nbblEncryptionUtil.generateSignECDSA(payload, transactionLog);
		logger.info("nbbl Signature (Base64) === " + base64Signature);

		String protectedStringBase64 = nbblEncryptionUtil.generateProtectedInfoBase64(paId, transactionLog);
		logger.info("nbbl protectedInfo (Base64) === " + protectedStringBase64);
		

		NbblRequestWrapper nbblRequestWrapper = new NbblRequestWrapper();
		
		Signature signature = new Signature();
		signature.setProtectedValue(protectedStringBase64);
		signature.setSignature(base64Signature);
		nbblRequestWrapper.setSignature(signature);
		
		paymentRequestnbbl.setSignature(signature);
		String payloadWithSignature = gson.toJson(paymentRequestnbbl);
		
		logger.info("payload with signature=>" + payloadWithSignature);
		
		String encryptPayload = nbblEncryptionUtil.encryptPayload(payloadWithSignature);
		
		nbblRequestWrapper.setPayload(encryptPayload);

		String finalRequestPayload = gson.toJson(nbblRequestWrapper);

		logger.info("nbbl encrypted request === " + finalRequestPayload);

		String trasnactionUrl = propMap.get(NBBL_HEART_BEAT_URL);
		trasnactionUrl = trasnactionUrl.replace("{referenceId}", refId);
		logger.info("nbbl NBBL_HEART_BEAT_URL === " + trasnactionUrl);

		String apikey = propMap.get(NBBL_REQ_TXN_INIT_API_KEY);
		String postapi = NbblUtil.postapi(finalRequestPayload, trasnactionUrl, apikey);
		logger.info("nbbl trasnaction api response === " + postapi);
		
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		
	}

}
