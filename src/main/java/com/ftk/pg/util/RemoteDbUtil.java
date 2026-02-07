package com.ftk.pg.util;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.ConvenienceCharges;
import com.ftk.pg.modal.MerchantCommision;
import com.getepay.au.payout.IMPSEncKeys;
public class RemoteDbUtil {
static Logger logger = LogManager.getLogger(Util.class);
	
	public IMPSEncKeys getAUKey() {
		IMPSEncKeys keys = new IMPSEncKeys();
		keys.setKey("dxW/a/raDOtWV9T/8UL8OLVig0am9k4kBMw4x9rddfg=");
		keys.setIv("aibKcM9Jq6i8NIt+ACg8LQ==");

		return keys;
	}

	public static String GST_CHARGES = "18";
	public static final String ICICI_QR_PREFIX_KEY = "ICICI_QR_PREFIX_KEY";
	public static final String GETEPAY_PRIVATE_KEY = "GETEPAY_PRIVATE_KEY";

	public static final String GETEPAY_PUBLIC_KEY = "GETEPAY_PUBLIC_KEY";

	public static final String GETEPAY_ICICI_PRIVATE_KEY = "GETEPAY_ICICI_PRIVATE_KEY";

	public static final String GETEPAY_ICICI_PUBLIC_KEY = "GETEPAY_ICICI_PUBLIC_KEY";

	public static final String GETEPAY_ICICI_COLLECT_PRIVATE_KEY = "GETEPAY_ICICI_COLLECT_PRIVATE_KEY";

	public static final String GETEPAY_CASHFREE_RU_KEY = "CASHFREE_RU";
	public static final String GETEPAY_CASHFREE_CU_KEY = "CASHFREE_CU";
	public static final String GETEPAY_CASHFREE_RU_KEY_V2 = "CASHFREE_RU_V2";
	public static final String GETEPAY_CASHFREE_CU_KEY_V2 = "CASHFREE_CU_V2";
	public static final String GETEPAY_CASHFREE_ORDER_URL_KEY = "CASHFREE_ORDER_URL";
	public static final String GETEPAY_CASHFREE_PAYMENT_URL_KEY = "CASHFREE_PAYMENT_URL";
	public static final String GETEPAY_CASHFREE_APIVERSION_KEY = "GETEPAY_CASHFREE_APIVERSION";
	public static final String ICICI_MANDATE_URL = "ICICI_MANDATE_URL";

	public static final String PAYMENT_MODE_DYNAMIC_QR_REFID = "gptn.";

	public static final String REQUEST_DYNAMIC_QR_REFID = "gpdr.";

	public static final int SETTLEMENT_SUMMARY_STATUS_NRNS = 0;
	public static final int SETTLEMENT_SUMMARY_STATUS_RF = 2;
	public static final int SETTLEMENT_SUMMARY_STATUS_RNS = 1;
	public static final int SETTLEMENT_SUMMARY_STATUS_RS = 3;

	public static final int SETTLEMENT_SUMMARY_STATUS_RR = 4;
	public static final String QR_PATH = "/media/shared/dynamicqrpath";

	public static String AUQR_CONFIG_API_KEY = "AUQR_CONFIG_API_KEY";
	public static String AUQR_CONFIG_API_ENC128 = "AUQR_CONFIG_API_ENC128";
	public static String AUQR_CONFIG_API_ENC256 = "AUQR_CONFIG_API_ENC256";
	public static String AUQR_CONFIG_API_MODSTRING = "AUQR_CONFIG_API_MODSTRING";
	public static String AUQR_CONFIG_API_EXPSTRING = "AUQR_CONFIG_API_EXPSTRING";
	public static String AUQR_CONFIG_API_URL = "AUQR_CONFIG_API_URL";
	public static String AUQR_CONFIG_API_USER = "AUQR_CONFIG_API_USER";

	public static String AUQR_CONFIG_API_MERCHANTCONFIG = "AUQR_CONFIG_API_MERCHANTCONFIG";

