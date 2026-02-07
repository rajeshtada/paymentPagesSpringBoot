package com.ftk.pg.modal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "nodal_data_icici_batch")
public class NodalDataIciciBatch implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "batch_id")
	private String batchId;

	@Column(name = "bulk_posting_ref_no")
	private String bulkPostingRefNo;

	@Column(name = "transaction_rrn")
	private String transactionRrn;

	@Column(name = "remitter_detail")
	private String remitterDetail;

	@Column(name = "beneficiary_detail")
	private String beneficiaryDetail;

	@Column(name = "amount")
	private BigDecimal amt;

	@Column(name = "txn_date_time")
	private LocalDateTime txnDateTime;

	@Column(name = "status")
	private String status;

	@Column(name = "cdr")
	private String cdr;

	@Column(name = "payment_mode")
	private String paymentMode;

	@Column(name = "payment_remarks")
	private String paymentRemarks;

	private String udf1;

	private String udf2;

	private String udf3;

	@Column(name = "fileId")
	private Long fileId;

	@Column(name = "pg_tranasction_id")
	private Long pgTranasctionId;

	@Column(name = "settlement_status")
	@NotNull
	@ColumnDefault("'0'")
	private int settlementStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getBulkPostingRefNo() {
		return bulkPostingRefNo;
	}

	public void setBulkPostingRefNo(String bulkPostingRefNo) {
		this.bulkPostingRefNo = bulkPostingRefNo;
	}

	public String getTransactionRrn() {
		return transactionRrn;
	}

	public void setTransactionRrn(String transactionRrn) {
		this.transactionRrn = transactionRrn;
	}

	public String getRemitterDetail() {
		return remitterDetail;
	}

	public void setRemitterDetail(String remitterDetail) {
		this.remitterDetail = remitterDetail;
	}

	public String getBeneficiaryDetail() {
		return beneficiaryDetail;
	}

	public void setBeneficiaryDetail(String beneficiaryDetail) {
		this.beneficiaryDetail = beneficiaryDetail;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public LocalDateTime getTxnDateTime() {
		return txnDateTime;
	}

	public void setTxnDateTime(LocalDateTime txnDateTime) {
		this.txnDateTime = txnDateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCdr() {
		return cdr;
	}

	public void setCdr(String cdr) {
		this.cdr = cdr;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getPaymentRemarks() {
		return paymentRemarks;
	}

	public void setPaymentRemarks(String paymentRemarks) {
		this.paymentRemarks = paymentRemarks;
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

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public Long getPgTranasctionId() {
		return pgTranasctionId;
	}

	public void setPgTranasctionId(Long pgTranasctionId) {
		this.pgTranasctionId = pgTranasctionId;
	}

	public int getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(int settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	@Override
	public String toString() {
		return "NodalDataIciciBatch [id=" + id + ", createdDate=" + createdDate + ", batchId=" + batchId
				+ ", bulkPostingRefNo=" + bulkPostingRefNo + ", transactionRrn=" + transactionRrn + ", remitterDetail="
				+ remitterDetail + ", beneficiaryDetail=" + beneficiaryDetail + ", amt=" + amt + ", txnDateTime="
				+ txnDateTime + ", status=" + status + ", cdr=" + cdr + ", paymentMode=" + paymentMode
				+ ", paymentRemarks=" + paymentRemarks + ", udf1=" + udf1 + ", udf2=" + udf2 + ", udf3=" + udf3
				+ ", fileId=" + fileId + ", pgTranasctionId=" + pgTranasctionId + ", settlementStatus="
				+ settlementStatus + "]";
	}

}
