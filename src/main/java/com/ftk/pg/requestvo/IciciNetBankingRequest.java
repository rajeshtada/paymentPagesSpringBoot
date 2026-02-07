package com.ftk.pg.requestvo;

public class IciciNetBankingRequest {

//	private String MD;
	
//	private String P;

//	private String V;

	private String PID;

	private String PRN;

	private String ITC;

	private String AMT;

	private String CRN;

	private String RU;
	
	private String CG;
	
	private String ACCNO;
	
	private String BID;

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

	public String getRU() {
		return RU;
	}

	public void setRU(String rU) {
		RU = rU;
	}

	public String getCG() {
		return CG;
	}

	public void setCG(String cG) {
		CG = cG;
	}

	public String getACCNO() {
		return ACCNO;
	}

	public void setACCNO(String aCCNO) {
		ACCNO = aCCNO;
	}

	public String getBID() {
		return BID;
	}

	public void setBID(String bID) {
		BID = bID;
	}

	@Override
	public String toString() {
		return "IciciNetBankingRequest [PID=" + PID + ", PRN=" + PRN + ", ITC=" + ITC + ", AMT=" + AMT + ", CRN=" + CRN
				+ ", RU=" + RU + ", CG=" + CG + ", ACCNO=" + ACCNO + ", BID=" + BID + "]";
	}

	
	public String getFinalPipeData() {
		return "PRN="+PRN+"&ITC="+ITC+"&AMT="+AMT+"&CRN="+CRN+"&RU="+RU+"&CG="+CG+"&ACCNO"+ACCNO;
		
	}

	
}