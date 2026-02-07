package com.ftk.pg.modal;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "balance_sheet")
public class BalanceSheet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "processor")
	private String processor;

	@Column(name = "total_cr", updatable = false)
	private Double totalCr;

	@Column(name = "total_dr")
	private Double totalDr;

	@Column(name = "balance")
	private Double balance;

	@Column(name = "total_balance")
	private Double totalBalance;
	
	@Column(name = "date")
	private Date uploadDate;
	
	@Column(name = "count_cr")
	private Long countCr;
	
	@Column(name = "count_dr")
	private Long countDr;
	
	public Long getCountDr() {
		return countDr;
	}

	public void setCountDr(Long countDr) {
		this.countDr = countDr;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public Double getTotalCr() {
		return totalCr;
	}

	public void setTotalCr(Double totalCr) {
		this.totalCr = totalCr;
	}

	public Double getTotalDr() {
		return totalDr;
	}

	public void setTotalDr(Double totalDr) {
		this.totalDr = totalDr;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(Double totalBalance) {
		this.totalBalance = totalBalance;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	@Override
	public String toString() {
		return "BalanceSheet [id=" + id + ", processor=" + processor + ", totalCr=" + totalCr + ", totalDr=" + totalDr
				+ ", balance=" + balance + ", totalBalance=" + totalBalance + ", uploadDate=" + uploadDate
				+ ", countCr=" + countCr + ", countDr=" + countDr + "]";
	}

}