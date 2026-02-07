package com.ftk.pg.encdec;

import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftk.pg.encryption.GcmPgEncryption;
import com.ftk.pg.exception.EncDecException;
import com.ftk.pg.responsevo.PayResponse;

public class EncDec {
	
	public static void main(String[] args) throws Exception {
		String data="{\"method\":\"ccPay\",\"requestParams\":{\"browserLanguage\":\"en-US\",\"browserColorDepth\":\"24\",\"browserScreenHeight\":\"768\",\"browserScreenWidth\":\"1366\",\"browserTZ\":\"330\",\"javaEnabled\":\"false\",\"jsEnabled\":\"false\",\"ipAddress\":\"183.83.176.69\",\"amt\":\"1\",\"txncurr\":\"INR\",\"paymentMode\":\"CC\",\"transactionId\":\"10814\",\"login\":\"TestMerchant\",\"number\":\"4111111111111111\",\"month\":\"02\",\"year\":\"2026\",\"cvc\":\"542\",\"name\":\"priyam\",\"mobile\":\"9999999999\",\"udf3\":\"s@gmail.com\",\"nbbankid\":null,\"nbmobile\":null,\"merchantTxnId\":null}}";
		
	
		String token=getToken();
		System.out.println("Token ======>"+token);
		String encdata=encrypt(data,token);
		
		System.out.println(encdata);
		
		
//		String encString="HMQhSHq1bBlk6dg1EtAqBOr3lrLZ8DbodYv3M6+RBDWtAZ30BwEPVBzybQ6QCOaWDDVGGBdYBEEKv5g5S5m1ixc9Gs8AKpoGPUrQQCuwlvMWJTbCKXUc77TN91tbBoQraau0N27U7WEouNhGzqGFztJx/4nyvhEMloNQDIUNcddSj+/hPd+6DYsvHE3Ju6jo7XUfJbvNwOepFymARj7cC1t1Q3+8cY5fJZxlJaTfYF1Be4ETQ2SEb5E1ya5d19cddLi1hTtO1Pir6eo+G6dfJOdo0iAt8VKvzvXlV+09HzPq716x3hPg0UunmwpAv6P3IT8e3UIADCHWrfcKa7DDSQ4qHr+SKFe3kCEZg+ZQM0GZ9UoJzDLsFSepcR+F3MD1c4Nh0cX+R5f8dOppBxM/8VeBDE1SV8UNjCLqG5RAZVW8gDZf3wVeGPA83xE0mPZJi8I6LTNDz4FRxru5KzsnqpZjFYWGfctMD7mfl9CfTi6fLcUWS905hG4ejkYXqu4nY9ZXlrCEXiEo+8xU5f+JQmngCUs4W0E/9qQyY93+RnWS2ZbX6QgBOXjpw5lkmVG+lOitISbca/MHughSp+xq6MQXJ7Wz4G+uOeBPxv261Gv2jmoWYnr7BFXtrRttgFYaE36bH1zk5R4CTHhgmiwxeRggUPHho4vtG3xlC0tQrqnRMBYYGFZg7Bw7J6VgZlTD5Jq2uUCzOWW8nzqgRTPoiGh2K8rEJb4YdxBnAyKYClVyDmqmx07zbyMKgAbAqI09sTEeEcM0RYjsC1fed6ArQslQZelX64XN2eapBgj64e/6C3x73Eb6apUs0+ERtZV7qpZDei28y1DQzjLRbUVI8d80i4aFFbTqv5Yqh3uIYnC9PY4jpX/C9pJvGazpCi3WjXbbpFzLTiEG6EYGic+D+2+n/ue4cmpAjIrBEtaSHo/yUWwio6SH5y/d7KLRfYT4FrMkNPj6sjO6S+wIUncI+Cyh8PpevR4fKb/hiNkHS/qzFyNPTP52c6Y/ei9SE1JJkVt+TL6vLrNRM5ZG5ROWSIUg4fo5TMxQNyPIw/qHkuhrHhLunMP3WLoQfMzN6ekbH4DT2DzVgC2NIq6/wgz3159sLBuyF8rBDXESJjIuoUMomy+6MbrAjBpPBEe08Qfpw+v7arI+0QObMyoGw4j6hPQ0H5iGkCQoPsT1F8XaCOpTP9e0puceeyla6Zbe1W0MiBBNmBpYqpMnbYykCyc8sYz570A5xZK7M9GvcgUOy7AFpfyhSNB5vT5+tM/m33oeOZhtPHBTg6ga7KQ/qqFlGCT9Ql7o3t3Qt7Ra2CqAaRePzzMIWqtwsbwKA8s2Ouy38wA0B+XbwBX6rkIffv2BAgMaY7BdMfW13s5FEP+werdhEDDMhYksuz4Als70DRLWPeWxl7ruKhIKtGaRjH3raT+eNUKNyOn3ieXCeHXjs83ZODdNVh33rKsisJPtEZDhIk66qNhc0WDNpwpHDA==";
//		
//		System.out.println(decryptdata(encString,"3c6e79a4-0bc7-4098-8118-45f124c89dd5",PayResponse.class));
		
	}
	
	public static String getToken() {

		String token = UUID.randomUUID().toString();
		return token;
	}

	public static String encrypt(String data, String ivKey) throws Exception {
		ivKey = ivKey.substring(7);
		GcmPgEncryption gcmPgEncryption = new GcmPgEncryption(ivKey);
		String encrypteddata = Optional.ofNullable(gcmPgEncryption.encryptWithMKeys(data))
				.orElseThrow(() -> new EncDecException("Unable to Encrypt the Response Data"));
		return encrypteddata;
	}

	public static <T> T decryptdata(String encryptedData, String ivKey, Class<T> clazz) throws Exception {
		ivKey = ivKey.substring(7);
		GcmPgEncryption gcmPgEncryption = new GcmPgEncryption(ivKey);
		String decryptedData = Optional.ofNullable(gcmPgEncryption.decryptWithMKeys(encryptedData))
				.orElseThrow(() -> new EncDecException("Unable to Decrypt the Response Data"));
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(decryptedData, clazz);
	}
	
	

}
