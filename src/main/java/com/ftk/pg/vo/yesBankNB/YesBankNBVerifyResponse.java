package com.ftk.pg.vo.yesBankNB;

import java.util.HashMap;
import java.util.Map;

public class YesBankNBVerifyResponse {
	private String fldMerchCode;
	private String fldClientCode;
	private String fldTxnCurr;
	private String fldTxnAmt;
	private String fldTxnScAmt;
	private String fldMerchRefNbr;
	private String fldDatTimeTxn;
	private String BankRefNo;
	private String fldClientAcctNo;
	private String flgSuccess;
	private String Message;
	private String fldVerify;
	private String flgVerify;
    private String fldRef2;
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

	public String getFldDatTimeTxn() {
		return fldDatTimeTxn;
	}

	public void setFldDatTimeTxn(String fldDatTimeTxn) {
		this.fldDatTimeTxn = fldDatTimeTxn;
	}

	public String getBankRefNo() {
		return BankRefNo;
	}

	public void setBankRefNo(String bankRefNo) {
		BankRefNo = bankRefNo;
	}

	public String getFldClientAcctNo() {
		return fldClientAcctNo;
	}

	public void setFldClientAcctNo(String fldClientAcctNo) {
		this.fldClientAcctNo = fldClientAcctNo;
	}

	public String getFlgSuccess() {
		return flgSuccess;
	}

	public void setFlgSuccess(String flgSuccess) {
		this.flgSuccess = flgSuccess;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public String getFldVerify() {
		return fldVerify;
	}

	public void setFldVerify(String fldVerify) {
		this.fldVerify = fldVerify;
	}

	public String getFldRef2() {
		return fldRef2;
	}

	public void setFldRef2(String fldRef2) {
		this.fldRef2 = fldRef2;
	}
	

	public String getFlgVerify() {
		return flgVerify;
	}

	public void setFlgVerify(String flgVerify) {
		this.flgVerify = flgVerify;
	}

	@Override
	public String toString() {
		return "YesBankNBVerifyResponse [fldMerchCode=" + fldMerchCode + ", fldClientCode=" + fldClientCode
				+ ", fldTxnCurr=" + fldTxnCurr + ", fldTxnAmt=" + fldTxnAmt + ", fldTxnScAmt=" + fldTxnScAmt
				+ ", fldMerchRefNbr=" + fldMerchRefNbr + ", fldDatTimeTxn=" + fldDatTimeTxn + ", BankRefNo=" + BankRefNo
				+ ", fldClientAcctNo=" + fldClientAcctNo + ", flgSuccess=" + flgSuccess + ", Message=" + Message
				+ ", fldVerify=" + fldVerify + ", fldRef2=" + fldRef2 + "]";
	}

	public YesBankNBVerifyResponse(String responseString) {

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

//		private String fldMerchCode;
//		private String fldClientCode;
//		private String fldTxnCurr;
//		private String fldTxnAmt;
//		private String fldTxnScAmt;
//		private String fldMerchRefNbr;
//		private String fldDatTimeTxn;
//		private String BankRefNo;
//		private String fldClientAcctNo;
//		private String flgSuccess;
//		private String Message;
//		private String fldVerify;
//		private String fldRef2;

//		fldMerchCode=INSOLUTION&fldClientCode=IN01&fldTxnCurr=INR&fldTxnAmt=1.00&fldTxnScAmt=0.00&fldMerchRefNbr=10724&fldDatTimeTxn=20/03/2025 12:46:17&BankRefNo=111151036760&fldClientAcctNo=&flgSuccess=S&Message=Merchant transaction successfull&flgVerify=V&fldRef2=INSUB01&checkSum=ed80dbf79d12f1a5a9edc15a07246862		
		// Map values to the object
		this.fldMerchCode = paramMap.getOrDefault("fldMerchCode", "");
		this.fldClientCode = paramMap.getOrDefault("fldClientCode", "");
		this.fldTxnCurr = paramMap.getOrDefault("fldTxnCurr", "");
		this.fldTxnAmt = paramMap.getOrDefault("fldTxnAmt", "");
		this.fldTxnScAmt = paramMap.getOrDefault("fldTxnScAmt", "");
		this.fldMerchRefNbr = paramMap.getOrDefault("fldMerchRefNbr", "");
		this.fldDatTimeTxn = paramMap.getOrDefault("fldDatTimeTxn", "");
		this.fldRef2 = paramMap.getOrDefault("fldRef2", "");
		this.BankRefNo = paramMap.getOrDefault("BankRefNo", "");
		this.fldClientAcctNo = paramMap.getOrDefault("fldClientAcctNo", "");
		this.Message = paramMap.getOrDefault("Message", "");
		this.flgVerify = paramMap.getOrDefault("flgVerify", "");
		this.fldRef2 = paramMap.getOrDefault("fldRef2", "");
		this.checkSum = paramMap.getOrDefault("checkSum", "");

	}

}
