package com.ftk.pg.responsevo;


public class Data {
	 private String transaction_id;
	    private String bank_code;
	    private String payment_mode;
	    private String payment_datetime;
	    private String response_code;
	    private String response_message;
	    private String order_id;
	    private String amount;
	    private String amount_orig;
	    private String tdr_amount;
	    private String tax_on_tdr_amount;
	    private String description;
	    private String error_desc;
	    private String customer_phone;
	    private String customer_name;
	    private String customer_email;
	    private String bank_transaction_id;
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
		public String getPayment_mode() {
			return payment_mode;
		}
		public void setPayment_mode(String payment_mode) {
			this.payment_mode = payment_mode;
		}
		public String getPayment_datetime() {
			return payment_datetime;
		}
		public void setPayment_datetime(String payment_datetime) {
			this.payment_datetime = payment_datetime;
		}
		public String getResponse_code() {
			return response_code;
		}
		public void setResponse_code(String response_code) {
			this.response_code = response_code;
		}
		public String getResponse_message() {
			return response_message;
		}
		public void setResponse_message(String response_message) {
			this.response_message = response_message;
		}
		public String getOrder_id() {
			return order_id;
		}
		public void setOrder_id(String order_id) {
			this.order_id = order_id;
		}
		public String getAmount() {
			return amount;
		}
		public void setAmount(String amount) {
			this.amount = amount;
		}
		public String getAmount_orig() {
			return amount_orig;
		}
		public void setAmount_orig(String amount_orig) {
			this.amount_orig = amount_orig;
		}
		public String getTdr_amount() {
			return tdr_amount;
		}
		public void setTdr_amount(String tdr_amount) {
			this.tdr_amount = tdr_amount;
		}
		public String getTax_on_tdr_amount() {
			return tax_on_tdr_amount;
		}
		public void setTax_on_tdr_amount(String tax_on_tdr_amount) {
			this.tax_on_tdr_amount = tax_on_tdr_amount;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getError_desc() {
			return error_desc;
		}
		public void setError_desc(String error_desc) {
			this.error_desc = error_desc;
		}
		public String getCustomer_phone() {
			return customer_phone;
		}
		public void setCustomer_phone(String customer_phone) {
			this.customer_phone = customer_phone;
		}
		public String getCustomer_name() {
			return customer_name;
		}
		public void setCustomer_name(String customer_name) {
			this.customer_name = customer_name;
		}
		public String getCustomer_email() {
			return customer_email;
		}
		public void setCustomer_email(String customer_email) {
			this.customer_email = customer_email;
		}
		public String getBank_transaction_id() {
			return bank_transaction_id;
		}
		public void setBank_transaction_id(String bank_transaction_id) {
			this.bank_transaction_id = bank_transaction_id;
		}
		@Override
		public String toString() {
			return "Data [transaction_id=" + transaction_id + ", bank_code=" + bank_code + ", payment_mode="
					+ payment_mode + ", payment_datetime=" + payment_datetime + ", response_code=" + response_code
					+ ", response_message=" + response_message + ", order_id=" + order_id + ", amount=" + amount
					+ ", amount_orig=" + amount_orig + ", tdr_amount=" + tdr_amount + ", tax_on_tdr_amount="
					+ tax_on_tdr_amount + ", description=" + description + ", error_desc=" + error_desc
					+ ", customer_phone=" + customer_phone + ", customer_name=" + customer_name + ", customer_email="
					+ customer_email + ", bank_transaction_id=" + bank_transaction_id + "]";
		}
	    

}