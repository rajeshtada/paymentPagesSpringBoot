package com.ftk.pg.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ftk.pg.dao.MerchantSettingDao;
//import com.ftk.pg.dto.PaymentRequest;
import com.ftk.pg.dto.RequestWrapper;
import com.ftk.pg.dto.ResponseBuilder;
import com.ftk.pg.dto.ResponseWrapper;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.Bank;
import com.ftk.pg.modal.Merchant;
import com.ftk.pg.modal.MerchantSetting;
import com.ftk.pg.modal.TransactionEssentials;
import com.ftk.pg.modal.TransactionLog;
import com.ftk.pg.pi.repo.MerchantKeysRepo;
import com.ftk.pg.repo.BankRepo;
import com.ftk.pg.repo.MerchantRepo;
import com.ftk.pg.repo.TransactionEssentialsRepo;
import com.ftk.pg.repo.TransactionLogRepo;
import com.ftk.pg.requestvo.CardDetailsVo;
import com.ftk.pg.requestvo.GenerateOTPResponse;
import com.ftk.pg.requestvo.PropertiesVo;
import com.ftk.pg.requestvo.RequestParams;
import com.ftk.pg.requestvo.RequestParamsWrapper;
import com.ftk.pg.responsevo.PayResponse;
import com.ftk.pg.responsevo.PaymentResponse;
import com.ftk.pg.util.EncryptionUtil;
import com.ftk.pg.util.PayubizUtil;
import com.ftk.pg.util.SBICardsUtils;
import com.ftk.pg.util.SbiCardUtilCall;
import com.ftk.pg.util.Utilities;
import com.ftk.pg.vo.sbiNb.SbiRequestHeader;
import com.google.gson.Gson;
import com.mb.getepay.icici.lyra.LyraUtil;
import com.pgcomponent.security.SignatureGenerator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PaymentProcessService {

//    private final GlobalExceptionHandler globalExceptionHandler;
	static Logger logger = LogManager.getLogger(PaymentProcessService.class);

	private final MerchantKeysRepo merchantKeysRepo;

	private final PropertiesService propertiesService;

	private final ApiService apiService;

	private final MerchantRepo merchantRepo;

	private final TransactionLogRepo transactionLogRepo;

	private final EncryptionService encryptionService;

	private final BankRepo bankRepo;

	private final CallBackService callbackService;

	private final MerchantSettingDao merchantSettingDao;

	private final TransactionEssentialsRepo transactionEssentialsRepo;

//    PaymentProcessService(GlobalExceptionHandler globalExceptionHandler) {
//        this.globalExceptionHandler = globalExceptionHandler;
//    }

	public ResponseEntity<ResponseWrapper<String>> pay(RequestWrapper requestWrapper, String token) throws Exception {
		ResponseEntity<ResponseWrapper<String>> response = null;
		RequestParamsWrapper requestParamsWrapper = EncryptionUtil.decryptdata(String.valueOf(requestWrapper.getData()),
				token, RequestParamsWrapper.class);

		RequestParams requestParams = requestParamsWrapper.getRequestParams();

		if (requestParams == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Invalid Payment Url", null));
		}

		try {

			TransactionLog transaction = transactionLogRepo.findById(Long.valueOf(requestParams.getTransactionId()))
					.get();
			logger.info("pay method transactionLog=> " + transaction);

			if (transaction.getTxnStatus().equalsIgnoreCase("SUCCESS") || transaction.getTxnStatus().equalsIgnoreCase("FAILED")) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(
						HttpStatus.INTERNAL_SERVER_ERROR.value(), "Request link has processed ", null));
			}
			
			if (transaction.getTxnStatus().equalsIgnoreCase("INITIATED")) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
						new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Request link Already Inititated", null));
			}

			// mark Initiated in Txn Log
			transaction.setTxnStatus("INITIATED");
			transaction.setStage("Trasnaction is initiated");
			transaction.setResponseCode("02");
			transactionLogRepo.save(transaction);
			
			apiService.insetUpdateTrasnsactionEssentials(requestParams, transaction.getTransactionId());
			
			requestParams.setMerchantTxnId(transaction.getMerchanttxnid());
			requestParams.setProductType("IPG");
			requestParams.setRu(transaction.getRu());
			requestParams.setTxncurr(transaction.getTxncurr());
			requestParams.setUdf1(transaction.getUdf1());
			requestParams.setUdf2(transaction.getUdf2());
			requestParams.setUdf3(transaction.getUdf3());
			requestParams.setUdf4(transaction.getUdf4());
			requestParams.setUdf5(transaction.getUdf5());

			Map<String, String> propMap = new HashMap<>();
			List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
			for (PropertiesVo property : PropertiesList) {
				propMap.put(property.getPropertyKey(), property.getPropertyValue());
			}
			
			String method = requestParamsWrapper.getMethod();
			logger.info("pay method => " + method);
			if (method.equalsIgnoreCase("ccPay")) {
				response = ccPay(requestParams, transaction, propMap,token);
			} else if (method.equalsIgnoreCase("dcPay")) {
				response = dcPay(requestParams, transaction, propMap,token);
			} else if (method.equalsIgnoreCase("nbPay")) {
				response = nbPay(requestParams, transaction, propMap,token);
			} else if (method.equalsIgnoreCase("upiPay")) {
				response = upiPay(requestParams, transaction, propMap,token);
			} else if (method.equalsIgnoreCase("neftPay")) {
				response = neftPay(requestParams, transaction, propMap,token);
			} else if (method.equalsIgnoreCase("challanPay")) {
				response = challanPay(requestParams, transaction, propMap,token);
			} else if (method.equalsIgnoreCase("walletPay")) {
				response = walletPay(requestParams, transaction, propMap,token);
			} else {
				logger.info("pay method not valid => " + method);
			}
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something Went Wrong", null));
		}
		return response;
	}

	private ResponseEntity<ResponseWrapper<String>> walletPay(RequestParams requestParams, TransactionLog transaction, Map<String, String> propMap, String token)
			throws Exception {

		logger.info("Payment Request Params: txnId ==>" + requestParams.getTransactionId());
		PayResponse payresponse = new PayResponse();
		
		if (requestParams.getName() != null && !requestParams.getName().equals("")) {
			transaction.setUdf1(requestParams.getName());
		}
		if (requestParams.getNbmobile() != null && !requestParams.getNbmobile().equals("")) {
			transaction.setUdf2(requestParams.getNbmobile());
		}
		if (requestParams.getUdf3() != null && !requestParams.getUdf3().equals("")) {
			transaction.setUdf3(requestParams.getUdf3());
		}
		transactionLogRepo.save(transaction);

		requestParams.setCardType("wallet");
		requestParams.setPaymentMode("wallet");
		requestParams.setProductType("IPG");
		
		PaymentResponse pgresponse = apiService.callPaymentApi(requestParams,propMap);
		logger.info(" callPaymentApi Pg Response =>" + pgresponse);
		
		if (pgresponse.getProcessor() != null && pgresponse.getProcessor().equalsIgnoreCase("Simulator")) {
			payresponse.setOtpValidation("true");
			payresponse.setTid(String.valueOf(transaction.getTransactionId()));
			return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
					"Redirect simulator page", HttpStatus.OK);
		}

		String threeDSecureUrl = pgresponse.getThreeDSecureUrl();
		boolean result = pgresponse.isResult();
		if (result) {
			if (pgresponse.getPostReqParam() == null || pgresponse.getPostReqParam().size() <= 0
					|| pgresponse.getPostReqParam().isEmpty()) {
				payresponse.setRu(threeDSecureUrl);
				payresponse.setPaymentResponse(pgresponse);
				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token), "Redirect",
						HttpStatus.OK);
			} else {

				String PAYU_URL_KEY = propMap.get(PayubizUtil.PAYU_URL_KEY);
				payresponse.setRu(PAYU_URL_KEY.trim());
				payresponse.setRequestMapObj(pgresponse.getPostReqParam());
				payresponse.setPaymentResponse(pgresponse);
				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
						"Redirect payuProcess page", HttpStatus.OK);
			}

		}
		if (requestParams.getRu() != null && (!requestParams.getRu().trim().equals(""))
				&& (!requestParams.getRu().trim().equals("https://pay.getepay.in/getePaymentPages/paynetzResponse/"))) {
			pgresponse.setRu(requestParams.getRu());
		} else {
			pgresponse.setRu("");
		}
		logger.info("FAILED API Request to Processor : txnId ==> " + requestParams.getTransactionId() + " Timing ==>"
				+ LocalDate.now());
		try {
			pgresponse.setmId(transaction.getMerchantId());
		} catch (Exception e) {
			pgresponse.setmId(0l);
		}
		pgresponse.setResponseCode("01");
		pgresponse.setStatus("FAILED");
		pgresponse.setMerchantTxnId(requestParams.getMerchantTxnId());
		BigDecimal uamount = Utilities.requaryAmount(transaction);
		pgresponse.setAmt(requestParams.getAmt());
		pgresponse.setUdf1(transaction.getUdf1());
		pgresponse.setUdf2(transaction.getUdf2());
		pgresponse.setUdf3(transaction.getUdf3());
		pgresponse.setUdf4(transaction.getUdf4());
		pgresponse.setUdf5(transaction.getUdf5());
		pgresponse.setPaymentMode(transaction.getPaymentMode());
		pgresponse.setTotalCharges(Utilities.getSurcharge(transaction));
		pgresponse.setTotalAmount(String.valueOf(uamount));
		logger.info("Pg Response ==========================>" + pgresponse);
		logger.info("FAILED  RESPONSE to Processor : txnId ==> " + pgresponse.getTransactionId() + " Response code ==> "
				+ pgresponse.getResponseCode() + " Timing ==>" + LocalDate.now());
		payresponse.setPaymentResponse(pgresponse);
		return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
				"Redirect process page", HttpStatus.OK);

	}

	private ResponseEntity<ResponseWrapper<String>> challanPay(RequestParams requestParams, TransactionLog transaction, Map<String, String> propMap, String token)
			throws Exception {

		logger.info("Payment Request Params: txnId ==>" + requestParams.getTransactionId());
		PayResponse payresponse = new PayResponse();

		if (requestParams.getNeftName() != null && !requestParams.getNeftName().equals("")) {
			transaction.setUdf1(requestParams.getNeftName());
		}
		if (requestParams.getNeftNumber() != null && !requestParams.getNeftNumber().equals("")) {
			transaction.setUdf2(requestParams.getNeftNumber());
		}
		if (requestParams.getUdf3() != null && !requestParams.getUdf3().equals("")) {
			transaction.setUdf3(requestParams.getUdf3());
		}
		transactionLogRepo.save(transaction);

		requestParams.setCardType("CHALLAN");
		requestParams.setPaymentMode("CHALLAN");
		
		PaymentResponse pgresponse = apiService.callPaymentApi(requestParams,propMap);
		logger.info(" callPaymentApi Pg Response =>" + pgresponse);

		String threeDSecureUrl = pgresponse.getThreeDSecureUrl();
		logger.info("threeDSecureUrl==============>" + threeDSecureUrl);

		boolean result = pgresponse.isResult();
		
		if (result) {
			if (pgresponse.getPostReqParam() == null || pgresponse.getPostReqParam().size() <= 0
					|| pgresponse.getPostReqParam().isEmpty()) {
				payresponse.setRu(threeDSecureUrl);
				payresponse.setPaymentResponse(pgresponse);
				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token), "Redirect",
						HttpStatus.OK);
			} else {
				payresponse.setRu(threeDSecureUrl);
				payresponse.setRequestMapObj(pgresponse.getPostReqParam());
				payresponse.setPaymentResponse(pgresponse);
				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
						"Redirect payuProcess page", HttpStatus.OK);
			}

		}
		if (requestParams.getRu() != null && (!requestParams.getRu().trim().equals(""))
				&& (!requestParams.getRu().trim().equals("https://pay.getepay.in/getePaymentPages/paynetzResponse/"))) {
			pgresponse.setRu(requestParams.getRu());
		} else {
			pgresponse.setRu("");
		}

		logger.info("FAILED API Request to Processor : txnId ==> " + requestParams.getTransactionId() + " Timing ==>"
				+ LocalDate.now());
		try {
			pgresponse.setmId(transaction.getMerchantId());
		} catch (Exception e) {
			pgresponse.setmId(0l);
		}
		pgresponse.setResponseCode("01");
		pgresponse.setStatus("FAILED");
		pgresponse.setMerchantTxnId(requestParams.getMerchantTxnId());
		BigDecimal uamount = Utilities.requaryAmount(transaction);
		pgresponse.setAmt(requestParams.getAmt());

		pgresponse.setUdf1(transaction.getUdf1());
		pgresponse.setUdf2(transaction.getUdf2());
		pgresponse.setUdf3(transaction.getUdf3());
		pgresponse.setUdf4(transaction.getUdf4());
		pgresponse.setUdf5(transaction.getUdf5());
		pgresponse.setPaymentMode(transaction.getPaymentMode());
		pgresponse.setTotalCharges(Utilities.getSurcharge(transaction));
		pgresponse.setTotalAmount(String.valueOf(uamount));
		logger.info("Pg Response ==========================>" + pgresponse);
		logger.info("FAILED  RESPONSE to Processor : txnId ==> " + pgresponse.getTransactionId() + " Response code ==> "
				+ pgresponse.getResponseCode() + " Timing ==>" + LocalDate.now());
		payresponse.setPaymentResponse(pgresponse);
		return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
				"Redirect process page", HttpStatus.OK);
	}

	private ResponseEntity<ResponseWrapper<String>> neftPay(RequestParams requestParams, TransactionLog transaction, Map<String, String> propMap, String token)
			throws Exception {

		logger.info("Payment Request Params: txnId ==>" + requestParams.getTransactionId());
		PayResponse payresponse = new PayResponse();

		if (requestParams.getNeftName() != null && !requestParams.getNeftName().equals("")) {
			transaction.setUdf1(requestParams.getNeftName());
		}
		if (requestParams.getNeftNumber() != null && !requestParams.getNeftNumber().equals("")) {
			transaction.setUdf2(requestParams.getNeftNumber());
		}
		if (requestParams.getUdf3() != null && !requestParams.getUdf3().equals("")) {
			transaction.setUdf3(requestParams.getUdf3());
		}
		transactionLogRepo.save(transaction);

		requestParams.setCardType("NEFT");
		requestParams.setPaymentMode("NEFT");
		requestParams.setProductType("IPG");
		
		PaymentResponse pgresponse = apiService.callPaymentApi(requestParams,propMap);
		logger.info(" callPaymentApi Pg Response =>" + pgresponse);
		
		String threeDSecureUrl = pgresponse.getThreeDSecureUrl();

		boolean result = pgresponse.isResult();

		if (result) {
			if (pgresponse.getPostReqParam() == null || pgresponse.getPostReqParam().size() <= 0
					|| pgresponse.getPostReqParam().isEmpty()) {
				payresponse.setRu(threeDSecureUrl);
				payresponse.setPaymentResponse(pgresponse);
				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token), "Redirect",
						HttpStatus.OK);
			} else {
				PropertiesVo properties = propertiesService.findByPropertykeyWithUpdatedCerts(PayubizUtil.PAYU_URL_KEY);
				payresponse.setRu(properties.getPropertyValue().trim());
				payresponse.setRequestMapObj(pgresponse.getPostReqParam());
				payresponse.setPaymentResponse(pgresponse);
				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
						"Redirect payuProcess page", HttpStatus.OK);
			}

		}
		if (requestParams.getRu() != null && (!requestParams.getRu().trim().equals(""))
				&& (!requestParams.getRu().trim().equals("https://pay.getepay.in/getePaymentPages/paynetzResponse/"))) {
			pgresponse.setRu(requestParams.getRu());
		} else {
			pgresponse.setRu("");
		}
		logger.info("FAILED API Request to Processor : txnId ==>" + requestParams.getTransactionId() + " Timing ==>"
				+ LocalDate.now());
		try {
			pgresponse.setmId(transaction.getMerchantId());
		} catch (Exception e) {
			pgresponse.setmId(0l);
		}
		pgresponse.setResponseCode("01");
		pgresponse.setStatus("FAILED");
		pgresponse.setMerchantTxnId(requestParams.getMerchantTxnId());
		BigDecimal uamount = Utilities.requaryAmount(transaction);
		pgresponse.setAmt(requestParams.getAmt());
		String signatureKey = SignatureGenerator.signatureGeneration(new String[] { requestParams.getLogin(),
				requestParams.getMerchantTxnId(), requestParams.getAmt(), pgresponse.getStatus() },
				requestParams.getMerchantTxnId());
		pgresponse.setSignature(signatureKey);
		pgresponse.setUdf1(transaction.getUdf1());
		pgresponse.setUdf2(transaction.getUdf2());
		pgresponse.setUdf3(transaction.getUdf3());
		pgresponse.setUdf4(transaction.getUdf4());
		pgresponse.setUdf5(transaction.getUdf5());
		pgresponse.setPaymentMode(transaction.getPaymentMode());
		pgresponse.setTotalCharges(Utilities.getSurcharge(transaction));
		pgresponse.setTotalAmount(String.valueOf(uamount));
		logger.info("Pg Response ==========================>" + pgresponse);
		logger.info("FAILED  RESPONSE to Processor : txnId ==> " + pgresponse.getTransactionId() + " Response code ==> "
				+ pgresponse.getResponseCode() + " Timing ==>" + LocalDate.now());
		payresponse.setPaymentResponse(pgresponse);
		return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
				"Redirect process page", HttpStatus.OK);

	}

	private ResponseEntity<ResponseWrapper<String>> upiPay(RequestParams requestParams, TransactionLog transaction, Map<String, String> propMap, String token) throws Exception {

		logger.info("Payment Request Params: txnId ==>" + requestParams.getTransactionId());

		PayResponse payresponse = new PayResponse();
		if (requestParams.getName() != null && !requestParams.getName().equals("")) {
			transaction.setUdf1(requestParams.getName());
		}
		if (requestParams.getMobile() != null && !requestParams.getMobile().equals("")) {
			transaction.setUdf2(requestParams.getMobile());
		}
		if (requestParams.getUdf3() != null && !requestParams.getUdf3().equals("")) {
			transaction.setUdf3(requestParams.getUdf3());
		}
		transactionLogRepo.save(transaction);

		requestParams.setCardType("UPI");
		requestParams.setPaymentMode("UPI");
		requestParams.setProductType("IPG");
		
		PaymentResponse	pgresponse = apiService.callPaymentApi(requestParams,propMap);
		logger.info(" callPaymentApi Pg Response =>" + pgresponse);
		
		boolean result = pgresponse.isResult();
		if (transaction.getRiskStatus() == 2) {
			logger.info("Inside pp Risk Transaction is failed==========>");
			transaction.setTxnStatus("FAILED");
			transaction.setStage("Trasnaction is Failed");
			transaction.setResponseCode("01");
			transactionLogRepo.save(transaction);
			callbackService.addCallbackInQueue(transaction.getTransactionId());
		} else {
			transaction.setTxnStatus("INITIATED");
			transaction.setStage("Trasnaction is initiated");
			transaction.setResponseCode("02");
			transactionLogRepo.save(transaction);
		}

		BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(requestParams.getAmt()))
				.add(BigDecimal.valueOf(transaction.getTotalServiceCharge()).add(transaction.getCommision()));

		String threeDSecureUrl = pgresponse.getThreeDSecureUrl();
		// mark Initiated in Txn Log

		if (pgresponse.getProcessor() != null && pgresponse.getProcessor().equalsIgnoreCase("Simulator")) {
			payresponse.setOtpValidation("true");
			payresponse.setCardNo(null);
			payresponse.setTid(String.valueOf(transaction.getTransactionId()));
			payresponse.setMerchantName(transaction.getMerchantName());
			payresponse.setAmount(String.valueOf(transaction.getAmt()));
			payresponse.setTAmount(String.valueOf(amount));
			payresponse.setPaymentResponse(pgresponse);
			return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
					"Redirect simulator page", HttpStatus.OK);
		}

		if (result) {
			if (pgresponse.getPostReqParam() == null || pgresponse.getPostReqParam().size() <= 0
					|| pgresponse.getPostReqParam().isEmpty()) {
				payresponse.setRu(threeDSecureUrl);
				payresponse.setPaymentResponse(pgresponse);
				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token), "Redirect",
						HttpStatus.OK);
			} else {
				payresponse.setRu(threeDSecureUrl);
				payresponse.setRequestMapObj(pgresponse.getPostReqParam());
				payresponse.setPaymentResponse(pgresponse);
				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
						"Redirect payuProcess page", HttpStatus.OK);
			}
		}
		
		if (requestParams.getRu() != null && (!requestParams.getRu().trim().equals(""))
				&& (!requestParams.getRu().trim().equals("https://pay.getepay.in/getePaymentPages/paynetzResponse/"))) {
			pgresponse.setRu(requestParams.getRu());
		} else {
			pgresponse.setRu("");
		}
		logger.info("FAILED API Request to Processor : txnId ==>" + requestParams.getTransactionId() + " Timing ==>"
				+ LocalDate.now());

		pgresponse.setmId(transaction.getMerchantId());
		pgresponse.setResponseCode("01");
		pgresponse.setStatus("FAILED");
		pgresponse.setMerchantTxnId(requestParams.getMerchantTxnId());
		BigDecimal uamount = Utilities.requaryAmount(transaction);
		pgresponse.setAmt(requestParams.getAmt());

		pgresponse.setTransactionId(transaction.getTransactionId());
		pgresponse.setUdf1(transaction.getUdf1());
		pgresponse.setUdf2(transaction.getUdf2());
		pgresponse.setUdf3(transaction.getUdf3());
		pgresponse.setUdf4(transaction.getUdf4());
		pgresponse.setUdf5(transaction.getUdf5());
		pgresponse.setPaymentMode(transaction.getPaymentMode());
		pgresponse.setTotalCharges(Utilities.getSurcharge(transaction));
		pgresponse.setTotalAmount(String.valueOf(uamount));
		logger.info("Pg Response ==========================>" + pgresponse);
		logger.info("FAILED  RESPONSE to Processor : txnId ==> " + pgresponse.getTransactionId() + " Response code ==> "
				+ pgresponse.getResponseCode() + " Timing ==>" + LocalDate.now());
		payresponse.setPaymentResponse(pgresponse);
		return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
				"Redirect process page", HttpStatus.OK);

	}

	private ResponseEntity<ResponseWrapper<String>> nbPay(RequestParams requestParams, TransactionLog transaction, Map<String, String> propMap, String token) throws Exception {

		logger.info("Payment Request Params: txnId ==>" + requestParams.getTransactionId());

		PayResponse payresponse = new PayResponse();
		PaymentResponse pgresponse = new PaymentResponse();
		if (requestParams.getName() != null && !requestParams.getName().equals("")) {
			transaction.setUdf1(requestParams.getName());
		}
		if (requestParams.getNbmobile() != null && !requestParams.getNbmobile().equals("")) {
			transaction.setUdf2(requestParams.getNbmobile());
		}
		if (requestParams.getUdf3() != null && !requestParams.getUdf3().equals("")) {
			transaction.setUdf3(requestParams.getUdf3());
		}
		transactionLogRepo.save(transaction);


		requestParams.setCardType("NB");
		requestParams.setPaymentMode("NB");
		requestParams.setProductType("IPG");
		
		pgresponse = apiService.callPaymentApi(requestParams,propMap);
		logger.info(" callPaymentApi Pg Response =>" + pgresponse);
		Bank bank = bankRepo.findById(Long.valueOf(requestParams.getNbbankid())).get();

		boolean result = pgresponse.isResult();
		if (transaction.getRiskStatus() == 2) {
			logger.info("Inside pp Risk Transaction is failed==========>");
			transaction.setTxnStatus("FAILED");
			transaction.setStage("Trasnaction is Failed");
			transaction.setResponseCode("01");
			transactionLogRepo.save(transaction);
			callbackService.addCallbackInQueue(transaction.getTransactionId());
		} else {
			transaction.setTxnStatus("INITIATED");
			transaction.setStage("Trasnaction is initiated");
			transaction.setResponseCode("02");
			transactionLogRepo.save(transaction);
		}

		if (bank != null) {
			if (transaction.getProcessor() != null && !transaction.getProcessor().equals("")
					&& !transaction.getProcessor().equals("PAYNETZ")) {
				transaction.setBankname(bank.getBankName());

			}
		}
		BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(requestParams.getAmt()))
				.add(BigDecimal.valueOf(transaction.getTotalServiceCharge()).add(transaction.getCommision()));

		String threeDSecureUrl = pgresponse.getThreeDSecureUrl();
		if (pgresponse.getProcessor() != null && pgresponse.getProcessor().equalsIgnoreCase("Simulator")) {
			payresponse.setOtpValidation("true");
			payresponse.setCardNo(null);
			payresponse.setTid(String.valueOf(transaction.getTransactionId()));
			payresponse.setMerchantName(transaction.getMerchantName());
			payresponse.setAmount(String.valueOf(transaction.getAmt()));
			payresponse.setTAmount(String.valueOf(amount));
			payresponse.setPaymentResponse(pgresponse);
			return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
					"Redirect simulator page", HttpStatus.OK);
		}

		if (result) {

			if (pgresponse.getPostReqParam() == null || pgresponse.getPostReqParam().size() <= 0
					|| pgresponse.getPostReqParam().isEmpty()) {
				payresponse.setRu(threeDSecureUrl);
				payresponse.setPaymentResponse(pgresponse);
				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token), "Redirect",
						HttpStatus.OK);
			} else {
				payresponse.setRu(threeDSecureUrl);
				payresponse.setRequestMapObj(pgresponse.getPostReqParam());
				payresponse.setPaymentResponse(pgresponse);
				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
						"Redirect payuProcess page", HttpStatus.OK);

			}

		}
		if (requestParams.getRu() != null && (!requestParams.getRu().trim().equals(""))
				&& (!requestParams.getRu().trim().equals("https://pay.getepay.in/getePaymentPages/paynetzResponse/"))) {
			pgresponse.setRu(requestParams.getRu());
		} else {
			pgresponse.setRu("");
		}
		logger.info("FAILED API Request to Processor : txnId ==>" + requestParams.getTransactionId() + " Timing ==>"
				+ LocalDate.now());
		try {
			pgresponse.setmId(transaction.getMerchantId());
		} catch (Exception e) {
			pgresponse.setmId(0l);
		}
		pgresponse.setResponseCode("01");
		pgresponse.setStatus("FAILED");
		pgresponse.setMerchantTxnId(requestParams.getMerchantTxnId());
		BigDecimal uamount = Utilities.requaryAmount(transaction);
		pgresponse.setAmt(requestParams.getAmt().toString());
		String signatureKey = SignatureGenerator.signatureGeneration(new String[] { requestParams.getLogin(),
				requestParams.getMerchantTxnId(), requestParams.getAmt(), pgresponse.getStatus() },
				requestParams.getMerchantTxnId());
		pgresponse.setSignature(signatureKey);
		pgresponse.setUdf1(transaction.getUdf1());
		pgresponse.setUdf2(transaction.getUdf2());
		pgresponse.setUdf3(transaction.getUdf3());
		pgresponse.setUdf4(transaction.getUdf4());
		pgresponse.setUdf5(transaction.getUdf5());
		pgresponse.setPaymentMode(transaction.getPaymentMode());
		pgresponse.setTotalCharges(Utilities.getSurcharge(transaction));
		pgresponse.setTotalAmount(String.valueOf(uamount));
		logger.info("Pg Response ==========================>" + pgresponse);
		logger.info("FAILED  RESPONSE to Processor : txnId ==> " + pgresponse.getTransactionId() + " Response code ==> "
				+ pgresponse.getResponseCode() + " Timing ==>" + LocalDate.now());
		payresponse.setPaymentResponse(pgresponse);
		return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
				"Redirect process page", HttpStatus.OK);

	}

	private ResponseEntity<ResponseWrapper<String>> dcPay(RequestParams requestParams, TransactionLog transaction, Map<String, String> propMap, String token) throws Exception {

		GenerateOTPResponse generateOtp = new GenerateOTPResponse();
		Gson gson = new Gson();
		PayResponse payresponse = new PayResponse();

		logger.info("Payment Request Params: txnId ==>" + requestParams.getTransactionId());

		if (requestParams.getName() != null && !requestParams.getName().equals("")) {
			transaction.setUdf1(requestParams.getName());
		}
		if (requestParams.getMobile() != null && !requestParams.getMobile().equals("")) {
			transaction.setUdf2(requestParams.getMobile());
		}
		if (requestParams.getUdf3() != null && !requestParams.getUdf3().equals("")) {
			transaction.setUdf3(requestParams.getUdf3());
		}
		transactionLogRepo.save(transaction);
		Merchant merchant = merchantRepo.findByMid(transaction.getMerchantId());

		requestParams.setCardType("DC");
		requestParams.setExpiry(requestParams.getMonth() + "/" + requestParams.getYear());
		requestParams.setPaymentMode("DC");
		
		PaymentResponse pgresponse = apiService.callPaymentApi(requestParams,propMap);
		logger.info(" callPaymentApi Pg Response =>" + pgresponse);
		
		BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(requestParams.getAmt()))
				.add(BigDecimal.valueOf(transaction.getTotalServiceCharge()).add(transaction.getCommision()));

		String threeDSecureUrl = pgresponse.getThreeDSecureUrl();

		boolean result = pgresponse.isResult();
		if (transaction.getRiskStatus() == 2) {
			logger.info("Inside pp Risk Transaction is failed==========>");
			transaction.setTxnStatus("FAILED");
			transaction.setStage("Trasnaction is Failed");
			transaction.setResponseCode("01");
			transactionLogRepo.save(transaction);
			callbackService.addCallbackInQueue(transaction.getTransactionId());
		} else {
			transaction.setTxnStatus("INITIATED");
			transaction.setStage("Trasnaction is initiated");
			transaction.setResponseCode("02");
			transactionLogRepo.save(transaction);
		}

		if (result) {

			logger.info("pgresponse => " + pgresponse.getProcessor() + " : " + pgresponse.getType());
			if (pgresponse.getProcessor() != null && pgresponse.getProcessor().equalsIgnoreCase("Lyra")) {
				if (pgresponse.getType() != null && pgresponse.getType().equalsIgnoreCase("IFRAME")) {
					logger.info("transactionUuid => " + pgresponse.getHtml());
					payresponse.setUuid(pgresponse.getHtml());
					payresponse.setTid(String.valueOf(transaction.getTransactionId()));
					payresponse.setPaymentResponse(pgresponse);
					return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
							"Redirect lyra-iframe page", HttpStatus.OK);
				} else if (pgresponse.getType() != null && pgresponse.getType().equalsIgnoreCase("H2HOTP")) {
					MerchantSetting merchantSetting = new MerchantSetting();
					merchantSetting.setMerchantId(Long.valueOf(transaction.getMerchantId()));
					merchantSetting.setCurrency(requestParams.getTxncurr());
					merchantSetting.setPaymentMode(requestParams.getPaymentMode());
					merchantSetting.setProductType(requestParams.getProductType());

					merchantSetting = merchantSettingDao.findDefaultMerchantSetting(merchantSetting);

					String authoriztion = merchantSetting.getMloginId() + ":" + merchantSetting.getmPassword();
					authoriztion = Base64.getEncoder().encodeToString(authoriztion.getBytes());

					PropertiesVo properties = new PropertiesVo();
					properties = propertiesService
							.findByPropertykeyWithUpdatedCerts(String.valueOf(LyraUtil.LYRA_BASE_URL_TXN));
					String baseUrlTxn = properties.getPropertyValue();

					TransactionEssentials transactionEssentials = transactionEssentialsRepo
							.findByTransactionId(transaction.getTransactionId());

					if (transactionEssentials != null && transactionEssentials.getUdf60() != null
							&& !transactionEssentials.getUdf60().equals("")
							&& !transactionEssentials.getUdf60().equalsIgnoreCase("null")) {
						payresponse.setIpAddress(transactionEssentials.getUdf60());
					} else {
						payresponse.setIpAddress("192.168.0.1");
					}

					payresponse.setUuid(pgresponse.getHtml());
					payresponse.setCardNo(requestParams.getNumber().substring(requestParams.getNumber().length() - 5));
					payresponse.setMerchantName(transaction.getMerchantName());
					payresponse.setAmount(String.valueOf(Utilities.requaryAmount(transaction)));
					payresponse.setTid(String.valueOf(transaction.getTransactionId()));
					payresponse.setAuthoriztion(authoriztion);
					payresponse.setBaseUrlTxn(baseUrlTxn);
					payresponse.setPaymentResponse(pgresponse);

					return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
							"Redirect lyra-h2hotp page", HttpStatus.OK);

				}
			}
			if (pgresponse.getProcessor() != null && pgresponse.getProcessor().equalsIgnoreCase("KOTAK BANK")) {
				if (pgresponse.getType() != null && pgresponse.getType().equalsIgnoreCase("RUPAY")) {
					MerchantSetting merchantSetting = new MerchantSetting();
					merchantSetting.setMerchantId(Long.valueOf(transaction.getMerchantId()));
					merchantSetting.setCurrency(requestParams.getTxncurr());
					merchantSetting.setPaymentMode(requestParams.getPaymentMode());
					merchantSetting.setProductType(requestParams.getProductType());

					merchantSetting = merchantSettingDao.findDefaultMerchantSetting(merchantSetting);
					String dataBEPG = new String(Base64.getDecoder().decode(pgresponse.getHtml().getBytes()));

					logger.info("transactionUuid => " + pgresponse.getHtml());
					payresponse.setCardNo(requestParams.getNumber().substring(requestParams.getNumber().length() - 5));
					payresponse.setMerchantName(transaction.getMerchantName());
					payresponse.setAmount(String.valueOf(amount));
					payresponse.setTid(String.valueOf(transaction.getTransactionId()));
					payresponse.setDataBEPG(pgresponse.getHtml());
					payresponse.setPaymentResponse(pgresponse);

					return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
							"Redirect kotakrupay-otp page", HttpStatus.OK);
				}

			}
			if (pgresponse.getProcessor() != null && pgresponse.getProcessor().equalsIgnoreCase("SBI BANK")) {
				logger.info("Inside PP dcPay SBI BANK=============================>"
						+ " For transaction_id=============================>" + transaction.getTransactionId());
				if (pgresponse.getType() != null && pgresponse.getType().equalsIgnoreCase("SEAMLESS")) {
					logger.info("Inside PP dcPay SBI BANK SEAMLESS=============================>"
							+ " For transaction_id=============================>" + transaction.getTransactionId());
					MerchantSetting merchantSetting = new MerchantSetting();
					merchantSetting.setMerchantId(Long.valueOf(transaction.getMerchantId()));
					merchantSetting.setCurrency(requestParams.getTxncurr());
					merchantSetting.setPaymentMode(requestParams.getPaymentMode());
					merchantSetting.setProductType(requestParams.getProductType());

					merchantSetting = merchantSettingDao.findDefaultMerchantSetting(merchantSetting);
					SbiRequestHeader header = new SbiRequestHeader();

					header.setXapikey(propMap.get(SBICardsUtils.SBI_HEADER_XAPI_KEY));
					header.setPgInstanceId(propMap.get(SBICardsUtils.SBI_RUPAY_HEADER_PG_INSTANCE_ID));
					header.setMerchantId(propMap.get(SBICardsUtils.SBI_RUPAY_HEADER_MERCHANT_ID));

					logger.info("Sbi Rupay Header Headers======>" + header
							+ " For transaction_id=============================>" + transaction.getTransactionId());

					CardDetailsVo cardDetailsVo = gson.fromJson(pgresponse.getPostReqParam().get("carddetailsuser"),
							CardDetailsVo.class);
					try {

						BigDecimal mAmount = Utilities.requaryAmount(transaction).setScale(2,
								BigDecimal.ROUND_HALF_EVEN);
						int rupayamount = mAmount.multiply(new BigDecimal(100)).intValue();
						TransactionEssentials transactionEssentials = transactionEssentialsRepo
								.findByTransactionId(Long.valueOf(transaction.getTransactionId()));
						generateOtp = SbiCardUtilCall.generateotp(cardDetailsVo, header, propMap, merchant,
								merchantSetting, transaction, rupayamount, transactionEssentials);
					} catch (Exception e) {
						new GlobalExceptionHandler().customException(e);
					}

					logger.info("Inside PP dcPay otp generate succesfully=============================>");
					payresponse.setCardNo(requestParams.getNumber().substring(requestParams.getNumber().length() - 5));
					payresponse.setMerchantName(transaction.getMerchantName());
					payresponse.setAmount(String.valueOf(Utilities.requaryAmount(transaction)));
					payresponse.setTid(String.valueOf(transaction.getTransactionId()));
					payresponse.setProductType(pgresponse.getType());
					payresponse.setPgTransactionId(generateOtp.getPgTransactionId());
					payresponse.setPaymentResponse(pgresponse);

					return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
							"Redirect sbirupay-otp page", HttpStatus.OK);

				}
				if (pgresponse.getType() != null && pgresponse.getType().equalsIgnoreCase("REDIRECT")) {

					logger.info("Inside dcpay SBI Rupay Redirect =================>"
							+ " For transaction_id=============================>" + transaction.getTransactionId());
					payresponse.setRu(threeDSecureUrl);
					payresponse.setRequestMapObj(pgresponse.getPostReqParam());
					payresponse.setPaymentResponse(pgresponse);
					logger.info("SBI Rupay Redirect Generate Otp successFully=============>"
							+ " For transaction_id=============================>" + transaction.getTransactionId());
					return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
							"Redirect payuProcess page", HttpStatus.OK);

				}
			}

			if (pgresponse.getProcessor() != null && pgresponse.getProcessor().equalsIgnoreCase("Simulator")) {
				payresponse.setOtpValidation("true");
				payresponse.setCardNo(requestParams.getNumber().substring(requestParams.getNumber().length() - 5));
				payresponse.setTid(String.valueOf(transaction.getTransactionId()));
				payresponse.setMerchantName(transaction.getMerchantName());
				payresponse.setAmount(String.valueOf(transaction.getAmt()));
				payresponse.setTAmount(String.valueOf(amount));
				payresponse.setPaymentResponse(pgresponse);
				payresponse.setRu(threeDSecureUrl);
				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
						"Redirect simulator page", HttpStatus.OK);

			}

