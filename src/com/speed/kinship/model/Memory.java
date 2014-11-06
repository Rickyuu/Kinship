package com.speed.kinship.model;

import java.io.Serializable;
import java.util.Date;

public class Memory implements Serializable {

	private static final long serialVersionUID = 5634620329221051057L;
	private int id;
	private User creator;
	private String content;
	private Date time;
	
	public Memory() {
		
	}
	
	public int getId() {
		return id;
	}
	
	public User getCreator() {
		return creator;
	}
	
	public String getContent() {
		return content;
	}
	
	public Date getTime() {
		return time;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setTime(Date time) {
		this.time = time;
	}
	
}
