package com.ftk.pg.requestvo;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
@Data
public class PaymentRequest {
	
	
	private String login;
	private String pass;
	private String amt;
	@NotEmpty
	private String paymentMode;
	@NotEmpty
	private String txncurr;
	private String merchantTxnId;
	private String date;
	private String od;
	private String mobile;
	private String carddata;
	private String ru;
	private String signature;
	private String bankid;
	private Long transactionId;
	private String requestType;
	private String paymentType;
	private String mEmail;
	private String mNumber;
	private String productType;
	private String udf1;
	private String udf2;
	private String udf3;
	private String udf4;
	private String udf5;
	private String txnType;
	private String productDetails;
	private String van;
	private String name;
	private String ipAddress;

	

}
