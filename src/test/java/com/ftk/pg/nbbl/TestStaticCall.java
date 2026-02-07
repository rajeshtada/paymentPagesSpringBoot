package com.ftk.pg.nbbl;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Date;

import org.json.JSONObject;

import com.ftk.pg.util.NbblEncryptionUtil;
import com.ftk.pg.util.NbblUtil;
import com.ftk.pg.vo.nbbl.NbblRequestWrapper;
import com.ftk.pg.vo.nbbl.Signature;
import com.google.gson.Gson;

public class TestStaticCall {

	
	public static void main(String[] args) throws Exception {
		
		System.out.println("nbbl Test start : "+ new Date());
		
		String payload = args[0];
		
		String upiPrivateKeyCrt = "/media/shared/nbbl/key_file/ec_keys/nbbl-hpy-key.pem";
		String publicKeyCertificateFile = "/media/shared/nbbl/key_file/ec_keys/IBMB_PublicKey.txt";
		
		NbblEncryptionUtil nbblEncryptionUtil = new NbblEncryptionUtil(upiPrivateKeyCrt, publicKeyCertificateFile);
		Gson gson = new Gson();
		LocalDateTime createdDate = LocalDateTime.now();
		ZonedDateTime zonedDateTime = createdDate.atZone(ZoneId.systemDefault());
	    long created = zonedDateTime.toInstant().toEpochMilli();
	    long expires = created + (150 * 1000);
	    
		
		byte[] digest = nbblEncryptionUtil.blake2b512(payload.getBytes(StandardCharsets.UTF_8));
		String base64Digest = Base64.getEncoder().encodeToString(digest);
		String signingString = "(created):" + created + "\n" + "(expires):" + expires + "\n"
				+ "digest:BLAKE2b-512=" + base64Digest;
		System.out.println("nbbl Signing String === " + signingString);
		String base64Signature = nbblEncryptionUtil.signECDSASigningString(signingString);

		System.out.println("nbbl Signature (Base64) === " + base64Signature);

		String keyId = "PGP17";
		String protectedString = String.format(
				"keyId=\"%s|ecdsa\",algorithm=\"ecdsa\",created=\"%d\",expires=\"%d\",headers=\" (created)(expires)digest\"",
				keyId, created, expires);

		System.out.println("nbbl buildProtectedInfo String === " + protectedString);
		String protectedStringBase64 = nbblEncryptionUtil.buildProtectedInfoBase64ByProtectString(protectedString,
				created, expires);

		System.out.println("nbbl protectedInfo (Base64) === " + protectedStringBase64);

		

		NbblRequestWrapper nbblRequestWrapper = new NbblRequestWrapper();
		Signature signature = new Signature();
		signature.setProtectedValue(protectedStringBase64);
		signature.setSignature(base64Signature);
		nbblRequestWrapper.setSignature(signature);
		
		if ( args[1] != null && args[1].equals("sign")) {
		JSONObject postValidateCollectResponseJsonObj = new JSONObject(payload);
		String jsonSign = gson.toJson(signature);
		postValidateCollectResponseJsonObj.put("signature", jsonSign);
		payload = postValidateCollectResponseJsonObj.toString();
		}
//		paymentRequestnbbl.setSignature(signature);
//		String payloadWithSignature = gson.toJson(paymentRequestnbbl);
//		System.out.println("payload with signature=>" + payloadWithSignature);
		
		String encryptPayload = nbblEncryptionUtil.encryptPayload(payload);
		
		nbblRequestWrapper.setPayload(encryptPayload);

		String finalRequestPayload = gson.toJson(nbblRequestWrapper);
		
		System.out.println("nbbl encrypted request === " + finalRequestPayload);
		
		
		String apikey = "apikey";
		String trasnactionUrl = "https://ibmbcert.npci.org.in/ibmb/ReqTxnInit/1.0/urn:referenceId:PGP175223A0574967799";
				
		String postapi = NbblUtil.postapi(finalRequestPayload, trasnactionUrl, apikey);
		
		System.out.println("nbbl trasnaction api response === " + postapi);
		
	}
}
