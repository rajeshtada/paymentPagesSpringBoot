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
@Table(name = "sms")
public class SMS implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "merchant_name")
	private String mName;

	@Column(name = "customer_number")
	private String cusNumber;
	// TODO
	// @Type(type = "text")
	@Column(name = "sms_body")
	private String smsBody;

	@Column(name = "merchant_number")
	private String merNumber;

	@Column(name = "status")
	private boolean status;

	@Column(name = "send_for")
	private String sendFor;

	/*
	 * @CreationTimestamp
	 * 
	 * @Column(name="created_date",updatable=false) private Date createdDate;
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getCusNumber() {
		return cusNumber;
	}

	public void setCusNumber(String cusNumber) {
		this.cusNumber = cusNumber;
	}

	public String getSmsBody() {
		return smsBody;
	}

	public void setSmsBody(String smsBody) {
		this.smsBody = smsBody;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	/*
	 * public Date getCreatedDate() { return createdDate; }
	 * 
	 * public void setCreatedDate(Date createdDate) { this.createdDate =
	 * createdDate; }
	 */

	public String getMerNumber() {
		return merNumber;
	}

	public void setMerNumber(String merNumber) {
		this.merNumber = merNumber;
	}

	public String getSendFor() {
		return sendFor;
	}

	public void setSendFor(String sendFor) {
		this.sendFor = sendFor;
	}

	@Override
	public String toString() {
		return "SMS [id=" + id + ", mName=" + mName + ", cusNumber=" + cusNumber + ", smsBody=" + smsBody
				+ ", merNumber=" + merNumber + ", status=" + status + ", sendFor=" + sendFor + "]";
	}

}
