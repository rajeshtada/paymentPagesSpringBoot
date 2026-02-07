package com.ftk.pg;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.util.S3Util;

import software.amazon.awssdk.services.s3.S3Client;

public class StreamLambdaHandler implements RequestStreamHandler {
	static Logger logger = LogManager.getLogger(StreamLambdaHandler.class);

	private final S3Client s3Client = S3Client.builder().build(); // AWS S3 client
	private static SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

	static {
		try {
			handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(PaymentpagesApplication.class);
		} catch (ContainerInitializationException e) {
			// if we fail here. We re-throw the exception to force another cold start
			new GlobalExceptionHandler().customException(e);
			throw new RuntimeException("Could not initialize Spring Boot application", e);
		}
	}

	@Override
	public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
//		String messageId = context.getAwsRequestId();
//		// Set the unique AWS request ID as the messageId
//		if (messageId == null || messageId.trim().equals("")) {
//			messageId = "G-" + UUID.randomUUID().toString();
//		} else {
//			messageId = context.getAwsRequestId();
//		}
//		ThreadContext.put("messageId", messageId);
		

		// Proxy the incoming request to the Spring Boot application
		handler.proxyStream(inputStream, outputStream, context);
		// After request processing, upload log files to S3
		
		String messageId = ThreadContext.get("messageId");
		if (messageId == null || messageId.trim().equals("")) {
			messageId = "G-" + UUID.randomUUID().toString();
		} else {
			messageId = context.getAwsRequestId();
		}
		
		String format = new SimpleDateFormat("yyyy-MM-dd_HH:mm").format(new Date());
		
//		S3Util.uploadLogsToS3("pg/"+format+"_"+messageId+"/", s3Client);
		// Clear the ThreadContext
		ThreadContext.clearAll();
	}
}