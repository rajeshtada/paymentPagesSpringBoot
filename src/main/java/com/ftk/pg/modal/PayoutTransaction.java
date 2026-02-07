package com.ftk.pg.modal;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@Table(name = "payout_transaction")
@Audited
public class PayoutTransaction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3944857198614380853L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "mid")
	private Long mid;

	@Column(name = "account_number")
	private String accountNumber;

	@Column(name = "account_name")
	private String accountName;

	@Column(name = "amount")
	private Double amount;

	@Column(name = "ifsc_code")
	private String ifscCode;

	@Column(name = "status", nullable = false)
	private int status; // 0 in-process and 1 success and 2 failed

	@Column(name = "created_by", nullable = false)
	private String createdBy;

	@Column(name = "modify_by")
	private String modifyBy;

	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "modify_date")
	@UpdateTimestamp
	private LocalDateTime modifyDate;

	@Column(name = "act_code")
	private String actCode;

	@Column(name = "act_code_desc")
	private String actCodeDesc;

	@Column(name = "api_status")
	private String apiStatus;
	
	@Column(name = "vpa")
	private String vpa;
	
	@Column(name = "mode")
	private String mode;

	@Column(name = "merchant_transaction_id")
	private Long merchantTransactionId;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(LocalDateTime modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getActCode() {
		return actCode;
	}

	public void setActCode(String actCode) {
		this.actCode = actCode;
	}

	public String getActCodeDesc() {
		return actCodeDesc;
	}

	public void setActCodeDesc(String actCodeDesc) {
		this.actCodeDesc = actCodeDesc;
	}

	public String getApiStatus() {
		return apiStatus;
	}

	public void setApiStatus(String apiStatus) {
		this.apiStatus = apiStatus;
	}



	public String getVpa() {
		return vpa;
	}

	public void setVpa(String vpa) {
		this.vpa = vpa;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Long getMerchantTransactionId() {
		return merchantTransactionId;
	}

	public void setMerchantTransactionId(Long merchantTransactionId) {
		this.merchantTransactionId = merchantTransactionId;
	}
}
