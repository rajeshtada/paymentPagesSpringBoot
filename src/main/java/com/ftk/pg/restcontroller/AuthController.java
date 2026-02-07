//package com.ftk.getepaymentpages.restcontroller;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.ftk.getepaymentpages.dto.ResponseWrapper;
//import com.ftk.getepaymentpages.encryption.EncryptedRequest;
//import com.ftk.getepaymentpages.encryption.EncryptedResponse;
//import com.ftk.getepaymentpages.responsevo.PaymentResponse;
//import com.ftk.getepaymentpages.service.AuthService;
//
//@RestController
//@RequestMapping("pg")
//public class AuthController {
//
//	@Autowired
//	private AuthService authService;
//
//	@PostMapping("/doPayment")
//	public ResponseEntity<ResponseWrapper<PaymentResponse>> validatepaymentRequest(
//			@RequestParam("token") String token) {
//
//		return authService.dopayment(token);
//
//	}
//
//	@PostMapping("/validatepaymentRequest")
//	public ResponseEntity<ResponseWrapper<EncryptedResponse>> validatepaymentRequest(
//			@RequestBody EncryptedRequest encdata) throws Exception {
//
//		EncryptedResponse response = authService.validatepaymentRequest(encdata);
//		if (response == null) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//					.body(new ResponseWrapper<>(HttpStatus.BAD_REQUEST, "User creation failed", null));
//		}
//		return ResponseEntity.status(HttpStatus.CREATED)
//				.body(new ResponseWrapper<>(HttpStatus.OK, "User created successfully", response));
//
//	}
//
//	@PostMapping("/enc")
//	public ResponseEntity<ResponseWrapper<EncryptedResponse>> encdata(@RequestBody String data) {
//		try {
//			EncryptedResponse response = authService.encrypt(data);
//			return ResponseEntity.status(HttpStatus.CREATED)
//					.body(new ResponseWrapper<>(HttpStatus.OK, "User created successfully", response));
//		} catch (Exception e) {
//			new GlobalExceptionHandler().customException(e);
//		}
//
//		return null;
//
//	}
//
//}


