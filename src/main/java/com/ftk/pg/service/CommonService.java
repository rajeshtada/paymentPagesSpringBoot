package com.ftk.pg.service;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftk.pg.dao.ConvenienceChargesDao;
import com.ftk.pg.dao.MerchantCommisionDao;
import com.ftk.pg.dao.MerchantSettingDao;
import com.ftk.pg.dao.TransactionLogDao;
import com.ftk.pg.dto.RequestWrapper;
import com.ftk.pg.dto.ResponseBuilder;
import com.ftk.pg.dto.ResponseWrapper;
import com.ftk.pg.encryption.GcmPgEncryption;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.Bank;
import com.ftk.pg.modal.ConvenienceCharges;
import com.ftk.pg.modal.DmoOnboarding;
import com.ftk.pg.modal.DynamicQrRequestModel;
import com.ftk.pg.modal.Merchant;
import com.ftk.pg.modal.MerchantCallbackSetting;
import com.ftk.pg.modal.MerchantCommision;
import com.ftk.pg.modal.MerchantRisk;
import com.ftk.pg.modal.MerchantSetting;
import com.ftk.pg.modal.TransactionEssentials;
import com.ftk.pg.modal.TransactionLog;
import com.ftk.pg.pi.modal.IntermediateTransaction;
import com.ftk.pg.pi.modal.Invoice;
import com.ftk.pg.pi.modal.MerchantKeys;
import com.ftk.pg.pi.repo.IntermediateTransactionRepo;
import com.ftk.pg.pi.repo.InvoiceRepo;
import com.ftk.pg.pi.repo.MerchantKeysRepo;
import com.ftk.pg.repo.BankRepo;
import com.ftk.pg.repo.DmoOnboardingRepo;
import com.ftk.pg.repo.MerchantCallbackSettingRepo;
import com.ftk.pg.repo.MerchantRepo;
import com.ftk.pg.repo.MerchantRiskRepo;
import com.ftk.pg.repo.MerchantSettingRepo;
import com.ftk.pg.repo.TransactionEssentialsRepo;
import com.ftk.pg.repo.TransactionLogRepo;
import com.ftk.pg.repo.UpiQrDetailRepo;
import com.ftk.pg.requestvo.AuthorizationRequest;
import com.ftk.pg.requestvo.BEPG;
import com.ftk.pg.requestvo.CardDetailsVo;
import com.ftk.pg.requestvo.IciciHybridRequest;
import com.ftk.pg.requestvo.OTPVerifyWrapperRequest;
import com.ftk.pg.requestvo.PaymentDeclineVo;
import com.ftk.pg.requestvo.PgPushNotificationRequest;
import com.ftk.pg.requestvo.PropertiesVo;
import com.ftk.pg.requestvo.QrRequest;
import com.ftk.pg.requestvo.RedirectRequestVo;
import com.ftk.pg.requestvo.SaleStatusQueryApiRequest;
import com.ftk.pg.requestvo.TokenHeader;
import com.ftk.pg.requestvo.VerifyOtpApiRequest;
import com.ftk.pg.responsevo.AuthorizeApiResponse;
import com.ftk.pg.responsevo.AutorizationResponse;
import com.ftk.pg.responsevo.DynamicQrResponse;
import com.ftk.pg.responsevo.IciciDynamicQr3Response;
import com.ftk.pg.responsevo.MerchantRuResponseVo;
import com.ftk.pg.responsevo.MerchantRuResponseWrapper;
import com.ftk.pg.responsevo.PayResponse;
import com.ftk.pg.responsevo.PaymentResponse;
import com.ftk.pg.responsevo.RedirectPaymentResponseVo;
import com.ftk.pg.responsevo.RedirectResponseVo;
import com.ftk.pg.responsevo.SaleStatusQueryApiResponse;
import com.ftk.pg.responsevo.UpiCollectResponse;
import com.ftk.pg.responsevo.VerifyOTPResponse;
import com.ftk.pg.responsevo.VerifyOtpApiResponse;
import com.ftk.pg.util.CallbackThread;
import com.ftk.pg.util.CommissionModel;
import com.ftk.pg.util.ComponentUtils;
import com.ftk.pg.util.ConvenienceModel;
import com.ftk.pg.util.EncryptionUtil;
import com.ftk.pg.util.IciciCompositPay;
import com.ftk.pg.util.IciciDynamicQrCall;
import com.ftk.pg.util.KotakUtil;
import com.ftk.pg.util.RemoteDbUtil;
import com.ftk.pg.util.ResendOTPResponse;
import com.ftk.pg.util.SBICardsUtils;
import com.ftk.pg.util.SbiCardUtilCall;
import com.ftk.pg.util.Utilities;
import com.ftk.pg.vo.sbiNb.SBITokenResponse;
import com.ftk.pg.vo.sbiNb.SbiRequestHeader;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.mb.getepay.auupi.action.Call;
import com.mb.getepay.auupi.util.Config;
import com.mb.getepay.auupi.util.GetQrResponse;
import com.mb.getepay.icici.lyra.LyraOtpRequest;
import com.mb.getepay.icici.lyra.LyraOtpResponse;
import com.pgcomponent.security.SignatureGenerator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommonService {

	private Logger logger = LogManager.getLogger(CommonService.class);

	private final MerchantRiskRepo merchantRiskRepo;

	private final TransactionLogDao txnLogDao;

	private final ConvenienceChargesDao convenienceChargesDao;

	private final BankRepo bankRepo;

	private final MerchantCommisionDao merchantCommisionDao;

	private final PropertiesService propertiesService;

	private final DmoOnboardingRepo dmoOnboardingRepo;

	private final InvoiceRepo invoiceRepo;

	private final TransactionLogRepo transactionLogRepo;

	private final MerchantSettingDao merchantSettingDao;

	private final MerchantRepo merchantRepo;

	private final TransactionEssentialsRepo transactionEssentialsRepo;

	private final EncryptionService encryptionService;

	private final CallBackService callBackService;

	private final MerchantCallbackSettingRepo merchantCallbackSettingRepo;

	private final MerchantSettingRepo merchantSettingRepo;

	private final CallBackService callbackService;

	private final IntermediateTransactionRepo intermediateTransactionRepo;
	
	private final MerchantKeysRepo merchantKeysRepo;

	public void validateTxnRisk(TransactionLog txnLog) {
		Map<String, List<TransactionLog>> monthTxnMap = new HashMap<String, List<TransactionLog>>();
		Map<String, List<TransactionLog>> weekTxnMap = new HashMap<String, List<TransactionLog>>();
		Map<String, List<TransactionLog>> dailyTxnMap = new HashMap<String, List<TransactionLog>>();

		List<TransactionLog> mobMonthlyTxnList = new ArrayList<>();
		List<TransactionLog> mobWeeklyTxnList = new ArrayList<>();
		List<TransactionLog> mobDailyTxnList = new ArrayList<>();

		Double totalTxnAmt = 0.0;
		Double weekTotalAmt = 0.0;
		Double todayTotalAmt = 0.0;

		List<String> weekKey = new ArrayList<String>();
		List<String> dailyKey = new ArrayList<String>();
		List<String> monthKey = new ArrayList<String>();

		List<TransactionLog> txnLogList = new ArrayList<>();

		try {

			MerchantRisk merchantRisk = merchantRiskRepo.findByMidAndStatus(txnLog.getMerchantId(), 1);
			if (merchantRisk == null) {
				logger.info("Merchant transaction risk not configured !!");
			} else {
				// risk amount validate
//				if (merchantRisk.getHoldStatus() == 0) {
//					logger.info("Hold status not enables ");
//				} else {
				if (merchantRisk.getMaxTxnAmt().compareTo(txnLog.getAmt()) == 0
						|| txnLog.getAmt().compareTo(merchantRisk.getMaxTxnAmt()) == -1) {

					// set data in map
					try {
						DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
						Calendar c1 = Calendar.getInstance();
						c1.set(Calendar.DAY_OF_MONTH, 1);

						Calendar calendar = Calendar.getInstance();
						calendar.setTime(new Date());
						calendar.set(Calendar.DAY_OF_WEEK, 1);
						String weekFirstDate = df.format(calendar.getTime());

						logger.info("From date ==> " + df.format(c1.getTime()) + " To date ==> "
								+ getDateString(txnLog.getDate()));
						txnLog.setMerchantId(1l);
						txnLogList = txnLogDao.getSuccessTxnListByDateRangeAndMid(df.format(c1.getTime()),
								getDateString(txnLog.getDate()), txnLog.getMerchantId());
						if (!txnLogList.isEmpty()) {
							for (TransactionLog log : txnLogList) {
								totalTxnAmt = totalTxnAmt + Double.valueOf(log.getAmt().doubleValue());
							}

							//
							List<TransactionLog> txnList = new ArrayList<>();
							List<TransactionLog> wTxnList = new ArrayList<>();
							List<TransactionLog> dTxnList = new ArrayList<>();
							for (TransactionLog txn : txnLogList) {
								String key = txn.getMerchantId() + "_" + txn.getDate();
								if (monthTxnMap.containsKey(key)) {

									// week validation
									if (getDateFormat(weekFirstDate).compareTo(getDateFormat(txn.getDate())) == 0
											|| getDateFormat(weekFirstDate)
													.compareTo(getDateFormat(txn.getDate())) == -1) {
										String wkey = txn.getMerchantId() + "_" + txn.getDate();
										if (weekTxnMap.containsKey(wkey)) {
											wTxnList = weekTxnMap.get(key);
											wTxnList.add(txnLog);
											weekTxnMap.replace(wkey, wTxnList);
											weekTotalAmt = weekTotalAmt + txn.getAmt().doubleValue();
										} else {
											weekTotalAmt = weekTotalAmt + txn.getAmt().doubleValue();
											wTxnList = new ArrayList<>();
											wTxnList.add(txnLog);
											weekTxnMap.put(wkey, wTxnList);
											weekKey.add(wkey);
										}
										if (txn.getMobile().equalsIgnoreCase(txnLog.getMobile())) {
											mobWeeklyTxnList.add(txn);
										}
									}
									// daily validation
									if (getDateFormat(txn.getDate()).compareTo(new Date()) == 0) {
										String dkey = txn.getMerchantId() + "_" + txn.getDate();
										if (dailyTxnMap.containsKey(dkey)) {
											todayTotalAmt = todayTotalAmt + txn.getAmt().doubleValue();
											dTxnList = dailyTxnMap.get(key);
											dTxnList.add(txnLog);
											dailyTxnMap.replace(dkey, dTxnList);
										} else {
											todayTotalAmt = todayTotalAmt + txn.getAmt().doubleValue();
											dTxnList = new ArrayList<>();
											dailyTxnMap.put(dkey, dTxnList);
											dailyKey.add(dkey);
										}

										if (txn.getMobile().equalsIgnoreCase(txnLog.getMobile())) {
											mobDailyTxnList.add(txn);
										}
									}

									txnList = monthTxnMap.get(key);
									txnList.add(txn);
									monthTxnMap.replace(key, txnList);
								} else {
									if (getDateFormat(weekFirstDate).compareTo(getDateFormat(txn.getDate())) == 0
											|| getDateFormat(weekFirstDate)
													.compareTo(getDateFormat(txn.getDate())) == -1) {
										String wkey = txn.getMerchantId() + "_" + txn.getDate();
										if (weekTxnMap.containsKey(wkey)) {
											wTxnList = weekTxnMap.get(key);
											wTxnList.add(txnLog);
											weekTxnMap.replace(wkey, wTxnList);
											weekTotalAmt = weekTotalAmt + txn.getAmt().doubleValue();
										} else {
											wTxnList = new ArrayList<>();
											wTxnList.add(txnLog);
											weekTxnMap.put(wkey, wTxnList);
											weekKey.add(wkey);
											weekTotalAmt = weekTotalAmt + txn.getAmt().doubleValue();
										}

										if (txn.getMobile().equalsIgnoreCase(txnLog.getMobile())) {
											mobWeeklyTxnList.add(txn);
										}
									}
									// daily validation
									if (getDateFormat(txn.getDate()).compareTo(new Date()) == 0) {
										String dkey = txn.getMerchantId() + "_" + txn.getDate();
										if (dailyTxnMap.containsKey(dkey)) {
											todayTotalAmt = todayTotalAmt + txn.getAmt().doubleValue();
											dTxnList = dailyTxnMap.get(key);
											dTxnList.add(txnLog);
											dailyTxnMap.replace(dkey, dTxnList);
										} else {
											todayTotalAmt = todayTotalAmt + txn.getAmt().doubleValue();
											dTxnList = new ArrayList<>();
											dailyTxnMap.put(dkey, dTxnList);
											dailyKey.add(dkey);
										}
										if (txn.getMobile().equalsIgnoreCase(txnLog.getMobile())) {
											mobDailyTxnList.add(txn);
										}
									}
									txnList = new ArrayList<>();
									txnList.add(txn);
									monthTxnMap.put(key, txnList);
									monthKey.add(key);

								}

								if (txn.getMobile().equalsIgnoreCase(txnLog.getMobile())) {
									mobMonthlyTxnList.add(txn);
								}
							}
						}
						// final validation
						Boolean monthFlag = monthlyRiskValidationAndMonthlyAverage(txnLog, txnLogList, totalTxnAmt,
								merchantRisk);
						if (monthFlag) {
							Boolean weekFlag = weekRiskValidationAndAvg(txnLog, merchantRisk, weekTotalAmt, weekKey,
									weekTxnMap);
							if (weekFlag) {
								Boolean dailyFlag = dailyRiskValidationAndAvg(txnLog, merchantRisk, todayTotalAmt,
										dailyKey, dailyTxnMap);
								if (dailyFlag) {
									Boolean mMobFlag = mobRiskValidation(txnLog, merchantRisk, mobMonthlyTxnList);
									if (mMobFlag) {
										Boolean wMobFlag = weekRiskValidation(txnLog, merchantRisk, mobWeeklyTxnList);
										if (wMobFlag) {
											Boolean dMobFlag = dailyRiskValidation(txnLog, merchantRisk,
													mobDailyTxnList);
										}
									}
								}
							}
						}
					} catch (Exception e) {
						new GlobalExceptionHandler().customException(e);
					}

				} else {
//						txnLog.setSettlementStatus(4);
					if (merchantRisk.getHoldStatus() == 1) {
						txnLog.setHoldDate(new Date());
						txnLog.setRiskStatus(2);
					} else {
						txnLog.setRiskStatus(1);
					}
					txnLog.setRiskDescription("Exceed txn per amount");
				}
//				}

			}
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
	}

	private Boolean dailyRiskValidation(TransactionLog txnLog, MerchantRisk merchantRisk,
			List<TransactionLog> mobDailyTxnList) {
		Boolean flag = false;
		try {
			if (merchantRisk.getMaxTxnAgainstMobNoDaily() >= mobDailyTxnList.size()) {
				txnLog.setRiskStatus(0);
				flag = true;
			} else {
				txnLog.setRiskDescription("Exceed no of transactin daily");
				txnLog.setRiskStatus(1);
				if (merchantRisk.getHoldStatus() == 1) {
					txnLog.setHoldDate(new Date());
					txnLog.setRiskStatus(2);
				} else {
					txnLog.setRiskStatus(1);
				}
				return false;
			}
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			flag = false;
		}
		return flag;
	}

	private Boolean weekRiskValidation(TransactionLog txnLog, MerchantRisk merchantRisk,
			List<TransactionLog> mobWeeklyTxnList) {
		Boolean flag = false;
		try {
			if (merchantRisk.getMaxTxnAgainstMobNoWeekly() >= mobWeeklyTxnList.size()) {
				txnLog.setRiskStatus(0);
				flag = true;
			} else {
				txnLog.setRiskDescription("Exceed no of transactin weekly");
				txnLog.setRiskStatus(1);
				if (merchantRisk.getHoldStatus() == 1) {
					txnLog.setHoldDate(new Date());
					txnLog.setRiskStatus(2);
				} else {
					txnLog.setRiskStatus(1);
				}
				return false;
			}
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			flag = false;
		}
		return flag;
	}

	private Boolean mobRiskValidation(TransactionLog txnLog, MerchantRisk merchantRisk,
			List<TransactionLog> mobMonthlyTxnList) {
		Boolean flag = false;
		try {
			if (merchantRisk.getMaxTxnAgainstMobNoMonthly() >= mobMonthlyTxnList.size()) {
				flag = true;
			} else {
				txnLog.setRiskStatus(1);
				txnLog.setRiskDescription("Exceed no of transactin monthly");
				if (merchantRisk.getHoldStatus() == 1) {
					txnLog.setHoldDate(new Date());
					txnLog.setRiskStatus(2);
				} else {
					txnLog.setRiskStatus(1);
				}
				return false;
			}
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			flag = false;
		}
		return flag;
	}

	private Boolean dailyRiskValidationAndAvg(TransactionLog txnLog, MerchantRisk merchantRisk, Double todayTotalAmt,
			List<String> dailyKey, Map<String, List<TransactionLog>> dailyTxnMap) {
		Boolean flag = false;
		try {
			String dky = null;
			for (String key : dailyKey) {
				dky = key.trim();
			}
			List<TransactionLog> dailyTxnList = dailyTxnMap.get(dky);
			if (merchantRisk.getMaxNoOfTxnDaily() >= dailyTxnList.size()) {
				int noOfRatio = (dailyTxnList.size() * merchantRisk.getAvgNoOfTxnDaily() / 100);
				if (merchantRisk.getAvgNoOfTxnDaily() >= noOfRatio) {
					txnLog.setRiskStatus(0);
					flag = true;
				} else {
					txnLog.setRiskDescription("Exceed no of average transactin daily");
					if (merchantRisk.getHoldStatus() == 1) {
						txnLog.setHoldDate(new Date());
						txnLog.setRiskStatus(2);
					} else {
						txnLog.setRiskStatus(1);
					}
					return false;
				}
			} else {
				txnLog.setRiskDescription("Exceed no of transactin daily");
				if (merchantRisk.getHoldStatus() == 1) {
					txnLog.setHoldDate(new Date());
					txnLog.setRiskStatus(2);
				} else {
					txnLog.setRiskStatus(1);
				}
				return false;
			}
			if (merchantRisk.getMaxNoOfTxnDailyAmt().compareTo(new BigDecimal(todayTotalAmt)) == 0
					|| merchantRisk.getMaxNoOfTxnDailyAmt().compareTo(new BigDecimal(todayTotalAmt)) == -1) {
				BigDecimal noOfAmtRatio = (new BigDecimal(todayTotalAmt).multiply(merchantRisk.getAvgNoOfTxnDailyAmt())
						.divide(new BigDecimal(100)));
				if (noOfAmtRatio.compareTo(new BigDecimal(todayTotalAmt)) == 0
						|| noOfAmtRatio.compareTo(new BigDecimal(todayTotalAmt)) == -1) {
					txnLog.setRiskStatus(0);
					flag = true;
				} else {
					txnLog.setRiskDescription("Exceed no of average transactin amount daily");
					if (merchantRisk.getHoldStatus() == 1) {
						txnLog.setHoldDate(new Date());
						txnLog.setRiskStatus(2);
					} else {
						txnLog.setRiskStatus(1);
					}
				}
			} else {
				txnLog.setRiskDescription("Exceed txn amount daily");
				if (merchantRisk.getHoldStatus() == 1) {
					txnLog.setHoldDate(new Date());
					txnLog.setRiskStatus(2);
				} else {
					txnLog.setRiskStatus(1);
				}

				flag = false;
			}
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			flag = false;
		}
		return flag;
	}

	private Boolean weekRiskValidationAndAvg(TransactionLog txnLog, MerchantRisk merchantRisk, Double weekTotalAmt,
			List<String> weekKey, Map<String, List<TransactionLog>> weekTxnMap) {
		Boolean flag = false;
		try {
			String wky = null;
			for (String key : weekKey) {
				wky = key.trim();
			}
			List<TransactionLog> weekTxnList = weekTxnMap.get(wky);
			if (merchantRisk.getMaxNoOfTxnWeekly() >= weekTxnList.size()) {
				int noOfRatio = (weekTxnList.size() * merchantRisk.getAvgNoOfTxnMonthly() / 100);
				if (merchantRisk.getAvgNoOfTxnWeekly() >= noOfRatio) {
					txnLog.setRiskStatus(0);
					flag = true;
				} else {
					txnLog.setRiskDescription("Exceed no of average transactin weekly");
					if (merchantRisk.getHoldStatus() == 1) {
						txnLog.setHoldDate(new Date());
						txnLog.setRiskStatus(2);
					} else {
						txnLog.setRiskStatus(1);
					}
					return false;
				}
			} else {
				txnLog.setRiskStatus(1);
				txnLog.setRiskDescription("Exceed no of transactin weekly");
				if (merchantRisk.getHoldStatus() == 1) {
					txnLog.setHoldDate(new Date());
					txnLog.setRiskStatus(2);
				} else {
					txnLog.setRiskStatus(1);
				}

				return false;
			}
			if (merchantRisk.getMaxNoOfTxnWeeklyAmt().compareTo(new BigDecimal(weekTotalAmt)) == 0
					|| merchantRisk.getMaxNoOfTxnWeeklyAmt().compareTo(new BigDecimal(weekTotalAmt)) == -1) {
				BigDecimal noOfAmtRatio = (new BigDecimal(weekTotalAmt).multiply(merchantRisk.getAvgNoOfTxnMonthlyAmt())
						.divide(new BigDecimal(100)));
				if (noOfAmtRatio.compareTo(new BigDecimal(weekTotalAmt)) == 0
						|| noOfAmtRatio.compareTo(new BigDecimal(weekTotalAmt)) == -1) {
					txnLog.setRiskStatus(0);
					flag = true;
				} else {
					txnLog.setRiskDescription("Exceed no of average transactin amount weekly");
					if (merchantRisk.getHoldStatus() == 1) {
						txnLog.setHoldDate(new Date());
						txnLog.setRiskStatus(2);
					} else {
						txnLog.setRiskStatus(1);
					}
				}
			} else {
				txnLog.setRiskDescription("Exceed txn amount weekly");
				if (merchantRisk.getHoldStatus() == 1) {
					txnLog.setHoldDate(new Date());
					txnLog.setRiskStatus(2);
				} else {
					txnLog.setRiskStatus(1);
				}

				flag = false;
			}
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			flag = false;
		}
		return flag;
	}

	private Boolean monthlyRiskValidationAndMonthlyAverage(TransactionLog txnLog, List<TransactionLog> txnLogList,
			Double totalTxnAmt, MerchantRisk merchantRisk) {
		Boolean flag = false;
		if (merchantRisk.getMaxNoOfTxnMonthly() >= txnLogList.size()) {
			int noOfRatio = (txnLogList.size() * merchantRisk.getAvgNoOfTxnMonthly() / 100);
			if (merchantRisk.getAvgNoOfTxnMonthly() >= noOfRatio) {
				txnLog.setRiskStatus(0);
				flag = true;
			} else {
				if (merchantRisk.getHoldStatus() == 1) {
					txnLog.setHoldDate(new Date());
					txnLog.setRiskStatus(2);
				} else {
					txnLog.setRiskStatus(1);
				}
				txnLog.setRiskDescription("Exceed no of average transactin monthly");

				return false;
			}
		} else {
			if (merchantRisk.getHoldStatus() == 1) {
				txnLog.setHoldDate(new Date());
				txnLog.setRiskStatus(2);
			} else {
				txnLog.setRiskStatus(1);
			}
			txnLog.setRiskDescription("Exceed no of transactin monthly");
			return false;
		}
		if (merchantRisk.getMaxNoOfTxnMonthlyAmt().compareTo(new BigDecimal(totalTxnAmt)) == 0
				|| merchantRisk.getMaxNoOfTxnMonthlyAmt().compareTo(new BigDecimal(totalTxnAmt)) == -1) {
			BigDecimal noOfAmtRatio = (new BigDecimal(totalTxnAmt).multiply(merchantRisk.getAvgNoOfTxnMonthlyAmt())
					.divide(new BigDecimal(100)));
			if (noOfAmtRatio.compareTo(new BigDecimal(totalTxnAmt)) == 0
					|| noOfAmtRatio.compareTo(new BigDecimal(totalTxnAmt)) == -1) {
				txnLog.setRiskStatus(0);
				flag = true;
			} else {
				if (merchantRisk.getHoldStatus() == 1) {
					txnLog.setHoldDate(new Date());
					txnLog.setRiskStatus(2);
				} else {
					txnLog.setRiskStatus(1);
				}
				txnLog.setRiskDescription("Exceed no of average transactin amount monthly");
			}
		} else {
			if (merchantRisk.getHoldStatus() == 1) {
				txnLog.setHoldDate(new Date());
				txnLog.setRiskStatus(2);
			} else {
				txnLog.setRiskStatus(1);
			}
			txnLog.setRiskDescription("Exceed txn amount monthly");
			flag = false;
		}
		return flag;
	}

//	private Boolean monthlyRiskValidationAndMonthlyAverage(TransactionLog txnLog, MerchantRisk merchantRisk) {
//		Map<String, List<TransactionLog>> monthTxnMap = new HashMap<String, List<TransactionLog>>();
//		Map<String, List<TransactionLog>> weekTxnMap = new HashMap<String, List<TransactionLog>>();
//		Map<String, List<TransactionLog>> dailyTxnMap = new HashMap<String, List<TransactionLog>>();
//		Map<String, List<TransactionLog>> mobTxnMonthlyMap = new HashMap<String, List<TransactionLog>>();
//		Map<String, List<TransactionLog>> mobTxnWeeklyMap = new HashMap<String, List<TransactionLog>>();
//		Map<String, List<TransactionLog>> mobTxnDailyMap = new HashMap<String, List<TransactionLog>>();
//
//		Double weekTotalAmt = 0.0;
//		Double todayTotalAmt = 0.0;
//
//		List<String> weekKey = new ArrayList<String>();
//		List<String> dailyKey = new ArrayList<String>();
//		List<String> monthKey = new ArrayList<String>();
//		Boolean flag = false;
//		try {
//			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
//			Calendar c1 = Calendar.getInstance();
//			c1.set(Calendar.DAY_OF_MONTH, 1);
//
//			Calendar calendar = Calendar.getInstance();
//			calendar.setTime(new Date());
//			calendar.set(Calendar.DAY_OF_WEEK, 1);
//			String weekFirstDate = df.format(calendar.getTime());
//
//			logger.info(
//					"From date ==> " + df.format(c1.getTime()) + " To date ==> " + getDateString(txnLog.getDate()));
//			List<TransactionLog> txnLogList = txnLogDao.getSuccessTxnListByDateRangeAndMid(df.format(c1.getTime()),
//					getDateString(txnLog.getDate()), txnLog.getMerchantId());
//			if (!txnLogList.isEmpty()) {
//				Double totalTxnAmt = 0.0;
//				for (TransactionLog log : txnLogList) {
//					totalTxnAmt = totalTxnAmt + Double.valueOf(log.getAmt().doubleValue());
//				}
//
////				if (merchantRisk.getMaxNoOfTxnMonthly() >= txnLogList.size()) {
////					int noOfRatio = (txnLogList.size() * merchantRisk.getAvgNoOfTxnMonthly() / 100);
////					if (merchantRisk.getAvgNoOfTxnMonthly() >= noOfRatio) {
////						flag = true;
////					} else {
////						txnLog.setSettlementStatus(4);
////						txnLog.setRiskDescription("Exceed no of average transactin monthly");
////						return false;
////					}
////				} else {
////					txnLog.setSettlementStatus(4);
////					txnLog.setRiskDescription("Exceed no of transactin monthly");
////					return false;
////				}
////				if (merchantRisk.getMaxNoOfTxnMonthlyAmt().compareTo(new BigDecimal(totalTxnAmt)) == 0
////						|| merchantRisk.getMaxNoOfTxnMonthlyAmt().compareTo(new BigDecimal(totalTxnAmt)) == -1) {
////					BigDecimal noOfAmtRatio = (new BigDecimal(totalTxnAmt)
////							.multiply(merchantRisk.getAvgNoOfTxnMonthlyAmt()).divide(new BigDecimal(100)));
////					if (noOfAmtRatio.compareTo(new BigDecimal(totalTxnAmt)) == 0
////							|| noOfAmtRatio.compareTo(new BigDecimal(totalTxnAmt)) == -1) {
////						flag = true;
////					} else {
////						txnLog.setSettlementStatus(4);
////						txnLog.setRiskDescription("Exceed no of average transactin amount monthly");
////					}
////				} else {
////					txnLog.setSettlementStatus(4);
////					txnLog.setRiskDescription("Exceed txn amount monthly");
////					flag = false;
////				}
//
//				//
//				List<TransactionLog> txnList = new ArrayList<>();
//				List<TransactionLog> wTxnList = new ArrayList<>();
//				List<TransactionLog> dTxnList = new ArrayList<>();
//				for (TransactionLog txn : txnLogList) {
//					String key = txn.getMerchantId() + "_" + txn.getDate();
//					if (monthTxnMap.containsKey(key)) {
//
//						// week validation
//						if (getDateFormat(weekFirstDate).compareTo(getDateFormat(txn.getDate())) == 0
//								|| getDateFormat(weekFirstDate).compareTo(getDateFormat(txn.getDate())) == -1) {
//							String wkey = txn.getMerchantId() + "_" + txn.getDate();
//							if (weekTxnMap.containsKey(wkey)) {
//								wTxnList = weekTxnMap.get(key);
//								wTxnList.add(txnLog);
//								weekTxnMap.replace(wkey, wTxnList);
//								weekTotalAmt = weekTotalAmt + txn.getAmt().doubleValue();
//							} else {
//								weekTotalAmt = weekTotalAmt + txn.getAmt().doubleValue();
//								wTxnList = new ArrayList<>();
//								wTxnList.add(txnLog);
//								weekTxnMap.put(wkey, wTxnList);
//								weekKey.add(wkey);
//							}
//						}
//						// daily validation
//						if (getDateFormat(txn.getDate()).compareTo(new Date()) == 0) {
//							String dkey = txn.getMerchantId() + "_" + txn.getDate();
//							if (dailyTxnMap.containsKey(dkey)) {
//								todayTotalAmt = todayTotalAmt + txn.getAmt().doubleValue();
//								dTxnList = dailyTxnMap.get(key);
//								dTxnList.add(txnLog);
//								dailyTxnMap.replace(dkey, dTxnList);
//							} else {
//								todayTotalAmt = todayTotalAmt + txn.getAmt().doubleValue();
//								dTxnList = new ArrayList<>();
//								dailyTxnMap.put(dkey, dTxnList);
//								dailyKey.add(dkey);
//							}
//						}
//
//						txnList = monthTxnMap.get(key);
//						txnList.add(txn);
//						monthTxnMap.replace(key, txnList);
//					} else {
//						if (getDateFormat(weekFirstDate).compareTo(getDateFormat(txn.getDate())) == 0
//								|| getDateFormat(weekFirstDate).compareTo(getDateFormat(txn.getDate())) == -1) {
//							String wkey = txn.getMerchantId() + "_" + txn.getDate();
//							if (weekTxnMap.containsKey(wkey)) {
//								wTxnList = weekTxnMap.get(key);
//								wTxnList.add(txnLog);
//								weekTxnMap.replace(wkey, wTxnList);
//								weekTotalAmt = weekTotalAmt + txn.getAmt().doubleValue();
//							} else {
//								wTxnList = new ArrayList<>();
//								wTxnList.add(txnLog);
//								weekTxnMap.put(wkey, wTxnList);
//								weekKey.add(wkey);
//								weekTotalAmt = weekTotalAmt + txn.getAmt().doubleValue();
//							}
//						}
//						// daily validation
//						if (getDateFormat(txn.getDate()).compareTo(new Date()) == 0) {
//							String dkey = txn.getMerchantId() + "_" + txn.getDate();
//							if (dailyTxnMap.containsKey(dkey)) {
//								todayTotalAmt = todayTotalAmt + txn.getAmt().doubleValue();
//								dTxnList = dailyTxnMap.get(key);
//								dTxnList.add(txnLog);
//								dailyTxnMap.replace(dkey, dTxnList);
//							} else {
//								todayTotalAmt = todayTotalAmt + txn.getAmt().doubleValue();
//								dTxnList = new ArrayList<>();
//								dailyTxnMap.put(dkey, dTxnList);
//								dailyKey.add(dkey);
//							}
//						}
//						txnList = new ArrayList<>();
//						txnList.add(txn);
//						monthTxnMap.put(key, txnList);
//						monthKey.add(key);
//
//					}
//				}
//
//				// final validation
//
//			}
//		} catch (Exception e) {
//			new GlobalExceptionHandler().customException(e);
//			flag = false;
//		}
//		return false;
//	}

	private Date getDateFormat(String weekFirstDate) {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		try {
			java.util.Date date1 = df.parse(weekFirstDate);
			return date1;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	private String getDateString(String date) {
		if (date != null && date.contains("+05:30")) {
			date = date.replace("+05:30", "");
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String toDate = "";
		String dates = date.replace("'", "");
		date = dates.trim();
		try {
			java.util.Date date1 = dateFormat.parse(date);
			toDate = df.format(date1);
		} catch (Exception e) {
			// new GlobalExceptionHandler().customException(e);
			try {
				Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
				toDate = df.format(date1);
			} catch (Exception e2) {
				// e2.printStackTrace();
				try {
					Date date1 = new SimpleDateFormat("MM/dd/yyy HH:mm:ss").parse(date);
					toDate = df.format(date1);
				} catch (Exception e3) {
				}
			}
		}
		return toDate;
	}

	public TransactionLog setServiceCharges(TransactionLog log, String cardType) {
		logger.info("Inside service charge calculation..");
		ConvenienceCharges charges = convenienceChargesDao.getChargesByMidPaymentModeAndCardType(log.getMerchantId(),
				log.getPaymentMode(), cardType);

		BigDecimal serviceCharges = BigDecimal.ZERO;
		BigDecimal gstCharges = BigDecimal.ZERO;
		BigDecimal totalServiceCharges = BigDecimal.ZERO;

		if (charges == null) {
			logger.info("No service charge configuration found..");
			log.setServiceCharge(serviceCharges.setScale(2, RoundingMode.HALF_UP).doubleValue());
			log.setServiceChargeGst(gstCharges.setScale(2, RoundingMode.HALF_UP).doubleValue());
			log.setTotalServiceCharge(totalServiceCharges.setScale(2, RoundingMode.HALF_UP).doubleValue());
			log.setServiceChargeType("Incl");
			return log;
		}
		try {
			logger.info("service charge configuration found..");
			Double chargesAmt = charges.getChargesAmt();
			Double fixedVarible = charges.getFixedVarible();
			if (chargesAmt == null) {
				chargesAmt = 0d;
			}
			if (fixedVarible == null) {
				fixedVarible = 0d;
			}
			if (charges.getCommissionType() != null && charges.getCommissionType().equalsIgnoreCase("Fixed")) {
				serviceCharges = new BigDecimal(chargesAmt);
			} else if (charges.getCommissionType() != null
					&& charges.getCommissionType().equalsIgnoreCase("Variable")) {
				logger.info("service charge configuration found of type variable..");
				serviceCharges = (new BigDecimal(chargesAmt)).multiply(log.getAmt());
				serviceCharges = serviceCharges.divide(new BigDecimal("100"), RoundingMode.HALF_UP);
			} else if (charges.getCommissionType() != null
					&& charges.getCommissionType().equalsIgnoreCase("Fixed_Variable")) {
				BigDecimal fixedCharges = new BigDecimal(chargesAmt);

				BigDecimal variableCharges = (new BigDecimal(fixedVarible)).multiply(log.getAmt());
				variableCharges = variableCharges.divide(new BigDecimal("100"), RoundingMode.HALF_UP);

				serviceCharges = fixedCharges.add(variableCharges);
			}

			gstCharges = serviceCharges.multiply(new BigDecimal(ComponentUtils.GST_CHARGES));
			gstCharges = gstCharges.divide(new BigDecimal("100"), RoundingMode.HALF_UP);

			totalServiceCharges = serviceCharges.add(gstCharges);

			log.setServiceCharge(serviceCharges.setScale(2, RoundingMode.HALF_UP).doubleValue());
			log.setServiceChargeGst(gstCharges.setScale(2, RoundingMode.HALF_UP).doubleValue());
			log.setTotalServiceCharge(totalServiceCharges.setScale(2, RoundingMode.HALF_UP).doubleValue());
			log.setServiceChargeType(charges.getChargesType());
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return log;
	}

	public Bank getBankById(long id) {
		return bankRepo.findById(id).get();
	}

	public CommissionModel getCommisionAmountModel(Long mid, String payMode, double amounT, String productType,
			String subType) {
		BigDecimal amount = BigDecimal.valueOf(amounT);
		MerchantCommision merchantCommision = new MerchantCommision();
		merchantCommision.setMerchantId(mid);
		merchantCommision.setProductType(productType);
		merchantCommision.setPaymentMode(payMode);
		merchantCommision.setCommisionvalue(amount);
		merchantCommision.setDefault(false);
		if (payMode.equalsIgnoreCase("DC")) {
			merchantCommision.setSubType(subType);
		} else if (subType != null && !subType.equals("") && payMode.equalsIgnoreCase("NB")) {
			try {
				Bank bank = bankRepo.findById(Long.valueOf(subType)).get();
				merchantCommision.setSubType(bank.getBankName());
			} catch (Exception e) {
				new GlobalExceptionHandler().customException(e);
			}

		}
		List<MerchantCommision> commissions = merchantCommisionDao.searchMerchatCommision(merchantCommision);

		if (commissions == null || commissions.size() <= 0) {
			merchantCommision.setDefault(true);
			if (payMode.equalsIgnoreCase("NB") || payMode.equalsIgnoreCase("DC")) {
				merchantCommision.setSubType(null);
			}
			commissions = merchantCommisionDao.searchMerchatCommision(merchantCommision);
		}

		if (commissions != null && commissions.size() > 0) {
			merchantCommision = commissions.get(0);
		}
		return RemoteDbUtil.getCommissionChargesModel(merchantCommision, amounT);
		// logger.info("Final Amount Return From Method: **" + amount + "**");
		// return amount;
	}

	public ConvenienceModel getConvenienceChargesModel(long mid, String paymentMode, double parseDouble,
			String subType) {
		logger.info("getConvenienceChargesModel called==========>");
		ConvenienceCharges convenienceCharge = new ConvenienceCharges();
		List<ConvenienceCharges> convenienceCharges = new ArrayList<>();
		convenienceCharge.setMid(mid);
		convenienceCharge.setPaymentMode(paymentMode);

		if (paymentMode.equals("DC") || paymentMode.equals("CC")) {
			convenienceCharge.setCardType(subType);
		} else if (subType != null && !subType.equals("") && paymentMode.equals("NB")) {

			try {
				logger.info("Subtype for NB  called==========>");
				Bank bank = bankRepo.findById(Long.valueOf(subType)).get();
				convenienceCharge.setBankId(bank.getId());
			} catch (Exception e) {
				new GlobalExceptionHandler().customException(e);
			}

		}

		BigDecimal amount = BigDecimal.valueOf(parseDouble);
		if (!paymentMode.equals("") && (paymentMode.equalsIgnoreCase("UPI") || (paymentMode.equalsIgnoreCase("wallet"))
				|| paymentMode.equalsIgnoreCase("NEFT") || paymentMode.equalsIgnoreCase("DYNAMICQR")
				|| paymentMode.equalsIgnoreCase("UNB")) || paymentMode.equalsIgnoreCase("UPIQR")) {

			convenienceCharges = convenienceChargesDao.findConvenienceChargeOtherPayment(convenienceCharge);
		} else {
			convenienceCharges = convenienceChargesDao.getChargesByMidPaymentModeAndCardType(convenienceCharge);
		}

		if (convenienceCharges.isEmpty()) {
			convenienceCharges = convenienceChargesDao.getChargesByMidPaymentModeAndCardTypeAll(convenienceCharge);
		}

		if (convenienceCharges != null && convenienceCharges.size() > 0) {
			convenienceCharge = convenienceCharges.get(0);
		}

		return RemoteDbUtil.getConvenienceCharges(convenienceCharge, parseDouble);
	}

	public DynamicQrResponse getDynamicQr2(Merchant merchant, CommissionModel cmodel,
			DynamicQrRequestModel paymentRequest, String vpaId, DynamicQrResponse response) {
		logger.info("getting request to generate qr=>" + paymentRequest.toString());
		String totalAmount = cmodel.getTotalStr();
		if (vpaId.toLowerCase().contains(".augp@aubank")) {
			List<PropertiesVo> properties = propertiesService.findByPropertykeyWithUpdatedCertsLike("AUQR_CONFIG_");
			Map<String, String> propertyMap = new HashMap<String, String>();
			for (PropertiesVo property : properties) {
				propertyMap.put(property.getPropertyKey(), property.getPropertyValue());
			}

			Config config = new Config();
			config.setApiKey(propertyMap.get(RemoteDbUtil.AUQR_CONFIG_API_KEY));
			config.setEncKey128(propertyMap.get(RemoteDbUtil.AUQR_CONFIG_API_ENC128));
			config.setEncKey256(propertyMap.get(RemoteDbUtil.AUQR_CONFIG_API_ENC256));
			config.setModString(propertyMap.get(RemoteDbUtil.AUQR_CONFIG_API_MODSTRING));
			config.setExpString(propertyMap.get(RemoteDbUtil.AUQR_CONFIG_API_EXPSTRING));
			config.setApiUrl(propertyMap.get(RemoteDbUtil.AUQR_CONFIG_API_URL));
			config.setApiUser(propertyMap.get(RemoteDbUtil.AUQR_CONFIG_API_USER));
			config.setMerchantCode(propertyMap.get(RemoteDbUtil.AUQR_CONFIG_API_MERCHANTCONFIG));

			Call call = new Call(config);
			String refVPA = vpaId.replace(".augp@aubank", "");
			String refId = RemoteDbUtil.REQUEST_DYNAMIC_QR_REFID + paymentRequest.getTransactionId();

//			GetQrResponse qrResponse = call.getQrDynamic(vpaId, String.valueOf(totalAmount),
//					String.valueOf(totalAmount), refId, "", false, merchant.getMobileNumber().trim(),
//					merchant.getMerchantName());

			String mccCode = "0000";
			if (merchant.getMccCode() != null && !merchant.getMccCode().equals("")) {
				mccCode = merchant.getMccCode();
			}
			GetQrResponse qrResponse = call.getQrDynamic2(vpaId, String.valueOf(totalAmount),
					String.valueOf(totalAmount), refId, "", false, merchant.getMobileNumber().trim(),
					merchant.getMerchantName(), mccCode);

			if (qrResponse != null) {
				logger.info("QR GENERATE RESPONSE ==> " + qrResponse.getTransactionInfo().getAttributes());
				logger.info("QR GENERATE RESPONSE   ==> " + qrResponse.getTransactionInfo().getAttributes());
				try {
					return generateAUDynamicQr(vpaId.trim(), qrResponse.getTransactionInfo().getAttributes().getQr(),
							paymentRequest, response);
				} catch (Exception e) {
					logger.info("Error in generating qr image ==> " + e.getMessage());
					logger.info(e.fillInStackTrace());
					new GlobalExceptionHandler().customException(e);
				}
			}

		} else if (vpaId.toLowerCase().contains("@utkarshbank")) {

			String refId = RemoteDbUtil.REQUEST_DYNAMIC_QR_REFID + paymentRequest.getTransactionId();

			String vpa = vpaId;

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pa", vpa); // VPA Id
			map.put("pn", merchant.getMerchantName()); // merchant name
			map.put("am", totalAmount);
			map.put("tr", refId);

			if (paymentRequest.getMinimumAmount() != null && !paymentRequest.getMinimumAmount().equals("")) {
				map.put("mam", paymentRequest.getMinimumAmount());
			}
			if (paymentRequest.getExpiryDate() != null && !paymentRequest.getExpiryDate().equals("")) {
				// map.put("am", totalAmount);
			}

			String url = "upi://pay?";
			url += map.entrySet().stream().map(x -> x.getKey() + "=" + x.getValue()).collect(Collectors.joining("&"));
			// String urlEncode = URLEncoder.encode(url, "UTF-8").replace(" ", "%20");
			return generateQr(refId, url, response);

		} else if (vpaId.toLowerCase().contains("@bom") || vpaId.toLowerCase().contains("@mahb")) {
			try {
				String refIdPart1 = RemoteDbUtil.REQUEST_DYNAMIC_QR_REFID;
				String refIdPart2 = String.valueOf(paymentRequest.getTransactionId());

				if ((refIdPart1 + refIdPart2).length() < 13) {
					while ((refIdPart1 + refIdPart2).length() < 13) {
						refIdPart2 = "0" + refIdPart2;
					}
				}

				String refId = refIdPart1 + refIdPart2;

//				String vpa = "Getepay." + refId + "@mahb";
//				if(vpaId.toLowerCase().contains("@bom")) {
//					 vpa = "Getepay." + refId + "@bom";
//				}
				// String vpa = "Getepay.merchant59020@icici";

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("pa", vpaId); // VPA Id
				map.put("pn", merchant.getMerchantName()); // merchant name
				map.put("am", totalAmount);
				map.put("tr", refId);

				if (paymentRequest.getMinimumAmount() != null && !paymentRequest.getMinimumAmount().equals("")) {
					map.put("mam", paymentRequest.getMinimumAmount());
				}
				if (paymentRequest.getExpiryDate() != null && !paymentRequest.getExpiryDate().equals("")) {
					// map.put("am", totalAmount);
				}

				String url = "upi://pay?";
				url += map.entrySet().stream().map(x -> x.getKey() + "=" + x.getValue())
						.collect(Collectors.joining("&"));
				// String urlEncode = URLEncoder.encode(url, "UTF-8").replace(" ", "%20");
				return generateQr(refId, url, response);

			} catch (Exception e) {
				logger.info(e.fillInStackTrace());
				new GlobalExceptionHandler().customException(e);
			}
		} else if (vpaId.toLowerCase().contains("@esaf")) {
			try {
				String refIdPart1 = RemoteDbUtil.REQUEST_DYNAMIC_QR_REFID;
				String refIdPart2 = String.valueOf(paymentRequest.getTransactionId());

				if ((refIdPart1 + refIdPart2).length() < 13) {
					while ((refIdPart1 + refIdPart2).length() < 13) {
						refIdPart2 = "0" + refIdPart2;
					}
				}

				String refId = refIdPart1 + refIdPart2;

				// String vpa = "Getepay." + refId + "@esaf";
				// String vpa = "Getepay.merchant59020@icici";

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("pa", vpaId); // VPA Id
				map.put("pn", merchant.getMerchantName()); // merchant name
				map.put("am", totalAmount);
				map.put("tr", refId);

				if (paymentRequest.getMinimumAmount() != null && !paymentRequest.getMinimumAmount().equals("")) {
					map.put("mam", paymentRequest.getMinimumAmount());
				}
				if (paymentRequest.getExpiryDate() != null && !paymentRequest.getExpiryDate().equals("")) {
					// map.put("am", totalAmount);
				}

				String url = "upi://pay?";
				url += map.entrySet().stream().map(x -> x.getKey() + "=" + x.getValue())
						.collect(Collectors.joining("&"));
				// String urlEncode = URLEncoder.encode(url, "UTF-8").replace(" ", "%20");
				return generateQr(refId, url, response);

			} catch (Exception e) {
				logger.info(e.fillInStackTrace());
				new GlobalExceptionHandler().customException(e);
			}
		}

		else {
			try {
				// String vpa = "Getepay.merchant59020@icici";

				String refId = null;

				DmoOnboarding dmoOnboarding = dmoOnboardingRepo.findByVpa(vpaId);
				Map<String, Object> map = new HashMap<String, Object>();
				if (dmoOnboarding != null) {

					String refIdPart1 = RemoteDbUtil.ICICI_REQUEST_DYNAMIC_QR_REFID;
					String refIdPart2 = String.valueOf(paymentRequest.getTransactionId());

					int temp = (refIdPart1 + refIdPart2).length();
					if (temp < 13) {
						while (temp < 13) {
							refIdPart2 = "0" + refIdPart2;
							temp = (refIdPart1 + refIdPart2).length();
						}
					}

					refId = refIdPart1 + refIdPart2;

					String vpa = "Getepay." + refId + "@icici";

					map.put("pa", vpaId); // VPA Id
					map.put("pn", merchant.getMerchantName()); // merchant name
					map.put("am", totalAmount);
					map.put("tr", refId);
				} else {
					String refIdPart1 = RemoteDbUtil.REQUEST_DYNAMIC_QR_REFID;
					String refIdPart2 = String.valueOf(paymentRequest.getTransactionId());

					int temp = (refIdPart1 + refIdPart2).length();
					if (temp < 13) {
						while (temp < 13) {
							refIdPart2 = "0" + refIdPart2;
							temp = (refIdPart1 + refIdPart2).length();
						}
					}

					refId = refIdPart1 + refIdPart2;

					String refIdPart3 = RemoteDbUtil.ICICI_REQUEST_DYNAMIC_QR_REFID;
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
					map.put("pa", vpa); // VPA Id
					map.put("pn", merchant.getMerchantName()); // merchant name
					map.put("am", totalAmount);
					map.put("tr", refId2);
				}

				if (paymentRequest.getMinimumAmount() != null && !paymentRequest.getMinimumAmount().equals("")) {
					map.put("mam", paymentRequest.getMinimumAmount());
				}
				if (paymentRequest.getExpiryDate() != null && !paymentRequest.getExpiryDate().equals("")) {
					// map.put("am", totalAmount);
				}

				String url = "upi://pay?";
				url += map.entrySet().stream().map(x -> x.getKey() + "=" + x.getValue())
						.collect(Collectors.joining("&"));
				// String urlEncode = URLEncoder.encode(url, "UTF-8").replace(" ", "%20");
				return generateQr(refId, url, response);

			} catch (Exception e) {
				logger.info(e.fillInStackTrace());
				new GlobalExceptionHandler().customException(e);
			}
		}
		logger.info("sending null against request=>" + paymentRequest.toString());
		return null;

	}

	public DynamicQrResponse getDynamicQr(Merchant merchant, CommissionModel cmodel, ConvenienceModel conmodel,
			DynamicQrRequestModel paymentRequest, String vpaId, DynamicQrResponse response) {
		logger.info("getting request to generate qr=>" + paymentRequest.toString());
		String totalamount = cmodel.getTotalStr();
		BigDecimal totalAmount = new BigDecimal(totalamount);
		totalAmount = totalAmount.add(conmodel.getCharges());
		if (vpaId.toLowerCase().contains(".augp@aubank")) {
			List<PropertiesVo> properties = propertiesService.findByPropertykeyWithUpdatedCertsLike("AUQR_CONFIG_");
			Map<String, String> propertyMap = new HashMap<String, String>();
			for (PropertiesVo property : properties) {
				propertyMap.put(property.getPropertyKey(), property.getPropertyValue());
			}

			Config config = new Config();
			config.setApiKey(propertyMap.get(RemoteDbUtil.AUQR_CONFIG_API_KEY));
			config.setEncKey128(propertyMap.get(RemoteDbUtil.AUQR_CONFIG_API_ENC128));
			config.setEncKey256(propertyMap.get(RemoteDbUtil.AUQR_CONFIG_API_ENC256));
			config.setModString(propertyMap.get(RemoteDbUtil.AUQR_CONFIG_API_MODSTRING));
			config.setExpString(propertyMap.get(RemoteDbUtil.AUQR_CONFIG_API_EXPSTRING));
			config.setApiUrl(propertyMap.get(RemoteDbUtil.AUQR_CONFIG_API_URL));
			config.setApiUser(propertyMap.get(RemoteDbUtil.AUQR_CONFIG_API_USER));
			config.setMerchantCode(propertyMap.get(RemoteDbUtil.AUQR_CONFIG_API_MERCHANTCONFIG));

			Call call = new Call(config);
			String refVPA = vpaId.replace(".augp@aubank", "");
			String refId = RemoteDbUtil.REQUEST_DYNAMIC_QR_REFID + paymentRequest.getTransactionId();

//			GetQrResponse qrResponse = call.getQrDynamic(vpaId, String.valueOf(totalAmount),
//					String.valueOf(totalAmount), refId, "", false, merchant.getMobileNumber().trim(),
//					merchant.getMerchantName());

			String mccCode = "0000";
			if (merchant.getMccCode() != null && !merchant.getMccCode().equals("")) {
				mccCode = merchant.getMccCode();
			}
			GetQrResponse qrResponse = call.getQrDynamic2(vpaId, String.valueOf(totalAmount),
					String.valueOf(totalAmount), refId, "", false, merchant.getMobileNumber().trim(),
					merchant.getMerchantName(), mccCode);

			if (qrResponse != null) {
				logger.info("QR GENERATE RESPONSE ==> " + qrResponse.getTransactionInfo().getAttributes());
				logger.info("QR GENERATE RESPONSE   ==> " + qrResponse.getTransactionInfo().getAttributes());
				try {
					return generateAUDynamicQr(vpaId.trim(), qrResponse.getTransactionInfo().getAttributes().getQr(),
							paymentRequest, response);
				} catch (Exception e) {
					logger.info("Error in generating qr image ==> " + e.getMessage());
					logger.info(e.fillInStackTrace());
					new GlobalExceptionHandler().customException(e);
				}
			}

		} else if (vpaId.toLowerCase().contains("@utkarshbank")) {

			String refId = RemoteDbUtil.REQUEST_DYNAMIC_QR_REFID + paymentRequest.getTransactionId();

			String vpa = vpaId;

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pa", vpa); // VPA Id
			map.put("pn", merchant.getMerchantName()); // merchant name
			map.put("am", totalAmount);
			map.put("tr", refId);

			if (totalAmount != null && !String.valueOf(totalAmount).equals("")) {
				map.put("mam", totalAmount);
			}
			if (paymentRequest.getExpiryDate() != null && !paymentRequest.getExpiryDate().equals("")) {
				// map.put("am", totalAmount);
			}

			String url = "upi://pay?";
			url += map.entrySet().stream().map(x -> x.getKey() + "=" + x.getValue()).collect(Collectors.joining("&"));
			// String urlEncode = URLEncoder.encode(url, "UTF-8").replace(" ", "%20");
			return generateQr(refId, url, response);

		} else if (vpaId.toLowerCase().contains("@bom") || vpaId.toLowerCase().contains("@mahb")) {
			try {
				String refIdPart1 = RemoteDbUtil.REQUEST_DYNAMIC_QR_REFID;
				String refIdPart2 = String.valueOf(paymentRequest.getTransactionId());

				if ((refIdPart1 + refIdPart2).length() < 13) {
					while ((refIdPart1 + refIdPart2).length() < 13) {
						refIdPart2 = "0" + refIdPart2;
					}
				}

				String refId = refIdPart1 + refIdPart2;

//				String vpa = "Getepay." + refId + "@mahb";
//				if(vpaId.toLowerCase().contains("@bom")) {
//					 vpa = "Getepay." + refId + "@bom";
//				}
				// String vpa = "Getepay.merchant59020@icici";

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("pa", vpaId); // VPA Id
				map.put("pn", merchant.getMerchantName()); // merchant name
				map.put("am", totalAmount);
				map.put("tr", refId);

				if (totalAmount != null && !String.valueOf(totalAmount).equals("")) {
					map.put("mam", totalAmount);
				}
				if (paymentRequest.getExpiryDate() != null && !paymentRequest.getExpiryDate().equals("")) {
					// map.put("am", totalAmount);
				}

				String url = "upi://pay?";
				url += map.entrySet().stream().map(x -> x.getKey() + "=" + x.getValue())
						.collect(Collectors.joining("&"));
				// String urlEncode = URLEncoder.encode(url, "UTF-8").replace(" ", "%20");
				return generateQr(refId, url, response);

			} catch (Exception e) {
				logger.info(e.fillInStackTrace());
				new GlobalExceptionHandler().customException(e);
			}
		} else if (vpaId.toLowerCase().contains("@esaf")) {
			try {
				String refIdPart1 = RemoteDbUtil.REQUEST_DYNAMIC_QR_REFID;
				String refIdPart2 = String.valueOf(paymentRequest.getTransactionId());

				if ((refIdPart1 + refIdPart2).length() < 13) {
					while ((refIdPart1 + refIdPart2).length() < 13) {
						refIdPart2 = "0" + refIdPart2;
					}
				}

				String refId = refIdPart1 + refIdPart2;

				// String vpa = "Getepay." + refId + "@esaf";
				// String vpa = "Getepay.merchant59020@icici";

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("pa", vpaId); // VPA Id
				map.put("pn", merchant.getMerchantName()); // merchant name
				map.put("am", totalAmount);
				map.put("tr", refId);

				if (totalAmount != null && !String.valueOf(totalAmount).equals("")) {
					map.put("mam", totalAmount);
				}
				if (paymentRequest.getExpiryDate() != null && !paymentRequest.getExpiryDate().equals("")) {
					// map.put("am", totalAmount);
				}

				String url = "upi://pay?";
				url += map.entrySet().stream().map(x -> x.getKey() + "=" + x.getValue())
						.collect(Collectors.joining("&"));
				// String urlEncode = URLEncoder.encode(url, "UTF-8").replace(" ", "%20");
				return generateQr(refId, url, response);

			} catch (Exception e) {
				logger.info(e.fillInStackTrace());
				new GlobalExceptionHandler().customException(e);
			}
		} else if (vpaId.toLowerCase().contains("@kotak")) {

			try {
				String refIdPart1 = RemoteDbUtil.REQUEST_DYNAMIC_QR_REFID;
				String refIdPart2 = String.valueOf(paymentRequest.getTransactionId());

				if ((refIdPart1 + refIdPart2).length() < 13) {
					while ((refIdPart1 + refIdPart2).length() < 13) {
						refIdPart2 = "0" + refIdPart2;
					}
				}

				String refId = refIdPart1 + refIdPart2;

				// String vpa = "Getepay." + refId + "@esaf";
				// String vpa = "Getepay.merchant59020@icici";

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("pa", vpaId); // VPA Id
				map.put("pn", merchant.getMerchantName()); // merchant name
				map.put("am", totalAmount);
				map.put("tr", refId);

				if (totalAmount != null && !String.valueOf(totalAmount).equals("")) {
					map.put("mam", totalAmount);
				}
				if (paymentRequest.getExpiryDate() != null && !paymentRequest.getExpiryDate().equals("")) {
					// map.put("am", totalAmount);
				}

				map.put("ver", "01");
				map.put("mode", "01");
				map.put("purpose", "00");
				map.put("mc", merchant.getMccCode());
				map.put("qrMedium", "01");

				String url = "upi://pay?";
				url += map.entrySet().stream().map(x -> x.getKey() + "=" + x.getValue())
						.collect(Collectors.joining("&"));
				// String urlEncode = URLEncoder.encode(url, "UTF-8").replace(" ", "%20");
				return generateQr(refId, url, response);

			} catch (Exception e) {
				logger.info(e.fillInStackTrace());
				new GlobalExceptionHandler().customException(e);
			}

		}

		else {
			try {
				// String vpa = "Getepay.merchant59020@icici";

				Gson gson = new Gson();
				ObjectMapper objectMapper = new ObjectMapper();
				Map<String, String> propMap = new HashMap<>();
				List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
				for (PropertiesVo property : PropertiesList) {
					propMap.put(property.getPropertyKey(), property.getPropertyValue());
				}

				String refId = null;

				DmoOnboarding dmoOnboarding = dmoOnboardingRepo.findByVpa(vpaId);
				Map<String, Object> map = new HashMap<String, Object>();
				if (dmoOnboarding != null) {

					logger.info("Dmo Onboarding details=========================>" + dmoOnboarding);

					String refIdPart1 = RemoteDbUtil.ICICI_REQUEST_DYNAMIC_QR_REFID;
					String refIdPart2 = String.valueOf(paymentRequest.getTransactionId());

					int temp = (refIdPart1 + refIdPart2).length();
					if (temp < 13) {
						while (temp < 13) {
							refIdPart2 = "0" + refIdPart2;
							temp = (refIdPart1 + refIdPart2).length();
						}
					}

					refId = refIdPart1 + refIdPart2;

					String vpa = "Getepay." + refId + "@icici";

					if (propMap.get(RemoteDbUtil.ENABLE_NEW_DYNAMIC_QR).equalsIgnoreCase("true")) {

						logger.info("inside new Dynamic Qr===========================================>");

						String merchantlist = propMap.get(RemoteDbUtil.ENABLE_NEW_DYNAMIC_QR_MID);
						boolean isMidsPresent = false;
						if (merchantlist != null && !merchantlist.equalsIgnoreCase("")) {
							logger.info(
									"Merchant id eligible for the new dynamic qr===========================================>"
											+ merchantlist.toString());
							String[] midArrays = merchantlist.split(",");
							String mercId = String.valueOf(merchant.getMid());
							for (String mid : midArrays) {
								if (mid.equalsIgnoreCase(mercId)) {
									isMidsPresent = true;
									break;
								}
							}

						}

						if (isMidsPresent) {
							try {
								Map<String, String> properties = new HashMap<String, String>();
								QrRequest iciciQrRequest = new QrRequest();
								iciciQrRequest.setAmount(String.valueOf(totalAmount));
								iciciQrRequest.setMerchantId(String.valueOf(dmoOnboarding.getEazypayMerchantID()));
								iciciQrRequest.setMerchantTranId(refId);
								iciciQrRequest.setBillNumber(paymentRequest.getMerchanttxnid());
								iciciQrRequest.setTerminalId(merchant.getMccCode());
								iciciQrRequest.setPayerAccount(merchant.getAccountNumber());
								iciciQrRequest.setPayerIFSC(merchant.getIfscCode());
								iciciQrRequest.setValidatePayerAccFlag("N");

								String requestString = gson.toJson(iciciQrRequest);

								logger.info("Updated Dynamic Qr Request========================>" + iciciQrRequest);
								String requestId = "";

								String icici_dynamic_Qr_Url = propMap.get(IciciDynamicQrCall.ICICI_DYNAMIC_QR_URL);

								icici_dynamic_Qr_Url = icici_dynamic_Qr_Url.replace("#merchantId",
										String.valueOf(dmoOnboarding.getParentMerchantID()));

								logger.info("Updated Dynamic Qr URL========================>" + icici_dynamic_Qr_Url);

								properties.put(IciciDynamicQrCall.ICICI_CPAYPUBLICCER_KEY,
										propMap.get(IciciDynamicQrCall.ICICI_PUBLIC_KEY_DYNAMIC_QR)); // enc
								properties.put(IciciDynamicQrCall.GETEPAY_ICICI_PRIVATE_KEY,
										propMap.get(IciciDynamicQrCall.ICICI_PRIVATE_KEY_DYNAMIC_QR)); // dec

								IciciHybridRequest iciciHybridRequest = IciciCompositPay.hybridEncryption(properties,
										requestString, requestId);
								String encRequest = gson.toJson(iciciHybridRequest);
								logger.info("Updated Dynamic encRequest ======================> " + encRequest);

								String responseString = IciciDynamicQrCall.postApiQr3(icici_dynamic_Qr_Url, encRequest);

								logger.info(
										"Updated Dynamic Qr responseString =======================> " + responseString);

								if (responseString != null && !responseString.equals("")) {
									IciciHybridRequest iciciHybridResponse = gson.fromJson(responseString,
											IciciHybridRequest.class);
									String decString = IciciCompositPay.hybridDeryption(properties,
											iciciHybridResponse);

									IciciDynamicQr3Response qr3Response = objectMapper.readValue(decString,
											IciciDynamicQr3Response.class);
									logger.info(
											" Updated Dynamic Qr Dec Response ===========================================>"
													+ qr3Response);

									if (qr3Response != null && qr3Response.getRefId() != null) {

										logger.info(
												"If RefId is not null===================>" + qr3Response.getRefId());
										refId = qr3Response.getRefId();

										map.put("pa", vpaId); // VPA Id
										map.put("pn", merchant.getMerchantName()); // merchant name
										map.put("am", totalAmount);
										map.put("tr", refId);
									}

									else {
										logger.info("If RefId is null===================>");
										return null;
									}
								} else {

									logger.info("If Response is null===================>");
									return null;
								}

							} catch (Exception e) {

								logger.info("Dynamic Qr Exception===============>");
								new GlobalExceptionHandler().customException(e);

								return null;
							}
						} else {
							map.put("pa", vpaId); // VPA Id
							map.put("pn", merchant.getMerchantName()); // merchant name
							map.put("am", totalAmount);
							map.put("tr", refId);
						}
					} else {
						map.put("pa", vpaId); // VPA Id
						map.put("pn", merchant.getMerchantName()); // merchant name
						map.put("am", totalAmount);
						map.put("tr", refId);
					}

				} else {
					String refIdPart1 = RemoteDbUtil.REQUEST_DYNAMIC_QR_REFID;
					String refIdPart2 = String.valueOf(paymentRequest.getTransactionId());

					int temp = (refIdPart1 + refIdPart2).length();
					if (temp < 13) {
						while (temp < 13) {
							refIdPart2 = "0" + refIdPart2;
							temp = (refIdPart1 + refIdPart2).length();
						}
					}

					refId = refIdPart1 + refIdPart2;

					String refIdPart3 = RemoteDbUtil.ICICI_REQUEST_DYNAMIC_QR_REFID;
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
					map.put("pa", vpa); // VPA Id
					map.put("pn", merchant.getMerchantName()); // merchant name
					map.put("am", totalAmount);
					map.put("tr", refId2);
				}

				if (totalAmount != null && !String.valueOf(totalAmount).equals("")) {
					map.put("mam", totalAmount);
				}
				if (paymentRequest.getExpiryDate() != null && !paymentRequest.getExpiryDate().equals("")) {
					// map.put("am", totalAmount);
				}

				String url = "upi://pay?";
				url += map.entrySet().stream().map(x -> x.getKey() + "=" + x.getValue())
						.collect(Collectors.joining("&"));
				// String urlEncode = URLEncoder.encode(url, "UTF-8").replace(" ", "%20");
				return generateQr(refId, url, response);

			} catch (Exception e) {
				logger.info(e.fillInStackTrace());
				new GlobalExceptionHandler().customException(e);
			}
		}
		logger.info("sending null against request=>" + paymentRequest.toString());
		return null;

	}

	public DynamicQrResponse generateQr(String merchantCode, String urlEncode, DynamicQrResponse response) {

		BitMatrix matrix;
		// String returnFilePath = null;
		// String url = null;
		int qrCodewidth = 200;
		int qrCodeheight = 200;
		try {
			String qrLocation = RemoteDbUtil.QR_PATH;
			String fileName = merchantCode + ".png";
			String filePath = merchantCode.trim() + ".png";
			;
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

				response.setIntentUrl(urlEncode);
				response.setQrPath(filePath);
				response.setStatus(1);
				response.setMessage("Dynamic qr generated successfully");
			} catch (Exception e) {
				logger.info(e.fillInStackTrace());
				new GlobalExceptionHandler().customException(e);
			}

			return response;

		} catch (Exception e) {
			logger.info(e.fillInStackTrace());
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public DynamicQrResponse generateAUDynamicQr(String vpa, String qrString, DynamicQrRequestModel paymentRequest,
			DynamicQrResponse response) {

		String url = null;
		String urlEncode = null;
		int qrCodewidth = 500;
		int qrCodeheight = 500;
		try {
			url = qrString.replace("<![CDATA[", "").replace("&amp;", "").replace("]]", "");
			urlEncode = URLEncoder.encode(url, "UTF-8").replace(" ", "%20");
			String merchantCode = vpa.replace(".augp@aubank", "");
			return generateQr(merchantCode, urlEncode, response);
		} catch (Exception e) {
//			logger.info(e.fillInStackTrace());
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public ResponseEntity<ResponseWrapper<String>> verifyOtp(RequestWrapper requestWrapper, String token)
			throws Exception {
		ResponseEntity<ResponseWrapper<String>> response = null;
		OTPVerifyWrapperRequest otpVerifyWrapperRequest = EncryptionUtil
				.decryptdata(String.valueOf(requestWrapper.getData()), token, OTPVerifyWrapperRequest.class);
		if (otpVerifyWrapperRequest.getType().equalsIgnoreCase("SBI")) {
			response = sbiOtpVerify(otpVerifyWrapperRequest.getPayResponse(), token);
		} else if (otpVerifyWrapperRequest.getType().equalsIgnoreCase("LYRA")) {
			response = lyraOtpVerify(otpVerifyWrapperRequest.getPayResponse(), token);
		} else if (otpVerifyWrapperRequest.getType().equalsIgnoreCase("KOTAK BANK")) {
			response = kotakBankOtpVerify(otpVerifyWrapperRequest.getPayResponse(), token);
		} else if (otpVerifyWrapperRequest.getType().equalsIgnoreCase("SIMULATOR")) {
			response = simOtpVerify(otpVerifyWrapperRequest.getPayResponse(), token);
		}
		return response;
	}

	private ResponseEntity<ResponseWrapper<String>> kotakBankOtpVerify(PayResponse payResponse, String token)
			throws Exception {
		TransactionLog transactionLog = transactionLogRepo.findById(Long.valueOf(payResponse.getTid().trim())).get();

		BigDecimal amount = requaryAmount(transactionLog);
		payResponse.setMerchantName(transactionLog.getMerchantName());

		if (payResponse.getOtp() == null || payResponse.getOtp().trim() == "") {
			payResponse.setAmount(String.valueOf(amount));
			payResponse.setMessage("Otp can not be null or Empty");
			return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payResponse, token),
					"Redirect kotakrupay-otp page", HttpStatus.BAD_REQUEST);
		}

		Gson gson = new Gson();
		BEPG bepg = null;
		if (payResponse.getDataBEPG() != null && !payResponse.getDataBEPG().equals("")) {
			String dataBEPGdecrypt = new String(Base64.getDecoder().decode(payResponse.getDataBEPG().getBytes()));
			bepg = gson.fromJson(dataBEPGdecrypt, BEPG.class);
		}
		logger.info("transactionUuid => " + bepg);

		List<PropertiesVo> properties = propertiesService.findAllPropertyWithUpdatedCerts();

		Map<String, String> propMap = new HashMap<String, String>();
		for (PropertiesVo vo : properties) {
			propMap.put(vo.getPropertyKey(), vo.getPropertyValue());
		}

		String responseCode = "";
		PaymentResponse pgresponse = new PaymentResponse();
		String txnStatus = "";
		String Stage = "";
		String ProcessorCode = "";

		MerchantSetting merchantSetting = merchantSettingRepo.findByMerchantIdAndPaymentModeAndProcessor(
				transactionLog.getMerchantId(), transactionLog.getPaymentMode(), transactionLog.getProcessor());

		if (bepg != null) {
			logger.info("<------ Kotak Verify Otp Api ------->");
//			VerifyOtpApiRequest verifyotprequest = kotakService.verifyOtpApiRequest(merchantSetting, transactionLog,
//					parameterMap, httpServletRequest);

			VerifyOtpApiRequest verifyotprequest = new VerifyOtpApiRequest();
			verifyotprequest.setBankId(merchantSetting.getSetting4());
			verifyotprequest.setMerchantId(merchantSetting.getMloginId());
			verifyotprequest.setTerminalId(merchantSetting.getSetting1());
			verifyotprequest.setOrderId(String.valueOf(transactionLog.getTransactionId()));
			verifyotprequest.setAccessCode(merchantSetting.getSetting2());
			verifyotprequest.setPgId(transactionLog.getProcessorTxnId());
			if (payResponse.getOtp() != null && !payResponse.getOtp().equals("")) {
				verifyotprequest.setOTP(payResponse.getOtp());
			}
			verifyotprequest.setOTPCancelled("");
			verifyotprequest.setSecureHash("");

			VerifyOtpApiResponse verifyotpresponse = KotakUtil.verifyOtpApi(verifyotprequest, merchantSetting, propMap);

			if (verifyotpresponse.getResponseCode() != null
					&& verifyotpresponse.getResponseCode().equalsIgnoreCase("RETRY")) {

				String otpCountString = payResponse.getOtpCount();
				int otpCount = 0;
				try {
					otpCount = Integer.valueOf(otpCountString);
				} catch (Exception e) {
					new GlobalExceptionHandler().customException(e);
				}
				otpCount++;
				payResponse.setOtpCount(String.valueOf(otpCount));
				payResponse.setMessage(verifyotpresponse.getResponseMessage());
				payResponse.setResponseCode(verifyotpresponse.getResponseCode());
				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payResponse, token),
						"Redirect kotakrupay-otp page", HttpStatus.OK);
			} else {
				payResponse.setMessage(verifyotpresponse.getResponseMessage());
				payResponse.setResponseCode(verifyotpresponse.getResponseCode());
			}

//			logger.info("<------ Kotak Authorization Api ------->");
//
//			AuthorizationRequest request = KotakUtil.authorizationRequest(merchantSetting, transactionLog,
//					propMap, payResponse);
//			AutorizationResponse autorizationResponse = KotakUtil.authorizationApi(request, merchantSetting, propMap);
//			logger.info("autorizationResponse => " + autorizationResponse);
//
//			logger.info("<------ Kotak Sale Status Api ------->");
//
//			SaleStatusQueryApiRequest salerequest = kotakService.saleStatusrequest(merchantSetting, transactionLog);
//			SaleStatusQueryApiResponse saleStatusQueryApiResponse = KotakUtil.saleStatusApi(salerequest,
//					merchantSetting, propMap);
//			logger.info("saleStatusQueryApiResponse " + saleStatusQueryApiResponse);
//
//			txnStatus = "FAILED";
//			Stage = "Transaction is Failed";
//			responseCode = "01";
//			
//			if (saleStatusQueryApiResponse != null && saleStatusQueryApiResponse.getResponseCode() != null) {
//				ProcessorCode = saleStatusQueryApiResponse.getResponseCode();
//				if (saleStatusQueryApiResponse.getResponseCode().equalsIgnoreCase("00")) {
//					responseCode = "00";
//					txnStatus = "SUCCESS";
//					Stage = "Transaction is successfully processed. ";
//					// pgPush Notification......
//					addtopgPushNotifiactionQueue(transactionLog);
//				} else {
//					transactionLog.setBankErrorMsg(saleStatusQueryApiResponse.getResponseMessage());
//				}
//			}
//
//			transactionLog.setResponseCode(responseCode);
//			transactionLog.setTxnStatus(txnStatus);
//			transactionLog.setStage(Stage);
//			transactionLog.setProcessorCode(ProcessorCode);
//			transactionLogRepo.save(transactionLog);
//			callbackService.addCallbackInQueue(transactionLog.getTransactionId());
		}

		return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payResponse, token),
				"Redirect lyraOtpSuccessPage page", HttpStatus.OK);

	}

	private ResponseEntity<ResponseWrapper<String>> simOtpVerify(PayResponse payResponse, String token)
			throws Exception {
		TransactionLog transactionLog = transactionLogRepo.findById(Long.valueOf(payResponse.getTid().trim())).get();
		if (transactionLog == null) {
			return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payResponse, token),
					"Invalid Request Id", HttpStatus.BAD_REQUEST);
		}
		BigDecimal amount = requaryAmount(transactionLog);
		payResponse.setMerchantName(transactionLog.getMerchantName());

		if (payResponse.getOtp() == null || payResponse.getOtp().trim() == "") {
			payResponse.setAmount(String.valueOf(amount));
			payResponse.setMessage("Otp can not be null or Empty");
			return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payResponse, token),
					"Redirect simulator page", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		Gson gson = new Gson();

		if (payResponse.getOtp().equals("123456")) {
			payResponse.setMessage("accepted");
			payResponse.setResponseCode("00");

			transactionLog.setResponseCode("00");
			transactionLog.setTxnStatus("Success");
			transactionLog.setStage("Transaction is successfully processed. ");
			transactionLog.setProcessorCode("00");

			// transactionDetails.setOrderNumber();
			transactionLogRepo.save(transactionLog);
			logger.info("transction updated : " + transactionLog.getTransactionId());

			return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payResponse, token),
					"Redirect simulator page", HttpStatus.OK);
		} else if (payResponse.getOtp().equals("000000")) {
			payResponse.setMessage("invalid otp");
			payResponse.setResponseCode("01");
			String counter = payResponse.getOtpCount();
			if (counter == null || counter.equals("")) {
				counter = "3";
			}
			int otpCount = 3;
			try {
				otpCount = Integer.valueOf(counter);
			} catch (Exception e) {
				new GlobalExceptionHandler().customException(e);
			}
			otpCount--;
			payResponse.setOtpCount(String.valueOf(otpCount));

			return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payResponse, token),
					"Redirect simulator page", HttpStatus.CREATED);
		} else {
			payResponse.setAmount(String.valueOf(amount));
			payResponse.setResponseCode("450");
			return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payResponse, token),
					"Redirect simulator page", HttpStatus.CREATED);

		}

	}

	private ResponseEntity<ResponseWrapper<String>> lyraOtpVerify(PayResponse payResponse, String token)
			throws Exception {
		try {
			TransactionLog transactionLog = transactionLogRepo.findById(Long.valueOf(payResponse.getTid().trim()))
					.get();

			BigDecimal amount = requaryAmount(transactionLog);
			payResponse.setMerchantName(transactionLog.getMerchantName());

			if (payResponse.getOtp() == null || payResponse.getOtp().trim() == "") {
				payResponse.setAmount(String.valueOf(amount));
				payResponse.setMessage("Otp can not be null or Empty");
				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payResponse, token),
						"Redirect lyra-h2hotp page", HttpStatus.CREATED);
			}
			if (payResponse.getIpAddress() == null || payResponse.getIpAddress().trim() == "") {
				payResponse.setAmount(String.valueOf(amount));
				payResponse.setMessage("IP Address is not present");
				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payResponse, token),
						"Redirect lyra-h2hotp page", HttpStatus.CREATED);
			}

			LyraOtpRequest lyraOtpRequest = new LyraOtpRequest();
			lyraOtpRequest.setOtp(payResponse.getOtp());
			lyraOtpRequest.setIpAddress(payResponse.getIpAddress());

			Gson gson = new Gson();
			com.mb.getepay.icici.lyra.action.Call call = new com.mb.getepay.icici.lyra.action.Call("",
					payResponse.getBaseUrlTxn(), payResponse.getAuthoriztion());

			String requestString = gson.toJson(lyraOtpRequest);
			logger.info("verifyotp => " + requestString);

			String response = call.verifyOtpDetails(requestString, payResponse.getUuid());
			logger.info("Lyra Verify otp responseString ==> " + response);
			LyraOtpResponse lyraOtpResponse = gson.fromJson(response, LyraOtpResponse.class);
