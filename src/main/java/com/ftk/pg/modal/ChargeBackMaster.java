package com.ftk.pg.modal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.envers.Audited;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Audited
@Table(name = "charge_back_master")
public class ChargeBackMaster implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "charge_back_master_id")
	private Long chargebackMasterId;

	@Column(name = "transaction_id")
	private Long transactionId;

	@Column(name = "merchant_id")
	private Long mid;

	@Column(name = "remark_by")
	private String remarkBy;

	@Column(name = "remark")
	private String remark;

	@Column(name = "remark_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime remarkDate;

	@Column(name = "dispute_type")
	private String disputeType;

	@Column(name = "remarks_status")
	@ColumnDefault("'0'")
	private int remarksStatus;

	@Column(name = "remark_filepath")
	private String remarkFilePath;

	@Column(name = "due_date")
	private Date dueDate;

	@Column(name = "amount")
	private BigDecimal amt;

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public String getRemarkBy() {
		return remarkBy;
	}

	public void setRemarkBy(String remarkBy) {
		this.remarkBy = remarkBy;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public LocalDateTime getRemarkDate() {
		return remarkDate;
	}

	public void setRemarkDate(LocalDateTime remarkDate) {
		this.remarkDate = remarkDate;
	}

	public int getRemarksStatus() {
		return remarksStatus;
	}

	public void setRemarksStatus(int remarksStatus) {
		this.remarksStatus = remarksStatus;
	}

	public String getRemarkFilePath() {
		return remarkFilePath;
	}

	public void setRemarkFilePath(String remarkFilePath) {
		this.remarkFilePath = remarkFilePath;
	}

	public String getDisputeType() {
		return disputeType;
	}

	public void setDisputeType(String disputeType) {
		this.disputeType = disputeType;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Long getChargebackMasterId() {
		return chargebackMasterId;
	}

	public void setChargebackMasterId(Long chargebackMasterId) {
		this.chargebackMasterId = chargebackMasterId;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	@Override
	public String toString() {
		return "ChargeBackMaster [chargebackMasterId=" + chargebackMasterId + ", transactionId=" + transactionId
				+ ", mid=" + mid + ", remarkBy=" + remarkBy + ", remark=" + remark + ", remarkDate=" + remarkDate
				+ ", disputeType=" + disputeType + ", remarksStatus=" + remarksStatus + ", remarkFilePath="
				+ remarkFilePath + ", dueDate=" + dueDate + ", amt=" + amt + "]";
	}

}
