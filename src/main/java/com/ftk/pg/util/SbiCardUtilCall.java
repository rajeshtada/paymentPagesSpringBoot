package com.ftk.pg.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.enstage.mlehelper.beans.AES;
import com.enstage.mlehelper.beans.EncryptedRequestData;
import com.enstage.mlehelper.beans.EncryptedResponseData;
import com.enstage.mlehelper.client.Client;
import com.enstage.mlehelper.server.Server;
import com.enstage.mlehelper.util.DecryptionUtil;
import com.enstage.mlehelper.util.EncryptionUtil;
import com.enstage.mlehelper.util.JsonUtil;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.Merchant;
import com.ftk.pg.modal.MerchantSetting;
import com.ftk.pg.modal.Refund;
import com.ftk.pg.modal.TransactionEssentials;
import com.ftk.pg.modal.TransactionLog;
import com.ftk.pg.repo.TransactionEssentialsRepo;
import com.ftk.pg.requestvo.AuthorizeApiRequest;
import com.ftk.pg.requestvo.CardDetailsVo;
import com.ftk.pg.requestvo.GenerateOTPRequest;
import com.ftk.pg.requestvo.GenerateOTPResponse;
import com.ftk.pg.requestvo.Initiate2Request;
import com.ftk.pg.requestvo.MobilePhone;
import com.ftk.pg.requestvo.PRqFrqFinalRequest;
import com.ftk.pg.requestvo.ParqAuthenticationRequest;
import com.ftk.pg.requestvo.PvrqVersioningRequest;
import com.ftk.pg.requestvo.ResendOTPRequest;
import com.ftk.pg.requestvo.RupayCheckbinRequest;
import com.ftk.pg.requestvo.RupayEncryptedData;
import com.ftk.pg.requestvo.SbiSaleAuthorizationRequest;
import com.ftk.pg.requestvo.TokenHeader;
import com.ftk.pg.requestvo.VerifyOTPRequest;
import com.ftk.pg.responsevo.AuthorizeApiResponse;
import com.ftk.pg.responsevo.Initiate2Response;
import com.ftk.pg.responsevo.PRqFrqFinalResponse;
import com.ftk.pg.responsevo.ParqAuthenticationResponse;
import com.ftk.pg.responsevo.PvrqVersioningResponse;
import com.ftk.pg.responsevo.RupayCheckbinResponse;
import com.ftk.pg.responsevo.VerifyOTPResponse;
import com.ftk.pg.vo.sbiNb.SBITokenEncryptedData;
import com.ftk.pg.vo.sbiNb.SBITokenRequest;
import com.ftk.pg.vo.sbiNb.SBITokenResponse;
import com.ftk.pg.vo.sbiNb.SbiRefundVoidRequest;
import com.ftk.pg.vo.sbiNb.SbiRefundVoidResponse;
import com.ftk.pg.vo.sbiNb.SbiRequestHeader;
import com.ftk.pg.vo.sbiNb.SbiSaleAuthorizationResponse;
import com.google.gson.Gson;

public class SbiCardUtilCall {

	public static final int GCM_TAG_LENGTH = 16;
	private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
	private static final String DIGITS = "0123456789";
	private static final String ALL_CHARACTERS = UPPERCASE_LETTERS + LOWERCASE_LETTERS + DIGITS;

	static Logger logger = LogManager.getLogger(SbiCardUtilCall.class);

	@Autowired
	static TransactionEssentialsRepo transactionEssentialsRepo;

