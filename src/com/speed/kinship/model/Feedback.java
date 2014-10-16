package com.speed.kinship.model;

import java.io.Serializable;

public class Feedback implements Serializable {

	private static final long serialVersionUID = 4405564064446098693L;
	private Identity identity;
	private String content;
	
	public Feedback(Identity identity, String content) {
		this.identity = identity;
		this.content = content;
	}
	
	public Identity getIdentity() {
		return identity;
	}
	
	public String getContent() {
		return content;
	}
	
}
