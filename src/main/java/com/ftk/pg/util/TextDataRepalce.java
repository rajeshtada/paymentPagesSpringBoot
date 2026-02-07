package com.ftk.pg.util;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

public class TextDataRepalce {
	
	static Logger logger = LogManager.getLogger(TextDataRepalce.class);

	public static String getTextFromModel(Map<String, Object> map, String data) {
		try {
			logger.info("Inside getTextFromModel ");
			VelocityEngine velocityEngine = new VelocityEngine();
			velocityEngine.init();
			VelocityContext context = new VelocityContext();
			Set<Entry<String, Object>> entrySet = map.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				context.put(entry.getKey(), entry.getValue());
			}
			StringWriter writer = new StringWriter();
			Velocity.evaluate(context, writer, "mystring", data);
			logger.info(writer.toString());
			return writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Exception In getTextFromModel: " + e.getMessage());
			return null;
		}
	}
	
	public static java.util.Date getYearMonthDateFormat(String fromDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return format.parse(fromDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static java.util.Date getMonthDateYearFormat(String fromDate) {
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		try {
			return format.parse(fromDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
