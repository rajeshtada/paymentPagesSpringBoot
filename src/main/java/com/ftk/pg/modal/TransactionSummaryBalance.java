package com.ftk.pg.modal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction_summary_balance")
public class TransactionSummaryBalance implements Serializable {

	private static final long serialVersionUID = 890184559522860738L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "date")
	private Date date;

	@Column(name = "count_txn")
	private int count_txn;
	
	@Column(name = "sum_amount")
	private BigDecimal sum_amount;

	@Column(name = "processor")
	private String processor;

	@Column(name = "remark")
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getCount_txn() {
		return count_txn;
	}

	public void setCount_txn(int count_txn) {
		this.count_txn = count_txn;
	}

	public BigDecimal getSum_amount() {
		return sum_amount;
	}

	public void setSum_amount(BigDecimal sum_amount) {
		this.sum_amount = sum_amount;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "TransactionBalance [id=" + id + ", date=" + date + ", count_txn=" + count_txn + ", sum_amount="
				+ sum_amount + ", processor=" + processor + ", remark=" + remark + "]";
	}

}
