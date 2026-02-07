package com.ftk.pg.util;


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
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.crypto.digests.Blake2bDigest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.IESParameterSpec;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import com.ftk.pg.modal.TransactionLog;
import com.ftk.pg.vo.nbbl.NbblRequestWrapper;
import com.google.gson.Gson;

public class NbblEncryptionUtil {

//	static {
//		Security.addProvider(new BouncyCastleProvider());
//	}

	Logger logger =LogManager.getLogger(NbblEncryptionUtil.class);
	
	private String privateKeyFile;
	private String publicKeyCert;

	public static void main(String[] args) throws Exception {

		String decPayLoad = "Hello, secure world!";
		System.out.println("Body :" + decPayLoad);

		long created = System.currentTimeMillis();
		long expires = created + (150 * 1000);

		String upiPrivateKeyCrt = "/media/shared/nbbl/key_file/nbbl-hpy-key.pem";
		String publicKeyCertificateFile = "/media/shared/nbbl/key_file/nbbl-hpy-cert.pem";
		NbblEncryptionUtil nbblEncryptionUtil = new NbblEncryptionUtil(upiPrivateKeyCrt, publicKeyCertificateFile);

		String base64Signature = nbblEncryptionUtil.signECDSA(decPayLoad, created, expires);
		System.out.println("Signature (Base64): " + base64Signature);

		String keyId = "PGP17";
		String protectedStringBase64 = nbblEncryptionUtil.buildProtectedInfoBase64(keyId, created, expires);
		System.out.println("protectedInfo (Base64): " + protectedStringBase64);

		boolean isVerified = nbblEncryptionUtil.verifyECDSA(decPayLoad, base64Signature);
		System.out.println("Signature verified: " + isVerified);

	}

	public NbblEncryptionUtil(String privateKeyFile, String publicKeyCert) {

//		   if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
//		        Security.addProvider(new BouncyCastleProvider());
//		    }
		   
			Security.removeProvider("BC");
			Security.addProvider(new BouncyCastleProvider());
			
		this.privateKeyFile = privateKeyFile;
		this.publicKeyCert = publicKeyCert;

	}

	public byte[] blake2b512(byte[] input) {
		 Blake2bDigest digest = new Blake2bDigest(512);
	        digest.update(input, 0, input.length);
	        byte[] output = new byte[digest.getDigestSize()];
	        digest.doFinal(output, 0);
	        return output;
	}
	
	public byte[] blake2b512FromString(String formattedString) {
		Blake2bDigest blake2b = new Blake2bDigest(512);
		blake2b.update(formattedString.getBytes(), 0, formattedString.getBytes().length);
		byte[] output = new byte[blake2b.getDigestSize()];
		blake2b.doFinal(output, 0);
		return output;
	}
	
	public String generateSignECDSA(String decPayLoad, TransactionLog transactionLog) throws Exception {

		LocalDateTime createdDate = transactionLog.getCreatedDate();
		ZonedDateTime zonedDateTime = createdDate.atZone(ZoneId.systemDefault());

	    long created = zonedDateTime.toInstant().toEpochMilli();
	    long expires = created + (180 * 1000);
	    
		byte[] digest = blake2b512(decPayLoad.getBytes(StandardCharsets.UTF_8));
		String base64Digest = Base64.getEncoder().encodeToString(digest);

		String signingString = "(created):" + created + "\n" + "(expires):" + expires + "\n" + "digest:BLAKE2b-512="
				+ base64Digest;
		logger.info("Signing String: " + signingString);

		PrivateKey privateKey = getPrivateKeyCertificate(privateKeyFile);
		Signature signer = Signature.getInstance("SHA512withECDSA");
		signer.initSign(privateKey);
		signer.update(signingString.getBytes(StandardCharsets.UTF_8));
		byte[] signature = signer.sign();
		return Base64.getEncoder().encodeToString(signature);
	}

