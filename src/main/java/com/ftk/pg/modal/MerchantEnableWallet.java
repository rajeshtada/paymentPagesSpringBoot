package com.ftk.pg.modal;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "merchant_enable_wallet")
public class MerchantEnableWallet implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private long id;
	
	@Column(name = "wid")
	private long wId;
	
	@Column(name = "processor_id")
	private long processorId;
	
	@Column(name = "mid")
	private long mid;
	
	@Column(name = "processor_code")
	private String processorCode;
	
	@CreationTimestamp
	@Column(name = "created_date", updatable = false)
	private Date createdDate;
	
	@UpdateTimestamp
	@Column(name = "modified_date")
	private Date modifiedDate;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "modify_by")
	private String modifyBy;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getwId() {
		return wId;
	}

	public void setwId(long wId) {
		this.wId = wId;
	}

	public long getProcessorId() {
		return processorId;
	}

	public void setProcessorId(long processorId) {
		this.processorId = processorId;
	}

	public long getMid() {
		return mid;
	}

	public void setMid(long mid) {
		this.mid = mid;
	}

	public String getProcessorCode() {
		return processorCode;
	}

	public void setProcessorCode(String processorCode) {
		this.processorCode = processorCode;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "MerchantEnableWallet [id=" + id + ", wId=" + wId + ", processorId=" + processorId + ", mid=" + mid
				+ ", processorCode=" + processorCode + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate
				+ ", createdBy=" + createdBy + ", modifyBy=" + modifyBy + "]";
	}

}
