package com.ftk.pg.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import com.ftk.pg.requestvo.KotakRequeryRequest;
import com.ftk.pg.responsevo.KotakRequeryResponse;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class KotakRequeryApiCall {

	static Logger logger = LogManager.getLogger(KotakRequeryApiCall.class);

	String generateTokenurl;
	String clientId;
	String clientSecret;

	String refundApiUrl;
	String apiKey;
	String checkSumKey;
	String merchantId;
	

	LocalDateTime txnDate;
//	Long uniqueSequence;
//	String amount;
	String merchantReference;
//	String traceNo;

	
	public static void main(String[] args) {
		try {

			KotakRequeryApiCall kotakRequeryApiCall = new KotakRequeryApiCall();
//			kotakRequeryApiCall.setRefundApiUrl("https://apigwuat.kotak.com:8443/KBSecPG");
//			kotakRequeryApiCall.setApiKey("211995C8985ABA0F291E1941608FEFBD");
//			kotakRequeryApiCall.setCheckSumKey("KMBANK");
//			kotakRequeryApiCall.setMerchantId("EOSFC");
//			kotakRequeryApiCall.setGenerateTokenurl("https://apigwuat.kotak.com:8443/k2/auth/oauth/v2/token");
//			kotakRequeryApiCall.setClientId("3b4c289e-c52c-4d77-a59c-34f6357f431f");
//			kotakRequeryApiCall.setClientSecret("119c9fb7-1c1d-4ac1-a4b8-4a4fe64693ed");
//			LocalDateTime ldt = LocalDateTime.now();
//			kotakRequeryApiCall.setTxnDate(/* "2023-05-22 19:41:56.551000" */ldt);
//			kotakRequeryApiCall.setUniqueSequence(34l);
//			kotakRequeryApiCall.setAmount("10");
//			kotakRequeryApiCall.setMerchantReference("2427401388304019");

//			String uniq = "" + System.currentTimeMillis();
//			kotakRequeryApiCall.setTraceNo(uniq);

			KotakRequeryResponse kotakRefundResponse = kotakRequeryApiCall.kotakRequeryApi();
//			logger.info(kotakRefundResponse);
			System.out.println("kotakRequeryResponse===> "+kotakRefundResponse);
			// kotakRefundApiCall.generateToken();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public KotakRequeryResponse kotakRequeryApi() {
		try {

			String encrytString = null;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHH");
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

//		LocalDateTime transactionDate = LocalDateTime.parse(txnDate, dateTimeFormatter);
			// LocalDateTime transactionDate = LocalDateTime.now();

//		String uniqueRefNo = getRefNo(transactionDate, uniqueSequence);
//		String date = formatter.format(transactionDate);

			KotakRequeryRequest request = new KotakRequeryRequest();
			request.setMessageCode("0520");
			request.setDateAndTime(formatter.format(txnDate));
			request.setMerchantId(merchantId);
			request.setMerchantReference(merchantReference);
			request.setFuture1("");
			request.setFuture2("");
			request.setCheckSum("");

			String pipeSeprated = request.getRequestToCalculateChecksum();
			logger.info("requestStringToCalculateChecksum => " + pipeSeprated);
//		pipeSeprated = "0050|1705202315|EOSFC|2313715440000028|3816690123|10|||";
//		logger.info("requestStringToCalculateChecksum => " + pipeSeprated);

			String checkSum = KotakUtils.getHMAC256Checksum(pipeSeprated, checkSumKey);
			request.setCheckSum(checkSum.toLowerCase());
			String finalRequest = request.getRequestWithChecksum();
			logger.info("request => " + request);

//		String pipeSeprated = "0050|1705202315|EOSFC|2313715440000028|3816690123|10|||";
////		pipeSeprated = "0050|1705202315|EOSFC|" + uniq +"|3816690123|10|||";
//		String checkSum = KotakUtils.getHMAC256Checksum(pipeSeprated, checkSumKey);
//		String finalRequest = pipeSeprated + "|" + checkSum;

			logger.info("requestStringToCalculateChecksum => " + pipeSeprated);
			logger.info("requestString => " + finalRequest);

			try {
				encrytString = KotakUtils.encrypt(String.valueOf(finalRequest), apiKey);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("final ===> " + encrytString);
			String token = generateToken();
			logger.info("token => " + token);
			String responseString = postApi(refundApiUrl, encrytString, token);

			try {
				responseString = KotakUtils.decrypt(String.valueOf(responseString), apiKey);
				logger.info("decrypted result = " + responseString);
				KotakRequeryResponse kotakResponse = new KotakRequeryResponse(responseString);
				logger.info("kotakResponse => " + kotakResponse);
				return kotakResponse;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String postApi(String authUrl, String requestString, String token) {
		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, requestString);
			Request request = new Request.Builder().url(authUrl).method("POST", body)
					.addHeader("Content-Type", "application/json").addHeader("Authorization", "Bearer " + token)
					.build();
			Response response = client.newCall(request).execute();
			logger.info("response : " + response);
			String responseString = response.body().string();
			logger.info("response ==> " + responseString);
			if (response.code() == 200) {
				return responseString;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public  String getRefNo(LocalDateTime transactionDate, Long uniqueSequenceNo) {
		String yearString = String.valueOf(transactionDate.getYear());
		String year = yearString.substring(2);
		String uniqueRefNo = year;

		String juleanDay = String.valueOf(transactionDate.getDayOfYear());
		while (juleanDay.length() < 3) {
			juleanDay = "0" + juleanDay;
		}
		uniqueRefNo += juleanDay;

		String hour = String.valueOf(transactionDate.getHour());

		if (hour.length() < 2) {
			hour = "0" + hour;
		}

		String min = String.valueOf(transactionDate.getMinute());

		if (min.length() < 2) {
			min = "0" + min;
		}

		uniqueRefNo += hour;
		uniqueRefNo += min;

		String uniqueSequenceNoString = String.valueOf(uniqueSequenceNo);
		while (uniqueSequenceNoString.length() < 7) {
			uniqueSequenceNoString = "0" + uniqueSequenceNoString;
		}
		uniqueRefNo += uniqueSequenceNoString;
		return uniqueRefNo;
	}

	private String generateToken() {
		try {
			OAuthClient client = new OAuthClient(new URLConnectionClient());

			OAuthClientRequest request = OAuthClientRequest.tokenLocation(generateTokenurl)
					.setGrantType(GrantType.CLIENT_CREDENTIALS).setClientId(clientId).setClientSecret(clientSecret)
					.buildBodyMessage();

			logger.info(request.getBody());

			String token = client.accessToken(request, OAuth.HttpMethod.POST, OAuthJSONAccessTokenResponse.class)
					.getAccessToken();

			return token;
		} catch (Exception exn) {
			exn.printStackTrace();
		}
		return "";
	}

	public String getGenerateTokenurl() {
		return generateTokenurl;
	}

	public void setGenerateTokenurl(String generateTokenurl) {
		this.generateTokenurl = generateTokenurl;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getCheckSumKey() {
		return checkSumKey;
	}

	public void setCheckSumKey(String checkSumKey) {
		this.checkSumKey = checkSumKey;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getRefundApiUrl() {
		return refundApiUrl;
	}

	public void setRefundApiUrl(String refundApiUrl) {
		this.refundApiUrl = refundApiUrl;
	}

	public LocalDateTime getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(LocalDateTime txnDate) {
		this.txnDate = txnDate;
	}



	public String getMerchantReference() {
		return merchantReference;
	}

	public void setMerchantReference(String merchantReference) {
		this.merchantReference = merchantReference;
	}

	@Override
	public String toString() {
		return "KotakRequeryApiCall [generateTokenurl=" + generateTokenurl + ", clientId=" + clientId
				+ ", clientSecret=" + clientSecret + ", refundApiUrl=" + refundApiUrl + ", apiKey=" + apiKey
				+ ", checkSumKey=" + checkSumKey + ", merchantId=" + merchantId + ", txnDate=" + txnDate
				+ ", merchantReference=" + merchantReference + "]";
	}
	
	
}
