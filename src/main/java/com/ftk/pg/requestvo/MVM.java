package com.ftk.pg.requestvo;

public class MVM {

	public String MD;

	public String PaReq;

	public String TermUrl;

	public String getMD() {
		return MD;
	}

	public void setMD(String mD) {
		MD = mD;
	}

	public String getPaReq() {
		return PaReq;
	}

	public void setPaReq(String paReq) {
		PaReq = paReq;
	}

	public String getTermUrl() {
		return TermUrl;
	}

	public void setTermUrl(String termUrl) {
		TermUrl = termUrl;
	}

	@Override
	public String toString() {
		return "MVM [MD=" + MD + ", PaReq=" + PaReq + ", TermUrl=" + TermUrl + "]";
	}

}
