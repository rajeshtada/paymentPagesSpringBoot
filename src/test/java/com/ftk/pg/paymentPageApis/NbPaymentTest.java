package com.ftk.pg.paymentPageApis;

import org.json.JSONObject;

import com.ftk.pg.dto.ResponseWrapper;
import com.ftk.pg.util.TestUtil;
import com.google.gson.Gson;

public class NbPaymentTest {

	static String baseUrl = "https://service.getepay.in/pg";
//	static String baseUrl = "https://sandboxapi.getepay.in/pg";
//	static String baseUrl = "http://localhost:8080/pg";

	
	public static void main(String[] args) throws Exception {
		
		
//		String token = "4564e2f6-63ad-4a43-8f8f-3c4058a9d32b";
//		String txnId = "2";
//		String login = "rajesh";
		
		String paymentMode = "NB";
		String method = "nbPay";
		String nbbankid = "1";
		
		String mid = "887729";
		String environment="live";	
		
		GenerateInvoice generateInvoice = new GenerateInvoice(environment,mid);
		String token = generateInvoice.invoice();
		String initiateResponse = initiate(token);
		
		JSONObject jsonObj = new JSONObject(initiateResponse);
		String txnId = (String) jsonObj.get("transactionId");
		JSONObject paymentRequestJson = (JSONObject) jsonObj.get("paymentRequest");
		String login = String.valueOf(paymentRequestJson.get("login"));
		String merchantTxnId = String.valueOf(paymentRequestJson.get("merchantTxnId"));
		
		System.out.println("txnId : "+txnId);
		
		
		String request = "{\n"
				+ "        \"method\": \""+method+"\",\n"
				+ "     	\"requestParams\": {\n"
				+ "            \"browserLanguage\": \"en-GB\",\n"
				+ "            \"browserColorDepth\": \"24\",\n"
				+ "            \"browserScreenHeight\": \"768\",\n"
				+ "            \"browserScreenWidth\": \"1366\",\n"
				+ "            \"browserTZ\": \"-330\",\n"
				+ "            \"javaEnabled\": \"false\",\n"
				+ "            \"jsEnabled\": \"false\",\n"
				+ "            \"ipAddress\": ,\n"
				+ "            \"amt\": \"1.0\",\n"
				+ "            \"txncurr\": \"INR\",\n"
				+ "            \"paymentMode\": \""+paymentMode+"\",\n"
				+ "            \"transactionId\": \""+txnId+"\",\n"
				+ "            \"login\": \""+login+"\",\n"
				+ "            \"number\": null,\n"
				+ "            \"month\": null,\n"
				+ "            \"year\": null,\n"
				+ "            \"cvc\": null,\n"
				+ "            \"name\": \"SIDDHARTH PUROHIT\",\n"
				+ "            \"mobile\": \"6350043232\",\n"
				+ "            \"udf3\": \"siddharth@gmail.com\",\n"
				+ "            \"nbbankid\": \""+nbbankid+"\",\n"
				+ "            \"nbmobile\": \"6350043232\",\n"
				+ "            \"merchantTxnId\": \""+merchantTxnId+"\",\n"
				+ "            \"neftName\": null,\n"
				+ "            \"neftNumber\": null,\n"
				+ "            \"upiId\": null\n"
				+ "    }\n"
				+ "}";
		
		
		System.out.println("Plain ValidateCollectJson : "+request);
		
		String encApiData = TestUtil.enc(request, token);
		String apiRequest = "{\n      \"data\": \"" + encApiData + "\"\n}";
		String finalUrl = baseUrl + "/api/pay";
		System.out.println("finalUrl url : "+finalUrl);
		String postValidateCollectResponse = TestUtil.postApi(finalUrl, apiRequest, token);
		System.out.println("postValidateCollectResponse : "+postValidateCollectResponse);
		
		
		JSONObject postValidateCollectResponseJsonObj = new JSONObject(postValidateCollectResponse);
		String collectResData = (String) postValidateCollectResponseJsonObj.get("data");
		String postDecApi = TestUtil.dec(collectResData, token);
		
		System.out.println("postDecApi : "+ postDecApi);
		
	}
	
	
	
	public static String initiate(String token) throws Exception {
		String ivKey = token;

		String deReq = "{\n" + "        \"token\": \"" + token + "\"\n" + "    }";

		System.out.println("deReq==>" + deReq);
		String enc = TestUtil.enc(deReq, ivKey);

		String url = baseUrl + "/api/payment";
		System.out.println("payment url==>" + url);
		String req = "{\n      \"data\": \"" + enc + "\"\n}";
		System.out.println("final payment Request==>" + req);
		String res = TestUtil.postApi(url, req, token);

		System.out.println(res);

		Gson gson = new Gson();
		ResponseWrapper decRes = gson.fromJson(res, ResponseWrapper.class);
		System.out.println("resData : " + decRes.getData());

		if (decRes != null && decRes.getData() != null) {
			String dec = TestUtil.dec(String.valueOf(decRes.getData()), ivKey);
			return dec;
		}
		return null;
	}
	


}
