package com.ftk.pg.modal;

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
@Table(name = "wallet_processor_holder")
public class WalletProcessorHolder 
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private long id;
	
	@Column(name = "wid")
	private long wId;
	
	@Column(name = "pid")
	private long pId;
	
	@Column(name = "p_name")
	private String pName;
	
	@Column(name = "p_code")
	private String PCode;
	
	@Column(name = "status")
	private boolean status;
	
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

	public long getpId() {
		return pId;
	}

	public void setpId(long pId) {
		this.pId = pId;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getPCode() {
		return PCode;
	}

	public void setPCode(String pCode) {
		PCode = pCode;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
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
		return "WalletProcessorHolder [id=" + id + ", wId=" + wId + ", pId=" + pId + ", pName=" + pName + ", PCode="
				+ PCode + ", status=" + status + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate
				+ ", createdBy=" + createdBy + ", modifyBy=" + modifyBy + "]";
	}
	
}

