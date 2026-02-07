package com.ftk.pg.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftk.pg.dto.PaymentRequest;
import com.ftk.pg.dto.ResponseWrapper;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.Merchant;
import com.ftk.pg.modal.MerchantSetting;
import com.ftk.pg.modal.TransactionEssentials;
import com.ftk.pg.modal.TransactionLog;
import com.ftk.pg.repo.MerchantRepo;
import com.ftk.pg.repo.TransactionEssentialsRepo;
import com.ftk.pg.repo.TransactionLogRepo;
import com.ftk.pg.requestvo.FrmRiskRequest;
import com.ftk.pg.requestvo.PropertiesVo;
import com.ftk.pg.requestvo.RiskApiRequestVo;
import com.ftk.pg.requestvo.ValidationRequestDTO;
import com.ftk.pg.responsevo.FrmRiskResponse;
import com.ftk.pg.responsevo.PaymentResponse;
import com.ftk.pg.responsevo.RiskApiResponseVo;
import com.ftk.pg.util.ComponentUtils;
import com.ftk.pg.util.FrmEncryptionUtil;
import com.ftk.pg.util.FrmUtils;
import com.ftk.pg.util.PGUtility;
import com.ftk.pg.util.RiskApiCall;
import com.ftk.pg.util.UserCardDetails;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RiskService {

	Logger logger = LogManager.getLogger(RiskService.class);

	
	private final TransactionEssentialsRepo transactionEssentialsRepo;

	
	private final PropertiesService propertiesService;

	
	private final TransactionLogRepo transactionLogRepo;

	
	private  final CommonService commonService;

	
	private  final MerchantRepo merchantRepo;

	public PaymentResponse validateRiskParamByApiCall(PaymentRequest paymenRequest, PaymentResponse pgResponse) {

		try {
			logger.info("<------- Risk API Call -------->");

			RiskApiRequestVo requestVo = new RiskApiRequestVo();

			TransactionEssentials transactionEssentials = transactionEssentialsRepo
					.findByTransactionId(paymenRequest.getTransactionId());
			String ipAddress = "183.83.176.106";
			if (transactionEssentials != null) {
				ipAddress = transactionEssentials.getUdf60();
			}
			if (ipAddress.equalsIgnoreCase("null")) {
				ipAddress = "183.83.176.106";
			}
			logger.info(ipAddress);

			TransactionLog log = transactionLogRepo.findById(paymenRequest.getTransactionId()).get();
			String udf7 = "123456789";
			if (log != null && log.getUdf7() != null) {
				udf7 = log.getUdf7();
			}
			logger.info(udf7);

			// Priyam Chauhan //30092024
			Merchant merchant = merchantRepo.findByMidAndStatus(log.getMerchantId(), 1);
			MerchantSetting merchantSetting = null;

			if (merchant == null) {
				pgResponse.setResponseCode("01");
				pgResponse.setDescription("Unable to Validate the Merchant in Risk");
				return pgResponse;
			}

			UserCardDetails cardDetails = PGUtility.parseCardDetails(paymenRequest, merchant, merchantSetting);
			if (cardDetails == null) {
				pgResponse.setResponseCode("01");
				pgResponse.setDescription("Invalid Card data format in Risk ");
				return pgResponse;
			}
			List<PropertiesVo> propertiesList = propertiesService.findByPropertykeyWithUpdatedCertsLike("RISK_API_");
			Map<String, String> propertiesMap = new HashMap<>();
			for (PropertiesVo properties : propertiesList) {
				propertiesMap.put(properties.getPropertyKey(), properties.getPropertyValue());
			}
			String riskApiUrl = propertiesMap.get(ComponentUtils.RISK_API_URL);
			String apiKey = propertiesMap.get(ComponentUtils.RISK_API_HEADER_KEY);

			requestVo.setAmount(Double.valueOf(String.valueOf(paymenRequest.getAmt())));
			requestVo.setIp(ipAddress);
			requestVo.setMid(paymenRequest.getMerchantId());
			requestVo.setMobile(paymenRequest.getMobile());
			requestVo.setPayment_mode(paymenRequest.getPaymentMode());
			requestVo.setRrn(udf7);
			requestVo.setTransaction_id(paymenRequest.getTransactionId());
			requestVo.setUdf3(paymenRequest.getUdf3());
			requestVo.setCardNum(PGUtility.maskCard(cardDetails.getCardNo()));

			Gson gson = new Gson();

			String requestString = gson.toJson(requestVo);
			logger.info("riskrequestVo =>" + requestString);
			RiskApiCall riskApiCall = new RiskApiCall();
			String responseString = riskApiCall.callRiskApi(requestString, riskApiUrl, apiKey);
			logger.info("riskresponseString => " + responseString);

			RiskApiResponseVo response = riskApiCall.parseResponse(responseString);
			logger.info("riskresponse => " + responseString);

			pgResponse.setStatus("success");
			pgResponse.setDescription("validate successfully");
			pgResponse.setResult(true);
			pgResponse.setResponseCode("00");

			log.setRiskDescription(response.getDescription());
			log.setRiskStatus(0);

			if (response != null) {
				if (response.getStatus_code().equals("200")) {
					if (response.getRisk_status() == 0) {

						log.setRiskDescription(response.getDescription());
						log.setRiskStatus(0);

						pgResponse.setResult(true);
						pgResponse.setStatus("success");
						pgResponse.setResponseCode("00");
						pgResponse.setDescription("validate successfully");
					} else if (response.getRisk_status() == 1) {

						log.setRiskDescription(response.getDescription());
						log.setRiskStatus(1);

						pgResponse.setResult(true);
						pgResponse.setStatus("success");
						pgResponse.setResponseCode("00");
						pgResponse.setDescription(response.getDescription());
					} else if (response.getRisk_status() == 2) {

						log.setRiskDescription(response.getDescription());
						log.setRiskStatus(2);
						log.setHoldDate(new Date());
						log.setTxnStatus("FAILED");
						log.setUdf9(PGUtility.maskCard(cardDetails.getCardNo()));
						log.setPaymentMode(paymenRequest.getPaymentMode());

						pgResponse.setResult(false);
						pgResponse.setStatus("failed");
						pgResponse.setResponseCode("01");
						pgResponse.setDescription(response.getDescription());
					}
				}
			}

			transactionLogRepo.save(log);
			logger.info("risk status update " + log.getTransactionId() + " :: " + response.getDescription());

			return pgResponse;

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			logger.info("error in risk api call => " + e.getMessage());
		}

		pgResponse.setStatus("success");
		pgResponse.setDescription("validate successfully");
		pgResponse.setResult(true);
		pgResponse.setResponseCode("00");
		return pgResponse;

	}

	public PaymentResponse validateRiskParamByApiCallQR(TransactionLog transactionLog) {
		PaymentResponse pgResponse = new PaymentResponse();
		try {
			logger.info("<------- Risk API Call -------->");

			RiskApiRequestVo requestVo = new RiskApiRequestVo();

			TransactionEssentials transactionEssentials = transactionEssentialsRepo
					.findByTransactionId(transactionLog.getTransactionId());
			String ipAddress = "";
			if (transactionEssentials != null) {
				ipAddress = transactionEssentials.getUdf60();
			}
			logger.info(ipAddress);

			String udf7 = "123456789";
			if (transactionLog != null && transactionLog.getUdf7() != null) {
				udf7 = transactionLog.getUdf7();
			}
			logger.info(udf7);

			List<PropertiesVo> propertiesList = propertiesService.findByPropertykeyWithUpdatedCertsLike("RISK_API_");
			Map<String, String> propertiesMap = new HashMap<>();
			for (PropertiesVo properties : propertiesList) {
				propertiesMap.put(properties.getPropertyKey(), properties.getPropertyValue());
			}
			String riskApiUrl = propertiesMap.get(ComponentUtils.RISK_API_URL);
			String apiKey = propertiesMap.get(ComponentUtils.RISK_API_HEADER_KEY);

			requestVo.setAmount(Double.valueOf(String.valueOf(transactionLog.getAmt())));
			requestVo.setIp(ipAddress);
			requestVo.setMid(transactionLog.getMerchantId());
			requestVo.setMobile(transactionLog.getUdf1());
			requestVo.setPayment_mode(transactionLog.getPaymentMode());
			requestVo.setRrn(udf7);
			requestVo.setTransaction_id(transactionLog.getTransactionId());
			requestVo.setUdf8(transactionLog.getUdf8());

			Gson gson = new Gson();

			String requestString = gson.toJson(requestVo);
			logger.info("riskrequestVo =>" + requestString);
			RiskApiCall riskApiCall = new RiskApiCall();
			String responseString = riskApiCall.callRiskApi(requestString, riskApiUrl, apiKey);
			logger.info("riskresponseString => " + responseString);

			RiskApiResponseVo response = riskApiCall.parseResponse(responseString);
			logger.info("riskresponse => " + responseString);

			pgResponse.setStatus("success");
			pgResponse.setDescription("validate successfully");
			pgResponse.setResult(true);
			pgResponse.setResponseCode("00");

			transactionLog.setRiskDescription(response.getDescription());
			transactionLog.setRiskStatus(0);

			if (response != null) {
				if (response.getStatus_code().equals("200")) {
					if (response.getRisk_status() == 0) {

						transactionLog.setRiskDescription(response.getDescription());
						transactionLog.setRiskStatus(0);

						pgResponse.setResult(true);
						pgResponse.setStatus("success");
						pgResponse.setResponseCode("00");
						pgResponse.setDescription("validate successfully");
					} else if (response.getRisk_status() == 1) {

						transactionLog.setRiskDescription(response.getDescription());
						transactionLog.setRiskStatus(1);

						pgResponse.setResult(true);
						pgResponse.setStatus("success");
						pgResponse.setResponseCode("00");
						pgResponse.setDescription(response.getDescription());
					} else if (response.getRisk_status() == 2) {

						transactionLog.setRiskDescription(response.getDescription());
						transactionLog.setRiskStatus(2);
						transactionLog.setHoldDate(new Date());

						pgResponse.setResult(false);
						pgResponse.setStatus("failed");
						pgResponse.setResponseCode("01");
						pgResponse.setDescription(response.getDescription());
					}
				}
			}

			logger.info("risk status QR=>" + transactionLog.getTransactionId() + " :: " + response.getDescription());

			return pgResponse;

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			logger.info("error in risk api call => " + e.getMessage());
		}

		pgResponse.setStatus("success");
		pgResponse.setDescription("validate successfully");
		pgResponse.setResult(true);
		pgResponse.setResponseCode("00");
		return pgResponse;

	}

	public void riskApiCallForICICIQr(TransactionLog log) {
		try {
			PropertiesVo properties = propertiesService.findByPropertykeyWithUpdatedCerts("RISK_API_ENABLE");

			if (properties != null && properties.getPropertyValue().equalsIgnoreCase("true")) {
				validateRiskParamByApiCallQR(log);
			}

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
	}

	public void riskApiCallForAUQr(TransactionLog log) {
		try {
			PropertiesVo properties = propertiesService.findByPropertykeyWithUpdatedCerts("RISK_API_ENABLE");

			if (properties != null && properties.getPropertyValue().equalsIgnoreCase("true")) {
				validateRiskParamByApiCallQR(log);
			} else {
				commonService.validateTxnRisk(log);
			}
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
	}

	
//	@Override
	public PaymentResponse validateFrmRiskParamByApiCall(PaymentRequest paymenRequest, PaymentResponse pgResponse) {

		try {
			logger.info("<-------FRM Risk API Call -------->");

			FrmRiskRequest requestVo = new FrmRiskRequest();
			TransactionEssentials transactionEssentials = transactionEssentialsRepo.findByTransactionId(paymenRequest.getTransactionId());
			String ipAddress = "183.83.176.106";
			if (transactionEssentials != null) {
				ipAddress = transactionEssentials.getUdf60();
			}
			if (ipAddress.equalsIgnoreCase("null")) {
				ipAddress = "183.83.176.106";
			}
			logger.info(ipAddress);

			TransactionLog log = transactionLogRepo.findById(paymenRequest.getTransactionId()).get();
			String udf7 = "123456789";
			if (log != null && log.getUdf7() != null) {
				udf7 = log.getUdf7();
			}
			logger.info(udf7);

			// Priyam Chauhan //30092024
			Merchant merchant = merchantRepo.findByMid(log.getMerchantId());
			MerchantSetting merchantSetting = null;

			if (merchant == null) {
				pgResponse.setResponseCode("01");
				pgResponse.setDescription("Unable to Validate the Merchant in Risk");
				return pgResponse;
			}

			UserCardDetails cardDetails = PGUtility.parseCardDetails(paymenRequest, merchant, merchantSetting);
			if (cardDetails == null) {
				pgResponse.setResponseCode("01");
				pgResponse.setDescription("Invalid Card data format in Risk ");
				return pgResponse;
			}
			
			pgResponse.setStatus("success");
			pgResponse.setDescription("validate successfully");
			pgResponse.setResult(true);
			pgResponse.setResponseCode("00");

			log.setRiskDescription("No Risk");
			log.setRiskStatus(0);
			
			List<PropertiesVo> propertiesList = propertiesService.findByPropertykeyWithUpdatedCertsLike("FRM_RISK_API_");
			Map<String, String> propertiesMap = new HashMap<>();
			for (PropertiesVo properties : propertiesList) {
				propertiesMap.put(properties.getPropertyKey(), properties.getPropertyValue());
			}
			String riskApiUrl = propertiesMap.get(FrmUtils.FRM_RISK_API_URL_V2);

			Map<String, String> fields = new HashMap<>();
			fields.put("txnid", String.valueOf(paymenRequest.getTransactionId()));
			fields.put("rrnno", udf7);
			fields.put("amount", String.valueOf(paymenRequest.getAmt()));
			fields.put("mobileno", paymenRequest.getMobile());
			fields.put("paymentmode", paymenRequest.getPaymentMode());
			fields.put("ip", ipAddress);
			fields.put("mid", String.valueOf(merchant.getMid()));
			fields.put("email", log.getUdf3());

			if (paymenRequest.getPaymentMode() != null && (paymenRequest.getPaymentMode().equalsIgnoreCase("CC")
					|| paymenRequest.getPaymentMode().equalsIgnoreCase("DC"))) {

				fields.put("cardnumber", PGUtility.maskCard(cardDetails.getCardNo()));
				fields.put("cardholdername", log.getCustomername());

			} else if (paymenRequest.getPaymentMode() != null
					&& paymenRequest.getPaymentMode().equalsIgnoreCase("UPI")) {
				fields.put("payervpa", requestVo.getPAYER_VPA());
			}

			fields.put("date", log.getDate());

			Gson gson = new Gson();

			ValidationRequestDTO requestDTO = new ValidationRequestDTO();
			requestDTO.setFields(fields);
			String frmriskrequestjson = gson.toJson(requestDTO);

			logger.info("Frm Risk Request Json =====================>" + frmriskrequestjson);
			String encrypteddata = FrmEncryptionUtil.encrypt(frmriskrequestjson);
			logger.info("Frm Risk enc Request Json =====================>" + encrypteddata);

			
//			FrmEncryptedData frmEncryptedRequestData = new FrmEncryptedData();
//			frmEncryptedRequestData.setData(encrypteddata);
			JSONObject frmEncryptedRequestData = new JSONObject();
			frmEncryptedRequestData.put("data", encrypteddata);
			
			String responseString = FrmUtils.frmRiskApiCall2(riskApiUrl, frmEncryptedRequestData.toString());

			ResponseWrapper<String> jsonfrmResponse = gson.fromJson(responseString, ResponseWrapper.class);

			logger.info("Frm Risk Response========================> " + jsonfrmResponse);
			if (jsonfrmResponse.getStatus() == 200) {
			String decrypteddata = FrmEncryptionUtil.decrypt(jsonfrmResponse.getData());

			logger.info("Frm Risk decrypted Response========================> " + decrypteddata);
			FrmRiskResponse frmriskResponse = gson.fromJson(decrypteddata, FrmRiskResponse.class);

				if (frmriskResponse != null) {
					logger.info("risk status QR=>" + log.getTransactionId() + " :: "
							+ frmriskResponse.getErrors().get(0).getMessage());

					if (frmriskResponse.getErrors().size() > 0) {
						if (frmriskResponse.getRiskScore() >= 0 && frmriskResponse.getRiskScore() <= 200) {
							log.setRiskDescription(frmriskResponse.getErrors().get(0).getMessage());
							log.setRiskStatus(0);

							pgResponse.setResult(true);
							pgResponse.setStatus("success");
							pgResponse.setResponseCode("00");
							pgResponse.setDescription("validate successfully");
						} else if (frmriskResponse.getRiskScore() >= 201 && frmriskResponse.getRiskScore() <= 500) {
							log.setRiskDescription(frmriskResponse.getErrors().get(0).getMessage());
							log.setRiskStatus(1);

							pgResponse.setResult(true);
							pgResponse.setStatus("success");
							pgResponse.setResponseCode("00");
							pgResponse.setDescription("validate successfully");
						} else if (frmriskResponse.getRiskScore() >= 501) {
							log.setRiskDescription(frmriskResponse.getErrors().get(0).getMessage());
							log.setRiskStatus(2);
							log.setHoldDate(new Date());
							
							pgResponse.setResult(false);
							pgResponse.setStatus("failed");
							pgResponse.setResponseCode("01");
							pgResponse.setDescription(frmriskResponse.getErrors().get(0).getMessage());
						}
					} else {
						logger.info("Frm Risk Response Error list size null !!");
					}

				} else {
					logger.info("Frm Risk Response is null !! ");
				}

			} else {
				logger.info("Frm Risk Response status not valid !! ");
			}
			
			transactionLogRepo.save(log);
			return pgResponse;

		} catch (Exception e) {
			
			new GlobalExceptionHandler().customException(e);
		}

		pgResponse.setStatus("success");
		pgResponse.setDescription("validate successfully");
		pgResponse.setResult(true);
		pgResponse.setResponseCode("00");
		return pgResponse;
	}
	
	
}
