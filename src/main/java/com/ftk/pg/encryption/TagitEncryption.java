package com.ftk.pg.encryption;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.util.AuEncryptionUtil;
import com.google.common.io.BaseEncoding;

public class TagitEncryption {

	static Logger logger = LogManager.getLogger(TagitEncryption.class);

	static String CHECKSUM_MESSAGE_SEPARATOR = "|";

	public static void main2(String[] args) {

		try {
			/*
			 * String s = tagitEncrypt("ec6defe64f1c0c96", "HmacSHA256", "9258767c05260da1",
			 * "AES/ECB/PKCS5Padding", "REF123456", "2.00", "INR",
			 * "https://m.getepay.in:8443/getePaymentPages/aunbResponse", "WEB",
			 * "https://pay.getepay.in", "GATEPAY1");
			 */

			String s = tagitEncrypt("ec6edfe64f1c0c96", "HmacSHA256", "9259867c05260da1", "AES/ECB/PKCS5Padding",
					"11000000125779", "3.00", "INR", "https://caller.atomtech.in/ots/bank/ru/ausmallfinancenb.ots",
					"WEB", "https://caller.atomtech.in/ots/bank/ru/ausmallfinancenb.ots", "ATOMTECH1");

			logger.info(s);
		} catch (InvalidKeyException e) {
			new GlobalExceptionHandler().customException(e);
		} catch (NoSuchAlgorithmException e) {
			new GlobalExceptionHandler().customException(e);
		} catch (UnsupportedEncodingException e) {
			new GlobalExceptionHandler().customException(e);
		} catch (NoSuchPaddingException e) {
			new GlobalExceptionHandler().customException(e);
		} catch (IllegalBlockSizeException e) {
			new GlobalExceptionHandler().customException(e);
		} catch (BadPaddingException e) {
			new GlobalExceptionHandler().customException(e);
		}

	}

