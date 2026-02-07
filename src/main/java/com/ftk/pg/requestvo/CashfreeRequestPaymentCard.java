package com.ftk.pg.requestvo;

public class CashfreeRequestPaymentCard extends CashfreeRequestPaymentBase {
	private String card_number;
	private String card_holder_name;
	private String card_expiry_mm;
	private String card_expiry_yy;
	private String card_cvv;
	private String card_alias;
	private String card_bank_name;
	private String emi_tenure;
	public String getCard_number() {
		return card_number;
	}
	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}
	public String getCard_holder_name() {
		return card_holder_name;
	}
	public void setCard_holder_name(String card_holder_name) {
		this.card_holder_name = card_holder_name;
	}
	public String getCard_expiry_mm() {
		return card_expiry_mm;
	}
	public void setCard_expiry_mm(String card_expiry_mm) {
		this.card_expiry_mm = card_expiry_mm;
	}
	public String getCard_expiry_yy() {
		return card_expiry_yy;
	}
	public void setCard_expiry_yy(String card_expiry_yy) {
		this.card_expiry_yy = card_expiry_yy;
	}
	public String getCard_cvv() {
		return card_cvv;
	}
	public void setCard_cvv(String card_cvv) {
		this.card_cvv = card_cvv;
	}
	public String getCard_alias() {
		return card_alias;
	}
	public void setCard_alias(String card_alias) {
		this.card_alias = card_alias;
	}
	public String getCard_bank_name() {
		return card_bank_name;
	}
	public void setCard_bank_name(String card_bank_name) {
		this.card_bank_name = card_bank_name;
	}
	public String getEmi_tenure() {
		return emi_tenure;
	}
	public void setEmi_tenure(String emi_tenure) {
		this.emi_tenure = emi_tenure;
	}

	
	
}
