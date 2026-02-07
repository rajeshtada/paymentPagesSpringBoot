package com.ftk.pg.requestvo;

public class IciciBankStatementRequest {

	private String CORPID;
	private String USERID;
	private String AGGRID;
	private String AccountNumber;
	private String FROMDATE;
	private String TODATE;
	private String UNIQUEREFERENCENumber;
	
	
	
	public String getCORPID() {
		return CORPID;
	}
	public void setCORPID(String cORPID) {
		CORPID = cORPID;
	}

	public String getAGGRID() {
		return AGGRID;
	}
	public void setAGGRID(String aGGRID) {
		AGGRID = aGGRID;
	}
	public String getAccountNumber() {
		return AccountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		AccountNumber = accountNumber;
	}
	public String getFROMDATE() {
		return FROMDATE;
	}
	public void setFROMDATE(String fROMDATE) {
		FROMDATE = fROMDATE;
	}
	public String getTODATE() {
		return TODATE;
	}
	public void setTODATE(String tODATE) {
		TODATE = tODATE;
	}
	public String getUNIQUEREFERENCENumber() {
		return UNIQUEREFERENCENumber;
	}
	public void setUNIQUEREFERENCENumber(String uNIQUEREFERENCENumber) {
		UNIQUEREFERENCENumber = uNIQUEREFERENCENumber;
	}


	public String getUSERID() {
		return USERID;
	}

	public void setUSERID(String USERID) {
		this.USERID = USERID;
	}
}
