package com.ftk.pg.util;
import org.apache.logging.log4j.Logger;

public class CustomExceptionLoggerError {

//	public static void customExceptionPrintTrace(Logger logger, Exception e) {
//
//		StackTraceElement[] arr = e.getStackTrace();
//
//		for (StackTraceElement s : arr) {
//			if (s.getClassName().contains("com.ftk")) {
//				logger.error("==> " + s.getClassName() + " : " + s.getMethodName() + " : " + s.getLineNumber() + " : "
//						+ e.getMessage());
//			}
//		}
//
//	}
//
//	public static void customExceptionPrintTrace(Logger logger, Exception e, String msg) {
//		StackTraceElement[] arr = e.getStackTrace();
//
//		for (StackTraceElement s : arr) {
//			if (s.getClassName().contains("com.ftk")) {
//				logger.error("==> " + msg + " : " + s.getClassName() + " : " + s.getMethodName() + " : "
//						+ s.getLineNumber() + " : " + e.getMessage());
//			}
//		}
//	}
//
//	public static void customExceptionPrintTraceSimple(Logger logger, Exception e) {
//		StackTraceElement[] arr = e.getStackTrace();
//
//		for (StackTraceElement s : arr) {
//			if (s.getClassName().contains("com.ftk")) {
//				logger.error("==> " + s.getClassName() + " : " + s.getMethodName() + " : " + s.getLineNumber() + " : "
//						+ e.getMessage());
//				break;
//			}
//		}
//
//	}
//
//	public static void customExceptionPrintTraceSimple(Logger logger, Exception e, String msg) {
//		StackTraceElement[] arr = e.getStackTrace();
//
//		for (StackTraceElement s : arr) {
//			if (s.getClassName().contains("com.ftk")) {
//				logger.error("==> " + msg + " : " + s.getClassName() + " : " + s.getMethodName() + " : "
//						+ s.getLineNumber() + " : " + e.getMessage());
//				break;
//			}
//		}
//	}
//
//	public static void customExceptionPrintTraceEmbeded(Logger logger, Exception e) {
//		StackTraceElement[] arr = e.getStackTrace();
//		String text = " ";
//
//		for (StackTraceElement s : arr) {
//			if (s.getClassName().contains("com.ftk")) {
//				text += s.getClassName() + " : " + s.getMethodName() + " : " + s.getLineNumber() + " > ";
//
//			}
//		}
//
//		logger.error("==> " + text + e.getMessage());
//	}
//
//	public static void customExceptionPrintTraceEmbeded(Logger logger, Exception e, String msg) {
//		StackTraceElement[] arr = e.getStackTrace();
//		String text = " ";
//
//		for (StackTraceElement s : arr) {
//			if (s.getClassName().contains("com.ftk")) {
//				text += s.getClassName() + " : " + s.getMethodName() + " : " + s.getLineNumber() + " > ";
//
//			}
//		}
//
//		logger.error("==> " + msg + text + e.getMessage());
//	}
}
