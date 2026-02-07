package com.ftk.pg.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class ResponseWrapper<T> {

	private String message;
	private T data;
	private int status;

	public ResponseWrapper(String message, T data) {
		this.message = message;
		this.data = data;
	}

	public ResponseWrapper(int status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

}
