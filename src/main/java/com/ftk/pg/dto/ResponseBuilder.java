package com.ftk.pg.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class ResponseBuilder {

	public static <T> ResponseEntity<ResponseWrapper<T>> buildResponse(T data, String message, HttpStatus status) {
		ResponseWrapper<T> response = new ResponseWrapper<>(status.value(), message, data);
		return new ResponseEntity<>(response, status);
	}


}