	public String signECDSA(String decPayLoad, long created, long expires) throws Exception {


		byte[] digest = blake2b512(decPayLoad.getBytes(StandardCharsets.UTF_8));
		String base64Digest = Base64.getEncoder().encodeToString(digest);

		String signingString = "(created):" + created + "\n" + "(expires):" + expires + "\n" + "digest:BLAKE2b-512="
				+ base64Digest;
		logger.info("Signing String: " + signingString);

		PrivateKey privateKey = getPrivateKeyCertificate(privateKeyFile);
		Signature signer = Signature.getInstance("SHA512withECDSA");
		signer.initSign(privateKey);
		signer.update(signingString.getBytes(StandardCharsets.UTF_8));
//		return signer.sign();
		byte[] signature = signer.sign();
		String encodeToString = Base64.getEncoder().encodeToString(signature);
		return encodeToString;
	}
	
	public String signECDSASigningString(String signingString) throws Exception {

//		logger.info("Signing String:\n" + signingString);

		PrivateKey privateKey = getPrivateKeyCertificate(privateKeyFile);
		Signature signer = Signature.getInstance("SHA512withECDSA");
		signer.initSign(privateKey);
		signer.update(signingString.getBytes());
//		return signer.sign();
		byte[] signature = signer.sign();
//		String encodeToString = 
		return Base64.getEncoder().encodeToString(signature);
	}

	public String generateProtectedInfoBase64(String paId, TransactionLog transactionLog) {
		// Create the protected string
		
		LocalDateTime createdDate = transactionLog.getCreatedDate();
		ZonedDateTime zonedDateTime = createdDate.atZone(ZoneId.systemDefault());

	    long created = zonedDateTime.toInstant().toEpochMilli();
	    long expires = created + (180 * 1000);
	    
		String protectedString = String.format(
				"keyId=\"%s|ecdsa\",algorithm=\"ecdsa\",created=\"%d\",expires=\"%d\",headers=\" (created)(expires)digest\"",
				paId, created, expires);

		logger.info("buildProtectedInfo String:\n" + protectedString);
		String protectedStringBase64 = Base64.getEncoder()
				.encodeToString(protectedString.getBytes(StandardCharsets.UTF_8));
		return protectedStringBase64;
	}
	
	public String buildProtectedInfoBase64(String keyId, long created, long expires) {
		// Create the protected string
		String protectedString = String.format(
				"keyId=\"%s|ecdsa\",algorithm=\"ecdsa\",created=\"%d\",expires=\"%d\",headers=\" (created)(expires)digest\"",
				keyId, created, expires);

		logger.info("buildProtectedInfo String:\n" + protectedString);
		String protectedStringBase64 = Base64.getEncoder()
				.encodeToString(protectedString.getBytes(StandardCharsets.UTF_8));
		return protectedStringBase64;
	}
	
	public String buildProtectedInfoBase64ByProtectString(String protectedString, long created, long expires) {
		String protectedStringBase64 = Base64.getEncoder()
				.encodeToString(protectedString.getBytes());
		return protectedStringBase64;
	}
	
	// Verify ECDSA signature
	public boolean verifyECDSA(String decPayLoad, String base64Signature) throws Exception {

		PublicKey publicKeyCertificate = getPublicKeyCertificate(publicKeyCert);
		byte[] signature = Base64.getDecoder().decode(base64Signature);

		byte[] digest = blake2b512(decPayLoad.getBytes(StandardCharsets.UTF_8));
		String base64Digest = Base64.getEncoder().encodeToString(digest);

		long created = System.currentTimeMillis();
		long expires = created + (150 * 1000);
		String signingString = "(created): " + created + "\n" + "(expires): " + expires + "\n" + "digest: BLAKE2b-512="
				+ base64Digest;

		logger.info("Signing String:\n" + signingString);

		Signature verifier = Signature.getInstance("SHA256withECDSA");
		verifier.initVerify(publicKeyCertificate);
		verifier.update(signingString.getBytes(StandardCharsets.UTF_8));
		return verifier.verify(signature);
	}
	
