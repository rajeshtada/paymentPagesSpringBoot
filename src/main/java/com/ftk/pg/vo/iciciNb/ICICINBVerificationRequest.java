package com.ftk.pg.vo.iciciNb;

public class ICICINBVerificationRequest {
	private String MD;
	private String PID;
	private String PRN;
	private String ITC;
	private String AMT;
	private String CRN;
	private String BID;
	public String getMD() {
		return MD;
	}
	public void setMD(String mD) {
		MD = mD;
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
	public String getAMT() {
		return AMT;
	}
	public void setAMT(String aMT) {
		AMT = aMT;
	}
	public String getCRN() {
		return CRN;
	}
	public void setCRN(String cRN) {
		CRN = cRN;
	}
	public String getBID() {
		return BID;
	}
	public void setBID(String bID) {
		BID = bID;
	}
	@Override
	public String toString() {
		return "ICICINBVerificationRequest [MD=" + MD + ", PID=" + PID + ", PRN=" + PRN + ", ITC=" + ITC + ", AMT="
				+ AMT + ", CRN=" + CRN + ", BID=" + BID + "]";
	}
	

}
