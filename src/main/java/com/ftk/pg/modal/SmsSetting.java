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
@Table(name="sms_setting")
public class SmsSetting implements Serializable{

	private static final long serialVersionUID = 3403942781620577166L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sms_setting_id")
	private Long smsSettingId;
	
	@Column(name = "sender_id")
	@NotNull
	private String senderId;
	
	@Column(name = "receiver_id")
	@NotNull
	private String receiverId;
	
	@Column(name = "api_url")
	@NotNull
	private String apiURL;
	
	@Column(name = "username")
	@NotNull
	private String userName;
	
	@Column(name = "password")
	@NotNull
	private String password;
	
	@Column(name = "provider")
	@NotNull
	private String provider;
	
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

	@Column(name="sms_status")
	@ColumnDefault(value = "0")
	private boolean status;

	public Long getSmsSettingId() {
		return smsSettingId;
	}

	public void setSmsSettingId(Long smsSettingId) {
		this.smsSettingId = smsSettingId;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getApiURL() {
		return apiURL;
	}

	public void setApiURL(String apiURL) {
		this.apiURL = apiURL;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
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
		return "SmsSetting [smsSettingId=" + smsSettingId + ", senderId=" + senderId + ", receiverId=" + receiverId
				+ ", apiURL=" + apiURL + ", userName=" + userName + ", password=" + password + ", provider=" + provider
				+ ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", createdBy=" + createdBy
				+ ", modifiedBy=" + modifiedBy + ", status=" + status + "]";
	}
	
}
