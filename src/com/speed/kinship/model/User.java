package com.speed.kinship.model;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = -3146997068513321809L;
	private int id;
	private String userName;
	private String password;
	private Identity identity;
	
	public User() {
		
	}
	
	public int getId() {
		return id;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public Identity getIdentity() {
		return identity;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setIdentity(Identity identity) {
		this.identity = identity;
	}
	
}
