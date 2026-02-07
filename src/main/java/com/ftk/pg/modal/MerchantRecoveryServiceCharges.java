package com.ftk.pg.modal;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "merchant_recovery_service_charges")
public class MerchantRecoveryServiceCharges implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recovery_charges_id")
	private Long recoveryChargesId;

	@Column(name = "mid")
	private Long mid;

	@Column(name = "refund_Id")
	private Long refundId;

	@Column(name = "type")
	private String type;

	@Column(name = "vpa")
	private String vpa;

	@Column(name = "period_of_time")
	private int periodOfTime;

	@Column(name = "amount")
	private Double amount;

	@Column(name = "max_amount")
	private Double maxAmount;

	@Column(name = "limited_amount")
	private Double limitedAmount;

	@Column(name = "cycle")
	private String cycle;

	@Column(name = "title")
	private String title;

	@Column(name = "started_date", updatable = false)
	@Temporal(TemporalType.DATE)
	private Date startedDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "account_type")
	private String accountType;

	@Column(name = "account_no")
	private String accountNo;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "account_holder_name")
	private String accountHolderName;

	@Column(name = "ifsc")
	private String ifsc;

	@Column(name = "status")
	private int status;

	@Column(name = "recovery_amount")
	private Double recoveryAmount = 0d;

	@Column(name = "due_recovery_amount")
	private Double dueRecoveryAmount;

	@Column(name = "next_recovery_cycle")
	private String nextRecoveryCycle;

	@Temporal(TemporalType.DATE)
	@Column(name = "next_recovery_date")
	private Date nextRecoveryDate;

	@Column(name = "areas", columnDefinition = "double default 0.0")
	private Double areas;

	@Column(name = "attempt", columnDefinition = "bigint default 0")
	private Long attempt;
	
	@Column(name = "product_code")
	private String productCode;
	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Double getAreas() {
		return areas;
	}

	public void setAreas(Double areas) {
		this.areas = areas;
	}

	public Long getAttempt() {
		return attempt;
	}

	public void setAttempt(Long attempt) {
		this.attempt = attempt;
	}

	public Long getRecoveryChargesId() {
		return recoveryChargesId;
	}

	public Long getMid() {
		return mid;
	}

	public String getVpa() {
		return vpa;
	}

	public int getPeriodOfTime() {
		return periodOfTime;
	}

	public Double getAmount() {
		return amount;
	}

	public String getCycle() {
		return cycle;
	}

	public String getTitle() {
		return title;
	}

	public Date getStartedDate() {
		return startedDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getAccountType() {
		return accountType;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public String getBankName() {
		return bankName;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setRecoveryChargesId(Long recoveryChargesId) {
		this.recoveryChargesId = recoveryChargesId;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public void setVpa(String vpa) {
		this.vpa = vpa;
	}

	public void setPeriodOfTime(int periodOfTime) {
		this.periodOfTime = periodOfTime;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setStartedDate(Date startedDate) {
		this.startedDate = startedDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	public Double getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(Double maxAmount) {
		this.maxAmount = maxAmount;
	}

	public Double getLimitedAmount() {
		return limitedAmount;
	}

	public void setLimitedAmount(Double limitedAmount) {
		this.limitedAmount = limitedAmount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Double getRecoveryAmount() {
		return recoveryAmount;
	}

	public void setRecoveryAmount(Double recoveryAmount) {
		this.recoveryAmount = recoveryAmount;
	}

	public Double getDueRecoveryAmount() {
		return dueRecoveryAmount;
	}

	public void setDueRecoveryAmount(Double dueRecoveryAmount) {
		this.dueRecoveryAmount = dueRecoveryAmount;
	}

	public String getNextRecoveryCycle() {
		return nextRecoveryCycle;
	}

	public void setNextRecoveryCycle(String nextRecoveryCycle) {
		this.nextRecoveryCycle = nextRecoveryCycle;
	}

	public Date getNextRecoveryDate() {
		return nextRecoveryDate;
	}

	public void setNextRecoveryDate(Date nextRecoveryDate) {
		this.nextRecoveryDate = nextRecoveryDate;
	}

	public Long getRefundId() {
		return refundId;
	}

	public void setRefundId(Long refundId) {
		this.refundId = refundId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "MerchantRecoveryServiceCharges [recoveryChargesId=" + recoveryChargesId + ", mid=" + mid + ", refundId="
				+ refundId + ", type=" + type + ", vpa=" + vpa + ", periodOfTime=" + periodOfTime + ", amount=" + amount
				+ ", maxAmount=" + maxAmount + ", limitedAmount=" + limitedAmount + ", cycle=" + cycle + ", title="
				+ title + ", startedDate=" + startedDate + ", endDate=" + endDate + ", accountType=" + accountType
				+ ", accountNo=" + accountNo + ", bankName=" + bankName + ", accountHolderName=" + accountHolderName
				+ ", ifsc=" + ifsc + ", status=" + status + ", recoveryAmount=" + recoveryAmount
				+ ", dueRecoveryAmount=" + dueRecoveryAmount + ", nextRecoveryCycle=" + nextRecoveryCycle
				+ ", nextRecoveryDate=" + nextRecoveryDate + ", areas=" + areas + ", attempt=" + attempt + "]";
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

}
