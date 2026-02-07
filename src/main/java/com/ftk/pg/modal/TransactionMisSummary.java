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
@Table(name = "transaction_mis_summary")
public class TransactionMisSummary implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "srh_id")
	private Long srhId;
	@Column(name = "account_no")
	private String accountNo;
	@Column(name = "merchant_name")
	private String merchantName;
	@Column(name = "settlement_date")
	private LocalDateTime settlementDate;
	private Long mid;
	private String vpa;
	@Column(name = "txn_type")
	private String txnType;
	@Column(name = "prod_code")
	private String prodCode;
	@Column(name = "recon_status")
	private String reconStatus;
	@Column(name = "recon_date")
	private String reconDate;
	private Double amount;
	@Column(name = "net_amount")
	private Double netAmount;
	@Column(name = "duplicate_txn")
	private String duplicateTxn;
	@Column(name = "duplicate_txn_amount")
	private Double duplicateTxnAmount;
	@Column(name = "hold_txn")
	private String holdTxn;
	@Column(name = "hold_txn_amount")
	private Double holdTxnAmount;
	@Column(name = "hold_reason")
	private String holdReason;
	@Column(name = "recovery_amount")
	private Double recoveryAmount;
	@Column(name = "recovery_reason")
	private String recoveryReason;
	@Column(name = "utr_no")
	private String utrNo;
	@Column(name = "utr_date")
	private String utrDate;
	@Column(name = "commission_charge")
	private Double commissionCharge;
	@Column(name = "service_charge")
	private Double serviceCharge;
	private String ifsc;
	@Column(name = "charge_back_amount")
	private Double chargeBackAmount;
	@Column(name = "refund_amount")
	private Double refundAmount;
	@Column(name = "total_transaction_amount")
	private Double totalTransactionAmount;
	@Column(name = "file_id")
	private Long fileId;
	@Column(name = "release_status")
	private String releaseStatus;
	@Column(name = "release_remark")
	private String releaseRemark;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSrhId() {
		return srhId;
	}
	public void setSrhId(Long srhId) {
		this.srhId = srhId;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public LocalDateTime getSettlementDate() {
		return settlementDate;
	}
	public void setSettlementDate(LocalDateTime settlementDate) {
		this.settlementDate = settlementDate;
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
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getProdCode() {
		return prodCode;
	}
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}
	public String getReconStatus() {
		return reconStatus;
	}
	public void setReconStatus(String reconStatus) {
		this.reconStatus = reconStatus;
	}
	public String getReconDate() {
		return reconDate;
	}
	public void setReconDate(String reconDate) {
		this.reconDate = reconDate;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(Double netAmount) {
		this.netAmount = netAmount;
	}
	public String getDuplicateTxn() {
		return duplicateTxn;
	}
	public void setDuplicateTxn(String duplicateTxn) {
		this.duplicateTxn = duplicateTxn;
	}
	public Double getDuplicateTxnAmount() {
		return duplicateTxnAmount;
	}
	public void setDuplicateTxnAmount(Double duplicateTxnAmount) {
		this.duplicateTxnAmount = duplicateTxnAmount;
	}
	public String getHoldTxn() {
		return holdTxn;
	}
	public void setHoldTxn(String holdTxn) {
		this.holdTxn = holdTxn;
	}
	public Double getHoldTxnAmount() {
		return holdTxnAmount;
	}
	public void setHoldTxnAmount(Double holdTxnAmount) {
		this.holdTxnAmount = holdTxnAmount;
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
	public String getUtrNo() {
		return utrNo;
	}
	public void setUtrNo(String utrNo) {
		this.utrNo = utrNo;
	}
	public String getUtrDate() {
		return utrDate;
	}
	public void setUtrDate(String utrDate) {
		this.utrDate = utrDate;
	}
	public Double getCommissionCharge() {
		return commissionCharge;
	}
	public void setCommissionCharge(Double commissionCharge) {
		this.commissionCharge = commissionCharge;
	}
	public Double getServiceCharge() {
		return serviceCharge;
	}
	public void setServiceCharge(Double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	public String getIfsc() {
		return ifsc;
	}
	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
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
	public Double getTotalTransactionAmount() {
		return totalTransactionAmount;
	}
	public void setTotalTransactionAmount(Double totalTransactionAmount) {
		this.totalTransactionAmount = totalTransactionAmount;
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
		return "TransactionMisSummary [id=" + id + ", srhId=" + srhId + ", accountNo=" + accountNo + ", merchantName="
				+ merchantName + ", settlementDate=" + settlementDate + ", mid=" + mid + ", vpa=" + vpa + ", txnType="
				+ txnType + ", prodCode=" + prodCode + ", reconStatus=" + reconStatus + ", reconDate=" + reconDate
				+ ", amount=" + amount + ", netAmount=" + netAmount + ", duplicateTxn=" + duplicateTxn
				+ ", duplicateTxnAmount=" + duplicateTxnAmount + ", holdTxn=" + holdTxn + ", holdTxnAmount="
				+ holdTxnAmount + ", holdReason=" + holdReason + ", recoveryAmount=" + recoveryAmount
				+ ", recoveryReason=" + recoveryReason + ", utrNo=" + utrNo + ", utrDate=" + utrDate
				+ ", commissionCharge=" + commissionCharge + ", serviceCharge=" + serviceCharge + ", ifsc=" + ifsc
				+ ", chargeBackAmount=" + chargeBackAmount + ", refundAmount=" + refundAmount
				+ ", totalTransactionAmount=" + totalTransactionAmount + ", fileId=" + fileId + ", releaseStatus="
				+ releaseStatus + ", releaseRemark=" + releaseRemark + "]";
	}
	
	



	
}
