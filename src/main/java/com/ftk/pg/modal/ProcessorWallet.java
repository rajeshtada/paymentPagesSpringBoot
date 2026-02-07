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
@Table(name = "processor_wallet")
public class ProcessorWallet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "wallet_id")
	private Long walletId;

	@Column(name = "processor_wallet_id")
	private String processorwalletId;

	@Column(name = "processor_wallet_name")
	private String processorWalletName;

	@Column(name = "merchant_id")
	private Long mId;

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

	@Column(name = "processor")
	private String processor;
	
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

	public Long getWalletId() {
		return walletId;
	}

	public void setWalletId(Long walletId) {
		this.walletId = walletId;
	}



	public String getProcessorWalletName() {
		return processorWalletName;
	}

	public void setProcessorWalletName(String processorWalletName) {
		this.processorWalletName = processorWalletName;
	}

	public Long getmId() {
		return mId;
	}

	public void setmId(Long mId) {
		this.mId = mId;
	}

	@Override
	public String toString() {
		return "ProcessorWallet [id=" + id + ", walletId=" + walletId + ", processorwalletId=" + processorwalletId
				+ ", processorWalletName=" + processorWalletName + ", mId=" + mId + ", createdDate=" + createdDate
				+ ", modifiedDate=" + modifiedDate + ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy
				+ ", status=" + status + "]";
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public String getProcessorwalletId() {
		return processorwalletId;
	}

	public void setProcessorwalletId(String processorwalletId) {
		this.processorwalletId = processorwalletId;
	}

}
