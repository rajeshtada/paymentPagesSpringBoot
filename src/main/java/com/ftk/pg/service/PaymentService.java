//package com.ftk.pg.service;
//
//import java.io.StringReader;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Base64;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.Unmarshaller;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import com.ftk.pg.dto.MerchantDTO;
//import com.ftk.pg.dto.RequestWrapper;
//import com.ftk.pg.dto.ResponseBuilder;
//import com.ftk.pg.dto.ResponseWrapper;
//import com.ftk.pg.encryption.AesEncryption;
//import com.ftk.pg.exception.GlobalExceptionHandler;
//import com.ftk.pg.modal.Bank;
//import com.ftk.pg.modal.Merchant;
//import com.ftk.pg.modal.MerchantPartner;
//import com.ftk.pg.modal.MerchantProductIdDetails;
//import com.ftk.pg.modal.MerchantSetting;
//import com.ftk.pg.modal.ProcessorBank;
//import com.ftk.pg.modal.TransactionEssentials;
//import com.ftk.pg.modal.TransactionLog;
//import com.ftk.pg.modal.UIColorScheme;
//import com.ftk.pg.modal.UpiQrDetail;
//import com.ftk.pg.modal.User;
//import com.ftk.pg.modal.Wallet;
//import com.ftk.pg.pi.modal.IntermediateTransaction;
//import com.ftk.pg.pi.repo.IntermediateTransactionRepo;
//import com.ftk.pg.repo.BankRepo;
//import com.ftk.pg.repo.MerchantPartnerRepo;
//import com.ftk.pg.repo.MerchantProductIdDetailsRepo;
//import com.ftk.pg.repo.MerchantRepo;
//import com.ftk.pg.repo.MerchantSettingRepo;
//import com.ftk.pg.repo.ProcessorBankRepo;
//import com.ftk.pg.repo.TransactionEssentialsRepo;
//import com.ftk.pg.repo.TransactionLogRepo;
//import com.ftk.pg.repo.UIColorSchemeRepo;
//import com.ftk.pg.repo.UpiQrDetailRepo;
//import com.ftk.pg.repo.UserRepo;
//import com.ftk.pg.repo.WalletRepo;
//import com.ftk.pg.requestvo.API_PaymentRequest;
//import com.ftk.pg.requestvo.DynamicQrModel;
//import com.ftk.pg.requestvo.PaymentRequest;
//import com.ftk.pg.requestvo.PropertiesVo;
//import com.ftk.pg.requestvo.RequestProductsVo;
//import com.ftk.pg.responsevo.API_PaymentResponse;
//import com.ftk.pg.responsevo.PaymentResponse;
//import com.ftk.pg.util.CommissionModel;
//import com.ftk.pg.util.ConvenienceModel;
//import com.ftk.pg.util.Utilities;
//import com.pgcomponent.security.SignatureGenerator;
//
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//@Service
//public class PaymentService {
//
//	private static Logger logger = LogManager.getLogger(PaymentService.class);
//
//	private final PropertiesService propertiesService;
//
//	private final ApiService apiService;
//
//	private final MerchantRepo merchantRepo;
//
//	private final UpiQrDetailRepo upiQrDetailRepo;
//
//	private final UIColorSchemeRepo uiColorSchemeRepo;
//
//	private final MerchantPartnerRepo merchantPartnerRepo;
//
//	private final TransactionLogRepo transactionLogRepo;
//
//	private final EncryptionService encryptionService;
//
//	private final MerchantProductIdDetailsRepo merchantProductIdDetailsRepo;
//
//	private final UserRepo userRepo;
//
//	private final BankRepo bankRepo;
//
//	private final MerchantSettingRepo merchantSettingRepo;
//
//	private final ProcessorBankRepo processorBankRepo;
//
//	private final WalletRepo walletRepo;
//
//	private final MerchantCommisionService merchantCommisionService;
//
//	private final ConvenienceChragesService convenienceChragesService;
//
//	private final IntermediateTransactionRepo intermediateTransactionRepo;
//
//	private final TransactionEssentialsRepo transactionEssentialsRepo;
//
//	public ResponseEntity<ResponseWrapper<String>> payByVpaV2(API_PaymentRequest pgRequest, String token)
//			throws Exception {
//		logger.info("inside payByVpaV2 method : " + pgRequest);
//
//		API_PaymentResponse paymentResponse = new API_PaymentResponse();
//		Map<String, String> propMap = new HashMap<>();
//		PaymentResponse pgresponse = new PaymentResponse();
//		List<Bank> merchantBanks = new ArrayList<Bank>();
////		MerchantKeys merchantKeys = merchantKeysRepo.findByMidAndTerminalId(Long.parseLong(requestWrapper.getMid()),
////				requestWrapper.getTerminalId());
////		GcmPgEncryption gcmPgEncryption = new GcmPgEncryption(merchantKeys.getIv(), merchantKeys.getKey());
////		API_PaymentRequest pgRequest = EncryptionUtil.decryptdata(requestWrapper.getData(), merchantKeys,
////				API_PaymentRequest.class);
//
//		if (pgRequest.getUdf6() != null) {
//			pgRequest.setUdf6(pgRequest.getUdf6().replaceAll(" ", "+"));
//		}
//
//		String vpa = pgRequest.getMid();
//		User userDetails = apiService.getUserDetailByVpaId(vpa);
//		if (userDetails == null) {
//			pgresponse.setDescription("Invalid VPA");
//		} else {
//
//			List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
//			for (PropertiesVo property : PropertiesList) {
//				propMap.put(property.getPropertyKey(), property.getPropertyValue());
//			}
//
//			Merchant merchant = merchantRepo.findByMid(userDetails.getMid());
//			UpiQrDetail qrDetailgenre = upiQrDetailRepo.findByVpa(vpa);
//
//			MerchantPartner merchantPartner = new MerchantPartner();
//			UIColorScheme uiColorScheme = new UIColorScheme();
//			if (vpa != null && !vpa.equals("")) {
//				uiColorScheme = uiColorSchemeRepo.findByVpa(vpa);
//
//			}
//			if (uiColorScheme == null || uiColorScheme.getId() <= 0) {
//				merchantPartner = merchantPartnerRepo.findByMid(merchant.getMid());
//				if (merchantPartner != null) {
//					uiColorScheme = uiColorSchemeRepo.findByPartnerId(merchantPartner.getPartnerId());
//				}
//			}
//			String encyptedlogoPath = null;
//			if (uiColorScheme != null) {
//				if (uiColorScheme.getLogoPath() != null && !uiColorScheme.getLogoPath().equals("")) {
//					encyptedlogoPath = AesEncryption.encrypt(uiColorScheme.getLogoPath());
////					encyptedlogoPath = Base64.getEncoder().encodeToString(uiColorScheme.getLogoPath().getBytes("UTF-8"));
//				}
//			}
//
//			String merchantemailslist = propMap.get(Utilities.EMAIL_MERCHANTS);
//			if (merchantemailslist != null && !merchantemailslist.equalsIgnoreCase("")) {
//				String[] midArrays = merchantemailslist.split(",");
//				boolean isMidsPresent = false;
//				String mercId = String.valueOf(merchant.getMid());
//				for (String mid : midArrays) {
//					if (mid.equalsIgnoreCase(mercId)) {
//						isMidsPresent = true;
//						break;
//					}
//				}
//				if (isMidsPresent) {
//					paymentResponse.setMerchantemail(merchant.getEmail());
//				} else {
//					paymentResponse.setMerchantemail(null);
//				}
//			} else {
//				paymentResponse.setMerchantemail(null);
//			}
//
//			String merchantchargesShowList = propMap.get(Utilities.MERCHANT_CHARGES_ENABLE);
//			if (merchantchargesShowList != null && !merchantchargesShowList.equalsIgnoreCase("")) {
//				String[] midArrays = merchantchargesShowList.split(",");
//				boolean isMidsPresent = false;
//				String mercId = String.valueOf(merchant.getMid());
//				for (String mid : midArrays) {
//					if (mid.equalsIgnoreCase(mercId)) {
//						isMidsPresent = true;
//						break;
//					}
//				}
//				if (isMidsPresent) {
//					paymentResponse.setMerchantchanrgesShow(String.valueOf(merchant.getMid()));
//				} else {
//					paymentResponse.setMerchantchanrgesShow(null);
//				}
//			} else {
//				paymentResponse.setMerchantchanrgesShow(null);
//			}
//
////			String signature = SignatureGenerator.signatureGeneration(
////					new String[] { pgRequest.getCurrency(), pgRequest.getLogin(), pgRequest.getMerchantOrderNo(),
////							pgRequest.getMid(), pgRequest.getPaymentMode(), pgRequest.getProductType(),
////							pgRequest.getTxnAmount(), pgRequest.getUdf1(), pgRequest.getUdf2() },
////					pgRequest.getMid());
//
////			if (signature.equals(pgRequest.getSignature())) {
//			pgRequest.setMid(String.valueOf(merchant.getMid()));
//			pgRequest.setLogin(userDetails.getUsername());
////			String pgSignature = SignatureGenerator.signatureGeneration(
////					new String[] { pgRequest.getCurrency(), pgRequest.getLogin(), pgRequest.getMerchantOrderNo(),
////							pgRequest.getMid(), pgRequest.getPaymentMode(), pgRequest.getProductType(),
////							pgRequest.getTxnAmount(), pgRequest.getUdf1(), pgRequest.getUdf2() },
////					userDetails.getPassword());
////
////			pgRequest.setSignature(pgSignature);
////			Long mid = userDetails.getMid();
//
//			PaymentRequest paymentRequest = new PaymentRequest();
//			BeanUtils.copyProperties(pgRequest, paymentRequest);
//			paymentRequest.setAmt(pgRequest.getTxnAmount());
////			paymentRequest.setLogin(pgRequest.getLogin());
//			paymentRequest.setDate(pgRequest.getTxndatetime());
//			paymentRequest.setMerchantTxnId(pgRequest.getMerchantOrderNo());
////			paymentRequest.setPaymentMode(pgRequest.getPaymentMode());
////			paymentRequest.setProductType(pgRequest.getProductType());
////			paymentRequest.setRu(pgRequest.getRu());
//			paymentRequest.setTxncurr(pgRequest.getCurrency());
////			paymentRequest.setUdf1(pgRequest.getUdf1());
////			paymentRequest.setUdf2(pgRequest.getUdf2());
////			paymentRequest.setUdf3(pgRequest.getUdf3());
////			paymentRequest.setUdf4(pgRequest.getUdf4());
////			paymentRequest.setUdf5(pgRequest.getUdf5());
////			paymentRequest.setBankid(pgRequest.getBankId());
//			paymentRequest.setMobile(pgRequest.getUdf2());
//			paymentRequest.setName(pgRequest.getUdf1());
//
//			if (paymentRequest.getUdf5() != null && paymentRequest.getPaymentMode() != null
//					&& paymentRequest.getPaymentMode().contains("NEFT")) {
//				if (paymentRequest.getUdf5().contains("|")) {
//					String[] pr = paymentRequest.getUdf5().split("|");
//					if (pr != null && pr.length > 0) {
//						paymentRequest.setVan(pr[0]);
//					}
//				}
//			}
//
//			if (pgRequest.getTxnType() != null && pgRequest.getTxnType().equalsIgnoreCase("multi")) {
//				paymentRequest.setProductDetails(
//						new String(Base64.getDecoder().decode(pgRequest.getUdf6().replaceAll(" ", "+"))));
//			}
//
//			// TODO comment 06_may rajesh
////			TransactionLog log = transactionLogRepo.findByMerchanttxnid(paymentRequest.getMerchantTxnId());
//////			logger.info("Transaction id by Merchant Txn Id===================>" + log);
////			if (log != null && merchant.getForceUpdate() != null && Boolean.TRUE.equals(merchant.getForceUpdate())) {
//////				logger.info("Transaction Log is not null and Merchant Forced Update is true");
////				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(pgresponse, token), "error",
////						HttpStatus.BAD_REQUEST);
////			}
//
//			List<TransactionLog> byMerchanttxnid = transactionLogRepo
//					.findByMerchanttxnid(paymentRequest.getMerchantTxnId());
//
//			for (TransactionLog log : byMerchanttxnid) {
//
//				if (log != null && log.getTxnStatus().equalsIgnoreCase("SUCCESS")) {
//					return ResponseBuilder.buildResponse(encryptionService.encryptResponse(pgresponse, token),
//							"Payment link already processed", HttpStatus.BAD_REQUEST);
//				} else if (log != null) {
//					logger.info("Payment link retry and pgTxn mark as failed : " + log.getTransactionId());
//					log.setResponseCode("02");
//					log.setTxnStatus("FAILED");
//					log.setBankErrorMsg("Payment link retry and pgTxn mark as failed");
//					transactionLogRepo.save(log);
//				}
//			}
//
//			com.ftk.pg.dto.PaymentRequest paymentRequest2 = new com.ftk.pg.dto.PaymentRequest();
//			BeanUtils.copyProperties(paymentRequest, paymentRequest2);
//			paymentRequest2.setAmt(new BigDecimal(Double.valueOf(paymentRequest.getAmt())));
//			pgresponse = apiService.checkDetails(paymentRequest2, merchant);
//
//			TransactionEssentials transactionEssential = new TransactionEssentials();
//			IntermediateTransaction iTxnLog = intermediateTransactionRepo
//					.findByTransactionId(Long.valueOf(pgresponse.getMerchantTxnId()));
//			TransactionLog txnLog = transactionLogRepo.findById(Long.valueOf(pgresponse.getTransactionId())).get();
//			apiService.saveTransactionMapping(txnLog, iTxnLog);
//
//			// TPV Transaction
//
//			if (iTxnLog != null && iTxnLog.getUdf15() != null && !iTxnLog.getUdf15().equals("")
//					&& iTxnLog.getUdf15().contains("TPV")) {
//
//				logger.info("Inside TPV check=======================>");
//
//				apiService.saveTranasctionEssential(pgRequest, txnLog, iTxnLog);
//				transactionEssential = transactionEssentialsRepo
//						.findByTransactionId(Long.valueOf(txnLog.getTransactionId()));
//
//			}
//
//			// TODO comment 06_may rajesh
////			if (pgRequest.getTxnType() != null && (pgRequest.getTxnType().contains("single|TPV")
////					|| pgRequest.getTxnType().contains("multi|TPV"))) {
////				TransactionEssentials tessential = apiService.saveTranasctionEssential(pgRequest, log);
////			}
//
//			// TODO
//////              		
////				ProcessorIdAndPortalIdRequest processorIdAndPortalIdRequest = new ProcessorIdAndPortalIdRequest();
////				processorIdAndPortalIdRequest.setTransactionId(pgresponse.getMerchantTxnId());
////				processorIdAndPortalIdRequest.setProcessorId(String.valueOf(pgresponse.getTransactionId()));
////
////				callService.addprocessorIdInQueue(processorIdAndPortalIdRequest);
//
//			// multipart********
//			MerchantProductIdDetails merchantproductDetails = new MerchantProductIdDetails();
//
//			if (pgRequest != null && pgRequest.getTxnType().equalsIgnoreCase("multi")) {
//				RequestProductsVo products = parseRequestProducts(paymentRequest.getProductDetails());
////					logger.info("Inside Multi Condition==============================>");
////					logger.info("product Size ==============================>" + products.getProducts().size());
//				if (products.getProducts() != null && products.getProducts().size() == 1) {
//					String multiproductmids = propMap.get(Utilities.MULTIPRODUCTMIDS);
//					if (multiproductmids != null && !multiproductmids.equalsIgnoreCase("")) {
////							logger.info("multiproductmids Condition==============================>" + multiproductmids);
//						String[] midArrays = multiproductmids.split(",");
//						boolean isMidsPresent = false;
//						String mercId = String.valueOf(merchant.getMid());
//						for (String merchantId : midArrays) {
//							if (merchantId.equalsIgnoreCase(mercId)) {
//								isMidsPresent = true;
//								break;
//							} else {
//								isMidsPresent = false;
//							}
//						}
//						if (isMidsPresent) {
//							merchantproductDetails = merchantProductIdDetailsRepo.findByMerchantIdAndProductCode(
//									merchant.getMid(), products.getProducts().get(0).getCode());
//							if (merchantproductDetails != null) {
//								paymentResponse.setMerchantProductIdDetails(merchantproductDetails);
//							}
//						}
//					}
//				} else {
//					paymentResponse.setMerchantProductIdDetails(merchantproductDetails);
//				}
//			}
//			if (pgresponse != null && pgresponse.getDescription().equalsIgnoreCase("Merchant Txn Id already Exist")
//					&& pgresponse.getResponseCode().equalsIgnoreCase("01")) {
//				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(pgresponse, token), "error",
//						HttpStatus.BAD_REQUEST);
//			}
//
//			if (pgresponse.isResult()) {
//				merchant = merchantRepo.findByMid(pgresponse.getmId());
//				String enabledPayModes = "";
//				if (merchant != null) {
//					enabledPayModes = pgRequest.getPaymentMode();
//				}
//				CommissionModel commissionModel = new CommissionModel();
//				if (enabledPayModes == null || enabledPayModes.equals("") || enabledPayModes.equals("ALL")) {
//					enabledPayModes = apiService.getEnabledPaymode(pgRequest.getLogin(), pgRequest.getProductType());
//					commissionModel.setZeroCharges(new BigDecimal(pgRequest.getTxnAmount()));
//					merchantBanks = bankRepo.findAll();
//				} else {
//
//					String filteredPaymentModes = "";
//
//					String[] requestPaymentModeArray = enabledPayModes.split(",");
//
//					String configuredPaymentModes = apiService.getEnabledPaymode(pgRequest.getLogin(),
//							pgRequest.getProductType());
//					String[] configuredPaymentModesArray = configuredPaymentModes.split(",");
//					for (int i = 0; i < requestPaymentModeArray.length; i++) {
//						String rpm = requestPaymentModeArray[i];
//						for (int j = 0; j < configuredPaymentModesArray.length; j++) {
//							String pm = configuredPaymentModesArray[j];
//							if (rpm.equals(pm)) {
//								if (filteredPaymentModes != null && !filteredPaymentModes.equals("")) {
//									filteredPaymentModes = filteredPaymentModes + ",";
//								}
//								filteredPaymentModes = filteredPaymentModes + pm;
//							}
//						}
//					}
//					enabledPayModes = filteredPaymentModes;
//					commissionModel.setZeroCharges(new BigDecimal(pgRequest.getTxnAmount()));
//
//				}
//
//				if (enabledPayModes.toUpperCase().contains("NB")) {
//
//					if (propMap.get(Utilities.ENABLE_BANK_LIST) != null
//							&& propMap.get(Utilities.ENABLE_BANK_LIST).equalsIgnoreCase("true")) {
//
//						MerchantSetting setting = merchantSettingRepo.findByMerchantIdAndPaymentModeAndProcessor(merchant.getMid(),
//								"NB", "PAYNETZ");
//
//						if (setting != null) {
//							List<ProcessorBank> processorBankList = processorBankRepo.findByProcessorAndMIdAndStatus(
//									setting.getProcessor(), setting.getMerchantId(), true);
//
//							if (processorBankList.size() > 0) {
//								Long[] bankIdArray = processorBankList.stream().map(ProcessorBank::getBankId)
//										.toArray(Long[]::new);
//								logger.info("bank Array Id=======================================>" + bankIdArray);
////								merchantBanks = bankRepo.findByStatusAndId(true, bankIdArray);
//								List<Long> bankIdList = Arrays.asList(bankIdArray);
//								merchantBanks = bankRepo.findByStatusAndIdIn(true, bankIdList);
//								logger.info("Merchant Banks lists================================>" + merchantBanks);
//							} else {
//
//								if (transactionEssential != null && transactionEssential.getUdf57() != null
//										&& transactionEssential.getUdf57().contains("TPV")) {
//									List<Bank> tpvEnalbeBankEnable = bankRepo.findByTpvEnable(true);
//									merchantBanks = new ArrayList<Bank>();
//									merchantBanks.addAll(tpvEnalbeBankEnable);
//								} else {
//									merchantBanks = bankRepo.findAll();
//								}
//
//							}
//						} else {
//
//							if (transactionEssential != null && transactionEssential.getUdf57() != null
//									&& transactionEssential.getUdf57().contains("TPV")) {
//								List<Bank> tpvEnalbeBankEnable = bankRepo.findByTpvEnable(true);
//								merchantBanks = new ArrayList<Bank>();
//								merchantBanks.addAll(tpvEnalbeBankEnable);
//							} else {
//								merchantBanks = bankRepo.findAll();
//							}
//						}
//
//					} else {
//
//						if (transactionEssential != null && transactionEssential.getUdf57() != null
//								&& transactionEssential.getUdf57().contains("TPV")) {
//							List<Bank> tpvEnalbeBankEnable = bankRepo.findByTpvEnable(true);
//							merchantBanks = new ArrayList<Bank>();
//							merchantBanks.addAll(tpvEnalbeBankEnable);
//						} else {
//							merchantBanks = bankRepo.findAll();
//						}
//					}
//				}
//
//				List<Wallet> merchantWallets = new ArrayList<>();
//				if (enabledPayModes.toUpperCase().contains("WALLET")) {
//					merchantWallets = walletRepo.findAll();
//				}
//
//				pgRequest.setTransactionId(pgresponse.getTransactionId());
//				paymentRequest.setTransactionId(pgresponse.getTransactionId());
//				if (enabledPayModes.toUpperCase().contains("DYNAMICQR")
//						|| enabledPayModes.toUpperCase().contains("UPI")) {
//					UpiQrDetail qrDetail = upiQrDetailRepo.findByVpaAndEnable(vpa, true);
//					if (qrDetail != null && qrDetail.getVpa() != null && !qrDetail.getVpa().equals("")) {
//
//						String qr = qrDetail.getVpa();
//						CommissionModel cmodel = merchantCommisionService.getCommisionAmountModel(merchant.getMid(), "DYNAMICQR",
//								Double.parseDouble(pgRequest.getTxnAmount()), "IPG", "");
//						ConvenienceModel conmodel = convenienceChragesService.getConvenienceChargesModel(
//								merchant.getMid(), "DYNAMICQR", Double.parseDouble(pgRequest.getTxnAmount()), null);
//						DynamicQrModel qrModel = apiService.getDynamicQr(merchant, cmodel, conmodel, paymentRequest,
//								qr);
//
//						if (qrModel != null) {
//							String qrImage = qrModel.getQrImage();
//							String qrstring = qrModel.getQrString();
//							paymentResponse.setQrpath(Base64.getEncoder().encodeToString(qrImage.getBytes("UTF-8")));
//							paymentResponse.setQrstring(qrstring);
//						} else {
//							enabledPayModes = enabledPayModes.replace("DYNAMICQR", "").replace(",,", ",");
//						}
//
//					} else {
//						String qrImage = null;
//						String qrstring = null;
//						paymentResponse.setQrpath(qrImage);
//						paymentResponse.setQrstring(qrstring);
//						enabledPayModes = enabledPayModes.replace("DYNAMICQR", "").replace(",,", ",");
//					}
//				}
//
//				paymentResponse.setPaymentRequest(paymentRequest);
//				paymentResponse.setCommissionModel(commissionModel);
//				paymentResponse.setEnabledPayModes(enabledPayModes);
//				paymentResponse.setMerchant(toDTO(merchant));
//				paymentResponse.setMonths(Utilities.getMonths());
//				paymentResponse.setYears(Utilities.getYears());
//				paymentResponse.setBanks(merchantBanks);
//				paymentResponse.setWallets(merchantWallets);
//				paymentResponse.setUpiQrDetail(qrDetailgenre);
//				
//				if (transactionEssential != null && transactionEssential.getUdf57() != null
//						&& transactionEssential.getUdf57().contains("TPV")) {
//					paymentResponse.setTpv("TPV");
//				} else {
//					paymentResponse.setTpv(null);
//				}
//
//				if (uiColorScheme != null) {
//					if (encyptedlogoPath != null) {
//						uiColorScheme.setLogoPath(encyptedlogoPath);
//					} else {
//						uiColorScheme.setLogoPath(null);
//					}
//					paymentResponse.setUIColorScheme(uiColorScheme);
//				}
//
//				paymentResponse.setTransactionId("" + pgRequest.getTransactionId());
//				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(paymentResponse, token),
//						"SUCCESS", HttpStatus.OK);
//			}
//		}
//		
//		return ResponseBuilder.buildResponse(encryptionService.encryptResponse(pgresponse, token), "FAILED",
//				HttpStatus.BAD_REQUEST);
//	}
//
//	private MerchantDTO toDTO(Merchant merchant) {
//		if (merchant == null) {
//			return null;
//		}
//
//		MerchantDTO merchantDTO = new MerchantDTO();
//
//		merchantDTO.setMid(merchant.getMid());
//		merchantDTO.setMerchantName(merchant.getMerchantName());
//		merchantDTO.setEmail(merchant.getEmail());
//		merchantDTO.setMobileNumber(merchant.getMobileNumber());
//		merchantDTO.setAddress(merchant.getAddress());
//		merchantDTO.setCity(merchant.getCity());
//		merchantDTO.setState(merchant.getState());
//		merchantDTO.setPincode(merchant.getPincode());
//		merchantDTO.setBusinessCategory(merchant.getBusinessCategory());
////		merchantDTO.setAccountNumber(merchant.getAccountNumber());
////		merchantDTO.setIfscCode(merchant.getIfscCode());
////		merchantDTO.setBankName(merchant.getBankName());
////		merchantDTO.setCreatedDate(merchant.getCreatedDate());
////		merchantDTO.setModifiedDate(merchant.getModifiedDate());
//
//		return merchantDTO;
//	}
//
//	public static final RequestProductsVo parseRequestProducts(String request) {
//		try {
////			String productsString = new String(Base64.decodeBase64(request));
////			if(productsString.isEmpty()) {
////				return null;
////			}
//			JAXBContext jaxbContext = JAXBContext.newInstance(RequestProductsVo.class);
////			logger.info("jaxbContext ==> " + jaxbContext);
//			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
////			logger.info("jaxbUnmarshaller ==> " + jaxbUnmarshaller);
//			RequestProductsVo products = (RequestProductsVo) jaxbUnmarshaller
//					.unmarshal(new StringReader(request.trim()));
//			return products;
//		} catch (Exception e) {
//			new GlobalExceptionHandler().customException(e);
//
//		}
//		return null;
//	}
//
//}
