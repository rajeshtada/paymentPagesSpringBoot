
package com.ftk.pg.modal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = "transaction_log")
public class TransactionLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -937542610394637133L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transaction_id")
	private Long transactionId;

	@Column(name = "card_data")
	private String carddata;

	@Column(name = "bank_name")
	private String bankname;

	@Column(name = "merchant_txn_id")
	private String merchanttxnid;

	@Column(name = "product_id")
	private String prodid;

	@Column(name = "billing_address")
	private String billingaddress;

	@Column(name = "transaction_date_time")
	private String date;

	@Column(name = "transaction_currency")
	private String txncurr;

	@Column(name = "email_id")
	private String emailid;

	@Column(name = "amount")
	private BigDecimal amt;

	@Column(name = "payment_mode")
	private String paymentMode;

	@Column(name = "return_url")
	private String ru;

	@Column(name = "customer_name")
	private String customername;

	@Column(name = "order_discription")
	private String od;

	@Column(name = "bank_id")
	private String bankId;

	// @Column(name = "card_no")
	// private String cardNo;
	//
	// @Column(name = "card_exp_cvv")
	// private String cardExpCVV;

	@Column(name = "bank_msg")
	private String bankMsg;

	@Column(name = "bank_error_msg")
	private String bankErrorMsg;

	@Column(name = "order_number")
	private String orderNumber;

	@Column(name = "response_code")
	private String responseCode;

	@Column(name = "transaction_status")
	private String txnStatus;

	@Column(name = "refund_status")
	private String refundStatus;

	@Column(name = "processor_code")
	private String processorCode;

	private String stage;

	private String processor;

	private String remarks;

	private String mobile;

	@Column(name = "merchant_id")
	private Long merchantId;

	@Column(name = "merchant_setting_id")
	private Long merchantSettingId;

	@Column(name = "merchant_name")
	private String merchantName;

	@Column(name = "channel_type")
	private String channelType;

	@Column(name = "total_refunded_amount")
	private BigDecimal totalrefundAmt;

	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "modified_date")
	@UpdateTimestamp
	private LocalDateTime modifiedDate;

	@Column(name = "commision_type")
	private String commisionType;

	@Column(name = "commison")
	private BigDecimal commision;

	@Column(name = "commision_id")
	private Long commisionId;

	@Column(name = "processor_tx_reference")
	private String processorTxReference;

	@Column(name = "processor_txn_id")
	private String processorTxnId;

	@Column(name = "processor_recon_status")
	private String reconStatus;

	@Column(name = "processor_settlement_amount")
	private String settlementAmount;

