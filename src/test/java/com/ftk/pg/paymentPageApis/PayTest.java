package com.ftk.pg.paymentPageApis;

import com.ftk.pg.dto.ResponseWrapper;
import com.ftk.pg.util.TestUtil;
import com.google.gson.Gson;

public class PayTest {

//	static String baseUrl = "https://eovtg2gb67.execute-api.ap-south-1.amazonaws.com/UAT";
	static String baseUrl = "https://sandboxapi.getepay.in";

	String method;
	String token;

//	String paymentMode;

//	public PayTest(String method,String token, ) {
//		this.method = method;
//	}

//	public PayTest() {
//
//	}

	public PayTest(String method, String token) {
		this.method = method;
		this.token = token;
	}

	public static void main(String[] args) throws Exception {

		String token = "d5f421af-f9e2-4862-8045-af27483e866c";

		PayTest payTest = new PayTest("ccPay", token);
		payTest.pay();

	}

	private void pay() throws Exception {

//		String token = token;
		String ivKey = token;

		String deReq = getRequest(method);

		System.out.println("deReq==>" + deReq);

		String enc = TestUtil.enc(deReq, ivKey);

		String url = baseUrl + "/pg/api/pay";
		String req = "{\n      \"data\": \"" + enc + "\"\n}";
		String res = TestUtil.postApi(url, req, token);

		System.out.println(res);
		Gson gson = new Gson();
		ResponseWrapper decRes = gson.fromJson(res, ResponseWrapper.class);
		System.out.println("resData : " + decRes.getData());

		if (decRes != null && decRes.getData() != null) {
			String dec = TestUtil.dec(String.valueOf(decRes.getData()), ivKey);
			System.out.println(dec);
		}

	}

