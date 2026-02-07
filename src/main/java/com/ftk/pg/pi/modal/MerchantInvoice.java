package com.ftk.pg.pi.modal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// import org.hibernate.annotations.ColumnDefault;
// import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "merchant_invoice")
public class MerchantInvoice implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1l;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "mid")
	private Long mId;

	@Column(name = "invoice_no")
	private String invoiceNo;

	@Column(name = "invoice_no_type")
	private Integer invoiceNoType; // 0=> default 1=> manual

	@Column(name = "merchant_name")
	private String merchantName;

	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "currency")
	private String currency;

	@Column(name = "paymentMode")
	private String paymentMode;

	@Column(name = "customer_name")
	private String customerName;
	
	@Column(name = "customer_id")
	private Long customerId;

	@Column(name = "customer_emailId")
	private String customerEmailId;

	@Column(name = "customer_mobile_number")
	private String customerMobileNo;

	@Column(name = "token")
	private String token;

	@Column(name = "expireTime", nullable = false)
	private Date expireTime;

	private String productType;

	@Column(name = "created_date", updatable = false)
	// @CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "email_status")
	// @ColumnDefault(value = "0")
	private Boolean emailStatus;

	@Column(name = "payment_status")
	// @ColumnDefault(value = "0")
	private Boolean payStatus;

	@Column(name = "txn_id")
	private Long txnId;

	@Column(name = "isSMS")
	// @ColumnDefault(value = "0")
	private int isSMS;

	@Column(name = "isEMail")
	// @ColumnDefault(value = "0")
	private int isEMail;

