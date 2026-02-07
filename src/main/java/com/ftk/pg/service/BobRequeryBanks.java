package com.ftk.pg.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import org.apache.log4j.Logger;

import com.fss.plugin.bob.iPayPipe;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.MerchantSetting;
import com.ftk.pg.modal.TransactionLog;
import com.ftk.pg.util.CustomExceptionLoggerError;
import com.ftk.pg.util.RequaryUtil;
import com.ftk.pg.util.Utils;


public class BobRequeryBanks {
	
	
	private static Logger logger = LogManager.getLogger(BobRequeryBanks.class);
	
	public static TransactionLog bobRequary(TransactionLog transactionLog, MerchantSetting merchantSetting, Map<String, String> propMap) {
		String responseCode = "01";
		String txnStatus = "FAILED";
		try {
			String keystorePath = propMap.get(Utils.BOB_REQUERY_RESOURCE_PATH);
			String resourcePath = merchantSetting.getSetting3();
			String alias = merchantSetting.getSetting2();
			String rurl = "http://www.demomerchant.com/result.jsp";
			String errorUrl = "http://www.demomerchant.com/result.jsp";

			logger.info("resource=>" + resourcePath);
			logger.info("alias=>" + alias);
			logger.info("keystorePath=>" + keystorePath);
			if (resourcePath == null || alias == null) {
				logger.info("inside resourcePath null");
				return null;
			}

			logger.info("BOB Action =========> 8");
			String action = "8";
			String trackId = String.valueOf(transactionLog.getTransactionId());
			String currency = "356";
			String language = "USA";

			String transId = String.valueOf(transactionLog.getTransactionId());

			logger.info("Bob TrasnactionID " + transId);
			String cardType = "UPI_VPA";
			if (transactionLog.getPaymentMode().equalsIgnoreCase("CC")) {
				cardType = "C";
				logger.info("cardType : " + cardType);
			} else if (transactionLog.getPaymentMode().equalsIgnoreCase("DC")) {
				cardType = "D";
				logger.info("cardType : " + cardType);
			}

//			String udf5 = "PaymentID";
			String udf5 = "TrackID";

			BigDecimal netAmt = RequaryUtil.requaryAmount(transactionLog);

			logger.info("Net Amount ==========>" + netAmt);
			String netAmtStr = netAmt.setScale(2, RoundingMode.HALF_UP).toString();
			iPayPipe pipe = new iPayPipe();
			pipe.setResourcePath(resourcePath);
			pipe.setKeystorePath(keystorePath);
			pipe.setAlias(alias);
			pipe.setAction(action);
			pipe.setCurrency(currency);
			pipe.setLanguage(language);
			pipe.setResponseURL(rurl);
			pipe.setErrorURL(errorUrl);
			pipe.setAmt(netAmtStr);
//			pipe.setTransId(transactionLog.getOrderNumber());
			pipe.setTransId(trackId);
			pipe.setType(cardType);
			pipe.setTrackId(String.valueOf(System.currentTimeMillis()));
			//pipe.setTrackId(trackId);
			pipe.setUdf5(udf5);
			pipe.setUdf1("Udf1");
			pipe.setUdf2("Udf2");
			pipe.setUdf3("Udf3");
			pipe.setUdf4("Udf4");
			pipe.setUdf6("Udf6");
			pipe.setUdf7("Udf7");

			logger.info("iPayPipe Fields=>" + "action" + "::" + action);
			logger.info("iPayPipe Fields=>" + "transId" + "::" + pipe.getTransId());
			logger.info("iPayPipe Fields=>" + "trackId" + "::" + pipe.getTrackId());
			logger.info("iPayPipe Fields=>" + "udf5" + "::" + udf5);
			logger.info("iPayPipe Fields=>" + "amount" + "::" + netAmtStr);
			logger.info("I paypipe Object=========>" + pipe.toString());
			//pipe.performVbVTransaction();
			 pipe.performTransaction();

			String url = pipe.getWebAddress();

			logger.info("Url Requeary ======================================>" + url);

			
		//	String response=call(url);
			
		//	logger.info("Response=========================================>"+response);
			Object responseResult = pipe.getResult();
			String processorTransactionId = pipe.getPaymentId();
			 logger.info("responseResult ===> " + responseResult);
			if (responseResult == null || responseResult.equals("")) {
				responseResult = pipe.getError();
			}

			if (responseResult != null && (responseResult.equals("SUCCESS") || responseResult.equals("CAPTURED"))) {
				responseCode = "00";
				logger.info("inside responseResult success or captured");
				txnStatus = "SUCCESS";
				transactionLog.setResponseCode(responseCode);
				transactionLog.setTxnStatus(txnStatus);
				transactionLog.setStage("Transaction is marked as success in requery. ");
//				transactionLog.setProcessorCode(responseResult);
//				transactionLog.setOrderNumber(processorTransactionId);
				return transactionLog;

			}
			else {
				responseCode = "01";
				logger.info("inside responseResult failed");
				txnStatus = "FAILED";
				transactionLog.setResponseCode(responseCode);
				transactionLog.setTxnStatus(txnStatus);
				transactionLog.setStage("Transaction is marked as failed in requery. ");
//				transactionLog.setProcessorCode(responseResult);
//				transactionLog.setOrderNumber(processorTransactionId);
				return transactionLog;
			}

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			logger.error(e);
		}

		return null;
	}

}
