package com.ftk.pg.responsevo;

public class CashfreeResponsePayment {

	private int cf_payment_id;
	private String payment_method;
	private String channel;
	private String action;
	
	private CashfreeResponsePaymentData data;

	public int getCf_payment_id() {
		return cf_payment_id;
	}

	public void setCf_payment_id(int cf_payment_id) {
		this.cf_payment_id = cf_payment_id;
	}

	public String getPayment_method() {
		return payment_method;
	}

	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public CashfreeResponsePaymentData getData() {
		return data;
	}

	public void setData(CashfreeResponsePaymentData data) {
		this.data = data;
	}
	
	
	
}
