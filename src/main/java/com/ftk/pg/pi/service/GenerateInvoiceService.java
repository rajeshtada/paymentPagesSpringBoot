package com.ftk.pg.pi.service;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ftk.pg.encryption.GcmPgEncryption;
import com.ftk.pg.encryption.PgEncryption;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.DynamicQrRequestModel;
import com.ftk.pg.modal.Merchant;
import com.ftk.pg.modal.MerchantProducts;
import com.ftk.pg.modal.UpiQrDetail;
import com.ftk.pg.pi.modal.AdvanceProperties;
import com.ftk.pg.pi.modal.CurrencyConverter;
import com.ftk.pg.pi.modal.FormTransaction;
import com.ftk.pg.pi.modal.IntermediateTransaction;
import com.ftk.pg.pi.modal.MerchantInvoice;
import com.ftk.pg.pi.modal.MerchantKeys;
import com.ftk.pg.pi.modal.Merchants;
import com.ftk.pg.pi.modal.PaymentProcessEntity;
import com.ftk.pg.pi.modal.Properties;
import com.ftk.pg.pi.modal.SoundBoxInventory;
import com.ftk.pg.pi.repo.AdvancePropertiesRepo;
import com.ftk.pg.pi.repo.CurrencyConverterRepo;
import com.ftk.pg.pi.repo.FormTransactionRepo;
import com.ftk.pg.pi.repo.IntermediateTransactionRepo;
import com.ftk.pg.pi.repo.MerchantInvoiceRepo;
import com.ftk.pg.pi.repo.MerchantKeysRepo;
import com.ftk.pg.pi.repo.MerchantsRepo;
import com.ftk.pg.pi.repo.PaymentProcessEntityRepo;
import com.ftk.pg.pi.repo.PropertiesRepo;
import com.ftk.pg.pi.repo.SoundBoxInventoryRepo;
import com.ftk.pg.repo.DynamicQrRequestModelRepo;
import com.ftk.pg.repo.MerchantProductsRepo;
import com.ftk.pg.repo.MerchantRepo;
import com.ftk.pg.repo.UpiQrDetailRepo;
import com.ftk.pg.responsevo.DynamicQrResponse;
import com.ftk.pg.service.CommonService;
import com.ftk.pg.util.CommissionModel;
import com.ftk.pg.util.ConvenienceModel;
import com.ftk.pg.util.Util;
import com.ftk.pg.util.Utils;
import com.ftk.pg.vo.generateInvoice.GenerateInvoiceResponseWrapper;
import com.ftk.pg.vo.generateInvoice.InvoiceStatusRequest;
import com.ftk.pg.vo.generateInvoice.InvoiceStatusResponse;
import com.ftk.pg.vo.generateInvoice.InvoiceStatusResponseWrapper;
import com.ftk.pg.vo.generateInvoice.OtpRequestVo;
import com.ftk.pg.vo.generateInvoice.PgRequest;
import com.ftk.pg.vo.generateInvoice.PgRequestWrapper;
import com.ftk.pg.vo.generateInvoice.ResponseData;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenerateInvoiceService {

	Logger logger = LogManager.getLogger(GenerateInvoiceService.class);

	private final MerchantsRepo merchantsRepo;

	private final MerchantKeysRepo merchantKeysRepo;

	private final AdvancePropertiesRepo advancePropertiesRepo;

	private final UpiQrDetailRepo upiQrDetailRepo;

	private final MerchantProductsRepo merchantProductsRepo;

	private final CurrencyConverterRepo currencyConverterRepo;

	private final MerchantInvoiceRepo merchantInvoiceRepo;

	private final IntermediateTransactionRepo intermediateTransactionRepo;

	private final FormTransactionRepo formTransactionRepo;

	private final PaymentProcessEntityRepo paymentProcessEntityRepo;

	@Qualifier("PortalPropertiesRepo")
	private final PropertiesRepo propertiesRepo;

	private final MerchantRepo merchantRepo;

	private final CommonService commonService;

	private final DynamicQrRequestModelRepo dynamicQrRequestModelRepo;

	private final SoundBoxInventoryRepo soundBoxInventoryRepo;

	private final AsyncPortalService asyncPortalService;

	public GenerateInvoiceResponseWrapper generateInvoiceV1(PgRequestWrapper requestWrapper) {
		
		logger.info("Encrypted request (req) received: " + requestWrapper);

		GenerateInvoiceResponseWrapper responseWrapper = new GenerateInvoiceResponseWrapper();
		responseWrapper.setStatus("FAILED");
		responseWrapper.setMessage("Unable to process payment request");

		String midString = requestWrapper.getMid();
		String terminalId = requestWrapper.getTerminalId();
		String req = requestWrapper.getReq();

		try {
			

			if (req == null || req.isEmpty()) {
				logger.info("Encrypted request (req) is null or empty.");
				responseWrapper.setMessage("Request data is empty or missing");
				return responseWrapper;
			}

			if (midString == null || midString.trim().equals("") || terminalId == null
					|| terminalId.trim().equals("")) {
				logger.info("Invalid merchant detail  !! ");
				responseWrapper.setMessage("Invalid Request !!");
				return responseWrapper;
			}

			Long mid = Long.parseLong(midString);
			Merchants merchants = merchantsRepo.findByMid(mid);
			if (merchants == null) {
				responseWrapper.setMessage("Merchant not found");
				return responseWrapper;
			}

			MerchantKeys merchantKeys = merchantKeysRepo.findByMidAndTerminalId(mid, terminalId);
			if (merchantKeys == null || merchantKeys.getId() <= 0) {
				responseWrapper.setMessage("Encryption keys not configured");
				return responseWrapper;
			}

			PgEncryption encryption = new PgEncryption(merchantKeys);
			PgRequest request = encryption.decryptRequest(req);
			
			responseWrapper = processGenerateInvoiceRequest(requestWrapper, request, "v1");
			
			String responseObjJsonString = responseWrapper.getResponse();
			Gson gson = new Gson();
			ResponseData responseData = gson.fromJson(responseObjJsonString, ResponseData.class);
			String responseObjEncString = encryption.encryptResponse(responseData);
			logger.info("responseJsonString==>" + responseObjEncString);
			
			responseWrapper.setResponse(responseObjEncString);
			
			return responseWrapper;
			
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			responseWrapper.setStatus("Failed");
			responseWrapper.setMessage("Internal server error: " + e.getMessage());
		}
			return responseWrapper;
	}
		
	public GenerateInvoiceResponseWrapper processGenerateInvoiceRequest(PgRequestWrapper requestWrapper, PgRequest request, String version) {
		
		GenerateInvoiceResponseWrapper response = new GenerateInvoiceResponseWrapper();
		Gson gson = new Gson();
		response.setStatus("FAILED");
		response.setMessage("Unable to process payment request");
		
		String midString = requestWrapper.getMid();
		String terminalId = requestWrapper.getTerminalId();
		
		if (!terminalId.equals(request.getTerminalId())) {
			response.setMessage("Invalid terminalId");
			return response;
		}
		if (!midString.equals(request.getMid())) {
			response.setMessage("Invalid MID");
			return response;
		}
		Long mid = Long.parseLong(midString);
		Merchants merchants = merchantsRepo.findByMid(mid);
		if (merchants == null) {
			response.setMessage("Merchant not found");
			return response;
		}

		List<AdvanceProperties> advanceProperties = advancePropertiesRepo.findAll();
		Map<String, String> propertiesMap = new HashMap<>();
		for (AdvanceProperties advanceProperties2 : advanceProperties) {
			propertiesMap.put(advanceProperties2.getPropertyKey(), advanceProperties2.getPropertyValue());
		}
		
		String mainCurrency = request.getCurrency();
		String mainAmount = request.getAmount();

		try {
			BigDecimal bigDecimalAmt = new BigDecimal(mainAmount);
			if ( bigDecimalAmt.compareTo(BigDecimal.ZERO) > 1) {
				response.setMessage("Amount Not Valid");
				return response;
			}
		} catch (Exception e) {
			response.setMessage("Amount Not Valid");
			return response;
		}
		
		if (request.getCurrency() == null) {
			request.setCurrency("INR");
		} else if (request.getCurrency() != null && !request.getCurrency().equalsIgnoreCase("INR")) {
			UpiQrDetail upiQrDetail = upiQrDetailRepo.findByVpa(terminalId);
			MerchantProducts MerchantProducts = merchantProductsRepo.findByMerchantId(upiQrDetail.getMid());
			String currencyDetailsVpaId = null;
			if (MerchantProducts != null && MerchantProducts.getEnabledCurrencies() != null) {
				currencyDetailsVpaId = MerchantProducts.getEnabledCurrencies();
			}
			if (currencyDetailsVpaId == null
					|| !currencyDetailsVpaId.toLowerCase().contains(request.getCurrency().toLowerCase())) {
				response.setStatus("Failed");
				response.setMessage(request.getCurrency() + "  currency not enabled for this merchant.");
				return response;
			}

			CurrencyConverter currencyConverter = currencyConverterRepo.findLatestData(request.getCurrency());
			BigDecimal am = new BigDecimal(mainAmount).multiply(currencyConverter.getValue());
			request.setAmount(String.valueOf(am));
			request.setCurrency("INR");
		}

		MerchantInvoice invoice = createInvoice(request, merchants);
		Date expiryDate = invoice.getExpiryDate();
		Date now = new Date();
		if (expiryDate.getTime() < now.getTime()) {
			response.setStatus("Failed");
			response.setMessage("Invalid Expiry-time..");
			return response;
		}
		
		Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.MONTH, 1);
	    Date oneMonthNext = cal.getTime();
		if (expiryDate.getTime() > oneMonthNext.getTime()) {
			response.setStatus("Failed");
			response.setMessage("Expiry-time limit exceed..");
			return response;
		}
	     
		merchantInvoiceRepo.save(invoice);
		String invoiceId = String.valueOf(invoice.getId());

		// Create transaction
		IntermediateTransaction txn = createTransaction(request, merchants, invoiceId, mainCurrency, mainAmount);
		intermediateTransactionRepo.save(txn);
		Long transactionId = txn.getTransactionId();

		invoice.setTxnId(transactionId);
		merchantInvoiceRepo.save(invoice);

		if (request.getPaymentFrom() != null && request.getPaymentFrom().equalsIgnoreCase("form")) {
			FormTransaction formTransaction = formTransactionRepo.findById(request.getFormTransactionId()).get();
			formTransaction.setTransactionId(transactionId);
			formTransactionRepo.save(formTransaction);
		} else if (request.getPaymentFrom() != null
				&& request.getPaymentFrom().equalsIgnoreCase("PaymentReminder")) {
			PaymentProcessEntity entity = paymentProcessEntityRepo.findById(Long.valueOf(request.getFormId()))
					.get();
			entity.setPaymentId(transactionId);
			paymentProcessEntityRepo.save(entity);
		}
		String payUrlKey = "";
		String showQrCodeImageUrlKey = "";
		if ( version.equalsIgnoreCase("v1")) {
			payUrlKey = "GENERATE_INVOICE_V1_PAYMENT_PAGES_URL";
			showQrCodeImageUrlKey = "SHOW_QR_CODE_IMAGE_URL_V1";
		} else if (version.equalsIgnoreCase("v3")) {
			payUrlKey = "GENERATE_INVOICE_V3_PAYMENT_PAGES_URL";
			showQrCodeImageUrlKey = "SHOW_QR_CODE_IMAGE_URL_V3";
		}
		String payUrl = propertiesMap.get(payUrlKey);

		String finalUrl = payUrl.replace("#token", invoice.getToken());
		ResponseData responseData = new ResponseData();
		responseData.setPaymentId(String.valueOf(transactionId));
		responseData.setToken(invoice.getToken());
		responseData.setPaymentUrl(finalUrl);

		Properties properties = propertiesRepo.findByMidAndStatus(mid, true);

		String showQrCodeImageUrl = propertiesMap.get(showQrCodeImageUrlKey);

		if (properties == null || properties.getSetting14() == null
				|| properties.getSetting14().trim().equalsIgnoreCase("on")) {

			DynamicQrResponse dynamicQrResponse = generateDynamicQr(invoice, txn, request, propertiesMap);

			String qrImagePath = dynamicQrResponse.getQrPath();
			String qrIntentLink = dynamicQrResponse.getIntentUrl();
			responseData.setQrIntent(qrIntentLink);
			responseData.setQr(qrImagePath);
			String qrUrl = null;

			if (qrImagePath != null && !qrImagePath.equals("")) {
				String encImagePath = java.util.Base64.getEncoder().encodeToString(qrImagePath.getBytes());
				txn.setUdf43(encImagePath);
//				qrUrl = showQrCodeImageUrl + "/getepayPortal/apis/showQrCode3?qrCode=" + encImagePath;
				qrUrl = showQrCodeImageUrl + encImagePath;
			}
			if (qrIntentLink != null && !qrIntentLink.equals("")) {
				String encQrIntentLink = java.util.Base64.getEncoder().encodeToString(qrIntentLink.getBytes());
				txn.setUdf44(encQrIntentLink);
				if (request.getNoQr() == null || request.getNoQr().equals("1")) {
					logger.info("Inside QR");
					List<SoundBoxInventory> soundBoxInv = soundBoxInventoryRepo.findByVpa(request.getTerminalId());
					asyncPortalService.mqttSoundboxDynamicQr(invoice, qrIntentLink, request.getTerminalId(),
							propertiesMap, soundBoxInv);
				}
			}

			if (qrUrl != null && !qrUrl.equals("")) {
				txn.setUdf45(qrUrl);
				responseData.setQrPath(qrUrl);
			}

			intermediateTransactionRepo.save(txn);
		}

		if (properties != null && properties.getSetting15() != null
				&& properties.getSetting15().trim().equalsIgnoreCase("on")) {

			try {
				OtpRequestVo otp = new OtpRequestVo();
				String msg = Util.INVOICE_MSG_WITH_MERCHANT_NAME;

				msg = msg.replace("#amount", request.getAmount())
						.replace("#merchantName", merchants.getMerchantName()).replace("#paymentURL", finalUrl);

				otp.setMessage(URLEncoder.encode(msg, "UTF-8").replaceAll("\\s", "%20"));
				otp.setMobileNo(request.getUdf1());
				asyncPortalService.callBms(otp.getMessage(), otp.getMobileNo(), propertiesMap);
			} catch (Exception e) {
				new GlobalExceptionHandler().customException(e);
			}

		}

		logger.info("responseData : " + responseData);
		String responseJson = gson.toJson(responseData);
		response.setMid(requestWrapper.getMid());
		response.setTerminalId(requestWrapper.getTerminalId());
		response.setResponse(responseJson);
		response.setStatus("SUCCESS");
		response.setMessage("Payment link successfully created.");
		response.setRu(null);
		
		return response;
		
	}
	
	public GenerateInvoiceResponseWrapper generateInvoice(PgRequestWrapper requestWrapper) {

		logger.info("Encrypted request (req) received: " + requestWrapper);

		GenerateInvoiceResponseWrapper response = new GenerateInvoiceResponseWrapper();
		Gson gson = new Gson();
		response.setStatus("FAILED");
		response.setMessage("Unable to process payment request");

		String midString = requestWrapper.getMid();
		String terminalId = requestWrapper.getTerminalId();
		String req = requestWrapper.getReq();

		try {
			if (req == null || req.isEmpty()) {
				logger.info("Encrypted request (req) is null or empty.");
				response.setMessage("Request data is empty or missing");
				return response;
			}

			if (midString == null || midString.trim().equals("") || terminalId == null
					|| terminalId.trim().equals("")) {
				logger.info("Invalid merchant detail  !! ");
				response.setMessage("Invalid Request !!");
				return response;
			}

			Long mid = Long.parseLong(midString);
			Merchants merchants = merchantsRepo.findByMid(mid);
			if (merchants == null) {
				response.setMessage("Merchant not found");
				return response;
			}

			MerchantKeys merchantKeys = merchantKeysRepo.findByMidAndTerminalId(mid, terminalId);
			if (merchantKeys == null || merchantKeys.getId() <= 0) {
				response.setMessage("Encryption keys not configured");
				return response;
			}

			GcmPgEncryption gcmPgEncryption = new GcmPgEncryption(merchantKeys.getIv(), merchantKeys.getKey());
//			GcmPgEncryptionPortal gcmPgEncryption = new GcmPgEncryptionPortal(merchantKeys.getIv(), merchantKeys.getKey());
			String decryptedReq = null;

			try {
				decryptedReq = gcmPgEncryption.decryptWithMKeys(req);
				if (decryptedReq == null || decryptedReq.isEmpty()) {
					logger.error("Decrypted request is null or empty.");
					response.setMessage("Failed to decrypt request");
					return response;
				}
				logger.info("Decrypted request: " + decryptedReq);
			} catch (Exception e) {
				logger.info("Failed to decrypt request: " + e.getMessage());
				response.setMessage("Failed to decrypt request");
				return response;
			}

			PgRequest request = gson.fromJson(decryptedReq, PgRequest.class);
			
			if (!terminalId.equals(request.getTerminalId())) {
				response.setMessage("Invalid terminalId");
				return response;
			}
			if (!midString.equals(request.getMid())) {
				response.setMessage("Invalid MID");
				return response;
			}

			List<AdvanceProperties> advanceProperties = advancePropertiesRepo.findAll();
			Map<String, String> propertiesMap = new HashMap<>();
			for (AdvanceProperties advanceProperties2 : advanceProperties) {
				propertiesMap.put(advanceProperties2.getPropertyKey(), advanceProperties2.getPropertyValue());
			}
			String mainCurrency = request.getCurrency();
			String mainAmount = request.getAmount();

			try {
				BigDecimal bigDecimalAmt = new BigDecimal(mainAmount);
				if ( bigDecimalAmt.compareTo(BigDecimal.ZERO) > 1) {
					response.setMessage("Amount Not Valid");
					return response;
				}
			} catch (Exception e) {
				response.setMessage("Amount Not Valid");
				return response;
			}
			
			if (request.getCurrency() == null) {
				request.setCurrency("INR");
			} else if (request.getCurrency() != null && !request.getCurrency().equalsIgnoreCase("INR")) {
				UpiQrDetail upiQrDetail = upiQrDetailRepo.findByVpa(terminalId);
				MerchantProducts MerchantProducts = merchantProductsRepo.findByMerchantId(upiQrDetail.getMid());
				String currencyDetailsVpaId = null;
				if (MerchantProducts != null && MerchantProducts.getEnabledCurrencies() != null) {
					currencyDetailsVpaId = MerchantProducts.getEnabledCurrencies();
				}
				if (currencyDetailsVpaId == null
						|| !currencyDetailsVpaId.toLowerCase().contains(request.getCurrency().toLowerCase())) {
					response.setStatus("Failed");
					response.setMessage(request.getCurrency() + "  currency not enabled for this merchant.");
					return response;
				}

				CurrencyConverter currencyConverter = currencyConverterRepo.findLatestData(request.getCurrency());
				BigDecimal am = new BigDecimal(mainAmount).multiply(currencyConverter.getValue());
				request.setAmount(String.valueOf(am));
				request.setCurrency("INR");
			}

			MerchantInvoice invoice = createInvoice(request, merchants);
			Date expiryDate = invoice.getExpiryDate();
			Date now = new Date();
			if (expiryDate.getTime() < now.getTime()) {
				response.setStatus("Failed");
				response.setMessage("Invalid Expiry Time..");
				return response;
			}
			merchantInvoiceRepo.save(invoice);
			String invoiceId = String.valueOf(invoice.getId());

			// Create transaction
			IntermediateTransaction txn = createTransaction(request, merchants, invoiceId, mainCurrency, mainAmount);
			intermediateTransactionRepo.save(txn);
			Long transactionId = txn.getTransactionId();

			invoice.setTxnId(transactionId);
			merchantInvoiceRepo.save(invoice);

			if (request.getPaymentFrom() != null && request.getPaymentFrom().equalsIgnoreCase("form")) {
				FormTransaction formTransaction = formTransactionRepo.findById(request.getFormTransactionId()).get();
				formTransaction.setTransactionId(transactionId);
				formTransactionRepo.save(formTransaction);
			} else if (request.getPaymentFrom() != null
					&& request.getPaymentFrom().equalsIgnoreCase("PaymentReminder")) {
				PaymentProcessEntity entity = paymentProcessEntityRepo.findById(Long.valueOf(request.getFormId()))
						.get();
				entity.setPaymentId(transactionId);
				paymentProcessEntityRepo.save(entity);
			}
			String payUrlKey = "GENERATE_INVOICE_V3_PAYMENT_PAGES_URL";
			String payUrl = propertiesMap.get(payUrlKey);

			String finalUrl = payUrl.replace("#token", invoice.getToken());
			ResponseData responseData = new ResponseData();
			responseData.setPaymentId(String.valueOf(transactionId));
			responseData.setToken(invoice.getToken());
			responseData.setPaymentUrl(finalUrl);

			Properties properties = propertiesRepo.findByMidAndStatus(mid, true);

			String showQrCodeImageUrlKey = "SHOW_QR_CODE_IMAGE_URL_V2";
			String showQrCodeImageUrl = propertiesMap.get(showQrCodeImageUrlKey);

			if (properties == null || properties.getSetting14() == null
					|| properties.getSetting14().trim().equalsIgnoreCase("on")) {

				DynamicQrResponse dynamicQrResponse = generateDynamicQr(invoice, txn, request, propertiesMap);

				String qrImagePath = dynamicQrResponse.getQrPath();
				String qrIntentLink = dynamicQrResponse.getIntentUrl();
				responseData.setQrIntent(qrIntentLink);
				responseData.setQr(qrImagePath);
				String qrUrl = null;

				if (qrImagePath != null && !qrImagePath.equals("")) {
					String encImagePath = java.util.Base64.getEncoder().encodeToString(qrImagePath.getBytes());
					txn.setUdf43(encImagePath);
//					qrUrl = showQrCodeImageUrl + "/getepayPortal/apis/showQrCode3?qrCode=" + encImagePath;
					qrUrl = showQrCodeImageUrl + encImagePath;
				}
				if (qrIntentLink != null && !qrIntentLink.equals("")) {
					String encQrIntentLink = java.util.Base64.getEncoder().encodeToString(qrIntentLink.getBytes());
					txn.setUdf44(encQrIntentLink);
					if (request.getNoQr() == null || request.getNoQr().equals("1")) {
						logger.info("Inside QR");
						List<SoundBoxInventory> soundBoxInv = soundBoxInventoryRepo.findByVpa(request.getTerminalId());
						asyncPortalService.mqttSoundboxDynamicQr(invoice, qrIntentLink, request.getTerminalId(),
								propertiesMap, soundBoxInv);
					}
				}

				if (qrUrl != null && !qrUrl.equals("")) {
					txn.setUdf45(qrUrl);
					responseData.setQrPath(qrUrl);
				}

				intermediateTransactionRepo.save(txn);
			}

			if (properties != null && properties.getSetting15() != null
					&& properties.getSetting15().trim().equalsIgnoreCase("on")) {

				try {
					OtpRequestVo otp = new OtpRequestVo();
					String msg = Util.INVOICE_MSG_WITH_MERCHANT_NAME;

					msg = msg.replace("#amount", request.getAmount())
							.replace("#merchantName", merchants.getMerchantName()).replace("#paymentURL", finalUrl);

					otp.setMessage(URLEncoder.encode(msg, "UTF-8").replaceAll("\\s", "%20"));
					otp.setMobileNo(request.getUdf1());
					asyncPortalService.callBms(otp.getMessage(), otp.getMobileNo(), propertiesMap);
				} catch (Exception e) {
					new GlobalExceptionHandler().customException(e);
				}

			}

			logger.info("responseData : " + responseData);
			String responseEncrypted = gcmPgEncryption.encryptWithMKeys(gson.toJson(responseData));
			response.setMid(requestWrapper.getMid());
			response.setTerminalId(requestWrapper.getTerminalId());
			response.setResponse(responseEncrypted);
			response.setStatus("SUCCESS");
			response.setMessage("Payment link successfully created.");
			response.setRu(null);
			return response;

//			if (request.getCurrency() != null && request.getCurrency().equalsIgnoreCase("INR")) {
//				mainCurrency = request.getCurrency();
//			} else if (request.getCurrency() != null) {
//				UpiQrDetail upiQrDetail = upiQrDetailRepo.findByVpa(terminalId);
//				MerchantProducts MerchantProducts = merchantProductsRepo.findByMerchantId(upiQrDetail.getMid());
//				String currencyDetailsVpaId = null;
//				if (MerchantProducts != null && MerchantProducts.getEnabledCurrencies() != null) {
//					currencyDetailsVpaId = MerchantProducts.getEnabledCurrencies();
//				}
//				if (currencyDetailsVpaId == null) {
//					response.setMessage("Currency not found for this merchant.");
//					return response;
//				} else {
//					if (currencyDetailsVpaId != null && currencyDetailsVpaId.contains(",")) {
//						String[] split = currencyDetailsVpaId.split("\\,");
//						boolean val = false;
//						for (String curr : split) {
//							if (curr.equalsIgnoreCase(request.getCurrency())) {
//								val = true;
//								break;
//							}
//						}
//						if (val == false) {
//							response.setStatus("Failed");
//							response.setMessage(request.getCurrency() + " currency not enabled for this merchant.");
//							return response;
//						}
//					} else if (currencyDetailsVpaId != null && !currencyDetailsVpaId.equals("")
//							&& !currencyDetailsVpaId.contains(",")) {
//						if (!currencyDetailsVpaId.equalsIgnoreCase(request.getCurrency())) {
//							response.setStatus("Failed");
//							response.setMessage(request.getCurrency() + "  currency not enabled for this merchant.");
//							return response;
//						}
//
//					}
//				}
//
//			} else {
//				response.setMessage("Please provide valid Currency");
//				return response;
//			}

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			response.setStatus("Failed");
			response.setMessage("Internal server error: " + e.getMessage());
		}
		return response;
	}

	private DynamicQrResponse generateDynamicQr(MerchantInvoice invoice, IntermediateTransaction txn, PgRequest request,
			Map<String, String> propertiesMap) {
		DynamicQrResponse response = new DynamicQrResponse();
		try {
			logger.info("inside  generateDynamicQr: " + txn.getTransactionId());
			UpiQrDetail upiQrDetail = upiQrDetailRepo.findByVpa(invoice.getVpa());

			Merchant merchant = merchantRepo.findByMid(upiQrDetail.getMid());

			CommissionModel cmodel = commonService.getCommisionAmountModel(merchant.getMid(), "DYNAMICQR",
					txn.getTxnAmount(), "IPG", "");
			ConvenienceModel conmodel = commonService.getConvenienceChargesModel(merchant.getMid(), "DYNAMICQR",
					txn.getTxnAmount(), null);

			DynamicQrRequestModel model = new DynamicQrRequestModel();
			model.setAmt(new BigDecimal(txn.getTxnAmount()));
			model.setCommision(cmodel.getCharges());
			model.setCreatedDate(LocalDateTime.now());
			model.setDate(String.valueOf(txn.getTxnDatetime()));
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
			model.setExpiryDate(sdf1.format(invoice.getExpiryDate()));
			model.setMerchantId(merchant.getMid());
			model.setMerchantName(model.getMerchantName());
			model.setMerchanttxnid(String.valueOf(txn.getTransactionId()));
			model.setMinimumAmount(String.valueOf(txn.getTxnAmount()));
			model.setMobile(request.getUdf1());
			model.setModifiedDate(LocalDateTime.now());

			if (invoice.getVpa().toLowerCase().contains("%aubank")) {
				model.setProcessor("AU Bank");
			} else if (invoice.getVpa().toLowerCase().contains("%icici")) {
				model.setProcessor("ICICI");
			}

			if (request.getProductType() == null || request.getProductType().equals("")) {
				model.setProductType("IPG");
			} else {
				model.setProductType(request.getProductType());
			}

			model.setRemarks(txn.getRemark());
			model.setTerminalId(request.getTerminalId());

			if (request.getCurrency() == null || request.getCurrency().equals("")) {
				model.setTxncurr("INR");
			} else {
				model.setTxncurr(request.getCurrency());
			}

			model.setTxnStatus("PENDING");

			if (request.getTxnType() == null || request.getTxnType().equals("")) {
				model.setTxnType("single");
			} else {
				model.setTxnType(request.getTxnType());
			}

			model.setVpa(invoice.getVpa());
			model.setUdf1(request.getUdf1());
			model.setUdf2(request.getUdf2());
			model.setUdf3(request.getUdf3());
			model.setUdf4(request.getUdf4());
			model.setUdf5(request.getUdf5());
			model.setUdf6(request.getUdf6());
			model.setUdf7(request.getUdf7());
			model.setUdf8(request.getUdf8());
			model.setUdf9(request.getUdf9());
			model.setUdf10(request.getUdf10());
			model = dynamicQrRequestModelRepo.save(model);
			return commonService.getDynamicQr(merchant, cmodel, conmodel, model, invoice.getVpa(), response);

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return response;
	}

	private IntermediateTransaction createTransaction(PgRequest request, Merchants merchants, String invoiceId,
			String mainCurrency, String mainAmount) {
		String merchantOrderNo = "";

		if (request.getPaymentFrom() != null && request.getPaymentFrom().equalsIgnoreCase("form")) {
			merchantOrderNo = "frm_" + request.getFormId() + "_" + invoiceId;
		} else if (request.getPaymentFrom() != null && request.getPaymentFrom().equalsIgnoreCase("PaymentReminder")) {
			merchantOrderNo = "invp_" + request.getFormId() + "_" + invoiceId;
		} else {
			merchantOrderNo = "inv_" + invoiceId;
		}

		BigDecimal convertedAmount = new BigDecimal(request.getAmount());
		IntermediateTransaction txn = new IntermediateTransaction();
		txn.setTxnAmount(convertedAmount.doubleValue());
		txn.setAgentName(merchants.getMerchantName());
		txn.setStatus("PENDING");
		txn.setMid(merchants.getMid());
		txn.setTxnDatetime(new Date());
		txn.setOrderNumber(request.getMerchantTransactionId());
		txn.setRu(request.getRu());
		txn.setCreatedDate(new Date());
		txn.setModifiedDate(new Date());
		txn.setMerchantOrderNo(merchantOrderNo);
		txn.setPaymentType(request.getPaymentMode());

		txn.setProductType(request.getProductType());
		txn.setUdf1(request.getUdf1());
		txn.setUdf2(request.getUdf2());
		txn.setUdf3(request.getUdf3());
		txn.setUdf4(request.getUdf4());
		txn.setUdf5(request.getUdf5());
		txn.setUdf6(request.getTerminalId());

		// txn.setUdf11(request.getUdf6());
		txn.setUdf12(request.getUdf7());
		txn.setUdf13(request.getUdf8());
		txn.setUdf14(request.getUdf9());
		txn.setUdf15(request.getUdf10());

		txn.setRemark(request.getTxnNote());
		txn.setUdf41(request.getRu());
		txn.setUdf42(request.getCallbackUrl());
		if (mainCurrency != null) {
			txn.setUdf46(mainAmount + "|" + mainCurrency + "|" + request.getAmount() + "|" + request.getCurrency());
		}

		// REQUIRED non-nullable fields
		txn.setSurcharge(0.0); // Or calculate if needed
		txn.setRiskStatus(0);
		txn.setRuStatus(0);
		return txn;
//		return intermediateTransactionRepo.save(txn);
	}

	private MerchantInvoice createInvoice(PgRequest request, Merchants merchants) {
		MerchantInvoice invoice = new MerchantInvoice();
		invoice.setAmount(new BigDecimal(request.getAmount()));
		invoice.setTotalAmount(new BigDecimal(request.getAmount()));
		invoice.setCreatedDate(LocalDateTime.now());
		invoice.setCustomerMobileNo(request.getUdf1());
		invoice.setCustomerEmailId(request.getUdf2());
		invoice.setCustomerName(request.getUdf3());
		invoice.setmId(merchants.getMid());
		invoice.setInvoiceType("PAYMENT");
		invoice.setInvoiceNo(String.valueOf(Util.generateNumber()));
		invoice.setToken(UUID.randomUUID().toString());
		invoice.setMerchantName(merchants.getMerchantName());
		invoice.setCurrency(request.getCurrency());
		invoice.setEmailStatus(false);
		invoice.setVpa(request.getTerminalId());
		invoice.setUdf8(request.getTxnType() + "|" + request.getUdf6());
		invoice.setPaymentMode(request.getPaymentMode());
		// Set expiry date

		String expiryDateTime = request.getExpiryDateTime();
		Date expiryDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			expiryDate = sdf.parse(expiryDateTime);
		} catch (Exception e) {
			logger.info("Unparsable Expiry Date time");
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.MINUTE, 10); // 10 minutes expiry
			expiryDate = cal.getTime();
			try {
				String format = sdf.format(expiryDate);
				expiryDate = sdf.parse(format);
			} catch (Exception e2) {
				expiryDate = cal.getTime();
			}

		}
		invoice.setExpiryDate(expiryDate);
		invoice.setExpireTime(expiryDate);
