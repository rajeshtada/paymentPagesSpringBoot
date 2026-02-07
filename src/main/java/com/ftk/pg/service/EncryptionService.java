package com.ftk.pg.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.util.EncryptionUtil;

@Service
public class EncryptionService {

	Logger logger = LogManager.getLogger(EncryptionService.class);

	private ObjectMapper objectMapper = new ObjectMapper();

//	public String encryptResponse(Object object) throws Exception {
//		Gson gson=new Gson();
//		String encString = objectMapper.writeValueAsString(object);
//		String encData=gson.toJson(EncryptionUtil.encryptdata(encString));
//		return encData;
//	}

	public String encryptResponse(Object object, String ivKey) throws Exception {
//		Gson gson = new Gson();
		String encString = objectMapper.writeValueAsString(object);
		String encData = EncryptionUtil.encrypt(encString, ivKey);
		return encData;
	}

//	public String encrypt(Object decObject, MerchantKeys merchantKeys) {
//		try {
//			String encString = objectMapper.writeValueAsString(decObject);
////			logger.info("encString => " + encString);
//			String enc = EncryptionUtil.encryptdata(encString);
//			return enc;
//		} catch (Exception e) {
//			new GlobalExceptionHandler().customException(e);
//		}
//		return null;
//	}

	public Object decrypt(String enc, String ivKey, Class<?> clazz) {
		try {
			Object decObject = EncryptionUtil.decryptdata(enc, ivKey, clazz);
//			logger.info("decString => " + decString);
//			Object decObject = objectMapper.readValue(decString, clazz);
			return decObject;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

//	public String encrypt(Object decObject, Class<?> clazz) throws Exception {
//			Gson gson=new Gson();
//			String encString = objectMapper.writeValueAsString(decObject);
////			logger.info("encString => " + encString);
//			String enc = gson.toJson(EncryptionUtil.encryptdata(encString));
//			return enc;
//		
//	}

//	public Object decrypt(String enc, Class<?> clazz) {
//		try {
//			String decString = EncryptionUtil.decryptdata(enc);
////			logger.info("decString => " + decString);
//			Object decObject = objectMapper.readValue(decString, clazz);
//			return decObject;
//		} catch (Exception e) {
//			new GlobalExceptionHandler().customException(e);
//		}
//		return null;
//	}

}