package com.ftk.pg.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftk.pg.dto.PaymentRequest;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.CardBean;
import com.ftk.pg.modal.DmoOnboarding;
import com.ftk.pg.modal.Merchant;
import com.ftk.pg.modal.MerchantProductTransaction;
import com.ftk.pg.modal.MerchantProducts;
import com.ftk.pg.modal.TransactionEssentials;
import com.ftk.pg.modal.TransactionLog;
import com.ftk.pg.modal.UpiQrDetail;
import com.ftk.pg.modal.User;
import com.ftk.pg.pi.modal.IntermediateTransaction;
import com.ftk.pg.pi.modal.TransactionMapping;
import com.ftk.pg.pi.modal.TransactionMappingPk;
import com.ftk.pg.pi.repo.IntermediateTransactionRepo;
import com.ftk.pg.pi.service.TransactionMappingService;
import com.ftk.pg.repo.BankRepo;
import com.ftk.pg.repo.CardBeanRepo;
import com.ftk.pg.repo.DmoOnboardingDao;
import com.ftk.pg.repo.MerchantProductTransactionRepo;
import com.ftk.pg.repo.MerchantProductsRepo;
import com.ftk.pg.repo.MerchantRepo;
import com.ftk.pg.repo.TransactionEssentialsRepo;
import com.ftk.pg.repo.TransactionLogRepo;
import com.ftk.pg.repo.UpiQrDetailRepo;
import com.ftk.pg.repo.UserRepo;
import com.ftk.pg.requestvo.API_PaymentRequest;
import com.ftk.pg.requestvo.CardBinRequest;
import com.ftk.pg.requestvo.CardBinRequestWrapper;
import com.ftk.pg.requestvo.DynamicQrModel;
import com.ftk.pg.requestvo.IciciHybridRequest;
import com.ftk.pg.requestvo.PropertiesVo;
import com.ftk.pg.requestvo.QrRequest;
import com.ftk.pg.requestvo.RequestParams;
import com.ftk.pg.requestvo.RequestProductVo;
import com.ftk.pg.requestvo.RequestProductsVo;
import com.ftk.pg.responsevo.CardBinResponse;
import com.ftk.pg.responsevo.CardBinResponseWrapper;
import com.ftk.pg.responsevo.IciciDynamicQr3Response;
import com.ftk.pg.responsevo.PaymentResponse;
import com.ftk.pg.util.CardBinUtils;
import com.ftk.pg.util.CommissionModel;
import com.ftk.pg.util.Constants;
import com.ftk.pg.util.ConvenienceModel;
import com.ftk.pg.util.FrmUtils;
import com.ftk.pg.util.IciciCompositPay;
import com.ftk.pg.util.IciciDynamicQrCall;
import com.ftk.pg.util.PGUtility;
import com.ftk.pg.util.RemoteDbUtil;
import com.ftk.pg.util.Util;
import com.ftk.pg.util.Utilities;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.mb.getepay.auupi.action.Call;
import com.mb.getepay.auupi.util.Config;
import com.mb.getepay.auupi.util.GetQrResponse;
import com.pgcomponent.security.SecureCardData;
import com.pgcomponent.security.SignatureGenerator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ApiService {
	
	static Logger logger = LogManager.getLogger(ApiService.class);
	
	private final UpiQrDetailRepo upiQrDetailRepo;
	
	private final UserRepo userRepo;
	
	private final PropertiesService propertiesService;
	
	private final  TransactionEssentialsRepo transactionEssentialsRepo;

	private final IntermediateTransactionRepo intermediateTransactionRepo;
	
	private final  MerchantProductsRepo merchantProductsRepo;
	
	private final BankRepo bankRepo;
	
	private final MerchantRepo merchantRepo;
	
	private final  CardBeanRepo cardBeanRepo;

	private final ValidationService validationService;

	private final TransactionLogRepo transactionLogRepo;
	
	private final MerchantProductTransactionRepo merchantProductTransactionRepo;
	
	private final DmoOnboardingDao digitalMerchantOnboardingDao;
	
	private final TransactionMappingService transactionMappingService;
	
	private final CardBeanService cardBeanService;
	
//	private final CommissionModelRepo commissionModelRepo;

	public User getUserDetailByVpaId(String vpa) {
		UpiQrDetail upiQrDetail = upiQrDetailRepo.findByVpaAndEnable(vpa.trim(), true);
		if (upiQrDetail != null) {
			User u = new User();
			u.setMid(upiQrDetail.getMid());
			u.setRoleId(3l);
			List<User> users = userRepo.findByMidAndRoleId(upiQrDetail.getMid(), 3l);
			return users.get(0);
		}
		return null;
	}


	public void saveTranasctionEssential(API_PaymentRequest pgRequest, TransactionLog tlog,
			IntermediateTransaction intermidateTrasnaction) {
		logger.info("Inside saveTranasctionEssential for the TPV transaction===>for txnId" + tlog.getTransactionId());
		try {
			if (tlog != null) {
				TransactionEssentials transactionEssentials = transactionEssentialsRepo.findByTransactionId(Long.valueOf(tlog.getTransactionId()));

				if (transactionEssentials == null) {
					transactionEssentials = new TransactionEssentials();
					transactionEssentials.setTransactionId(tlog.getTransactionId());
					transactionEssentials.setUdf57(intermidateTrasnaction.getUdf15());
					Object id = transactionEssentialsRepo.save(transactionEssentials);
					logger.info("save TransactionEssentials ==> " + id);
				} else {
					transactionEssentials.setUdf57(intermidateTrasnaction.getUdf15());
					transactionEssentialsRepo.save(transactionEssentials);
					logger.info("update TransactionEssentials ==> ");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getEnabledPaymode(String merchantName, String productType) {
//		logger.info("Merchant Name: " + merchantName);

		User user = new User();
		user = userRepo.findByUsername(merchantName);
		MerchantProducts merchantProducts = new MerchantProducts();
		merchantProducts = merchantProductsRepo.findByMerchantIdAndProductType(user.getMid(), productType);
		return merchantProducts.getEnabledPaymentModes();

	}

	public void insetUpdateTrasnsactionEssentials(RequestParams requestParams, Long transactionId) {

		if (transactionId != null) {
			TransactionEssentials transactionEssentials = transactionEssentialsRepo.findByTransactionId(transactionId);

			if (transactionEssentials == null) {
				transactionEssentials = new TransactionEssentials();
			} 
			transactionEssentials.setTransactionId(transactionId);
			transactionEssentials.setUdf56("PG-V2");
			transactionEssentials.setUdf31(requestParams.getBrowserData());
			transactionEssentials.setUdf60(requestParams.getIpAddress());
			transactionEssentialsRepo.save(transactionEssentials);

		}

	}

	public PaymentResponse callPaymentApi(RequestParams requestParams, Map<String, String> propMap) {
		PaymentResponse paymentResponse = new PaymentResponse();
		try {
//			PropertiesVo paynowURlProp = propertiesService.findByPropertykeyWithUpdatedCerts(Constants.PAYNOW_URL);
//			if (paynowURlProp != null) {
//				Utilities.paynowURl = paynowURlProp.getPropertyValue();
//			} else {
//				paymentResponse.setDescription("Paynow URL is not found");
//				paymentResponse.setResponseCode("01");
//				paymentResponse.setResult(false);
//				paymentResponse.setStatus("Failed");
//				return paymentResponse;
//			}
//			PropertiesVo returnURlProp = propertiesService.findByPropertykeyWithUpdatedCerts(Constants.RETURN_URL);
			String returnUrl = propMap.get(Constants.RETURN_URL);
			
			if (returnUrl != null) {
				Utilities.returnUrl = returnUrl;
			} else {
				paymentResponse.setDescription("Return URL is not found");
				paymentResponse.setResponseCode("01");
				paymentResponse.setResult(false);
				paymentResponse.setStatus("Failed");
				return paymentResponse;
			}

			PaymentRequest pgRequest = copyRequestparamsToPgRequest(requestParams, propMap);
			paymentResponse = validationService.paynow(pgRequest,propMap);
			return paymentResponse;
		} catch (Exception e) {
			logger.info("Error in Generated Processor Request :: " + requestParams.getTransactionId() + " :: " + e.getMessage());
			paymentResponse.setDescription("Error occured :: " + e.getMessage());
			paymentResponse.setResponseCode("01");
			paymentResponse.setResult(false);
			paymentResponse.setStatus("Failed");
			new GlobalExceptionHandler().customException(e);
		}
		return paymentResponse;
	}

	private PaymentRequest copyRequestparamsToPgRequest(RequestParams requestParams,Map<String, String> propMap) {
		PaymentRequest pgRequest = new PaymentRequest();
		String privateKey = "";
		String signatureKey = "";
		LocalDateTime localDate = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String txnDate = localDate.format(formatter);
		String amt = requestParams.getAmt();
		String merchantTxnId = requestParams.getMerchantTxnId();
		String login = requestParams.getLogin();
		String txncurr = requestParams.getTxncurr();
		String cardType = requestParams.getCardType();
		String custName = requestParams.getName();
		String mobile = requestParams.getMobile();
		String paymode = requestParams.getPaymentMode();
		Long transactionId = requestParams.getTransactionId();

		User user = userRepo.findByUsernameAndEnabled(login, true);
//
		Merchant merchantDetails = merchantRepo.findByMid(user.getMid());
		signatureKey = merchantDetails.getRequestHashKey();
		privateKey = merchantDetails.getMerchantPrivateKey();

		pgRequest.setAmt(new BigDecimal(amt));
		pgRequest.setLogin(login);
		pgRequest.setMerchantTxnId(merchantTxnId);
		pgRequest.setMobile(mobile);
		pgRequest.setPaymentMode(paymode.toUpperCase());
		pgRequest.setTxncurr(txncurr);
		pgRequest.setRu(Utilities.returnUrl);
		pgRequest.setOd("Getepat Txn");
		pgRequest.setTransactionId(transactionId);
		pgRequest.setPaymentType(cardType);
		pgRequest.setDate(txnDate);
		pgRequest.setProductType(requestParams.getProductType());

		if (requestParams.getUdf1() != null) {
			pgRequest.setUdf1(requestParams.getUdf1());
		}
		if (requestParams.getUdf2() != null) {
			pgRequest.setUdf2(requestParams.getUdf2());
		}
		if (requestParams.getUdf3() != null) {
			pgRequest.setUdf3(requestParams.getUdf3());
		}
		if (requestParams.getUdf4() != null) {
			pgRequest.setUdf4(requestParams.getUdf4());
		}
		if (requestParams.getUdf5() != null) {
			pgRequest.setUdf5(requestParams.getUdf5());
		}

		String encryptData = "";
//		String signature = "";
//
//		try {
//			signature = SignatureGenerator.signatureGeneration(new String[] { amt, txncurr, login, merchantTxnId },
//					signatureKey);
//			logger.info("Generated Processor Request signature ::   ****" + signature + "****");
//
//			pgRequest.setSignature(signature);
//		} catch (Exception e) {
//			logger.info("Error in Generated Processor Request signature ::   ****" + e.getMessage());
//		}
		try {
			SecureCardData secureCardData = new SecureCardData();
			if (paymode.equalsIgnoreCase("CC") || paymode.equalsIgnoreCase("DC")) {
				String cardNo = requestParams.getNumber();
				cardNo = cardNo.replaceAll("\\s", "");
				logger.info(
						"Card NO ::" + cardNo.substring(0, 4) + "**** ****" + cardNo.substring(12, cardNo.length()));
				String cardAssociate = "";
				String binStr = cardNo.substring(0, 9);
				int bin = Integer.parseInt(binStr);

//				CardBean bean = cardBeanRepo.getBeanDetailbyBeanValue(bin);
				CardBean cardBean = null;
				Gson gson = new Gson();
//				Map<String, String> propMap = new HashMap<>();

				try {
//					List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
//					for (PropertiesVo property : PropertiesList) {
//						propMap.put(property.getPropertyKey(), property.getPropertyValue());
//					}
					String cardBinApiFlag = propMap.get(CardBinUtils.CARD_BEAN_V2_API_ENABLE);
					if (cardBinApiFlag == null || cardBinApiFlag.equalsIgnoreCase("false")) {
//						List<CardBean> listCardBean = cardBeanRepo.getBeanDetailbyBeanValue(bin);
						List<CardBean> listCardBean = cardBeanService.getBeanDetailsByValue(bin);
						cardBean = listCardBean.get(0);
					} else {
						cardBean = new CardBean();
						CardBinRequest cardBinRequest = new CardBinRequest();
						String cardnumber = String.valueOf(binStr).substring(0, 6);
						cardBinRequest.setBin(cardnumber);

						logger.info("CardBinRequest=====================>" + gson.toJson(cardBinRequest));
						String encrytpedData = CardBinUtils.encrypt(gson.toJson(cardBinRequest),
								propMap.get(CardBinUtils.CARD_BIN_MASTER_KEY));

						CardBinRequestWrapper cardBinRequestWrapper = new CardBinRequestWrapper();
						cardBinRequestWrapper.setMid(propMap.get(CardBinUtils.CARD_BIN_MID));
						cardBinRequestWrapper.setMethod(propMap.get(CardBinUtils.CARD_BIN_METHOD));
						cardBinRequestWrapper.setRequest(encrytpedData);
						cardBinRequestWrapper.setSignature(CardBinUtils.signatureGenarator(cardBinRequestWrapper));

						logger.info("Card Bin Wrapper Request=====================>" + gson.toJson(cardBinRequestWrapper));
						String response = FrmUtils.frmRiskApiCall2(propMap.get(CardBinUtils.NEW_CARD_BEAN_API),
								gson.toJson(cardBinRequestWrapper));

						logger.info("Card Bin Enceypted Response=====>" + response);

						CardBinResponseWrapper cardbeanResponseWrapper = gson.fromJson(response,
								CardBinResponseWrapper.class);

						String decryptedData = CardBinUtils.decrypt(cardbeanResponseWrapper.getResponse(),
								propMap.get(CardBinUtils.CARD_BIN_MASTER_KEY));

						CardBinResponse cardbeanResponse = gson.fromJson(decryptedData, CardBinResponse.class);
						logger.info("Card Bin Decrypted Response================>" + gson.toJson(cardbeanResponse));

						if (cardbeanResponse != null && cardbeanResponse.getCountry().equalsIgnoreCase("INDIA")) {
							cardBean.setDomesticInternational("D");
						} else {
							cardBean.setDomesticInternational("I");
						}

						if (cardbeanResponse != null && cardbeanResponse.getCategory() != null
								&& cardbeanResponse.getCategory().contains("corporate")) {
							cardBean.setDomesticInternational("C");
						}

						if (cardbeanResponse != null && cardbeanResponse.getType().equalsIgnoreCase("Debit")) {
							cardBean.setTypeOfCard("D");
						} else if (cardbeanResponse != null && cardbeanResponse.getType().equalsIgnoreCase("Credit")) {
							cardBean.setTypeOfCard("C");
						} else {
							cardBean.setTypeOfCard("P");
						}

						if (cardbeanResponse != null && cardbeanResponse.getScheme().equalsIgnoreCase("Visa")) {
							cardBean.setCardType("VISA");
						} else if (cardbeanResponse != null
								&& cardbeanResponse.getScheme().equalsIgnoreCase("Mastercard")) {
							cardBean.setCardType("MASTERCARD");
						} else if (cardbeanResponse != null && cardbeanResponse.getScheme().equalsIgnoreCase("Rupay")) {
							cardBean.setCardType("RUPAY");
						}
					}
				} catch (Exception e) {
					logger.info("Error in Card Bin Api !!!!!!");
					new GlobalExceptionHandler().customException(e);
				}
				if (cardBean != null) {
					cardAssociate = cardBean.getCardType();
				} else {
					if (cardNo.startsWith("5")) {
						cardAssociate = "masterCard".toUpperCase();
					} else if (cardNo.startsWith("4")) {
						cardAssociate = "VISA";
					}
				}

				String expiry = requestParams.getExpiry();
				String[] split = expiry.split("/");
				String month = split[0].trim();
				String year = split[1].trim();
				if (year != null && year.length() == 2) {
					year = "20" + year;
				}
				String cvc = requestParams.getCvc().trim();
				String cardData = cardNo + "|" + cvc + "|" + year + "|" + month;
				logger.info("Call Encrypt Data Service  :: " + cardNo.substring(0, 4) + "**** ****"
						+ cardNo.substring(12, cardNo.length()));
				encryptData = secureCardData.encryptData(cardData, privateKey);
				logger.info("Encrypt Data Service Call :: " + encryptData);
				encryptData += "|" + custName + "|" + cardType + "|" + cardAssociate;
				pgRequest.setBankid(cardAssociate);
				pgRequest.setCardBean(cardBean);
			} else if (paymode.equalsIgnoreCase("wallet")) {
				mobile = requestParams.getNbmobile();
				pgRequest.setBankid(requestParams.getNbbankid());
				pgRequest.setMobile(mobile);
				pgRequest.setName(custName);
				encryptData = secureCardData.encryptData(mobile, privateKey);

			} else if (paymode.equalsIgnoreCase("upi")) {
				String upiId = requestParams.getUpiId();
				encryptData = secureCardData.encryptData(upiId, privateKey);
			} else if (paymode.equalsIgnoreCase("nb")) {
				mobile = requestParams.getNbmobile();
				pgRequest.setMobile(mobile);
				pgRequest.setName(custName);
				pgRequest.setBankid(requestParams.getNbbankid());
			} else if (paymode.equalsIgnoreCase("NEFT")) {
				pgRequest.setMobile(requestParams.getNeftNumber());
				// pgRequest.setUdf1(requestParams.getNeftNumber());
				pgRequest.setName(requestParams.getNeftName());
				pgRequest.setName(custName);

			} else if (paymode.equalsIgnoreCase("CHALLAN")) {

				pgRequest.setMobile(requestParams.getNeftNumber());
				// pgRequest.setUdf1(requestParams.getNeftNumber());
				pgRequest.setName(requestParams.getNeftName());
				pgRequest.setName(custName);

			}
		} catch (Exception e) {
			logger.info("Error in the payment process Detail :: " + e.getMessage());
			new GlobalExceptionHandler().customException(e);
		}
		pgRequest.setRequestType("PAYMENT_PAGES");
		pgRequest.setCarddata(encryptData);
		return pgRequest;
	}

	public PaymentResponse checkDetails(PaymentRequest paymentRequest,Merchant merchant) {
		logger.info(" GetePay paymentRequest ==> " + paymentRequest);

		PaymentResponse response = validationService.validatePaymentRequest(paymentRequest);
//		Merchant merchant = merchantRepo.findByMid(response.getmId());

		if (response.isResult()) {
			TransactionLog transactionLog = new TransactionLog();
			BigDecimal mAmount = paymentRequest.getAmt().setScale(2, RoundingMode.HALF_EVEN);
			transactionLog.setMerchantId(merchant.getMid());
			transactionLog.setAmt(mAmount);
			transactionLog.setMerchantName(merchant.getMerchantName());
			transactionLog.setTxncurr(paymentRequest.getTxncurr());
			transactionLog.setMerchanttxnid(paymentRequest.getMerchantTxnId());
			transactionLog.setOrderNumber(paymentRequest.getMerchantTxnId());
			transactionLog.setResponseCode("02");
			transactionLog.setStage("Transaction in Process");
			transactionLog.setTxnStatus("PENDING");
			transactionLog.setTxnType(paymentRequest.getTxnType());
			transactionLog.setProductType(paymentRequest.getProductType());

			if (paymentRequest.getUdf1() != null) {
				transactionLog.setUdf1(paymentRequest.getUdf1());
			}
			if (paymentRequest.getUdf2() != null) {
				transactionLog.setUdf2(paymentRequest.getUdf2());
			}
			if (paymentRequest.getUdf3() != null) {
				transactionLog.setUdf3(paymentRequest.getUdf3());
			}
			if (paymentRequest.getUdf4() != null) {
				transactionLog.setUdf4(paymentRequest.getUdf4());
			}
			if (paymentRequest.getUdf5() != null) {
				if (paymentRequest.getUdf5().contains("|")) {
					String[] pr = paymentRequest.getUdf5().split("\\|");
					if (pr != null && pr.length > 1) {
						transactionLog.setUdf5(pr[0]);
						transactionLog.setUdf6(pr[1]);
					} else if (pr != null && pr.length == 1) {
						transactionLog.setUdf5(pr[0]);
					} else {
						transactionLog.setUdf5(paymentRequest.getUdf5());
					}
				}

			}

			if (paymentRequest.getPaymentMode() != null && paymentRequest.getPaymentMode().equalsIgnoreCase("neft")) {

				if (paymentRequest.getUdf5() == null) {
					transactionLog.setUdf5(paymentRequest.getVan());
				}
				transactionLog.setPaymentMode("NEFT");

			}

			else if (paymentRequest.getPaymentMode() != null
					&& paymentRequest.getPaymentMode().equalsIgnoreCase("challan")) {
				if (paymentRequest.getUdf5() == null) {
					transactionLog.setUdf5(paymentRequest.getVan());
				}

				else {
					transactionLog.setUdf5(paymentRequest.getUdf5());
				}
				transactionLog.setPaymentMode("CHALLAN");
			}

			if (paymentRequest.getRu() != null && (!paymentRequest.getRu().trim().equals(""))) {
				transactionLog.setRu(paymentRequest.getRu());
			}
			transactionLog.setTotalrefundAmt(BigDecimal.ZERO);
			transactionLog.setServiceCharge(0.0);
			transactionLog.setServiceChargeType("");

			// BigDecimal cardAmount =
			// merchantCommisionService.getCommisionAmount(merchant.getMid(),
			// "card",paymentRequest.getAmt().doubleValue());
			// BigDecimal nbAmount =
			// merchantCommisionService.getCommisionAmount(merchant.getMid(),
			// "nb",paymentRequest.getAmt().doubleValue());

			transactionLog = transactionLogRepo.save(transactionLog);
			logger.info(" GetePay Router TransactionId ==> " + transactionLog.getTransactionId());

			if (transactionLog != null) {
				try {
					if (paymentRequest.getTxnType() != null && paymentRequest.getTxnType().equalsIgnoreCase("multi")) {
						RequestProductsVo products = PGUtility.parseRequestProducts(paymentRequest.getProductDetails());
						if (products != null && !products.getProducts().isEmpty()) {
							for (RequestProductVo productVo : products.getProducts()) {

								try {
									MerchantProductTransaction productTxn = new MerchantProductTransaction();
									productTxn.setTxnId(transactionLog.getTransactionId());
									productTxn.setMid(merchant.getMid());
									productTxn.setProductAmt(new BigDecimal(productVo.getAmount()));
									productTxn.setProductCode(productVo.getCode());
									productTxn.setProductName(productVo.getName());

									merchantProductTransactionRepo.save(productTxn);
								} catch (Exception e) {
									new GlobalExceptionHandler().customException(e);
								}
							}
						}

						List<MerchantProductTransaction> isExsitProductTxn = merchantProductTransactionRepo
								.findByTxnIdAndMid(transactionLog.getTransactionId(), merchant.getMid());
						if (isExsitProductTxn == null || isExsitProductTxn.isEmpty()) {
							response.setAmt("0");
							response.setResult(false);
							response.setStatus("failed");
							response.setDescription("Merchant product txn not save");
							return response;

						}
						logger.info("Merchant product txn save for txn id ==> " + transactionLog.getTransactionId()
								+ " and Merchant id ==>> " + merchant.getMid() + " isExsitProductTxn : "
								+ isExsitProductTxn.size());
					}
				} catch (Exception e) {
					logger.info("Error in save multiProduct txn ==> " + e.getMessage());
				}

				response.setTransactionId(transactionLog.getTransactionId());
				response.setResult(true);
				response.setStatus("success");
				response.setDescription("success");
				response.setmId(merchant.getMid());
				response.setAmt(paymentRequest.getAmt().toString());
				// response.setCardAmount(cardAmount.toString());
				// response.setNbAmount(nbAmount);

			} else {
				response.setAmt("0");
				response.setResult(false);
				response.setStatus("failed");
				response.setDescription("Unable to insert record");
			}
		}
		return response;
	}

	public DynamicQrModel getDynamicQr(Merchant merchant, CommissionModel cmodel, ConvenienceModel conmodel,
			com.ftk.pg.requestvo.PaymentRequest paymentRequest, String vpaId) {
		TransactionLog transactionLog = new TransactionLog();
//		UpiQrDetail upiQrDetail = new UpiQrDetail();
		String totalAmount = cmodel.getTotalStr();
		BigDecimal totalamount = new BigDecimal(totalAmount);
		totalamount = totalamount.add(conmodel.getCharges());

		logger.info("paymentRequest for getDynamicQr =>" + paymentRequest);
		transactionLog = transactionLogRepo.findById(paymentRequest.getTransactionId()).get();
//		upiQrDetail = upiQrDetailRepo.findByMidAndEnable(transactionLog.getMerchantId(), true);

		transactionLog.setUdf9(vpaId);
		transactionLogRepo.save(transactionLog);

		DynamicQrModel model = new DynamicQrModel();
		if (vpaId.toLowerCase().contains(".augp@aubank")) {
			List<PropertiesVo> properties = propertiesService.findByPropertykeyWithUpdatedCertsLike("AUQR_CONFIG_");
			Map<String, String> propertyMap = new HashMap<String, String>();
			for (PropertiesVo property : properties) {
				propertyMap.put(property.getPropertyKey(), property.getPropertyValue());
			}

			Config config = new Config();
			config.setApiKey(propertyMap.get(Constants.AUQR_CONFIG_API_KEY));
			config.setEncKey128(propertyMap.get(Constants.AUQR_CONFIG_API_ENC128));
			config.setEncKey256(propertyMap.get(Constants.AUQR_CONFIG_API_ENC256));
			config.setModString(propertyMap.get(Constants.AUQR_CONFIG_API_MODSTRING));
			config.setExpString(propertyMap.get(Constants.AUQR_CONFIG_API_EXPSTRING));
			config.setApiUrl(propertyMap.get(Constants.AUQR_CONFIG_API_URL));
			config.setApiUser(propertyMap.get(Constants.AUQR_CONFIG_API_USER));
			config.setMerchantCode(propertyMap.get(Constants.AUQR_CONFIG_API_MERCHANTCONFIG));

			Call call = new Call(config);
			String refVPA = vpaId.replace(".augp@aubank", "");
			String refId = RemoteDbUtil.PAYMENT_MODE_DYNAMIC_QR_REFID + paymentRequest.getTransactionId();

			GetQrResponse qrResponse = call.getQrDynamic(vpaId, String.valueOf(totalamount),
					String.valueOf(totalamount), refId, "", false, merchant.getMobileNumber().trim(),
					merchant.getMerchantName());
			if (qrResponse != null) {
				logger.info("QR GENERATE RESPONSE   ==> " + qrResponse.getTransactionInfo().getAttributes());
				try {
					String qrPath = generateAUDynamicQr(vpaId.trim(),
							qrResponse.getTransactionInfo().getAttributes().getQr(), paymentRequest);

					model.setQrImage(qrPath);
					model.setQrString(qrResponse.getTransactionInfo().getAttributes().getQr());
					return model;
				} catch (Exception e) {
					logger.info("Error in generating qr image ==> " + e.getMessage());
					new GlobalExceptionHandler().customException(e);
				}
			}

		} else if (vpaId.toLowerCase().contains("@utkarshbank")) {
			try {

				String refId = RemoteDbUtil.PAYMENT_MODE_DYNAMIC_QR_REFID + paymentRequest.getTransactionId();

				String vpa = vpaId;

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("pa", vpa); // VPA Id
				map.put("pn", merchant.getMerchantName()); // merchant name
				map.put("am", totalAmount);
				map.put("tr", refId);

				if (totalAmount != null && !String.valueOf(totalAmount).equals("")) {
					map.put("mam", totalAmount);
				}

				String url = "upi://pay?";
				url += map.entrySet().stream().map(x -> x.getKey() + "=" + x.getValue())
						.collect(Collectors.joining("&"));
				// String urlEncode = URLEncoder.encode(url, "UTF-8").replace(" ", "%20");
				String qrImage = generateQr(refId, url);
				model.setQrImage(qrImage);
				model.setQrString(url);
				return model;

			} catch (Exception e) {
				new GlobalExceptionHandler().customException(e);
			}
		}
//		else if (vpaId.toLowerCase().contains("@bom") || vpaId.toLowerCase().contains("@mahb")) {
//			try {
//				String refIdPart1 = Util.PAYMENT_MODE_DYNAMIC_QR_REFID;
//				String refIdPart2 = String.valueOf(paymentRequest.getTransactionId());
//
//				if ((refIdPart1 + refIdPart2).length() < 13) {
//					while ((refIdPart1 + refIdPart2).length() < 13) {
//						refIdPart2 = "0" + refIdPart2;
//					}
//				}
//
//				String refId = refIdPart1 + refIdPart2;
//
////				String vpa = "Getepay." + refId + "@mahb";
////				if(vpaId.toLowerCase().contains("@bom")) {
////					 vpa = "Getepay." + refId + "@bom";
////				}
//				// String vpa = "Getepay.merchant59020@icici";
//
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("pa", vpaId); // VPA Id
//				map.put("pn", merchant.getMerchantName()); // merchant name
//				map.put("am", totalAmount);
//				map.put("tr", refId);
//
//				if (totalAmount != null && !String.valueOf(totalAmount).equals("")) {
//					map.put("mam", totalAmount);
//				}
//
//				String url = "upi://pay?";
//				url += map.entrySet().stream().map(x -> x.getKey() + "=" + x.getValue())
//						.collect(Collectors.joining("&"));
//
//				String qrImage = generateQr(refId, url);
//				model.setQrImage(qrImage);
//				model.setQrString(url);
//				return model;
//
//			} catch (Exception e) {
//
//				e.printStackTrace();
//			}
//		} else if (vpaId.toLowerCase().contains("@esaf")) {
//			try {
//				String refIdPart1 = Util.PAYMENT_MODE_DYNAMIC_QR_REFID;
//				String refIdPart2 = String.valueOf(paymentRequest.getTransactionId());
//
//				if ((refIdPart1 + refIdPart2).length() < 13) {
//					while ((refIdPart1 + refIdPart2).length() < 13) {
//						refIdPart2 = "0" + refIdPart2;
//					}
//				}
//
//				String refId = refIdPart1 + refIdPart2;
//
//				// String vpa = "Getepay." + refId + "@esaf";
//				// String vpa = "Getepay.merchant59020@icici";
//
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("pa", vpaId); // VPA Id
//				map.put("pn", merchant.getMerchantName()); // merchant name
//				map.put("am", totalAmount);
//				map.put("tr", refId);
//
//				if (totalAmount != null && !String.valueOf(totalAmount).equals("")) {
//					map.put("mam", totalAmount);
//				}
//
//				String url = "upi://pay?";
//				url += map.entrySet().stream().map(x -> x.getKey() + "=" + x.getValue())
//						.collect(Collectors.joining("&"));
//				// String urlEncode = URLEncoder.encode(url, "UTF-8").replace(" ", "%20");
//				String qrImage = generateQr(refId, url);
//				model.setQrImage(qrImage);
//				model.setQrString(url);
//				return model;
//
//			} catch (Exception e) {
//
//				e.printStackTrace();
//			}
//		}
//
//		else if (vpaId.toLowerCase().contains("@kotak")) {
//
//			try {
//				String refIdPart1 = Util.PAYMENT_MODE_DYNAMIC_QR_REFID;
//				String refIdPart2 = String.valueOf(paymentRequest.getTransactionId());
//
//				if ((refIdPart1 + refIdPart2).length() < 13) {
//					while ((refIdPart1 + refIdPart2).length() < 13) {
//						refIdPart2 = "0" + refIdPart2;
//					}
//				}
//
//				String refId = refIdPart1 + refIdPart2;
//
//				// String vpa = "Getepay." + refId + "@esaf";
//				// String vpa = "Getepay.merchant59020@icici";
//				// &ver=01&mode=01&purpose=00&mc=5411&qrMedium=01
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("pa", vpaId); // VPA Id
//				map.put("pn", merchant.getMerchantName()); // merchant name
//				map.put("am", totalAmount);
//				map.put("tr", refId);
//
//				if (totalAmount != null && !String.valueOf(totalAmount).equals("")) {
//					map.put("mam", totalAmount);
//				}
////				if (paymentRequest.getExpiryDate() != null && !paymentRequest.getExpiryDate().equals("")) {
////					// map.put("am", totalAmount);
////				}
//				map.put("ver", "01");
//				map.put("mode", "01");
//				map.put("purpose", "00");
//				map.put("mc", merchant.getMccCode());
//				map.put("qrMedium", "01");
//
//				String url = "upi://pay?";
//				url += map.entrySet().stream().map(x -> x.getKey() + "=" + x.getValue())
//						.collect(Collectors.joining("&"));
//				// String urlEncode = URLEncoder.encode(url, "UTF-8").replace(" ", "%20");
//				String qrImage = generateQr(refId, url);
//				model.setQrImage(qrImage);
//				model.setQrString(url);
//				return model;
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//		} else if (vpaId.toLowerCase().contains("@axis")) {
//
//			Map<String, String> propMap = new HashMap<>();
//			List<Properties> PropertiesList = (List<Properties>) propertiesDao.findAll(Properties.class);
//			for (Properties property : PropertiesList) {
//				propMap.put(property.getPropertykey(), property.getPropertyValue());
//			}
//
//			try {
//				String refIdPart1 = Util.PAYMENT_MODE_DYNAMIC_QR_REFID;
//				String refIdPart2 = String.valueOf(paymentRequest.getTransactionId());
//				int temp = (refIdPart1 + refIdPart2).length();
//				if (temp < 13) {
//					while (temp < 13) {
//						refIdPart2 = "0" + refIdPart2;
//						temp = (refIdPart1 + refIdPart2).length();
//					}
//				}
//				String refIdPart3 = Util.ICICI_PAYMENT_MODE_DYNAMIC_QR_REFID;
//				String refIdPart4 = String.valueOf(paymentRequest.getTransactionId());
//				temp = (refIdPart3 + refIdPart4).length();
//				if (temp < 13) {
//					while (temp < 13) {
//						refIdPart4 = "0" + refIdPart4;
//						temp = (refIdPart3 + refIdPart4).length();
//
//					}
//				}
//				String refId2 = refIdPart3 + refIdPart4;
//				// String vpa = "Getepay.merchant59020@icici";
//				Long id = Long.valueOf(refIdPart2);
//				logger.info("<= testId =>" + paymentRequest);
//				if (id <= 0) {
//					return null;
//				}
//				Gson gson = new Gson();
//				AxisVerificationRequest req = new AxisVerificationRequest();
//				req.setMerchChanId(propMap.get(AxisDynamicQrUtil.AXIS_DYNAMIC_QR_MERCHANT_CHAN_ID));
//				req.setCreditVpa(vpaId);
//				req.setOrderId(refId2);
//
//				logger.info("Axis Bank Dynamic Qr Verification Request=======================>" + gson.toJson(req));
//				String checkString = AxisDynamicQrUtil
//						.checksum(propMap.get(AxisDynamicQrUtil.AXIS_DYNAMIC_QR_PUBLIC_KEY), req.checksum());
//
//				logger.info("Axis Bank Dynamic Qr CheckSum=======================>" + checkString);
//
//				req.setCheckSum(checkString);
//
//				logger.info(
//						"Axis Bank Dynamic Qr Verification Final Request=======================>" + gson.toJson(req));
//
//				String response = AxisDynamicQrUtil
//						.postapi(propMap.get(AxisDynamicQrUtil.AXIS_DYNAMIC_QR_VERIFICATION_URL), gson.toJson(req));
//
//				logger.info("Axis Bank Dynamic Qr Verification Final Response================>" + response);
//				AxisVerificationResponse axisResponse = gson.fromJson(response, AxisVerificationResponse.class);
//
//				if (axisResponse != null && axisResponse.getCode().equalsIgnoreCase("000")) {
//					Map<String, Object> map = new HashMap<String, Object>();
//					map.put("pa", vpaId); // VPA Id
//					map.put("pn", merchant.getMerchantName()); // merchant name
//					map.put("am", totalAmount);
//					map.put("tr", axisResponse.getData());
//
//					if (totalAmount != null && !String.valueOf(totalAmount).equals("")) {
//						map.put("mam", totalAmount);
//					}
////					if (paymentRequest.getExpiryDate() != null && !paymentRequest.getExpiryDate().equals("")) {
////						// map.put("am", totalAmount);
////					}
//					String url = "upi://pay?";
//					url += map.entrySet().stream().map(x -> x.getKey() + "=" + x.getValue())
//							.collect(Collectors.joining("&"));
//					// String urlEncode = URLEncoder.encode(url, "UTF-8").replace(" ", "%20");
//					String qrImage = generateQr(refId2, url);
//					model.setQrImage(qrImage);
//					model.setQrString(url);
//					return model;
//
//				} else {
//					return null;
//				}
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			return null;
//
//		}

		else if (vpaId.toLowerCase().contains("@icici")) {
			try {

				// DynamicQrManagement dynamicQrmanagement = new DynamicQrManagement();
				Map<String, String> propMap = new HashMap<>();
				List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
				for (PropertiesVo property : PropertiesList) {
					propMap.put(property.getPropertyKey(), property.getPropertyValue());
				}

				String refId = null;
				Map<String, Object> map = new HashMap<String, Object>();
				DmoOnboarding dmoOnboarding = digitalMerchantOnboardingDao.findByVpa(vpaId);
				if (dmoOnboarding != null) {
					String refIdPart1 = Util.ICICI_PAYMENT_MODE_DYNAMIC_QR_REFID;
					String refIdPart2 = String.valueOf(paymentRequest.getTransactionId());
					int temp = (refIdPart1 + refIdPart2).length();
					if (temp < 13) {
						while (temp < 13) {
							refIdPart2 = "0" + refIdPart2;
							temp = (refIdPart1 + refIdPart2).length();
						}
					}
					refId = refIdPart1 + refIdPart2;
					// String vpa = "Getepay.merchant59020@icici";
					Long id = Long.valueOf(refIdPart2);
					logger.info("<= testId =>" + paymentRequest);
					if (id <= 0) {
						return null;
					}

					if (propMap.get(Utilities.ENABLE_NEW_DYNAMIC_QR).equalsIgnoreCase("true")) {

						String merchantlist = propMap.get(Utilities.ENABLE_NEW_DYNAMIC_QR_MID);
						boolean isMidsPresent = false;
						if (merchantlist != null && !merchantlist.equalsIgnoreCase("")) {
							String[] midArrays = merchantlist.split(",");
							Set<String> midsOfnewDynamicQr = Arrays.stream(midArrays).collect(Collectors.toSet());
								if (midsOfnewDynamicQr.contains(String.valueOf(merchant.getMid()))) {
									isMidsPresent = true;
							}
						}
						
						if (isMidsPresent) {

							logger.info("Inside Updated Dynamic Qr===========================================>");
							// ICICI DYNAMIC_QR
							try {
								String requestId = "";
								String qr3URl = "";
								Gson gson = new Gson();
								ObjectMapper objectMapper = new ObjectMapper();
//								Properties properties = null;
								QrRequest request = new QrRequest();
								request.setAmount(String.valueOf(totalamount));
								request.setMerchantId(String.valueOf(dmoOnboarding.getEazypayMerchantID())); // use EazypayMerchantId for mid
								request.setTerminalId(String.valueOf(merchant.getMccCode()));
								request.setMerchantTranId(refId);
								request.setBillNumber(String.valueOf(transactionLog.getTransactionId()));
								request.setValidatePayerAccFlag("N");
								request.setPayerAccount(merchant.getAccountNumber());
								request.setPayerIFSC(merchant.getIfscCode());
								String requestString = gson.toJson(request); // convert request into json paylo0ad

								logger.info(" Updated Dynamic Qr Request=======================>"+ request);
//								properties = new Properties();
//								properties.setPropertykey(IciciDynamicQrCall.ICICI_DYNAMIC_QR_URL);
//								Properties qr3URlProp = propertiesDao.findByPropertykey(IciciDynamicQrCall.ICICI_DYNAMIC_QR_URL);
								qr3URl = propMap.get(IciciDynamicQrCall.ICICI_DYNAMIC_QR_URL);
								if (qr3URl != null) {
//									qr3URl = qr3URlProp.getPropertyValue();
									qr3URl = qr3URl.replace("#merchantId",
											String.valueOf(dmoOnboarding.getParentMerchantID()));
								}

								logger.info(" Updated Dynamic Qr Api ===============>"+ qr3URl);
								
								String ICICI_CPAYPUBLICCER_KEY = IciciDynamicQrCall.ICICI_CPAYPUBLICCER_KEY;
								String GETEPAY_ICICI_PRIVATE_KEY = IciciDynamicQrCall.GETEPAY_ICICI_PRIVATE_KEY;

//								properties = new Properties();
//								properties.setPropertykey(IciciDynamicQrCall.ICICI_PUBLIC_KEY_DYNAMIC_QR); // QR3 api public key
								String publicKeyValue = propMap.get(IciciDynamicQrCall.ICICI_PUBLIC_KEY_DYNAMIC_QR);
//								properties = new Properties();
//								properties.setPropertykey(IciciDynamicQrCall.ICICI_PRIVATE_KEY_DYNAMIC_QR); // QR3 api private key
								String privateKeyValue = propMap.get(IciciDynamicQrCall.ICICI_PRIVATE_KEY_DYNAMIC_QR);

								Map<String, String> propertiesMap = new HashMap<String, String>();
								propertiesMap.put(ICICI_CPAYPUBLICCER_KEY, publicKeyValue); // enc
								propertiesMap.put(GETEPAY_ICICI_PRIVATE_KEY, privateKeyValue); // dec

								IciciHybridRequest iciciHybridRequest = IciciCompositPay.hybridEncryption(propertiesMap,
										requestString, requestId);

								logger.info("Updated Dynamic Qr Encrypted Request ===============>"+ iciciHybridRequest);
								String encRequest = gson.toJson(iciciHybridRequest);

								String response = IciciDynamicQrCall.postApiQr3(qr3URl, encRequest);

								logger.info("Api Call Response===================================>" + response);
								if (response != null && !response.equals("")) {
									IciciHybridRequest iciciHybridResponse = new Gson().fromJson(response,
											IciciHybridRequest.class);
									String decResponse = IciciDynamicQrCall.hybridDeryption(propertiesMap,
											iciciHybridResponse);

									logger.info("Updated Dynamic Qr Encrtyped Response ==================>" + decResponse);

									IciciDynamicQr3Response qr3Response = objectMapper.readValue(decResponse,
											IciciDynamicQr3Response.class);
									logger.info("Updated Dynamic Qr Dec Response ==============>"+ qr3Response);

									if (qr3Response != null && qr3Response.getRefId() != null) {

										logger.info("If RefId is not null==========>" + qr3Response.getRefId());
										refId = qr3Response.getRefId();

										map.put("pa", vpaId); // VPA Id
										map.put("pn", merchant.getMerchantName()); // merchant name
										map.put("am", totalamount);
										map.put("tr", refId);

									} else {
										logger.info("qr3Response Dynamic Qr3 Api is null===============>");
										return null;
									}

								} else {
									logger.info("Dynamic Qr3 Api is null===============>");
									return null;
								}
							} catch (Exception e) {

								logger.info("Dynamic Qr Exception===============>");
								new GlobalExceptionHandler().customException(e);
								return null;
							}

						}

						else {
							map.put("pa", vpaId); // VPA Id
							map.put("pn", merchant.getMerchantName()); // merchant name
							map.put("am", totalamount);
							map.put("tr", refId);
						}
					}

					else {

						map.put("pa", vpaId); // VPA Id
						map.put("pn", merchant.getMerchantName()); // merchant name
						map.put("am", totalamount);
						map.put("tr", refId);
					}

				} else {
					String refIdPart1 = Util.PAYMENT_MODE_DYNAMIC_QR_REFID;
					String refIdPart2 = String.valueOf(paymentRequest.getTransactionId());
					int temp = (refIdPart1 + refIdPart2).length();
					if (temp < 13) {
						while (temp < 13) {
							refIdPart2 = "0" + refIdPart2;
							temp = (refIdPart1 + refIdPart2).length();
						}
					}
					refId = refIdPart1 + refIdPart2;
					String refIdPart3 = Util.ICICI_PAYMENT_MODE_DYNAMIC_QR_REFID;
					String refIdPart4 = String.valueOf(paymentRequest.getTransactionId());
					temp = (refIdPart3 + refIdPart4).length();
					if (temp < 13) {
						while (temp < 13) {
							refIdPart4 = "0" + refIdPart4;
							temp = (refIdPart3 + refIdPart4).length();

						}
					}
					String refId2 = refIdPart3 + refIdPart4;
					String vpa = "Getepay." + refId + "@icici";
					// String vpa = "Getepay.merchant59020@icici";
					Long id = Long.valueOf(refIdPart2);
					logger.info("<= testId =>" + paymentRequest);
					if (id <= 0) {
						return null;
					}
					map.put("pa", vpa); // VPA Id
					map.put("pn", merchant.getMerchantName()); // merchant name
					map.put("am", totalamount);
					map.put("tr", refId2);
				}

				if (totalAmount != null && !String.valueOf(totalAmount).equals("")) {
					map.put("mam", totalamount);
				}

				String url = "upi://pay?";
				url += map.entrySet().stream().map(x -> x.getKey() + "=" + x.getValue())
						.collect(Collectors.joining("&"));
				String qrImage = generateQr(refId, url);
				model.setQrImage(qrImage);
				model.setQrString(url);
				return model;

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		else {
			return null;
		}
		return null;
	}

	public String generateQr(String merchantCode, String urlEncode) {
		BitMatrix matrix;
		int qrCodewidth = 500;
		int qrCodeheight = 500;
		try {
			String qrLocation = Constants.QR_PATH;
			logger.info("urlEncode : "+ urlEncode);
			String filePath = merchantCode.trim() + ".png";
			String charset = "UTF-8"; // or "ISO-8859-1"

			@SuppressWarnings("rawtypes")
			Map hintMap = new HashMap();
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
			hintMap.put(EncodeHintType.MARGIN, 1);

			try {
				// returnFilePath = "DynamicQRCode" + File.separator + filePath;

				String sourcePath = qrLocation;
				File isDir = new File(sourcePath);
				if (!isDir.exists()) {
					new File(sourcePath).mkdirs();
				}
				filePath = sourcePath + File.separator + filePath;
				matrix = new MultiFormatWriter().encode(new String(urlEncode.getBytes(charset), charset),
						BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);
				MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath.lastIndexOf('.') + 1),
						new File(filePath));
//				/mnt/efs/lambda/pg/dynamicqrpath/
//				filePath = filePath.replace("/mnt/efs/lambda/pg/dynamicqrpath/", "/media/shared/lambda/pg/dynamicqrpath");
				return filePath;
			} catch (UnsupportedEncodingException e) {
				new GlobalExceptionHandler().customException(e);
			} catch (WriterException e) {
				new GlobalExceptionHandler().customException(e);
			} catch (IOException e) {
				new GlobalExceptionHandler().customException(e);
			}

			logger.info("filePath : "+ filePath);
			return filePath;

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public String generateAUDynamicQr(String vpa, String qrString, com.ftk.pg.requestvo.PaymentRequest paymentRequest) {

		String url = null;
		String urlEncode = null;
//		int qrCodewidth = 400;
//		int qrCodeheight = 400;
		try {
			url = qrString.replace("<![CDATA[", "").replace("&amp;", "").replace("]]", "");
//			urlEncode = URLEncoder.encode(url, "UTF-8").replace(" ", "%20");
			urlEncode = url;
			String merchantCode = vpa.replace(".augp@aubank", "");
			return generateQr(merchantCode, urlEncode);
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public void saveTransactionMapping(TransactionLog txnLog, IntermediateTransaction intermediateTransaction) {
		logger.info("----------Inside the save method of Transaction Mapping-------------");
		try {
			logger.info("ProcessorId===>" + txnLog.getTransactionId());
			logger.info("transactionid===>" + intermediateTransaction.getTransactionId());

			TransactionMappingPk transactionMappingPk = new TransactionMappingPk();
			transactionMappingPk.setProcessorId(txnLog.getTransactionId());
			transactionMappingPk.setTransactionId(intermediateTransaction.getTransactionId());

			logger.info("transactionMappingPk===>" + transactionMappingPk);
			TransactionMapping txnMapping = new TransactionMapping();
			txnMapping.setId(transactionMappingPk);
			txnMapping.setPiMid(intermediateTransaction.getMid());
			txnMapping.setPgMid(txnLog.getMerchantId());
			txnMapping.setTerminalId(
					intermediateTransaction.getUdf6() == null ? txnLog.getUdf9() : intermediateTransaction.getUdf6());
			txnMapping.setMerchantOrderNumber(intermediateTransaction.getOrderNumber());

			transactionMappingService.saveTransactionMapping(txnMapping);

			logger.info("TransactionMapping saved-------------" + txnMapping);

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}

	}
	
}
