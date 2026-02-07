package com.ftk.pg.responsevo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RedirectResponseVo {
	
	private String redirectUrl;
	private String retry;
//	private RedirectPaymentResponseVo paymentResponse;
	private MerchantRuResponseWrapper paymentResponse;
	

}
