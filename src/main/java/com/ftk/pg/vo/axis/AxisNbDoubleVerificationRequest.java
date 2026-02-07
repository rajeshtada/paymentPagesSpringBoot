package com.ftk.pg.vo.axis;
public class AxisNbDoubleVerificationRequest {
	

	private String payeeid;
	private String prn;
	private String amt;
	private String itc;
	private String date;
	private String chksum;
	public String getPayeeid() {
		return payeeid;
	}
	public void setPayeeid(String payeeid) {
		this.payeeid = payeeid;
	}
	public String getPrn() {
		return prn;
	}
	public void setPrn(String prn) {
		this.prn = prn;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getItc() {
		return itc;
	}
	public void setItc(String itc) {
		this.itc = itc;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getChksum() {
		return chksum;
	}
	public void setChksum(String chksum) {
		this.chksum = chksum;
	}
	@Override
	public String toString() {
		return "AxisNbDoubleVerificationRequest [payeeid=" + payeeid + ", prn=" + prn + ", amt=" + amt + ", itc=" + itc
				+ ", date=" + date + ", chksum=" + chksum + "]";
	}
	
	public String checksumpipeSeprated() {
		return "payeeid="+payeeid+"|itc="+itc+"|prn="+prn+"|date="+date+"|amt="+amt;	
	}
	
	public String pipeSeprated() {
		return "payeeid="+payeeid+"|itc="+itc+"|prn="+prn+"|date="+date+"|amt="+amt+"|chksum="+chksum;
	}


}
