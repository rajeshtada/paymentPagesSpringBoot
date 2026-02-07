//package com.ftk.pg.service;
//
//import static org.mockito.Mockito.when;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.junit.jupiter.api.TestInstance.Lifecycle;
//import org.mockito.ArgumentMatchers;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.ResponseEntity;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ftk.pg.dto.RequestWrapper;
//import com.ftk.pg.dto.ResponseWrapper;
//import com.ftk.pg.modal.TransactionLog;
//import com.ftk.pg.repo.TransactionLogRepo;
//import com.ftk.pg.responsevo.UpiCollectResponse;
//import com.ftk.pg.util.EncryptionUtil;
//
//@TestInstance(Lifecycle.PER_CLASS)
//public class CommonServiceTest {
//	
//	@InjectMocks
//	CommonService commonService;
//	@Mock
//	TransactionLogRepo transactionLogRepo;
//	@Mock
//	private EncryptionService encryptionService;
//	
//	@BeforeAll
//	void setup() {
//		CommonServiceTest cst = new CommonServiceTest();
//		MockitoAnnotations.openMocks(this);
//	}
//	
//	@Test
//	public void validateCollectTest() throws Exception{
//		
//		ObjectMapper objectMapper = new ObjectMapper();
//		
//		TransactionLog txn = new TransactionLog();
//		txn.setResponseCode("00");
//		txn.setProcessorCode("000");
//		txn.setTxnStatus("success");
//		when(transactionLogRepo.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(txn));
//		
//		String token = "ea7ac1c2-327c-4a4c-8d21-2788b116227e";
//		UpiCollectResponse upiCollectResponse = new UpiCollectResponse();
//		upiCollectResponse.setMessage("Y");
//		String encString = objectMapper.writeValueAsString(upiCollectResponse);
//		String encData = EncryptionUtil.encrypt(encString, token);
//		when(encryptionService.encryptResponse(ArgumentMatchers.notNull(),ArgumentMatchers.anyString())).thenReturn(encData);
//		
//		
//		RequestWrapper requestWrapper = new  RequestWrapper();
//		requestWrapper.setData("1aFUKXT6R8aEtJZAuqYj9np8W86iMQSK9eKKGp/u9u0ysdztl+6Gu/G9d0qh+uqhatAxprrRHfzDhWsohjC6BxxZ6qwfcAMJdw==");
//		
//		ResponseEntity<ResponseWrapper<String>> validateCollect = commonService.validateCollect(requestWrapper, token);
//		
//		System.out.println(validateCollect.getBody());
//		String data = validateCollect.getBody().getData();
//		UpiCollectResponse decrypt = EncryptionUtil.decryptdata(data, token,  UpiCollectResponse.class);
//		System.out.println(decrypt);
//		assertEquals(decrypt.getMessage(), "Y");
//
//		
//	}
//	
//}
