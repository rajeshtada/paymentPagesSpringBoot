package com.ftk.pg.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftk.pg.encryption.RSAUtil;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.DmoOnboarding;
import com.ftk.pg.modal.Merchant;
import com.ftk.pg.modal.MerchantSetting;
import com.ftk.pg.modal.NodalMerchantSettlement;
import com.ftk.pg.modal.PanyTransaction;
import com.ftk.pg.modal.PayoutBeneficiaryTransaction;
import com.ftk.pg.modal.PayoutTransaction;
import com.ftk.pg.modal.TransactionEssentials;
import com.ftk.pg.modal.TransactionLog;
import com.ftk.pg.modal.UpiQrDetail;
import com.ftk.pg.requestvo.IciciBankStatementRequest;
import com.ftk.pg.requestvo.IciciBeneficiaryRegistrationRequest;
import com.ftk.pg.requestvo.IciciCIBRegistrationRequest;
import com.ftk.pg.requestvo.IciciCollectRequest;
import com.ftk.pg.requestvo.IciciCollectResponse;
import com.ftk.pg.requestvo.IciciCompositPayIMPSRequest;
import com.ftk.pg.requestvo.IciciCompositPayNEFTRequest;
import com.ftk.pg.requestvo.IciciCompositPayRTGSRequest;
import com.ftk.pg.requestvo.IciciHybridRequest;
import com.ftk.pg.requestvo.IciciImpsRequeryRequest;
import com.ftk.pg.requestvo.IciciNeftDebitRequeryRequest;
import com.ftk.pg.requestvo.IciciNeftRequeryRequest;
import com.ftk.pg.responsevo.IciciBankStatementResponse;
import com.ftk.pg.responsevo.IciciBeneficiaryRegistrationResponse;
import com.ftk.pg.responsevo.IciciCIBRegistrationResponse;
import com.ftk.pg.responsevo.IciciCompositPayIMPSResponse;
import com.ftk.pg.responsevo.IciciCompositPayNEFTResponse;
import com.ftk.pg.responsevo.IciciCompositPayRTGSResponse;
import com.ftk.pg.responsevo.IciciImpsRequeryResponse;
import com.ftk.pg.responsevo.IciciImpsRequeryResponseWrapper;
import com.ftk.pg.responsevo.IciciNeftDebitRequeryResponse;
import com.ftk.pg.responsevo.IciciNeftRequeryResponse;
import com.google.gson.Gson;

public class IciciCompositPay {

	static Logger logger = LogManager.getLogger(IciciCompositPay.class);

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

//	public static final String OFFLINE_ICICI_URN_KEY = "OFFLINE_ICICI_URN_KEY"; // Need to call cib reg
//	public static final String OFFLINE_ICICI_CORPID_KEY = "OFFLINE_ICICI_CORPID_KEY"; // Need to call cib reg
//	public static final String OFFLINE_ICICI_USERID_KEY = "OFFLINE_ICICI_USERID_KEY"; // Need to call cib reg
	
	public static final String ICICI_COMPAYURL_KEY = "ICICI_COMPAYURL_KEY";
	public static final String ICICI_REMITTERMOBNO_KEY = "ICICI_REMITTERMOBNO_KEY"; // Need to check with pravin
	public static final String ICICI_IMPSPASSCODE_KEY = "ICICI_IMPSPASSCODE_KEY"; // Not available
	public static final String ICICI_BCID_KEY = "ICICI_BCID_KEY";// Not available
	public static final String ICICI_IMPSRETCODE_KEY = "ICICI_IMPSRETCODE_KEY"; // Not available
	public static final String ICICI_CPAYPUBLICCER_KEY = "ICICI_CPAYPUBLICCER_KEY"; // Need to verify
	public static final String ICICI_CPAYXPRIORITYNEFT_KEY = "ICICI_CPAYXPRIORITYNEFT_KEY"; // Fixed, check for test
																							// class
	public static final String ICICI_IFSC_KEY = "ICICI_IFSC_KEY"; // Fixed, check for test class
	public static final String ICICI_CPAYXPRIORITYIMPS_KEY = "ICICI_CPAYXPRIORITYIMPS_KEY"; // Fixed, check for test
																							// class
	public static final String ICICI_CPAYXPRIORITYRTGS_KEY = "ICICI_CPAYXPRIORITYRTGS_KEY"; // Fixed, check for test
																							// class

	public static final String ICICI_NEFTTXNSTATUSURL_KEY = "ICICI_NEFTTXNSTATUSURL_KEY"; // Need to verify
	public static final String ICICI_IMPSTXNSTATUSURL_KEY = "ICICI_IMPSTXNSTATUSURL_KEY"; // Not available

	public static final String ICICI_PAYEENAME_KEY = "ICICI_PAYEENAME_KEY"; // Need to check

	public static final String ICICI_RTGSCURRENCY_KEY = "ICICI_RTGSCURRENCY_KEY"; // Need to check

	public static final String ICICI_COMPAY_API_KEY = "ICICI_COMPAY_API_KEY";
	public static final String ICICI_NEFTTXNCREDITSTATUSURL_KEY = "ICICI_NEFTTXNCREDITSTATUSURL_KEY";
	public static final String ICICI_NEFTTXNDEBITSTATUSURL_KEY = "ICICI_NEFTTXNDEBITSTATUSURL_KEY";

	// Collect UPI
	public static final String ICICI_COLLECTURL_KEY = "ICICI_COLLECTURL_KEY";
	public static final String ICICI_COLLECTMERCHANT_KEY = "ICICI_COLLECTMERCHANT_KEY";
	public static final String ICICI_COLLECTCER_KEY = "ICICI_COLLECTCER_KEY";

	// Mandate UPI
	public static final String ICICI_MANDATE_MERCHANT_KEY = "ICICI_MANDATE_MERCHANT_KEY";

	// Statement Api
	public static final String ICICI_NODAL_ACCOUNT_NUMBER = "ICICI_NODAL_ACCOUNT_NUMBER";
	public static final String ICICI_NODAL_STATEMENT_API_URL = "ICICI_NODAL_STATEMENT_API_URL";

	public static final String DMO_CPAYPUBLICCER_KEY = "DMO_CPAYPUBLICCER_KEY";
	public static final String GETEPAY_ICICI_PRIVATE_KEY = "GETEPAY_ICICI_PRIVATE_KEY";
	public static final String DMO_PRIVATE_KEY = "DMO_PRIVATE_KEY";

	private static final String OFFLINE_ICICI_CORPID_KEY ="OFFLINE_ICICI_CORPID_KEY";
	private static final String OFFLINE_ICICI_USERID_KEY ="OFFLINE_ICICI_USERID_KEY";
	private static final String OFFLINE_ICICI_AGGID_KEY = "OFFLINE_ICICI_AGGID_KEY";
	private static final String OFFLINE_ICICI_API_KEY =   "OFFLINE_ICICI_API_KEY";
	private static final String OFFLINE_ICICI_URN_KEY =   "OFFLINE_ICICI_URN_KEY";
	
	
	//
	
	public static final String UPDATED_ICICI_COLLECT_ENABLE="UPDATED_ICICI_COLLECT_ENABLE";
	public static final String UPDATED_ICICI_COLLECTURL_KEY="UPDATED_ICICI_COLLECTURL_KEY";
	
	//
	public static final String UPDATED_ICICI_COLLECT_GETcollect="GETcollect";
	public static final String UPDATED_ICICI_COLLECT_MID_ENABLE="UPDATED_ICICI_COLLECT_MID_ENABLE";

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

		String encodedData = ComponentUtils.encryptAES(requestString.getBytes(), iv, randomNo.getBytes());

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

