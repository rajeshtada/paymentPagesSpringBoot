package com.ftk.pg.requestvo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDetailsRequest {
	private String txnId;
	
	private String customerMobNo;
	
	private String customerName;
	
	private String email;
}
