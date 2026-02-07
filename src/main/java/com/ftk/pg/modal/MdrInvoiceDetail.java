package com.ftk.pg.modal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mdr_invoice_detail")
public class MdrInvoiceDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "mid")
	private Long mid;
	
	@Column(name = "from_date")
	private LocalDate fromDate;
	
	@Column(name = "to_date")
	private LocalDate toDate;
	
	@Column(name = "invoice_number")
	private String invoiceNumber;
	
	@Column(name = "commission")
	private BigDecimal commission;
	
	@Column(name = "commission_gst")
	private BigDecimal commissionGst;
	
	@Column(name = "total_commission")
	private BigDecimal totalCommission;
	
	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;
	
	@Column(name = "file_path")
	private String filePath;

	@Column(name = "gst_number")
	private String gstNo;
	
	@Column(name = "status")
	private int status;

//	@Transient
	@Column(name = "vpa")
	private String vpa;
	
	
	
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

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public BigDecimal getCommissionGst() {
		return commissionGst;
	}

	public void setCommissionGst(BigDecimal commissionGst) {
		this.commissionGst = commissionGst;
	}

	public BigDecimal getTotalCommission() {
		return totalCommission;
	}

	public void setTotalCommission(BigDecimal totalCommission) {
		this.totalCommission = totalCommission;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	
	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getVpa() {
		return vpa;
	}

	public void setVpa(String vpa) {
		this.vpa = vpa;
	}

	@Override
	public String toString() {
		return "MdrInvoiceDetail [id=" + id + ", mid=" + mid + ", fromDate=" + fromDate + ", toDate=" + toDate
				+ ", invoiceNumber=" + invoiceNumber + ", commission=" + commission + ", commissionGst=" + commissionGst
				+ ", totalCommission=" + totalCommission + ", createdDate=" + createdDate + ", filePath=" + filePath
				+ ", gstNo=" + gstNo + ", status=" + status + ", vpa=" + vpa + "]";
	}


}

