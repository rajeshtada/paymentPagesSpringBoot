package com.ftk.pg.requestvo;
import java.math.BigDecimal;

public class NorthAcrossPaymentStatusRequest {
    private String api_key;
    private String order_id;
    private String transaction_id;
    private String bank_code;
    private String response_code;
    private String customer_phone;
    private String customer_email;
    private String customer_name;
    private String date_from;
    private String date_to;
    private String hash;
	public String getApi_key() {
		return api_key;
	}
	public void setApi_key(String api_key) {
		this.api_key = api_key;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getBank_code() {
		return bank_code;
	}
	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}
	

	public String getResponse_code() {
		return response_code;
	}
	public void setResponse_code(String response_code) {
		this.response_code = response_code;
	}
	public String getCustomer_phone() {
		return customer_phone;
	}
	public void setCustomer_phone(String customer_phone) {
		this.customer_phone = customer_phone;
	}
	public String getCustomer_email() {
		return customer_email;
	}
	public void setCustomer_email(String customer_email) {
		this.customer_email = customer_email;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getDate_from() {
		return date_from;
	}
	public void setDate_from(String date_from) {
		this.date_from = date_from;
	}
	public String getDate_to() {
		return date_to;
	}
	public void setDate_to(String date_to) {
		this.date_to = date_to;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	@Override
	public String toString() {
		return "IDFCPaymentStatusRequest [api_key=" + api_key + ", order_id=" + order_id + ", transaction_id="
				+ transaction_id + ", bank_code=" + bank_code + ", response_code=" + response_code + ", customer_phone="
				+ customer_phone + ", customer_email=" + customer_email + ", customer_name=" + customer_name
				+ ", date_from=" + date_from + ", date_to=" + date_to + ", hash=" + hash + "]";
	}
    

}