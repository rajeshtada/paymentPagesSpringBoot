package com.ftk.pg.responsevo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RedirectPaymentResponseVo {

	private Long getepayTxnId;
	private Long mid;
	private String txnAmount;
	private String totalCharges;
	private String totalAmount;
	private String txnStatus;
	private String merchantOrderNo;
	private String description;
	private String discriminator;
	
	
	private String signature;
	private String udf1;
	private String udf2;
	private String udf3;
	private String udf4;
	private String udf5;
	private String tid;
	
}
