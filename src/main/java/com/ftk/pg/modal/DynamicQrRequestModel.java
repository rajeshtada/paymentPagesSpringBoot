package com.ftk.pg.modal;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Audited
@Table(name = "dynamic_qr_request")
public class DynamicQrRequestModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -937542610394637133L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transaction_id")
	private Long transactionId;

	@Column(name = "merchant_txn_id")
	private String merchanttxnid;

	@Column(name = "transaction_date_time")
	private String date;

	@Column(name = "transaction_currency")
	private String txncurr;

	@Column(name = "amount")
	private BigDecimal amt;


	@Column(name = "response_code")
	private String responseCode;

	@Column(name = "transaction_status")
	private String txnStatus;

	private String processor;

	private String remarks;

	private String mobile;

	@Column(name = "merchant_id")
	private Long merchantId;

	@Column(name = "merchant_name")
	private String merchantName;

	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "modified_date")
	@UpdateTimestamp
	private LocalDateTime modifiedDate;

	@Column(name = "commison")
	private BigDecimal commision;

	@Column(name = "terminal_id")
	private String terminalId;

	@Column(name = "productType")
	private String productType;
	
	@Column(name = "expiry_date")
	private String expiryDate;
	
	@Column(name = "minimum_amount")
	private String minimumAmount;
	
	private String vpa; 

	private String udf1;

	private String udf2;

	private String udf3;

	private String udf4;

	private String udf5;

	private String udf6;

	private String udf7; // cusREfno

	private String udf8;

	private String udf9;

	private String udf10;

	private String txnType;

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getMerchanttxnid() {
		return merchanttxnid;
	}

	public void setMerchanttxnid(String merchanttxnid) {
		this.merchanttxnid = merchanttxnid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTxncurr() {
		return txncurr;
	}

	public void setTxncurr(String txncurr) {
		this.txncurr = txncurr;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getTxnStatus() {
		return txnStatus;
	}

	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	public BigDecimal getCommision() {
		return commision;
	}

	public void setCommision(BigDecimal commision) {
		this.commision = commision;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getMinimumAmount() {
		return minimumAmount;
	}

	public void setMinimumAmount(String minimumAmount) {
		this.minimumAmount = minimumAmount;
	}

	public String getVpa() {
		return vpa;
	}

	public void setVpa(String vpa) {
		this.vpa = vpa;
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

	public String getUdf4() {
		return udf4;
	}

	public void setUdf4(String udf4) {
		this.udf4 = udf4;
	}

	public String getUdf5() {
		return udf5;
	}

	public void setUdf5(String udf5) {
		this.udf5 = udf5;
	}

	public String getUdf6() {
		return udf6;
	}

	public void setUdf6(String udf6) {
		this.udf6 = udf6;
	}

	public String getUdf7() {
		return udf7;
	}

	public void setUdf7(String udf7) {
		this.udf7 = udf7;
	}

	public String getUdf8() {
		return udf8;
	}

	public void setUdf8(String udf8) {
		this.udf8 = udf8;
	}

	public String getUdf9() {
		return udf9;
	}

	public void setUdf9(String udf9) {
		this.udf9 = udf9;
	}

	public String getUdf10() {
		return udf10;
	}

	public void setUdf10(String udf10) {
		this.udf10 = udf10;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	@Override
	public String toString() {
		return "DynamicQrRequestModel [transactionId=" + transactionId + ", merchanttxnid=" + merchanttxnid + ", date="
				+ date + ", txncurr=" + txncurr + ", amt=" + amt + ", responseCode=" + responseCode + ", txnStatus="
				+ txnStatus + ", processor=" + processor + ", remarks=" + remarks + ", mobile=" + mobile
				+ ", merchantId=" + merchantId + ", merchantName=" + merchantName + ", createdDate=" + createdDate
				+ ", modifiedDate=" + modifiedDate + ", commision=" + commision + ", terminalId=" + terminalId
				+ ", productType=" + productType + ", expiryDate=" + expiryDate + ", minimumAmount=" + minimumAmount
				+ ", vpa=" + vpa + ", udf1=" + udf1 + ", udf2=" + udf2 + ", udf3=" + udf3 + ", udf4=" + udf4 + ", udf5="
				+ udf5 + ", udf6=" + udf6 + ", udf7=" + udf7 + ", udf8=" + udf8 + ", udf9=" + udf9 + ", udf10=" + udf10
				+ ", txnType=" + txnType + "]";
	}

	
}
