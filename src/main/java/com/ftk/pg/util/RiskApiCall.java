package com.ftk.pg.util;

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

import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.requestvo.RiskApiRequestVo;
import com.ftk.pg.responsevo.RiskApiResponseVo;
import com.google.gson.Gson;

public class RiskApiCall {

	static Logger logger = LogManager.getLogger(RiskApiCall.class);

	public static void main(String[] args) throws Exception {
		RiskApiCall riskApiCall = new RiskApiCall();
		riskApiCall.testRiskApi();
	}

	private void testRiskApi() throws Exception {

		RiskApiRequestVo requestVo = new RiskApiRequestVo();

		String riskApiUrl = "https://fq352im6q9.execute-api.ap-south-1.amazonaws.com/v1/post_risk/";
		String apiKey = "";

		requestVo.setAmount(10.0);
		requestVo.setIp("183.83.177.146");
		requestVo.setMid(1l);
		requestVo.setMobile("6350043232");
		requestVo.setPayment_mode("NB");
		requestVo.setRrn("123456789");
		requestVo.setTransaction_id(18384886l);

		Gson gson = new Gson();

		String requestString = gson.toJson(requestVo);
		logger.info("riskrequestVo =>" + requestString);

		String responseString = callRiskApi(requestString, riskApiUrl, apiKey);
		logger.info("riskresponseString => " + responseString);

		RiskApiResponseVo response = parseResponse(responseString);
		logger.info("riskresponse => " + responseString);
	}

	public RiskApiResponseVo parseResponse(String responseString) {
		if (responseString == null) {
			return null;
		}
		try {
			Gson gson = new Gson();
			RiskApiResponseVo response = gson.fromJson(responseString, RiskApiResponseVo.class);
			return response;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;

	}

	public String callRiskApi(String requestBody, String apiUrl, String apiKey) {
		try {
			URL url = new URL(apiUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");

			byte[] bodyBytes = requestBody.getBytes(StandardCharsets.UTF_8);

			Map<String, String> headers = new LinkedHashMap<>();
			headers.put("Content-Type", "application/json");
			headers.put("x-api-key", apiKey /* "T4rPUAFzCk7NBzNmcOSlZ5pKSIDhv26f5vH4mIJQ" */);

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

}