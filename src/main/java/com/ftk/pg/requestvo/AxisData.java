package com.ftk.pg.requestvo;

public class AxisData {

	private String encdata;
	private String payeeid;
	private String enccat;
	private String mercat;

	public String getEncdata() {
		return encdata;
	}

	public void setEncdata(String encdata) {
		this.encdata = encdata;
	}

	public String getPayeeid() {
		return payeeid;
	}

	public void setPayeeid(String payeeid) {
		this.payeeid = payeeid;
	}

	public String getEnccat() {
		return enccat;
	}

	public void setEnccat(String enccat) {
		this.enccat = enccat;
	}

	public String getMercat() {
		return mercat;
	}

	public void setMercat(String mercat) {
		this.mercat = mercat;
	}

	@Override
	public String toString() {
		return "AxisData [encdata=" + encdata + ", payeeid=" + payeeid + ", enccat=" + enccat + ", mercat=" + mercat
				+ "]";
	}

}
