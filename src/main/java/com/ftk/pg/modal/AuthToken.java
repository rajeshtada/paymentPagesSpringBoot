package com.ftk.pg.modal;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "auth_token")
@Entity
public class AuthToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "mid")
	private Long mid;

	@Column(name = "created_date")
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "token")
	private String token;

	@Column(name = "otp")
	private String otp;

	@Column(name = "status")
	private int status;

	@Column(name = "expired_date")
	private LocalDateTime expiredDate;

	@Column(name = "valid_date")
	@CreationTimestamp
	private LocalDateTime validDate;

	@Column(name = "type")
	private String type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public LocalDateTime getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(LocalDateTime expiredDate) {
		this.expiredDate = expiredDate;
	}

	public LocalDateTime getValidDate() {
		return validDate;
	}

	public void setValidDate(LocalDateTime validDate) {
		this.validDate = validDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "AuthToken [id=" + id + ", username=" + username + ", mid=" + mid + ", createdDate=" + createdDate
				+ ", token=" + token + ", otp=" + otp + ", status=" + status + ", expiredDate=" + expiredDate
				+ ", validDate=" + validDate + ", type=" + type + "]";
	}

}
