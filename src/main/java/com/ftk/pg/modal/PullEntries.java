package com.ftk.pg.modal;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.envers.Audited;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Audited
@Table(name = "pull_entries")
public class PullEntries implements Serializable{

	private static final long serialVersionUID = -3537203480661936784L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	private LocalDateTime transactionDate;
	private String valueDate;
	private String transaction;
	private Double debit;
	private Double credit;
	private Double balance;
	private String type;
	private String txnRefNo;
	
	private String udf1; // processor name
	private String udf2; // settlementFileId
	private String udf3;
	private String udf4;
	private String udf5;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getTransaction() {
		return transaction;
	}
	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}
	public Double getDebit() {
		return debit;
	}
	public void setDebit(Double debit) {
		this.debit = debit;
	}
	public Double getCredit() {
		return credit;
	}
	public void setCredit(Double credit) {
		this.credit = credit;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public String getUdf1() {
		return udf1;
	}
	public void setUdf1(String udf1) {
		this.udf1 = udf1;
	}
	public String getUdf2() {
		return udf2;
	}
	public void setUdf2(String udf2) {
		this.udf2 = udf2;
	}
	public String getUdf3() {
		return udf3;
	}
	public void setUdf3(String udf3) {
		this.udf3 = udf3;
	}
	public String getUdf4() {
		return udf4;
	}
	public void setUdf4(String udf4) {
		this.udf4 = udf4;
	}
	public String getUdf5() {
		return udf5;
	}
	public void setUdf5(String udf5) {
		this.udf5 = udf5;
	}
	public String getValueDate() {
		return valueDate;
	}
	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getTxnRefNo() {
		return txnRefNo;
	}
	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}
	@Override
	public String toString() {
		return "PullEntries [id=" + id + ", transactionDate=" + transactionDate + ", valueDate=" + valueDate
				+ ", transaction=" + transaction + ", debit=" + debit + ", credit=" + credit + ", balance=" + balance
				+ ", type=" + type + ", txnRefNo=" + txnRefNo + ", udf1=" + udf1 + ", udf2=" + udf2 + ", udf3=" + udf3
				+ ", udf4=" + udf4 + ", udf5=" + udf5 + "]";
	}
	
	
	
}
