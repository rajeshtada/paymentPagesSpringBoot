package com.ftk.pg.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ftk.pg.exception.GlobalExceptionHandler;
import com.google.gson.Gson;

public class KotakSecureHashGenenerator {

	static Logger logger = LogManager.getLogger(KotakSecureHashGenenerator.class);

	public static void main(String[] args) throws Exception {
		String secureSecret = "F3C63F6FFFC176E4AAD530C59833719D";
		String req = "{\"BankId\":\"000065\",\"MerchantId\":\"SIT000000000001\",\"TerminalId\":\"SIT10031\",\"OrderId\":\"ORD00001\",\"AccessCode\":\"OXEY8157\",\"Command\":\"Pay+TP\",\"Currency\":\"356\",\"Amount\":\"12345\",\"PaymentOption\":\"cc\",\"IpAddress\":\"110.175.211.10\",\"BrowserDetails\":\"Windows, Chrome-89.0.4389.90\",\"UserAgent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64)AppleWebKit/537.36 (KHTML, like Gecko)Chrome/89.0.4389.90 Safari/537.36\",\"CardNumber\":\"5453010000095323\",\"ExpiryDate\":\"082025\",\"AcceptHeader\":\"text/html,application/xhtml+xml,application/xml;q\\u003d0.9,image/avif,image/webp,image/apng;q\\u003d0.8,application/signed-exchange;v\\u003db3;q\\u003d0.9\",\"AuthenticationResponseURL\":\"https://sandbox.isgpay.com/PGRedirect/S2SAPis/authenticationResponse.jsp\",\"browserScreenHeight\":\"400\",\"browserScreenWidth\":\"250\",\"browserJavaEnabled\":\"false\",\"browserColorDepth\":\"8\",\"browserJavascriptEnabled\":\"true\"}\n";

		// System.out.println("req => " + req);
		// String hash = generateHash(req, secureSecret);
		// System.out.println("hash => " + hash);

		/*
		 * String secureSecret = "0F5DD14AE2E38C7EBD8814D29CF6F6F0"; String string =
		 * "0F5DD14AE2E38C7EBD8814D29CF6F6F02995MER123Order456";
		 * System.out.println(generateSHA256Signature(string));
		 */

		generateHash(req, secureSecret);

	}

	public static String generateHash(String req, String secureSecret) throws Exception {

		Gson gson = new Gson();
		Map<String, Object> requestMap = gson.fromJson(req, Map.class);

		Set<String> keys = requestMap.keySet();

		Stream<String> sortedKeys = keys.stream().sorted(new KotakComparator());
		StringBuilder result = new StringBuilder();
		result.append(secureSecret);

		for (Iterator iterator = sortedKeys.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			
			Object obj = requestMap.get(string);
			String value = new String(String.valueOf(obj).getBytes(), "UTF-8");
			if (!string.equalsIgnoreCase("SecureHash") && value != null) {
				result.append(value);
			}
		}
		System.out.println();
		String checksumString = result.toString();

		logger.info("kotak checksum string=>" + checksumString);

		String sha256generation = generateSHA256Signature(checksumString);
		logger.info("generated kotak checksum string=>" + sha256generation);
		return sha256generation;

	}

	public static String generateSHA256Signature(String data) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
			byte[] hashBytes = digest.digest(dataBytes);
			StringBuilder hexString = new StringBuilder();
			for (byte b : hashBytes) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

}