package com.ftk.pg.pi.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.pi.modal.MerchantInvoice;
import com.ftk.pg.pi.modal.SoundBoxInventory;
import com.ftk.pg.util.Util;
import com.ftk.pg.vo.generateInvoice.MqttDynamicQrRequest;
import com.ftk.pg.vo.generateInvoice.MqttDynamicQrRequestWrapper;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@RequiredArgsConstructor
@Service
public class AsyncPortalService {

	static Logger logger = LogManager.getLogger(AsyncPortalService.class);

	@Async
	public void mqttSoundboxDynamicQr(MerchantInvoice invoice, String qrIntentLink, String vpa,
			Map<String, String> propertiesMap, List<SoundBoxInventory> soundBoxInv) {
		try {
			String soundBoxNotificationApiUrl = propertiesMap.get(Util.SOUNDBOX_NOTIFICATION_API_URL);
			String soundBoxNotificationApiKey = propertiesMap.get(Util.SOUNDBOX_NOTIFICATION_API_KEY);

			for (SoundBoxInventory soundBoxInventory : soundBoxInv) {
				if (soundBoxInventory.getSoundboxType() != null
						&& !soundBoxInventory.getSoundboxType().trim().equals("")
						&& soundBoxInventory.getSoundboxType().equalsIgnoreCase("4G")) {
					if (soundBoxInventory != null) {
						MqttDynamicQrRequest mqttDynamicQrRequest = new MqttDynamicQrRequest();

						mqttDynamicQrRequest.setQR(qrIntentLink);
						mqttDynamicQrRequest.setMessage("");
						mqttDynamicQrRequest.setMessageType("");
						mqttDynamicQrRequest.setOrderNum(
								"inv_" + invoice.getId() + soundBoxInventory.getSearialNo().replace("KD68", ""));
						mqttDynamicQrRequest.setType("1");
						Double fAmt = invoice.getAmount().doubleValue() * 100;
						mqttDynamicQrRequest.setPrice(String.valueOf(BigDecimal.valueOf(fAmt).setScale(0).longValue()));

						MqttDynamicQrRequestWrapper mqttDynamicQrRequestWrapper = new MqttDynamicQrRequestWrapper();
						mqttDynamicQrRequestWrapper.setSerialNo("p2p/" + soundBoxInventory.getSearialNo());
						mqttDynamicQrRequestWrapper.setMqttModal(mqttDynamicQrRequest);

						callMqttApi(mqttDynamicQrRequestWrapper, soundBoxNotificationApiUrl,
								soundBoxNotificationApiKey);
					}
				} else if (soundBoxInventory.getSoundboxType() != null
						&& !soundBoxInventory.getSoundboxType().trim().equals("")
						&& soundBoxInventory.getSoundboxType().equalsIgnoreCase("I4G")) {
					MqttDynamicQrRequest mqttDynamicQrRequest = new MqttDynamicQrRequest();

					mqttDynamicQrRequest.setQR(qrIntentLink);
					mqttDynamicQrRequest.setMessage("");
					mqttDynamicQrRequest.setMessageType("");
					mqttDynamicQrRequest.setOrderNum("inv_" + invoice.getId() + soundBoxInventory.getSearialNo());
					mqttDynamicQrRequest.setType("1");
					Double fAmt = invoice.getAmount().doubleValue() * 100;
					mqttDynamicQrRequest.setPrice(String.valueOf(BigDecimal.valueOf(fAmt).setScale(0).longValue()));

					MqttDynamicQrRequestWrapper mqttDynamicQrRequestWrapper = new MqttDynamicQrRequestWrapper();
					mqttDynamicQrRequestWrapper.setSerialNo(soundBoxInventory.getSearialNo());
					mqttDynamicQrRequestWrapper.setMqttModal(mqttDynamicQrRequest);

					callMqttApi(mqttDynamicQrRequestWrapper, soundBoxNotificationApiUrl, soundBoxNotificationApiKey);
				}
			}
		} catch (Exception e) {
//			logger.error("Error processing payment request: " + e.getMessage(), e);
			new GlobalExceptionHandler().customException(e);
		}
	}
	
	private void callMqttApi(MqttDynamicQrRequestWrapper mqttDynamicQrRequestWrapper, String apiUrl, String apiKey) {
		Gson gson = new Gson();
		Response responseApi = null;
		try {

			String requestString = gson.toJson(mqttDynamicQrRequestWrapper);
			logger.info("soundboxrequest => " + requestString);

			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, requestString);
			Request request = new Request.Builder().url(apiUrl).method("POST", body).addHeader("x-api-key", apiKey)
					.addHeader("Content-Type", "application/json").build();
			responseApi = client.newCall(request).execute();
			logger.info("soundboxresponse => " + responseApi.code() + " :: " + responseApi.body().string());

		} catch (Exception e) {
//			logger.error("Error processing payment request: " + e.getMessage(), e);
			new GlobalExceptionHandler().customException(e);
		} finally {
			if (responseApi != null) {
				responseApi.body().close();
			}
		}
	}
	
	
	public void callBms(String msg, String mobileNo, Map<String, String> propertiesMap) {

		logger.info("Calling msg" + msg);
		logger.info("Calling BmobileNoMS" + mobileNo);
		String CALL_BMS_BASE_URL = propertiesMap.get("CALL_BMS_BASE_URL");
		String propertyValueUserName = propertiesMap.get("BMS_SMS_USERNAME");
		logger.info("Calling userAdvanceProperties" + propertyValueUserName);
		String propertyValuePass = propertiesMap.get("BMS_SMS_PASSWORD");
		logger.info("Calling passwordAdvanceProperties" + propertyValuePass);
		String url = CALL_BMS_BASE_URL.replace("#username", propertyValueUserName)
				.replace("#password", propertyValuePass).replace("#msg", msg).replace("#mobileNo", mobileNo);
		logger.info("BMS SMS URL =====> " + url);
		Response response = null;
		String resp = mobileNo + "|" + "Failed" + "|" + "Failed";
		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			Request request = new Request.Builder().url(url).get().build();
			response = client.newCall(request).execute();
			logger.info("bms resp===> " + resp);

			resp = new String(response.body().bytes());
			if (CALL_BMS_BASE_URL.contains("bmsVendor")) {
				logger.info("bms resp contains===> " + resp);
				if (response.code() == 200) {
					logger.info("bms resp contains===> " + true);
				} else {
					logger.info("bms resp contains===> " + false);
				}
			} else {
				if (resp != null && resp.contains("1010")) {
					resp = resp.replace("Error code: ", "");
					resp = mobileNo + "|" + resp + "|" + resp;
				}
			}
		} catch (IOException e) {
			new GlobalExceptionHandler().customException(e);
		} finally {
			if (response != null) {
				response.body().close();
			}
		}
	}

}
