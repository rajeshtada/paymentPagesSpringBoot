package com.ftk.pg.modal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Audited
@Table(name = "merchant_product_transaction")
public class MerchantProductTransaction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3503563519153504464L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "txn_id")
	private Long txnId;

	@Column(name = "mid")
	private Long mid;

	@Column(name = "settlement_date")
	private Date settlementDate;

	@Column(name = "settlement_amt")
	private BigDecimal settlementAmt;

	@Column(name = "settlement_ref_no")
	private String settlementRefNo;

	@Column(name = "settlement_status")
	private int settlementStatus;

	@Column(name = "product_code")
	private String productCode;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "product_amt")
	private BigDecimal productAmt;

	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "updated_date")
	@UpdateTimestamp
	private LocalDateTime updatedDate;
	
	@Column(name = "settlement_history_id")
	private Long settlementHistoryId;
	
	@Column(name = "settlement_file_id")
	private Long settlementFileId;

	public Long getSettlementHistoryId() {
		return settlementHistoryId;
	}

	public void setSettlementHistoryId(Long settlementHistoryId) {
		this.settlementHistoryId = settlementHistoryId;
	}

	public Long getSettlementFileId() {
		return settlementFileId;
	}

	public void setSettlementFileId(Long settlementFileId) {
		this.settlementFileId = settlementFileId;
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

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	public BigDecimal getSettlementAmt() {
		return settlementAmt;
	}

	public void setSettlementAmt(BigDecimal settlementAmt) {
		this.settlementAmt = settlementAmt;
	}

	public String getSettlementRefNo() {
		return settlementRefNo;
	}

	public void setSettlementRefNo(String settlementRefNo) {
		this.settlementRefNo = settlementRefNo;
	}

	public int getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(int settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getProductAmt() {
		return productAmt;
	}

	public void setProductAmt(BigDecimal productAmt) {
		this.productAmt = productAmt;
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

	@Override
	public String toString() {
		return "MerchantProductTransaction [id=" + id + ", txnId=" + txnId + ", mid=" + mid + ", settlementDate="
				+ settlementDate + ", settlementAmt=" + settlementAmt + ", settlementRefNo=" + settlementRefNo
				+ ", settlementStatus=" + settlementStatus + ", productCode=" + productCode + ", productName="
				+ productName + ", productAmt=" + productAmt + ", createdDate=" + createdDate + ", updatedDate="
				+ updatedDate + ", settlementHistoryId=" + settlementHistoryId + ", settlementFileId="
				+ settlementFileId + "]";
	}

}
