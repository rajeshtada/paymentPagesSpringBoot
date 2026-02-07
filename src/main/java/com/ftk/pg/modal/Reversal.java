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

@Entity
@Table(name = "reversal")
public class Reversal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "transaction_id")
	private Long transactionId;
	
	@Column(name = "rrn")
	private String rrn;
	
	@Column(name = "mid")
	private Long mid ;
	
	@Column(name = "vpa")
	private String vpa;
	

	@Column(name = "refund_id")
	private Long refundId ;
	
	@Column(name = "refund_status")
	private String refundStatus;
	
	@Column(name = "status")
	private int status;
	

	@Column(name = "remark")
	private String remark;
	
	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "modified_date")
	@UpdateTimestamp
	private LocalDateTime modifiedDate;
	
	@Column(name = "bank_utr_no")
	private String bankUtrNo;
	
	@Column(name = "refund_process_date")
	private LocalDateTime refundProcessDate;
		
	@Column(name = "udf1")
	private String udf1;	

	@Column(name = "udf2")
	private String udf2;
	
	@Column(name = "udf3")
	private String udf3;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public String getVpa() {
		return vpa;
	}

	public void setVpa(String vpa) {
		this.vpa = vpa;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	
	public Long getRefundId() {
		return refundId;
	}

	public void setRefundId(Long refundId) {
		this.refundId = refundId;
	}

	public String getBankUtrNo() {
		return bankUtrNo;
	}

	public void setBankUtrNo(String bankUtrNo) {
		this.bankUtrNo = bankUtrNo;
	}

	public LocalDateTime getRefundProcessDate() {
		return refundProcessDate;
	}

	public void setRefundProcessDate(LocalDateTime refundProcessDate) {
		this.refundProcessDate = refundProcessDate;
	}

	@Override
	public String toString() {
		return "Reversal [id=" + id + ", transactionId=" + transactionId + ", rrn=" + rrn + ", mid=" + mid + ", vpa="
				+ vpa + ", refundId=" + refundId + ", refundStatus=" + refundStatus + ", status=" + status + ", remark="
				+ remark + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", bankUtrNo="
				+ bankUtrNo + ", refundProcessDate=" + refundProcessDate + ", udf1=" + udf1 + ", udf2=" + udf2
				+ ", udf3=" + udf3 + "]";
	}

}

