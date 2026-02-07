package com.ftk.pg.responsevo;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IciciImpsRequeryResponseWrapper {

	@JsonProperty(value = "ImpsResponse")	
	private IciciImpsRequeryResponse impsResponse;

	public IciciImpsRequeryResponse getImpsResponse() {
		return impsResponse;
	}

	public void setImpsResponse(IciciImpsRequeryResponse impsResponse) {
		this.impsResponse = impsResponse;
	}	
	
	
	
}
