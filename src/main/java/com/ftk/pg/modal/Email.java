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
@Table(name="email")
public class Email  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="customer_email")
	private String customerMail;
	
	@Column(name="merchant_email")
	private String merchantMail;
	
	@Column(name="to_id")
	private String to;
	
	@Column(name="cc_id")
	private String cc;
	
	@Column(name="bcc_id")
	private String bcc;
	
	@Column(name="subject")
	private String subject;
	
	//TODO
	//@Type(type="text")
	@Column(name="body")
	private String body;
	
	@Column(name="status")
	private Boolean status;
	
	@Column(name="send_for")
	private String sendFor;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustomerMail() {
		return customerMail;
	}

	public void setCustomerMail(String customerMail) {
		this.customerMail = customerMail;
	}

	public String getMerchantMail() {
		return merchantMail;
	}

	public void setMerchantMail(String merchantMail) {
		this.merchantMail = merchantMail;
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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getSendFor() {
		return sendFor;
	}

	public void setSendFor(String sendFor) {
		this.sendFor = sendFor;
	}

	@Override
	public String toString() {
		return "Email [id=" + id + ", customerMail=" + customerMail + ", merchantMail=" + merchantMail + ", to=" + to
				+ ", cc=" + cc + ", bcc=" + bcc + ", subject=" + subject + ", body=" + body + ", status=" + status
				+ ", sendFor=" + sendFor + "]";
	}
	
}
