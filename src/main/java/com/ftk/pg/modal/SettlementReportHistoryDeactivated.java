package com.ftk.pg.modal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "settlement_report_history_deactivated")
@Entity
public class SettlementReportHistoryDeactivated implements Serializable{
	
	private static final long serialVersionUID = 2037494244888540739L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "mid")
	private Long mid;

	@Column(name = "product_id")
	private String productId;

	@Column(name = "processor")
	private String processor;

	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "final_amount")
	private BigDecimal netAmount;

	@Column(name = "recover_amount")
	private BigDecimal recoverAmount;

	@Column(name = "remitter_account_no")
	private String remitterAccountNo;

	@Column(name = "remitter_name")
	private String remitterName;

	@Column(name = "ifsc_code")
	private String ifscCode;

	@Column(name = "beneficiary_account_no")
	private String beneficiaryAccountNo;

	@Column(name = "beneficiary_name")
	private String beneficiaryName;

	@Column(name = "vpa_id")
	private String vpaId;

	@Column(name = "note")
	private String note;

	@Column(name = "transaction_desc_credit")
	private String transactionDescCredit;

	@Column(name = "settlement_part")
	private String settlementPart;

	@Column(name = "status")
	private int status;

	@Column(name = "merchant_name")
	private String merchantName;

	@Column(name = "settlement_ref_no")
	private String settlementRefNo;

	// @CreationTimestamp
	// @Column(name = "transaction_date", updatable = false)
	// private Date transactionDate;

	@CreationTimestamp
	@Column(name = "settlement_date", updatable = false)
	private Date settlementDate;

	private String udf1;
	private String udf2;
	private String udf3;
	private long fileId;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_date")
	@UpdateTimestamp
	private LocalDateTime updatedDate;

	@Column(name = "next_settlement_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime nextSettlementDate;

	@Column(name = "reason")
	private String reason;

	@Column(name = "next_settlement_part")
	private String nextSettlementPart;

	@Column(name = "txn_type")
	private String txnType = "single";

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public Long getId() {
		return id;
	}

	public Long getMid() {
		return mid;
	}

	public String getProductId() {
		return productId;
	}

	public String getProcessor() {
		return processor;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public String getRemitterAccountNo() {
		return remitterAccountNo;
	}

	public String getRemitterName() {
		return remitterName;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public String getBeneficiaryAccountNo() {
		return beneficiaryAccountNo;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public String getVpaId() {
		return vpaId;
	}

	public String getNote() {
		return note;
	}

	public String getTransactionDescCredit() {
		return transactionDescCredit;
	}

	public String getSettlementPart() {
		return settlementPart;
	}

	public int getStatus() {
		return status;
	}

	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setRemitterAccountNo(String remitterAccountNo) {
		this.remitterAccountNo = remitterAccountNo;
	}

	public void setRemitterName(String remitterName) {
		this.remitterName = remitterName;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public void setBeneficiaryAccountNo(String beneficiaryAccountNo) {
		this.beneficiaryAccountNo = beneficiaryAccountNo;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public void setVpaId(String vpaId) {
		this.vpaId = vpaId;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setTransactionDescCredit(String transactionDescCredit) {
		this.transactionDescCredit = transactionDescCredit;
	}

	public void setSettlementPart(String settlementPart) {
		this.settlementPart = settlementPart;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	public String getUdf1() {
		return udf1;
	}

	public void setUdf1(String udf1) {
		this.udf1 = udf1;
	}

	public String getUdf2() {
		return udf2;
	}

	public void setUdf2(String udf2) {
		this.udf2 = udf2;
	}

	public String getUdf3() {
		return udf3;
	}

	public void setUdf3(String udf3) {
		this.udf3 = udf3;
	}


	public long getFileId() {
		return fileId;
	}

	public void setFileId(long fileId) {
		this.fileId = fileId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public LocalDateTime getNextSettlementDate() {
		return nextSettlementDate;
	}

	public void setNextSettlementDate(LocalDateTime nextSettlementDate) {
		this.nextSettlementDate = nextSettlementDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getNextSettlementPart() {
		return nextSettlementPart;
	}

	public void setNextSettlementPart(String nextSettlementPart) {
		this.nextSettlementPart = nextSettlementPart;
	}

	public BigDecimal getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	public BigDecimal getRecoverAmount() {
		return recoverAmount;
	}

	public void setRecoverAmount(BigDecimal recoverAmount) {
		this.recoverAmount = recoverAmount;
	}

	public String getSettlementRefNo() {
		return settlementRefNo;
	}

	public void setSettlementRefNo(String settlementRefNo) {
		this.settlementRefNo = settlementRefNo;
	}

	@Override
	public String toString() {
		return "SettlementReportHistoryDeactivated [id=" + id + ", mid=" + mid + ", productId=" + productId
				+ ", processor=" + processor + ", amount=" + amount + ", netAmount=" + netAmount + ", recoverAmount="
				+ recoverAmount + ", remitterAccountNo=" + remitterAccountNo + ", remitterName=" + remitterName
				+ ", ifscCode=" + ifscCode + ", beneficiaryAccountNo=" + beneficiaryAccountNo + ", beneficiaryName="
				+ beneficiaryName + ", vpaId=" + vpaId + ", note=" + note + ", transactionDescCredit="
				+ transactionDescCredit + ", settlementPart=" + settlementPart + ", status=" + status
				+ ", merchantName=" + merchantName + ", settlementRefNo=" + settlementRefNo + ", settlementDate="
				+ settlementDate + ", udf1=" + udf1 + ", udf2=" + udf2 + ", udf3=" + udf3 + ", fileId=" + fileId
				+ ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", nextSettlementDate="
				+ nextSettlementDate + ", reason=" + reason + ", nextSettlementPart=" + nextSettlementPart
				+ ", txnType=" + txnType + "]";
	}
	
	

}

