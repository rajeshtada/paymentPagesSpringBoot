//package com.ftk.getepaymentpages.util;
//
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.kms.AWSKMS;
//import com.amazonaws.services.kms.AWSKMSClientBuilder;
//import com.amazonaws.services.kms.model.DecryptRequest;
//import com.amazonaws.services.kms.model.DecryptResult;
//import com.amazonaws.services.kms.model.EncryptRequest;
//import com.amazonaws.services.kms.model.EncryptResult;
//
//import java.nio.ByteBuffer;
//import java.util.Base64;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//public class KmsOperations {
//	public static final String KMS_ENC_ACCESS_KEY_ID="KMS_ENC_ACCESS_KEY_ID";
//	public static final String KMS_ENC_SECRET_ACCESS_KEY="KMS_ENC_SECRET_ACCESS_KEY";
//	public static final String KMS_ENC_KEY_ID="KMS_ENC_KEY_ID";
//	public static final String KMS_REGION="KMS_REGION";
//	public static final String KMS_DEC_ACCESS_KEY_ID="KMS_DEC_ACCESS_KEY_ID";
//	public static final String KMS_DEC_SECRET_ACCESS_KEY="KMS_DEC_SECRET_ACCESS_KEY";
//	
//	static Logger logger = LogManager.getLogger(KmsOperations.class);
//	public static String encrypt(String plaintext, String accessKeyId, String secretAccessKey, String region,
//			String kmsKeyId) {
//		try {
//			// Initialize AWS credentials
//			BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
//
//			// Create an AWS KMS client
//			AWSKMS kmsClient = AWSKMSClientBuilder.standard()
//					.withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).withRegion(region).build();
//
//			// Encrypt plaintext
//			ByteBuffer plaintextBuffer = ByteBuffer.wrap(plaintext.getBytes());
//			EncryptRequest encryptRequest = new EncryptRequest().withKeyId(kmsKeyId).withPlaintext(plaintextBuffer);
//			EncryptResult encryptResult = kmsClient.encrypt(encryptRequest);
//			String ciphertextBase64 = Base64.getEncoder().encodeToString(encryptResult.getCiphertextBlob().array());
//			logger.info("Ciphertext (Base64): " + ciphertextBase64);
//			return ciphertextBase64;
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			new GlobalExceptionHandler().customException(e);
//		}
//		return null;
//	}
//
//	public static String decrypt(String enc, String accessKeyId, String secretAccessKey, String region) {
//		try {
//			enc = enc.replace(" ", "+");
//
//			// Initialize AWS credentials
//			BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
//
//			// Create an AWS KMS client
//			AWSKMS kmsClient = AWSKMSClientBuilder.standard()
//					.withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).withRegion(region).build();
//
//			// Decrypt ciphertext
//			DecryptRequest decryptRequest = new DecryptRequest()
//					.withCiphertextBlob(ByteBuffer.wrap(Base64.getDecoder().decode(enc)));
//			DecryptResult decryptResult = kmsClient.decrypt(decryptRequest);
//			String decryptedPlaintext = new String(decryptResult.getPlaintext().array());
//			//logger.info("Decrypted plaintext: " + decryptedPlaintext);
//			return decryptedPlaintext;
//		} catch (Exception e) {
//			new GlobalExceptionHandler().customException(e);
//		}
//		return null;
//	}
//
//}


