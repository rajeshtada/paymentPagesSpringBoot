package com.ftk.pg.requestvo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PaymentDeclineVo {

	@NotEmpty
	private Long transactionId;
	@NotEmpty
	private String reason;
	
}
