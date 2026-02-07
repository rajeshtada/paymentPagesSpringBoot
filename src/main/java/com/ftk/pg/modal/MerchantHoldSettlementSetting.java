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

@Table(name = "merchant_hold_settlement_setting")
@Entity
public class MerchantHoldSettlementSetting implements Serializable {

	private static final long serialVersionUID = 8306443398220063100L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	private Long mid;

	@Column(name = "payment_mode")
	private String paymentMode;

	@Column(name = "hold_type")
	private String holdType;

	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "updated_date")
	@UpdateTimestamp
	private LocalDateTime updatedDate;

	@Column(name = "created_by", updatable = false)
	private String createdBy;

	@Column(name = "modify_by")
	private String modifyBy;

	@Column(name = "status", nullable = false, columnDefinition = "int default 0")
	private int status;

	@Column(name = "vpa")
	private String vpa;

	@Column(name = "reason")
	private String reason;

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

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getHoldType() {
		return holdType;
	}

	public void setHoldType(String holdType) {
		this.holdType = holdType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getVpa() {
		return vpa;
	}

	public void setVpa(String vpa) {
		this.vpa = vpa;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "MerchantHoldSettlementSetting [id=" + id + ", mid=" + mid + ", paymentMode=" + paymentMode
				+ ", holdType=" + holdType + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate
				+ ", createdBy=" + createdBy + ", modifyBy=" + modifyBy + ", status=" + status + ", vpa=" + vpa
				+ ", reason=" + reason + "]";
	}

}
