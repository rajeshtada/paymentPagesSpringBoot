package com.ftk.pg.modal;

import java.io.Serializable;
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
@Table(name = "nodal_data_icici")
public class NodalDataICICI implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "account_number")
	private String accountNumber;

	@Column(name = "transaction_id")
	private String transactionId;

	@Column(name = "transaction_date")
	private String transactionDate;

	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "value_date")
	private String valueDate;

	@Column(name = "transaction_narration")
	private String transactionNarration;

	private String remarks;

	@Column(name = "instrument_number")
	private String instrumentNumber;

	@Column(name = "debit_amount")
	private Double debitAmount;

	@Column(name = "credit_amount")
	private Double creditAmount;

	@Column(name = "time_stamp")
	private LocalDateTime timeStamp;

	private String udf1;

	private String udf2;

	private String udf3;

	private String rrn;

	@Column(name = "fileId")
	private Long fileId;

	@Column(name = "pg_tranasction_id")
	private Long pgTranasctionId;

	@Column(name = "status")
	@NotNull
	@ColumnDefault("'0'")
	private int status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getValueDate() {
		return valueDate;
	}

	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}

	public String getTransactionNarration() {
		return transactionNarration;
	}

	public void setTransactionNarration(String transactionNarration) {
		this.transactionNarration = transactionNarration;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getInstrumentNumber() {
		return instrumentNumber;
	}

	public void setInstrumentNumber(String instrumentNumber) {
		this.instrumentNumber = instrumentNumber;
	}

	public Double getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(Double debitAmount) {
		this.debitAmount = debitAmount;
	}

	public Double getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(Double creditAmount) {
		this.creditAmount = creditAmount;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "NodalDataICICI [id=" + id + ", accountNumber=" + accountNumber + ", transactionId=" + transactionId
				+ ", transactionDate=" + transactionDate + ", createdDate=" + createdDate + ", valueDate=" + valueDate
				+ ", transactionNarration=" + transactionNarration + ", remarks=" + remarks + ", instrumentNumber="
				+ instrumentNumber + ", debitAmount=" + debitAmount + ", creditAmount=" + creditAmount + ", timeStamp="
				+ timeStamp + ", udf1=" + udf1 + ", udf2=" + udf2 + ", udf3=" + udf3 + ", rrn=" + rrn + ", fileId="
				+ fileId + ", pgTranasctionId=" + pgTranasctionId + ", status=" + status + "]";
	}

}
