package com.ftk.pg.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fss.plugin.bob.iPayPipe;
import com.ftk.pg.dao.EmailConfigurationDao;
import com.ftk.pg.dao.SMSConfigurationDao;
import com.ftk.pg.dto.BankTxnResponseVo;
import com.ftk.pg.dto.RequestWrapper;
import com.ftk.pg.dto.ResponseBuilder;
import com.ftk.pg.dto.ResponseWrapper;
import com.ftk.pg.encryption.KmsOperations;
import com.ftk.pg.encryption.UcoEncryption;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.Email;
import com.ftk.pg.modal.EmailConfiguration;
import com.ftk.pg.modal.Merchant;
import com.ftk.pg.modal.MerchantSetting;
import com.ftk.pg.modal.SMS;
import com.ftk.pg.modal.SMSConfiguration;
import com.ftk.pg.modal.TransactionEssentials;
import com.ftk.pg.modal.TransactionLog;
import com.ftk.pg.pi.modal.IntermediateTransaction;
import com.ftk.pg.pi.repo.IntermediateTransactionRepo;
import com.ftk.pg.repo.EmailRepo;
import com.ftk.pg.repo.MerchantRepo;
import com.ftk.pg.repo.MerchantSettingRepo;
import com.ftk.pg.repo.SMSConfigurationRepo;
import com.ftk.pg.repo.SMSRepo;
import com.ftk.pg.repo.TransactionEssentialsRepo;
import com.ftk.pg.repo.TransactionLogRepo;
import com.ftk.pg.requestvo.AxisData;
import com.ftk.pg.requestvo.CardDetailsVo;
import com.ftk.pg.requestvo.PropertiesVo;
import com.ftk.pg.requestvo.TokenHeader;
import com.ftk.pg.responsevo.AuthorizeApiResponse;
import com.ftk.pg.responsevo.CashfreeResponse;
import com.ftk.pg.responsevo.PRqFrqFinalResponse;
import com.ftk.pg.responsevo.ParqAuthenticationResponse;
import com.ftk.pg.responsevo.PaymentResponse;
import com.ftk.pg.responsevo.RuResponseTxnVo;
import com.ftk.pg.responsevo.RuResponseWrapperVo;
import com.ftk.pg.responsevo.VerifyOTPResponse;
import com.ftk.pg.util.AES256EncDnc;
import com.ftk.pg.util.AxisUtils;
import com.ftk.pg.util.BillDeskUtils;
import com.ftk.pg.util.Constants;
import com.ftk.pg.util.HdfcUtils;
import com.ftk.pg.util.IDBINBBankUtils;
import com.ftk.pg.util.JoseHelper;
import com.ftk.pg.util.NCrossUtils;
import com.ftk.pg.util.NorthAcrossUtil;
import com.ftk.pg.util.PayubizUtil;
import com.ftk.pg.util.RemoteDbUtil;
import com.ftk.pg.util.SBICardsUtils;
import com.ftk.pg.util.SBIUtils;
import com.ftk.pg.util.SbiCardUtilCall;
import com.ftk.pg.util.TextDataRepalce;
import com.ftk.pg.util.UcoBankUtils;
import com.ftk.pg.util.Utilities;
import com.ftk.pg.util.VerifyJWS;
import com.ftk.pg.util.YESBankUtils;
import com.ftk.pg.vo.BillDeskFinalResponse;
import com.ftk.pg.vo.IDBINBResponse;
import com.ftk.pg.vo.NorthAcrossPaymentResponse;
import com.ftk.pg.vo.PayuResponseVo;
import com.ftk.pg.vo.UcoReponseVo;
import com.ftk.pg.vo.axis.AxisNbDoubleVerificationRequest;
import com.ftk.pg.vo.axis.AxisResponse;
import com.ftk.pg.vo.axis.DataSet;
import com.ftk.pg.vo.hdfccard.HdfcCardsResponse;
import com.ftk.pg.vo.iciciNb.ICICINBUtils;
import com.ftk.pg.vo.iciciNb.ICICINBVerificationRequest;
import com.ftk.pg.vo.iciciNb.ICICINetbankingResponseVo;
import com.ftk.pg.vo.iciciNb.IciciNbEncUtil;
import com.ftk.pg.vo.iciciNb.VerifyOutput;
import com.ftk.pg.vo.sbiNb.SBIPaymentRequest;
import com.ftk.pg.vo.sbiNb.SBIPaymentResponse;
import com.ftk.pg.vo.sbiNb.SBIRequestWrapper;
import com.ftk.pg.vo.sbiNb.SBITokenResponse;
import com.ftk.pg.vo.sbiNb.SBIVerificaltionResponse;
import com.ftk.pg.vo.sbiNb.SbiRequestHeader;
import com.ftk.pg.vo.sbiNb.SbiSaleAuthorizationResponse;
import com.ftk.pg.vo.yesBankNB.YesBankNBResponse;
import com.ftk.pg.vo.yesBankNB.YesBankNBVerifyRequest;
import com.ftk.pg.vo.yesBankNB.YesBankNBVerifyResponse;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.mb.getepay.icici.lyra.LyraChargeResponse;
import com.mb.getepay.icici.lyra.LyraUtil;
import com.mb.getepay.icici.lyra.action.Call;
import com.paynetz.payment.PaynetzPayment;
import com.paynetz.pojo.Config;
import com.paynetz.pojo.PaynetzPaymentResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RuResponseService {

	Logger logger = LogManager.getLogger(RuResponseService.class);

	private final PropertiesService propertiesService;

	private final TransactionLogRepo transactionLogRepo;

	private final MerchantSettingRepo merchantSettingRepo;

	private final MerchantRepo merchantRepo;

	private final CallBackService callbackService;

	private final TransactionEssentialsRepo transactionEssentialsRepo;

	private final SMSConfigurationDao smsConfigurationDao;

	private final SMSRepo smsRepo;

	private final EmailConfigurationDao emailConfigurationDao;

	private final EmailRepo emailRepo;
	
	private final IntermediateTransactionRepo intermediateTransactionRepo;

	
	public ResponseEntity<ResponseWrapper<String>> processRuResponse(RequestWrapper requestWrapper) {

		String requestData = requestWrapper.getData().toString();

		// DecryptRu
//		requestData = decryptResponse(requestData);

		// Parse reponse
		Gson gson = new Gson();
		RuResponseWrapperVo ruResponseWrapper = gson.fromJson(requestData, RuResponseWrapperVo.class);
//		String responseData = ruResponseWrapper.getData();
//		HashMap<String, String> eventMap = new Gson().fromJson(responseData, new TypeToken<HashMap<String, String>>() {
//		}.getType());

		TransactionLog transctionDetail = null;
		RuResponseTxnVo ruResponseTxnVo = null;
		String processorType = ruResponseWrapper.getType();
		Map<String, String> paramMap = ruResponseWrapper.getParamMap();
		Map<String, String> requestMap = ruResponseWrapper.getRequestMap();

		logger.info("processorType : " + processorType);
		logger.info("paramMap data : " + paramMap);
		logger.info("requestMap data : " + requestMap);

		
		final Map<String, ProcessorFunction> processorMap = new HashMap<>();
		    processorMap.put("bobResponse", (req, param) -> parseBobResponse(req));
		    processorMap.put("cfResponse", (req, param) -> parseCfResponse(req));
		    processorMap.put("aunbResponse", (req, param) -> parseAuNbResponse(req));
		    processorMap.put("getepaylResponse", (req, param) -> parseGetepayLyraResponse(req));
		    processorMap.put("getepaySimResponse", (req, param) -> parseGetepaySimResponse(req));
		    processorMap.put("sbinbResponse", (req, param) -> parseSbiNbResponse(req, param));
		    processorMap.put("yesnbResponse", (req, param) -> parseYesNbResponse(req));
		    processorMap.put("axisnbResponse", (req, param) -> parseAxisNbResponse(req));
		    processorMap.put("IDBINBResponse", (req, param) -> parseIDBINBResponse(req));
		    processorMap.put("payuResponse", (req, param) -> parsePayuResponse(req, param));
		    processorMap.put("billDeskResponse", (req, param) -> parseBillDeskResponse(req, param));
		    processorMap.put("getepayUResponse", (req, param) -> parseGetepayUcoResponse(req));
		    processorMap.put("paynetzResponse", (req, param) -> parsePaynetzResponse(param));
		    processorMap.put("iciciNbResponse", (req, param) -> parseIciciNbResponse(req, param));
		    processorMap.put("northAkrossResponse", (req, param) -> parseNorthAkrossResponse(req, param));
		    processorMap.put("hdfccardResponse", (req, param) -> parseHdfccardResponse(req, param));
		    processorMap.put("sbicardResponse", (req, param) -> parseSbicardResponse(req, param));
		    processorMap.put("sbirupayResponse", (req, param) -> parsesbirupayResponse(req, param));
		
		    ProcessorFunction function = processorMap.get(processorType);
		    if (function != null) {
		        ruResponseTxnVo = function.apply(requestMap, paramMap);
		    } else {
		        logger.error("Invalid processorType: {}", processorType);
		    } 
		    
		
		logger.info(" processor ruResponseTxnVo =========================>" + ruResponseTxnVo); 
		if (ruResponseTxnVo == null || ruResponseTxnVo.getTransctionDetail() == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Invalid Bank Response!! ", null));
		}
		
		
		transctionDetail = ruResponseTxnVo.getTransctionDetail();
		 
		 if (ruResponseTxnVo.isTxnDoubleVerifiedFlag()) {
				// double verified case ===============
				transctionDetail.setResponseCode(ruResponseTxnVo.getResponseCode());
				transctionDetail.setTxnStatus(ruResponseTxnVo.getTxnStatus());
				transctionDetail.setStage(ruResponseTxnVo.getStage());
				transctionDetail.setProcessorCode(ruResponseTxnVo.getProcessorCode());
				transctionDetail.setProcessorTxnId(ruResponseTxnVo.getProcessorTxnId());
				transctionDetail.setBankErrorMsg(ruResponseTxnVo.getBankErrorMsg());
				transctionDetail.setOrderNumber(ruResponseTxnVo.getOrderNumber());
				// update Transaction detail
				transactionLogRepo.save(transctionDetail);
				
		 } else {
				// requery case =====================
				transctionDetail.setResponseCode(ruResponseTxnVo.getResponseCode());
				transctionDetail.setTxnStatus(ruResponseTxnVo.getTxnStatus());
				transctionDetail.setStage(ruResponseTxnVo.getStage());
				transctionDetail.setProcessorCode(ruResponseTxnVo.getProcessorCode());
				transctionDetail.setProcessorTxnId(ruResponseTxnVo.getProcessorTxnId());
				transctionDetail.setBankErrorMsg(ruResponseTxnVo.getBankErrorMsg());
				transctionDetail.setOrderNumber(ruResponseTxnVo.getOrderNumber());
				
				// update Transaction detail
				transactionLogRepo.save(transctionDetail);
				
		 }
		

		IntermediateTransaction iTxnLog = intermediateTransactionRepo.findByTransactionId(Long.valueOf(transctionDetail.getMerchanttxnid()));
		
		if ( iTxnLog.getStatus().equalsIgnoreCase("SUCCESS") && transctionDetail.getTxnStatus().equalsIgnoreCase("SUCCESS")) {
			// Reversal Case 
			
		} else if ( iTxnLog.getStatus().equalsIgnoreCase("SUCCESS") && transctionDetail.getTxnStatus().equalsIgnoreCase("FAILED")) {
			// do nothing
		} else if (!iTxnLog.getStatus().equalsIgnoreCase("SUCCESS")) {
			
			iTxnLog.setStatus(transctionDetail.getTxnStatus());
			iTxnLog.setPaymentType(transctionDetail.getPaymentMode());
			iTxnLog.setProcessorId(transctionDetail.getTransactionId());
			intermediateTransactionRepo.save(iTxnLog);
			
			// txnSync // pgPush Notification......
			callbackService.addtopgPushNotifiactionQueue(transctionDetail);
			callbackService.addCallbackInQueue(transctionDetail.getTransactionId());
		}
		
		


		


		return ResponseBuilder.buildResponse("", "success", HttpStatus.OK);
	}

	private RuResponseTxnVo parsesbirupayResponse(Map<String, String> requestMap, Map<String, String> paramMap) {
		String tid = requestMap.get("tid");
		RuResponseTxnVo txnVo = sbirupayResponse(tid, paramMap);
		return txnVo;
	}

	private RuResponseTxnVo sbirupayResponse(String tid, Map<String, String> parameterMap) {
		TransactionLog transactionDetails = null;
		RuResponseTxnVo ruResponseTxnVo = null;
		try {

			logger.info("SBI Card  => tID : " + tid /* + " cres : " + cres + " otp : " + otp */);
			Gson gson = new Gson();

			String responseCode = "01";
			String txnStatus = "FAILED";
			String processorTxnId = null;;
			String bankErrorMsg = null;
			String cardType = "R";
			AuthorizeApiResponse authresponse = new AuthorizeApiResponse();
			CardDetailsVo carddetailsVo = new CardDetailsVo();
			SBITokenResponse sbitokenResponse = new SBITokenResponse();
			

			transactionDetails = transactionLogRepo.findById(Long.valueOf(tid.trim())).get();

			if (transactionDetails != null) {

				ruResponseTxnVo = new RuResponseTxnVo();
				ruResponseTxnVo.setTransctionDetail(transactionDetails);
				
				TransactionEssentials transactionEssentials = transactionEssentialsRepo
						.findByTransactionId(transactionDetails.getTransactionId());

				MerchantSetting merchantSetting = merchantSettingRepo
						.findById(transactionDetails.getMerchantSettingId()).get();

				Merchant merchant = merchantRepo.findByMid(transactionDetails.getMerchantSettingId());

				List<PropertiesVo> properties = propertiesService.findAllPropertyWithUpdatedCerts();
				Map<String, String> propertiesMap = new HashMap<String, String>();
				for (PropertiesVo property : properties) {
					propertiesMap.put(property.getPropertyKey(), property.getPropertyValue());
				}


				String secretKeyString = propertiesMap.get(SBICardsUtils.SBI_TOKEN_DEC_SECRET_KEY);
				
				TokenHeader tokenheader = new TokenHeader();
				tokenheader.setClientId(propertiesMap.get(SBICardsUtils.SBI_TOKEN_CLIENT_ID));
				tokenheader.setClientApiKey(propertiesMap.get(SBICardsUtils.SBI_TOKEN_CLIENT_API_KEY));
				tokenheader.setClientApiUser(propertiesMap.get(SBICardsUtils.SBI_TOKEN_CLIENT_API_USER));

				logger.info("SBI   token Header============>" + gson.toJson(tokenheader)
						+ " For transaction_id=============================>" + transactionDetails.getTransactionId());

				SbiRequestHeader header = new SbiRequestHeader();
				header.setXapikey(propertiesMap.get(SBICardsUtils.SBI_HEADER_XAPI_KEY));
				header.setPgInstanceId(propertiesMap.get(SBICardsUtils.SBI_RUPAY_HEADER_PG_INSTANCE_ID));
				header.setMerchantId(propertiesMap.get(SBICardsUtils.SBI_RUPAY_HEADER_MERCHANT_ID));

				logger.info("Rupay Response Headers======>" + header
						+ " For transaction_id=============================>" + transactionDetails.getTransactionId());

				BigDecimal mAmount = requaryAmount(transactionDetails);
				int amount = mAmount.multiply(new BigDecimal(100)).intValue();

				if (merchantSetting.getProcessor() != null
						&& merchantSetting.getProcessor().equalsIgnoreCase("SBI BANK")) {
					if (parameterMap.get("productType") != null
							&& parameterMap.get("productType").equalsIgnoreCase("SEAMLESS")) {

						VerifyOTPResponse verifyOtpResponse = SbiCardUtilCall.verifyotp(
								parameterMap.get("pgTransactionId"), parameterMap.get("otp"), header, propertiesMap, merchant,
								merchantSetting, transactionDetails);
						if (verifyOtpResponse != null && verifyOtpResponse.getErrorcode().equalsIgnoreCase("450")) {
//							model.addAttribute("cardNo", parameterMap.get("cardNo"));
//							model.addAttribute("amount", String.valueOf(Utilities.requaryAmount(transactionDetails)));
//							model.addAttribute("merchantName", transactionDetails.getMerchantName());
//							model.addAttribute("tId", transactionDetails.getTransactionId());
//							model.addAttribute("productType", parameterMap.get("productType"));
//							model.addAttribute("pgTransactionId", parameterMap.get("pgTransactionId"));
//							model.addAttribute("message", verifyOtpResponse.getErrormsg());
//							return "sbirupay-otp";
						}

						if (verifyOtpResponse != null && verifyOtpResponse.getErrorcode().equalsIgnoreCase("00")) {

							transactionEssentials.setUdf40(verifyOtpResponse.getTranCtxId()); // TransCtxId
							transactionEssentialsRepo.save(transactionEssentials);
							
							sbitokenResponse = SbiCardUtilCall.generateAltIdToken(carddetailsVo, tokenheader, propertiesMap,
									merchant, merchantSetting, transactionDetails, transactionEssentials, cardType,
									amount, transactionEssentials.getUdf41());

							if (sbitokenResponse != null
									&& sbitokenResponse.getStatusCode().equalsIgnoreCase("TK0000")) {

								authresponse = SbiCardUtilCall.authorize(header, propertiesMap, merchant, merchantSetting,
										transactionDetails, sbitokenResponse, secretKeyString, parameterMap);
								if (authresponse != null && authresponse.getStatus().equalsIgnoreCase("50020")) {
									responseCode = "00";
									txnStatus = "SUCCESS";
									processorTxnId = authresponse.getRrn();
//									transactionDetails.setProcessorTxnId(authresponse.getRrn());
								} else {
									responseCode = "01";
									txnStatus = "FAILED";
									bankErrorMsg = authresponse.getPgErrorDetail();
//									transactionDetails.setBankErrorMsg(authresponse.getPgErrorDetail());
								}
							} else {
								responseCode = "01";
								txnStatus = "FAILED";
								bankErrorMsg = sbitokenResponse.getErrorDesc();
//								transactionDetails.setBankErrorMsg(sbitokenResponse.getErrorDesc());
							}
						}

						else {
							responseCode = "01";
							txnStatus = "FAILED";
						}
					} else {
						try {

							sbitokenResponse = SbiCardUtilCall.generateAltIdToken(carddetailsVo, tokenheader, propertiesMap,
									merchant, merchantSetting, transactionDetails, transactionEssentials, cardType,
									amount, transactionEssentials.getUdf41());

							if (sbitokenResponse != null
									&& sbitokenResponse.getStatusCode().equalsIgnoreCase("TK0000")) {

								authresponse = SbiCardUtilCall.authorize(header, propertiesMap, merchant, merchantSetting,
										transactionDetails, sbitokenResponse, secretKeyString, parameterMap);
								if (authresponse != null && authresponse.getStatus().equalsIgnoreCase("50020")) {
									responseCode = "00";
									txnStatus = "SUCCESS";
									processorTxnId = authresponse.getRrn();
//									transactionDetails.setProcessorTxnId(authresponse.getRrn());
								} else {
									responseCode = "01";
									txnStatus = "FAILED";
									bankErrorMsg = authresponse.getPgErrorDetail();
//									transactionDetails.setBankErrorMsg(authresponse.getPgErrorDetail());
								}
							} else {
								responseCode = "01";
								txnStatus = "FAILED";
								bankErrorMsg = sbitokenResponse.getErrorDesc();
//								transactionDetails.setBankErrorMsg(sbitokenResponse.getErrorDesc());
							}

						} catch (Exception e) {
							new GlobalExceptionHandler().customException(e);
						}
					}

				}

//				transactionDetails.setResponseCode(responseCode);
//				transactionDetails.setTxnStatus(txnStatus);
//				transactionDetails.setStage("Transaction is successfully processed. ");
//				transactionDetails.setProcessorCode("00");
//				transactionDetails.setProcessorTxnId(processorTxnId);
//				transactionDetails.setBankErrorMsg(bankErrorMsg);
				
				ruResponseTxnVo.setResponseCode(responseCode);
				ruResponseTxnVo.setTxnStatus(txnStatus);
				ruResponseTxnVo.setStage("Transaction is successfully processed. ");
				ruResponseTxnVo.setProcessorCode("00");
				ruResponseTxnVo.setProcessorTxnId(processorTxnId);
				ruResponseTxnVo.setBankErrorMsg(bankErrorMsg);
//				ruResponseTxnVo.setOrderNumber(bankPCode);
				ruResponseTxnVo.setTxnDoubleVerifiedFlag(true);

			}
			
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
//		return transactionDetails;	
		return ruResponseTxnVo;
	}
	
	private RuResponseTxnVo parseSbicardResponse(Map<String, String> requestMap, Map<String, String> paramMap) {
		String tid = requestMap.get("tid");
		RuResponseTxnVo txnVo = sbicardsResponse(tid, paramMap);
		return txnVo;
	}

	private RuResponseTxnVo sbicardsResponse(String tid, Map<String, String> parameterMap) {

		TransactionLog transactionDetails = null;
		RuResponseTxnVo ruResponseTxnVo = null;
		try {

			logger.info("SBI Card  => tID : " + tid /* + " cres : " + cres + " otp : " + otp */);
			Gson gson = new Gson();
			SbiSaleAuthorizationResponse saleauthResponse = new SbiSaleAuthorizationResponse();
			ParqAuthenticationResponse parqAuthenticationResponse = new ParqAuthenticationResponse();
			PRqFrqFinalResponse prqFrqFinalResponse = new PRqFrqFinalResponse();

			String responseCode = "01";
			String txnStatus = "FAILED";
			String processorTxnId = null;;
			String bankErrorMsg = null;

			List<PropertiesVo> properties = propertiesService.findAllPropertyWithUpdatedCerts();
			Map<String, String> propertiesMap = new HashMap<String, String>();
			for (PropertiesVo property : properties) {
				propertiesMap.put(property.getPropertyKey(), property.getPropertyValue());
			}

			transactionDetails = transactionLogRepo.findById(Long.valueOf(tid.trim())).get();

			if (transactionDetails != null) {

				ruResponseTxnVo = new RuResponseTxnVo();
				ruResponseTxnVo.setTransctionDetail(transactionDetails);
				
				TransactionEssentials transactionEssentials = transactionEssentialsRepo
						.findByTransactionId(transactionDetails.getTransactionId());

				MerchantSetting merchantSetting = merchantSettingRepo
						.findById(transactionDetails.getMerchantSettingId()).get();

				Merchant merchant = merchantRepo.findByMid(transactionDetails.getMerchantSettingId());

				String secretKeyString = propertiesMap.get(SBICardsUtils.SBI_TOKEN_DEC_SECRET_KEY);

				SbiRequestHeader header = new SbiRequestHeader();

				header.setXapikey(propertiesMap.get(SBICardsUtils.SBI_HEADER_XAPI_KEY)); // todo bank
				header.setPgInstanceId(propertiesMap.get(SBICardsUtils.SBI_ACQUIRERID));// aquirer bin
				header.setMerchantId(propertiesMap.get(SBICardsUtils.SBI_ACQUIRERID));
				logger.info("Header REQUEST" + header + " For transaction_id=============================>"
						+ transactionDetails.getTransactionId());

				BigDecimal saleAuthamount = requaryAmount(transactionDetails);

				if (parameterMap.get("cres") != null && parameterMap.containsKey("cres")) {

					logger.info("The ParameterMap Response ==========>cres"
							+ " For transaction_id=============================>"
							+ transactionDetails.getTransactionId());
					prqFrqFinalResponse = SbiCardUtilCall.PRqFrqFinalRequest(header, transactionEssentials,
							merchantSetting, transactionDetails, parameterMap, propertiesMap);
					if (prqFrqFinalResponse != null) {
						if (!prqFrqFinalResponse.getErrorCode().equals("")
								&& prqFrqFinalResponse.getErrorCode().equals("000")) {

							header.setPgInstanceId(propertiesMap.get(SBICardsUtils.SBI_PG_INSTENCE_ID_AUTH_SALE));
							header.setMerchantId(propertiesMap.get(SBICardsUtils.SBI_PG_INSTENCE_ID_AUTH_SALE));

							logger.info("Header REQUEST" + header
									+ " For AuthSale transaction_id=============================>"
									+ transactionDetails.getTransactionId());
							saleauthResponse = SbiCardUtilCall.saleAuth(header, merchant, merchantSetting,
									transactionEssentials, transactionDetails, prqFrqFinalResponse, parameterMap,
									parqAuthenticationResponse, secretKeyString, propertiesMap, saleAuthamount);
							if (!saleauthResponse.getStatus().equals("")
									&& saleauthResponse.getStatus().equals("50020")) {
								responseCode = "00";
								txnStatus = "SUCCESS";
								processorTxnId = saleauthResponse.getTransactionId();
//								transactionDetails.setProcessorTxnId(saleauthResponse.getTransactionId());
							} else {
								responseCode = "01";
								txnStatus = "FAILED";
								bankErrorMsg = saleauthResponse.getPgErrorDetail();
//								transactionDetails.setBankErrorMsg(saleauthResponse.getPgErrorDetail());
							}

						} else {
							responseCode = "01";
							txnStatus = "FAILED";
							bankErrorMsg = prqFrqFinalResponse.getErrorDescription();
//							transactionDetails.setBankErrorMsg(prqFrqFinalResponse.getErrorDescription());
						}
					} else {
						responseCode = "01";
						txnStatus = "FAILED";
					}

				} else if (parameterMap.get("parqAuthenticationResponse") != null
						&& parameterMap.containsKey("parqAuthenticationResponse")) {

					logger.info("The ParameterMap Response ==========> parqAuthenticationResponse"
							+ " For transaction_id=============================>"
							+ transactionDetails.getTransactionId());
					parqAuthenticationResponse = gson.fromJson(parameterMap.get("parqAuthenticationResponse"),
							parqAuthenticationResponse.getClass());

					header.setPgInstanceId(propertiesMap.get(SBICardsUtils.SBI_PG_INSTENCE_ID_AUTH_SALE));
					header.setMerchantId(propertiesMap.get(SBICardsUtils.SBI_PG_INSTENCE_ID_AUTH_SALE));
					logger.info("Header REQUEST" + header + " For AuthSale transaction_id=============================>"
							+ transactionDetails.getTransactionId());
					saleauthResponse = SbiCardUtilCall.saleAuth(header, merchant, merchantSetting,
							transactionEssentials, transactionDetails, prqFrqFinalResponse, parameterMap,
							parqAuthenticationResponse, secretKeyString, propertiesMap, saleAuthamount);
					if (!saleauthResponse.getStatus().equals("") && saleauthResponse.getStatus().equals("50020")) {
						responseCode = "00";
						txnStatus = "SUCCESS";
						processorTxnId = saleauthResponse.getTransactionId();
//						transactionDetails.setProcessorTxnId(saleauthResponse.getTransactionId());
					} else {
						responseCode = "01";
						txnStatus = "FAILED";
						bankErrorMsg = saleauthResponse.getPgErrorDetail();
//						transactionDetails.setBankErrorMsg(saleauthResponse.getPgErrorDetail());
					}
				}

				else if (parameterMap.get("pares") != null && parameterMap.containsKey("pares")) {

					logger.info("The ParameterMap Response ==========> pares"
							+ " For transaction_id=============================>"
							+ transactionDetails.getTransactionId());
					prqFrqFinalResponse = SbiCardUtilCall.PRqFrqFinalRequest(header, transactionEssentials,
							merchantSetting, transactionDetails, parameterMap, propertiesMap);
					if (prqFrqFinalResponse != null) {
						if (!prqFrqFinalResponse.getErrorCode().equals("")
								&& prqFrqFinalResponse.getErrorCode().equals("000")) {

							header.setPgInstanceId(propertiesMap.get(SBICardsUtils.SBI_PG_INSTENCE_ID_AUTH_SALE));
							header.setMerchantId(propertiesMap.get(SBICardsUtils.SBI_PG_INSTENCE_ID_AUTH_SALE));
							logger.info("Header REQUEST" + header
									+ " For AuthSale transaction_id=============================>"
									+ transactionDetails.getTransactionId());
							saleauthResponse = SbiCardUtilCall.saleAuth(header, merchant, merchantSetting,
									transactionEssentials, transactionDetails, prqFrqFinalResponse, parameterMap,
									parqAuthenticationResponse, secretKeyString, propertiesMap, saleAuthamount);

							if (!saleauthResponse.getStatus().equals("")
									&& saleauthResponse.getStatus().equals("50020")) {
								responseCode = "00";
								txnStatus = "SUCCESS";
								processorTxnId = saleauthResponse.getTransactionId();
//								transactionDetails.setProcessorTxnId(saleauthResponse.getTransactionId());
							} else {
								responseCode = "01";
								txnStatus = "FAILED";
								bankErrorMsg = saleauthResponse.getPgErrorDetail();
//								transactionDetails.setBankErrorMsg(saleauthResponse.getPgErrorDetail());
							}

						} else {
							responseCode = "01";
							txnStatus = "FAILED";
							bankErrorMsg = prqFrqFinalResponse.getErrorDescription();
//							transactionDetails.setBankErrorMsg(prqFrqFinalResponse.getErrorDescription());
						}
					} else {
						responseCode = "01";
						txnStatus = "FAILED";
					}

				}

//				transactionDetails.setResponseCode(responseCode);
//				transactionDetails.setTxnStatus(txnStatus);
//				transactionDetails.setStage("Transaction is successfully processed. ");
//				transactionDetails.setProcessorCode("00");
//				transactionDetails.setProcessorTxnId(processorTxnId);
//				transactionDetails.setBankErrorMsg(bankErrorMsg);
				// transactionDetails.setOrderNumber();
//				transactionLogRepo.save(transactionDetails);

				ruResponseTxnVo.setResponseCode(responseCode);
				ruResponseTxnVo.setTxnStatus(txnStatus);
				ruResponseTxnVo.setStage("Transaction is successfully processed. ");
				ruResponseTxnVo.setProcessorCode("00");
				ruResponseTxnVo.setProcessorTxnId(processorTxnId);
				ruResponseTxnVo.setBankErrorMsg(bankErrorMsg);
//				ruResponseTxnVo.setOrderNumber(bankPCode);
				ruResponseTxnVo.setTxnDoubleVerifiedFlag(true);
			}

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
//		return transactionDetails;
		return ruResponseTxnVo;

	}
	
	

	private RuResponseTxnVo parseHdfccardResponse(Map<String, String> requestMap, Map<String, String> paramMap) {
		String tid = requestMap.get("tid");
		RuResponseTxnVo txnVo = hdfccardResponse(tid, paramMap);
		return txnVo;
	}

	private RuResponseTxnVo hdfccardResponse(String tid, Map<String, String> parameterMap) {
		TransactionLog transactionDetails = null;
		RuResponseTxnVo ruResponseTxnVo = null;
		try {
			
			String responseCode = "01";
			String txnStatus = "FAILED";

			transactionDetails = transactionLogRepo.findById(Long.valueOf(tid.trim())).get();

			if (transactionDetails != null) {

				ruResponseTxnVo = new RuResponseTxnVo();
				ruResponseTxnVo.setTransctionDetail(transactionDetails);
				
				List<PropertiesVo> properties = propertiesService.findAllPropertyWithUpdatedCerts();
				Map<String, String> propertiesMap = new HashMap<String, String>();
				for (PropertiesVo property : properties) {
					propertiesMap.put(property.getPropertyKey(), property.getPropertyValue());
				}

//				TransactionEssentials transactionEssentials = transactionEssentialsRepo
//						.findByTransactionId(transactionDetails.getTransactionId());
//				MerchantSetting merchantSetting = merchantSettingRepo
//						.findById(transactionDetails.getMerchantSettingId()).get();
//				Merchant merchant = merchantRepo.findByMid(transactionDetails.getMerchantSettingId());
				
				String transdata = parameterMap.get("trandata");
				logger.info("HDFC Response Transaction Data===========>" + transdata);

				String decryptedData = HdfcUtils.decryptTextAes(propertiesMap.get(HdfcUtils.HDFC_CARDS_ENCRYPTION_KEY),
						transdata);
				logger.info("Decrypted Data ============================>" + decryptedData);
				
				String wrappedXml = "<root>" + decryptedData + "</root>"; // Wrap in <root> for valid XML structure
				HdfcCardsResponse hdfcCardResponse = HdfcUtils.parseDecryptedData(wrappedXml);
				logger.info("HdfcCardsResponse Data ============================>" + hdfcCardResponse);

				if (hdfcCardResponse != null && hdfcCardResponse.getResult().equalsIgnoreCase("CAPTURED")) {
					responseCode = "00";
					txnStatus = "SUCCESS";
//					transactionDetails.setProcessorTxnId(String.valueOf(hdfcCardResponse.getPaymentid()));
					ruResponseTxnVo.setProcessorTxnId(String.valueOf(hdfcCardResponse.getPaymentid()));

				} else {
					responseCode = "01";
					txnStatus = "FAILED";
				}
//				transactionDetails.setResponseCode(responseCode);
//				transactionDetails.setTxnStatus(txnStatus);
//				transactionDetails.setStage("Transaction is successfully processed. ");
//				transactionDetails.setProcessorCode("00");
//				transactionLogRepo.save(transactionDetails);
				
				ruResponseTxnVo.setResponseCode(responseCode);
				ruResponseTxnVo.setTxnStatus(txnStatus);
				ruResponseTxnVo.setStage("Transaction is successfully processed. ");
				ruResponseTxnVo.setProcessorCode("00");
//				ruResponseTxnVo.setProcessorTxnId(northAcrossPaymentResponse.getTransaction_id());
//				ruResponseTxnVo.setBankErrorMsg();
//				ruResponseTxnVo.setOrderNumber(bankPCode);
				ruResponseTxnVo.setTxnDoubleVerifiedFlag(false);
			}
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
//		return transactionDetails;	
		return ruResponseTxnVo;
	}

	

	private RuResponseTxnVo parseNorthAkrossResponse(Map<String, String> requestMap, Map<String, String> paramMap) {
		String tId = requestMap.get("tId");
		RuResponseTxnVo txnVo = northAkrossResponse(tId, paramMap);
		return txnVo;
	}

	private RuResponseTxnVo northAkrossResponse(String tId, Map<String, String> paramMap) {

		TransactionLog transactionDetails = null;
		RuResponseTxnVo ruResponseTxnVo = null;
		try {

			String apiKey = paramMap.get("api_key");
			String encryptedData = paramMap.get("encrypted_data");
			String response = null;
			NCrossUtils nCrossUtils = new NCrossUtils();
			String responseCode = "01";
			String txnStatus = "FAILED";
			Gson gson = new Gson();

			transactionDetails = transactionLogRepo.findById(Long.valueOf(tId.trim())).get();
			if (transactionDetails != null) {

				ruResponseTxnVo = new RuResponseTxnVo();
				ruResponseTxnVo.setTransctionDetail(transactionDetails);
				
				MerchantSetting merchantSetting = merchantSettingRepo
						.findById(transactionDetails.getMerchantSettingId()).get();

				List<PropertiesVo> properties = propertiesService.findAllPropertyWithUpdatedCerts();
				Map<String, String> propertiesMap = new HashMap<String, String>();
				for (PropertiesVo property : properties) {
					propertiesMap.put(property.getPropertyKey(), property.getPropertyValue());
				}

				NorthAcrossPaymentResponse northAcrossPaymentResponse = null;
				String decrypted = null;
				if (merchantSetting != null && merchantSetting.getSetting6() != null
						&& merchantSetting.getSetting6().length() > 0) {
					decrypted = NorthAcrossUtil.decrypt(encryptedData, merchantSetting.getSetting6());
					logger.info("Decrypted Data==> " + decrypted);
					northAcrossPaymentResponse = gson.fromJson(decrypted, NorthAcrossPaymentResponse.class);
					logger.info("NetbankingResponseVo => " + northAcrossPaymentResponse);
				}
				Map<String, String> mapUtils = new HashMap<String, String>();
				mapUtils = nCrossUtils.propertyData(merchantSetting, propertiesMap);

				if (decrypted != null && !decrypted.equals("")) {

					Boolean hash = NorthAcrossUtil.ReturnHashCalculate(decrypted, merchantSetting.getSetting1(),
							northAcrossPaymentResponse.getHash());

					if (hash) {
						if (northAcrossPaymentResponse != null
								&& northAcrossPaymentResponse.getResponse_code().equals("0")) {
							responseCode = "00";
							txnStatus = "SUCCESS";
						} else {
							responseCode = "01";
							txnStatus = "FAILED";
						}
					}
				}

//				transactionDetails.setResponseCode(responseCode);
//				transactionDetails.setTxnStatus(txnStatus);
//				transactionDetails.setStage("Transaction is successfully processed. ");
//				transactionDetails.setProcessorCode("00");
//				transactionDetails.setProcessorTxnId(northAcrossPaymentResponse.getTransaction_id());

//				transactionLogRepo.save(transactionDetails);
				
				ruResponseTxnVo.setResponseCode(responseCode);
				ruResponseTxnVo.setTxnStatus(txnStatus);
				ruResponseTxnVo.setStage("Transaction is successfully processed. ");
				ruResponseTxnVo.setProcessorCode("00");
				ruResponseTxnVo.setProcessorTxnId(northAcrossPaymentResponse.getTransaction_id());
//				ruResponseTxnVo.setBankErrorMsg();
//				ruResponseTxnVo.setOrderNumber(bankPCode);
				ruResponseTxnVo.setTxnDoubleVerifiedFlag(false);
			}

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
//		return transactionDetails;
		return ruResponseTxnVo;
	}

	private RuResponseTxnVo parseIciciNbResponse(Map<String, String> requestMap, Map<String, String> paramMap) {

		String tId = requestMap.get("tid");
		RuResponseTxnVo txnVo = iciciNbResponse(tId, paramMap);
		return txnVo;
	}

	private RuResponseTxnVo iciciNbResponse(String tId, Map<String, String> paramMap) {
		TransactionLog transactionDetails = null;
		RuResponseTxnVo ruResponseTxnVo = null;
		try {

			String responseCode = "01";
			String txnStatus = "FAILED";
			ICICINBVerificationRequest verificationrequest = new ICICINBVerificationRequest();

			transactionDetails = transactionLogRepo.findById(Long.parseLong(tId.trim())).get();
			if (transactionDetails != null) {

				ruResponseTxnVo = new RuResponseTxnVo();
				ruResponseTxnVo.setTransctionDetail(transactionDetails);
				
				MerchantSetting merchantSetting = merchantSettingRepo
						.findById(transactionDetails.getMerchantSettingId()).get();

				List<PropertiesVo> properties = propertiesService.findAllPropertyWithUpdatedCerts();
				Map<String, String> propertiesMap = new HashMap<String, String>();
				for (PropertiesVo property : properties) {
					propertiesMap.put(property.getPropertyKey(), property.getPropertyValue());
				}

				ICICINetbankingResponseVo iciciNetbankingResponseVo = null;
				if (merchantSetting != null && merchantSetting.getmPassword() != null
						&& merchantSetting.getmPassword().length() > 0) {
					String decrypted = IciciNbEncUtil.decrypt(merchantSetting.getmPassword(), paramMap.get("ES"));
					logger.info("Decrypted Data==> " + decrypted);
					iciciNetbankingResponseVo = new ICICINetbankingResponseVo(decrypted);
					logger.info("ICICINetbankingResponseVo => " + iciciNetbankingResponseVo);

				}
				transactionDetails = transactionLogRepo
						.findById(Long.valueOf(iciciNetbankingResponseVo.getPRN().trim())).get();

				if (iciciNetbankingResponseVo != null && iciciNetbankingResponseVo.getPAID() != null
						&& iciciNetbankingResponseVo.getPAID().equalsIgnoreCase("Y")) {

					String verificationurl = propertiesMap.get(Utilities.ICICI_NB_VERIFICATION_URL_KEY);

					logger.info("Verfication Url============>" + verificationurl);
					verificationrequest.setAMT(String.valueOf(requaryAmount(transactionDetails)));
					verificationrequest.setBID(iciciNetbankingResponseVo.getBID());
					verificationrequest.setCRN("INR");
					verificationrequest.setITC(String.valueOf(transactionDetails.getMerchanttxnid()));
					verificationrequest.setMD("V");
					verificationrequest.setPID(merchantSetting.getSetting1());
					verificationrequest.setPRN(String.valueOf(transactionDetails.getTransactionId()));
					logger.info("verfication Request==========>" + verificationrequest);
					verificationurl = verificationurl.replace("#login", merchantSetting.getMloginId());
					verificationurl = verificationurl.replace("#md", verificationrequest.getMD());
					verificationurl = verificationurl.replace("#pid", merchantSetting.getSetting1());
					verificationurl = verificationurl.replace("#prn", verificationrequest.getPRN());
					verificationurl = verificationurl.replace("#itc", verificationrequest.getITC());
					verificationurl = verificationurl.replace("#amt", verificationrequest.getAMT());
					verificationurl = verificationurl.replace("#crn", verificationrequest.getCRN());
					verificationurl = verificationurl.replace("#bid", verificationrequest.getBID());

					logger.info("Final verfication Url=============>" + verificationurl);

					VerifyOutput verifyOutput = ICICINBUtils.getApi(verificationurl);
					logger.info("Verify Output=====================>" + verifyOutput);
					if (verifyOutput != null && verifyOutput.getVERIFIED().equalsIgnoreCase("Success")) {
						responseCode = "00";
						txnStatus = "SUCCESS";
					}
				}
//				transactionDetails.setResponseCode(responseCode);
//				transactionDetails.setTxnStatus(txnStatus);
//				transactionDetails.setStage("Transaction is successfully processed. ");
//				transactionDetails.setProcessorCode("00");
//				transactionDetails.setProcessorTxnId(iciciNetbankingResponseVo.getBID());
//				transactionLogRepo.save(transactionDetails);
				
				ruResponseTxnVo.setResponseCode(responseCode);
				ruResponseTxnVo.setTxnStatus(txnStatus);
				ruResponseTxnVo.setStage("Transaction is successfully processed. ");
				ruResponseTxnVo.setProcessorCode("00");
				ruResponseTxnVo.setProcessorTxnId(iciciNetbankingResponseVo.getBID());
//				ruResponseTxnVo.setBankErrorMsg();
//				ruResponseTxnVo.setOrderNumber(bankPCode);
				ruResponseTxnVo.setTxnDoubleVerifiedFlag(true);
			}

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
//		return transactionDetails;
		return ruResponseTxnVo;

	}

	private RuResponseTxnVo parsePaynetzResponse(Map<String, String> paramMap) {
		RuResponseTxnVo txnVo = paynetzResponse(paramMap);
		return txnVo;
	}

	private RuResponseTxnVo paynetzResponse(Map<String, String> request) {
		
		TransactionLog transactionDetails = null;
		RuResponseTxnVo ruResponseTxnVo = null;
		try {

			Set<Entry<String, String>> enums = request.entrySet();
			for (Entry<String, String> entry : enums) {
				String fieldName = (String) entry.getKey();
				String fieldValue = entry.getValue();

				logger.info(fieldName + " : " + fieldValue + "<br>");
				logger.info(fieldName + " : " + fieldValue + "<br>");
			}

			SMS sms = new SMS();
			Email email = new Email();
			String mmp_txn = request.get("mmp_txn");
			String mer_txn = request.get("mer_txn");
			String f_code = request.get("f_code");
			String prod = request.get("prod");
			String discriminator = request.get("discriminator");
			String amt = request.get("amt");
			String bank_txn = request.get("bank_txn");
			String signature = request.get("signature");
			PaynetzPaymentResponse paynetzPaymentRes = new PaynetzPaymentResponse();
			paynetzPaymentRes.setAmt(amt);
			Config config = new Config();
			// this is request Signature
			paynetzPaymentRes.setSignature(signature);
			paynetzPaymentRes.setMmp_txn(mmp_txn);
			paynetzPaymentRes.setMer_txn(mer_txn);
			paynetzPaymentRes.setF_code(f_code);
			paynetzPaymentRes.setProdid(prod);
			paynetzPaymentRes.setDiscriminator(discriminator);
			paynetzPaymentRes.setBank_txn(bank_txn);
			logger.info("validate Request => " + paynetzPaymentRes);

			logger.info("Processor Response ==> mmp_txn :: " + mmp_txn
					+ " mer_txn :: " + mer_txn + " f_code :: " + f_code + " prod :: " + prod + " discriminator ::"
					+ discriminator + " amt :: " + amt + " bank_txn ::" + bank_txn + " signature ::" + signature);
			
			String resHashKey = "";
			Merchant merchant = null;
			String atomTxnId = mer_txn;
			logger.info("AtomTxnID:: " + atomTxnId);
			transactionDetails = transactionLogRepo.findById(Long.parseLong(atomTxnId)).get();

			if (transactionDetails != null) {

				ruResponseTxnVo = new RuResponseTxnVo();
				ruResponseTxnVo.setTransctionDetail(transactionDetails);
				
				MerchantSetting merchantSetting = merchantSettingRepo
						.findById(transactionDetails.getMerchantSettingId()).get();
				resHashKey = merchantSetting.getSetting3();
				logger.info("ResHashKey => " + resHashKey);

				config.setResponsetHashKey(resHashKey);
				PaynetzPayment payment = new PaynetzPayment(config);
				boolean validateResponse = payment.validateResponse(paynetzPaymentRes);
				logger.info("validate Sign => " + validateResponse);

				String merchTxnId = transactionDetails.getMerchanttxnid();
				logger.info("MerchTxnId:: " + merchTxnId);
				
				String txnStatus = "";
				String responseCode = "";
				String stage = "";

				if (f_code != null && f_code.equalsIgnoreCase("ok") && validateResponse) {
					responseCode = "00";
					txnStatus = "SUCCESS";
					stage = "Transaction is successfully done. ";
//					transactionDetails.setProcessorCode(responseCode);
//					transactionDetails.setOrderNumber(mmp_txn);
					ruResponseTxnVo.setProcessorCode(responseCode);
					ruResponseTxnVo.setOrderNumber(mmp_txn);
				} else {
					responseCode = "01";
					txnStatus = "FAILED";
					stage = "Transaction is failed.";
//					transactionDetails.setBankErrorMsg(request.get("discriminator"));
					ruResponseTxnVo.setBankErrorMsg(request.get("discriminator"));
				}

//				transactionDetails.setResponseCode(responseCode);
//				transactionDetails.setTxnStatus(txnStatus);
//				transactionDetails.setStage(stage);
//				transactionLogRepo.save(transactionDetails);

				ruResponseTxnVo.setResponseCode(responseCode);
				ruResponseTxnVo.setTxnStatus(txnStatus);
				ruResponseTxnVo.setStage(stage);
//				ruResponseTxnVo.setProcessorTxnId();
				ruResponseTxnVo.setTxnDoubleVerifiedFlag(false);
				
				merchant = merchantRepo.findByMidAndStatus(merchantSetting.getMerchantId(), 1);

				// For SMS Sending Block

				SMSConfiguration smsConfiguration = new SMSConfiguration();
				smsConfiguration.setMid(merchant.getMid());
				smsConfiguration.setIsCustomer(true);
				smsConfiguration.setTriggerCode("FTK-2");

				SMSConfiguration customerSmsConfig = smsConfigurationDao
						.findAllSmsConfigByNameAndTriggerName(smsConfiguration);

				if (customerSmsConfig != null) {
					sms = new SMS();
					sms.setmName(customerSmsConfig.getmName());
					sms.setCusNumber(transactionDetails.getUdf2());
					String smsBody = customerSmsConfig.getSmsBody();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("TXN-AMT", transactionDetails.getTxncurr().toUpperCase() + "-" + amt);
					map.put("TXN-ID", merchTxnId);
					map.put("TXN-STS", txnStatus);
					map.put("MER-NAME", merchant.getMerchantName());
					map.put("REF-STS", transactionDetails.getRefundStatus());
					map.put("SETT-STS", transactionDetails.getSettlementStatus());
					String data = smsBody;
					String textFromModel = TextDataRepalce.getTextFromModel(map, data);
					sms.setSmsBody(textFromModel);
					sms.setSendFor("customer");
					sms.setStatus(true);
					sms = smsRepo.save(sms);

					if (sms != null && sms.getId() > 0) {
						logger.info("SMS Is Saved Against Id: " + sms.getId());
					} else {
						logger.info("SMS Is Not Saved ");
					}
				}

				smsConfiguration.setIsCustomer(false);
				smsConfiguration.setIsMerchant(true);
				SMSConfiguration merchantSmsConfig = smsConfigurationDao
						.findAllSmsConfigByNameAndTriggerName(smsConfiguration);

				if (merchantSmsConfig != null) {
					sms = new SMS();
					sms.setmName(merchantSmsConfig.getmName());
					sms.setMerNumber(merchant.getMobileNumber());
					String smsBody = merchantSmsConfig.getSmsBody();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("TXN-AMT", transactionDetails.getTxncurr().toUpperCase() + "-" + amt);
					map.put("TXN-ID", merchTxnId);
					map.put("TXN-STS", txnStatus);
					map.put("MER-NAME", merchant.getMerchantName());
					map.put("REF-STS", transactionDetails.getRefundStatus());
					map.put("SETT-STS", transactionDetails.getSettlementStatus());
					String data = smsBody;
					String textFromModel = TextDataRepalce.getTextFromModel(map, data);
					sms.setSmsBody(textFromModel);
					sms.setStatus(true);
					sms.setSendFor("merchant");
					sms = smsRepo.save(sms);
					if (sms != null && sms.getId() > 0) {
						logger.info("SMS Is Saved Against Id: " + sms.getId());
					} else {
						logger.info("SMS Is Not Saved ");
					}
				}
				smsConfiguration.setIsCustomer(true);
				SMSConfiguration smsConfigForBoth = smsConfigurationDao
						.findAllSmsConfigByNameAndTriggerName(smsConfiguration);
				if (smsConfigForBoth != null) {
					sms = new SMS();
					sms.setmName(smsConfigForBoth.getmName());
					sms.setCusNumber(transactionDetails.getUdf2());
					sms.setMerNumber(merchant.getMobileNumber());
					String smsBody = smsConfigForBoth.getSmsBody();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("TXN-AMT", transactionDetails.getTxncurr().toUpperCase() + "-" + amt);
					map.put("TXN-ID", merchTxnId);
					map.put("TXN-STS", txnStatus);
					map.put("MER-NAME", merchant.getMerchantName());
					map.put("REF-STS", transactionDetails.getRefundStatus());
					map.put("SETT-STS", transactionDetails.getSettlementStatus());
					String data = smsBody;
					String textFromModel = TextDataRepalce.getTextFromModel(map, data);
					sms.setSmsBody(textFromModel);
					sms.setStatus(true);
					sms.setSendFor("cusAndmer");
					sms = smsRepo.save(sms);
					if (sms != null && sms.getId() > 0) {
						logger.info("SMS Is Saved Against Id: " + sms.getId());
					} else {
						logger.info("SMS Is Not Saved ");
					}
				}

				// For Email Sending Block

				EmailConfiguration emailConfiguration = new EmailConfiguration();
				emailConfiguration.setmId(merchant.getMid());
				emailConfiguration.setCustomer(true);
				emailConfiguration.setTriggerCode("FTK-2");
				EmailConfiguration customerConfig = emailConfigurationDao
						.findEmailConfigByTriggerCode(emailConfiguration);
				if (customerConfig != null) {
					email = new Email();
					email.setCustomerMail(transactionDetails.getUdf3());
					email.setSubject(customerConfig.getSubject());
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("TXN-AMT", transactionDetails.getTxncurr().toUpperCase() + "-" + amt);
					map.put("TXN-ID", merchTxnId);
					map.put("TXN-STS", txnStatus);
					map.put("MER-NAME", merchant.getMerchantName());
					map.put("REF-STS", transactionDetails.getRefundStatus());
					map.put("SETT-STS", transactionDetails.getSettlementStatus());
					String data = customerConfig.getBody();
					String textFromModel = TextDataRepalce.getTextFromModel(map, data);
					email.setBody(textFromModel);
					email.setStatus(true);
					email.setSendFor("customer");
					emailRepo.save(email);

				}
				emailConfiguration.setMerchant(true);
				emailConfiguration.setCustomer(false);
				EmailConfiguration merchantConfig = emailConfigurationDao
						.findEmailConfigByTriggerCode(emailConfiguration);

				if (merchantConfig != null) {
					email = new Email();
					email.setMerchantMail(merchant.getEmail());
					email.setBcc(merchantConfig.getBcc());
					email.setCc(merchantConfig.getCc());
					email.setTo(merchantConfig.getTo());
					email.setSubject(merchantConfig.getSubject());
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("TXN-AMT", transactionDetails.getTxncurr().toUpperCase() + "-" + amt);
					map.put("TXN-ID", merchTxnId);
					map.put("TXN-STS", txnStatus);
					map.put("MER-NAME", merchant.getMerchantName());
					map.put("REF-STS", transactionDetails.getRefundStatus());
					map.put("SETT-STS", transactionDetails.getSettlementStatus());
					String data = merchantConfig.getBody();
					String textFromModel = TextDataRepalce.getTextFromModel(map, data);
					email.setBody(textFromModel);
					email.setStatus(true);
					email.setSendFor("merchant");
					emailRepo.save(email);
				}

				emailConfiguration.setCustomer(true);
				EmailConfiguration customerAndmerchantConfig = emailConfigurationDao
						.findEmailConfigByTriggerCode(emailConfiguration);

				if (customerAndmerchantConfig != null) {
					email = new Email();
					email.setMerchantMail(merchant.getEmail());
					email.setCustomerMail(transactionDetails.getUdf3());
					email.setCc(customerAndmerchantConfig.getCc());
					email.setSubject(customerAndmerchantConfig.getSubject());
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("TXN-AMT", transactionDetails.getTxncurr().toUpperCase() + "-" + amt);
					map.put("TXN-ID", merchTxnId);
					map.put("TXN-STS", txnStatus);
					map.put("MER-NAME", merchant.getMerchantName());
					map.put("REF-STS", transactionDetails.getRefundStatus());
					map.put("SETT-STS", transactionDetails.getSettlementStatus());
					String data = customerAndmerchantConfig.getBody();
					String textFromModel = TextDataRepalce.getTextFromModel(map, data);
					email.setBody(textFromModel);
					email.setStatus(true);
					email.setSendFor("cusAndmer");
					emailRepo.save(email);
				}

			}

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
//		return transactionDetails;
		return ruResponseTxnVo;
	}

	private RuResponseTxnVo parseGetepayUcoResponse(Map<String, String> requestMap) {
		RuResponseTxnVo txnVo = getepayUResponse(requestMap);
		return txnVo;
	}

	private RuResponseTxnVo getepayUResponse(Map<String, String> requestMap) {

		TransactionLog transactionDetails = null;
		RuResponseTxnVo ruResponseTxnVo = null;
		try {

			String msg = "";
			for (Entry<String, String> entry : requestMap.entrySet()) {
				msg = entry.getKey();
				break;
			}
			logger.info("msg => " + msg);

			List<PropertiesVo> properties = propertiesService.findAllPropertyWithUpdatedCerts();
			Map<String, String> propertiesMap = new HashMap<String, String>();
			for (PropertiesVo property : properties) {
				propertiesMap.put(property.getPropertyKey(), property.getPropertyValue());
			}

			String encrypt = msg.replaceAll(" ", "+");
			logger.info("Encrypted data==> " + encrypt);
			String decrypted = UcoEncryption.decrypt(encrypt, propertiesMap.get(UcoBankUtils.UCO_NB_ENCRYPTION_KEY),
					"AES", 256);
			logger.info("Decrypted Data==> " + decrypted);
			UcoReponseVo ucoReponseVo = new UcoReponseVo(decrypted);
			logger.info("ucoReponseVo => " + ucoReponseVo);

			transactionDetails = transactionLogRepo.findById(Long.valueOf(ucoReponseVo.getPrn().trim())).get();

			if (transactionDetails != null) {

				ruResponseTxnVo = new RuResponseTxnVo();
				ruResponseTxnVo.setTransctionDetail(transactionDetails);
				
				String responseCode = "01";
				String txnStatus = "FAILED";
				
				if (ucoReponseVo != null && ucoReponseVo.getPaid().equalsIgnoreCase("Y")) {
					responseCode = "00";
					txnStatus = "SUCCESS";
				} else if (ucoReponseVo != null && ucoReponseVo.getPaid().equalsIgnoreCase("P")) {
					responseCode = "01";
					txnStatus = "PENDING";
				}

//				transactionDetails.setResponseCode(responseCode);
//				transactionDetails.setTxnStatus(txnStatus);
//				transactionDetails.setStage("Transaction is successfully processed. ");
//				transactionDetails.setProcessorCode("00");
//				transactionDetails.setProcessorTxnId(idbiResponse.getPAID());
//				transactionLogRepo.save(transactionDetails);

				ruResponseTxnVo.setResponseCode(responseCode);
				ruResponseTxnVo.setTxnStatus(txnStatus);
				ruResponseTxnVo.setStage("Transaction is successfully processed. ");
				ruResponseTxnVo.setProcessorCode("00");
				ruResponseTxnVo.setProcessorTxnId(ucoReponseVo.getBid());
//				ruResponseTxnVo.setBankErrorMsg();
//				ruResponseTxnVo.setOrderNumber(bankPCode);
				ruResponseTxnVo.setTxnDoubleVerifiedFlag(false);
				
			}

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
//		return transactionDetails;
		return ruResponseTxnVo;
	}

	private RuResponseTxnVo parseBillDeskResponse(Map<String, String> requestMap, Map<String, String> paramMap) {
		String tId = requestMap.get("tid");

		RuResponseTxnVo txnVo = billDeskResponse(tId, paramMap);
		return txnVo;
	}

	private RuResponseTxnVo billDeskResponse(String tId, Map<String, String> paramMap) {

		TransactionLog transactionDetails = null;
		RuResponseTxnVo ruResponseTxnVo = null;
		String transaction_response = paramMap.get("paramMap");

		BillDeskFinalResponse billdeskFinalResponse = new BillDeskFinalResponse();
		try {
			transactionDetails = transactionLogRepo.findById(Long.valueOf(tId)).get();
			if (transactionDetails != null) {

				ruResponseTxnVo = new RuResponseTxnVo();
				ruResponseTxnVo.setTransctionDetail(transactionDetails);
				
//				List<PropertiesVo> properties = propertiesService.findAllPropertyWithUpdatedCerts();
				List<PropertiesVo> properties = propertiesService.findByPropertykeyWithUpdatedCertsLike("BILL_DESK");
				Map<String, String> propMap = new HashMap<String, String>();
				for (PropertiesVo property : properties) {
					propMap.put(property.getPropertyKey(), property.getPropertyValue());
				}

				logger.info("Value of filled===>" + transaction_response);
				String todecrypt = transaction_response.replace(" ", "+");
				logger.info("Replace Space to + string=====>" + todecrypt);
				RSAPublicKey encryptionKey = (RSAPublicKey) VerifyJWS
						.getPublicKey(propMap.get(BillDeskUtils.BILL_DESK_ENC_KEY));
				RSAPrivateKey privateKey = (RSAPrivateKey) BillDeskUtils
						.decyptionPrivateKey(propMap.get(BillDeskUtils.BILL_DESK_GETEPAY_PRIVATE_DEC_KEY));
				String decResponse = JoseHelper.verifyAndDecrypt(transaction_response, encryptionKey, privateKey);
				ObjectMapper objectMapper = new ObjectMapper();
				billdeskFinalResponse = objectMapper.readValue(decResponse, BillDeskFinalResponse.class);
				logger.info("BIllDesk Final Response ==========>" + billdeskFinalResponse);

				String responseCode = "01";
				String txnStatus = "FAILED";
				String stage = "";
				if (billdeskFinalResponse != null
						&& billdeskFinalResponse.getTransaction_error_type().equalsIgnoreCase("success")) {
					responseCode = "00";
					txnStatus = "SUCCESS";
					stage = "Transaction is successfully processed. ";
				} else {
					responseCode = "01";
					txnStatus = "FAILED";
					stage = "Transaction is FAILED. ";
				}
				
//				transactionDetails.setResponseCode(responseCode);
//				transactionDetails.setTxnStatus(txnStatus);
//				transactionDetails.setProcessorCode("00");
//				transactionDetails.setStage(stage);
//				transactionDetails.setProcessorTxnId(billdeskFinalResponse.getTransactionid());
//				transactionDetails.setBankErrorMsg(billdeskFinalResponse.getTransaction_error_desc());
//				transactionLogRepo.save(transactionDetails);
				
				ruResponseTxnVo.setResponseCode(responseCode);
				ruResponseTxnVo.setTxnStatus(txnStatus);
				ruResponseTxnVo.setProcessorCode("00");
				ruResponseTxnVo.setStage(stage);
				ruResponseTxnVo.setProcessorTxnId(billdeskFinalResponse.getTransactionid());
				ruResponseTxnVo.setBankErrorMsg(billdeskFinalResponse.getTransaction_error_desc());
//				ruResponseTxnVo.setOrderNumber(bankPCode);
				ruResponseTxnVo.setTxnDoubleVerifiedFlag(false);
			}

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
//		return transactionDetails;
		return ruResponseTxnVo;
	}

	private RuResponseTxnVo parsePayuResponse(Map<String, String> requestMap, Map<String, String> paramMap) {

		RuResponseTxnVo txnVo = payuResponse(requestMap, paramMap);
		return txnVo;
	}

	private RuResponseTxnVo payuResponse(Map<String, String> requestMap, Map<String, String> responseMaps) {
		
		TransactionLog transactionDetails = null;
		RuResponseTxnVo ruResponseTxnVo = null;
		try {

			Gson gson = new Gson();
			String requestJson = gson.toJson(requestMap);
			PayuResponseVo payuResponse = new Gson().fromJson(requestJson, PayuResponseVo.class);
			
			BigDecimal convinenceCharge = BigDecimal.ZERO;
			if (payuResponse != null && payuResponse.getTxnid() != null && !payuResponse.getTxnid().trim().equals("")) {

				transactionDetails = transactionLogRepo.findById(Long.parseLong(payuResponse.getTxnid().trim())).get();

				if (transactionDetails != null) {

					ruResponseTxnVo = new RuResponseTxnVo();
					ruResponseTxnVo.setTransctionDetail(transactionDetails);
					
					MerchantSetting merchantSetting = merchantSettingRepo
							.findById(transactionDetails.getMerchantSettingId()).get();

//					List<PropertiesVo> properties = propertiesService.findAllPropertyWithUpdatedCerts();
//					Map<String, String> propertiesMap = new HashMap<String, String>();
//					for (PropertiesVo property : properties) {
//						propertiesMap.put(property.getPropertyKey(), property.getPropertyValue());
//					}

					String generatedChecksum = PayubizUtil.calculateResponseHash(responseMaps,
							merchantSetting.getmPassword());
					String payuHash = payuResponse.getHash();

					String response = payuResponse.getStatus();


					String bankPCode = payuResponse.getMihpayid();

					BigDecimal txnAmount = transactionDetails.getAmt();
					if (transactionDetails.getCommisionType() != null
							&& transactionDetails.getCommisionType().equalsIgnoreCase("excl")) {

						logger.info("Commision  Charges========>" + transactionDetails.getCommision());
						txnAmount = txnAmount.add(transactionDetails.getCommision());
					}

					if (transactionDetails.getServiceChargeType() != null
							&& transactionDetails.getServiceChargeType().equalsIgnoreCase("Excl")) {
						logger.info("PayResponse Convience Charge");
						convinenceCharge = new BigDecimal(transactionDetails.getTotalServiceCharge());
						convinenceCharge = convinenceCharge.setScale(2, RoundingMode.UP);
						logger.info("Convience Charges========>" + convinenceCharge);
						txnAmount = txnAmount.add(convinenceCharge);

					}

					logger.info("Txn Amount" + txnAmount);

					BigDecimal payUAmt = new BigDecimal(payuResponse.getAmount());
					logger.info("Pay U Amount" + payUAmt);

					boolean isValidAmt = true;
					String responseCode = "01";
					String txnStatus = "FAILED";
					if (txnAmount.compareTo(payUAmt) != 0) {
						logger.info("Amount mismatched=> " + payuResponse.getAmount());
						isValidAmt = false;
					}

					if (response != null && response.equalsIgnoreCase("success")
							&& generatedChecksum.equalsIgnoreCase(payuHash) && isValidAmt) {
						responseCode = "00";
						txnStatus = "SUCCESS";
					}

//					transactionDetails.setResponseCode(responseCode);
//					transactionDetails.setTxnStatus(txnStatus);
//					transactionDetails.setStage("Transaction is successfully processed. ");
//					transactionDetails.setProcessorCode("00");
//					transactionDetails.setOrderNumber(bankPCode);
//					transactionDetails.setBankErrorMsg(payuResponse.getError_Message());
//					transactionLogRepo.save(transactionDetails);
					
					ruResponseTxnVo.setResponseCode(responseCode);
					ruResponseTxnVo.setTxnStatus(txnStatus);
					ruResponseTxnVo.setStage("Transaction is successfully processed. ");
					ruResponseTxnVo.setProcessorCode("00");
					ruResponseTxnVo.setOrderNumber(bankPCode);
					ruResponseTxnVo.setBankErrorMsg(payuResponse.getError_Message());
//					ruResponseTxnVo.setProcessorTxnId();
					ruResponseTxnVo.setTxnDoubleVerifiedFlag(false);
					
				}

			} else {
				logger.info(" null Payu response =  " + payuResponse);
			}

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
//		return transactionDetails;
		return ruResponseTxnVo;
	}

	private RuResponseTxnVo parseIDBINBResponse(Map<String, String> requestMap) {
		RuResponseTxnVo txnVo = idbiNbResponse(requestMap);
		return txnVo;
	}

	private RuResponseTxnVo idbiNbResponse(Map<String, String> requestMap) {

		TransactionLog transactionDetails = null;
		RuResponseTxnVo ruResponseTxnVo = null;
		try {

			String msg = "";
			for (Entry<String, String> entry : requestMap.entrySet()) {
				msg = entry.getKey();
				break;
			}
			logger.info("msg => " + msg);

			List<PropertiesVo> properties = propertiesService.findAllPropertyWithUpdatedCerts();
			Map<String, String> propertiesMap = new HashMap<String, String>();
			for (PropertiesVo property : properties) {
				propertiesMap.put(property.getPropertyKey(), property.getPropertyValue());
			}

			String encrypt = msg.replaceAll(" ", "+");
			logger.info("Encrypted data==> " + encrypt);
			String decrypted = UcoEncryption.decrypt(encrypt, propertiesMap.get(IDBINBBankUtils.IDBI_NB_ENCRYPTION_KEY),
					"AES", 256);
			logger.info("Decrypted Data==> " + decrypted);
			IDBINBResponse idbiResponse = new IDBINBResponse(decrypted);
			logger.info("ucoReponseVo => " + idbiResponse);

			transactionDetails = transactionLogRepo.findById(Long.valueOf(idbiResponse.getPRN().trim())).get();

			String responseCode = "01";
			String txnStatus = "FAILED";

			if (transactionDetails != null) {

				ruResponseTxnVo = new RuResponseTxnVo();
				ruResponseTxnVo.setTransctionDetail(transactionDetails);
				
				if (idbiResponse != null && idbiResponse.getPAID().equalsIgnoreCase("Y")) {
					responseCode = "00";
					txnStatus = "SUCCESS";
				} else if (idbiResponse != null && idbiResponse.getPAID().equalsIgnoreCase("P")) {
					responseCode = "01";
					txnStatus = "PENDING";
				}

//				transactionDetails.setResponseCode(responseCode);
//				transactionDetails.setTxnStatus(txnStatus);
//				transactionDetails.setStage("Transaction is successfully processed. ");
//				transactionDetails.setProcessorCode("00");
//				transactionDetails.setProcessorTxnId(idbiResponse.getPAID());

//				transactionLogRepo.save(transactionDetails);
				
				ruResponseTxnVo.setResponseCode(responseCode);
				ruResponseTxnVo.setTxnStatus(txnStatus);
				ruResponseTxnVo.setStage("Transaction is successfully processed. ");
				ruResponseTxnVo.setProcessorCode("00");
				ruResponseTxnVo.setProcessorTxnId(idbiResponse.getPAID());
//				ruResponseTxnVo.setBankErrorMsg(bankError);
				ruResponseTxnVo.setTxnDoubleVerifiedFlag(false);

			}

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
//		return transactionDetails;
		return ruResponseTxnVo;
	}

	private RuResponseTxnVo parseAxisNbResponse(Map<String, String> requestMap) {
		String tId = requestMap.get("tid");
		String encdata = requestMap.get("encdata");
		RuResponseTxnVo txnVo = axisResponse(tId, encdata);
		return txnVo;
	}

	private RuResponseTxnVo axisResponse(String tId, String encdata) {

		TransactionLog transactionDetails = null;
		RuResponseTxnVo ruResponseTxnVo = null;
		try {

			transactionDetails = transactionLogRepo.findById(Long.valueOf(tId)).get();

			if (transactionDetails != null) {

				ruResponseTxnVo = new RuResponseTxnVo();
				ruResponseTxnVo.setTransctionDetail(transactionDetails);
				
				List<PropertiesVo> properties = propertiesService.findAllPropertyWithUpdatedCerts();
				Map<String, String> propertiesMap = new HashMap<String, String>();
				for (PropertiesVo property : properties) {
					propertiesMap.put(property.getPropertyKey(), property.getPropertyValue());
				}

				MerchantSetting merchantSetting = merchantSettingRepo
						.findById(transactionDetails.getMerchantSettingId()).get();

				logger.info("Deccodded Data==============================================>" + encdata);

				encdata = encdata.replaceAll(" ", "+");
				String decrypted = AxisUtils.qsDecrypt(propertiesMap.get(AxisUtils.AXIS_NB_SALT),
						propertiesMap.get(AxisUtils.AXIS_NB_IV), propertiesMap.get(AxisUtils.AXIS_NB_SECRET_KEY),
						encdata);

				logger.info("decrypted Data==============================================>" + decrypted);
				AxisResponse response = new AxisResponse(decrypted);

				logger.info("Decrypted Response ==============================================>" + response);

				AxisNbDoubleVerificationRequest request = new AxisNbDoubleVerificationRequest();
				request.setAmt(response.getAMT());
				String txnDate = transactionDetails.getDate().trim();
				try {
					txnDate = txnDate.split(Pattern.quote(" "))[0];
				} catch (Exception e) {
				}
				request.setDate(txnDate);
				request.setItc(transactionDetails.getMerchanttxnid());
				request.setPayeeid(merchantSetting.getSetting1());
				request.setPrn(String.valueOf(transactionDetails.getTransactionId()));
				String checksumRequest = request.checksumpipeSeprated();
				logger.info("AXIS NB Double Verification Checksum Request================>" + checksumRequest);
				String checksumvalue = AES256EncDnc.getSHA256(checksumRequest);
				logger.info("AXIS NB Double Verification Checksum Caluclated====================>" + checksumvalue);
				request.setChksum(checksumvalue);
				String finalRequest = request.pipeSeprated();
				logger.info("AXIS NB Double Verification Final Request ========================>" + finalRequest);
				String encRequest = AES256EncDnc.encryptforpayment(finalRequest,
						propertiesMap.get(AES256EncDnc.AXIS_DV_ENC_KEY));
				logger.info("AXIS NB Double Verification Enc Request============================>" + encRequest);
				AxisData axisdata = new AxisData();
				axisdata.setEncdata(encRequest);
				axisdata.setPayeeid(merchantSetting.getSetting1());
				axisdata.setEnccat(propertiesMap.get(AES256EncDnc.AXIS_NB_ENCCAT));
				axisdata.setMercat(propertiesMap.get(AES256EncDnc.AXIS_NB_CATEGORY_ID));
				String doubleVerficationResponse = AES256EncDnc
						.sendFormPostRequest(propertiesMap.get(AES256EncDnc.AXIS_NB_VERIFICATION_URL), axisdata);
//					String response = null;
				logger.info("AXIS NB Double Verification Response========================================>"
						+ doubleVerficationResponse);
				String decryptedResponse = AES256EncDnc.decryptdoubleverification(doubleVerficationResponse,
						propertiesMap.get(AES256EncDnc.AXIS_DV_ENC_KEY));

				logger.info("AXIS NB Double Verification decrypted Response====================================> "
						+ decryptedResponse);

				DataSet dataSet = DataSet.fromXML(decryptedResponse);
				
				String responseCode = "01";
				String txnStatus = "FAILED";
				String stage = "";
				String bankError = null;
				
				if (dataSet != null && dataSet.getTable1().get(0).getPaymentStatus().equalsIgnoreCase("S")) {

					responseCode = "00";
					txnStatus = "SUCCESS";
					stage = "Transaction is successfully processed. ";

				} else {
					responseCode = "01";
					txnStatus = "FAILED";
					stage = "Transaction is Failed. ";
					bankError = dataSet.getTable1().get(0).getPaymentStatus();
				}
				
//				transactionDetails.setResponseCode(responseCode);
//				transactionDetails.setTxnStatus(txnStatus);
//				transactionDetails.setStage(stage);
//				transactionDetails.setProcessorCode("00");
//				transactionDetails.setProcessorTxnId(response.getBID());
//				transactionDetails.setBankErrorMsg(bankError);
//				transactionLogRepo.save(transactionDetails);
				
				ruResponseTxnVo.setResponseCode(responseCode);
				ruResponseTxnVo.setTxnStatus(txnStatus);
				ruResponseTxnVo.setStage(stage);
				ruResponseTxnVo.setProcessorCode("00");
				ruResponseTxnVo.setProcessorTxnId(response.getBID());
				ruResponseTxnVo.setBankErrorMsg(bankError);
				ruResponseTxnVo.setTxnDoubleVerifiedFlag(true);

			}

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
//		return transactionDetails;
		return ruResponseTxnVo;
	}

	private RuResponseTxnVo parseYesNbResponse(Map<String, String> requestMap) {
		String tId = requestMap.get("tid");
		String encdata = requestMap.get("encdata");
		RuResponseTxnVo txnVo = yesNbResponse(tId, encdata);
		return txnVo;
	}

	private RuResponseTxnVo yesNbResponse(String tId, String encdata) {
		
		TransactionLog transactionDetails = null;
		RuResponseTxnVo ruResponseTxnVo = null;

		try {

			encdata = encdata.replace(" ", "+");
			logger.info("YES BANK NB Response Transaction Data===========>" + encdata);

			transactionDetails = transactionLogRepo.findById(Long.valueOf(tId)).get();

			if (transactionDetails != null) {

				ruResponseTxnVo = new RuResponseTxnVo();
				ruResponseTxnVo.setTransctionDetail(transactionDetails);
				
				List<PropertiesVo> properties = propertiesService.findAllPropertyWithUpdatedCerts();
				Map<String, String> propertiesMap = new HashMap<String, String>();
				for (PropertiesVo property : properties) {
					propertiesMap.put(property.getPropertyKey(), property.getPropertyValue());
				}

				MerchantSetting merchantSetting = merchantSettingRepo
						.findById(transactionDetails.getMerchantSettingId()).get();

				String decryptedData = YESBankUtils.decrypt(encdata, propertiesMap.get(YESBankUtils.YES_BANK_NB_KEY));

				logger.info("Yes bank NB Decrypted Data ============================>" + decryptedData);
				YesBankNBResponse yesbanknbResponse = new YesBankNBResponse(decryptedData);
				logger.info("Yes bank NB Response Data ============================>" + yesbanknbResponse);

				String returnUrl = propertiesMap.get(YESBankUtils.YES_BANK_VERIFY_RETURN_URL);
				returnUrl = returnUrl.replace("#ru", String.valueOf(transactionDetails.getTransactionId()));

				String dateString = transactionDetails.getDate();
				SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date transactionDate = inputFormat.parse(dateString);

				SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				String formattedDate = outputFormat.format(transactionDate);

				YesBankNBVerifyRequest verifyrequest = new YesBankNBVerifyRequest();
				verifyrequest.setFldMerchCode(merchantSetting.getmPassword());
				verifyrequest.setFldClientCode(merchantSetting.getMloginId());
				verifyrequest.setFldTxnCurr(merchantSetting.getCurrency());
				verifyrequest.setFldTxnAmt(yesbanknbResponse.getFldTxnAmt());
				verifyrequest.setFldTxnScAmt(yesbanknbResponse.getFldTxnScAmt());
				verifyrequest.setFldMerchRefNbr(String.valueOf(transactionDetails.getTransactionId()));
				verifyrequest.setFldDatTimeTxn(formattedDate);
				verifyrequest.setBankRefNo(yesbanknbResponse.getBankRefNo());
				verifyrequest.setRU(returnUrl);
				verifyrequest.setFlgVerify(YESBankUtils.YES_BANK_VERIFY_FLAG);
				verifyrequest.setFldRef2(merchantSetting.getSetting2());

				logger.info("Yes bank NB verify  Checksum Request====>" + verifyrequest.checkSum());

				String sha2Checksum = YESBankUtils.CalculatedChecksum(verifyrequest.checkSum());
				logger.info("Yes bank NB verify calculated Checksum====>" + sha2Checksum);

				verifyrequest.setCHECKSUM(sha2Checksum);
				String finaldata = verifyrequest.finalCheckSum();
				logger.info("Yes bank NB verify  final data===>" + finaldata);

				String encData = YESBankUtils.encrypt(finaldata, propertiesMap.get(YESBankUtils.YES_BANK_NB_KEY));
				logger.info("Yes bank NB verify  enc Data===>" + encData);

				String appendString = "PID=" + merchantSetting.getSetting1() + "&encdata=" + encData;
				String url = propertiesMap.get(YESBankUtils.YES_BANK_VERIFY_URL);
				url = url + "?" + appendString;
				logger.info("Yes bank NB verify  final Url===>" + url);

				String verifyapicallresponse = null;
				String verifyResponse = null;
				YesBankNBVerifyResponse yesbankverifyResponse = null;
				try {
					verifyapicallresponse = YESBankUtils.apicallTest(url);
					logger.info("Yes bank NB Verify  Response=====================>" + verifyapicallresponse);

					verifyResponse = YESBankUtils.decrypt(verifyapicallresponse,
							propertiesMap.get(YESBankUtils.YES_BANK_NB_KEY));

					yesbankverifyResponse = new YesBankNBVerifyResponse(verifyResponse);
				} catch (Exception e) {
					new GlobalExceptionHandler().customException(e);
				}

				logger.info("Yes bank NB Verfiy Decrypted Response=============>" + verifyResponse);
				
				String responseCode = "01";
				String txnStatus = "FAILED";
				String bankError = null;
//				String stage = "";
				if (yesbankverifyResponse != null && !yesbankverifyResponse.getFlgSuccess().equalsIgnoreCase("S")) {
					responseCode = "00";
					txnStatus = "SUCCESS";
//					stage = "Transaction is successfully processed. ";
				} else {
					responseCode = "01";
					txnStatus = "FAILED";
					bankError = yesbanknbResponse.getMessage();
				}
				
//				transactionDetails.setResponseCode(responseCode);
//				transactionDetails.setTxnStatus(txnStatus);
//				transactionDetails.setStage("Transaction is successfully processed. ");
//				transactionDetails.setProcessorCode("00");
//				transactionDetails.setProcessorTxnId(String.valueOf(yesbanknbResponse.getBankRefNo()));
//				transactionDetails.setBankErrorMsg(bankError);
//				transactionLogRepo.save(transactionDetails);
				
				ruResponseTxnVo.setResponseCode(responseCode);
				ruResponseTxnVo.setTxnStatus(txnStatus);
				ruResponseTxnVo.setStage("Transaction is successfully processed. ");
				ruResponseTxnVo.setProcessorCode("00");
				ruResponseTxnVo.setProcessorTxnId(String.valueOf(yesbanknbResponse.getBankRefNo()));
				ruResponseTxnVo.setBankErrorMsg(bankError);
				ruResponseTxnVo.setTxnDoubleVerifiedFlag(true);
				
			}

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
//		return transactionDetails;
		return ruResponseTxnVo;
	}

	private RuResponseTxnVo parseSbiNbResponse(Map<String, String> requestMap, Map<String, String> paramMap) {
		String tId = requestMap.get("tid");
		String encdata = paramMap.get("encdata");
		RuResponseTxnVo txnVo = sbinbResponse(tId, encdata);
		return txnVo;
	}

	public RuResponseTxnVo sbinbResponse(String tId, String encdata) {

		TransactionLog transactionDetails = null;
		RuResponseTxnVo ruResponseTxnVo = null;
		try {

			transactionDetails = transactionLogRepo.findById(Long.valueOf(tId)).get();

			if (transactionDetails != null) {

				ruResponseTxnVo = new RuResponseTxnVo();
				ruResponseTxnVo.setTransctionDetail(transactionDetails);
				
				List<PropertiesVo> properties = propertiesService.findAllPropertyWithUpdatedCerts();
				Map<String, String> propertiesMap = new HashMap<String, String>();
				for (PropertiesVo property : properties) {
					propertiesMap.put(property.getPropertyKey(), property.getPropertyValue());
				}

				MerchantSetting merchantSetting = merchantSettingRepo
						.findById(transactionDetails.getMerchantSettingId()).get();

				String todecrypt = encdata.trim().replace(" ", "+");
				logger.info("Replace Space to + string=====>" + todecrypt);

				String decryptedResponse = SBIUtils.Decrypt(todecrypt, propertiesMap.get(SBIUtils.SBINB_KEYPATH));
				logger.info("PipeSeprated Response=======>" + decryptedResponse);
				SBIPaymentResponse sbiResponse = new SBIPaymentResponse(decryptedResponse);
				logger.info("Decrypted response SBINB=============>" + sbiResponse);

				SBIPaymentRequest sbirequest = new SBIPaymentRequest();
				BigDecimal amount = requaryAmount(transactionDetails);
				sbirequest.setRef_no(String.valueOf(transactionDetails.getTransactionId()));
				sbirequest.setAmount(String.valueOf(amount));
				sbirequest.setRedirect_url(propertiesMap.get(SBIUtils.SBI_NB_RETURN_URL));
				sbirequest.setCrn("INR");
				sbirequest.setTransaction_category("INB");
				sbirequest.setCheckSum("");
				logger.info("Verification Checksum Request====>" + sbirequest.checksum());
				String pipeSepratedChecksum = SBIUtils.getSHA2Checksum(sbirequest.checksum());
				logger.info("Verification calculated Checksum====>" + pipeSepratedChecksum);
				sbirequest.setCheckSum(pipeSepratedChecksum);

				String pipeSepratedfinal = sbirequest.checksumEncrypted();

				logger.info(sbirequest.checksumEncrypted());

				logger.info("Verification final => " + pipeSepratedfinal);
				String encData = SBIUtils.Encrypt(pipeSepratedfinal, propertiesMap.get(SBIUtils.SBINB_KEYPATH));
				logger.info("Verification encryted Data=======>" + encData);

				SBIRequestWrapper requestWrapper = new SBIRequestWrapper();
				requestWrapper.setEncdata(encData);
				requestWrapper.setMerchant_code(merchantSetting.getSetting1());

//			
				logger.info("Verification URL===>" + propertiesMap.get(SBIUtils.SBI_NB_DOUBLE_VERIFICATION_API_URL));
				String response = SBIUtils.postapi2(propertiesMap.get(SBIUtils.SBI_NB_DOUBLE_VERIFICATION_API_URL),
						requestWrapper);
				logger.info("Verification Response ====>" + response);
				String result = response.trim().replaceAll("\n", "").replaceAll("\r", "");

				String decrypteddata = SBIUtils.Decrypt(result, propertiesMap.get(SBIUtils.SBINB_KEYPATH));
				logger.info("Verification Decryptewd Data==>" + decrypteddata);
				SBIVerificaltionResponse sbiresponse = new SBIVerificaltionResponse(decrypteddata);

				String responseCode = "01";
				String txnStatus = "FAILED";
				String stage = "";
				String bankError = null;
				if (sbiresponse != null && sbiresponse.getStatus().equalsIgnoreCase("Success")) {
					responseCode = "00";
					txnStatus = "SUCCESS";
					stage = "Transaction is successfully processed. ";
				} else {
					responseCode = "01";
					txnStatus = "FAILED";
					stage = "Transaction is FAILED. ";
					bankError = sbiresponse.getStatus_description();
				}
				
//				transactionDetails.setResponseCode(responseCode);
//				transactionDetails.setTxnStatus(txnStatus);
//				transactionDetails.setStage(stage);
//				transactionDetails.setProcessorCode("00");
//				transactionDetails.setProcessorTxnId(sbiresponse.getBank_ref_no());
//				transactionDetails.setBankErrorMsg(bankError);
				// transactionDetails.setOrderNumber();
//				transactionLogRepo.save(transactionDetails);

				ruResponseTxnVo.setResponseCode(responseCode);
				ruResponseTxnVo.setTxnStatus(txnStatus);
				ruResponseTxnVo.setStage(stage);
				ruResponseTxnVo.setProcessorCode("00");
				ruResponseTxnVo.setProcessorTxnId(sbiresponse.getBank_ref_no());
				ruResponseTxnVo.setBankErrorMsg(bankError);
				ruResponseTxnVo.setTxnDoubleVerifiedFlag(true);
				
			}

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
//		return transactionDetails;
		return ruResponseTxnVo;
	}

	private RuResponseTxnVo parseGetepaySimResponse(Map<String, String> requestMap) {
		String tId = requestMap.get("tid");
		String status = requestMap.get("status");
		RuResponseTxnVo txnVo = getepaySimResponse(tId, status);
		return txnVo;
	}

	private RuResponseTxnVo getepaySimResponse(String tId, String status) {
		
		TransactionLog transactionDetails = null;
		RuResponseTxnVo ruResponseTxnVo = null;
		try {
			logger.info("<-------Inside Sim Response------->");
			logger.info("getepaySimResponse => tId : " + tId);
			String responseCode = "01";
			String txnStatus = "FAILED";
			Gson gson = new Gson();
			Long tid = Long.valueOf(tId);

			if (tid > 0) {
				
				transactionDetails = transactionLogRepo.findById(tid).get();
				
				ruResponseTxnVo = new RuResponseTxnVo();
				ruResponseTxnVo.setTransctionDetail(transactionDetails);
				
				if (status != null && status.equalsIgnoreCase("success")) {
					responseCode = "00";
					txnStatus = "SUCCESS";
				}
//				transactionDetails.setResponseCode(responseCode);
//				transactionDetails.setTxnStatus(txnStatus);
//				transactionDetails.setStage("Transaction is successfully processed. ");
//				transactionDetails.setProcessorCode("00");
				// transactionDetails.setOrderNumber();
//				transactionLogRepo.save(transactionDetails);
				
				ruResponseTxnVo.setResponseCode(responseCode);
				ruResponseTxnVo.setTxnStatus(txnStatus);
				ruResponseTxnVo.setStage("Transaction is successfully processed. ");
				ruResponseTxnVo.setProcessorCode("00");
				ruResponseTxnVo.setTxnDoubleVerifiedFlag(true);
				
			} else {
				responseCode = "10";
			}

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
//		return transactionDetails;
		return ruResponseTxnVo;
	}

	private RuResponseTxnVo parseGetepayLyraResponse(Map<String, String> requestMap) {
		String tId = requestMap.get("tId");
		if (tId == null) {
			tId = requestMap.get("tid");
		}
		RuResponseTxnVo txnVo = getepaylResponse(tId);
		return txnVo;
	}

	private RuResponseTxnVo getepaylResponse(String tId) {

		TransactionLog transactionDetails = null;
		RuResponseTxnVo ruResponseTxnVo = null;
		try {
			Long tid = Long.valueOf(tId);
			logger.info("getepaylResponse => tId : " + tid);
			Gson gson = new Gson();
			Call call = new Call();

			if (tid > 0) {

				transactionDetails = transactionLogRepo.findById(tid).get();

				if (transactionDetails != null) {

					ruResponseTxnVo = new RuResponseTxnVo();
					ruResponseTxnVo.setTransctionDetail(transactionDetails);
					
					List<PropertiesVo> properties = propertiesService.findAllPropertyWithUpdatedCerts();
					Map<String, String> propertiesMap = new HashMap<String, String>();
					for (PropertiesVo property : properties) {
						propertiesMap.put(property.getPropertyKey(), property.getPropertyValue());
					}

					MerchantSetting merchantSetting = merchantSettingRepo
							.findById(transactionDetails.getMerchantSettingId()).get();

					String authoriztion = merchantSetting.getMloginId() + ":" + merchantSetting.getmPassword();
					authoriztion = Base64.getEncoder().encodeToString(authoriztion.getBytes());
					String baseUrl = propertiesMap.get(LyraUtil.LYRA_BASE_URL);

					call.setBaseUrl(baseUrl);
					call.setAuthoriztion(authoriztion);

					String uuid = transactionDetails.getProcessorTxnId().trim();
					String lyraResponse = call.getACharge(uuid);

					LyraChargeResponse lyraChargeResponse = gson.fromJson(lyraResponse, LyraChargeResponse.class);
					logger.info("response : " + lyraChargeResponse);

//					 TransactionEssentials transactionEssentials = transactionEssentialsRepo
//					 .findByTransactionId(transactionDetails.getTransactionId());
//					 transactionEssentials.setUdf37(lyraChargeResponse.getTransactions().get(0).getCardVariant());
//					 transactionEssentials.setUdf38(lyraChargeResponse.getTransactions().get(0).getIssuingBank());
//					 transactionEssentialsRepo.save(transactionEssentials);

					String responseCode = "01";
					String txnStatus = "FAILED";
					if (lyraChargeResponse != null && (lyraChargeResponse.getStatus().equalsIgnoreCase("success")
							|| lyraChargeResponse.getStatus().equalsIgnoreCase("PAID"))) {
						responseCode = "00";
						txnStatus = "SUCCESS";
					}

//					transactionDetails.setResponseCode(responseCode);
//					transactionDetails.setTxnStatus(txnStatus);
//					transactionDetails.setStage("Transaction is successfully processed. ");
//					transactionDetails.setProcessorCode("00");
//					transactionDetails.setBankErrorMsg(lyraChargeResponse.getTransactions().get(0).getError());
					// transactionDetails.setOrderNumber();
//					transactionLogRepo.save(transactionDetails);

					ruResponseTxnVo.setBankErrorMsg(lyraChargeResponse.getTransactions().get(0).getError());
					ruResponseTxnVo.setResponseCode(responseCode);
					ruResponseTxnVo.setTxnStatus(txnStatus);
					ruResponseTxnVo.setStage("Transaction is successfully processed. ");
					ruResponseTxnVo.setProcessorCode("00");
					ruResponseTxnVo.setTxnDoubleVerifiedFlag(true);
				} else {
//					responseCode = "10";
				}
			}
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}

//		return transactionDetails;
		return ruResponseTxnVo;
	}

	private RuResponseTxnVo parseAuNbResponse(Map<String, String> requestMap) {
		String responseData = requestMap.get("data");
		RuResponseTxnVo aunbResponse = aunbResponse(responseData);
		return aunbResponse;
	}

	public RuResponseTxnVo aunbResponse(String responseData) {

		TransactionLog transactionDetails = null;
		RuResponseTxnVo ruResponseTxnVo = null;
		String responseCode = "01";
		String txnStatus = "FAILED";
		BigDecimal convinenceCharge = BigDecimal.ZERO;
		try {

			if (responseData != null && !responseData.trim().equals("")) {

				List<PropertiesVo> properties = propertiesService.findAllPropertyWithUpdatedCerts();
				Map<String, String> propertiesMap = new HashMap<String, String>();
				for (PropertiesVo property : properties) {
					propertiesMap.put(property.getPropertyKey(), property.getPropertyValue());
				}
				String auKey = propertiesMap.get("AUNB_ENCRYPTION_KEY");

				Map<String, String> decryptedData = Utilities.parseAunbResponse(responseData, auKey);

				String txnIdString = decryptedData.get("merchantRefNumber");
				transactionDetails = transactionLogRepo.findById(Long.parseLong(txnIdString)).get();

				if (transactionDetails != null) {

					ruResponseTxnVo = new RuResponseTxnVo();
					ruResponseTxnVo.setTransctionDetail(transactionDetails);
					
					String bankPCode = decryptedData.get("externalRefId");
					if (bankPCode == null) {
						bankPCode = "";
					}
					BigDecimal txnAmount = transactionDetails.getAmt();
					if (transactionDetails.getCommisionType() != null
							&& transactionDetails.getCommisionType().equalsIgnoreCase("Excl")) {
						txnAmount = txnAmount.add(transactionDetails.getCommision());
					}

					if (transactionDetails.getServiceChargeType() != null
							&& transactionDetails.getServiceChargeType().equalsIgnoreCase("Excl")) {
						convinenceCharge = new BigDecimal(transactionDetails.getTotalServiceCharge());
						convinenceCharge = convinenceCharge.setScale(2, RoundingMode.UP);
						txnAmount = txnAmount.add(convinenceCharge);

					}

					BigDecimal auAmt = new BigDecimal(decryptedData.get("transactionAmt"));

					boolean isValidAmt = true;
					if (txnAmount.compareTo(auAmt) != 0) {
						logger.info("Amount mismatched=> " + decryptedData.get("transactionAmt"));
						isValidAmt = false;
					}

					String response = decryptedData.get("paid");
					if (response != null && response.equalsIgnoreCase("Y") && isValidAmt) {
						responseCode = "00";
						txnStatus = "SUCCESS";

					}

//					transactionDetails.setBankErrorMsg(decryptedData.get("message"));
//					transactionDetails.setResponseCode(responseCode);
//					transactionDetails.setTxnStatus(txnStatus);
//					transactionDetails.setStage("Transaction is successfully processed. ");
//					transactionDetails.setProcessorCode("00");
//					transactionDetails.setOrderNumber(bankPCode);
//					transactionLogRepo.save(transactionDetail);
					
					ruResponseTxnVo.setBankErrorMsg(decryptedData.get("message"));
					ruResponseTxnVo.setResponseCode(responseCode);
					ruResponseTxnVo.setTxnStatus(txnStatus);
					ruResponseTxnVo.setStage("Transaction is successfully processed. ");
					ruResponseTxnVo.setProcessorCode("00");
					ruResponseTxnVo.setOrderNumber(bankPCode);
					ruResponseTxnVo.setTxnDoubleVerifiedFlag(false);
				}
			} else {
				responseCode = "10";
			}

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
//		return transactionDetails;
		return  ruResponseTxnVo;
	}

	private RuResponseTxnVo parseCfResponse(Map<String, String> requestMap) {
		String orderId = requestMap.get("order_id");
		String orderToken = requestMap.get("order_token");
		RuResponseTxnVo bankTxnVo = cfResponse(orderId, orderToken);
		return bankTxnVo;
	}

	private RuResponseTxnVo cfResponse(String orderId, String orderToken) {

		long txnId = Long.valueOf(orderId);
		TransactionLog transactionDetails = null;
		RuResponseTxnVo ruResponseTxnVo = null;
		try {

			transactionDetails = transactionLogRepo.findById(txnId).get();

			if (transactionDetails != null && transactionDetails.getTransactionId() != null
					&& transactionDetails.getTransactionId() > 0 && transactionDetails.getUdf10() != null
					&& transactionDetails.getUdf10().equals(orderToken)) {

				ruResponseTxnVo = new RuResponseTxnVo();
				ruResponseTxnVo.setTransctionDetail(transactionDetails);
				
				List<PropertiesVo> properties = propertiesService.findAllPropertyWithUpdatedCerts();
				Map<String, String> propertiesMap = new HashMap<String, String>();
				for (PropertiesVo property : properties) {
					propertiesMap.put(property.getPropertyKey(), property.getPropertyValue());
				}

				MerchantSetting merchantSetting = merchantSettingRepo.findById(transactionDetails.getMerchantSettingId())
						.get();

				String cfOrderKey = propertiesMap.get(RemoteDbUtil.GETEPAY_CASHFREE_ORDER_URL_KEY);
				String url = cfOrderKey + "/" + orderId;

				logger.info("cf requery url=>" + url);

				String cfApiVersionKey = propertiesMap.get(RemoteDbUtil.GETEPAY_CASHFREE_APIVERSION_KEY);

				HttpClient httpClient = HttpClientBuilder.create().build();
				HttpGet get = new HttpGet(url);
				get.setHeader("content-type", "application/json");
				get.setHeader("x-client-id", merchantSetting.getMloginId());
				get.setHeader("x-client-secret", merchantSetting.getmPassword());
				get.setHeader("x-api-version", cfApiVersionKey);

				HttpResponse responseObject = httpClient.execute(get);
				HttpEntity entity = responseObject.getEntity();
				String responseString = EntityUtils.toString(entity, "UTF-8");
				logger.info("Cashfree order status response " + responseString);

				int responseCode = responseObject.getStatusLine().getStatusCode();

				String txnResponseCode = "";
				String txnStatus = "";
				String txnStage = "";
				if (responseCode == 200) {
					Gson gson = new Gson();
					CashfreeResponse response = gson.fromJson(responseString, CashfreeResponse.class);
					logger.info("Order status=>" + response.getCf_order_id() + "::" + response.getOrder_status());
					if (response.getOrder_status() != null && response.getOrder_status().equalsIgnoreCase("PAID")) {
						txnResponseCode = "00";
						txnStatus = "SUCCESS";
						txnStage = "Transaction is successfully processed. ";

					} else {
						txnResponseCode = "01";
						txnStatus = "FAILED";
						txnStage = "Transaction failed. ";
					}
//					transactionDetails.setResponseCode(txnResponseCode);
//					transactionDetails.setTxnStatus(txnStatus);
//					transactionDetails.setStage(txnStage);
//					transactionDetails.setProcessorCode(String.valueOf(responseCode));
//					transactionDetails.setOrderNumber(response.getOrder_id());
					
					ruResponseTxnVo.setResponseCode(txnResponseCode);
					ruResponseTxnVo.setTxnStatus(txnStatus);
					ruResponseTxnVo.setStage(txnStage);
					ruResponseTxnVo.setProcessorCode(String.valueOf(responseCode));
					ruResponseTxnVo.setOrderNumber(response.getOrder_id());
					ruResponseTxnVo.setTxnDoubleVerifiedFlag(false);
				}
				
//				transactionLogRepo.save(transactionDetail);
			}
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}

//		return transactionDetails;
		return ruResponseTxnVo;
	}

	private RuResponseTxnVo parseBobResponse(Map<String, String> requestMap) {

		String trandata = requestMap.get("trandata");
		String error = requestMap.get("ErrorText");
		String trackid = requestMap.get("trackid");
		RuResponseTxnVo bankTxnVo = bobResponse(trandata, error, trackid);
		return bankTxnVo;
	}

	private RuResponseTxnVo bobResponse(String response, String error, String trackid) {

		logger.info("<-------Inside Bob Response------->");
		logger.info("Bob trandata==>" + response + "trackid=====>" + trackid);

		TransactionLog transactionDetails = null;
		RuResponseTxnVo ruResponseTxnVo = null;
		String responseCode = "01";
		String txnStatus = "FAILED";
		String stage = "";
		try {

			if (trackid != null && !trackid.trim().equals("")) {
				transactionDetails = transactionLogRepo.findById(Long.parseLong(trackid)).get();
				if (transactionDetails != null) {
					
					ruResponseTxnVo = new RuResponseTxnVo();
					ruResponseTxnVo.setTransctionDetail(transactionDetails);
					
					List<PropertiesVo> properties = propertiesService.findAllPropertyWithUpdatedCerts();
					Map<String, String> propertiesMap = new HashMap<String, String>();
					for (PropertiesVo property : properties) {
						propertiesMap.put(property.getPropertyKey(), property.getPropertyValue());
					}

					MerchantSetting merchantSetting = merchantSettingRepo
							.findById(transactionDetails.getMerchantSettingId()).get();
					iPayPipe pipe = new iPayPipe();

					String keystorePath = merchantSetting.getSetting1();
					if (keystorePath == null || keystorePath.trim().equals("")) {
						keystorePath = propertiesMap.get(Constants.BOB_PROPERTIES_PATH_KEY);
					}
					logger.info("keystore path" + " : " + keystorePath + "<br>");
					pipe.setKeystorePath(keystorePath);
					pipe.setResourcePath(keystorePath);
					String alias = merchantSetting.getSetting2();
					if (alias == null || alias.trim().equals("")) {
						alias = propertiesMap.get(Constants.BOB_ALIAS_KEY);
					}
					logger.info("alias path" + " : " + alias + "<br>");

					pipe.setAlias(alias);
					int decryptedResponse = pipe.parseEncryptedResult(response);

					logger.info("Decrypted Response from BOB for txn id and parse status==>" + decryptedResponse + "::"
							+ pipe.getTransId() + "::" + decryptedResponse + "::" + pipe.getResult() + "::"
							+ pipe.getError() + "::" + pipe.getError_text());
					logger.info(
							"Decrypted Response from BOB for txn id and parse raw status==>" + pipe.getRawResponse());

					String processorTransactionId = pipe.getPaymentId();
					String txnIdInResponse = pipe.getTrackId();
					long txnIdResponse = 0;
					if (txnIdInResponse != null && !txnIdInResponse.equals("")) {
						txnIdResponse = Long.valueOf(txnIdInResponse);
					}

					String bankReponse = "";
					if (decryptedResponse == 0) {
						bankReponse = pipe.getResult();
					} else {
						bankReponse = error;
					}

					logger.info("Bob Error========================================>" + error);

					if (bankReponse != null && txnIdResponse == Long.valueOf(trackid)
							&& (bankReponse.equalsIgnoreCase("SUCCESS") || bankReponse.equalsIgnoreCase("APPROVED")
									|| bankReponse.equalsIgnoreCase("CAPTURED"))) {
						responseCode = "00";
						txnStatus = "SUCCESS";
						stage = "Transaction is successfully processed.";

					} else {
						// update transaction here as failed
						responseCode = "01";
						txnStatus = "FAILED";
						stage = "Transaction is failed. ";
						transactionDetails.setBankErrorMsg(pipe.getError_text());
					}
					
//					transactionDetails.setResponseCode(responseCode);
//					transactionDetails.setTxnStatus(txnStatus);
//					transactionDetails.setStage(stage);
//					transactionDetails.setProcessorCode(bankReponse);
//					transactionDetails.setOrderNumber(processorTransactionId);
//					transactionLogRepo.save(transactionDetails);
					
					ruResponseTxnVo.setResponseCode(responseCode);
					ruResponseTxnVo.setTxnStatus(txnStatus);
					ruResponseTxnVo.setStage(stage);
					ruResponseTxnVo.setProcessorCode(bankReponse);
					ruResponseTxnVo.setOrderNumber(processorTransactionId);
					ruResponseTxnVo.setTxnDoubleVerifiedFlag(false);

				} else {
					responseCode = "10";
				}
			}
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}

//		return transactionDetails;
		return ruResponseTxnVo;

	}

	public static BigDecimal requaryAmount(TransactionLog t) {
		BigDecimal amount = t.getAmt();
		BigDecimal commissionCharge = BigDecimal.ZERO;
		BigDecimal convinenceCharge = BigDecimal.ZERO;
		if (t.getCommisionType() != null && t.getCommisionType().equalsIgnoreCase("Excl")) {
			commissionCharge = t.getCommision();
			amount = amount.add(commissionCharge);

		} else {
			amount = amount.add(commissionCharge);
		}
		if (t.getServiceChargeType() != null && t.getServiceChargeType().equalsIgnoreCase("Excl")) {
			convinenceCharge = new BigDecimal(t.getTotalServiceCharge());
			convinenceCharge = convinenceCharge.setScale(2, RoundingMode.HALF_UP);
			amount = amount.add(convinenceCharge);

		} else {
			amount = amount.add(convinenceCharge);
		}

		return amount;
	}

	private String decryptResponse(String enc) {
		List<PropertiesVo> properties = propertiesService.findByPropertykeyWithUpdatedCertsLike("KMS");
		Map<String, String> propertiesMap = new HashMap<String, String>();
		for (PropertiesVo property : properties) {
			propertiesMap.put(property.getPropertyKey(), property.getPropertyValue());
		}

		String accessKeyId = propertiesMap.get(KmsOperations.KMS_DEC_ACCESS_KEY_ID);
		String secretAccessKey = propertiesMap.get(KmsOperations.KMS_DEC_SECRET_ACCESS_KEY);
		// Replace with your AWS region and KMS key ID
		String region = propertiesMap.get(KmsOperations.KMS_REGION);
		String kmsKey = propertiesMap.get(KmsOperations.KMS_KEY);
		String decrypt = KmsOperations.decrypt(kmsKey, accessKeyId, secretAccessKey, region);
		return decrypt;
	}

}
