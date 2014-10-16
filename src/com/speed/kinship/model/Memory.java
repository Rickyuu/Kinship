package com.speed.kinship.model;

import java.io.Serializable;
import java.util.Date;

public class Memory implements Serializable {

	private static final long serialVersionUID = 5634620329221051057L;
	private User user;
	private String content;
	private Date time;
	
	public Memory(User user, String content, Date time) {
		this.user = user;
		this.content = content;
		this.time = time;
	}
	
	public User getUser() {
		return user;
	}
	
	public String getContent() {
		return content;
	}
	
	public Date getTime() {
		return time;
	}
	
}
