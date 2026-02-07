package com.ftk.pg.paymentPageApis;

import java.io.IOException;

import org.json.JSONObject;

import com.ftk.pg.encryption.GcmPgEncryptionPortal;
import com.ftk.pg.responsevo.GenerateInvoiceV2PgResponse;
import com.ftk.pg.responsevo.GenerateInvoiceV2ResponseData;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GenerateInvoiceV2 {

	// String url =
	// "https://ec2-15-207-56-109.ap-south-1.compute.amazonaws.com/getepayPortal/pg/generateInvoice";
	// String url = "http://164.52.216.34:8085/getepayPortal/pg/generateInvoice";
//	String url = "https://portal.getepay.in:8443/getepayPortal/pg/v2.O/generateInvoice";
//	String url = "https://pay1.getepay.in:8443/getepayPortal/pg/v2/generateInvoice";
	String url = "https://pay1.getepay.in/getepayPortal/pg/v2/generateInvoice";
	String key = "JoYPd+qso9s7T+Ebj8pi4Wl8i+AHLv+5UNJxA3JkDgY=";
//	String key = "TlNB70wDXVrjhOMUa6/wXRnwLDspCoiimsrcttol6ks=";
	String iv = "hlnuyA9b4YxDq6oJSZFl8g==";
//	String iv = "iGu8AjNTnQ86zEA+xr81cQ==";
	String mid = "108";
//	String mid = "121";
	//// Test Bank, Lyra
	String terminalId = "getepay.merchant61062@icici";
//	String terminalId = "getepay.merchant53001@icici";

//	String key = "FwWGX+b4CyADH9a+HxKmPFpCzZsxrzr59J2YSXwZQaQ=";
//	String iv = "tablENlWHig3dh7/h0DM0Q==";
//	String mid = "995732";
//	String terminalId = "Getepay.merchant128457@icici";

	// Kotak
	// String terminalId = "getepay.merchant500000@icici";
	// UCO
	// String terminalId = "getepay.merchant53001@icici";
	// AU
	// terminalId = "merchant29994.augp@aubank";

	// String key = "JoYPd+qso9s7T+Ebj8pi4Wl8i+AHLv+5UNJxA3JkDgY=";
	// String iv = "hlnuyA9b4YxDq6oJSZFl8g==";
	// String mid = "108";
	// String terminalId = "Getepay.merchant61062@icici";

	// Live
	// String mid = "705016";
	// String terminalId = "Getepay.merchant129118@icici";
	// String iv = "9TXpxfuXD6/4RJ0cuOCX0g==";
	// String key = "lGed7VR7NcE1oSeCeUN0ng==";

	// M AU
	// String mid = "2";
	// String terminalId = "merchant29994.augp@aubank";
	// String iv = "wvbxdJzynQEOm+Ocz7DD4g==";
	// String key = "/cRO0RfO0o1eb6r6BoImSg==";

	// String mid = "842824";
	// String terminalId = "Getepay.merchant131335@icici";
	// String mid = "114";
	// String terminalId = "eshiksa25.admin@icici";

	public static void main(String[] args) throws Exception {

		GenerateInvoiceV2 generateInvoiceV2 = new GenerateInvoiceV2();
//		generateInvoice.virtualAccount();
		generateInvoiceV2.invoice();
//		generateInvoice.decrypt();

		// MerchantKeys merchantKeys = PgEncryption.generateKeys();
		// System.out.println(merchantKeys.getKey() + " : " + merchantKeys.getIv());
	}

	private void virtualAccount() throws Exception {

		String req = "{\n" + "    		\"emailId\": \"ani9773@gmail.com\",\n" + "    		\"name\": \"Anirudh\",\n"
				+ "   		\"uniqueid\": \"590479\",\n" + "    		\"mobileNo\": \"9773361932\",\n"
				+ "    		\"merchantRefNo\": \"1690361329716\",\n" + "    		\"udf1\": \"\",\n"
				+ "   		\"udf2\": \"\",\n" + "    		\"udf3\": \"\",\n" + "    		\"udf4\": \"\",\n"
				+ "    		\"udf5\": \"\",\n" + "    		\"virtualAcc\": {\n"
				+ "        		\"balance\": \"30000\",\n" + "        		\"type\": \"virtual_acc\"\n"
				+ "    	  	 }\n" + "	    }";
		System.out.println("req => " + req);
		GcmPgEncryptionPortal gcmPgEncryption = new GcmPgEncryptionPortal(iv, key);
		String request = gcmPgEncryption.encryptWithMKeys(req);

		String finalString = "{\r\n    \"mid\": \"" + mid + "\",\r\n    \"req\": \"" + request
				+ "\",\r\n    \"terminalId\": \"" + terminalId + "\"\r\n}";

		System.out.println("request => " + finalString);

//		String request = "{\r    \"mid\": \"108\",\r    \"terminalId\": \"getepay.merchant61062@icici\",\r    \"req\": \"7DB2F89006B34D954BBD7D5F2AFA5FC1DBD83E8CC176F8A2CF4022E99A057C8E52FC2880FC5397F657C13CB4A501F7DBF68BB7C1BAB0834ED9B32E2F24E2920A25953776DD4BEC5209066E8569243788481CE29DC32A95037C4B62102ABE8870B347D1CB726E807123B7BB4CED8C839501CDBC888A76D931D530BA0CF2DECEED785C98C2A239814218388A034F00ED3650AA6282E7598839DB2D8D0C1DA164FAB46292999F043FE3232D53EE9396A735AF6FC5129802CD2E67A444E4C4A377629EBFA88C3A9C7EB6768F76C293630ABF3060D8904387930F1334DDD12CE4E56C\"\r}";
		String urlS = "https://pay1.getepay.in:8443/getepayPortal/v1/createVirtualVpa";
		String res = postApi(finalString, urlS);
		getResponse(res);
	}

	private void encrypt() throws Exception {

		String req = "{\"txnId\":\"121749235\",\"upiTxnId\":null,\"custRef\":\"319837194310\",\"amount\":\"1.00\",\"txnStatus\":\"SUCCESS\",\"payerVpa\":\"9773361932@ybl\",\"payerVerifiedName\":\"ANIRUDH R PACHISIA\",\"payerMobile\":\"0000000000\",\"payeeVpa\":\"CdyH2EgSnzSKauiuG5d8ipqeCXi77eV4Yz5KTw7mogM=\",\"payeeVerifiedName\":\"AAPDA RAAHAT KOSH 2023\",\"payeeMobile\":\"9418353231\",\"mrchId\":null,\"aggrId\":null,\"txnDateTime\":\"2023-07-17 17:20:34\",\"refId\":\"\",\"refUrl\":null,\"sendNotification\":\"1\",\"riskDescription\":null,\"riskStatus\":0,\"settlement_status\":null,\"settlement_date\":null,\"settlement_amount\":null,\"remarks\":null,\"settlement_account_no\":\"NDA2MTAxMDczODE=\",\"virtualVpa\":\"\"}";
		req = "{\"txnId\":\"121749235\",\"upiTxnId\":null,\"custRef\":\"319837194310\",\"amount\":\"1.00\",\"txnStatus\":\"SUCCESS\",\"payerVpa\":\"9773361932@ybl\",\"payerVerifiedName\":\"ANIRUDH R PACHISIA\",\"payerMobile\":\"0000000000\",\"payeeVpa\":\"CdyH2EgSnzSKauiuG5d8ipqeCXi77eV4Yz5KTw7mogM=\",\"payeeVerifiedName\":\"AAPDA RAAHAT KOSH 2023\",\"payeeMobile\":\"9418353231\",\"mrchId\":null,\"aggrId\":null,\"txnDateTime\":\"2023-07-17 17:20:34\",\"refId\":\"\",\"refUrl\":null,\"sendNotification\":\"1\",\"riskDescription\":null,\"riskStatus\":0,\"settlement_status\":null,\"settlement_date\":null,\"settlement_amount\":null,\"remarks\":null,\"settlement_account_no\":\"NDA2MTAxMDczODE=\",\"virtualVpa\":\"\"}";
		
		GcmPgEncryptionPortal gcmPgEncryption= new GcmPgEncryptionPortal(iv, key);
		req = gcmPgEncryption.encryptWithMKeys(req);
		System.out.println("enc : " + req);
	}

	private void decrypt() throws Exception {
		String req = "	";
		req = "6FC0C9F047F5E06346B5CFB3DAA9839AAB3E17085707BC376DE45B2BA2F77AD2044420CF8F70AB05CA5BF6F4D719F2C0BF55B8D7CC5F89133B815DB25E6C91B3B5F79A0729803B973C418048EB9B1E4A89E693AB902027CE7414372BF4F703977C9DE2E51C9E267313106C4552992CD2C9233A9222B611037827FD32117E8FEC94FEDFDBF70A05CBFB32A5EF85989F7F0565105825F894C1B8C71840332D01EF3B34040C2C8CC1CE1117E951B04333F58C5E140BE72B1DDE1065C3355F83F1D769AF2E21EC8CA48E08AE70CE4CAFBAE36D8485FDDB97EB0E90AA4006A0A3B7F009D58E2012344C3674F2C1107FD53DDD27C431D98FB25F05A3319FA32E8BE25B7B4298D8BCFC01741BBEC96CA8DE5EFF0D655A1C5E63E93A7D6F1B8450136052767E166D0BE26C6CED2C3195C97D52913F307DEBE99B040375D69B70EC1F372754EFA2D15ED08281422F420BBAF15893B46825895FE71CECA702A1115A612FB6E374F368079FD00F40A68F48380597067F36EDB2E4EABA6A5938F6AEC1B0E6E8AACB3A10563280FD3F3CBE0FF59F6B0058AE653D51A0A7405F52B412E35A5874BCF041F36B47B99A9E20EE7A80AEF670A4C2BD49BEAB8C8331F98B26590597546850C75D044CE90043D724EE8778CC71BE31EB543E3C2484650E7691033275BC7B10B6C01EA59BE72BD317D64B3DE7AAA5A0C3879371965E865E38BA3668FE04DF760D09ACB3049FCD80CB2EC5BD7A9C";
	
		GcmPgEncryptionPortal gcmPgEncryption = new GcmPgEncryptionPortal(iv, key);
		String request = gcmPgEncryption.decryptWithMKeys(req);
		
		System.out.println("dec : " + request);
	}

	private void invoice() {
		try {

			System.out.println("url => " + url);

			String request = getRequest();

			String res = postApi(request, url);
			getResponse(res);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getResponse(String reponseString) throws Exception {

		Gson gson = new Gson();
		System.out.println("reponseString => " + reponseString);
//		GenerateInvoiceV2PgResponse pgResponse = gson.fromJson(reponseString, GenerateInvoiceV2PgResponse.class);
//		String response = pgResponse.getResponse();
		JSONObject jsonObj = new JSONObject(reponseString);
		String response = (String) jsonObj.get("response");

		GcmPgEncryptionPortal gcmPgEncryption = new GcmPgEncryptionPortal(iv, key);
		String request = gcmPgEncryption.decryptWithMKeys(response);
		
		System.out.println("response => " + request);
		GenerateInvoiceV2ResponseData responseData = gson.fromJson(request, GenerateInvoiceV2ResponseData.class);
		String paymentUrl = responseData.getPaymentUrl();
		
//		JSONObject responseData = new JSONObject(request);
//		String paymentUrl = (String) responseData.get("paymentUrl");
		
		System.out.println(paymentUrl);
		System.out.println(gson.toJson(responseData));
		
	}

	private String postApi(String finalString, String url) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, finalString);
		Request request1 = new Request.Builder().url(url).method("POST", body)
				.addHeader("Content-Type", "application/json").build();
		Response response = client.newCall(request1).execute();
		String reponseString = response.body().string();
		System.out.println("response => " + reponseString);
		return reponseString;
	}

	private String getRequest() throws Exception {

		String ru = "https://pay1.getepay.in:8443/getepayPortal/login";
		// ru = "https://pay1.getepay.in:8443/getepayPortal/getepayResponse";
		// ru = "https://pay1.getepay.in:8443/getepayPortal/pg/updateStatus";
		ru = "https://pay1.getepay.in:8443/getepayPortal/callbackPG";
		ru = "https://pay1.getepay.in:8443/getepayPortal/pg/v2.O/pgPaymentResponse";
		String req = "";

		String udf6 = "";
		String txnType = "single";
		String paymentMode = "ALL";
		String am = "1";
		// paymentMode = "DYNAMICQR";
		String callbackUrl = "https://pay1.getepay.in:8443/getepayPortal/callbackPG";
		callbackUrl = "https://pay1.getepay.in:8443/getePaymentPages/callBack/allPayment";
		callbackUrl = "https://pay1.getepay.in:8443/getepayPortal/callbackPG";
		// txnType = "multi";
		// udf6 =
		// "PHByb2R1Y3RzPgogICAgPHByb2R1Y3Q+CiAgICAgICAgPGNvZGU+Q29kZTAwMTwvY29kZT4KICAgICAgICA8bmFtZT43MDwvbmFtZT4KICAgICAgICA8YW1vdW50PjM3OTA8L2Ftb3VudD4KICAgIDwvcHJvZHVjdD4KICAgIDxwcm9kdWN0PgogICAgICAgIDxjb2RlPkNvZGUwMDI8L2NvZGU+CiAgICAgICAgPG5hbWU+NzE8L25hbWU+CiAgICAgICAgPGFtb3VudD4yNDI5MDwvYW1vdW50PgogICAgPC9wcm9kdWN0Pgo8L3Byb2R1Y3RzPg==";
		// am = "28080";
		// am = "1";
		req = "{\"mid\":\"" + mid + "\",\"amount\":\"" + am
				+ "\",\"merchantTransactionId\":\"4ee0927a7c3e36acd203\",\"transactionDate\":\"Mon Feb 27 11:02:35 IST 2023\",\"terminalId\":\""
				+ terminalId
				+ "\",\"udf1\":\"6350043232\",\"udf2\":\"siddharth@gmail.com\",\"udf3\":\"siddharth\",\"udf4\":\"14008|254819|2000\",\"udf5\":\"GETEPAYESHF00000227|2023-03-09\",\"udf6\":\""
				+ udf6 + "\",\"udf7\":\"\",\"udf8\":\"\",\"udf9\":\"\",\"udf10\":\"\",\"ru\":\"" + ru
				+ "\\t\",\"callbackUrl\":\"" + callbackUrl + "\",\"currency\":\"INR\",\"paymentMode\":\"" + paymentMode
				+ "\",\"bankId\":\"\",\"txnType\":\"" + txnType
				+ "\",\"productType\":\"IPG\",\"txnNote\":\"Colony world Txn\",\"vpa\":\"" + terminalId + "\"}";

		System.out.println("req => " + req);
		
		GcmPgEncryptionPortal gcmPgEncryption = new GcmPgEncryptionPortal(iv, key);
		req = gcmPgEncryption.encryptWithMKeys(req);
		System.out.println(" encrypted => " + req);

		String finalString = "{\r\n    \"mid\": \"" + mid + "\",\r\n    \"req\": \"" + req
				+ "\",\r\n    \"terminalId\": \"" + terminalId + "\"\r\n}";
		System.out.println(mid + " :: " + terminalId);
		System.out.println("finalString => " + finalString);

		return finalString;

	}

	private String getRequestEshMulti() throws Exception {
		System.out.println("<--- Request Esh Multi ------->");
		// String mid = "855482";
		// String terminalId = "Getepay.merchant131388@icici";

		String req = "{\n" + "    \"mid\": \"" + mid + "\",\n" + "    \"amount\": 11375,\n"
				+ "    \"merchantTransactionId\": \"eshf_64b4eeb1c6d2f7541689579185\",\n"
				+ "    \"transactionDate\": \"Mon Jul 17 13:03:30 IST 2023\",\n" + "    \"terminalId\": \"" + terminalId
				+ "\",\n" + "    \"udf1\": \"7300068952\",\n" + "    \"udf2\": \"123456\",\n"
				+ "    \"udf3\": \"Mansavi Gupta\",\n" + "    \"udf4\": \"573611|855482|11375\",\n"
				+ "    \"udf5\": \"GETEPAYESHF00573611|2023-07-17\",\n"
				+ "    \"udf6\": \"PHByb2R1Y3RzPjxwcm9kdWN0Pjxjb2RlPklUQ0ZFRUNvbnRpZ2VuY2llczwvY29kZT48bmFtZT42NTAzMzwvbmFtZT48YW1vdW50PjM1MDA8L2Ftb3VudD48L3Byb2R1Y3Q+PHByb2R1Y3Q+PGNvZGU+SVRDRkVFTW9uZXk8L2NvZGU+PG5hbWU+NjUwMzQ8L25hbWU+PGFtb3VudD4yMDAwPC9hbW91bnQ+PC9wcm9kdWN0Pjxwcm9kdWN0Pjxjb2RlPklUQ0ZFRVNHQTwvY29kZT48bmFtZT42NTAzNTwvbmFtZT48YW1vdW50PjEwMDA8L2Ftb3VudD48L3Byb2R1Y3Q+PHByb2R1Y3Q+PGNvZGU+SVRDRkVFQ0xHPC9jb2RlPjxuYW1lPjY1MDQ0PC9uYW1lPjxhbW91bnQ+NDg3NTwvYW1vdW50PjwvcHJvZHVjdD48L3Byb2R1Y3RzPg==\",\n"
				+ "    \"udf7\": \"\",\n" + "    \"udf8\": \"\",\n" + "    \"udf9\": \"\",\n" + "    \"udf10\": \"\",\n"
				+ "    \"ru\": \"https:\\/\\/erp.eshiksa.net\\/DirectFeesv3\\/ITLucknowUGPay\\/response\",\n"
				+ "    \"callbackUrl\": \"https:\\/\\/erp.eshiksa.net\\/esh\\/index.php?plugin=Studenttemplates&action=getepayV2OnlineFormCallbackTxn\",\n"
				+ "    \"currency\": \"INR\",\n" + "    \"paymentMode\": \"NB\",\n" + "    \"bankId\": \"\",\n"
				+ "    \"txnType\": \"multi\",\n" + "    \"productType\": \"IPG\",\n"
				+ "    \"txnNote\": \"eShiksa v2 Txn\",\n" + "    \"vpa\": \"" + terminalId + "\"\n" + "}";

		System.out.println("req => " + req);
		
		GcmPgEncryptionPortal gcmPgEncryption = new GcmPgEncryptionPortal(iv, key);
		req = gcmPgEncryption.encryptWithMKeys(req);

		System.out.println(" encrypted => " + req);

		String finalString = "{\r\n    \"mid\": \"" + mid + "\",\r\n    \"req\": \"" + req
				+ "\",\r\n    \"terminalId\": \"" + terminalId + "\"\r\n}";
		System.out.println(mid + " :: " + terminalId);
		System.out.println("finalString => " + finalString);

		return finalString;
	}
	}
