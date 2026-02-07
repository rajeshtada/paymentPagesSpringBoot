package com.ftk.pg.modal;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Table(name = "settlement_process")
@Entity
public class SettlementProcess implements Serializable {

	private static final long serialVersionUID = 2037494244888540739L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "transaction_id")
	private Long transactionId;

	@Column(name = "settlement_history_id")
	private Long settlementHistoryId;

	@Column(name = "settlement_file_id")
	private Long settlementFileId;

	@Column(name = "settlement_status")
	@NotNull
	@ColumnDefault("'0'") // 1 for request send to processor, 2 for get the response against Tx.,
	// 3 Amount Settled by Admin and 4 risk txn
	private int settlementStatus;

	@Column(name = "file_type")
	String fileType;

	@Column(name = "processor")
	String processor;

	@Column(name = "merchant_settlement_date")
	private Date merchantSettlementDate;

	@Column(name = "type")
	private String type;

	@Column(name = "settlement_ref_no")
	private String settlementRefNo;

	public String getSettlementRefNo() {
		return settlementRefNo;
	}

	public void setSettlementRefNo(String settlementRefNo) {
		this.settlementRefNo = settlementRefNo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

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

	public int getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(int settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public Date getMerchantSettlementDate() {
		return merchantSettlementDate;
	}

	public void setMerchantSettlementDate(Date merchantSettlementDate) {
		this.merchantSettlementDate = merchantSettlementDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "SettlementProcess [id=" + id + ", transactionId=" + transactionId + ", settlementHistoryId="
				+ settlementHistoryId + ", settlementFileId=" + settlementFileId + ", settlementStatus="
				+ settlementStatus + ", fileType=" + fileType + ", processor=" + processor + ", merchantSettlementDate="
				+ merchantSettlementDate + ", type=" + type + ", settlementRefNo=" + settlementRefNo + "]";
	}

}
