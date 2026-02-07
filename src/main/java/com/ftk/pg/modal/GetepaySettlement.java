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
@Table(name = "getepay_settlement")
public class GetepaySettlement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3198536735021820744L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;
	
	private Long mid;
	private String vpa;
	
	@Column(name = "settlement_date")
	private Date settlementDate;
	
	@Column(name = "settlement_part")
	private String settlementPart;
	
	@Column(name = "gross_settlement_amount")
	private BigDecimal grossSettlementAmount;
	
	@Column(name = "refund_amount")
	private BigDecimal refundAmount;
	
	@Column(name = "other_deduction_amount")
	private BigDecimal otherDeductionAmount;
	
	@Column(name = "net_settlement_amount")
	private BigDecimal netSettlementAmount;
	
	@Column(name = "product_code")
	private BigDecimal productCode;

	@Column(name = "beneficiary_account_number")
	private String beneficiaryAccountNumber;
	
	@Column(name = "beneficiary_account_name")
	private String beneficiaryAccontName;
	
	@Column(name = "beneficiary_ifsc_code")
	private String beneficiaryIfscCode;
	
	private String processor;
	
	@Column(name = "created_date")
	private Date createdDate;	
	
	@Column(name = "updated_date")
	private Date updatedDate;
	
	@Column(name = "status")
	private int status;
	
	@Column(name = "payment_mode")
	private String paymentMode;	
	
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

	public String getVpa() {
		return vpa;
	}

	public void setVpa(String vpa) {
		this.vpa = vpa;
	}

	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	public String getSettlementPart() {
		return settlementPart;
	}

	public void setSettlementPart(String settlementPart) {
		this.settlementPart = settlementPart;
	}

	public BigDecimal getGrossSettlementAmount() {
		return grossSettlementAmount;
	}

	public void setGrossSettlementAmount(BigDecimal grossSettlementAmount) {
		this.grossSettlementAmount = grossSettlementAmount;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public BigDecimal getOtherDeductionAmount() {
		return otherDeductionAmount;
	}

	public void setOtherDeductionAmount(BigDecimal otherDeductionAmount) {
		this.otherDeductionAmount = otherDeductionAmount;
	}

	public BigDecimal getNetSettlementAmount() {
		return netSettlementAmount;
	}

	public void setNetSettlementAmount(BigDecimal netSettlementAmount) {
		this.netSettlementAmount = netSettlementAmount;
	}

	public BigDecimal getProductCode() {
		return productCode;
	}

	public void setProductCode(BigDecimal productCode) {
		this.productCode = productCode;
	}

	public String getBeneficiaryAccountNumber() {
		return beneficiaryAccountNumber;
	}

	public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
	}

	public String getBeneficiaryAccontName() {
		return beneficiaryAccontName;
	}

	public void setBeneficiaryAccontName(String beneficiaryAccontName) {
		this.beneficiaryAccontName = beneficiaryAccontName;
	}

	public String getBeneficiaryIfscCode() {
		return beneficiaryIfscCode;
	}

	public void setBeneficiaryIfscCode(String beneficiaryIfscCode) {
		this.beneficiaryIfscCode = beneficiaryIfscCode;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	
	
	
}
