package com.ftk.pg.vo.axis;

public class AxisResponse {
	private String PAID;
	private String BID;
	private String PRN;
	private String AMT;
	private String ITC;
	private String CRN;
	private String STATFLG;
	private String checksum;

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

	public String getITC() {
		return ITC;
	}

	public void setITC(String iTC) {
		ITC = iTC;
	}

	public String getCRN() {
		return CRN;
	}

	public void setCRN(String cRN) {
		CRN = cRN;
	}

	public String getSTATFLG() {
		return STATFLG;
	}

	public void setSTATFLG(String sTATFLG) {
		STATFLG = sTATFLG;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	@Override
	public String toString() {
		return "AxisResponse [PAID=" + PAID + ", BID=" + BID + ", PRN=" + PRN + ", AMT=" + AMT + ", ITC=" + ITC
				+ ", CRN=" + CRN + ", STATFLG=" + STATFLG + ", checksum=" + checksum + "]";
	}

	public String checksumpipeSeprated() {
		return "PAID~" + PAID + "$BID~" + BID + "$PRN~" + PRN + "$AMT~" + AMT + "$CRN~" + CRN + "$ITC~" + ITC + "$CRN~"
				+ CRN + "$STATFLG~" + STATFLG;
	}

	public String pipeSeprated() {
		return "PAID~" + PAID + "$BID~" + BID + "$PRN~" + PRN + "$AMT~" + AMT + "$CRN~" + CRN + "$ITC~" + ITC + "$CRN~"
				+ CRN + "$STATFLG~" + STATFLG + "";
	}
	
	 public AxisResponse(String response) {
	        String[] arr = response.split("&");
	        this.PAID = arr[0].split("=").length <= 1 ? "" : arr[0].split("=")[1];
	        this.PRN = arr[1].split("=").length <= 1 ? "" : arr[1].split("=")[1];
	        this.BID = arr[2].split("=").length <= 1 ? "" : arr[2].split("=")[1];
	        this.ITC = arr[3].split("=").length <= 1 ? "" : arr[3].split("=")[1];
	        this.AMT = arr[4].split("=").length <= 1 ? "" : arr[4].split("=")[1];
	        this.CRN = arr[5].split("=").length <= 1 ? "" : arr[5].split("=")[1];
	        this.STATFLG = arr[6].split("=").length <= 1 ? "" : arr[6].split("=")[1];
	        this.checksum = arr[7].split("=").length <= 1 ? "" : arr[7].split("=")[1];
	    }

}