	public static String TXN_SYNC_API_URL = "TXN_SYNC_API_URL";
	public static String TXN_SYNC_API_KEY = "TXN_SYNC_API_KEY";
	public static String REQUARY_API_URL="REQUARY_API_URL";
	public static final String ICICI_PAYMENT_MODE_DYNAMIC_QR_REFID = "GETgptn";
    public static final String ICICI_REQUEST_DYNAMIC_QR_REFID = "GETgpdr";
    public static final String PROCESSOR_IDS_SYNC="PROCESSOR_IDS_SYNC";
    public static final String PG_PUSH_NOTIFICATION_URL="PG_PUSH_NOTIFICATION_URL";
    public static final String ENABLE_NEW_DYNAMIC_QR="ENABLE_NEW_DYNAMIC_QR";
    public static final String ENABLE_NEW_DYNAMIC_QR_MID="ENABLE_NEW_DYNAMIC_QR_MID";

	public static final String getFileSettlementStatusText(int status) {
		switch (status) {
		case SETTLEMENT_SUMMARY_STATUS_RNS:
			return "Reconciled Not Settled";
		case SETTLEMENT_SUMMARY_STATUS_RF:
			return "Reconciled Failed";
		case SETTLEMENT_SUMMARY_STATUS_RS:
			return "Reconciled Settled";
		case SETTLEMENT_SUMMARY_STATUS_RR:
			return "Reconciled Resolved";
		default:
			return "Not Reconciled Not Settled";
		}
	}