	private static String getRequest(String method) {
		String deReq = "";
		if (method.equalsIgnoreCase("ccPay")) {

			String transactionId = "515757857";
			String number = "5100010000000049";
			String month = "02";
			String year = "2027";

			String cvc = "123";
			deReq = "{\n" + "    \"method\": \"ccPay\",\n" + "    \"requestParams\": {\n"
					+ "        \"browserLanguage\": \"en-US\",\n" + "        \"browserColorDepth\": \"24\",\n"
					+ "        \"browserScreenHeight\": \"768\",\n" + "        \"browserScreenWidth\": \"1366\",\n"
					+ "        \"browserTZ\": \"330\",\n" + "        \"javaEnabled\": \"false\",\n"
					+ "        \"jsEnabled\": \"false\",\n" + "        \"ipAddress\": \"183.83.176.69\",\n"
					+ "        \"amt\": \"1.0\",\n" + "        \"txncurr\": \"INR\",\n"
					+ "        \"paymentMode\": \"CC\",\n" + "        \"transactionId\": \"559445141\",\n"
					+ "        \"login\": \"887729\",\n" + "        \"number\": \"4315814079970006\",\n"
					+ "        \"month\": \"04\",\n" + "        \"year\": \"2030\",\n" + "        \"cvc\": \"871\",\n"
					+ "        \"name\": \"SIDDHARTH PUROHIT\",\n" + "        \"mobile\": \"6350043232\",\n"
					+ "        \"udf3\": \"s@gmail.com\",\n" + "        \"nbbankid\": null,\n"
					+ "        \"nbmobile\": null,\n" + "        \"merchantTxnId\": null,\n"
					+ "        \"neftName\": null,\n" + "        \"neftNumber\": null,\n" + "        \"upiId\": null\n"
					+ "    }\n" + "}";

			deReq = "{\n" + "    \"method\": \"ccPay\",\n" + "    \"requestParams\": {\n"
					+ "        \"browserLanguage\": \"en-US\",\n" + "        \"browserColorDepth\": \"24\",\n"
					+ "        \"browserScreenHeight\": \"768\",\n" + "        \"browserScreenWidth\": \"1366\",\n"
					+ "        \"browserTZ\": \"330\",\n" + "        \"javaEnabled\": \"false\",\n"
					+ "        \"jsEnabled\": \"false\",\n" + "        \"ipAddress\": \"183.83.176.69\",\n"
					+ "        \"amt\": \"1.0\",\n" + "        \"txncurr\": \"INR\",\n"
					+ "        \"paymentMode\": \"CC\",\n" + "        \"transactionId\": \"559445141\",\n"
					+ "        \"login\": \"887729\",\n" + "        \"number\": \"4315814079970006\",\n"
					+ "        \"month\": \"04\",\n" + "        \"year\": \"2030\",\n" + "        \"cvc\": \"871\",\n"
					+ "        \"name\": \"SIDDHARTH PUROHIT\",\n" + "        \"mobile\": \"6350043232\",\n"
					+ "        \"udf3\": \"s@gmail.com\",\n" + "        \"nbbankid\": null,\n"
					+ "        \"nbmobile\": null,\n" + "        \"merchantTxnId\": null,\n"
					+ "        \"neftName\": null,\n" + "        \"neftNumber\": null,\n" + "        \"upiId\": null\n"
					+ "    }\n" + "}";

		} else if (method.equalsIgnoreCase("dcPay")) {
			deReq = "{\n" + "    \"browserLanguage\": \"en-GB\",\n" + "    \"browserColorDepth\": \"24\",\n"
					+ "    \"browserScreenHeight\": \"768\",\n" + "    \"browserScreenWidth\": \"1366\",\n"
					+ "    \"browserTZ\": \"-330\",\n" + "    \"javaEnabled\": \"false\",\n"
					+ "    \"jsEnabled\": \"true\",\n" + "    \"ipAddress\": \"null\",\n" + "    \"amt\": \"10\",\n"
					+ "    \"txncurr\": \"INR\",\n" + "    \"paymentMode\": \"DC\",\n"
					+ "    \"transactionId\": \"515756671\",\n" + "    \"login\": \"testmerchant\",\n"
					+ "    \"number\": \"4111111111111111\",\n" + "    \"month\": \"02\",\n"
					+ "    \"year\": \"2027\",\n" + "    \"cvc\": \"123\",\n" + "    \"name\": \"siddharth\",\n"
					+ "    \"mobile\": \"6350043232\",\n" + "    \"udf3\": \"s@gmail.com\"\n" + "}";
		} else if (method.equalsIgnoreCase("nbPay")) {
			deReq = "{\n" + "    \"method\": \"nbPay\",\n" + "    \"requestParams\": {\n"
					+ "        \"browserLanguage\": \"en-US\",\n" + "        \"browserColorDepth\": \"24\",\n"
					+ "        \"browserScreenHeight\": \"768\",\n" + "        \"browserScreenWidth\": \"1366\",\n"
					+ "        \"browserTZ\": \"330\",\n" + "        \"javaEnabled\": \"false\",\n"
					+ "        \"jsEnabled\": \"false\",\n" + "        \"ipAddress\": \"183.83.176.69\",\n"
					+ "        \"amt\": \"1\",\n" + "        \"txncurr\": \"INR\",\n"
					+ "        \"paymentMode\": \"NB\",\n" + "        \"transactionId\": \"515758272\",\n"
					+ "        \"login\": \"TestMerchant\",\n" + "        \"number\": null,\n"
					+ "        \"month\": null,\n" + "        \"year\": null,\n" + "        \"cvc\": null,\n"
					+ "        \"name\": \"Eshiksa Branch\",\n" + "        \"mobile\": null,\n"
					+ "        \"udf3\": \"s@gmail.com\",\n" + "        \"nbbankid\": \"40\",\n"
					+ "        \"nbmobile\": \"6350043232\",\n" + "        \"merchantTxnId\": \"19162478\"\n"
					+ "    }\n" + "}";
		} else if (method.equalsIgnoreCase("upiPay")) {
			deReq = "{\n" + "    \"method\": \"upiPay\",\n" + "    \"requestParams\": {\n"
					+ "        \"browserLanguage\": \"en-US\",\n" + "        \"browserColorDepth\": \"24\",\n"
					+ "        \"browserScreenHeight\": \"768\",\n" + "        \"browserScreenWidth\": \"1366\",\n"
					+ "        \"browserTZ\": \"330\",\n" + "        \"javaEnabled\": \"false\",\n"
					+ "        \"jsEnabled\": \"false\",\n" + "        \"ipAddress\": \"183.83.176.69\",\n"
					+ "        \"amt\": \"1000.0\",\n" + "        \"txncurr\": \"INR\",\n"
					+ "        \"paymentMode\": \"UPI\",\n" + "        \"transactionId\": \"515759677\",\n"
					+ "        \"login\": \"887729\",\n" + "        \"number\": null,\n" + "        \"month\": null,\n"
					+ "        \"year\": null,\n" + "        \"cvc\": null,\n" + "        \"name\": null,\n"
					+ "        \"mobile\": null,\n" + "        \"udf3\": \"s@gmail.com\",\n"
					+ "        \"nbbankid\": null,\n" + "        \"nbmobile\": null,\n"
					+ "        \"merchantTxnId\": null,\n" + "        \"neftName\": null,\n"
					+ "        \"neftNumber\": null,\n" + "        \"upiId\": \"6350043232@apl\"\n" + "    }\n" + "}";
		} else if (method.equalsIgnoreCase("neftPay")) {
		} else if (method.equalsIgnoreCase("challanPay")) {
		} else if (method.equalsIgnoreCase("walletPay")) {
		} else {
			System.out.println("pay method not valid => " + method);
		}

		return deReq;
	}

}
