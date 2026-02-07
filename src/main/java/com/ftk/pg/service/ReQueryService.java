package com.ftk.pg.service;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.DmoOnboarding;
import com.ftk.pg.modal.Merchant;
import com.ftk.pg.modal.MerchantSetting;
import com.ftk.pg.modal.TransactionEssentials;
import com.ftk.pg.modal.TransactionLog;
import com.ftk.pg.modal.UpiQrDetail;
import com.ftk.pg.pi.modal.AdvanceProperties;
import com.ftk.pg.pi.repo.AdvancePropertiesRepo;
import com.ftk.pg.repo.DmoOnboardingRepo;
import com.ftk.pg.repo.MerchantRepo;
import com.ftk.pg.repo.MerchantSettingRepo;
import com.ftk.pg.repo.TransactionEssentialsRepo;
import com.ftk.pg.repo.TransactionLogRepo;
import com.ftk.pg.repo.UpiQrDetailRepo;
import com.ftk.pg.requestvo.KotakRequeryRequest;
import com.ftk.pg.requestvo.NorthAcrossPaymentStatusRequest;
import com.ftk.pg.requestvo.PropertiesVo;
import com.ftk.pg.requestvo.SaleStatusQueryApiRequest;
import com.ftk.pg.responsevo.Data;
import com.ftk.pg.responsevo.KotakRequeryResponse;
import com.ftk.pg.responsevo.NorthAcrossPaymentStatusResponse;
import com.ftk.pg.responsevo.SaleStatusQueryApiResponse;
import com.ftk.pg.util.IciciUtils;
import com.ftk.pg.util.KotakBankUtil;
import com.ftk.pg.util.KotakRequeryApiCall;
import com.ftk.pg.util.KotakUtils;
import com.ftk.pg.util.NCrossUtils;
import com.ftk.pg.util.NorthAcrossUtil;
import com.ftk.pg.util.Utils;
import com.google.gson.Gson;
import com.mb.getepay.icici.lyra.LyraChargeResponse;
import com.mb.getepay.icici.lyra.action.Call;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReQueryService {

	static Logger log = LogManager.getLogger(ReQueryService.class);

	private final PropertiesService propertiesService;

	private final TransactionLogRepo transactionLogRepo;

	private final MerchantSettingRepo merchantSettingRepo;

	private final MerchantRepo merchantRepo;

	private final TransactionEssentialsRepo transactionEssentialsRepo;

	private final DmoOnboardingRepo dmoOnboardingRepo;

	private final AdvancePropertiesRepo advancePropertiesRepo;

	private final UpiQrDetailRepo upiQrDetailRepo;

	public static final String LYRA_BASE_URL = "LYRA_BASE_URL";

	public String banksRequary(TransactionLog transactionLog) {
		log.info("<====== Inside Requery Api  ======> " + transactionLog.getTransactionId());

		Map<String, String> propMap = new HashMap<>();
		List<PropertiesVo> propertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
		for (PropertiesVo properties : propertiesList) {
			propMap.put(properties.getPropertyKey(), properties.getPropertyValue());
		}

		transactionLog = transactionLogRepo.findById(transactionLog.getTransactionId()).get();

		Merchant merchant = merchantRepo.findByMid(transactionLog.getMerchantId());

		log.info("TransactionLog    " + transactionLog);
		if (transactionLog.getTxnStatus().equalsIgnoreCase("SUCCESS")
				|| transactionLog.getTxnStatus().equalsIgnoreCase("FAILED")) {
			if ("SBI BANK".equals(transactionLog.getProcessor())) {
				TransactionEssentials transactionEssentials = transactionEssentialsRepo
						.findByTransactionId(transactionLog.getTransactionId());
				transactionEssentials.setUdf41(null);
				transactionEssentialsRepo.save(transactionEssentials);
			}

			return "SUCCESS";
		} else {

			if (transactionLog.getProcessor() == null) {
				log.info("Requery Functionality is not available Due to NULL Processor   =====> "
						+ transactionLog.getTransactionId());
				return "SUCCESS";
			}

			TransactionLog updateTransactionLog = requeryTransactionLog(transactionLog, propMap, merchant);
			log.info("UpdateTransactionLog ======> " + updateTransactionLog);

			Set<String> allowedProcessor = Utils.handleProcessor(propMap.get(Utils.REQUERY_ALLOWED_PROCESSOR));

			log.info("Requery allowedProcessor    " + allowedProcessor);

//			String txnSyncUrl = propMap.get(Utils.TXN_SYNC_API_URL);

			if (updateTransactionLog != null && updateTransactionLog.getProcessor() != null
					&& allowedProcessor.contains(updateTransactionLog.getProcessor())) {

				if (updateTransactionLog != null && updateTransactionLog.getTxnStatus().equalsIgnoreCase("SUCCESS")) {
					TransactionLog save = transactionLogRepo.save(updateTransactionLog);
//					Utils.addTranasctionQueue(save.getTransactionId(), txnSyncUrl);
				} else if (transactionLog != null && transactionLog.getTxnStatus().equalsIgnoreCase("FAILED")) {
					TransactionLog save = transactionLogRepo.save(transactionLog);
//					Utils.addTranasctionQueue(save.getTransactionId(), txnSyncUrl);
				} else {
					if (merchant.getForceUpdate() != null && merchant.getForceUpdate()) {
						transactionLog.setResponseCode("01");
						transactionLog.setTxnStatus("FAILED");
						transactionLog.setStage("Transaction is marked as failed in requery Due to Force Update");
						transactionLog.setBankErrorMsg("Transaction Timeout");
						TransactionLog save = transactionLogRepo.save(transactionLog);
//						Utils.addTranasctionQueue(save.getTransactionId(), txnSyncUrl);
					} else {
						TransactionLog save = transactionLogRepo.save(transactionLog);
//						Utils.addTranasctionQueue(save.getTransactionId(), txnSyncUrl);
					}
				}
			} else {
				log.info(transactionLog.getProcessor() + "  is not allowed for this Requery");
			}
		}

		return "SUCCESS";
	}

	private TransactionLog requeryTransactionLog2(TransactionLog transactionLog, Map<String, String> propMap,
			Merchant merchant) {

		if (transactionLog.getProcessor() == null) {
			transactionLog.setResponseCode("01");
			transactionLog.setTxnStatus("FAILED");
			transactionLog.setStage("Transaction is marked as failed in requery.");
			transactionLog.setBankErrorMsg("Transaction Timeout");
			TransactionLog save = transactionLogRepo.save(transactionLog);
			return save;
		} else if ((!transactionLog.getProcessor().equalsIgnoreCase("ICICI"))
				&& !transactionLog.getProcessor().equalsIgnoreCase("HDFC BANK")) {

			if (transactionLog.getMerchantSettingId() == null) {
				return null;
			} else {
				MerchantSetting merchantSetting = merchantSettingRepo.findById(transactionLog.getMerchantSettingId())
						.orElse(null);
				if (merchantSetting == null) {
					log.info("MerchantSetting not found for id : " + transactionLog.getMerchantSettingId());
					return null;
				}

				try {
					String requeryUrl = propMap.get(Utils.REQUARY_API_URL);

					if (transactionLog.getProcessor().equals("BOB")) {
						transactionLog = BobRequeryBanks.bobRequary(transactionLog, merchantSetting, propMap);

						return transactionLog;
					}

					else if (transactionLog.getProcessor().equals("LICICI")) {
						String baseUrl = propMap.get(LYRA_BASE_URL);
						transactionLog = RequeryBanks.lyraRequery(transactionLog, merchantSetting, baseUrl, requeryUrl);
						return transactionLog;
					}

					else if ((transactionLog.getProcessor().equalsIgnoreCase("SHIVALIKNB"))
							|| (transactionLog.getProcessor().equalsIgnoreCase("IDFCNB"))) {
						transactionLog = RequeryBanks.northAcross(transactionLog, merchantSetting, propMap);
						return transactionLog;
					} else if (transactionLog.getProcessor().equalsIgnoreCase("KOTAK BANK")) {
						transactionLog = RequeryBanks.kotakBankRequery(transactionLog, merchantSetting, propMap);
						return null;
					} else if (transactionLog.getProcessor().equalsIgnoreCase("KOTAKNB")) {
						transactionLog = RequeryBanks.kotakNBRequery(transactionLog, merchantSetting, propMap,
								requeryUrl);
						return null;
					} else if (transactionLog.getProcessor().equalsIgnoreCase("PAYNETZ")) {
						transactionLog = RequeryBanks.doAtomRequery(transactionLog, merchantSetting, requeryUrl);
						return transactionLog;
					} else if (transactionLog.getProcessor().equals("SBI BANK")) {
						transactionLog = RequeryBanks.sbiCardRequery(transactionLog, merchantSetting, propMap,
								requeryUrl);
						TransactionEssentials transactionEssentials = transactionEssentialsRepo
								.findByTransactionId(transactionLog.getTransactionId());
						transactionEssentials.setUdf41(null);
						transactionEssentialsRepo.save(transactionEssentials);
						return transactionLog;
					} else if (transactionLog.getProcessor().equalsIgnoreCase("SBINB")) {
						transactionLog = RequeryBanks.sbinbRequary(transactionLog, merchantSetting, propMap,
								requeryUrl);
						return transactionLog;
					} else if (transactionLog.getProcessor().equalsIgnoreCase("AXISNB")) {
						transactionLog = RequeryBanks.doAxisNB(transactionLog, merchantSetting, propMap);
						return null;
					} else if (transactionLog.getProcessor().equalsIgnoreCase("ICICINB")) {
						transactionLog = RequeryBanks.iciciNbRequery(transactionLog, merchantSetting, propMap,
								requeryUrl);
						return transactionLog;

					} else {
						log.info(" PROCESSOR NOT FOUND ");
					}
				} catch (Exception e) {
					new GlobalExceptionHandler().customException(e);
				}
			}
		} else {
			if (transactionLog.getProcessor().equalsIgnoreCase("ICICI")) {
				DmoOnboarding dmoOnboarding = dmoOnboardingRepo.findByVpa(transactionLog.getUdf9());
				log.info("DmoOnboarding ===> " + dmoOnboarding);
				if (dmoOnboarding != null) {
					AdvanceProperties propertyKey = advancePropertiesRepo
							.findByPropertyKey(IciciUtils.DMO_PARENT_MERCHANTID_ONLINE);
					String dmoMerchantIdOnline = propertyKey.getPropertyValue();
					UpiQrDetail upiQrDetail = upiQrDetailRepo
							.findFirstByMidAndVpaIsNotNull(transactionLog.getMerchantId());
					transactionLog = RequeryBanks.iciciBankRequery(transactionLog, dmoOnboarding, propMap,
							dmoMerchantIdOnline);

					return transactionLog;
				}
			} else if (transactionLog.getProcessor().equalsIgnoreCase("HDFC BANK")) {
				transactionLog = RequeryBanks.hdfcCardsRequery(transactionLog, propMap);
				return null;
			}
		}
		return null;
	}

	public TransactionLog requeryTransactionLog(TransactionLog transactionLog, Map<String, String> propMap,
			Merchant merchant) {

		String processor = transactionLog.getProcessor();

		if (transactionLog.getProcessor() == null) {
			return transactionLog;
		}

		MerchantSetting merchantSetting = null;
		if (transactionLog.getMerchantSettingId() == null) {
			return null;
		} else {
			merchantSetting = merchantSettingRepo.findById(transactionLog.getMerchantSettingId()).orElse(null);
			if (merchantSetting == null) {
				log.info("MerchantSetting not found for id : " + transactionLog.getMerchantSettingId());
				return null;
			}
		}

		String baseUrl = propMap.get(LYRA_BASE_URL);
		String requeryUrl = propMap.get(Utils.REQUARY_API_URL);

		transactionLog = switch (processor) {
		case "BOB" -> BobRequeryBanks.bobRequary(transactionLog, merchantSetting, propMap);
		case "LICICI" -> RequeryBanks.lyraRequery(transactionLog, merchantSetting, baseUrl, requeryUrl);
		case "SHIVALIKNB" -> RequeryBanks.northAcross(transactionLog, merchantSetting, propMap);
		case "IDFCNB" -> RequeryBanks.northAcross(transactionLog, merchantSetting, propMap);
		case "KOTAK BANK" -> RequeryBanks.kotakBankRequery(transactionLog, merchantSetting, propMap);
		case "KOTAKNB" -> RequeryBanks.kotakNBRequery(transactionLog, merchantSetting, propMap, requeryUrl);
		case "PAYNETZ" -> RequeryBanks.doAtomRequery(transactionLog, merchantSetting, requeryUrl);
		case "SBI BANK" -> sbiCardRequery(transactionLog, merchantSetting, propMap, requeryUrl);
		case "SBINB" -> RequeryBanks.sbinbRequary(transactionLog, merchantSetting, propMap, requeryUrl);
		case "AXISNB" -> RequeryBanks.doAxisNB(transactionLog, merchantSetting, propMap);
		case "ICICINB" -> RequeryBanks.iciciNbRequery(transactionLog, merchantSetting, propMap, requeryUrl);
		case "ICICI" -> iciciBankRequery(transactionLog, propMap);
		case "HDFC BANK" -> RequeryBanks.hdfcCardsRequery(transactionLog, propMap);

		case null -> handleNull(transactionLog);
		default -> handleDefault(transactionLog);
		};

		return transactionLog;

	}

	private TransactionLog sbiCardRequery(TransactionLog transactionLog, MerchantSetting merchantSetting,
			Map<String, String> propMap, String requeryUrl) {
		transactionLog = RequeryBanks.sbiCardRequery(transactionLog, merchantSetting, propMap, requeryUrl);
		TransactionEssentials transactionEssentials = transactionEssentialsRepo
				.findByTransactionId(transactionLog.getTransactionId());
		transactionEssentials.setUdf41(null);
		transactionEssentialsRepo.save(transactionEssentials);
		return transactionLog;
	}

	private TransactionLog iciciBankRequery(TransactionLog transactionLog, Map<String, String> propMap) {
		DmoOnboarding dmoOnboarding = dmoOnboardingRepo.findByVpa(transactionLog.getUdf9());
		log.info("DmoOnboarding ===> " + dmoOnboarding);
		if (dmoOnboarding != null) {
			AdvanceProperties propertyKey = advancePropertiesRepo
					.findByPropertyKey(IciciUtils.DMO_PARENT_MERCHANTID_ONLINE);
			String dmoMerchantIdOnline = propertyKey.getPropertyValue();
//			UpiQrDetail upiQrDetail = upiQrDetailRepo
//					.findFirstByMidAndVpaIsNotNull(transactionLog.getMerchantId());
			transactionLog = RequeryBanks.iciciBankRequery(transactionLog, dmoOnboarding, propMap, dmoMerchantIdOnline);
		}
		return transactionLog;
	}

	private TransactionLog handleDefault(TransactionLog transactionLog) {
		// TODO Auto-generated method stub
		return transactionLog;
	}

	private TransactionLog handleNull(TransactionLog transactionLog) {
		log.info("Requery Functionality is not available Due to NULL Processor   =====> "
				+ transactionLog.getTransactionId());
		return transactionLog;
	}

	public static TransactionLog lyraRequery(TransactionLog transactionLog, MerchantSetting merchantSetting,
			String baseUrl, String requeryUrl) {
		try {
			String responseCode = "01";
			String txnStatus = "FAILED";
			Gson gson = new Gson();
			Call call = new Call();

			String authoriztion = merchantSetting.getMloginId() + ":" + merchantSetting.getmPassword();
			authoriztion = Base64.getEncoder().encodeToString(authoriztion.getBytes());
//			String baseUrl = propMap.get(LyraUtil.LYRA_BASE_URL).getPropertyValue();

			call.setBaseUrl(baseUrl);
			call.setAuthoriztion(authoriztion);

			String uuid = transactionLog.getProcessorTxnId().trim();
			String lyraResponse = call.getACharge(uuid);

			LyraChargeResponse lyraChargeResponse = gson.fromJson(lyraResponse, LyraChargeResponse.class);
			log.info("Icici Lyra Response : " + lyraChargeResponse);

			if (lyraChargeResponse != null && (lyraChargeResponse.getStatus().equalsIgnoreCase("success")
					|| lyraChargeResponse.getStatus().equalsIgnoreCase("PAID"))) {

				log.info("Lyra Requery is Success=================> ");
				responseCode = "00";
				txnStatus = "SUCCESS";
				transactionLog.setResponseCode(responseCode);
				transactionLog.setTxnStatus(txnStatus);
				transactionLog.setStage("Transaction is marked as success in requery. ");
				transactionLog.setProcessorCode(lyraChargeResponse.getStatus());
				// transactionLog.setBankErrorMsg("REVERSAL");
//				transactionLog.setOrderNumber();
				return transactionLog;

//			} 
//				else if (lyraChargeResponse != null && lyraChargeResponse.getTransactions().size() > 0
//					&& lyraChargeResponse.getTransactions().get(0).getStatus().equalsIgnoreCase("DECLINED")) {
//				log.info("Lyra Requery is Failed=================> " );
//				responseCode = "01";
//				// update transaction here as failed
//				txnStatus = "FAILED";
//				transactionLog.setResponseCode(responseCode);
//				transactionLog.setTxnStatus(txnStatus);
//				transactionLog.setStage("Transaction is marked as failed in requery");
//				transactionLog.setProcessorCode(lyraChargeResponse.getStatus());
////				transactionLog.setOrderNumber();
//				transactionLog.setBankErrorMsg("Transaction Timeout");
//				return transactionLog;
			} else if (lyraChargeResponse != null && lyraChargeResponse.getTransactions().size() > 0
					&& lyraChargeResponse.getTransactions().get(0).getStatus().equalsIgnoreCase("FAILED")) {
				log.info("Lyra Requery is Failed=================> ");
				responseCode = "01";
				// update transaction here as failed
				txnStatus = "FAILED";
				transactionLog.setResponseCode(responseCode);
				transactionLog.setTxnStatus(txnStatus);
				transactionLog.setStage("Transaction is marked as failed in requery");
				transactionLog.setProcessorCode(lyraChargeResponse.getStatus());
//				transactionLog.setOrderNumber();
				transactionLog.setBankErrorMsg("Transaction Timeout");
				return transactionLog;
			}

			else {
//				txnStatus = lyraChargeResponse.getStatus();
//				transactionLog.setResponseCode(responseCode);
//				transactionLog.setTxnStatus(txnStatus);
//				transactionLog.setStage("Transaction is marked as failed in requery");
//				transactionLog.setProcessorCode(lyraChargeResponse.getStatus());
////				transactionLog.setOrderNumber();
////				transactionLog.setBankErrorMsg("Transaction Timeout");
				return transactionLog;
//				Utils.addRequaryInQueue(transactionLog.getTransactionId(), requeryUrl);
//				log.info("Requery ADD AGAIN IN QUEUE DUE TO PENDING OR INITIATE =================> "+transactionLog.getTransactionId());
			}
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public static TransactionLog northAcross(TransactionLog logs, MerchantSetting merchantSet,
			Map<String, String> propertiesData) {

		log.info("INSIDE NORTHACROSS REQUERY  ====> ");
		try {
			String response = null;
			ObjectMapper objectMapper = new ObjectMapper();
			NCrossUtils nCrossUtils = new NCrossUtils();
			Map<String, String> mapUtils = new HashMap<String, String>();
			mapUtils = nCrossUtils.propertyData(merchantSet, propertiesData);
			Gson gson = new Gson();
			NorthAcrossPaymentStatusRequest paymentStatusRequest = new NorthAcrossPaymentStatusRequest();
			paymentStatusRequest.setApi_key(merchantSet.getSetting2());
			paymentStatusRequest.setOrder_id(logs.getTransactionId().toString());
			paymentStatusRequest.setBank_code(merchantSet.getSetting4());
			paymentStatusRequest.setHash("");

			String pipesepratedhash = NorthAcrossUtil.generateHash(gson.toJson(paymentStatusRequest),
					merchantSet.getSetting1());
			log.info("pipesepratedhash  ====> " + pipesepratedhash);
			paymentStatusRequest.setHash(pipesepratedhash);
			log.info("paymentStatusRequest  ====> " + paymentStatusRequest);
			if (merchantSet.getProcessor().equals("SHIVALIKNB")) {
				log.info("Inside SHIVALIKNB REQUERY  ====> ");
				response = NorthAcrossUtil.PostApiCallShiv(paymentStatusRequest, mapUtils.get("paymentStatusApiUrl"));
			} else {
				log.info("INSIDE IDFCNB REQUERY  ====> ");
				response = NorthAcrossUtil.PostApiCall(paymentStatusRequest, mapUtils.get("paymentStatusApiUrl"));
			}

			NorthAcrossPaymentStatusResponse paymentStatusResponse = objectMapper.readValue(response,
					NorthAcrossPaymentStatusResponse.class);
			log.info("Response NorthAcrossPaymentStatusResponse " + paymentStatusResponse);
//			log.info("Response NorthAcrossPaymentStatusResponse" + paymentStatusResponse);
			Data data = new Data();
			if (paymentStatusResponse != null) {
				try {
					data = paymentStatusResponse.getData().get(0);

					if (data != null && !data.equals("")) {
						Boolean hash1 = NorthAcrossUtil.ReturnHashCalculate(gson.toJson(data),
								merchantSet.getSetting1(), paymentStatusResponse.getHash());

						if (hash1) {
							if (!data.equals("") && data.getResponse_code().equals("0")) {
								logs.setTxnStatus("SUCCESS");
								logs.setResponseCode("00");
//								logs.setBankErrorMsg("Transaction Timeout");
								logs.setStage("Transaction is marked as Success in requery");
								logs.setProcessorCode(data.getTransaction_id());

							} else {
								logs.setTxnStatus("FAILED");
								logs.setResponseCode("01");
//								logs.setBankErrorMsg("Transaction Timeout");
								logs.setStage("Transaction is marked as failed in requery");
								logs.setProcessorCode(data.getTransaction_id());
							}
						}
					}
				} catch (Exception e) {

					e.printStackTrace();
				}
			} else {
				try {
					logs.setTxnStatus("FAILED");
					logs.setResponseCode("01");
//					logs.setBankErrorMsg("Transaction Timeout");
					logs.setStage("Transaction is marked as failed in requery");
					logs.setProcessorCode(data.getTransaction_id());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return logs;
	}

	public static TransactionLog kotakBankRequery(TransactionLog transactionLog, MerchantSetting merchantSetting,
			Map<String, String> propMap) {
		try {

			String responseCode = "01";
			String txnStatus = "";
			SaleStatusQueryApiRequest salerequest = new SaleStatusQueryApiRequest();
			salerequest.setBankId(merchantSetting.getSetting4());
			salerequest.setMerchantId(merchantSetting.getMloginId());
			salerequest.setTerminalId(merchantSetting.getSetting1());
			salerequest.setOrderId(String.valueOf(transactionLog.getTransactionId()));
			salerequest.setAccessCode(merchantSetting.getSetting2());
			salerequest.setTxnType("Status");
			salerequest.setSecureHash("");

			SaleStatusQueryApiResponse saleStatusQueryApiResponse = KotakBankUtil.saleStatusApi(salerequest,
					merchantSetting, propMap);
			log.info("Kotak Bank saleStatusQueryApiResponse ====> " + saleStatusQueryApiResponse);
			if (saleStatusQueryApiResponse != null
					&& saleStatusQueryApiResponse.getResponseCode().equalsIgnoreCase("00")) {
				responseCode = "00";
				txnStatus = "SUCCESS";
				transactionLog.setResponseCode(responseCode);
				transactionLog.setTxnStatus(txnStatus);
				transactionLog.setStage("Transaction is marked as success in requery. ");
				transactionLog.setProcessorCode(saleStatusQueryApiResponse.getResponseCode());
				// transactionLog.setBankErrorMsg("REVERSAL");

			} else {
				try {
					responseCode = "01";
					// update transaction here as failed
					txnStatus = "FAILED";
					transactionLog.setResponseCode(responseCode);
					transactionLog.setTxnStatus(txnStatus);
					transactionLog.setStage("Transaction is marked as failed in requery");
					transactionLog.setProcessorCode(saleStatusQueryApiResponse.getResponseCode());
					transactionLog.setBankErrorMsg("Transaction Timeout");

				} catch (Exception e) {
					new GlobalExceptionHandler().customException(e);
				}

			}
			log.info(saleStatusQueryApiResponse);
			return transactionLog;

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public static TransactionLog kotakNBRequery(TransactionLog transactionLog, MerchantSetting merchantSetting,
			Map<String, String> propMap, String requeryUrl) {

		log.info("<=================Inside KOTAKNB Requery=====================> ");

		try {
			KotakRequeryApiCall kotakRequeryApiCall = new KotakRequeryApiCall();
//			kotakRequeryApiCall.setRefundApiUrl(propMap.get(KotakUtils.KOTAK_NB_REFUND_API_URL));
//			kotakRequeryApiCall.setApiKey(propMap.get(KotakUtils.KOTAK_NB_REFUND_API_KEY));
//			kotakRequeryApiCall.setCheckSumKey(propMap.get(KotakUtils.KOTAK_NB_REFUND_CHECKSUM_KEY));
//			kotakRequeryApiCall.setMerchantId(merchantSetting.getMloginId());
//			kotakRequeryApiCall.setGenerateTokenurl(propMap.get(KotakUtils.KOTAK_NB_REFUND_GENERATE_TOKEN_URL));
//			kotakRequeryApiCall.setClientId(propMap.get(KotakUtils.KOTAK_NB_REFUND_CLIENT_ID));
//			kotakRequeryApiCall.setClientSecret(propMap.get(KotakUtils.KOTAK_NB_REFUND_CLIENT_SECRET));
			kotakRequeryApiCall.setTxnDate(transactionLog.getCreatedDate());
			String refNo = kotakRequeryApiCall.getRefNo(kotakRequeryApiCall.getTxnDate(),
					transactionLog.getTransactionId());
			kotakRequeryApiCall.setMerchantReference(refNo);

			String generateTokenUrl = propMap.get(KotakUtils.KOTAK_NB_REFUND_GENERATE_TOKEN_URL);
			String client_id = propMap.get(KotakUtils.KOTAK_NB_REFUND_CLIENT_ID);
			String client_Secret = propMap.get(KotakUtils.KOTAK_NB_REFUND_CLIENT_SECRET);

			String token = KotakUtils.generateToken(generateTokenUrl, client_Secret, client_id);
			if (token == null) {
			}

			String dateAndTime = KotakUtils.dateAndTime(transactionLog.getDate());
			KotakRequeryRequest kotakRequeryRequest = new KotakRequeryRequest();

			kotakRequeryRequest.setMessageCode("0520");
			kotakRequeryRequest.setDateAndTime(dateAndTime);
			kotakRequeryRequest.setMerchantId(merchantSetting.getMloginId());
			kotakRequeryRequest.setMerchantReference(refNo);
			kotakRequeryRequest.setFuture1("");
			kotakRequeryRequest.setFuture2("");

			String msg = kotakRequeryRequest.getRequestToCalculateChecksum();

			String pipeSeprated = KotakUtils.getHMAC256Checksum(msg, propMap.get(KotakUtils.KOTAK_CHECKSUM_KEY));

			kotakRequeryRequest.setCheckSum(pipeSeprated);

			String finalRequest = kotakRequeryRequest.getRequestWithChecksum();

			String encryptRequest = KotakUtils.encrypt(finalRequest, propMap.get(KotakUtils.KOTAK_ENCRYPTION_KEY));

			String responseString = KotakUtils.postApi(propMap.get(KotakUtils.KOTAK_NB_REFUND_API_URL), encryptRequest,
					token);

			String decryptResponse = KotakUtils.decrypt(String.valueOf(responseString),
					propMap.get(KotakUtils.KOTAK_ENCRYPTION_KEY));
			KotakRequeryResponse response = new KotakRequeryResponse(decryptResponse);

//			KotakRequeryResponse response = kotakRequeryApiCall.kotakRequeryApi();

			if (response.getAuthStatus().equalsIgnoreCase("Y")) {
				transactionLog.setResponseCode("00");
				transactionLog.setTxnStatus("SUCCESS");
				transactionLog.setStage("Transaction is marked as success in requery. ");
				// // transactionLog.setBankErrorMsg("REVERSAL");
			} else {
				transactionLog.setResponseCode("01");
				transactionLog.setTxnStatus("FAILED");
				transactionLog.setStage("Transaction is marked as failed in requery. ");
//				transactionLog.setBankErrorMsg("Transaction Timeout");
			}
			return transactionLog;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

}
