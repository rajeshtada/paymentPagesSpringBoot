package com.ftk.pg.service;

import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ftk.pg.dto.MerchantDTO;
//import com.ftk.pg.dto.PaymentRequest;
import com.ftk.pg.dto.RequestWrapper;
import com.ftk.pg.dto.ResponseBuilder;
import com.ftk.pg.dto.ResponseWrapper;
import com.ftk.pg.encryption.AesEncryption;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.Bank;
import com.ftk.pg.modal.Merchant;
import com.ftk.pg.modal.MerchantCommision;
import com.ftk.pg.modal.MerchantPartner;
import com.ftk.pg.modal.MerchantProductIdDetails;
import com.ftk.pg.modal.MerchantSetting;
import com.ftk.pg.modal.ProcessorBank;
import com.ftk.pg.modal.TransactionEssentials;
import com.ftk.pg.modal.TransactionLog;
import com.ftk.pg.modal.UIColorScheme;
import com.ftk.pg.modal.UpiQrDetail;
import com.ftk.pg.modal.User;
import com.ftk.pg.modal.Wallet;
import com.ftk.pg.pi.modal.IntermediateTransaction;
import com.ftk.pg.pi.modal.Invoice;
import com.ftk.pg.pi.repo.IntermediateTransactionRepo;
import com.ftk.pg.pi.repo.InvoiceRepo;
import com.ftk.pg.pi.repo.MerchantKeysRepo;
import com.ftk.pg.pi.service.AdvancePropertiesService;
import com.ftk.pg.repo.BankRepo;
import com.ftk.pg.repo.MerchantPartnerRepo;
import com.ftk.pg.repo.MerchantProductIdDetailsRepo;
import com.ftk.pg.repo.MerchantRepo;
import com.ftk.pg.repo.MerchantSettingRepo;
import com.ftk.pg.repo.ProcessorBankRepo;
import com.ftk.pg.repo.TransactionEssentialsRepo;
import com.ftk.pg.repo.TransactionLogRepo;
import com.ftk.pg.repo.UIColorSchemeRepo;
import com.ftk.pg.repo.UpiQrDetailRepo;
import com.ftk.pg.repo.UserRepo;
import com.ftk.pg.repo.WalletRepo;
import com.ftk.pg.requestvo.API_PaymentRequest;
import com.ftk.pg.requestvo.DynamicQrModel;
import com.ftk.pg.requestvo.PaymentRequest;
import com.ftk.pg.requestvo.PropertiesVo;
import com.ftk.pg.requestvo.RequestProductsVo;
import com.ftk.pg.requestvo.TokenRequest;
import com.ftk.pg.responsevo.API_PaymentResponse;
import com.ftk.pg.responsevo.PaymentResponse;
import com.ftk.pg.util.CommissionModel;
import com.ftk.pg.util.ConvenienceModel;
import com.ftk.pg.util.EncryptionUtil;
import com.ftk.pg.util.PGUtility;
import com.ftk.pg.util.Utilities;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PaymentInitiationService {

	static Logger logger = LogManager.getLogger(PaymentInitiationService.class);

	private final InvoiceRepo invoiceRepo;

	private final AdvancePropertiesService advancePropertiesService;

	private final PropertiesService propertiesService;

	private final ApiService apiService;

	private final MerchantRepo merchantRepo;

	private final UpiQrDetailRepo upiQrDetailRepo;

	private final UIColorSchemeRepo uiColorSchemeRepo;

	private final MerchantPartnerRepo merchantPartnerRepo;

	private final TransactionLogRepo transactionLogRepo;

	private final EncryptionService encryptionService;

	private final MerchantProductIdDetailsRepo merchantProductIdDetailsRepo;

	private final UserRepo userRepo;

	private final BankRepo bankRepo;

	private final MerchantSettingRepo merchantSettingRepo;

	private final ProcessorBankRepo processorBankRepo;

	private final WalletRepo walletRepo;

	private final MerchantCommisionService merchantCommisionService;

	private final ConvenienceChragesService convenienceChragesService;

	private final IntermediateTransactionRepo intermediateTransactionRepo;

	private final TransactionEssentialsRepo transactionEssentialsRepo;

	public ResponseEntity<ResponseWrapper<String>> pgPayment(RequestWrapper requestWrapper, String token)
			throws Exception {

		logger.info("inside pgPayment method");
		TokenRequest tokenRequest = EncryptionUtil.decryptdata(String.valueOf(requestWrapper.getData()), token,
				TokenRequest.class);

		API_PaymentRequest api_paymentRequest = new API_PaymentRequest();
		ResponseEntity<ResponseWrapper<String>> response = doPayment(tokenRequest, api_paymentRequest);
		logger.info("doPayment checked : " + response);
		if (response != null) {
			return response;
		}
		ResponseEntity<ResponseWrapper<String>> payByVpaV2 = payByVpaV2(api_paymentRequest, token);
		return payByVpaV2;

	}

	private ResponseEntity<ResponseWrapper<String>> doPayment(TokenRequest tokenRequest,
			API_PaymentRequest api_paymentRequest) {
		try {

			Invoice invoice = invoiceRepo.findByToken(tokenRequest.getToken());
			if (invoice == null || (invoice.getPayStatus() != null && invoice.getPayStatus())) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
						new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Invalid Payment Url", null));
			}
			logger.info("Invoice  => " + invoice.getId() + " : " + invoice.getExpiryDate());
			LocalDateTime currentLocalDatetime = LocalDateTime.now();
			Date localDate = Date.from(currentLocalDatetime.atZone(ZoneId.systemDefault()).toInstant());
			if (!localDate.before(invoice.getExpiryDate())) {

				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
						new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Payment Link Expired", null));
			}

			// if (localDate.before(invoice.getExpiryDate()) ||
			// localDate.equals(invoice.getExpiryDate())) {

			IntermediateTransaction transaction = intermediateTransactionRepo.findByTransactionId(invoice.getTxnId());
			if (transaction == null || transaction.getTransactionId() == null || transaction.getTransactionId() <= 0) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
						new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Invalid Payment Url", null));
			}
			logger.info("IntermediateTransaction txnId => " + transaction.getTransactionId() + " : "
					+ transaction.getStatus());

			if (transaction.getStatus() != null && transaction.getStatus().equalsIgnoreCase("SUCCESS")) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(
						HttpStatus.INTERNAL_SERVER_ERROR.value(), "Payment link has processed", null));
			}

			if (invoice.getUdf8() != null && invoice.getUdf8().trim() != "") {
				try {
					api_paymentRequest.setTxnType(invoice.getUdf8().split("\\|")[0]);
					api_paymentRequest.setUdf6(invoice.getUdf8().split("\\|")[1]);
				} catch (Exception e) {
					api_paymentRequest.setTxnType(invoice.getUdf8().split("\\|")[0]);
				}
			}
			api_paymentRequest.setCurrency("INR");
			api_paymentRequest.setProductType("IPG");
			api_paymentRequest.setPaymentMode("ALL");

			if (invoice.getPaymentMode() == null || invoice.getPaymentMode().equals("")) {
				invoice.setPaymentMode(transaction.getPaymentType());
				invoiceRepo.save(invoice);
				logger.info("paymentMode updated in invoice => " + invoice.getPaymentMode());
			}
			if (invoice.getPaymentMode() != null && !invoice.getPaymentMode().equals("")) {
				api_paymentRequest.setPaymentMode(invoice.getPaymentMode());
			} else if (transaction.getPaymentType() != null && !transaction.getPaymentType().equals("")) {
				api_paymentRequest.setPaymentMode(transaction.getPaymentType());
			} else {
				api_paymentRequest.setPaymentMode("ALL");
			}

			logger.info("paymentMode => " + api_paymentRequest.getPaymentMode());
			api_paymentRequest.setTxnAmount(transaction.getTxnAmount().toString());
			api_paymentRequest.setTxndatetime(LocalDate.now().toString());
			api_paymentRequest.setMerchantOrderNo(transaction.getTransactionId().toString());
			api_paymentRequest.setUdf1(transaction.getUdf3()); // Name
			api_paymentRequest.setUdf2(transaction.getUdf1()); // Mobile
			api_paymentRequest.setUdf3(transaction.getUdf2()); // Email

			if (api_paymentRequest.getUdf1() == null || api_paymentRequest.getUdf1().equals("")) {
				api_paymentRequest.setUdf1("Test");
			}
			if (api_paymentRequest.getUdf2() == null || api_paymentRequest.getUdf2().equals("")) {
				api_paymentRequest.setUdf2("9999999999");
			}
			if (api_paymentRequest.getUdf3() == null || api_paymentRequest.getUdf3().equals("")) {
				api_paymentRequest.setUdf3("Test");
			}

			api_paymentRequest.setUdf4(transaction.getOrderNumber());
			api_paymentRequest.setUdf5("");

			if (transaction.getUdf5() != null && !transaction.getUdf5().trim().equals("")) {
				api_paymentRequest.setUdf5(transaction.getUdf5());
			}

			api_paymentRequest.setMid(invoice.getVpa());
			api_paymentRequest.setLogin(invoice.getVpa());
			PropertiesVo properties = advancePropertiesService.findByPropertyKey("GETEPAY_PGDYNAMIC_RURL_KEY_V2");
			api_paymentRequest.setRu(properties.getPropertyValue());
			// } else {
			// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
			// new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Payment Link
			// Expired", null));
			// }
			return null;

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something Went Wrong", null));
		}

	}

	public ResponseEntity<ResponseWrapper<String>> payByVpaV2(API_PaymentRequest pgRequest, String token)
			throws Exception {
		logger.info("inside payByVpaV2 method : " + pgRequest);

		API_PaymentResponse paymentResponse = new API_PaymentResponse();
		Map<String, String> propMap = new HashMap<>();
		PaymentResponse pgresponse = new PaymentResponse();
		List<Bank> merchantBanks = new ArrayList<Bank>();

		if (pgRequest.getUdf6() != null) {
			pgRequest.setUdf6(pgRequest.getUdf6().replaceAll(" ", "+"));
		}

		String vpa = pgRequest.getMid();
		User userDetails = apiService.getUserDetailByVpaId(vpa);
		if (userDetails == null) {
			pgresponse.setDescription("Invalid VPA");
		} else {

			List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
			for (PropertiesVo property : PropertiesList) {
				propMap.put(property.getPropertyKey(), property.getPropertyValue());
			}

			Merchant merchant = merchantRepo.findByMid(userDetails.getMid());
			UpiQrDetail qrDetailgenre = upiQrDetailRepo.findByVpa(vpa);

			MerchantPartner merchantPartner = new MerchantPartner();
			UIColorScheme uiColorScheme = new UIColorScheme();
			if (vpa != null && !vpa.equals("")) {
				uiColorScheme = uiColorSchemeRepo.findByVpa(vpa);

			}
			if (uiColorScheme == null || uiColorScheme.getId() <= 0) {
				merchantPartner = merchantPartnerRepo.findByMid(merchant.getMid());
				if (merchantPartner != null) {
					uiColorScheme = uiColorSchemeRepo.findByPartnerId(merchantPartner.getPartnerId());
				}
			}
			String encyptedlogoPath = null;
			if (uiColorScheme != null) {
				if (uiColorScheme.getLogoPath() != null && !uiColorScheme.getLogoPath().equals("")) {
					encyptedlogoPath = AesEncryption.encrypt(uiColorScheme.getLogoPath());
//					encyptedlogoPath = Base64.getEncoder().encodeToString(uiColorScheme.getLogoPath().getBytes("UTF-8"));
				}
			}

			String merchantemailslist = propMap.get(Utilities.EMAIL_MERCHANTS);
			if (merchantemailslist != null && !merchantemailslist.equalsIgnoreCase("")) {
				String[] midArrays = merchantemailslist.split(",");
				boolean isMidsPresent = false;
				String mercId = String.valueOf(merchant.getMid());
				for (String mid : midArrays) {
					if (mid.equalsIgnoreCase(mercId)) {
						isMidsPresent = true;
						break;
					}
				}
				if (isMidsPresent) {
					paymentResponse.setMerchantemail(merchant.getEmail());
				} else {
					paymentResponse.setMerchantemail(null);
				}
			} else {
				paymentResponse.setMerchantemail(null);
			}

			String merchantchargesShowList = propMap.get(Utilities.MERCHANT_CHARGES_ENABLE);
			if (merchantchargesShowList != null && !merchantchargesShowList.equalsIgnoreCase("")) {
				String[] midArrays = merchantchargesShowList.split(",");
				boolean isMidsPresent = false;
				String mercId = String.valueOf(merchant.getMid());
				for (String mid : midArrays) {
					if (mid.equalsIgnoreCase(mercId)) {
						isMidsPresent = true;
						break;
					}
				}
				if (isMidsPresent) {
					paymentResponse.setMerchantchanrgesShow(String.valueOf(merchant.getMid()));
				} else {
					paymentResponse.setMerchantchanrgesShow(null);
				}
			} else {
				paymentResponse.setMerchantchanrgesShow(null);
			}

			pgRequest.setMid(String.valueOf(merchant.getMid()));
			pgRequest.setLogin(userDetails.getUsername());

			PaymentRequest paymentRequest = new PaymentRequest();
			BeanUtils.copyProperties(pgRequest, paymentRequest);
			paymentRequest.setAmt(pgRequest.getTxnAmount());
//			paymentRequest.setLogin(pgRequest.getLogin());
			paymentRequest.setDate(pgRequest.getTxndatetime());
			paymentRequest.setMerchantTxnId(pgRequest.getMerchantOrderNo());
//			paymentRequest.setPaymentMode(pgRequest.getPaymentMode());
//			paymentRequest.setProductType(pgRequest.getProductType());
//			paymentRequest.setRu(pgRequest.getRu());
			paymentRequest.setTxncurr(pgRequest.getCurrency());
//			paymentRequest.setUdf1(pgRequest.getUdf1());
//			paymentRequest.setUdf2(pgRequest.getUdf2());
//			paymentRequest.setUdf3(pgRequest.getUdf3());
//			paymentRequest.setUdf4(pgRequest.getUdf4());
//			paymentRequest.setUdf5(pgRequest.getUdf5());
//			paymentRequest.setBankid(pgRequest.getBankId());
			paymentRequest.setMobile(pgRequest.getUdf2());
			paymentRequest.setName(pgRequest.getUdf1());

			if (paymentRequest.getUdf5() != null && paymentRequest.getPaymentMode() != null
					&& paymentRequest.getPaymentMode().contains("NEFT")) {
				if (paymentRequest.getUdf5().contains("|")) {
					String[] pr = paymentRequest.getUdf5().split("|");
					if (pr != null && pr.length > 0) {
						paymentRequest.setVan(pr[0]);
					}
				}
			}

			if (pgRequest.getTxnType() != null && pgRequest.getTxnType().equalsIgnoreCase("multi")) {
				paymentRequest.setProductDetails(
						new String(Base64.getDecoder().decode(pgRequest.getUdf6().replaceAll(" ", "+"))));
			}

			// TODO comment 06_may rajesh
//			TransactionLog log = transactionLogRepo.findByMerchanttxnid(paymentRequest.getMerchantTxnId());
////			logger.info("Transaction id by Merchant Txn Id===================>" + log);
//			if (log != null && merchant.getForceUpdate() != null && Boolean.TRUE.equals(merchant.getForceUpdate())) {
////				logger.info("Transaction Log is not null and Merchant Forced Update is true");
//				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(pgresponse, token), "error",
//						HttpStatus.BAD_REQUEST);
//			}

			List<TransactionLog> byMerchanttxnid = transactionLogRepo
					.findByMerchanttxnid(paymentRequest.getMerchantTxnId());

			for (TransactionLog log : byMerchanttxnid) {

				if (log != null && log.getTxnStatus().equalsIgnoreCase("SUCCESS")) {
					return ResponseBuilder.buildResponse(encryptionService.encryptResponse(pgresponse, token),
							"Payment link already processed", HttpStatus.BAD_REQUEST);
				}  else if (log != null && !log.getTxnStatus().toLowerCase().contains("FAIL")) {
					logger.info("Payment link retry and pgTxn mark as failed : " + log.getTransactionId());
					log.setResponseCode("02");
					log.setTxnStatus("FAILED");
					log.setBankErrorMsg("Payment link retry and pgTxn mark as failed");
					transactionLogRepo.save(log);
				}
			}

			com.ftk.pg.dto.PaymentRequest paymentRequest2 = new com.ftk.pg.dto.PaymentRequest();
			BeanUtils.copyProperties(paymentRequest, paymentRequest2);
			paymentRequest2.setAmt(new BigDecimal(Double.valueOf(paymentRequest.getAmt())));
			pgresponse = apiService.checkDetails(paymentRequest2, merchant);

			TransactionEssentials transactionEssential = new TransactionEssentials();
			IntermediateTransaction iTxnLog = intermediateTransactionRepo
					.findByTransactionId(Long.valueOf(pgresponse.getMerchantTxnId()));
			TransactionLog txnLog = transactionLogRepo.findById(Long.valueOf(pgresponse.getTransactionId())).get();
			iTxnLog.setProcessorId(txnLog.getTransactionId());
			intermediateTransactionRepo.save(iTxnLog);
			apiService.saveTransactionMapping(txnLog, iTxnLog);

			// TPV Transaction

			if (iTxnLog != null && iTxnLog.getUdf15() != null && !iTxnLog.getUdf15().equals("")
					&& iTxnLog.getUdf15().contains("TPV")) {

				logger.info("Inside TPV check=======================>");

				apiService.saveTranasctionEssential(pgRequest, txnLog, iTxnLog);
				transactionEssential = transactionEssentialsRepo
						.findByTransactionId(Long.valueOf(txnLog.getTransactionId()));

			}

			// TODO comment 06_may rajesh
//			if (pgRequest.getTxnType() != null && (pgRequest.getTxnType().contains("single|TPV")
//					|| pgRequest.getTxnType().contains("multi|TPV"))) {
//				TransactionEssentials tessential = apiService.saveTranasctionEssential(pgRequest, log);
//			}

			// TODO
////              		
//				ProcessorIdAndPortalIdRequest processorIdAndPortalIdRequest = new ProcessorIdAndPortalIdRequest();
//				processorIdAndPortalIdRequest.setTransactionId(pgresponse.getMerchantTxnId());
//				processorIdAndPortalIdRequest.setProcessorId(String.valueOf(pgresponse.getTransactionId()));
//				callService.addprocessorIdInQueue(processorIdAndPortalIdRequest);

			// multipart********
			MerchantProductIdDetails merchantproductDetails = new MerchantProductIdDetails();

			if (pgRequest != null && pgRequest.getTxnType().equalsIgnoreCase("multi")) {
				RequestProductsVo products = PGUtility.parseRequestProducts(paymentRequest.getProductDetails());
				if (products.getProducts() != null && products.getProducts().size() == 1) {
					String multiproductmids = propMap.get(Utilities.MULTIPRODUCTMIDS);
					if (multiproductmids != null && !multiproductmids.equalsIgnoreCase("")) {
						String[] midArrays = multiproductmids.split(",");
						boolean isMidsPresent = false;
						String mercId = String.valueOf(merchant.getMid());
						for (String merchantId : midArrays) {
							if (merchantId.equalsIgnoreCase(mercId)) {
								isMidsPresent = true;
								break;
							} else {
								isMidsPresent = false;
							}
						}
						if (isMidsPresent) {
							merchantproductDetails = merchantProductIdDetailsRepo.findByMerchantIdAndProductCode(
									merchant.getMid(), products.getProducts().get(0).getCode());
							if (merchantproductDetails != null) {
								paymentResponse.setMerchantProductIdDetails(merchantproductDetails);
							}
						}
					}
				} else {
					paymentResponse.setMerchantProductIdDetails(merchantproductDetails);
				}
			}
			if (pgresponse != null && pgresponse.getDescription().equalsIgnoreCase("Merchant Txn Id already Exist")
					&& pgresponse.getResponseCode().equalsIgnoreCase("01")) {
				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(pgresponse, token), "error",
						HttpStatus.BAD_REQUEST);
			}

			if (pgresponse.isResult()) {
				merchant = merchantRepo.findByMid(pgresponse.getmId());
				String enabledPayModes = "";
				if (merchant != null) {
					enabledPayModes = pgRequest.getPaymentMode();
				}
				CommissionModel commissionModel = new CommissionModel();
				if (enabledPayModes == null || enabledPayModes.equals("") || enabledPayModes.equals("ALL")) {
					enabledPayModes = apiService.getEnabledPaymode(pgRequest.getLogin(), pgRequest.getProductType());
					commissionModel.setZeroCharges(new BigDecimal(pgRequest.getTxnAmount()));
					merchantBanks = bankRepo.findAll();
				} else {

					String filteredPaymentModes = "";

					String[] requestPaymentModeArray = enabledPayModes.split(",");

					String configuredPaymentModes = apiService.getEnabledPaymode(pgRequest.getLogin(),
							pgRequest.getProductType());
					String[] configuredPaymentModesArray = configuredPaymentModes.split(",");
					for (int i = 0; i < requestPaymentModeArray.length; i++) {
						String rpm = requestPaymentModeArray[i];
						for (int j = 0; j < configuredPaymentModesArray.length; j++) {
							String pm = configuredPaymentModesArray[j];
							if (rpm.equals(pm)) {
								if (filteredPaymentModes != null && !filteredPaymentModes.equals("")) {
									filteredPaymentModes = filteredPaymentModes + ",";
								}
								filteredPaymentModes = filteredPaymentModes + pm;
							}
						}
					}
					enabledPayModes = filteredPaymentModes;
					commissionModel.setZeroCharges(new BigDecimal(pgRequest.getTxnAmount()));

				}

				if (enabledPayModes.toUpperCase().contains("NB")) {

					if (propMap.get(Utilities.ENABLE_BANK_LIST) != null
							&& propMap.get(Utilities.ENABLE_BANK_LIST).equalsIgnoreCase("true")) {

						MerchantSetting setting = merchantSettingRepo
								.findByMerchantIdAndPaymentModeAndProcessor(merchant.getMid(), "NB", "PAYNETZ");

						if (setting != null) {
							List<ProcessorBank> processorBankList = processorBankRepo.findByProcessorAndMIdAndStatus(
									setting.getProcessor(), setting.getMerchantId(), true);

							if (processorBankList.size() > 0) {
								Long[] bankIdArray = processorBankList.stream().map(ProcessorBank::getBankId)
										.toArray(Long[]::new);
								logger.info("bank Array Id=======================================>" + bankIdArray);
//								merchantBanks = bankRepo.findByStatusAndId(true, bankIdArray);
								List<Long> bankIdList = Arrays.asList(bankIdArray);
								merchantBanks = bankRepo.findByStatusAndIdIn(true, bankIdList);
								logger.info("Merchant Banks lists================================>" + merchantBanks);
							} else {

								if (transactionEssential != null && transactionEssential.getUdf57() != null
										&& transactionEssential.getUdf57().contains("TPV")) {
									List<Bank> tpvEnalbeBankEnable = bankRepo.findByTpvEnable(true);
									merchantBanks = new ArrayList<Bank>();
									merchantBanks.addAll(tpvEnalbeBankEnable);
								} else {
									merchantBanks = bankRepo.findAll();
								}

							}
						} else {

							if (transactionEssential != null && transactionEssential.getUdf57() != null
									&& transactionEssential.getUdf57().contains("TPV")) {
								List<Bank> tpvEnalbeBankEnable = bankRepo.findByTpvEnable(true);
								merchantBanks = new ArrayList<Bank>();
								merchantBanks.addAll(tpvEnalbeBankEnable);
							} else {
								merchantBanks = bankRepo.findAll();
							}
						}

					} else {

						if (transactionEssential != null && transactionEssential.getUdf57() != null
								&& transactionEssential.getUdf57().contains("TPV")) {
							List<Bank> tpvEnalbeBankEnable = bankRepo.findByTpvEnable(true);
							merchantBanks = new ArrayList<Bank>();
							merchantBanks.addAll(tpvEnalbeBankEnable);
						} else {
							merchantBanks = bankRepo.findAll();
						}
					}
				}

				List<Wallet> merchantWallets = new ArrayList<>();
				if (enabledPayModes.toUpperCase().contains("WALLET")) {
					merchantWallets = walletRepo.findAll();
				}

				pgRequest.setTransactionId(pgresponse.getTransactionId());
				paymentRequest.setTransactionId(pgresponse.getTransactionId());
				if (enabledPayModes.toUpperCase().contains("DYNAMICQR")
						|| enabledPayModes.toUpperCase().contains("UPI")) {
					UpiQrDetail qrDetail = upiQrDetailRepo.findByVpaAndEnable(vpa, true);
					if (qrDetail != null && qrDetail.getVpa() != null && !qrDetail.getVpa().equals("")) {

						String qr = qrDetail.getVpa();
						
						MerchantCommision merchantCommisionData = merchantCommisionService.getMerchantCommisionData(merchant.getMid(),
								"DYNAMICQR", Double.parseDouble(pgRequest.getTxnAmount()), "IPG", null, propMap);
						CommissionModel cmodel = Utilities.getCommissionChargesModel(merchantCommisionData, Double.parseDouble(pgRequest.getTxnAmount()), propMap);
//						CommissionModel cmodel = merchantCommisionService.getCommisionAmountModel(merchant.getMid(),
//								"DYNAMICQR", Double.parseDouble(pgRequest.getTxnAmount()), "IPG", "");
						ConvenienceModel conmodel = convenienceChragesService.getConvenienceChargesModel(
								merchant.getMid(), "DYNAMICQR", Double.parseDouble(pgRequest.getTxnAmount()), null);
						DynamicQrModel qrModel = apiService.getDynamicQr(merchant, cmodel, conmodel, paymentRequest,
								qr);

						if (qrModel != null) {
							String qrImage = qrModel.getQrImage();
							String qrstring = qrModel.getQrString();
							paymentResponse.setQrpath(Base64.getEncoder().encodeToString(qrImage.getBytes("UTF-8")));
							paymentResponse.setQrstring(qrstring);
						} else {
							enabledPayModes = enabledPayModes.replace("DYNAMICQR", "").replace(",,", ",");
						}

					} else {
						String qrImage = null;
						String qrstring = null;
						paymentResponse.setQrpath(qrImage);
						paymentResponse.setQrstring(qrstring);
						enabledPayModes = enabledPayModes.replace("DYNAMICQR", "").replace(",,", ",");
					}
				}

				paymentResponse.setPaymentRequest(paymentRequest);
				paymentResponse.setCommissionModel(commissionModel);
				paymentResponse.setEnabledPayModes(enabledPayModes);
				paymentResponse.setMerchant(toDTO(merchant));
				paymentResponse.setMonths(Utilities.getMonths());
				paymentResponse.setYears(Utilities.getYears());
				paymentResponse.setBanks(merchantBanks);
				paymentResponse.setWallets(merchantWallets);
				paymentResponse.setUpiQrDetail(qrDetailgenre);

				if (transactionEssential != null && transactionEssential.getUdf57() != null
						&& transactionEssential.getUdf57().contains("TPV")) {
					paymentResponse.setTpv("TPV");
				} else {
					paymentResponse.setTpv(null);
				}

				if (uiColorScheme != null) {
					if (encyptedlogoPath != null) {
						uiColorScheme.setLogoPath(encyptedlogoPath);
					} else {
						uiColorScheme.setLogoPath(null);
					}
					paymentResponse.setUIColorScheme(uiColorScheme);
				}

				paymentResponse.setTransactionId("" + pgRequest.getTransactionId());
				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(paymentResponse, token),
						"SUCCESS", HttpStatus.OK);
			}
		}

		return ResponseBuilder.buildResponse(encryptionService.encryptResponse(pgresponse, token), "FAILED",
				HttpStatus.BAD_REQUEST);
	}

	private MerchantDTO toDTO(Merchant merchant) {
		if (merchant == null) {
			return null;
		}

		MerchantDTO merchantDTO = new MerchantDTO();

		merchantDTO.setMid(merchant.getMid());
		merchantDTO.setMerchantName(merchant.getMerchantName());
		merchantDTO.setEmail(merchant.getEmail());
		merchantDTO.setMobileNumber(merchant.getMobileNumber());
		merchantDTO.setAddress(merchant.getAddress());
		merchantDTO.setCity(merchant.getCity());
		merchantDTO.setState(merchant.getState());
		merchantDTO.setPincode(merchant.getPincode());
		merchantDTO.setBusinessCategory(merchant.getBusinessCategory());
//		merchantDTO.setAccountNumber(merchant.getAccountNumber());
//		merchantDTO.setIfscCode(merchant.getIfscCode());
//		merchantDTO.setBankName(merchant.getBankName());
//		merchantDTO.setCreatedDate(merchant.getCreatedDate());
//		merchantDTO.setModifiedDate(merchant.getModifiedDate());

		return merchantDTO;
	}

}
