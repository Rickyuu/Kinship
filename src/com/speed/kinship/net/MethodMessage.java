package com.speed.kinship.net;
/*# CSIT 6000B    
 *# Liang You      20229016   yliangao@connect.ust.hk
 *# Zhan Xiaojun   20244793   xzhanab@connect.ust.hk
 *# Tao Ye         20225905   ytaoac@connect.ust.hk  
 * */ 
import java.io.Serializable;

public class MethodMessage implements Serializable {

	private static final long serialVersionUID = 2388822513641439611L;
	private String name;
	private Arguments arguments;
	
	public MethodMessage(String name, Arguments arguments) {
		this.name = name;
		this.arguments = arguments;
	}
	
	public String getName() {
		return name;
	}
	
	public Arguments getArguments() {
		return arguments;
	}
	
}
