package com.ftk.pg.nbbl;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;

import org.bouncycastle.crypto.digests.Blake2bDigest;

import com.ftk.pg.util.NbblEncryptionUtil;

public class SignatureVerifier {

    public static void main(String[] args) throws Exception {
    	
        String base64PublicKey = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE64Y9ZtjdZFOBhBRu+0FCAFINHeEAy5uDOTsprMyEkhlS7O94r05vjZdY7qQv0S82iT6NS/nb/1cvrP98ushs/A==";
        String payLoadRequest = "{\n"
        		+ "    \"head\": {\n"
        		+ "        \"ver\": \"1.0\",\n"
        		+ "        \"ts\": \"2024-11-19T15:22:13+05:30\",\n"
        		+ "        \"msgID\": \"MSGIDJM5TNJOC5NDVP5X9HKDTB943241525\",\n"
        		+ "        \"orgID\": \"PAG01\"\n"
        		+ "    }\n"
        		+ "}";
//        String base64Signature = "MEYCIQDmta9Sl6QrF4JGBP2QJmSzHZfLhO7RlzBvBo95Tq/ABQIhAJJT47WyzTXTdIv+HOHNOv5Dddxtpu5jfmB5IVygK/SL";
        
        

		String upiPrivateKeyCrt = "/media/shared/nbbl/key_file/ec_keys/nbbl-hpy-key.pem";
		NbblEncryptionUtil nbblEncryptionUtil = new NbblEncryptionUtil(upiPrivateKeyCrt, null);
		
		LocalDateTime createdDate = LocalDateTime.now();
		ZonedDateTime zonedDateTime = createdDate.atZone(ZoneId.systemDefault());
	    long created = zonedDateTime.toInstant().toEpochMilli();
	    long expires = created + (150 * 1000);
	    
		String base64Signature = nbblEncryptionUtil.signECDSA(payLoadRequest, created, expires);
		System.out.println("Signature (Base64): " + base64Signature);
		
		
        String base64Digest = blake2b512(payLoadRequest.getBytes(StandardCharsets.UTF_8));

		String signingString = "(created):" + created + "\n" + "(expires):" + expires + "\n" + "digest:BLAKE2b-512="
				+ base64Digest;
        
		Signature signature = Signature.getInstance("SHA256withECDSA");
        PublicKey publicKey = getECPublicKey(base64PublicKey);
        signature.initVerify(publicKey);
        signature.update(signingString.getBytes("UTF-8"));

        boolean isValid = signature.verify(Base64.getDecoder().decode(base64Signature));
        System.out.println("Signature valid: " + isValid);
    }

    public static PublicKey getECPublicKey(String base64) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(base64);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory kf = KeyFactory.getInstance("EC");
        return kf.generatePublic(spec);
    }
	public static String  blake2b512(byte[] input) {
		 Blake2bDigest digest = new Blake2bDigest(512);
	        digest.update(input, 0, input.length);
	        byte[] output = new byte[digest.getDigestSize()];
	        digest.doFinal(output, 0);
	        String encodeToString = Base64.getEncoder().encodeToString(output);
	        return encodeToString;
	}
}