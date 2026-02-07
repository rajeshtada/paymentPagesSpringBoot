package com.ftk.pg.responsevo;

import java.util.List;
import java.util.Map;


public class CreateTransactionResponse {
    private String objectid;
    private String transactionid;
    private String orderid;
    private String mercid;
    private String transaction_date;
    private String amount;
    private String surcharge;
    private String discount;
    private String charge_amount;
    private String currency;
    private Map<String, String> additional_info;
    private String ru;
    private String txn_process_type;
    private String bankid;
    private String itemcode;
    private String auth_status;
    private String transaction_error_code;
    private String transaction_error_desc;
    private String transaction_error_type;
    private String payment_method_type;
    private String next_step;
    private List<Link> links;
    private String payment_category;
	public String getObjectid() {
		return objectid;
	}
	public void setObjectid(String objectid) {
		this.objectid = objectid;
	}
	public String getTransactionid() {
		return transactionid;
	}
	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getMercid() {
		return mercid;
	}
	public void setMercid(String mercid) {
		this.mercid = mercid;
	}
	public String getTransaction_date() {
		return transaction_date;
	}
	public void setTransaction_date(String transaction_date) {
		this.transaction_date = transaction_date;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getSurcharge() {
		return surcharge;
	}
	public void setSurcharge(String surcharge) {
		this.surcharge = surcharge;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getCharge_amount() {
		return charge_amount;
	}
	public void setCharge_amount(String charge_amount) {
		this.charge_amount = charge_amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Map<String, String> getAdditional_info() {
		return additional_info;
	}
	public void setAdditional_info(Map<String, String> additional_info) {
		this.additional_info = additional_info;
	}
	public String getRu() {
		return ru;
	}
	public void setRu(String ru) {
		this.ru = ru;
	}
	public String getTxn_process_type() {
		return txn_process_type;
	}
	public void setTxn_process_type(String txn_process_type) {
		this.txn_process_type = txn_process_type;
	}
	public String getBankid() {
		return bankid;
	}
	public void setBankid(String bankid) {
		this.bankid = bankid;
	}
	public String getItemcode() {
		return itemcode;
	}
	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}
	public String getAuth_status() {
		return auth_status;
	}
	public void setAuth_status(String auth_status) {
		this.auth_status = auth_status;
	}
	public String getTransaction_error_code() {
		return transaction_error_code;
	}
	public void setTransaction_error_code(String transaction_error_code) {
		this.transaction_error_code = transaction_error_code;
	}
	public String getTransaction_error_desc() {
		return transaction_error_desc;
	}
	public void setTransaction_error_desc(String transaction_error_desc) {
		this.transaction_error_desc = transaction_error_desc;
	}
	public String getTransaction_error_type() {
		return transaction_error_type;
	}
	public void setTransaction_error_type(String transaction_error_type) {
		this.transaction_error_type = transaction_error_type;
	}
	public String getPayment_method_type() {
		return payment_method_type;
	}
	public void setPayment_method_type(String payment_method_type) {
		this.payment_method_type = payment_method_type;
	}
	public String getNext_step() {
		return next_step;
	}
	public void setNext_step(String next_step) {
		this.next_step = next_step;
	}
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	public String getPayment_category() {
		return payment_category;
	}
	public void setPayment_category(String payment_category) {
		this.payment_category = payment_category;
	}
	@Override
	public String toString() {
		return "CreateTransactionResponse [objectid=" + objectid + ", transactionid=" + transactionid + ", orderid="
				+ orderid + ", mercid=" + mercid + ", transaction_date=" + transaction_date + ", amount=" + amount
				+ ", surcharge=" + surcharge + ", discount=" + discount + ", charge_amount=" + charge_amount
				+ ", currency=" + currency + ", additional_info=" + additional_info + ", ru=" + ru
				+ ", txn_process_type=" + txn_process_type + ", bankid=" + bankid + ", itemcode=" + itemcode
				+ ", auth_status=" + auth_status + ", transaction_error_code=" + transaction_error_code
				+ ", transaction_error_desc=" + transaction_error_desc + ", transaction_error_type="
				+ transaction_error_type + ", payment_method_type=" + payment_method_type + ", next_step=" + next_step
				+ ", links=" + links + ", payment_category=" + payment_category + "]";
	}




}
	


