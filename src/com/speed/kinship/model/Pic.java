package com.speed.kinship.model;

import java.io.Serializable;

public class Pic implements Serializable {

	private static final long serialVersionUID = 2184847377718388477L;
	private byte[] content;
	
	public Pic(byte[] content) {
		this.content = content;
	}
	
	public byte[] getContent() {
		return content;
	}
	
}
