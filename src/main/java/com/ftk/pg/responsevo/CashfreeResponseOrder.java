package com.ftk.pg.responsevo;

import com.ftk.pg.requestvo.CashfreeRequestCustomer;
import com.ftk.pg.requestvo.CashfreeRequestOrderMeta;

public class CashfreeResponseOrder {

	private String cf_order_id;
	private String order_id;
	private String entity;
	private String order_currency;
	private String order_amount;
	private String order_status;
	private String order_token;
	private String order_expiry_time;
	private String order_note;
	private String payment_link;
	private CashfreeRequestCustomer customer_details;
	private CashfreeRequestOrderMeta order_meta;
	
	private CashfreeResponsePayments payments;
	private CashfreeResponseRefunds refunds;
	private CashfreeResponseSettlements settlements;
	
	
	public String getCf_order_id() {
		return cf_order_id;
	}
	public void setCf_order_id(String cf_order_id) {
		this.cf_order_id = cf_order_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String getOrder_currency() {
		return order_currency;
	}
	public void setOrder_currency(String order_currency) {
		this.order_currency = order_currency;
	}
	public String getOrder_amount() {
		return order_amount;
	}
	public void setOrder_amount(String order_amount) {
		this.order_amount = order_amount;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public String getOrder_token() {
		return order_token;
	}
	public void setOrder_token(String order_token) {
		this.order_token = order_token;
	}
	public String getOrder_expiry_time() {
		return order_expiry_time;
	}
	public void setOrder_expiry_time(String order_expiry_time) {
		this.order_expiry_time = order_expiry_time;
	}
	public String getOrder_note() {
		return order_note;
	}
	public void setOrder_note(String order_note) {
		this.order_note = order_note;
	}
	public String getPayment_link() {
		return payment_link;
	}
	public void setPayment_link(String payment_link) {
		this.payment_link = payment_link;
	}
	public CashfreeRequestCustomer getCustomer_details() {
		return customer_details;
	}
	public void setCustomer_details(CashfreeRequestCustomer customer_details) {
		this.customer_details = customer_details;
	}
	public CashfreeRequestOrderMeta getOrder_meta() {
		return order_meta;
	}
	public void setOrder_meta(CashfreeRequestOrderMeta order_meta) {
		this.order_meta = order_meta;
	}
	public CashfreeResponsePayments getPayments() {
		return payments;
	}
	public void setPayments(CashfreeResponsePayments payments) {
		this.payments = payments;
	}
	public CashfreeResponseRefunds getRefunds() {
		return refunds;
	}
	public void setRefunds(CashfreeResponseRefunds refunds) {
		this.refunds = refunds;
	}
	public CashfreeResponseSettlements getSettlements() {
		return settlements;
	}
	public void setSettlements(CashfreeResponseSettlements settlements) {
		this.settlements = settlements;
	}
	
	
	
	
}
