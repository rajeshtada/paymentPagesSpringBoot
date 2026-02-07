package com.ftk.pg.modal;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "paymode_limitation")
public class PaymodeLimitation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "payment_mode")
	private String paymentMode;

	@Column(name = "daily_limit")
	private String dailyLimit;

	@Column(name = "weekly_limit")
	private String weeklyLimit;

	@Column(name = "monthly_limit")
	private String monthlyLimit;

	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "modified_date")
	@UpdateTimestamp
	private LocalDateTime modifiedDate;

	@Column(name = "created_by", updatable = false)
	private String createdBy;

	@Column(name = "modified_by")
	private String modifiedBy;

	private boolean enabled;

	private Boolean defaultRisk;

	private Long mid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getDailyLimit() {
		return dailyLimit;
	}

	public void setDailyLimit(String dailyLimit) {
		this.dailyLimit = dailyLimit;
	}

	public String getWeeklyLimit() {
		return weeklyLimit;
	}

	public void setWeeklyLimit(String weeklyLimit) {
		this.weeklyLimit = weeklyLimit;
	}

	public String getMonthlyLimit() {
		return monthlyLimit;
	}

	public void setMonthlyLimit(String monthlyLimit) {
		this.monthlyLimit = monthlyLimit;
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public Boolean getDefaultRisk() {
		return defaultRisk;
	}

	public void setDefaultRisk(Boolean defaultRisk) {
		this.defaultRisk = defaultRisk;
	}

	@Override
	public String toString() {
		return "PaymodeLimitation [id=" + id + ", paymentMode=" + paymentMode + ", dailyLimit=" + dailyLimit
				+ ", weeklyLimit=" + weeklyLimit + ", monthlyLimit=" + monthlyLimit + ", createdDate=" + createdDate
				+ ", modifiedDate=" + modifiedDate + ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy
				+ ", enabled=" + enabled + ", defaultRisk=" + defaultRisk + ", mid=" + mid + "]";
	}

}
