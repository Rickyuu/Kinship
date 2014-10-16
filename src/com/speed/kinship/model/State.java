package com.speed.kinship.model;

import java.io.Serializable;
import java.util.Date;

public class State implements Serializable {

	private static final long serialVersionUID = 1331000042887924714L;
	private User user;
	private Date time;
	private String content;
	private Pic pic;
	private Feedback[] feedbacks;
	
	public State(User user, Date time, String content, Pic pic) {
		this.user = user;
		this.time = time;
		this.content = content;
		this.pic = pic;
	}
	
	public State(User user, Date time, String content) {
		this.user = user;
		this.time = time;
		this.content = content;
	}
	
	public User getUser() {
		return user;
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
	
	public Feedback[] getFeedbacks() {
		return feedbacks;
	}
	
}
