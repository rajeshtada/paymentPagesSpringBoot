package com.ftk.pg.requestvo;

import lombok.Data;

@Data
public class RequestParamsWrapper {

	private String method;
	private RequestParams requestParams;
}
