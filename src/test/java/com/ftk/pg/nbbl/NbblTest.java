package com.ftk.pg.nbbl;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.ftk.pg.util.NbblEncryptionUtil;
import com.ftk.pg.vo.nbbl.NbblRequestWrapper;
import com.ftk.pg.vo.nbbl.Signature;
import com.ftk.pg.vo.nbbl.reqTxnInit.PaymentRequestNBBL;
import com.google.gson.Gson;


public class NbblTest {
	
	public static void main(String[] args) throws Exception {

		
		initiateTransaction();
	}

	public static String initiateTransaction() throws Exception {

		PaymentRequestNBBL paymentRequestnbbl = PaymentRequestNbblJsonTest.generateNbblRequest();
		String keyId = "PGP17";
		
		Gson gson=new Gson();
		
		String payload=gson.toJson(paymentRequestnbbl);
		
		System.out.println(payload);
		
		String upiPrivateKeyCrt = "/media/shared/nbbl/key_file/ec_keys/nbbl-hpy-key.pem";
		String publicKeyCertificateFile = "/media/shared/nbbl/key_file/ec_keys/nbbl-hpy-pub.txt";
		NbblEncryptionUtil nbblEncryptionUtil = new NbblEncryptionUtil(upiPrivateKeyCrt, publicKeyCertificateFile);
		
//		long created = System.currentTimeMillis();
//		long expires = created + (150 * 1000);
		LocalDateTime createdDate = LocalDateTime.now();
		ZonedDateTime zonedDateTime = createdDate.atZone(ZoneId.systemDefault());
	    long created = zonedDateTime.toInstant().toEpochMilli();
	    long expires = created + (150 * 1000);
	    
		String base64Signature = nbblEncryptionUtil.signECDSA(payload, created, expires);
		System.out.println("Signature (Base64): " + base64Signature);


//		String protectedStringBase64 = nbblEncryptionUtil.buildProtectedInfoBase64(keyId, created, expires);
		String protectedString = String.format(
				"keyId=\"%s|ecdsa\",algorithm=\"ecdsa\",created=\"%d\",expires=\"%d\",headers=\"(created)(expires)digest\"",
				keyId, created, expires);

		System.out.println("nbbl buildProtectedInfo String === " + protectedString);
		String protectedStringBase64 = nbblEncryptionUtil.buildProtectedInfoBase64ByProtectString(protectedString,
				created, expires);
		System.out.println("protectedInfo (Base64): " + protectedStringBase64);
		
		NbblRequestWrapper nbblRequestWrapper = new NbblRequestWrapper();
		Signature signature = new Signature();
		signature.setProtectedValue(protectedStringBase64);
		signature.setSignature(base64Signature);
		nbblRequestWrapper.setSignature(signature);
		
		String encryptPayload = nbblEncryptionUtil.encryptPayload(payload);
		nbblRequestWrapper.setPayload(encryptPayload);
		
		String finalRequestPayload=gson.toJson(nbblRequestWrapper);
		System.out.println("finalRequestPayload : "+ finalRequestPayload);
		
		String decryptPayload = nbblEncryptionUtil.decryptPayload(encryptPayload);
		System.out.println("decryptPayload : "+decryptPayload);
		
		
//		String trasnactionUrl = "https://ibmbcert.npci.org.in/ibmb/ReqTxnInit/1.0/urn:referenceId:PGP174818R0574963605";
//		String postapi = NbblUtil.postapi(finalRequestPayload, trasnactionUrl, "apikey");
//		System.out.println("nbbl trasnaction api response === " + postapi);
		
		
		return null;

	}

}
