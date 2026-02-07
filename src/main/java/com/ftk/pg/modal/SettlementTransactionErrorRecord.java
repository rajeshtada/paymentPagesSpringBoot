package com.ftk.pg.modal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "settlement_transaction_error_record")
@Entity
public class SettlementTransactionErrorRecord implements Serializable {

	private static final long serialVersionUID = 2037494244888540739L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "mid")
	private Long mid;
	
	@Column(name = "transaction_id")
	private Long transactionId;

	@Column(name = "file_id")
	private Long fileId;

	@Column(name = "settlement_date")
	private LocalDateTime settlementDate;
	
	@Column(name = "updated_By")
	private String updatedBy;

	@Column(name = "updated_date")
	private LocalDateTime updatedDate;
	
	@Column(name = "settlement_status")
	private int settlementStatus;
	
	@Column(name = "settlement_part")
	private String settlementPart;
	
	@Column(name = "next_settlement_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime nextSettlementDate;

	@Column(name = "reason")
	private String reason;

	@Column(name = "next_settlement_part")
	private String nextSettlementPart;
	
	@Column(name = "vpa")
	private String vpa;
	
	@Column(name = "amount")
	private BigDecimal amount;
	
	@Column(name = "merchant_name")
	private String merchantName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public LocalDateTime getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(LocalDateTime settlementDate) {
		this.settlementDate = settlementDate;
	}

	public int getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(int settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	public String getSettlementPart() {
		return settlementPart;
	}

	public void setSettlementPart(String settlementPart) {
		this.settlementPart = settlementPart;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "SettlementTransactionErrorRecord [id=" + id + ", mid=" + mid + ", transactionId=" + transactionId
				+ ", fileId=" + fileId + ", settlementDate=" + settlementDate + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + ", settlementStatus=" + settlementStatus + ", settlementPart="
				+ settlementPart + ", nextSettlementDate=" + nextSettlementDate + ", reason=" + reason
				+ ", nextSettlementPart=" + nextSettlementPart + ", vpa=" + vpa + ", amount=" + amount
				+ ", merchantName=" + merchantName + "]";
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public LocalDateTime getNextSettlementDate() {
		return nextSettlementDate;
	}

	public void setNextSettlementDate(LocalDateTime nextSettlementDate) {
		this.nextSettlementDate = nextSettlementDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getNextSettlementPart() {
		return nextSettlementPart;
	}

	public void setNextSettlementPart(String nextSettlementPart) {
		this.nextSettlementPart = nextSettlementPart;
	}

	public String getVpa() {
		return vpa;
	}

	public void setVpa(String vpa) {
		this.vpa = vpa;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}
}
