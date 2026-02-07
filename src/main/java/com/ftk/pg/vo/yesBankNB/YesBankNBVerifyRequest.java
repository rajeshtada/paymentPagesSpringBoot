package com.ftk.pg.vo.yesBankNB;

public class YesBankNBVerifyRequest {
	private String fldClientCode;
	private String fldMerchCode;
	private String fldTxnCurr;
	private String fldTxnAmt;
	private String fldTxnScAmt;
	private String fldMerchRefNbr;
	private String fldDatTimeTxn;
	private String BankRefNo;
	private String fldRef2;
	private String fldClientAcctNo;
	private String RU;
	private String flgVerify;

	private String CHECKSUM;

	public String getFldClientCode() {
		return fldClientCode;
	}

	public void setFldClientCode(String fldClientCode) {
		this.fldClientCode = fldClientCode;
	}

	public String getFldMerchCode() {
		return fldMerchCode;
	}

	public void setFldMerchCode(String fldMerchCode) {
		this.fldMerchCode = fldMerchCode;
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

	public String getFldRef2() {
		return fldRef2;
	}

	public void setFldRef2(String fldRef2) {
		this.fldRef2 = fldRef2;
	}

	public String getRU() {
		return RU;
	}

	public void setRU(String rU) {
		RU = rU;
	}

	public String getFlgVerify() {
		return flgVerify;
	}

	public void setFlgVerify(String flgVerify) {
		this.flgVerify = flgVerify;
	}

	public String getCHECKSUM() {
		return CHECKSUM;
	}

	public void setCHECKSUM(String cHECKSUM) {
		CHECKSUM = cHECKSUM;
	}

	@Override
	public String toString() {
		return "YesBankNBVerifyRequest [fldClientCode=" + fldClientCode + ", fldMerchCode=" + fldMerchCode
				+ ", fldTxnCurr=" + fldTxnCurr + ", fldTxnAmt=" + fldTxnAmt + ", fldTxnScAmt=" + fldTxnScAmt
				+ ", fldMerchRefNbr=" + fldMerchRefNbr + ", fldDatTimeTxn=" + fldDatTimeTxn + ", BankRefNo=" + BankRefNo
				+ ", fldRef2=" + fldRef2 + ", fldClientAcctNo=" + fldClientAcctNo + ", RU=" + RU + ", flgVerify="
				+ flgVerify + ", CHECKSUM=" + CHECKSUM + "]";
	}

	public String getFldClientAcctNo() {
		return fldClientAcctNo;
	}

	public void setFldClientAcctNo(String fldClientAcctNo) {
		this.fldClientAcctNo = fldClientAcctNo;
	}

	public String checkSum() {

		return "fldClientCode=" + fldClientCode + "&fldMerchCode=" + fldMerchCode + "&fldTxnCurr=" + fldTxnCurr
				+ "&fldTxnAmt=" + fldTxnAmt + "&fldTxnScAmt=" + fldTxnScAmt + "&fldMerchRefNbr=" + fldMerchRefNbr
				+ "&BankRefNo=" + BankRefNo + "&flgVerify=" + flgVerify + "&fldDatTimeTxn=" + fldDatTimeTxn
				+ "&fldRef2=" + fldRef2 + "&RU=" + RU + "&fldClientAcctNo=";
	}

	public String finalCheckSum() {
		return "fldClientCode=" + fldClientCode + "&fldMerchCode=" + fldMerchCode + "&fldTxnCurr=" + fldTxnCurr
				+ "&fldTxnAmt=" + fldTxnAmt + "&fldTxnScAmt=" + fldTxnScAmt + "&fldMerchRefNbr=" + fldMerchRefNbr
				+ "&BankRefNo=" + BankRefNo + "&flgVerify=" + flgVerify + "&fldDatTimeTxn=" + fldDatTimeTxn
				+ "&fldRef2=" + fldRef2 + "&RU=" + RU + "&fldClientAcctNo=" + "&CHECKSUM=" + CHECKSUM;

	}

}
