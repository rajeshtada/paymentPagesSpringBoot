package com.ftk.pg.util;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.ftk.pg.exception.GlobalExceptionHandler;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEDecrypter;
import com.nimbusds.jose.JWEEncrypter;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.util.Base64URL;

public class JoseHelper {
	static Logger logger = LogManager.getLogger(JoseHelper.class);

	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	public static String encryptAndSign(String message, RSAPublicKey encryptionKey, RSAPrivateKey signingKey,
			String signingKeyFingerPrint, String encryptionKeyFingerPrint, String clientId) throws JOSEException {

		JWEEncrypter encrypter = new RSAEncrypter(encryptionKey);
		Security.addProvider(new BouncyCastleProvider());
		JWEHeader jweHeader = new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A128GCM)
				.x509CertSHA256Thumbprint(new Base64URL(encryptionKeyFingerPrint)).customParam("clientid", clientId)
				.build();
		System.out.println("Jwe Header=====>" + jweHeader);
		Payload payload = new Payload(message);
		JWEObject jweObject = new JWEObject(jweHeader, payload);
		jweObject.encrypt(encrypter);
		String encryptedString = jweObject.serialize();
		System.out.println("Jwe Encrytped String=======>" + encryptedString);

		JWSSigner jwsSigner = new RSASSASigner(signingKey);
		JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.PS256)
				.x509CertSHA256Thumbprint(new Base64URL(signingKeyFingerPrint)).customParam("clientid", clientId)
				.build();
		JWSObject jwsObject = new JWSObject(jwsHeader, new Payload(encryptedString));
		System.out.println("Jws Header=======>" + jwsHeader);
		jwsObject.sign(jwsSigner);

		String jwsenc = jwsObject.serialize();
		System.out.println("Jws Encrytped String=======>" + jwsenc);
		return jwsenc;
	}

	public static String verifyAndDecrypt(String encryptedSignedMessage, RSAPublicKey verificationKey,
			RSAPrivateKey decrytpionKey) throws Exception {
		JWSObject jwsObject = JWSObject.parse(encryptedSignedMessage);

		JWSVerifier verifier = new RSASSAVerifier(verificationKey);
		jwsObject.verify(verifier);
		String encryptedMessage = jwsObject.getPayload().toString();
		JWEObject jweObject = JWEObject.parse(encryptedMessage);
		JWEDecrypter decrypter = new RSADecrypter(decrytpionKey);
		jweObject.decrypt(decrypter);
		return jweObject.getPayload().toString();

	}

	public static String signingKeyFingerPrint() throws CertificateException, NoSuchAlgorithmException, IOException {
		FileInputStream in = new FileInputStream("C:\\media\\shared\\BillDeskKey\\getepay-signing.txt");
		byte[] keyBytes = new byte[in.available()];
		in.read(keyBytes);
		in.close();
		String certStr = new String(keyBytes, "UTF-8");

		certStr = certStr.replaceAll("BEGIN CERTIFICATE", "");
		certStr = certStr.replaceAll("END CERTIFICATE", "");
		certStr = certStr.replaceAll("-", "").replaceAll("\\s", "");
		certStr = certStr.trim();
		byte[] decodeVal = Base64.getDecoder().decode(certStr);
		ByteArrayInputStream instr = new ByteArrayInputStream(decodeVal);
		CertificateFactory cf;
		cf = CertificateFactory.getInstance("X.509");
		X509Certificate certObj = (X509Certificate) cf.generateCertificate(instr);

		String thumbprint = Base64URL.encode(MessageDigest.getInstance("SHA-256").digest(certObj.getEncoded()))
				.toString();
		return thumbprint;
	}

	public static String encryptionKeyFingerPrint() throws CertificateException, NoSuchAlgorithmException, IOException {
		try {
			FileInputStream in = new FileInputStream("C:\\media\\shared\\BillDeskKey\\getepay-encryption.txt");
			byte[] keyBytes = new byte[in.available()];
			in.read(keyBytes);
			in.close();
			String certStr = new String(keyBytes, "UTF-8");
			certStr = certStr.replaceAll("BEGIN CERTIFICATE", "");
			certStr = certStr.replaceAll("END CERTIFICATE", "");
			certStr = certStr.replaceAll("-", "").replaceAll("\\s", "");
			certStr = certStr.trim();

			// remove start and end of certificate

			byte[] decodeVal = Base64.getDecoder().decode(certStr);
			ByteArrayInputStream instr = new ByteArrayInputStream(decodeVal);
			CertificateFactory cf;
			cf = CertificateFactory.getInstance("X.509");
			X509Certificate certObj = (X509Certificate) cf.generateCertificate(instr);
			System.out.println("Key id encryptionKeyFingerPrint = "
					+ Base64URL.encode(MessageDigest.getInstance("SHA-256").digest(certObj.getEncoded())).toString());
			System.out.println(System.currentTimeMillis());

			// RSAPublicKey pubkey = (RSAPublicKey) certObj.getPublicKey();
			String fingerPrint = Base64URL.encode(MessageDigest.getInstance("SHA-256").digest(certObj.getEncoded()))
					.toString();

			return fingerPrint;
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}
}