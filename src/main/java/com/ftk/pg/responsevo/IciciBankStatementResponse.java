package com.ftk.pg.responsevo;

public class IciciBankStatementResponse {

	private String CORPID;
	private String USERID;
	private String AGGRID;
	private String AccountNumber;
	private String RECORD;
	private String UNIQUEREFERENCENUMBER;
	private String RESPONSE;

	public String getCORPID() {
		return CORPID;
	}

	public void setCORPID(String CORPID) {
		this.CORPID = CORPID;
	}

	public String getUSERID() {
		return USERID;
	}

	public void setUSERID(String USERID) {
		this.USERID = USERID;
	}

	public String getAGGRID() {
		return AGGRID;
	}

	public void setAGGRID(String AGGRID) {
		this.AGGRID = AGGRID;
	}

	public String getAccountNumber() {
		return AccountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		AccountNumber = accountNumber;
	}

	public String getRECORD() {
		return RECORD;
	}

	public void setRECORD(String RECORD) {
		this.RECORD = RECORD;
	}

	public String getUNIQUEREFERENCENUMBER() {
		return UNIQUEREFERENCENUMBER;
	}

	public void setUNIQUEREFERENCENUMBER(String UNIQUEREFERENCENUMBER) {
		this.UNIQUEREFERENCENUMBER = UNIQUEREFERENCENUMBER;
	}

	public String getRESPONSE() {
		return RESPONSE;
	}

	public void setRESPONSE(String RESPONSE) {
		this.RESPONSE = RESPONSE;
	}
}
