package com.rkvs.stBlockCh.model;

/*
 * Auther: @RaviS
 * Authenticate the user
 */
public class UserAuthentication {
	private String user;
	private String secrete;
	
	public UserAuthentication() {
		user = null;
		secrete = null;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String userId) {
		this.user = userId;
	}
	public String getSecrete() {
		return secrete;
	}
	public void setSecrete(String secrete) {
		this.secrete = secrete;
	}
	
	
}
