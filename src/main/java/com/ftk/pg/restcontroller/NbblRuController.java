package com.ftk.pg.restcontroller;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftk.pg.dto.RequestWrapper;
import com.ftk.pg.dto.ResponseBuilder;
import com.ftk.pg.dto.ResponseWrapper;
import com.ftk.pg.service.CommonService;
import com.ftk.pg.service.PaymentInitiationService;
import com.ftk.pg.service.PaymentProcessService;
import com.ftk.pg.util.Util;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("pg")
public class NbblRuController {

	Logger logger =LogManager.getLogger(NbblRuController.class);
	
	
	@PostMapping("/api/nbblResponse/refID")
	public ResponseEntity<ResponseWrapper<String>> pgPayment(@PathVariable("refID") String refID , @RequestBody Object requestWrapper) throws Exception {
		String token = UUID.randomUUID().toString(); //Util.extractToken(auth);
		try {
			MDC.put("messageId", token);

			logger.info(requestWrapper.toString());
		String response = "{\n"
				+ "  \"refID\": \""+refID+"\",\n"
				+ "  \"msgID\": \"RPFZBR15RQWOW2Q87XW9Z20KGDV79712168\",\n"
				+ "  \"result\": \"SUCCESS\",\n"
				+ "  \"ts\": \"/1280-05-36T18:06:03-14:1\",\n"
				+ "  \"api\": \"ACK\",\n"
				+ "  \"errCode\": \"Qh2shZJO\",\n"
				+ "  \"errReason\": \"UTAzbsPCSp45oJGvNqs4o4Gh5A8hFHaPKg1koKLW yy7hlP3U0Kni6AIJpo3A77QOh8ybgg P11u4A\"\n"
				+ "}";
		
		return ResponseBuilder.buildResponse(response, "SUCCESS", HttpStatus.OK);
		
		}finally {
			MDC.clear();
		}
	}
	
}
