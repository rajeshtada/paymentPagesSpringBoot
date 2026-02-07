
package com.ftk.pg.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ftk.pg.dao.MerchantCommisionDao;
import com.ftk.pg.dto.PaymentRequest;
import com.ftk.pg.dto.ResponseBuilder;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.Bank;
import com.ftk.pg.modal.CardBean;
import com.ftk.pg.modal.Merchant;
import com.ftk.pg.modal.MerchantCommision;
import com.ftk.pg.modal.TransactionEssentials;
import com.ftk.pg.modal.TransactionLog;
import com.ftk.pg.repo.BankRepo;
import com.ftk.pg.repo.CardBeanRepo;
import com.ftk.pg.repo.TransactionEssentialsRepo;
import com.ftk.pg.repo.UpiQrDetailRepo;
import com.ftk.pg.requestvo.CardBinRequest;
import com.ftk.pg.requestvo.CardBinRequestWrapper;
import com.ftk.pg.requestvo.PropertiesVo;
import com.ftk.pg.responsevo.CardBinResponse;
import com.ftk.pg.responsevo.CardBinResponseWrapper;
import com.ftk.pg.util.CardBinUtils;
import com.ftk.pg.util.CommissionModel;
import com.ftk.pg.util.CustomExceptionLoggerError;
import com.ftk.pg.util.FrmUtils;
import com.ftk.pg.util.Utilities;
import com.google.gson.Gson;
import com.pgcomponent.security.SecureCardData;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MerchantCommisionService {

	static Logger logger = LogManager.getLogger(MerchantCommisionService.class);

	private final String MERCHANT_COMMSSION_VALUE = "MERCHANT_COMMSSION_VALUE";

	private final String MERCHANT_COMMSSION_ID = "MERCHANT_COMMSSION_ID";

	private final BankRepo bankRepo;

	private final MerchantCommisionDao merchantCommisionDao;

	private final UpiQrDetailRepo upiQrDetailRepo;

	private final PropertiesService propertiesService;

	private final CardBeanRepo cardbeanDao;

	private final TransactionEssentialsRepo transactionEssentialsRepo;

	public void getMerchantCommission(TransactionLog transactionLog, PaymentRequest paymentRequest, Merchant merchant) {

		MerchantCommision merchantCommision = null;
		Long merchantId = transactionLog.getMerchantId();
		BigDecimal amount = transactionLog.getAmt().setScale(2, BigDecimal.ROUND_HALF_UP);
		String paymentMode = paymentRequest.getPaymentMode();
		String subType = null;

		String carddata = paymentRequest.getCarddata();
		String[] carddata_enc = carddata.split("\\|");
		String cardNo = carddata_enc[0];

		if (paymentMode != null && (paymentMode.equalsIgnoreCase("DC") || paymentMode.equalsIgnoreCase("CC"))) {
			CardBean cardBean = paymentRequest.getCardBean();
			if (cardBean == null) {
				if (cardNo.startsWith("4")) {
					subType = "VISA";
				} else {
					subType = "MASTER";
				}
			} else {
				try {
					if (cardBean.getCardType().equals("MASTERCARD")) {
						cardBean.setCardType("MASTER");
					}
					subType = cardBean.getCardType();
					if (cardBean.getDomesticInternational().equalsIgnoreCase("I")
							|| cardBean.getDomesticInternational().equalsIgnoreCase("P")) {
						subType = subType + "_" + cardBean.getDomesticInternational();
					}

				} catch (Exception e) {
					new GlobalExceptionHandler().customException(e);
				}

			}
		} else if (paymentMode != null && paymentMode.equalsIgnoreCase("NB")) {
			subType = paymentRequest.getBankid();
		}

		Map<String, String> propMap = new HashMap<>();
		List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
		for (PropertiesVo property : PropertiesList) {
			propMap.put(property.getPropertyKey(), property.getPropertyValue());
		}

		merchantCommision = getMerchantCommisionData(merchantId, paymentMode, amount.doubleValue(),
				paymentRequest.getProductType(), subType, propMap);

		if (merchantCommision != null) {

			BigDecimal cAmount = amount;
			BigDecimal commision = BigDecimal.ZERO;
			BigDecimal commisionValue = merchantCommision.getCommisionvalue();

			if (commisionValue.compareTo(BigDecimal.ZERO) == 1) {
				if (merchantCommision.getCommisionType().equalsIgnoreCase("Percentage")) {
					commision = amount.multiply(commisionValue).divide(new BigDecimal("100"));
				} else {
					commision = commisionValue;
				}
				if (merchantCommision != null && merchantCommision.getChargeType().equalsIgnoreCase("Excl")) {
					cAmount = amount.add(commision);
				}
			}
			transactionLog.setCommision(commision);
			transactionLog.setCommisionType(merchantCommision.getChargeType());
			transactionLog.setCommisionId(merchantCommision.getId());
			paymentRequest.setAmt(cAmount);

			String merchantState = null;
			if (merchant != null && merchant.getState() != null) {
				merchantState = merchant.getState();
			}
			String getepayState = "Rajasthan";

			BigDecimal netCommissionAmt = transactionLog.getCommision();
			netCommissionAmt = netCommissionAmt.multiply(new BigDecimal("100"));
			netCommissionAmt = netCommissionAmt.divide(new BigDecimal("118"), RoundingMode.HALF_UP);

			if (merchantState != null && !merchantState.equalsIgnoreCase(getepayState)) {

				BigDecimal gstPercent = new BigDecimal("0.18");
				BigDecimal gst = netCommissionAmt.multiply(gstPercent);
				gst = gst.setScale(2, RoundingMode.HALF_UP);
				transactionLog.setIgstAmount(gst);
				transactionLog.setSgstAmount(BigDecimal.ZERO);
				transactionLog.setCgstAmount(BigDecimal.ZERO);

			} else {

				BigDecimal gstPercent = new BigDecimal("0.09");
				BigDecimal gst = netCommissionAmt.multiply(gstPercent);
				gst = gst.setScale(2, RoundingMode.HALF_UP);
				transactionLog.setCgstAmount(gst);
				transactionLog.setSgstAmount(gst);
				transactionLog.setIgstAmount(BigDecimal.ZERO);
			}

		} else {
			transactionLog.setCommision(new BigDecimal("0"));
		}
	}

	public void getMerchantCommission2(TransactionLog transactionLog, PaymentRequest paymentRequest,
			Merchant merchant) {

		MerchantCommision merchantCommision = null;
		BigDecimal commision;
		Long merchantId = transactionLog.getMerchantId();
		BigDecimal amount = transactionLog.getAmt().setScale(2, BigDecimal.ROUND_HALF_UP);
		String paymentMode = paymentRequest.getPaymentMode();
		String conSubType = null;
		String subType = null;

		MerchantCommision merchantCommisionObj = new MerchantCommision();
		merchantCommisionObj.setMerchantId(merchantId);
		merchantCommisionObj.setPaymentMode(paymentMode);
		merchantCommisionObj.setProductType(paymentRequest.getProductType());
		merchantCommisionObj.setCommisionvalue(amount);

		String carddata = paymentRequest.getCarddata();
		String[] carddata_enc = new String[4];
		String cardno = "";
		String carddatadec = "";
		carddata_enc = carddata.split("\\|");
		cardno = carddata_enc[0];

		if (paymentMode.trim().equalsIgnoreCase("DC") || paymentMode.trim().equalsIgnoreCase("CC")) {

			logger.info("Caluclating Convience Charges for CC and DC ============>");
			SecureCardData obj1 = new SecureCardData();
			logger.info("Encrypt card data - " + cardno + " ------- " + paymentRequest.getMerchantTxnId());
			try {
				carddatadec = obj1.decryptData(cardno, merchant.getMerchantPrivateKey());
			} catch (Exception e1) {
				new GlobalExceptionHandler().customException(e1);
			}
			if (carddatadec.contains("|")) {
				try {
					String card_data_array[] = carddatadec.split("\\|");
					cardno = card_data_array[0];
				} catch (Exception e) {
					new GlobalExceptionHandler().customException(e);
				}
			} else {
				cardno = carddatadec;
			}
			int value = Integer.parseInt(cardno.substring(0, 9));
			CardBean cardBean = paymentRequest.getCardBean();

			if (cardBean != null && cardBean.getId() != null && cardBean.getId() > 0) {
				subType = cardBean.getCardType();
				if (cardBean.getCardType().equals("MASTERCARD")) {
					String cardTypeMaster = "MASTER";
					subType = cardTypeMaster;
					logger.info("Card Type======>" + cardTypeMaster);

					conSubType = cardTypeMaster + "_" + cardBean.getDomesticInternational();
					if (conSubType.equalsIgnoreCase("MASTER_I") || conSubType.equalsIgnoreCase("MASTER_C")) {
						subType = conSubType;
					}
				} else {

					conSubType = cardBean.getCardType() + "_" + cardBean.getDomesticInternational();
					if (conSubType.equalsIgnoreCase("VISA_I") || conSubType.equalsIgnoreCase("MASTER_I")
							|| conSubType.equalsIgnoreCase("VISA_C") || conSubType.equalsIgnoreCase("MASTER_C")) {
						subType = conSubType;
					}
				}
			}

		}

		if (paymentMode.trim().equalsIgnoreCase("NB")) {
			Bank bank = bankRepo.findById(Long.parseLong(paymentRequest.getBankid())).get();
			merchantCommisionObj.setSubType(bank.getBankName());
			merchantCommision = merchantCommisionDao.findByMidAndBank(merchantCommisionObj);
			if (merchantCommision == null) {
				merchantCommisionObj.setCommisionvalue(null);
				merchantCommision = merchantCommisionDao.findByMidAndBank(merchantCommisionObj);
			}
		} else if (paymentMode.trim().equalsIgnoreCase("DC")) {
			if (transactionLog.getUdf10().equalsIgnoreCase("RUPAY")) {
				transactionLog.setCommision(new BigDecimal("0"));
				return;
			}

			merchantCommisionObj.setSubType(subType);
			merchantCommision = merchantCommisionDao.findByMidAndBank(merchantCommisionObj);

			if (merchantCommision == null) {
				merchantCommisionObj.setSubType(transactionLog.getUdf10().toUpperCase());
				merchantCommision = merchantCommisionDao.findByMidAndBank(merchantCommisionObj);
			}

			if (merchantCommision == null) {
				merchantCommisionObj.setCommisionvalue(null);
				merchantCommision = merchantCommisionDao.findByMidAndBank(merchantCommisionObj);
			}
			if (merchantCommision == null) {
				merchantCommisionObj.setSubType("ALL");
				merchantCommisionObj.setCommisionvalue(amount);
				merchantCommision = merchantCommisionDao.findByMidAndBank(merchantCommisionObj);
			}
			if (merchantCommision == null) {
				merchantCommisionObj.setSubType("ALL");
				merchantCommisionObj.setCommisionvalue(null);
				merchantCommision = merchantCommisionDao.findByMidAndBank(merchantCommisionObj);
			}
		} else if (paymentMode.trim().equalsIgnoreCase("CC")) {

			merchantCommisionObj.setSubType(conSubType);
			merchantCommision = merchantCommisionDao.findByMidAndBank(merchantCommisionObj);

			if (merchantCommision == null) {
				merchantCommisionObj.setSubType(transactionLog.getUdf10().toUpperCase());
				merchantCommision = merchantCommisionDao.findByMidAndBank(merchantCommisionObj);
			}

			if (merchantCommision == null) {
				merchantCommisionObj.setCommisionvalue(null);
				merchantCommision = merchantCommisionDao.findByMidAndBank(merchantCommisionObj);
			}
			if (merchantCommision == null) {
				merchantCommisionObj.setSubType("ALL");
				merchantCommisionObj.setCommisionvalue(amount);
				merchantCommision = merchantCommisionDao.findByMidAndBank(merchantCommisionObj);
			}
			if (merchantCommision == null) {
				merchantCommisionObj.setSubType("ALL");
				merchantCommisionObj.setCommisionvalue(null);
				merchantCommision = merchantCommisionDao.findByMidAndBank(merchantCommisionObj);
			}

		}

		else {

			merchantCommision = merchantCommisionDao.findByMidAndPayMode(merchantCommisionObj);
			if (merchantCommision == null) {
				logger.info("Error in getMerchantCommission findByMidAndPayMode :: " + merchantCommisionObj);
				merchantCommisionObj.setCommisionvalue(null);
				merchantCommision = merchantCommisionDao.findByMidAndPayMode(merchantCommisionObj);
			}
		}

		if (merchantCommision == null) {
			try {
				merchantCommisionObj = new MerchantCommision();
				merchantCommisionObj.setMerchantId(merchantId);
				merchantCommisionObj.setPaymentMode(paymentMode);
				merchantCommisionObj.setProductType(paymentRequest.getProductType());
				merchantCommisionObj.setCommisionvalue(amount);
				merchantCommision = merchantCommisionDao.findDefaultCommison(merchantCommisionObj);
			} catch (Exception e) {
			}
		}
		if (merchantCommision == null) {
			merchantCommisionObj = new MerchantCommision();
			merchantCommisionObj.setMerchantId(merchantId);
			merchantCommisionObj.setPaymentMode(paymentMode);
			merchantCommisionObj.setProductType(paymentRequest.getProductType());
			try {
				merchantCommision = merchantCommisionDao.findDefaultCommison(merchantCommisionObj);
			} catch (Exception e) {
			}
		}
		if (merchantCommision != null) {

			Map<String, String> propMap = new HashMap<>();
			List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
			for (PropertiesVo property : PropertiesList) {
				propMap.put(property.getPropertyKey(), property.getPropertyValue());
			}
			Long commisiionId = Long.valueOf(propMap.get(MERCHANT_COMMSSION_ID));

			if (String.valueOf(commisiionId).equalsIgnoreCase(String.valueOf(merchantCommision.getId()))) {
				String cv = propMap.get(MERCHANT_COMMSSION_VALUE);

				if (cv != null && !cv.equalsIgnoreCase("")) {
					BigDecimal bg = BigDecimal.valueOf(Double.valueOf(cv));
					merchantCommision.setCommisionvalue(bg);
				}

			}

			BigDecimal cAmount = amount;
			if (merchantCommision.getCommisionType().equalsIgnoreCase("Percentage")) {
				if (merchantCommision != null && merchantCommision.getChargeType().equalsIgnoreCase("Excl")) {
					BigDecimal commisionValue = merchantCommision.getCommisionvalue();
					if (commisionValue.compareTo(BigDecimal.ZERO) == 1) {
						commision = amount.multiply(commisionValue).divide(new BigDecimal("100"));
						cAmount = amount.add(commision);
					} else {
						commision = BigDecimal.ZERO;
						cAmount = amount.add(commision);
					}
					// transactionLog.setAmt(cAmount);
					transactionLog.setCommision(commision);
					transactionLog.setCommisionType(merchantCommision.getChargeType());
					transactionLog.setCommisionId(merchantCommision.getId());
					paymentRequest.setAmt(cAmount);

				} else if (merchantCommision != null && merchantCommision.getChargeType().equalsIgnoreCase("Incl")) {
					BigDecimal commisionValue = merchantCommision.getCommisionvalue();
					if (commisionValue.compareTo(BigDecimal.ZERO) == 1) {
						// commision = amount.divide(commisionValue.add(new
						// BigDecimal("100"))).multiply(commisionValue);
						commision = amount.multiply(commisionValue).divide(new BigDecimal("100"));
					} else {
						commision = BigDecimal.ZERO;
					}
					transactionLog.setCommision(commision);
					transactionLog.setCommisionType(merchantCommision.getChargeType());
					transactionLog.setCommisionId(merchantCommision.getId());
				}
			} else {

				if (merchantCommision != null && merchantCommision.getChargeType().equalsIgnoreCase("Excl")) {
					BigDecimal commisionValue = merchantCommision.getCommisionvalue();
					if (commisionValue.compareTo(BigDecimal.ZERO) == 1) {
						commision = commisionValue;
					} else {
						commision = BigDecimal.ZERO;
					}
					cAmount = amount.add(commision);

					// transactionLog.setAmt(cAmount);
					transactionLog.setCommision(commision);
					transactionLog.setCommisionType(merchantCommision.getChargeType());
					transactionLog.setCommisionId(merchantCommision.getId());
					paymentRequest.setAmt(cAmount);

				} else if (merchantCommision != null && merchantCommision.getChargeType().equalsIgnoreCase("Incl")) {
					BigDecimal commisionValue = merchantCommision.getCommisionvalue();
					if (commisionValue.compareTo(BigDecimal.ZERO) == 1) {
						commision = commisionValue;
					} else {
						commision = BigDecimal.ZERO;
					}
					transactionLog.setCommision(commision);
					transactionLog.setCommisionType(merchantCommision.getChargeType());
					transactionLog.setCommisionId(merchantCommision.getId());
				}

			}

			BigDecimal comission = transactionLog.getCommision();

			String merchantState = null;
			if (merchant != null && merchant.getState() != null) {
				merchantState = merchant.getState();
			}

			String getepayState = "Rajasthan";
			if (merchantState != null && !merchantState.equalsIgnoreCase(getepayState)) {

				BigDecimal gstPercent = new BigDecimal("0.18");

				BigDecimal am = comission;
				am = am.multiply(new BigDecimal("100"));
				am = am.divide(new BigDecimal("118"), RoundingMode.HALF_UP);

				BigDecimal gst = am.multiply(gstPercent);

				String gstString = gst.setScale(2, RoundingMode.HALF_UP).toString();

				if (String.valueOf(commisiionId).equalsIgnoreCase(String.valueOf(merchantCommision.getId()))) {
					String cv = propMap.get(MERCHANT_COMMSSION_VALUE);

					if (cv != null && !cv.equalsIgnoreCase("")) {
						gstString = gst.setScale(4, RoundingMode.HALF_UP).toString();
					}

				}

				transactionLog.setIgstAmount(new BigDecimal(gstString));

				transactionLog.setSgstAmount(BigDecimal.ZERO);
				transactionLog.setCgstAmount(BigDecimal.ZERO);

			} else {

				BigDecimal gstPercent = new BigDecimal("0.09");
				BigDecimal am = comission;

				am = am.multiply(new BigDecimal("100"));
				am = am.divide(new BigDecimal("118"), RoundingMode.HALF_UP);

				BigDecimal gst = am.multiply(gstPercent);

				String gstString = gst.setScale(2, RoundingMode.HALF_UP).toString();

				if (String.valueOf(commisiionId).equalsIgnoreCase(String.valueOf(merchantCommision.getId()))) {
					String cv = propMap.get(MERCHANT_COMMSSION_VALUE);

					if (cv != null && !cv.equalsIgnoreCase("")) {
						gstString = gst.setScale(4, RoundingMode.HALF_UP).toString();
					}

				}

				transactionLog.setCgstAmount(new BigDecimal(gstString));
				transactionLog.setSgstAmount(new BigDecimal(gstString));

				transactionLog.setIgstAmount(BigDecimal.ZERO);
			}
		} else {
			transactionLog.setCommision(new BigDecimal("0"));
		}
	}

//	public MerchantCommision findByMidAndPaymode(MerchantCommision commision) {
//		return merchantCommisionDao.findByMidAndPayMode(commision);
//	}
//
//	public MerchantCommision findDefaultCommision(MerchantCommision commision) {
//		return merchantCommisionDao.findDefaultCommison(commision);
//	}

	public MerchantCommision getMerchantCommisionData(long merchantId, String paymentMode, double amounT,
			String productType, String subType, Map<String, String> propMap) {

		logger.info("generateCharges mid : " + merchantId + " paymentMode : " + paymentMode + " amount : " + amounT
				+ " subType : " + subType);

		BigDecimal amount = BigDecimal.valueOf(amounT);

		MerchantCommision merchantCommision = null;

		if (paymentMode != null && (paymentMode.equalsIgnoreCase("DC") || paymentMode.equalsIgnoreCase("CC")
				|| paymentMode.equalsIgnoreCase("NB")) && (subType == null || subType.equals(""))) {
			logger.info("Invalid paymentMode or subType");
			return null;
		}

		if (propMap != null) {
			Long commisiionId = Long.valueOf(propMap.get(MERCHANT_COMMSSION_ID));

			if (String.valueOf(commisiionId).equalsIgnoreCase(String.valueOf(merchantId))) {
				String cv = propMap.get(MERCHANT_COMMSSION_VALUE);

				if (cv != null && !cv.equalsIgnoreCase("")) {
					logger.info("MERCHANT_COMMSSION_ID case");
					BigDecimal bg = BigDecimal.valueOf(Double.valueOf(cv));

					merchantCommision = new MerchantCommision();
					merchantCommision.setMerchantId(merchantId);
					merchantCommision.setProductType(productType);
					merchantCommision.setPaymentMode(paymentMode);
					merchantCommision.setDefault(false);
					merchantCommision.setChargeType("Incl");
					merchantCommision.setCommisionType("Percentage");
					merchantCommision.setCommisionvalue(bg);
				}
			}
		}
		if (paymentMode.trim().equalsIgnoreCase("DC")) {
			logger.info("DC Rupay case");
			if (subType.contains("RUPAY")) {
				merchantCommision = new MerchantCommision();
				merchantCommision.setMerchantId(merchantId);
				merchantCommision.setProductType(productType);
				merchantCommision.setPaymentMode(paymentMode);
				merchantCommision.setCommisionvalue(amount);
				merchantCommision.setDefault(false);
				merchantCommision.setChargeType("Incl");
				merchantCommision.setCommisionType("Fixed");
				merchantCommision.setCommisionvalue(BigDecimal.ZERO);
			}
		}
		if (merchantCommision != null) {
			return merchantCommision;
		}

		MerchantCommision merchantCommisionObj = new MerchantCommision();
		merchantCommisionObj.setMerchantId(merchantId);
		merchantCommisionObj.setPaymentMode(paymentMode);
		merchantCommisionObj.setProductType(productType);
		merchantCommisionObj.setCommisionvalue(amount);
		merchantCommisionObj.setDefault(false);

		if (paymentMode.trim().equalsIgnoreCase("NB")) {
			logger.info("NB case");
			Bank bank = bankRepo.findById(Long.parseLong(subType)).get();
			merchantCommisionObj.setSubType(bank.getBankName());
		}

		if (paymentMode.trim().equalsIgnoreCase("DC") || paymentMode.trim().equalsIgnoreCase("CC")
				|| paymentMode.trim().equalsIgnoreCase("NB")) {
			logger.info("CC/DC/NB case");
			merchantCommisionObj.setSubType(subType);
			merchantCommision = merchantCommisionDao.findByMidAndBank(merchantCommisionObj);

			if (merchantCommision == null) {
				logger.info("case 1 : higer slab ");
				merchantCommision = merchantCommisionDao
						.searchMerchantCommisionGreaterThanSlabSingleResult(merchantCommisionObj);
			}

			if (merchantCommision == null) {
				logger.info("case 2 : no slab ");
				merchantCommisionObj.setCommisionvalue(null);
				merchantCommision = merchantCommisionDao.findByMidAndBank(merchantCommisionObj);
			}
			if (merchantCommision == null) {
				logger.info("case 3 : All case");
				merchantCommisionObj.setSubType("ALL");
				merchantCommisionObj.setCommisionvalue(amount);
				merchantCommision = merchantCommisionDao.findByMidAndBank(merchantCommisionObj);
			}
			if (merchantCommision == null) {
				logger.info("case 4 : higer slab ");
				merchantCommision = merchantCommisionDao
						.searchMerchantCommisionGreaterThanSlabSingleResult(merchantCommisionObj);
			}
			if (merchantCommision == null) {
				logger.info("case 5 : All no slab case");
				merchantCommisionObj.setSubType("ALL");
				merchantCommisionObj.setCommisionvalue(null);
				merchantCommision = merchantCommisionDao.findByMidAndBank(merchantCommisionObj);
			}
		} else {
			logger.info("other case : " + paymentMode);
			merchantCommision = merchantCommisionDao.findByMidAndPayMode(merchantCommisionObj);
			if (merchantCommision == null) {
				logger.info("case 1 : higer slab ");
				merchantCommision = merchantCommisionDao
						.searchMerchantCommisionGreaterThanSlabSingleResult(merchantCommisionObj);
			}
			if (merchantCommision == null) {
				logger.info("case 2 : no slab ");
				merchantCommisionObj.setCommisionvalue(null);
				merchantCommision = merchantCommisionDao.findByMidAndPayMode(merchantCommisionObj);
			}
		}

		// default case start
		if (merchantCommision == null) {
			logger.info("default case 1");
			logger.info("case : " + paymentMode);
			merchantCommisionObj = new MerchantCommision();
			merchantCommisionObj.setMerchantId(merchantId);
			merchantCommisionObj.setPaymentMode(paymentMode);
			merchantCommisionObj.setProductType(productType);
			merchantCommisionObj.setCommisionvalue(amount);
			merchantCommisionObj.setDefault(true);
			merchantCommision = merchantCommisionDao.findDefaultCommison(merchantCommisionObj);
		}
		if (merchantCommision == null) {
			logger.info("default case 2 : higer slab ");
			merchantCommision = merchantCommisionDao
					.searchMerchantCommisionGreaterThanSlabSingleResult(merchantCommisionObj);
		}
		if (merchantCommision == null) {
			logger.info("default default case 2 : no slab ");
			merchantCommisionObj.setCommisionvalue(null);
			merchantCommision = merchantCommisionDao.findDefaultCommison(merchantCommisionObj);
		}

		// merchantCommision NA case
		if (merchantCommision == null) {
			logger.info("merchantCommision NA case");
			merchantCommision = new MerchantCommision();
			merchantCommision.setMerchantId(merchantId);
			merchantCommision.setProductType(productType);
			merchantCommision.setPaymentMode(paymentMode);
			merchantCommision.setCommisionvalue(amount);
			merchantCommision.setDefault(false);
			merchantCommision.setChargeType("Incl");
			merchantCommision.setCommisionvalue(BigDecimal.ZERO);
		}

		return merchantCommision;

	}

	public CommissionModel getCommisionAmountModel2(long mid, String payMode, double amounT, String productType,
			String subType, String conSubType) {
		Map<String, String> propMap = new HashMap<>();
		BigDecimal amount = BigDecimal.valueOf(amounT);
		MerchantCommision merchantCommision = new MerchantCommision();
		merchantCommision.setMerchantId(mid);
		merchantCommision.setProductType(productType);
		merchantCommision.setPaymentMode(payMode);
		merchantCommision.setCommisionvalue(amount);
		merchantCommision.setDefault(false);
		if (payMode.equalsIgnoreCase("DC") || payMode.equalsIgnoreCase("CC")) {
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
			commissions = merchantCommisionDao.searchMerchatCommision(merchantCommision);
		}

		if (commissions == null || commissions.size() <= 0) {
			merchantCommision.setDefault(true);
			if (payMode.equalsIgnoreCase("NB") || payMode.equalsIgnoreCase("DC") || payMode.equalsIgnoreCase("CC")) {
				merchantCommision.setSubType(null);
			}
			commissions = merchantCommisionDao.searchMerchatCommision(merchantCommision);
		}

		if (commissions != null && commissions.size() > 0) {
			merchantCommision = commissions.get(0);
		}

		try {

			List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
			for (PropertiesVo property : PropertiesList) {
				propMap.put(property.getPropertyKey(), property.getPropertyValue());
			}

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}

		return Utilities.getCommissionChargesModel(merchantCommision, amounT, propMap);

	}

	public CommissionModel getCommisionAmountModel(Long mid, String payMode, double amounT, String productType,
			String subType) {
		Map<String, String> propMap = new HashMap<>();
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

		try {

			List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
			for (PropertiesVo property : PropertiesList) {
				propMap.put(property.getPropertyKey(), property.getPropertyValue());
			}

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}

		return Utilities.getCommissionChargesModel(merchantCommision, amounT, propMap);

	}

}
