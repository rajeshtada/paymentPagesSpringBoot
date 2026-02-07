package com.ftk.pg.modal;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "lyra_onboarding")
public class LyraOnBoardingResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "creation_date")
	private String creationDate;

	@Column(name = "registration_id")
	private String registrationId;

	@Column(name = "merchant_name")
	private String merchantName;

	@Column(name = "mid")
	private String mid;

	@Column(name = "tid")
	private String tid;

	@Column(name = "status")
	private String status;

	// extra fields
	@Column(name = "timestamp")
	private String timestamp;

	@Column(name = "error")
	private String error;

	@Column(name = "path")
	private String path;

	// duplicate merchant fields
	@Column(name = "version")
	private String version;

	@Column(name = "response_time")
	private String responseTime;

	@Column(name = "code")
	private String code;

	@Column(name = "message")
	private String message;

	@Column(name = "causes")
	private String causes;

	// Getter Methods

	public String getCreationDate() {
		return creationDate;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public String getMid() {
		return mid;
	}

	public String getTid() {
		return tid;
	}

	public String getStatus() {
		return status;
	}

	// Setter Methods

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCauses() {
		return causes;
	}

	public void setCauses(String causes) {
		this.causes = causes;
	}

	@Override
	public String toString() {
		return "LyraOnBoardingSuccessResponse [creationDate=" + creationDate + ", registrationId=" + registrationId
				+ ", merchantName=" + merchantName + ", mid=" + mid + ", tid=" + tid + ", status=" + status
				+ ", timestamp=" + timestamp + ", error=" + error + ", path=" + path + ", version=" + version
				+ ", responseTime=" + responseTime + ", code=" + code + ", message=" + message + ", causes=" + causes
				+ "]";
	}

}