package com.ftk.pg.vo;

public class IDBINBResponse {

	private String PAID;
	private String BID;
	private String CRN;
	private String AMT;
	private String PID;
	private String PRN;
	private String ITC;
	private String ACCOUNT_NO;
	public String getPAID() {
		return PAID;
	}
	public void setPAID(String pAID) {
		PAID = pAID;
	}
	public String getBID() {
		return BID;
	}
	public void setBID(String bID) {
		BID = bID;
	}
	public String getCRN() {
		return CRN;
	}
	public void setCRN(String cRN) {
		CRN = cRN;
	}
	public String getAMT() {
		return AMT;
	}
	public void setAMT(String aMT) {
		AMT = aMT;
	}
	public String getPID() {
		return PID;
	}
	public void setPID(String pID) {
		PID = pID;
	}
	public String getPRN() {
		return PRN;
	}
	public void setPRN(String pRN) {
		PRN = pRN;
	}
	public String getITC() {
		return ITC;
	}
	public void setITC(String iTC) {
		ITC = iTC;
	}
	public String getACCOUNT_NO() {
		return ACCOUNT_NO;
	}
	public void setACCOUNT_NO(String aCCOUNT_NO) {
		ACCOUNT_NO = aCCOUNT_NO;
	}
	@Override
	public String toString() {
		return "IDBINBResponse [PAID=" + PAID + ", BID=" + BID + ", CRN=" + CRN + ", AMT=" + AMT + ", PID=" + PID
				+ ", PRN=" + PRN + ", ITC=" + ITC + ", ACCOUNT_NO=" + ACCOUNT_NO + "]";
	}
	
	public IDBINBResponse(String response) {
		String[] arr = response.split("&");
		this.PAID = arr[0].split("=").length <=1 ? "": arr[0].split("=")[1];
		this.BID = arr[1].split("=").length <=1 ? "": arr[1].split("=")[1];
		this.CRN = arr[2].split("=").length <=1 ? "": arr[2].split("=")[1];
		this.AMT = arr[3].split("=").length <=1 ? "": arr[3].split("=")[1];
		this.PID = arr[4].split("=").length <=1 ? "": arr[4].split("=")[1];
		this.PRN = arr[5].split("=").length <=1 ? "": arr[5].split("=")[1];
		this.ITC = arr[6].split("=").length <=1 ? "": arr[6].split("=")[1];
		this.ACCOUNT_NO = arr[7].split("=").length <=1 ? "": arr[7].split("=")[1];

	}
	
	
	
	
	

	
}
