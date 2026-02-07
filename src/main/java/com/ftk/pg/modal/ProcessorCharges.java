package com.ftk.pg.modal;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "processor_charges")
public class ProcessorCharges implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "modified_date")
	@UpdateTimestamp
	private LocalDateTime modifiedDate;

	@Column(name = "mcc_code")
	private String mccCode;

	@Column(name = "processor")
	private String processor;

	@Column(name = "payment_mode")
	private String paymentMode;

	@Column(name = "from_amount")
	private Double fromAmount;

	@Column(name = "to_amount")
	private Double toAmount;

	@Column(name = "charge_type")
	private String chargeType;

	@Column(name = "charge_value")
	private Double chargeValue;

	@Column(name = "card_type")
	private String cardType;

	@Column(name = "card_category")
	private String cardCategory;

	@Column(name = "net_banking_id")
	private Long netBankingId;

	@Column(name = "status")
	@NotNull
	@ColumnDefault("'1'")
	private int status;

	@Column(name = "processor_id")
	private Long processorId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getMccCode() {
		return mccCode;
	}

	public void setMccCode(String mccCode) {
		this.mccCode = mccCode;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public Double getFromAmount() {
		return fromAmount;
	}

	public void setFromAmount(Double fromAmount) {
		this.fromAmount = fromAmount;
	}

	public Double getToAmount() {
		return toAmount;
	}

	public void setToAmount(Double toAmount) {
		this.toAmount = toAmount;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public Double getChargeValue() {
		return chargeValue;
	}

	public void setChargeValue(Double chargeValue) {
		this.chargeValue = chargeValue;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardCategory() {
		return cardCategory;
	}

	public void setCardCategory(String cardCategory) {
		this.cardCategory = cardCategory;
	}

	public Long getNetBankingId() {
		return netBankingId;
	}

	public void setNetBankingId(Long netBankingId) {
		this.netBankingId = netBankingId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getProcessorId() {
		return processorId;
	}

	public void setProcessorId(Long processorId) {
		this.processorId = processorId;
	}

	@Override
	public String toString() {
		return "ProcessorCharges [id=" + id + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate
				+ ", mccCode=" + mccCode + ", processor=" + processor + ", paymentMode=" + paymentMode + ", fromAmount="
				+ fromAmount + ", toAmount=" + toAmount + ", chargeType=" + chargeType + ", chargeValue=" + chargeValue
				+ ", cardType=" + cardType + ", cardCategory=" + cardCategory + ", netBankingId=" + netBankingId
				+ ", status=" + status + ", processorId=" + processorId + "]";
	}

}
