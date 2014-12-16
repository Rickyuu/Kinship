package com.speed.kinship.model;
/*# CSIT 6000B    
 *# Liang You      20229016   yliangao@connect.ust.hk
 *# Zhan Xiaojun   20244793   xzhanab@connect.ust.hk
 *# Tao Ye         20225905   ytaoac@connect.ust.hk  
 * */ 
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
