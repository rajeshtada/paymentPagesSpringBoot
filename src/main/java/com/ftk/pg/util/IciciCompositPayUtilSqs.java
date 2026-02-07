package com.ftk.pg.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftk.pg.encryption.RSAUtil;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.NodalMerchantSettlement;
import com.ftk.pg.modal.SettlementReportHistory;
import com.ftk.pg.modal.UpiQrDetail;
import com.ftk.pg.requestvo.IciciHybridRequest;
import com.ftk.pg.requestvo.IciciNeftDebitRequeryRequest;
import com.ftk.pg.requestvo.IciciNeftRequeryRequest;
import com.ftk.pg.responsevo.IciciCompositPayNEFTResponse;
import com.ftk.pg.responsevo.IciciNeftDebitRequeryResponse;
import com.ftk.pg.responsevo.IciciNeftRequeryResponse;

public class IciciCompositPayUtilSqs {
	static Logger logger = LogManager.getLogger(IciciCompositPayUtilSqs.class);

	public static final String ICICI_AGGID_KEY = "ICICI_AGGID_KEY";
	public static final String ICICI_AGGNAME_KEY = "ICICI_AGGNAME_KEY";
	public static final String ICICI_CORPID_KEY = "ICICI_CORPID_KEY"; // Not available for production
	public static final String ICICI_USERID_KEY = "ICICI_USERID_KEY"; // Not available for production
	public static final String ICICI_DEBITAC_KEY = "ICICI_DEBITAC_KEY"; // Ask Pravin
	public static final String ICICI_API_KEY = "ICICI_API_KEY";
	public static final String ICICI_BENEREGURL_KEY = "ICICI_BENEREGURL_KEY";// Need to check
	public static final String ICICI_REGPUBLICCER_KEY = "ICICI_REGPUBLICCER_KEY";// Need to check
	public static final String ICICI_ALIASID_KEY = "ICICI_ALIASID_KEY"; // Need to check
	public static final String ICICI_CIBREGURL_KEY = "ICICI_CIBREGURL_KEY";
	public static final String ICICI_URN_KEY = "ICICI_URN_KEY"; // Need to call cib reg
	public static final String ICICI_CPAYXPRIORITYIMPS_KEY = "ICICI_CPAYXPRIORITYIMPS_KEY"; // Fixed, check for test
	public static final String ICICI_CPAYPUBLICCER_KEY = "ICICI_CPAYPUBLICCER_KEY"; // Need to verify
	public static final String ICICI_COMPAY_API_KEY = "ICICI_COMPAY_API_KEY"; // Need to verify
	public static final String ICICI_NEFTTXNCREDITSTATUSURL_KEY = "ICICI_NEFTTXNCREDITSTATUSURL_KEY"; // Need to verify
	public static final String ICICI_CPAYXPRIORITYNEFT_KEY = "ICICI_CPAYXPRIORITYNEFT_KEY";
	public static final String ICICI_CPAYXPRIORITYRTGS_KEY = "ICICI_COMPAY_API_KEY";
	// public static final String ICICI_COMPAY_API_KEY = "ICICI_COMPAY_API_KEY";
	public static final String GETEPAY_ICICI_PRIVATE_KEY = "GETEPAY_ICICI_PRIVATE_KEY";
	public static final String OFFLINE_GETEPAY_ICICI_PRIVATE_KEY = "OFFLINE_GETEPAY_ICICI_PRIVATE_KEY";

	public static final String ICICI_NEFTTXNDEBITSTATUSURL_KEY = "ICICI_NEFTTXNDEBITSTATUSURL_KEY";

	public static final String ICICI_COMPAYURL_KEY = "ICICI_COMPAYURL_KEY";

	public static final String ICICI_REMITTERMOBNO_KEY = "ICICI_REMITTERMOBNO_KEY"; // Need to check with pravin

	public static final String ICICI_IFSC_KEY = "ICICI_IFSC_KEY"; // Fixed, check for test class

	public static final String ICICI_COMPOSIT_API_CALL_FLAG = "ICICI_COMPOSIT_API_CALL_FLAG"; // Api call start flag
	public static final String NEFT_ICICI_USERID_KEY = "NEFT_ICICI_USERID_KEY"; //
	public static final String NEFT_ICICI_ALIASID_KEY = "NEFT_ICICI_ALIASID_KEY"; //
	public static final String NEFT_ICICI_URN_KEY = "NEFT_ICICI_URN_KEY"; //

	private static String generateRandomNumber() {
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 16;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		String generatedString = buffer.toString();
		// logger.info(generatedString);
		return generatedString;
	}

	public static String hybridDeryption(Map<String, String> properties, IciciHybridRequest hybridResponse)
			throws Exception {
		String getepayClientCertificatePath = properties.get(OFFLINE_GETEPAY_ICICI_PRIVATE_KEY);
		logger.info("iciciPrivateKeyPath " + getepayClientCertificatePath);
		String encryptedData = hybridResponse.getEncryptedData();
		byte[] encryptedDataBytes = Base64.getDecoder().decode(encryptedData);
		// Arrays.copyOfRange(original, 16, original.length);
		byte[] ivBytes = Arrays.copyOfRange(encryptedDataBytes, 0, 16);
		byte[] finalEncryptedDataBytes = Arrays.copyOfRange(encryptedDataBytes, 16, encryptedDataBytes.length);
		String encryptedKeyRes = RSAUtil.decrypt(hybridResponse.getEncryptedKey(), getepayClientCertificatePath);
		String decodedData = Util.decryptAES(finalEncryptedDataBytes, ivBytes, encryptedKeyRes.getBytes());
		return decodedData;
	}

