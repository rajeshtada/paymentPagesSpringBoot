package com.ftk.pg.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftk.pg.dao.ConvenienceChargesDao;
import com.ftk.pg.dto.PaymentRequest;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.Bank;
import com.ftk.pg.modal.CardBean;
import com.ftk.pg.modal.ConvenienceCharges;
import com.ftk.pg.modal.Merchant;
import com.ftk.pg.modal.TransactionLog;
import com.ftk.pg.repo.BankRepo;
import com.ftk.pg.repo.CardBeanRepo;
import com.ftk.pg.util.Charge;
import com.ftk.pg.util.ConvenienceModel;
import com.ftk.pg.util.Utilities;
import com.pgcomponent.security.SecureCardData;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class ConvenienceChragesService  {
	static Logger logger = LogManager.getLogger(ConvenienceChragesService.class);


	
	private final BankRepo bankRepo;

	
	private final ConvenienceChargesDao convenienceChargesDao;

	
	private final CardBeanRepo cardbeanRepo;
	
	public static String GST_CHARGES = "18";


	public void getConvienceCharges(TransactionLog transactionlog, PaymentRequest pgRequest, Merchant merchant) {
		try {
			logger.info("Convience Charges Calculating =============================>");
			ConvenienceCharges conviCharges = new ConvenienceCharges();
			List<ConvenienceCharges> merchantconvenienceCharges = new ArrayList<>();
			BigDecimal convienceCharges = BigDecimal.ZERO;
			String conSubType = null;
			Charge charges = new Charge();
			Double valuecharges = 0.00;
			BigDecimal conAmount = pgRequest.getAmt();
			BigDecimal amount = transactionlog.getAmt().setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal gstCharges = BigDecimal.ZERO;
			BigDecimal totalpgAmount = null;

			conviCharges.setMid(transactionlog.getMerchantId());
			conviCharges.setPaymentMode(pgRequest.getPaymentMode());

			String carddata = pgRequest.getCarddata();
			String[] carddata_enc = new String[4];
			String cardno = "";
			String carddatadec = "";
			carddata_enc = carddata.split("\\|");
			cardno = carddata_enc[0];
			
        if (!pgRequest.getPaymentMode().equals("") && pgRequest.getPaymentMode().equals("NB")) {
				logger.info("Convience Charge For Net Banking===================================================>");

				Bank bank = bankRepo.findById(Long.parseLong(pgRequest.getBankid())).get();
				if (bank != null) {
					conviCharges.setBankId(bank.getId());
					merchantconvenienceCharges = convenienceChargesDao.getChargesByMidAndBankId(conviCharges);

				} else {
					transactionlog.setTotalServiceCharge(0);
					transactionlog.setServiceCharge(0);
					pgRequest.setAmt(conAmount.add(new BigDecimal("0.00")));

				}
			} else if ((!pgRequest.getPaymentMode().equals("") && pgRequest.getPaymentMode().equals("DC"))
					|| (!pgRequest.getPaymentMode().equals("") && pgRequest.getPaymentMode().equals("CC"))) {
				try {
					logger.info(
							"Caluclating Convience Charges for CC and DC =================================================>");
					SecureCardData obj1 = new SecureCardData();
					logger.info("Encrypt card data - " + cardno + " ------- " + pgRequest.getMerchantTxnId());
					try {
						carddatadec = obj1.decryptData(cardno, merchant.getMerchantPrivateKey());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
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
//					CardBean cardBean = cardbeanRepo.getBeanDetailbyBeanValue(value);
					CardBean cardBean = pgRequest.getCardBean();
					if (cardBean != null && cardBean.getId() != null && cardBean.getId() > 0) {
						String typeOfCard = cardBean.getTypeOfCard();
						if (cardBean.getCardType().equals("MASTERCARD")) {
							String cardTypeMaster = "MASTER";
							logger.info("Card Type======>" + cardTypeMaster);

							conSubType = cardTypeMaster + "_" + cardBean.getDomesticInternational();
						} else {
							conSubType = cardBean.getCardType() + "_" + cardBean.getDomesticInternational();
						}
						conviCharges.setCardType(conSubType);
						merchantconvenienceCharges = convenienceChargesDao
								.getChargesByMidPaymentModeAndCardType(conviCharges);
					} else {
						logger.info("Do not find Card Bean=========================>");
						transactionlog.setTotalServiceCharge(0);
						transactionlog.setServiceCharge(0);
						pgRequest.setAmt(conAmount.add(new BigDecimal("0.00")));
						logger.info("Convience Charge is =======================>" + conAmount);

					}
				} catch (NumberFormatException e) {
//					new GlobalExceptionHandler().customException(e);

				}

			} else if (!pgRequest.getPaymentMode().equalsIgnoreCase("") && (pgRequest.getPaymentMode().equalsIgnoreCase("UPI")||
					(pgRequest.getPaymentMode().equalsIgnoreCase("wallet"))||pgRequest.getPaymentMode().equalsIgnoreCase("NEFT")||
					pgRequest.getPaymentMode().equalsIgnoreCase("DYNAMICQR")||pgRequest.getPaymentMode().equalsIgnoreCase("UNB"))||pgRequest.getPaymentMode().equalsIgnoreCase("UPIQR")) {
				
				
				merchantconvenienceCharges = convenienceChargesDao.findConvenienceChargeOtherPayment(conviCharges);

			}
			
			if(merchantconvenienceCharges.isEmpty() || merchantconvenienceCharges==null) {
				merchantconvenienceCharges=convenienceChargesDao.getChargesByMidPaymentModeAndCardTypeAll(conviCharges);
			}
			
             if (merchantconvenienceCharges!=null && merchantconvenienceCharges.size() > 0) {
				logger.info("merchant Convience Charges object===============================>"
						+ merchantconvenienceCharges);

				conviCharges = merchantconvenienceCharges.get(0);
				if (conviCharges != null && conviCharges.getChargesType().equals("Excl")) {
					logger.info("Exclusive Charges==========================================================>");
					if (conviCharges.getCommissionType().equals("Fixed")) {
						logger.info("Fixed Charges==========================================================>");
						convienceCharges = new BigDecimal(conviCharges.getChargesAmt()).setScale(2, RoundingMode.HALF_UP);
						charges.setConvienceCharges(convienceCharges);
						valuecharges = charges.getConvienceCharges().doubleValue();

						transactionlog.setTotalServiceCharge(valuecharges);
						transactionlog.setServiceCharge(valuecharges);
						pgRequest.setAmt(conAmount.add(convienceCharges));
						transactionlog.setServiceChargeType("Excl");

					} else if (conviCharges.getCommissionType().equals("Variable")) {
						logger.info("Variable Charges==========================================================>");
						convienceCharges = amount.multiply(new BigDecimal(conviCharges.getChargesAmt()))
								.divide(new BigDecimal("100"),2,RoundingMode.HALF_UP);
						charges.setConvienceCharges(convienceCharges);
						valuecharges = charges.getConvienceCharges().doubleValue();

						transactionlog.setTotalServiceCharge(valuecharges);
						transactionlog.setServiceCharge(valuecharges);
						pgRequest.setAmt(conAmount.add(convienceCharges));
						transactionlog.setServiceChargeType("Excl");
					} else if (conviCharges.getCommissionType().equals("Fixed Variable")) {
						logger.info(
								"Fixed_Variable Charges==========================================================>");
						BigDecimal fixedCharges = new BigDecimal(conviCharges.getFixedVarible());

						BigDecimal variableCharges = amount.multiply(new BigDecimal(conviCharges.getChargesAmt()))
								.divide(new BigDecimal("100"),2, RoundingMode.HALF_UP);

						convienceCharges = fixedCharges.add(variableCharges);
						charges.setConvienceCharges(convienceCharges);
						valuecharges = charges.getConvienceCharges().doubleValue();
						transactionlog.setTotalServiceCharge(valuecharges);
						transactionlog.setServiceCharge(valuecharges);
						pgRequest.setAmt(conAmount.add(convienceCharges));
						transactionlog.setServiceChargeType("Excl");

					}

				} else if (conviCharges != null && conviCharges.getChargesType().equalsIgnoreCase("Incl")) {
					if (conviCharges.getCommissionType().equals("Fixed")) {
						logger.info("Fixed Charges==========================================================>");
						convienceCharges = new BigDecimal(conviCharges.getChargesAmt()).setScale(2, RoundingMode.HALF_UP);
						charges.setConvienceCharges(convienceCharges);
						valuecharges = charges.getConvienceCharges().doubleValue();

						transactionlog.setTotalServiceCharge(valuecharges);
						transactionlog.setServiceCharge(valuecharges);
						transactionlog.setServiceChargeType("Incl");

					} else if (conviCharges.getCommissionType().equals("Variable")) {
						logger.info("Variable Charges==========================================================>");
						convienceCharges = amount.multiply(new BigDecimal(conviCharges.getChargesAmt()))
								.divide(new BigDecimal("100"),2, RoundingMode.HALF_UP);
						charges.setConvienceCharges(convienceCharges);
						valuecharges = charges.getConvienceCharges().doubleValue();

						transactionlog.setTotalServiceCharge(valuecharges);
						transactionlog.setServiceCharge(valuecharges);
						transactionlog.setServiceChargeType("Incl");
					} else if (conviCharges.getCommissionType().equals("Fixed Variable")) {
						logger.info(
								"Fixed_Variable Charges==========================================================>");
						BigDecimal fixedCharges = new BigDecimal(conviCharges.getFixedVarible());

						BigDecimal variableCharges = amount.multiply(new BigDecimal(conviCharges.getChargesAmt()))
								.divide(new BigDecimal("100"),2, RoundingMode.HALF_UP);

						convienceCharges = fixedCharges.add(variableCharges);
						charges.setConvienceCharges(convienceCharges);
						valuecharges = charges.getConvienceCharges().doubleValue();
						transactionlog.setTotalServiceCharge(valuecharges);
						transactionlog.setServiceCharge(valuecharges);
						transactionlog.setServiceChargeType("Incl");

					}

				}
				totalpgAmount = pgRequest.getAmt();
			} else {
				charges.setConvienceCharges(new BigDecimal("0"));
				valuecharges = charges.getConvienceCharges().doubleValue();

				transactionlog.setTotalServiceCharge(valuecharges);
				transactionlog.setServiceCharge(valuecharges);
				pgRequest.setAmt(conAmount.add(convienceCharges));
				totalpgAmount = pgRequest.getAmt();

			}

			if (transactionlog != null && transactionlog.getServiceChargeType().equals("Excl")) {
				logger.info("gst Charges Calculating==========================================================>");
				gstCharges = convienceCharges.multiply(new BigDecimal(GST_CHARGES));
				gstCharges = gstCharges.divide(new BigDecimal("100"),2,RoundingMode.HALF_UP);
				logger.info("Gst Charges==========================================================>");
				double gstcharges = gstCharges.doubleValue();
				transactionlog.setServiceChargeGst(gstcharges);
				transactionlog.setTotalServiceCharge(new BigDecimal(gstcharges + valuecharges).setScale(2,RoundingMode.HALF_UP).doubleValue());
				pgRequest.setAmt(totalpgAmount.add(gstCharges));
				logger.info("Transaction Log=====>" + transactionlog);
				logger.info("PgRequest Log=====>" + pgRequest);

			} else if (transactionlog != null && transactionlog.getServiceChargeType().equals("Incl")) {
				logger.info("gst Charges Calculating==========================================================>");
				gstCharges = convienceCharges.multiply(new BigDecimal(GST_CHARGES));
				gstCharges = gstCharges.divide(new BigDecimal("100"),2,RoundingMode.HALF_UP);
				logger.info("Gst Charges==========================================================>");
				double gstcharges = gstCharges.doubleValue();
				transactionlog.setServiceChargeGst(gstcharges);
				transactionlog.setTotalServiceCharge(new BigDecimal(gstcharges + valuecharges).setScale(2,RoundingMode.HALF_UP).doubleValue());
				logger.info("Transaction Log=====>" + transactionlog);
				logger.info("PgRequest Log=====>" + pgRequest);

			} else {

				logger.info("gst Charges Calculating==========================================================>");
				gstCharges = convienceCharges.multiply(new BigDecimal(GST_CHARGES));
				gstCharges = gstCharges.divide(new BigDecimal("100"),2,RoundingMode.HALF_UP);
				logger.info("Gst Charges==========================================================>");
				double gstcharges = gstCharges.doubleValue();
				transactionlog.setServiceChargeGst(gstcharges);
				transactionlog.setTotalServiceCharge(gstcharges + valuecharges);
				pgRequest.setAmt(totalpgAmount.add(gstCharges));
				logger.info("Transaction Log=====>" + transactionlog.getTransactionId());
				logger.info("PgRequest Log=====>" + pgRequest);

			}

		} catch (NumberFormatException e) {
			new GlobalExceptionHandler().customException(e);
		}
	}

	
		public ConvenienceModel getConvenienceChargesModel(long mid, String paymentMode, double parseDouble,
				String subType) {
			logger.info("getConvenienceChargesModel called==========>");
			ConvenienceCharges convenienceCharge = new ConvenienceCharges();
			List<ConvenienceCharges> convenienceCharges=new ArrayList<>();
			convenienceCharge.setMid(mid);
			convenienceCharge.setPaymentMode(paymentMode);
				
			if (paymentMode.equals("DC")||paymentMode.equals("CC")) {
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
			if(!paymentMode.equals("") && (paymentMode.equalsIgnoreCase("UPI")||
						(paymentMode.equalsIgnoreCase("wallet"))||paymentMode.equalsIgnoreCase("NEFT")||
						paymentMode.equalsIgnoreCase("DYNAMICQR")||paymentMode.equalsIgnoreCase("UNB"))
					||paymentMode.equalsIgnoreCase("UPIQR")){
				
				convenienceCharges = convenienceChargesDao
						.findConvenienceChargeOtherPayment(convenienceCharge);
			}
			else {
			convenienceCharges = convenienceChargesDao
					.getChargesByMidPaymentModeAndCardType(convenienceCharge);
			}
			
			
			if(convenienceCharges.isEmpty()) {
				convenienceCharges = convenienceChargesDao
						.getChargesByMidPaymentModeAndCardTypeAll(convenienceCharge);
			}
			
			if(convenienceCharges!=null && convenienceCharges.size()>0) {
				convenienceCharge=convenienceCharges.get(0);
			}
			
			

			return Utilities.getConvenienceCharges(convenienceCharge, parseDouble);
		}

	

}
