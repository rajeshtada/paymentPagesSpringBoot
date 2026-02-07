package com.ftk.pg.vo.axis;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Table1 {
	@XmlElement(name = "PAYEEID")
	private String payeeId;

	@XmlElement(name = "ITC")
	private String itc;

	@XmlElement(name = "PRN")
	private String prn;

	@XmlElement(name = "PaymentDate")
	private String paymentDate;

	@XmlElement(name = "Amount")
	private Double amount;

	@XmlElement(name = "BID")
	private String bid;

	@XmlElement(name = "PaymentStatus")
	private String paymentStatus;

	// Getters and Setters
	public String getPayeeId() {
		return payeeId;
	}

	public void setPayeeId(String payeeId) {
		this.payeeId = payeeId;
	}

	public String getItc() {
		return itc;
	}

	public void setItc(String itc) {
		this.itc = itc;
	}

	public String getPrn() {
		return prn;
	}

	public void setPrn(String prn) {
		this.prn = prn;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	@Override
	public String toString() {
		return "Table1{" + "payeeId='" + payeeId + '\'' + ", itc='" + itc + '\'' + ", prn='" + prn + '\''
				+ ", paymentDate='" + paymentDate + '\'' + ", amount=" + amount + ", bid='" + bid + '\''
				+ ", paymentStatus='" + paymentStatus + '\'' + '}';
	}
}