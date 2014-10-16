package com.speed.kinship.model;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = -3146997068513321809L;
	private String username;
	private String password;
	private Identity identity;
	
	private User(String username, String password, Identity identity) {
		this.username = username;
		this.password = password;
		this.identity = identity;
	}
	
	public String getUserName() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public Identity getIdentity() {
		return identity;
	}
	
}
