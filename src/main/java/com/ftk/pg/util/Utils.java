package com.ftk.pg.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class Utils {
	public static String REQUARY_API_URL = "REQUARY_API_URL";

	public static final String BOB_REQUERY_RESOURCE_PATH = "BOB_REQUERY_RESOURCE_PATH";

	public static final String REQUERY_ALLOWED_PROCESSOR = "REQUERY_ALLOWED_PROCESSOR";

	public static String TXN_SYNC_API_URL = "TXN_SYNC_API_URL";

	public static Set<String> handleProcessor(String processor) {
		if (processor.contains(",")) {
			return Arrays.stream(processor.split(",")).map(String::trim).collect(Collectors.toSet());
		} else {
			return Collections.singleton(processor.trim());
		}
	}

	public static String formatLocalDateTime(LocalDateTime localDateTime) {
		// Define the format pattern
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		// Convert LocalDateTime to String with the specified format
		return localDateTime.toLocalDate().format(formatter);
	}
	
    public static boolean isNumeric(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

}
