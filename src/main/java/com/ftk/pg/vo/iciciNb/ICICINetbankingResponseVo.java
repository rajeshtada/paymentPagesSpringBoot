package com.ftk.pg.vo.iciciNb;

public class ICICINetbankingResponseVo {

	private String PAID;

	private String PRN;

	private String ITC;

	private String AMT;

	private String CRN;

	private String FEDID;
	
	private String BID;

	private String redeemgc;

	private String gcertno;
	

	public String getPAID() {
		return PAID;
	}

	public void setPAID(String pAID) {
		PAID = pAID;
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

	public String getRedeemgc() {
		return redeemgc;
	}

	public void setRedeemgc(String redeemgc) {
		this.redeemgc = redeemgc;
	}

	public String getGcertno() {
		return gcertno;
	}

	public void setGcertno(String gcertno) {
		this.gcertno = gcertno;
	}
	

	public String getFEDID() {
		return FEDID;
	}

	public void setFEDID(String fEDID) {
		FEDID = fEDID;
	}
	
	

	@Override
	public String toString() {
		return "ICICINetbankingResponseVo [PAID=" + PAID + ", PRN=" + PRN + ", ITC=" + ITC + ", AMT=" + AMT + ", CRN="
				+ CRN + ", FEDID=" + FEDID + ", BID=" + BID + ", redeemgc=" + redeemgc + ", gcertno=" + gcertno + "]";
	}

	public ICICINetbankingResponseVo(String response) {
		String[] arr = response.split("&");
		this.PRN = arr[0].split("=").length <= 1 ? "" : arr[0].split("=")[1];
		this.ITC = arr[1].split("=").length <= 1 ? "" : arr[1].split("=")[1];
		this.AMT = arr[2].split("=").length <= 1 ? "" : arr[2].split("=")[1];
		this.CRN = arr[3].split("=").length <= 1 ? "" : arr[3].split("=")[1];
		this.PAID = arr[4].split("=").length <= 1 ? "" : arr[4].split("=")[1];
		if (arr.length > 5) {
			this.BID = arr[5].split("=").length <= 1 ? "" : arr[5].split("=")[1];
		}
		if (arr.length > 6) {
			this.BID = arr[6].split("=").length <= 1 ? "" : arr[6].split("=")[1];
		}

	}
}
