package com.ftk.pg.restcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftk.pg.dto.RequestWrapper;
import com.ftk.pg.dto.ResponseWrapper;
import com.ftk.pg.requestvo.UserRequest;
import com.ftk.pg.responsevo.UserResponse;
import com.ftk.pg.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("pg/api")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	
	public final UserService userService;
	
	
	@PostMapping("fetchUserName")
	public ResponseEntity<ResponseWrapper<UserResponse>> fetchUserName(
	       @RequestBody UserRequest userRequest, HttpServletRequest request) throws Exception {
		try {
			ResponseEntity<ResponseWrapper<UserResponse>> response = userService.fetchUserName(userRequest, request);
			
			logger.info("Response for fetchUserName: {}", response.getBody().getMessage());
			return response;
		}catch(Exception e) {
			ResponseWrapper<UserResponse> errorResponse = new ResponseWrapper<>();
			errorResponse.setMessage("Internal Server Error: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
		
	}
	
//	@PostMapping("updateCustomerDetials")
//	public ResponseEntity<ResponseWrapper<String>> updateCustomerDetails(@RequestBody RequestWrapper requestWrapper){
//		try {
//			return userService.updateCustomerDetails(requestWrapper);
//		}catch(Exception e) {
//			ResponseWrapper<UserResponse> errorResponse = new ResponseWrapper<>();
//			errorResponse.setMessage("Internal Server Error: " + e.getMessage());
//		}
//		return null;
//		
//	}
}
