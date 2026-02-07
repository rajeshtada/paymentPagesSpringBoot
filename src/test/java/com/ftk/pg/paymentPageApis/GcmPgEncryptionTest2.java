package com.ftk.pg.paymentPageApis;

import java.math.BigDecimal;

import com.ftk.pg.encryption.GcmPgEncryption;
import com.ftk.pg.modal.TransactionLog;
import com.ftk.pg.requestvo.TokenRequest;
import com.google.gson.Gson;

public class GcmPgEncryptionTest2 {

	public static void main(String[] args) throws Exception {
		
//		TransactionLog transaction=new TransactionLog();
//		transaction.setTotalServiceCharge(10);
//		transaction.setCommision(new BigDecimal(10));
		
//		
//		BigDecimal amount = BigDecimal.valueOf(Double.parseDouble("1.00"))
//				.add(BigDecimal.valueOf(transaction.getTotalServiceCharge()).add(transaction.getCommision()));
//System.out.println("amount==>"+amount);

		String token = "888050ae-ca9f-4483-bc53-5730c7a20d90";

		String ivKey = token;

		String deReq = "{\"token\":\"" + token + "\"}";


		deReq = "{\n" + "    \"method\": \"ccPay\",\n" + "    \"requestParams\": {\n"
				+ "        \"browserLanguage\": \"en-GB\",\n" + "        \"browserColorDepth\": \"24\",\n"
				+ "        \"browserScreenHeight\": \"768\",\n" + "        \"browserScreenWidth\": \"1366\",\n"
				+ "        \"browserTZ\": \"-330\",\n" + "        \"javaEnabled\": \"false\",\n"
				+ "        \"jsEnabled\": \"true\",\n" + "        \"ipAddress\": \"null\",\n"
				+ "        \"amt\": \"10\",\n" + "        \"txncurr\": \"INR\",\n"
				+ "        \"paymentMode\": \"CC\",\n" + "        \"transactionId\": \"515757413\",\n"
				+ "        \"login\": \"testmerchant\",\n" + "        \"number\": \"5100010000000049\",\n"
				+ "        \"month\": \"02\",\n" + "        \"year\": \"2027\",\n" + "        \"cvc\": \"123\",\n"
				+ "        \"name\": \"siddharth\",\n" + "        \"mobile\": \"6350043232\",\n"
				+ "        \"udf3\": \"s@gmail.com\"\n" + "    }\n" + "}";
		
		deReq="{\n"
				+ "    \"mid\": \"108\",\n"
				+ "    \"cardNo\": \"\",\n"
				+ "    \"paymentMode\": \"CC\",\n"
				+ "    \"amount\": \"1000\",\n"
				+ "    \"bankId\": \"\"\n"
				+ "}";
		deReq = "{\"method\":\"ccPay\",\"requestParams\":{\"browserLanguage\":\"en-US\",\"browserColorDepth\":\"24\",\"browserScreenHeight\":\"768\",\"browserScreenWidth\":\"1366\",\"browserTZ\":\"330\",\"javaEnabled\":\"false\",\"jsEnabled\":\"false\",\"ipAddress\":\"183.83.176.69\",\"amt\":\"1000.0\",\"txncurr\":\"INR\",\"paymentMode\":\"CC\",\"transactionId\":\"515757836\",\"login\":\"TestMerchant\",\"number\":\"4111111111111111\",\"month\":\"02\",\"year\":\"2026\",\"cvc\":\"545\",\"name\":\"test\",\"mobile\":\"9999999999\",\"udf3\":\"s@gmail.com\"}}";

		System.out.println("deReq==>" + deReq);

		String enc = enc(deReq, ivKey);

		String dec = dec(enc, ivKey);

	}

	private static String enc(String data, String ivKey) throws Exception {
		GcmPgEncryption gcmPgEncryption = new GcmPgEncryption(ivKey);
		String enc = gcmPgEncryption.encryptWithMKeys(data);
		System.out.println("enc => " + enc);
		return enc;
	}

	private static String dec(String enc, String ivKey) throws Exception {
		GcmPgEncryption gcmPgEncryption = new GcmPgEncryption(ivKey);
		String dec = gcmPgEncryption.decryptWithMKeys(enc);
		System.out.println("dec => " + dec);
		return dec;
	}

}
