package com.ftk.pg.nbbl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.ftk.pg.util.NbblEncryptionUtil;
import com.ftk.pg.vo.nbbl.NbblRequestWrapper;
import com.ftk.pg.vo.nbbl.Signature;
import com.google.gson.Gson;


public class NbblEncryprionUtilTest {

	public static void main(String[] args) throws Exception {
		String payLoadRequest = "{\"test\" : \"hello world\"}";
		System.out.println("DecPayLoad :" + payLoadRequest);
		
		String upiPrivateKeyCrt = "/media/shared/nbbl/key_file/ec_keys/nbbl-hpy-key.pem";
//		String publicKeyCertificateFile = "/media/shared/nbbl/key_file/ec_keys/nbbl-hpy-pub.txt";
		String publicKeyCertificateFile = "/media/shared/nbbl/key_file/ec_keys/IBMB_PublicKey.txt";
		
		NbblEncryptionUtil nbblEncryptionUtil = new NbblEncryptionUtil(upiPrivateKeyCrt, publicKeyCertificateFile);
		String encryptPayload = nbblEncryptionUtil.encryptPayload(payLoadRequest);
		
		System.out.println(encryptPayload);
		
		//BEiaO7PFeIscWGUDL42pf6+LWt9ny4g4DFHHQZ1srKW2kQru8V2wguj+3Phe9O/pxq4NbG92awfYH4mMqarhg+g7+TmzR+7rSrI4+dnBg56ihdgLjXLoHOHq5S0+BqcA87vgVPiKcxmUG4sGgOb2l7uV8OLvnqTd1zmfPGX0kS3RRDxJzETJZOVWjFE=
		//BCxzIAZOJz0JvpNuSui75hrSQxdd3qc3g0w9/7L7u/5Kl/A1pmwiDHHtsvpelTqc3j1ozXO9GprTtvVchmkcpcFf1ojbfEbuT6Rf/WBnAjUueInwQGaBWQXWCCZrwGd28PA4KSKZMGwVi/bAjsmlrRgfnjtvQWpJn5vtdRWnO2hANGUlJI82ZnI+ibk=
	
	String encPayload = "aTMDynoLaMm2UlqwnYNGOHL92yLSde1e+mLmU0ZgRcWU4uCqReIQg9hFDLV5skmjtZXdKomROmQT/PMx01oOfwQhyetLG5PM06BhLfcqyFP6LGYPIuIumokJ2/mnlHpf8voUuHOI1D2aPcbvMDdU/J3jtL7D3Tjddd8cvqOOr6o3NB7UIafl+TyRXxcqQgzjBUDLBhpYEm85xP4TzOGVzSBpWy3Ghm+qmkxSYjx2fhnQ+ONAlv4mBBaFQGLKxK8Cm8Qr0zz7vmwC9rACW/Nqe7nsDiIf5P4imIkT915oxLF+MqfcOLgUkuxXH0pxTKxa19m/K+nkHhWqCtcU6BCUJXuYfuhf8yX0Gi7GxZcgy75XdGhWXjq2k1i1nnhJ1k80ozYHtmgDlt7h6pPzQS8HjFS/8uCpDsUxdIkWzwjT7bBS0fdqSbEVfZqIeS3nV9EmuD+RzW2DreOi9pNOxvSvJvoOWJZnlx8Aq/okTsFy3flbYDsvYjDahEnG19U6qMrTTKEM/p9pa1KrxcpyI+FEez1JhnZl+FwiTjxrzLLEJLL+r9EAnjKZUqSw2cMO/rxDN1GOjpnc283772TBVR4QF4JAZjvgAymYVrHAW/hJ7Wxu94QMdR51FFnAxnWqztDu3W9VmeOqHLQKeqbHxcH386SJlt13nofudq+Ex35+bK3z&keyVer=1";
	String decryptPayload = nbblEncryptionUtil.decryptPayload(encPayload);
	System.out.println("decryptPayload : "+decryptPayload);
	}
	
