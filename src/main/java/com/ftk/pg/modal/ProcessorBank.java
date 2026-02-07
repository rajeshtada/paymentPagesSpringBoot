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
@Table(name = "processor_bank")
public class ProcessorBank implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "bank_id")
	private Long bankId;

	@Column(name = "processor_bank_id")
	private String processorBankId;

	@Column(name = "processor_bank_name")
	private String processorBankName;

	@Column(name = "merchant_id")
	private Long mId;
	
	@Column(name = "processor")
	private String processor;

	@CreationTimestamp
	@Column(name = "created_date", updatable = false)
	private Date createdDate;

	@UpdateTimestamp
	@Column(name = "modified_date")
	private Date modifiedDate;

	@Column(name = "created_by", updatable = false)
	private Long createdBy;

	@Column(name = "modified_by")
	private Long modifiedBy;

	private boolean status;

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

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}


	public String getProcessorBankId() {
		return processorBankId;
	}

	public void setProcessorBankId(String processorBankId) {
		this.processorBankId = processorBankId;
	}

	public String getProcessorBankName() {
		return processorBankName;
	}

	public void setProcessorBankName(String processorBankName) {
		this.processorBankName = processorBankName;
	}

	public Long getmId() {
		return mId;
	}

	public void setmId(Long mId) {
		this.mId = mId;
	}

	

	@Override
	public String toString() {
		return "ProcessorBank [id=" + id + ", bankId=" + bankId + ", processorBankId=" + processorBankId
				+ ", processorBankName=" + processorBankName + ", mId=" + mId + ", processor=" + processor
				+ ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", createdBy=" + createdBy
				+ ", modifiedBy=" + modifiedBy + ", status=" + status + "]";
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

}
