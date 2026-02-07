package com.ftk.pg.modal;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "smart_routing")
public class SmartRouting implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "payment_mode")
	private String paymentMode;

	private boolean status;

	@Column(name = "bank_id")
	private int bankId;

	@Column(name = "processor_name")
	private String processor;

	@Column(name = "priority")
	private int priority;

	@Column(name = "bin")
	private int bin;

	@Column(name = "currency")
	private String currency;

	@Column(name = "card_type")
	private String cardType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getBankId() {
		return bankId;
	}

	public void setBankId(int bankId) {
		this.bankId = bankId;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getBin() {
		return bin;
	}

	public void setBin(int bin) {
		this.bin = bin;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	@Override
	public String toString() {
		return "SmartRouting [id=" + id + ", paymentMode=" + paymentMode + ", status=" + status + ", bankId=" + bankId
				+ ", processor=" + processor + ", priority=" + priority + ", bin=" + bin + ", currency=" + currency
				+ ", cardType=" + cardType + "]";
	}

}
