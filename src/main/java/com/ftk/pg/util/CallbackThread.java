package com.ftk.pg.util;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftk.pg.encryption.RSAUtil;
import com.ftk.pg.modal.MerchantCallbackSetting;
import com.ftk.pg.modal.TransactionLog;
import com.ftk.pg.requestvo.PropertiesVo;
import com.ftk.pg.responsevo.RequeryResponse;
import com.ftk.pg.responsevo.TransactionRequeryResponse;

public class CallbackThread implements Runnable {

	private Logger logger = LogManager.getLogger(CallbackThread.class);

	private TransactionLog log;
	private MerchantCallbackSetting merchantCallbackSetting;
	private List<PropertiesVo> properties;

	public CallbackThread(TransactionLog log, MerchantCallbackSetting merchantCallbackSetting,
			List<PropertiesVo> properties) {
		this.log = log;
		this.merchantCallbackSetting = merchantCallbackSetting;
		this.properties = properties;
	}

	private void upiQRCallback() {

	}

	private void pgCallback() {
		ObjectMapper wrapper = new ObjectMapper();
		wrapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		String responseString = null;
		try {
			RequeryResponse response = new RequeryResponse();
			response.setResult(false);

			TransactionRequeryResponse transactionRequeryResponse = new TransactionRequeryResponse();
			transactionRequeryResponse.setRequeryStatus("SUCCESS");
			transactionRequeryResponse.setRequeryMessage("Please refer txnStatus for transaction status.");
			BigDecimal com = log.getCommision();
			if (com == null) {
				com = BigDecimal.ZERO;
			}
			transactionRequeryResponse.setCommission(com.toString());
			transactionRequeryResponse.setPaymentMode(log.getPaymentMode());
			transactionRequeryResponse.setTxnStatus(log.getTxnStatus());
			transactionRequeryResponse.setMerchantOrderNo(log.getMerchanttxnid());
			transactionRequeryResponse.setMid(String.valueOf(log.getMerchantId()));
			transactionRequeryResponse.setDescription(log.getStage());
			transactionRequeryResponse.setGetepayTxnId(String.valueOf(log.getTransactionId()));
			transactionRequeryResponse.setTxnAmount(log.getAmt().toString());
			transactionRequeryResponse.setUdf1(log.getUdf1());
			transactionRequeryResponse.setUdf2(log.getUdf2());
			transactionRequeryResponse.setUdf3(log.getUdf3());
			transactionRequeryResponse.setUdf4(log.getUdf4());
			transactionRequeryResponse.setUdf5(log.getUdf5());

			responseString = wrapper.writeValueAsString(transactionRequeryResponse);

			String privateKeyPath = "";
			String publicKeyPath = "";

			for (Iterator iterator = properties.iterator(); iterator.hasNext();) {
				PropertiesVo properties2 = (PropertiesVo) iterator.next();
				if (properties2.getPropertyKey() != null
						&& properties2.getPropertyKey().equalsIgnoreCase("GETEPAY_PAYOUT_PRIVATE_KEY_PATH")) {
					privateKeyPath = properties2.getPropertyValue();
				}
				if (properties2.getPropertyKey() != null
						&& properties2.getPropertyKey().equalsIgnoreCase("GETEPAY_PAYOUT_PUBLIC_KEY_PATH")) {
					publicKeyPath = properties2.getPropertyValue();
				}
			}
			logger.info("public key path in callback=>" + publicKeyPath);

			// String r= Util.encryptIciciRequest(responseString, publicKeyPath);
			String r = Base64.getEncoder().encodeToString(RSAUtil.encrypt(responseString, publicKeyPath));
			String callbackUrl = this.merchantCallbackSetting.getCallbackUrl();
			if (transactionRequeryResponse.getPaymentMode() != null
					&& transactionRequeryResponse.getPaymentMode().equalsIgnoreCase("UPIQR")) {
				callbackUrl = this.merchantCallbackSetting.getQrcallbackUrl();
			}

			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(20 * 1000).build();
			HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
			HttpPost post = new HttpPost(callbackUrl.trim());
			StringEntity poStringEntity = new StringEntity(r);
			logger.info("poStringEntity ==> " + poStringEntity);
			post.setEntity(poStringEntity);
			HttpResponse responseRBL = httpClient.execute(post);
			HttpEntity entity = responseRBL.getEntity();
			responseString = EntityUtils.toString(entity, "UTF-8");
			logger.info("Callback post for ==> " + log.getTransactionId() + responseString);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		if (this.log != null && this.merchantCallbackSetting != null) {
			if (this.log.getPaymentMode().equalsIgnoreCase("UPIQR")) {
				upiQRCallback();
			} else {
				pgCallback();
			}
		}
	}
}
