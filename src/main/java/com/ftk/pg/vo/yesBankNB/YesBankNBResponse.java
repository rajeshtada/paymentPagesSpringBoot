package com.ftk.pg.vo.yesBankNB;

import java.util.HashMap;
import java.util.Map;

public class YesBankNBResponse {


	private String fldMerchCode;
	private String fldClientCode;
	private String fldTxnCurr;
	private String fldTxnAmt;
	private String fldTxnScAmt;
	private String fldMerchRefNbr;
	private String fldSucStatFlg;
	private String fldFailStatFlg;
	private String fldDatTimeTxn;
	private String fldRef2;
	private String bankRefNo;
	private String message;
	private String checkSum;

	public String getFldMerchCode() {
		return fldMerchCode;
	}

	public void setFldMerchCode(String fldMerchCode) {
		this.fldMerchCode = fldMerchCode;
	}

	public String getFldClientCode() {
		return fldClientCode;
	}

	public void setFldClientCode(String fldClientCode) {
		this.fldClientCode = fldClientCode;
	}

	public String getFldTxnCurr() {
		return fldTxnCurr;
	}

	public void setFldTxnCurr(String fldTxnCurr) {
		this.fldTxnCurr = fldTxnCurr;
	}

	public String getFldTxnAmt() {
		return fldTxnAmt;
	}

	public void setFldTxnAmt(String fldTxnAmt) {
		this.fldTxnAmt = fldTxnAmt;
	}

	public String getFldTxnScAmt() {
		return fldTxnScAmt;
	}

	public void setFldTxnScAmt(String fldTxnScAmt) {
		this.fldTxnScAmt = fldTxnScAmt;
	}

	public String getFldMerchRefNbr() {
		return fldMerchRefNbr;
	}

	public void setFldMerchRefNbr(String fldMerchRefNbr) {
		this.fldMerchRefNbr = fldMerchRefNbr;
	}

	public String getFldSucStatFlg() {
		return fldSucStatFlg;
	}

	public void setFldSucStatFlg(String fldSucStatFlg) {
		this.fldSucStatFlg = fldSucStatFlg;
	}

	public String getFldFailStatFlg() {
		return fldFailStatFlg;
	}

	public void setFldFailStatFlg(String fldFailStatFlg) {
		this.fldFailStatFlg = fldFailStatFlg;
	}

	public String getFldDatTimeTxn() {
		return fldDatTimeTxn;
	}

	public void setFldDatTimeTxn(String fldDatTimeTxn) {
		this.fldDatTimeTxn = fldDatTimeTxn;
	}

	public String getFldRef2() {
		return fldRef2;
	}

	public void setFldRef2(String fldRef2) {
		this.fldRef2 = fldRef2;
	}

	public String getBankRefNo() {
		return bankRefNo;
	}

	public void setBankRefNo(String bankRefNo) {
		this.bankRefNo = bankRefNo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}

	@Override
	public String toString() {
		return "YesBankNBResponse [fldMerchCode=" + fldMerchCode + ", fldClientCode=" + fldClientCode + ", fldTxnCurr="
				+ fldTxnCurr + ", fldTxnAmt=" + fldTxnAmt + ", fldTxnScAmt=" + fldTxnScAmt + ", fldMerchRefNbr="
				+ fldMerchRefNbr + ", fldSucStatFlg=" + fldSucStatFlg + ", fldFailStatFlg=" + fldFailStatFlg
				+ ", fldDatTimeTxn=" + fldDatTimeTxn + ", fldRef2=" + fldRef2 + ", bankRefNo=" + bankRefNo
				+ ", message=" + message + ", checkSum=" + checkSum + "]";
	}

	public String checksum() {
		return "fldMerchCode=" + fldMerchCode + "&fldClientCode=" + fldClientCode + "&fldTxnCurr=" + fldTxnCurr
				+ "&fldTxnAmt=" + fldTxnAmt + "&fldTxnScAmt=" + fldTxnScAmt + "&fldMerchRefNbr=" + fldMerchRefNbr
				+ "&fldSucStatFlg=" + fldSucStatFlg + "&fldFailStatFlg=" + fldFailStatFlg + "&fldDatTimeTxn="
				+ fldDatTimeTxn + "&fldRef2=" + fldRef2 + "&BankRefNo=" + bankRefNo + "&Message=" + message;

	}

	public String finalChecksum() {
		return "fldMerchCode=" + fldMerchCode + "&fldClientCode=" + fldClientCode + "&fldTxnCurr=" + fldTxnCurr
				+ "&fldTxnAmt=" + fldTxnAmt + "&fldTxnScAmt=" + fldTxnScAmt + "&fldMerchRefNbr=" + fldMerchRefNbr
				+ "&fldSucStatFlg=" + fldSucStatFlg + "&fldFailStatFlg=" + fldFailStatFlg + "&fldDatTimeTxn="
				+ fldDatTimeTxn + "&fldRef2=" + fldRef2 + "&BankRefNo=" + bankRefNo + "&Message=" + message
				+ "&checkSum=" + checkSum;
	}

	public YesBankNBResponse(String responseString) {

		Map<String, String> paramMap = new HashMap<>();

		// Split key-value pairs
		String[] pairs = responseString.split("&");
		for (String pair : pairs) {
			String[] keyValue = pair.split("=", 2); // Use limit = 2 to handle missing values
			if (keyValue.length == 2) {
				paramMap.put(keyValue[0], keyValue[1]);
			} else {
				paramMap.put(keyValue[0], ""); // Handle cases where value is missing
			}
		}

		// Map values to the object
		this.fldMerchCode = paramMap.getOrDefault("fldMerchCode", "");
		this.fldClientCode = paramMap.getOrDefault("fldClientCode", "");
		this.fldTxnCurr = paramMap.getOrDefault("fldTxnCurr", "");
		this.fldTxnAmt = paramMap.getOrDefault("fldTxnAmt", "");
		this.fldTxnScAmt = paramMap.getOrDefault("fldTxnScAmt", "");
		this.fldMerchRefNbr = paramMap.getOrDefault("fldMerchRefNbr", "");
		this.fldSucStatFlg = paramMap.getOrDefault("fldSucStatFlg", "");
		this.fldFailStatFlg = paramMap.getOrDefault("fldFailStatFlg", "");
		this.fldDatTimeTxn = paramMap.getOrDefault("fldDatTimeTxn", "");
		this.fldRef2 = paramMap.getOrDefault("fldRef2", "");
		this.bankRefNo = paramMap.getOrDefault("BankRefNo", "");
		this.message = paramMap.getOrDefault("Message", "");
		this.checkSum = paramMap.getOrDefault("checkSum", "");

	}
}