	private static IciciHybridRequest hybridEncryption(Map<String, String> properties, String requestString,
			TransactionLog txn) throws Exception {
		String iciciPublicKeyPath = properties.get(ICICI_COLLECTCER_KEY);
		String randomNo = generateRandomNumber();
		logger.info("Random No=>" + randomNo + "::key length=>" + randomNo.getBytes().length);

		byte[] rsaEncrypted = RSAUtil.encrypt(randomNo, iciciPublicKeyPath);
		String encryptedKey = Base64.getEncoder().encodeToString(rsaEncrypted);

		SecureRandom randomSecureRandom = new SecureRandom();
		byte[] iv = new byte[16];
		randomSecureRandom.nextBytes(iv);

		String base64EncodedIv = Base64.getEncoder().encodeToString(iv);

		String encodedData = ComponentUtils.encryptAES(requestString.getBytes(), iv, randomNo.getBytes());

		logger.info("Encrypted Data=>" + encodedData);

		IciciHybridRequest hybridRequest = new IciciHybridRequest();

		hybridRequest.setClientInfo("Getepay");
		hybridRequest.setEncryptedData(encodedData);
		hybridRequest.setEncryptedKey(encryptedKey);
		hybridRequest.setIv(base64EncodedIv);
		hybridRequest.setOaepHashingAlgorithm("");
		hybridRequest.setOptionalParam("");
		hybridRequest.setRequestId(String.valueOf(txn.getTransactionId()));
		hybridRequest.setService("LOP");
		return hybridRequest;
	}

	public static String hybridDeryption(Map<String, String> properties, IciciHybridRequest hybridResponse)
			throws Exception {
		String getepayClientCertificatePath = properties.get(ComponentUtils.GETEPAY_ICICI_PRIVATE_KEY);
		String encryptedData = hybridResponse.getEncryptedData();

		byte[] encryptedDataBytes = Base64.getDecoder().decode(encryptedData);
		// Arrays.copyOfRange(original, 16, original.length);
		byte[] ivBytes = Arrays.copyOfRange(encryptedDataBytes, 0, 16);
		byte[] finalEncryptedDataBytes = Arrays.copyOfRange(encryptedDataBytes, 16, encryptedDataBytes.length);

		String encryptedKeyRes = RSAUtil.decrypt(hybridResponse.getEncryptedKey(), getepayClientCertificatePath);

		String decodedData = ComponentUtils.decryptAES(finalEncryptedDataBytes, ivBytes, encryptedKeyRes.getBytes());
		return decodedData;
	}

	public static String hybridDeryptionCollect(Map<String, String> properties, IciciHybridRequest hybridResponse)
			throws Exception {
		String getepayClientCertificatePath = properties.get(ComponentUtils.GETEPAY_ICICI_COLLECT_PRIVATE_KEY);
		String encryptedData = hybridResponse.getEncryptedData();

		byte[] encryptedDataBytes = Base64.getDecoder().decode(encryptedData);
		// Arrays.copyOfRange(original, 16, original.length);
		byte[] ivBytes = Arrays.copyOfRange(encryptedDataBytes, 0, 16);
		byte[] finalEncryptedDataBytes = Arrays.copyOfRange(encryptedDataBytes, 16, encryptedDataBytes.length);

		String encryptedKeyRes = RSAUtil.decrypt(hybridResponse.getEncryptedKey(), getepayClientCertificatePath);

		String decodedData = ComponentUtils.decryptAES(finalEncryptedDataBytes, ivBytes, encryptedKeyRes.getBytes());
		return decodedData;
	}

	public static ObjectMapper getObjectMapper() {
		ObjectMapper wrapper = new ObjectMapper();
		wrapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return wrapper;
	}

