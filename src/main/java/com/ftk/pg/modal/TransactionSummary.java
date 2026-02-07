package com.ftk.pg.modal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction_summary")

public class TransactionSummary implements Serializable{
	
	private static final long serialVersionUID = -937542610394637133L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "transaction_id")
	private Long transactionId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "amount")
	private BigDecimal amt;
	
	@Column(name = "merchant_id")
	private Long merchantId;
	
	@Column(name = "merchant_name")
	private String merchantname;
	
	@Column(name = "merchant_txn_id")
	private String merchantTxnId;
	
	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;
	
	@Column(name = "transaction_date_time", updatable = false)
	@CreationTimestamp
	private LocalDateTime transactiondatetime;
	
	@Column(name = "payment_mode")
	private String paymentmode;
	
	@Column(name = "processor")
	private String processor;
	
	@Column(name = "transaction_status")
	private String transactionstatus;
	
	@Column(name = "rrn")
	private String rrn;
	
	@Column(name = "vpa")
	private String vpa;
	
	@Column(name = "merchant_settlement_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime merchantsettlementdate;
	
	@Column(name = "merchant_settlement_amount")
	private BigDecimal merchantsettlementamount;
	
	@Column(name = "merchant_settlement_ref_no")
	private String merchantsettlementref_no;
	
	@Column(name = "settlement_status")
	private int settlementstatus;

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantname() {
		return merchantname;
	}

	public void setMerchantname(String merchantname) {
		this.merchantname = merchantname;
	}

	public String getMerchantTxnId() {
		return merchantTxnId;
	}

	public void setMerchantTxnId(String merchantTxnId) {
		this.merchantTxnId = merchantTxnId;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getTransactiondatetime() {
		return transactiondatetime;
	}

	public void setTransactiondatetime(LocalDateTime transactiondatetime) {
		this.transactiondatetime = transactiondatetime;
	}

	public String getPaymentmode() {
		return paymentmode;
	}

	public void setPaymentmode(String paymentmode) {
		this.paymentmode = paymentmode;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public String getTransactionstatus() {
		return transactionstatus;
	}

	public void setTransactionstatus(String transactionstatus) {
		this.transactionstatus = transactionstatus;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public String getVpa() {
		return vpa;
	}

	public void setVpa(String vpa) {
		this.vpa = vpa;
	}

	public LocalDateTime getMerchantsettlementdate() {
		return merchantsettlementdate;
	}

	public void setMerchantsettlementdate(LocalDateTime merchantsettlementdate) {
		this.merchantsettlementdate = merchantsettlementdate;
	}

	public BigDecimal getMerchantsettlementamount() {
		return merchantsettlementamount;
	}

	public void setMerchantsettlementamount(BigDecimal merchantsettlementamount) {
		this.merchantsettlementamount = merchantsettlementamount;
	}

	public String getMerchantsettlementref_no() {
		return merchantsettlementref_no;
	}

	public void setMerchantsettlementref_no(String merchantsettlementref_no) {
		this.merchantsettlementref_no = merchantsettlementref_no;
	}

	public int getSettlementstatus() {
		return settlementstatus;
	}

	public void setSettlementstatus(int settlementstatus) {
		this.settlementstatus = settlementstatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "TransactionSummary [id=" + id + ", transactionId=" + transactionId + ", amt=" + amt + ", merchantId="
				+ merchantId + ", merchantname=" + merchantname + ", merchantTxnId=" + merchantTxnId + ", createdDate="
				+ createdDate + ", transactiondatetime=" + transactiondatetime + ", paymentmode=" + paymentmode
				+ ", processor=" + processor + ", transactionstatus=" + transactionstatus + ", rrn=" + rrn + ", vpa="
				+ vpa + ", merchantsettlementdate=" + merchantsettlementdate + ", merchantsettlementamount="
				+ merchantsettlementamount + ", merchantsettlementref_no=" + merchantsettlementref_no
				+ ", settlementstatus=" + settlementstatus + "]";
	}

	
}
