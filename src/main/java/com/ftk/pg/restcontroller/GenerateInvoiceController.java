package com.ftk.pg.restcontroller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ftk.pg.pi.service.GenerateInvoiceService;
import com.ftk.pg.vo.generateInvoice.GenerateInvoiceResponseWrapper;
import com.ftk.pg.vo.generateInvoice.InvoiceStatusResponseWrapper;
import com.ftk.pg.vo.generateInvoice.PgRequestWrapper;

@RestController
@RequestMapping("pg")
public class GenerateInvoiceController {
	
	private Logger logger = LoggerFactory.getLogger(GenerateInvoiceController.class);
	
	@Autowired
	private GenerateInvoiceService generateInvoiceService;
	
	@PostMapping("/generateInvoiceV3")
	public @ResponseBody GenerateInvoiceResponseWrapper generateInvoice(@RequestBody PgRequestWrapper requestWrapper) throws Exception{
		String token = UUID.randomUUID().toString();
		try {
			MDC.put("messageId", token);
			return generateInvoiceService.generateInvoice(requestWrapper);
		} finally {
			MDC.clear();
		}
	}
	
	@PostMapping("/generateInvoiceV1")
	public @ResponseBody GenerateInvoiceResponseWrapper generateInvoiceV1(@RequestBody PgRequestWrapper requestWrapper) throws Exception{
		String token = UUID.randomUUID().toString();
		try {
			MDC.put("messageId", token);
			return generateInvoiceService.generateInvoiceV1(requestWrapper);
		} finally {
			MDC.clear();
		}
	}
	
	@PostMapping("/invoiceStatusV3")
	public @ResponseBody InvoiceStatusResponseWrapper invoiceStatusV3(@RequestBody PgRequestWrapper requestWrapper) throws Exception{
		String token = UUID.randomUUID().toString();
		try {
			MDC.put("messageId", token);
			return generateInvoiceService.invoiceStatusV3(requestWrapper);
		} finally {
			MDC.clear();
		}
	}
}
