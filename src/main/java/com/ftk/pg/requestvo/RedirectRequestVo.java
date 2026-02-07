package com.ftk.pg.requestvo;

import com.ftk.pg.responsevo.PaymentResponse;
import com.ftk.pg.responsevo.RedirectResponseVo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RedirectRequestVo {

	private String otp;
	
	private String status;
	
	private String transactionId;
}

