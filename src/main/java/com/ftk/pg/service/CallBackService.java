package com.ftk.pg.service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.TransactionLog;
import com.ftk.pg.requestvo.PgPushNotificationRequest;
import com.ftk.pg.requestvo.PropertiesVo;
import com.ftk.pg.util.ComponentUtils;
import com.ftk.pg.util.RemoteDbUtil;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CallBackService {

	static Logger logger = LogManager.getLogger(CallBackService.class);

//	@Autowired
//	PropertiesRepo propertiesRepo;
	
	
	 private final PropertiesService propertiesService;

	public void addCallbackInQueue(Long txnId) {
		logger.info("add txnSync queue => " + txnId);
		if (txnId == null || txnId <= 0) {
			return;
		}

		String requestString = "";
		try {
			PropertiesVo properties = new PropertiesVo();
			properties = propertiesService.findByPropertykeyWithUpdatedCerts(RemoteDbUtil.TXN_SYNC_API_URL);
			String apiUrl = properties.getPropertyValue();

			requestString = String.valueOf(txnId);
			logger.info("txnSync addCallbackInQueue requestString => " + requestString);

			String responseString = callApi(requestString, apiUrl);
			logger.info("txnSync response => " + " :: " + responseString);

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			logger.error(e.getMessage());
		}
	}

	public void addRequaryInQueue(Long txnId) {
		logger.info("add txnSync queue => " + txnId);
		if (txnId == null || txnId <= 0) {
			return;
		}

		String requestString = "";
		try {
			PropertiesVo properties = new PropertiesVo();
			properties = propertiesService.findByPropertykeyWithUpdatedCerts(ComponentUtils.REQUARY_API_URL);
			String apiUrl = properties.getPropertyValue();

			requestString = String.valueOf(txnId);
			logger.info("txnSync addCallbackInQueue requestString => " + requestString);

			String responseString = callApi(requestString, apiUrl);
			logger.info("txnSync response => " + " :: " + responseString);

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			logger.error(e.getMessage());
		}
	}

//	@Override
//	public void addprocessorIdInQueue(ProcessorIdAndPortalIdRequest processorIdAndPortalIdRequest) {
//		logger.info("add processorIdAndPortalIdRequest queue => " + processorIdAndPortalIdRequest);
////		if (processorIdAndPortalIdRequest ==null && processorIdAndPortalIdRequest.getProcessorId() ==null && processorIdAndPortalIdRequest.getTrasnactionId()==null) {
////			return;
////		}
//
//		Gson gson = new Gson();
//		String requestString = "";
//		try {
//			Properties properties = new Properties();
//			properties.setPropertykey(Util.PROCESSOR_IDS_SYNC);
//			properties = propertiesDao.getPropertyByKey(properties);
//			String apiUrl = properties.getPropertyValue();
//
//			requestString = gson.toJson(processorIdAndPortalIdRequest);
//			logger.info("processorIdAndPortalIdRequest requestString => " + requestString);
//
//			String responseString = callApi(requestString, apiUrl);
//			logger.info("processorIdAndPortalIdRequest response => " + " :: " + responseString);
//
//		} catch (Exception e) {
//			new GlobalExceptionHandler().customException(e);
//			logger.error(e.getMessage());
//		}
//	}

//	public String callApi(String requestBody, String apiUrl, String apiKey) {
//		try {
//			OkHttpClient client = new OkHttpClient().newBuilder().build();
//			MediaType mediaType = MediaType.parse("application/json");
//			RequestBody body = RequestBody.create(mediaType, requestString);
//			Request request = new Request.Builder().url(apiUrl).method("POST", body)
//					.addHeader("Content-Type", "application/json").build();
//			Response response = client.newCall(request).execute();
//			String responseString = response.body().string();
//			logger.info("txnSync response => " + response.code() + " :: " + responseString);
//
//			return responseString;
//		} catch (Exception e) {
//			new GlobalExceptionHandler().customException(e);
//		}
//		return null;
//	}

	public String callApi(String requestBody, String apiUrl) {
		try {
			URL url = new URL(apiUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");

			byte[] bodyBytes = requestBody.getBytes(StandardCharsets.UTF_8);

			Map<String, String> headers = new LinkedHashMap<>();
			headers.put("Content-Type", "application/json");

			logger.info(headers.entrySet().stream().map(e -> e.getKey() + ": " + e.getValue())
					.collect(Collectors.joining("\n")));

			// Send
			headers.forEach((key, val) -> connection.setRequestProperty(key, val));
			connection.setDoOutput(true);
			connection.getOutputStream().write(bodyBytes);
			connection.getOutputStream().flush();

			int responseCode = connection.getResponseCode();
			logger.info("connection.getResponseCode()=" + responseCode);

			String responseContentType = connection.getHeaderField("Content-Type");
			logger.info("responseContentType=" + responseContentType);

			StringBuilder response = new StringBuilder();

			try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {

				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
				return response.toString();
			}
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;

	}



	
	public void addpgPushNotification(PgPushNotificationRequest pgpushnotifactionRequest) {

		logger.info("add pgpushnotifactionRequest queue => " + pgpushnotifactionRequest);
//		if (processorIdAndPortalIdRequest ==null && processorIdAndPortalIdRequest.getProcessorId() ==null && processorIdAndPortalIdRequest.getTrasnactionId()==null) {
//			return;
//		}

		Gson gson = new Gson();
		String requestString = "";
		try {
			PropertiesVo properties = new PropertiesVo();
			properties = propertiesService.findByPropertykeyWithUpdatedCerts(RemoteDbUtil.PG_PUSH_NOTIFICATION_URL);
			String apiUrl = properties.getPropertyValue();

			requestString = gson.toJson(pgpushnotifactionRequest);
			logger.info("pgpushnotifactionRequest requestString => " + requestString);

			String responseString = callApi(requestString, apiUrl);
			logger.info("pgpushnotifaction response => " + " :: " + responseString);

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			logger.error(e.getMessage());
		}
	
	}
	
	public void addtopgPushNotifiactionQueue(TransactionLog log) {
		try {

			logger.info("Inside add to pg Push Notifiaction..............");
			PgPushNotificationRequest pgPushNotificationRequest = new PgPushNotificationRequest();
			pgPushNotificationRequest.setPgTxnId(String.valueOf(log.getTransactionId()));
			pgPushNotificationRequest.setPortalTxnId(log.getMerchanttxnid());

			addpgPushNotification(pgPushNotificationRequest);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
