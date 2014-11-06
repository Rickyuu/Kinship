package com.speed.kinship.model;

import java.io.Serializable;

public class Feedback implements Serializable {

	private static final long serialVersionUID = 4405564064446098693L;
	private int id;
	private User creator;
	private String content;
	
	public Feedback() {
		
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
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
}
