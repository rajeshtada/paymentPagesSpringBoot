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

@Table(name = "settlement_report_history_instant")
@Entity
public class SettlementReportHistoryInstant implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "pg_transaction_id")
	private Long pgTransactionId;
	
	@Column(name = "settlement_history_id")
	private Long settlementHistoryId;
	
	@Column(name = "portal_transaction_id")
	private Long portalTransactionId;
	
	@Column(name = "status")
	private int status;
	
	@Column(name = "rrn")
	private String rrn; // cusREfno
	
	@Column(name = "merchant_settlement_ref_no")
	private String merchantSettlementRefNo;
	
	@Column(name = "mid")
	private Long mid;
	
	@Column(name = "vpa")
	private String vpa;
	
	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "modified_date")
	@UpdateTimestamp
	private LocalDateTime modifiedDate;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "amount")
	private BigDecimal amt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPgTransactionId() {
		return pgTransactionId;
	}

	public void setPgTransactionId(Long pgTransactionId) {
		this.pgTransactionId = pgTransactionId;
	}

	public Long getSettlementHistoryId() {
		return settlementHistoryId;
	}

	public void setSettlementHistoryId(Long settlementHistoryId) {
		this.settlementHistoryId = settlementHistoryId;
	}

	public Long getPortalTransactionId() {
		return portalTransactionId;
	}

	public void setPortalTransactionId(Long portalTransactionId) {
		this.portalTransactionId = portalTransactionId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public String getMerchantSettlementRefNo() {
		return merchantSettlementRefNo;
	}

	public void setMerchantSettlementRefNo(String merchantSettlementRefNo) {
		this.merchantSettlementRefNo = merchantSettlementRefNo;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	@Override
	public String toString() {
		return "SettlementReportHistoryInstant [id=" + id + ", pgTransactionId=" + pgTransactionId
				+ ", settlementHistoryId=" + settlementHistoryId + ", portalTransactionId=" + portalTransactionId
				+ ", status=" + status + ", rrn=" + rrn + ", merchantSettlementRefNo=" + merchantSettlementRefNo
				+ ", mid=" + mid + ", vpa=" + vpa + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate
				+ ", remark=" + remark + ", amt=" + amt + "]";
	}
	
	
	
	
}


