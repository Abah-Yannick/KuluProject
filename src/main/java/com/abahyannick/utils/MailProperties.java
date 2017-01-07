package com.abahyannick.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@PropertySource("application.properties")
public class MailProperties {

	@Value("${smtp.host}")
	private String host;
	
	@Value("${smtp.port}")
	private String port;
	
	@Value("${smtp.user}")
	private String user;
	
	@Value("${smtp.pass}")
	private String pass;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	
}
