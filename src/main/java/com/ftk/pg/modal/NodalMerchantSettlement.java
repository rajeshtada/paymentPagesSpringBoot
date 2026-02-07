package com.ftk.pg.modal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "nodal_merchant_settlement")
@Entity
public class NodalMerchantSettlement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7948069818368386229L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(name = "mid")
	private Long mid;

	@Column(name = "vpa")
	private String vpa;

	@Column(name = "settlement_date")
	private Date settlementDate;

	@Column(name = "settlement_part")
	private String settlementPart;

	@Column(name = "settlement_amount")
	private BigDecimal settlementAmount;

	@Column(name = "status", columnDefinition = "INT default 0")
	private int status;

	@Column(name = "settlement_remarks")
	private String remarks;

	@Column(name = "processor_id")
	private Long processorId;

	@Column(name = "processor_name")
	private String processorName;

	@Column(name = "created_date", columnDefinition = "DATETIME default CURRENT_TIMESTAMP")
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "updated_date", columnDefinition = "DATETIME default CURRENT_TIMESTAMP")
	@UpdateTimestamp
	private LocalDateTime updatedDate;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "account_no")
	private String accountNo;

	@Column(name = "account_name")
	private String accountName;

	@Column(name = "ifsc_code")
	private String ifscCode;

	@Column(name = "utr")
	private String utr;

	@Column(name = "status_text")
	private String statusText;

	@Column(name = "settlement_mode")
	private String settlementMode;

	@Column(name = "file_id")
	private Long fileId;

	@Column(name = "settlement_history_Id", columnDefinition = "bigint default 0")
	private Long settlementHistoryId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public String getVpa() {
		return vpa;
	}

	public void setVpa(String vpa) {
		this.vpa = vpa;
	}

	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	public String getSettlementPart() {
		return settlementPart;
	}

	public void setSettlementPart(String settlementPart) {
		this.settlementPart = settlementPart;
	}

	public BigDecimal getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(BigDecimal settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getProcessorId() {
		return processorId;
	}

	public void setProcessorId(Long processorId) {
		this.processorId = processorId;
	}

	public String getProcessorName() {
		return processorName;
	}

	public void setProcessorName(String processorName) {
		this.processorName = processorName;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getUtr() {
		return utr;
	}

	public void setUtr(String utr) {
		this.utr = utr;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public String getSettlementMode() {
		return settlementMode;
	}

	public void setSettlementMode(String settlementMode) {
		this.settlementMode = settlementMode;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public Long getSettlementHistoryId() {
		return settlementHistoryId;
	}

	public void setSettlementHistoryId(Long settlementHistoryId) {
		this.settlementHistoryId = settlementHistoryId;
	}

	@Override
	public String toString() {
		return "NodalMerchantSettlement [id=" + id + ", mid=" + mid + ", vpa=" + vpa + ", settlementDate="
				+ settlementDate + ", settlementPart=" + settlementPart + ", settlementAmount=" + settlementAmount
				+ ", status=" + status + ", remarks=" + remarks + ", processorId=" + processorId + ", processorName="
				+ processorName + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", createdBy="
				+ createdBy + ", accountNo=" + accountNo + ", accountName=" + accountName + ", ifscCode=" + ifscCode
				+ ", utr=" + utr + ", statusText=" + statusText + ", settlementMode=" + settlementMode + ", fileId="
				+ fileId + ", settlementHistoryId=" + settlementHistoryId + "]";
	}

}