	public static final String formatDate(Date d, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			if (d == null) {
				return "";
			}
			return sdf.format(d);
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);

		}
		return "";
	}
	
	public static int getNumericCurrencyCode(String currencyCode) {
		switch (currencyCode) {
		case "INR":
			return 356;
		default:
			return 356;
		}
	}

	public static void main(String[] args) {
		try {
			String value = "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";
//					<img alt="UPI QR Code" src="data:image/png;base64,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" />
			String encodeId = new String(Base64.getDecoder().decode(value));
			logger.info("AU BANK QR PATH ==> " + encodeId);

//			Util util = new Util();
//			IMPSEncKeys iMPSEncKeys = util.getAUKey();
//			String request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
//					+ "<upi:ReqTxnConfirmation xmlns:upi=\"http://npci.org/upi/schema/\">\n"
//					+ "    <Head ver=\"1.0\" ts=\"2019-07-05T18:55:45+05:30\" orgId=\"NPCI\" msgId=\"1GRDpegBbA5Qa2O1mQn5\" />\n"
//					+ "    <txn_id>XYZ05da89f2edae4dce822c0220f6f8df00</txn_id>\n"
//					+ "    <custRef>918618504713</custRef>\n" + "    <note>Test</note>\n"
//					+ "    <refId>XYZ18bacfd455854835973489ca54930c9c</refId>\n"
//					+ "    <refUrl>http://www.cityunionbank.com</refUrl>\n" + "    <ts>2019-07-05T18:55:45+05:30</ts>\n"
//					+ "    <type>TxnConfirmation</type>\n"
//					+ "    <orgTxnId>XYZ05da89f2edae4dce822c0220f6f8df00</orgTxnId>\n"
//					+ "    <initiationMode>00</initiationMode>\n" + "    <purpose>00</purpose>\n"
//					+ "    <txn_confirmation_note>Test</txn_confirmation_note>\n"
//					+ "    <txn_confirmation_org_status>S</txn_confirmation_org_status>\n"
//					+ "    <txn_confirmation_org_err_code></txn_confirmation_org_err_code>\n"
//					+ "    <txn_confirmation_type>PAY</txn_confirmation_type>\n" + "    <ref_type>PAYEE</ref_type>\n"
//					+ "    <ref_seq_num>1</ref_seq_num>\n" + "    <ref_addr>XYZnetc.ch0hfhjop@XYZupi</ref_addr>\n"
//					+ "    <ref_regName>FASTAG UPI MASTER ACCOUNT</ref_regName>\n"
//					+ "    <ref_settAmount>45</ref_settAmount>\n" + "    <ref_settCurrency>INR</ref_settCurrency>\n"
//					+ "    <ref_orgAmount>45</ref_orgAmount>\n" + "    <ref_approvalNum>576813</ref_approvalNum>\n"
//					+ "    <ref_espCode>00</ref_espCode>\n" + "    <ref_reversalRespCode></ref_reversalRespCode>\n"
//					+ "</upi:ReqTxnConfirmation>";
//			String encryptData = IMPSAES.encrypt(request, iMPSEncKeys);
//			logger.info("encryptData ==> " + encryptData);
//			String decryptedData = IMPSAES.decrypt(encryptData, iMPSEncKeys);
//			logger.info("decryptedData ==> " + decryptedData);

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
	}

	public static String HMACSHA256(String message, String key) {
		try {
			Mac sha256_hmac = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
			sha256_hmac.init(secret_key);
			String hash = Hex.encodeHexString(sha256_hmac.doFinal(message.getBytes("UTF-8")));
			return hash;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return "";
	}

	/*
	 * private static PrivateKey getPrivateKey(String privateKeyString) throws
	 * Exception { PrivateKey privateKey = null; PKCS8EncodedKeySpec keySpec = new
	 * PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyString.getBytes()));
	 * KeyFactory keyFactory = null; keyFactory = KeyFactory.getInstance("RSA");
	 * privateKey = keyFactory.generatePrivate(keySpec); return privateKey; }
	 */

	public static IciciEncryptionRequest encryptIciciResponse(String responseString, String getepayPublicKeyPath)
			throws Exception {
		IciciEncryptionRequest response = new IciciEncryptionRequest();
		String randomString = "GETEPAYP";

		// Get Public Key
		String encryptedKey = encryptRSA(responseString.getBytes(), getPublicKey(getepayPublicKeyPath));
		response.setEncryptedKey(encryptedKey);

		String encryptedDataWithoutIv = encryptAES(responseString.getBytes(),
				Base64.getEncoder().encode(randomString.getBytes()), randomString.getBytes());

		byte[] ivBytes = randomString.getBytes();
		byte[] encryptedDataBytes = encryptedDataWithoutIv.getBytes();

		byte[] finalBytes = new byte[ivBytes.length + encryptedDataBytes.length];
		System.arraycopy(ivBytes, 0, ivBytes, 0, ivBytes.length);
		System.arraycopy(encryptedDataBytes, 0, finalBytes, ivBytes.length, encryptedDataBytes.length);

		String encryptedData = Base64.getEncoder().encodeToString(finalBytes);

		response.setEncryptedData(encryptedData);

		return response;
	}

	public static String decryptIcicRequest(IciciEncryptionRequest request, String getepayPrivateKeyPath) {
		try {
			String encryptedDataString = request.getEncryptedData();

			byte[] encryptedDataBytes = Base64.getDecoder().decode(encryptedDataString.getBytes());

			byte[] iv = Arrays.copyOfRange(encryptedDataBytes, 0, 16);
			byte[] enData = Arrays.copyOfRange(encryptedDataBytes, 16, encryptedDataBytes.length);

			String encryptedKey = request.getEncryptedKey();
			String privateKeyString = new String(Files.readAllBytes(Paths.get(getepayPrivateKeyPath)));

			String decryptedKey = decryptRSA(encryptedKey.getBytes(), getPrivateKey(privateKeyString));

			logger.info("Decrypted Key=>" + decryptedKey);

			return decryptAES(enData, iv, decryptedKey.getBytes());

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public static String encryptAES(byte[] decrypted, byte[] iv, byte[] key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		SecretKey secKey = new SecretKeySpec(key, 0, key.length, "AES");
		cipher.init(Cipher.ENCRYPT_MODE, secKey, ivSpec);
		byte[] encrypted = cipher.doFinal(decrypted);
		return Base64.getEncoder().encodeToString(encrypted);
	}

	public static String decryptAES(byte[] encrypted, byte[] iv, byte[] key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		SecretKey secKey = new SecretKeySpec(key, 0, key.length, "AES");
		cipher.init(Cipher.DECRYPT_MODE, secKey, ivSpec);
		byte[] decryptedText = cipher.doFinal(encrypted);
		return new String(decryptedText);
	}

	public static String decryptRSA(byte[] data, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return new String(cipher.doFinal(data));
	}

	public static String encryptRSA(byte[] data, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return Base64.getEncoder().encodeToString(cipher.doFinal(data));
	}

	/*
	 * public static PublicKey getPublicKey(String base64PublicKey) throws Exception
	 * { PublicKey publicKey = null;
	 * 
	 * X509EncodedKeySpec keySpec = new
	 * X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
	 * KeyFactory keyFactory = KeyFactory.getInstance("RSA"); publicKey =
	 * keyFactory.generatePublic(keySpec); return publicKey; }
	 */

	/*
	 * public static PublicKey getPublicKey(String publicKeyText) throws Exception {
	 * byte[] publicBytes = Base64.getDecoder().decode(publicKeyText);
	 * X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes); KeyFactory
	 * keyFactory = KeyFactory.getInstance("RSA"); PublicKey pubKey =
	 * keyFactory.generatePublic(keySpec); return pubKey; }
	 */

	public static KeyPair getKeyPair(KeyStore keystore, String alias, char[] password) throws Exception {
		// Get private key
		Key key = keystore.getKey(alias, password);
		if (key instanceof PrivateKey) {
			// Get certificate of public key
			java.security.cert.Certificate cert = keystore.getCertificate(alias);

			// Get public key
			PublicKey publicKey = cert.getPublicKey();

			// Return a key pair
			return new KeyPair(publicKey, (PrivateKey) key);
		}
		return null;
	}

	public static PublicKey getPublicKey(String keystorePath) throws Exception {
		File file = new File(keystorePath);
		FileInputStream is = new FileInputStream(file);
		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		String alias = "1";
		String password = "Getepay@2020";
		char[] passwd = password.toCharArray();
		keystore.load(is, passwd);
		KeyPair kp = getKeyPair(keystore, alias, passwd);
		PublicKey pubKey = kp.getPublic();
		// logger.info(publicKeyString);
		is.close();
		return pubKey;
	}

	public static PrivateKey getPrivateKey(String keystorePath) throws Exception {
		File file = new File(keystorePath);
		FileInputStream is = new FileInputStream(file);
		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		String alias = "1";
		String password = "Getepay@2020";
		char[] passwd = password.toCharArray();
		keystore.load(is, passwd);
		KeyPair kp = getKeyPair(keystore, alias, passwd);

		// logger.info(publicKeyString);
		is.close();
		return kp.getPrivate();
	}

	public static String decryptIciciRequest(String request, String privateKeyPath) throws Exception {
		// String privateKeyString = new
		// String(Files.readAllBytes(Paths.get(privateKeyPath)));

		byte[] reqBytes = Base64.getDecoder().decode(request.getBytes());
		byte[] plainResponseBytes = Base64.getDecoder()
				.decode(decryptRSA(Base64.getDecoder().decode(request), getPrivateKey(privateKeyPath)));
		String plainResponse = new String(plainResponseBytes);
		return plainResponse;
	}

	public static String encryptIciciRequest(String response, String publicKeyPath) throws Exception {
		// String publicKeyString = new
		// String(Files.readAllBytes(Paths.get(publicKeyPath)));
		byte[] responseBytes = Base64.getEncoder()
				.encode(encryptRSA(response.getBytes(), getPublicKey(publicKeyPath)).getBytes());
		String plainResponse = new String(responseBytes);
		return plainResponse;
	}

	public static LocalDateTime getTodayFromDate() {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		return convertToLocalDateTimeViaInstant(cal.getTime());

	}

	public static LocalDateTime getTodayToDate() {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return convertToLocalDateTimeViaInstant(cal.getTime());

	}

	public static LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public static List<List<String>> splitList(List<String> list, int length) {
		List<List<String>> resultList = new ArrayList<List<String>>();
		int i = 1;
		List<String> tempList = new ArrayList<String>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			if (i % length == 0) {
				resultList.add(tempList);
				tempList = new ArrayList<String>();
			}
			tempList.add(string);
			i++;
		}
		if (tempList != null && tempList.size() > 0) {
			resultList.add(tempList);
		}
		return resultList;
	}

	public static CommissionModel getCommissionChargesModel(MerchantCommision merchantCommision, double amounT) {
		BigDecimal amount = BigDecimal.valueOf(amounT);
		BigDecimal commisionvalue = BigDecimal.ZERO;
		BigDecimal commision = BigDecimal.ZERO;
		if (merchantCommision != null && merchantCommision.getChargeType() != null
				&& merchantCommision.getChargeType().equalsIgnoreCase("Excl")) {
			int com_comprision = merchantCommision.getCommisionvalue().compareTo(BigDecimal.ZERO);
			if (merchantCommision.getCommisionType().equalsIgnoreCase("Fixed") || !(com_comprision == 1)) {
				commisionvalue = merchantCommision.getCommisionvalue();
				commision = commisionvalue;
				amount = amount.add(commision);

			} else {
				commisionvalue = merchantCommision.getCommisionvalue();
				commision = amount.multiply(commisionvalue).divide(new BigDecimal("100"));
				amount = amount.add(commision);
			}
		}
		CommissionModel model = new CommissionModel();
		model.setAmount(new BigDecimal(amounT));
		model.setCharges(commision);
		model.setTotal(amount);
		return model;
	}
	public static ConvenienceModel getConvenienceCharges(ConvenienceCharges convenienceCharges, double amounT) {
		BigDecimal amount = BigDecimal.valueOf(amounT);
		BigDecimal commisionvalue = BigDecimal.ZERO;
		BigDecimal commision = BigDecimal.ZERO;
		if (convenienceCharges != null && convenienceCharges.getChargesType() != null
				&& convenienceCharges.getChargesType().equalsIgnoreCase("Excl")) {
			if (convenienceCharges.getCommissionType().equalsIgnoreCase("Fixed")) {
				commisionvalue = BigDecimal.valueOf(convenienceCharges.getChargesAmt()).setScale(2, RoundingMode.HALF_UP);
				commision = commisionvalue;

			} else if (convenienceCharges.getCommissionType().equalsIgnoreCase("Variable")) {
				commisionvalue = BigDecimal.valueOf(convenienceCharges.getChargesAmt());
				commision = amount.multiply(commisionvalue).divide(new BigDecimal("100"),2,RoundingMode.HALF_UP);

			} else {

				BigDecimal fixedCharges = new BigDecimal(convenienceCharges.getFixedVarible());

				BigDecimal variableCharges = amount.multiply(new BigDecimal(convenienceCharges.getChargesAmt()))
						.divide(new BigDecimal("100"),2,RoundingMode.HALF_UP);

				commision = fixedCharges.add(variableCharges);

			}

		}

		ConvenienceModel model = new ConvenienceModel();
		BigDecimal gstCharges = BigDecimal.ZERO;
		gstCharges = commision.multiply(new BigDecimal(RemoteDbUtil.GST_CHARGES));
		gstCharges = gstCharges.divide(new BigDecimal("100"),2,RoundingMode.HALF_UP);

		BigDecimal totalServiceCharges = BigDecimal.ZERO;
		totalServiceCharges = commision.add(gstCharges).setScale(2,RoundingMode.HALF_UP);
		model.setCharges(totalServiceCharges);

		return model;
	}
	
	// Mask any String or number 
	public static String maskStringValue(String stringForMask) {
		String maskString = "";
		try {
			String last = "";
			if(stringForMask.length()>4) {
			  last = stringForMask.substring(stringForMask.length() - 4);
			}else {
				last = stringForMask.substring(stringForMask.length() - 0);
			}
			int len = stringForMask.length() - last.length();
			String subString = stringForMask.substring(0, len);
			int size = subString.length();
			for (int i = 0; i < size; i++) {
				maskString += "*";
			}
			maskString += last;
		} catch (Exception e) {
		}
		return maskString;
	}
	// mask email id
	public static String maskEmailStringValue(String email) {
		if(email!=null && !email.equals("")) {
			
			int atIndex = email.indexOf('@');
			if (atIndex == -1) {
				return email;
			}
			int dotIndex = email.indexOf('.', atIndex);
			if (dotIndex == -1) {
				return email;
			}
			String prefix = email.substring(0, 3);
			String suffix = email.substring(dotIndex - 0);
			String mask = "";
			for (int i = 3; i < dotIndex - 3; i++) {
				mask += "*";
			}
			return prefix + mask + suffix;
		}
		return null;
	}
}