	public static IciciHybridRequest hybridEncryption(Map<String, String> properties, String requestString,
			String requestId) throws Exception {
		String iciciPublicKeyPath = properties.get(ICICI_CPAYPUBLICCER_KEY);
		String randomNo = generateRandomNumber();
//		logger.info("Random No=>" + randomNo + "::key length=>" + randomNo.getBytes().length);
		byte[] rsaEncrypted = RSAUtil.encrypt(randomNo, iciciPublicKeyPath);
		String encryptedKey = Base64.getEncoder().encodeToString(rsaEncrypted);
		SecureRandom randomSecureRandom = new SecureRandom();
		byte[] iv = new byte[16];
		randomSecureRandom.nextBytes(iv);
		String base64EncodedIv = Base64.getEncoder().encodeToString(iv);
//		String encodedData = Util.encryptAES(requestString.getBytes(), iv, randomNo.getBytes());
		String encodedData = encryptAES(requestString.getBytes(), iv, randomNo.getBytes());
//		logger.info("Encrypted Data=>" + encodedData);
		IciciHybridRequest hybridRequest = new IciciHybridRequest();
		hybridRequest.setClientInfo("Getepay");
		hybridRequest.setEncryptedData(encodedData);
		hybridRequest.setEncryptedKey(encryptedKey);
		hybridRequest.setIv(base64EncodedIv);
		hybridRequest.setOaepHashingAlgorithm("");
		hybridRequest.setOptionalParam("");
		hybridRequest.setRequestId(requestId);
		hybridRequest.setService("LOP");
		return hybridRequest;
	}

	private static IciciHybridRequest hybridEncryption(Map<String, String> properties, String requestString,
			SettlementReportHistory settlement) throws Exception {
		String iciciPublicKeyPath = properties.get(ICICI_CPAYPUBLICCER_KEY);
		logger.info("iciciPublicKeyPath " + iciciPublicKeyPath);
		
		String iciciDupReqKey = properties.get("ICICI_DUPLICATE_REQUEST_KEY");
		if (iciciDupReqKey == null || iciciDupReqKey.trim().equals("")) {
			iciciDupReqKey = "";
		} else {
			iciciDupReqKey = iciciDupReqKey.trim();
		}
		String iciciDebitDupReqKey = properties.get("ICICI_DUPLICATE_INQUIRY_REQUEST_KEY");
		if (iciciDebitDupReqKey == null || iciciDebitDupReqKey.trim().equals("")) {
			iciciDebitDupReqKey = "";
		} else {
			iciciDebitDupReqKey = iciciDebitDupReqKey.trim();
		}

		String randomNo = generateRandomNumber();
		logger.info("Random No=>" + randomNo + "::key length=>" + randomNo.getBytes().length);

		byte[] rsaEncrypted = RSAUtil.encrypt(randomNo, iciciPublicKeyPath);
		String encryptedKey = Base64.getEncoder().encodeToString(rsaEncrypted);

		SecureRandom randomSecureRandom = new SecureRandom();

		byte[] iv = new byte[16];
		randomSecureRandom.nextBytes(iv);

		String base64EncodedIv = Base64.getEncoder().encodeToString(iv);

		String encodedData = Util.encryptAES(requestString.getBytes(), iv, randomNo.getBytes());

		logger.info("Encrypted Data=>" + encodedData);

		IciciHybridRequest hybridRequest = new IciciHybridRequest();

		hybridRequest.setClientInfo("Getepay");
		hybridRequest.setEncryptedData(encodedData);
		hybridRequest.setEncryptedKey(encryptedKey);
		hybridRequest.setIv(base64EncodedIv);
		hybridRequest.setOaepHashingAlgorithm("");
		hybridRequest.setOptionalParam("");
		hybridRequest.setRequestId(String.valueOf(settlement.getId() + iciciDupReqKey + iciciDebitDupReqKey));
		hybridRequest.setService("LOP");
		return hybridRequest;
	}

	public static String encryptAES(byte[] decrypted, byte[] iv, byte[] key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		SecretKey secKey = new SecretKeySpec(key, 0, key.length, "AES");
		cipher.init(Cipher.ENCRYPT_MODE, secKey, ivSpec);
		byte[] encrypted = cipher.doFinal(decrypted);
		return Base64.getEncoder().encodeToString(encrypted);
	}

	public static ObjectMapper getObjectMapper() {
		ObjectMapper wrapper = new ObjectMapper();
		wrapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return wrapper;
	}

