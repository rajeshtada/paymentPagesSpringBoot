package com.ftk.pg.requestvo;

import java.util.HashMap;
import java.util.Map;

public class ChallanParam {
	Map<String, String> params = new HashMap<>();

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	@Override
	public String toString() {
		return "ChallanParam [params=" + params + "]";
	}
	
	

}
