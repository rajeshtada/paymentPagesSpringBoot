package com.ftk.pg.modal;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@Table(name = "partner_notification")
public class PartnerNotification implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "mid")
	private Long mid;
	
	@Column(name = "bank_id")
	private Long bankId;
	
	@Column(name = "status")
	private int status;
	
	@CreationTimestamp
	@Column(name = "created_date", updatable = false)
	private LocalDateTime createdDate;
	
	@UpdateTimestamp
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;
	
	@Column(name = "message_flag")
	private boolean messageFlag;

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

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public boolean isMessageFlag() {
		return messageFlag;
	}

	public void setMessageFlag(boolean messageFlag) {
		this.messageFlag = messageFlag;
	}

	@Override
	public String toString() {
		return "PartnerNotification [id=" + id + ", mid=" + mid + ", bankId=" + bankId + ", status=" + status
				+ ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", messageFlag=" + messageFlag
				+ "]";
	}
	
	

}
