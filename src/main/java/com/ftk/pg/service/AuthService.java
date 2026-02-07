//package com.ftk.getepaymentpages.service;
//
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import com.ftk.getepaymentpages.dto.ResponseWrapper;
//import com.ftk.getepaymentpages.encryption.AESGCM;
//import com.ftk.getepaymentpages.encryption.EncryptedRequest;
//import com.ftk.getepaymentpages.encryption.EncryptedResponse;
//import com.ftk.getepaymentpages.exception.TokenException;
//import com.ftk.getepaymentpages.modal.FilterVo;
//import com.ftk.getepaymentpages.modal.Merchant;
//import com.ftk.getepaymentpages.modal.MerchantProductIdDetails;
//import com.ftk.getepaymentpages.modal.MerchantProducts;
//import com.ftk.getepaymentpages.modal.Properties;
//import com.ftk.getepaymentpages.modal.TransactionLog;
//import com.ftk.getepaymentpages.modal.User;
//import com.ftk.getepaymentpages.portalmodal.Invoice;
//import com.ftk.getepaymentpages.portalrepo.InvoiceRepo;
//import com.ftk.getepaymentpages.repo.MerchantProductIdDetailsRepo;
//import com.ftk.getepaymentpages.repo.MerchantProductsRepo;
//import com.ftk.getepaymentpages.repo.MerchantRepo;
//import com.ftk.getepaymentpages.repo.TransactionLogRepo;
//import com.ftk.getepaymentpages.repo.UserRepo;
//import com.ftk.getepaymentpages.requestvo.API_PaymentRequest;
//import com.ftk.getepaymentpages.requestvo.PaymentRequest;
//import com.ftk.getepaymentpages.requestvo.RequestProductVo;
//import com.ftk.getepaymentpages.requestvo.RequestProductsVo;
//import com.ftk.getepaymentpages.responsevo.PaymentResponse;
//import com.ftk.getepaymentpages.util.EncryptionUtil;
//import com.ftk.getepaymentpages.util.ValidateUtil;
//import com.google.gson.Gson;
//
//@Service
//public class AuthService {
//
//	Gson gson = new Gson();
//	@Autowired
//	private PropertiesRepo propertiesRepo;
//
//	@Autowired
//	private MerchantProductsRepo merchantProductRepo;
//
//	@Autowired
//	private UserRepo userRepo;
//
//	@Autowired
//	private MerchantRepo merchantRepo;
//
//	@Autowired
//	private TransactionLogRepo tranasctionLogRepo;
//
//	@Autowired
//	private MerchantProductIdDetailsRepo merchantProductIdDetailsRepo;
//
//	@Autowired
//	private InvoiceRepo invoiceRepo;
//
//	public EncryptedResponse validatepaymentRequest(@RequestBody EncryptedRequest encdata) throws Exception {
//
//		Properties properties = propertiesRepo.findByPropertykey(AESGCM.GCM_ENCRYPTION_MASTER_KEY);
//		String decrypted = AESGCM.decrypt(encdata.getData(), properties.getPropertyValue());
//
//		PaymentRequest pgRequest = gson.fromJson(decrypted, PaymentRequest.class);
//		PaymentResponse pgResponse = new PaymentResponse();
//
//		BeanUtils.copyProperties(pgRequest, pgResponse);
//
//		String merchantName = pgRequest.getLogin();
//		pgResponse = checkBlankAndNull(merchantName, "login", pgResponse);
//		if (!pgResponse.isResult()) {
//			return EncryptionUtil.encryptdata(gson.toJson(pgResponse), properties.getPropertyValue());
//		}
//
//		/* check merchant txnid */
//		String merchnattxnid = pgRequest.getMerchantTxnId();
//		pgResponse = checkBlankAndNull(merchnattxnid, "Merchant Transaction id", pgResponse);
//		if (!pgResponse.isResult()) {
//			return EncryptionUtil.encryptdata(gson.toJson(pgResponse), properties.getPropertyValue());
//		}
//
//		/* check transaction currency */
//		String txnCurr = pgRequest.getTxncurr();
//		pgResponse = checkBlankAndNull(txnCurr, "currency", pgResponse);
//		if (!pgResponse.isResult()) {
//			return EncryptionUtil.encryptdata(gson.toJson(pgResponse), properties.getPropertyValue());
//		}
//		txnCurr = txnCurr.toUpperCase();
//
//		// check product Type;
//		String productType = pgRequest.getProductType();
//		pgResponse = checkBlankAndNull(productType, "product Type", pgResponse);
//		if (!pgResponse.isResult()) {
//			return EncryptionUtil.encryptdata(gson.toJson(pgResponse), properties.getPropertyValue());
//		}
//
//		User user = userRepo.findByUsernameAndEnabled(pgRequest.getLogin(), true);
//
//		if (user == null) {
//
//			pgResponse.setResponseCode("01");
//			pgResponse.setStatus("fail");
//			pgResponse.setDescription("merchant not enabled");
//			return EncryptionUtil.encryptdata(gson.toJson(pgResponse), properties.getPropertyValue());
//		}
//
//		Merchant merchant = merchantRepo.findById(user.getMid()).get();
//		try {
//
//			pgResponse.setmId(merchant.getMid());
//
//		} catch (Exception e) {
//			new GlobalExceptionHandler().customException(e);
//		}
//		if (merchant == null) {
//			pgResponse.setResponseCode("01");
//			pgResponse.setResult(false);
//			pgResponse.setStatus("Failed");
//			pgResponse.setDescription("Invalid login or password");
//			return EncryptionUtil.encryptdata(gson.toJson(pgResponse), properties.getPropertyValue());
//		}
//		// get products details
//		MerchantProducts merchantProducts = new MerchantProducts();
//		merchantProducts = merchantProductRepo.findByMerchantIdAndProductType(merchant.getMid(), productType);
//		String enabledCurrencies = merchantProducts.getEnabledCurrencies();
//		if (!enabledCurrencies.contains(txnCurr)) {
//			pgResponse.setResponseCode("01");
//			pgResponse.setResult(false);
//			pgResponse.setDescription("Invalid Txn Currency");
//			pgResponse.setStatus("Failed");
//			return EncryptionUtil.encryptdata(gson.toJson(pgResponse), properties.getPropertyValue());
//		}
//
//		String enabledProducts = merchant.getEnabledProducts();
//		String[] productsList = enabledProducts.split(",");
//		if (!enabledProducts.contains(productType)) {
//			pgResponse.setResponseCode("01");
//			pgResponse.setResult(false);
//			pgResponse.setDescription("Invalid product Type");
//			pgResponse.setStatus("failed");
//			return EncryptionUtil.encryptdata(gson.toJson(pgResponse), properties.getPropertyValue());
//
//		}
//
//		/* check amount */
//		String actualTxnAmt = pgRequest.getAmt().toString();
//		String txnAmount = "";
//		pgResponse = checkBlankAndNull(actualTxnAmt, "amount", pgResponse);
//		if (!pgResponse.isResult()) {
//			return EncryptionUtil.encryptdata(gson.toJson(pgResponse), properties.getPropertyValue());
//		} else {
//			try {
////				txnAmount = ISOStandard.convertToISOStandard(actualTxnAmt, txnCurr);
//				txnAmount = actualTxnAmt;
//				if (txnAmount == null || Double.parseDouble(txnAmount) <= 0) {
//					pgResponse.setResult(false);
//					pgResponse.setResponseCode("01");
//					pgResponse.setDescription("Invalid Amount");
//					pgResponse.setStatus("failed");
//					return EncryptionUtil.encryptdata(gson.toJson(pgResponse), properties.getPropertyValue());
//				}
//			} catch (Exception e) {
//				pgResponse.setResult(false);
//				pgResponse.setResponseCode("01");
//				pgResponse.setDescription("Invalid Amount");
//				pgResponse.setStatus("failed");
//				return EncryptionUtil.encryptdata(gson.toJson(pgResponse), properties.getPropertyValue());
//			}
//		}
//
//		TransactionLog transactionLog = null;
//		try {
//			FilterVo filterVo = new FilterVo();
//			filterVo.setMerchantId(user.getMid());
//			filterVo.setMerchantTxnId(pgRequest.getMerchantTxnId());
//
//			boolean dupMTxnId = false;
//			if (merchant.getEnableDuplicateMTxnId() != null) {
//				dupMTxnId = merchant.getEnableDuplicateMTxnId();
//			}
//
//			transactionLog = tranasctionLogRepo.findByMerchantIdAndMerchanttxnid(user.getMid(),
//					pgRequest.getMerchantTxnId());
//		} catch (Exception e) {
//			new GlobalExceptionHandler().customException(e);
//		}
//
//		if (transactionLog != null) {
//			pgResponse.setResponseCode("01");
//			pgResponse.setResult(false);
//			pgResponse.setDescription("Merchant Txn Id already Exist");
//			pgResponse.setStatus("Failed");
//			return EncryptionUtil.encryptdata(gson.toJson(pgResponse), properties.getPropertyValue());
//		}
//
//		if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("neft")) {
//			if (pgRequest.getVan() == null || pgRequest.getVan().trim().equals("")) {
//				pgResponse.setResponseCode("01");
//				pgResponse.setResult(false);
//				pgResponse.setDescription("Parameter van is missing");
//				pgResponse.setStatus("Failed");
//			}
//		}
//
//		if (pgRequest.getTxnType() != null && pgRequest.getTxnType().equalsIgnoreCase("multi")) {
//			if (pgRequest.getProductDetails() == null) {
//				pgResponse.setResponseCode("01");
//				pgResponse.setResult(false);
//				pgResponse.setDescription("Product details should not be blank for transaction type Multi");
//				pgResponse.setStatus("Failed");
//			} else {
//				RequestProductsVo products = ValidateUtil.parseRequestProducts(pgRequest.getProductDetails());
//				if (products == null || products.getProducts().isEmpty()) {
//					pgResponse.setResponseCode("01");
//					pgResponse.setResult(false);
//					pgResponse.setDescription("Product details should not be blank for transaction type Multi");
//					pgResponse.setStatus("Failed");
//				} else {
//
//					Double totalProductAmt = 0.0;
//					Map<String, MerchantProductIdDetails> productMap = new HashMap<>();
//					List<MerchantProductIdDetails> merchantProductList = merchantProductIdDetailsRepo
//							.findByMerchantId(merchant.getMid());
//
//					for (MerchantProductIdDetails merchantProduct : merchantProductList) {
//
//						productMap.put(merchantProduct.getProductCode(), merchantProduct);
//					}
//					List<RequestProductVo> productCodes = products.getProducts();
//					for (Iterator iterator = productCodes.iterator(); iterator.hasNext();) {
//						RequestProductVo requestProductVo = (RequestProductVo) iterator.next();
//						// validate merchant product
//
//						if (productMap.containsKey(requestProductVo.getCode())) {
//							totalProductAmt = totalProductAmt + Double.valueOf(requestProductVo.getAmount());
//						} else {
//							pgResponse.setResponseCode("01");
//							pgResponse.setResult(false);
//							pgResponse.setDescription("Invalid Product code");
//							pgResponse.setStatus("Failed");
//							return EncryptionUtil.encryptdata(gson.toJson(pgResponse), properties.getPropertyValue());
//						}
//					}
//					if (totalProductAmt != Double.valueOf(pgRequest.getAmt())) {
//						pgResponse.setResponseCode("01");
//						pgResponse.setResult(false);
//						pgResponse.setDescription("Invalid Product Amount");
//						pgResponse.setStatus("Failed");
//					}
//				}
//			}
//		}
//
//		return EncryptionUtil.encryptdata(gson.toJson(pgResponse), properties.getPropertyValue());
//
//	}
//
//	private PaymentResponse checkBlankAndNull(String propertyValue, String propertyName, PaymentResponse pgResponse) {
//
//		if (propertyValue == null || propertyValue.trim().equals("") || propertyValue.isEmpty()) {
//			pgResponse.setResult(false);
//			pgResponse.setResponseCode("01");
//			pgResponse.setDescription(propertyName + " should not be null or blank");
//			pgResponse.setStatus("failed");
//		} else {
//			pgResponse.setStatus("success");
//			pgResponse.setDescription("validate successfully");
//			pgResponse.setResult(true);
//			pgResponse.setResponseCode("00");
//		}
//		return pgResponse;
//	}
//
//	public EncryptedResponse encrypt(String data) throws Exception {
//		Properties properties = propertiesRepo.findByPropertykey(AESGCM.GCM_ENCRYPTION_MASTER_KEY);
//		return EncryptionUtil.encryptdata(data, properties.getPropertyValue());
//	}
//
//	public ResponseEntity<ResponseWrapper<PaymentResponse>> dopayment(String token) {
//		ResponseWrapper responseWrapper = new ResponseWrapper<>();
//		PaymentResponse paymentResponse = new PaymentResponse();
//
//		API_PaymentRequest api_paymentRequest = new API_PaymentRequest();
//		
//		Optional.ofNullable(token).filter(t -> !t.trim().isEmpty())
//				.orElseThrow(() -> new TokenException("Token cannot be null or empty"));
//
//		 Invoice invoice=invoiceRepo.findByToken(token);
//		 
//		 
//
//		return null;
//	}
//
//}


