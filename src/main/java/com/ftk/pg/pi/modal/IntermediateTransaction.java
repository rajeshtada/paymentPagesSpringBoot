package com.ftk.pg.pi.modal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.UpdateTimestamp;

import com.ftk.pg.exception.GlobalExceptionHandler;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "intermediate_transaction")
@NamedNativeQueries(value = {
		@NamedNativeQuery(name = "findTopCashBackRank", query = "select * from (select count(transaction_id) as count, sum(txn_amount) as amount,any_value(agent_name) as agent_name,udf8 from intermediate_transaction where mid in(:mids) and payment_type='UPIQR' and date(txn_date) between :fromDate and :toDate and udf8 is not null and txn_amount >=:amount group by udf8) c order by c.amount desc ,c.count desc limit :limits"),
		@NamedNativeQuery(name = "findTopCashBackRankUniqVpa", query = "select * from (select count(transaction_id) as count, sum(txn_amount) as amount,any_value(agent_name) as agent_name, udf8 from intermediate_transaction where transaction_id in(:txnIds) and payment_type='UPIQR' and udf8 is not null and txn_amount >=:amount group by udf8) c order by c.amount desc ,c.count desc")

//		select * from (select count(transaction_id) as count, sum(txn_amount) as amount,any_value(agent_name) as agent_name,udf8 from intermediate_transaction where transaction_id  in(12597,12595,327,'574') and payment_type='UPIQR' and date(txn_date) between '2020-01-01' and '2020-11-05' and udf8 is not null and txn_amount >=90 group by udf8) c order by c.amount desc ,c.count desc limit 500

/*
 * @NamedNativeQuery (name = "@findTopCashBackRank", query =
 * "select * from (select count(transactionId) as count, sum(txnAmount) as amount, udf8 from IntermediateTransaction where paymentType='UPIQR' and txnDatetime between :fromDate and :toDate and udf8 is not null group by udf8) c where c.count>=1 order by c.amount desc ,c.count desc limit :limit"
 * ),
 * 
 * @NamedQuery (name = "@findTopCashBackRankUniqueVpa", query = "")
 */
})
public class IntermediateTransaction {
	static Logger logger = LogManager.getLogger(IntermediateTransaction.class);