//		return merchantInvoiceRepo.save(invoice);
		return invoice;
	}

	public InvoiceStatusResponseWrapper invoiceStatusV3(PgRequestWrapper requestWrapper) throws Exception {

		logger.info("Encrypted request (req) received: " + requestWrapper);

		InvoiceStatusResponseWrapper response = new InvoiceStatusResponseWrapper();
		Gson gson = new Gson();
		response.setStatus("FAILED");
		response.setMessage("Unable to process payment request");

		try {

			String midString = String.valueOf(requestWrapper.getMid());
			String terminalIdString = String.valueOf(requestWrapper.getTerminalId());
			String requestString = String.valueOf(requestWrapper.getReq());

			if (midString == null || midString.trim().equals("") || terminalIdString == null
					|| terminalIdString.trim().equals("") || requestString == null || requestString.trim().equals("")) {
				logger.info("Invalid Request Parameter  !! ");
				response.setMessage("Invalid Request !!");
				return response;
			}

			if (midString == null || !Utils.isNumeric(midString)) {
				response.setMessage("Invalid value for mid");
				return response;
			}

			if (terminalIdString == null || terminalIdString.trim().isEmpty()) {
				response.setMessage("Invalid value for terminalId");
				return response;
			}

			long mid = Long.parseLong(midString);
			response.setMid(midString);

			MerchantKeys merchantKeys = merchantKeysRepo.findByMidAndTerminalId(mid, terminalIdString);
			if (merchantKeys == null || merchantKeys.getId() <= 0) {
				response.setMessage("Encryption keys not configured");
				return response;
			}

			GcmPgEncryption gcmPgEncryption = new GcmPgEncryption(merchantKeys.getIv(), merchantKeys.getKey());
			String decryptedReq = null;

			try {
				decryptedReq = gcmPgEncryption.decryptWithMKeys(requestString);
				if (decryptedReq == null || decryptedReq.isEmpty()) {
					logger.error("Decrypted request is null or empty.");
					response.setMessage("Failed to decrypt request");
					return response;
				}
				logger.info("Decrypted request: " + decryptedReq);
			} catch (Exception e) {
				logger.info("Failed to decrypt request: " + e.getMessage());
				response.setMessage("Failed to decrypt request");
				return response;
			}

			logger.info("Decrypted Request: " + decryptedReq);

			InvoiceStatusRequest invoiceStatusRequest = gson.fromJson(decryptedReq, InvoiceStatusRequest.class);

			if (!terminalIdString.equals(invoiceStatusRequest.getTerminalId())
					|| !midString.equals(invoiceStatusRequest.getMid())) {
				response.setMessage("Invalid request: mid or terminalId do not match");
				return response;
			}

			long transactionId;
			try {
				transactionId = Long.parseLong(invoiceStatusRequest.getPaymentId());
			} catch (NumberFormatException e) {
				response.setMessage("Invalid paymentId format");
				return response;
			}

			IntermediateTransaction transaction = intermediateTransactionRepo.findByTransactionId(transactionId);

			if (transaction == null || transaction.getTransactionId() <= 0) {
				response.setMessage("Invalid request: Payment Id not found");
				return response;
			}

			long invoiceId;
			try {
				String merchantOrderNo = transaction.getMerchantOrderNo();
				if (merchantOrderNo != null && merchantOrderNo.contains("_")) {
					String[] parts = merchantOrderNo.split("_");
					invoiceId = Long.parseLong(parts[1]);
				} else {
					invoiceId = Long.parseLong(merchantOrderNo);
				}
			} catch (Exception e) {
				response.setMessage("Invalid merchant_order_no format");
				return response;
			}

			MerchantInvoice invoice = merchantInvoiceRepo.findById(invoiceId).get();

			if (invoice != null && invoice.getId() > 0 && terminalIdString.equalsIgnoreCase(invoice.getVpa())
					&& transaction.getTransactionId().equals(invoice.getTxnId())) {

				InvoiceStatusResponse invoiceStatusResponse = InvoiceStatusResponse
						.fromIntermediateTransaction(transaction);

				String txnStatus = transaction.getStatus();
				response.setStatus(
						(txnStatus == null || txnStatus.trim().isEmpty()) ? "UNKNOWN" : txnStatus.trim().toUpperCase());
				response.setMessage("Fetch status successful");

				if (invoiceStatusResponse.getBankError() == null
						&& "PENDING".equalsIgnoreCase(transaction.getStatus())) {
					invoiceStatusResponse.setBankError("Payment Link Not Initiated");
				}

				String paymentDataResponse = gcmPgEncryption.encryptWithMKeys(gson.toJson(invoiceStatusResponse));
				response.setResponse(paymentDataResponse);
				response.setMid(String.valueOf(transaction.getMid()));
				response.setTerminalId(invoice.getVpa());

				return response;
			} else {
				response.setMessage("Invoice validation failed");
			}

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			response.setMessage("Something went wrong, Please try again.");
		}

		return response;
	}

}
