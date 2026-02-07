package com.ftk.pg.modal;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction_mis_detail")
public class TransactionMisDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "txn_id")
	private Long txnId;
	@Column(name = "srh_id")
	private Long srhId;
	@Column(name = "duplicate_txn")
	private String duplicateTxn;
	@Column(name = "hold_txn")
	private String holdTxn;
	@Column(name = "hold_reason")
	private String holdReason;
	@Column(name = "recovery_amount")
	private Double recoveryAmount;
	@Column(name = "recovery_reason")
	private String recoveryReason;
	@Column(name = "charge_back_amount")
	private Double chargeBackAmount;
	@Column(name = "refund_amount")
	private Double refundAmount;
	@Column(name = "refund_rrn")
	private String refundRrn;
	@Column(name = "file_id")
	private Long fileId;
	@Column(name = "release_status")
	private String releaseStatus;
	@Column(name = "release_remark")
	private String releaseRemark;
	@Column(name = "recon_date")
	private LocalDateTime reconDate;
	@Column(name = "utr_date")
	private LocalDateTime utrDate;
	private String status;
	private String vpa;
	private String rrn;
	private Long mid;
	@Column(name = "txn_type")
	private String txnType;


	public String getRefundRrn() {
		return refundRrn;
	}
	public void setRefundRrn(String refundRrn) {
		this.refundRrn = refundRrn;
	}
	public String getVpa() {
		return vpa;
	}
	public void setVpa(String vpa) {
		this.vpa = vpa;
	}
	public String getRrn() {
		return rrn;
	}
	public void setRrn(String rrn) {
		this.rrn = rrn;
	}
	public Long getMid() {
		return mid;
	}
	public void setMid(Long mid) {
		this.mid = mid;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public LocalDateTime getReconDate() {
		return reconDate;
	}
	public void setReconDate(LocalDateTime reconDate) {
		this.reconDate = reconDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTxnId() {
		return txnId;
	}
	public void setTxnId(Long txnId) {
		this.txnId = txnId;
	}
	public Long getSrhId() {
		return srhId;
	}
	public void setSrhId(Long srhId) {
		this.srhId = srhId;
	}
	public String getDuplicateTxn() {
		return duplicateTxn;
	}
	public void setDuplicateTxn(String duplicateTxn) {
		this.duplicateTxn = duplicateTxn;
	}
	public String getHoldTxn() {
		return holdTxn;
	}
	public void setHoldTxn(String holdTxn) {
		this.holdTxn = holdTxn;
	}
	public String getHoldReason() {
		return holdReason;
	}
	public void setHoldReason(String holdReason) {
		this.holdReason = holdReason;
	}
	public Double getRecoveryAmount() {
		return recoveryAmount;
	}
	public void setRecoveryAmount(Double recoveryAmount) {
		this.recoveryAmount = recoveryAmount;
	}
	public String getRecoveryReason() {
		return recoveryReason;
	}
	public void setRecoveryReason(String recoveryReason) {
		this.recoveryReason = recoveryReason;
	}
	public LocalDateTime getUtrDate() {
		return utrDate;
	}
	public void setUtrDate(LocalDateTime utrDate) {
		this.utrDate = utrDate;
	}
	public Double getChargeBackAmount() {
		return chargeBackAmount;
	}
	public void setChargeBackAmount(Double chargeBackAmount) {
		this.chargeBackAmount = chargeBackAmount;
	}
	public Double getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}
	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	public String getReleaseStatus() {
		return releaseStatus;
	}
	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}
	public String getReleaseRemark() {
		return releaseRemark;
	}
	public void setReleaseRemark(String releaseRemark) {
		this.releaseRemark = releaseRemark;
	}
	@Override
	public String toString() {
		return "TransactionMisDetail [id=" + id + ", txnId=" + txnId + ", srhId=" + srhId + ", duplicateTxn="
				+ duplicateTxn + ", holdTxn=" + holdTxn + ", holdReason=" + holdReason + ", recoveryAmount="
				+ recoveryAmount + ", recoveryReason=" + recoveryReason + ", chargeBackAmount=" + chargeBackAmount
				+ ", refundAmount=" + refundAmount + ", refundRrn=" + refundRrn + ", fileId=" + fileId
				+ ", releaseStatus=" + releaseStatus + ", releaseRemark=" + releaseRemark + ", reconDate=" + reconDate
				+ ", utrDate=" + utrDate + ", status=" + status + ", vpa=" + vpa + ", rrn=" + rrn + ", mid=" + mid
				+ ", txnType=" + txnType + "]";
	}

	
	
	
	
	

	
}
