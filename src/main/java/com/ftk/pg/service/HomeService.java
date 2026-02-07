package com.ftk.pg.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ftk.pg.dto.RequestWrapper;
import com.ftk.pg.dto.ResponseBuilder;
import com.ftk.pg.dto.ResponseWrapper;
import com.ftk.pg.encryption.AesEncryption;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.Merchant;
import com.ftk.pg.modal.TransactionLog;
import com.ftk.pg.repo.MerchantRepo;
import com.ftk.pg.repo.TransactionLogRepo;
import com.ftk.pg.requestvo.API_PaymentRequest;
import com.ftk.pg.requestvo.ChallanParam;
import com.ftk.pg.requestvo.PaymentDeclineVo;
import com.ftk.pg.requestvo.PropertiesVo;
import com.ftk.pg.requestvo.RequestProductsVo;
import com.ftk.pg.requestvo.ShowImageRequest;
import com.ftk.pg.responsevo.PayResponse;
import com.ftk.pg.responsevo.PaymentResponse;
import com.ftk.pg.responsevo.ShowImageResponse;
import com.ftk.pg.responsevo.UpiCollectResponse;
import com.ftk.pg.util.Constants;
import com.ftk.pg.util.EncryptionUtil;
import com.ftk.pg.util.Utilities;
import com.google.gson.Gson;
import com.pgcomponent.security.SignatureGenerator;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HomeService {

	private static final Logger logger = LoggerFactory.getLogger(HomeService.class);


	private final TransactionLogRepo transactionLogRepo;

	
	private final EncryptionService encryptionService;

	
	private final PropertiesService propertiesService;


	private final MerchantRepo merchantRepo;

//	@Autowired
//	PropertiesRepo propertiesRepos;

	public ResponseEntity<ResponseWrapper<String>> pgtimeout(RequestWrapper requestWrapper, String token)
			throws Exception {

		API_PaymentRequest api_payment_request = EncryptionUtil.decryptdata(String.valueOf(requestWrapper.getData()),
				token, API_PaymentRequest.class);

		PayResponse payresponse = new PayResponse();
		TransactionLog transactionLog = new TransactionLog();

		String responseCode = "01";
		String txnStatus = "FAILED";
		PaymentResponse pgresponse = new PaymentResponse();

		if (api_payment_request != null) {
			logger.info(" requestParams !=null ");

			Optional<TransactionLog> transactionLogDetails = transactionLogRepo
					.findById(Long.valueOf(api_payment_request.getTransactionId()));

			transactionLog = transactionLogDetails.get();

			if (transactionLog != null && transactionLog.getTxnStatus().equalsIgnoreCase("SUCCESS")) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(
						HttpStatus.INTERNAL_SERVER_ERROR.value(), "Transaction is Already Success", null));
			}

			if (transactionLog != null && transactionLog.getProcessor() != null
					&& (transactionLog.getPaymentMode().equalsIgnoreCase("CHALLAN")
							|| transactionLog.getPaymentMode().equalsIgnoreCase("NEFT"))) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(
						HttpStatus.INTERNAL_SERVER_ERROR.value(), "Transaction is Already Neft or Challan", null));

			}

			transactionLog.setResponseCode(responseCode);
			transactionLog.setTxnStatus(txnStatus);
			transactionLog.setStage("Transaction is Failed.");
			transactionLog.setBankErrorMsg("Time Exceed ");
			transactionLog.setProcessorCode("01");
			transactionLog.setCustomername(null);
			transactionLogRepo.save(transactionLog);

			BigDecimal amount = requaryAmount(transactionLog);
			logger.info("Amount added with convience charges and commision charges=====>" + amount);
			pgresponse.setAmt(transactionLog.getAmt().setScale(2, RoundingMode.HALF_UP).toString());
			pgresponse.setmId(transactionLog.getMerchantId());
			pgresponse.setMerchantTxnId(transactionLog.getMerchanttxnid());
			pgresponse.setStatus(transactionLog.getTxnStatus());
			pgresponse.setTransactionId(transactionLog.getTransactionId());
			pgresponse.setRu(transactionLog.getRu());
			pgresponse.setUdf1(transactionLog.getCustomername());
			pgresponse.setUdf2(transactionLog.getUdf2());
			pgresponse.setUdf3(transactionLog.getUdf3());
			pgresponse.setUdf4(transactionLog.getUdf4());
			pgresponse.setUdf5(transactionLog.getUdf5());
			pgresponse.setPaymentMode(transactionLog.getPaymentMode());
			pgresponse.setTotalCharges(Utilities.getSurcharge(transactionLog));
			pgresponse.setTotalAmount(String.valueOf(amount));

			logger.info("Pg Response ==========================>" + pgresponse);
			String signatureKey = SignatureGenerator.signatureGeneration(new String[] { pgresponse.getmId().toString(),
					pgresponse.getMerchantTxnId(), pgresponse.getAmt(), pgresponse.getStatus() },
					pgresponse.getMerchantTxnId());
			pgresponse.setSignature(signatureKey);

			payresponse.setPaymentResponse(pgresponse);

			return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
					"Successfully Update", HttpStatus.OK);

		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Invalid Request", null));
		}

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

	public ResponseEntity<ResponseWrapper<String>> transactionCancel(RequestWrapper requestWrapper, String token)
			throws Exception {

		PaymentDeclineVo cancel_request = EncryptionUtil.decryptdata(String.valueOf(requestWrapper.getData()), token,
				PaymentDeclineVo.class);

		UpiCollectResponse payresponse = new UpiCollectResponse();
		PaymentResponse pgresponse = new PaymentResponse();

		if (cancel_request != null) {
			logger.info(" cancel_request = " + cancel_request);

			String responseCode = "01";
			String txnStatus = "FAILED";
			String declineType = cancel_request.getReason();

			Optional<TransactionLog> transactionLogDetails = transactionLogRepo
					.findById(Long.valueOf(cancel_request.getTransactionId()));

			TransactionLog transactionLog = transactionLogDetails.get();

			if (transactionLog != null && transactionLog.getTxnStatus() != null
					&& (transactionLog.getTxnStatus().equalsIgnoreCase("SUCCESS"))) {

				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(
						HttpStatus.INTERNAL_SERVER_ERROR.value(), "Transaction Request Processed", null));
			}

			transactionLog.setResponseCode(responseCode);
			transactionLog.setTxnStatus(txnStatus);
			transactionLog.setStage("Transaction is Failed.");
//			transactionLog.setBankErrorMsg("Cancelled By User");
			transactionLog.setBankErrorMsg(declineType);
			transactionLog.setProcessorCode("01");
			transactionLogRepo.save(transactionLog);

			BigDecimal amount = requaryAmount(transactionLog);
			logger.info("Amount added with convience charges and commision charges=====>" + amount);
			pgresponse.setAmt(transactionLog.getAmt().setScale(2, RoundingMode.HALF_UP).toString());
			pgresponse.setmId(transactionLog.getMerchantId());
			pgresponse.setMerchantTxnId(transactionLog.getMerchanttxnid());
			pgresponse.setStatus(transactionLog.getTxnStatus());
			pgresponse.setTransactionId(transactionLog.getTransactionId());
			pgresponse.setRu(transactionLog.getRu());
			pgresponse.setUdf1(transactionLog.getCustomername());
			pgresponse.setUdf2(transactionLog.getUdf2());
			pgresponse.setUdf3(transactionLog.getUdf3());
			pgresponse.setUdf4(transactionLog.getUdf4());
			pgresponse.setUdf5(transactionLog.getUdf5());
			pgresponse.setPaymentMode(transactionLog.getPaymentMode());
			pgresponse.setTotalCharges(Utilities.getSurcharge(transactionLog));
			pgresponse.setTotalAmount(String.valueOf(amount));
			logger.info("Pg Response ==========================>" + pgresponse);
			String signatureKey = SignatureGenerator.signatureGeneration(new String[] { pgresponse.getmId().toString(),
					pgresponse.getMerchantTxnId(), pgresponse.getAmt(), pgresponse.getStatus() },
					pgresponse.getMerchantTxnId());
			pgresponse.setSignature(signatureKey);

			payresponse.setPaymentResponse(pgresponse);

			return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payresponse, token),
					"Successfully Update", HttpStatus.OK);

		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Invalid Request", null));
		}

	}

	public ResponseEntity<ResponseWrapper<String>> showDynamicQr(RequestWrapper requestWrapper, String token)
			throws Exception {

		ResponseEntity<ResponseWrapper<String>> response = null;
		API_PaymentRequest api_payment_request = EncryptionUtil.decryptdata(String.valueOf(requestWrapper.getData()),
				token, API_PaymentRequest.class);

		String responseCode = "02";
		String txnStatus = "INITIATED";
		TransactionLog transactionLog = new TransactionLog();
		Optional<TransactionLog> transactionLogDetails = transactionLogRepo
				.findById(Long.valueOf(api_payment_request.getTransactionId()));

		transactionLog = transactionLogDetails.get();

		if (api_payment_request != null) {
			logger.info(" requestParams !=null ");

			if (transactionLog != null && transactionLog.getTxnStatus() != null
					&& (transactionLog.getTxnStatus().equalsIgnoreCase("SUCCESS")
							|| transactionLog.getTxnStatus().equalsIgnoreCase("FAILED"))) {

				return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(HttpStatus.OK.value(),
						"Transaction is already successfull or failed", null));

			}

			else if (transactionLog != null && transactionLog.getTxnStatus() != null
					&& transactionLog.getTxnStatus().equalsIgnoreCase("PENDING")) {
				transactionLog.setTxnStatus(txnStatus);
				transactionLog.setResponseCode(responseCode);
				transactionLog.setStage("Transaction is initated.");
				transactionLog.setPaymentMode("UPIQR");
				transactionLogRepo.save(transactionLog);
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseWrapper<>(HttpStatus.OK.value(), "Successfully Updated", null));
			}

		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Invalid Request", null));

	}

	public ResponseEntity<ResponseWrapper<String>> gchalan(RequestWrapper requestWrapper, String token)
			throws Exception {

		Gson gson = new Gson();
		Map<String, String> param = new HashMap<>();
		ResponseEntity<ResponseWrapper<String>> response = null;
		ChallanParam challanParam = new ChallanParam();
		API_PaymentRequest api_payment_request = EncryptionUtil.decryptdata(String.valueOf(requestWrapper.getData()),
				token, API_PaymentRequest.class);

		TransactionLog transactionLog = new TransactionLog();
		Optional<TransactionLog> transactionLogDetails = transactionLogRepo
				.findById(Long.valueOf(api_payment_request.getTransactionId()));

		transactionLog = transactionLogDetails.get();

		List<PropertiesVo> properties = propertiesService.findAllPropertyWithUpdatedCerts();

		Map<String, String> pMap = new HashMap<String, String>();
		for (PropertiesVo vo : properties) {
			pMap.put(vo.getPropertyKey(), vo.getPropertyValue());
		}

		String ifscCode = pMap.get(Constants.CHALLAN_IFSC_CODE);
		param.put("transaction", gson.toJson(transactionLog));
		param.put("ifscCode", ifscCode);
		Merchant merchant = merchantRepo.findById(transactionLog.getMerchantId()).get();
		param.put("merchant", gson.toJson(merchant));

		BigDecimal txnAmount = transactionLog.getAmt().add(transactionLog.getCommision());
		String amt = txnAmount.setScale(2, RoundingMode.HALF_UP).toPlainString();

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM yyyy");
		String challanDate = transactionLog.getCreatedDate().format(dtf);

		String challanValidity = transactionLog.getUdf6();
		if (challanValidity == null || challanValidity.equals("")) {
			LocalDateTime validity = transactionLog.getCreatedDate().plus(Period.ofDays(30));
			challanValidity = validity.format(dtf);
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date d = sdf.parse(challanValidity);
				SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMM yyyy");
				challanValidity = sdf2.format(d);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		Utilities.generateChallan(transactionLog, merchant, ifscCode, pMap);

		param.put("challanDate", challanDate);
		param.put("challanValidity", challanValidity);

		param.put("amount", amt);
		challanParam.setParams(param);
		return ResponseBuilder.buildResponse(encryptionService.encryptResponse(challanParam, token),
				"Successfully Update", HttpStatus.OK);

	}


	public ResponseEntity<ResponseWrapper<String>> payModeTrigger(RequestWrapper requestWrapper, String token) {
		Object data = requestWrapper.getData();
		logger.info("payModeTrigger data : " + String.valueOf(data));
		return ResponseBuilder.buildResponse("", "success", HttpStatus.OK);
	}

	public ResponseEntity<ResponseWrapper<String>> preProceedPayTrigger(RequestWrapper requestWrapper, String token) {
		Object data = requestWrapper.getData();
		logger.info("preProceedPayTrigger data : " + String.valueOf(data));
		return ResponseBuilder.buildResponse("", "success", HttpStatus.OK);
	}

	public ResponseEntity<ResponseWrapper<String>> postProceedPayTrigger(RequestWrapper requestWrapper, String token) {
		Object data = requestWrapper.getData();
		logger.info("postProceedPayTrigger data : " + String.valueOf(data));
		return ResponseBuilder.buildResponse("", "success", HttpStatus.OK);
	}

	public ResponseEntity<ResponseWrapper<String>> getTestLink(RequestWrapper requestWrapper, String token)
			throws Exception {
		String data = (String) requestWrapper.getData();
		List<PropertiesVo> byPropertykeyLike = propertiesService
				.findByPropertykeyWithUpdatedCertsLike(data);
		List<String> collect = byPropertykeyLike.stream().map(x -> x.getPropertyValue()).collect(Collectors.toList());
		Gson objGson = new Gson();
		return ResponseBuilder.buildResponse(objGson.toJson(collect), "success", HttpStatus.OK);
	}
	

	public ResponseEntity<ResponseWrapper<String>> getXmlTest(RequestWrapper requestWrapper, String token)
			throws Exception {
		String data = (String) requestWrapper.getData();
		
		try {
//			String productsString = new String(Base64.decodeBase64(request));
//			if(productsString.isEmpty()) {
//				return null;
//			}
			String stringData = new String(Base64.getDecoder().decode(data.replaceAll(" ", "+")));
			logger.info("stringData ==> " + stringData);
			
			
			JAXBContext jaxbContext = JAXBContext.newInstance(RequestProductsVo.class);
			logger.info("jaxbContext ==> " + jaxbContext);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			logger.info("jaxbUnmarshaller ==> " + jaxbUnmarshaller);
			RequestProductsVo products = (RequestProductsVo) jaxbUnmarshaller
					.unmarshal(new StringReader(stringData));
			Gson objGson = new Gson();
			return ResponseBuilder.buildResponse(objGson.toJson(products), "success", HttpStatus.OK);
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return ResponseBuilder.buildResponse("", "success", HttpStatus.BAD_REQUEST);
	}

}
