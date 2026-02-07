package com.ftk.pg.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.Security;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.responsevo.HdfcResponse;
import com.ftk.pg.vo.hdfccard.HdfcCardsResponse;
import com.ftk.pg.vo.hdfccard.Request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cryptix.provider.Cryptix;
import cryptix.provider.key.RawSecretKey;
import cryptix.util.core.Hex;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import xjava.security.Cipher;

public class HdfcUtils {

	public static final String HDFC_CARDS_ACTION = "HDFC_CARDS_ACTION";
	public static final String HDFC_CARDS_CURRENCY_CODE = "HDFC_CARDS_CURRENCY_CODE";
	public static final String HDFC_CARDS_ENCRYPTION_KEY = "HDFC_CARDS_ENCRYPTION_KEY";
	public static final String HDFC_CARDS_RETURN_URL = "HDFC_CARDS_RETURN_URL";
	public static final String HDFC_CARDS_URL = "HDFC_CARDS_URL";

	public static final String HDFC_CARDS_REQUERY_URL = "HDFC_CARDS_REQUERY_URL";
	public static final String HDFC_CARDS_TRANPORTALID = "HDFC_CARDS_TRANPORTALID";

	Gson gson = new Gson();

	public static void main(String[] args) {
		String request = "dc89e0fe26f11a13e6c73ee6ba1c45fbdf84f6abe8b198f3daf5030760f61d3cef9f0428215f781d7a4f3db866b0393dddcff955ef4069cf576c732161b40cdd4f71dacaf4237757b69a44c0844e4e28e4305c64595f0a161600babf5abd741036892cde4460393232984f1f95794fd4792ffbd619262b91ef1f14e50a7cd35b126d6d5cf893e1b099225827a85334522761b692338a1f50db8da6ed59cb29a821e73504a63be59c1366afcea3c60159994195eaf98acd83cdf4ab110aad6599ef1b7745f302700908fdd89d1bd1d0ab0e71835af75749c5f1f24f50548dc21b95da2684ad12e7aaf2f0a8f9b534a63a9bce0132a87dd89038afbe2496e4d4831d5423f94a5f86c83e39fbab1abf39e151cbc30ae5142e11243cc22de355d793a761c997bb1f0b93892b773255acc7fe05b369ad3d497f6681b6a1c561793d3404bf26d576b8e5ca00dea83acf264547bca0a27519b91b5652014c5e3ba6eb0c109be2169e919cdf0fa6c24ab57acfdfa473b8821d369ac1d88d37f3211306765af1acef58c1e71b60ce4fa92169d35170dea5e7faaed5d524144e3593";
		try {
			String key = "42707454342142707454342342707454";

			String decryptText = decryptTextAes(key, request);
			System.out.println(decryptText);
			// String decrypt=IDFCUtil.decrypt(request,key);
			// System.out.println(decrypt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String convertToJson(Request request) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(request);
	}

	// Method to convert JSON string back to HdfcCardsRequest object
	public static Request convertFromJson(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, Request.class);
	}

