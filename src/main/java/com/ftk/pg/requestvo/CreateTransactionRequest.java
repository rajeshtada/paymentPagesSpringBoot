package com.ftk.pg.requestvo;

public class CreateTransactionRequest {
	private String orderid;
	private String mercid;
	private String amount;
	private String currency;
	private String authentication_type;
	private String _3ds_parameter;
	private String bankid;
	private String ru;
	private String itemcode;
	private String payment_method_type;
	private String txn_process_type;
	private String settlement_lob;
	private String customer_refid;
	private Device device;
	private Customer customer;
	private Card card;
	private Upi upi;
	private CardHolderDetails cardholder_details;
	private Additional_Info additional_info;

	public Additional_Info getAdditionalInfo() {
		return additional_info;
	}

	public void setAdditionalInfo(Additional_Info additional_info) {
		this.additional_info = additional_info;
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

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getAuthentication_type() {
		return authentication_type;
	}

	public void setAuthentication_type(String authentication_type) {
		this.authentication_type = authentication_type;
	}

	public String get_3ds_parameter() {
		return _3ds_parameter;
	}

	public void set_3ds_parameter(String _3ds_parameter) {
		this._3ds_parameter = _3ds_parameter;
	}

	public String getBankid() {
		return bankid;
	}

	public void setBankid(String bankid) {
		this.bankid = bankid;
	}

	public String getRu() {
		return ru;
	}

	public void setRu(String ru) {
		this.ru = ru;
	}

	

	public String getItemcode() {
		return itemcode;
	}

	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}

	public String getPayment_method_type() {
		return payment_method_type;
	}

	public void setPayment_method_type(String payment_method_type) {
		this.payment_method_type = payment_method_type;
	}

	public String getTxn_process_type() {
		return txn_process_type;
	}

	public void setTxn_process_type(String txn_process_type) {
		this.txn_process_type = txn_process_type;
	}

	public String getSettlement_lob() {
		return settlement_lob;
	}

	public void setSettlement_lob(String settlement_lob) {
		this.settlement_lob = settlement_lob;
	}

	public String getCustomer_refid() {
		return customer_refid;
	}

	public void setCustomer_refid(String customer_refid) {
		this.customer_refid = customer_refid;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public Upi getUpi() {
		return upi;
	}

	public void setUpi(Upi upi) {
		this.upi = upi;
	}

	public CardHolderDetails getCardholder_details() {
		return cardholder_details;
	}

	public void setCardholder_details(CardHolderDetails cardholder_details) {
		this.cardholder_details = cardholder_details;
	}

	@Override
	public String toString() {
		return "CreateTransactionRequest [orderid=" + orderid + ", mercid=" + mercid + ", amount=" + amount
				+ ", currency=" + currency + ", authentication_type=" + authentication_type + ", _3ds_parameter="
				+ _3ds_parameter + ", bankid=" + bankid + ", ru=" + ru + ", itemcode=" + itemcode
				+ ", payment_method_type=" + payment_method_type + ", txn_process_type=" + txn_process_type
				+ ", settlement_lob=" + settlement_lob + ", customer_refid=" + customer_refid + ", device=" + device
				+ ", customer=" + customer + ", card=" + card + ", upi=" + upi + ", cardholder_details="
				+ cardholder_details + ", additional_info=" + additional_info + "]";
	}


}
