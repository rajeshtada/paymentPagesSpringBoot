package com.ftk.pg.responsevo;

import java.util.Map;

import lombok.Data;

@Data
public class PayResponse {

	private String uuid;
	private String tid;
	private String ipAddress;
	private String cardNo;
	private String merchantName;
	private String amount;
	private String authoriztion;
	private String baseUrlTxn;
	private String dataBEPG;
	private String productType;
	private String pgTransactionId;
	private String ru;
	private String TokenId;
	private String merchantId;
	private String otpValidation;
	private String tAmount;
	private Map<String, String> requestMapObj;
	private String htmlCode;
	private PaymentResponse paymentResponse;
	private String flag;
	private String message;
	private String otp;
	private String otpCount;
	private String responseCode;
	private String status;
}