//		LyraOtpResponse lyraOtpResponse = new LyraOtpResponse();
//		lyraOtpResponse.setMessage("accepted");
//		lyraOtpResponse.setResponseCode("00");
			logger.info("Lyra Verify otp response ==> " + lyraOtpResponse);
			payResponse.setMessage(lyraOtpResponse.getMessage());

			if (lyraOtpResponse.getResponseCode().equals("00")) {
				payResponse.setAmount(String.valueOf(transactionLog.getAmt()));

				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payResponse, token),
						"Redirect lyraOtpSuccessPage page", HttpStatus.OK);
			} else if (lyraOtpResponse.getResponseCode().equals("450")) {

				String counter = payResponse.getOtpCount();
				if (counter == null || counter.equals("")) {
					payResponse.setOtpCount("2");
				}
				payResponse.setAmount(String.valueOf(amount));
				payResponse.setResponseCode("450");
				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payResponse, token),
						"Redirect lyra-h2hotp page", HttpStatus.CREATED);
			} else if (lyraOtpResponse.getResponseCode().equals("451")) {
				payResponse.setAmount(String.valueOf(amount));
				payResponse.setResponseCode("451");
				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payResponse, token),
						"Redirect lyra-h2hotp page", HttpStatus.CREATED);
			} else if (lyraOtpResponse.getResponseCode().equals("452")) {
				payResponse.setAmount(String.valueOf(amount));
				payResponse.setResponseCode("452");
				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payResponse, token),
						"Redirect lyra-h2hotp page", HttpStatus.CREATED);
			} else {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//					.body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Invalid Request", null));
				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payResponse, token),
						"Redirect lyra-h2hotp page", HttpStatus.CREATED);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL_SERVER_ERROR", null));
		}
	}

	private ResponseEntity<ResponseWrapper<String>> sbiOtpVerify(PayResponse payResponse, String token)
			throws Exception {
		logger.info("Inside SBI  Rupay Response  => tID : " + payResponse.getTid());
		Gson gson = new Gson();
		AuthorizeApiResponse authresponse = new AuthorizeApiResponse();
		String responseCode = "01";
		Map<String, String> parameterMap = new HashMap<>();
		PaymentResponse pgresponse = new PaymentResponse();
		TransactionEssentials transactionEssentials = new TransactionEssentials();
		CardDetailsVo carddetailsVo = new CardDetailsVo();
		SBITokenResponse sbitokenResponse = new SBITokenResponse();

		String cardType = "R";
		String txnStatus = "FAILED";

		Map<String, String> propMap = new HashMap<>();
		List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
		for (PropertiesVo property : PropertiesList) {
			propMap.put(property.getPropertyKey(), property.getPropertyValue());
		}
		TransactionLog transactionDetails = transactionLogRepo.findById(Long.valueOf(payResponse.getTid().trim()))
				.get();
		MerchantSetting merchantSetting = new MerchantSetting();

		merchantSetting = merchantSettingDao.findByMerchantSettingId(transactionDetails.getMerchantSettingId());
		Merchant merchant = merchantRepo.findByMid(merchantSetting.getMerchantId());
		String secretKeyString = propMap.get(SBICardsUtils.SBI_TOKEN_DEC_SECRET_KEY);

		if (merchantSetting != null) {

			TokenHeader tokenheader = new TokenHeader();

			tokenheader.setClientId(propMap.get(SBICardsUtils.SBI_TOKEN_CLIENT_ID));
			tokenheader.setClientApiKey(propMap.get(SBICardsUtils.SBI_TOKEN_CLIENT_API_KEY));
			tokenheader.setClientApiUser(propMap.get(SBICardsUtils.SBI_TOKEN_CLIENT_API_USER));

			logger.info("SBI   token Header============>" + gson.toJson(tokenheader)
					+ " For transaction_id=============================>" + transactionDetails.getTransactionId());

			SbiRequestHeader header = new SbiRequestHeader();

			header.setXapikey(propMap.get(SBICardsUtils.SBI_HEADER_XAPI_KEY));
			header.setPgInstanceId(propMap.get(SBICardsUtils.SBI_RUPAY_HEADER_PG_INSTANCE_ID));
			header.setMerchantId(propMap.get(SBICardsUtils.SBI_RUPAY_HEADER_MERCHANT_ID));

			logger.info("Rupay Response Headers======>" + header + " For transaction_id=============================>"
					+ transactionDetails.getTransactionId());

			BigDecimal mAmount = requaryAmount(transactionDetails);
			int amount = mAmount.multiply(new BigDecimal(100)).intValue();

			transactionEssentials = transactionEssentialsRepo
					.findByTransactionId(transactionDetails.getTransactionId());

			if (merchantSetting.getProcessor() != null && merchantSetting.getProcessor().equalsIgnoreCase("SBI BANK")) {
				if (payResponse.getProductType() != null && payResponse.getProductType().equalsIgnoreCase("SEAMLESS")) {
					VerifyOTPResponse verifyOtpResponse = SbiCardUtilCall.verifyotp(
							String.valueOf(transactionDetails.getTransactionId()), payResponse.getOtp(), header,
							propMap, merchant, merchantSetting, transactionDetails);
					if (verifyOtpResponse != null && verifyOtpResponse.getErrorcode().equalsIgnoreCase("450")) {

//						payResponse.setCardNo(parameterMap.get("cardNo"));
						payResponse.setAmount(String.valueOf(Utilities.requaryAmount(transactionDetails)));
						payResponse.setMerchantName(transactionDetails.getMerchantName());
						payResponse.setTid(String.valueOf(transactionDetails.getTransactionId()));
//						payResponse.setProductType(parameterMap.get("productType"));
//						payResponse.setPgTransactionId(parameterMap.get("pgTransactionId"));
						payResponse.setMessage(verifyOtpResponse.getErrormsg());
						return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payResponse, token),
								"Redirect sbirupay-otp page", HttpStatus.OK);
					}

					if (verifyOtpResponse != null && verifyOtpResponse.getErrorcode().equalsIgnoreCase("00")) {

						transactionEssentials.setUdf40(verifyOtpResponse.getTranCtxId()); // TransCtxId
						transactionEssentialsRepo.save(transactionEssentials);

						sbitokenResponse = SbiCardUtilCall.generateAltIdToken(carddetailsVo, tokenheader, propMap,
								merchant, merchantSetting, transactionDetails, transactionEssentials, cardType, amount,
								transactionEssentials.getUdf41());

						if (sbitokenResponse != null && sbitokenResponse.getStatusCode().equalsIgnoreCase("TK0000")) {

							authresponse = SbiCardUtilCall.authorize(header, propMap, merchant, merchantSetting,
									transactionDetails, sbitokenResponse, secretKeyString, parameterMap);
							if (authresponse != null && authresponse.getStatus().equalsIgnoreCase("50020")) {
								responseCode = "00";
								txnStatus = "SUCCESS";
								transactionDetails.setProcessorTxnId(authresponse.getRrn());
								// pgPush Notification......
								addtopgPushNotifiactionQueue(transactionDetails);

							} else {
								responseCode = "01";
								txnStatus = "FAILED";
								transactionDetails.setBankErrorMsg(authresponse.getPgErrorDetail());

							}
						} else {
							responseCode = "01";
							txnStatus = "FAILED";
							transactionDetails.setBankErrorMsg(sbitokenResponse.getErrorDesc());
						}

					}

					else {
						responseCode = "01";
						txnStatus = "FAILED";
					}
				} else {
					try {

						sbitokenResponse = SbiCardUtilCall.generateAltIdToken(carddetailsVo, tokenheader, propMap,
								merchant, merchantSetting, transactionDetails, transactionEssentials, cardType, amount,
								transactionEssentials.getUdf41());

						if (sbitokenResponse != null && sbitokenResponse.getStatusCode().equalsIgnoreCase("TK0000")) {
							parameterMap.put("pgTransactionId", String.valueOf(transactionDetails.getTransactionId()));
							parameterMap.put("productType", payResponse.getProductType());
							authresponse = SbiCardUtilCall.authorize(header, propMap, merchant, merchantSetting,
									transactionDetails, sbitokenResponse, secretKeyString, parameterMap);
							if (authresponse != null && authresponse.getStatus().equalsIgnoreCase("50020")) {
								responseCode = "00";
								txnStatus = "SUCCESS";
								transactionDetails.setProcessorTxnId(authresponse.getRrn());
								// pgPush Notification......
								addtopgPushNotifiactionQueue(transactionDetails);

							} else {
								responseCode = "01";
								txnStatus = "FAILED";
								transactionDetails.setBankErrorMsg(authresponse.getPgErrorDetail());
							}
						} else {
							responseCode = "01";
							txnStatus = "FAILED";
							transactionDetails.setBankErrorMsg(sbitokenResponse.getErrorDesc());
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}

		}
		transactionDetails.setResponseCode(responseCode);
		transactionDetails.setTxnStatus(txnStatus);
		transactionDetails.setStage("Transaction is successfully processed. ");
		transactionDetails.setProcessorCode("00");

		// transactionDetails.setOrderNumber();
		transactionLogRepo.save(transactionDetails);

		callBackService.addCallbackInQueue(transactionDetails.getTransactionId());

		if (transactionDetails != null) {
			BigDecimal amount = requaryAmount(transactionDetails);
			logger.info("Amount added with convience charges and commision charges=====>" + amount);
			pgresponse.setAmt(transactionDetails.getAmt().setScale(2, RoundingMode.HALF_UP).toString());
			pgresponse.setmId(transactionDetails.getMerchantId());
			pgresponse.setMerchantTxnId(transactionDetails.getMerchanttxnid());
			pgresponse.setStatus(transactionDetails.getTxnStatus());

			pgresponse.setTransactionId(transactionDetails.getTransactionId());
			pgresponse.setRu(transactionDetails.getRu());
			pgresponse.setMerchantTxnId(transactionDetails.getMerchanttxnid());
			pgresponse.setUdf1(transactionDetails.getUdf1());
			pgresponse.setUdf2(transactionDetails.getUdf2());
			pgresponse.setUdf3(transactionDetails.getUdf3());
			pgresponse.setUdf4(transactionDetails.getUdf4());
			pgresponse.setUdf5(transactionDetails.getUdf5());
			pgresponse.setPaymentMode(transactionDetails.getPaymentMode());

			pgresponse.setTotalCharges(Utilities.getSurcharge(transactionDetails));
			pgresponse.setTotalAmount(String.valueOf(amount));
			logger.info("Pg Response ==========================>" + pgresponse);

			String signatureKey = SignatureGenerator.signatureGeneration(new String[] { pgresponse.getmId().toString(),
					pgresponse.getMerchantTxnId(), pgresponse.getAmt(), pgresponse.getStatus() },
					pgresponse.getMerchantTxnId());
			pgresponse.setSignature(signatureKey);
		}

		logger.info("FAILED  RESPONSE to Processor : txnId ==> " + pgresponse.getTransactionId() + " Response code ==> "
				+ pgresponse.getResponseCode() + " Timing ==>" + LocalDate.now());
		payResponse.setPaymentResponse(pgresponse);
//		try {
//			List<PropertiesVo> keyProperties = propertiesService
//					.findByPropertykeyWithUpdatedCertsLike("%GETEPAY_PAYOUT_%");
//			if (keyProperties != null) {
//				logger.info("Property size=>" + keyProperties.size());
//			}
//			MerchantCallbackSetting callbackSetting = merchantCallbackSettingRepo
//					.findByMidAndStatus(transactionDetails.getMerchantId(), 1);
//			if (callbackSetting != null && callbackSetting.getId() != null && callbackSetting.getId() > 0) {
//				CallbackThread callbackThread = new CallbackThread(transactionDetails, callbackSetting, keyProperties);
//				Thread t1 = new Thread(callbackThread);
//				t1.start();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payResponse, token),
				"Redirect process page", HttpStatus.OK);

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

	public void addtopgPushNotifiactionQueue(TransactionLog log) {
		try {

			logger.info("Inside add to pg Push Notifiaction..............");
			PgPushNotificationRequest pgPushNotificationRequest = new PgPushNotificationRequest();
			pgPushNotificationRequest.setPgTxnId(String.valueOf(log.getTransactionId()));
			pgPushNotificationRequest.setPortalTxnId(log.getMerchanttxnid());

			callBackService.addpgPushNotification(pgPushNotificationRequest);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ResponseEntity<ResponseWrapper<String>> resendOtp(RequestWrapper requestWrapper, String token)
			throws Exception {
		ResponseEntity<ResponseWrapper<String>> response = null;
		OTPVerifyWrapperRequest otpVerifyWrapperRequest = EncryptionUtil
				.decryptdata(String.valueOf(requestWrapper.getData()), token, OTPVerifyWrapperRequest.class);
		if (otpVerifyWrapperRequest.getType().equalsIgnoreCase("SBI")) {
			response = sbiResendOtp(otpVerifyWrapperRequest.getPayResponse(), token);
		} else if (otpVerifyWrapperRequest.getType().equalsIgnoreCase("LYRA")) {
			response = lyraResendOtp(otpVerifyWrapperRequest.getPayResponse(), token);
		} else if (otpVerifyWrapperRequest.getType().equalsIgnoreCase("SIMULATOR")) {
			response = simResendOtp(otpVerifyWrapperRequest.getPayResponse(), token);
		}
		return response;
	}

	private ResponseEntity<ResponseWrapper<String>> simResendOtp(PayResponse payResponse, String token)
			throws Exception {

		TransactionLog transactionLog = transactionLogRepo.findById(Long.valueOf(payResponse.getTid().trim())).get();
		if (transactionLog == null) {
			return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payResponse, token),
					"Invalid Request Id", HttpStatus.BAD_REQUEST);
		}
		BigDecimal amount = requaryAmount(transactionLog);
		payResponse.setMerchantName(transactionLog.getMerchantName());

		payResponse.setMessage("otp send successfully ");
		payResponse.setResponseCode("00");
		return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payResponse, token),
				"Redirect simulatorRedirction page", HttpStatus.OK);

	}

	private ResponseEntity<ResponseWrapper<String>> sbiResendOtp(PayResponse payResponse, String token)
			throws Exception {
		Map<String, String> propMap = new HashMap<>();
		List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
		for (PropertiesVo property : PropertiesList) {
			propMap.put(property.getPropertyKey(), property.getPropertyValue());
		}
		TransactionLog transactionDetails = transactionLogRepo.findById(Long.valueOf(payResponse.getTid().trim()))
				.get();
		MerchantSetting merchantSetting = new MerchantSetting();
		String responseCode = "01";
		String txnStatus = "FAILED";
		PaymentResponse pgresponse = new PaymentResponse();

		merchantSetting = merchantSettingDao.findByMerchantSettingId(transactionDetails.getMerchantSettingId());
		Merchant merchant = merchantRepo.findByMid(merchantSetting.getMerchantId());

		if (merchantSetting != null) {
			SbiRequestHeader header = new SbiRequestHeader();

//			header.setXapikey("849ca23e-b115-11ed-a376-005056b59d84");
//			header.setPgInstanceId("72702415");
//			header.setMerchantId("72702415");

			header.setXapikey(propMap.get(SBICardsUtils.SBI_HEADER_XAPI_KEY));
			header.setPgInstanceId(propMap.get(SBICardsUtils.SBI_RUPAY_HEADER_PG_INSTANCE_ID));
			header.setMerchantId(propMap.get(SBICardsUtils.SBI_RUPAY_HEADER_MERCHANT_ID));

			logger.info("SBI Cards Rupay Resend Otp Headers======>" + header
					+ " For transaction_id=============================>" + transactionDetails.getTransactionId());

			ResendOTPResponse resendOtpResponse = SbiCardUtilCall.resendotp(
					String.valueOf(transactionDetails.getTransactionId()), header, propMap, merchant, merchantSetting,
					transactionDetails);

			if (resendOtpResponse != null && (resendOtpResponse.getErrorcode().equalsIgnoreCase("453")
					|| resendOtpResponse.getErrorcode().equalsIgnoreCase("10024"))) {
				responseCode = "01";
				txnStatus = "FAILED";
				transactionDetails.setBankErrorMsg(resendOtpResponse.getErrormsg());
				transactionDetails.setResponseCode(responseCode);
				transactionDetails.setTxnStatus(txnStatus);
				transactionDetails.setStage("Transaction is successfully processed. ");
				transactionDetails.setProcessorCode("00");

				transactionLogRepo.save(transactionDetails);

//				callBackService.addCallbackInQueue(transactionDetails.getTransactionId());

				if (transactionDetails != null) {
					BigDecimal amount = requaryAmount(transactionDetails);
					logger.info("Amount added with convience charges and commision charges=====>" + amount);
					pgresponse.setAmt(transactionDetails.getAmt().setScale(2, RoundingMode.HALF_UP).toString());
					pgresponse.setmId(transactionDetails.getMerchantId());
					pgresponse.setMerchantTxnId(transactionDetails.getMerchanttxnid());
					pgresponse.setStatus(transactionDetails.getTxnStatus());

					pgresponse.setTransactionId(transactionDetails.getTransactionId());
					pgresponse.setRu(transactionDetails.getRu());
					pgresponse.setMerchantTxnId(transactionDetails.getMerchanttxnid());
					pgresponse.setUdf1(transactionDetails.getUdf1());
					pgresponse.setUdf2(transactionDetails.getUdf2());
					pgresponse.setUdf3(transactionDetails.getUdf3());
					pgresponse.setUdf4(transactionDetails.getUdf4());
					pgresponse.setUdf5(transactionDetails.getUdf5());
					pgresponse.setPaymentMode(transactionDetails.getPaymentMode());

					pgresponse.setTotalCharges(Utilities.getSurcharge(transactionDetails));
					pgresponse.setTotalAmount(String.valueOf(amount));
					logger.info("Pg Response ==========================>" + pgresponse);

					String signatureKey = SignatureGenerator
							.signatureGeneration(
									new String[] { pgresponse.getmId().toString(), pgresponse.getMerchantTxnId(),
											pgresponse.getAmt(), pgresponse.getStatus() },
									pgresponse.getMerchantTxnId());
					pgresponse.setSignature(signatureKey);
				}

				logger.info("FAILED  RESPONSE to Processor : txnId ==> " + pgresponse.getTransactionId()
						+ " Response code ==> " + pgresponse.getResponseCode() + " Timing ==>" + LocalDate.now());
				payResponse.setPaymentResponse(pgresponse);
				
//				try {
//					List<PropertiesVo> keyProperties = propertiesService
//							.findByPropertykeyWithUpdatedCertsLike("%GETEPAY_PAYOUT_%");
//					if (keyProperties != null) {
//						logger.info("Property size=>" + keyProperties.size());
//					}
//					MerchantCallbackSetting callbackSetting = merchantCallbackSettingRepo
//							.findByMidAndStatus(transactionDetails.getMerchantId(), 1);
//					if (callbackSetting != null && callbackSetting.getId() != null && callbackSetting.getId() > 0) {
//						CallbackThread callbackThread = new CallbackThread(transactionDetails, callbackSetting,
//								keyProperties);
//						Thread t1 = new Thread(callbackThread);
//						t1.start();
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}

				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payResponse, token),
						"Redirect process page", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			payResponse.setAmount(String.valueOf(requaryAmount(transactionDetails)));
			payResponse.setMerchantName(transactionDetails.getMerchantName());
			payResponse.setProductType("SEAMLESS");
			payResponse.setMessage(resendOtpResponse.getErrormsg());
			return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payResponse, token),
					"Redirect sbirupay-otp page", HttpStatus.OK);

		}

		return null;
	}

	private ResponseEntity<ResponseWrapper<String>> lyraResendOtp(PayResponse payResponse, String token)
			throws Exception {
		String tid = payResponse.getTid();
		TransactionLog transactionLog = transactionLogRepo.findById(Long.valueOf(tid.trim())).get();

		BigDecimal amount = requaryAmount(transactionLog);
		payResponse.setAmount(String.valueOf(amount));
		payResponse.setMerchantName(transactionLog.getMerchantName());

		com.mb.getepay.icici.lyra.action.Call call = new com.mb.getepay.icici.lyra.action.Call("",
				payResponse.getBaseUrlTxn(), payResponse.getAuthoriztion());

		String response = call.reGenerateOtp(null, payResponse.getUuid());

		logger.info("Lyra Re-Generate otp response ==> " + response);
		payResponse.setMessage("Otp Send Successfully");
		return ResponseBuilder.buildResponse(encryptionService.encryptResponse(payResponse, token),
				"Redirect lyra-h2hotp page", HttpStatus.OK);
	}

	public ResponseEntity<ResponseWrapper<String>> upiCollect(RequestWrapper requestWrapper, String token)
			throws Exception {
		UpiCollectResponse upiCollectResponse = EncryptionUtil.decryptdata(String.valueOf(requestWrapper.getData()),
				token, UpiCollectResponse.class);
		String tid = upiCollectResponse.getTransactionId();
		TransactionLog transaction = transactionLogRepo.findById(Long.valueOf(tid.trim())).get();
		logger.info("Upi Collect transaction log======>" + transaction);
		if (transaction != null && transaction.getResponseCode() != null && transaction.getResponseCode().equals("02")
				&& transaction.getProcessorCode() != null && transaction.getProcessorCode().equals("475")) {
			logger.info("Inside if Processor code is 475===========>");
			upiCollectResponse.setTransactionId(tid);
			upiCollectResponse.setUdf7(transaction.getUdf7());

			BigDecimal amount = transaction.getAmt();
			if (transaction.getCommision() != null && transaction.getCommisionType() != null
					&& transaction.getCommisionType().equalsIgnoreCase("Excl")) {
				amount = amount.add(transaction.getCommision());
			}
			upiCollectResponse.setAmount(String.valueOf(amount));
			upiCollectResponse.setCurrency(transaction.getTxncurr());
			return ResponseBuilder.buildResponse(encryptionService.encryptResponse(upiCollectResponse, token),
					"Redirect upicollect page", HttpStatus.OK);
		} else {
			return ResponseBuilder.buildResponse(encryptionService.encryptResponse(upiCollectResponse, token),
					"Redirect invalid page", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<ResponseWrapper<String>> upiIciciCollect(RequestWrapper requestWrapper, String token)
			throws Exception {
		UpiCollectResponse upiCollectResponse = EncryptionUtil.decryptdata(String.valueOf(requestWrapper.getData()),
				token, UpiCollectResponse.class);
		String tid = upiCollectResponse.getTransactionId();
		TransactionLog transactionDetails = transactionLogRepo.findById(Long.valueOf(tid.trim())).get();

		PaymentResponse pgresponse = new PaymentResponse();
		if (transactionDetails != null && transactionDetails.getTxnStatus() != null
				&& transactionDetails.getTxnStatus().equalsIgnoreCase("SUCCESS")) {
			BigDecimal amount = requaryAmount(transactionDetails);
			logger.info("Amount added with convience charges and commision charges=====>" + amount);
			pgresponse.setAmt(transactionDetails.getAmt().setScale(2, RoundingMode.HALF_UP).toString());
			pgresponse.setmId(transactionDetails.getMerchantId());
			pgresponse.setMerchantTxnId(transactionDetails.getMerchanttxnid());
			pgresponse.setStatus(transactionDetails.getTxnStatus());
			pgresponse.setTransactionId(transactionDetails.getTransactionId());
			pgresponse.setRu(transactionDetails.getRu());
			pgresponse.setUdf1(transactionDetails.getUdf1());
			pgresponse.setUdf2(transactionDetails.getUdf2());
			pgresponse.setUdf3(transactionDetails.getUdf3());
			pgresponse.setUdf4(transactionDetails.getUdf4());
			pgresponse.setUdf5(transactionDetails.getUdf5());
			pgresponse.setPaymentMode(transactionDetails.getPaymentMode());
			pgresponse.setTotalCharges(Utilities.getSurcharge(transactionDetails));
			pgresponse.setTotalAmount(String.valueOf(amount));
			logger.info("Pg Response ==========================>" + pgresponse);
			String signatureKey = SignatureGenerator.signatureGeneration(new String[] { pgresponse.getmId().toString(),
					pgresponse.getMerchantTxnId(), pgresponse.getAmt(), pgresponse.getStatus() },
					pgresponse.getMerchantTxnId());
			pgresponse.setSignature(signatureKey);
		} else {

			// Call requery..
			BigDecimal amount = requaryAmount(transactionDetails);
			logger.info("Amount added with convience charges and commision charges=====>" + amount);
			pgresponse.setAmt(transactionDetails.getAmt().setScale(2, RoundingMode.HALF_UP).toString());
			pgresponse.setmId(transactionDetails.getMerchantId());
			pgresponse.setMerchantTxnId(transactionDetails.getMerchanttxnid());
			pgresponse.setStatus(transactionDetails.getTxnStatus());
			pgresponse.setTransactionId(transactionDetails.getTransactionId());
			pgresponse.setRu(transactionDetails.getRu());
			pgresponse.setMerchantTxnId(transactionDetails.getMerchanttxnid());
			pgresponse.setUdf1(transactionDetails.getUdf1());
			pgresponse.setUdf2(transactionDetails.getUdf2());
			pgresponse.setUdf3(transactionDetails.getUdf3());
			pgresponse.setUdf4(transactionDetails.getUdf4());
			pgresponse.setUdf5(transactionDetails.getUdf5());
			pgresponse.setPaymentMode(transactionDetails.getPaymentMode());
			pgresponse.setTotalCharges(String.valueOf(
					new BigDecimal(transactionDetails.getTotalServiceCharge()).add(transactionDetails.getCommision())));
			pgresponse.setTotalAmount(String.valueOf(amount));
			logger.info("Pg Response ==========================>" + pgresponse);
			String signatureKey = SignatureGenerator.signatureGeneration(new String[] { pgresponse.getmId().toString(),
					pgresponse.getMerchantTxnId(), pgresponse.getAmt(), pgresponse.getStatus() },
					pgresponse.getMerchantTxnId());
			pgresponse.setSignature(signatureKey);
		}
		logger.info("FAILED  RESPONSE to Processor : txnId ==> " + pgresponse.getTransactionId() + " Response code ==> "
				+ pgresponse.getResponseCode() + " Timing ==>" + LocalDate.now());
		logger.info("FAILED  RESPONSE to Processor : txnId ==> " + pgresponse.getTransactionId() + " Response code ==> "
				+ pgresponse.getResponseCode() + " Timing ==>" + LocalDate.now());
		upiCollectResponse.setPaymentResponse(pgresponse);
		return ResponseBuilder.buildResponse(encryptionService.encryptResponse(upiCollectResponse, token),
				"Redirect process page", HttpStatus.OK);
	}

	public ResponseEntity<ResponseWrapper<String>> validateCollect(RequestWrapper requestWrapper, String token)
			throws Exception {
		UpiCollectResponse upiCollectResponse = EncryptionUtil.decryptdata(String.valueOf(requestWrapper.getData()),
				token, UpiCollectResponse.class);
		String tid = upiCollectResponse.getTransactionId();
		TransactionLog transaction = transactionLogRepo.findById(Long.valueOf(tid.trim())).get();
//		if (transaction != null && transaction.getResponseCode() != null && !transaction.getResponseCode().equals("02")
//				&& transaction.getProcessorCode() != null && !transaction.getProcessorCode().equals("475")) {
//			upiCollectResponse.setMessage("Y");
//		} else {
//			upiCollectResponse.setMessage("N");
//		}
		if (transaction != null && transaction.getResponseCode() != null && !transaction.getResponseCode().equals("02")
				&& transaction.getProcessorCode() != null && !transaction.getProcessorCode().equals("475")) {
			upiCollectResponse.setMessage("Y");
		} else if (transaction != null && transaction.getResponseCode() != null
				&& transaction.getResponseCode().equals("02") && transaction.getProcessorCode() != null
				&& transaction.getProcessorCode().equals("475")
				&& transaction.getTxnStatus().equalsIgnoreCase("FAILED")) {
			upiCollectResponse.setMessage("F");
		} else {
			upiCollectResponse.setMessage("N");
		}

		return ResponseBuilder.buildResponse(encryptionService.encryptResponse(upiCollectResponse, token), "SUCCESS",
				HttpStatus.OK);
	}

	public ResponseEntity<ResponseWrapper<String>> redirectProcess(RequestWrapper requestWrapper, String token)
			throws Exception {

		RedirectRequestVo redirectReqVo = EncryptionUtil.decryptdata(String.valueOf(requestWrapper.getData()), token,
				RedirectRequestVo.class);
		logger.info("redirect process called : txnId ==> " + redirectReqVo.getTransactionId());

		String tid = redirectReqVo.getTransactionId();
		RedirectResponseVo resVo = new RedirectResponseVo();

		try {

			TransactionLog transactionDetails = transactionLogRepo.findById(Long.valueOf(tid.trim())).get();
			logger.info(" transactionDetails =========================>" + transactionDetails);

			if (transactionDetails != null) {

//				RedirectPaymentResponseVo pgresponse = new RedirectPaymentResponseVo();
//				BigDecimal amount = requaryAmount(transactionDetails);
//				logger.info("Amount added with convience charges and commision charges=====>" + amount);
//				pgresponse.setGetepayTxnId(transactionDetails.getTransactionId());
//				pgresponse.setMid(transactionDetails.getMerchantId());
//				pgresponse.setTxnAmount(transactionDetails.getAmt().setScale(2, RoundingMode.HALF_UP).toPlainString());
//				pgresponse.setTotalCharges(Utilities.getSurcharge(transactionDetails));
//				pgresponse.setTotalAmount(String.valueOf(amount));
//				pgresponse.setTxnStatus(transactionDetails.getTxnStatus());
//				pgresponse.setMerchantOrderNo(transactionDetails.getMerchanttxnid());
//				pgresponse.setUdf1(transactionDetails.getUdf1());
//				pgresponse.setUdf2(transactionDetails.getUdf2());
//				pgresponse.setUdf3(transactionDetails.getUdf3());
//				pgresponse.setUdf4(transactionDetails.getUdf4());
//				pgresponse.setUdf5(transactionDetails.getUdf5());
//				pgresponse.setDescription("");
//				pgresponse.setDiscriminator(transactionDetails.getPaymentMode());
//				pgresponse.setTid(String.valueOf(tid));
//				logger.info("Pg Response ==========================>" + pgresponse);
//				String signatureKey = SignatureGenerator.signatureGeneration(
//						new String[] { pgresponse.getMid().toString(), pgresponse.getMerchantOrderNo(),
//								pgresponse.getTxnAmount(), pgresponse.getTxnStatus() },
//						pgresponse.getMerchantOrderNo());
//				pgresponse.setSignature(signatureKey);
//
//				resVo.setRedirectUrl(transactionDetails.getRu());
//				resVo.setPaymentResponse(pgresponse);

//				if (transactionDetails.getTxnStatus().equalsIgnoreCase("FAILED")) {
//					PropertiesVo properties2 = propertiesService
//							.findByPropertykeyWithUpdatedCerts("PAYMENT_RETRY_PAGE_ENABLE_FLAG");
//					if (properties2 != null && properties2.getPropertyValue() != null
//							&& properties2.getPropertyValue().equalsIgnoreCase("true")) {
//						resVo.setRetry("true");
//					}
//				}

				Gson gson = new Gson();
				IntermediateTransaction transaction = intermediateTransactionRepo.findByTransactionId(Long.valueOf(transactionDetails.getMerchanttxnid()));
				
				if (!transaction.getStatus().equalsIgnoreCase("SUCCESS")) {
					PropertiesVo properties2 = propertiesService
							.findByPropertykeyWithUpdatedCerts("PAYMENT_RETRY_PAGE_ENABLE_FLAG");
					if (properties2 != null && properties2.getPropertyValue() != null
							&& properties2.getPropertyValue().equalsIgnoreCase("true")) {
						resVo.setRetry("true");
					}
				}
				
				
				Invoice invoice = invoiceRepo.findByToken(token);
				Long portalMid = transaction.getMid();
				String terminalId = invoice.getVpa();
				MerchantKeys merchantKeys = merchantKeysRepo.findByMidAndTerminalId(portalMid, terminalId);
				GcmPgEncryption gcmPgEncryption = new GcmPgEncryption(merchantKeys.getIv(), merchantKeys.getKey());
				MerchantRuResponseVo merchantRuResponseVo = fromIntermediateTransaction(transaction, invoice);
				
				String responseEncrypted = gcmPgEncryption.encryptWithMKeys(gson.toJson(merchantRuResponseVo));
				
				MerchantRuResponseWrapper merchantRuResponseWrapper = new MerchantRuResponseWrapper();
				merchantRuResponseWrapper.setResponse(responseEncrypted);
				merchantRuResponseWrapper.setMid(String.valueOf(transaction.getMid()));
				merchantRuResponseWrapper.setTerminalId(terminalId);
				merchantRuResponseWrapper.setStatus("SUCCESS");
				
				resVo.setPaymentResponse(merchantRuResponseWrapper);
				resVo.setRedirectUrl(transaction.getRu());
				

				logger.info("Redirect response : txnId ==> " + transactionDetails.getTransactionId() + " RedictUrl ==> "
						+ resVo.getRedirectUrl() + " Timing ==>" + LocalDateTime.now());
				logger.info("Redirect response :"+gson.toJson(resVo));
				return ResponseBuilder.buildResponse(encryptionService.encryptResponse(resVo, token),
						"Redirect process page", HttpStatus.OK);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Invalid Request", null));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error !!", null));
		}
	}

	public ResponseEntity<ResponseWrapper<String>> cancelRedirection(RequestWrapper requestWrapper, String token)
			throws Exception {

		try {

			PaymentDeclineVo cancel_request = EncryptionUtil.decryptdata(String.valueOf(requestWrapper.getData()),
					token, PaymentDeclineVo.class);
			logger.info("cancelRedirection called :" + cancel_request);

			if (cancel_request != null && cancel_request.getTransactionId() != null
					&& cancel_request.getTransactionId() > 0) {

				String responseCode = "01";
				String txnStatus = "FAILED";
				String reason = cancel_request.getReason();

				RedirectResponseVo resVo = new RedirectResponseVo();

				Long tid = cancel_request.getTransactionId();
				TransactionLog transactionDetails = transactionLogRepo.findById(tid).get();
				logger.info(" transactionDetails =========================>" + transactionDetails);
				if (transactionDetails != null) {

					if (transactionDetails != null && transactionDetails.getTxnStatus() != null
							&& transactionDetails.getTxnStatus().equalsIgnoreCase("SUCCESS")) {

						return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(
								HttpStatus.INTERNAL_SERVER_ERROR.value(), "Invalid Payment Url", null));
					}

					transactionDetails.setResponseCode(responseCode);
					transactionDetails.setTxnStatus(txnStatus);
					transactionDetails.setStage("Transaction is Failed.");
					transactionDetails.setBankErrorMsg(reason);
					transactionDetails.setProcessorCode("01");
					transactionLogRepo.save(transactionDetails);

					callbackService.addCallbackInQueue(transactionDetails.getTransactionId());

					BigDecimal amount = requaryAmount(transactionDetails);
					logger.info("Amount added with convience charges and commision charges=====>" + amount);
//					RedirectPaymentResponseVo pgresponse = new RedirectPaymentResponseVo();
//					pgresponse.setGetepayTxnId(transactionDetails.getTransactionId());
//					pgresponse.setMid(transactionDetails.getMerchantId());
//					pgresponse.setTxnAmount(
//							transactionDetails.getAmt().setScale(2, RoundingMode.HALF_UP).toPlainString());
//					pgresponse.setTotalCharges(Utilities.getSurcharge(transactionDetails));
//					pgresponse.setTotalAmount(String.valueOf(amount));
//					pgresponse.setTxnStatus(transactionDetails.getTxnStatus());
//					pgresponse.setMerchantOrderNo(transactionDetails.getMerchanttxnid());
//					pgresponse.setUdf1(transactionDetails.getUdf1());
//					pgresponse.setUdf2(transactionDetails.getUdf2());
//					pgresponse.setUdf3(transactionDetails.getUdf3());
//					pgresponse.setUdf4(transactionDetails.getUdf4());
//					pgresponse.setUdf5(transactionDetails.getUdf5());
//					pgresponse.setDescription("");
//					pgresponse.setDiscriminator(transactionDetails.getPaymentMode());
//					logger.info("Pg Response ==========================>" + pgresponse);
//					String signatureKey = SignatureGenerator.signatureGeneration(
//							new String[] { pgresponse.getMid().toString(), pgresponse.getMerchantOrderNo(),
//									pgresponse.getTxnAmount(), pgresponse.getTxnStatus() },
//							pgresponse.getMerchantOrderNo());
//					pgresponse.setSignature(signatureKey);
//					pgresponse.setTid(String.valueOf(tid));
//
//					resVo.setPaymentResponse(pgresponse);
//					resVo.setRedirectUrl(transactionDetails.getRu());

					PropertiesVo properties2 = propertiesService
							.findByPropertykeyWithUpdatedCerts("PAYMENT_RETRY_PAGE_ENABLE_FLAG");
					if (properties2 != null && properties2.getPropertyValue() != null
							&& properties2.getPropertyValue().equalsIgnoreCase("true")) {
						resVo.setRetry("true");
					}

					IntermediateTransaction transaction = intermediateTransactionRepo.findByTransactionId(Long.valueOf(transactionDetails.getMerchanttxnid()));
					if (transaction != null && transaction.getStatus() != null
							&& transaction.getStatus().equalsIgnoreCase("SUCCESS")) {

						return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(
								HttpStatus.INTERNAL_SERVER_ERROR.value(), "Invalid Payment Url", null));
					}
					transaction.setStatus(txnStatus);
					intermediateTransactionRepo.save(transaction);
					
					Invoice invoice = invoiceRepo.findByToken(token);
					Long portalMid = transaction.getMid();
					String terminalId = invoice.getVpa();
					MerchantKeys merchantKeys = merchantKeysRepo.findByMidAndTerminalId(portalMid, terminalId);
					GcmPgEncryption gcmPgEncryption = new GcmPgEncryption(merchantKeys.getIv(), merchantKeys.getKey());
					
					MerchantRuResponseVo merchantRuResponseVo = fromIntermediateTransaction(transaction, invoice);
					
					Gson gson = new Gson();
					String responseEncrypted = gcmPgEncryption.encryptWithMKeys(gson.toJson(merchantRuResponseVo));
					
					MerchantRuResponseWrapper merchantRuResponseWrapper = new MerchantRuResponseWrapper();
					merchantRuResponseWrapper.setResponse(responseEncrypted);
					merchantRuResponseWrapper.setMid(String.valueOf(transaction.getMid()));
					merchantRuResponseWrapper.setTerminalId(terminalId);
					merchantRuResponseWrapper.setStatus("SUCCESS");
					
					resVo.setPaymentResponse(merchantRuResponseWrapper);
					resVo.setRedirectUrl(transaction.getRu());
					
					logger.info("Redirect response : txnId ==> " + transactionDetails.getTransactionId() + " RedictUrl ==> "
							+ resVo.getRedirectUrl() + " Timing ==>" + LocalDateTime.now());
					logger.info("Redirect response :"+gson.toJson(resVo));
					return ResponseBuilder.buildResponse(encryptionService.encryptResponse(resVo, token),
							"Redirect process page", HttpStatus.OK);

				} else {
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(
							HttpStatus.INTERNAL_SERVER_ERROR.value(), "Invalid Payment Url", null));
				}

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Invalid Payment Url", null));
			}
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error !!", null));
		}
	}
	
	public static MerchantRuResponseVo fromIntermediateTransaction(IntermediateTransaction intermedateTransaction,
			Invoice invoice) {
		MerchantRuResponseVo response = null;
		try {
			response = new MerchantRuResponseVo();
			response.setDiscriminator(intermedateTransaction.getUdf43());
			response.setGetepayTxnId(String.valueOf(intermedateTransaction.getTransactionId()));
			response.setMerchantOrderNo(intermedateTransaction.getOrderNumber());
			response.setMid(String.valueOf(intermedateTransaction.getMid()));
			response.setPaymentMode(intermedateTransaction.getPaymentType());
			response.setTxnAmount(String.valueOf(intermedateTransaction.getTxnAmount()));
			response.setTxnStatus(intermedateTransaction.getStatus());
			if (intermedateTransaction.getUdf1() != null) {
				response.setUdf1(intermedateTransaction.getUdf1());
			}
			if (intermedateTransaction.getUdf2() != null) {
				response.setUdf2(intermedateTransaction.getUdf2());
			}
			if (intermedateTransaction.getUdf3() != null) {
				response.setUdf3(intermedateTransaction.getUdf3());
			}
			if (intermedateTransaction.getUdf4() != null) {
				response.setUdf4(intermedateTransaction.getUdf4());
			}
			if (intermedateTransaction.getUdf5() != null) {
				response.setUdf5(intermedateTransaction.getUdf5());
			}
			if (invoice.getUdf8() != null) {
				String[] arr = invoice.getUdf8().split("\\|");
				if (arr.length > 1) {
					response.setUdf6(arr[1]);
				}
			}
			if (intermedateTransaction.getUdf12() != null) {
				response.setUdf7(intermedateTransaction.getUdf12());
			}
			if (intermedateTransaction.getUdf13() != null) {
				response.setUdf8(intermedateTransaction.getUdf13());
			}
			if (intermedateTransaction.getUdf14() != null) {
				response.setUdf9(intermedateTransaction.getUdf14());
			}
			if (intermedateTransaction.getUdf15() != null) {
				response.setUdf10(intermedateTransaction.getUdf15());
			}
			if (intermedateTransaction.getUdf41() != null) {
				response.setUdf41(intermedateTransaction.getUdf41());
			}
			if (intermedateTransaction.getUdf7() != null) {
				response.setCustRefNo(intermedateTransaction.getUdf7());
			}
			if (intermedateTransaction.getTxnDatetime() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				response.setTxnDate(sdf.format(intermedateTransaction.getTxnDatetime()));
			}
			if (intermedateTransaction.getSurcharge() != 0) {
				response.setSurcharge(String.valueOf(intermedateTransaction.getSurcharge()));
			}
			response.setTxnNote(String.valueOf(intermedateTransaction.getRemark()));
			response.setPaymentStatus(intermedateTransaction.getStatus());
			response.setAgentName(intermedateTransaction.getAgentName());
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return response;
	}

	public ResponseEntity<ResponseWrapper<String>> processPaymentResponse(MerchantRuResponseWrapper requestWrapper) throws Exception {

			Gson gson = new Gson();
			logger.info("processPaymentResponse Request :"+gson.toJson(requestWrapper));
			String portalMid = requestWrapper.getMid();
			String terminalId = requestWrapper.getTerminalId();
			String encResponse = requestWrapper.getResponse();
			
			MerchantKeys merchantKeys = merchantKeysRepo.findByMidAndTerminalId(Long.valueOf(portalMid), terminalId);
			GcmPgEncryption gcmPgEncryption = new GcmPgEncryption(merchantKeys.getIv(), merchantKeys.getKey());
			String responseEncrypted = gcmPgEncryption.decryptWithMKeys(encResponse);
			logger.info("processPaymentResponse Request :"+responseEncrypted);
			return ResponseBuilder.buildResponse(responseEncrypted,	"Redirect payment response", HttpStatus.OK);
		
	}
	
	
}
