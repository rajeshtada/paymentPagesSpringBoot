package com.ftk.pg.restcontroller;


import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftk.pg.dto.RequestWrapper;
import com.ftk.pg.dto.ResponseWrapper;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.exception.InvalidAuthKeyException;
import com.ftk.pg.service.CommonService;
import com.ftk.pg.service.PaymentInitiationService;
import com.ftk.pg.service.PaymentProcessService;
import com.ftk.pg.util.Util;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("pg")
public class PaymentController {
	private Logger logger = LoggerFactory.getLogger(PaymentController.class);
	
	private final PaymentInitiationService paymentInitiationService;

	private final PaymentProcessService paymentProcessService;

	private final CommonService commonService;

	
	@PostMapping("/api/payment")
	public ResponseEntity<ResponseWrapper<String>> pgPayment(@RequestHeader("Authorization") String auth,
			@RequestBody RequestWrapper requestWrapper) throws Exception {
		try {
			String token = Util.extractToken(auth);
			MDC.put("messageId", token);
			return paymentInitiationService.pgPayment(requestWrapper, token);
		}finally {
			MDC.clear();
		}

	}

	@PostMapping(value = "/api/pay")
	public ResponseEntity<ResponseWrapper<String>> pay(@RequestHeader("Authorization") String auth,
			@RequestBody RequestWrapper requestWrapper) throws Exception {
		try {
			String token = Util.extractToken(auth);
			MDC.put("messageId", token);
			return paymentProcessService.pay(requestWrapper,token);
		}finally {
			MDC.clear();
		}

	}
	
	@PostMapping(value = "/api/verifyOtp")
	public ResponseEntity<ResponseWrapper<String>> verifyOtp(@RequestHeader("Authorization") String auth,
			@RequestBody RequestWrapper requestWrapper) throws Exception {
		try {
			String token = Util.extractToken(auth);
			MDC.put("messageId", token);
			return commonService.verifyOtp(requestWrapper,token);
		}finally {
			MDC.clear();
		}
	}

	@PostMapping(value = "/api/resendOtp")
	public ResponseEntity<ResponseWrapper<String>> resendOtp(@RequestHeader("Authorization") String auth,
			@RequestBody RequestWrapper requestWrapper) throws Exception {
		try {
			String token = Util.extractToken(auth);
			MDC.put("messageId", token);
			return commonService.resendOtp(requestWrapper,token);
		}finally {
			MDC.clear();
		}
	}
	
	@PostMapping("/api/upiCollect")
	public ResponseEntity<ResponseWrapper<String>> upiCollect(@RequestHeader("Authorization") String auth,
			@RequestBody RequestWrapper requestWrapper) throws Exception {
		try {
			String token = Util.extractToken(auth);
			MDC.put("messageId", token);
			return commonService.upiCollect(requestWrapper,token);
		}finally {
			MDC.clear();
		}
	}
	
	@PostMapping("/api/validateCollect")
	public ResponseEntity<ResponseWrapper<String>> validateCollect(@RequestHeader("Authorization") String auth,
			@RequestBody RequestWrapper requestWrapper) throws Exception {
		try {
			String token = Util.extractToken(auth);
			MDC.put("messageId", token);
			return commonService.validateCollect(requestWrapper,token);
		}finally {
			MDC.clear();
		}
	}
	
	@PostMapping("/api/upiIciciCollect")
	public ResponseEntity<ResponseWrapper<String>> upiIciciCollect(@RequestHeader("Authorization") String auth,
			@RequestBody RequestWrapper requestWrapper) throws Exception {
		try {
			String token = Util.extractToken(auth);
			MDC.put("messageId", token);
			return commonService.upiIciciCollect(requestWrapper,token);
		}finally {
			MDC.clear();
		}
	}
	
	@PostMapping("/api/redirectProcess")
	public ResponseEntity<ResponseWrapper<String>> redirectProcess(@RequestHeader("Authorization") String auth,
			@RequestBody RequestWrapper requestWrapper) throws Exception {
		try {
			String token = Util.extractToken(auth);
			MDC.put("messageId", token);
			return commonService.redirectProcess(requestWrapper,token);
		}finally {
			MDC.clear();
		}
	}
	
	@PostMapping("/api/cancelRedirection")
	public ResponseEntity<ResponseWrapper<String>> cancelRedirection(@RequestHeader("Authorization") String auth,
			@RequestBody RequestWrapper requestWrapper) throws Exception {
		try {
			String token = Util.extractToken(auth);
			MDC.put("messageId", token);
			return commonService.cancelRedirection(requestWrapper,token);
		}finally {
			MDC.clear();
		}
	}
	
}