	public static PvrqVersioningResponse pvrq(CardDetailsVo usercardDetails, SbiRequestHeader header,
			MerchantSetting setting, Merchant merchant, TransactionLog log, Map<String, String> propMap) {

		logger.info("Inside Sbi pvrq========================================>");
		String signedResponse = null;
		Gson gson = new Gson();

		String threeDSRequestorMethodNotificationRespURL = propMap.get(SBICardsUtils.SBI_CARD_RETURN_URL_V2);
		threeDSRequestorMethodNotificationRespURL = threeDSRequestorMethodNotificationRespURL.replace("#ru",
				String.valueOf(log.getTransactionId()));

		logger.info("SBI Cards Return Url=========================>" + threeDSRequestorMethodNotificationRespURL);

		try {
			AES aes = AES.init();
			PvrqVersioningRequest request = new PvrqVersioningRequest();
			request.setMessageType(propMap.get(SBICardsUtils.pVrq_MESSAGE_TYPE));
			request.setDeviceChannel(propMap.get(SBICardsUtils.SBI_DEVICE_CHANNEL));
			request.setMerchantTransID(String.valueOf(log.getTransactionId()));
			request.setAcctNumber(usercardDetails.getCardNo());

			if (usercardDetails.getCardProvider().equalsIgnoreCase("VISA")) {
				request.setAcquirerBIN(propMap.get(SBICardsUtils.SBI_ACQUIRER_BIN_VISA));
			} else if (usercardDetails.getCardProvider().equalsIgnoreCase("MASTER")) {
				request.setAcquirerBIN(propMap.get(SBICardsUtils.SBI_ACQUIRER_BIN_MASTER));
			}
			request.setAcquirerID(propMap.get(SBICardsUtils.SBI_ACQUIRERID));

			request.setThreeDSRequestorMethodNotificationRespURL(threeDSRequestorMethodNotificationRespURL);
			request.setP_messageVersion(propMap.get(SBICardsUtils.SBI_p_VERSIONING));

			logger.info("SBI PVRq json Request  ==========>" + request + " For transaction_id===================>"
					+ log.getTransactionId());
			String encrequestjson = encrypt(gson.toJson(request), aes);

			logger.info("SBI PVRq Encrypted Request==============>" + encrequestjson
					+ " For transaction_id=====================>" + log.getTransactionId());

			String pvrqurl = propMap.get(SBICardsUtils.SBI_pVrq_VERSIONING_REQUEST);
			pvrqurl = pvrqurl.replace("#acquirerId", request.getAcquirerID());
			pvrqurl = pvrqurl.replace("#merchantTxnId", request.getMerchantTransID());

			logger.info("SBI pvrqurl===========>" + pvrqurl);
			String response = SBICardsUtils.postapi(pvrqurl, encrequestjson, header);

			logger.info("SBI PVRq Encrytped Response==============>" + response
					+ " For transaction_id===========================> " + log.getTransactionId());

			EncryptedResponseData encryptedResponse = gson.fromJson(response, EncryptedResponseData.class);

			String decryptResponse = decypt(encryptedResponse, aes);

			logger.info("SBI PVRq Encrytped Response==============>" + decryptResponse
					+ " For transaction_id===========================> " + log.getTransactionId());

			PvrqVersioningResponse pvrqVersioningResponse = gson.fromJson(decryptResponse,
					PvrqVersioningResponse.class);

			return pvrqVersioningResponse;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public static ParqAuthenticationResponse pareq(CardDetailsVo usercardDetails, int amount, SbiRequestHeader header,
			MerchantSetting setting, Merchant merchant, TransactionLog log,
			PvrqVersioningResponse pvrqVersioningResponse, Map<String, String> propMap,
			TransactionEssentials transactionEssentials) {

		logger.info("SBI Inside Pareq=======================================================>"
				+ " For transaction_id========================> " + log.getTransactionId());

		LocalDateTime now = LocalDateTime.now();
		String pattern = "yyyyMMddHHmmss";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		String formattedDateTime = now.format(formatter);

		String signedResponse = null;
		Gson gson = new Gson();
		logger.info("SBI Pareq Headers===========>" + header + " For transaction_id================================> "
				+ log.getTransactionId());
		String threeDSRequestorURL = propMap.get(SBICardsUtils.SBI_CARD_RETURN_URL_V2);
		threeDSRequestorURL = threeDSRequestorURL.replace("#ru", String.valueOf(log.getTransactionId()));

		try {
			AES aes = AES.init();
			String url = pvrqVersioningResponse.getThreeDSServerPaRqURL();
			logger.info("SBI Parq url=============>" + url);
			ParqAuthenticationRequest paymentRequest = new ParqAuthenticationRequest();
			paymentRequest.setMessageType("pArq");
			paymentRequest.setDeviceChannel("02");
			paymentRequest.setMerchantTransID(String.valueOf(log.getTransactionId()));
			paymentRequest.setMessageVersion(pvrqVersioningResponse.getP_messageVersion());
			paymentRequest.setMessageCategory("01");
			paymentRequest.setThreeDSServerTransID(pvrqVersioningResponse.getThreeDSServerTransID());
			paymentRequest.setThreeDSRequestorID(setting.getmPassword()); // Aquarier Merchant Id
			paymentRequest.setThreeDSRequestorName(merchant.getMerchantName()); // Merchant Name
			paymentRequest.setThreeDSRequestorURL(threeDSRequestorURL);
			paymentRequest.setThreeDSCompInd("Y");
			paymentRequest.setThreeDSRequestorAuthenticationInd("01");

//			ThreeDSRequestorAuthenticationInfo authInfo = new ThreeDSRequestorAuthenticationInfo();
//			authInfo.setThreeDSReqAuthMethod("04");
//			authInfo.setThreeDSReqAuthTimestamp("202303131445");
//			authInfo.setThreeDSReqAuthData("00");
//			paymentRequest.setThreeDSRequestorAuthenticationInfo(authInfo);

			paymentRequest.setAcctNumber(usercardDetails.getCardNo());
			paymentRequest.setAcctType("03");
			paymentRequest.setAddrMatch("Y");

			String cardexp = usercardDetails.getExpiryYear().substring(2) + usercardDetails.getExpiryMonth();
			paymentRequest.setCardExpiryDate(cardexp);
			paymentRequest.setCardholderName(usercardDetails.getCustomerName());
			paymentRequest.setMcc(merchant.getMccCode());
			paymentRequest.setMerchantName(merchant.getMerchantName());
			paymentRequest.setMerchantUrl(threeDSRequestorURL);
			paymentRequest.setMerchantCountryCode("356");
//			paymentRequest.setAcquirerBIN(propMap.get(SBICardsUtils.SBI_ACQUIRERBIN));

			if (usercardDetails.getCardProvider().equalsIgnoreCase("VISA")) {
				paymentRequest.setAcquirerBIN(propMap.get(SBICardsUtils.SBI_ACQUIRER_BIN_VISA));
			} else if (usercardDetails.getCardProvider().equalsIgnoreCase("MASTER")) {
				paymentRequest.setAcquirerBIN(propMap.get(SBICardsUtils.SBI_ACQUIRER_BIN_MASTER));
			}
			paymentRequest.setAcquirerID(propMap.get(SBICardsUtils.SBI_ACQUIRERID));

			// todo check which one mid
			paymentRequest.setAcquirerMerchantID(setting.getmPassword()); // Aquarier Merchant Id
			paymentRequest.setPurchaseCurrency("356");
			paymentRequest.setPurchaseExponent("2");
			paymentRequest.setPurchaseAmount("" + amount);

			paymentRequest.setPurchaseDate(formattedDateTime);

			MobilePhone mobilePhone = new MobilePhone();
			mobilePhone.setCc("91");
			mobilePhone.setSubscriber(log.getUdf2());
			paymentRequest.setMobilePhone(mobilePhone);
			paymentRequest.setEmail("alerts@getepay.in");
			paymentRequest.setTransType("01");
			paymentRequest.setThreeRIInd("02");
			paymentRequest.setThreeDSRequestorFinalAuthRespURL(threeDSRequestorURL);
			paymentRequest.setBrowserAcceptHeader("*/*");

			if (transactionEssentials.getUdf60() != null && !transactionEssentials.getUdf60().equals("")
					&& !transactionEssentials.getUdf60().equalsIgnoreCase("null")) {
				paymentRequest.setBrowserIP(transactionEssentials.getUdf60());
			} else {
				paymentRequest.setBrowserIP("183.83.177.146");

			}
			paymentRequest.setBrowserJavaEnabled(true);
			paymentRequest.setBrowserJavascriptEnabled(true);
			paymentRequest.setBrowserLanguage("en-US");
			paymentRequest.setBrowserColorDepth(transactionEssentials.getUdf31().split("\\|")[1]);
			paymentRequest.setBrowserScreenHeight(transactionEssentials.getUdf31().split("\\|")[2]);
			paymentRequest.setBrowserScreenWidth(transactionEssentials.getUdf31().split("\\|")[3]);
			paymentRequest.setBrowserTZ(transactionEssentials.getUdf31().split("\\|")[4]);
			paymentRequest.setBrowserUserAgent(
					"mozilla/5.0+(x11;+ubuntu;+linux+x86_64;+rv:72.0)+gecko/20100101+firefox/72.0");

			paymentRequest.setP_messageVersion(pvrqVersioningResponse.getP_messageVersion());

			String jsonrequest = gson.toJson(paymentRequest);
			logger.info("SBI pareq Request======================>" + paymentRequest
					+ " For transaction_id===============================> " + log.getTransactionId());
			String signedRequest = EncryptionUtil.digitalSignWithRSA(jsonrequest, Client.getPrivateKey());
			String encryptedRequest = EncryptionUtil.encrypt(signedRequest, aes);
			String encSymmetricKey = EncryptionUtil.encryptDEK(aes.getKey(), Server.getPublicKey());
			EncryptedRequestData encryptedRequestData = EncryptedRequestData.buildRequest(encryptedRequest,
					encSymmetricKey, aes);
			logger.info("SBI pareq Encrypted request ==============> " + JsonUtil.getJsonString(encryptedRequestData));
			String encrequestjson = gson.toJson(encryptedRequestData);
			String response = SBICardsUtils.postapi(url, encrequestjson, header);
			logger.info("SBI pareq Encrypted Response ================> " + response
					+ " For transaction_id==============================> " + log.getTransactionId());

			EncryptedResponseData encryptedResponse = gson.fromJson(response, EncryptedResponseData.class);

			if (encryptedResponse.getStatusCode().equals("PG99200")) {
				SecretKey decryptedSymmetricKey = DecryptionUtil
						.decryptDEK(encryptedResponse.getResponseSymmetricEncKey(), Client.getPrivateKey());
				signedResponse = DecryptionUtil.decrypt(encryptedResponse.getSignedEncResponsePayload(),
						encryptedResponse.getIv(), decryptedSymmetricKey);

				DecryptionUtil.verifySignature(signedResponse, Server.getPublicKey());
				logger.info("SBI pareq Decrypted Response" + DecryptionUtil.getJsonFromJws(signedResponse));

			}

			ParqAuthenticationResponse parqAuthresponse = gson.fromJson(DecryptionUtil.getJsonFromJws(signedResponse),
					ParqAuthenticationResponse.class);
			return parqAuthresponse;

		}

		catch (Exception e) {
			new GlobalExceptionHandler().customException(e);

		}
		return null;
	}

	public static PRqFrqFinalResponse PRqFrqFinalRequest(SbiRequestHeader header,
			TransactionEssentials transactionEssentials, MerchantSetting merchantSetting, TransactionLog transactionLog,
			Map<String, String> parameterMap, Map<String, String> propMap) throws Exception {
		String signedResponse = null;

		logger.info("Inside SBI Cards PRqFrqFinalRequest===========================>");

		Gson gson = new Gson();
		try {
			AES aes = AES.init();
			String url = transactionEssentials.getUdf46();

			logger.info(" SBI Cards PRqFrqFinalRequest Url===========================>" + url);
			PRqFrqFinalRequest prqFrqFinalRequest = new PRqFrqFinalRequest();
			prqFrqFinalRequest.setMessageType("pRqFrq");
			prqFrqFinalRequest.setMessageVersion(transactionEssentials.getUdf48());
			prqFrqFinalRequest.setThreeDSServerTransID(transactionEssentials.getUdf47());
			prqFrqFinalRequest.setAcsTransID(transactionEssentials.getUdf49());
			prqFrqFinalRequest.setDsTransID(transactionEssentials.getUdf50());
			prqFrqFinalRequest.setMerchantTransID(String.valueOf(transactionLog.getTransactionId()));
			prqFrqFinalRequest.setAcquirerID(propMap.get(SBICardsUtils.SBI_ACQUIRERID));

			if (parameterMap.get("cres") != null && parameterMap.containsKey("cres")) {
				prqFrqFinalRequest.setAcsAuthResponse(parameterMap.get("cres"));
			} else if (parameterMap.get("pares") != null && parameterMap.containsKey("pares")) {
				prqFrqFinalRequest.setAcsAuthResponse(parameterMap.get("pares"));
			}
			prqFrqFinalRequest.setP_messageVersion(transactionEssentials.getUdf48());

			String encrequestjson = encrypt(gson.toJson(prqFrqFinalRequest), aes);

			logger.info("SBI PRqFrqFinalRequest Encrypted Request ================> " + prqFrqFinalRequest
					+ " For transaction_id==============================> " + transactionLog.getTransactionId());

			logger.info("SBI PRqFrqFinalRequest Url======>" + url
					+ " For transaction_id==============================> " + transactionLog.getTransactionId());
			String response = SBICardsUtils.postapi(url, encrequestjson, header);

			logger.info("SBI PRqFrqFinalRequest Encrypted Response ================> " + response
					+ " For transaction_id==============================> " + transactionLog.getTransactionId());

			EncryptedResponseData encryptedResponse = gson.fromJson(response, EncryptedResponseData.class);

			String decryptResponse = decypt(encryptedResponse, aes);

			logger.info("SBI PRqFrqFinalRequest Decrypted Response ================> " + decryptResponse
					+ " For transaction_id==============================> " + transactionLog.getTransactionId());

			PRqFrqFinalResponse prqFrqFinalResponse = gson.fromJson(decryptResponse, PRqFrqFinalResponse.class);

			return prqFrqFinalResponse;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public static SbiSaleAuthorizationResponse saleAuth(SbiRequestHeader header, Merchant merchant,
			MerchantSetting setting, TransactionEssentials transactionEssentials, TransactionLog log,
			PRqFrqFinalResponse prqFrqFinalResponse, Map<String, String> parameterMap,
			ParqAuthenticationResponse parqAuthenticationResponse, String secretKey, Map<String, String> propMap,BigDecimal saleAuthamount) {

		logger.info("Inside Sbi Sale Auth=============================================>");

		String signedResponse = null;
		Gson gson = new Gson();
		try {

			String panNo = tokendecrypt(transactionEssentials.getUdf45(), secretKey,
					transactionEssentials.getUdf42().getBytes());

			AES aes = AES.init();
			SbiSaleAuthorizationRequest saleauth = new SbiSaleAuthorizationRequest();
//			saleauth.setPgInstanceId(setting.getSetting1());
//			saleauth.setMerchantId(setting.getSetting2());
			saleauth.setPgInstanceId(propMap.get(SBICardsUtils.SBI_PG_INSTENCE_ID_AUTH_SALE));
			saleauth.setMerchantId(setting.getMloginId());
			saleauth.setAcquiringBankId(propMap.get(SBICardsUtils.SBI_ACQUIRING_BANK_ID));
			saleauth.setAction("SERVICE_POST_MPI");
			saleauth.setTransactionTypeCode("9003");
			saleauth.setDeviceCategory("0");
			saleauth.setPan(panNo);

			saleauth.setExpiryDateYYYY("20" + transactionEssentials.getUdf44().substring(2, 4));
			saleauth.setExpiryDateMM(transactionEssentials.getUdf44().substring(0, 2));
			// saleauth.setCvv2("123");
			saleauth.setNameOnCard(log.getCustomername());
			saleauth.setEmail(log.getUdf3());

			saleauth.setCurrencyCode("356");
			
			logger.info("Sale Auth Amount in Big Decimal =======================>"+saleAuthamount);
			int amount = saleAuthamount.multiply(new BigDecimal(100)).intValue();
			logger.info("Sale Auth Amount in Integer =======================>"+amount);
			
			
			saleauth.setAmount(String.valueOf(amount));
			saleauth.setMerchantReferenceNo(String.valueOf(log.getTransactionId()));
			saleauth.setOrderDesc("Order Description for " + log.getTransactionId());
			saleauth.setCustomerDeviceId(log.getUdf2());
			saleauth.setCryptogram(transactionEssentials.getUdf43());
			if (parameterMap.get("cres") != null && parameterMap.containsKey("cres")) {
				saleauth.setMpiTransactionId(prqFrqFinalResponse.getMerchantTransID());
				saleauth.setThreeDsStatus(prqFrqFinalResponse.getTransStatus());
				saleauth.setThreeDsEci(prqFrqFinalResponse.getEci());
				saleauth.setThreeDsCavvAav(prqFrqFinalResponse.getAuthenticationValue());
				saleauth.setThreeDsXid(prqFrqFinalResponse.getAuthenticationValue());
			} else if (parameterMap.get("parqAuthenticationResponse") != null
					&& parameterMap.containsKey("parqAuthenticationResponse")) {
				saleauth.setMpiTransactionId(parqAuthenticationResponse.getMerchantTransID());
				saleauth.setThreeDsStatus(parqAuthenticationResponse.getTransStatus());
				saleauth.setThreeDsEci(parqAuthenticationResponse.getEci());
				saleauth.setThreeDsCavvAav(parqAuthenticationResponse.getAuthenticationValue());
				saleauth.setThreeDsXid(parqAuthenticationResponse.getAuthenticationValue());
			} else if (parameterMap.get("pares") != null && parameterMap.containsKey("pares")) {
				saleauth.setMpiTransactionId(prqFrqFinalResponse.getMerchantTransID());
				saleauth.setThreeDsStatus(prqFrqFinalResponse.getTransStatus());
				saleauth.setThreeDsEci(prqFrqFinalResponse.getEci());
				saleauth.setThreeDsCavvAav(prqFrqFinalResponse.getAuthenticationValue());
				saleauth.setThreeDsXid(prqFrqFinalResponse.getAuthenticationValue());
			}
			saleauth.setAltIdFlag("Y");

			String requestjson = gson.toJson(saleauth);
			logger.info("SBI Request json for sale Auth==================>" + saleauth
					+ " For transaction_id==================>" + log.getTransactionId());
			String encrequestjson = encrypt(requestjson, aes);

			logger.info("SBI Cards Encrypted Request ==================>" + encrequestjson
					+ " For transaction_id==================>" + log.getTransactionId());
			String saleauthurl = propMap.get(SBICardsUtils.SBI_SALE_AUTH_URL);
			String response = SBICardsUtils.postapi(saleauthurl, encrequestjson, header);

			logger.info("SBI Cards Encrypted Response ==================>" + encrequestjson
					+ " For transaction_id==================>" + log.getTransactionId());

			EncryptedResponseData encryptedResponse = gson.fromJson(response, EncryptedResponseData.class);

			String decryptResponse = decypt(encryptedResponse, aes);

			SbiSaleAuthorizationResponse sbiSaleAuthorizationResponse = gson.fromJson(decryptResponse,
					SbiSaleAuthorizationResponse.class);

			logger.info("SBI Cards Decrypted Response ==================>" + decryptResponse
					+ " For transaction_id==================>" + log.getTransactionId());

			return sbiSaleAuthorizationResponse;

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public static RupayCheckbinResponse checkbin(int value, SbiRequestHeader header, Map<String, String> propMap,
			Merchant merchant, MerchantSetting merchantsetting, TransactionLog log,
			TransactionEssentials transactionEssentials) throws Exception {

		logger.info("Inside Rupay Check Bin=====================>");
		String signedResponse = null;
		Gson gson = new Gson();
		try {
			AES aes = AES.init();
			RupayCheckbinRequest rupaycheckbinrequest = new RupayCheckbinRequest();
			// rupaycheckbinrequest.setPgInstanceId(merchantsetting.getSetting1());
			rupaycheckbinrequest.setPgInstanceId(propMap.get(SBICardsUtils.SBI_RUPAY_HEADER_PG_INSTANCE_ID));
			rupaycheckbinrequest.setCardBin(String.valueOf(value));
			String requestjson = gson.toJson(rupaycheckbinrequest);
			logger.info("Request Check Bin Request=========>" + rupaycheckbinrequest + " For transaction_id==================>"
					+ log.getTransactionId());

			String checkbinurl = propMap.get(SBICardsUtils.SBI_RUPAY_CHECKBIN_URL);
			logger.info("Rupay Chaeck Bin Url========================>" + checkbinurl
					+ " For transaction_id==================>" + log.getTransactionId());

			String encrequestjson = encrypt(requestjson, aes);

			logger.info("Rupay CheckBin encrypted Request======================>" + encrequestjson
					+ " For transaction_id==================>" + log.getTransactionId());

			String response = SBICardsUtils.postapi(checkbinurl, encrequestjson, header);

			logger.info("Rupay Checkbin Encrypted Response=================================>" + response
					+ " For transaction_id==================>" + log.getTransactionId());

			EncryptedResponseData encryptedResponse = gson.fromJson(response, EncryptedResponseData.class);

			String decryptResponse = decypt(encryptedResponse, aes);

			RupayCheckbinResponse rupaycheckbinResponse = gson.fromJson(decryptResponse, RupayCheckbinResponse.class);

			logger.info("Rupay Checkbin Decrypted Response=======================>" + decryptResponse
					+ " For transaction_id==================>" + log.getTransactionId());

			return rupaycheckbinResponse;

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}

		return null;

	}

	public static Initiate2Response initiate(CardDetailsVo usercardDetails, SbiRequestHeader header,
			Map<String, String> propMap, Merchant merchant, int amount, MerchantSetting merchantsetting,
			TransactionLog log, SBITokenResponse sbitokenResponse, TransactionEssentials transactionEssentials) {

		logger.info("Inside SBI Rupay Initate ===========================>");

		Gson gson = new Gson();
		String signedResponse = null;

		String redirectionurl = propMap.get(SBICardsUtils.SBI_RUPAY_REDIRECT_RETURN_URL_V2);
		redirectionurl = redirectionurl.replace("#ru", String.valueOf(log.getTransactionId()));

		logger.info(" SBI Rupay Redirect Url ===========================>" + redirectionurl);
		try {
			AES aes = AES.init();

			Initiate2Request redirectrupay = new Initiate2Request();
//			redirectrupay.setPgInstanceId(merchantsetting.getSetting1());
//			redirectrupay.setMerchantId(merchantsetting.getSetting2());
			redirectrupay.setPgInstanceId(propMap.get(SBICardsUtils.SBI_RUPAY_HEADER_PG_INSTANCE_ID));
			redirectrupay.setMerchantId(merchantsetting.getMloginId());
			redirectrupay.setMerchantReferenceNo(String.valueOf(log.getTransactionId()));
			redirectrupay.setPan(usercardDetails.getCardNo());
			redirectrupay.setCardExpDate(usercardDetails.getExpiryMonth() + usercardDetails.getExpiryYear());
			redirectrupay.setBrowserUserAgent("Windows, Chrome-89.0.4389.90");

			if(transactionEssentials.getUdf60()==null || transactionEssentials.getUdf60().equalsIgnoreCase("null")) {
				redirectrupay.setIpAddress("183.83.177.146");
			}
			
			else {
				redirectrupay.setIpAddress(transactionEssentials.getUdf60());
			}

			redirectrupay.setHttpAccept("*/*");
			redirectrupay.setAuthAmount(String.valueOf(amount));
			redirectrupay.setCurrencyCode("356");
			redirectrupay.setNameOnCard(usercardDetails.getCustomerName());
			if (log.getUdf3() == null) {
				redirectrupay.setEmail("alerts@getepay.in");
			} else {
				redirectrupay.setEmail(log.getUdf3());
			}

			redirectrupay.setOriginalAmount(String.valueOf(amount));
			redirectrupay.setAmountInInr(String.valueOf(amount));
			redirectrupay.setMobileNumber(merchant.getMobileNumber());
			redirectrupay.setExt1("123456");
			redirectrupay.setExt2("123456");

//			  String cvvencrypt = EncryptionUtil.encrypt("123", aes);
//			  redirectrupay.setCvd2(cvvencrypt);

			redirectrupay.setCvd2(usercardDetails.getCvv());

			redirectrupay.setOrderDesc("Order Description for " + log.getTransactionId());
			redirectrupay.setMerchantResponseUrl(redirectionurl);
			redirectrupay.setPurposeOfAuthentication("ALT ID Transaction");
			// redirectrupay.setTokenAuthenticationValue();

			String requestjson = gson.toJson(redirectrupay);
			logger.info("Json Request for SBI Rupay Initate=========>" + redirectrupay
					+ " For transaction_id==================>" + log.getTransactionId());

			logger.info("SBI Rupay Initate Url===========>" + propMap.get(SBICardsUtils.SBI_RUPAY_INITIATE_URL));

			String intiateurl = propMap.get(SBICardsUtils.SBI_RUPAY_INITIATE_URL);

			String encrequestjson = encrypt(requestjson, aes);

			logger.info("SBI Rupay Initate Encrypted Request===========>" + encrequestjson
					+ " For transaction_id==================>" + log.getTransactionId());

			String response = SBICardsUtils.postapi(intiateurl, encrequestjson, header);

			logger.info("SBI Rupay Initate Encrypted Response===========>" + response
					+ " For transaction_id==================>" + log.getTransactionId());

			EncryptedResponseData encryptedResponse = gson.fromJson(response, EncryptedResponseData.class);

			String decryptResponse = decypt(encryptedResponse, aes);

			logger.info("SBI Rupay Initate Decrypted Response===========>" + decryptResponse
					+ " For transaction_id==================>" + log.getTransactionId());

			Initiate2Response initiateResponse = gson.fromJson(decryptResponse, Initiate2Response.class);

			return initiateResponse;

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public static ResendOTPResponse resendotp(String pgTrasnactionId, SbiRequestHeader header,
			Map<String, String> propMap, Merchant merchant, MerchantSetting merchantsetting, TransactionLog log)
			throws Exception {
		Gson gson = new Gson();

		logger.info("Inside SBI RUPAY Resend OTP ======================>");
		AES aes = AES.init();
		try {
			ResendOTPRequest resentotprequest = new ResendOTPRequest();
//			resentotprequest.setPgInstanceId(merchantsetting.getSetting1());
//			resentotprequest.setMerchantId(merchantsetting.getSetting2());

			resentotprequest.setPgInstanceId(propMap.get(SBICardsUtils.SBI_RUPAY_HEADER_PG_INSTANCE_ID));
			resentotprequest.setMerchantId(merchantsetting.getMloginId());
			resentotprequest.setPgTransactionId(pgTrasnactionId);
			resentotprequest.setMerchantReferenceNo(String.valueOf(log.getTransactionId()));
			resentotprequest.setCardHolderStatus("GC");
			String requestjson = gson.toJson(resentotprequest);

			logger.info("SBI Rupay Resend Otp Request=========>" + resentotprequest
					+ " For transaction_id==================>" + log.getTransactionId());

			logger.info(
					"SBI Rupay Resend Otp Rupay Url========>" + propMap.get(SBICardsUtils.SBI_RUPAY_RESEND_OTP_URL));
			String intiateurl = propMap.get(SBICardsUtils.SBI_RUPAY_RESEND_OTP_URL);

			String encrequestjson = encrypt(requestjson, aes);

			logger.info("SBI Resend Otp Encrypted Request=========>" + encrequestjson
					+ " For transaction_id==================>" + log.getTransactionId());
			String response = SBICardsUtils.postapi(intiateurl, encrequestjson, header);

			logger.info("SBI Resend Otp Encrypted Response=========>" + encrequestjson
					+ " For transaction_id==================>" + log.getTransactionId());

			EncryptedResponseData encryptedResponse = gson.fromJson(response, EncryptedResponseData.class);

			String decryptResponse = decypt(encryptedResponse, aes);

			logger.info("SBI Resend Otp Decrypted Response=========>" + encrequestjson
					+ " For transaction_id==================>" + log.getTransactionId());

			ResendOTPResponse resendotpResponse = gson.fromJson(decryptResponse, ResendOTPResponse.class);

			return resendotpResponse;

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);

		}
		return null;
	}

	public static VerifyOTPResponse verifyotp(String pgTrasnactionId, String otp, SbiRequestHeader header,
			Map<String, String> propMap, Merchant merchant, MerchantSetting merchantsetting, TransactionLog log)
			throws Exception {

		logger.info("Inside  SBI Rupay Verify Otp ========================>" + " For transaction_id==================>"
				+ log.getTransactionId());
		Gson gson = new Gson();
		AES aes = AES.init();
		try {
			VerifyOTPRequest verifyOTPRequest = new VerifyOTPRequest();
			verifyOTPRequest.setPgInstanceId(propMap.get(SBICardsUtils.SBI_RUPAY_HEADER_PG_INSTANCE_ID));
			verifyOTPRequest.setMerchantId(merchantsetting.getMloginId());

			verifyOTPRequest.setMerchantReferenceNo(String.valueOf(log.getTransactionId()));
			verifyOTPRequest.setPgTransactionId(pgTrasnactionId);
			verifyOTPRequest.setOtp(otp);

			String requestjson = gson.toJson(verifyOTPRequest);

			logger.info("SBI Verify Otp Request=========>" + verifyOTPRequest + " For transaction_id==================>"
					+ log.getTransactionId());

			logger.info("SBI Rupay Verify Otp Url============>" + propMap.get(SBICardsUtils.SBI_RUPAY_VERIFY_OTP_URL));
			String intiateurl = propMap.get(SBICardsUtils.SBI_RUPAY_VERIFY_OTP_URL);

			String encrequestjson = encrypt(requestjson, aes);
			logger.info("SBI Verify Otp Encrypted Request=========>" + encrequestjson
					+ " For transaction_id==================>" + log.getTransactionId());
			String response = SBICardsUtils.postapi(intiateurl, encrequestjson, header);

			logger.info("SBI Verify Otp Encrypted Response=========>" + response
					+ " For transaction_id==================>" + log.getTransactionId());

			EncryptedResponseData encryptedResponse = gson.fromJson(response, EncryptedResponseData.class);

			String decryptResponse = decypt(encryptedResponse, aes);

			logger.info("SBI Verify Otp Decrypted Response=========>" + decryptResponse
					+ " For transaction_id==================>" + log.getTransactionId());

			VerifyOTPResponse verifyotpresponse = gson.fromJson(decryptResponse, VerifyOTPResponse.class);

			return verifyotpresponse;

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);

		}
		return null;
	}

	public static AuthorizeApiResponse authorize(SbiRequestHeader header, Map<String, String> propMap,
			Merchant merchant, MerchantSetting merchantsetting, TransactionLog log, SBITokenResponse sbiTokenResponse,
			String secretKey, Map<String, String> parameterMap) throws Exception {

		logger.info("Inside SBI Rupay Authorize =============================>"
				+ " For transaction_id==================>" + log.getTransactionId());
		Gson gson = new Gson();
		AES aes = AES.init();
		try {
			String panNo = tokendecrypt(sbiTokenResponse.getEncTokenInfo(), secretKey,
					sbiTokenResponse.getIv().getBytes());

			AuthorizeApiRequest authApiRequest = new AuthorizeApiRequest();
//			authApiRequest.setPgInstanceId(merchantsetting.getSetting1());
//			authApiRequest.setMerchantId(merchantsetting.getSetting2());
			authApiRequest.setPgInstanceId(propMap.get(SBICardsUtils.SBI_RUPAY_HEADER_PG_INSTANCE_ID));
			authApiRequest.setMerchantId(merchantsetting.getMloginId());
			authApiRequest.setPgTransactionId(parameterMap.get("pgTransactionId"));
			authApiRequest.setMerchantReferenceNo(String.valueOf(log.getTransactionId()));
			if (parameterMap.containsKey("productType") && parameterMap.get("productType").equalsIgnoreCase("SEAMLESS")) {
				String year="20";
				String data=sbiTokenResponse.getTokenExpiryDate();
				String modifiedString = data.substring(0, 2) + year + data.substring(2);
				
				logger.info("Modified date String====================>"+modifiedString);
				authApiRequest.setAltExpiry(modifiedString);
			}
			else {
				authApiRequest.setAltExpiry(sbiTokenResponse.getTokenExpiryDate());
			}

			
			authApiRequest.setAltId(panNo);
			authApiRequest.setTokenAuthenticationValue(sbiTokenResponse.getVar2());

			String requestjson = gson.toJson(authApiRequest);

			logger.info(" SBI Rupay Authorize Request=========>" + authApiRequest
					+ " For transaction_id==================>" + log.getTransactionId());

			logger.info("SBI Rupay Auth Url=======>" + propMap.get(SBICardsUtils.SBI_RUPAY_AUTH_URL));
			String authrizeUrl = propMap.get(SBICardsUtils.SBI_RUPAY_AUTH_URL);

			String encrequestjson = encrypt(requestjson, aes);

			logger.info("SBI Rupay Authorize Encrypted Request=========>" + encrequestjson
					+ " For transaction_id==================>" + log.getTransactionId());
			String response = SBICardsUtils.postapi(authrizeUrl, encrequestjson, header);

			logger.info("SBI Rupay Authorize Encrypted Response=========>" + response
					+ " For transaction_id==================>" + log.getTransactionId());

			EncryptedResponseData encryptedResponse = gson.fromJson(response, EncryptedResponseData.class);

			String decryptResponse = decypt(encryptedResponse, aes);

			logger.info("SBI Rupay Authorize Decrypted Response=========>" + decryptResponse
					+ " For transaction_id==================>" + log.getTransactionId());

			AuthorizeApiResponse authresponse = gson.fromJson(decryptResponse, AuthorizeApiResponse.class);

			return authresponse;

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);

		}
		return null;
	}

	public static SbiRefundVoidResponse refund(SbiRequestHeader header, Map<String, String> propMap,
			MerchantSetting merchantsetting, TransactionLog log, Refund refund) throws Exception {
		Gson gson = new Gson();
		AES aes = AES.init();
		try {
			SbiRefundVoidRequest refundRequest = new SbiRefundVoidRequest();
			refundRequest.setPgInstanceId(merchantsetting.getSetting1());
			refundRequest.setMerchantId(String.valueOf(log.getMerchantId()));
			refundRequest.setAction("voidorrefund");
			refundRequest.setOrignalTransactionId(String.valueOf(log.getTransactionId()));
			refundRequest.setMerchantReferenceNo(String.valueOf(log.getTransactionId()));
			refundRequest.setMessageHash("");
			refundRequest.setAmount(String.valueOf(refund.getRefundAmount()));
			refundRequest.setRefundType("CN");

			String requestjson = gson.toJson(refundRequest);

			logger.info("Request Check Bin Request=========>" + requestjson);

			logger.info(propMap.get(SBICardsUtils.SBI_REFUND_URL));
			String refundUrl = propMap.get(SBICardsUtils.SBI_REFUND_URL);

			String encrequestjson = encrypt(requestjson, aes);

			String response = SBICardsUtils.postapi(refundUrl, encrequestjson, header);

			logger.info(response);

			EncryptedResponseData encryptedResponse = gson.fromJson(response, EncryptedResponseData.class);

			String decryptResponse = decypt(encryptedResponse, aes);

			SbiRefundVoidResponse refundresponse = gson.fromJson(decryptResponse, SbiRefundVoidResponse.class);

			return refundresponse;

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);

		}
		return null;
	}

	public static GenerateOTPResponse generateotp(CardDetailsVo usercardDetails, SbiRequestHeader header,
			Map<String, String> propMap, Merchant merchant, MerchantSetting merchantsetting, TransactionLog log,
			int rupayamount,TransactionEssentials transactionEssentials) throws Exception {

		logger.info("Inside SBI Rupay Generate OTP==================================>");
		Gson gson = new Gson();
		AES aes = AES.init();
		try {
			GenerateOTPRequest otpreq = new GenerateOTPRequest();
//			otpreq.setPgInstanceId(merchantsetting.getSetting1());
//			otpreq.setMerchantId(merchantsetting.getSetting2());

			otpreq.setPgInstanceId(propMap.get(SBICardsUtils.SBI_RUPAY_HEADER_PG_INSTANCE_ID));
			otpreq.setMerchantId(merchantsetting.getMloginId());
			otpreq.setMerchantReferenceNo(String.valueOf(log.getTransactionId()));
			otpreq.setPan(usercardDetails.getCardNo());
			otpreq.setCardExpDate(usercardDetails.getExpiryMonth() + usercardDetails.getExpiryYear());
			otpreq.setCvd2(usercardDetails.getCvv());
			otpreq.setNameOnCard(usercardDetails.getCustomerName());
			otpreq.setCardHolderStatus("GC");
			otpreq.setAmount(String.valueOf(rupayamount));
			otpreq.setEmail(log.getUdf3()); // TODO- Change
			otpreq.setCurrencyCode("356");
			if(transactionEssentials.getUdf60()==null || transactionEssentials.getUdf60().equalsIgnoreCase("null")) {
				otpreq.setCustomerIpAddress("203.11.11.12");
			}
			else {
				otpreq.setCustomerIpAddress(transactionEssentials.getUdf60());
			}
			
			otpreq.setBrowserUserAgent("Windows, Chrome-89.0.4389.90");
			otpreq.setHttpAccept("*/*");
			otpreq.setExt1("123456");
			otpreq.setExt2("234565");
			otpreq.setAmountInInr(String.valueOf(rupayamount));
			otpreq.setOriginalAmount(String.valueOf(rupayamount));
			otpreq.setPurposeOfAuthentication("ALT ID Transaction");
			otpreq.setOrderDesc("Order Description For " + log.getMerchantId());
			// otpreq.setTokenAuthenticationValue("");

			String requestjson = gson.toJson(otpreq);

			logger.info("SBI Rupay Generate OTP Request=========>" + otpreq + " For transaction_id==================>"
					+ log.getTransactionId());

			logger.info(propMap.get(SBICardsUtils.SBI_RUPAY_GENERATE_OTP_URL));
			String generateOtpRupayUrl = propMap.get(SBICardsUtils.SBI_RUPAY_GENERATE_OTP_URL);

			String encrequestjson = encrypt(requestjson, aes);

			logger.info("SBI Rupay Generate OTP Encrypted Request=========>" + otpreq
					+ " For transaction_id==================>" + log.getTransactionId());
			String response = SBICardsUtils.postapi(generateOtpRupayUrl, encrequestjson, header);

			logger.info("SBI Rupay Generate OTP Encrypted Response=========>" + response
					+ " For transaction_id==================>" + log.getTransactionId());

			EncryptedResponseData encryptedResponse = gson.fromJson(response, EncryptedResponseData.class);

			String decryptResponse = decypt(encryptedResponse, aes);

			logger.info("SBI Rupay Generate OTP Decrypted Response=========>" + decryptResponse
					+ " For transaction_id==================>" + log.getTransactionId());

			GenerateOTPResponse verifyotpresponse = gson.fromJson(decryptResponse, GenerateOTPResponse.class);

			return verifyotpresponse;

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);

		}
		return null;
	}

	public static SBITokenResponse generateAltIdToken(CardDetailsVo usercardDetails, TokenHeader tokenheader,
			Map<String, String> propMap, Merchant merchant, MerchantSetting merchantsetting, TransactionLog log,
			TransactionEssentials transactionEssentials, String cardType, int amount, String enccardData) {

		logger.info("Inside Generate Alt Id Token========================================================>");
		Gson gson = new Gson();
		try {
			long fulltimeStamp = System.currentTimeMillis();
			long timestamp = fulltimeStamp / 1000;

			logger.info("SBI Alt Id Token Generation timeStamp============================>" + fulltimeStamp);
			String iv = null;

//			if (cardType != null && !cardType.equalsIgnoreCase("")) {
//				if (cardType.equalsIgnoreCase("M") || cardType.equalsIgnoreCase("R")) {
//					iv = tokenIV();
//				} else if (cardType.equalsIgnoreCase("V")) {
//					iv = tokenIV();
//				}
//			}

			if (cardType != null && !cardType.equalsIgnoreCase("")) {
				iv = tokenIV();
			}

			logger.info("Enc Card Data==========================>" + enccardData
					+ " For transaction_id====================>" + log.getTransactionId());

			String secretKeyString = propMap.get(SBICardsUtils.SBI_TOKEN_ENC_SECRET_KEY);
			byte[] IV = iv.getBytes();

			if (enccardData == null) {
				SBITokenEncryptedData encryptedData = new SBITokenEncryptedData();
				encryptedData.setPan(usercardDetails.getCardNo());
				encryptedData.setExpiryYear(usercardDetails.getExpiryYear());
				encryptedData.setExpiryMonth(usercardDetails.getExpiryMonth());
				encryptedData.setSecurityCode(usercardDetails.getCvv());
				encryptedData.setCountryCode("IN");
				encryptedData.setEmail("alerts@getepay.in");

				String jsonEncryptedData = gson.toJson(encryptedData);
				SecretKey secretKey = generateSecretKey(secretKeyString);
				byte[] plainText = jsonEncryptedData.getBytes();
				enccardData = tokenencrypt(plainText, secretKey, IV);
				logger.info("SBI Encrypted data======>" + enccardData + "transaction id======================>"
						+ log.getTransactionId());

			}

			SBITokenRequest sbitokenRequest = new SBITokenRequest();

			sbitokenRequest.setEncryptedData(enccardData);

			if (cardType.equalsIgnoreCase("V")) {

				logger.info("SBI Token Request for VISA=========================>"
						+ "For transaction_id=====================>" + log.getTransactionId());
				sbitokenRequest.setCardType(cardType);
				sbitokenRequest.setProvider("GC");
				sbitokenRequest.setTokenRequestorId(propMap.get(SBICardsUtils.SBI_TOKEN_REQUESTOR_ID_VISA));
				sbitokenRequest.setUserConsent("Y");
				sbitokenRequest.setMerchantId(propMap.get(SBICardsUtils.SBI_WIBMO_MERCHANT_ID)); // Wibmo Merchant ID
				sbitokenRequest.setClientReferenceId(
						ClientReferenceId(Integer.valueOf(propMap.get(SBICardsUtils.SBI_CLIENT_REFERENCE_ID_LENGTH))));
				sbitokenRequest.setIv(iv);
				sbitokenRequest.setAcquirerMerchantId(merchantsetting.getmPassword()); // Acquirer Merchant ID
				sbitokenRequest.setAcquirerInstanceId(merchantsetting.getSetting1()); // DPA ID
				sbitokenRequest.setAmount(String.valueOf(amount));
				sbitokenRequest.setCurrency("356");
				sbitokenRequest.setMerchantName(merchant.getMerchantName());
				if (cardType.equalsIgnoreCase("R")) {
					sbitokenRequest.setAuthCode(merchantsetting.getSetting9());

				}
			}

			else if (cardType.equalsIgnoreCase("M")) {

				logger.info("SBI Token Request for MASTER=========================>"
						+ "For transaction_id==================>" + log.getTransactionId());
				sbitokenRequest.setCardType(cardType);
				sbitokenRequest.setProvider("GC");
				sbitokenRequest.setTokenRequestorId(propMap.get(SBICardsUtils.SBI_TOKEN_REQUESTOR_ID_MASTER));
				sbitokenRequest.setUserConsent("Y");
				sbitokenRequest.setMerchantId(propMap.get(SBICardsUtils.SBI_WIBMO_MERCHANT_ID)); // change the value at
																									// uat test and live
				sbitokenRequest.setClientReferenceId(
						ClientReferenceId(Integer.valueOf(propMap.get(SBICardsUtils.SBI_CLIENT_REFERENCE_ID_LENGTH))));
				sbitokenRequest.setIv(iv);
				sbitokenRequest.setAcquirerMerchantId(merchantsetting.getmPassword()); // Acquirer Merchant ID
				sbitokenRequest.setAcquirerInstanceId(merchantsetting.getSetting1()); // DPA ID
				sbitokenRequest.setAmount(String.valueOf(amount));
				sbitokenRequest.setCurrency("356");
				sbitokenRequest.setMerchantName(merchant.getMerchantName());
			}

			else if (cardType.equalsIgnoreCase("R")) {
				logger.info("SBI Token Request for RUPAY=========================>"
						+ "For transaction_id==============>" + log.getTransactionId());
				sbitokenRequest.setCardType(cardType);
				sbitokenRequest.setProvider("GC");
				sbitokenRequest.setTokenRequestorId(propMap.get(SBICardsUtils.SBI_TOKEN_REQUESTOR_ID_RUPAY));
				sbitokenRequest.setUserConsent("Y");
				sbitokenRequest.setMerchantId(propMap.get(SBICardsUtils.SBI_WIBMO_MERCHANT_ID)); // todo Bank
				sbitokenRequest.setClientReferenceId(
						ClientReferenceId(Integer.valueOf(propMap.get(SBICardsUtils.SBI_CLIENT_REFERENCE_ID_LENGTH))));
				sbitokenRequest.setIv(transactionEssentials.getUdf39());
				sbitokenRequest.setAcquirerMerchantId(merchantsetting.getmPassword()); // Acquirer Merchant ID
				sbitokenRequest.setAcquirerInstanceId(merchantsetting.getSetting1()); // DPA ID
				sbitokenRequest.setAmount(String.valueOf(amount));
				sbitokenRequest.setCurrency("356");
				sbitokenRequest.setMerchantName(merchant.getMerchantName());
				if (cardType.equalsIgnoreCase("R")) {
					sbitokenRequest.setAuthCode(transactionEssentials.getUdf40());

				}

			}

			String jsonReuqest = gson.toJson(sbitokenRequest);
			logger.info("SBI Token Request=====================>" + jsonReuqest + "For transaction_id============>"
					+ log.getTransactionId());
			String vaultId = propMap.get(SBICardsUtils.SBI_VAULT_ID);

			String XAuthToken = generateHashWithTS(vaultId, tokenheader.getClientId(), tokenheader.getClientApiUser(),
					tokenheader.getClientApiKey(), secretKeyString, timestamp);

			logger.info("XAuthToken===================>" + XAuthToken + "For transaction_id===================>"
					+ log.getTransactionId());
			tokenheader.setAuthToken(XAuthToken);

			logger.info("SBI Token Header=======>" + gson.toJson(tokenheader) + "For transaction_id==============>"
					+ log.getTransactionId());

			String response = SBICardsUtils.posttokenapi(propMap.get(SBICardsUtils.CREATE_TOKEN), jsonReuqest,
					tokenheader);

			logger.info("SBI Token Creation Response======>" + response + "For transaction_id ========>"
					+ log.getTransactionId());

			SBITokenResponse sbiTokenResponse = gson.fromJson(response, SBITokenResponse.class);

			return sbiTokenResponse;

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}

		return null;
	}

	public static RupayEncryptedData encrptCardDetails(CardDetailsVo usercardDetails, TokenHeader tokenheader,
			Map<String, String> propMap, Merchant merchant, MerchantSetting merchantsetting, TransactionLog log,
			TransactionEssentials transactionEssentials, String cardType, int amount, String enccardData) {

		try {

			logger.info("Inside SBI Rupay Ecrypted Card Details==========>" + "For transaction_id================>"
					+ log.getTransactionId());
			Gson gson = new Gson();
			long timestamp = System.currentTimeMillis() / 1000;
			RupayEncryptedData rupayEncData = new RupayEncryptedData();
			String iv = null;

			if (cardType != null && !cardType.equalsIgnoreCase("")) {
//				if (cardType.equalsIgnoreCase("M") || cardType.equalsIgnoreCase("R")) {
//					iv = tokenIV();
//				} else if (cardType.equalsIgnoreCase("V")) {
//					iv = tokenIV();
//				}

				iv = tokenIV();

				String secretKeyString = propMap.get(SBICardsUtils.SBI_TOKEN_ENC_SECRET_KEY);
				byte[] IV = iv.getBytes();

				SBITokenEncryptedData encryptedData = new SBITokenEncryptedData();
				encryptedData.setPan(usercardDetails.getCardNo());
				encryptedData.setExpiryYear(usercardDetails.getExpiryYear());
				encryptedData.setExpiryMonth(usercardDetails.getExpiryMonth());
				encryptedData.setSecurityCode(usercardDetails.getCvv());
				encryptedData.setCountryCode("IN");
				encryptedData.setEmail("alerts@getepay.in");

				String jsonEncryptedData = gson.toJson(encryptedData);
				SecretKey secretKey = generateSecretKey(secretKeyString);
				byte[] plainText = jsonEncryptedData.getBytes();
				enccardData = tokenencrypt(plainText, secretKey, IV);
				logger.info("SBI Rupay Ecrpyted Request for Card Details========>" + enccardData
						+ " For transaction_id================>" + log.getTransactionId());

				rupayEncData.setEncryptedData(enccardData);
				rupayEncData.setIv(iv);
				return rupayEncData;

			}

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;

	}

	public static void main(String[] args) throws Exception {

		System.out.println("Inside Generate Alt Id Token========================================================>");
		Gson gson = new Gson();
		try {

			CardDetailsVo usercardDetails = new CardDetailsVo();
			usercardDetails.setCardNo("6528680163936007");
			usercardDetails.setCardProvider("RUPAY");
			usercardDetails.setCustomerName("ROHIT");
			usercardDetails.setCvv("747");
			usercardDetails.setExpiryMonth("08");
			usercardDetails.setExpiryYear("2030");

			// String secrettokenKey="f23374ef-eb4f-408b-91b4-db0930feb982";
			String secrettokenKey = "b04f581a-c785-49b0-8696-66c8655dfe36";

			String SBI_TOKEN_REQUESTOR_ID_VISA = "DFFFAC4C-B2D8-4CEA-BEBB-E7EB1716A348";
			String SBI_TOKEN_REQUESTOR_ID_MASTER = "035f91e4-8b3a-4127-a037-574fb82387c0";
			String SBI_TOKEN_REQUESTOR_ID_RUPAY = "77700011145";

			String SBI_WIBMO_MERCHANT_ID = "SBGATEPAYJPINALTID001";
			String SBI_VAULT_ID = "100001";
			String CREATE_TOKEN = "https://tokenvault-sbi.wibmo.com/tokenVault/v3/tokenize";
			// String
			// CREATE_TOKEN="https://tokenvault-sbi.wibmo.com/tokenVault/v3/tokenize";
			String SBI_CLIENT_REFERENCE_ID_LENGTH = "10";

			TransactionLog log = new TransactionLog();
			log.setTransactionId(System.currentTimeMillis());

			MerchantSetting merchantsetting = new MerchantSetting();
			merchantsetting.setSetting1("218f8488-8618-450d-9c77-57e77519f14d");
			merchantsetting.setmPassword("969344022867298");

			Merchant merchant = new Merchant();
			merchant.setMerchantName("Mradul Bahety");

			TransactionEssentials transactionEssentials = new TransactionEssentials();
			transactionEssentials.setUdf40("xyz");

			String cardType = "R";

			String enccardData = null;
			TokenHeader tokenheader = new TokenHeader();
			tokenheader.setClientId("d442e442-74b7-4ab6-8bb1-8f77a7252f7d");
			tokenheader.setClientApiKey("Futu6bG6tL7oZ9");
			tokenheader.setClientApiUser("100001-Futu-4wK9zV5pC4");

			long timestamp = System.currentTimeMillis() / 1000;
			String iv = null;

			String amount = "100";

			if (cardType != null && !cardType.equalsIgnoreCase("")) {
				iv = tokenIV();
			}

			System.out.println("Enc Card Data==========================>" + enccardData
					+ "For transaction_id====================>" + log.getTransactionId());

			String secretKeyString = secrettokenKey;
			byte[] IV = iv.getBytes();

			if (enccardData == null) {
				SBITokenEncryptedData encryptedData = new SBITokenEncryptedData();
				encryptedData.setPan(usercardDetails.getCardNo());
				encryptedData.setExpiryYear(usercardDetails.getExpiryYear());
				encryptedData.setExpiryMonth(usercardDetails.getExpiryMonth());
				encryptedData.setSecurityCode(usercardDetails.getCvv());
				encryptedData.setCountryCode("IN");
				encryptedData.setEmail("alerts@getepay.in");
				System.out.println("User Data====================================>" + encryptedData);

				String jsonEncryptedData = gson.toJson(encryptedData);
				SecretKey secretKey = generateSecretKey(secretKeyString);
				byte[] plainText = jsonEncryptedData.getBytes();
				enccardData = tokenencrypt(plainText, secretKey, IV);
				System.out.println("SBI Encrypted data======>" + enccardData + "transaction id======================>"
						+ log.getTransactionId());

			}

			SBITokenRequest sbitokenRequest = new SBITokenRequest();

			sbitokenRequest.setEncryptedData(enccardData);

			if (cardType.equalsIgnoreCase("V")) {

				System.out.println("SBI Token Request for VISA=========================>"
						+ "For transaction_id=====================>" + log.getTransactionId());
				sbitokenRequest.setCardType(cardType);
				sbitokenRequest.setProvider("GC");
				sbitokenRequest.setTokenRequestorId(SBI_TOKEN_REQUESTOR_ID_VISA);
				sbitokenRequest.setUserConsent("Y");
				sbitokenRequest.setMerchantId(SBI_WIBMO_MERCHANT_ID); // Wibmo Merchant ID
				sbitokenRequest
						.setClientReferenceId(ClientReferenceId(Integer.valueOf(SBI_CLIENT_REFERENCE_ID_LENGTH)));
				sbitokenRequest.setIv(iv);
				sbitokenRequest.setAcquirerMerchantId(merchantsetting.getmPassword()); // Acquirer Merchant ID
				sbitokenRequest.setAcquirerInstanceId(merchantsetting.getSetting1()); // DPA ID
				sbitokenRequest.setAmount(String.valueOf(amount));
				sbitokenRequest.setCurrency("356");
				sbitokenRequest.setMerchantName(merchant.getMerchantName());
				if (cardType.equalsIgnoreCase("R")) {
					sbitokenRequest.setAuthCode(merchantsetting.getSetting9());

				}
			}

			else if (cardType.equalsIgnoreCase("M")) {

				System.out.println("SBI Token Request for MASTER=========================>"
						+ "For transaction_id==================>" + log.getTransactionId());
				sbitokenRequest.setCardType(cardType);
				sbitokenRequest.setProvider("GC");
				sbitokenRequest.setTokenRequestorId(SBI_TOKEN_REQUESTOR_ID_MASTER);
				sbitokenRequest.setUserConsent("Y");
				sbitokenRequest.setMerchantId(SBI_WIBMO_MERCHANT_ID); // change the value at uat test and live
				sbitokenRequest
						.setClientReferenceId(ClientReferenceId(Integer.valueOf(SBI_CLIENT_REFERENCE_ID_LENGTH)));
				sbitokenRequest.setIv(iv);
				sbitokenRequest.setAcquirerMerchantId(merchantsetting.getmPassword()); // Acquirer Merchant ID
				sbitokenRequest.setAcquirerInstanceId(merchantsetting.getSetting1()); // DPA ID
				sbitokenRequest.setAmount(String.valueOf(amount));
				sbitokenRequest.setCurrency("356");
				sbitokenRequest.setMerchantName(merchant.getMerchantName());
			}

			else if (cardType.equalsIgnoreCase("R")) {
				System.out.println("SBI Token Request for RUPAY=========================>"
						+ "For transaction_id==============>" + log.getTransactionId());
				sbitokenRequest.setCardType(cardType);
				sbitokenRequest.setProvider("GC");
				sbitokenRequest.setTokenRequestorId(SBI_TOKEN_REQUESTOR_ID_RUPAY);
				sbitokenRequest.setUserConsent("Y");
				sbitokenRequest.setMerchantId(SBI_WIBMO_MERCHANT_ID); // todo Bank
				sbitokenRequest
						.setClientReferenceId(ClientReferenceId(Integer.valueOf(SBI_CLIENT_REFERENCE_ID_LENGTH)));
				sbitokenRequest.setIv(iv);
				sbitokenRequest.setAcquirerMerchantId(merchantsetting.getmPassword()); // Acquirer Merchant ID
				sbitokenRequest.setAcquirerInstanceId(merchantsetting.getSetting1()); // DPA ID
				sbitokenRequest.setAmount(String.valueOf(amount));
				sbitokenRequest.setCurrency("356");
				sbitokenRequest.setMerchantName(merchant.getMerchantName());
				if (cardType.equalsIgnoreCase("R")) {
					sbitokenRequest.setAuthCode("1727073194893");

				}

			}

			String jsonReuqest = gson.toJson(sbitokenRequest);
			System.out.println("SBI Token Request=====================>" + jsonReuqest
					+ "For transaction_id============>" + log.getTransactionId());
			String vaultId = SBI_VAULT_ID;

			String XAuthToken = generateHashWithTS(vaultId, tokenheader.getClientId(), tokenheader.getClientApiUser(),
					tokenheader.getClientApiKey(), secretKeyString, timestamp);

			System.out.println("XAuthToken===================>" + XAuthToken + "For transaction_id===================>"
					+ log.getTransactionId());
			tokenheader.setAuthToken(XAuthToken);

			System.out.println("SBI Token Header=======>" + gson.toJson(tokenheader)
					+ "For transaction_id==============>" + log.getTransactionId());

			String response = SBICardsUtils.posttokenapi(CREATE_TOKEN, jsonReuqest, tokenheader);

			System.out.println("SBI Token Creation Response======>" + response + "For transaction_id ========>"
					+ log.getTransactionId());

			SBITokenResponse sbiTokenResponse = gson.fromJson(response, SBITokenResponse.class);

			System.out.println(sbiTokenResponse);
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}

	}

	public static String encrypt(String requestjson, AES aes) throws Exception {

		Gson gson = new Gson();
		try {

			String signedRequest = EncryptionUtil.digitalSignWithRSA(requestjson, Client.getPrivateKey());

			String encryptedRequest = EncryptionUtil.encrypt(signedRequest, aes);
			String encSymmetricKey = EncryptionUtil.encryptDEK(aes.getKey(), Server.getPublicKey());
			EncryptedRequestData encryptedRequestData = EncryptedRequestData.buildRequest(encryptedRequest,
					encSymmetricKey, aes);
			logger.info("Encrypted request : " + JsonUtil.getJsonString(encryptedRequestData));

			String encrequestjson = gson.toJson(encryptedRequestData);
			return encrequestjson;

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;

	}

	public static String decypt(EncryptedResponseData encryptedResponse, AES aes) {
		String signedResponse = null;
		try {
			if (encryptedResponse.getStatusCode().equals("PG99200")) {
				SecretKey decryptedSymmetricKey = DecryptionUtil
						.decryptDEK(encryptedResponse.getResponseSymmetricEncKey(), Client.getPrivateKey());
				signedResponse = DecryptionUtil.decrypt(encryptedResponse.getSignedEncResponsePayload(),
						encryptedResponse.getIv(), decryptedSymmetricKey);

				DecryptionUtil.verifySignature(signedResponse, Server.getPublicKey());
				logger.info("Decrypted Data" + DecryptionUtil.getJsonFromJws(signedResponse));

			}
			return DecryptionUtil.getJsonFromJws(signedResponse);
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}

		return null;

	}

	public static String generateHashWithTS(String vaultId, String clientId, String apiUser, String apiKey,
			String tokenSecretKey, long timestamp) {
		String digest = null;
		try {
			String data = timestamp + "|" + vaultId + "|" + clientId + "|" + apiUser + "|" + apiKey;
			System.out.println(data);
			digest = getDigest("HmacSHA256", tokenSecretKey, data, false);
			digest = "wtv1:" + timestamp + ":" + digest;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return digest;
	}

	private static String getDigest(String algorithm, String sharedSecret, String data, boolean toLower)
			throws SignatureException {
		try {
			Mac sha256HMAC = Mac.getInstance(algorithm);
			SecretKeySpec secretKey = new SecretKeySpec(sharedSecret.getBytes(StandardCharsets.UTF_8), algorithm);
			sha256HMAC.init(secretKey);
			byte[] hashByte = sha256HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
			String hashString = toHex(hashByte);
			return toLower ? hashString.toLowerCase() : hashString;
		} catch (Exception e) {
			throw new SignatureException(e);
		}
	}

	private static String toHex(byte[] bytes) {
		BigInteger bi = new BigInteger(1, bytes);
		return String.format("%0" + (bytes.length << 1) + "X", bi);
	}

	public static String tokenencrypt(byte[] plaintext, SecretKey key, byte[] IV) throws Exception {

		// Get Cipher Instance
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
		// Create SecretKeySpec
		SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
		// Create GCMParameterSpec
		GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, IV);
		// Initialize Cipher for ENCRYPT_MODE
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec); // Perform Encryption
		byte[] cipherText = cipher.doFinal(plaintext);
		return new String(Hex.encodeHex(cipherText));

	}

	private static SecretKey generateSecretKey(String secretKeyString) {
		secretKeyString = secretKeyString.replace("-", "");
		SecretKey secretKey = new SecretKeySpec(secretKeyString.getBytes(), 0, secretKeyString.length(), "AES");
		return secretKey;
	}

	public static String tokendecrypt(String data, String key, byte[] IV)
			throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
			IllegalBlockSizeException, InvalidAlgorithmParameterException, DecoderException {
		key = key.replace("-", "");
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), 0, key.length(), "AES");
		SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
		GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, IV);
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, gcmParameterSpec);
		String accountDetails = new String(cipher.doFinal(Hex.decodeHex(data.toCharArray())));
		return accountDetails;
	}

