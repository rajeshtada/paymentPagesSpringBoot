package com.ftk.pg.restcontroller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ftk.pg.dto.RequestWrapper;
import com.ftk.pg.dto.ResponseWrapper;
import com.ftk.pg.pi.modal.MerchantKeys;
import com.ftk.pg.pi.repo.MerchantKeysRepo;
import com.ftk.pg.service.EncryptionService;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@RestController
@RequestMapping("pg/api/v1/")
public class EncryptionController {

	
	private final EncryptionService encryptionService;

	
	private final MerchantKeysRepo merchantKeysRepo;

	Logger logger = LogManager.getLogger(EncryptionController.class);

//	@PreAuthorize("hasAuthority('USER')")
	@PostMapping("enc")
	public @ResponseBody RequestWrapper encApi(@RequestBody RequestWrapper requestWrapper) throws Exception {
//		MerchantKeys merchantKeys = merchantKeysRepo.findByMidAndTerminalId(Long.parseLong(requestWrapper.getMid()),
//				requestWrapper.getTerminalId());
//		return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(HttpStatus.OK.value(), null,
//				encryptionService.encrypt(requestWrapper.getData(), merchantKeys)));
		requestWrapper.setData(encryptionService.encryptResponse(requestWrapper.getData(), requestWrapper.getToken()));
		return requestWrapper;
	}

//	@PreAuthorize("hasAuthority('USER')")
	@PostMapping("dec")
	public Object decApi(@RequestBody RequestWrapper requestWrapper) {
//		MerchantKeys merchantKeys = merchantKeysRepo.findByMidAndTerminalId(Long.parseLong(requestWrapper.getMid()),
//				requestWrapper.getTerminalId());
//		return encryptionService.decrypt(String.valueOf(requestWrapper.getData()), merchantKeys, Object.class);
		requestWrapper.setData(encryptionService.decrypt(String.valueOf(requestWrapper.getData()),
				requestWrapper.getToken(), Object.class));
		return requestWrapper;
	}

}
