package com.ftk.pg.requestvo;

import com.ftk.pg.responsevo.PayResponse;

import lombok.Data;
@Data
public class OTPVerifyWrapperRequest {
	
	private String type;
	private PayResponse payResponse; 

}