	public static IciciCompositPayRTGSResponse rtgsPay(Map<String, String> properties, Merchant merchant,
			NodalMerchantSettlement settlement) throws Exception {
		IciciCompositPayRTGSRequest request = new IciciCompositPayRTGSRequest();

		String txnType = "RGS";
		String apiKey = properties.get(ICICI_API_KEY);

		if (settlement.getIfscCode() == null || settlement.getIfscCode().equals("")) {
			settlement.setIfscCode(properties.get(ICICI_IFSC_KEY));
		}
		if (settlement.getIfscCode() != null && !settlement.getIfscCode().equals("")
				&& settlement.getIfscCode().toUpperCase().startsWith("ICIC")) {
			txnType = "TPA";
		}

		request.setAggrId(properties.get(ICICI_AGGID_KEY));
		request.setAggrName(properties.get(ICICI_AGGNAME_KEY));
		request.setAmount(settlement.getSettlementAmount().toString());
		request.setUrn(properties.get(ICICI_URN_KEY));
		// request.setTxnType(txnType);
		request.setTxnType("RRR");
		request.setCorpId(properties.get(ICICI_CORPID_KEY));
		request.setUserId(properties.get(ICICI_USERID_KEY));

		request.setCreditAcc(settlement.getAccountNo());
		request.setIfsc(settlement.getIfscCode());
		request.setRemarks(settlement.getRemarks());

		request.setDebitAcc(properties.get(ICICI_DEBITAC_KEY));
		request.setUniqueId("FUTURETEK" + settlement.getId());

		request.setPayeeName(properties.get(ICICI_PAYEENAME_KEY));
		request.setCurrency(properties.get(ICICI_RTGSCURRENCY_KEY));

		String jsonString = getObjectMapper().writeValueAsString(request);
		logger.info("Request String=>" + jsonString);

		IciciHybridRequest hybridRequest = hybridEncryption(properties, jsonString, settlement);

		String jsonStringHybritReq = getObjectMapper().writeValueAsString(hybridRequest);
		logger.info("Hybrid Request String=>" + jsonStringHybritReq);

		String urlString = properties.get(ICICI_COMPAYURL_KEY);

		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		// con.setRequestProperty("Accept", "*/*");
		// con.setRequestProperty("content-length", "684");
		con.setRequestProperty("apikey", apiKey);
		con.setRequestProperty("x-priority", properties.get(ICICI_CPAYXPRIORITYRTGS_KEY));

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
				IciciCompositPayRTGSResponse r = getObjectMapper().readValue(responseString,
						IciciCompositPayRTGSResponse.class);
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

	public static IciciCompositPayNEFTResponse neftPay(Map<String, String> properties,
			NodalMerchantSettlement settlement) throws Exception {
		IciciCompositPayNEFTRequest request = new IciciCompositPayNEFTRequest();

		String txnType = "RGS";
		String apiKey = properties.get(ICICI_COMPAY_API_KEY);

		if (settlement.getIfscCode() == null || settlement.getIfscCode().equals("")) {
			settlement.setIfscCode(properties.get(ICICI_IFSC_KEY));
		}
		String ifsc = settlement.getIfscCode();
		if (settlement.getIfscCode() != null && !settlement.getIfscCode().equals("")
				&& settlement.getIfscCode().toUpperCase().startsWith("ICIC")) {
			String ifsc2 = settlement.getIfscCode().toLowerCase().replace("icic", "");
			try {
				long parseNumeric = Long.valueOf(ifsc2);
				txnType = "TPA";
				ifsc = "ICIC0000011";
			} catch (Exception e) {
				logger.info("ifsc code is not numeric, " + ifsc);
			}
		}
		logger.info("txntype=>" + txnType + " for id:" + request.getUrn());
		request.setAggrId(properties.get(ICICI_AGGID_KEY));
		request.setAggrName(properties.get(ICICI_AGGNAME_KEY));
		request.setAmount(settlement.getSettlementAmount().toString());
		request.setBeneAccNo(settlement.getAccountNo());

		request.setBeneIFSC(ifsc);

		request.setBeneName(settlement.getAccountName());
		request.setCrpId(properties.get(ICICI_CORPID_KEY));
		request.setCrpUsr(properties.get(ICICI_USERID_KEY));
		request.setMobile(properties.get(ICICI_REMITTERMOBNO_KEY));
		request.setNarration1(settlement.getRemarks());
		request.setNarration2("");
		request.setSenderAcctNo(properties.get(ICICI_DEBITAC_KEY));
		request.setTranRefNo("FUTURETEKCOMPAY" + settlement.getId());
		request.setTxnType(txnType);

		request.setUrn(properties.get(ICICI_URN_KEY));

		String jsonString = getObjectMapper().writeValueAsString(request);
		logger.info("Request String=>" + jsonString);

		IciciHybridRequest hybridRequest = hybridEncryption(properties, jsonString, settlement);

		String jsonStringHybritReq = getObjectMapper().writeValueAsString(hybridRequest);
		logger.info("Hybrid Request String=>" + jsonStringHybritReq);

		String urlString = properties.get(ICICI_COMPAYURL_KEY);

		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		// con.setRequestProperty("Accept", "*/*");
		// con.setRequestProperty("content-length", "684");
		con.setRequestProperty("apikey", apiKey);
		con.setRequestProperty("x-priority", properties.get(ICICI_CPAYXPRIORITYNEFT_KEY));
		// con.setRequestProperty("host", "api.icicibank.com:8443");

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
				IciciCompositPayNEFTResponse r = getObjectMapper().readValue(decodedData,
						IciciCompositPayNEFTResponse.class);
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

	public static IciciCompositPayIMPSResponse impsPayR(Map<String, String> properties, Merchant merchant,
			NodalMerchantSettlement settlement) throws Exception {
		IciciCompositPayIMPSRequest request = new IciciCompositPayIMPSRequest();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

		request.setLocalTxnDtTime(sdf.format(date));
		request.setBeneAccNo(settlement.getAccountNo());
		request.setBeneIFSC(settlement.getIfscCode());
		request.setAmount(settlement.getSettlementAmount().toPlainString());
		request.setTranRefNo("FUTURETEKCOMPAY" + String.valueOf(settlement.getId()));
		request.setPaymentRef(settlement.getRemarks());
		request.setSenderName(merchant.getMerchantName());
		request.setMobile(properties.get(ICICI_REMITTERMOBNO_KEY));
		request.setRetailerCode("rcode");
		request.setPassCode(properties.get(ICICI_IMPSPASSCODE_KEY));
		request.setBcID(properties.get(ICICI_BCID_KEY));
		request.setCrpId(properties.get(ICICI_CORPID_KEY));
		request.setCrpUsr(properties.get(ICICI_USERID_KEY));

		String apiKey = properties.get(ICICI_API_KEY);

		String jsonString = getObjectMapper().writeValueAsString(request);
		logger.info("Request String=>" + jsonString);

		IciciHybridRequest hybridRequest = hybridEncryption(properties, jsonString, settlement);

		String jsonStringHybritReq = getObjectMapper().writeValueAsString(hybridRequest);
		logger.info("Hybrid Request String=>" + jsonStringHybritReq);

		String urlString = properties.get(ICICI_COMPAYURL_KEY);

		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		// con.setRequestProperty("Accept", "*/*");
		// con.setRequestProperty("content-length", "684");
		con.setRequestProperty("apikey", apiKey);
		con.setRequestProperty("x-priority", properties.get(ICICI_CPAYXPRIORITYIMPS_KEY));

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
				IciciCompositPayIMPSResponse r = getObjectMapper().readValue(responseString,
						IciciCompositPayIMPSResponse.class);
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

	public static IciciCompositPayIMPSResponse impsPay(Map<String, String> properties,
			NodalMerchantSettlement settlement) throws Exception {
		IciciCompositPayIMPSRequest request = new IciciCompositPayIMPSRequest();

		String iciciDupReqKey = properties.get("ICICI_DUPLICATE_REQUEST_KEY");
//		if ( iciciDupReqKey != null && !iciciDupReqKey.trim().equals("")) {
//			iciciDupReqKey = "_"+iciciDupReqKey;
//		} else 
		if (iciciDupReqKey == null || iciciDupReqKey.trim().equals("")) {
			iciciDupReqKey = "";
		} else {
			iciciDupReqKey = iciciDupReqKey.trim();
		}
		// Calendar nowAsiaIndia =
		// Calendar.getInstance(TimeZone.getTimeZone("Asia/Kolkata"));
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

		request.setLocalTxnDtTime(sdf.format(date));
		request.setBeneAccNo(settlement.getAccountNo());
		request.setBeneIFSC(settlement.getIfscCode());
		request.setAmount(settlement.getSettlementAmount().toPlainString());
		request.setTranRefNo("FUTURETEKCOMPAY" + String.valueOf(settlement.getId()+iciciDupReqKey));
//		FUTURETEKPANY

		String vpa = settlement.getVpa().toLowerCase();
		vpa = vpa.replace("@icici", "").replace("getepay.", "");
		request.setPaymentRef(settlement.getSettlementPart() + " " + vpa);

		request.setSenderName(settlement.getAccountName());
		request.setMobile(properties.get(ICICI_REMITTERMOBNO_KEY));
		request.setRetailerCode("rcode");
		request.setPassCode(properties.get(ICICI_IMPSPASSCODE_KEY));
		request.setBcID(properties.get(ICICI_BCID_KEY));
		
		// online property
		request.setCrpId(properties.get(ICICI_CORPID_KEY));
		request.setCrpUsr(properties.get(ICICI_USERID_KEY));

		// offline property
		request.setCrpId(properties.get(OFFLINE_ICICI_CORPID_KEY));
		request.setCrpUsr(properties.get(OFFLINE_ICICI_USERID_KEY));
		request.setUrn(properties.get(OFFLINE_ICICI_URN_KEY));
		request.setAggrName(properties.get(ICICI_AGGNAME_KEY));
		
		String apiKey = properties.get(ICICI_COMPAY_API_KEY);

		String jsonString = getObjectMapper().writeValueAsString(request);
		logger.info("Request String=>" + jsonString);
		logger.info("Request String=>" + jsonString);
		IciciHybridRequest hybridRequest = hybridEncryption(properties, jsonString, settlement);

		String jsonStringHybritReq = getObjectMapper().writeValueAsString(hybridRequest);
		logger.info("Hybrid Request String=>" + jsonStringHybritReq);
		logger.info("Hybrid Request String=>" + jsonStringHybritReq);
		String urlString = properties.get(ICICI_COMPAYURL_KEY);

		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		// con.setRequestProperty("Accept", "*/*");
		// con.setRequestProperty("content-length", "684");
		con.setRequestProperty("apikey", apiKey);
		con.setRequestProperty("x-priority", properties.get(ICICI_CPAYXPRIORITYIMPS_KEY));

		con.setDoOutput(true);

		for (Map.Entry<String, List<String>> entries : con.getRequestProperties().entrySet()) {

			String values = "";
			for (String value : entries.getValue()) {
				values += value + ",";
			}
			logger.info("Headers=>" + entries.getKey() + " - " + values);
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
			logger.info("response=>" + responseString);
			IciciHybridRequest hybridResponse = getObjectMapper().readValue(responseString, IciciHybridRequest.class);

			if (hybridResponse != null) {
				String decodedData = hybridDeryption(properties, hybridResponse);
				logger.info("Decoded Data=>" + decodedData);
				logger.info("Decoded Data=>" + decodedData);
				IciciCompositPayIMPSResponse r = getObjectMapper().readValue(decodedData,
						IciciCompositPayIMPSResponse.class);
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

	public static IciciCompositPayRTGSResponse rtgsPayout(Map<String, String> properties, PayoutTransaction transaction)
			throws Exception {
		IciciCompositPayRTGSRequest request = new IciciCompositPayRTGSRequest();

		String txnType = "RGS";
		String apiKey = properties.get(ICICI_API_KEY);

		if (transaction.getIfscCode() == null || transaction.getIfscCode().equals("")) {
			transaction.setIfscCode(properties.get(ICICI_IFSC_KEY));
		}
		if (transaction.getIfscCode() != null && !transaction.getIfscCode().equals("")
				&& transaction.getIfscCode().toUpperCase().startsWith("ICIC")) {
			txnType = "TPA";
		}

		request.setAggrId(properties.get(ICICI_AGGID_KEY));
		request.setAggrName(properties.get(ICICI_AGGNAME_KEY));
		request.setAmount(String.valueOf(transaction.getAmount()));
		request.setUrn(properties.get(ICICI_URN_KEY));
		// request.setTxnType(txnType);
		request.setTxnType("RRR");
		request.setCorpId(properties.get(ICICI_CORPID_KEY));
		request.setUserId(properties.get(ICICI_USERID_KEY));

		String vpa = transaction.getVpa().toLowerCase();
		vpa = vpa.replace("@icici", "").replace("getepay.", "");

		request.setCreditAcc(transaction.getAccountNumber());
		request.setIfsc(transaction.getIfscCode());
		request.setRemarks("PAYOUT " + vpa);

		request.setDebitAcc(properties.get(ICICI_DEBITAC_KEY));
		request.setUniqueId("FUTURETEKPAYOUT" + transaction.getId());

		request.setPayeeName(properties.get(ICICI_PAYEENAME_KEY));
		request.setCurrency(properties.get(ICICI_RTGSCURRENCY_KEY));

		String jsonString = getObjectMapper().writeValueAsString(request);
		logger.info("Request String=>" + jsonString);
		NodalMerchantSettlement nms = new NodalMerchantSettlement();
		nms.setId(transaction.getId());
		IciciHybridRequest hybridRequest = hybridEncryption(properties, jsonString, nms);

		String jsonStringHybritReq = getObjectMapper().writeValueAsString(hybridRequest);
		logger.info("Hybrid Request String=>" + jsonStringHybritReq);

		String urlString = properties.get(ICICI_COMPAYURL_KEY);

		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		// con.setRequestProperty("Accept", "*/*");
		// con.setRequestProperty("content-length", "684");
		con.setRequestProperty("apikey", apiKey);
		con.setRequestProperty("x-priority", properties.get(ICICI_CPAYXPRIORITYRTGS_KEY));

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
				IciciCompositPayRTGSResponse r = getObjectMapper().readValue(responseString,
						IciciCompositPayRTGSResponse.class);
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

	public static IciciCompositPayNEFTResponse neftPayout(Map<String, String> properties, PayoutTransaction transaction)
			throws Exception {
		IciciCompositPayNEFTRequest request = new IciciCompositPayNEFTRequest();

		String txnType = "RGS";
		String apiKey = properties.get(ICICI_COMPAY_API_KEY);

		if (transaction.getIfscCode() == null || transaction.getIfscCode().equals("")) {
			transaction.setIfscCode(properties.get(ICICI_IFSC_KEY));
		}
		String ifsc = transaction.getIfscCode();
		if (transaction.getIfscCode() != null && !transaction.getIfscCode().equals("")
				&& transaction.getIfscCode().toUpperCase().startsWith("ICIC")) {
			txnType = "TPA";
			ifsc = "ICIC0000011";
		}

		String vpa = transaction.getVpa().toLowerCase();
		vpa = vpa.replace("@icici", "").replace("getepay.", "");

		request.setAggrId(properties.get(ICICI_AGGID_KEY));
		request.setAggrName(properties.get(ICICI_AGGNAME_KEY));
		request.setAmount(String.valueOf(transaction.getAmount()));
		request.setBeneAccNo(transaction.getAccountNumber());

		request.setBeneIFSC(ifsc);

		request.setBeneName(transaction.getAccountName());
		request.setCrpId(properties.get(ICICI_CORPID_KEY));
		request.setCrpUsr(properties.get(ICICI_USERID_KEY));
		request.setMobile(properties.get(ICICI_REMITTERMOBNO_KEY));
		request.setNarration1("PAYOUT " + vpa);
		request.setNarration2("");
		request.setSenderAcctNo(properties.get(ICICI_DEBITAC_KEY));
		request.setTranRefNo("FUTURETEKPAYOUT" + transaction.getId());
		request.setTxnType(txnType);

		request.setUrn(properties.get(ICICI_URN_KEY));

		String jsonString = getObjectMapper().writeValueAsString(request);
		logger.info("Request String=>" + jsonString);

		NodalMerchantSettlement nms = new NodalMerchantSettlement();
		nms.setId(transaction.getId());
		IciciHybridRequest hybridRequest = hybridEncryption(properties, jsonString, nms);

		String jsonStringHybritReq = getObjectMapper().writeValueAsString(hybridRequest);
		logger.info("Hybrid Request String=>" + jsonStringHybritReq);

		String urlString = properties.get(ICICI_COMPAYURL_KEY);

		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		// con.setRequestProperty("Accept", "*/*");
		// con.setRequestProperty("content-length", "684");
		con.setRequestProperty("apikey", apiKey);
		con.setRequestProperty("x-priority", properties.get(ICICI_CPAYXPRIORITYNEFT_KEY));
		// con.setRequestProperty("host", "api.icicibank.com:8443");

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
				IciciCompositPayNEFTResponse r = getObjectMapper().readValue(decodedData,
						IciciCompositPayNEFTResponse.class);
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

	public static PayoutTransaction payout(PayoutTransaction transaction, Merchant merchant,
			Map<String, String> properties) throws Exception {
		String mode = transaction.getMode();
		if (mode.equals("IMPS")) {
			IciciCompositPayIMPSResponse response = impsPayout(properties, transaction, merchant);
			transaction.setActCode(response.getActCode());
			transaction.setActCodeDesc(response.getActCodeDesc());
			transaction.setApiStatus(response.getStatus());
			if (response.getStatus() != null && response.getStatus().equalsIgnoreCase("true")
					&& response.getActCode().equals("0")) {
				transaction.setStatus(5);
			} else {
				transaction.setStatus(6);
			}
		} else {
			/*
			 * if(transaction.getAmount() > 200000) { IciciCompositPayRTGSResponse response
			 * = rtgsPayout(properties, transaction); transaction.set } else {
			 * IciciCompositPayNEFTResponse response = neftPayout(properties, transaction);
			 * }
			 */
		}
		return transaction;
	}

	public static IciciCompositPayIMPSResponse impsPayout(Map<String, String> properties, PayoutTransaction transaction,
			Merchant merchant) throws Exception {
		IciciCompositPayIMPSRequest request = new IciciCompositPayIMPSRequest();

		// Calendar nowAsiaIndia =
		// Calendar.getInstance(TimeZone.getTimeZone("Asia/Kolkata"));
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

		request.setLocalTxnDtTime(sdf.format(date));
		request.setBeneAccNo(transaction.getAccountNumber());
		request.setBeneIFSC(transaction.getIfscCode());
		request.setAmount(String.valueOf(transaction.getAmount()));
		request.setTranRefNo("FUTURETEKPAYOUT" + String.valueOf(transaction.getId()));
//		FUTURETEKPANY

		String vpa = transaction.getVpa().toLowerCase();
		vpa = vpa.replace("@icici", "").replace("getepay.", "");
		request.setPaymentRef("PAYOUT " + vpa);

		request.setSenderName(merchant.getMerchantName());
		request.setMobile(properties.get(ICICI_REMITTERMOBNO_KEY));
		request.setRetailerCode("rcode");
		request.setPassCode(properties.get(ICICI_IMPSPASSCODE_KEY));
		request.setBcID(properties.get(ICICI_BCID_KEY));
		request.setCrpId(properties.get(ICICI_CORPID_KEY));
		request.setCrpUsr(properties.get(ICICI_USERID_KEY));

		String apiKey = properties.get(ICICI_COMPAY_API_KEY);

		String jsonString = getObjectMapper().writeValueAsString(request);
		logger.info("Request String=>" + jsonString);

		NodalMerchantSettlement nms = new NodalMerchantSettlement();
		nms.setId(transaction.getId());

		IciciHybridRequest hybridRequest = hybridEncryption(properties, jsonString, nms);

		String jsonStringHybritReq = getObjectMapper().writeValueAsString(hybridRequest);
		logger.info("Hybrid Request String=>" + jsonStringHybritReq);

		String urlString = properties.get(ICICI_COMPAYURL_KEY);

		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		// con.setRequestProperty("Accept", "*/*");
		// con.setRequestProperty("content-length", "684");
		con.setRequestProperty("apikey", apiKey);
		con.setRequestProperty("x-priority", properties.get(ICICI_CPAYXPRIORITYIMPS_KEY));

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
				IciciCompositPayIMPSResponse r = getObjectMapper().readValue(decodedData,
						IciciCompositPayIMPSResponse.class);
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

	public static IciciCompositPayIMPSResponse PanyPay(Map<String, String> properties, PanyTransaction panyTxn)
			throws Exception {
		IciciCompositPayIMPSRequest request = new IciciCompositPayIMPSRequest();

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

		request.setLocalTxnDtTime(sdf.format(date));
		request.setBeneAccNo(panyTxn.getAccountNo());
		request.setBeneIFSC(panyTxn.getIfscCode());
		request.setAmount(panyTxn.getTxnAmount().toString());
		request.setTranRefNo("FUTURETEKPANY" + String.valueOf(panyTxn.getId()));
//		FUTURETEKPANY

		request.setPaymentRef("FUTURETEKPANY");

		request.setSenderName(panyTxn.getAccountName());
		request.setMobile(properties.get(ICICI_REMITTERMOBNO_KEY));
		request.setRetailerCode("rcode");
		request.setPassCode(properties.get(ICICI_IMPSPASSCODE_KEY));
		request.setBcID(properties.get(ICICI_BCID_KEY));
		request.setCrpId(properties.get(ICICI_CORPID_KEY));
		request.setCrpUsr(properties.get(ICICI_USERID_KEY));

		String apiKey = properties.get(ICICI_COMPAY_API_KEY);

		String jsonString = getObjectMapper().writeValueAsString(request);
		logger.info("Request String=>" + jsonString);

		NodalMerchantSettlement sett = new NodalMerchantSettlement();
		sett.setId(panyTxn.getId());
		IciciHybridRequest hybridRequest = hybridEncryption(properties, jsonString, sett);

		String jsonStringHybritReq = getObjectMapper().writeValueAsString(hybridRequest);
		logger.info("Hybrid Request String=>" + jsonStringHybritReq);

		String urlString = properties.get(ICICI_COMPAYURL_KEY);

		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("apikey", apiKey);
		con.setRequestProperty("x-priority", properties.get(ICICI_CPAYXPRIORITYIMPS_KEY));

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
				IciciCompositPayIMPSResponse r = getObjectMapper().readValue(decodedData,
						IciciCompositPayIMPSResponse.class);
				return r;
			}
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;

	}

	public static IciciCIBRegistrationResponse cibRegistration(Map<String, String> properties, String urn)
			throws Exception {
		logger.info("Inside IciciCIBRegistrationResponse method ");
		logger.info("Inside IciciCIBRegistrationResponse method ");
		IciciCIBRegistrationRequest request = new IciciCIBRegistrationRequest();

		String aggId = properties.get(ICICI_AGGID_KEY);
		String aggName = properties.get(ICICI_AGGNAME_KEY);
		// online property
//		String corpId = properties.get(ICICI_CORPID_KEY);
//		String corpUser = properties.get(ICICI_USERID_KEY);
		// offline property
		String corpId = properties.get("OFFLINE_ICICI_CORPID_KEY");
		String corpUser = properties.get("OFFLINE_ICICI_USERID_KEY");
//		String aliasId = properties.get(ICICI_ALIASID_KEY);

		String apiKey = properties.get(ICICI_API_KEY);

		// String urn = properties.get(ICICI_URN_KEY);

		request.setAggrId(aggId);
		request.setAggrName(aggName);
//		request.setAliasId(aliasId);
		request.setAliasId("");
		request.setCorpId(corpId);
		request.setUrn(urn);
		request.setUserId(corpUser);

		String jsonString = getObjectMapper().writeValueAsString(request);

		logger.info("Request String=>" + jsonString);
		logger.info("Request String=>" + jsonString);

//		String iciciClientCertificatePath = properties.get(ICICI_REGPUBLICCER_KEY);
//		byte[] encodedBytes = Base64.getEncoder().encode(RSAUtil.encrypt(jsonString, iciciClientCertificatePath));
//		String encryptedPayload = new String(encodedBytes);

		Gson gson = new Gson();
		Map<String, String> propertyMap = new HashMap<String, String>();
		propertyMap.put(ICICI_CPAYPUBLICCER_KEY, properties.get(ICICI_CPAYPUBLICCER_KEY)); // enc

		propertyMap.put(GETEPAY_ICICI_PRIVATE_KEY, properties.get(GETEPAY_ICICI_PRIVATE_KEY)); // dec
		logger.info("Hybrid request propertyMap = " + propertyMap);
		IciciHybridRequest iciciHybridRequest = IciciCompositPay.hybridEncryption(propertyMap, jsonString, "");
		String encryptedPayload = gson.toJson(iciciHybridRequest);

		logger.info("Enc Request String=>" + encryptedPayload);
		logger.info("Enc Request String=>" + encryptedPayload);
		String urlString = properties.get(ICICI_CIBREGURL_KEY);
		logger.info("urlString String=>" + urlString);

		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
//		con.setRequestProperty("Content-Type", "text/plain");
//		con.setRequestProperty("Accept", "*/*");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("content-length", "684");
		con.setRequestProperty("apikey", apiKey);

		con.setDoOutput(true);

		for (Map.Entry<String, List<String>> entries : con.getRequestProperties().entrySet()) {

			String values = "";
			for (String value : entries.getValue()) {
				values += value + ",";
			}
			logger.info("Headers=>" + entries.getKey() + " - " + values);
			logger.info("Headers=>" + entries.getKey() + " - " + values);
		}

		try (OutputStream os = con.getOutputStream()) {
			byte[] input = encryptedPayload.getBytes("utf-8");
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
			logger.info("response=>" + responseString);

//			String getepayClientCertificatePath = properties.get(Util.GETEPAY_ICICI_PRIVATE_KEY);
//			String decryptedResponse = new String(RSAUtil.decrypt(responseString, getepayClientCertificatePath));

			IciciHybridRequest iciciHybridResponse = gson.fromJson(responseString, IciciHybridRequest.class);
			String decryptedResponse = IciciCompositPay.hybridDeryption(propertyMap, iciciHybridResponse);

			logger.info("dec response=>" + decryptedResponse);
			logger.info("dec response=>" + decryptedResponse);
			IciciCIBRegistrationResponse r = getObjectMapper().readValue(decryptedResponse,
					IciciCIBRegistrationResponse.class);
			logger.info("IciciCIBRegistrationResponse =>" + r);
			return r;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;

	}

	public static IciciBeneficiaryRegistrationResponse beneficiaryRegistration(Merchant merchant,
			Map<String, String> properties) throws Exception {
		IciciBeneficiaryRegistrationRequest request = new IciciBeneficiaryRegistrationRequest();

		String corpId = properties.get(ICICI_CORPID_KEY);
		String corpUser = properties.get(ICICI_USERID_KEY);
		String aggId = properties.get(ICICI_AGGID_KEY);
		String apiKey = properties.get(ICICI_API_KEY);
		String urn = properties.get(ICICI_URN_KEY);

		String payeeType = "O";
		if (merchant.getIfscCode() != null && merchant.getIfscCode().toLowerCase().startsWith("icic")) {
			payeeType = "W";
		}

		request.setAggrId(aggId);
		request.setBnfAccNo(merchant.getAccountNumber());
		request.setBnfName(merchant.getMerchantName());
		request.setBnfNickName(String.valueOf(merchant.getMid()) + String.valueOf(System.currentTimeMillis()));
		request.setCrpId(corpId);
		request.setCrpUsr(corpUser);
		request.setIfsc(merchant.getIfscCode());
		request.setPayeeType(payeeType);
		request.setUrn(urn);

		String jsonString = getObjectMapper().writeValueAsString(request);

		logger.info("Request String=>" + jsonString);

		String iciciClientCertificatePath = properties.get(ICICI_REGPUBLICCER_KEY);

		byte[] encodedBytes = Base64.getEncoder().encode(RSAUtil.encrypt(jsonString, iciciClientCertificatePath));
		String encryptedPayload = new String(encodedBytes);
		logger.info("Request String Enc=>" + encryptedPayload);
		String urlString = properties.get(ICICI_BENEREGURL_KEY);

		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "text/plain");
		con.setRequestProperty("Accept", "*/*");
		con.setRequestProperty("content-length", "684");
		con.setRequestProperty("apikey", apiKey);
		con.setRequestProperty("x-priority", properties.get(ICICI_CPAYXPRIORITYIMPS_KEY));

		con.setDoOutput(true);
		try (OutputStream os = con.getOutputStream()) {
			byte[] input = encryptedPayload.getBytes("utf-8");
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
			String getepayClientCertificatePath = properties.get(ComponentUtils.GETEPAY_ICICI_PRIVATE_KEY);
			String decryptedResponse = new String(RSAUtil.decrypt(responseString, getepayClientCertificatePath));
			logger.info("dec ben reg response=>" + decryptedResponse);
			IciciBeneficiaryRegistrationResponse r = getObjectMapper().readValue(decryptedResponse,
					IciciBeneficiaryRegistrationResponse.class);
			return r;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
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

	public static IciciImpsRequeryResponse iciciImpsRequeryRequestR(Merchant merchant, Map<String, String> properties,
			NodalMerchantSettlement settlement) throws Exception {

		IciciImpsRequeryRequest request = new IciciImpsRequeryRequest();
		request.setBcID(properties.get(ICICI_BCID_KEY));
		request.setPassCode(properties.get(ICICI_IMPSPASSCODE_KEY));
		request.setTransRefNo("FUTURETEK" + String.valueOf(settlement.getId()));

		String apiKey = properties.get(ICICI_API_KEY);

		String jsonString = getObjectMapper().writeValueAsString(request);
		logger.info("Request String=>" + jsonString);

		IciciHybridRequest hybridRequest = hybridEncryption(properties, jsonString, settlement);

		String jsonStringHybritReq = getObjectMapper().writeValueAsString(hybridRequest);
		logger.info("Hybrid Request String=>" + jsonStringHybritReq);

		String urlString = properties.get(ICICI_IMPSTXNSTATUSURL_KEY);

		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("apikey", apiKey);
		con.setDoOutput(true);

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
				IciciImpsRequeryResponse r = getObjectMapper().readValue(responseString,
						IciciImpsRequeryResponse.class);
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

	public static IciciImpsRequeryResponse iciciImpsRequeryRequest(Map<String, String> properties,
			NodalMerchantSettlement settlement) throws Exception {

		IciciImpsRequeryRequest request = new IciciImpsRequeryRequest();
		request.setBcID(properties.get(ICICI_BCID_KEY));
		request.setPassCode(properties.get(ICICI_IMPSPASSCODE_KEY));
		// request.setTransRefNo(settlement.getUtr());
		request.setTransRefNo("FUTURETEKCOMPAY" + String.valueOf(settlement.getId()));

		String apiKey = properties.get(ICICI_API_KEY);

		String jsonString = getObjectMapper().writeValueAsString(request);
		logger.info("Request String=>" + jsonString);

		IciciHybridRequest hybridRequest = hybridEncryption(properties, jsonString, settlement);

		String jsonStringHybritReq = getObjectMapper().writeValueAsString(hybridRequest);
		logger.info("Hybrid Request String=>" + jsonStringHybritReq);

		String urlString = properties.get(ICICI_IMPSTXNSTATUSURL_KEY);

		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("apikey", apiKey);
		con.setDoOutput(true);

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
				IciciImpsRequeryResponseWrapper r = getObjectMapper().readValue(decodedData,
						IciciImpsRequeryResponseWrapper.class);
				return r.getImpsResponse();
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

	public static IciciNeftRequeryResponse iciciNeftRequeryAsymmetricRequest(Map<String, String> properties,
			NodalMerchantSettlement settlement) throws Exception {
		IciciNeftRequeryRequest request = new IciciNeftRequeryRequest();

		request.setUserId(properties.get(ICICI_USERID_KEY));
		request.setAggrId(properties.get(ICICI_AGGID_KEY));
		request.setCorpId(properties.get(ICICI_CORPID_KEY));
		// request.setUniqueId("FUTURETEK"+ String.valueOf(settlement.getId()));
		request.setUrn(properties.get(ICICI_URN_KEY));
		request.setUniqueId(settlement.getUtr());

		String apiKey = properties.get(ICICI_API_KEY);
		// apiKey ="1a67b79647bf4cacbf6a7f0abafb6f4c";

		String jsonString = getObjectMapper().writeValueAsString(request);
		logger.info("Request String=>" + jsonString);

		String iciciClientCertificatePath = properties.get(ICICI_REGPUBLICCER_KEY);
		// String iciciClientCertificatePath = properties.get(ICICI_REGPUBLICCER_KEY);
		// nnnn

		byte[] encodedBytes = Base64.getEncoder().encode(RSAUtil.encrypt(jsonString, iciciClientCertificatePath));

		String encryptedPayload = new String(encodedBytes);

		logger.info("Enc Request String=>" + encryptedPayload);

		String urlString = properties.get(ICICI_NEFTTXNCREDITSTATUSURL_KEY);

		logger.info("Url String=>" + urlString);

		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "text/plain");
		// con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "*/*");
		con.setRequestProperty("content-length", "684");
		// con.setRequestProperty("x-forwarded-for", "13.234.84.113");
		// con.setRequestProperty("host", "api.icicibank.com:8443");
		con.setRequestProperty("apikey", apiKey);

		con.setDoOutput(true);

		for (Map.Entry<String, List<String>> entries : con.getRequestProperties().entrySet()) {

			String values = "";
			for (String value : entries.getValue()) {
				values += value + ",";
			}
			logger.info("Headers=>" + entries.getKey() + " - " + values);
		}

		try (OutputStream os = con.getOutputStream()) {
			byte[] input = encryptedPayload.getBytes("utf-8");
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
			String getepayClientCertificatePath = properties.get(ComponentUtils.GETEPAY_ICICI_PRIVATE_KEY);
			String decryptedResponse = new String(RSAUtil.decrypt(responseString, getepayClientCertificatePath));
			logger.info("dec response=>" + decryptedResponse);
			IciciNeftRequeryResponse r = getObjectMapper().readValue(decryptedResponse, IciciNeftRequeryResponse.class);
			return r;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public static IciciCollectResponse iciciUpiCollect(Map<String, String> properties, TransactionLog transaction,
			Merchant merchant, MerchantSetting setting, String payerVpa) throws Exception {
		String collectUrl = properties.get(ICICI_COLLECTURL_KEY);
		String merchantName = properties.get(ICICI_COLLECTMERCHANT_KEY);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

		IciciCollectRequest request = new IciciCollectRequest();
		request.setPayerVa(payerVpa);
		BigDecimal commission = BigDecimal.ZERO;
		if (transaction.getCommision() != null && transaction.getCommisionType() != null
				&& transaction.getCommisionType().equalsIgnoreCase("Excl")) {
			commission = transaction.getCommision();
		}

		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.MINUTE, 10);
		logger.info("Icici collect amount =>" + commission);
		request.setAmount(transaction.getAmt().add(commission).setScale(2, RoundingMode.HALF_UP).toString());

		BigDecimal totalConvienceCharge = BigDecimal.ZERO;
		if (transaction.getServiceChargeType() != null && transaction.getServiceChargeType().equalsIgnoreCase("Excl")) {

			logger.info("Inside the Transaction Service Type Charges for UPI Collect");

			totalConvienceCharge = totalConvienceCharge.add(new BigDecimal(transaction.getTotalServiceCharge()));

			logger.info("total Convience Charges" + totalConvienceCharge);
		}

		request.setAmount(new BigDecimal(request.getAmount()).add(totalConvienceCharge)
				.setScale(2, RoundingMode.HALF_UP).toString());

		logger.info("Total ICICI UPI Collect amount=======>" + request.getAmount());
		request.setNote("UPI Collect " + transaction.getTransactionId());
		request.setCollectByDate(sdf.format(cal.getTime()));
		request.setMerchantId(setting.getMloginId());
		request.setMerchantName(merchantName);
		request.setSubMerchantId(setting.getmPassword());
		request.setSubMerchantName(merchant.getMerchantName());
		request.setTerminalId(setting.getSetting2());
		request.setMerchantTranId(String.valueOf(transaction.getTransactionId()));
		request.setBillNumber(String.valueOf(transaction.getTransactionId()));
		request.setValidatePayerAccFlag("N");
		request.setPayerAccount("");
		request.setPayerIFSC("");

		String jsonString = getObjectMapper().writeValueAsString(request);
		logger.info("Request String=>" + jsonString);

		IciciHybridRequest hybridRequest = hybridEncryption(properties, jsonString, transaction);

		String jsonStringHybritReq = getObjectMapper().writeValueAsString(hybridRequest);
		logger.info("Hybrid Request String=>" + jsonStringHybritReq);

		String urlString = collectUrl;

		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		// con.setRequestProperty("apikey", "5zfG1JGLvoxkIG4AJ8n9XHo7mGTFADmP");
		con.setDoOutput(true);

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
				String decodedData = hybridDeryptionCollect(properties, hybridResponse);
				logger.info("Decoded Data=>" + decodedData);
				IciciCollectResponse r = getObjectMapper().readValue(decodedData, IciciCollectResponse.class);
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
	
	
	public static IciciCollectResponse iciciUpiCollectupdated(TransactionEssentials txnEssential, Map<String, String> properties, TransactionLog transaction,
			Merchant merchant, MerchantSetting setting, String payerVpa,DmoOnboarding dmoOnboarding) throws Exception {
		
		logger.info("Inside Upi Collect Updated Call=====================>");
		String collectUrl = properties.get(UPDATED_ICICI_COLLECTURL_KEY);
		String merchantName = properties.get(ICICI_COLLECTMERCHANT_KEY);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

		IciciCollectRequest request = new IciciCollectRequest();
		request.setPayerVa(payerVpa);
		BigDecimal commission = BigDecimal.ZERO;
		
		if (transaction.getCommision() != null && transaction.getCommisionType() != null
				&& transaction.getCommisionType().equalsIgnoreCase("Excl")) {
			commission = transaction.getCommision();
		}

		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.MINUTE, 10);
		logger.info("Icici collect amount =>" + commission);
		request.setAmount(transaction.getAmt().add(commission).setScale(2, RoundingMode.HALF_UP).toString());

		BigDecimal totalConvienceCharge = BigDecimal.ZERO;
		if (transaction.getServiceChargeType() != null && transaction.getServiceChargeType().equalsIgnoreCase("Excl")) {

			logger.info("Inside the Transaction Service Type Charges for UPI Collect");

			totalConvienceCharge = totalConvienceCharge.add(new BigDecimal(transaction.getTotalServiceCharge()));

			logger.info("total Convience Charges" + totalConvienceCharge);
		}

		
		
		
		request.setAmount(new BigDecimal(request.getAmount()).add(totalConvienceCharge)
				.setScale(2, RoundingMode.HALF_UP).toString());

		logger.info("Total ICICI UPI Collect amount=======>" + request.getAmount());
		request.setNote("UPI Collect " + transaction.getTransactionId());
		request.setCollectByDate(sdf.format(cal.getTime()));
		request.setMerchantId(String.valueOf(dmoOnboarding.getEazypayMerchantID()));
		request.setMerchantName(merchantName);
		request.setSubMerchantId(String.valueOf(dmoOnboarding.getEazypayMerchantID()));
		request.setSubMerchantName(merchant.getMerchantName());
		request.setTerminalId(setting.getSetting2());
		request.setMerchantTranId(UPDATED_ICICI_COLLECT_GETcollect+String.valueOf(transaction.getTransactionId()));
		request.setBillNumber(String.valueOf(transaction.getTransactionId()));
		
		if (txnEssential != null
				&& txnEssential.getUdf57() != null
				&& txnEssential.getUdf57().contains("TPV")) {

			String[] values = txnEssential.getUdf57().split("\\|");

			request.setValidatePayerAccFlag("Y");
			request.setPayerAccount(values[0]);
			request.setPayerIFSC(values[1]);
		} else {
			request.setValidatePayerAccFlag("N");
			request.setPayerAccount("");
			request.setPayerIFSC("");
		}

		String jsonString = getObjectMapper().writeValueAsString(request);
		logger.info("ICICI Updated Collect Request String=>" + jsonString);

		IciciHybridRequest hybridRequest = hybridEncryption(properties, jsonString, transaction);

		String jsonStringHybritReq = getObjectMapper().writeValueAsString(hybridRequest);
		logger.info("ICICI Updated Collect Hybrid Request String=>" + jsonStringHybritReq);

		String urlString = collectUrl;
		
		urlString=urlString.replaceAll("#merchant_id",String.valueOf(dmoOnboarding.getParentMerchantID()));

		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		// con.setRequestProperty("apikey", "5zfG1JGLvoxkIG4AJ8n9XHo7mGTFADmP");
		con.setDoOutput(true);

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
			logger.info("ICICI Updated Collect Hybrid Response String=>" + responseString);

			IciciHybridRequest hybridResponse = getObjectMapper().readValue(responseString, IciciHybridRequest.class);

			if (hybridResponse != null) {
				String decodedData = hybridDeryptionCollect(properties, hybridResponse);
				logger.info("ICICI Updated Collect Decoded Data=>" + decodedData);
				IciciCollectResponse r = getObjectMapper().readValue(decodedData, IciciCollectResponse.class);
				return r;
			}

			
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public static IciciBeneficiaryRegistrationResponse beneficiaryRegistration(
			PayoutBeneficiaryTransaction payoutBeneficiaryTransaction, Map<String, String> properties)
			throws Exception {
		IciciBeneficiaryRegistrationRequest request = new IciciBeneficiaryRegistrationRequest();

		String corpId = properties.get(ICICI_CORPID_KEY);
		String corpUser = properties.get(ICICI_USERID_KEY);
		String aggId = properties.get(ICICI_AGGID_KEY);
		String apiKey = properties.get(ICICI_API_KEY);
		String urn = properties.get(ICICI_URN_KEY);

		String payeeType = "O";
		if (payoutBeneficiaryTransaction.getBeneficiaryIfscCode() != null
				&& payoutBeneficiaryTransaction.getBeneficiaryIfscCode().toLowerCase().startsWith("icic")) {
			payeeType = "W";
		}

		request.setAggrId(aggId);
		request.setBnfAccNo(payoutBeneficiaryTransaction.getBeneficiaryAccountNumber());
		request.setBnfName(payoutBeneficiaryTransaction.getBeneficiaryAccountName());
		request.setBnfNickName(
				String.valueOf(payoutBeneficiaryTransaction.getId()) + String.valueOf(System.currentTimeMillis()));
		request.setCrpId(corpId);
		request.setCrpUsr(corpUser);
		request.setIfsc(payoutBeneficiaryTransaction.getBeneficiaryIfscCode());
		request.setPayeeType(payeeType);
		request.setUrn(urn);

		String jsonString = getObjectMapper().writeValueAsString(request);

		logger.info("Request String=>" + jsonString);

		String iciciClientCertificatePath = properties.get(ICICI_REGPUBLICCER_KEY);

		byte[] encodedBytes = Base64.getEncoder().encode(RSAUtil.encrypt(jsonString, iciciClientCertificatePath));
		String encryptedPayload = new String(encodedBytes);
		logger.info("Request String Enc=>" + encryptedPayload);
		String urlString = properties.get(ICICI_BENEREGURL_KEY);

		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "text/plain");
		con.setRequestProperty("Accept", "*/*");
		con.setRequestProperty("content-length", "684");
		con.setRequestProperty("apikey", apiKey);
		con.setRequestProperty("x-priority", properties.get(ICICI_CPAYXPRIORITYIMPS_KEY));

		con.setDoOutput(true);
		try (OutputStream os = con.getOutputStream()) {
			byte[] input = encryptedPayload.getBytes("utf-8");
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
			String getepayClientCertificatePath = properties.get(ComponentUtils.GETEPAY_ICICI_PRIVATE_KEY);
			String decryptedResponse = new String(RSAUtil.decrypt(responseString, getepayClientCertificatePath));
			logger.info("dec ben reg response=>" + decryptedResponse);
			IciciBeneficiaryRegistrationResponse r = getObjectMapper().readValue(decryptedResponse,
					IciciBeneficiaryRegistrationResponse.class);
			return r;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public static IciciBeneficiaryRegistrationResponse beneficiaryRegistrationHybrid(
			PayoutBeneficiaryTransaction payoutBeneficiaryTransaction, Map<String, String> properties)
			throws Exception {
		IciciBeneficiaryRegistrationRequest request = new IciciBeneficiaryRegistrationRequest();

		String corpId = properties.get(ICICI_CORPID_KEY);
		String corpUser = properties.get(ICICI_USERID_KEY);
		String aggId = properties.get(ICICI_AGGID_KEY);
		String apiKey = properties.get(ICICI_API_KEY);
		String urn = properties.get(ICICI_URN_KEY);

		String payeeType = "O";
		if (payoutBeneficiaryTransaction.getBeneficiaryIfscCode() != null
				&& payoutBeneficiaryTransaction.getBeneficiaryIfscCode().toLowerCase().startsWith("icic")) {
			payeeType = "W";
		}

		request.setAggrId(aggId);
		request.setBnfAccNo(payoutBeneficiaryTransaction.getBeneficiaryAccountNumber());
		request.setBnfName(payoutBeneficiaryTransaction.getBeneficiaryAccountName());
		request.setBnfNickName(
				String.valueOf(payoutBeneficiaryTransaction.getId()) + String.valueOf(System.currentTimeMillis()));
		request.setCrpId(corpId);
		request.setCrpUsr(corpUser);
		request.setIfsc(payoutBeneficiaryTransaction.getBeneficiaryIfscCode());
		request.setPayeeType(payeeType);
		request.setUrn(urn);

		String jsonString = getObjectMapper().writeValueAsString(request);

		String requestId = payoutBeneficiaryTransaction.getId() + String.valueOf(System.currentTimeMillis());

		logger.info("Request String=>" + jsonString);

		IciciHybridRequest hybridRequest = hybridEncryption(properties, jsonString, requestId);

		String jsonStringHybritReq = getObjectMapper().writeValueAsString(hybridRequest);
		logger.info("Hybrid Request String=>" + jsonStringHybritReq);

		String urlString = properties.get(ICICI_BENEREGURL_KEY);

		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("apikey", apiKey);

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
				IciciBeneficiaryRegistrationResponse r = getObjectMapper().readValue(decodedData,
						IciciBeneficiaryRegistrationResponse.class);
				return r;
			}
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public static IciciHybridRequest hybridEncryption(Map<String, String> properties, String requestString,
			String requestId) throws Exception {
		String iciciPublicKeyPath = properties.get(ICICI_CPAYPUBLICCER_KEY);
		String randomNo = generateRandomNumber();
		logger.info("Random No=>" + randomNo + "::key length=>" + randomNo.getBytes().length);
		logger.info("Random No=>" + randomNo + "::key length=>" + randomNo.getBytes().length);

		byte[] rsaEncrypted = RSAUtil.encrypt(randomNo, iciciPublicKeyPath);
		String encryptedKey = Base64.getEncoder().encodeToString(rsaEncrypted);

		SecureRandom randomSecureRandom = new SecureRandom();
		byte[] iv = new byte[16];
		randomSecureRandom.nextBytes(iv);

		String base64EncodedIv = Base64.getEncoder().encodeToString(iv);

		String encodedData = ComponentUtils.encryptAES(requestString.getBytes(), iv, randomNo.getBytes());

		logger.info("Encrypted Data=>" + encodedData);

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

	public static IciciBankStatementResponse fetchAccountStatement(String fromDate, String toDate,
			Map<String, String> properties) throws Exception {
		IciciBankStatementRequest request = new IciciBankStatementRequest();
		String corpId = properties.get(ICICI_CORPID_KEY);
		String corpUser = properties.get(ICICI_USERID_KEY);
		String aggId = properties.get(ICICI_AGGID_KEY);
		String apiKey = properties.get(ICICI_API_KEY);
		String urn = properties.get(ICICI_URN_KEY);

		String accountNumber = properties.get(ICICI_NODAL_ACCOUNT_NUMBER);
		String reqId = String.valueOf(System.currentTimeMillis());

		request = new IciciBankStatementRequest();
		request.setAccountNumber(accountNumber);
		request.setAGGRID(aggId);
		request.setCORPID(corpId);
		request.setFROMDATE(fromDate);
		request.setTODATE(toDate);
		request.setUNIQUEREFERENCENumber(reqId);
		request.setUSERID(corpUser);

		String jsonString = getObjectMapper().writeValueAsString(request);

		logger.info("Json string=>" + jsonString);

		String urlString = properties.get(ICICI_NODAL_STATEMENT_API_URL);

		logger.info("urlString string=>" + urlString);

		logger.info("api Key=>" + apiKey);

		IciciHybridRequest hybridRequest = hybridEncryption(properties, jsonString, reqId);

		String jsonStringHybritReq = getObjectMapper().writeValueAsString(hybridRequest);
		logger.info("Hybrid Request String=>" + jsonStringHybritReq);

		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("apiKey", apiKey);
		con.setDoOutput(true);
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

			IciciBankStatementResponse r = getObjectMapper().readValue(responseString,
					IciciBankStatementResponse.class);
			return null;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public static IciciBeneficiaryRegistrationResponse beneficiaryRegistrationHybrid(Merchant merchant,
			Map<String, String> properties, UpiQrDetail upiQrDetail) throws Exception {
		IciciBeneficiaryRegistrationRequest request = new IciciBeneficiaryRegistrationRequest();

		String corpId = null;
		String corpUser = null;
		String aggId = null;
		String apiKey = null;
		String urn = null;

		if (upiQrDetail != null && upiQrDetail.getMerchantGenre() != null
				&& upiQrDetail.getMerchantGenre().equalsIgnoreCase("OFFLINE")) {

			corpId = properties.get(OFFLINE_ICICI_CORPID_KEY);
			corpUser = properties.get(OFFLINE_ICICI_USERID_KEY);
			aggId = properties.get(OFFLINE_ICICI_AGGID_KEY);
			apiKey = properties.get(OFFLINE_ICICI_API_KEY);
			urn = properties.get(OFFLINE_ICICI_URN_KEY);

		} else {
			corpId = properties.get(ICICI_CORPID_KEY);
			corpUser = properties.get(ICICI_USERID_KEY);
			aggId = properties.get(ICICI_AGGID_KEY);
			apiKey = properties.get(ICICI_API_KEY);
			urn = properties.get(ICICI_URN_KEY);

		}

		String payeeType = "O";
		if (merchant.getIfscCode() != null && merchant.getIfscCode().toLowerCase().startsWith("icic")) {
			String ifsc = merchant.getIfscCode().toLowerCase().replace("icic", "");
			try {
				long parseNumeric = Long.valueOf(ifsc);
				payeeType = "W";
			} catch (Exception e) {

			}
		}

		request.setAggrId(aggId);
		request.setBnfAccNo(merchant.getAccountNumber().trim());
		request.setBnfName(merchant.getMerchantName().trim());
		request.setBnfNickName(String.valueOf(merchant.getMid()) + String.valueOf(merchant.getAccountNumber()));
		request.setCrpId(corpId);
		request.setCrpUsr(corpUser);
		request.setIfsc(merchant.getIfscCode().trim());
		request.setPayeeType(payeeType);
		request.setUrn(urn);

		String jsonString = getObjectMapper().writeValueAsString(request);

		String requestId = merchant.getMid() + String.valueOf(System.currentTimeMillis());

		logger.info("Request String=>" + jsonString);

		IciciHybridRequest hybridRequest = hybridEncryption(properties, jsonString, requestId);

		String jsonStringHybritReq = getObjectMapper().writeValueAsString(hybridRequest);
		logger.info("Hybrid Request String=>" + jsonStringHybritReq);

		String urlString = properties.get(ICICI_BENEREGURL_KEY);

		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("apikey", apiKey);

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
				IciciBeneficiaryRegistrationResponse r = getObjectMapper().readValue(decodedData,
						IciciBeneficiaryRegistrationResponse.class);
				return r;
			}
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

}

/*
 * public static IciciUpiMandateResponse createUpiMandate(UpiMandate mandate,
 * Map<String, String> properties ) { IciciUpiMandateRequest request = new
 * IciciUpiMandateRequest();
 * 
 * String iciciMerchantId = properties.get(ICICI_MANDATE_MERCHANT_KEY);
 * 
 * SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:MM aa");
 * 
 * request.setMerchantId(iciciMerchantId);
 * request.setSubMerchantId(iciciMerchantId);
 * 
 * request.setMerchantName("Getepay");
 * request.setSubMerchantName(mandate.getMerchantName()); // man
 * request.setTerminalId(mandate.getMcc());
 * 
 * request.setPayerVa(mandate.getPayerVa());
 * 
 * request.setAmount(mandate.getAmount().toPlainString());
 * request.setNote(mandate.getNote());
 * 
 * 
 * 
 * request.setCollectByDate(sdf.format(mandate.getCollectByDate()));
 * request.setMerchantTranId(String.valueOf(mandate.getId()));
 * 
 * request.setBillNumber(mandate.getBillNumber());
 * 
 * request.setRequestType(mandate.getRequestType());
 * 
 * 
 * 
 * }
 */
