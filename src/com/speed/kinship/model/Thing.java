package com.speed.kinship.model;
/*# CSIT 6000B    
 *# Liang You      20229016   yliangao@connect.ust.hk
 *# Zhan Xiaojun   20244793   xzhanab@connect.ust.hk
 *# Tao Ye         20225905   ytaoac@connect.ust.hk  
 * */ 
import java.io.Serializable;
import java.util.Date;

public class Thing implements Serializable {

	private static final long serialVersionUID = -4658380569522122400L;
	private int id;
	private User creator;
	private String title;
	private Date time;
	private String content;
	private byte[] pic;
	
	public Thing() {
		
	}
	
	public int getId() {
		return id;
	}
	
	public User getCreator() {
		return creator;
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
	
	public byte[] getPic() {
		return pic;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setTime(Date time) {
		this.time = time;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setPic(byte[] pic) {
		this.pic = pic;
	}
	
}
