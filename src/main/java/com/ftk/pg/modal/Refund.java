package com.ftk.pg.modal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.envers.Audited;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "refund")
@Audited
@Entity
public class Refund implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "refund_id")
	private Long id;

	@Column(name = "refund_ref_no")
	private String refundRefno;
	@Column(name = "transaction_id")
	private Long transactionId;
	@Column(name = "refund_amount")
	private BigDecimal refundAmount;
	@Column(name = "transaction_amount")
	private BigDecimal transactionAmount;
	@Column(name = "pending_amount")
	private BigDecimal pendingAmount;
	@Column(name = "response_code")
	private String responseCode;
	@Column(name = "response_msg")
	private String responseMsg;
	@Column(name = "refund_date")
	@CreationTimestamp()
	private LocalDateTime refundDate;
	@Column(name = "card_no")
	private String cardNo;
	@Column(name = "merchant_name")
	private String merchantName;
	@Column(name = "mid")
	private Long mid;
	@Column(name = "settlement_date")
	private LocalDateTime settlementDate;
	@Column(name = "settlement_note")
	private String settlementnote;
	@Column(name = "refund_processing_date")
	@CreationTimestamp()
	private LocalDateTime refundProcessingDate;

	public LocalDateTime getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(LocalDateTime settlementDate) {
		this.settlementDate = settlementDate;
	}

	public String getSettlementnote() {
		return settlementnote;
	}

	public void setSettlementnote(String settlementnote) {
		this.settlementnote = settlementnote;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRefundRefno() {
		return refundRefno;
	}

	public void setRefundRefno(String refundRefno) {
		this.refundRefno = refundRefno;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public BigDecimal getPendingAmount() {
		return pendingAmount;
	}

	public void setPendingAmount(BigDecimal pendingAmount) {
		this.pendingAmount = pendingAmount;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	public LocalDateTime getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(LocalDateTime refundDate) {
		this.refundDate = refundDate;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public LocalDateTime getRefundProcessingDate() {
		return refundProcessingDate;
	}

	public void setRefundProcessingDate(LocalDateTime refundProcessingDate) {
		this.refundProcessingDate = refundProcessingDate;
	}

	@Override
	public String toString() {
		return "Refund [id=" + id + ", refundRefno=" + refundRefno + ", transactionId=" + transactionId
				+ ", refundAmount=" + refundAmount + ", transactionAmount=" + transactionAmount + ", pendingAmount="
				+ pendingAmount + ", responseCode=" + responseCode + ", responseMsg=" + responseMsg + ", refundDate="
				+ refundDate + ", cardNo=" + cardNo + ", merchantName=" + merchantName + ", mid=" + mid
				+ ", settlementDate=" + settlementDate + ", settlementnote=" + settlementnote
				+ ", refundProcessingDate=" + refundProcessingDate + "]";
	}

}
