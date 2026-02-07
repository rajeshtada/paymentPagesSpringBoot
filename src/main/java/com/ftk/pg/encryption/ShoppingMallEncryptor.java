package com.ftk.pg.encryption;

import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ftk.pg.exception.GlobalExceptionHandler;
import com.infosys.bankaway.icici.util.EncryptDecryptUtil;

/**
 * @author arun k n
 */
public class ShoppingMallEncryptor {
	static Logger logger = LogManager.getLogger(ShoppingMallEncryptor.class);


//	public static void main(String[] args) {
	// get the master key here from db or flat file
//		String masterKey = "sf&r8,+cd&^9 04-";

	/*-----Encryption of String #### From Vendor to Infinity - Start------*/

	// Intializing the string that should be encrypted
	/*
	 * Vendor should encrypt the values of PRN, ITC, AMT, RU, CRN, CG etc as shown
	 * below Note that RU should start and end with %22
	 */
//		String stringToEncrypt = "PRN=78945612396325871&ITC=1023814&AMT=1&CRN=INR&RU=%22https://commerce.rediff.com/commerce/returntorediff.jsp?payopt=ICI&redeemgc=n&gcertno=%22&CG=Y";

	// Calling the encrypt function to encrypt the String by passing
	// the master key and the string to encrypt function.
	// This encrypted value is passed in the ES field of the URL posted to infinity
//		String encryptedString = encrypt(masterKey,stringToEncrypt);

	// Printing the encrypted String.
//		System.out.println("Encrypted string is ::: " + encryptedString);

	/*-----Encryption of String#### From Vendor to Infinity - End-----*/

	/*
	 * The request is processed at infinity end and as part of the return URL a
	 * parameter ES is passed to the vendor
	 */

	/*-----Decryption of String#### From Infinity to Vendor - Start-----*/

	// String
	// ESstringFromInfinity="Qav+NqYMZtCrFH2zqhtQ0m59jcnu4z7X0n0DOfmlkz2m4SjsuTLdDHOOXO3+OzUrfrgKJRCX+OiS46uSQx8bv2+Zau3fxfgeyWxlC3Pa6PlAO9Y/OpNx2gHFSicwJY0D";

	// Calling the decrypt function to decrypt the String by passing
	// the master key and the string to decrypt function.

	// String decryptedString = decrypt(masterKey, ESstringFromInfinity);

	// Printing the decrypted String.
	// System.out.println("decryptedString is ::: " + decryptedString);

	/*-----Decryption of String#### From Infinity to Vendor - End-----*/
//	}

	public static String encrypt(String masterKey, String stringToEncrypt) {
		logger.info("Inside encrypt method and the masterKey is ::: " + masterKey);
		String encryptedString = "";

		try {
			EncryptDecryptUtil util = new EncryptDecryptUtil(masterKey);
			encryptedString = util.encrypt(stringToEncrypt);
//			System.out.println("--------------------------------------------------------");
//			System.out.println("Decrypted String is ::: " + util.decrypt(encryptedString));
//			System.out.println("--------------------------------------------------------");
		} catch (NoSuchAlgorithmException e) {
			logger.info("NoSuchAlgorithmException occurred");
			new GlobalExceptionHandler().customException(e);
		} catch (NoSuchPaddingException e) {
			logger.info("NoSuchPaddingException occurred");
			new GlobalExceptionHandler().customException(e);
		} catch (Exception e) {
			logger.info("Exception occurred");
			new GlobalExceptionHandler().customException(e);
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
			new GlobalExceptionHandler().customException(e);
		} catch (NoSuchPaddingException e) {
			logger.info("NoSuchPaddingException occurred");
			new GlobalExceptionHandler().customException(e);
		} catch (Exception e) {
			logger.info("Exception occurred");
			new GlobalExceptionHandler().customException(e);
		}
		return decryptedString;
	}

}