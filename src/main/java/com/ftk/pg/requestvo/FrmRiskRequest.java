package com.ftk.pg.requestvo;

public class FrmRiskRequest {
	private String TXN_ID;
	private String RRN_NO;
	private String AMOUNT;
	private String PAYER_VPA;
	private String MOBILE_NUMBER;
	private String PAYMENT_MODE;
	private String IP;
	private String CARD_NUMBER;
	private String CARD_HOLDER_NAME;
	private String DATE;
	private String MID;
	private String EMAIL;
	public String getTXN_ID() {
		return TXN_ID;
	}
	public void setTXN_ID(String tXN_ID) {
		TXN_ID = tXN_ID;
	}
	public String getRRN_NO() {
		return RRN_NO;
	}
	public void setRRN_NO(String rRN_NO) {
		RRN_NO = rRN_NO;
	}
	public String getAMOUNT() {
		return AMOUNT;
	}
	public void setAMOUNT(String aMOUNT) {
		AMOUNT = aMOUNT;
	}
	public String getPAYER_VPA() {
		return PAYER_VPA;
	}
	public void setPAYER_VPA(String pAYER_VPA) {
		PAYER_VPA = pAYER_VPA;
	}
	public String getMOBILE_NUMBER() {
		return MOBILE_NUMBER;
	}
	public void setMOBILE_NUMBER(String mOBILE_NUMBER) {
		MOBILE_NUMBER = mOBILE_NUMBER;
	}
	public String getPAYMENT_MODE() {
		return PAYMENT_MODE;
	}
	public void setPAYMENT_MODE(String pAYMENT_MODE) {
		PAYMENT_MODE = pAYMENT_MODE;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getCARD_NUMBER() {
		return CARD_NUMBER;
	}
	public void setCARD_NUMBER(String cARD_NUMBER) {
		CARD_NUMBER = cARD_NUMBER;
	}
	public String getCARD_HOLDER_NAME() {
		return CARD_HOLDER_NAME;
	}
	public void setCARD_HOLDER_NAME(String cARD_HOLDER_NAME) {
		CARD_HOLDER_NAME = cARD_HOLDER_NAME;
	}
	public String getDATE() {
		return DATE;
	}
	public void setDATE(String dATE) {
		DATE = dATE;
	}
	
	public String getMID() {
		return MID;
	}
	public void setMID(String mID) {
		MID = mID;
	}
	
	
	public String getEMAIL() {
		return EMAIL;
	}
	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}
	@Override
	public String toString() {
		return "FrmRiskRequest [TXN_ID=" + TXN_ID + ", RRN_NO=" + RRN_NO + ", AMOUNT=" + AMOUNT + ", PAYER_VPA="
				+ PAYER_VPA + ", MOBILE_NUMBER=" + MOBILE_NUMBER + ", PAYMENT_MODE=" + PAYMENT_MODE + ", IP=" + IP
				+ ", CARD_NUMBER=" + CARD_NUMBER + ", CARD_HOLDER_NAME=" + CARD_HOLDER_NAME + ", DATE=" + DATE
				+ ", MID=" + MID + ", EMAIL=" + EMAIL + "]";
	}
	

}
