package com.ftk.pg.modal;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "pany_transaction")
@Entity
public class PanyTransaction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4558463088930327409L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(name = "mid")
	private Long mid;

	@Column(name = "account_no")
	private String accountNo;

	@Column(name = "account_name")
	private String accountName;

	@Column(name = "ifsc_code")
	private String ifscCode;

	@Column(name = "txn_amount")
	private Double txnAmount;

	@Column(name = "created_date", columnDefinition = "DATETIME default CURRENT_TIMESTAMP")
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "updated_date", columnDefinition = "DATETIME default CURRENT_TIMESTAMP")
	@UpdateTimestamp
	private LocalDateTime updatedDate;

	private String status;
	private String actCodeDesc;
	private String actCode;

	public Long getId() {
		return id;
	}

	public Long getMid() {
		return mid;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public Double getTxnAmount() {
		return txnAmount;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public void setTxnAmount(Double txnAmount) {
		this.txnAmount = txnAmount;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getStatus() {
		return status;
	}

	public String getActCodeDesc() {
		return actCodeDesc;
	}

	public String getActCode() {
		return actCode;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setActCodeDesc(String actCodeDesc) {
		this.actCodeDesc = actCodeDesc;
	}

	public void setActCode(String actCode) {
		this.actCode = actCode;
	}

	@Override
	public String toString() {
		return "PanyTransaction [id=" + id + ", mid=" + mid + ", accountNo=" + accountNo + ", accountName="
				+ accountName + ", ifscCode=" + ifscCode + ", txnAmount=" + txnAmount + ", createdDate=" + createdDate
				+ ", updatedDate=" + updatedDate + ", status=" + status + ", actCodeDesc=" + actCodeDesc + ", actCode="
				+ actCode + "]";
	}

}
