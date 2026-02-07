package com.ftk.pg.vo.iciciNb;


import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ftk.pg.util.EncryptDecryptUtil;
import com.ftk.pg.util.PGUtility;


public class IciciNbEncUtil {
	
	static Logger logger = LogManager.getLogger(IciciNbEncUtil.class);

	public static void main(String[] args) {
		String masterKey = "ATH3UR9KHLHKVW82==";

		String stringToEncrypt = "PRN=78945612396325871&ITC=1023814&AMT=1&CRN=INR&RU=%22https://commerce.rediff.com/commerce/returntorediff.jsp?payopt=ICI&redeemgc=n&gcertno=%22&CG=Y";

		String encryptedString = encrypt(masterKey, stringToEncrypt);

		// Printing the encrypted String.
		System.out.println("Encrypted string is ::: " + encryptedString);

		String ESstringFromInfinity = "Qav+NqYMZtCrFH2zqhtQ0m59jcnu4z7X0n0DOfmlkz2m4SjsuTLdDHOOXO3+OzUrfrgKJRCX+OiS46uSQx8bv2+Zau3fxfgeyWxlC3Pa6PlAO9Y/OpNx2gHFSicwJY0D";

		String decryptedString = decrypt(masterKey, ESstringFromInfinity);

		System.out.println("decryptedString is ::: " + decryptedString);

	}

	public static String encrypt(String masterKey, String stringToEncrypt) {
		logger.info("Inside encrypt method and the masterKey is ::: " + masterKey);
		String encryptedString = "";

		try {
			EncryptDecryptUtil util = new EncryptDecryptUtil(masterKey);
			encryptedString = util.encrypt(stringToEncrypt);
		} catch (NoSuchAlgorithmException e) {
			logger.info("NoSuchAlgorithmException occurred");
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			logger.info("NoSuchPaddingException occurred");
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("Exception occurred");
			e.printStackTrace();
		}
		return encryptedString;
	}

	public static String decrypt(String masterKey, String encryptedString) {
		// masterkey will be fetched from PRPM, here it is passes as an argument
		logger.info("Inside decrypt method and the masterKey is ::: " + masterKey);
		String decryptedString = "";
		try {
			EncryptDecryptUtil util = new EncryptDecryptUtil(masterKey);
			decryptedString = util.decrypt(encryptedString);
		} catch (NoSuchAlgorithmException e) {
			logger.info("NoSuchAlgorithmException occurred");
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			logger.info("NoSuchPaddingException occurred");
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("Exception occurred");
			e.printStackTrace();
		}
		return decryptedString;
	}

}