	public static void main(String[] args) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException,
			IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		logger.info("PaymentRequestQueryString  -" + getPaymentRequestQueryString());
	}

	static String getPaymentRequestQueryString() throws InvalidKeyException, NoSuchPaddingException,
			NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		String CHECKSUM_MESSAGE_SEPARATOR = "|";
		String txnCurrency = "INR";
		String merchantCode = "GATEPAY1";
		String merchantRefNumber = "1131";
		String retURL = "https://m.getepay.in:8443/getePaymentPages/aunbResponse";
		String transactionAmt = "117.70";
		String merURL = "https://pay.getepay.in";
		String channel = "WEB";
		String customerAccNo = "";
		String checksumKey = "ec6defe64f1c0c96";// ec6defe64f1c0c96
		String checksumAlgorithm = "HmacSHA256";
		String encryptionKey = "9258767c05260da1";
		String encryptionAlgorithm = "AES/ECB/PKCS5Padding";

		String checksumMessage = new StringBuilder(merchantCode).append(CHECKSUM_MESSAGE_SEPARATOR)
				.append(merchantRefNumber).append(CHECKSUM_MESSAGE_SEPARATOR).append(transactionAmt)
				.append(CHECKSUM_MESSAGE_SEPARATOR).append(txnCurrency).append(CHECKSUM_MESSAGE_SEPARATOR)
				.append(retURL).append(CHECKSUM_MESSAGE_SEPARATOR).append(channel).append(CHECKSUM_MESSAGE_SEPARATOR)
				.append(merURL).append(CHECKSUM_MESSAGE_SEPARATOR).append(checksumKey).toString();
		byte[] mac = AuEncryptionUtil.mac(checksumMessage, checksumKey, checksumAlgorithm);
		String checksumValue = BaseEncoding.base16().lowerCase().encode(mac);
		logger.info("checksumValue -" + checksumValue);
		// Prepare the data
		UriComponentsBuilder builder = UriComponentsBuilder.newInstance().queryParam("merchantCode", merchantCode)
				.queryParam("merchantRefNumber", merchantRefNumber).queryParam("transactionAmt", transactionAmt)
				.queryParam("txnCurrency", txnCurrency).queryParam("retURL", retURL)
				.queryParam("checksumValue", checksumValue).queryParam("channel", channel).queryParam("merURL", merURL);
		if (StringUtils.isNotBlank(customerAccNo)) {
			builder.queryParam("customerAccNo", customerAccNo);
		}
		UriComponents uri = builder.build();
		// Encrypt the data
		String plain = uri.getQuery();
		logger.info("Plain Data -" + plain);
		byte[] encrypted = AuEncryptionUtil.cipherEncryptMessage(plain, encryptionKey, encryptionAlgorithm);
		String encoded = BaseEncoding.base64().encode(encrypted);
		// Prepare the response
		UriComponents responseUri = UriComponentsBuilder.newInstance().queryParam("merchantCode", merchantCode)
				.queryParam("qs", encoded).build();
		// Form the response
		return responseUri.getQuery();
	}
	/*
	 * public static String tagitEncrypt(String checksumKey, String
	 * checksumAlgorithm, String encryptionKey, String encryptionAlgorithm, String
	 * merchantRefNumber, String transactionAmt, String txnCurrency, String retURL,
	 * String channel, String merURL, String merchantCode) throws
	 * InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException,
	 * NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
	 * final String checksumMessage = merchantCode + "|" + merchantRefNumber + "|" +
	 * transactionAmt + "|" + txnCurrency + "|" + retURL + "|" + channel + "|" +
	 * merURL + "|" + checksumKey; logger.info("Checksum string=>" +
	 * checksumMessage); final byte[] mac = TagitEncryption.mac(checksumMessage,
	 * checksumKey, checksumAlgorithm); final String checksumValue =
	 * BaseEncoding.base16().lowerCase().encode(mac);
	 * logger.info("[testEncrypt] :: checksumValue - " + checksumValue); final
	 * UriComponentsBuilder builder =
	 * UriComponentsBuilder.newInstance().queryParam("merchantRefNumber",
	 * merchantRefNumber).queryParam("transactionAmt", transactionAmt)
	 * .queryParam("txnCurrency", txnCurrency).queryParam("retURL",
	 * retURL).queryParam("channel", channel).queryParam("merURL",
	 * merURL).queryParam("checksumValue", checksumValue); final UriComponents uri =
	 * builder.build(); final String plain = uri.getQuery(); final byte[] encrypted
	 * = TagitEncryption.cipherEncryptMessage(plain, encryptionKey,
	 * encryptionAlgorithm); final String queryString =
	 * BaseEncoding.base64().encode(encrypted); final UriComponents responseUri =
	 * UriComponentsBuilder.newInstance().queryParam("merchantCode",
	 * merchantCode).queryParam("qs", queryString).build();
	 * logger.info("[testEncrypt] :: responseUri - " + responseUri.getFragment());
	 * return responseUri.getQuery();
	 * 
	 * }
	 */

	public static String tagitEncrypt(String checksumKey, String checksumAlgorithm, String encryptionKey,
			String encryptionAlgorithm, String merchantRefNumber, String transactionAmt, String txnCurrency,
			String retURL, String channel, String merURL, String merchantCode)
			throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException {
		String customerAccNo = "";
		/*
		 * final String checksumMessage = merchantCode + "|" + merchantRefNumber + "|" +
		 * transactionAmt + "|" + txnCurrency + "|" + retURL + "|" + channel + "|" +
		 * merURL + "|" + checksumKey; logger.info("Checksum string=>" +
		 * checksumMessage); final byte[] mac = TagitEncryption.mac(checksumMessage,
		 * checksumKey, checksumAlgorithm); final String checksumValue =
		 * BaseEncoding.base16().lowerCase().encode(mac);
		 * logger.info("[testEncrypt] :: checksumValue - " + checksumValue); final
		 * UriComponentsBuilder builder =
		 * UriComponentsBuilder.newInstance().queryParam("merchantRefNumber",
		 * merchantRefNumber).queryParam("transactionAmt", transactionAmt)
		 * .queryParam("txnCurrency", txnCurrency).queryParam("retURL",
		 * retURL).queryParam("channel", channel).queryParam("merURL",
		 * merURL).queryParam("checksumValue", checksumValue); final UriComponents uri =
		 * builder.build(); final String plain = uri.getQuery(); final byte[] encrypted
		 * = TagitEncryption.cipherEncryptMessage(plain, encryptionKey,
		 * encryptionAlgorithm); final String queryString =
		 * BaseEncoding.base64().encode(encrypted); final UriComponents responseUri =
		 * UriComponentsBuilder.newInstance().queryParam("merchantCode",
		 * merchantCode).queryParam("qs", queryString).build();
		 * logger.info("[testEncrypt] :: responseUri - " + responseUri.getFragment());
		 * return responseUri.getQuery();
		 */

		String checksumMessage = new StringBuilder(merchantCode).append(CHECKSUM_MESSAGE_SEPARATOR)
				.append(merchantRefNumber).append(CHECKSUM_MESSAGE_SEPARATOR).append(transactionAmt)
				.append(CHECKSUM_MESSAGE_SEPARATOR).append(txnCurrency).append(CHECKSUM_MESSAGE_SEPARATOR)
				.append(retURL).append(CHECKSUM_MESSAGE_SEPARATOR).append(channel).append(CHECKSUM_MESSAGE_SEPARATOR)
				.append(merURL).append(CHECKSUM_MESSAGE_SEPARATOR).append(checksumKey).toString();
		byte[] mac = AuEncryptionUtil.mac(checksumMessage, checksumKey, checksumAlgorithm);
		String checksumValue = BaseEncoding.base16().lowerCase().encode(mac);
		logger.info("checksumValue -" + checksumValue);
		// Prepare the data
		UriComponentsBuilder builder = UriComponentsBuilder.newInstance().queryParam("merchantCode", merchantCode)
				.queryParam("merchantRefNumber", merchantRefNumber).queryParam("transactionAmt", transactionAmt)
				.queryParam("txnCurrency", txnCurrency).queryParam("retURL", retURL)
				.queryParam("checksumValue", checksumValue).queryParam("channel", channel).queryParam("merURL", merURL);
		if (StringUtils.isNotBlank(customerAccNo)) {
			builder.queryParam("customerAccNo", customerAccNo);
		}
		UriComponents uri = builder.build();
		// Encrypt the data
		String plain = uri.getQuery();
		logger.info("Plain Data -" + plain);
		byte[] encrypted = AuEncryptionUtil.cipherEncryptMessage(plain, encryptionKey, encryptionAlgorithm);
		String encoded = BaseEncoding.base64().encode(encrypted);

		encoded = URLEncoder.encode(encoded, "UTF-8");

		// Prepare the response
		UriComponents responseUri = UriComponentsBuilder.newInstance().queryParam("merchantCode", merchantCode)
				.queryParam("qs", encoded).build();
		// Form the response
		return responseUri.getQuery();
	}

	public static byte[] mac(String message, String key, String algorithm)
			throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
		return mac(message.getBytes("UTF-8"), key.getBytes("UTF-8"), algorithm);
	}

	public static byte[] mac(byte[] message, byte[] keyBytes, String algorithm)
			throws InvalidKeyException, NoSuchAlgorithmException {
		SecretKeySpec signingKey = new SecretKeySpec(keyBytes, algorithm);
		Mac mac = Mac.getInstance(algorithm);
		mac.init(signingKey);
		return mac.doFinal(message);
	}

	public static byte[] cipherEncryptMessage(String message, String key, String algorithm)
			throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException {
		return cipherEncryptMessage(message.getBytes("UTF-8"), key.getBytes("UTF-8"), algorithm);
	}

	public static byte[] cipherEncryptMessage(byte[] message, byte[] keyBytes, String algorithm)
			throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException,
			BadPaddingException {
		String keySpecAlgo = algorithm.split("/")[0];
		Cipher cipher = Cipher.getInstance(algorithm);
		SecretKey secretKey = new SecretKeySpec(keyBytes, keySpecAlgo);
		cipher.init(1, secretKey);
		return cipher.doFinal(message);
	}
}
