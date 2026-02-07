package com.ftk.pg.requestvo;

public class CashfreeRequestPayment {

	private String order_token;
	private CashfreeRequestPaymentWrapper payment_method;
	public String getOrder_token() {
		return order_token;
	}
	public void setOrder_token(String order_token) {
		this.order_token = order_token;
	}
	public CashfreeRequestPaymentWrapper getPayment_method() {
		return payment_method;
	}
	public void setPayment_method(CashfreeRequestPaymentWrapper payment_method) {
		this.payment_method = payment_method;
	}

	
	
	
}
