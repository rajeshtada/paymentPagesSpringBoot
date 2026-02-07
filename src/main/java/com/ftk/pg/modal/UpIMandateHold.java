package com.ftk.pg.modal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "upi_mandate_hold")
public class UpIMandateHold implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private long id;

	private long mid;
	private String payeeVpa;
	private String merchantMandateId;

	private int status;

	private String mcc;

	private String merchantName;
	private String subMerchantName;
	private String payerVa;

	private BigDecimal amount;
	private String note;

	private Date collectByDate;
	private String billNumber;
	private String requestType;
	private Date validityStartDate;
	private Date validityEndDate;
	private String amountLimit;
	private String frequency;
	private String remark;
	private String autoExecute;
	private String debitDay;
	private String debitRule;

	private String revokable;

	private String blockFund;
	private String purpose;

	private String bankRRN;
	private String responseMessage;
	private String umn;
	private String ru;

	private String customerName;
	private String customerMobileNumber;
	private long parentMandate;
	private int mandateAttempt;
	private LocalDateTime mandateExecutionDate;
	
	public int getMandateAttempt() {
		return mandateAttempt;
	}

	public void setMandateAttempt(int mandateAttempt) {
		this.mandateAttempt = mandateAttempt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getMid() {
		return mid;
	}

	public void setMid(long mid) {
		this.mid = mid;
	}

	public String getPayeeVpa() {
		return payeeVpa;
	}

	public void setPayeeVpa(String payeeVpa) {
		this.payeeVpa = payeeVpa;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getSubMerchantName() {
		return subMerchantName;
	}

	public void setSubMerchantName(String subMerchantName) {
		this.subMerchantName = subMerchantName;
	}

	public String getPayerVa() {
		return payerVa;
	}

	public void setPayerVa(String payerVa) {
		this.payerVa = payerVa;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getAmountLimit() {
		return amountLimit;
	}

	public void setAmountLimit(String amountLimit) {
		this.amountLimit = amountLimit;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAutoExecute() {
		return autoExecute;
	}

	public void setAutoExecute(String autoExecute) {
		this.autoExecute = autoExecute;
	}

	public String getDebitDay() {
		return debitDay;
	}

	public void setDebitDay(String debitDay) {
		this.debitDay = debitDay;
	}

	public String getDebitRule() {
		return debitRule;
	}

	public void setDebitRule(String debitRule) {
		this.debitRule = debitRule;
	}

	public String getRevokable() {
		return revokable;
	}

	public void setRevokable(String revokable) {
		this.revokable = revokable;
	}

	public String getBlockFund() {
		return blockFund;
	}

	public void setBlockFund(String blockFund) {
		this.blockFund = blockFund;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getBankRRN() {
		return bankRRN;
	}

	public void setBankRRN(String bankRRN) {
		this.bankRRN = bankRRN;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getMcc() {
		return mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getCollectByDate() {
		return collectByDate;
	}

	public void setCollectByDate(Date collectByDate) {
		this.collectByDate = collectByDate;
	}

	public String getMerchantMandateId() {
		return merchantMandateId;
	}

	public void setMerchantMandateId(String merchantMandateId) {
		this.merchantMandateId = merchantMandateId;
	}

	public Date getValidityStartDate() {
		return validityStartDate;
	}

	public void setValidityStartDate(Date validityStartDate) {
		this.validityStartDate = validityStartDate;
	}

	public Date getValidityEndDate() {
		return validityEndDate;
	}

	public void setValidityEndDate(Date validityEndDate) {
		this.validityEndDate = validityEndDate;
	}

	public String getRu() {
		return ru;
	}

	public void setRu(String ru) {
		this.ru = ru;
	}

	public String getUmn() {
		return umn;
	}

	public void setUmn(String umn) {
		this.umn = umn;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerMobileNumber() {
		return customerMobileNumber;
	}

	public void setCustomerMobileNumber(String customerMobileNumber) {
		this.customerMobileNumber = customerMobileNumber;
	}

	public long getParentMandate() {
		return parentMandate;
	}

	public void setParentMandate(long parentMandate) {
		this.parentMandate = parentMandate;
	}

	public LocalDateTime getMandateExecutionDate() {
		return mandateExecutionDate;
	}

	public void setMandateExecutionDate(LocalDateTime mandateExecutionDate) {
		this.mandateExecutionDate = mandateExecutionDate;
	}

	@Override
	public String toString() {
		return "UpiMandate [id=" + id + ", mid=" + mid + ", payeeVpa=" + payeeVpa + ", merchantMandateId="
				+ merchantMandateId + ", status=" + status + ", mcc=" + mcc + ", merchantName=" + merchantName
				+ ", subMerchantName=" + subMerchantName + ", payerVa=" + payerVa + ", amount=" + amount + ", note="
				+ note + ", collectByDate=" + collectByDate + ", billNumber=" + billNumber + ", requestType="
				+ requestType + ", validityStartDate=" + validityStartDate + ", validityEndDate=" + validityEndDate
				+ ", amountLimit=" + amountLimit + ", frequency=" + frequency + ", remark=" + remark + ", autoExecute="
				+ autoExecute + ", debitDay=" + debitDay + ", debitRule=" + debitRule + ", revokable=" + revokable
				+ ", blockFund=" + blockFund + ", purpose=" + purpose + ", bankRRN=" + bankRRN + ", responseMessage="
				+ responseMessage + ", umn=" + umn + ", ru=" + ru + ", customerName=" + customerName
				+ ", customerMobileNumber=" + customerMobileNumber + ", parentMandate=" + parentMandate
				+ ", mandateAttempt=" + mandateAttempt +", mandateExecutionDate=" + mandateExecutionDate + "]";
	}

}