	public static void main1(String[] args) throws Exception {
		// Sample content to hash
//		String payLoadRequest = "{\n"
//				+ "    \"head\": {\n"
//				+ "        \"ver\": \"1.0\",\n"
//				+ "        \"ts\": \"2024-11-19T15:22:13+05:30\",\n"
//				+ "        \"msgID\": \"MSGIDJM5TNJOC5NDVP5X9HKDTB943241525\",\n"
//				+ "        \"orgID\": \"PAG01\"\n"
//				+ "    }\n"
//				+ "}";
		String payLoadRequest = "{\"head\":{\"ver\":\"1.0\",\"ts\":\"2025-08-19T14:59:47+05:30\",\"msgID\":\"EXXGXVQFBCMP12I3KQ7BMP2KRLV52311459\",\"bankID\":\"BAN01\",\"orgID\":\"PGP17\",\"correlationKey\":\"\"},\"txn\":{\"refID\":\"PGP175231H0574968335\",\"ts\":\"2025-08-19T14:59:47+05:30\",\"expiry\":300,\"initiationMode\":\"REDIRECTION\"},\"pa\":{\"paID\":\"PGP17\",\"paName\":\"Getepay\",\"creds\":{\"type\":\"GSTN\",\"value\":\"27BBBBB0000A1Z6\"}},\"merchant\":{\"mcc\":\"5411\",\"mid\":\"PGP17KRSHNMRT05\",\"mName\":\"Shree Krishna Retail Pvt Ltd\",\"returnUrl\":{\"success\":\"https://pay1.getepay.in/pg/pg/api/nbblResponse/refID\"}},\"payer\":{\"amount\":{\"value\":10.0,\"curr\":\"INR\",\"amountBreakUp\":{\"tag\":[{\"name\":\"Convenience Charges\",\"value\":\"12\"}]}},\"device\":{\"mobile\":\"919876543210\",\"tag\":[{\"name\":\"OS\",\"value\":\"IOS\"}]}},\"additionalInfo\":[{\"name\":\"SampleadditionalInfo\",\"value\":\"sampleAddInfoValue\",\"visibility\":false}]}";
		System.out.println("DecPayLoad :" + payLoadRequest);

		LocalDateTime createdDate = LocalDateTime.now();
		ZonedDateTime zonedDateTime = createdDate.atZone(ZoneId.systemDefault());
//	    long created = zonedDateTime.toInstant().toEpochMilli();
//	    long expires = created + (150 * 1000);
	    long created = 1755595787902L;
	    long expires = created + (150 * 1000);

		String upiPrivateKeyCrt = "/media/shared/nbbl/key_file/ec_keys/nbbl-hpy-key.pem";
//		String publicKeyCertificateFile = "/media/shared/nbbl/key_file/ec_keys/IBMB_PublicKey.txt";
		String publicKeyCertificateFile = "/media/shared/nbbl/key_file/ec_keys/nbbl-hpy-pub.txt";

//		String upiPrivateKeyCrt = "/media/shared/nbbl/key_file/nbbl-hpy-key.pem";
//		String publicKeyCertificateFile = "/media/shared/nbbl/key_file/nbbl-hpy-cert.pem";
		
		NbblEncryptionUtil nbblEncryptionUtil = new NbblEncryptionUtil(upiPrivateKeyCrt, publicKeyCertificateFile);

		String keyId = "PGP17";
		String protectedInfoBase64 = nbblEncryptionUtil.buildProtectedInfoBase64(keyId, created, expires);
		System.out.println("protectedInfo (Base64): " + protectedInfoBase64);
		
		String base64Signature = nbblEncryptionUtil.signECDSA(payLoadRequest, created, expires);
		System.out.println("Signature (Base64): " + base64Signature);
		
		NbblRequestWrapper nbblRequestWrapper = new NbblRequestWrapper();
		Signature signature = new Signature();
		signature.setProtectedValue(protectedInfoBase64);
		signature.setSignature(base64Signature);
		nbblRequestWrapper.setSignature(signature);
		
		String encryptPayload = nbblEncryptionUtil.encryptPayload(payLoadRequest);
		nbblRequestWrapper.setPayload(encryptPayload);
		
		
		
		Gson gson=new Gson();
		String finalRequestPayload=gson.toJson(nbblRequestWrapper);
		System.out.println("finalRequestPayload : "+ finalRequestPayload);
		
//		String responseSignature = nbblEncryptionUtil.signECDSA(payLoadRequest, created, expires);
//		boolean verifyECDSA = nbblEncryptionUtil.verifyECDSA(payLoadRequest, base64Signature);
//		System.out.println("Signature verified: " + verifyECDSA);
		
		
		// Encryption cycle end
		System.out.println("Encryption cycle end ============================================= ");
		
//		String upiPrivateKeyCrt2 = "/media/shared/nbbl/key_file/ec_keys/nbbl-hpy-key.pem";
////		String publicKeyCertificateFile2 = "/media/shared/nbbl/key_file/ec_keys/IBMB_PublicKey.txt";
//		String publicKeyCertificateFile2 = "/media/shared/nbbl/key_file/ec_keys/nbbl-hpy-pub.txt";
//
//		NbblEncryptionUtil nbblEncryptionUtil2 = new NbblEncryptionUtil(upiPrivateKeyCrt2, publicKeyCertificateFile2);
		
		
		
		String decryptPayload = nbblEncryptionUtil.decryptPayload(encryptPayload);
		
		boolean verifySignature = nbblEncryptionUtil.verifySignature(finalRequestPayload);
		System.out.println("verifySignature : "+verifySignature);
		
		

		
	}


}
