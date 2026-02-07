package com.ftk.pg.restcontroller;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ftk.pg.dto.RequestWrapper;
import com.ftk.pg.dto.ResponseBuilder;
import com.ftk.pg.dto.ResponseWrapper;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.service.ChargesService;
import com.ftk.pg.util.Util;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pg")
public class ChargesController {

	Logger logger =LogManager.getLogger(ChargesController.class);


	
	private  final ChargesService chargesService;

	@PostMapping("/api/charges")
	public ResponseEntity<ResponseWrapper<String>> generateCharges(@RequestHeader("Authorization") String auth,
			@RequestBody RequestWrapper requestWrapper) throws Exception {
		String token = Util.extractToken(auth);
		ThreadContext.put("messageId", token);
		logger.info("INSIDE GENERATE CHARGES");
		return chargesService.generateCharges2(requestWrapper, token);
	}
	
	@PostMapping("/test")
	public ResponseEntity<ResponseWrapper<String>> test() throws Exception {
		try {
//			MDC.put("messageId", UUID.randomUUID().toString());
			ThreadContext.put("messageId", UUID.randomUUID().toString());
			logger.info("INSIDE Test");
			logger.info("start time==>"+LocalDateTime.now());
			 String API_URL = "https://sandboxapi.getepay.in/txnstatussync";
		     String API_KEY = "rYMVCwj6hq2IAVXxY7uCXdRWeyN8XphJ33b1eMmsg";

			
			HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	        headers.set("x-api-key", API_KEY);

	        // Create request body
	        String requestBody = "{\"data\":\"" + 19354489 + "\"}";

	        // Create the HTTP entity
	        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

	        // Create RestTemplate instance
	        RestTemplate restTemplate = new RestTemplate();
	        
	        
			logger.info("REsponse==>"+restTemplate.exchange(
	                API_URL,
	                HttpMethod.POST,
	                entity,
	                String.class
	            ));

		}catch(Exception e) {
			logger.info("end time==>"+LocalDateTime.now());
			new GlobalExceptionHandler().customException(e);
			logger.error(e.getMessage());
		}
	
		return ResponseBuilder.buildResponse(null,
				"SUCCESS", HttpStatus.OK);
	}
}
