package com.ftk.pg.pi.modal;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "payment_process")
public class PaymentProcessEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "party_name")
	private String merchantName;

	@Column(name = "invoice_date")
	private String invoiceDate;

	@Column(name = "mid")
	private Long mid;

	@Column(name = "created_date", updatable = true)
	@CreationTimestamp
	private LocalDateTime createDate;

	@Column(name = "modified_date")
	@CreationTimestamp
	private LocalDateTime modified_date;

	@Column(name = "invoice_amount")
	private String invoiceAmount;

	@Column(name = "payment_id")
	private Long paymentId;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@Column(name = "email_id")
	private String emailId;

	@Column(name = "invoice_no")
	private String invoiceNo;

	@Column(name = "pending_amount")
	private String dueAmount;

	@Column(name = "due_on")
	private LocalDate paymentDueDate;

	@Column(name = "last_remindersent")
	private LocalDate lastReminderSent;

	@Column(name = "status")
	private Integer status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getModified_date() {
		return modified_date;
	}

	public void setModified_date(LocalDateTime modified_date) {
		this.modified_date = modified_date;
	}

	public String getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(String invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(String dueAmount) {
		this.dueAmount = dueAmount;
	}

	public LocalDate getPaymentDueDate() {
		return paymentDueDate;
	}

	public void setPaymentDueDate(LocalDate paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}

	public LocalDate getLastReminderSent() {
		return lastReminderSent;
	}

	public void setLastReminderSent(LocalDate lastReminderSent) {
		this.lastReminderSent = lastReminderSent;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "PaymentProcessEntity [id=" + id + ", merchantName=" + merchantName + ", invoiceDate=" + invoiceDate
				+ ", mid=" + mid + ", createDate=" + createDate + ", modified_date=" + modified_date
				+ ", invoiceAmount=" + invoiceAmount + ", paymentId=" + paymentId + ", mobileNumber=" + mobileNumber
				+ ", emailId=" + emailId + ", invoiceNo=" + invoiceNo + ", dueAmount=" + dueAmount + ", paymentDueDate="
				+ paymentDueDate + ", lastReminderSent=" + lastReminderSent + ", status=" + status + "]";
	}

}
