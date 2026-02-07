package com.ftk.pg.responsevo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MerchantRuResponseVo {

	private String getepayTxnId = "";
	private String mid = "";
	private String txnAmount = "";
	private String txnStatus = "";
	private String merchantOrderNo = "";
	private String udf1 = "";
	private String udf2 = "";
	private String udf3 = "";
	private String udf4 = "";
	private String udf5 = "";
	private String udf6 = "";
	private String udf7 = "";
	private String udf8 = "";
	private String udf9 = "";
	private String udf10 = "";
	private String udf41 = "";
	private String custRefNo = "";
	private String paymentMode = "";
	private String discriminator = "";
	private String message = "";
	private String paymentStatus;
	private String txnDate = "";
	private String surcharge = "";
	private String totalAmount = "";
	private String settlementAmount = "";
	private String settlementRefNo = "";
	private String settlementDate = "";
	private String settlementStatus = "";
	private String txnNote = "";
	private String refundStatus;
	private String refundAmount;
	private String bankError;
	private String agentName;

}
