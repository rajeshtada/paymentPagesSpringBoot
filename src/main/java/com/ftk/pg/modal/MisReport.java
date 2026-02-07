package com.ftk.pg.modal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mis_report")
public class MisReport implements Serializable {

	private static final long serialVersionUID = 1503919125711033360L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "carry_privious")
	private BigDecimal carryPrivious;

	@Column(name = "bank_file_upload")
	private BigDecimal bankFileUpload;

	@Column(name = "refund_upload")
	private BigDecimal refundUpload;

	@Column(name = "reversal_upload_cr")
	private BigDecimal reversalUploadCr;

	@Column(name = "reversal_upload_dr")
	private BigDecimal reversalUploadDr;

	@Column(name = "refund_charge")
	private BigDecimal refundCharge;

	@Column(name = "charge_back_charges")
	private BigDecimal chargeBackCharges;

	@Column(name = "recovery_charge")
	private BigDecimal recoveryCharge;

	@Column(name = "hold_txn")
	private BigDecimal holdTxn;

	@Column(name = "error_txn")
	private BigDecimal errorTxn;

	@Column(name = "batch1_eligible_cr")
	private BigDecimal batch1EligibleCr;
	@Column(name = "batch1_eligible_dr")
	private BigDecimal batch1EligibleDr;
	
	@Column(name = "batch2_eligible_cr")
	private BigDecimal batch2EligibleCr;
	@Column(name = "batch2_eligible_dr")
	private BigDecimal batch2EligibleDr;

	@Column(name = "batch3_eligible_cr")
	private BigDecimal batch3EligibleCr;
	@Column(name = "batch3_eligible_dr")
	private BigDecimal batch3EligibleDr;

	@Column(name = "batch4_eligible_cr")
	private BigDecimal batch4EligibleCr;
	@Column(name = "batch4_eligible_dr")
	private BigDecimal batch4EligibleDr;

	@Column(name = "batch5_eligible_cr")
	private BigDecimal batch5EligibleCr;
	@Column(name = "batch5_eligible_dr")
	private BigDecimal batch5EligibleDr;

	@Column(name = "batch6_eligible_cr")
	private BigDecimal batch6EligibleCr;
	@Column(name = "batch6_eligible_dr")
	private BigDecimal batch6EligibleDr;
	
	@Column(name = "file_id")
	private Long fileId;
	
	@Column(name = "settlement_date")
	private Date settlementDate;

	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "file_type")
	private String fileType;
	
	@Column(name = "processor")
	private String processor;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getCarryPrivious() {
		return carryPrivious;
	}
	public void setCarryPrivious(BigDecimal carryPrivious) {
		this.carryPrivious = carryPrivious;
	}
	public BigDecimal getBankFileUpload() {
		return bankFileUpload;
	}
	public void setBankFileUpload(BigDecimal bankFileUpload) {
		this.bankFileUpload = bankFileUpload;
	}
	public BigDecimal getRefundUpload() {
		return refundUpload;
	}
	public void setRefundUpload(BigDecimal refundUpload) {
		this.refundUpload = refundUpload;
	}
	public BigDecimal getReversalUploadCr() {
		return reversalUploadCr;
	}
	public void setReversalUploadCr(BigDecimal reversalUploadCr) {
		this.reversalUploadCr = reversalUploadCr;
	}
	public BigDecimal getReversalUploadDr() {
		return reversalUploadDr;
	}
	public void setReversalUploadDr(BigDecimal reversalUploadDr) {
		this.reversalUploadDr = reversalUploadDr;
	}
	public BigDecimal getRefundCharge() {
		return refundCharge;
	}
	public void setRefundCharge(BigDecimal refundCharge) {
		this.refundCharge = refundCharge;
	}
	public BigDecimal getChargeBackCharges() {
		return chargeBackCharges;
	}
	public void setChargeBackCharges(BigDecimal chargeBackCharges) {
		this.chargeBackCharges = chargeBackCharges;
	}
	public BigDecimal getRecoveryCharge() {
		return recoveryCharge;
	}
	public void setRecoveryCharge(BigDecimal recoveryCharge) {
		this.recoveryCharge = recoveryCharge;
	}
	public BigDecimal getHoldTxn() {
		return holdTxn;
	}
	public void setHoldTxn(BigDecimal holdTxn) {
		this.holdTxn = holdTxn;
	}
	public BigDecimal getErrorTxn() {
		return errorTxn;
	}
	public void setErrorTxn(BigDecimal errorTxn) {
		this.errorTxn = errorTxn;
	}
	public BigDecimal getBatch1EligibleCr() {
		return batch1EligibleCr;
	}
	public void setBatch1EligibleCr(BigDecimal batch1EligibleCr) {
		this.batch1EligibleCr = batch1EligibleCr;
	}
	public BigDecimal getBatch1EligibleDr() {
		return batch1EligibleDr;
	}
	public void setBatch1EligibleDr(BigDecimal batch1EligibleDr) {
		this.batch1EligibleDr = batch1EligibleDr;
	}
	public BigDecimal getBatch2EligibleCr() {
		return batch2EligibleCr;
	}
	public void setBatch2EligibleCr(BigDecimal batch2EligibleCr) {
		this.batch2EligibleCr = batch2EligibleCr;
	}
	public BigDecimal getBatch2EligibleDr() {
		return batch2EligibleDr;
	}
	public void setBatch2EligibleDr(BigDecimal batch2EligibleDr) {
		this.batch2EligibleDr = batch2EligibleDr;
	}
	public BigDecimal getBatch3EligibleCr() {
		return batch3EligibleCr;
	}
	public void setBatch3EligibleCr(BigDecimal batch3EligibleCr) {
		this.batch3EligibleCr = batch3EligibleCr;
	}
	public BigDecimal getBatch3EligibleDr() {
		return batch3EligibleDr;
	}
	public void setBatch3EligibleDr(BigDecimal batch3EligibleDr) {
		this.batch3EligibleDr = batch3EligibleDr;
	}
	public BigDecimal getBatch4EligibleCr() {
		return batch4EligibleCr;
	}
	public void setBatch4EligibleCr(BigDecimal batch4EligibleCr) {
		this.batch4EligibleCr = batch4EligibleCr;
	}
	public BigDecimal getBatch4EligibleDr() {
		return batch4EligibleDr;
	}
	public void setBatch4EligibleDr(BigDecimal batch4EligibleDr) {
		this.batch4EligibleDr = batch4EligibleDr;
	}
	public BigDecimal getBatch5EligibleCr() {
		return batch5EligibleCr;
	}
	public void setBatch5EligibleCr(BigDecimal batch5EligibleCr) {
		this.batch5EligibleCr = batch5EligibleCr;
	}
	public BigDecimal getBatch5EligibleDr() {
		return batch5EligibleDr;
	}
	public void setBatch5EligibleDr(BigDecimal batch5EligibleDr) {
		this.batch5EligibleDr = batch5EligibleDr;
	}
	public BigDecimal getBatch6EligibleCr() {
		return batch6EligibleCr;
	}
	public void setBatch6EligibleCr(BigDecimal batch6EligibleCr) {
		this.batch6EligibleCr = batch6EligibleCr;
	}
	public BigDecimal getBatch6EligibleDr() {
		return batch6EligibleDr;
	}
	public void setBatch6EligibleDr(BigDecimal batch6EligibleDr) {
		this.batch6EligibleDr = batch6EligibleDr;
	}
	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	public Date getSettlementDate() {
		return settlementDate;
	}
	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getProcessor() {
		return processor;
	}
	public void setProcessor(String processor) {
		this.processor = processor;
	}
	
	@Override
	public String toString() {
		return "MisReport [id=" + id + ", carryPrivious=" + carryPrivious + ", bankFileUpload=" + bankFileUpload
				+ ", refundUpload=" + refundUpload + ", reversalUploadCr=" + reversalUploadCr + ", reversalUploadDr="
				+ reversalUploadDr + ", refundCharge=" + refundCharge + ", chargeBackCharges=" + chargeBackCharges
				+ ", recoveryCharge=" + recoveryCharge + ", holdTxn=" + holdTxn + ", errorTxn=" + errorTxn
				+ ", batch1EligibleCr=" + batch1EligibleCr + ", batch1EligibleDr=" + batch1EligibleDr
				+ ", batch2EligibleCr=" + batch2EligibleCr + ", batch2EligibleDr=" + batch2EligibleDr
				+ ", batch3EligibleCr=" + batch3EligibleCr + ", batch3EligibleDr=" + batch3EligibleDr
				+ ", batch4EligibleCr=" + batch4EligibleCr + ", batch4EligibleDr=" + batch4EligibleDr
				+ ", batch5EligibleCr=" + batch5EligibleCr + ", batch5EligibleDr=" + batch5EligibleDr
				+ ", batch6EligibleCr=" + batch6EligibleCr + ", batch6EligibleDr=" + batch6EligibleDr + ", fileId="
				+ fileId + ", settlementDate=" + settlementDate + ", createdDate=" + createdDate + ", fileType="
				+ fileType + ", processor=" + processor + "]";
	}

	
	
}
