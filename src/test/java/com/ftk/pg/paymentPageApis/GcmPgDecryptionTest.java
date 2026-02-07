package com.ftk.pg.paymentPageApis;

import com.ftk.pg.encryption.GcmPgEncryption;
import com.ftk.pg.requestvo.TokenRequest;
import com.google.gson.Gson;

public class GcmPgDecryptionTest {

	public static void main(String[] args) throws Exception {

		String token = "d8c8af0b-bfda-4388-a46a-8d06db09ac9f";
		String ivKey = token;

//		String deReq = "{\"token\":\"" + token + "\"}";
//		System.out.println("deReq==>" + deReq);
//
//		String enc = enc(deReq, ivKey);
		String enc = "lWbsOtv8ckAAUFNP/x41LdzsDGmSouDGW/Em/JJtGonx8DNBhn+sfyveXcnmldHruOFNUelCzjCNuw5oZLHtzs0eO1fstJfQL/M9QKv1gEauv9odFbs25egTv04=";	
		String dec = dec(enc, ivKey);

	}

	private static String enc(String data, String ivKey) throws Exception {
		GcmPgEncryption gcmPgEncryption = new GcmPgEncryption(ivKey);
		String enc = gcmPgEncryption.encryptWithMKeys(data);
		System.out.println("enc => " + enc);
		return enc;
	}

	private static String dec(String enc, String ivKey) throws Exception {
		GcmPgEncryption gcmPgEncryption = new GcmPgEncryption(ivKey);
		String dec = gcmPgEncryption.decryptWithMKeys(enc);
		System.out.println("dec => " + dec);
		return dec;
	}

}
