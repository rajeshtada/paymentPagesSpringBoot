package com.ftk.pg.restcontroller;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftk.pg.dto.RequestWrapper;
import com.ftk.pg.dto.ResponseWrapper;
import com.ftk.pg.encryption.AesEncryption;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.exception.ResourceNotFoundException;
import com.ftk.pg.service.ApiService;
import com.ftk.pg.service.HomeService;
import com.ftk.pg.service.RuResponseService;
import com.ftk.pg.util.Util;
import com.ftk.pg.util.Utilities;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("pg")
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private final HomeService homeService;

	private final RuResponseService ruResponseService;

	
	@PostMapping(value = "/api/pgTimeOut")
	public ResponseEntity<ResponseWrapper<String>> pgTimeOut(@RequestHeader("Authorization") String auth,
			@RequestBody RequestWrapper requestWrapper) throws Exception {
		String token = Util.extractToken(auth);
		try {
			MDC.put("messageId", token);
			return homeService.pgtimeout(requestWrapper, token);
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server Error", null));
		} finally {
			MDC.clear();
		}
	}

	@PostMapping(value = "/api/transactionCancel")
	public ResponseEntity<ResponseWrapper<String>> transactionCancel(@RequestHeader("Authorization") String auth,
			@RequestBody RequestWrapper requestWrapper) throws Exception {
		String token = Util.extractToken(auth);
		try {
			MDC.put("messageId", token);
			return homeService.transactionCancel(requestWrapper, token);
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server Error", null));
		} finally {
			MDC.clear();
		}
	}

	@PostMapping(value = "/api/showDynamicQr")
	public ResponseEntity<ResponseWrapper<String>> showDynamicQr(@RequestHeader("Authorization") String auth,
			@RequestBody RequestWrapper requestWrapper) throws Exception {
		String token = Util.extractToken(auth);
		try {
			MDC.put("messageId", token);
			return homeService.showDynamicQr(requestWrapper, token);
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server Error", null));
		} finally {
			MDC.clear();
		}
	}

	@GetMapping(value = "/api/gchallan")
	public ResponseEntity<ResponseWrapper<String>> gchalan(@RequestHeader("Authorization") String auth,
			@RequestBody RequestWrapper requestWrapper) throws Exception {
		String token = Util.extractToken(auth);
		try {
			MDC.put("messageId", token);
			return homeService.gchalan(requestWrapper, token);
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server Error", null));
		} finally {
			MDC.clear();
		}
	}

	@PostMapping(value = "/api/payModeTrigger")
	public ResponseEntity<ResponseWrapper<String>> payModeTrigger(@RequestHeader("Authorization") String auth,
			@RequestBody RequestWrapper requestWrapper) throws Exception {
		String token = Util.extractToken(auth);
		try {
			MDC.put("messageId", token);
			return homeService.payModeTrigger(requestWrapper, token);
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server Error", null));
		} finally {
			MDC.clear();
		}
	}

	@PostMapping(value = "api/preProceedPayTrigger")
	public ResponseEntity<ResponseWrapper<String>> preProceedPayTrigger(@RequestHeader("Authorization") String auth,
			@RequestBody RequestWrapper requestWrapper) throws Exception {
		String token = Util.extractToken(auth);
		try {
			MDC.put("messageId", token);
			return homeService.preProceedPayTrigger(requestWrapper, token);
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server Error", null));
		} finally {
			MDC.clear();
		}
	}

	@PostMapping(value = "/api/postProceedPayTrigger")
	public ResponseEntity<ResponseWrapper<String>> postProceedPayTrigger(@RequestHeader("Authorization") String auth,
			@RequestBody RequestWrapper requestWrapper) throws Exception {
		String token = Util.extractToken(auth);
		ThreadContext.put("messageId", token);
		try {
			MDC.put("messageId", token);
			return homeService.postProceedPayTrigger(requestWrapper, token);
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server Error", null));
		} finally {
			MDC.clear();
		}
	}

	@PostMapping(value = "/api/getTestLink")
	public ResponseEntity<ResponseWrapper<String>> getTestLink(@RequestHeader("Authorization") String auth,
			@RequestBody RequestWrapper requestWrapper) throws Exception {
		String token = Util.extractToken(auth);
		ThreadContext.put("messageId", token);
		return homeService.getTestLink(requestWrapper, token);
	}
	
	@PostMapping(value = "/api/getXmlTest")
	public ResponseEntity<ResponseWrapper<String>> getXmlTest(@RequestHeader("Authorization") String auth,
			@RequestBody RequestWrapper requestWrapper) throws Exception {
		String token = Util.extractToken(auth);
		ThreadContext.put("messageId", token);
		return homeService.getXmlTest(requestWrapper, token);
	}

	@GetMapping("/api/showMediaImage/{type}")
	public ResponseEntity<FileSystemResource> download(@PathVariable String type, @RequestParam("i") String fileName) {

		try {
			fileName = fileName.replace(" ", "+");
			String imgName = AesEncryption.decrypt(fileName);
//			imgPath = imgPath.replace("/media/shared/lambda/pg/dynamicqrpath", "/mnt/efs/lambda/pg/dynamicqrpath/");

			String rootPath = "";
			if (type.equalsIgnoreCase("type1")) {
				rootPath = Utilities.IMAGE_ROOTPATH;
			} else {
				rootPath = Utilities.TMP_IMAGE_ROOTPATH;
			}
			String imgPath = rootPath + File.separator + imgName;
			File file = new File(imgPath);

			if (!file.exists()) {
				throw new ResourceNotFoundException("Not a valid path..  ");
			}

			FileSystemResource fileResource = new FileSystemResource(file);
			return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=\"" + file + "\"")
					.header("Cache-Control", "no-store, no-cache, must-revalidate").header("Pragma", "no-cache")
					.header("type", "image/jpeg").body(fileResource);
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			HttpHeaders headers = new HttpHeaders();
			return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
		}
	}

}
