package com.ftk.pg.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftk.pg.dao.CurrencyConvertorDao;
import com.ftk.pg.dao.MerchantSettingDao;
import com.ftk.pg.dao.TransactionLogDao;
import com.ftk.pg.dto.PaymentRequest;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.Bank;
import com.ftk.pg.modal.CurrencyConvertor;
import com.ftk.pg.modal.FilterVo;
import com.ftk.pg.modal.Merchant;
import com.ftk.pg.modal.MerchantProductIdDetails;
import com.ftk.pg.modal.MerchantProducts;
import com.ftk.pg.modal.MerchantRisk;
import com.ftk.pg.modal.MerchantSetting;
import com.ftk.pg.modal.Token;
import com.ftk.pg.modal.TransactionLog;
import com.ftk.pg.modal.User;
import com.ftk.pg.repo.BankRepo;
import com.ftk.pg.repo.MerchantProductIdDetailsRepo;
import com.ftk.pg.repo.MerchantProductsRepo;
import com.ftk.pg.repo.MerchantRepo;
import com.ftk.pg.repo.MerchantRiskRepo;
import com.ftk.pg.repo.MerchantSettingRepo;
import com.ftk.pg.repo.TokenRepo;
import com.ftk.pg.repo.TransactionLogRepo;
import com.ftk.pg.repo.UserRepo;
import com.ftk.pg.requestvo.PropertiesVo;
//import com.ftk.pg.requestvo.PaymentRequest;
import com.ftk.pg.requestvo.RequestProductVo;
import com.ftk.pg.requestvo.RequestProductsVo;
import com.ftk.pg.responsevo.PaymentResponse;
import com.ftk.pg.util.ComponentUtils;
import com.ftk.pg.util.PGUtility;
import com.ftk.pg.util.RemoteDbUtil;
import com.pgcomponent.security.SignatureGenerator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ValidationService {

	Logger logger = LogManager.getLogger(ValidationService.class);
	
	private final  MerchantRepo merchantDao;

//	private final MerchantSettingRepo<MerchantSetting> merchantSettingDao;
//
//	private final RefundDao<Refund> refundDao;
	
	private final TransactionLogRepo transactionLogRepo;

	private final TransactionLogDao transactionLogDao;

//	private final ProcessorWalletDao<ProcessorWallet> processorWalletDao;

//	private final ProcessorBankDao<ProcessorBank> processorBankDao;
	
	private  final UserRepo userDao;
	
	private final MerchantProductsRepo merchantProductsDao;
	
	private final  MerchantProductIdDetailsRepo productDao;

//	private final MerchantRiskDao<MerchantRisk> merchantRiskDao;

//	TransactionEssentialsDao<TransactionEssentials> transactionEssentialsDao;

//	private final PropertiesDao<Properties> propertiesDao;

	private final  RiskService riskService;
	
	private final CallBackService callbackService;
	
//	private final RoutingSettingDao<RoutingSetting> routingSettingDao;
	
//	private final MerchantPartnerDao<MerchantPartner> merchantPartnerDao;
	
//	private final SmartRoutingTemplateDao<SmartRoutingTemplate> smartRoutingTemplateDao;
	
	private final CurrencyConvertorDao currencyConvertorDao;
	
	private final MerchantProductsRepo merchantProductsRepo;
	
	private final MerchantSettingRepo merchantSettingRepo;
	
	private final MerchantSettingDao merchantSettingDao;
	
	private final BankRepo bankRepo;
	
	private  final PgService pgService;
	
	private  final TokenRepo tokenRepo;
	
	private  final PropertiesService propertiesService;
	
	private final MerchantRiskRepo merchantRiskRepo;
	
	private final MerchantSettingService merchantSettingService;

	public PaymentResponse validatePaymentRequest(PaymentRequest pgRequest) {
		PaymentResponse pgResponse = new PaymentResponse();
		BeanUtils.copyProperties(pgRequest, pgResponse);

		/* check merchant name */
		String merchantName = pgRequest.getLogin();
		pgResponse = checkBlankAndNull(merchantName, "login", pgResponse);
		if (!pgResponse.isResult()) {
			return pgResponse;
		}

		/* check merchant txnid */
		String merchnattxnid = pgRequest.getMerchantTxnId();
		pgResponse = checkBlankAndNull(merchnattxnid, "Merchant Transaction id", pgResponse);
		if (!pgResponse.isResult()) {
			return pgResponse;
		}

		/* check transaction currency */
		String txnCurr = pgRequest.getTxncurr();
		pgResponse = checkBlankAndNull(txnCurr, "currency", pgResponse);
		if (!pgResponse.isResult()) {
			return pgResponse;
		}
		txnCurr = txnCurr.toUpperCase();

		// check product Type;
		String productType = pgRequest.getProductType();
		pgResponse = checkBlankAndNull(productType, "product Type", pgResponse);
		if (!pgResponse.isResult()) {
			return pgResponse;
		}

		// check enabled product types
		User userDetails = null;
		try {
			// get merchant details
			User user = new User();
			user.setUsername(merchantName);
			user.setEnabled(true);
			userDetails = userDao.findByUsername(user.getUsername());

			if (userDetails == null) {

				pgResponse.setResponseCode("01");
				pgResponse.setStatus("fail");
				pgResponse.setDescription("merchant not enabled");
				return pgResponse;
			}
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}

		Merchant merchant = null;
		try {
//			merchant = new Merchant();
//			merchant.setMid(userDetails.getMid());
			merchant = merchantDao.findByMidAndStatus(userDetails.getMid(),1);
			pgResponse.setmId(merchant.getMid());
			// setMerchant(merchant);
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		if (merchant == null) {
			pgResponse.setResponseCode("01");
			pgResponse.setResult(false);
			pgResponse.setStatus("Failed");
			pgResponse.setDescription("Invalid login or password");
			return pgResponse;
		}
		// get products details
		MerchantProducts merchantProducts = new MerchantProducts();
		merchantProducts.setMerchantId(merchant.getMid());
		merchantProducts.setProductType(productType);
		merchantProducts = merchantProductsDao.findByMerchantIdAndProductType(merchantProducts.getMerchantId(),
				merchantProducts.getProductType());
		// setMerchantProducts(merchantProducts);
		String enabledCurrencies = merchantProducts.getEnabledCurrencies();
		if (!enabledCurrencies.contains(txnCurr)) {
			pgResponse.setResponseCode("01");
			pgResponse.setResult(false);
			pgResponse.setDescription("Invalid Txn Currency");
			pgResponse.setStatus("Failed");
			return pgResponse;
		}

		String enabledProducts = merchant.getEnabledProducts();
//		String[] productsList = enabledProducts.split(",");
		if (!enabledProducts.contains(productType)) {
			pgResponse.setResponseCode("01");
			pgResponse.setResult(false);
			pgResponse.setDescription("Invalid product Type");
			pgResponse.setStatus("failed");
			return pgResponse;

		}

		/* check amount */
		String actualTxnAmt = pgRequest.getAmt().toString();
		String txnAmount = "";
		pgResponse = checkBlankAndNull(actualTxnAmt, "amount", pgResponse);
		if (!pgResponse.isResult()) {
			return pgResponse;
		} else {
			try {
//				txnAmount = ISOStandard.convertToISOStandard(actualTxnAmt, txnCurr);
				txnAmount = actualTxnAmt;
				if (txnAmount == null || Double.parseDouble(txnAmount) <= 0) {
					pgResponse.setResult(false);
					pgResponse.setResponseCode("01");
					pgResponse.setDescription("Invalid Amount");
					pgResponse.setStatus("failed");
					return pgResponse;
				}
			} catch (Exception e) {
				pgResponse.setResult(false);
				pgResponse.setResponseCode("01");
				pgResponse.setDescription("Invalid Amount");
				pgResponse.setStatus("failed");
				return pgResponse;
			}
		}

		TransactionLog transactionLog = null;
		try {
			FilterVo filterVo = new FilterVo();
			filterVo.setMerchantId(userDetails.getMid());
			filterVo.setMerchantTxnId(pgRequest.getMerchantTxnId());

			boolean dupMTxnId = false;
			if (merchant.getEnableDuplicateMTxnId() != null) {
				dupMTxnId = merchant.getEnableDuplicateMTxnId();
			}

			transactionLog = transactionLogDao.findByMerchantAndMerchantTxnId(filterVo, dupMTxnId);
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}

		if (transactionLog != null) {
			pgResponse.setResponseCode("01");
			pgResponse.setResult(false);
			pgResponse.setDescription("Merchant Txn Id already Exist");
			pgResponse.setStatus("Failed");
			return pgResponse;
		}

		if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("neft")) {
			if (pgRequest.getVan() == null || pgRequest.getVan().trim().equals("")) {
				pgResponse.setResponseCode("01");
				pgResponse.setResult(false);
				pgResponse.setDescription("Parameter van is missing");
				pgResponse.setStatus("Failed");
			}
		}

		if (pgRequest.getTxnType() != null && pgRequest.getTxnType().equalsIgnoreCase("multi")) {
			if (pgRequest.getProductDetails() == null) {
				pgResponse.setResponseCode("01");
				pgResponse.setResult(false);
				pgResponse.setDescription("Product details should not be blank for transaction type Multi");
				pgResponse.setStatus("Failed");
			} else {
				RequestProductsVo products = PGUtility.parseRequestProducts(pgRequest.getProductDetails());
				if (products == null || products.getProducts().isEmpty()) {
					pgResponse.setResponseCode("01");
					pgResponse.setResult(false);
					pgResponse.setDescription("Product details should not be blank for transaction type Multi");
					pgResponse.setStatus("Failed");
				} else {
//					Mradul
					Double totalProductAmt = 0.0;
					Map<String, MerchantProductIdDetails> productMap = new HashMap<>();
					List<MerchantProductIdDetails> merchantProductList = productDao
							.findByMerchantIdAndStatus(merchant.getMid(), 1);

					logger.info("merchant products=>" + merchantProductList.size());

					for (MerchantProductIdDetails merchantProduct : merchantProductList) {
						logger.info("code::" + merchantProduct.getProductCode());
						productMap.put(merchantProduct.getProductCode(), merchantProduct);
					}
					List<RequestProductVo> productCodes = products.getProducts();
					for (Iterator iterator = productCodes.iterator(); iterator.hasNext();) {
						RequestProductVo requestProductVo = (RequestProductVo) iterator.next();
						// validate merchant product
						logger.info("verifying code::" + requestProductVo.getCode());
						if (productMap.containsKey(requestProductVo.getCode())) {
							totalProductAmt = totalProductAmt + Double.valueOf(requestProductVo.getAmount());
						} else {
							pgResponse.setResponseCode("01");
							pgResponse.setResult(false);
							pgResponse.setDescription("Invalid Product code");
							pgResponse.setStatus("Failed");
							return pgResponse;
						}
					}
					if (totalProductAmt != pgRequest.getAmt().doubleValue()) {
						pgResponse.setResponseCode("01");
						pgResponse.setResult(false);
						pgResponse.setDescription("Invalid Product Amount");
						pgResponse.setStatus("Failed");
					}
				}
			}
		}

		return pgResponse;

	}

	private PaymentResponse checkBlankAndNull(String propertyValue, String propertyName, PaymentResponse pgResponse) {

		if (propertyValue == null || propertyValue.trim().equals("") || propertyValue.isEmpty()) {
			pgResponse.setResult(false);
			pgResponse.setResponseCode("01");
			pgResponse.setDescription(propertyName + " should not be null or blank");
			pgResponse.setStatus("failed");
		} else {
			pgResponse.setStatus("success");
			pgResponse.setDescription("validate successfully");
			pgResponse.setResult(true);
			pgResponse.setResponseCode("00");
		}
		return pgResponse;
	}

	public PaymentResponse paynow(PaymentRequest pgRequest, Map<String, String> propMap) {
		PaymentResponse response =validatePayNowRequest(pgRequest);
		logger.info("Paynow  validatePayNowRequest response => " + response );

		
//		Gson gson=new Gson();
		if (response.isResult()) {

			if (!pgRequest.getTxncurr().equalsIgnoreCase("INR")
					&& ((pgRequest.getPaymentMode().equalsIgnoreCase("wallet"))
							|| (pgRequest.getPaymentMode().equalsIgnoreCase("upi"))
							|| (pgRequest.getPaymentMode().equalsIgnoreCase("nb"))
							|| (pgRequest.getPaymentMode().equalsIgnoreCase("neft"))
							|| (pgRequest.getPaymentMode().equalsIgnoreCase("challan")))) {
				BigDecimal pgRequestamt = pgRequest.getAmt();
				String fromCurrency = pgRequest.getTxncurr();
				String toCurrency = "INR";
				logger.info("before currency convert => " + pgRequestamt + " : " + fromCurrency);

				CurrencyConvertor currencyConvertor = currencyConvertorDao.findByCurrency(fromCurrency, toCurrency);
				BigDecimal value = new BigDecimal(currencyConvertor.getValue());
				BigDecimal amount = pgRequestamt.multiply(value);

				pgRequest.setAmt(amount);
				pgRequest.setTxncurr(toCurrency);
				logger.info("after currency convert => " + amount + " : " + toCurrency);
			}

			Merchant merchant = merchantDao.findByMidAndStatus(response.getmId(),1);
			MerchantSetting merchantSetting = merchantSettingService.getMerchantSetting(merchant.getMid(), pgRequest);

			String processorMode = "";
			if (merchantSetting == null || merchantSetting.getProcessor() == null) {
				// processorMode = merchant.getDefaultProcessor();
				logger.info("Inside defoult processor");
				MerchantProducts merchantProducts = merchantProductsRepo.findByMerchantIdAndProductType(merchant.getMid(), pgRequest.getProductType());
				processorMode = merchantProducts.getDefaultProcessor();
			} else {
				processorMode = merchantSetting.getProcessor();
			}

			// NB Routing
			if (pgRequest.getPaymentMode().equalsIgnoreCase("NB")) {
				logger.info("NB bank id=>" + pgRequest.getBankid());
				Bank bank = bankRepo.findById(Long.valueOf(pgRequest.getBankid())).get();
				logger.info("NB bank id=>" + bank.getBankName());
				if (bank.getBankName().equalsIgnoreCase("AU Bank")) {
					response = pgService.auBankPayment(pgRequest, merchant, merchantSetting);
					return response;
				}
			}
			logger.info("processorMode => " + processorMode);
			if (processorMode.equalsIgnoreCase("PAYNETZ")) {
				response = pgService.paynetzPayment(pgRequest, merchant, merchantSetting);

				if (response.isResult()) {
//					Token token = new Token();
//					token.setUrl(response.getThreeDSecureUrl());
//					token.setMethodType("post");
//					token.setTokenStatus(0);
//					token = tokenRepo.save(token);
//					PropertiesVo paynetzReturnUrl = propertiesService.findByPropertykeyWithUpdatedCerts("PAYNETZ_RESPONSE_URL_V2");
//					logger.info("Token============" + token.getToken());
//					response.setThreeDSecureUrl(paynetzReturnUrl.getPropertyValue() + token.getToken());
				} else {
//					response.setThreeDSecureUrl("");
				}
			} else if (processorMode.equalsIgnoreCase("RBL")) {
				response = pgService.rblPayment(pgRequest, merchant, merchantSetting);
				if (response.isResult()) {

				}
			} else if (processorMode.equalsIgnoreCase("BOB")) {
				response = pgService.bobPayment(pgRequest, merchant, merchantSetting);
				if (response.isResult()) {

				}
			} else if (processorMode.equalsIgnoreCase("IDFC")) {
				response = pgService.idfcPayment(pgRequest, merchant, merchantSetting);
				if (response.isResult()) {

				}
			} /*
				 * else if (processorMode.equalsIgnoreCase("AUNB")) { response =
				 * pgService.auBankPayment(pgRequest, merchant, merchantSetting); if
				 * (response.isResult()) {
				 * 
				 * } }
				 */ else if (processorMode.equalsIgnoreCase("ICICI")
					&& pgRequest.getPaymentMode().equalsIgnoreCase("upi")) {
				response = pgService.iciciCollectPayment(pgRequest, merchant, merchantSetting);
			} else if (processorMode != null && !processorMode.trim().equals("")
					&& processorMode.equalsIgnoreCase("PAYU")) {
				response = pgService.payuPayment(pgRequest, merchant, merchantSetting);
				if (response.isResult()) {
				}
			} else if (processorMode.equalsIgnoreCase("CASHFREE")) {
				response = pgService.cashfreePayment(pgRequest, merchant, merchantSetting);
				if (response.isResult()) {
					/*
					 * Gson gson = new Gson(); String jsonString = gson.toJson(response); String
					 * encJsonString = Base64.getEncoder().encodeToString(jsonString.getBytes());
					 * Token token = new Token(); token.setUrl(response.getThreeDSecureUrl());
					 * token.setMethodType("post"); token.setTokenStatus(0);
					 * token.setResponseJson(encJsonString); String tokenId = null; try { tokenId =
					 * tokenService.saveToken(token); } catch (Exception e) { new GlobalExceptionHandler().customException(e); }
					 * Properties properties = new Properties();
					 * properties.setPropertykey("THREED_SECURE_RESPONSE_URL"); Properties
					 * paynetzReturnUrl = propertiesService.getPropertyByKey(properties);
					 * logger.info("Token============" + tokenId);
					 * response.setThreeDSecureUrl(paynetzReturnUrl.getPropertyValue() + tokenId);
					 * response.setPostReqParam(new HashMap<String,String>());
					 */
				}
			} else if (processorMode.equalsIgnoreCase("LICICI")) {
				response = pgService.lyraPayment(pgRequest, merchant, merchantSetting);
			} else if (processorMode.equalsIgnoreCase("KOTAKNB")) {
				response = pgService.kotakNb(pgRequest, merchant, merchantSetting);
			} else if (processorMode.equalsIgnoreCase("UCOBANK")) {
				response = pgService.ucoFEBAPayment(pgRequest, merchant, merchantSetting);
				if (response.isResult()) {
//					Token token = new Token();
//					token.setUrl(response.getThreeDSecureUrl());
//					token.setMethodType("post");
//					token.setTokenStatus(0);
//					token = tokenRepo.save(token);
////					Properties properties = new Properties();
//					PropertiesVo paynetzReturnUrl = propertiesService.findByPropertykeyWithUpdatedCerts("PAYNETZ_RESPONSE_URL");
//					logger.info("Token============" + token.getToken());
//					response.setThreeDSecureUrl(paynetzReturnUrl.getPropertyValue() + token.getToken());
				} else {
//					response.setThreeDSecureUrl("");
				}
			} else if (processorMode.equalsIgnoreCase("KOTAK BANK")) {
				response = pgService.kotakPayment(pgRequest, merchant, merchantSetting);
			} else if (processorMode.equalsIgnoreCase("ICICINB")) {
				response = pgService.iciciNb(pgRequest, merchant, merchantSetting);
			} else if ((processorMode.equalsIgnoreCase("SHIVALIKNB")) || (processorMode.equalsIgnoreCase("IDFCNB"))) {
				response = pgService.northAkorssnb(pgRequest, merchant, merchantSetting);
			} else if (processorMode.equalsIgnoreCase("SBINB")) {
				response = pgService.sbinb(pgRequest, merchant, merchantSetting);

			} else if (processorMode.equalsIgnoreCase("SBI BANK")) {
				response = pgService.sbicards(pgRequest, merchant, merchantSetting);

			} else if (processorMode.equalsIgnoreCase("PAYNETZNEW")) {
				response = pgService.paynetznew(pgRequest, merchant, merchantSetting);
			} else if (processorMode.equalsIgnoreCase("BILLDESK")) {
				response = pgService.billDesk(pgRequest, merchant, merchantSetting);
			} else if (processorMode.equalsIgnoreCase("IDBI")) {
				response = pgService.idbichallan(pgRequest, merchant, merchantSetting);
			} else if (processorMode.equalsIgnoreCase("Simulator")) {
				response = pgService.simulatorPayment(pgRequest, merchant, merchantSetting);
			} else if (processorMode.equalsIgnoreCase("AXISNB")) {
				response = pgService.axisnb(pgRequest, merchant, merchantSetting);
			}
//			else if (processorMode.equalsIgnoreCase("HDFC BANK")) {
//				response = pgService.hdfcBank(pgRequest, merchant, merchantSetting);
//			}
//			else if(processorMode.equalsIgnoreCase("HDFCNB")) {
//				response = pgService.hdfcnb(pgRequest, merchant, merchantSetting);
//			}
			else if(processorMode.equalsIgnoreCase("YESNB")) {
				response = pgService.yesnb(pgRequest, merchant, merchantSetting);
			} else if(processorMode.equalsIgnoreCase("NBBL")) {
				response = pgService.nbbl(pgRequest, merchant, merchantSetting);
			}

//			callbackService.addRequaryInQueue(pgRequest.getTransactionId());

		}
		return response;
	}


	private PaymentResponse validatePayNowRequest(PaymentRequest pgRequest) {
		PaymentResponse pgResponse = new PaymentResponse();
		try {

			BeanUtils.copyProperties(pgRequest, pgResponse);
			pgResponse.setPaymentMode(pgRequest.getPaymentMode());
			String requestType = pgRequest.getRequestType();
			if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
				Long transactionId = pgRequest.getTransactionId();
				if (transactionId == null || transactionId.equals(0L)) {
					pgResponse.setResult(false);
					pgResponse.setResponseCode("01");
					pgResponse.setDescription("Transaction Id should not be null or blank");
					pgResponse.setStatus("failed");
					return pgResponse;
				}
			}

			// check merchant name
			String merchantName = pgRequest.getLogin();
			pgResponse = checkBlankAndNull(merchantName, "login", pgResponse);
			if (!pgResponse.isResult()) {
				return pgResponse;
			}


			// get merchant details
			User user = new User();
			user.setUsername(merchantName);
			user.setEnabled(true);
			User userDetails = userDao.findByUsernameAndEnabled(user.getUsername(), user.isEnabled());

			if (userDetails == null) {

				pgResponse.setResponseCode("01");
				pgResponse.setStatus("fail");
				pgResponse.setDescription("merchant not enabled");
				return pgResponse;
			}

			pgRequest.setMerchantId(userDetails.getMid());

			Merchant merchant = null;
			try {
				merchant = merchantDao.findByMidAndStatus(userDetails.getMid(), 1);
			} catch (Exception e) {
				new GlobalExceptionHandler().customException(e);
			}
			
			if (merchant == null) {
				pgResponse.setResponseCode("01");
				pgResponse.setResult(false);
				pgResponse.setDescription("Invalid login or password");
				return pgResponse;
			} 
			pgResponse.setmId(merchant.getMid());
			
			// check product Type;
			String productType = pgRequest.getProductType();
			pgResponse = checkBlankAndNull(productType, "product Type", pgResponse);
			if (!pgResponse.isResult()) {
				return pgResponse;
			}
			// check enabled product types
			String enabledProducts = merchant.getEnabledProducts();
//			String[] productsList = enabledProducts.split(",");
			if (!enabledProducts.contains(productType)) {
				pgResponse.setResponseCode("01");
				pgResponse.setResult(false);
				pgResponse.setDescription("Invalid product Type");
				pgResponse.setStatus("failed");
				return pgResponse;

			}
			
			// get products details
			MerchantProducts merchantProducts = merchantProductsRepo
					.findByMerchantIdAndProductType(merchant.getMid(), productType);
			
			String txnCurr = pgRequest.getTxncurr();
			pgResponse = checkBlankAndNull(txnCurr, "currency", pgResponse);
			if (!pgResponse.isResult()) {
				return pgResponse;
			}
			txnCurr = txnCurr.toUpperCase();
			String enabledCurrencies = merchantProducts.getEnabledCurrencies();
			if (!enabledCurrencies.contains(txnCurr)) {
				pgResponse.setResponseCode("01");
				pgResponse.setResult(false);
				pgResponse.setDescription("Invalid Txn Currency");
				pgResponse.setStatus("failed");
				return pgResponse;
			}

			// check payment type
			String paymentMode = pgRequest.getPaymentMode();
			pgResponse = checkBlankAndNull(paymentMode, "paymentMode", pgResponse);
			if (!pgResponse.isResult()) {
				return pgResponse;
			} 
				String enablePaymentMode = merchantProducts.getEnabledPaymentModes();

				if (!enablePaymentMode.toUpperCase().contains(paymentMode)) {
					pgResponse.setResponseCode("01");
					pgResponse.setResult(false);
					pgResponse.setDescription("Invalid Payment Mode");
					pgResponse.setStatus("failed");
					return pgResponse;
			}

			// check amount
			String actualTxnAmt = pgRequest.getAmt().toString();
			String txnAmount = "";
			pgResponse = checkBlankAndNull(actualTxnAmt, "amt", pgResponse);
			if (!pgResponse.isResult()) {
				return pgResponse;
			} 
				try {
					// txnAmount = ISOStandard.convertToISOStandard(actualTxnAmt, txnCurr); //
					txnAmount = actualTxnAmt;
					if (txnAmount == null || Double.parseDouble(txnAmount) <= 0) {
						pgResponse.setResult(false);
						pgResponse.setResponseCode("01");
						pgResponse.setDescription("Invalid Amount");
						return pgResponse;
					}
				} catch (Exception e) {
					pgResponse.setResult(false);
					pgResponse.setResponseCode("01");
					pgResponse.setDescription("Invalid Amount");
					return pgResponse;
				}

			// check merchant txnid
			String merchnattxnid = pgRequest.getMerchantTxnId();
			pgResponse = checkBlankAndNull(merchnattxnid, "merchnattxnid", pgResponse);
			if (!pgResponse.isResult()) {
				return pgResponse;
			}


//			String paymentType = pgRequest.getPaymentType();
			// check mobile number
			if (!paymentMode.equalsIgnoreCase("upi")) {
				String mobile = pgRequest.getMobile();
				pgResponse = checkBlankAndNull(mobile, "mobile", pgResponse);
				if (!pgResponse.isResult()) {
					return pgResponse;
				} else {
					Pattern p = Pattern.compile("\\d{10}");
					Matcher m = p.matcher(mobile);

					if (!m.matches()) {
						pgResponse.setResult(false);
						pgResponse.setResponseCode("01");
						pgResponse.setDescription("Invalid Mobile Number");
						return pgResponse;
					}
				}
			}

			// check card data
			if (!paymentMode.equalsIgnoreCase("nb") && !paymentMode.equalsIgnoreCase("neft")
					&& !paymentMode.equalsIgnoreCase("challan")) {
				String carddata = pgRequest.getCarddata();
				pgResponse = checkBlankAndNull(carddata, "carddata", pgResponse);
				if (!pgResponse.isResult()) {
					return pgResponse;
				}
			}

			// check bank id
			if (paymentMode.equalsIgnoreCase("wallet") || paymentMode.equalsIgnoreCase("nb")) {
				String bankid = pgRequest.getBankid();
				pgResponse = checkBlankAndNull(bankid, "bankid", pgResponse);
				if (!pgResponse.isResult()) {
					return pgResponse;
				}
			}

			// check od
			String od = pgRequest.getOd();
			pgResponse = checkBlankAndNull(od, "od", pgResponse);
			if (!pgResponse.isResult()) {
				return pgResponse;
			}

			// check ru
			String ru = pgRequest.getRu();
			pgResponse = checkBlankAndNull(ru, "ru", pgResponse);
			if (!pgResponse.isResult()) {
				return pgResponse;
			}

			MerchantSetting merchantSetting = null;
//			ProcessorWallet processorWallet = null;
//			ProcessorBank processorBank = new ProcessorBank();
			MerchantSetting merchantSettingObj = null;

			try {
				try {
					merchantSettingObj = new MerchantSetting();
					merchantSettingObj.setMerchantId(userDetails.getMid());
					merchantSettingObj.setCurrency(pgRequest.getTxncurr());
					merchantSettingObj.setPaymentMode(pgRequest.getPaymentMode());
					merchantSettingObj.setPaymentType(pgRequest.getPaymentType());

					if (paymentMode.equalsIgnoreCase("nb")) {
						merchantSettingObj.setBank(Long.valueOf(pgRequest.getBankid()));
					}
					merchantSettingObj.setDefault(false);
					merchantSetting = merchantSettingDao.findByMerchantSetting(merchantSettingObj);
					logger.info("merchant setting by nb and bank=>" + merchantSettingObj);
				} catch (Exception e) {
					new GlobalExceptionHandler().customException(e);
				}

				if (merchantSetting == null) {
					merchantSettingObj.setPaymentType("ALL");
					merchantSetting = merchantSettingDao.findByMerchantSetting(merchantSettingObj);
					logger.info("merchant setting by ALL=>" + merchantSettingObj);
				}

				if (merchantSetting == null) {
					try {
						merchantSettingObj.setBank(0l);
						merchantSettingObj.setPaymentType(pgRequest.getPaymentType());
						merchantSettingObj.setDefault(false);
						merchantSetting = merchantSettingDao.findByMerchantSetting(merchantSettingObj);
						logger.info("merchant setting by nb and blank bank=>" + merchantSettingObj);
					} catch (Exception e) {
						new GlobalExceptionHandler().customException(e);
					}
				}

				if (merchantSetting == null) {
					merchantSettingObj.setPaymentType("ALL");
					merchantSetting = merchantSettingDao.findByMerchantSetting(merchantSettingObj);
					logger.info("merchant setting by ALL=>" + merchantSettingObj);
				}

				if (merchantSetting == null) {
//			if(pgRequest.getPaymentMode().equalsIgnoreCase("NB")) {
//				merchantSettingObj.setBank(Long.valueOf(pgRequest.getBankid()));
//			}
					merchantSettingObj.setDefault(true);
					merchantSettingObj.setPaymentType("ALL");
					merchantSetting = merchantSettingDao.findByMerchantSetting(merchantSettingObj);
					logger.info("merchant setting default setting=>" + merchantSettingObj);
				}
//		if (merchantSetting == null) {
//			//183E code
//			merchantSettingObj.setBank(Long.valueOf(pgRequest.getBankid()));
//			merchantSettingObj.setDefault(true);
//			merchantSettingObj.setPaymentType("ALL");
//			merchantSetting = merchantSettingDao.findByMerchantSetting(merchantSettingObj);
//			logger.info("merchant setting default setting=>" + merchantSettingObj);
//		}

				// setMerchantSetting(merchantSetting);
			} catch (Exception e) {
				new GlobalExceptionHandler().customException(e);
			}
			if (merchantSetting == null) {

				merchantSetting = merchantSettingDao.findDefaultMerchantSetting(merchantSettingObj);
				// setMerchantSetting(merchantSetting);
			}
			long bankId = 0;

			if (paymentMode.equalsIgnoreCase("nb")) {
				String bankIdString = pgRequest.getBankid();
				if (bankIdString != null && !bankIdString.trim().equals("")) {
					bankId = Long.valueOf(bankIdString);
				}
			}

			if (paymentMode.equalsIgnoreCase("nb") && ComponentUtils.getDirectBankIds().contains(bankId)) {

			} else if (merchantSetting == null) {

				pgResponse.setResult(false);
				pgResponse.setResponseCode("01");
				pgResponse.setDescription("No default Setting Found");
				pgResponse.setStatus("failed");
				return pgResponse;
			}
			logger.info("Selected merchant setting=>" + merchantSetting.getMerchantSettingId());


			PropertiesVo properties = propertiesService.findByPropertykeyWithUpdatedCerts("RISK_API_ENABLE");
			PropertiesVo propertiesV2 = propertiesService.findByPropertykeyWithUpdatedCerts("RISK_API_ENABLE_V2");
			
			if (propertiesV2 != null && propertiesV2.getPropertyValue().equalsIgnoreCase("true") ) {
				pgResponse = riskService.validateFrmRiskParamByApiCall(pgRequest, pgResponse);
			} else {
			if (properties != null && properties.getPropertyValue().equalsIgnoreCase("true")) {
				pgResponse = riskService.validateRiskParamByApiCall(pgRequest, pgResponse);
			} else {
				pgResponse = validateRiskParams(pgRequest, pgResponse);
			}
			}
			if (!pgResponse.isResult()) {
				return pgResponse;
			}

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}

		return pgResponse;

	}
	private PaymentResponse validateRiskParams(PaymentRequest paymenRequest, PaymentResponse pgResponse) {
		MerchantRisk merchantRisk = merchantRiskRepo.findByMidAndStatus(paymenRequest.getMerchantId(), 1);

		if (merchantRisk != null && merchantRisk.getId() != null && merchantRisk.getId() > 0) {

			if (merchantRisk.getMaxTxnAmt() != null && merchantRisk.getMaxTxnAmt().compareTo(BigDecimal.ZERO) > 0) {
				BigDecimal configuredRiskAmount = merchantRisk.getMaxTxnAmt();
				BigDecimal txnAmount = paymenRequest.getAmt();

				if (txnAmount.compareTo(configuredRiskAmount) > 0) {
					pgResponse.setResult(false);
					pgResponse.setResponseCode("01");
					pgResponse.setDescription(
							"Transaction Amount should not be greater than " + configuredRiskAmount.toString());
					pgResponse.setStatus("failed");
					return pgResponse;
				}

			}

			if (merchantRisk.getMaxNoOfTxnDailyAmt() != null
					&& merchantRisk.getMaxNoOfTxnDailyAmt().compareTo(BigDecimal.ZERO) > 0) {
				BigDecimal configuredRiskAmount = merchantRisk.getMaxNoOfTxnDailyAmt();
				LocalDateTime fromDate = RemoteDbUtil.getTodayFromDate();
				LocalDateTime toDate = RemoteDbUtil.getTodayToDate();

				BigDecimal todaysAmount = transactionLogDao.getTransactionSumOfMid(fromDate, toDate,
						paymenRequest.getMerchantId());

				if (todaysAmount == null) {
					todaysAmount = BigDecimal.ZERO;
				}

				todaysAmount = todaysAmount.add(paymenRequest.getAmt());

				if (todaysAmount.compareTo(configuredRiskAmount) > 0) {
					pgResponse.setResult(false);
					pgResponse.setResponseCode("01");
					pgResponse.setDescription(
							"Transaction Amount should not be greater than " + configuredRiskAmount.toString());
					pgResponse.setStatus("failed");
					return pgResponse;
				}
			}

		}
		pgResponse.setStatus("success");
		pgResponse.setDescription("validate successfully");
		pgResponse.setResult(true);
		pgResponse.setResponseCode("00");
		return pgResponse;

	}
}