	public static Object unmarshaller(String xmlDataResponse, Class<?> clazz) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Object respBalEnq = unmarshaller.unmarshal(new StringReader(xmlDataResponse));
			return respBalEnq;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String marshaller(Object req, Class<?> clazz) {
		if (req instanceof String) {
			return (String) req;
		}
		try {
			System.setProperty("com.sun.xml.bind.v2.bytecode.ClassTailor.noOptimize", "true");
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter stringWriter = new StringWriter();
			marshaller.marshal(req, stringWriter);
			return stringWriter.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String encryptText(String key, String valueToBeEncrypted) throws Exception {

		String enc1 = "";
		String value = "";
		String encadd = "";
		String key1 = "";
		String key2 = "";
		String key3 = "";
		String checking = "";
		try {

			key1 = alpha2Hex(key.substring(0, 8));
			key2 = alpha2Hex(key.substring(8, 16));
			key3 = alpha2Hex(key.substring(16, 24));

			if ((valueToBeEncrypted.length() % 8) != 0) {
				valueToBeEncrypted = rightPadZeros(valueToBeEncrypted);
			}
			for (int i = 0; i < valueToBeEncrypted.length(); i = i + 8) {
				value = valueToBeEncrypted.substring(i, i + 8);
				checking = checking + alpha2Hex(value);
				enc1 = getTripleHexValue(alpha2Hex(value), key1, key2, key3);
				encadd = encadd + enc1;
			}
			return encadd;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			enc1 = null;
			value = null;
			encadd = null;
			key1 = null;
			key2 = null;
			key3 = null;
		}

	}

	public static String rightPadZeros(String Str) {
		if (null == Str) {
			return null;
		}
		String PadStr = new String(Str);

		for (int i = Str.length(); (i % 8) != 0; i++) {
			PadStr = PadStr + '^';
		}
		return PadStr;
	}

	public static String getTripleHexValue(final String pin, final String key1, final String key2, final String key3)
			throws Exception {

		try {

			Security.addProvider(new Cryptix());
			String encryptedKey = getHexValue(pin, key1);

			encryptedKey = getDexValue(encryptedKey, key2);
			encryptedKey = binary2hex(asciiChar2binary(encryptedKey)).toUpperCase();

			encryptedKey = getHexValue(encryptedKey, key3);

			return encryptedKey;
		} catch (final Exception e) {
			throw e;
		}
	}

	public static String binary2hex(final String binaryString) {
		if (binaryString == null) {
			return null;
		}
		String hexString = "";
		for (int i = 0; i < binaryString.length(); i += 8) {
			String temp = binaryString.substring(i, i + 8);

			int intValue = 0;
			for (int k = 0, j = temp.length() - 1; j >= 0; j--, k++) {
				intValue += Integer.parseInt("" + temp.charAt(j)) * Math.pow(2, k);

			}
			temp = "0" + Integer.toHexString(intValue);
			hexString += temp.substring(temp.length() - 2);

		}
		return hexString;
	}

	public static String encryptTextAes(String keyString, String plainText) {
		try {
			SecretKey secretKey = generateAESKey(keyString);
			byte[] iv = generateIV(keyString);
			byte[] cipherText = encrypt(secretKey, iv, plainText.getBytes());
			String result = byteArrayToHexString(cipherText);
			return result;
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	public static byte[] encrypt(SecretKey key, byte[] IV, byte[] plaintext) throws Exception {
		javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES/GCM/NoPadding");
		SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
		GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(16 * 8, IV);
		cipher.init(1, keySpec, gcmParameterSpec);
		byte[] cipherText = cipher.doFinal(plaintext);
		return cipherText;
	}

	public static String byteArrayToHexString(byte[] data, int length) {
		StringBuffer buf = new StringBuffer();
		String HEX_DIGITS = "0123456789abcdef";
		for (int i = 0; i != length; i++) {
			int v = data[i] & 0xff;

			buf.append(HEX_DIGITS.charAt(v >> 4));
			buf.append(HEX_DIGITS.charAt(v & 0xf));
		}

		return buf.toString();
	}

	public static String byteArrayToHexString(byte[] data) {
		return byteArrayToHexString(data, data.length);
	}

	public static String decryptText(String key, String valueToBeDecrypted) throws Exception {
		String key1 = "";
		String key2 = "";
		String key3 = "";
		String key4 = "";
		try {

			key1 = alpha2Hex(key.substring(0, 8));
			key2 = alpha2Hex(key.substring(8, 16));
			key3 = alpha2Hex(key.substring(16, 24));
			key4 = alpha2Hex(key.substring(24, 32));

			String decryptedStr = getTripleDesValue(valueToBeDecrypted, key4, key3, key2, key1);
			System.out.println(decryptedStr);
			decryptedStr = hexToString(decryptedStr);
			if (decryptedStr.startsWith("<")) {
				decryptedStr = decryptedStr.substring(0, decryptedStr.lastIndexOf('>') + 1);
			} else {
				decryptedStr = decryptedStr.substring(0, decryptedStr.lastIndexOf('&') + 1);
			}

			System.out.println(decryptedStr);
			return decryptedStr;

		} catch (Exception e) {
			return null;
		} finally {
			key1 = null;
			key2 = null;
			key3 = null;
		}
	}

	public static String alpha2Hex(String data) {
		char[] alpha = data.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < alpha.length; i++) {
			int count = Integer.toHexString(alpha[i]).toUpperCase().length();
			if (count <= 1) {
				sb.append("0").append(Integer.toHexString(alpha[i]).toUpperCase());
			} else {
				sb.append(Integer.toHexString(alpha[i]).toUpperCase());
			}
		}
		return sb.toString();
	}

	public static String getTripleDesValue(final String pin, final String key1, final String key2, final String key3,
			final String key4) throws Exception {
		String decryptedKey = null;
		try {

			Security.addProvider(new Cryptix());
			decryptedKey = getDexValue(pin, key1);

			decryptedKey = binary2hex(asciiChar2binary(decryptedKey)).toUpperCase();
			System.out.println("Decryption After Key 1 :: " + decryptedKey + "::::key1 ::" + key1);

			decryptedKey = getHexValue(decryptedKey, key2);
			System.out.println("Decryption After Key 2 :: " + decryptedKey + "::::key2 ::" + key2);

			decryptedKey = getDexValue(decryptedKey, key3);

			decryptedKey = binary2hex(asciiChar2binary(decryptedKey)).toUpperCase();
			System.out.println("Decryption After Key 3 :: " + decryptedKey + "::::key3 ::" + key3);

			decryptedKey = binary2hex(asciiChar2binary(decryptedKey)).toUpperCase();
			System.out.println("Decryption After Key 4 :: " + decryptedKey + "::::key4 ::" + key4);

		} catch (final Exception e) {
			e.printStackTrace();
		}
		return decryptedKey;
	}

	public static String getDexValue(final String pin, final String key) throws Exception {
		byte[] ciphertext = null;
		byte[] pinInByteArray = null;
		try {

			Cipher des = null;
			RawSecretKey desKey = null;

			des = Cipher.getInstance("DES/ECB/NONE", "Cryptix");
			desKey = new RawSecretKey("DES", Hex.fromString(key));

			des.initDecrypt(desKey);

			pinInByteArray = Hex.fromString(pin);

			ciphertext = des.crypt(pinInByteArray);

		} catch (final Exception e) {
			e.printStackTrace();
		}
		return toString(ciphertext);
	}

	public static String asciiChar2binary(final String asciiString) {
		if (asciiString == null) {
			return null;
		}
		String binaryString = "";
		String temp = "";
		int intValue = 0;
		for (int i = 0; i < asciiString.length(); i++) {
			intValue = (int) asciiString.charAt(i);

			temp = "00000000" + Integer.toBinaryString(intValue);
			binaryString += temp.substring(temp.length() - 8);
		}
		return binaryString;

	}

	public static String toString(final byte[] temp) {
		final char ch[] = new char[temp.length];
		for (int i = 0; i < temp.length; i++) {
			ch[i] = (char) temp[i];
		}
		final String s = new String(ch);
		return s;
	}

	public static String getHexValue(final String pin, final String key) throws Exception {
		try {
			Cipher des = null;
			RawSecretKey desKey = null;

			des = Cipher.getInstance("DES/ECB/NONE", "Cryptix");
			desKey = new RawSecretKey("DES", Hex.fromString(key));
			des.initEncrypt(desKey);
			final byte[] pinInByteArray = Hex.fromString(pin);
			final byte[] ciphertext = des.crypt(pinInByteArray);
			return (Hex.toString(ciphertext));
		} catch (final Exception e) {
			throw e;
		}
	}

	public static String hexToString(String txtInHex)

	{

		byte[] txtInByte = new byte[txtInHex.length() / 2];

		int j = 0;

		for (int i = 0; i < txtInHex.length(); i += 2)

		{

			txtInByte[j++] = Byte.parseByte(txtInHex.substring(i, i + 2), 16);

		}

		System.out.println(txtInByte);
		return new String(txtInByte);

	}

	public static String decryptTextAes(String keyString, String encryptedText) {
		try {
			byte[] encText = hexStringToByteArray(encryptedText);
			SecretKey secretKey = generateAESKey(keyString);
			byte[] iv = generateIV(keyString);
			return new String(decrypt(secretKey, iv, encText));
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	public static SecretKey generateAESKey(String keyString) {
		if (keyString.length() > 32) {
			keyString = keyString.substring(0, 32);
		} else {
			keyString = keyString + keyString;
			return generateAESKey(keyString);
		}
		byte[] key = new byte[32];
		byte[] rawKey = stringToByteArray(keyString);
		System.arraycopy(rawKey, 0, key, 0, 32);
		return new SecretKeySpec(key, "AES");
	}

	public static byte[] generateIV(String keyString) {
		if (keyString.length() > 32) {
			keyString = keyString.substring(0, 32);
		} else {
			keyString = keyString + keyString;
			return generateIV(keyString);
		}
		byte[] key = new byte[12];
		byte[] rawKey = stringToByteArray(
				keyString.substring(0, 6) + keyString.substring(keyString.length() - 6 - 1, keyString.length() - 1));
		System.arraycopy(rawKey, 0, key, 0, 12);
		return key;
	}

	public static byte[] stringToByteArray(String string) {
		byte[] bytes = new byte[string.length()];
		char[] chars = string.toCharArray();
		for (int i = 0; i != chars.length; i++) {
			bytes[i] = (byte) chars[i];
		}
		return bytes;
	}

	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	private static byte[] decrypt(SecretKey key, byte[] IV, byte[] cipherText) throws Exception {
		javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES/GCM/NoPadding");
		SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
		GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(16 * 8, IV);
		cipher.init(2, keySpec, gcmParameterSpec);
		byte[] decryptedText = cipher.doFinal(cipherText);
		return decryptedText;
	}

	public String parseDetails(String decryptedData) {

		String jsonString = null;
		try {
			JSONObject jsonObject = XML.toJSONObject(decryptedData);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			new GlobalExceptionHandler().customException(e);
		}

		// Extract HdfcCardsResponse from the nested JSON structure
		// JSONObject hdfcJsonObject = jsonObject.getJSONObject("HdfcCardsResponse");
//          String hdfcJsonString = hdfcJsonObject.toString();

		return jsonString;
	}

	// Method to unmarshal JSON string to a Java object
	public static <T> T unmarshalJson(String jsonData, Class<T> clazz) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();

		// Configure ObjectMapper to handle unknown properties without failing
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try {
			return objectMapper.readValue(jsonData, clazz);
		} catch (Exception e) {
			throw new Exception("Error deserializing JSON to " + clazz.getSimpleName() + ": " + e.getMessage(), e);
		}
	}

	public static HdfcCardsResponse parseDecryptedData(String decryptedData) throws Exception {
		// Initialize the response object
		HdfcCardsResponse response = new HdfcCardsResponse();
		// Create a DocumentBuilderFactory
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		// Parse the decrypted XML string into a Document
		Document document = builder.parse(new java.io.ByteArrayInputStream(decryptedData.getBytes("UTF-8")));
		// Create XPath object for querying XML
		XPath xpath = XPathFactory.newInstance().newXPath();
		// Extracting values using XPath
//        String paymentidStr = xpath.evaluate("/root/paymentid", document);
//        if (!paymentidStr.isEmpty()) {
//            response.setPaymentid(Long.parseLong(paymentidStr));  // Convert to long
//        }
		response.setPaymentid(xpath.evaluate("/root/paymentid", document));
		response.setResult(xpath.evaluate("/root/result", document));
		response.setAuth(xpath.evaluate("/root/auth", document));
		response.setRef(xpath.evaluate("/root/ref", document));
		response.setPostdate(xpath.evaluate("/root/postdate", document));
		response.setTranid(xpath.evaluate("/root/tranid", document));
		response.setTrackid(xpath.evaluate("/root/trackid", document));
		response.setAuthRespCode(xpath.evaluate("/root/authRespCode", document));
		response.setAvr(xpath.evaluate("/root/avr", document));
		// Extracting UDF values dynamically and storing them in the map
//        for (int i = 1; i <= 25; i++) {
//            String udfValue = xpath.evaluate("/root/udf" + i, document);
//            response.setUdf("udf" + i, udfValue);  // Store each UDF value in the map
//        }
		return response;
	}

	public static HdfcResponse callApiUrl(String apiUrl, String xmlRequest) {

		// Set custom timeouts for connection and read
		OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS) // Set the connection
																								// timeout to 30 seconds
				.readTimeout(30, TimeUnit.SECONDS) // Set the read timeout to 30 seconds
				.writeTimeout(30, TimeUnit.SECONDS) // Optional: Set the write timeout to 30 seconds
				.build();

		MediaType mediaType = MediaType.parse("application/xml");
		RequestBody body = RequestBody.create(mediaType, xmlRequest);

		okhttp3.Request request = new okhttp3.Request.Builder().url(apiUrl).post(body).build();

		try {
			Response response = client.newCall(request).execute();
			String responseBody = response.body().string();
			String wrap = "<response>" + responseBody + "</response>";

			try {
				HdfcResponse xmlResponse = parseXmlResponse(wrap);
				return xmlResponse;
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static HdfcResponse parseXmlResponse(String xml) throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(HdfcResponse.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		StringReader reader = new StringReader(xml);
		return (HdfcResponse) unmarshaller.unmarshal(reader);
	}
}
