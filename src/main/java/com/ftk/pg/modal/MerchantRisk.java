package com.ftk.pg.modal;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "merchant_risk")
public class MerchantRisk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4080126914975932136L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	private Long mid;

	@Column(name = "max_no_of_txn_daily")
	private int maxNoOfTxnDaily;

	@Column(name = "max_no_of_txn_daily_amt")
	private BigDecimal maxNoOfTxnDailyAmt;

	@Column(name = "max_no_of_txn_weekly")
	private int maxNoOfTxnWeekly;

	@Column(name = "max_no_of_txn_weekly_amt")
	private BigDecimal maxNoOfTxnWeeklyAmt;

	@Column(name = "max_no_of_txn_monthly")
	private int maxNoOfTxnMonthly;

	@Column(name = "max_no_of_txn_monthly_amt")
	private BigDecimal maxNoOfTxnMonthlyAmt;

	@Column(name = "avg_no_of_txn_daily")
	private int avgNoOfTxnDaily;

	@Column(name = "avg_no_of_txn_daily_amt")
	private BigDecimal avgNoOfTxnDailyAmt;

	@Column(name = "avg_no_of_txn_weekly")
	private int avgNoOfTxnWeekly;

	@Column(name = "avg_no_of_txn_weekly_amt")
	private BigDecimal avgNoOfTxnWeeklyAmt;

	@Column(name = "avg_no_of_txn_monthly")
	private int avgNoOfTxnMonthly;

	@Column(name = "avg_no_of_txn_monthly_amt")
	private BigDecimal avgNoOfTxnMonthlyAmt;

	@Column(name = "max_txn_amt")
	private BigDecimal maxTxnAmt; // per transaction maximum amt

	@Column(name = "max_txn_against_mob_no_daily")
	private int maxTxnAgainstMobNoDaily;

	@Column(name = "max_txn_against_mob_no_weekly")
	private int maxTxnAgainstMobNoWeekly;

	@Column(name = "max_txn_against_mob_no_monthly")
	private int maxTxnAgainstMobNoMonthly;

	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "modify_date")
	@UpdateTimestamp
	private LocalDateTime modifyDate;

	@Column(name = "created_by", updatable = false)
	private String createdBy;

	@Column(name = "modify_by")
	private String modifyBy;

	@Column(name = "status")
	private int status;

	@Column(name = "hold_status", nullable = false, columnDefinition = "int default 0")
	private int holdStatus;

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

	public int getMaxNoOfTxnDaily() {
		return maxNoOfTxnDaily;
	}

	public void setMaxNoOfTxnDaily(int maxNoOfTxnDaily) {
		this.maxNoOfTxnDaily = maxNoOfTxnDaily;
	}

	public BigDecimal getMaxNoOfTxnDailyAmt() {
		return maxNoOfTxnDailyAmt;
	}

	public void setMaxNoOfTxnDailyAmt(BigDecimal maxNoOfTxnDailyAmt) {
		this.maxNoOfTxnDailyAmt = maxNoOfTxnDailyAmt;
	}

	public int getMaxNoOfTxnWeekly() {
		return maxNoOfTxnWeekly;
	}

	public void setMaxNoOfTxnWeekly(int maxNoOfTxnWeekly) {
		this.maxNoOfTxnWeekly = maxNoOfTxnWeekly;
	}

	public BigDecimal getMaxNoOfTxnWeeklyAmt() {
		return maxNoOfTxnWeeklyAmt;
	}

	public void setMaxNoOfTxnWeeklyAmt(BigDecimal maxNoOfTxnWeeklyAmt) {
		this.maxNoOfTxnWeeklyAmt = maxNoOfTxnWeeklyAmt;
	}

	public int getMaxNoOfTxnMonthly() {
		return maxNoOfTxnMonthly;
	}

	public void setMaxNoOfTxnMonthly(int maxNoOfTxnMonthly) {
		this.maxNoOfTxnMonthly = maxNoOfTxnMonthly;
	}

	public BigDecimal getMaxNoOfTxnMonthlyAmt() {
		return maxNoOfTxnMonthlyAmt;
	}

	public void setMaxNoOfTxnMonthlyAmt(BigDecimal maxNoOfTxnMonthlyAmt) {
		this.maxNoOfTxnMonthlyAmt = maxNoOfTxnMonthlyAmt;
	}

	public int getAvgNoOfTxnDaily() {
		return avgNoOfTxnDaily;
	}

	public void setAvgNoOfTxnDaily(int avgNoOfTxnDaily) {
		this.avgNoOfTxnDaily = avgNoOfTxnDaily;
	}

	public BigDecimal getAvgNoOfTxnDailyAmt() {
		return avgNoOfTxnDailyAmt;
	}

	public void setAvgNoOfTxnDailyAmt(BigDecimal avgNoOfTxnDailyAmt) {
		this.avgNoOfTxnDailyAmt = avgNoOfTxnDailyAmt;
	}

	public int getAvgNoOfTxnWeekly() {
		return avgNoOfTxnWeekly;
	}

	public void setAvgNoOfTxnWeekly(int avgNoOfTxnWeekly) {
		this.avgNoOfTxnWeekly = avgNoOfTxnWeekly;
	}

	public BigDecimal getAvgNoOfTxnWeeklyAmt() {
		return avgNoOfTxnWeeklyAmt;
	}

	public void setAvgNoOfTxnWeeklyAmt(BigDecimal avgNoOfTxnWeeklyAmt) {
		this.avgNoOfTxnWeeklyAmt = avgNoOfTxnWeeklyAmt;
	}

	public int getAvgNoOfTxnMonthly() {
		return avgNoOfTxnMonthly;
	}

	public void setAvgNoOfTxnMonthly(int avgNoOfTxnMonthly) {
		this.avgNoOfTxnMonthly = avgNoOfTxnMonthly;
	}

	public BigDecimal getAvgNoOfTxnMonthlyAmt() {
		return avgNoOfTxnMonthlyAmt;
	}

	public void setAvgNoOfTxnMonthlyAmt(BigDecimal avgNoOfTxnMonthlyAmt) {
		this.avgNoOfTxnMonthlyAmt = avgNoOfTxnMonthlyAmt;
	}

	public BigDecimal getMaxTxnAmt() {
		return maxTxnAmt;
	}

	public void setMaxTxnAmt(BigDecimal maxTxnAmt) {
		this.maxTxnAmt = maxTxnAmt;
	}

	public int getMaxTxnAgainstMobNoDaily() {
		return maxTxnAgainstMobNoDaily;
	}

	public void setMaxTxnAgainstMobNoDaily(int maxTxnAgainstMobNoDaily) {
		this.maxTxnAgainstMobNoDaily = maxTxnAgainstMobNoDaily;
	}

	public int getMaxTxnAgainstMobNoWeekly() {
		return maxTxnAgainstMobNoWeekly;
	}

	public void setMaxTxnAgainstMobNoWeekly(int maxTxnAgainstMobNoWeekly) {
		this.maxTxnAgainstMobNoWeekly = maxTxnAgainstMobNoWeekly;
	}

	public int getMaxTxnAgainstMobNoMonthly() {
		return maxTxnAgainstMobNoMonthly;
	}

	public void setMaxTxnAgainstMobNoMonthly(int maxTxnAgainstMobNoMonthly) {
		this.maxTxnAgainstMobNoMonthly = maxTxnAgainstMobNoMonthly;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(LocalDateTime modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getHoldStatus() {
		return holdStatus;
	}

	public void setHoldStatus(int holdStatus) {
		this.holdStatus = holdStatus;
	}

	@Override
	public String toString() {
		return "MerchantRisk [id=" + id + ", mid=" + mid + ", maxNoOfTxnDaily=" + maxNoOfTxnDaily
				+ ", maxNoOfTxnDailyAmt=" + maxNoOfTxnDailyAmt + ", maxNoOfTxnWeekly=" + maxNoOfTxnWeekly
				+ ", maxNoOfTxnWeeklyAmt=" + maxNoOfTxnWeeklyAmt + ", maxNoOfTxnMonthly=" + maxNoOfTxnMonthly
				+ ", maxNoOfTxnMonthlyAmt=" + maxNoOfTxnMonthlyAmt + ", avgNoOfTxnDaily=" + avgNoOfTxnDaily
				+ ", avgNoOfTxnDailyAmt=" + avgNoOfTxnDailyAmt + ", avgNoOfTxnWeekly=" + avgNoOfTxnWeekly
				+ ", avgNoOfTxnWeeklyAmt=" + avgNoOfTxnWeeklyAmt + ", avgNoOfTxnMonthly=" + avgNoOfTxnMonthly
				+ ", avgNoOfTxnMonthlyAmt=" + avgNoOfTxnMonthlyAmt + ", maxTxnAmt=" + maxTxnAmt
				+ ", maxTxnAgainstMobNoDaily=" + maxTxnAgainstMobNoDaily + ", maxTxnAgainstMobNoWeekly="
				+ maxTxnAgainstMobNoWeekly + ", maxTxnAgainstMobNoMonthly=" + maxTxnAgainstMobNoMonthly
				+ ", createdDate=" + createdDate + ", modifyDate=" + modifyDate + ", createdBy=" + createdBy
				+ ", modifyBy=" + modifyBy + ", status=" + status + ", holdStatus=" + holdStatus + "]";
	}

}