//			return "redirect:" + threeDSecureUrl;
			if (pgresponse.getPostReqParam() == null || pgresponse.getPostReqParam().size() <= 0
					|| pgresponse.getPostReqParam().isEmpty()) {
				payresponse.setRu(threeDSecureUrl);
				payresponse.setPaymentResponse(pgresponse);
				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token), "Redirect",
						HttpStatus.OK);
			} else {
				payresponse.setRu(threeDSecureUrl);
				payresponse.setRequestMapObj(pgresponse.getPostReqParam());
				payresponse.setPaymentResponse(pgresponse);
				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
						"Redirect payuProcess page", HttpStatus.OK);

			}

		}

		if (pgresponse.isEnableHtml() && pgresponse.getHtml() != null && !pgresponse.getHtml().equals("")) {
			String html = new String(Base64.getDecoder().decode(pgresponse.getHtml().getBytes()));
			payresponse.setHtmlCode(html);
			payresponse.setPaymentResponse(pgresponse);
			return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
					"Redirect htmlRenderPage page", HttpStatus.OK);

		}

		if (requestParams.getRu() != null && (!requestParams.getRu().trim().equals(""))
				&& (!requestParams.getRu().trim().equals("https://pay.getepay.in/getePaymentPages/paynetzResponse/"))) {
			pgresponse.setRu(requestParams.getRu());
		} else {
			pgresponse.setRu("");
		}
		logger.info("FAILED API Request to Processor : txnId ==>" + requestParams.getTransactionId() + " Timing ==>"
				+ LocalDate.now());
		try {
			pgresponse.setmId(transaction.getMerchantId());
		} catch (Exception e) {
			pgresponse.setmId(0l);
		}
		pgresponse.setResponseCode("01");
		pgresponse.setStatus("FAILED");
		pgresponse.setMerchantTxnId(requestParams.getMerchantTxnId());
		BigDecimal uamount = Utilities.requaryAmount(transaction);
		logger.info("Amount added with convience charges and commision charges=====>" + uamount);
		pgresponse.setAmt(requestParams.getAmt());
		String signatureKey = SignatureGenerator.signatureGeneration(new String[] { requestParams.getLogin(),
				requestParams.getMerchantTxnId(), requestParams.getAmt(), pgresponse.getStatus() },
				requestParams.getMerchantTxnId());
		pgresponse.setSignature(signatureKey);
		pgresponse.setUdf1(transaction.getUdf1());
		pgresponse.setUdf2(transaction.getUdf2());
		pgresponse.setUdf3(transaction.getUdf3());
		pgresponse.setUdf4(transaction.getUdf4());
		pgresponse.setUdf5(transaction.getUdf5());
		pgresponse.setPaymentMode(transaction.getPaymentMode());
		pgresponse.setTotalCharges(Utilities.getSurcharge(transaction));
		pgresponse.setTotalAmount(String.valueOf(uamount));
		logger.info("Pg Response ==========================>" + pgresponse);
		logger.info("FAILED  RESPONSE to Processor : txnId ==> " + pgresponse.getTransactionId() + " Response code ==> "
				+ pgresponse.getResponseCode() + " Timing ==>" + LocalDate.now());
		logger.info("FAILED  RESPONSE to Processor : txnId ==> " + pgresponse.getTransactionId() + " Response code ==> "
				+ pgresponse.getResponseCode() + " Timing ==>" + LocalDate.now());
		payresponse.setPaymentResponse(pgresponse);
		return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
				"Redirect process page", HttpStatus.OK);

	}

	private ResponseEntity<ResponseWrapper<String>> ccPay(RequestParams requestParams, TransactionLog transaction, Map<String, String> propMap, String token) throws Exception {
		Gson gson = new Gson();
		GenerateOTPResponse generateOtp = new GenerateOTPResponse();
		logger.info("Payment Request Params: txnId ==>" + requestParams.getTransactionId());
		
		if (requestParams.getName() != null && !requestParams.getName().equals("")) {
			transaction.setUdf1(requestParams.getName());
		}
		if (requestParams.getMobile() != null && !requestParams.getMobile().equals("")) {
			transaction.setUdf2(requestParams.getMobile());
		}
		if (requestParams.getUdf3() != null && !requestParams.getUdf3().equals("")) {
			transaction.setUdf3(requestParams.getUdf3());
		}
		transactionLogRepo.save(transaction);
		
		requestParams.setPaymentMode("CC");
		requestParams.setCardType("CC");
		requestParams.setExpiry(requestParams.getMonth() + "/" + requestParams.getYear());
		
		PaymentResponse pgresponse = apiService.callPaymentApi(requestParams,propMap);
		logger.info(" callPaymentApi Pg Response =>" + pgresponse);

		BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(requestParams.getAmt()))
				.add(BigDecimal.valueOf(transaction.getTotalServiceCharge()).add(transaction.getCommision()));

		String threeDSecureUrl = pgresponse.getThreeDSecureUrl();

		if (transaction.getRiskStatus() == 2) {
			logger.info("Inside pp Risk Transaction is failed==========>");
			transaction.setTxnStatus("FAILED");
			transaction.setStage("Trasnaction is Failed");
			transaction.setResponseCode("01");
			callbackService.addCallbackInQueue(transaction.getTransactionId());
		} else {
			transaction.setTxnStatus("INITIATED");
			transaction.setStage("Trasnaction is initiated");
			transaction.setResponseCode("02");
		}
		transactionLogRepo.save(transaction);
		logger.info("transaction saved : " + transaction.getTransactionId());

		PayResponse payresponse = new PayResponse();
		boolean result = pgresponse.isResult();
		if (result) {

			logger.info("pgresponse => " + pgresponse.getProcessor() + " : " + pgresponse.getType());
			if (pgresponse.getProcessor() != null && pgresponse.getProcessor().equalsIgnoreCase("Lyra")) {
				if (pgresponse.getType() != null && pgresponse.getType().equalsIgnoreCase("IFRAME")) {
					logger.info("transactionUuid => " + pgresponse.getHtml());
					payresponse.setUuid(pgresponse.getHtml());
					payresponse.setTid(String.valueOf(transaction.getTransactionId()));
					payresponse.setPaymentResponse(pgresponse);
					return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
							"Redirect lyra-iframe page", HttpStatus.OK);
				} else if (pgresponse.getType() != null && pgresponse.getType().equalsIgnoreCase("H2HOTP")) {
					MerchantSetting merchantSetting = new MerchantSetting();
					merchantSetting.setMerchantId(Long.valueOf(transaction.getMerchantId()));
					merchantSetting.setCurrency(requestParams.getTxncurr());
					merchantSetting.setPaymentMode(requestParams.getPaymentMode());
					merchantSetting.setProductType(requestParams.getProductType());

					merchantSetting = merchantSettingDao.findDefaultMerchantSetting(merchantSetting);

					String authoriztion = merchantSetting.getMloginId() + ":" + merchantSetting.getmPassword();
					authoriztion = Base64.getEncoder().encodeToString(authoriztion.getBytes());

					PropertiesVo properties = new PropertiesVo();
					properties = propertiesService
							.findByPropertykeyWithUpdatedCerts(String.valueOf(LyraUtil.LYRA_BASE_URL_TXN));
					String baseUrlTxn = properties.getPropertyValue();

					TransactionEssentials transactionEssentials = transactionEssentialsRepo
							.findByTransactionId(transaction.getTransactionId());
					if (transactionEssentials != null && transactionEssentials.getUdf60() != null
							&& !transactionEssentials.getUdf60().equals("")
							&& !transactionEssentials.getUdf60().equalsIgnoreCase("null")) {
						payresponse.setIpAddress(transactionEssentials.getUdf60());
					} else {
						payresponse.setIpAddress("192.168.0.1");
					}

					logger.info("transactionUuid => " + pgresponse.getHtml());
					payresponse.setUuid(pgresponse.getHtml());
					payresponse.setIpAddress(requestParams.getIpAddress());
					payresponse.setCardNo(requestParams.getNumber().substring(requestParams.getNumber().length() - 5));
					payresponse.setMerchantName(transaction.getMerchantName());
					payresponse.setAmount(String.valueOf(amount));
					payresponse.setTid(String.valueOf(transaction.getTransactionId()));
					payresponse.setAuthoriztion(authoriztion);
					payresponse.setBaseUrlTxn(baseUrlTxn);
					payresponse.setPaymentResponse(pgresponse);

					return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
							"Redirect lyra-h2hotp page", HttpStatus.OK);

				}
			}
			if (pgresponse.getProcessor() != null && pgresponse.getProcessor().equalsIgnoreCase("KOTAK BANK")) {
				if (pgresponse.getType() != null && pgresponse.getType().equalsIgnoreCase("RUPAY")) {

					logger.info("transactionUuid => " + pgresponse.getHtml());
					payresponse.setCardNo(requestParams.getNumber().substring(requestParams.getNumber().length() - 5));
					payresponse.setMerchantName(transaction.getMerchantName());
					payresponse.setAmount(String.valueOf(amount));
					payresponse.setTid(String.valueOf(transaction.getTransactionId()));
//					String dataBEPG = new String(Base64.getDecoder().decode(pgresponse.getHtml().getBytes()));
					payresponse.setDataBEPG(pgresponse.getHtml());
					payresponse.setPaymentResponse(pgresponse);

					return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
							"Redirect kotakrupay-otp page", HttpStatus.OK);
				}
			}
			if (pgresponse.getProcessor() != null && pgresponse.getProcessor().equalsIgnoreCase("SBI BANK")) {

				logger.info("Inside PP ccPay SBI BANK=============================>"
						+ " For transaction_id=============================>" + transaction.getTransactionId());
				if (pgresponse.getType() != null && pgresponse.getType().equalsIgnoreCase("SEAMLESS")) {

					logger.info("Inside PP ccPay SBI BANK the Card is SEAMLESS=============================>");

					MerchantSetting merchantSetting = new MerchantSetting();
					merchantSetting.setMerchantId(Long.valueOf(transaction.getMerchantId()));
					merchantSetting.setCurrency(requestParams.getTxncurr());
					merchantSetting.setPaymentMode(requestParams.getPaymentMode());
					merchantSetting.setProductType(requestParams.getProductType());

					merchantSetting = merchantSettingDao.findDefaultMerchantSetting(merchantSetting);
					SbiRequestHeader header = new SbiRequestHeader();

//					header.setXapikey("849ca23e-b115-11ed-a376-005056b59d84");
//					header.setPgInstanceId("72702415");
//					header.setMerchantId("72702415");

					header.setXapikey(propMap.get(SBICardsUtils.SBI_HEADER_XAPI_KEY));
					header.setPgInstanceId(propMap.get(SBICardsUtils.SBI_RUPAY_HEADER_PG_INSTANCE_ID));
					header.setMerchantId(propMap.get(SBICardsUtils.SBI_RUPAY_HEADER_MERCHANT_ID));

					logger.info("Sbi Rupay Seamless Headers======>"
							+ " For transaction_id=============================>" + transaction.getTransactionId());

					CardDetailsVo cardDetailsVo = gson.fromJson(pgresponse.getPostReqParam().get("carddetailsuser"),
							CardDetailsVo.class);
					try {

						BigDecimal mAmount = Utilities.requaryAmount(transaction).setScale(2,
								BigDecimal.ROUND_HALF_EVEN);
						int rupayamount = mAmount.multiply(new BigDecimal(100)).intValue();

						TransactionEssentials transactionEssentials = transactionEssentialsRepo
								.findByTransactionId(Long.valueOf(transaction.getTransactionId()));
						Merchant merchant = merchantRepo.findByMid(transaction.getMerchantId());
						generateOtp = SbiCardUtilCall.generateotp(cardDetailsVo, header, propMap, merchant,
								merchantSetting, transaction, rupayamount, transactionEssentials);
					} catch (Exception e) {
						new GlobalExceptionHandler().customException(e);

					}

					logger.info("SBI Rupay Seamless Generate Otp successFully");
					payresponse.setCardNo(requestParams.getNumber().substring(requestParams.getNumber().length() - 5));
					payresponse.setMerchantName(transaction.getMerchantName());
					payresponse.setAmount(String.valueOf(Utilities.requaryAmount(transaction)));
					payresponse.setTid(String.valueOf(transaction.getTransactionId()));
					payresponse.setProductType(pgresponse.getType());
					payresponse.setPgTransactionId(generateOtp.getPgTransactionId());
					payresponse.setPaymentResponse(pgresponse);

					return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
							"Redirect sbirupay-otp page", HttpStatus.OK);
				}
				if (pgresponse.getType() != null && pgresponse.getType().equalsIgnoreCase("REDIRECT")) {
					logger.info("Inside SBI Rupay Redirect =================>"
							+ " For transaction_id=============================>" + transaction.getTransactionId());

					payresponse.setRu(threeDSecureUrl);
					payresponse.setRequestMapObj(pgresponse.getPostReqParam());
					payresponse.setPaymentResponse(pgresponse);

					logger.info("SBI Rupay Redirect Generate Otp successFully=============>"
							+ " For transaction_id=============================>" + transaction.getTransactionId());
					return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
							"Redirect payuProcess page", HttpStatus.OK);
				}
			}
			if (pgresponse.getProcessor() != null && pgresponse.getProcessor().equalsIgnoreCase("PAYNETZNEW")) {

				logger.info("PAYNETZNEW Running=================>" + " For transaction_id=============================>"
						+ transaction.getTransactionId());
				payresponse.setTokenId(pgresponse.getPostReqParam().get("TokenId"));
				payresponse.setAmount(pgresponse.getPostReqParam().get("amount"));
				payresponse.setMerchantId(pgresponse.getPostReqParam().get("merchantId"));
				payresponse.setRu(pgresponse.getPostReqParam().get("returnURL"));
				payresponse.setPaymentResponse(pgresponse);

				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
						"Redirect checkout page", HttpStatus.OK);
			}

			if (pgresponse.getProcessor() != null && pgresponse.getProcessor().equalsIgnoreCase("Simulator")) {
				payresponse.setOtpValidation("true");
				payresponse.setCardNo(requestParams.getNumber().substring(requestParams.getNumber().length() - 5));
				payresponse.setTid(String.valueOf(transaction.getTransactionId()));
				payresponse.setMerchantName(transaction.getMerchantName());
				payresponse.setAmount(String.valueOf(transaction.getAmt()));
				payresponse.setTAmount(String.valueOf(amount));
				payresponse.setPaymentResponse(pgresponse);
				payresponse.setFlag("OTP");
				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
						"Redirect simulator page", HttpStatus.OK);

			}
			logger.info("pgresponse null processor:  ==>" + pgresponse);
			if (pgresponse.getPostReqParam() == null || pgresponse.getPostReqParam().size() <= 0
					|| pgresponse.getPostReqParam().isEmpty()) {
				payresponse.setRu(threeDSecureUrl);
				payresponse.setPaymentResponse(pgresponse);
				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token), "Redirect",
						HttpStatus.OK);
			} else {
				payresponse.setRu(threeDSecureUrl);
				payresponse.setRequestMapObj(pgresponse.getPostReqParam());
				payresponse.setPaymentResponse(pgresponse);
				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
						"Redirect payuProcess page", HttpStatus.OK);
			}
		}
		logger.info("pgresponse isEnableHtml :  ==>" + pgresponse);
		if (pgresponse.isEnableHtml() && pgresponse.getHtml() != null && !pgresponse.getHtml().equals("")) {
			String html = new String(Base64.getDecoder().decode(pgresponse.getHtml().getBytes()));
			payresponse.setHtmlCode(html);
			payresponse.setPaymentResponse(pgresponse);
			return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
					"Redirect htmlRenderPage page", HttpStatus.OK);
		}

		if (requestParams.getRu() != null && (!requestParams.getRu().trim().equals(""))
				&& (!requestParams.getRu().trim().equals("https://pay.getepay.in/getePaymentPages/paynetzResponse/"))) {
			pgresponse.setRu(requestParams.getRu());
		} else {
			pgresponse.setRu("");
		}
		logger.info("FAILED API Request to Processor : txnId ==>" + requestParams.getTransactionId() + " Timing ==>"
				+ LocalDate.now());

