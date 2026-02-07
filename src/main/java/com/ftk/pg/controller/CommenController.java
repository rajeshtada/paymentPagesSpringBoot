package com.ftk.pg.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ftk.pg.encryption.AesEncryption;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.exception.ResourceNotFoundException;
import com.ftk.pg.util.Util;
import com.ftk.pg.util.Utilities;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("pg")
public class CommenController {

	private Logger logger = LoggerFactory.getLogger(CommenController.class);

	public static String IMAGE_ROOTPATH = "/media/shared/";

	@GetMapping(value = "api/showQrImage", produces = MediaType.IMAGE_PNG_VALUE)
	public void showQrImage(@RequestParam("i") String imagePath, HttpServletResponse response) throws IOException {
		response.setContentType(MediaType.IMAGE_PNG_VALUE);
		String imgPath = new String(Base64.getDecoder().decode(imagePath));

//		imgPath = imgPath.replace("/media/shared/lambda/pg/dynamicqrpath", "/mnt/efs/lambda/pg/dynamicqrpath/");
//		imgPath = imgPath.replace("/media/shared", "/mnt/efs");
		logger.info(" imgPath :  " + imgPath);

//		FileInputStream input = new FileInputStream(imgPath);
//		byte[] imageBytes = input.readAllBytes();
//		String base64String = Base64.getEncoder().encodeToString(imageBytes);
		try (FileInputStream input = new FileInputStream(imgPath)) {
			org.springframework.util.StreamUtils.copy(input, response.getOutputStream());
		}

	}

	@GetMapping("/api/showImage2")
	public void getThumbnails2(@RequestParam("i") String imagePath, HttpServletResponse response) throws Exception {
		try {

			if (imagePath.isEmpty()) {
				response.setStatus(HttpStatus.NOT_FOUND.value());
			} else {
				imagePath = imagePath.replaceAll(" ", "+");
				String decryptedImagePath = AesEncryption.decrypt(imagePath);
				if (decryptedImagePath != null) {
					logger.info(" decryptedImagePath :  " + decryptedImagePath);
//				String rootPath = "/media/shared";
//				String rootPath = "/mnt/efs";
					String rootPath = Utilities.IMAGE_ROOTPATH;
					String sourcePath = rootPath + File.separator + "partner-logo";
					String imgPath = sourcePath + File.separator + decryptedImagePath;

					File file = new File(imgPath);
					if (!file.exists()) {
						imgPath = rootPath + File.separator + decryptedImagePath;
						file = new File(imgPath);
					}

					logger.info(" imgPath :  " + imgPath);
					if (file.exists()) {
						setFileExtenction(response, file);
						InputStream in = new FileInputStream(file);
						IOUtils.copy(in, response.getOutputStream());
						IOUtils.closeQuietly(in);
						IOUtils.closeQuietly(response.getOutputStream());
						response.setStatus(HttpServletResponse.SC_OK);

					} else {
						response.setStatus(HttpStatus.NOT_FOUND.value());
					}
				}
			}
		} catch (IOException e) {
			new GlobalExceptionHandler().customException(e);
		}
	}

	private void setFileExtenction(HttpServletResponse response, File file) {
		String fileExtenction = getFileExtension(file.toString());
		if (fileExtenction.equalsIgnoreCase("jpeg")) {
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		} else if (fileExtenction.equalsIgnoreCase("png")) {
			response.setContentType(MediaType.IMAGE_PNG_VALUE);
		}
	}

	private String getFileExtension(String fName) {
		String fileName = new File(fName).getName();
		int dotIndex = fileName.lastIndexOf('.');
		return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
	}

	@GetMapping("showQrCode")
	public void showQrCode3(@RequestParam("qrCode") String qrCode, HttpServletResponse response) {

		try {
			byte[] decodedBytes = java.util.Base64.getDecoder().decode(qrCode);
			String decodedString = new String(decodedBytes);
			logger.info("qr path ==> " + decodedString);

			String qr = decodedString;
			qr = qr.toLowerCase().replace("staticauqr/", "");
			qr = qr.toLowerCase().replace("staticqrcode/", "");

			String imagesPath = Util.getQrImagePath(decodedString);

			logger.info("image path ==>>" + decodedString);
			File file = null;
			// file = new File(imagesPath + File.separator + qrCode);
			file = new File(decodedString);
			// View image
			if (file.exists()) {
				viewAllFiles(file, response);
			} else {
				String filePath = IMAGE_ROOTPATH + imagesPath;
				file = new File(filePath.trim());
				viewAllFiles(file, response);
			}

		} catch (Exception e) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			new GlobalExceptionHandler().customException(e);
			logger.info("Error in converting data in byteStreamCode ==> ");
		}

	}

	private void viewAllFiles(File file, HttpServletResponse response) {
		String fileExtenction = null;
		try {
			logger.info(file.getAbsolutePath());
			fileExtenction = getFileExtension(file.toString());

			if (fileExtenction.equalsIgnoreCase("png")) {
				InputStream in = new FileInputStream(file);
				response.setContentType(MediaType.IMAGE_PNG_VALUE);
				IOUtils.copy(in, response.getOutputStream());
				IOUtils.closeQuietly(in);
				IOUtils.closeQuietly(response.getOutputStream());
				response.setStatus(HttpServletResponse.SC_OK);
			} else if (fileExtenction.equalsIgnoreCase("jpeg")) {
				InputStream in = new FileInputStream(file);
				response.setContentType(MediaType.IMAGE_JPEG_VALUE);
				IOUtils.copy(in, response.getOutputStream());
				IOUtils.closeQuietly(in);
				IOUtils.closeQuietly(response.getOutputStream());
				response.setStatus(HttpServletResponse.SC_OK);
			} else if (fileExtenction.equalsIgnoreCase("jpg")) {
				InputStream in = new FileInputStream(file);
				response.setContentType(MediaType.IMAGE_JPEG_VALUE);
				IOUtils.copy(in, response.getOutputStream());
				IOUtils.closeQuietly(in);
				IOUtils.closeQuietly(response.getOutputStream());
				response.setStatus(HttpServletResponse.SC_OK);
			} else {
				logger.info("This file not valid ==> ");
			}
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
	}
}
