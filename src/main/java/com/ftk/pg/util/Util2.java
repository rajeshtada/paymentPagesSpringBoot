package com.ftk.pg.util;

import java.util.stream.Stream;

import com.ftk.pg.exception.ResourceNotFoundException;
import com.ftk.pg.responsevo.PaymentResponse;

public class Util2 {
	
	public static final String key = "dxW/a/raDOtWV9T/8UL8OLVig0am9k4kBMw4x9rddfg=";


	public static String maskStringValue(String value) {

		String maskString = "";
		try {
			String last = "";
			if (value.length() > 4) {
				last = value.substring(value.length() - 4);
			} else {
				last = value.substring(value.length() - 0);
			}
			int len = value.length() - last.length();
			String subString = value.substring(0, len);
			int size = subString.length();
			for (int i = 0; i < size; i++) {
				maskString += "*";
			}
			maskString += last;
		} catch (Exception e) {
		}
		return maskString;

	}

	public static String maskEmailStringValue(String email) {

		if (email != null && !email.equals("")) {

			int atIndex = email.indexOf('@');
			if (atIndex == -1) {
				return email;
			}
			int dotIndex = email.indexOf('.', atIndex);
			if (dotIndex == -1) {
				return email;
			}
			String prefix = email.substring(0, 3);
			String suffix = email.substring(dotIndex - 0);
			String mask = "";
			for (int i = 3; i < dotIndex - 3; i++) {
				mask += "*";
			}
			return prefix + mask + suffix;
		}
		return null;

	}

}
