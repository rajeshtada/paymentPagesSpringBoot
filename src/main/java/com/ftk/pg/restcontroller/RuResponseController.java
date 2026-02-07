package com.ftk.pg.restcontroller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftk.pg.dto.RequestWrapper;
import com.ftk.pg.dto.ResponseWrapper;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.TransactionLog;
import com.ftk.pg.repo.TransactionLogRepo;
import com.ftk.pg.responsevo.MerchantRuResponseWrapper;
import com.ftk.pg.service.CommonService;
import com.ftk.pg.service.HomeService;
import com.ftk.pg.service.RuResponseService;
import com.ftk.pg.vo.cashfree.CashfreeCallbackResponse;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("pg")
public class RuResponseController {

	private static final Logger logger = LoggerFactory.getLogger(RuResponseController.class);
	
	private final HomeService homeService;
	
	private final RuResponseService ruResponseService;
	
	private final TransactionLogRepo transactionLogRepo;
	
	private final CommonService commonService;
	
	@PostMapping(value = "/api/getRuResponse")
	public ResponseEntity<ResponseWrapper<String>> getRuResponse(
			@RequestBody RequestWrapper requestWrapper) throws Exception {
		String token = UUID.randomUUID().toString(); //Util.extractToken(auth);
		try {
			MDC.put("messageId", token);
			return ruResponseService.processRuResponse(requestWrapper);
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server Error", null));
		}finally {
			MDC.clear();
		}
	}
	
	@PostMapping("/api/cfCallbackResponse")
	public String cfCallbackResponse(@RequestBody String requestString) {
		try {
			logger.info("Received cfCallbackResponse request=>" + requestString);
			Gson gson = new Gson();
			CashfreeCallbackResponse response = gson.fromJson(requestString, CashfreeCallbackResponse.class);
			logger.info("order Id in response=>" + response.getData().getOrder().getOrder_id());

			TransactionLog transactionDetail = transactionLogRepo.findById(Long.valueOf(response.getData().getOrder().getOrder_id())).get();
			
			if (response.getData().getPayment().getPayment_status() != null
					&& response.getData().getPayment().getPayment_status().equalsIgnoreCase("SUCCESS")) {
				transactionDetail.setResponseCode("00");
				transactionDetail.setTxnStatus("SUCCESS");
				transactionDetail.setStage("Transaction is successfully processed. ");
				transactionDetail.setProcessorCode("200");
			} else {
				transactionDetail.setResponseCode("01");
				transactionDetail.setTxnStatus("FAILED");
				transactionDetail.setStage("Transaction failed. ");
				transactionDetail.setProcessorCode(String.valueOf("01"));
			}

			transactionLogRepo.save(transactionDetail);

			return "SUCCESS";
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}
	 
	@PostMapping(value = "/api/getPaymentResponse")
	public ResponseEntity<ResponseWrapper<String>> getPaymentResponse(@RequestBody MerchantRuResponseWrapper requestWrapper) throws Exception {
		String token = UUID.randomUUID().toString();
		try {
			MDC.put("messageId", token);
			return commonService.processPaymentResponse(requestWrapper);
		}finally {
			MDC.clear();
		}
	}
}
