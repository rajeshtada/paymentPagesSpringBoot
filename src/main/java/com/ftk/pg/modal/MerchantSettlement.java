package com.ftk.pg.modal;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@Table(name = "settlement")
public class MerchantSettlement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "settlement_id")
	private Long settlementId;

	@Column(name = "settlement_ref_no")
	private String settlementRefNo;

	@Column(name = "merchant_name")
	private String merchantName;

	@Column(name = "settlement_amount")
	private Double settlementAmt;

	@Column(name = "settlementRemarks")
	private String settlementRemarks;

	@Column(name = "transactionId")
	private Long transactionId;

	@Column(name = "settlement_date")
	private String settlementDate;

	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "mode_of_pay")
	private String modeOfPay;

	@Column(name = "merchant_id")
	private Long mId;

	public Long getSettlementId() {
		return settlementId;
	}

	public void setSettlementId(Long settlementId) {
		this.settlementId = settlementId;
	}

	public String getSettlementRefNo() {
		return settlementRefNo;
	}

	public void setSettlementRefNo(String settlementRefNo) {
		this.settlementRefNo = settlementRefNo;
	}

	public Double getSettlementAmt() {
		return settlementAmt;
	}

	public void setSettlementAmt(Double settlementAmt) {
		this.settlementAmt = settlementAmt;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getSettlementRemarks() {
		return settlementRemarks;
	}

	public void setSettlementRemarks(String settlementRemarks) {
		this.settlementRemarks = settlementRemarks;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public Long getmId() {
		return mId;
	}

	public void setmId(Long mId) {
		this.mId = mId;
	}

	public String getModeOfPay() {
		return modeOfPay;
	}

	public void setModeOfPay(String modeOfPay) {
		this.modeOfPay = modeOfPay;
	}

	@Override
	public String toString() {
		return "MerchantSettlement [settlementId=" + settlementId + ", settlementRefNo=" + settlementRefNo
				+ ", merchantName=" + merchantName + ", settlementAmt=" + settlementAmt + ", settlementRemarks="
				+ settlementRemarks + ", transactionId=" + transactionId + ", settlementDate=" + settlementDate
				+ ", createdDate=" + createdDate + ", modeOfPay=" + modeOfPay + ", mId=" + mId + "]";
	}

}
