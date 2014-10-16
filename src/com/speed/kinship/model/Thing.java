package com.speed.kinship.model;

import java.io.Serializable;
import java.util.Date;

public class Thing implements Serializable {

	private static final long serialVersionUID = -4658380569522122400L;
	private User user;
	private String title;
	private Date time;
	private String content;
	private Pic pic;
	
	public Thing(User user, String title, Date time, String content, Pic pic) {
		this.user = user;
		this.title = title;
		this.time = time;
		this.content = content;
		this.pic = pic;
	}
	
	public User getUser() {
		return user;
	}
	
	public String getTitle() {
		return title;
	}
	
	public Date getTime() {
		return time;
	}
	
	public String getContent() {
		return content;
	}
	
	public Pic getPic() {
		return pic;
	}
	
}
