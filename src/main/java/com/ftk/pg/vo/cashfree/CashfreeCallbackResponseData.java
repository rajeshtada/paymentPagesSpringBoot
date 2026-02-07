package com.ftk.pg.vo.cashfree;

public class CashfreeCallbackResponseData {
	
	private CashfreeCallbackResponseOrder order;
	private CashfreeCallbackResponsePayment payment;
	
	public CashfreeCallbackResponseOrder getOrder() {
		return order;
	}
	public void setOrder(CashfreeCallbackResponseOrder order) {
		this.order = order;
	}
	public CashfreeCallbackResponsePayment getPayment() {
		return payment;
	}
	public void setPayment(CashfreeCallbackResponsePayment payment) {
		this.payment = payment;
	}
	
	
	
}
