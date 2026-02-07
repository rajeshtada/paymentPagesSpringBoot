package com.ftk.pg.pi.modal;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "form_transaction")
public class FormTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "mid")
	private Long mid;
	
	@Column(name = "transaction_id")
	private Long transactionId;

	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private Date createdDate;

	@Column(name = "modified_date")
	@UpdateTimestamp
	private Date modifiedDate;

	@Column(name = "txn_amount")
	private Double txnAmount;

	@Column(name = "form_name")
	private String formName;
	
	@Column(name = "form_id")
	private Long formId;
	
	@Column(name = "merchant_order_no")
	private String merchantOrderNo;
	
	@Column(name = "vpa")
	private String vpa;
	
	@Column(name = "receipt")
	private String receipt;
	
	@Column(name = "receipt_80g")
	private String receipt80G;
	
	@Column(name = "tc_content", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	public String tcContent;
	
	@Column(name = "udf1", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	public String udf1;

	@Column(name = "udf2", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	public String udf2;

	@Column(name = "udf3", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf3;

	// Address Reserved
	@Column(name = "udf11", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf11;

	// PAN Reserved
	@Column(name = "udf12", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf12;

	@Column(name = "udf13", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf13;

	@Column(name = "udf14", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf14;

	@Column(name = "udf15", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf15;

	@Column(name = "udf16", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf16;

	@Column(name = "udf17", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf17;

	@Column(name = "udf18", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf18;

	@Column(name = "udf19", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf19;

	@Column(name = "udf20", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf20;

	@Column(name = "udf21", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf21;

	@Column(name = "udf22", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf22;

	@Column(name = "udf23", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf23;

	@Column(name = "udf24", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf24;

	@Column(name = "udf25", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf25;
	
	@Column(name = "udf26", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf26;

	@Column(name = "udf27", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf27;

	@Column(name = "udf28", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf28;

	@Column(name = "udf29", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf29;

	@Column(name = "udf30", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf30;

	@Column(name = "udf31", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf31;

	@Column(name = "udf32", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf32;

	@Column(name = "udf33", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf33;

	@Column(name = "udf34", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf34;

	@Column(name = "udf35", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf35;

	@Column(name = "udf36", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf36;

	@Column(name = "udf37", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf37;

	@Column(name = "udf38", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf38;

	@Column(name = "udf39", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf39;

	@Column(name = "udf40", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf40;

	@Column(name = "udf41", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf41;

	@Column(name = "udf42", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf42;

	@Column(name = "udf43", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf43;

	@Column(name = "udf44", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf44;

	@Column(name = "udf45", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf45;

	@Column(name = "udf46", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf46;

	@Column(name = "udf47", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf47;

	@Column(name = "udf48", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf48;

	@Column(name = "udf49", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf49;

	@Column(name = "udf50", columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String udf50;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Double getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(Double txnAmount) {
		this.txnAmount = txnAmount;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
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

	public String getUdf11() {
		return udf11;
	}

	public void setUdf11(String udf11) {
		this.udf11 = udf11;
	}

	public String getUdf12() {
		return udf12;
	}

	public void setUdf12(String udf12) {
		this.udf12 = udf12;
	}

	public String getUdf13() {
		return udf13;
	}

	public void setUdf13(String udf13) {
		this.udf13 = udf13;
	}

	public String getUdf14() {
		return udf14;
	}

	public void setUdf14(String udf14) {
		this.udf14 = udf14;
	}

	public String getUdf15() {
		return udf15;
	}

	public void setUdf15(String udf15) {
		this.udf15 = udf15;
	}

	public String getUdf16() {
		return udf16;
	}

	public void setUdf16(String udf16) {
		this.udf16 = udf16;
	}

	public String getUdf17() {
		return udf17;
	}

	public void setUdf17(String udf17) {
		this.udf17 = udf17;
	}

	public String getUdf18() {
		return udf18;
	}

	public void setUdf18(String udf18) {
		this.udf18 = udf18;
	}

	public String getUdf19() {
		return udf19;
	}

	public void setUdf19(String udf19) {
		this.udf19 = udf19;
	}

	public String getUdf20() {
		return udf20;
	}

	public void setUdf20(String udf20) {
		this.udf20 = udf20;
	}

	public String getUdf21() {
		return udf21;
	}

	public void setUdf21(String udf21) {
		this.udf21 = udf21;
	}

	public String getUdf22() {
		return udf22;
	}

	public void setUdf22(String udf22) {
		this.udf22 = udf22;
	}

	public String getUdf23() {
		return udf23;
	}

	public void setUdf23(String udf23) {
		this.udf23 = udf23;
	}

	public String getUdf24() {
		return udf24;
	}

	public void setUdf24(String udf24) {
		this.udf24 = udf24;
	}

	public String getUdf25() {
		return udf25;
	}

	public void setUdf25(String udf25) {
		this.udf25 = udf25;
	}

	public String getUdf26() {
		return udf26;
	}

	public void setUdf26(String udf26) {
		this.udf26 = udf26;
	}

	public String getUdf27() {
		return udf27;
	}

	public void setUdf27(String udf27) {
		this.udf27 = udf27;
	}

	public String getUdf28() {
		return udf28;
	}

	public void setUdf28(String udf28) {
		this.udf28 = udf28;
	}

	public String getUdf29() {
		return udf29;
	}

	public void setUdf29(String udf29) {
		this.udf29 = udf29;
	}

	public String getUdf30() {
		return udf30;
	}

	public void setUdf30(String udf30) {
		this.udf30 = udf30;
	}

	public String getUdf31() {
		return udf31;
	}

	public void setUdf31(String udf31) {
		this.udf31 = udf31;
	}

	public String getUdf32() {
		return udf32;
	}

	public void setUdf32(String udf32) {
		this.udf32 = udf32;
	}

	public String getUdf33() {
		return udf33;
	}

	public void setUdf33(String udf33) {
		this.udf33 = udf33;
	}

	public String getUdf34() {
		return udf34;
	}

	public void setUdf34(String udf34) {
		this.udf34 = udf34;
	}

	public String getUdf35() {
		return udf35;
	}

	public void setUdf35(String udf35) {
		this.udf35 = udf35;
	}

	public String getUdf36() {
		return udf36;
	}

	public void setUdf36(String udf36) {
		this.udf36 = udf36;
	}

	public String getUdf37() {
		return udf37;
	}

	public void setUdf37(String udf37) {
		this.udf37 = udf37;
	}

	public String getUdf38() {
		return udf38;
	}

	public void setUdf38(String udf38) {
		this.udf38 = udf38;
	}

	public String getUdf39() {
		return udf39;
	}

	public void setUdf39(String udf39) {
		this.udf39 = udf39;
	}

	public String getUdf40() {
		return udf40;
	}

	public void setUdf40(String udf40) {
		this.udf40 = udf40;
	}

	public String getUdf41() {
		return udf41;
	}

	public void setUdf41(String udf41) {
		this.udf41 = udf41;
	}

	public String getUdf42() {
		return udf42;
	}

	public void setUdf42(String udf42) {
		this.udf42 = udf42;
	}

	public String getUdf43() {
		return udf43;
	}

	public void setUdf43(String udf43) {
		this.udf43 = udf43;
	}

	public String getUdf44() {
		return udf44;
	}

	public void setUdf44(String udf44) {
		this.udf44 = udf44;
	}

	public String getUdf45() {
		return udf45;
	}

	public void setUdf45(String udf45) {
		this.udf45 = udf45;
	}

	public String getUdf46() {
		return udf46;
	}

	public void setUdf46(String udf46) {
		this.udf46 = udf46;
	}

	public String getUdf47() {
		return udf47;
	}

	public void setUdf47(String udf47) {
		this.udf47 = udf47;
	}

	public String getUdf48() {
		return udf48;
	}

	public void setUdf48(String udf48) {
		this.udf48 = udf48;
	}

	public String getUdf49() {
		return udf49;
	}

	public void setUdf49(String udf49) {
		this.udf49 = udf49;
	}

	public String getUdf50() {
		return udf50;
	}

	public void setUdf50(String udf50) {
		this.udf50 = udf50;
	}

	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	public String getReceipt80G() {
		return receipt80G;
	}

	public void setReceipt80G(String receipt80g) {
		receipt80G = receipt80g;
	}

	public String getVpa() {
		return vpa;
	}

	public void setVpa(String vpa) {
		this.vpa = vpa;
	}

	public String getTcContent() {
		return tcContent;
	}

	public void setTcContent(String tcContent) {
		this.tcContent = tcContent;
	}

	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	@Override
	public String toString() {
		return "FormTransaction [id=" + id + ", mid=" + mid + ", transactionId=" + transactionId + ", createdDate="
				+ createdDate + ", modifiedDate=" + modifiedDate + ", txnAmount=" + txnAmount + ", formName=" + formName
				+ ", formId=" + formId + ", merchantOrderNo=" + merchantOrderNo + ", vpa=" + vpa + ", receipt="
				+ receipt + ", receipt80G=" + receipt80G + ", tcContent=" + tcContent + ", udf1=" + udf1 + ", udf2="
				+ udf2 + ", udf3=" + udf3 + ", udf11=" + udf11 + ", udf12=" + udf12 + ", udf13=" + udf13 + ", udf14="
				+ udf14 + ", udf15=" + udf15 + ", udf16=" + udf16 + ", udf17=" + udf17 + ", udf18=" + udf18 + ", udf19="
				+ udf19 + ", udf20=" + udf20 + ", udf21=" + udf21 + ", udf22=" + udf22 + ", udf23=" + udf23 + ", udf24="
				+ udf24 + ", udf25=" + udf25 + ", udf26=" + udf26 + ", udf27=" + udf27 + ", udf28=" + udf28 + ", udf29="
				+ udf29 + ", udf30=" + udf30 + ", udf31=" + udf31 + ", udf32=" + udf32 + ", udf33=" + udf33 + ", udf34="
				+ udf34 + ", udf35=" + udf35 + ", udf36=" + udf36 + ", udf37=" + udf37 + ", udf38=" + udf38 + ", udf39="
				+ udf39 + ", udf40=" + udf40 + ", udf41=" + udf41 + ", udf42=" + udf42 + ", udf43=" + udf43 + ", udf44="
				+ udf44 + ", udf45=" + udf45 + ", udf46=" + udf46 + ", udf47=" + udf47 + ", udf48=" + udf48 + ", udf49="
				+ udf49 + ", udf50=" + udf50 + "]";
	}
}
