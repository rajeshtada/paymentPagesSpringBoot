package com.ftk.pg.modal;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "txn_revenue")
public class TxnRevenue implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "updated_date")
	@UpdateTimestamp
	private LocalDateTime updatedDate;

	@Column(name = "transaction_id")
	private Long transactionId;

	@Column(name = "transaction_amount")
	private Double transactionAmt;

	@Column(name = "mdr_charge")
	private Double mdrCharge;

	@Column(name = "mdr_charge_type")
	private String mdrChargeType;

	@Column(name = "service_charge")
	private Double serviceCharge;

	@Column(name = "service_charge_type")
	private String serviceChargeType;

	@Column(name = "txn_request_amount")
	private Double txnRequestAmount;

	@Column(name = "processor_settlement_amount")
	private Double processorSettlementAmount;

	@Column(name = "merchant_settlement_amount")
	private Double merchantSettlementAmount;

	@Column(name = "net_mdr_charge")
	private Double netMdrCharge;

	@Column(name = "net_service_charge")
	private Double netServiceCharge;

	@Column(name = "revenue_amount")
	private Double revenueAmount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Double getTransactionAmt() {
		return transactionAmt;
	}

	public void setTransactionAmt(Double transactionAmt) {
		this.transactionAmt = transactionAmt;
	}

	public Double getMdrCharge() {
		return mdrCharge;
	}

	public void setMdrCharge(Double mdrCharge) {
		this.mdrCharge = mdrCharge;
	}

	public String getMdrChargeType() {
		return mdrChargeType;
	}

	public void setMdrChargeType(String mdrChargeType) {
		this.mdrChargeType = mdrChargeType;
	}

	public Double getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(Double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public String getServiceChargeType() {
		return serviceChargeType;
	}

	public void setServiceChargeType(String serviceChargeType) {
		this.serviceChargeType = serviceChargeType;
	}

	public Double getTxnRequestAmount() {
		return txnRequestAmount;
	}

	public void setTxnRequestAmount(Double txnRequestAmount) {
		this.txnRequestAmount = txnRequestAmount;
	}

	public Double getProcessorSettlementAmount() {
		return processorSettlementAmount;
	}

	public void setProcessorSettlementAmount(Double processorSettlementAmount) {
		this.processorSettlementAmount = processorSettlementAmount;
	}

	public Double getMerchantSettlementAmount() {
		return merchantSettlementAmount;
	}

	public void setMerchantSettlementAmount(Double merchantSettlementAmount) {
		this.merchantSettlementAmount = merchantSettlementAmount;
	}

	public Double getNetMdrCharge() {
		return netMdrCharge;
	}

	public void setNetMdrCharge(Double netMdrCharge) {
		this.netMdrCharge = netMdrCharge;
	}

	public Double getNetServiceCharge() {
		return netServiceCharge;
	}

	public void setNetServiceCharge(Double netServiceCharge) {
		this.netServiceCharge = netServiceCharge;
	}

	public Double getRevenueAmount() {
		return revenueAmount;
	}

	public void setRevenueAmount(Double revenueAmount) {
		this.revenueAmount = revenueAmount;
	}

	@Override
	public String toString() {
		return "TxnRevenue [id=" + id + ", updatedDate=" + updatedDate + ", transactionId=" + transactionId
				+ ", transactionAmt=" + transactionAmt + ", mdrCharge=" + mdrCharge + ", mdrChargeType=" + mdrChargeType
				+ ", serviceCharge=" + serviceCharge + ", serviceChargeType=" + serviceChargeType
				+ ", txnRequestAmount=" + txnRequestAmount + ", processorSettlementAmount=" + processorSettlementAmount
				+ ", merchantSettlementAmount=" + merchantSettlementAmount + ", netMdrCharge=" + netMdrCharge
				+ ", netServiceCharge=" + netServiceCharge + ", revenueAmount=" + revenueAmount + "]";
	}

}
