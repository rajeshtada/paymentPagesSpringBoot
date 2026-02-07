package com.ftk.pg.requestvo;
import java.util.HashMap;
import java.util.Map;

public class CashfreeRequestOrder {
	

	
	public CashfreeRequestOrder() {
		this.order_tags = new HashMap<String, String>();
	}

	private String order_id;
	private double order_amount;
	private String order_currency;
	private CashfreeRequestCustomer customer_details;
	private CashfreeRequestOrderMeta order_meta;
	//private String order_expiry_time;
	private String order_note;
	private Map<String, String> order_tags;
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public double getOrder_amount() {
		return order_amount;
	}
	public void setOrder_amount(double order_amount) {
		this.order_amount = order_amount;
	}
	public String getOrder_currency() {
		return order_currency;
	}
	public void setOrder_currency(String order_currency) {
		this.order_currency = order_currency;
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

	public String getOrder_note() {
		return order_note;
	}
	public void setOrder_note(String order_note) {
		this.order_note = order_note;
	}
	public Map<String, String> getOrder_tags() {
		return order_tags;
	}
	public void setOrder_tags(Map<String, String> order_tags) {
		this.order_tags = order_tags;
	}
	
	
}
