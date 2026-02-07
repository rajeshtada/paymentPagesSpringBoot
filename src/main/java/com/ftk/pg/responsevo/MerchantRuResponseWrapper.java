package com.ftk.pg.responsevo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MerchantRuResponseWrapper {

	private String status;
	private String message;
	private String mid;
	private String response;
	private String terminalId;	
}
