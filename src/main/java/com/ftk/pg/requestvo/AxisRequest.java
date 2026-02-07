package com.ftk.pg.requestvo;

public class AxisRequest {
	private String PID;
	private String PRN;
	private String AMT;
	private String MD;
	private String CRN;
	private String ITC;
	private String CG;
	private String RESPONSE;
	private String checksum;

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

	public String getAMT() {
		return AMT;
	}

	public void setAMT(String aMT) {
		AMT = aMT;
	}

	public String getMD() {
		return MD;
	}

	public void setMD(String mD) {
		MD = mD;
	}

	public String getCRN() {
		return CRN;
	}

	public void setCRN(String cRN) {
		CRN = cRN;
	}

	public String getITC() {
		return ITC;
	}

	public void setITC(String iTC) {
		ITC = iTC;
	}

	public String getCG() {
		return CG;
	}

	public void setCG(String cG) {
		CG = cG;
	}

	public String getRESPONSE() {
		return RESPONSE;
	}

	public void setRESPONSE(String rESPONSE) {
		RESPONSE = rESPONSE;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	@Override
	public String toString() {
		return "AxisRequest [PID=" + PID + ", PRN=" + PRN + ", AMT=" + AMT + ", MD=" + MD + ", CRN=" + CRN + ", ITC="
				+ ITC + ", CG=" + CG + ", RESPONSE=" + RESPONSE + ", checksum=" + checksum + "]";
	}

	public String checksumpipeSeprated() {
		return "PRN~" + PRN + "$PID~" + PID + "$MD~" + MD + "$ITC~" + ITC + "$CRN~" + CRN + "$AMT~" + AMT + "$RESPONSE~"
				+ RESPONSE + "$CG~" + CG;
	}

	public String pipeSeprated() {
		return "PRN~" + PRN + "$PID~" + PID + "$MD~" + MD + "$ITC~" + ITC + "$CRN~" + CRN + "$AMT~" + AMT + "$RESPONSE~"
				+ RESPONSE + "$CG~" + CG + "$checksum~" + checksum;
	}

}
