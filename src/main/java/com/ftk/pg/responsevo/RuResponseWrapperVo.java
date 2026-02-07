package com.ftk.pg.responsevo;

import java.util.Map;

public class RuResponseWrapperVo {

	public String type;
	
	public String subType;
	
	public Map<String,String> requestMap;
	
	public Map<String,String> paramMap;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public Map<String, String> getRequestMap() {
		return requestMap;
	}

	public void setRequestMap(Map<String, String> requestMap) {
		this.requestMap = requestMap;
	}

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}

	@Override
	public String toString() {
		return "RuResponseWrapperVo [type=" + type + ", subType=" + subType + ", requestMap=" + requestMap
				+ ", paramMap=" + paramMap + "]";
	}


	
	
}
