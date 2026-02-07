package com.ftk.pg.requestvo;

import lombok.Data;

@Data
public class GenerateOTPResponse {

	
	private String status;
	private String validityPeriod;
	private String errorcode;
	private String errormsg;
	private String pgTransactionId;
	
	
}