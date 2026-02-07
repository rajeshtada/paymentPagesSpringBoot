package com.ftk.pg.modal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "settlement_request_rts")
public class SettlementRequestRts implements Serializable {

	private static final long serialVersionUID = 1L;

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
	
	@Id
	@Column(name = "srhId")
	private Long srhId;
	
	@OneToOne
	@JoinColumn(name = "srhId")
	@MapsId
	private SettlementReportHistory settlementReportHistory;
	
	public SettlementReportHistory getSettlementReportHistory() {
		return settlementReportHistory;
	}
	public void setSettlementReportHistory(SettlementReportHistory settlementReportHistory) {
		this.settlementReportHistory = settlementReportHistory;
	}

	@Column(name = "status")
	private int status;

	@Column(name = "settlement_ref_no")
	private String settlementRefNo;

	@Column(name = "mid")
	private Long mid;

	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "modified_date")
	@UpdateTimestamp
	private LocalDateTime modifiedDate;

	@Column(name = "remark")
	private String remark;

	@Column(name = "merchant_settlement_date")
	private Date merchantSettlementDate;

	@Column(name = "settlement_amount")
	private BigDecimal settlementAmount;

	@Column(name = "file_id")
	private Long fileId;
	
	
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
	public Long getSrhId() {
		return srhId;
	}

	public void setSrhId(Long srhId) {
		this.srhId = srhId;
	}
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSettlementRefNo() {
		return settlementRefNo;
	}

	public void setSettlementRefNo(String settlementRefNo) {
		this.settlementRefNo = settlementRefNo;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
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

	public Date getMerchantSettlementDate() {
		return merchantSettlementDate;
	}

	public void setMerchantSettlementDate(Date merchantSettlementDate) {
		this.merchantSettlementDate = merchantSettlementDate;
	}

	public BigDecimal getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(BigDecimal settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	@Override
	public String toString() {
		return "SettlementRequestRts [srhId=" + srhId + ", status=" + status
				+ ", settlementRefNo=" + settlementRefNo + ", mid=" + mid + ", createdDate=" + createdDate
				+ ", modifiedDate=" + modifiedDate + ", remark=" + remark + ", merchantSettlementDate="
				+ merchantSettlementDate + ", settlementAmount=" + settlementAmount + ", fileId=" + fileId + "]";
	}

}
