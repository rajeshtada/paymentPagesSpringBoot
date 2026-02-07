package com.ftk.pg.vo;

public class UcoReponseVo {
	private String paid;
	private String bid;
	private String crn;
	private String amt;
	private String pid;
	private String prn;
	private String itc;
	private String Acct_No;

	public String getPaid() {
		return paid;
	}

	public void setPaid(String paid) {
		this.paid = paid;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getCrn() {
		return crn;
	}

	public void setCrn(String crn) {
		this.crn = crn;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPrn() {
		return prn;
	}

	public void setPrn(String prn) {
		this.prn = prn;
	}

	public String getItc() {
		return itc;
	}

	public void setItc(String itc) {
		this.itc = itc;
	}

	public String getAcct_No() {
		return Acct_No;
	}

	public void setAcct_No(String acct_No) {
		Acct_No = acct_No;
	}

	public UcoReponseVo(String response) {
		String[] arr = response.split("&");
		this.paid = arr[0].split("=").length <=1 ? "": arr[0].split("=")[1];
		this.bid = arr[1].split("=").length <=1 ? "": arr[1].split("=")[1];
		this.crn = arr[2].split("=").length <=1 ? "": arr[2].split("=")[1];
		this.amt = arr[3].split("=").length <=1 ? "": arr[3].split("=")[1];
		this.pid = arr[4].split("=").length <=1 ? "": arr[4].split("=")[1];
		this.prn = arr[5].split("=").length <=1 ? "": arr[5].split("=")[1];
		this.itc = arr[6].split("=").length <=1 ? "": arr[6].split("=")[1];
		this.Acct_No = arr[7].split("=").length <=1 ? "": arr[7].split("=")[1];

	}

	public UcoReponseVo(String paid, String bid, String crn, String amt, String pid, String prn, String itc,
			String acct_No) {
		super();
		this.paid = paid;
		this.bid = bid;
		this.crn = crn;
		this.amt = amt;
		this.pid = pid;
		this.prn = prn;
		this.itc = itc;
		this.Acct_No = acct_No;
	}

	@Override
	public String toString() {
		return "UcoReponseVo [paid=" + paid + ", bid=" + bid + ", crn=" + crn + ", amt=" + amt + ", pid=" + pid
				+ ", prn=" + prn + ", itc=" + itc + ", Acct_No=" + Acct_No + "]";
	}

}
