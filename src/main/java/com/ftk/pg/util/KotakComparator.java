package com.ftk.pg.util;

import java.util.Comparator;

public class KotakComparator implements Comparator<String> {

	@Override
	public int compare(String o1, String o2) {
		int result = o1.compareTo(o2);
        if(result==0)
            result = o1.compareTo(o2);
        return result;
	}
	
	

}
