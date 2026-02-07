package com.ftk.pg.modal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "convenience_charges")
public class ConvenienceCharges implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4672738088776515561L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	private Long mid;

	@Column(name = "bank_id")
	private Long bankId;

	@Column(name = "payment_mode")
	private String paymentMode;

	@Column(name = "card_type")
	private String cardType;

	@Column(name = "charges_type")
	private String chargesType;

	@Column(name = "commission_type")
	private String commissionType;

	@Column(name = "charges_amt")
	private Double chargesAmt;

	@Column(name = "fixed_varible")
	private Double fixedVarible;

	@Column(name = "created_by", updatable = false)
	private String createdBy;

	@Column(name = "modify_by")
	private String modifyBy;

	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "modify_date")
	@UpdateTimestamp
	private LocalDateTime modifyDate;

	@Column(name = "status", nullable = false, columnDefinition = "int default 0")
	private int status;

	@Column(name="from_amount")
    private BigDecimal fromAmount;
    @Column(name="to_amount")
    private BigDecimal toAmount;

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getChargesType() {
		return chargesType;
	}

	public void setChargesType(String chargesType) {
		this.chargesType = chargesType;
	}

	public String getCommissionType() {
		return commissionType;
	}

	public void setCommissionType(String commissionType) {
		this.commissionType = commissionType;
	}

	public Double getChargesAmt() {
		return chargesAmt;
	}

	public void setChargesAmt(Double chargesAmt) {
		this.chargesAmt = chargesAmt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(LocalDateTime modifyDate) {
		this.modifyDate = modifyDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ConvenienceCharges [id=" + id + ", mid=" + mid + ", bankId=" + bankId + ", paymentMode=" + paymentMode
				+ ", cardType=" + cardType + ", chargesType=" + chargesType + ", commissionType=" + commissionType
				+ ", chargesAmt=" + chargesAmt + ", fixedVarible=" + fixedVarible + ", createdBy=" + createdBy
				+ ", modifyBy=" + modifyBy + ", createdDate=" + createdDate + ", modifyDate=" + modifyDate + ", status="
				+ status + ", fromAmount=" + fromAmount + ", toAmount=" + toAmount + "]";
	}

	public Double getFixedVarible() {
		return fixedVarible;
	}

	public void setFixedVarible(Double fixedVarible) {
		this.fixedVarible = fixedVarible;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getFromAmount() {
		return fromAmount;
	}

	public void setFromAmount(BigDecimal fromAmount) {
		this.fromAmount = fromAmount;
	}

	public BigDecimal getToAmount() {
		return toAmount;
	}

	public void setToAmount(BigDecimal toAmount) {
		this.toAmount = toAmount;
	}
	
	

}
