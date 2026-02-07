package com.ftk.pg.modal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "au_mdr_charge_file")
public class AuMdrChargeFile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@CreationTimestamp
	@Column(name = "created_date", updatable = false)
	private Date createdDate;

	@Column(name = "transaction_amount")
	private BigDecimal transactionAmount;

	@Column(name = "mdr")
	private BigDecimal mdr;

	@Column(name = "mdr_percentage")
	private String MDRPercentage;

	@Column(name = "gst")
	private BigDecimal gst;

	@Column(name = "merchant_settlement_amount")
	private BigDecimal merchantSettlementAmount;

	@Column(name = "merchant_type")
	private String merchantType;

	@Column(name = "payee_mcc")
	private Long payeeMCC;

	@Column(name = "aggregator_name")
	private String aggregatorName;

	@Column(name = "master_vpa")
	private String masterVPA;

	@Column(name = "aggregator_gstn")
	private String aggregatorGSTN;

	@Column(name = "mcc_code")
	private String mccCode;

	@Column(name = "merchant_name")
	private String merchantName;

	@Column(name = "merchant_vpa")
	private String merchantVPA;

	@Column(name = "external_ref_no")
	private String externalRefNo;

	@Column(name = "cust_ref_number")
	private String custRefNumber;

	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "transaction_start_ts")
	private String transactionStartTs;

	@Column(name = "type")
	private String type;

	@Column(name = "status")
	private String status;

	@Column(name = "npci_response_code")
	private String NPCIResponseCode;

	@Column(name = "ifsc_code")
	private String ifscCode;

	@Column(name = "verified_name")
	private String verifiedName;

	@Column(name = "vpa")
	private String vpa;

	@Column(name = "participant_type")
	private String participantType;

	@Column(name = "payer_vpa")
	private String payerVPA;

	@Column(name = "udf1")
	private String udf1;

	@Column(name = "udf2")
	private String udf2;

	@Column(name = "udf3")
	private String udf3;

	@Column(name = "udf4")
	private String udf4;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public BigDecimal getMdr() {
		return mdr;
	}

	public void setMdr(BigDecimal mdr) {
		this.mdr = mdr;
	}

	public String getMDRPercentage() {
		return MDRPercentage;
	}

	public void setMDRPercentage(String mDRPercentage) {
		MDRPercentage = mDRPercentage;
	}

	public BigDecimal getGst() {
		return gst;
	}

	public void setGst(BigDecimal gst) {
		this.gst = gst;
	}

	public BigDecimal getMerchantSettlementAmount() {
		return merchantSettlementAmount;
	}

	public void setMerchantSettlementAmount(BigDecimal merchantSettlementAmount) {
		this.merchantSettlementAmount = merchantSettlementAmount;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public Long getPayeeMCC() {
		return payeeMCC;
	}

	public void setPayeeMCC(Long payeeMCC) {
		this.payeeMCC = payeeMCC;
	}

	public String getAggregatorName() {
		return aggregatorName;
	}

	public void setAggregatorName(String aggregatorName) {
		this.aggregatorName = aggregatorName;
	}

	public String getMasterVPA() {
		return masterVPA;
	}

	public void setMasterVPA(String masterVPA) {
		this.masterVPA = masterVPA;
	}

	public String getAggregatorGSTN() {
		return aggregatorGSTN;
	}

	public void setAggregatorGSTN(String aggregatorGSTN) {
		this.aggregatorGSTN = aggregatorGSTN;
	}

	public String getMccCode() {
		return mccCode;
	}

	public void setMccCode(String mccCode) {
		this.mccCode = mccCode;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantVPA() {
		return merchantVPA;
	}

	public void setMerchantVPA(String merchantVPA) {
		this.merchantVPA = merchantVPA;
	}

	public String getExternalRefNo() {
		return externalRefNo;
	}

	public void setExternalRefNo(String externalRefNo) {
		this.externalRefNo = externalRefNo;
	}

	public String getCustRefNumber() {
		return custRefNumber;
	}

	public void setCustRefNumber(String custRefNumber) {
		this.custRefNumber = custRefNumber;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getTransactionStartTs() {
		return transactionStartTs;
	}

	public void setTransactionStartTs(String transactionStartTs) {
		this.transactionStartTs = transactionStartTs;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNPCIResponseCode() {
		return NPCIResponseCode;
	}

	public void setNPCIResponseCode(String nPCIResponseCode) {
		NPCIResponseCode = nPCIResponseCode;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getVerifiedName() {
		return verifiedName;
	}

	public void setVerifiedName(String verifiedName) {
		this.verifiedName = verifiedName;
	}

	public String getVpa() {
		return vpa;
	}

	public void setVpa(String vpa) {
		this.vpa = vpa;
	}

	public String getParticipantType() {
		return participantType;
	}

	public void setParticipantType(String participantType) {
		this.participantType = participantType;
	}

	public String getPayerVPA() {
		return payerVPA;
	}

	public void setPayerVPA(String payerVPA) {
		this.payerVPA = payerVPA;
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

	@Override
	public String toString() {
		return "AuMdrChargeFile [id=" + id + ", createdDate=" + createdDate + ", transactionAmount=" + transactionAmount
				+ ", mdr=" + mdr + ", MDRPercentage=" + MDRPercentage + ", gst=" + gst + ", merchantSettlementAmount="
				+ merchantSettlementAmount + ", merchantType=" + merchantType + ", payeeMCC=" + payeeMCC
				+ ", aggregatorName=" + aggregatorName + ", masterVPA=" + masterVPA + ", aggregatorGSTN="
				+ aggregatorGSTN + ", mccCode=" + mccCode + ", merchantName=" + merchantName + ", merchantVPA="
				+ merchantVPA + ", externalRefNo=" + externalRefNo + ", custRefNumber=" + custRefNumber + ", amount="
				+ amount + ", transactionStartTs=" + transactionStartTs + ", type=" + type + ", status=" + status
				+ ", NPCIResponseCode=" + NPCIResponseCode + ", ifscCode=" + ifscCode + ", verifiedName=" + verifiedName
				+ ", vpa=" + vpa + ", participantType=" + participantType + ", payerVPA=" + payerVPA + ", udf1=" + udf1
				+ ", udf2=" + udf2 + ", udf3=" + udf3 + ", udf4=" + udf4 + "]";
	}

}