	private static byte[] generateSalt() {
		// Use a secure random number generator to generate the salt
		SecureRandom secureRandom = new SecureRandom();
		byte[] salt = new byte[16]; // Adjust the length based on your requirements
		secureRandom.nextBytes(salt);
		return salt;
	}

	public static String tokenIV() {
		try {

			SecureRandom secureRandom = new SecureRandom();

			byte[] iv = new byte[16];

			secureRandom.nextBytes(iv);

			StringBuilder hexString = new StringBuilder();
			for (byte b : iv) {
				hexString.append(String.format("%02x", b));
			}

			logger.info("Generated IV: " + hexString.toString());
			return hexString.toString();

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}

		return null;

	}

	public static String ClientReferenceId(int length) {

		try {

			SecureRandom random = new SecureRandom();
			StringBuilder stringBuilder = new StringBuilder(length);

			// Ensure at least one character of each type
			stringBuilder.append(UPPERCASE_LETTERS.charAt(random.nextInt(UPPERCASE_LETTERS.length())));
			stringBuilder.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
			stringBuilder.append(LOWERCASE_LETTERS.charAt(random.nextInt(LOWERCASE_LETTERS.length())));

			// Fill the rest of the string with random characters from all character sets
			for (int i = 3; i < length; i++) {
				stringBuilder.append(ALL_CHARACTERS.charAt(random.nextInt(ALL_CHARACTERS.length())));
			}

			// Shuffle the string to ensure random distribution
			for (int i = 0; i < stringBuilder.length(); i++) {
				int randomIndex = random.nextInt(stringBuilder.length());
				char temp = stringBuilder.charAt(i);
				stringBuilder.setCharAt(i, stringBuilder.charAt(randomIndex));
				stringBuilder.setCharAt(randomIndex, temp);
			}

			return stringBuilder.toString();

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}

		return null;

	}

	public static String expDate(String altTokenExp) {
		try {

			logger.info("Inside exp Date ==========================>");
			String numberToAdd = "20";

			// Create a new StringBuilder
			 String modifiedString = altTokenExp.substring(0, 2) + numberToAdd + altTokenExp.substring(2);
			 
			 return modifiedString;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		
		return null;

	}

}