//		transaction = transactionLogRepo.findById(pgresponse.getTransactionId()).get();
		try {
			pgresponse.setmId(transaction.getMerchantId());
		} catch (Exception e) {
			pgresponse.setmId(0l);
		}
		pgresponse.setResponseCode("01");
		pgresponse.setStatus("FAILED");
		pgresponse.setMerchantTxnId(requestParams.getMerchantTxnId());
		BigDecimal uamount = Utilities.requaryAmount(transaction);
		pgresponse.setAmt(requestParams.getAmt());
		String signatureKey = SignatureGenerator.signatureGeneration(new String[] { requestParams.getLogin(),
				requestParams.getMerchantTxnId(), requestParams.getAmt(), pgresponse.getStatus() },
				requestParams.getMerchantTxnId());
		pgresponse.setSignature(signatureKey);
		pgresponse.setUdf1(transaction.getUdf1());
		pgresponse.setUdf2(transaction.getUdf2());
		pgresponse.setUdf3(transaction.getUdf3());
		pgresponse.setUdf4(transaction.getUdf4());
		pgresponse.setUdf5(transaction.getUdf5());
		pgresponse.setPaymentMode(transaction.getPaymentMode());
		pgresponse.setTotalCharges(Utilities.getSurcharge(transaction));
		pgresponse.setTotalAmount(String.valueOf(uamount));
		pgresponse.setTransactionId(Long.valueOf(requestParams.getTransactionId()));
		logger.info("Pg Response ==========================>" + pgresponse);
		logger.info("FAILED  RESPONSE to Processor : txnId ==> " + pgresponse.getTransactionId() + " Response code ==> "
				+ pgresponse.getResponseCode() + " Timing ==>" + LocalDate.now());
		payresponse.setPaymentResponse(pgresponse);
		return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
				"Redirect process page", HttpStatus.OK);
	}

}
