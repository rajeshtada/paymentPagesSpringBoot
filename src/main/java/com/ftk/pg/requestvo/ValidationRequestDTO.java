package com.ftk.pg.requestvo;


import java.util.Map;

public class ValidationRequestDTO {
	private String entityCode;
	private Map<String, String> fields;

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public Map<String, String> getFields() {
		return fields;
	}

	public void setFields(Map<String, String> fields) {
		this.fields = fields;
	}

}
