package com.ftk.pg.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChargesDto {

	private long mid;
	private String cardNo;
	private String paymentMode;
	private String amount;
	private String bankId;

}
