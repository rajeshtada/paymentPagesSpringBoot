package com.ftk.pg.requestvo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@ToString
public class API_PaymentRequest {
	@NotEmpty
	private String mid;

	@NotEmpty
	private String login;

	@NotEmpty
	private String merchantOrderNo;

	@NotEmpty
	private String txnAmount;

	@NotEmpty
	private String udf1;

	@NotEmpty
	private String udf2;

	private String udf3;

	private String udf4;

	private String udf5;
	private String udf6;

	@NotEmpty
	private String productType;

	@NotEmpty
	private String currency;

	@NotEmpty
	private String paymentMode;

	private String txndatetime;

	private String ru;

	private String bankId;

	@NotEmpty
	private String signature;

	private String txnType;

	private long transactionId;

	private String ipAddress;
	

}
