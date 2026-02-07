package com.ftk.pg.requestvo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "channelpartnerloginres")
@XmlAccessorType(XmlAccessType.FIELD)
public class RblChnlPrtnrLoginResVo {

	@XmlElement(name = "sessiontoken")
	private String sessiontoken;

	@XmlElement(name = "timeout")
	private String timeout;

	@XmlElement(name = "status")
	private int status;

	public String getSessiontoken() {
		return sessiontoken;
	}

	public void setSessiontoken(String sessiontoken) {
		this.sessiontoken = sessiontoken;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "RblChnlPrtnrLoginResVo [sessiontoken=" + sessiontoken + ", timeout=" + timeout + ", status=" + status
				+ "]";
	}

}