	@Id
	@Column(name = "transaction_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transactionId;

	@Column(name = "agent_name")
	private String agentName;

	@Column(name = "agent_id")
	private String agentId;

	@Column(name = "mid")
	private Long mid;

	@Column(name = "merchant_order_no")
	private String merchantOrderNo;

	@Column(name = "txn_date")
	private Date txnDatetime;

	@Column(name = "created_date", updatable = false)
//	@CreationTimestamp
	private Date createdDate;

	@Column(name = "modified_date")
	@UpdateTimestamp
	private Date modifiedDate;

	public String udf1; // Mobile
	public String udf2; // Email
	private String udf3; // Name
	private String udf4;
	private String udf5;

	private String udf6;
	private String udf7; // rrn number
	private String udf8; // vpaId
	private String udf9; // invoice no
	private String udf10;

	private String udf11; // merchantAmt
	private String udf12; // seller Amt
	private String udf13;
	private String udf14;
	private String udf15;
	private String udf16;
	private String udf17;
	private String udf18;
	private String udf19;
	private String udf20;

	private String udf21;
	private String udf22;
	private String udf23;
	private String udf24;
	private String udf25;
	private String udf26;
	private String udf27;
	private String udf28;
	private String udf29;
	private String udf30;

	private String udf31;
	private String udf32;
	private String udf33;
	private String udf34;
	private String udf35;
	private String udf36;
	private String udf37;
	private String udf38;
	private String udf39;
	private String udf40; // merchant_settlement_ref_no

	private String udf41; // ru
	private String udf42; // callbackurl
	private String udf43; // discriminator
	private String udf44; // payerVpa
	private String udf45;
	private String udf46;
	private String udf47; // Challan Remarks
	private String udf48; // Vehicle No.
	private String udf49; // Employee Name
	// is allocated by eId in case of challan payment
	private String udf50; // Settlement Part

	@Column(name = "txn_amount")
	private Double txnAmount;

	@Column(name = "txn_discount")
	private Double txnDiscount;

	@Column(name = "applied_coupen")
	private String appliedCoupen;

	private String ru;

	private String status;

	private String message;

	@Column(name = "order_number")
	private String orderNumber;

	@Column(name = "payment_type")
	private String paymentType;

	@Column(name = "settlement_amount")
	private Double settlementAmount;

	@Column(name = "settlement_date")
	private LocalDate settlementDate;

	@Column(name = "settlement_atom_txnid")
	private String settlementAtomTxnId;

	@Column(name = "settlement_status")
	private String settlementStatus;

	@Column(name = "recon_status")
	private String reconStatus;

	private String bid;

	private double surcharge;

	@Column(name = "product_type")
	private String productType;

	@Column(name = "processor_id")
	private Long processorId;

	@Column(name = "risk_description")
	private String riskDescription;

	@Column(name = "hold_date")
	private Date holdDate;

	@Column(name = "release_date")
	private Date releaseDate;

	@Column(name = "risk_status", nullable = false, columnDefinition = "int default 0")
	private int riskStatus;

	@Column(name = "remark")
	private String remark;

	@Column(name = "ru_status", nullable = false, columnDefinition = "int default 0")
	private int ruStatus;

	public IntermediateTransaction() {
		try {
			this.createdDate = new Date();
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setCreatedDate(String format) throws ParseException {
		Date date1;
		try {
			date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format);
		} catch (Exception e) {
			try {
				date1 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(format);
			} catch (Exception e2) {
				date1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(format);
			}
		}
		this.createdDate = date1;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
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

	public Double getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(Double txnAmount) {
		this.txnAmount = txnAmount;
	}

	public Double getTxnDiscount() {
		return txnDiscount;
	}

	public void setTxnDiscount(Double txnDiscount) {
		this.txnDiscount = txnDiscount;
	}

	public String getAppliedCoupen() {
		return appliedCoupen;
	}

	public void setAppliedCoupen(String appliedCoupen) {
		this.appliedCoupen = appliedCoupen;
	}

	public String getRu() {
		return ru;
	}

	public void setRu(String ru) {
		this.ru = ru;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Date getTxnDatetime() {
		return txnDatetime;
	}

	public void setTxnDatetime(Date txnDatetime) {
		this.txnDatetime = txnDatetime;
	}

	public void setTxnDatetime(String format) throws ParseException {
		Date date1;
		try {
			date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format);
		} catch (Exception e) {
			try {
				date1 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(format);
			} catch (Exception e2) {
				try {
					date1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(format);
				} catch (Exception e3) {
					date1 = new SimpleDateFormat("yyyy-MM-dd").parse(format);
				}
			}
		}
		this.txnDatetime = date1;
	}

	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
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

	public String getUdf11() {
		return udf11;
	}

	public void setUdf11(String udf11) {
		this.udf11 = udf11;
	}

	public String getUdf12() {
		return udf12;
	}

	public void setUdf12(String udf12) {
		this.udf12 = udf12;
	}

	public String getUdf13() {
		return udf13;
	}

	public void setUdf13(String udf13) {
		this.udf13 = udf13;
	}

	public String getUdf14() {
		return udf14;
	}

	public void setUdf14(String udf14) {
		this.udf14 = udf14;
	}

	public String getUdf15() {
		return udf15;
	}

	public void setUdf15(String udf15) {
		this.udf15 = udf15;
	}

	public Double getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(Double settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public LocalDate getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(LocalDate settlementDate) {
		this.settlementDate = settlementDate;
	}

	public String getSettlementAtomTxnId() {
		return settlementAtomTxnId;
	}

	public void setSettlementAtomTxnId(String settlementAtomTxnId) {
		this.settlementAtomTxnId = settlementAtomTxnId;
	}

	public String getReconStatus() {
		return reconStatus;
	}

	public void setReconStatus(String reconStatus) {
		this.reconStatus = reconStatus;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public double getSurcharge() {
		return surcharge;
	}

	public void setSurcharge(double surcharge) {
		this.surcharge = surcharge;
	}

	public String getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(String settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	public String getUdf16() {
		return udf16;
	}

	public void setUdf16(String udf16) {
		this.udf16 = udf16;
	}

	public String getUdf17() {
		return udf17;
	}

	public void setUdf17(String udf17) {
		this.udf17 = udf17;
	}

	public String getUdf18() {
		return udf18;
	}

	public void setUdf18(String udf18) {
		this.udf18 = udf18;
	}

	public String getUdf19() {
		return udf19;
	}

	public void setUdf19(String udf19) {
		this.udf19 = udf19;
	}

	public String getUdf20() {
		return udf20;
	}

	public void setUdf20(String udf20) {
		this.udf20 = udf20;
	}

	public String getUdf21() {
		return udf21;
	}

	public void setUdf21(String udf21) {
		this.udf21 = udf21;
	}

	public String getUdf22() {
		return udf22;
	}

	public void setUdf22(String udf22) {
		this.udf22 = udf22;
	}

	public String getUdf23() {
		return udf23;
	}

	public void setUdf23(String udf23) {
		this.udf23 = udf23;
	}

	public String getUdf24() {
		return udf24;
	}

	public void setUdf24(String udf24) {
		this.udf24 = udf24;
	}

	public String getUdf25() {
		return udf25;
	}

	public void setUdf25(String udf25) {
		this.udf25 = udf25;
	}

	public String getUdf26() {
		return udf26;
	}

	public void setUdf26(String udf26) {
		this.udf26 = udf26;
	}

	public String getUdf27() {
		return udf27;
	}

	public void setUdf27(String udf27) {
		this.udf27 = udf27;
	}

	public String getUdf28() {
		return udf28;
	}

	public void setUdf28(String udf28) {
		this.udf28 = udf28;
	}

	public String getUdf29() {
		return udf29;
	}

	public void setUdf29(String udf29) {
		this.udf29 = udf29;
	}

	public String getUdf30() {
		return udf30;
	}

	public void setUdf30(String udf30) {
		this.udf30 = udf30;
	}

	public String getUdf31() {
		return udf31;
	}

	public void setUdf31(String udf31) {
		this.udf31 = udf31;
	}

	public String getUdf32() {
		return udf32;
	}

	public void setUdf32(String udf32) {
		this.udf32 = udf32;
	}

	public String getUdf33() {
		return udf33;
	}

	public void setUdf33(String udf33) {
		this.udf33 = udf33;
	}

	public String getUdf34() {
		return udf34;
	}

	public void setUdf34(String udf34) {
		this.udf34 = udf34;
	}

	public String getUdf35() {
		return udf35;
	}

	public void setUdf35(String udf35) {
		this.udf35 = udf35;
	}

	public String getUdf36() {
		return udf36;
	}

	public void setUdf36(String udf36) {
		this.udf36 = udf36;
	}

	public String getUdf37() {
		return udf37;
	}

	public void setUdf37(String udf37) {
		this.udf37 = udf37;
	}

	public String getUdf38() {
		return udf38;
	}

	public void setUdf38(String udf38) {
		this.udf38 = udf38;
	}

	public String getUdf39() {
		return udf39;
	}

	public void setUdf39(String udf39) {
		this.udf39 = udf39;
	}

	public String getUdf40() {
		return udf40;
	}

	public void setUdf40(String udf40) {
		this.udf40 = udf40;
	}

	public String getUdf41() {
		return udf41;
	}

	public void setUdf41(String udf41) {
		this.udf41 = udf41;
	}

	public String getUdf42() {
		return udf42;
	}

	public void setUdf42(String udf42) {
		this.udf42 = udf42;
	}

	public String getUdf43() {
		return udf43;
	}

	public void setUdf43(String udf43) {
		this.udf43 = udf43;
	}

	public String getUdf44() {
		return udf44;
	}

	public void setUdf44(String udf44) {
		this.udf44 = udf44;
	}

	public String getUdf45() {
		return udf45;
	}

	public void setUdf45(String udf45) {
		this.udf45 = udf45;
	}

	public String getUdf46() {
		return udf46;
	}

	public void setUdf46(String udf46) {
		this.udf46 = udf46;
	}

	public String getUdf47() {
		return udf47;
	}

	public void setUdf47(String udf47) {
		this.udf47 = udf47;
	}

	public String getUdf48() {
		return udf48;
	}

	public void setUdf48(String udf48) {
		this.udf48 = udf48;
	}

	public String getUdf49() {
		return udf49;
	}

	public void setUdf49(String udf49) {
		this.udf49 = udf49;
	}

	public String getUdf50() {
		return udf50;
	}

	public void setUdf50(String udf50) {
		this.udf50 = udf50;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Long getProcessorId() {
		return processorId;
	}

	public void setProcessorId(Long processorId) {
		this.processorId = processorId;
	}

	public String getRiskDescription() {
		return riskDescription;
	}

	public void setRiskDescription(String riskDescription) {
		this.riskDescription = riskDescription;
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
		this.holdDate = date1;
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

	public int getRiskStatus() {
		return riskStatus;
	}

	public void setRiskStatus(int riskStatus) {
		this.riskStatus = riskStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getRuStatus() {
		return ruStatus;
	}

	public void setRuStatus(int ruStatus) {
		this.ruStatus = ruStatus;
	}

	@Override
	public String toString() {
		return "IntermediateTransaction [transactionId=" + transactionId + ", agentName=" + agentName + ", agentId="
				+ agentId + ", mid=" + mid + ", merchantOrderNo=" + merchantOrderNo + ", txnDatetime=" + txnDatetime
				+ ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", udf1=" + udf1 + ", udf2="
				+ udf2 + ", udf3=" + udf3 + ", udf4=" + udf4 + ", udf5=" + udf5 + ", udf6=" + udf6 + ", udf7=" + udf7
				+ ", udf8=" + udf8 + ", udf9=" + udf9 + ", udf10=" + udf10 + ", udf11=" + udf11 + ", udf12=" + udf12
				+ ", udf13=" + udf13 + ", udf14=" + udf14 + ", udf15=" + udf15 + ", udf16=" + udf16 + ", udf17=" + udf17
				+ ", udf18=" + udf18 + ", udf19=" + udf19 + ", udf20=" + udf20 + ", udf21=" + udf21 + ", udf22=" + udf22
				+ ", udf23=" + udf23 + ", udf24=" + udf24 + ", udf25=" + udf25 + ", udf26=" + udf26 + ", udf27=" + udf27
				+ ", udf28=" + udf28 + ", udf29=" + udf29 + ", udf30=" + udf30 + ", udf31=" + udf31 + ", udf32=" + udf32
				+ ", udf33=" + udf33 + ", udf34=" + udf34 + ", udf35=" + udf35 + ", udf36=" + udf36 + ", udf37=" + udf37
				+ ", udf38=" + udf38 + ", udf39=" + udf39 + ", udf40=" + udf40 + ", udf41=" + udf41 + ", udf42=" + udf42
				+ ", udf43=" + udf43 + ", udf44=" + udf44 + ", udf45=" + udf45 + ", udf46=" + udf46 + ", udf47=" + udf47
				+ ", udf48=" + udf48 + ", udf49=" + udf49 + ", udf50=" + udf50 + ", txnAmount=" + txnAmount
				+ ", txnDiscount=" + txnDiscount + ", appliedCoupen=" + appliedCoupen + ", ru=" + ru + ", status="
				+ status + ", message=" + message + ", orderNumber=" + orderNumber + ", paymentType=" + paymentType
				+ ", settlementAmount=" + settlementAmount + ", settlementDate=" + settlementDate
				+ ", settlementAtomTxnId=" + settlementAtomTxnId + ", settlementStatus=" + settlementStatus
				+ ", reconStatus=" + reconStatus + ", bid=" + bid + ", surcharge=" + surcharge + ", productType="
				+ productType + ", processorId=" + processorId + ", riskDescription=" + riskDescription + ", holdDate="
				+ holdDate + ", releaseDate=" + releaseDate + ", riskStatus=" + riskStatus + ", remark=" + remark
				+ ", ruStatus=" + ruStatus + "]";
	}

}
