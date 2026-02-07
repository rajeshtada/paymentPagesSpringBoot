package com.ftk.pg.util;

import java.io.File;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

public class S3Util {

	private static final Logger logger = LogManager.getLogger(S3Util.class);

	/* PROD */
	private final static String bucketName = "lambdafrmlog";
	/* UAT */
//	private final static String bucketName = "getepay-logs";
	
//	private final static String bucketName = "ecom-p" ;

	public static void uploadLogsToS3(String prefix, S3Client s3Client) {
		try {
			File logDirectory = new File("/tmp/app/");
			for (File logFile : logDirectory.listFiles()) {
				if (logFile.getName().endsWith(".log") || logFile.getName().endsWith(".gz")) {
					String s3FileName = null;
					if (logFile.getName().equals("app-${ctx:messageId}.log")) {
						String messageId = "G-" + UUID.randomUUID().toString();
						s3FileName = prefix + logFile.getName().replace("${ctx:messageId}", messageId);
					} else {
						s3FileName = prefix + logFile.getName();
					}
					PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(s3FileName)
							.build();
					s3Client.putObject(putObjectRequest, Paths.get(logFile.getAbsolutePath()));

					System.out.println("Uploaded log file: " + logFile.getName());
					// Delete the file from /tmp after upload
					if (!logFile.getName().equals("app-${ctx:messageId}.log")) {
						if (!logFile.delete()) {
							System.out.println("Failed to delete log file: " + s3FileName);
						}
					}
				}
			}
		} catch (Exception e) {
//			System.out.println(" :: " + e.getMessage() + " : "+e.getCause());
			logger.info(e.getMessage() + " : " + e.getCause());

//			e.printStackTrace(); // Debugging purposes (do not log recursively)
		}
	}
}
