package com.ftk.pg.restcontroller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.ftk.pg.dto.ResponseWrapper;

@RestController
@EnableWebMvc
//@RequestMapping("pg")
public class PingController {
	private Logger logger = LoggerFactory.getLogger(PingController.class);

	@RequestMapping(path = "/pg/ping", method = RequestMethod.GET)
	public Map<String, String> ping() {
		logger.info("pong " + "Hello, World! " + LocalDateTime.now());
		Map<String, String> pong = new HashMap<>();
		pong.put("pong", "Hello, World!");
		return pong;
	}

	@GetMapping(value = { "/", "/pg" })
	public ResponseEntity<ResponseWrapper<String>> homePage() throws Exception {
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(new ResponseWrapper<>(HttpStatus.OK.value(), "Welcome to Pg Api", null));
	}

}