	public static IciciNeftDebitRequeryResponse iciciNeftRequeryRequest(Map<String, String> properties,
			NodalMerchantSettlement settlement) throws Exception {
		IciciNeftDebitRequeryRequest request = new IciciNeftDebitRequeryRequest();

		request.setUserId(properties.get(ICICI_USERID_KEY));
		request.setAggrId(properties.get(ICICI_AGGID_KEY));
		request.setCorpId(properties.get(ICICI_CORPID_KEY));
		request.setUniqueId("FUTURETEKCOMPAY" + String.valueOf(settlement.getId()));
		// request.setUniqueId(settlement.getUtr());
		request.setUrn(properties.get(ICICI_URN_KEY));

		String apiKey = properties.get(ICICI_COMPAY_API_KEY);
		// String apiKey = "1a67b79647bf4cacbf6a7f0abafb6f4c";

		String jsonString = getObjectMapper().writeValueAsString(request);
		logger.info("Request String=>" + jsonString);

		IciciHybridRequest hybridRequest = hybridEncryption(properties, jsonString, settlement);

		String jsonStringHybritReq = getObjectMapper().writeValueAsString(hybridRequest);
		logger.info("Hybrid Request String=>" + jsonStringHybritReq);

		String urlString = properties.get(ICICI_NEFTTXNDEBITSTATUSURL_KEY);

		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		// con.setRequestProperty("Accept", "*/*");
		// con.setRequestProperty("content-length", "684");
		con.setRequestProperty("apikey", apiKey);

		if (settlement.getSettlementAmount().compareTo(new BigDecimal("200000")) == -1) {
			con.setRequestProperty("x-priority", properties.get(ICICI_CPAYXPRIORITYNEFT_KEY));
		} else {
			con.setRequestProperty("x-priority", properties.get(ICICI_CPAYXPRIORITYRTGS_KEY));
		}

		con.setDoOutput(true);

		for (Map.Entry<String, List<String>> entries : con.getRequestProperties().entrySet()) {

			String values = "";
			for (String value : entries.getValue()) {
				values += value + ",";
			}
			logger.info("Headers=>" + entries.getKey() + " - " + values);
		}

		try (OutputStream os = con.getOutputStream()) {
			byte[] input = jsonStringHybritReq.getBytes("utf-8");
			os.write(input, 0, input.length);
		}

		String responseString = null;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			responseString = response.toString();
			logger.info("response=>" + responseString);

			IciciHybridRequest hybridResponse = getObjectMapper().readValue(responseString, IciciHybridRequest.class);

			if (hybridResponse != null) {
				String decodedData = hybridDeryption(properties, hybridResponse);
				logger.info("Decoded Data=>" + decodedData);
				IciciNeftDebitRequeryResponse r = getObjectMapper().readValue(decodedData,
						IciciNeftDebitRequeryResponse.class);
				return r;
			}

			/*
			 * String getepayClientCertificatePath =
			 * properties.get(Util.GETEPAY_PRIVATE_KEY); String decryptedResponse = new
			 * String(RSAUtil.decrypt(responseString, getepayClientCertificatePath));
			 * logger.info("dec response=>" + decryptedResponse);
			 * IciciCIBRegistrationResponse r =
			 * getObjectMapper().readValue(decryptedResponse,
			 * IciciCIBRegistrationResponse.class); return r;
			 */
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	private static IciciHybridRequest hybridEncryption(Map<String, String> properties, String requestString,
			NodalMerchantSettlement settlement) throws Exception {
		String iciciPublicKeyPath = properties.get(ICICI_CPAYPUBLICCER_KEY);
		String randomNo = generateRandomNumber();
		logger.info("Random No=>" + randomNo + "::key length=>" + randomNo.getBytes().length);

		byte[] rsaEncrypted = RSAUtil.encrypt(randomNo, iciciPublicKeyPath);
		String encryptedKey = Base64.getEncoder().encodeToString(rsaEncrypted);

		SecureRandom randomSecureRandom = new SecureRandom();
		;
		byte[] iv = new byte[16];
		randomSecureRandom.nextBytes(iv);

		String base64EncodedIv = Base64.getEncoder().encodeToString(iv);

		String encodedData = Util.encryptAES(requestString.getBytes(), iv, randomNo.getBytes());

		logger.info("Encrypted Data=>" + encodedData);

		IciciHybridRequest hybridRequest = new IciciHybridRequest();

		hybridRequest.setClientInfo("Getepay");
		hybridRequest.setEncryptedData(encodedData);
		hybridRequest.setEncryptedKey(encryptedKey);
		hybridRequest.setIv(base64EncodedIv);
		hybridRequest.setOaepHashingAlgorithm("");
		hybridRequest.setOptionalParam("");
		hybridRequest.setRequestId(String.valueOf(settlement.getId()));
		hybridRequest.setService("LOP");
		return hybridRequest;
	}

	public static IciciNeftRequeryResponse iciciCreditNeftRequeryRequest(Map<String, String> properties,
			NodalMerchantSettlement settlement) throws Exception {
		IciciNeftRequeryRequest request = new IciciNeftRequeryRequest();

		request.setUserId(properties.get(ICICI_USERID_KEY));
		request.setAggrId(properties.get(ICICI_AGGID_KEY));
		request.setCorpId(properties.get(ICICI_CORPID_KEY));
		// request.setUniqueId("FUTURETEK"+ String.valueOf(settlement.getId()));
		request.setUrn(properties.get(ICICI_URN_KEY));
		request.setUniqueId(settlement.getUtr());

		String apiKey = properties.get(ICICI_COMPAY_API_KEY);
		// String apiKey = "1a67b79647bf4cacbf6a7f0abafb6f4c";

		String jsonString = getObjectMapper().writeValueAsString(request);
		logger.info("Request String=>" + jsonString);

		IciciHybridRequest hybridRequest = hybridEncryption(properties, jsonString, settlement);

		String jsonStringHybritReq = getObjectMapper().writeValueAsString(hybridRequest);
		logger.info("Hybrid Request String=>" + jsonStringHybritReq);

		String urlString = properties.get(ICICI_NEFTTXNCREDITSTATUSURL_KEY);

		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		// con.setRequestProperty("Accept", "*/*");
		// con.setRequestProperty("content-length", "684");
		con.setRequestProperty("apikey", apiKey);

		if (settlement.getSettlementAmount().compareTo(new BigDecimal("200000")) == -1) {
			con.setRequestProperty("x-priority", properties.get(ICICI_CPAYXPRIORITYNEFT_KEY));
		} else {
			con.setRequestProperty("x-priority", properties.get(ICICI_CPAYXPRIORITYRTGS_KEY));
		}

		con.setDoOutput(true);

		for (Map.Entry<String, List<String>> entries : con.getRequestProperties().entrySet()) {

			String values = "";
			for (String value : entries.getValue()) {
				values += value + ",";
			}
			logger.info("Headers=>" + entries.getKey() + " - " + values);
		}

		try (OutputStream os = con.getOutputStream()) {
			byte[] input = jsonStringHybritReq.getBytes("utf-8");
			os.write(input, 0, input.length);
		}

		String responseString = null;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			responseString = response.toString();
			logger.info("response=>" + responseString);

			IciciHybridRequest hybridResponse = getObjectMapper().readValue(responseString, IciciHybridRequest.class);

			if (hybridResponse != null) {
				String decodedData = hybridDeryption(properties, hybridResponse);
				logger.info("Decoded Data=>" + decodedData);
				IciciNeftRequeryResponse r = getObjectMapper().readValue(decodedData, IciciNeftRequeryResponse.class);
				return r;
			}

			/*
			 * String getepayClientCertificatePath =
			 * properties.get(Util.GETEPAY_PRIVATE_KEY); String decryptedResponse = new
			 * String(RSAUtil.decrypt(responseString, getepayClientCertificatePath));
			 * logger.info("dec response=>" + decryptedResponse);
			 * IciciCIBRegistrationResponse r =
			 * getObjectMapper().readValue(decryptedResponse,
			 * IciciCIBRegistrationResponse.class); return r;
			 */
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

//	public static IciciCompositPayNEFTResponse neftPay(Map<String, String> properties,
//			NodalMerchantSettlement settlement) throws Exception {
//		IciciCompositPayNEFTRequest request = new IciciCompositPayNEFTRequest();
//
//		String txnType = "RGS";
//		String apiKey = properties.get(ICICI_COMPAY_API_KEY);
//
//		if (settlement.getIfscCode() == null || settlement.getIfscCode().equals("")) {
//			settlement.setIfscCode(properties.get(ICICI_IFSC_KEY));
//		}
//		String ifsc = settlement.getIfscCode();
//		if (settlement.getIfscCode() != null && !settlement.getIfscCode().equals("")
//				&& settlement.getIfscCode().toUpperCase().startsWith("ICIC")) {
//			String ifsc2 = settlement.getIfscCode().toLowerCase().replace("icic", "");
//			try {
//				long parseNumeric = Long.valueOf(ifsc2);
//				txnType = "TPA";
//				ifsc = "ICIC0000011";
//			} catch (Exception e) {
//				logger.info("ifsc code is not numeric, " + ifsc);
//			}
//		}
//		logger.info("txntype=>" + txnType + " for id:" + request.getUrn());
//		request.setAggrId(properties.get(ICICI_AGGID_KEY));
//		request.setAggrName(properties.get(ICICI_AGGNAME_KEY));
//		request.setAmount(settlement.getSettlementAmount().toString());
//		request.setBeneAccNo(settlement.getAccountNo());
//
//		request.setBeneIFSC(ifsc);
//
//		request.setBeneName(settlement.getAccountName());
//		request.setCrpId(properties.get(ICICI_CORPID_KEY));
//		request.setCrpUsr(properties.get(ICICI_USERID_KEY));
//		request.setMobile(properties.get(ICICI_REMITTERMOBNO_KEY));
//		request.setNarration1(settlement.getRemarks());
//		request.setNarration2("");
//		request.setSenderAcctNo(properties.get(ICICI_DEBITAC_KEY));
//		request.setTranRefNo("FUTURETEKCOMPAY" + settlement.getId());
//		request.setTxnType(txnType);
//
//		request.setUrn(properties.get(ICICI_URN_KEY));
//
//		String jsonString = getObjectMapper().writeValueAsString(request);
//		logger.info("Request String=>" + jsonString);
//
//		IciciHybridRequest hybridRequest = hybridEncryption(properties, jsonString, settlement);
//
//		String jsonStringHybritReq = getObjectMapper().writeValueAsString(hybridRequest);
//		logger.info("Hybrid Request String=>" + jsonStringHybritReq);
//
//		String urlString = properties.get(ICICI_COMPAYURL_KEY);
//
//		URL url = new URL(urlString);
//		HttpURLConnection con = (HttpURLConnection) url.openConnection();
//		con.setRequestMethod("POST");
//		con.setRequestProperty("Content-Type", "application/json");
//		// con.setRequestProperty("Accept", "*/*");
//		// con.setRequestProperty("content-length", "684");
//		con.setRequestProperty("apikey", apiKey);
//		con.setRequestProperty("x-priority", properties.get(ICICI_CPAYXPRIORITYNEFT_KEY));
//		// con.setRequestProperty("host", "api.icicibank.com:8443");
//
//		con.setDoOutput(true);
//
//		for (Map.Entry<String, List<String>> entries : con.getRequestProperties().entrySet()) {
//
//			String values = "";
//			for (String value : entries.getValue()) {
//				values += value + ",";
//			}
//			logger.info("Headers=>" + entries.getKey() + " - " + values);
//		}
//
//		try (OutputStream os = con.getOutputStream()) {
//			byte[] input = jsonStringHybritReq.getBytes("utf-8");
//			os.write(input, 0, input.length);
//		}
//
//		String responseString = null;
//		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
//			StringBuilder response = new StringBuilder();
//			String responseLine = null;
//			while ((responseLine = br.readLine()) != null) {
//				response.append(responseLine.trim());
//			}
//			responseString = response.toString();
//			logger.info("response=>" + responseString);
//
//			IciciHybridRequest hybridResponse = getObjectMapper().readValue(responseString, IciciHybridRequest.class);
//
//			if (hybridResponse != null) {
//				String decodedData = hybridDeryption(properties, hybridResponse);
//				logger.info("Decoded Data=>" + decodedData);
//				IciciCompositPayNEFTResponse r = getObjectMapper().readValue(decodedData,
//						IciciCompositPayNEFTResponse.class);
//				return r;
//			}
//
//			/*
//			 * String getepayClientCertificatePath =
//			 * properties.get(Util.GETEPAY_PRIVATE_KEY); String decryptedResponse = new
//			 * String(RSAUtil.decrypt(responseString, getepayClientCertificatePath));
//			 * logger.info("dec response=>" + decryptedResponse);
//			 * IciciCIBRegistrationResponse r =
//			 * getObjectMapper().readValue(decryptedResponse,
//			 * IciciCIBRegistrationResponse.class); return r;
//			 */
//		} catch (Exception e) {
//			new GlobalExceptionHandler().customException(e);
//		}
//		return null;
//
//	}

//	public static IciciCompositPayNEFTResponse neftPay2(Map<String, String> properties, SettlementReportHistory srh,
//			UpiQrDetail upiQrDetail) throws Exception {
//		logger.info("========IciciCompositPayNEFTResponse calling===========");
//		IciciCompositPayNEFTRequest request = new IciciCompositPayNEFTRequest();
//
//		Long srhId = srh.getId();
//		if (srh.getNetAmount() == null) {
//			srh.setNetAmount(srh.getAmount());
//		}
//		String merchantGenre = upiQrDetail.getMerchantGenre();
//
//		String crpId = "";
//		String crpUsr = "";
//		String aggrId = "";
//		String aggrName = "";
//		String urn = "";
//		String senderAcctNo = "";
//		String apiKey = "";
//		
////		if (merchantGenre != null && merchantGenre.equals("OFFLINE")) {
////			senderAcctNo = properties.get("OFFLINE_ICICI_DEBITAC_KEY");
////			crpId = properties.get("OFFLINE_ICICI_CORPID_KEY");
////			crpUsr = properties.get("OFFLINE_ICICI_USERID_KEY");
////			aggrId = properties.get("OFFLINE_ICICI_AGGID_KEY");
////			aggrName = properties.get("OFFLINE_ICICI_AGGNAME_KEY");
////			urn = properties.get("OFFLINE_ICICI_URN_KEY");
//////			String getepayClientCertificatePath = properties.get(Util.GETEPAY_ICICI_PRIVATE_KEY);
////			String privateKey = properties.get("OFFLINE_GETEPAY_ICICI_PRIVATE_KEY");
////			properties.put(GETEPAY_ICICI_PRIVATE_KEY, privateKey);
////			apiKey = properties.get("OFFLINE_ICICI_API_KEY");
////		} else {
//////			senderAcctNo = properties.get(ICICI_DEBITAC_KEY);
////			senderAcctNo = properties.get("ONLINE_ICICI_DEBITAC_KEY");
//////			crpId = properties.get(ICICI_CORPID_KEY);
////			crpId = properties.get("ONLINE_ICICI_CORPID_KEY");
//////			crpUsr = properties.get("NEFT_ICICI_USERID_KEY");
////			crpUsr = properties.get("ONLINE_ICICI_USERID_KEY");
//////			aggrId = properties.get(ICICI_AGGID_KEY);
////			aggrId = properties.get("ONLINE_ICICI_AGGID_KEY");
//////			aggrName = properties.get(ICICI_AGGNAME_KEY);
////			aggrName = properties.get("ONLINE_ICICI_AGGNAME_KEY");
//////			urn = properties.get(NEFT_ICICI_URN_KEY);
////			urn = properties.get("ONLINE_ICICI_URN_KEY");
////			apiKey = properties.get(ICICI_COMPAY_API_KEY);
////		}
//		
//		senderAcctNo = properties.get("OFFLINE_ICICI_DEBITAC_KEY");
//		crpId = properties.get("OFFLINE_ICICI_CORPID_KEY");
//		crpUsr = properties.get("OFFLINE_ICICI_USERID_KEY");
//		aggrId = properties.get("OFFLINE_ICICI_AGGID_KEY");
//		aggrName = properties.get("OFFLINE_ICICI_AGGNAME_KEY");
//		urn = properties.get("OFFLINE_ICICI_URN_KEY");
//		String privateKey = properties.get("OFFLINE_GETEPAY_ICICI_PRIVATE_KEY");
//		properties.put(GETEPAY_ICICI_PRIVATE_KEY, privateKey);
//		apiKey = properties.get("OFFLINE_ICICI_API_KEY");
//
//		String txnType = "RGS";
//
//		String iciciDupReqKey = properties.get("ICICI_DUPLICATE_REQUEST_KEY");
//		if (iciciDupReqKey == null || iciciDupReqKey.trim().equals("")) {
//			iciciDupReqKey = "";
//		} else {
//			iciciDupReqKey = iciciDupReqKey.trim();
//		}
//
//		if (srh.getIfscCode() == null || srh.getIfscCode().equals("")) {
//			srh.setIfscCode(properties.get(ICICI_IFSC_KEY));
//		}
//		String ifsc = srh.getIfscCode();
//		if (srh.getIfscCode() != null && !srh.getIfscCode().equals("")
//				&& srh.getIfscCode().toUpperCase().startsWith("ICIC")) {
//			String ifsc2 = srh.getIfscCode().toLowerCase().replace("icic", "");
//			try {
//				long parseNumeric = Long.valueOf(ifsc2);
//				txnType = "TPA";
//				ifsc = "ICIC0000011";
//			} catch (Exception e) {
//				logger.info("ifsc code is not numeric, " + ifsc);
//			}
//		}
//		logger.info("txntype=>" + txnType + " for id:" + srhId);
//
//		request.setAmount(srh.getNetAmount().toString());
//		request.setBeneIFSC(ifsc);
//		request.setBeneAccNo(srh.getBeneficiaryAccountNo());
//		request.setBeneName(srh.getBeneficiaryName());
//		request.setNarration1(srh.getTransactionDescCredit().replace("-", ""));
//		request.setNarration2("");
//		request.setTranRefNo("FUTURETEKCOMPAY" + srh.getId() + iciciDupReqKey);
//		request.setTxnType(txnType);
//
////		request.setCrpId(properties.get(ICICI_CORPID_KEY));
//////		request.setCrpUsr(properties.get(ICICI_USERID_KEY));
////		request.setCrpUsr(properties.get(NEFT_ICICI_USERID_KEY));
////		request.setAggrId(properties.get(ICICI_AGGID_KEY));
////		request.setAggrName(properties.get(ICICI_AGGNAME_KEY));
//////		request.setMobile(properties.get(ICICI_REMITTERMOBNO_KEY));
////		request.setSenderAcctNo(properties.get(ICICI_DEBITAC_KEY));
//////		request.setUrn(properties.get(ICICI_URN_KEY));
////		request.setUrn(properties.get(NEFT_ICICI_URN_KEY));
//
//		request.setCrpId(crpId);
//		request.setCrpUsr(crpUsr);
//		request.setAggrId(aggrId);
//		request.setAggrName(aggrName);
//		request.setSenderAcctNo(senderAcctNo);
//		request.setUrn(urn);
////		request.setWorkflowReqd("N");
//		request.setWorkflowReqd("Y");
//
//		String jsonString = getObjectMapper().writeValueAsString(request);
//		logger.info("IciciCompositPay Request =>" + srhId + " : " + jsonString);
//
//		IciciHybridRequest hybridRequest = hybridEncryption(properties, jsonString, srh);
//
//		String jsonStringHybritReq = getObjectMapper().writeValueAsString(hybridRequest);
//		logger.info("IciciCompositPay Hybrid Request=>" + srhId + " : " + jsonStringHybritReq);
//
//		String urlString = properties.get(ICICI_COMPAYURL_KEY);
//
//		logger.info("urlString =>" + urlString);
//
//		URL url = new URL(urlString);
//		HttpURLConnection con = (HttpURLConnection) url.openConnection();
//		con.setRequestMethod("POST");
//		con.setRequestProperty("Content-Type", "application/json");
//		con.setRequestProperty("apikey", apiKey);
////		con.setRequestProperty("x-priority", properties.get(ICICI_CPAYXPRIORITYNEFT_KEY));
//		if (srh.getAmount().compareTo(new BigDecimal("200000")) == -1) {
//			con.setRequestProperty("x-priority", properties.get(ICICI_CPAYXPRIORITYNEFT_KEY));
//		} else {
//			con.setRequestProperty("x-priority", properties.get(ICICI_CPAYXPRIORITYRTGS_KEY));
//		}
//
//		con.setDoOutput(true);
//
//		for (Map.Entry<String, List<String>> entries : con.getRequestProperties().entrySet()) {
//
//			String values = "";
//			for (String value : entries.getValue()) {
//				values += value + ",";
//			}
//			logger.info("Headers=>" + entries.getKey() + " - " + values);
//		}
//
//		String apiFlag = properties.get(ICICI_COMPOSIT_API_CALL_FLAG);
//		if ( apiFlag != null && apiFlag.equalsIgnoreCase("true")) {
//		try (OutputStream os = con.getOutputStream()) {
//			byte[] input = jsonStringHybritReq.getBytes("utf-8");
//			os.write(input, 0, input.length);
//		}
//			} else {
//				logger.info("Api apiFlag is null or false for srhId "+srh.getId()+" : "+apiFlag);
//			}
//
//		String responseString = null;
//		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
//			StringBuilder response = new StringBuilder();
//			String responseLine = null;
//			while ((responseLine = br.readLine()) != null) {
//				response.append(responseLine.trim());
//			}
//			responseString = response.toString();
//			logger.info("IciciCompositPay Enc response=>" + srhId + " : " + responseString);
//
//			IciciHybridRequest hybridResponse = getObjectMapper().readValue(responseString, IciciHybridRequest.class);
//
//			if (hybridResponse != null) {
//				String decodedData = hybridDeryption(properties, hybridResponse);
//				logger.info("Decoded Data=>" + srhId + " : " + decodedData);
//				IciciCompositPayNEFTResponse r = getObjectMapper().readValue(decodedData,
//						IciciCompositPayNEFTResponse.class);
//				return r;
//			}
//
//			/*
//			 * String getepayClientCertificatePath =
//			 * properties.get(Util.GETEPAY_PRIVATE_KEY); String decryptedResponse = new
//			 * String(RSAUtil.decrypt(responseString, getepayClientCertificatePath));
//			 * logger.info("dec response=>" + decryptedResponse);
//			 * IciciCIBRegistrationResponse r =
//			 * getObjectMapper().readValue(decryptedResponse,
//			 * IciciCIBRegistrationResponse.class); return r;
//			 */
//		} catch (Exception e) {
//			new GlobalExceptionHandler().customException(e);
//
//		}
//		return null;
//
//	}

	public static IciciNeftDebitRequeryResponse iciciNeftDebitRequeryRequest2(Map<String, String> properties,
			SettlementReportHistory srh, UpiQrDetail upiQrDetail) throws Exception {

		logger.info("==========IciciNeftDebitRequeryResponse =========");
		IciciNeftDebitRequeryRequest request = new IciciNeftDebitRequeryRequest();
		Long srhId = srh.getId();
		if (srh.getNetAmount() == null) {
			srh.setNetAmount(srh.getAmount());
		}
		String iciciDupReqKey = properties.get("ICICI_DUPLICATE_REQUEST_KEY");
//		if ( iciciDupReqKey != null && !iciciDupReqKey.trim().equals("")) {
//			iciciDupReqKey = "_"+iciciDupReqKey;
//		} else 
		if (iciciDupReqKey == null || iciciDupReqKey.trim().equals("")) {
			iciciDupReqKey = "";
		} else {
			iciciDupReqKey = iciciDupReqKey.trim();
		}

		String merchantGenre = upiQrDetail.getMerchantGenre();

		String crpId = "";
		String crpUsr = "";
		String aggrId = "";
		String aggrName = "";
		String urn = "";
		String senderAcctNo = "";
		String apiKey = "";

//		if (merchantGenre != null && merchantGenre.equals("OFFLINE")) {
//			senderAcctNo = properties.get("OFFLINE_ICICI_DEBITAC_KEY");
//			crpId = properties.get("OFFLINE_ICICI_CORPID_KEY");
//			crpUsr = properties.get("OFFLINE_ICICI_USERID_KEY");
//			aggrId = properties.get("OFFLINE_ICICI_AGGID_KEY");
//			aggrName = properties.get("OFFLINE_ICICI_AGGNAME_KEY");
//			urn = properties.get("OFFLINE_ICICI_URN_KEY");
//			String privateKey = properties.get("OFFLINE_GETEPAY_ICICI_PRIVATE_KEY");
//			properties.put(GETEPAY_ICICI_PRIVATE_KEY, privateKey);
//			apiKey = properties.get("OFFLINE_ICICI_API_KEY");
//		} else {
////			senderAcctNo = properties.get(ICICI_DEBITAC_KEY);
//			senderAcctNo = properties.get("ONLINE_ICICI_DEBITAC_KEY");
////			crpId = properties.get(ICICI_CORPID_KEY);
//			crpId = properties.get("ONLINE_ICICI_CORPID_KEY");
////			crpUsr = properties.get("NEFT_ICICI_USERID_KEY");
//			crpUsr = properties.get("ONLINE_ICICI_USERID_KEY");
////			aggrId = properties.get(ICICI_AGGID_KEY);
//			aggrId = properties.get("ONLINE_ICICI_AGGID_KEY");
////			aggrName = properties.get(ICICI_AGGNAME_KEY);
//			aggrName = properties.get("ONLINE_ICICI_AGGNAME_KEY");
////			urn = properties.get(NEFT_ICICI_URN_KEY);
//			urn = properties.get("ONLINE_ICICI_URN_KEY");
//			apiKey = properties.get(ICICI_COMPAY_API_KEY);
//		}
		
		senderAcctNo = properties.get("OFFLINE_ICICI_DEBITAC_KEY");
		crpId = properties.get("OFFLINE_ICICI_CORPID_KEY");
		crpUsr = properties.get("OFFLINE_ICICI_USERID_KEY");
		aggrId = properties.get("OFFLINE_ICICI_AGGID_KEY");
		aggrName = properties.get("OFFLINE_ICICI_AGGNAME_KEY");
		urn = properties.get("OFFLINE_ICICI_URN_KEY");
		String privateKey = properties.get("OFFLINE_GETEPAY_ICICI_PRIVATE_KEY");
		properties.put(GETEPAY_ICICI_PRIVATE_KEY, privateKey);
		apiKey = properties.get("OFFLINE_ICICI_API_KEY");

		request.setUserId(crpUsr);
		request.setAggrId(aggrId);
		request.setCorpId(crpId);
		request.setUniqueId("FUTURETEKCOMPAY" + srh.getId() + iciciDupReqKey);
		request.setUrn(urn);

		String jsonString = getObjectMapper().writeValueAsString(request);
		logger.info("Request String=>" + jsonString);

		IciciHybridRequest hybridRequest = hybridEncryption(properties, jsonString, srh);

		String jsonStringHybritReq = getObjectMapper().writeValueAsString(hybridRequest);
		logger.info("Hybrid Request String=>" + jsonStringHybritReq);

		String urlString = properties.get("ICICI_COMPAY_DEBIT_STATUS_URL_KEY");

		
		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("apikey", apiKey);

		if (srh.getNetAmount().compareTo(new BigDecimal("200000")) == -1) {
			con.setRequestProperty("x-priority", properties.get(ICICI_CPAYXPRIORITYNEFT_KEY));
		} else {
			con.setRequestProperty("x-priority", properties.get(ICICI_CPAYXPRIORITYRTGS_KEY));
		}

		con.setDoOutput(true);

		for (Map.Entry<String, List<String>> entries : con.getRequestProperties().entrySet()) {

			String values = "";
			for (String value : entries.getValue()) {
				values += value + ",";
			}
			logger.info("Headers=>" + entries.getKey() + " - " + values);
		}

		String apiFlag = properties.get(ICICI_COMPOSIT_API_CALL_FLAG);
		logger.info("ICICI_COMPOSIT_API_CALL_FLAG for srhID : "+srh.getId()+" : "+apiFlag);
		if ( apiFlag != null && apiFlag.equalsIgnoreCase("true")) {
		try (OutputStream os = con.getOutputStream()) {
			byte[] input = jsonStringHybritReq.getBytes("utf-8");
			os.write(input, 0, input.length);
		}
		} else {
			logger.info("Api apiFlag is null or false for srhId "+srh.getId()+" : "+apiFlag);
		}

		String responseString = null;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			responseString = response.toString();
			logger.info("response=>" + srhId + " : " + responseString);

			IciciHybridRequest hybridResponse = getObjectMapper().readValue(responseString, IciciHybridRequest.class);

			if (hybridResponse != null) {
				String decodedData = hybridDeryption(properties, hybridResponse);
				logger.info("Decoded Data=>" + srhId + " : " + decodedData);
				IciciNeftDebitRequeryResponse r = getObjectMapper().readValue(decodedData,
						IciciNeftDebitRequeryResponse.class);
				return r;
			}

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public static IciciNeftRequeryResponse iciciNeftCreditRequeryRequest(Map<String, String> properties,
			SettlementReportHistory settlement,UpiQrDetail upiQrDetail) throws Exception {

		logger.info("===========iciciCreditNeftRequeryRequest function calling==========");
		IciciNeftRequeryRequest request = new IciciNeftRequeryRequest();
		String iciciDupReqKey = properties.get("ICICI_DUPLICATE_REQUEST_KEY");
		if (iciciDupReqKey == null || iciciDupReqKey.trim().equals("")) {
			iciciDupReqKey = "";
		} else {
			iciciDupReqKey = iciciDupReqKey.trim();
		}
		
		String merchantGenre = upiQrDetail.getMerchantGenre();

		String crpId = "";
		String crpUsr = "";
		String aggrId = "";
		String aggrName = "";
		String urn = "";
		String senderAcctNo = "";
		String apiKey = "";

//		if (merchantGenre != null && merchantGenre.equals("OFFLINE")) {
//			senderAcctNo = properties.get("OFFLINE_ICICI_DEBITAC_KEY");
//			crpId = properties.get("OFFLINE_ICICI_CORPID_KEY");
//			crpUsr = properties.get("OFFLINE_ICICI_USERID_KEY");
//			aggrId = properties.get("OFFLINE_ICICI_AGGID_KEY");
//			aggrName = properties.get("OFFLINE_ICICI_AGGNAME_KEY");
//			urn = properties.get("OFFLINE_ICICI_URN_KEY");
//			apiKey = properties.get("OFFLINE_ICICI_CIB_API_KEY");
//			String privateKey = properties.get("OFFLINE_GETEPAY_ICICI_PRIVATE_KEY");
//			properties.put(GETEPAY_ICICI_PRIVATE_KEY, privateKey);
//			
//			String iciciPublicKeyPath = properties.get(ICICI_REGPUBLICCER_KEY);
//			properties.put(ICICI_CPAYPUBLICCER_KEY, iciciPublicKeyPath);
//			
//		} else {
////			senderAcctNo = properties.get(ICICI_DEBITAC_KEY);
//			senderAcctNo = properties.get("ONLINE_ICICI_DEBITAC_KEY");
////			crpId = properties.get(ICICI_CORPID_KEY);
//			crpId = properties.get("ONLINE_ICICI_CORPID_KEY");
////			crpUsr = properties.get("NEFT_ICICI_USERID_KEY");
//			crpUsr = properties.get("ONLINE_ICICI_USERID_KEY");
////			aggrId = properties.get(ICICI_AGGID_KEY);
//			aggrId = properties.get("ONLINE_ICICI_AGGID_KEY");
////			aggrName = properties.get(ICICI_AGGNAME_KEY);
//			aggrName = properties.get("ONLINE_ICICI_AGGNAME_KEY");
////			urn = properties.get(NEFT_ICICI_URN_KEY);
//			urn = properties.get("ONLINE_ICICI_URN_KEY");
//			apiKey = properties.get(ICICI_COMPAY_API_KEY);
//			apiKey = properties.get("ICICI_CIB_API_KEY");
//			String iciciPublicKeyPath = properties.get(ICICI_REGPUBLICCER_KEY);
//			properties.put(ICICI_CPAYPUBLICCER_KEY, iciciPublicKeyPath);
//		}
		
		senderAcctNo = properties.get("OFFLINE_ICICI_DEBITAC_KEY");
		crpId = properties.get("OFFLINE_ICICI_CORPID_KEY");
		crpUsr = properties.get("OFFLINE_ICICI_USERID_KEY");
		aggrId = properties.get("OFFLINE_ICICI_AGGID_KEY");
		aggrName = properties.get("OFFLINE_ICICI_AGGNAME_KEY");
		urn = properties.get("OFFLINE_ICICI_URN_KEY");
		apiKey = properties.get("OFFLINE_ICICI_CIB_API_KEY");
		String privateKey = properties.get("OFFLINE_GETEPAY_ICICI_PRIVATE_KEY");
		properties.put(GETEPAY_ICICI_PRIVATE_KEY, privateKey);
		String iciciPublicKeyPath = properties.get(ICICI_REGPUBLICCER_KEY);
		properties.put(ICICI_CPAYPUBLICCER_KEY, iciciPublicKeyPath);
		
		request.setUserId(crpUsr);
		request.setAggrId(aggrId);
		request.setCorpId(crpId);
		request.setUrn(urn);
		request.setUniqueId("FUTURETEKCOMPAY" + String.valueOf(settlement.getId()+iciciDupReqKey));
		String utrNo = settlement.getSettlementRefNo();
		request.setUniqueId(utrNo);
		
		String urlString = properties.get("ICICI_COMPAY_CREDIT_STATUS_URL_KEY");
		
		String jsonString = getObjectMapper().writeValueAsString(request);
		logger.info("Request String=>" + jsonString);

//		String iciciPublicKeyPath = properties.get("ICICI_CIB_PUBLICCER_KEY");
//		properties.put(ICICI_CPAYPUBLICCER_KEY, iciciPublicKeyPath);
		IciciHybridRequest hybridRequest = hybridEncryption(properties, jsonString, settlement);

		String jsonStringHybritReq = getObjectMapper().writeValueAsString(hybridRequest);
		logger.info("Hybrid Request String=>" + jsonStringHybritReq);

		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("apikey", apiKey);

		if (settlement.getAmount().compareTo(new BigDecimal("200000")) == -1) {
			con.setRequestProperty("x-priority", properties.get(ICICI_CPAYXPRIORITYNEFT_KEY));
		} else {
			con.setRequestProperty("x-priority", properties.get(ICICI_CPAYXPRIORITYRTGS_KEY));
		}

		con.setDoOutput(true);

		for (Map.Entry<String, List<String>> entries : con.getRequestProperties().entrySet()) {

			String values = "";
			for (String value : entries.getValue()) {
				values += value + ",";
			}
			logger.info("Headers=>" + entries.getKey() + " - " + values);
		}

//		try (OutputStream os = con.getOutputStream()) {
//			byte[] input = jsonStringHybritReq.getBytes("utf-8");
//			os.write(input, 0, input.length);
//		}
		String apiFlag = properties.get(ICICI_COMPOSIT_API_CALL_FLAG);
		logger.info("ICICI_COMPOSIT_API_CALL_FLAG for srhID : "+settlement.getId()+" : "+apiFlag);
		if ( apiFlag != null && apiFlag.equalsIgnoreCase("true")) {
		try (OutputStream os = con.getOutputStream()) {
			byte[] input = jsonStringHybritReq.getBytes("utf-8");
			os.write(input, 0, input.length);
		}
		} else {
			logger.info("Api apiFlag is null or false for srhId "+settlement.getId()+" : "+apiFlag);
		}

		String responseString = null;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			responseString = response.toString();
			logger.info("response=>" + responseString);

			IciciHybridRequest hybridResponse = getObjectMapper().readValue(responseString, IciciHybridRequest.class);

			if (hybridResponse != null) {
				String decodedData = hybridDeryption(properties, hybridResponse);
				logger.info("Decoded Data=>" + decodedData);
				IciciNeftRequeryResponse r = getObjectMapper().readValue(decodedData, IciciNeftRequeryResponse.class);
				return r;
			}

			/*
			 * String getepayClientCertificatePath =
			 * properties.get(Util.GETEPAY_PRIVATE_KEY); String decryptedResponse = new
			 * String(RSAUtil.decrypt(responseString, getepayClientCertificatePath));
			 * logger.info("dec response=>" + decryptedResponse);
			 * IciciCIBRegistrationResponse r =
			 * getObjectMapper().readValue(decryptedResponse,
			 * IciciCIBRegistrationResponse.class); return r;
			 */
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

}

//