//	@Column(name = "processor_settlement_date")
//	private String settlementDate;

	@Column(name = "processor_settlement_d")
	private Date settlementDate;

	@Column(name = "settlement_status")
	@NotNull
	@ColumnDefault("'0'") // 1 for request send to processor, 2 for get the response against Tx.,
	// 3 Amount Settled by Admin and 4 risk txn
	private int settlementStatus;

	@Column(name = "settlement_ref_no")
	private String settlementRefNo;

	@Column(name = "pos_id")
	private String posId;

	@Column(name = "terminal_id")
	private String terminalId;

	@Column(name = "cgst_amount")
	private BigDecimal cgstAmount;

	@Column(name = "sgst_amount")
	private BigDecimal sgstAmount;

	@Column(name = "igst_amount")
	private BigDecimal igstAmount;

	@Column(name = "productType")
	private String productType;

	private String count;

	private String udf1;

	private String udf2;

	private String udf3;

	private String udf4;

	private String udf5;

	private String udf6;

	private String udf7; // cusREfno

	private String udf8;

	private String udf9;

	private String udf10;

	private String txnType;

	@Column(name = "risk_description")
	private String riskDescription;

	// add merchant settlement
	@Column(name = "merchant_settlement_ref_no")
	private String merchantSettlementRefNo;

	@Column(name = "merchant_settlement_amount")
	private Double merchantSettlementAmt;

	@Column(name = "merchant_settlementRemarks")
	private String merchantSettlementRemarks;

	@Column(name = "merchant_settlement_date")
	private Date merchantSettlementDate;

	@Column(name = "risk_status")
	@ColumnDefault(value = "0")
	private int riskStatus; // 0 for no risk and 1 for risk and 2 for hold and 3 for release

	@Column(name = "hold_date")
	private Date holdDate;

	@Column(name = "release_date")
	private Date releaseDate;

	// fileType
	@Column(name = "file_type")
	private String fileType;

	@Column(name = "send_callB_status", nullable = false, columnDefinition = "int default 0")
	private int sendCallBStatus;

	@Column(name = "total_service_charge", nullable = false, columnDefinition = "double default 0")
	@ColumnDefault("0")
	private double totalServiceCharge;

	@Column(name = "service_charge", nullable = false, columnDefinition = "double default 0")
	@ColumnDefault("0")
	private double serviceCharge;

	@Column(name = "service_charge_gst", nullable = false, columnDefinition = "double default 0")
	@ColumnDefault("0")
	private double serviceChargeGst;

	@Column(name = "service_charge_type", nullable = false, columnDefinition = "varchar(20) default 'Incl'")
	@ColumnDefault("Incl")
	private String serviceChargeType = "Incl";

	@Column(name = "settlement_history_id")
	private Long settlementHistoryId;

	@Column(name = "settlement_file_id")
	private Long settlementFileId;

	@Column(name = "mandate_Id")
	private Long mandateId;

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getCarddata() {
		return carddata;
	}

	public void setCarddata(String carddata) {
		this.carddata = carddata;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getMerchanttxnid() {
		return merchanttxnid;
	}

	public void setMerchanttxnid(String merchanttxnid) {
		this.merchanttxnid = merchanttxnid;
	}

	public String getProdid() {
		return prodid;
	}

	public void setProdid(String prodid) {
		this.prodid = prodid;
	}

	public String getBillingaddress() {
		return billingaddress;
	}

	public void setBillingaddress(String billingaddress) {
		this.billingaddress = billingaddress;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTxncurr() {
		return txncurr;
	}

	public void setTxncurr(String txncurr) {
		this.txncurr = txncurr;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getRu() {
		return ru;
	}

	public void setRu(String ru) {
		this.ru = ru;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getOd() {
		return od;
	}

	public void setOd(String od) {
		this.od = od;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankMsg() {
		return bankMsg;
	}

	public void setBankMsg(String bankMsg) {
		this.bankMsg = bankMsg;
	}

	public String getBankErrorMsg() {
		return bankErrorMsg;
	}

	public void setBankErrorMsg(String bankErrorMsg) {
		this.bankErrorMsg = bankErrorMsg;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getTxnStatus() {
		return txnStatus;
	}

	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getProcessorCode() {
		return processorCode;
	}

	public void setProcessorCode(String processorCode) {
		this.processorCode = processorCode;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Long getMerchantSettingId() {
		return merchantSettingId;
	}

	public void setMerchantSettingId(Long merchantSettingId) {
		this.merchantSettingId = merchantSettingId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public BigDecimal getTotalrefundAmt() {
		return totalrefundAmt;
	}

	public void setTotalrefundAmt(BigDecimal totalrefundAmt) {
		this.totalrefundAmt = totalrefundAmt;
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

	public String getCommisionType() {
		return commisionType;
	}

	public void setCommisionType(String commisionType) {
		this.commisionType = commisionType;
	}

	public BigDecimal getCommision() {
		if (commision == null) {
			commision = BigDecimal.ZERO;
		}
		return commision;
	}

	public void setCommision(BigDecimal commision) {
		this.commision = commision;
	}

	public Long getCommisionId() {
		return commisionId;
	}

	public void setCommisionId(Long commisionId) {
		this.commisionId = commisionId;
	}

	public String getProcessorTxReference() {
		return processorTxReference;
	}

	public void setProcessorTxReference(String processorTxReference) {
		this.processorTxReference = processorTxReference;
	}

	public String getProcessorTxnId() {
		return processorTxnId;
	}

	public void setProcessorTxnId(String processorTxnId) {
		this.processorTxnId = processorTxnId;
	}

	public String getReconStatus() {
		return reconStatus;
	}

	public void setReconStatus(String reconStatus) {
		this.reconStatus = reconStatus;
	}

	public String getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(String settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	public int getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(int settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	public String getSettlementRefNo() {
		return settlementRefNo;
	}

	public void setSettlementRefNo(String settlementRefNo) {
		this.settlementRefNo = settlementRefNo;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public BigDecimal getCgstAmount() {
		return cgstAmount;
	}

	public void setCgstAmount(BigDecimal cgstAmount) {
		this.cgstAmount = cgstAmount;
	}

	public BigDecimal getSgstAmount() {
		return sgstAmount;
	}

	public void setSgstAmount(BigDecimal sgstAmount) {
		this.sgstAmount = sgstAmount;
	}

	public BigDecimal getIgstAmount() {
		return igstAmount;
	}

	public void setIgstAmount(BigDecimal igstAmount) {
		this.igstAmount = igstAmount;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
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

	public String getUdf6() {
		return udf6;
	}

	public void setUdf6(String udf6) {
		this.udf6 = udf6;
	}

	public String getUdf7() {
		return udf7;
	}

	public void setUdf7(String udf7) {
		this.udf7 = udf7;
	}

	public String getUdf8() {
		return udf8;
	}

	public void setUdf8(String udf8) {
		this.udf8 = udf8;
	}

	public String getUdf9() {
		return udf9;
	}

	public void setUdf9(String udf9) {
		this.udf9 = udf9;
	}

	public String getUdf10() {
		return udf10;
	}

	public void setUdf10(String udf10) {
		this.udf10 = udf10;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getMerchantSettlementRefNo() {
		return merchantSettlementRefNo;
	}

	public void setMerchantSettlementRefNo(String merchantSettlementRefNo) {
		this.merchantSettlementRefNo = merchantSettlementRefNo;
	}

	public Double getMerchantSettlementAmt() {
		return merchantSettlementAmt;
	}

	public void setMerchantSettlementAmt(Double merchantSettlementAmt) {
		this.merchantSettlementAmt = merchantSettlementAmt;
	}

	public String getMerchantSettlementRemarks() {
		return merchantSettlementRemarks;
	}

	public void setMerchantSettlementRemarks(String merchantSettlementRemarks) {
		this.merchantSettlementRemarks = merchantSettlementRemarks;
	}

	public Date getMerchantSettlementDate() {
		return merchantSettlementDate;
	}

	public void setMerchantSettlementDate(Date merchantSettlementDate) {
		this.merchantSettlementDate = merchantSettlementDate;
	}

	public String getRiskDescription() {
		return riskDescription;
	}

	public void setRiskDescription(String riskDescription) {
		this.riskDescription = riskDescription;
	}

	public int getRiskStatus() {
		return riskStatus;
	}

	public void setRiskStatus(int riskStatus) {
		this.riskStatus = riskStatus;
	}

	public Date getHoldDate() {
		return holdDate;
	}

	public void setHoldDate(Date holdDate) {
		this.holdDate = holdDate;
	}

	public void setHoldDate(String format) throws ParseException {
		Date date1;
		try {
			date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format);
		} catch (Exception e) {
			date1 = new SimpleDateFormat("MM/dd/yyy HH:mm:ss").parse(format);
		}
		this.releaseDate = date1;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public void setReleaseDate(String format) throws ParseException {
		Date date1;
		try {
			date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format);
		} catch (Exception e) {
			date1 = new SimpleDateFormat("MM/dd/yyy HH:mm:ss").parse(format);
		}
		this.releaseDate = date1;
	}

	/*
	 * public Long getSettlementHistoryId() { return settlementHistoryId; }
	 * 
	 * public void setSettlementHistoryId(Long settlementHistoryId) {
	 * this.settlementHistoryId = settlementHistoryId; }
	 */

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public int getSendCallBStatus() {
		return sendCallBStatus;
	}

	public void setSendCallBStatus(int sendCallBStatus) {
		this.sendCallBStatus = sendCallBStatus;
	}

	public double getTotalServiceCharge() {
		return totalServiceCharge;
	}

	public void setTotalServiceCharge(double totalServiceCharge) {
		this.totalServiceCharge = totalServiceCharge;
	}

	public double getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public double getServiceChargeGst() {
		return serviceChargeGst;
	}

	public void setServiceChargeGst(double serviceChargeGst) {
		this.serviceChargeGst = serviceChargeGst;
	}

	public String getServiceChargeType() {
		return serviceChargeType;
	}

	public void setServiceChargeType(String serviceChargeType) {
		this.serviceChargeType = serviceChargeType;
	}

	public Long getSettlementHistoryId() {
		return settlementHistoryId;
	}

	public void setSettlementHistoryId(Long settlementHistoryId) {
		this.settlementHistoryId = settlementHistoryId;
	}

	public Long getSettlementFileId() {
		return settlementFileId;
	}

	public void setSettlementFileId(Long settlementFileId) {
		this.settlementFileId = settlementFileId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getMandateId() {
		return mandateId;
	}

	public void setMandateId(Long mandateId) {
		this.mandateId = mandateId;
	}

	@Override
	public String toString() {
		return "TransactionLog [transactionId=" + transactionId + ", carddata=" + carddata + ", bankname=" + bankname
				+ ", merchanttxnid=" + merchanttxnid + ", prodid=" + prodid + ", billingaddress=" + billingaddress
				+ ", date=" + date + ", txncurr=" + txncurr + ", emailid=" + emailid + ", amt=" + amt + ", paymentMode="
				+ paymentMode + ", ru=" + ru + ", customername=" + customername + ", od=" + od + ", bankId=" + bankId
				+ ", bankMsg=" + bankMsg + ", bankErrorMsg=" + bankErrorMsg + ", orderNumber=" + orderNumber
				+ ", responseCode=" + responseCode + ", txnStatus=" + txnStatus + ", refundStatus=" + refundStatus
				+ ", processorCode=" + processorCode + ", stage=" + stage + ", processor=" + processor + ", remarks="
				+ remarks + ", mobile=" + mobile + ", merchantId=" + merchantId + ", merchantSettingId="
				+ merchantSettingId + ", merchantName=" + merchantName + ", channelType=" + channelType
				+ ", totalrefundAmt=" + totalrefundAmt + ", createdDate=" + createdDate + ", modifiedDate="
				+ modifiedDate + ", commisionType=" + commisionType + ", commision=" + commision + ", commisionId="
				+ commisionId + ", processorTxReference=" + processorTxReference + ", processorTxnId=" + processorTxnId
				+ ", reconStatus=" + reconStatus + ", settlementAmount=" + settlementAmount + ", settlementDate="
				+ settlementDate + ", settlementStatus=" + settlementStatus + ", settlementRefNo=" + settlementRefNo
				+ ", posId=" + posId + ", terminalId=" + terminalId + ", cgstAmount=" + cgstAmount + ", sgstAmount="
				+ sgstAmount + ", igstAmount=" + igstAmount + ", productType=" + productType + ", count=" + count
				+ ", udf1=" + udf1 + ", udf2=" + udf2 + ", udf3=" + udf3 + ", udf4=" + udf4 + ", udf5=" + udf5
				+ ", udf6=" + udf6 + ", udf7=" + udf7 + ", udf8=" + udf8 + ", udf9=" + udf9 + ", udf10=" + udf10
				+ ", txnType=" + txnType + ", riskDescription=" + riskDescription + ", merchantSettlementRefNo="
				+ merchantSettlementRefNo + ", merchantSettlementAmt=" + merchantSettlementAmt
				+ ", merchantSettlementRemarks=" + merchantSettlementRemarks + ", merchantSettlementDate="
				+ merchantSettlementDate + ", riskStatus=" + riskStatus + ", holdDate=" + holdDate + ", releaseDate="
				+ releaseDate + ", fileType=" + fileType + ", sendCallBStatus=" + sendCallBStatus
				+ ", totalServiceCharge=" + totalServiceCharge + ", serviceCharge=" + serviceCharge
				+ ", serviceChargeGst=" + serviceChargeGst + ", serviceChargeType=" + serviceChargeType
				+ ", settlementHistoryId=" + settlementHistoryId + ", settlementFileId=" + settlementFileId
				+ ", mandateId=" + mandateId + "]";
	}

}
