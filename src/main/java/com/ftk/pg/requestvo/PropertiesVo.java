package com.ftk.pg.requestvo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertiesVo {

	private Long propertyId;

	private String propertyKey;

	private String propertyValue;

	private String createdDate;

	private String modifiedDate;

	private Long createdBy;

	private Long modifiedBy;

	private String status;
	
}
