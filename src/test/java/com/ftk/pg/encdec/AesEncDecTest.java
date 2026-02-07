package com.ftk.pg.encdec;

import com.mb.getepay.auupi.portalMerchant.AesEncryption;

public class AesEncDecTest {

	public static void main(String[] args) {
		String fileName = "eye-icon-logo-design.png";	
		String fileName1 = "GETgptn572333691.png";
		String encrypt = AesEncryption.encrypt(fileName1);
		
		System.out.println("encrypt : "+ encrypt);
		
		encrypt = "3FFdhCF7lmxlGNy+X8TqPtcekEhVo0updaBtEdmOOS_nvm_xzt7ujTWG5HCdFClwF5rptBXWt2MNPUNEW97w0A==";
//		encrypt = "3FFdhCF7lmxlGNy+X8TqPtcekEhVo0updaBtEdmOOS+w_FJ2SnYNzU3mUa9SgQw8UVeDgDMaWT3L+BYQTC1awQ==";
		String decrypt = AesEncryption.decrypt(encrypt);
		
		System.out.println("decrypt : "  + decrypt);
		
		
		
	}
}