//
	@Column(name = "item_name")
	private String itemName;

	@Column(name = "item_order_number")
	private String itemOrderNumber;

	@Column(name = "commission_amount")
	private String commissionAmount;

	private String igst;

	private String sgst;

	private String cgst;

	@Column(name = "total_amount")
	private BigDecimal totalAmount;

	@Column(name = "commission_type")
	private String commissionType;

	@Column(name = "item_serial_no")
	private String itemSerialNo;

	@Column(name = "invoice_type")
	private String invoiceType;

	@Column(name = "recursive_date")
	private String recursiveDate;

	@Column(name = "coupon_code")
	private String couponCode;

	@Column(name = "discount")
	private String discount;

	@Column(name = "due_charges")
	private String dueCharges;

	@Column(name = "customer_gst_no")
	private String customerGstNo;

	@Column(name = "invoice_notes")
	private String invoiceNotes;

	@Column(name = "invoice_description")
	private String invoiceDescription;

	@Column(name = "vpa")
	private String vpa;

	@Column(name = "expiryDate")
	private Date expiryDate;

	@Column(name = "fine")
	private BigDecimal fine;

	@Column(name = "udf1") // block Id
	private Long udf1;

	@Column(name = "udf2") // flat Id
	private Long udf2;

	@Column(name = "udf3")
	private Long udf3;

	@Column(name = "udf4")
	private Long udf4;

	@Column(name = "udf5")
	private String udf5; // Block name / bId

	@Column(name = "udf6") // Flat name / sId
	private String udf6;

	@Column(name = "udf7")
	private String udf7; // merchant order no

	@Column(name = "udf8")
	private String udf8;

	@Column(name = "udf9") // from date
	private LocalDate udf9;

	@Column(name = "udf10") // to date
	private LocalDate udf10;

	@Column(name = "status")
	// @ColumnDefault(value = "1")
	private Integer status;

	@Column(name = "type", nullable = false, columnDefinition = "int default 0")
	private int type; // 0 old invoice and 1 new format invoice
	
	@Column(name = "cess_tax") 
	private String cessTax;
	
	private String udf11; // invoicing

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getmId() {
		return mId;
	}

	public void setmId(Long mId) {
		this.mId = mId;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmailId() {
		return customerEmailId;
	}

	public void setCustomerEmailId(String customerEmailId) {
		this.customerEmailId = customerEmailId;
	}

	public String getCustomerMobileNo() {
		return customerMobileNo;
	}

	public void setCustomerMobileNo(String customerMobileNo) {
		this.customerMobileNo = customerMobileNo;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public void setExpireTime(int minute) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, minute);
		this.expireTime = now.getTime();
	}

	public boolean isExpired() {
		return new Date().after(this.expireTime);
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public Boolean getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(Boolean emailStatus) {
		this.emailStatus = emailStatus;
	}

	public Boolean getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Boolean payStatus) {
		this.payStatus = payStatus;
	}

	public Long getTxnId() {
		return txnId;
	}

	public void setTxnId(Long txnId) {
		this.txnId = txnId;
	}

	public int getIsSMS() {
		return isSMS;
	}

	public void setIsSMS(int isSMS) {
		this.isSMS = isSMS;
	}

	public int getIsEMail() {
		return isEMail;
	}

	public void setIsEMail(int isEMail) {
		this.isEMail = isEMail;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemOrderNumber() {
		return itemOrderNumber;
	}

	public void setItemOrderNumber(String itemOrderNumber) {
		this.itemOrderNumber = itemOrderNumber;
	}

	public String getCommissionAmount() {
		return commissionAmount;
	}

	public void setCommissionAmount(String commissionAmount) {
		this.commissionAmount = commissionAmount;
	}

	public String getIgst() {
		return igst;
	}

	public void setIgst(String igst) {
		this.igst = igst;
	}

	public String getSgst() {
		return sgst;
	}

	public void setSgst(String sgst) {
		this.sgst = sgst;
	}

	public String getCgst() {
		return cgst;
	}

	public void setCgst(String cgst) {
		this.cgst = cgst;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getCommissionType() {
		return commissionType;
	}

	public void setCommissionType(String commissionType) {
		this.commissionType = commissionType;
	}

	public String getItemSerialNo() {
		return itemSerialNo;
	}

	public void setItemSerialNo(String itemSerialNo) {
		this.itemSerialNo = itemSerialNo;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getRecursiveDate() {
		return recursiveDate;
	}

	public void setRecursiveDate(String recursiveDate) {
		this.recursiveDate = recursiveDate;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getDueCharges() {
		return dueCharges;
	}

	public void setDueCharges(String dueCharges) {
		this.dueCharges = dueCharges;
	}

	public String getCustomerGstNo() {
		return customerGstNo;
	}

	public void setCustomerGstNo(String customerGstNo) {
		this.customerGstNo = customerGstNo;
	}

	public String getInvoiceNotes() {
		return invoiceNotes;
	}

	public void setInvoiceNotes(String invoiceNotes) {
		this.invoiceNotes = invoiceNotes;
	}

	public String getInvoiceDescription() {
		return invoiceDescription;
	}

	public void setInvoiceDescription(String invoiceDescription) {
		this.invoiceDescription = invoiceDescription;
	}

	@Override
	public String toString() {
		return "Invoice [id=" + id + ", mId=" + mId + ", invoiceNo=" + invoiceNo + ", invoiceNoType=" + invoiceNoType
				+ ", merchantName=" + merchantName + ", amount=" + amount + ", currency=" + currency + ", paymentMode="
				+ paymentMode + ", customerName=" + customerName + ", customerId=" + customerId + ", customerEmailId="
				+ customerEmailId + ", customerMobileNo=" + customerMobileNo + ", token=" + token + ", expireTime="
				+ expireTime + ", productType=" + productType + ", createdDate=" + createdDate + ", emailStatus="
				+ emailStatus + ", payStatus=" + payStatus + ", txnId=" + txnId + ", isSMS=" + isSMS + ", isEMail="
				+ isEMail + ", itemName=" + itemName + ", itemOrderNumber=" + itemOrderNumber + ", commissionAmount="
				+ commissionAmount + ", igst=" + igst + ", sgst=" + sgst + ", cgst=" + cgst + ", totalAmount="
				+ totalAmount + ", commissionType=" + commissionType + ", itemSerialNo=" + itemSerialNo
				+ ", invoiceType=" + invoiceType + ", recursiveDate=" + recursiveDate + ", couponCode=" + couponCode
				+ ", discount=" + discount + ", dueCharges=" + dueCharges + ", customerGstNo=" + customerGstNo
				+ ", invoiceNotes=" + invoiceNotes + ", invoiceDescription=" + invoiceDescription + ", vpa=" + vpa
				+ ", expiryDate=" + expiryDate + ", fine=" + fine + ", udf1=" + udf1 + ", udf2=" + udf2 + ", udf3="
				+ udf3 + ", udf4=" + udf4 + ", udf5=" + udf5 + ", udf6=" + udf6 + ", udf7=" + udf7 + ", udf8=" + udf8
				+ ", udf9=" + udf9 + ", udf10=" + udf10 + ", status=" + status + ", type=" + type + ", cessTax="
				+ cessTax + ", udf11=" + udf11 + "]";
	}

	public String getVpa() {
		return vpa;
	}

	public void setVpa(String vpa) {
		this.vpa = vpa;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public BigDecimal getFine() {
		return fine;
	}

	public void setFine(BigDecimal fine) {
		this.fine = fine;
	}

	public Long getUdf1() {
		return udf1;
	}

	public void setUdf1(Long udf1) {
		this.udf1 = udf1;
	}

	public Long getUdf2() {
		return udf2;
	}

	public void setUdf2(Long udf2) {
		this.udf2 = udf2;
	}

	public Long getUdf3() {
		return udf3;
	}

	public void setUdf3(Long udf3) {
		this.udf3 = udf3;
	}

	public Long getUdf4() {
		return udf4;
	}

	public void setUdf4(Long udf4) {
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

	public LocalDate getUdf9() {
		return udf9;
	}

	public void setUdf9(LocalDate udf9) {
		this.udf9 = udf9;
	}

	public LocalDate getUdf10() {
		return udf10;
	}

	public void setUdf10(LocalDate udf10) {
		this.udf10 = udf10;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public Integer getInvoiceNoType() {
		return invoiceNoType;
	}

	public void setInvoiceNoType(Integer invoiceNoType) {
		this.invoiceNoType = invoiceNoType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCessTax() {
		return cessTax;
	}

	public void setCessTax(String cessTax) {
		this.cessTax = cessTax;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getUdf11() {
		return udf11;
	}

	public void setUdf11(String udf11) {
		this.udf11 = udf11;
	}

	
}

    

