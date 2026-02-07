package com.ftk.pg.modal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

//@Entity
//@Table(name = "getepay_gst_summary_data")
public class GetepayGstSummaryData implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(name = "mid")
	private Long mid;

	@Column(name = "month")
	private String month;

	@Column(name = "bussiness_type")
	private String bussinessType;

	@Column(name = "charge_type")
	private String chargeType;

	@Column(name = "charge_amount")
	private BigDecimal chargeAmount;

	@Column(name = "charge_gst_amount")
	private BigDecimal chargeGstAmount;

	@Column(name = "total_charge_amount")
	private BigDecimal totalChargeAmount;

	@Column(name = "status")
	private Integer status;

	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "remark")
	private String remark;

	@Column(name = "gst_no")
	private String gstNo;

	@Column(name = "udf1")
	private String udf1;

	@Column(name = "udf2")
	private String udf2;

	@Column(name = "udf3")
	private String udf3;

	@Column(name = "igst")
	private String igst;

	@Column(name = "cgst")
	private String cgst;

	@Column(name = "sgst")
	private String sgst;

	@Column(name = "invoice_number")
	private String invoiceNumber;

	@Column(name = "invoice_date")
	private LocalDate invoiceDate;

	@Column(name = "merchant_name")
	private String merchantName;

	@Column(name = "merchant_recovery_txnId")
	private String merchantRecoveryTxnId;

	@Column(name = "invoice_type")
	private String invoiceType;

	@Column(name = "address")
	private String address;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Transient
	private String accountHolderName;

	@Transient
	private String title;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getBussinessType() {
		return bussinessType;
	}

	public void setBussinessType(String bussinessType) {
		this.bussinessType = bussinessType;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public BigDecimal getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(BigDecimal chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public BigDecimal getChargeGstAmount() {
		return chargeGstAmount;
	}

	public void setChargeGstAmount(BigDecimal chargeGstAmount) {
		this.chargeGstAmount = chargeGstAmount;
	}

	public BigDecimal getTotalChargeAmount() {
		return totalChargeAmount;
	}

	public void setTotalChargeAmount(BigDecimal totalChargeAmount) {
		this.totalChargeAmount = totalChargeAmount;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
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

	public String getIgst() {
		return igst;
	}

	public void setIgst(String igst) {
		this.igst = igst;
	}

	public String getCgst() {
		return cgst;
	}

	public void setCgst(String cgst) {
		this.cgst = cgst;
	}

	public String getSgst() {
		return sgst;
	}

	public void setSgst(String sgst) {
		this.sgst = sgst;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantRecoveryTxnId() {
		return merchantRecoveryTxnId;
	}

	public void setMerchantRecoveryTxnId(String merchantRecoveryTxnId) {
		this.merchantRecoveryTxnId = merchantRecoveryTxnId;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "GetepayGstSummaryData [id=" + id + ", mid=" + mid + ", month=" + month + ", bussinessType="
				+ bussinessType + ", chargeType=" + chargeType + ", chargeAmount=" + chargeAmount + ", chargeGstAmount="
				+ chargeGstAmount + ", totalChargeAmount=" + totalChargeAmount + ", status=" + status + ", createdDate="
				+ createdDate + ", remark=" + remark + ", gstNo=" + gstNo + ", udf1=" + udf1 + ", udf2=" + udf2
				+ ", udf3=" + udf3 + ", igst=" + igst + ", cgst=" + cgst + ", sgst=" + sgst + ", invoiceNumber="
				+ invoiceNumber + ", invoiceDate=" + invoiceDate + ", merchantName=" + merchantName
				+ ", merchantRecoveryTxnId=" + merchantRecoveryTxnId + ", invoiceType=" + invoiceType + ", address="
				+ address + ", city=" + city + ", state=" + state + ", accountHolderName=" + accountHolderName
				+ ", title=" + title + "]";
	}

}
