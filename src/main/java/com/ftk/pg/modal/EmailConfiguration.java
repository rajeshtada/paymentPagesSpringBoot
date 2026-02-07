package com.ftk.pg.modal;

import java.io.Serializable;

import org.hibernate.envers.Audited;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Audited
@Table(name = "email_configuration")
public class EmailConfiguration implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "trigger_code")
	private String triggerCode;

	@Column(name = "is_customer")
	private boolean isCustomer = false;

	@Column(name = "is_merchant")
	private boolean isMerchant = false;

	@Column(name = "to_id")
	private String to;

	@Column(name = "cc_id")
	private String cc;

	@Column(name = "bcc_id")
	private String bcc;

	@Column(name = "subject")
	private String subject;
	// TODO
	// @Type(type = "text")
	@Column(name = "body")
	private String body;

	@Column(name = "merchant_name")
	private String mName;

	@Column(name = "mid")
	private Long mId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTriggerCode() {
		return triggerCode;
	}

	public void setTriggerCode(String triggerCode) {
		this.triggerCode = triggerCode;
	}

	public boolean isCustomer() {
		return isCustomer;
	}

	public void setCustomer(boolean isCustomer) {
		this.isCustomer = isCustomer;
	}

	public boolean isMerchant() {
		return isMerchant;
	}

	public void setMerchant(boolean isMerchant) {
		this.isMerchant = isMerchant;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public Long getmId() {
		return mId;
	}

	public void setmId(Long mId) {
		this.mId = mId;
	}

	@Override
	public String toString() {
		return "EmailConfiguration [id=" + id + ", triggerCode=" + triggerCode + ", isCustomer=" + isCustomer
				+ ", isMerchant=" + isMerchant + ", to=" + to + ", cc=" + cc + ", bcc=" + bcc + ", subject=" + subject
				+ ", body=" + body + ", mName=" + mName + ", mId=" + mId + "]";
	}

}