	public boolean verifySignature(String finalRequestPayload) throws Exception {

		Gson gson=new Gson();
		NbblRequestWrapper fromJson = gson.fromJson(finalRequestPayload, NbblRequestWrapper.class);
		String encPayload = fromJson.getPayload();
		String decryptPayload = decryptPayload(encPayload);
		logger.info("decryptPayload : "+decryptPayload);
		
		String responseSignature = fromJson.getSignature().getSignature();
		logger.info("responseSignature (Base64): " + responseSignature);
		
		String protectedValue = fromJson.getSignature().getProtectedValue();
        String decodedprotectedValue= new String(Base64.getDecoder().decode(protectedValue), StandardCharsets.UTF_8);
        logger.info("decodedprotectedValue : "+decodedprotectedValue);
        
		Pattern pattern = Pattern.compile("created=\"(\\d+)\".*expires=\"(\\d+)\"");
		Matcher matcher = pattern.matcher(decodedprotectedValue);
		Long created = 0L;	
		Long expires = 0L;	
		if (matcher.find()) {
		created = Long.valueOf(matcher.group(1));
        expires = Long.valueOf(matcher.group(2));
		} else {
			logger.info("Values not found");
        }
		
		byte[] digest = blake2b512(decryptPayload.getBytes(StandardCharsets.UTF_8));
		String base64Digest = Base64.getEncoder().encodeToString(digest);
		String signingString = "(created):" + created + "\n" + "(expires):" + expires + "\n"
				+ "digest:BLAKE2b-512=" + base64Digest;
		logger.info("nbbl Signing String === " + signingString);
		
		PublicKey publicKeyCertificate = getPublicKeyCertificate(publicKeyCert);
		byte[] signature = Base64.getDecoder().decode(responseSignature);


		logger.info("Signing String:\n" + signingString);

		Signature verifier = Signature.getInstance("SHA256withECDSA");
		verifier.initVerify(publicKeyCertificate);
		verifier.update(signingString.getBytes(StandardCharsets.UTF_8));
		return verifier.verify(signature);
	}

	private PrivateKey getPrivateKeyCertificate(String upiPrivateKeyCrt)
			throws CertificateException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, Exception {
		logger.info("GetEpay Private Key Cert Started: {}");

		FileReader fileReader = new FileReader(new File(upiPrivateKeyCrt), StandardCharsets.UTF_8);
		PemReader pemReader = new PemReader(fileReader);
		PemObject pemObject = pemReader.readPemObject();
		pemReader.close();

		byte[] keyBytes = pemObject.getContent();
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

		KeyFactory kf = KeyFactory.getInstance("EC"); // For ECC keys
		return kf.generatePrivate(keySpec);
	}

	private PublicKey getPublicKeyCertificate(String upiCrt)
			throws CertificateException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		logger.info("GetEpay Public Key Certificate Started: {}");

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

	public String encryptPayload(String jsonPayload) throws Exception {
		
		PublicKey publicKeyCertificate = getPublicKeyCertificate(publicKeyCert);

//		Cipher cipher = Cipher.getInstance("ECIES", "BC");
		Cipher cipher = Cipher.getInstance("ECIESwithSHA512/NONE/NoPadding");
//		Cipher cipher = Cipher.getInstance("ECIESwithSHA512/NONE/NoPadding", "BC");
//		Cipher cipher = Cipher.getInstance("ECIESwithSHA512andAES-CBC/NONE/NoPadding");
		IESParameterSpec iesParamSpec = new IESParameterSpec(null, null, 256); // MAC key size = 256 bits
		cipher.init(Cipher.ENCRYPT_MODE, publicKeyCertificate, iesParamSpec);
		
		byte[] encrypted = cipher.doFinal(jsonPayload.getBytes());
		return Base64.getEncoder().encodeToString(encrypted);

	}

	public String decryptPayload(String encPayLoadString) throws Exception {

		PrivateKey privateKey = getPrivateKeyCertificate(privateKeyFile);

//		Cipher cipher = Cipher.getInstance("ECIES", "BC");
		Cipher cipher = Cipher.getInstance("ECIESwithSHA512/NONE/NoPadding");
		IESParameterSpec iesParamSpec = new IESParameterSpec(null, null, 256);
		cipher.init(Cipher.DECRYPT_MODE, privateKey, iesParamSpec);

		byte[] decode = Base64.getDecoder().decode(encPayLoadString.getBytes());
		byte[] doFinal = cipher.doFinal(decode);
		return new String(doFinal);

	}

    
}
