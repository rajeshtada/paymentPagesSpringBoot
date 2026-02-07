package com.ftk.pg.requestvo;
public class NorthAcross_RefundStatus_Request {
 
	private String api_key;
	private String transaction_id;
	private String merchant_refund_id;
	private String hash;
	public String getApi_key() {
		return api_key;
	}
	public void setApi_key(String api_key) {
		this.api_key = api_key;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getMerchant_refund_id() {
		return merchant_refund_id;
	}
	public void setMerchant_refund_id(String merchant_refund_id) {
		this.merchant_refund_id = merchant_refund_id;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	@Override
	public String toString() {
		return "IDFC_RefundStatus_Request [api_key=" + api_key + ", transaction_id=" + transaction_id
				+ ", merchant_refund_id=" + merchant_refund_id + ", hash=" + hash + "]";
	}
	
	
}
