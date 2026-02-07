package com.ftk.pg.util;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.requestvo.BillDeskRequestHeader;
import com.nimbusds.jose.util.Base64URL;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BillDeskUtils {
	static Logger logger = LogManager.getLogger(BillDeskUtils.class);

	public static final String BILL_DESK_ENC_KEY = "BILL_DESK_ENC_KEY";
	public static final String BILL_DESK_GETEPAY_PUBLIC_SIGNING_KEY = "BILL_DESK_GETEPAY_PUBLIC_SIGNING_KEY";
	public static final String BILL_DESK_GETEPAY_PRIVATE_SIGNING_KEY = "BILL_DESK_GETEPAY_PRIVATE_SIGNING_KEY";
	public static final String BILL_DESK_GETEPAY_PRIVATE_DEC_KEY = "BILL_DESK_GETEPAY_PRIVATE_DEC_KEY";
	public static final String BILL_DESK_CLIENT_ID = "BILL_DESK_CLIENT_ID";
	public static final String BILL_DESK_MERC_ID = "BILL_DESK_MERC_ID";
	public static final String BILL_DESK_TRANSACTION_URL = "BILL_DESK_TRANSACTION_URL";
	public static final String BILL_DESK_NB_RETURN_URL = "BILL_DESK_NB_RETURN_URL";
	public static final String BILL_DESK_NB_RETURN_URL_V2 = "BILL_DESK_NB_RETURN_URL_V2";
	public static final String BILL_DESK_RETRIEVE_TRANSACTION_URL="BILL_DESK_RETRIEVE_TRANSACTION_URL";  
	public static final String BILL_DESK_CREATE_REFUND_URL="BILL_DESK_CREATE_REFUND_URL";  
	public static final String BILL_DESK_RETRIEVE_REFUND_URL="BILL_DESK_RETRIEVE_REFUND_URL";  

	public static String getThumbprint(String cer) throws Exception {
		// TODO Auto-generated method stub
		try {

			FileInputStream in = new FileInputStream(cer);
			byte[] keyBytes = new byte[in.available()];
			in.read(keyBytes);
			in.close();
			String certStr = new String(keyBytes, "UTF-8");
			certStr = certStr.replaceAll("BEGIN CERTIFICATE", "");
			certStr = certStr.replaceAll("END CERTIFICATE", "");
			certStr = certStr.replaceAll("-", "").replaceAll("\\s", "");
			certStr = certStr.trim();
			byte[] decodeVal = Base64.getDecoder().decode(certStr);
			ByteArrayInputStream instr = new ByteArrayInputStream(decodeVal);
			CertificateFactory cf;
			cf = CertificateFactory.getInstance("X.509");
			X509Certificate certObj = (X509Certificate) cf.generateCertificate(instr);
			String thumbprint = Base64URL.encode(MessageDigest.getInstance("SHA-256").digest(certObj.getEncoded()))
					.toString();
			logger.info("Bill Desk ThumbPrint=====>" + thumbprint);

			return thumbprint;

		} catch (CertificateException e) {

			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public static String getFingerprint(String cer) throws Exception {

		FileInputStream in = new FileInputStream(cer);

		byte[] keyBytes = new byte[in.available()];
		in.read(keyBytes);
		in.close();
		String certStr = new String(keyBytes, "UTF-8");

		certStr = certStr.replaceAll("BEGIN CERTIFICATE", "");
		certStr = certStr.replaceAll("END CERTIFICATE", "");
		certStr = certStr.replaceAll("-", "").replaceAll("\\s", "");
		certStr = certStr.trim();
		byte[] decodeVal = Base64.getDecoder().decode(certStr);
		ByteArrayInputStream instr = new ByteArrayInputStream(decodeVal);
		CertificateFactory cf;
		cf = CertificateFactory.getInstance("X.509");
		X509Certificate certObj = (X509Certificate) cf.generateCertificate(instr);
		String thumbprint = Base64URL.encode(MessageDigest.getInstance("SHA-256").digest(certObj.getEncoded()))
				.toString();
		logger.info("Bill Desk Finger Print======> " + thumbprint);

		return thumbprint;
	}

	public static PrivateKey signingPrivateKey(String privateKeyPath) throws Exception {
		String privateKeyString = new String(Files.readAllBytes(Paths.get(privateKeyPath)));
		PrivateKey pk = parsePrivateKey(privateKeyString);
		return pk;
	}

	public static PrivateKey decyptionPrivateKey(String privateKeyPath) throws Exception {
		String privateKeyString = new String(Files.readAllBytes(Paths.get(privateKeyPath)));
		PrivateKey pk = parsePrivateKey(privateKeyString);
		return pk;

	}

	private static PrivateKey parsePrivateKey(String keyString) throws Exception {
		String privateKeyPEM = keyString.replace("-----BEGIN PRIVATE KEY-----", "")
				.replaceAll(System.lineSeparator(), "").replace("-----END PRIVATE KEY-----", "");
		byte[] decodedKey = Base64.getDecoder().decode(privateKeyPEM);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePrivate(keySpec);
	}

	public static String generateTraceId() {
		String uniqueId = UUID.randomUUID().toString().replace("-", "");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String currentDate = dateFormat.format(new Date());
		String traceId = uniqueId + currentDate;
		traceId = traceId.substring(0, Math.min(traceId.length(), 35));

		return traceId;
	}

	public static String postapi(String url, String requestjson, BillDeskRequestHeader header) {
		try {
			logger.info("Transaction Url======>" + url);
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("application/jose");
			RequestBody body = RequestBody.create(requestjson, null);
			Request request = new Request.Builder().url(url).method("POST", body)
					.addHeader("Accept", "application/jose").addHeader("Content-Type", "application/jose")
					.addHeader("BD-Traceid", header.getBdtraceId()).addHeader("BD-Timestamp", header.getBdTimeStramp())
					.build();
			Headers headers = request.headers();
			for (int i = 0, size = headers.size(); i < size; i++) {
				logger.info(headers.name(i) + ": " + headers.value(i));
			}
			Response response = client.newCall(request).execute();
			String responseString = response.body().string();
			logger.info("Bill Desk response===> : " + responseString);
			return responseString;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

}
