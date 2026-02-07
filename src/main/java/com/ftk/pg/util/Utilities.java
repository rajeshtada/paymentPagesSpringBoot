package com.ftk.pg.util;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
//import com.google.common.io.BaseEncoding;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.ConvenienceCharges;
import com.ftk.pg.modal.Merchant;
import com.ftk.pg.modal.MerchantCommision;
import com.ftk.pg.modal.TransactionLog;
import com.google.common.io.BaseEncoding;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class Utilities {
	static Logger logger = LogManager.getLogger(Utilities.class);

	public static String IMAGE_ROOTPATH = "/media/shared";
	public static String TMP_IMAGE_ROOTPATH = "/media/tmp";
	public static String paymentUrl;
	public static String returnUrl;
	public static String paynowURl;
	public static final String EMAIL_MERCHANTS = "EMAIL_MERCHANTS";
	public static final String MULTIPRODUCTMIDS = "MULTIPRODUCTMIDS";
	public static final String ICICI_NB_VERIFICATION_URL_KEY = "ICICI_NB_VERIFICATION_URL_KEY";
	public static final String UPI_REDIRECTION_URL = "UPI_REDIRECTION_URL";
	private static final String MERCHANT_COMMSSION_VALUE = "MERCHANT_COMMSSION_VALUE";
	private static final String MERCHANT_COMMSSION_ID = "MERCHANT_COMMSSION_ID";
	public static final String ENABLE_BANK_LIST = "ENABLE_BANK_LIST";
	public static final String ENABLE_NEW_DYNAMIC_QR = "ENABLE_NEW_DYNAMIC_QR";
	public static final String ENABLE_NEW_DYNAMIC_QR_MID = "ENABLE_NEW_DYNAMIC_QR_MID";
	public static final String MERCHANT_CHARGES_ENABLE = "MERCHANT_CHARGES_ENABLE";

	//
	public static final String GETEPAY_PGDYNAMIC_URL_KEY = "GETEPAY_PGDYNAMIC_URL_KEY";
	public static final String GETEPAY_PGDYNAMIC_RURL_KEY = "GETEPAY_PGDYNAMIC_RURL_KEY";
	public static String GST_CHARGES = "18";

	public static BigDecimal getCommissionCharges(MerchantCommision merchantCommision, double amounT) {
		BigDecimal amount = BigDecimal.valueOf(amounT);
		BigDecimal commisionvalue = BigDecimal.ZERO;
		BigDecimal commision = BigDecimal.ZERO;
		if (merchantCommision != null && merchantCommision.getChargeType().equalsIgnoreCase("Excl")) {
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
		return amount;
	}

	public static CommissionModel getCommissionChargesModel(MerchantCommision merchantCommision, double amounT,
			Map<String, String> propMap) {
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

		Long commisiionId = Long.valueOf(propMap.get(MERCHANT_COMMSSION_ID));

		if (merchantCommision != null && merchantCommision.getChargeType() != null) {

			if (String.valueOf(commisiionId).equalsIgnoreCase(String.valueOf(merchantCommision.getId()))) {

				String cv = propMap.get(MERCHANT_COMMSSION_VALUE);

				if (cv != null && !cv.equalsIgnoreCase("")) {

					amount = BigDecimal.valueOf(amounT);
					BigDecimal bg = BigDecimal.valueOf(Double.valueOf(cv));
					if (merchantCommision != null && merchantCommision.getChargeType() != null
							&& merchantCommision.getChargeType().equalsIgnoreCase("Excl")) {
						int com_comprision = merchantCommision.getCommisionvalue().compareTo(BigDecimal.ZERO);
						if (merchantCommision.getCommisionType().equalsIgnoreCase("Fixed") || !(com_comprision == 1)) {
							commisionvalue = merchantCommision.getCommisionvalue();
							commision = bg;
							amount = amount.add(bg);

						} else {
							commisionvalue = bg;
							commision = amount.multiply(bg).divide(new BigDecimal("100"));
							amount = amount.add(commision);
						}
					}
					model.setAmount(new BigDecimal(amounT));
					model.setCharges2(bg);
					model.setTotal2(amount);
					return model;
				}

			}
		}

		model.setAmount(new BigDecimal(amounT));
		model.setCharges(commision);
		model.setTotal(amount);
		return model;
	}

//	public static CommissionModel2 getCommissionChargesModel2(MerchantCommision merchantCommision, double amounT) {
//		BigDecimal amount = BigDecimal.valueOf(amounT);
//		BigDecimal commisionvalue = BigDecimal.ZERO;
//		BigDecimal commision = BigDecimal.ZERO;
//		if (merchantCommision != null && merchantCommision.getChargeType() != null
//				&& merchantCommision.getChargeType().equalsIgnoreCase("Excl")) {
//			int com_comprision = merchantCommision.getCommisionvalue().compareTo(BigDecimal.ZERO);
//			if (merchantCommision.getCommisionType().equalsIgnoreCase("Fixed") || !(com_comprision == 1)) {
//				commisionvalue = merchantCommision.getCommisionvalue();
//				commision = commisionvalue;
//				amount = amount.add(commision);
//
//			} else {
//				commisionvalue = merchantCommision.getCommisionvalue();
//				commision = amount.multiply(commisionvalue).divide(new BigDecimal("100"));
//				amount = amount.add(commision);
//			}
//		}
//		CommissionModel2 model = new CommissionModel2();
//		model.setAmount(new BigDecimal(amounT));
//		model.setCharges(commision);
//		model.setTotal(amount);
//		return model;
//	}

	public static ConvenienceModel getConvenienceCharges(ConvenienceCharges convenienceCharges, double amounT) {
		BigDecimal amount = BigDecimal.valueOf(amounT);
		BigDecimal commisionvalue = BigDecimal.ZERO;
		BigDecimal commision = BigDecimal.ZERO;
		if (convenienceCharges != null && convenienceCharges.getChargesType() != null
				&& convenienceCharges.getChargesType().equalsIgnoreCase("Excl")) {
			if (convenienceCharges.getCommissionType().equalsIgnoreCase("Fixed")) {
				commisionvalue = BigDecimal.valueOf(convenienceCharges.getChargesAmt()).setScale(2,
						RoundingMode.HALF_UP);
				commision = commisionvalue;

			} else if (convenienceCharges.getCommissionType().equalsIgnoreCase("Variable")) {
				commisionvalue = BigDecimal.valueOf(convenienceCharges.getChargesAmt());
				commision = amount.multiply(commisionvalue).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

			} else {

				BigDecimal fixedCharges = new BigDecimal(convenienceCharges.getFixedVarible());

				BigDecimal variableCharges = amount.multiply(new BigDecimal(convenienceCharges.getChargesAmt()))
						.divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

				commision = fixedCharges.add(variableCharges);

			}

		}

		ConvenienceModel model = new ConvenienceModel();
		BigDecimal gstCharges = BigDecimal.ZERO;
		gstCharges = commision.multiply(new BigDecimal(GST_CHARGES));
		gstCharges = gstCharges.divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

		BigDecimal totalServiceCharges = BigDecimal.ZERO;
		totalServiceCharges = commision.add(gstCharges).setScale(2, RoundingMode.HALF_UP);
		model.setCharges(totalServiceCharges);

		return model;
	}

//	public static String generateChallan(TransactionLog transaction, Merchant merchant, String ifscCode,
//			Map<String, String> properties) {
//		String basePath = properties.get(Constants.CHALLAN_BASE_PATH);
//		String fileName = String.valueOf(transaction.getTransactionId()) + ".pdf";
//
//		String filePath = basePath + File.separator + fileName;
//
//		File baseFolder = new File(basePath);
//		if (!baseFolder.exists() || !baseFolder.isDirectory()) {
//			baseFolder.mkdirs();
//		}
//
//		try {
//
//			Document document = new Document();
//			PdfWriter.getInstance(document, new FileOutputStream(filePath));
//
//			document.open();
//
//			PdfPTable table = new PdfPTable(2);
//			table.setWidthPercentage(80);
//
//			PdfPCell c1 = new PdfPCell(new Phrase(merchant.getMerchantName() + " " + "NEFT/RTGS Challan"));
//			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//			c1.setColspan(2);
//			c1.setBorder(1);
//			c1.setBorderWidthLeft(1);
//			c1.setBorderWidthRight(1);
//			table.addCell(c1);
//
//			c1 = new PdfPCell(new Phrase("Beneficiary Details"));
//			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//			c1.setColspan(2);
//			c1.setBorder(1);
//			c1.setBorderWidthLeft(1);
//			c1.setBorderWidthRight(1);
//			table.addCell(c1);
//
//			addLeftCell("Beneficiary Account Number", table);
//			addRightCell(transaction.getUdf5(), table);
//
//			addLeftCell("Beneficiary IFSC Code", table);
//			addRightCell(ifscCode, table);
//
//			addLeftCell("Amount", table);
//
//			BigDecimal txnAmount = transaction.getAmt().add(transaction.getCommision());
//			String amt = txnAmount.setScale(2, RoundingMode.HALF_UP).toPlainString();
//
//			addRightCell(amt, table);
//
//			addLeftCell("Name", table);
//			addRightCell(merchant.getMerchantName(), table);
//
//			addLeftCell("Beneficiary Reference Number", table);
//			addRightCell(String.valueOf(transaction.getTransactionId()), table);
//
//			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM yyyy");
//			String challanDate = transaction.getCreatedDate().format(dtf);
//
//			String challanValidity = transaction.getUdf6();
//			if (challanValidity == null || challanValidity.equals("")) {
//				LocalDateTime validity = transaction.getCreatedDate().plus(Period.ofDays(30));
//				challanValidity = validity.format(dtf);
//			} else {
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//				try {
//					Date d = sdf.parse(challanValidity);
//					SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMM yyyy");
//					challanValidity = sdf2.format(d);
//				} catch (ParseException e) {
//					new GlobalExceptionHandler().customException(e);
//				}
//
//			}
//
//			addLeftCell("Challan Date", table);
//			addRightCell(challanDate, table);
//
//			addLeftCell("Challan Validity", table);
//			addRightCell(challanValidity, table);
//
//			c1 = new PdfPCell(new Phrase("Powered by: getepay.in"));
//			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
//			c1.setColspan(2);
//			c1.setBorder(0);
//			table.addCell(c1);
//
//			document.add(table);
//
//			document.close();
//
//		} catch (Exception e) {
//			new GlobalExceptionHandler().customException(e);
//		}
//
//		return filePath;
//	}

	private static void addLeftCell(String text, PdfPTable table) {
		PdfPCell c1 = new PdfPCell(new Phrase(text));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setBorder(1);
		c1.setBorderWidthLeft(1);
		c1.setBorderWidthBottom(1);
		// c1.setBorderWidthRight(1);
		table.addCell(c1);

	}

	private static void addRightCell(String text, PdfPTable table) {
		PdfPCell c1 = new PdfPCell(new Phrase(text));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		c1.setBorder(1);
		c1.setBorderWidthLeft(1);
		c1.setBorderWidthRight(1);
		c1.setBorderWidthBottom(1);
		table.addCell(c1);

	}

	public static List<String> getMonths() {
		List<String> months = new ArrayList<String>();
		months.add("01");
		months.add("02");
		months.add("03");
		months.add("04");
		months.add("05");
		months.add("06");
		months.add("07");
		months.add("08");
		months.add("09");
		months.add("10");
		months.add("11");
		months.add("12");
		return months;
	}

	public static List<String> getYears() {
		List<String> years = new ArrayList<String>();
		Calendar cal = new GregorianCalendar();
		for (int i = 0; i < 30; i++) {
			int year = cal.get(Calendar.YEAR);
			years.add(String.valueOf(year + i));
		}
		return years;
	}

	public static String aunbDecryptResponse(String response, String key) {
		try {
			response = response.replaceAll(" ", "+");
			byte[] responseMessage = auCipherAESECBPKCS5PaddingDecrypt(BaseEncoding.base64().decode(response),
					key.getBytes());
			if (responseMessage != null) {
				return new String(responseMessage);
			}
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public static byte[] auCipherAESECBPKCS5PaddingDecrypt(final byte[] message, final byte[] keyBytes)
			throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException,
			BadPaddingException {
		String algorithm = "AES/ECB/PKCS5Padding";
		final Cipher cipher = Cipher.getInstance(algorithm);
		final String keySpecAlgo = algorithm.split("/")[0];
		final SecretKey secretKey = new SecretKeySpec(keyBytes, keySpecAlgo);
		cipher.init(2, secretKey);
		return cipher.doFinal(message);
	}

	public static Map<String, String> parseAunbResponse(String response, String key) {
		Map<String, String> returnResponse = new HashMap<String, String>();
		String decryptedResponse = aunbDecryptResponse(response, key);
		String[] kv;
		if (decryptedResponse != null) {
			String[] fields = decryptedResponse.split("&");
			for (int i = 0; i < fields.length; ++i) {
				kv = fields[i].split("=");
				if (kv != null && kv.length == 2) {
					String value = kv[1];
					if (value == null) {
						value = "";
					}
					returnResponse.put(kv[0], value.trim());
				}
			}
		}
		return returnResponse;
	}

	public static BigDecimal requaryAmount(TransactionLog t) {
		BigDecimal amount = t.getAmt();
		BigDecimal commissionCharge = BigDecimal.ZERO;
		BigDecimal convinenceCharge = BigDecimal.ZERO;
		if (t.getCommisionType() != null && t.getCommisionType().equalsIgnoreCase("Excl")) {
			commissionCharge = t.getCommision();
			amount = amount.add(commissionCharge);

		} else {
			amount = amount.add(commissionCharge);
		}
		if (t.getServiceChargeType() != null && t.getServiceChargeType().equalsIgnoreCase("Excl")) {
			convinenceCharge = new BigDecimal(t.getTotalServiceCharge());
			convinenceCharge = convinenceCharge.setScale(2, RoundingMode.UP);
			amount = amount.add(convinenceCharge);

		} else {
			amount = amount.add(convinenceCharge);
		}

		return amount;
	}

	public static String getSurcharge(TransactionLog transactionLog) {
		try {
			String surCharges = String.valueOf(transactionLog.getCommision());
			return surCharges;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	// Mask any String or number
	public static String maskStringValue(String stringForMask) {
		String maskString = "";
		try {
			String last = "";
			if (stringForMask.length() > 4) {
				last = stringForMask.substring(stringForMask.length() - 4);
			} else {
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
		if (email != null && !email.equals("")) {

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

	public static String generateChallan(TransactionLog transaction, Merchant merchant, String ifscCode,
			Map<String, String> properties) {
		String basePath = properties.get(Constants.CHALLAN_BASE_PATH);
		String fileName = String.valueOf(transaction.getTransactionId()) + ".pdf";

		String filePath = basePath + File.separator + fileName;

		File baseFolder = new File(basePath);
		if (!baseFolder.exists() || !baseFolder.isDirectory()) {
			baseFolder.mkdirs();
		}

		try {

			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(filePath));

			document.open();

			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(80);

			PdfPCell c1 = new PdfPCell(new Phrase(merchant.getMerchantName() + " " + "NEFT/RTGS Challan"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setColspan(2);
			c1.setBorder(1);
			c1.setBorderWidthLeft(1);
			c1.setBorderWidthRight(1);
			table.addCell(c1);

			c1 = new PdfPCell(new Phrase("Beneficiary Details"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setColspan(2);
			c1.setBorder(1);
			c1.setBorderWidthLeft(1);
			c1.setBorderWidthRight(1);
			table.addCell(c1);

			addLeftCell("Beneficiary Account Number", table);
			addRightCell(transaction.getUdf5(), table);

			addLeftCell("Beneficiary IFSC Code", table);
			addRightCell(ifscCode, table);

			addLeftCell("Amount", table);

			BigDecimal txnAmount = transaction.getAmt().add(transaction.getCommision());
			String amt = txnAmount.setScale(2, RoundingMode.HALF_UP).toPlainString();

			addRightCell(amt, table);

			addLeftCell("Name", table);
			addRightCell(merchant.getMerchantName(), table);

			addLeftCell("Beneficiary Reference Number", table);
			addRightCell(String.valueOf(transaction.getTransactionId()), table);

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM yyyy");
			String challanDate = transaction.getCreatedDate().format(dtf);

			String challanValidity = transaction.getUdf6();
			if (challanValidity == null || challanValidity.equals("")) {
				LocalDateTime validity = transaction.getCreatedDate().plus(Period.ofDays(30));
				challanValidity = validity.format(dtf);
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date d = sdf.parse(challanValidity);
					SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMM yyyy");
					challanValidity = sdf2.format(d);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			addLeftCell("Challan Date", table);
			addRightCell(challanDate, table);

			addLeftCell("Challan Validity", table);
			addRightCell(challanValidity, table);

			c1 = new PdfPCell(new Phrase("Powered by: getepay.in"));
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			c1.setColspan(2);
			c1.setBorder(0);
			table.addCell(c1);

			document.add(table);

			document.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return filePath;
	}

}
