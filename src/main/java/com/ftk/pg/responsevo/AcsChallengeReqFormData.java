package com.ftk.pg.responsevo;

public class AcsChallengeReqFormData {
	private String paReq;
	private String cReq;
	private String md;
	public String getPaReq() {
		return paReq;
	}
	public void setPaReq(String paReq) {
		this.paReq = paReq;
	}
	public String getcReq() {
		return cReq;
	}
	public void setcReq(String cReq) {
		this.cReq = cReq;
	}
	public String getMd() {
		return md;
	}
	public void setMd(String md) {
		this.md = md;
	}
	@Override
	public String toString() {
		return "AcsChallengeReqFormData [paReq=" + paReq + ", cReq=" + cReq + ", md=" + md + "]";
	}
	

}
