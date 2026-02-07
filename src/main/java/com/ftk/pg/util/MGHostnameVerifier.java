package com.ftk.pg.util;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class MGHostnameVerifier  implements HostnameVerifier{
	  public boolean verify(String hostname, SSLSession session) {
		// TODO Auto-generated method stub
		return true;
	}
}
