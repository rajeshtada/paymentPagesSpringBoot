package com.ftk.pg.util;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.requestvo.RequestProductsVo;

public class ValidateUtil {
	static Logger logger = LogManager.getLogger(ValidateUtil.class);

	public static final RequestProductsVo parseRequestProducts(String request) {
		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(RequestProductsVo.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            RequestProductsVo products = (RequestProductsVo) jaxbUnmarshaller
					.unmarshal(new StringReader(request.trim()));
			return products;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

}
