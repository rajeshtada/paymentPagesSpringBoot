package com.ftk.pg.responsevo;

import lombok.Data;

@Data
public class UpiCollectResponse {

	private String transactionId;
	private String udf7;
	private String amount;
	private String currency;
	private PaymentResponse paymentResponse;
	private String message;
	
}
