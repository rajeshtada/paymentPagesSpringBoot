package com.ftk.pg.requestvo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

import jakarta.validation.constraints.Size;

@XmlRootElement(name = "channelpartnerloginreq")
@XmlAccessorType(XmlAccessType.FIELD)
public class RblChnlPrtnrLoginReqVo {

	@NotEmpty
	@Size(min = 1, max = 30)
	@XmlElement(name = "username")
	private String username;

	@NotEmpty
	@Size(min = 1, max = 40)
	@XmlElement(name = "password")
	private String password;

	@NotEmpty
	@Size(min = 1, max = 50)
	@XmlElement(name = "bcagent")
	private String agent;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	@Override
	public String toString() {
		return "RblChnlPrtnrLoginReqVo [username=" + username + ", password=" + password + ", agent=" + agent + "]";
	}

}
