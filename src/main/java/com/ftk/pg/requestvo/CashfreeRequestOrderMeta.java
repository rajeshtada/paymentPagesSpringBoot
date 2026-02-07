package com.ftk.pg.requestvo;

public class CashfreeRequestOrderMeta {

	private String return_url;
	private String notify_url;
	private String payment_methods;
	
	public String getReturn_url() {
		return return_url;
	}
	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getPayment_methods() {
		return payment_methods;
	}
	public void setPayment_methods(String payment_methods) {
		this.payment_methods = payment_methods;
	}
	
	
}
