package com.ftk.pg.modal;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Audited
@Table(name = "notification_trigger")
public class NotificationTrigger implements Serializable  {

	private static final long serialVersionUID = -5056900389797947179L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notification_trigger_id")
	private Long id;
	
	@Column(name = "trigger_name")
	@NotNull
	private String triggerName;
	
	@Column(name = "trigger_code")
	@NotNull
	private String triggerCode;
	
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

	@Column(name="trigger_status")
	@ColumnDefault(value = "0")
	private boolean status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getTriggerCode() {
		return triggerCode;
	}

	public void setTriggerCode(String triggerCode) {
		this.triggerCode = triggerCode;
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

	@Override
	public String toString() {
		return "NotificationTrigger [id=" + id + ", triggerName=" + triggerName + ", triggerCode=" + triggerCode
				+ ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", createdBy=" + createdBy
				+ ", modifiedBy=" + modifiedBy + ", status=" + status + "]";
	}
	
}
