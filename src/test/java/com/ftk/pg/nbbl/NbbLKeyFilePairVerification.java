package com.ftk.pg.nbbl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.IESParameterSpec;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

public class NbbLKeyFilePairVerification {

	public static void main(String[] args) throws Exception {
		
		   if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
		        Security.addProvider(new BouncyCastleProvider());
		    }
		   
		String payLoadRequest = "{\n"
				+ "    \"head\": {\n"
				+ "        \"ver\": \"1.0\",\n"
				+ "        \"ts\": \"2024-11-19T15:22:13+05:30\",\n"
				+ "        \"msgID\": \"MSGIDJM5TNJOC5NDVP5X9HKDTB943241525\",\n"
				+ "        \"orgID\": \"PAG01\"\n"
				+ "    }\n"
				+ "}";
		System.out.println("payLoadRequest :" + payLoadRequest);

		
		String upiPrivateKeyCrt = "/media/shared/nbbl/key_file/ec_keys/nbbl-hpy-key.pem";
		String publicKeyCertificateFile = "/media/shared/nbbl/key_file/ec_keys/nbbl-hpy-pub.txt";
		
		String encryptPayload = encryptPayload(payLoadRequest, publicKeyCertificateFile);
		System.out.println("encryptPayload : "+ encryptPayload);
		
		String decryptPayload = decryptPayload(encryptPayload, upiPrivateKeyCrt);
		System.out.println("decryptPayload : "+decryptPayload);
		
		
	}
	
	public static String encryptPayload(String jsonPayload,String publicKeyCert) throws Exception {
		
		PublicKey publicKeyCertificate = getPublicKeyCertificate(publicKeyCert);

//		Cipher cipher = Cipher.getInstance("ECIES", "BC");
		Cipher cipher = Cipher.getInstance("ECIESwithSHA512/NONE/NoPadding");
		IESParameterSpec iesParamSpec = new IESParameterSpec(null, null, 256); // MAC key size = 256 bits
		cipher.init(Cipher.ENCRYPT_MODE, publicKeyCertificate, iesParamSpec, new SecureRandom());
		
		byte[] encrypted = cipher.doFinal(jsonPayload.getBytes("UTF-8"));
		return Base64.getEncoder().encodeToString(encrypted);

	}
	
	private static PublicKey getPublicKeyCertificate(String upiCrt)
			throws CertificateException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		System.out.println("GetEpay Public Key Certificate Started: {}");

//		CertificateFactory cf = CertificateFactory.getInstance("X.509");
//		InputStream in = new FileInputStream(new File(upiCrt));
//		InputStream caInput = new BufferedInputStream(in);
//		Certificate ca;
//		try {
//			ca = cf.generateCertificate(caInput);
//			return ca.getPublicKey();
//		} finally {
//			caInput.close();
//		}
		
        String base64Key = new String(Files.readAllBytes(Paths.get(upiCrt))).trim();

        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        return keyFactory.generatePublic(keySpec);
        
	}
	

	public static String decryptPayload(String encPayLoadString, String privateKeyFile ) throws Exception {

		PrivateKey privateKey = getPrivateKeyCertificate(privateKeyFile);

//		Cipher cipher = Cipher.getInstance("ECIES", "BC");
		Cipher cipher = Cipher.getInstance("ECIESwithSHA512/NONE/NoPadding");
		IESParameterSpec iesParamSpec = new IESParameterSpec(null, null, 256);
		cipher.init(Cipher.DECRYPT_MODE, privateKey, iesParamSpec);

		byte[] decode = Base64.getDecoder().decode(encPayLoadString.getBytes());
		byte[] doFinal = cipher.doFinal(decode);
		return new String(doFinal);


	}
	
	private static PrivateKey getPrivateKeyCertificate(String upiPrivateKeyCrt)
			throws CertificateException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, Exception {
		System.out.println("GetEpay Private Key Cert Started: {}");

		FileReader fileReader = new FileReader(new File(upiPrivateKeyCrt), StandardCharsets.UTF_8);
		PemReader pemReader = new PemReader(fileReader);
		PemObject pemObject = pemReader.readPemObject();
		pemReader.close();

		byte[] keyBytes = pemObject.getContent();
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

		KeyFactory kf = KeyFactory.getInstance("EC", "BC"); // For ECC keys
		return kf.generatePrivate(keySpec);
	}
}
